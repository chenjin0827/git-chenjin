package com.chenjin.sys.controller;

import com.chenjin.common.entity.PageRequest;
import com.chenjin.common.framework.annotation.CurrentUser;
import com.chenjin.common.web.controller.BaseController;
import com.chenjin.sys.entity.Organization;
import com.chenjin.sys.entity.User;
import com.chenjin.sys.service.IOrganizationService;
import java.util.List;
import javax.annotation.Resource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping({"/sys/organization"})
public class OrganizationController extends BaseController
{

    @Resource
    private IOrganizationService organizationService;

    @RequestMapping({""})
    public String home()
    {
        return "sys/organization/list";
    }

    @RequestMapping({"/list"})
    @ResponseBody
    public List<Organization> list(PageRequest pageable, @CurrentUser User user)
    {
        List list = this.organizationService.list(user.getProjectCode(), pageable);
        return list;
    }

    protected void init(WebDataBinder binder)
    {
    }
}