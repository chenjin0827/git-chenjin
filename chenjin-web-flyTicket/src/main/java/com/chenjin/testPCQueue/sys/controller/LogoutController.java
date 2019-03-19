package com.chenjin.testPCQueue.sys.controller;

import com.chenjin.testPCQueue.common.web.controller.BaseController;
import com.chenjin.testPCQueue.sys.service.IUserService;
import javax.annotation.Resource;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping({"/sys/logout"})
public class LogoutController extends BaseController
{
    private static Logger logger = LoggerFactory.getLogger(LogoutController.class);

    @Resource(name="userService")
    private IUserService userService;

    @RequestMapping({""})
    public String logout() {
        Subject currentUser = SecurityUtils.getSubject();
        currentUser.logout();
        logger.info("[" + currentUser.getPrincipal() + "] 登出成功！");
        return "redirect:/login.jsp";
    }

    protected void init(WebDataBinder binder)
    {
    }
}