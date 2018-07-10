package com.ymy.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.ymy.entity.Checkin;
import com.ymy.repository.CheckinDao;
import com.ymy.service.CheckinService;

@Component
@Transactional
public class CheckinServiceImpl implements CheckinService{
	
	@Autowired
	CheckinDao checkinDao;
	
	@Override
	public Checkin save(Checkin checkin) {
		return checkinDao.save(checkin);
	}

	@Override
	public List<Checkin> getListByStudentid(Long studentid,Long schoolid) {
		return checkinDao.getListByStudentid(studentid, schoolid);
	}

}
