package com.ymy.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.ymy.entity.Course;
import com.ymy.repository.CourseDao;
import com.ymy.service.CourseService;

@Component
@Transactional
public class CourseServiceImpl implements CourseService{
	
	@Autowired
	CourseDao courseDao;
	
	@Override
	public List<Course> getList() {
	
		return (List<Course>)courseDao.findAll();
	}

	public List<Course> getListByTeacherid(Long teacherid) {
		return courseDao.getListByTeacherid(teacherid);
	}

	public Long getFirstCourseidByteacherid(Long teacherid) {
		return courseDao.getFirstCourseidByteacherid(teacherid);
	}

	public List<Course> getListBySchoolid(Long schoolid) {
		return courseDao.getListBySchoolid(schoolid);
	}

	public Page<Object[]> getGroupBySchoolid(Long schoolid, Integer pageNumber, Integer pageSize, String sortType) {
		PageRequest pageRequest=buildPageRequest(pageNumber, pageSize,sortType);
		Integer count=courseDao.getCourseCount(schoolid);
		return new PageImpl<Object[]>(courseDao.getGroupBySchoolid(schoolid,(pageNumber-1)*pageSize,pageSize),pageRequest,count);
	}

	/**
     * 分页
     */
	private PageRequest buildPageRequest(int pageNumber, int pageSize, String sortType) {
		Sort sort = null;
		if ("auto".equals(sortType)) {
			sort = new Sort(Direction.DESC, "id");
		}
		return new PageRequest(pageNumber - 1, pageSize, sort);
	}

}
