package com.chenjin.sys.controller;

import com.chenjin.common.framework.annotation.CurrentUser;
import com.chenjin.common.web.controller.BaseController;
import com.chenjin.sys.dto.Message;
import com.chenjin.sys.entity.User;
import com.chenjin.sys.service.IResourceService;
import com.chenjin.sys.service.realm.ShiroChainDefinitionsManager;
import java.util.List;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping({"/sys/authority"})
public class AuthorityController extends BaseController
{

    @javax.annotation.Resource
    private IResourceService resourceService;

    @javax.annotation.Resource
    private ShiroChainDefinitionsManager shiroChainDefinitionsManager;

    @RequestMapping({""})
    public String home()
    {
        return "sys/authority/list";
    }

    @RequestMapping({"/list"})
    @ResponseBody
    public List<com.chenjin.sys.entity.Resource> list(Long id, @CurrentUser User user)
    {
        List list = this.resourceService.getChlidList(user.getProjectCode(), id, "O");
        return list;
    }

    @RequestMapping(value={"/add"}, method={org.springframework.web.bind.annotation.RequestMethod.GET})
    public String add()
    {
        return "sys/authority/add";
    }

    @RequestMapping({"/add"})
    @ResponseBody
    public Message add(com.chenjin.sys.entity.Resource resource, @CurrentUser User user)
    {
        Message message = new Message();
        try {
            this.resourceService.save(user.getProjectCode(), resource);
            shiroRefresh(user.getProjectCode());
            message.setSuccess(true);
        } catch (Exception e) {
            e.printStackTrace();
            message.setSuccess(false);
        }
        return message;
    }

    @RequestMapping(value={"/edit"}, method={org.springframework.web.bind.annotation.RequestMethod.GET})
    public String edit()
    {
        return "sys/authority/edit";
    }

    @RequestMapping(value={"/edit"}, method={org.springframework.web.bind.annotation.RequestMethod.POST})
    @ResponseBody
    public Message edit(com.chenjin.sys.entity.Resource resource, @CurrentUser User user)
    {
        Message message = new Message();
        try {
            this.resourceService.updateWithInclude(user.getProjectCode(), resource, new String[] { "name", "url", "permCode" });
            shiroRefresh(user.getProjectCode());
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
            this.resourceService.delete(user.getProjectCode(), id);
            shiroRefresh(user.getProjectCode());
            message.setSuccess(true);
        } catch (Exception e) {
            e.printStackTrace();
            message.setSuccess(false);
        }

        return message;
    }

    private void shiroRefresh(String projectCode) {
        List list = this.resourceService.getAllForShiro(projectCode);

        this.shiroChainDefinitionsManager.updateFilterChains(list);
    }

    protected void init(WebDataBinder binder)
    {
    }
}