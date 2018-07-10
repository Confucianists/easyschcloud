package com.ymy.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.ymy.entity.Course;
import com.ymy.entity.Payment;

public interface CourseDao extends PagingAndSortingRepository<Course, Long>, JpaSpecificationExecutor<Course>{
	//根据老师id查询科目
	@Query(value="SELECT c.* FROM course c WHERE c.`id` IN (SELECT ctr.`courseid` FROM courseteacherrelation ctr WHERE ctr.`teacherid`=?1)",nativeQuery=true)
	public List<Course> getListByTeacherid(Long teacherid);
	//根据老师id查询第一门科目
	@Query(value="SELECT ctr.`courseid` FROM courseteacherrelation ctr WHERE ctr.`teacherid`=?1 LIMIT 0,1",nativeQuery=true)
	public Long getFirstCourseidByteacherid(Long teacherid);
	//根据学校id查询科目
	@Query(value="SELECT * FROM course WHERE schoolid=?1",nativeQuery=true)
	public List<Course> getListBySchoolid(Long schoolid);
	
	//根据学校id查询各科目报名人数
	@Query(value="SELECT tmp.cid,tmp.cnt,c.name FROM (SELECT s.course_id AS cid,COUNT(*) AS cnt FROM student  s WHERE schoolid=?1 GROUP BY s.`course_id`) tmp LEFT JOIN course c ON c.id=tmp.cid limit ?2,?3",nativeQuery=true)
	public List<Object[]> getGroupBySchoolid(Long schoolid,Integer pageNum,Integer pageSi);
	
	//根据学校id查询科目数
	@Query(value="SELECT COUNT(*) FROM (SELECT s.course_id,COUNT(*) FROM student  s WHERE schoolid=?1 GROUP BY s.`course_id`) t WHERE 1=1",nativeQuery=true)
	public Integer getCourseCount(Long schoolid);
}
