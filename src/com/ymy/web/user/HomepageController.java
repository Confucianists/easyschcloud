package com.ymy.web.user;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ymy.entity.Teacher;
import com.ymy.service.TeacherService;

@Controller
@RequestMapping(value="/homepage")
public class HomepageController {
	
	@Autowired
	TeacherService teacherService;
	
	@RequestMapping(value="list")
	@ResponseBody
	public Map<String, Object> getTeacherList(){
		Map<String,Object> result=new HashMap<>();
		List<Teacher> list =teacherService.getList();
		if(list.size()!=0){
			result.put("result", "1");
		}else{
			result.put("result", "0");
		}
		return result;
	}
	
	@RequestMapping(value="aa")
	public String todecorator(){
		return "homepage/homepage";
	}
	
}
