package com.ymy.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;


import com.ymy.entity.StudyReport;

public interface StudyReportDao  extends PagingAndSortingRepository<StudyReport, Long>, JpaSpecificationExecutor<StudyReport> {
	@Query(value="SELECT sr.* FROM studyreport  sr WHERE sr.studentid=?1",nativeQuery=true)
	public List<StudyReport> getListByStudentid(Long studentid);
}
