package com.ymy.service.impl;

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
import com.ymy.service.UserService;
import com.ymy.utils.Util;

@Component
@Transactional
public class UserServiceImpl implements UserService {

	@Autowired
	private UserDao userDao;

	public static final int HASH_INTERATIONS = 1024;
	public static final String HASH_ALGORITHM = "SHA-1";
	private static final int SALT_SIZE = 8;

	public Page<User> getUser(Map<String, Object> searchParams, int pageNumber, int pageSize, String sortType) {
		PageRequest pageRequest = buildPageRequest(pageNumber, pageSize, sortType);
		Specification<User> spec = Util.buildSpecification(searchParams, User.class);
		return userDao.findAll(spec, pageRequest);
	}

	/**
	 * 添加用户或�?�修改密码时使用
	 * 
	 * @param user
	 * @return
	 */
	public User saveorupdate(User user) {
		entryptPassword(user);
		return userDao.save(user);
	}

	/**
	 * 修改验证�?
	 * 
	 * @param repair
	 * @return
	 */
	public User saveCaptcha(User user) {
		return userDao.save(user);
	}

	/**
	 * 判断该用户名是否已存�?
	 * 
	 * @param loginName
	 * @return
	 */

	public User findByName(String name) {
		List<User> byNameList = userDao.findByName(name);
		if (byNameList != null && byNameList.size() > 0) {
			return byNameList.get(0);
		}
		return null;
	}

	public int findByNameAndId(String name, Long id) {
		return userDao.findByNameAndId(name, id);
	}

	/**
	 * 删除
	 * 
	 * @param id
	 */
	public void delete(Long id) {
		userDao.delete(id);
	}

	/**
	 * 分页排序
	 * 
	 * @param pageNumber
	 * @param pageSize
	 * @param sortType
	 * @return
	 */
	public PageRequest buildPageRequest(int pageNumber, int pageSize, String sortType) {
		Sort sort = null;
		if ("auto".equals(sortType)) {
			sort = new Sort(Direction.DESC, "id");
		} else if ("title".equals(sortType)) {
			sort = new Sort(Direction.ASC, "name");
		} else if ("createtime".equals(sortType)) {
			sort = new Sort(Direction.DESC, "createtime");
		}

		return new PageRequest(pageNumber - 1, pageSize, sort);
	}

	public User get(Long id) {
		return userDao.findOne(id);
	}

	public void update(User r) {
		userDao.save(r);
	}

	public List<User> findByRole(Long id) {
		return userDao.findByRole(id);
	}

	public void entryptPassword(User user) {
		byte[] salt = Digests.generateSalt(SALT_SIZE);
		user.setSalt(Encodes.encodeHex(salt));

		byte[] hashPassword = Digests.sha1(user.getPassword().getBytes(), salt, HASH_INTERATIONS);
		user.setPassword(Encodes.encodeHex(hashPassword));
	}

	public String entryptPasswordStr(String salt, String password) {
		byte[] deByte = Encodes.decodeHex(salt);
		byte[] hashPassword = Digests.sha1(password.getBytes(), deByte, HASH_INTERATIONS);
		return Encodes.encodeHex(hashPassword);
	}

	public boolean decryptPassword(User user, String inputPassword) {
		String dbSalt = user.getSalt();
		String dbPassword = user.getPassword();
		byte[] deByte = Encodes.decodeHex(dbSalt);
		byte[] dePassword = Digests.sha1(inputPassword.getBytes(), deByte, HASH_INTERATIONS);
		String dePasswordStr = Encodes.encodeHex(dePassword);
		if (dePasswordStr.equals(dbPassword)) {
			return true;
		}
		return false;
	}
	
	//用户自己修改密码
	public boolean editpassword(User user,String inputpassword,String newpassword){
		String dbSalt = user.getSalt();
		String dbPassword = user.getPassword();
		byte[] deByte = Encodes.decodeHex(dbSalt);
		byte[] dePassword = Digests.sha1(inputpassword.getBytes(), deByte, HASH_INTERATIONS);
		String dePasswordStr = Encodes.encodeHex(dePassword);
		if (dePasswordStr.equals(dbPassword)) {
			byte[] dePassword_new = Digests.sha1(newpassword.getBytes(), deByte, HASH_INTERATIONS);
			String dePasswordStr_new = Encodes.encodeHex(dePassword_new);
			user.setPassword(dePasswordStr_new);
			userDao.save(user);
			return true;
		}else{
			return false;
		}
		
	}
	

	public List<User> findUseByRole(Long id) {
		return userDao.findUseByRole(id);
	}

	public void setDel(Long id) {
		userDao.setdel(id);
	}

	public int getCountByRole(Long roleid) {
		return userDao.getCountByRole(roleid);
	}

	@Override
	public User doUserLogin(User user) {
		return user;
	}
}
