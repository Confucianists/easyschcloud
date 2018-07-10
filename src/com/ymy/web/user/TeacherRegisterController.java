package com.ymy.web.user;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ymy.entity.Teacher;
import com.ymy.service.TeacherService;

@Controller
@RequestMapping(value="/teacherregister")
public class TeacherRegisterController {
	
	@Autowired
	TeacherService teacherService;
	
	/**
	 * �������ñ���
	 * 
	 * @param account
	 * @param passsword
	 * @return
	 */
	@RequestMapping(value = "savereset")
	@ResponseBody
	public Map<String, String> saveReset(String account, String password) {
		Map<String, String> result = new HashMap<>();
		Teacher ter = teacherService.getByAccount(account);
		if (ter != null) {
				ter.setPassword(password);
				ter.setSalt(null);
				teacherService.save(ter);
				result.put("result", "1");//�޸ı���ɹ�
		} else {
			result.put("result", "0");//�޸�ʧ��
		}
		return result;
	}
}
