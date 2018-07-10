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

import com.ymy.entity.Resource;
import com.ymy.entity.Role;
import com.ymy.repository.ResourceDao;
import com.ymy.repository.RoleDao;
import com.ymy.service.RoleService;
import com.ymy.utils.Util;


/**
 * 类RoleService.java的实现描述：Role的Service�?
 */
@Component
//类中�?有public函数都纳入事务管理的标识.
@Transactional
public class RoleServiceImpl implements RoleService {
	
	@Autowired
	RoleDao roleDao;
	@Autowired
	ResourceDao resourceDao;
	public Page<Role> getRoles(Map<String, Object> searchParams,
			int pageNumber, int pageSize, String sortType) {
		PageRequest pageRequest = buildPageRequest(pageNumber, pageSize,
				sortType);
		Specification<Role> spec = Util.buildSpecification(searchParams,
				Role.class);
		return roleDao.findAll(spec, pageRequest);
	}

	public Role save(Role role) {
		return roleDao.save(role);
	}

	public Role update(Role role) {
		return roleDao.save(role);
	}

	public void delete(Long id) {
		roleDao.delete(id);
	}

	public Role get(Long id) {
		return roleDao.findOne(id);
	}
	
	public List<Role> findAllRoles() {
		return roleDao.findAllRoles();
	}
	
	public List<Role> findRoles() {
		return roleDao.findRoles();
	}
	
	
	public int findByName(String name) {
		List<Role> roleList = roleDao.findByName(name);
		if(roleList != null) {
			if(roleList.size() == 0) {
				return 1;
			}else{
				return 0;
			}
		}else{
			return 0;
		}
	}
	
	public int findByNameAndId(String name, Long id) {
		return roleDao.findByNameAndId(name, id);
	}

	public PageRequest buildPageRequest(int pageNumber, int pageSize,
			String sortType) {
		Sort sort = null;
		if ("auto".equals(sortType)) {
			sort = new Sort(Direction.ASC, "id");
		} else if ("name".equals(sortType)) {
			sort = new Sort(Direction.ASC, "name");
		} 

		return new PageRequest(pageNumber - 1, pageSize, sort);
	}
	
	
	public String getTree(Long roleId) {
		List<Resource> supResList = resourceDao.findAllIdSortedSupResources();
		List<Integer> hasId = resourceDao.findByRoleId(roleId);
		if(supResList != null && supResList.size() > 0) {
			StringBuilder allStr = new StringBuilder();
			allStr.append("[");
			for(Resource supres : supResList) {
				StringBuilder allSupStr = new StringBuilder();
				allSupStr.append("{");
				allSupStr.append("\"id\""+":"+"\""+supres.getId()+"\"");
				allSupStr.append(",");
				allSupStr.append("\"text\""+":"+"\""+supres.getName()+"\"");
				
				for(Integer in : hasId) {
					if(supres.getId() == (long)in) {
						allSupStr.append(",");
						allSupStr.append("\"state\""+":"+"{\"opened\":true,\"selected\":true}");
						continue;
					}
				}
				
				List<Resource> subResList = resourceDao.findAllSortedSubResourcesByPid(supres.getId());
				if(subResList != null && subResList.size() > 0) {
					allSupStr.append(",");
					StringBuilder allSubStr = new StringBuilder();
					allSubStr.append("[");
					for(Resource subres : subResList) {
						StringBuilder smallSubStr = new StringBuilder();
						smallSubStr.append("{");
						smallSubStr.append("\"id\""+":"+"\""+subres.getId()+"\"");
						smallSubStr.append(",");
						smallSubStr.append("\"text\""+":"+"\""+subres.getName()+"\"");
						smallSubStr.append(",");
						smallSubStr.append("\"icon\""+":"+"\"fa fa-folder icon-state-default\"");
						
						for(Integer in : hasId) {
							if(subres.getId() == (long)in) {
								smallSubStr.append(",");
								smallSubStr.append("\"state\""+":"+"{\"selected\":true}");
								continue;
							}
						}
						
						smallSubStr.append("}");
						smallSubStr.append(",");
						allSubStr.append(smallSubStr);
					}
					String allSubStrToStr = allSubStr.toString();
					int length = allSubStrToStr.length();
					StringBuilder allSubStrTemp = new StringBuilder(allSubStrToStr.substring(0, length-1));
					allSubStrTemp.append("]");
					
					allSupStr.append("\"children\""+":"+allSubStrTemp.toString());
				}
				allSupStr.append("}");
				allSupStr.append(",");
				allStr.append(allSupStr);
			}
			String allStrToStr = allStr.toString();
			int allLength = allStrToStr.length();
			StringBuilder allStrTemp = new StringBuilder(allStrToStr.substring(0,allLength-1));
			allStrTemp.append("]");
			String str = allStrTemp.toString();
			//System.out.println(str);
			return str;
		}
		return null;
	}
	
}
