package com.ymy.repository;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.ymy.entity.Employee;

public interface EmployeeDao extends PagingAndSortingRepository<Employee, Long>, JpaSpecificationExecutor<Employee> {

}
