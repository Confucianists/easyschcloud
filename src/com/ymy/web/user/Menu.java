package com.ymy.web.user;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.ymy.entity.Resource;
import com.ymy.entity.Role;

import com.ymy.service.ResourceService;


@Controller
@RequestMapping(value = "/manage/menu")
public class Menu {

	@RequestMapping(value = "welcome")
	public String welcome() {
		return "manage/welcome";
	}

}
