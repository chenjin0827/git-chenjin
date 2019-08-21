package com.chenjin.myproject.service.impl;

import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.chenjin.myproject.dao.IClassDao;
import com.chenjin.myproject.entity.Class;
import com.chenjin.myproject.service.IClassService;

@Service
@Transactional(readOnly=true)
public class ClassService  implements IClassService{
	private IClassDao classDao;

	public IClassDao getClassDao() {
		return classDao;
	}

	@Resource
	public void setClassDao(IClassDao classDao) {
		this.classDao = classDao;
	}
}
