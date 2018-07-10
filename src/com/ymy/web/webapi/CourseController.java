package com.ymy.web.webapi;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ymy.entity.User;
import com.ymy.service.CourseService;

@Controller
@RequestMapping(value="/manage/course")
public class CourseController {
	@Autowired
	CourseService courseService;
	
	@RequestMapping(value="group")
	@ResponseBody
	public Page<Object[]> getCourseCountByGroup(HttpServletRequest request,@RequestParam(value="page",defaultValue="1") Integer pageNumber,@RequestParam(value="pagesize",defaultValue="10") Integer pageSize,@RequestParam(value="sortType",defaultValue="auto") String sortType){
		User user =(User)request.getSession().getAttribute("user");
	return	courseService.getGroupBySchoolid(user.getSchoolid(),pageNumber,pageSize,sortType);
	}
}
