package com.ymy.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.ymy.entity.Role;


public interface RoleDao extends PagingAndSortingRepository<Role, Long>,
	JpaSpecificationExecutor<Role>{
	
	@Query(nativeQuery = true, value = "select * from role where id not in (2,3)")
	List<Role> findAllRoles();
	
	@Query(nativeQuery = true, value = "select * from role")
	List<Role> findRoles();
	
	@Query(nativeQuery = true, value = "select * from role r where r.name = ?1")
	List<Role> findByName(String name);

	@Query(nativeQuery = true, value = "select count(*) from role r where r.name=?1 and r.id!=?2")
	int findByNameAndId(String name, Long id);
	
}
