package com.chenjin.testPCQueue.sys.controller;

import com.chenjin.testPCQueue.common.web.controller.BaseController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping({"/workdesk"})
public class WorkdeskController extends BaseController
{
    @RequestMapping({""})
    public String home()
    {
        return "workdesk/workdesk";
    }

    protected void init(WebDataBinder binder)
    {
    }
}