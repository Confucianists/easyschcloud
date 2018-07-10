package com.ymy.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.ymy.entity.ScoreReport;
import com.ymy.repository.ScoreReportDao;
import com.ymy.service.ScoreReportService;

@Component
@Transactional
public class ScoreReportServiceImpl implements ScoreReportService{
	@Autowired
	ScoreReportDao scoreReportDao;
	
	@Override
	public ScoreReport save(ScoreReport scoreReport) {
		return scoreReportDao.save(scoreReport);
	}

	@Override
	public ScoreReport get(Long id) {
		return scoreReportDao.findOne(id);
	}

	@Override
	public void delete(Long id) {
		scoreReportDao.delete(id);
	}

	
	public List<ScoreReport> getListByStudentid(Long studentid) {
		return scoreReportDao.getListByStudentid(studentid);
	}
	
}
