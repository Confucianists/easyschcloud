package com.ymy.web.manage;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springside.modules.web.Servlets;


import com.ymy.entity.User;
import com.ymy.service.RoleService;
import com.ymy.service.UserService;

@Controller
@RequestMapping(value = "/manage/usermanage")
public class UserManageController {

	@Autowired
	UserService userService;
	@Autowired
	RoleService roleService;

	@RequestMapping(method = RequestMethod.GET)
	public String List(Model model) {
		model.addAttribute("roles", roleService.findRoles());
		return "manage/user/list";
	}

	@RequestMapping(value = "list")
	@ResponseBody
	private Page<User> getRecordList(@RequestParam(value = "page", defaultValue = "1") int pageNumber,
			@RequestParam(value = "pagesize", defaultValue = "10") int pageSize,
			@RequestParam(value = "sortType", defaultValue = "auto") String sortType, HttpServletRequest request) {
		Map<String, Object> searchParams = Servlets.getParametersStartingWith(request, "search_");
		searchParams.put("EQ_isdel", "0");
		if (searchParams.containsKey("EQ_role.id") && searchParams.get("EQ_role.id").toString().equals("0")) {
			searchParams.remove("EQ_role.id");
		}
		if (searchParams.containsKey("LIKE_realname")) {
			try {
				searchParams.put("LIKE_realname", URLDecoder
						.decode(URLDecoder.decode(searchParams.get("LIKE_realname").toString(), "utf-8"), "utf-8"));
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		}
		return userService.getUser(searchParams, pageNumber, pageSize, sortType);
	}

	@RequestMapping(value = "save")
	@ResponseBody
	public Map<String, Object> save(@Valid User user) {
		Map<String, Object> result = new HashMap<String, Object>();
		user.setPassword("000000");
		user.setRegistertime(new Date());
		user.setIsdel(0);
		userService.saveorupdate(user);
		result.put("result", "1");
		return result;
	}

	@RequestMapping(value = "update")
	@ResponseBody
	public Map<String, Object> update(@Valid User user) {
		Map<String, Object> result = new HashMap<String, Object>();
		User olduser = userService.get(user.getId());
		olduser.setRealname(user.getRealname());
		olduser.setRole(user.getRole());
		userService.update(olduser);
		result.put("result", "1");
		return result;
	}

	@RequestMapping(value = "/delete")
	@ResponseBody
	public Map<String, Object> delete(@RequestParam(value = "id") Long id) {
		Map<String, Object> result = new HashMap<String, Object>();
		userService.setDel(id);
		result.put("result", "1");
		return result;
	}
}
