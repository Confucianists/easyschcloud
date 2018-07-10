package com.ymy.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.ymy.entity.Teacher;

public interface TeacherDao extends PagingAndSortingRepository<Teacher, Long>,JpaSpecificationExecutor<Teacher>{
	
	@Query(value="select * from teacher order by id desc",nativeQuery=true)
	public List<Teacher> getList();
	
	@Query(value="select * from teacher where phone=?1 order by id desc",nativeQuery=true)
	public Teacher getByAccount(String phone);
}
