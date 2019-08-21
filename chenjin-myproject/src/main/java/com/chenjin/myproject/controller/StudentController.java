package com.chenjin.myproject.controller;

import java.util.List;
import javax.annotation.Resource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import com.chenjin.myproject.entity.Student;
import com.chenjin.myproject.service.IStudentService;

@Controller
@RequestMapping("/user//student")
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
