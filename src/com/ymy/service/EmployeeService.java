package com.ymy.service;

import java.util.Map;

import org.springframework.data.domain.Page;

import com.ymy.entity.Employee;
import com.ymy.entity.Payment;

public interface EmployeeService {
	public Employee save(Employee employee);
	
	public Employee get(Long id);

	public void delete(Long id);
		
		/**
		 * 所有员工列表
		 * 
		 * @param searchParams
		 * @param pageNumber
		 * @param pageSize
		 * @param sortType
		 * @return
		 */
		public Page<Employee> getList(Map<String, Object> searchParams, int pageNumber, int pageSize, String sortType);
}
