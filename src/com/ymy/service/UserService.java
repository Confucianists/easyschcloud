package com.ymy.service;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springside.modules.security.utils.Digests;
import org.springside.modules.utils.Encodes;

import com.ymy.entity.User;
import com.ymy.repository.UserDao;
import com.ymy.utils.Util;

public interface UserService {

	public Page<User> getUser(Map<String, Object> searchParams, int pageNumber, int pageSize, String sortType);

	public User saveorupdate(User user);

	public User saveCaptcha(User user);

	/**
	 * 判断该用户名是否已存�?
	 * 
	 * @param loginName
	 * @return
	 */

	public User findByName(String name);

	/**
	 * 根据ID�? 登录名查找用�? ，用户编辑时判断登陆名是否重�?
	 * 
	 * @param loginName
	 * @param id
	 * @return
	 */
	public int findByNameAndId(String name, Long id);

	public void delete(Long id);

	/**
	 * 分页排序
	 * 
	 * @param pageNumber
	 * @param pageSize
	 * @param sortType
	 * @return
	 */
	public PageRequest buildPageRequest(int pageNumber, int pageSize, String sortType);

	public User get(Long id);

	public void update(User r);

	public List<User> findByRole(Long id);

	public void entryptPassword(User user);

	public String entryptPasswordStr(String salt, String password);

	public boolean decryptPassword(User user, String inputPassword);

	public List<User> findUseByRole(Long id);

	/**
	 * 假删�?
	 * 
	 * @param id
	 */
	public void setDel(Long id);

	/**
	 * 根据角色查询用户数量
	 * @param roleid
	 * @return
	 */
	public int getCountByRole(Long roleid);
	
	
	/**
	 * 用户自己修改密码
	 * @param user
	 * @param inputpassword
	 * @param newpassword
	 * @return
	 */
	public boolean editpassword(User user,String inputpassword,String newpassword);
	
	
	public User doUserLogin(User user);
}
