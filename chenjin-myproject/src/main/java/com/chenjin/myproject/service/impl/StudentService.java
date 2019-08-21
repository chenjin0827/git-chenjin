package com.chenjin.myproject.service.impl;

import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.chenjin.myproject.dao.IStudentDao;
import com.chenjin.myproject.entity.Student;
import com.chenjin.myproject.service.IStudentService;

@Service
@Transactional(readOnly=true)
public class StudentService  implements IStudentService{
	private IStudentDao studentDao;

	public IStudentDao getStudentDao() {
		return studentDao;
	}

	@Resource
	public void setStudentDao(IStudentDao studentDao) {
		this.studentDao = studentDao;
	}
}
