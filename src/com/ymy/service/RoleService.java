package com.ymy.service;

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

import com.ymy.entity.Role;


@Component
//类中�?有public函数都纳入事务管理的标识.
@Transactional
public interface RoleService {
	
	
	public Page<Role> getRoles(Map<String, Object> searchParams,
			int pageNumber, int pageSize, String sortType) ;

	public Role save(Role role);

	public Role update(Role role);

	public void delete(Long id);

	public Role get(Long id);
	
	public List<Role> findAllRoles();
	
	public List<Role> findRoles() ;
	
	public int findByName(String name) ;
	
	public int findByNameAndId(String name, Long id);

	public PageRequest buildPageRequest(int pageNumber, int pageSize,
			String sortType);
	
	
	public String getTree(Long roleId);
	
}
