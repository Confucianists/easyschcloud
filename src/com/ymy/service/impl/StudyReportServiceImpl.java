package com.ymy.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.ymy.entity.StudyReport;
import com.ymy.repository.StudyReportDao;
import com.ymy.service.StudyReportService;

@Component
@Transactional
public class StudyReportServiceImpl implements StudyReportService{
	
	@Autowired
	StudyReportDao studyReportDao;
	
	@Override
	public StudyReport save(StudyReport studyReport) {
		return studyReportDao.save(studyReport);
	}

	@Override
	public StudyReport get(Long id) {
		return studyReportDao.findOne(id);
	}

	@Override
	public void delete(Long id) {
		studyReportDao.delete(id);
	}

	
	public List<StudyReport> getListByStudentid(Long studentid) {
		return studyReportDao.getListByStudentid(studentid);
	}

}
