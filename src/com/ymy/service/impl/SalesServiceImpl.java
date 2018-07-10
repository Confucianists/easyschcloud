package com.ymy.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.ymy.entity.Sales;
import com.ymy.repository.SalesDao;
import com.ymy.service.SalesService;

@Component
@Transactional
public class SalesServiceImpl implements SalesService{
	
	@Autowired
	SalesDao salesDao;
	
	@Override
	public List<Sales> getList() {
		
		return (List<Sales>)salesDao.findAll();
	}

}
