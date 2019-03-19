package com.chenjin.testPCQueue.sys.service.realm;

import java.io.PrintStream;
import javax.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping({"/sys/redirect"})
public class RedirectControlle
{
    @RequestMapping({""})
    public String home(String url, HttpSession session)
    {
        if (url == null) {
            return "redirect:/";
        }
        if (url.indexOf("?") > -1) {
            System.out.println("重定向===redirect:" + url + "&sessionId=" + session.getId());
            return "redirect:" + url + "&sessionId=" + session.getId();
        }
        System.out.println("重定向===redirect:" + url + "?sessionId=" + session.getId());
        return "redirect:" + url + "?sessionId=" + session.getId();
    }
}