package com.ymy.web.webapi;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.ymy.entity.School;
import com.ymy.entity.User;
import com.ymy.service.SchoolService;
import com.ymy.service.UserService;
import com.ymy.utils.POIUtil;

@Controller
@RequestMapping(value="/manage/schooladmin")
public class SchoolAdminController {
	
	@Autowired
	SchoolService schoolService;
	@Autowired
	UserService userService;
	
	@RequestMapping(value="newschool",method=RequestMethod.GET)
	public String tonewschool(){
		return "manage/schooladmin/newschool";
	}
	
	@RequestMapping(value="newsubject",method=RequestMethod.GET)
	public String tonewsubject(){
		return "manage/schooladmin/newsubject";
	} 
	
	@RequestMapping(value="importuser",method=RequestMethod.GET)
	public String toimportuser(){
		return "manage/schooladmin/importuser";
	} 
	
	@RequestMapping(value = "createschool")
	@ResponseBody
	public Map<String, Object> createSchool(@RequestParam(value = "name") String name,@RequestParam(value = "address") String address,@RequestParam(value = "note") String note,HttpServletRequest request){
		Map<String, Object> result = new HashMap<>();
		School school =new School();
		school.setName(name);
		school.setAddress(address);
		school.setNote(note);
		school.setCreatetime(new Date());
		try {
			School s=schoolService.save(school);//创建保存学校
			result.put("result", "1");
			User user=(User)request.getSession().getAttribute("user");//创建学校后，设置当前管理员的shoolid
			user.setSchoolid(s.getId());
			userService.saveCaptcha(user);
		} catch (Exception e) {
			e.printStackTrace();
			result.put("result", "0");
		}
		return result;
	}
	
	@RequestMapping(value = "importexcel")
	@ResponseBody
	private Map<String, Object> importExcel(@RequestParam(value = "excel", required = false) CommonsMultipartFile file,
			HttpServletRequest request) {
		Map<String, Object> result = new HashMap<>();
		User user = (User) request.getSession().getAttribute("user");
		if (user == null) {
			result.put("result", "-9");
		} else {

			try {
				List<String[]> ls = POIUtil.readExcel(file);
				if (ls != null) {
					List<Map<String, String>> data = new ArrayList<>();
					for (String[] s : ls) {
						//schoolTeacherService.checkST(s[2], s[1], s[3], user.getSchoolid());
					}
				}
				result.put("result", "1");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				result.put("result", "0");
			}
		}
		return result;
	}
}
