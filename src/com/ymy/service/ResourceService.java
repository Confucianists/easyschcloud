package com.ymy.service;

import java.util.ArrayList;
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

import com.ymy.entity.Resource;



public interface ResourceService {

	public Page<Resource> getResources(Map<String, Object> searchParams, int pageNumber, int pageSize, String sortType);

	public Resource save(Resource resource);

	public Resource update(Resource resource);

	public void delete(Long id);

	public Resource get(Long id);

	public List<Resource> findAllResources();

	public List<Resource> findOtherResources(String name);

	public List<Resource> findSubResources(Long id, Long roleId);

	public Resource findSupResources(Long id);

	public List<Resource> findAllSupResources();

	public Resource findByName(String name);

	public int findByNameAndId(String name, Long id);

	public List<Resource> findByRoleId(Long id);

	public List<Long> findResourceRoleIdByResourceId(Long id);

	public List<Resource> findOtherResources(Long id);

	public List<Long> getSortedList(List<Long> idList);

	public List<Resource> findAllSortedSubResourcesByPid(Long id);

	public PageRequest buildPageRequest(int pageNumber, int pageSize, String sortType);

	public String getTree();

	public List<Resource> getAllResource(Long roleid);

	/**
	 * 删除资源
	 * 
	 * @param srid
	 */
	public void deleteSubResouece(Long srid);
}