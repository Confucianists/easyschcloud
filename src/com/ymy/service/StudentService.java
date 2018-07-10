package com.ymy.service;

import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.ymy.entity.Student;

public interface StudentService {
	public Student save(Student student);
	
	public Student get(Long id);
	
	public void delete(Long id);
	
	public Page<Student> getListByTeacherAndCourse(Long teacherid,Map<String,Object> searchParams,int pageNumber,int pageSize,String sortType);
	
	public Page<Student> getListByReceptorAndCourse(Long receptorid,Map<String,Object> searchParams,int pageNumber,int pageSize,String sortType);
}
