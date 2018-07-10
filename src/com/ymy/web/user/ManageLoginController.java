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
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.ymy.entity.Resource;
import com.ymy.entity.User;
import com.ymy.service.ResourceService;
import com.ymy.service.UserService;

@Controller
@RequestMapping(value = "managelogin")
public class ManageLoginController {

	@Autowired
	private UserService userService;

	@Autowired
	ResourceService resourceService;

	private Logger logger = LoggerFactory.getLogger(ManageLoginController.class);

	@RequestMapping(method = RequestMethod.POST)
	public String login(String telephone, String password, HttpServletRequest request, String issave, Model model,
			HttpServletResponse response) {

		User user = userService.findByName(telephone);
		if (user != null && user.getIsdel() == 0) {
			boolean flag = userService.decryptPassword(user, password);
			if (!flag) {
				model.addAttribute("message", "您输入的密码和账户不匹配,请重新输入!");
				return "homepage/index";
			}
		} else {
			model.addAttribute("message", "您输入的账号不存在,请重新输入!");
			return "homepage/index";
		}
		if (issave != null) {
			Cookie ck = new Cookie("user", user.getId().toString());
			ck.setMaxAge(999999999);
			response.addCookie(ck);
		}

		String loginIp = request.getRemoteAddr();
		logger.error("用户" + user.getName() + "登陆的ip为    : " + loginIp);
		System.out.println("登陆的ip为    : " + loginIp);

		if (loginIp.equals("0:0:0:0:0:0:0:1")) {
			loginIp = "127.0.0.1";
		}

		//
		// 登陆成功 user放入缓存
		HttpSession session = request.getSession();
		// session.setMaxInactiveInterval(60*5);
		session.setAttribute("user", user);
		List<Map<Resource, List<Resource>>> mapList = new ArrayList<Map<Resource, List<Resource>>>();
		List<Resource> resList = resourceService.findByRoleId(user.getRole().getId());
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
				List<Resource> subResList = resourceService.findSubResources(pid, user.getRole().getId());
				Map<Resource, List<Resource>> map = new HashMap<Resource, List<Resource>>();
				map.put(pRes, subResList);
				mapList.add(map);
			}
			// request.getSession().setAttribute("access", resList);
			request.getSession().setAttribute("menuList", mapList);
		}
		return "redirect:/manage/menu/welcome";
	}

	/**
	 * 用户登录的GET方法
	 * 
	 * @author
	 * @date 2015�?4�?20�? 下午1:26:03
	 * @return
	 */
	@RequestMapping(method = RequestMethod.GET)
	public String login(HttpServletRequest request, Model model) {
		return "manage/account/login";
	}

	// @RequestMapping(value = "autologin")
	// public String autoLogin(HttpServletRequest request, @RequestParam(value =
	// "uid") Long uid) {
	// User u = userService.get(uid);
	// if (u != null && u.getIsdel() == 0) {
	// {
	// System.out.println("自动登陆");
	// request.getSession().setAttribute("user", u);
	//
	// List<Map<Resource, List<Resource>>> mapList = new ArrayList<Map<Resource,
	// List<Resource>>>();
	// List<Resource> resList =
	// resourceService.findByRoleId(u.getRole().getId());
	// if (resList != null && resList.size() > 0) {
	// List<Long> idList = new ArrayList<Long>();
	// for (Resource res : resList) {
	// if (res != null) {
	// Resource pRes = res.getResource();
	// if (pRes == null) {
	// idList.add(res.getId());
	// } else {
	// if (idList.contains(pRes.getId())) {
	// continue;
	// } else {
	// idList.add(pRes.getId());
	// }
	// }
	// }
	// }
	// List<Long> sortedList = resourceService.getSortedList(idList);
	// // System.out.println(sortedList);
	// for (Long pid : sortedList) {
	// Resource pRes = new Resource();
	// pRes = resourceService.get(pid);
	// // System.out.println(pRes.getName());
	// List<Resource> subResList = resourceService.findSubResources(pid,
	// u.getRole().getId());
	// Map<Resource, List<Resource>> map = new HashMap<Resource,
	// List<Resource>>();
	// map.put(pRes, subResList);
	// mapList.add(map);
	// }
	// // request.getSession().setAttribute("access", resList);
	// request.getSession().setAttribute("menuList", mapList);
	// }
	// }
	// }
	// return "redirect:/manage/menu/welcome";
	// }

}
