package com.ymy.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springside.modules.persistence.DynamicSpecifications;
import org.springside.modules.persistence.SearchFilter;
import org.springside.modules.persistence.SearchFilter.Operator;

import com.ymy.entity.Payment;
import com.ymy.repository.PaymentDao;
import com.ymy.service.PaymentService;
import com.ymy.utils.Util;

@Component
@Transactional
public class PaymentServiceImpl implements PaymentService{
	
	@Autowired
	PaymentDao paymentDao;
	
	
	@Override
	public Payment save(Payment payment) {
		return paymentDao.save(payment);
	}
	
	@Override
	public Payment get(Long id) {
		return paymentDao.findOne(id);
	}

	@Override
	public void delete(Long id) {
		paymentDao.delete(id);
	}

	@Override
	public Page<Payment> getList(Map<String, Object> searchParams, int pageNumber, int pageSize, String sortType) {
		PageRequest pageRequest = buildPageRequest(pageNumber, pageSize, sortType);
		Specification<Payment> spec = Util.buildSpecification(searchParams, Payment.class);
		return paymentDao.findAll(spec, pageRequest);
	}
	
	
	/**
     * ������ҳ����.
     */
	private PageRequest buildPageRequest(int pageNumber, int pageSize, String sortType) {
		Sort sort = null;
		if ("auto".equals(sortType)) {
			sort = new Sort(Direction.DESC, "id");
		}
		return new PageRequest(pageNumber - 1, pageSize, sort);
	}
	
 
    

	@Override
	public List<Object[]> getSumRecentWeek() {
		return paymentDao.getRecentWeek();
	}

	@Override
	public List<Object[]> getSumRecentMonth() {
		return paymentDao.getRecentMonth();
	}

	@Override
	public List<Object[]> getSumRecentThreeMonth() {
		return paymentDao.getRecentThreeMonth();
	}

	@Override
	public List<Object[]> getSumRecentSixMonth() {
		return paymentDao.getRecentSixMonth();
	}

	@Override
	public Double getCurrentMonthSum() {
		return paymentDao.getCurrentMonth();
	}

	
    
}
