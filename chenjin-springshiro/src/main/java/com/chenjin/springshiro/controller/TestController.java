package com.chenjin.springshiro.controller;

import com.chenjin.springshiro.service.ITestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/test/")
public class TestController {
    @Autowired
    private ITestService testService;

    @RequestMapping(value = "index", method = RequestMethod.GET)
    public String index(ModelMap model) {
        return "index";
    }

    @RequestMapping(value = "index2", method = RequestMethod.POST)
    public void index2(String s1, String s2, String s3) {
        testService.test(s1, s2, s3);
    }


}
