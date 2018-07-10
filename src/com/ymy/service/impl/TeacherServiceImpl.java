package com.ymy.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springside.modules.security.utils.Digests;
import org.springside.modules.utils.Encodes;

import com.ymy.entity.Teacher;
import com.ymy.repository.TeacherDao;
import com.ymy.service.TeacherService;

@Component
@Transactional
public class TeacherServiceImpl implements TeacherService{
	
	public static final int HASH_INTERATIONS = 1024;
	public static final String HASH_ALGORITHM = "SHA-1";
	private static final int SALT_SIZE = 8;
	
	@Autowired
	TeacherDao teacherDao;
	
	@Override
	public Teacher save(Teacher teacher) {
		entryptPassword(teacher);
		return teacherDao.save(teacher);
	}

	
	@Override
	public List<Teacher> getList() {
		
		return teacherDao.getList();
	}

	@Override
	public Teacher getByAccount(String account) {
		
		return teacherDao.getByAccount(account);
	}

	public void entryptPassword(Teacher teacher) {
		byte[] salt = Digests.generateSalt(SALT_SIZE);
		teacher.setSalt(Encodes.encodeHex(salt));
		byte[] hashPassword = Digests.sha1(teacher.getPassword().getBytes(), salt, HASH_INTERATIONS);
		teacher.setPassword(Encodes.encodeHex(hashPassword));
	}

	public boolean decryptPassword(Teacher teacher, String inputPassword) {
		String dbSalt = teacher.getSalt();
		String dbPassword = teacher.getPassword();
		byte[] deByte = Encodes.decodeHex(dbSalt);
		byte[] dePassword = Digests.sha1(inputPassword.getBytes(), deByte, HASH_INTERATIONS);
		String dePasswordStr = Encodes.encodeHex(dePassword);
		if (dePasswordStr.equals(dbPassword)) {
			return true;
		}
		return false;
	}

	
}
