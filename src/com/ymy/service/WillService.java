package com.ymy.service;

import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;

import com.ymy.entity.Will;

public interface WillService {
public Will save(Will will);
	
public Will get(Long id);

public void delete(Long id);
	
	/**
	 * ��ҳ��ѯ
	 * 
	 * @param searchParams
	 * @param pageNumber
	 * @param pageSize
	 * @param sortType
	 * @return
	 */
	public Page<Will> getList(Map<String, Object> searchParams, int pageNumber, int pageSize, String sortType);
	
	/**
	 * ��ҳ,������ѯ
	 * 
	 * @param id
	 * @param searchParams
	 * @param pageNumber
	 * @param pageSize
	 * @param sortType
	 * @return
	 */
	public Page<Will> getWillOkList(Integer id, Map<String, Object> searchParams, int pageNumber, int pageSize,
            String sortType);
	
	/**
	 * ��ҳ,������ѯ
	 * 
	 * @param id
	 * @param searchParams
	 * @param pageNumber
	 * @param pageSize
	 * @param sortType
	 * @return
	 */
	public Page<Will> getWillIngList(Integer id, Map<String, Object> searchParams, int pageNumber, int pageSize,
            String sortType); 
	
	public List<Object[]> getCountRecentWeek();
	
	public List<Object[]> getCountRecentMonth();
	
	public List<Object[]> getCountRecentThreeMonth();
	
	public List<Object[]> getCountRecentSixMonth();
	
	public int getCurrentMonthCount();
} 
