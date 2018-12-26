package com.chenjin.sys.controller;

import com.chenjin.common.entity.DataGrid;
import com.chenjin.common.entity.PageRequest;
import com.chenjin.common.entity.Sort;
import com.chenjin.common.entity.Sort.Direction;
import com.chenjin.common.entity.Sort.Order;
import com.chenjin.common.framework.annotation.CurrentUser;
import com.chenjin.common.web.controller.BaseController;
import com.chenjin.sys.dto.Message;
import com.chenjin.sys.entity.Group;
import com.chenjin.sys.entity.GroupUser;
import com.chenjin.sys.entity.Organization;
import com.chenjin.sys.entity.User;
import com.chenjin.sys.service.IGroupService;
import com.chenjin.sys.service.IGroupUserService;
import com.chenjin.sys.service.IUserService;
import java.io.PrintStream;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping({"/sys/group"})
public class GroupController extends BaseController
{

    @Resource
    private IUserService userService;

    @Resource
    private IGroupService groupService;

    @Resource
    private IGroupUserService groupUserService;

    @RequestMapping({""})
    public String home()
    {
        return "sys/group/list";
    }

    @RequestMapping({"/page"})
    @ResponseBody
    public DataGrid<Group> page(PageRequest pageable, @CurrentUser User currentUser)
    {
        Integer orgType = currentUser.getOrganization().getOrgType();
        if ((orgType.intValue() != 5) && (orgType.intValue() != 9)) {
            Map query = pageable.getQuery();
            query.put("t#orgType_S_EQ", orgType);
            query.put("t#organization.id_S_EQ", currentUser.getOrganization().getId());
        }
        Sort sort = new Sort(new Sort.Order[] { new Sort.Order(Sort.Direction.ASC, "orgType"), new Sort.Order(Sort.Direction.ASC, "organizationId") });
        pageable.setSort(sort);
        DataGrid page = this.groupService.query(currentUser.getProjectCode(), pageable);

        return page;
    }

    @RequestMapping({"/list"})
    @ResponseBody
    public List<GroupUser> list(PageRequest pageable, @CurrentUser User user)
    {
        List list = this.groupUserService.list(user.getProjectCode(), pageable);
        return list;
    }

    @RequestMapping({"/groupUserPage"})
    @ResponseBody
    public DataGrid<User> groupUserPage(PageRequest pageable, Long groupId, String name, @CurrentUser User currentUser)
    {
        DataGrid page = new DataGrid();
        Group g = (Group)this.groupService.getById(currentUser.getProjectCode(), groupId);
        if ((g == null) || (g.getOrgType() == null)) {
            return page;
        }

        Map query = pageable.getQuery();
        query.put("t#name_S_LK", name);
        query.put("t#organization.orgType_I_EQ", g.getOrgType());
        query.put("t#organization.id_I_EQ", g.getOrganization().getId());
        page = this.userService.query(currentUser.getProjectCode(), pageable);
        for (User user : (List<User>)page.getRows()) {
            GroupUser gu = this.groupUserService.findByKey(currentUser.getProjectCode(), user.getId(), g.getId());
            if (gu == null)
            {
                user.setIsSelected(Integer.valueOf(0));
            }
            else {
                user.setIsSelected(Integer.valueOf(1));
            }
        }
        return page;
    }

    @RequestMapping(value={"/add"}, method={org.springframework.web.bind.annotation.RequestMethod.GET})
    public String add()
    {
        return "sys/group/add";
    }

    @RequestMapping(value={"/add"}, method={org.springframework.web.bind.annotation.RequestMethod.POST})
    @ResponseBody
    public Message add(Group group, PageRequest pageable, @CurrentUser User currentUser)
    {
        Message message = new Message();
        try {
            Integer orgType = currentUser.getOrganization().getOrgType();
            if ((orgType.intValue() == 5) || (orgType.intValue() == 9)) {
                group.setParentId(Long.valueOf(Long.parseLong("-1")));
            } else {
                Map query = pageable.getQuery();
                query.put("t#user.id_L_EQ", currentUser.getId());
                query.put("t#group.parentId_L_EQ", Long.valueOf(Long.parseLong("-1")));
                List g = this.groupUserService.list(currentUser.getProjectCode(), pageable);
                System.out.println("g.size =====" + g.size());
                if ((g == null) || (g.size() != 1)) {
                    throw new Exception("新增失败，数据异常");
                }
                group.setParentId(((GroupUser)g.get(0)).getGroup().getId());

                group.setOrganization(currentUser.getOrganization());
                group.setOrgType(String.valueOf(orgType));
            }
            this.groupService.save(currentUser.getProjectCode(), group);
        } catch (Exception e) {
            e.printStackTrace();
            message.setSuccess(false);
            message.setMsg(e.getMessage());
        }
        return message;
    }

    @RequestMapping(value={"/edit"}, method={org.springframework.web.bind.annotation.RequestMethod.GET})
    public String edit(Long orgType, Long orgId, Model model)
    {
        if (orgType != null) {
            model.addAttribute("combox1", orgType);
        }
        if (orgId != null) {
            model.addAttribute("combox2", orgId);
        }
        return "sys/group/edit";
    }

    @RequestMapping(value={"/edit"}, method={org.springframework.web.bind.annotation.RequestMethod.POST})
    @ResponseBody
    public Message edit(Group group, @CurrentUser User currentUser)
    {
        Message message = new Message();
        try {
            Integer orgType = currentUser.getOrganization().getOrgType();
            if ((orgType.intValue() != 5) && (orgType.intValue() != 9)) {
                group.setOrgType(String.valueOf(orgType));
                group.setOrganization(currentUser.getOrganization());
            }
            this.groupService.updateWithInclude(currentUser.getProjectCode(), group, new String[] { "orgType", "organization", "name" });
            message.setSuccess(true);
        } catch (Exception e) {
            e.printStackTrace();
            message.setSuccess(false);
        }

        return message;
    }

    @RequestMapping(value={"/del"}, method={org.springframework.web.bind.annotation.RequestMethod.POST})
    @ResponseBody
    public Message del(Long id, @CurrentUser User user)
    {
        Message message = new Message();
        try
        {
            this.groupService.delete(user.getProjectCode(), id);
            message.setSuccess(true);
        } catch (Exception e) {
            e.printStackTrace();
            message.setSuccess(false);
            message.setMsg(e.getMessage());
        }

        return message;
    }

    protected void init(WebDataBinder binder)
    {
    }
}