package com.springboot.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Controller
@RequestMapping("/hello")
public class HelloWorldController {
    //模板不行 找不到模板
    @RequestMapping("/")
    public String sayHello() {
        return "Hello,World!";
    }

    @RequestMapping("/testHtml")
    public String testHtml(ModelMap map) {
        map.addAttribute("host", "http://www.baidu.com");
        return "html";
    }
    @RequestMapping("/testFreeMarker")
    public String testFreeMarker(ModelMap map) {
        map.addAttribute("host", "http://www.baidu.com");
        return "freeMarker";
    }
    @RequestMapping("/testVelocity")
    public String testVelocity(ModelMap map) {
        map.addAttribute("host", "http://www.baidu.com");
        return "velocity";
    }


}
