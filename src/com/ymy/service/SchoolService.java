package com.ymy.service;

import com.ymy.entity.Payment;
import com.ymy.entity.School;

public interface SchoolService {
	
	public School save(School s);
	
	public School get(Long id);

	public void delete(Long id);
}
