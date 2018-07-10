package com.ymy.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.ymy.entity.ResourceRole;

@Component
@Transactional
public interface ResourceRoleService {

	public ResourceRole save(ResourceRole resourceRole);

	public ResourceRole update(ResourceRole resourceRole);

	public void delete(Long id);

	public ResourceRole get(Long id);

	public void deleteByRoleAndResource(Long roleId, Long resourceId);

	public List<Long> findResourceRoleIdByRoleId(Long id);

	public void deleteByRoleId(Long roleId);

	public void deleteByResourceId(Long rid);

	public List<Integer> getRIDByRole(Long roleid);
}
