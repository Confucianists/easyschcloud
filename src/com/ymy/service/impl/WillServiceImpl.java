package com.ymy.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springside.modules.persistence.DynamicSpecifications;
import org.springside.modules.persistence.SearchFilter;
import org.springside.modules.persistence.SearchFilter.Operator;

import com.ymy.entity.Will;
import com.ymy.repository.WillDao;
import com.ymy.service.WillService;
import com.ymy.utils.Util;

@Component
@Transactional
public class WillServiceImpl implements WillService{
	
	@Autowired
	WillDao willDao; 

	@Override
	public Will save(Will will) {
		return willDao.save(will);
	}
	
	@Override
	public Will get(Long id) {
		return willDao.findOne(id);
	}

	@Override
	public void delete(Long id) {
		willDao.delete(id);
	}

	@Override
	public Page<Will> getList(Map<String, Object> searchParams, int pageNumber, int pageSize, String sortType) {
		PageRequest pageRequest = buildPageRequest(pageNumber, pageSize, sortType);
		Specification<Will> spec = Util.buildSpecification(searchParams, Will.class);
		return willDao.findAll(spec, pageRequest);
	}
	
	
	public Page<Will> getWillOkList(Integer id, Map<String, Object> searchParams, int pageNumber, int pageSize,
            String sortType) {
        PageRequest pageRequest = buildPageRequest(pageNumber, pageSize, sortType);
        Specification<Will> spec = buildSpecification(id, searchParams);
 
        return willDao.findAll(spec, pageRequest);
    }
	
	public Page<Will> getWillIngList(Integer id, Map<String, Object> searchParams, int pageNumber, int pageSize,
            String sortType) {
        PageRequest pageRequest = buildPageRequest(pageNumber, pageSize, sortType);
        Specification<Will> spec = buildSpecification(id, searchParams);
 
        return willDao.findAll(spec, pageRequest);
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
     * 查询条件
     */
    private Specification<Will> buildSpecification(Integer status, Map<String, Object> searchParams) {
        Map<String, SearchFilter> filters = SearchFilter.parse(searchParams);
        filters.put("status", new SearchFilter("status", Operator.EQ, status));
        Specification<Will> spec = DynamicSpecifications.bySearchFilter(filters.values(), Will.class);
        return spec;
    }

	@Override
	public List<Object[]> getCountRecentWeek() {
		return willDao.getRecentWeek();
	}

	@Override
	public List<Object[]> getCountRecentMonth() {
		return willDao.getRecentMonth();
	}

	@Override
	public List<Object[]> getCountRecentThreeMonth() {
		return willDao.getRecentThreeMonth();
	}

	@Override
	public List<Object[]> getCountRecentSixMonth() {
		return willDao.getRecentSixMonth();
	}

	@Override
	public int getCurrentMonthCount() {
		return willDao.getCurrentMonth();
	}
    
    
}
