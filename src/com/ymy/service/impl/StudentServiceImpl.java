package com.ymy.service.impl;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springside.modules.persistence.DynamicSpecifications;
import org.springside.modules.persistence.SearchFilter;
import org.springside.modules.persistence.SearchFilter.Operator;

import com.ymy.entity.Student;
import com.ymy.entity.Will;
import com.ymy.repository.CourseDao;
import com.ymy.repository.StudentDao;
import com.ymy.service.CourseService;
import com.ymy.service.StudentService;
import com.ymy.utils.Util;

@Component
@Transactional
public class StudentServiceImpl implements StudentService{
	
	@Autowired
	StudentDao studentDao;
	@Autowired
	CourseService courseService;
	
	@Override
	public Student save(Student student) {
		return studentDao.save(student);
	}

	@Override
	public Student get(Long id) {
		return studentDao.findOne(id);
	}

	@Override
	public void delete(Long id) {
		studentDao.delete(id);
	}

	
	public Page<Student> getListByTeacherAndCourse(Long teacherid,Map<String,Object> searchParams,int pageNumber,int pageSize,String sortType) {
		PageRequest pagerequest=buildPageRequest(pageNumber,pageSize,sortType);
		Specification<Student> spec=buildSpecification(teacherid,searchParams);
		return studentDao.findAll(spec, pagerequest);
	}
	
	
	public Page<Student> getListByReceptorAndCourse(Long receptorid, Map<String, Object> searchParams, int pageNumber,
			int pageSize, String sortType) {
		PageRequest pagerequest=buildPageRequest(pageNumber,pageSize,sortType);
		Specification<Student> spec=buildSpecification2(receptorid,searchParams);
		return studentDao.findAll(spec, pagerequest);
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
	
    /**
     * 老师id，查询条件
     */
    private Specification<Student> buildSpecification(Long teacherid, Map<String, Object> searchParams) {
        Map<String, SearchFilter> filters = SearchFilter.parse(searchParams);
        filters.put("teacherid", new SearchFilter("teacherid", Operator.EQ, teacherid));
//        Long courseid=courseService.getFirstCourseidByteacherid(teacherid);
//        filters.put("course", new SearchFilter("course", Operator.EQ, courseid));
//        Integer teacherdel=0;
//        filters.put("teacherdel", new SearchFilter("teacherdel", Operator.EQ, teacherdel));
        Specification<Student> spec = DynamicSpecifications.bySearchFilter(filters.values(), Student.class);
        return spec;
    }

    /**
     * 学管师id，查询条件
     */
    private Specification<Student> buildSpecification2(Long receptorid, Map<String, Object> searchParams) {
        Map<String, SearchFilter> filters = SearchFilter.parse(searchParams);
        filters.put("receptorid", new SearchFilter("receptorid", Operator.EQ, receptorid));
        Specification<Student> spec = DynamicSpecifications.bySearchFilter(filters.values(), Student.class);
        return spec;
    }
	

	
}
