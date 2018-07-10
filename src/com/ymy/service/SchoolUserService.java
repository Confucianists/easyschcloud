package com.ymy.service;

import com.ymy.entity.SchoolUser;

public interface SchoolUserService {
	
	public SchoolUser save(SchoolUser su);
	
	public SchoolUser get(Long id);

	public void delete(Long id);
	
	/***
	 * 检查导入数据，是否注册，判断状态
	 * 
	 * @param phone
	 * @param name
	 * @param subs
	 * @param schoolid
	 */
	public void checkST(String name, String realname,String rolename, String subjname, Long schoolid);
}
