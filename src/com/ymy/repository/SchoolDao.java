package com.ymy.repository;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.ymy.entity.Sales;
import com.ymy.entity.School;

public interface SchoolDao  extends PagingAndSortingRepository<School, Long>, JpaSpecificationExecutor<School>{

}
