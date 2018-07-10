package com.ymy.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.ymy.entity.ResourceRole;
import com.ymy.repository.ResourceRoleDao;
import com.ymy.service.ResourceRoleService;

@Component
@Transactional
public class ResourceRoleServiceImpl implements ResourceRoleService {

	@Autowired
	private ResourceRoleDao resourceRoleDao;

	public ResourceRole save(ResourceRole resourceRole) {
		return resourceRoleDao.save(resourceRole);
	}

	public ResourceRole update(ResourceRole resourceRole) {
		return resourceRoleDao.save(resourceRole);
	}

	public void delete(Long id) {
		resourceRoleDao.delete(id);
	}

	public ResourceRole get(Long id) {
		return resourceRoleDao.findOne(id);
	}

	public void deleteByRoleAndResource(Long roleId, Long resourceId) {
		resourceRoleDao.deleteByRoleAndResource(roleId, resourceId);
	}

	public List<Long> findResourceRoleIdByRoleId(Long id) {
		List<Long> longList = new ArrayList<Long>();
		List<Integer> intList = resourceRoleDao.findResourceRoleIdByRoleId(id);
		if (intList != null && intList.size() > 0) {
			for (Integer intOne : intList) {
				longList.add(intOne.longValue());
			}
			return longList;
		}
		return null;
	}

	public void deleteByRoleId(Long roleId) {
		resourceRoleDao.deleteByRoleId(roleId);
	}

	public void deleteByResourceId(Long rid) {
		resourceRoleDao.deleteByResourceId(rid);
	}

	public List<Integer> getRIDByRole(Long roleid) {
		return resourceRoleDao.getRIDByRole(roleid);
	}
}
