package com.chenjin.testPCQueue.sys.controller;

import com.chenjin.testPCQueue.common.entity.DataGrid;
import com.chenjin.testPCQueue.common.entity.PageRequest;
import com.chenjin.testPCQueue.common.entity.Sort;
import com.chenjin.testPCQueue.common.framework.annotation.CurrentUser;
import com.chenjin.testPCQueue.common.web.controller.BaseController;
import com.chenjin.testPCQueue.commons.CommonProperties;
import com.chenjin.testPCQueue.sys.dto.Message;
import com.chenjin.testPCQueue.sys.entity.*;
import com.chenjin.testPCQueue.sys.service.IGroupUserService;
import com.chenjin.testPCQueue.sys.service.IMenuService;
import com.chenjin.testPCQueue.sys.service.IPermissionService;
import com.chenjin.testPCQueue.sys.service.IResourceService;
import com.chenjin.testPCQueue.sys.service.realm.ShiroChainDefinitionsManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping({"/sys/menu"})
public class MenuController extends BaseController
{

    @javax.annotation.Resource
    private IMenuService menuService;

    @javax.annotation.Resource
    private IResourceService resourceService;

    @javax.annotation.Resource
    private ShiroChainDefinitionsManager shiroChainDefinitionsManager;

    @javax.annotation.Resource
    private IPermissionService permissionService;

    @javax.annotation.Resource
    private IGroupUserService groupUserService;

    @RequestMapping({""})
    public String home()
    {
        return "sys/menu/list";
    }

    @RequestMapping({"/list"})
    @ResponseBody
    public List<Menu> list(String sysId, String orgType, PageRequest pageable, @CurrentUser User currentUser)
    {
        pageable.getQuery().put("t#sysId_S_EQ", sysId);
        pageable.getQuery().put("t#orgType_S_EQ", orgType);
        pageable.setSort(new Sort(Sort.Direction.ASC, new String[] { "sort" }));
        List<Menu> list = this.menuService.list(currentUser.getProjectCode(), pageable);

        for (Menu menu : list) {
            Resource r = (Resource)this.resourceService.getById(CommonProperties.MAIN_PROJECTDS, menu.getResourceId());
            if (menu.getName() == null) {
                menu.setName(r.getName());
            }
            if (menu.getUrl() == null) {
                menu.setUrl(r.getUrl());
            }
            if (menu.getIcon() == null) {
                menu.setIcon(r.getIcon());
            }
        }
        return list;
    }

    @RequestMapping({"/perlist"})
    @ResponseBody
    public List<Menu> perlist(String sysId, String orgType, PageRequest pageable, @CurrentUser User currentUser)
    {
        pageable.getQuery().put("t#sysId_S_EQ", sysId);
        pageable.getQuery().put("t#orgType_S_EQ", orgType);
        pageable.setSort(new Sort(Sort.Direction.ASC, new String[] { "sort" }));
        List<Menu> list = this.menuService.list(currentUser.getProjectCode(), pageable);

        for (Menu menu : list) {
            Resource r = (Resource)this.resourceService.getById(CommonProperties.MAIN_PROJECTDS, menu.getResourceId());
            if (menu.getName() == null) {
                menu.setName(r.getName());
            }
            if (menu.getUrl() == null) {
                menu.setUrl(r.getUrl());
            }
            if (menu.getIcon() == null) {
                menu.setIcon(r.getIcon());
            }
        }
        String empid = currentUser.getEmpId();
        if (!empid.equals("admin")) {
            Object set = new HashSet();
            pageable = new PageRequest();
            pageable.getQuery().put("t#group.organization.id_S_EQ", currentUser.getOrganization().getId());
            pageable.getQuery().put("t#user.id_L_EQ", currentUser.getId());
            List <GroupUser> gulist= this.groupUserService.list(currentUser.getProjectCode(), pageable);
            List perlist;
            Permission permission;
            for (GroupUser groupUser : gulist) {
                System.out.println(groupUser.getGroup().getName());
                pageable = new PageRequest();

                pageable.getQuery().put("t#group.id_L_EQ", groupUser.getGroup().getId());
                perlist = this.permissionService.list(currentUser.getProjectCode(), pageable);
                for (Iterator localIterator3 = perlist.iterator(); localIterator3.hasNext(); ) { permission = (Permission)localIterator3.next();
                    ((Set)set).add(permission.getResourceId());
                }
            }

            Map perMap = new HashMap();
            for (Object resourceId : (Set)set) {
                perMap.put(resourceId, resourceId);
                System.out.println(resourceId);
            }

            List<Menu> rtnlist = new ArrayList();
            for (Menu menu : list) {
                if (perMap.get(menu.getResourceId()) != null) {
                    ((List)rtnlist).add(menu);
                }
            }
            return rtnlist;
        }

        return (List<Menu>)(List<Menu>)list;
    }

    @RequestMapping({"/resourcePage"})
    @ResponseBody
    public DataGrid<Resource> resourcePage(String sysId, String orgType, Long parentId, PageRequest pageable, @CurrentUser User user)
    {
        DataGrid page = new DataGrid();
        if ((orgType == null) || (parentId == null)) {
            return page;
        }
        Map querym = pageable.getQuery();
        if (querym == null)
            querym = new HashMap();
        querym.put("t#type_S_EQ", "F");
        pageable.setQuery(querym);
        page = this.resourceService.query(CommonProperties.MAIN_PROJECTDS, pageable);
        List<Resource> rows = page.getRows();
        for (Resource r : rows) {
            Menu m = new Menu();
            m.setOrgType(orgType);
            m.setParentId(parentId);
            m.setResourceId(r.getId());
            m.setSysId(sysId);
            m = this.menuService.findByOrgTypeAndParentId(user.getProjectCode(), m);

            if (m != null)
                r.setState("1");
            else {
                r.setState("0");
            }
        }
        return page;
    }

    @RequestMapping({"/lvlone"})
    @ResponseBody
    public List<Menu> lvlone(String orgType, PageRequest pageable, @CurrentUser User user)
    {
        pageable.getQuery().put("t#orgType_S_EQ", orgType);
        pageable.getQuery().put("t#parentId_L_EQ", Long.valueOf(Long.parseLong("-1")));
        List<Menu> list = this.menuService.list(user.getProjectCode(), pageable);
        for (Menu menu : list) {
            Resource r = (Resource)this.resourceService.getById(menu.getResourceId());
            menu.setSegmentStr(r.getName());
        }
        return list;
    }

    @RequestMapping(value={"/add"}, method={org.springframework.web.bind.annotation.RequestMethod.GET})
    public String add(String sysId, String orgType, Long parentId, Model model, @CurrentUser User user)
    {
        model.addAttribute("sysId", sysId);
        model.addAttribute("orgType", orgType);
        model.addAttribute("parentId", parentId);
        if (parentId.longValue() == -1L) {
            model.addAttribute("parentName", "根节点");
        } else {
            Menu m = (Menu)this.menuService.getById(user.getProjectCode(), parentId);
            if (m.getName() != null) {
                model.addAttribute("parentName", m.getName());
            } else {
                Resource r = (Resource)this.resourceService.getById(user.getProjectCode(), m.getResourceId());
                model.addAttribute("parentName", r.getName());
            }
        }
        return "sys/menu/add";
    }

    @RequestMapping(value={"/add"}, method={org.springframework.web.bind.annotation.RequestMethod.POST})
    @ResponseBody
    public Message add(Menu menu, @CurrentUser User user)
    {
        Message message = new Message();
        try {
            Integer sort = this.menuService.getNewSort(user.getProjectCode(), menu.getParentId());
            menu.setSort(sort);
            menu = (Menu)this.menuService.save(user.getProjectCode(), menu);
            if (menu.getParentId() == null) {
                menu.setParentId(menu.getId());
                this.menuService.update(user.getProjectCode(), menu);
            }
            message.setSuccess(true);
        } catch (Exception e) {
            e.printStackTrace();
            message.setSuccess(false);
        }
        return message;
    }

    @RequestMapping(value={"/edit"}, method={org.springframework.web.bind.annotation.RequestMethod.GET})
    public String edit(Long id, Model model, @CurrentUser User user)
    {
        if (id != null) {
            model.addAttribute("id", id);
            Menu m = (Menu)this.menuService.getById(user.getProjectCode(), id);
            model.addAttribute("sysId", m.getSysId());
            model.addAttribute("orgType", m.getOrgType());

            model.addAttribute("name", m.getName());
            if (m.getParentId().longValue() == -1L)
                model.addAttribute("parentId", "");
            else {
                model.addAttribute("parentId", m.getParentId());
            }

            Resource r = (Resource)this.resourceService.getById(user.getProjectCode(), m.getResourceId());
            model.addAttribute("resourceName", r.getName());
        }
        return "sys/menu/edit";
    }

    @RequestMapping(value={"/edit"}, method={org.springframework.web.bind.annotation.RequestMethod.POST})
    @ResponseBody
    public Message edit(Menu menu, @CurrentUser User user)
    {
        Message message = new Message();
        try {
            if (menu.getParentId() == null) {
                menu.setParentId(Long.valueOf(Long.parseLong("-1")));
            }
            this.menuService.updateWithInclude(user.getProjectCode(), menu, new String[] { "name", "parentId" });
            message.setSuccess(true);
        } catch (Exception e) {
            e.printStackTrace();
            message.setSuccess(false);
        }
        return message;
    }

    @RequestMapping(value={"/del"}, method={org.springframework.web.bind.annotation.RequestMethod.POST})
    @ResponseBody
    public Message del(Menu menu, @CurrentUser User user)
    {
        Message message = new Message();
        try {
            Menu m = this.menuService.findByOrgTypeAndParentId(user.getProjectCode(), menu);
            if (m != null)
                this.menuService.delete(user.getProjectCode(), m);
        }
        catch (Exception e) {
            e.printStackTrace();
            message.setSuccess(false);
            message.setMsg(e.getMessage());
        }

        return message;
    }

    @RequestMapping(value={"/delete"}, method={org.springframework.web.bind.annotation.RequestMethod.POST})
    @ResponseBody
    public Message delete(Long id, @CurrentUser User user)
    {
        Message message = new Message();
        try
        {
            this.menuService.delete(user.getProjectCode(), id);
            message.setSuccess(true);
        } catch (Exception e) {
            e.printStackTrace();
            message.setSuccess(false);
        }

        return message;
    }

    @RequestMapping(value={"/drag"}, method={org.springframework.web.bind.annotation.RequestMethod.POST})
    @ResponseBody
    public Message drag(Long targetId, Long sourceId, String point, @CurrentUser User user)
    {
        Message message = new Message();
        try {
            System.out.println("资源:" + sourceId + ",目标:" + targetId + ",位子:" + point);
            this.menuService.drag(user.getProjectCode(), targetId, sourceId, point);
            message.setSuccess(true);
        } catch (Exception e) {
            e.printStackTrace();
            message.setSuccess(false);
        }
        return message;
    }

    protected void init(WebDataBinder binder)
    {
    }
}