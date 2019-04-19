package com.chenjin.springshiro.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/test/")
public class TestController {
    @RequestMapping(value = "index")
    public String chart(ModelMap model) {
        return "index";
    }


}
