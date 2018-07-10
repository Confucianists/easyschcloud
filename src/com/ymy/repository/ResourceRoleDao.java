package com.ymy.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.ymy.entity.ResourceRole;

public interface ResourceRoleDao
		extends PagingAndSortingRepository<ResourceRole, Long>, JpaSpecificationExecutor<ResourceRole> {

	@Modifying
	@Query(nativeQuery = true, value = "delete from resourcerole where role_id=?1 and resource_id=?2")
	void deleteByRoleAndResource(Long roleId, Long resourceId);

	@Query(nativeQuery = true, value = "select rr.id from resourcerole rr where rr.role_id=?1")
	List<Integer> findResourceRoleIdByRoleId(Long id);

	@Modifying
	@Query(nativeQuery = true, value = "delete from resourcerole where role_id=?1")
	void deleteByRoleId(Long roleId);

	@Modifying
	@Query(nativeQuery = true, value = "delete from resourcerole where resource_id=?1")
	void deleteByResourceId(Long rid);

	@Query(value = "select resource_id from resourcerole WHERE role_id=?1", nativeQuery = true)
	List<Integer> getRIDByRole(Long roleid);

}
