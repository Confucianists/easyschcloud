package com.ymy.web.webapi;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping(value="/manage/index")
public class IndexController {
	
	@RequestMapping(method = RequestMethod.GET)
	public String tolistpage(Model model){
		return "manage/index";
	}

}
