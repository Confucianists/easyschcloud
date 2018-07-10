package com.ymy.service;

import java.util.List;

import com.ymy.entity.Checkin;

public interface CheckinService {
	public Checkin save(Checkin checkin);
	
	public List<Checkin> getListByStudentid(Long studentid,Long schoolid);
}
