package com.chenjin.myproject.controller;

import com.chenjin.myproject.service.IStudentService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;

@Controller
@RequestMapping("/student")
public class StudentController {

	@Resource
	private IStudentService studentService;

	/**
	 * 主页
	* @return
	*/
	@RequestMapping("")
	public String home(){
		return "student/list";
}
}
