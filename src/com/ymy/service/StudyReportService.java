package com.ymy.service;

import java.util.List;

import com.ymy.entity.ScoreReport;
import com.ymy.entity.StudyReport;

public interface StudyReportService {
	public StudyReport save(StudyReport studyReport);
	
	public StudyReport get(Long id);

	public void delete(Long id);
	
	public List<StudyReport> getListByStudentid(Long studentid);
}
