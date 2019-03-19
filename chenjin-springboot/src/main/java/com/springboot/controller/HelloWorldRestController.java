package com.springboot.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
//返回json
@RestController
public class HelloWorldRestController {
    @RequestMapping("/")
    public String sayHello() {
        return "Hello,World!";
    }
}
