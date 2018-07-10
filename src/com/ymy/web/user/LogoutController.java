package com.ymy.web.user;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * 类LogoutController.java的实现描述：用户账号退出系统调用的Controller
 * 
 */
@Controller
@RequestMapping(value = "/logout")
public class LogoutController {

	@RequestMapping(method = RequestMethod.GET)
	public String logout(HttpServletRequest request, HttpServletResponse response) {
		HttpSession session = request.getSession();
		// 从session里取的用户名信息
		session.removeAttribute("user");
		session.removeAttribute("menuList");
		session.invalidate();
		Cookie ck = new Cookie("user", null);
		ck.setMaxAge(0);
		response.addCookie(ck);
		return "homepage/index";
	}

	/**
	 * 账号退出系统时候的POST方法
	 * 
	 * @date 2015年4月20日 下午1:28:16
	 * @return
	 */
	@RequestMapping(method = RequestMethod.POST)
	public String logout() {
		return "homepage/index";
	}
}
