package com.springboot.controller;

import com.springboot.exception.MyException;
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
    @RequestMapping("/hhh")
    public String hhh() throws Exception {
        throw new Exception("发生错误");
    }

    @RequestMapping("/json")
    public String json() throws MyException {
        throw new MyException("发生错误2,用于测试json");
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
