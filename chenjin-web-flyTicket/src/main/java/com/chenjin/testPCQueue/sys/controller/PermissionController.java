package com.chenjin.testPCQueue.sys.controller;

import com.chenjin.testPCQueue.common.entity.PageRequest;
import com.chenjin.testPCQueue.common.framework.annotation.CurrentUser;
import com.chenjin.testPCQueue.common.web.controller.BaseController;
import com.chenjin.testPCQueue.commons.CommonProperties;
import com.chenjin.testPCQueue.sys.dto.Message;
import com.chenjin.testPCQueue.sys.entity.*;
import com.chenjin.testPCQueue.sys.service.IMenuService;
import com.chenjin.testPCQueue.sys.service.IPermissionService;
import com.chenjin.testPCQueue.sys.service.IResourceService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping({"/sys/permission"})
public class PermissionController extends BaseController
{

    @javax.annotation.Resource
    private IResourceService resourceService;

    @javax.annotation.Resource
    private IMenuService menuService;

    @javax.annotation.Resource
    private IPermissionService permissionService;
    private static Logger logger = LoggerFactory.getLogger(PermissionController.class);

    @RequestMapping({""})
    public String home()
    {
        return "sys/permission/list";
    }

    @RequestMapping({"/list"})
    @ResponseBody
    public List<Resource> list(Long groupId, Long parentId, @CurrentUser User user)
    {
        System.out.println("groupId" + groupId);
        System.out.println("parentId" + parentId);
        Integer orgType = user.getOrganization().getOrgType();

        System.out.println("orgType" + orgType);
        List l = new ArrayList();

        PageRequest page = new PageRequest();
        page.getQuery().put("t#parentId_L_EQ", parentId);
        List<Menu> listmenu = this.menuService.list(user.getProjectCode(), page);
        Map pmMap = new HashMap();
        if ((orgType.intValue() != 9) && (orgType.intValue() != 5)) {
            List pmList = this.permissionService.getMyPermission(user.getProjectCode(), user.getId());
            pmMap = makePmMap(pmList);
        }
        System.out.println("pmMap====");
        System.out.println(pmMap);

        for (Menu menu : listmenu) {
            Resource pr = (Resource)this.resourceService.getById(CommonProperties.MAIN_PROJECTDS, menu.getResourceId());
            if (pr != null) {
                Long resourceId = pr.getId();
                if ((orgType.intValue() != 9) && (orgType.intValue() != 5) &&
                        (pmMap.get(resourceId) == null)) {
                    System.out.println("pmMap.get(resourceId+) == null");
                }
                else
                {
                    isPermission(user.getProjectCode(), pr, groupId);
                    l.add(pr);

                    page = new PageRequest();
                    page.getQuery().put("t#parentId_L_EQ", pr.getId());
                    List<Resource> listResource = this.resourceService.list(CommonProperties.MAIN_PROJECTDS, page);
                    for (Resource resource : listResource) {
                        isPermission(user.getProjectCode(), resource, groupId);
                        l.add(resource);
                    }
                }
            }
        }
        System.out.println("l.size=" + l.size());
        return l;
    }

    private Map<String, String> makePmMap(List<Map<String, Object>> pmList) {
        Map m = new HashMap();
        try {
            if (pmList == null) return m;
            for (Map map : pmList) {
                String rid = (String)map.get("RESOURCEID");
                m.put(rid, rid);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return m;
    }

    private void isPermission(String projectCode, Resource resource, Long groupId)
    {
        Permission pp = this.permissionService.findByRG(projectCode, resource.getId(), groupId);
        if (pp != null)
            resource.setSegmentStr("1");
    }

    @RequestMapping(value={"/add"}, method={org.springframework.web.bind.annotation.RequestMethod.GET})
    public String add()
    {
        return "sys/permission/add";
    }

    @RequestMapping(value={"/add"}, method={org.springframework.web.bind.annotation.RequestMethod.POST})
    @ResponseBody
    public Message add(Long resourceId, Long groupId, Long parentId, @CurrentUser User user)
    {
        Message message = new Message();
        try {
            System.out.println("resourceId=" + resourceId);
            System.out.println("groupId=" + groupId);
            System.out.println("parentId=" + parentId);
            Permission permission = new Permission();
            Resource r = (Resource)this.resourceService.getById(CommonProperties.MAIN_PROJECTDS, resourceId);
            Group g = new Group();
            g.setId(groupId);
            permission.setResourceId(resourceId);
            permission.setGroup(g);
            this.permissionService.save(user.getProjectCode(), permission);

            Menu pm = (Menu)this.menuService.getById(user.getProjectCode(), parentId);

            Permission pp = this.permissionService.findByRG(user.getProjectCode(), pm.getResourceId(), groupId);
            if (pp == null) {
                pp = new Permission();
                pp.setResourceId(pm.getResourceId());
                pp.setGroup(g);
                this.permissionService.save(user.getProjectCode(), pp);
                logger.info(user.getEmpId() + "添加了" + g.getName() + "的新权限[" + r.getName() + "]");
            }
            message.setSuccess(true);
        } catch (Exception e) {
            e.printStackTrace();
            message.setSuccess(false);
        }
        return message;
    }

    @RequestMapping(value={"/del"}, method={org.springframework.web.bind.annotation.RequestMethod.POST})
    @ResponseBody
    public Message del(Long resourceId, Long groupId, Long parentId, @CurrentUser User user)
    {
        System.out.println("删除resourceId=" + resourceId);
        System.out.println("groupId=" + groupId);
        System.out.println("parentId=" + parentId);
        Resource r = (Resource)this.resourceService.getById(CommonProperties.MAIN_PROJECTDS, resourceId);
        Message message = new Message();
        try {
            Permission p = this.permissionService.findByRG(user.getProjectCode(), resourceId, groupId);
            if (p != null) {
                this.permissionService.delete(user.getProjectCode(), p);

                int needDelP = 1;
                PageRequest page = new PageRequest();
                page.getQuery().put("t#parentId_L_EQ", parentId);
                List<Menu> listmenu = this.menuService.list(user.getProjectCode(), page);
                for (Menu menu : listmenu) {
                    Permission per = this.permissionService.findByRG(user.getProjectCode(), menu.getResourceId(), groupId);
                    if (per != null) {
                        needDelP = 0;
                        break;
                    }
                }

                if (needDelP == 1) {
                    System.out.println("删除parentId的权限");
                    Menu pm = (Menu)this.menuService.getById(user.getProjectCode(), parentId);
                    Permission pp = this.permissionService.findByRG(user.getProjectCode(), pm.getResourceId(), groupId);
                    if (pp != null) {
                        this.permissionService.delete(pp);
                        logger.info(user.getEmpId() + "删除了" + pp.getGroup().getName() + "的权限[" + r.getName() + "]");
                    }
                }
            }
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
    public Message delete(Permission am, int type)
    {
        Message message = new Message();
        try
        {
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