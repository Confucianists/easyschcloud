package com.ymy.web.webapi;

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

import com.ymy.entity.Payment;
import com.ymy.entity.Will;
import com.ymy.service.CourseService;
import com.ymy.service.PaymentService;
import com.ymy.service.SalesService;
import com.ymy.utils.ExcelUtil;
import com.ymy.utils.QRGenUtils;
import com.ymy.utils.Util;

@Controller
@RequestMapping(value="/manage/payment")
public class PaymentController {
	@Autowired
	PaymentService paymentService;
	@Autowired
	CourseService courseService;
	@Autowired
	SalesService salesService;
	
	@RequestMapping(method = RequestMethod.GET)
	public String topage(){
		return "manage/payment/echarts";
	}
	@RequestMapping(value="list",method = RequestMethod.GET)
	public String tolistpage(Model model){
		model.addAttribute("courses",courseService.getList());
		model.addAttribute("saless",salesService.getList());
		return "manage/payment/list";
	}
	
	
	@RequestMapping(value = "paymentlist")
	@ResponseBody
	private Page<Payment> getRecordList(@RequestParam(value = "page", defaultValue = "1") int pageNumber,
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

		return paymentService.getList(searchParams, pageNumber, pageSize, sortType);
	}
	
	
	
	
	
	
	
	
	@RequestMapping(value="data")
	@ResponseBody
	public Map<String, Object> getRecentSum(){
		
		Map<String, Object> result=new HashMap<>();
		List<Object[]> lob =paymentService.getSumRecentWeek();
		
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
	@RequestMapping(value = "recentweekdata")
	@ResponseBody
	public Map<String, Object>  getCount7(){
		
		Map<String, Object> result=new HashMap<>();
		List<Object[]> lob =paymentService.getSumRecentWeek();
		
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
		List<Object[]> lob =paymentService.getSumRecentMonth();
		
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
		List<Object[]> lob =paymentService.getSumRecentSixMonth();
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
		Double count=paymentService.getCurrentMonthSum();
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
			title="一周收费额";
			dataListtemp = paymentService.getSumRecentWeek();
		}else if(time.toString().equals("month")){
			title="一个月收费额";
			dataListtemp = paymentService.getSumRecentMonth();
		}else if(time.toString().equals("sixmonth")){
			title="六个月收费额";
			dataListtemp = paymentService.getSumRecentSixMonth();
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
			title="近一周收费额";
			dataListtemp = paymentService.getSumRecentWeek();
		}else if(time.toString().equals("month")){
			title="近一个月收费额";
			dataListtemp = paymentService.getSumRecentMonth();
		}else if(time.toString().equals("sixmonth")){
			title="近六个月收费额";
			dataListtemp = paymentService.getSumRecentSixMonth();
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
		public Map<String, Object> save(@Valid Payment payment) {
			Map<String, Object> result = new HashMap<String, Object>();
			payment.setCreatetime(new Date());
			paymentService.save(payment);
			result.put("result", "1");
			return result;
		}

		@RequestMapping(value = "update")
		@ResponseBody
		public Map<String, Object> update(@Valid Payment payment) {
			Map<String, Object> result = new HashMap<String, Object>();
			Payment oldpayment = paymentService.get(payment.getId());
			
			payment.setCreatetime(oldpayment.getCreatetime());
			paymentService.save(payment);
			result.put("result", "1");
			return result;
		}

		@RequestMapping(value = "/delete")
		@ResponseBody
		public Map<String, Object> delete(@RequestParam(value = "id") Long id) {
			Map<String, Object> result = new HashMap<String, Object>();
			paymentService.delete(id);
			result.put("result", "1");
			return result;
		}
}
