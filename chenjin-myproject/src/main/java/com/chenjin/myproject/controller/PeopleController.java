package com.chenjin.myproject.controller;

import java.util.List;
import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import com.chenjin.myproject.entity.People;
import com.chenjin.myproject.service.IPeopleService;

@Controller
@RequestMapping("/people")
public class PeopleController {

    @Resource
    private IPeopleService peopleService;

    /**
     * 主页
     *
     * @return
     */
    @RequestMapping("")
    public String home() {
        return "people/list";
    }


    @RequestMapping("/save")
    public String save() {
        People people = new People();
        people.setName("chenjin");
        people.setSex("男");
        peopleService.save(people);
        return "test";
    }
}
