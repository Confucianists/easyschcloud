package com.ymy.web.will;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springside.modules.web.Servlets;
import org.w3c.dom.css.ElementCSSInlineStyle;

import com.ymy.utils.QRGenUtils;
import com.ymy.utils.Util;
import com.ymy.entity.Will;
import com.ymy.service.CourseService;
import com.ymy.service.SalesService;
import com.ymy.service.WillService;
import com.ymy.utils.ExcelUtil;


@Controller
@RequestMapping(value = "/manage/will")
public class WillController {
	
	@Autowired
	WillService willService;
	
	@Autowired
	CourseService courseService;
	
	@Autowired
	SalesService salesService;
	
	
	/**
	 * ��������ѧԱ�б�
	 * @return
	 */
	@RequestMapping(value="ing",method = RequestMethod.GET)
	public String ingList(Model model) {
		model.addAttribute("courses",courseService.getList());
		model.addAttribute("saless",salesService.getList());
		return "manage/will/inglist";
	}
	
	/**
	 * ��ת���ɹ�ѧԱ�б�
	 * @return
	 */
	@RequestMapping(value="ok",method = RequestMethod.GET)
	public String okList(Model model) {
		model.addAttribute("courses",courseService.getList());
		model.addAttribute("saless",salesService.getList());
		return "manage/will/oklist";
	}
	
	/**
	 * ������ѧԱͳ��ͼҳ��
	 * @return
	 */
	@RequestMapping(method = RequestMethod.GET)
	public String dataCollect() {
		return "manage/will/echarts";
	}
	
	/**
	 * ��ȡȫ����ѯ��ѧԱ��ת���ɹ�������У�
	 * @param pageNumber
	 * @param pageSize
	 * @param sortType
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "willlist")
	@ResponseBody
	private Page<Will> getRecordList(@RequestParam(value = "page", defaultValue = "1") int pageNumber,
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

		return willService.getList(searchParams, pageNumber, pageSize, sortType);
	}
	
	
	/**
	 * ��ȡת���ɹ���ѧԱ
	 * @param pageNumber
	 * @param pageSize
	 * @param sortType
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "willoklist")
	@ResponseBody
	private Page<Will> getWillOKList(@RequestParam(value = "page", defaultValue = "1") int pageNumber,
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
		
		if (searchParams.get("EQ_course.id") != null && searchParams.get("EQ_course.id").toString().equals("0")) {
			searchParams.remove("EQ_course.id");
		}

		if (searchParams.get("EQ_sales.id") != null
				&& searchParams.get("EQ_sales.id").toString().equals("0")) {
			searchParams.remove("EQ_sales.id");
		}
		
		return willService.getWillOkList(1, searchParams, pageNumber, pageSize, sortType);
	}
	
	/**
	 * ��ȡ��ѯ�����е�ѧԱ
	 * @param pageNumber
	 * @param pageSize
	 * @param sortType
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "willinglist")
	@ResponseBody
	private Page<Will> getWillIngList(@RequestParam(value = "page", defaultValue = "1") int pageNumber,
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
		
		if (searchParams.get("EQ_course.id") != null && searchParams.get("EQ_course.id").toString().equals("0")) {
			searchParams.remove("EQ_course.id");
		}

		if (searchParams.get("EQ_sales.id") != null
				&& searchParams.get("EQ_sales.id").toString().equals("0")) {
			searchParams.remove("EQ_sales.id");
		}
		
		return willService.getWillOkList(0, searchParams, pageNumber, pageSize, sortType);
	}
	
	/**
	 * ��ȡͳ��ͼ����
	 * @return
	 */
	@RequestMapping(value = "recentweekdata")
	@ResponseBody
	public Map<String, Object>  getCount7(){
		
		Map<String, Object> result=new HashMap<>();
		List<Object[]> lob =willService.getCountRecentWeek();
		
		if(lob.size()==0){
			result.put("result", "0");
		}else{
			
			result.put("result", "1");
			result.put("datas", lob);
		}
		return result;
	}
	
	/**
	 * ��ȡͳ��ͼ����
	 * @return
	 */
	@RequestMapping(value = "recentmonthdata")
	@ResponseBody
	public Map<String, Object>  getCount30(){
		
		Map<String, Object> result=new HashMap<>();
		List<Object[]> lob =willService.getCountRecentMonth();
		
		if(lob.size()==0){
			result.put("result", "0");
		}else{
			
			result.put("result", "1");
			result.put("datas", lob);
		}
		return result;
	}
	
	/**
	 * ��ȡͳ��ͼ����
	 * @return
	 */
	@RequestMapping(value = "recentsixmonthdata")
	@ResponseBody
	public Map<String, Object>  getCount180(){
		
		Map<String, Object> result=new HashMap<>();
		List<Object[]> lob =willService.getCountRecentSixMonth();
		if(lob.size()==0){
			result.put("result", "0");
		}else{
			
			result.put("result", "1");
			result.put("datas", lob);
		}
		return result;
	}
	
	/**
	 * 当前月报名人数
	 * @return
	 */
	@RequestMapping(value = "currentmonthcount")
	@ResponseBody
	public Map<String,Object> getCount(){
		Map<String,Object> result=new HashMap<>();
		Integer count=willService.getCurrentMonthCount();
		result.put("count", count);
		return result;
	}
	
	
	@RequestMapping(value = "export/{time}")
	@ResponseBody
	public void exportExcel(@PathVariable(value = "time") String time,HttpServletResponse response) {
		
		
		String[] rowsName = new String[] { "���", "����", "����" };
		List<Object[]> dataList=new ArrayList<>();
		String title = null;
		List<Object[]> dataListtemp = null;
		if(time.toString().equals("week")){
			title="一周数据";
			dataListtemp = willService.getCountRecentWeek();
		}else if(time.toString().equals("month")){
			title="一个月数据";
			dataListtemp = willService.getCountRecentMonth();
		}else if(time.toString().equals("sixmonth")){
			title="六个月数据";
			dataListtemp = willService.getCountRecentSixMonth();
		}
		for(Object[] ob:dataListtemp){
			Object[] obs=new Object[3];
			//obs[0]="";
			obs[1]=ob[0];
			obs[2]=ob[1];
			dataList.add(obs);
		}
		
		ExcelUtil ex = new ExcelUtil(title, rowsName, dataList);
		try {
			String fileName = "Excel-" + String.valueOf(System.currentTimeMillis()).substring(4, 13) + ".xls";
			String headStr = "attachment; filename=\"" + fileName + "\"";
			response.setContentType("APPLICATION/OCTET-STREAM");
			response.setHeader("Content-Disposition", headStr);
			OutputStream out = response.getOutputStream();
			ex.export().write(out);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	
	
	@RequestMapping(value = "exporthtml/{time}")
	@ResponseBody
	public void exportHtml(@PathVariable(value = "time") String time,HttpServletResponse response) {
		
		String title=null;
		
		List<Object[]> dataList = new ArrayList<Object[]>();
		List<Object[]> dataListtemp=null;
		if(time.toString().equals("week")){
			title="近一周报名数据";
			dataListtemp = willService.getCountRecentWeek();
		}else if(time.toString().equals("month")){
			title="近一个月报名数据";
			dataListtemp = willService.getCountRecentMonth();
		}else if(time.toString().equals("sixmonth")){
			title="近六个月报名数据";
			dataListtemp = willService.getCountRecentSixMonth();
		}
		
		if (dataListtemp.size() > 0) {
			for(int i=0;i<dataListtemp.size();i++){
				Object[] obs=new Object[3];
				obs[0]=i+1;
				obs[1]=dataListtemp.get(i)[0];
				obs[2]=dataListtemp.get(i)[1];
				dataList.add(obs);
			}
		}
		
		String head="<h2 style='text-align: center;'>"+title+"</h2>";
		
		StringBuffer buffer=QRGenUtils.getDataList(dataList);
	
		try {
			String template = readFile(Util.getRootPath() + "template" + File.separator + "code.html");
				
			template = template.replace("{name}", head);
			template = template.replace("{codecontent}", buffer);
			String fileName = "code-" + String.valueOf(System.currentTimeMillis()).substring(4, 13) + ".html";
			String headStr = "attachment; filename=\"" + fileName + "\"";
			response.setContentType("APPLICATION/OCTET-STREAM");
			response.setHeader("Content-Disposition", headStr);
			response.getOutputStream().write(template.getBytes());
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
	
	// 读取HTML模板文件new.template
		public String readFile(String path) throws IOException {
			InputStream is = null;
			String result = "";
			try {
				@SuppressWarnings("unused")
				int data = 0;
				byte[] by = new byte[1024];
				is = new FileInputStream(path);
				while ((data = is.read(by)) != -1) {
					// result+=(char)data;
					// result=new String(data);
					result = new String(by, 0, by.length);
				}
			} catch (FileNotFoundException e) {
				System.out.println("未找到new.template文件！");
				e.printStackTrace();
			} finally {
				System.out.println("创建成功！");
				is.close();
			}
			// return result.toString();
			return result;
		}
	
	
	@RequestMapping(value = "save")
	@ResponseBody
	public Map<String, Object> save(@Valid Will will) {
		Map<String, Object> result = new HashMap<String, Object>();
		will.setCreatetime(new Date());
		willService.save(will);
		result.put("result", "1");
		return result;
	}

	@RequestMapping(value = "update")
	@ResponseBody
	public Map<String, Object> update(@Valid Will will) {
		Map<String, Object> result = new HashMap<String, Object>();
		Will oldwill = willService.get(will.getId());
		
		will.setCreatetime(oldwill.getCreatetime());
		willService.save(will);
		result.put("result", "1");
		return result;
	}

	@RequestMapping(value = "/delete")
	@ResponseBody
	public Map<String, Object> delete(@RequestParam(value = "id") Long id) {
		Map<String, Object> result = new HashMap<String, Object>();
		willService.delete(id);
		result.put("result", "1");
		return result;
	}
}
