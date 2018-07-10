package com.ymy.service;

import java.util.List;

import org.springframework.data.domain.Page;

import com.ymy.entity.Course;

public interface CourseService {
	public List<Course> getList();
	
	public List<Course> getListByTeacherid(Long teacherid);
	
	public Long getFirstCourseidByteacherid(Long teacherid);
	
	public List<Course> getListBySchoolid(Long schoolid);
	
	public Page<Object[]> getGroupBySchoolid(Long schoolid,Integer pageNumber,Integer pageSize,String sortType);
}
