package com.ymy.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.ymy.entity.Course;
import com.ymy.entity.ScoreReport;

public interface ScoreReportDao extends PagingAndSortingRepository<ScoreReport, Long>, JpaSpecificationExecutor<ScoreReport>{
	
	@Query(value="SELECT sr.* FROM scorereport  sr WHERE sr.studentid=?1",nativeQuery=true)
	public List<ScoreReport> getListByStudentid(Long studentid);
}
