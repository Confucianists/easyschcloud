package com.ymy.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.ymy.entity.School;
import com.ymy.repository.SchoolDao;
import com.ymy.service.SchoolService;

@Component
@Transactional
public class SchoolServiceImpl implements SchoolService{
	
	@Autowired
	SchoolDao schoolDao;
	
	@Override
	public School save(School s) {
		return schoolDao.save(s);
	}

	@Override
	public School get(Long id) {
		return schoolDao.findOne(id);
	}

	@Override
	public void delete(Long id) {
		schoolDao.delete(id);
	}
	
}
