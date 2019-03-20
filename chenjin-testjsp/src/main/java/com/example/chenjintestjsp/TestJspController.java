package com.example.chenjintestjsp;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class TestJspController {
    @RequestMapping("/index")
    public String index() {
        return "index";
    }
}
