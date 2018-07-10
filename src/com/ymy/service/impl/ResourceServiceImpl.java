package com.ymy.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
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


import com.ymy.utils.Util;
import com.ymy.entity.Resource;
import com.ymy.repository.ResourceDao;
import com.ymy.service.ResourceRoleService;
import com.ymy.service.ResourceService;

@Component
@Transactional
public class ResourceServiceImpl implements ResourceService {

	@Autowired
	private ResourceDao resourceDao;
	@Autowired
	ResourceRoleService resourceRoleService;

	public Page<Resource> getResources(Map<String, Object> searchParams, int pageNumber, int pageSize,
			String sortType) {
		PageRequest pageRequest = buildPageRequest(pageNumber, pageSize, sortType);
		Specification<Resource> spec = Util.buildSpecification(searchParams, Resource.class);
		return resourceDao.findAll(spec, pageRequest);
	}

	public Resource save(Resource resource) {
		return resourceDao.save(resource);
	}

	public Resource update(Resource resource) {
		return resourceDao.save(resource);
	}

	public void delete(Long id) {
		resourceDao.delete(id);
	}

	public Resource get(Long id) {
		return resourceDao.findOne(id);
	}

	public List<Resource> findAllResources() {
		return resourceDao.findAllResources();
	}

	public List<Resource> findOtherResources(String name) {
		return resourceDao.findOtherResources(name);
	}

	public List<Resource> findSubResources(Long id, Long roleId) {
		return resourceDao.findSubResources(id, roleId);
	}

	public Resource findSupResources(Long id) {
		return resourceDao.findSupResources(id);
	}

	public List<Resource> findAllSupResources() {
		return resourceDao.findAllSupResources();
	}

	public Resource findByName(String name) {
		List<Resource> byNameList = resourceDao.findByName(name);
		if (byNameList != null && byNameList.size() > 0) {
			return byNameList.get(0);
		}
		return null;
	}

	public int findByNameAndId(String name, Long id) {
		return resourceDao.findByNameAndId(name, id);
	}

	public List<Resource> findByRoleId(Long id) {
		List<Resource> rrList = new ArrayList<Resource>();
		List<Integer> rrIdList = resourceDao.findByRoleId(id);
		if (rrIdList != null && rrIdList.size() > 0) {
			for (Integer rid : rrIdList) {
				Resource res = resourceDao.findOne(rid.longValue());
				rrList.add(res);
			}
			return rrList;
		}
		return null;
	}

	public List<Long> findResourceRoleIdByResourceId(Long id) {
		List<Long> longId = new ArrayList<Long>();
		List<Integer> intId = resourceDao.findResourceRoleIdByResourceId(id);
		if (intId != null) {
			for (Integer oneId : intId) {
				longId.add(oneId.longValue());
			}
			return longId;
		}
		return null;
	}

	public List<Resource> findOtherResources(Long id) {
		List<Resource> rList = resourceDao.findOtherResources(id);
		if (rList != null && rList.size() > 0) {
			return rList;
		}
		return null;
	}

	public List<Long> getSortedList(List<Long> idList) {
		List<Long> longList = new ArrayList<Long>();
		List<Integer> intList = resourceDao.findSortedList(idList);
		if (intList != null && intList.size() > 0) {
			for (Integer intL : intList) {
				longList.add(intL.longValue());
			}
		}
		return longList;
	}

	public List<Resource> findAllSortedSubResourcesByPid(Long id) {
		return resourceDao.findAllSortedSubResourcesByPid(id);
	}

	public PageRequest buildPageRequest(int pageNumber, int pageSize, String sortType) {
		Sort sort = null;
		if ("auto".equals(sortType)) {
			sort = new Sort(Direction.ASC, "createtime");
		} else if ("name".equals(sortType)) {
			sort = new Sort(Direction.DESC, "createtime");
		}

		return new PageRequest(pageNumber - 1, pageSize, sort);
	}

	public String getTree() {
		List<Resource> supResList = resourceDao.findAllIdSortedSupResources();
		if (supResList != null && supResList.size() > 0) {
			StringBuilder allStr = new StringBuilder();
			allStr.append("[");
			for (Resource supres : supResList) {
				StringBuilder allSupStr = new StringBuilder();
				allSupStr.append("{");
				allSupStr.append("\"id\"" + ":" + "\"" + supres.getId() + "\"");
				allSupStr.append(",");
				allSupStr.append("\"text\"" + ":" + "\"" + supres.getName() + "\"");

				allSupStr.append(",");
				allSupStr.append("\"state\"" + ":" + "{\"opened\":true}");

				List<Resource> subResList = resourceDao.findAllSortedSubResourcesByPid(supres.getId());
				if (subResList != null && subResList.size() > 0) {
					allSupStr.append(",");
					StringBuilder allSubStr = new StringBuilder();
					allSubStr.append("[");
					for (Resource subres : subResList) {
						StringBuilder smallSubStr = new StringBuilder();
						smallSubStr.append("{");
						smallSubStr.append("\"id\"" + ":" + "\"" + subres.getId() + "\"");
						smallSubStr.append(",");
						smallSubStr.append("\"text\"" + ":" + "\"" + subres.getName() + "\"");
						smallSubStr.append(",");
						smallSubStr.append("\"icon\"" + ":" + "\"fa fa-folder icon-state-default\"");
						smallSubStr.append("}");
						smallSubStr.append(",");
						allSubStr.append(smallSubStr);
					}
					String allSubStrToStr = allSubStr.toString();
					int length = allSubStrToStr.length();
					StringBuilder allSubStrTemp = new StringBuilder(allSubStrToStr.substring(0, length - 1));
					allSubStrTemp.append("]");

					allSupStr.append("\"children\"" + ":" + allSubStrTemp.toString());
				}
				allSupStr.append("}");
				allSupStr.append(",");
				allStr.append(allSupStr);
			}
			String allStrToStr = allStr.toString();
			int allLength = allStrToStr.length();
			StringBuilder allStrTemp = new StringBuilder(allStrToStr.substring(0, allLength - 1));
			allStrTemp.append("]");
			String str = allStrTemp.toString();
			// System.out.println(str);
			return str;
		}
		return null;
	}

	public List<Resource> getAllResource(Long roleid) {
		List<Resource> lr = (List<Resource>) resourceDao.findAll();
		if (roleid != null) {
			List<Integer> ll = resourceRoleService.getRIDByRole(roleid);
			for (Resource r : lr) {
				for (Integer l : ll) {
					if (r.getId().toString().equals(l.toString())) {
						Map<String, Object> state = new HashMap<String, Object>();
						state.put("selected", true);
						r.setState(state);
						break;
					}
				}
			}
		}
		sortlist(toTree(lr));
		return lr;
	}

	public void sortlist(List<Resource> lr) {
		Collections.sort(lr);
		for (Resource r : lr) {
			if (r.getResources() != null) {
				sortlist(r.getResources());
			}
		}
	}

	/**
	 * 转树状结�?
	 * 
	 * @param lr
	 * @return
	 */
	public List<Resource> toTree(List<Resource> lr) {
		List<Resource> removel = new ArrayList<Resource>();
		for (Resource r : lr) {
			// 如果没父节点
			if (r.getResource() == null) {
				continue;
			}
			for (Resource rr : lr) {
				// 找到对应父节�?
				if (rr.getId().equals(r.getResource().getId())) {
					if (rr.getResources() == null) {
						rr.setResources(new ArrayList<Resource>());
					}
					rr.getResources().add(r);
					removel.add(r);
					// lr.remove(r);
					break;
				}
			}
		}
		for (Resource r : removel) {
			lr.remove(r);
		}
		if (check(lr)) {
			return lr;
		} else {
			return toTree(lr);
		}
	}

	/**
	 * �?查是否所有没父节�?
	 * 
	 * @param lr
	 * @return
	 */
	public boolean check(List<Resource> lr) {
		for (Resource r : lr) {
			if (r.getResource() != null) {
				return false;
			}
		}
		return true;
	}

	public void deleteSubResouece(Long srid) {
		List<Integer> sids = resourceDao.getSubidByPid(srid);
		for (Integer id : sids) {
			deleteSubResouece(Long.parseLong(id.toString()));
		}
		resourceRoleService.deleteByResourceId(srid);
		resourceDao.delete(srid);
	}
}