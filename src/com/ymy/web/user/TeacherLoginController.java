package com.ymy.web.user;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.ymy.entity.Resource;
import com.ymy.entity.Teacher;
import com.ymy.entity.User;
import com.ymy.service.ResourceService;
import com.ymy.service.TeacherService;



@Controller
@RequestMapping(value="/teacherlogin")
public class TeacherLoginController {
	
	@Autowired
	TeacherService teacherService;
//	@Autowired
//	LoginRecordService loginRecordService;

	@Autowired
	RedisTemplate redisTemplate;

//	@RequestMapping(method = RequestMethod.GET)
//	public String login(HttpServletRequest request) {
//		Teacher teacher = (Teacher) request.getSession().getAttribute("teacher");
//		if (teacher != null) {
//			return "redirect:/teacher/index";
//		}
//		return "teacher/login";
//	}

//	@RequestMapping(method = RequestMethod.POST)
//	public String login(String account, String password, Integer issave, HttpServletRequest request, Model model,
//			HttpServletResponse response) {
//
//		Teacher teacher = teacherService.getByAccount(account);
//		if (teacher != null) {
//			boolean flag = teacherService.decryptPassword(teacher, password);
//			if (!flag) {
//				model.addAttribute("message", "�������������˻���ƥ��,����������!");
//				return "teacher/login";
//			}
//		} else {
//			model.addAttribute("message", "��������˺Ų�����,����������!");
//			return "teacher/login";
//		}
//
//		if (issave != null) {
//			Cookie ck = new Cookie("teacher", teacher.getId().toString());
//			ck.setMaxAge(999999999);
//			ck.setPath("/");
//			response.addCookie(ck);
//		}
//		String loginIp = request.getRemoteAddr();
//		System.out.println("��½��ipΪ    : " + loginIp);
//
//		if (loginIp.equals("0:0:0:0:0:0:0:1")) {
//			loginIp = "127.0.0.1";
//		}
//		// �����¼��¼
//		//LoginRecord lr = new LoginRecord();
////		lr.setAccountid(teacher.getId());
////		lr.setAccounttype("teacher");
////		lr.setCreatetime(new Date());
////		lr.setIp(loginIp);
////		lr.setType("web");
////		LoginRecordSaveThread lrst = new LoginRecordSaveThread(loginRecordService, lr);
//
//		//lrst.run();
//
//		// lrst.run();
//
//		HttpSession session = request.getSession();
//		session.setAttribute("teacher", teacher);
//		return "redirect:/teacher/index";
//	}

//	@RequestMapping(value = "out")
//	public String logout(HttpServletRequest request, HttpServletResponse response) {
//		HttpSession session = request.getSession();
//		// ��session��ȡ���û�����Ϣ
//		session.removeAttribute("teacher");
//		session.invalidate();
//		Cookie ck = new Cookie("teacher", null);
//		ck.setMaxAge(0);
//		ck.setPath("/");
//		response.addCookie(ck);
//		return "teacher/login";
//	}
	
	
	@Autowired
	ResourceService resourceService;

	private Logger logger = LoggerFactory.getLogger(TeacherLoginController.class);

	@RequestMapping(method = RequestMethod.POST)
	public String login(String telephone, String password, HttpServletRequest request, String issave, Model model,
			HttpServletResponse response) {

		Teacher teacher = teacherService.getByAccount(telephone);
		if (teacher != null && teacher.getIsdel() == 0) {
			boolean flag = teacherService.decryptPassword(teacher, password);
			if (!flag) {
				model.addAttribute("message", "您输入的密码和账户不匹配,请重新输入!");
				return "homepage/index";
			}
		} else {
			model.addAttribute("message", "您输入的账号不存在,请重新输入!");
			return "homepage/index";
		}
		if (issave != null) {
			Cookie ck = new Cookie("teacher", teacher.getId().toString());
			ck.setMaxAge(999999999);
			response.addCookie(ck);
		}

		String loginIp = request.getRemoteAddr();
		logger.error("用户" + teacher.getRealname() + "登陆的ip为    : " + loginIp);
		System.out.println("登陆的ip为    : " + loginIp);

		if (loginIp.equals("0:0:0:0:0:0:0:1")) {
			loginIp = "127.0.0.1";
		}

		//
		// 登陆成功 user放入缓存
		HttpSession session = request.getSession();
		// session.setMaxInactiveInterval(60*5);
		session.setAttribute("teacher", teacher);
		List<Map<Resource, List<Resource>>> mapList = new ArrayList<Map<Resource, List<Resource>>>();
		List<Resource> resList = resourceService.findByRoleId(teacher.getRole().getId());
		if (resList != null && resList.size() > 0) {
			List<Long> idList = new ArrayList<Long>();
			for (Resource res : resList) {
				if (res != null) {
					Resource pRes = res.getResource();
					if (pRes == null) {
						idList.add(res.getId());
					} else {
						if (idList.contains(pRes.getId())) {
							continue;
						} else {
							idList.add(pRes.getId());
						}
					}
				}
			}
			List<Long> sortedList = resourceService.getSortedList(idList);
			// System.out.println(sortedList);
			for (Long pid : sortedList) {
				Resource pRes = new Resource();
				pRes = resourceService.get(pid);
				// System.out.println(pRes.getName());
				List<Resource> subResList = resourceService.findSubResources(pid, teacher.getRole().getId());
				Map<Resource, List<Resource>> map = new HashMap<Resource, List<Resource>>();
				map.put(pRes, subResList);
				mapList.add(map);
			}
			// request.getSession().setAttribute("access", resList);
			request.getSession().setAttribute("menuList", mapList);
		}
		return "redirect:/manage/menu/welcome";
	}

	

	
}
