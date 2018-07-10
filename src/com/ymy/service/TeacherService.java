package com.ymy.service;

import java.util.List;

import com.ymy.entity.Teacher;

public interface TeacherService {
	
	public Teacher save(Teacher teacher);
	
	public List<Teacher> getList();
	
	public Teacher getByAccount(String account);
	
	/**
	 * ���ɼ�������
	 * 
	 * @param teacher
	 */
	public void entryptPassword(Teacher teacher);

	/**
	 * �ȶ�����
	 * 
	 * @param teacher
	 * @param inputPassword
	 * @return
	 */
	public boolean decryptPassword(Teacher teacher, String inputPassword);
}
