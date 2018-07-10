package com.ymy.web.webapi;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
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

import com.ymy.entity.Checkin;
import com.ymy.entity.Course;
import com.ymy.entity.ScoreReport;
import com.ymy.entity.Student;
import com.ymy.entity.StudyReport;
import com.ymy.entity.Teacher;
import com.ymy.entity.User;
import com.ymy.service.CheckinService;
import com.ymy.service.CourseService;
import com.ymy.service.ScoreReportService;
import com.ymy.service.StudentService;
import com.ymy.service.StudyReportService;

@Controller
@RequestMapping(value="/manage/teacher")
public class TeacherController {
	@Autowired
	StudentService studentService;
	@Autowired
	CourseService courseService;
	@Autowired
	ScoreReportService scoreReportService;
	@Autowired
	StudyReportService studyReportService;
	@Autowired
	CheckinService checkinService;
	
	@RequestMapping(value="course",method=RequestMethod.GET)
	public String tocourseone(Model model,HttpServletRequest request){
		User user =(User) request.getSession().getAttribute("user");
		if(user!=null){
			List<Course> courselist=courseService.getListByTeacherid(user.getId());
			model.addAttribute("courselist", courselist);
		}
		return "manage/teacher/studentlist";
	}
	
	@RequestMapping(value="/studentlist")
	@ResponseBody
	public Page<Student> getStudentList(@RequestParam(value="pageNumber", defaultValue = "1")int pageNumber,
			@RequestParam(value="pageSize", defaultValue = "10")int pageSize,
			@RequestParam(value="sortType", defaultValue = "auto")String sortType,HttpServletRequest request){
		User user =(User) request.getSession().getAttribute("user");
		
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
		if (searchParams.get("EQ_course.id") != null && searchParams.get("EQ_course.id").toString().equals("0")) {
			searchParams.remove("EQ_course.id");
		}
		return studentService.getListByTeacherAndCourse(user.getId(), searchParams, pageNumber, pageSize, sortType);
	}
	
	/**
	 * 由id获取学生名字
	 * @param studentid
	 * @return
	 */
	@RequestMapping(value="/getstudentname")
	@ResponseBody
	public Map<String,Object> getStudentName(Long studentid){
		Map<String,Object> result =new HashMap<>();
		String name=studentService.get(studentid).getName();
		result.put("result", "1");
		result.put("name", name);
		return result;
	}
	
	
	/**
	 * 增加某学生的一条成绩记录
	 * @param scoreReport
	 * @param studentid
	 * @return
	 */
	@RequestMapping(value="/addscorereport")
	@ResponseBody
	public Map<String,Object> addScoreReport(@Valid ScoreReport scoreReport){
		Map<String,Object> result =new HashMap<>();
		scoreReport.setCreatetime(new Date());
		scoreReportService.save(scoreReport);
		String name=studentService.get(scoreReport.getStudentid()).getName();
		result.put("result", "1");
		result.put("name", name);
		return result;
	}
	
	/**
	 * 查询某学生的以往成绩单
	 * @param studentid
	 * @return
	 */
	@RequestMapping(value="/scorereportlist")
	@ResponseBody
	public Map<String,Object> scoreReportList(@RequestParam(value="studentid")Long studentid){
		Map<String,Object> result =new HashMap<>();
		String name=studentService.get(studentid).getName();
		List<ScoreReport> list=scoreReportService.getListByStudentid(studentid);
		if(list.size()>0){
			result.put("result", "1");
			result.put("name", name);
			result.put("data", list);
		}else{
			result.put("result", "0");
		}
		return result;
	}
	
	/**
	 * 增加某学生的一条表现记录
	 * @param studyReport
	 * @param studentid
	 * @return
	 */
	@RequestMapping(value="/addstudyreport")
	@ResponseBody
	public Map<String,Object> addStudyReport(@Valid StudyReport studyReport){
		Map<String,Object> result =new HashMap<>();
		studyReport.setCreatetime(new Date());
		studyReport.setTag(1);//表示有老师添加
		studyReportService.save(studyReport);
		result.put("result", "1");
		return result;
	}
	
	/**
	 * 查询某学生的以往上课表现
	 * @param studentid
	 * @return
	 */
	@RequestMapping(value="/studyreportlist")
	@ResponseBody
	public Map<String,Object> studyReportList(@RequestParam(value="studentid")Long studentid){
		Map<String,Object> result =new HashMap<>();
		String name=studentService.get(studentid).getName();
		List<StudyReport> list=studyReportService.getListByStudentid(studentid);
		if(list!=null&&list.size()>0){
			result.put("result", "1");
			result.put("name", name);
			result.put("data", list);
		}else{
			result.put("result", "0");
		}
		return result;
	}
	
	/**
	 * 打卡
	 * @param studentid
	 * @return
	 */
	@RequestMapping(value="/addusecount")
	@ResponseBody
	public Map<String,Object> addUseCount(Long studentid){
		Map<String,Object> result =new HashMap<>();
		Student student=studentService.get(studentid);
		Integer oldcount=student.getUsecount();
		Integer newcount=oldcount+1;
		student.setUsecount(newcount);
		studentService.save(student);
		result.put("result", "1");
		return result;
	}
	
	/**
	 * 撤销打卡
	 * @param studentid
	 * @return
	 */
	@RequestMapping(value="/reduceusecount")
	@ResponseBody
	public Map<String,Object> reduceUseCount(Long studentid){
		Map<String,Object> result =new HashMap<>();
		Student student=studentService.get(studentid);
		Integer oldcount=student.getUsecount();
		Integer newcount=(oldcount-1)<0?0:(oldcount-1);
		student.setUsecount(newcount);
		studentService.save(student);
		result.put("result", "1");
		return result;
	}
	
	/**
	 * 增加一个打卡记录（一次或多次课消）
	 * @param studentid
	 * @return
	 */
	@RequestMapping(value="/addcheckin")
	@ResponseBody
	public Map<String,Object> addCheckin(Long studentid,
										String createtime,
										String note,
										 Integer times,
										HttpServletRequest request){
		Map<String,Object> result=new HashMap<>();
		User user=(User)request.getSession().getAttribute("user");
		Checkin checkin=new Checkin();
		checkin.setStudentid(studentid);
		checkin.setNote(note);
		checkin.setTimes(times);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date date = null;
		try {
			date = sdf.parse(createtime);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		checkin.setCreatetime(date);
		checkin.setSchoolid(user.getSchoolid());
		checkinService.save(checkin);
		result.put("result", "1");
		return result;
	}
	
	/**
	 * 单个学生打卡记录列表（一次或多次课消）
	 * @param studentid
	 * @param schoolid
	 * @return
	 */
	@RequestMapping(value="/checkinlist")
	@ResponseBody
	public Map<String,Object> checkinList(Long studentid,HttpServletRequest request){
		Map<String,Object> result=new HashMap<>();
		User user=(User)request.getSession().getAttribute("user");
		String name=studentService.get(studentid).getName();
		List<Checkin> list=checkinService.getListByStudentid(studentid, user.getSchoolid());
		if(list!=null&&list.size()>0){
			result.put("result", "1");
			result.put("data", list);
			result.put("name", name);
		}else{
			result.put("result", "0");
		}
		return result;
	} 
	
	
	/**
	 * 删除（假删除）
	 * @param studentid
	 * @return
	 */
	@RequestMapping(value="/teacherdel")
	@ResponseBody
	public Map<String,Object> teacherDel(Long studentid){
		Map<String,Object> result =new HashMap<>();
		Student student=studentService.get(studentid);
		student.setTeacherdel(1);
		studentService.save(student);
		result.put("result", "1");
		return result;
	}
}
