package com.ymy.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.ymy.entity.Resource;



public interface ResourceDao extends PagingAndSortingRepository<Resource, Long>, JpaSpecificationExecutor<Resource> {

	/**
	 * 查询�?有的菜单资源
	 * 
	 * @author yigang
	 * @date 2015�?4�?1�? 上午9:42:11
	 * @return
	 */
	@Query(nativeQuery = true, value = "select * from sysresource")
	List<Resource> findAllResources();

	/**
	 * 查询除指定名称之外的�?有的菜单资源
	 * 
	 * @author yigang
	 * @date 2015�?4�?1�? 上午9:42:31
	 * @param name
	 * @return
	 */
	@Query(nativeQuery = true, value = "select * from sysresource r where r.name !=?1")
	List<Resource> findOtherResources(String name);

	/**
	 * 根据角色Id在资源权限表查找�?有具有权限的资源的Id，然后再在资源表里面查找这些资源的父级资源的列表
	 * 
	 * @author yigang
	 * @date 2015�?4�?1�? 上午9:43:00
	 * @param id
	 * @param roleId
	 * @return
	 */
	@Query(nativeQuery = true, value = "select * from sysresource r where r.pid =?1 and r.id in (select rr.resource_id from resourcerole rr where rr.role_id =?2) order by r.sorts asc")
	List<Resource> findSubResources(Long id, Long roleId);

	/**
	 * 根据特定资源的父级资�?
	 * 
	 * @author yigang
	 * @date 2015�?4�?1�? 上午9:46:46
	 * @param id
	 * @return
	 */
	@Query(nativeQuery = true, value = "select * from sysresource r where r.id = (select rr.pid from sysresource rr where rr.id=?1)")
	Resource findSupResources(Long id);

	/**
	 * 查询�?有的�?级菜单资�?
	 * 
	 * @author yigang
	 * @date 2015�?4�?1�? 上午9:47:14
	 * @return
	 */
	@Query(nativeQuery = true, value = "select * from sysresource r where r.pid is null")
	List<Resource> findAllSupResources();

	/**
	 * 根据资源名称查找资源列表
	 * 
	 * @author yigang
	 * @date 2015�?4�?1�? 上午9:47:46
	 * @param name
	 * @return
	 */
	@Query(nativeQuery = true, value = "select * from sysresource r where r.name = ?1")
	List<Resource> findByName(String name);

	/**
	 * 根据资源名称和资源的Id来查找资源的数量
	 * 
	 * @author yigang
	 * @date 2015�?4�?1�? 上午9:48:06
	 * @param name
	 * @param id
	 * @return
	 */
	@Query(nativeQuery = true, value = "select count(*) from sysresource r where r.name=?1 and r.id!=?2")
	int findByNameAndId(String name, Long id);

	/**
	 * 根据特定的roleId来查找这个Role�?具有权限的资源的Id列表
	 * 
	 * @author yigang
	 * @date 2015�?4�?1�? 上午9:51:05
	 * @param id
	 * @return
	 */
	@Query(nativeQuery = true, value = "select rr.resource_id from resourcerole rr where rr.role_id=?1")
	List<Integer> findByRoleId(Long id);

	/**
	 * 根据特定的资源Id来查找资源角色表里面资源Id为该Id的记�?
	 * 
	 * @author yigang
	 * @date 2015�?4�?1�? 上午9:52:14
	 * @param id
	 * @return
	 */
	@Query(nativeQuery = true, value = "select rr.id from resourcerole rr where rr.resource_id=?1")
	List<Integer> findResourceRoleIdByResourceId(Long id);

	/**
	 * 对给定的�?些资源Id来查找这些资源，并且按照sorts升序排序
	 * 
	 * @author yigang
	 * @date 2015�?4�?1�? 上午9:53:32
	 * @param idList
	 * @return
	 */
	@Query(nativeQuery = true, value = "select r.id from sysresource r where r.id in ?1 order by sorts asc")
	List<Integer> findSortedList(List<Long> idList);

	/**
	 * 查找某个角色不具有权限的�?有资源的列表
	 * 
	 * @author yigang
	 * @date 2015�?4�?1�? 上午9:55:00
	 * @param id
	 * @return
	 */
	@Query(nativeQuery = true, value = "select * from sysresource r where r.id not in (select rr.resource_id from resourcerole rr where rr.role_id =?1) and length(r.url) > 0")
	List<Resource> findOtherResources(Long id);

	/**
	 * 查找�?有的�?级菜单，并按照id升序排序
	 * 
	 * @author yigang
	 * @date 2015�?4�?1�? 上午9:58:49
	 * @return
	 */
	@Query(nativeQuery = true, value = "select * from sysresource r where r.pid is null order by id asc")
	List<Resource> findAllIdSortedSupResources();

	/**
	 * 根据某个�?级菜单的Id，来查找它的�?有的子菜单列表，并且按照sorts升序排序
	 * 
	 * @author yigang
	 * @date 2015�?4�?1�? 上午9:59:35
	 * @param id
	 * @return
	 */
	@Query(nativeQuery = true, value = "select * from sysresource r where r.pid=?1 order by sorts asc")
	List<Resource> findAllSortedSubResourcesByPid(Long id);

	/**
	 * 根据父级查子集ID
	 * @param pid
	 * @return
	 */
	@Query(value = "select id from sysresource where pid=?1", nativeQuery = true)
	List<Integer> getSubidByPid(Long pid);

}
