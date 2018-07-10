package com.ymy.service;

import java.util.List;

import com.ymy.entity.Teacher;

public interface TeacherService {
	
	public Teacher save(Teacher teacher);
	
	public List<Teacher> getList();
	
	public Teacher getByAccount(String account);
	
	/**
	 * 生成加密密码
	 * 
	 * @param teacher
	 */
	public void entryptPassword(Teacher teacher);

	/**
	 * 比对密码
	 * 
	 * @param teacher
	 * @param inputPassword
	 * @return
	 */
	public boolean decryptPassword(Teacher teacher, String inputPassword);
}
