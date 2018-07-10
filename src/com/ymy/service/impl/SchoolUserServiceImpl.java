package com.ymy.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.ymy.entity.SchoolUser;
import com.ymy.repository.SchoolUserDao;
import com.ymy.service.SchoolUserService;

@Component
@Transactional
public class SchoolUserServiceImpl implements SchoolUserService{
	
	@Autowired
	SchoolUserDao schoolUserDao;
	
	@Override
	public SchoolUser save(SchoolUser su) {
		return schoolUserDao.save(su);
	}

	@Override
	public SchoolUser get(Long id) {
		return schoolUserDao.findOne(id);
	}

	@Override
	public void delete(Long id) {
		schoolUserDao.delete(id);
	}

	public void checkST(String name, String realname, String rolename, String subjname, Long schoolid) {
		
	}

}
