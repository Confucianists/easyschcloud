package com.ymy.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.ymy.entity.Teacher;
import com.ymy.entity.User;

public interface UserDao extends PagingAndSortingRepository<User, Long>, JpaSpecificationExecutor<User> {

	@Query(nativeQuery = true, value = "select * from user u where u.name = ?1")
	List<User> findByName(String name);

	@Query(nativeQuery = true, value = "select count(*) from user u where u.name=?1 and u.id!=?2")
	int findByNameAndId(String name, Long id);

	@Query(nativeQuery = true, value = "select * from user u where role_id=?1")
	List<User> findByRole(Long id);

	@Query(nativeQuery = true, value = "select * from user u where role_id=?1 and state=1 order by registertime desc")
	List<User> findUseByRole(Long id);

	@Query(value = "update user set isdel=1 where id=?1", nativeQuery = true)
	@Modifying
	void setdel(Long id);

	@Query(value = "select count(*) from user where role_id=?1", nativeQuery = true)
	public int getCountByRole(Long roleid);
	
	@Query(value="select * from user where name=?1 order by id desc",nativeQuery=true)
	public User getByAccount(String name);
}