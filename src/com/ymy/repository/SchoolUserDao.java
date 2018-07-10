package com.ymy.repository;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.ymy.entity.Payment;
import com.ymy.entity.SchoolUser;

public interface SchoolUserDao  extends PagingAndSortingRepository<SchoolUser, Long>, JpaSpecificationExecutor<SchoolUser>{

}
