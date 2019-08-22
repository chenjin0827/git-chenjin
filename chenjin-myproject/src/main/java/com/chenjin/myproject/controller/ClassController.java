package com.chenjin.myproject.controller;

import java.util.List;
import javax.annotation.Resource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import com.chenjin.myproject.entity.Class;
import com.chenjin.myproject.service.IClassService;

@Controller
@RequestMapping("/class")
public class ClassController {

	@Resource
	private IClassService classService;

	/**
	 * 主页
	* @return
	*/
	@RequestMapping("")
	public String home(){
		return "class/list";
}
}
