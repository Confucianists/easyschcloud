package com.ymy.service;

import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;

import com.ymy.entity.Payment;
import com.ymy.entity.Will;

public interface PaymentService {
	
	public Payment save(Payment payment);
	
	public Payment get(Long id);

	public void delete(Long id);
		
		/**
		 * ��ҳ��ѯ
		 * 
		 * @param searchParams
		 * @param pageNumber
		 * @param pageSize
		 * @param sortType
		 * @return
		 */
		public Page<Payment> getList(Map<String, Object> searchParams, int pageNumber, int pageSize, String sortType);
		
		
		public List<Object[]> getSumRecentWeek();
		
		public List<Object[]> getSumRecentMonth();
		
		public List<Object[]> getSumRecentThreeMonth();
		
		public List<Object[]> getSumRecentSixMonth();
		
		public Double getCurrentMonthSum();
}
