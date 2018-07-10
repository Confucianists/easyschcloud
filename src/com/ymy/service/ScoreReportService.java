package com.ymy.service;

import java.util.List;

import com.ymy.entity.Payment;
import com.ymy.entity.ScoreReport;

public interface ScoreReportService {
	public ScoreReport save(ScoreReport scoreReport);
	
	public ScoreReport get(Long id);

	public void delete(Long id);
	
	public List<ScoreReport> getListByStudentid(Long studentid);
}
