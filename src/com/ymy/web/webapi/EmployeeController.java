package com.ymy.web.webapi;

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

import com.ymy.entity.Employee;
import com.ymy.entity.Payment;
import com.ymy.service.CourseService;
import com.ymy.service.EmployeeService;
import com.ymy.service.PaymentService;
import com.ymy.service.SalesService;

@Controller
@RequestMapping(value="/manage/employee")
public class EmployeeController {
	
	@Autowired
	EmployeeService employeeService;
	
	@Autowired
	CourseService courseService;
	@Autowired
	SalesService salesService;
	
	
	@RequestMapping(value="list",method = RequestMethod.GET)
	public String tolistpage(Model model){
		model.addAttribute("courses",courseService.getList());
		model.addAttribute("saless",salesService.getList());
		return "manage/employee/list";
	}
	
	
	@RequestMapping(value = "employeelist")
	@ResponseBody
	private Page<Employee> getRecordList(@RequestParam(value = "page", defaultValue = "1") int pageNumber,
			@RequestParam(value = "pagesize", defaultValue = "10") int pageSize,
			@RequestParam(value = "sortType", defaultValue = "auto") String sortType, Model model,
			HttpServletRequest request) {
		Map<String, Object> searchParams = Servlets.getParametersStartingWith(request, "search_");
		if (searchParams.containsKey("GTE_createtime")) {
			if (searchParams.get("GTE_createtime").equals("")) {
				searchParams.remove("GTE_createtime");
			} else {
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				try {
					searchParams.put("GTE_createtime", sdf.parse(searchParams.get("GTE_createtime").toString()));
				} catch (ParseException e) {
					e.printStackTrace();
				}
			}
		}
		if (searchParams.containsKey("LTE_createtime")) {
			if (searchParams.get("LTE_createtime").equals("")) {
				searchParams.remove("LTE_createtime");
			} else {
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				Date date = null;
				try {
					date = sdf.parse(searchParams.get("LTE_createtime").toString());
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				Calendar ca = Calendar.getInstance();
				ca.setTime(date);
				ca.add(Calendar.HOUR, +24);
				searchParams.put("LTE_createtime", ca.getTime());
			}

		}

		if (searchParams.get("EQ_school.id") != null && searchParams.get("EQ_school.id").toString().equals("0")) {
			searchParams.remove("EQ_school.id");
		}

		if (searchParams.get("EQ_deviceModel.id") != null
				&& searchParams.get("EQ_deviceModel.id").toString().equals("0")) {
			searchParams.remove("EQ_deviceModel.id");
		}

		return employeeService.getList(searchParams, pageNumber, pageSize, sortType);
	}
	
	
	@RequestMapping(value = "save")
	@ResponseBody
	public Map<String, Object> save(@Valid Employee employee) {
		Map<String, Object> result = new HashMap<String, Object>();
		employee.setCreatetime(new Date());
		employeeService.save(employee);
		result.put("result", "1");
		return result;
	}

	@RequestMapping(value = "update")
	@ResponseBody
	public Map<String, Object> update(@Valid Employee employee) {
		Map<String, Object> result = new HashMap<String, Object>();
		Employee oldemployee = employeeService.get(employee.getId());
		
		employee.setCreatetime(oldemployee.getCreatetime());
		employeeService.save(employee);
		result.put("result", "1");
		return result;
	}

	@RequestMapping(value = "/delete")
	@ResponseBody
	public Map<String, Object> delete(@RequestParam(value = "id") Long id) {
		Map<String, Object> result = new HashMap<String, Object>();
		employeeService.delete(id);
		result.put("result", "1");
		return result;
	}
}
