package com.chenjin.myproject.controller;

import com.chenjin.myproject.entity.People;
import com.chenjin.myproject.service.IPeopleService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;

@Controller
public class PeopleController {

    @RequestMapping("/people")
    public String index(){
        return "people/index";
    }

    @Resource(name="peopleService")
    private IPeopleService peopleService;

    @RequestMapping("/save")
    public String save(){
        People people = new People();
        people.setName("chenjin");
        people.setSex("男");
        peopleService.save(people);
        return "test";
    }
}