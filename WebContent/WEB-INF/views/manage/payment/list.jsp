<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<html>
<head>
<meta charset="utf-8" />
<title>白板程序</title>

</head>
<style type="text/css">
#contentTable td {
	WORD-WRAP: break-word;
}
</style> 
<body>
	<div class="hlrow">
		<a>前台业务</a> > <a class="nowpage"> 收费</a>
	</div>
	<div class="hltable">
		
		<div class="hltablecontent">
			<form id="searchform" action="">
				<div class="hltoolbar">
					<div class="hlrowsec">
						<div class="hltd hltd33">
							<div class="tdleft">
								<label>学员姓名</label>
							</div>
							<div class="tdright">
								<input id="name" name="search_LIKE_name" class="hlinput" />
							</div>
						</div>
						<div class="hltd hltd33">
							<div class="tdleft">
								<label>咨询课程</label>
							</div>
							<div class="tdright">
								<select class="hlselect" id="course" name="search_EQ_course.id">
									<option value="0">全部</option>
									<c:forEach var="course" items="${courses}">
										<option value="${course.id}">${course.name}</option>
									</c:forEach>
								</select>
							</div>
						</div>
						<div class="hltd hltd33">
							<div class="tdleft">
								<label>责任人</label>
							</div>
							<div class="tdright">
								<select class="hlselect" id="sales"
									name="search_EQ_sales.id">
									<option value="0">全部</option>
									<c:forEach var="sales" items="${saless}">
										<option value="${sales.id}">${sales.realname}</option>
									</c:forEach>
								</select>
							</div>
						</div>
						<div class="hltd hltd25" style="text-align: right;">
							<a class="actionbtn bluebtn searchbtn">搜索</a> <a
								class="actionbtn lightblue resetbtn">重置</a>
						</div>
					</div>
					<!-- <div class="hlrowsec">
						<div class="hltd hltd25"></div>
						<div class="hltd  hltd45"></div>
						<div class="hltd hltd25" style="text-align: right;">
							<a class="actionbtn bluebtn searchbtn">查询</a> <a
								class="actionbtn lightblue resetbtn">重置</a>
						</div>
					</div> -->
				</div>
			</form>

		</div>


	</div>

	<div class="hltable">
		<div class="hltitle">
			<div class="titlename">列表</div>
			<div class="titleactions">
				<a class="actionbtn bluebtn newbtn">新增</a> <a
					class="actionbtn redbtn deletebtn">删除</a>
			</div>
		</div>
		<div class="hltablecontent"> 

			<table border="0" cellpadding="2" cellspacing="0">
				<thead>
					<tr>
						<th class="hlsort">序号</th>
						<th>姓名</th>
						<th>性别</th>
						<th style="width: 150px;">手机号码</th>
						<th style="width: 150px;">交费日期</th>
						<th style="width: 120px;">报名课程</th>
						<th style="width: 150px;">交费金额</th>
						<th style="width: 80px;">业绩归属人</th>
						<th style="width: 80px;">交费类型</th>
						<th style="width: 150px;">备注</th>
						<th style="width: 150px;">操作</th>
					</tr>
				</thead>
				<tbody id="list">

				</tbody>
			</table>
		</div>
		<div class="hltablefoot">
			<div id="Pagination" class="pagination"></div>
		</div>
	</div>
	


	<!-- 新增收费面板 -->

	<div class="ymyframe">
		<div class="ymyframetitle">新增/修改</div>
		<div class="ymyframecontent">
			<form>
				<div class="formtitle">
					<label class="rowlabel">学员信息</label>
				</div>
				<div class="ymyformrow">
					<div class="formlabel">
						<font class="btfont">*</font>姓名
					</div>
					<div class="frominput">
						<input class="ymyinput" type="" name="">
					</div>
					<div class="errortip">输入错误</div>
				</div>
				<div class="ymyformrow">
					<div class="formlabel">
						<font class="btfont">*</font>手机号
					</div>
					<div class="frominput">
						<input class="ymyinput" type="" name="">
					</div>
					<div class="errortip">输入错误</div>
				</div>
				

			</form>
		</div>
		<div class="ymyframeaction"></div>
	</div>




	<div class="hlframe">
		<div id="titlediv" class="hlframetitle">新增/修改收费</div>
		<div class="hlframecontent">
			<form action="">
				<input type="hidden" name="id" id="id" />
				<div class="hlformrow">
					<div class="formlabel">
						 学生姓名
					</div>
					<div class="frominput">
						<input class="hlinput" id="name" name="name" required="required"
							maxlength="20" />
					</div>
					<div class="errortip">输入错误</div>
				</div>

				<div class="hlformrow">
					<div class="formlabel">
						性别
					</div>
					<div class="frominput">
						<select class="hlselect" id="sex" name="sex">
							<option value="1">男</option>
							<option value="2">女</option>
						</select>
					</div>
				</div>

				<div class="hlformrow">
					<div class="formlabel">
						 手机号
					</div>
					<div class="frominput">
						<input class="hlinput" id="phone" name="phone" required="required"
							maxlength="20" />
					</div>
					<div class="errortip">输入错误</div>
				</div>

				<div class="hlformrow">
					<div class="formlabel">
						报名课程
					</div>
					<div class="frominput">
						<select class="hlselect" id="course" name="course">
							<option value="0">无</option>
							<c:forEach var="course" items="${courses}">
								<option value="${course.id}">${course.name}</option>
							</c:forEach>
						</select>
					</div>
					<div class="errortip">输入错误</div>
				</div>

				<div class="hlformrow">
					<div class="formlabel">
						 交费金额
					</div>
					<div class="frominput">
						<input class="hlinput" id="price" name="price" required="required"
							maxlength="20" />
					</div>
					<div class="errortip">输入错误</div>
				</div>

				<div class="hlformrow">
					<div class="formlabel">
						业绩归属人
					</div>
					<div class="frominput">
						<select class="hlselect" id="sales" name="sales">
							<option value="0">无</option>
							<c:forEach var="sales" items="${saless}">
								<option value="${sales.id}">${sales.realname}</option>
							</c:forEach>
						</select>
					</div>
					<div class="errortip">输入错误</div>
				</div>
				
				<div class="hlformrow">
					<div class="formlabel">
						交费类型
					</div>
					<div class="frominput">
						<select class="hlselect" id="type" name="type">
							<option value="0">未知</option>
							<option value="1">新签</option>
							<option value="2">续费</option>
							<option value="3">扩科</option>
						</select>
					</div>
				</div>

				<div class="hlformdiv">
					<div class="formlabel">备注</div>
					<div class="frominput">
						<textarea rows="" cols="" id="note" name="note"></textarea>

					</div>
					<div class="errortip">输入错误</div>
				</div>

			</form>
		</div>
		<div class="hlframeaction">
			<a class="actionbtn bluebtn savebtn">保存</a> <a
				class="actionbtn redbtn cancelbtn">取消</a>
		</div>
	</div>





	<link rel="stylesheet"
		href="${ctx}/static/jquery-ui-1.12.0.custom/jquery-ui.min.css">
	<link rel="stylesheet" href="${ctx}/static/payment/list.css">
	<script src="${ctx}/static/jquery-ui-1.12.0.custom/jquery-ui.min.js"></script>
	<script src="${ctx}/static/jquery-ui-1.12.0.custom/datepicker-zh.js"></script>
	<script type="text/javascript">
		var pagin;
		var dataurl = "${ctx}/manage/payment/paymentlist";
		$(function() {
			init();
		});

		function init() {
			$("#stime").datepicker({
				"dateFormat" : "yy-mm-dd"
			}, $.datepicker.regional["zh"]);
			$("#etime").datepicker({
				"dateFormat" : "yy-mm-dd"
			}, $.datepicker.regional["zh"]);
			var opt = {
				callback : pageselectCallback,
				items_per_page : 10,
			};
			pagin = $("#Pagination").pagination(dataurl, opt);

			$(".searchbtn").bind("click", function() {
				var params=getMap(decodeURIComponent($("#searchform").serialize(),true));
				//var params = getMap($("#searchform").serialize());
				pagin = $("#Pagination").pagination(dataurl, opt, params);
			});

			$(".resetbtn").bind("click", function() {
				resetAll();
			});
			$(".newbtn").bind("click", function() {
				shownew();
			});

			$(".savebtn").bind("click", function() {
				check();
			});
			$(".cancelbtn").bind("click", function() {
				hidenew();
			});
		}
		function pageselectCallback(data) {
			$("#list").empty();
			for (var i = 0; i < data.content.length; i++) {
				var flagtype="";
				if(data.content[i].type==1){
					flagtype="新签";
				}else if(data.content[i].type==2){
					flagtype="续费";
				}else if(data.content[i].type==3){
					flagtype="扩科";
				}
				$("#list")
						.append(
								'<tr><td>'
										+ (i + 1)
										+ '</td><td>'
										+ data.content[i].name
										+ '</td><td>'
										+ (data.content[i].sex == 1 ? "男"
												: "女")
										+ '</td><td>'
										+ data.content[i].phone
										+ '</td><td>'
										+ (data.content[i].createtime == null ? "-"
												: formattime(data.content[i].createtime))
										+ '</td><td>'
										+ data.content[i].course.name
										+ '</td><td>'
										+ data.content[i].price
										+ '</td><td>'
										+ data.content[i].sales.name
										+ '</td><td>'
										+ flagtype
										+ '</td><td>'
										+ (data.content[i].note== null ? "-"
												: data.content[i].note)
										+ '</td><td><a aid="'
										+ data.content[i].id
										+ '" name="'
										+ data.content[i].name
										+ '" sex="'
										+ data.content[i].sex
										+ '" phone="'
										+ data.content[i].phone 
										+ '" createtime="'
										+ (data.content[i].createtime == null ? "-"
												: formattime(data.content[i].createtime))
										+ '" coursename="'
										+ data.content[i].course.id
										+ '" price="'
										+ data.content[i].price
										+ '" salesname="'
										+ data.content[i].sales.id
										+ '" type="'
										+ data.content[i].type
										+ '" note="'
										+ (data.content[i].note== null ? "-"
												: data.content[i].note)
										+ '" class="actiona modify">修改</a><a aid="'+data.content[i].id+'" class="actiona deletea">删除</a></td></tr>');
				/* setTimeout('window.parent.iFrameHeight();',100); */
			}
			/* initcheckbox("#list"); */
			initbableaction();
		}
		function initbableaction() {
			$(".modify").bind(
					"click",
					function() {
						showmodify($(this).attr("aid"), $(this).attr("name"), 
								$(this).attr("sex"), $(this).attr("phone"),
								$(this).attr("coursename"),
								$(this).attr("price"),$(this).attr("salesname"),
								$(this).attr("type"),$(this).attr("note"));
					});
			$(".deletea").bind("click", function() {
				showdelete($(this).attr("aid"));
			});
		}
		function resetAll() {
			$("#searchform #name").val("");
			$("#searchform #course").val("0");
			$("#searchform #sales").val("0");
		}

		//展示添加框
		function shownew() {
			$(".backcover").show();
			
			
			
			$("#titlediv").html("新增收费");
			$(".hlframe").showhlframe();
			$(".hlframe .hlinput")[0].focus();
			actiontype = "save";

			
		}
		function showmodify(id, name, sex, phone,coursename,price,salesname,type,note) {
			$(".backcover").show();
			$(".hlframe #id").val(id);
			$(".hlframe #name").val(name);
			$(".hlframe #sex").val(sex);
			$(".hlframe #phone").val(phone);
			$(".hlframe #course").val(coursename);
			$(".hlframe #price").val(price);
			$(".hlframe #sales").val(salesname);
			$(".hlframe #type").val(type);
			$(".hlframe #note").val(note);
			$(".errortip").hide();
			$("#titlediv").html("修改收费");
			$(".hlframe").showhlframe();
			$(".hlframe .hlinput")[0].focus();
			actiontype = "update";
		}

		function hidenew() {
			$(".backcover").hide();
			$(".hlframe").hide();
		}

		function check() {
			var param = getMap($(".hlframe form").serialize());
			if (actiontype == "save") {
				if (param.name == "") {
					showerror("name", "不能为空");
					return;
				} else {
					hideerror("name");
				}
				if (param.phone == "") {
					showerror("phone", "不能为空");
					return;
				} else {
					hideerror("phone");
				}
				if (param.price == "") {
					showerror("price", "不能为空");
					return;
				} else {
					hideerror("price");
				}
			}
			
			submit();
		}
		function submit() {
			var saveurl = actiontype == "save" ? "${ctx}/manage/payment/save"
					: "${ctx}/manage/payment/update";
			var options = {
				type : 'post',
				url : saveurl,
				dataType : 'json',
				success : function(ret) {
					if (ret.result == "1") {
						pagin.refresh();
						hidenew();
					}
				},
				error : function(ret) {

				}
			};
			$('.hlframe form').ajaxForm(options);
			$('.hlframe form').submit();
		}

		function showdelete(id) {
			showConfirm("是否要删除所选记录", function() {
				dodelete(id);
			});
		}
		function dodelete(id) {
			$.post('${ctx}/manage/payment/delete', {
				"id" : id
			}, function(data) {
				if (data.result == "1") {
					pagin.refresh(1);
					showAlert("删除成功");
				} else {
					showAlert("删除失败");
				}
			});
		}
	</script>
</body>
</html>