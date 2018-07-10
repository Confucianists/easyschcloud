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
		<a>咨询管理</a> > <a class="nowpage"> 转化成功学员</a>
	</div>
	
	<div class="hltable">
		<!-- <div class="hltitle">
			<div class="titlename">搜索</div>
		</div> -->
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
						<th style="width: 50px;">序号</th>
						<th style="width: 14%;">姓名</th>
						<th style="width: 6%;">性别</th>
						<th style="width: 10%;">手机号码</th>
						<th style="width: 14%;">咨询日期</th>
						<th style="width: 6%;">意向度</th>
						<th style="width: 10%;">咨询课程</th>
						<th style="width: 10%;">责任人</th>
						<th style="width: 10%;">转化状态</th>
						<th style="width: 10%;">备注</th>
						<th style="width: 10%;">操作</th>
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
	

	<div class="hlframe">
		<div class="hlframetitle">新增/修改咨询学员</div>
		<div class="hlframecontent">
			<form action="">
				<input type="hidden" name="id" id="id" />
				<div class="hlformrow">
					<div class="formlabel">
						<font class="btfont">*</font> 学生姓名
					</div>
					<div class="frominput">
						<input class="hlinput" id="name" name="name" required="required"
							maxlength="20" />
					</div>
					<div class="errortip">输入错误</div>
				</div>

				<div class="hlformrow">
					<div class="formlabel">
						<font class="btfont">*</font> 手机号
					</div>
					<div class="frominput">
						<input class="hlinput" id="phone" name="phone" required="required"
							maxlength="20" />
					</div>
					<div class="errortip">输入错误</div>
				</div>

				<div class="hlformrow">
					<div class="formlabel">
						<font class="btfont">*</font>性别
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
						<font class="btfont">*</font>意向度
					</div>
					<div class="frominput">
						<select class="hlselect" id="per" name="per">
							<option value="0">未知</option>
							<option value="1">低</option>
							<option value="2">中</option>
							<option value="3">高</option>
						</select>
					</div>
				</div>

				<div class="hlformrow">
					<div class="formlabel">
						<font class="btfont">*</font>咨询课程
					</div>
					<div class="frominput">
						<select class="hlselect" id="course" name="course.id">
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
						<font class="btfont">*</font>责任人
					</div>
					<div class="frominput">
						<select class="hlselect" id="sales" name="sales.id">
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
						<font class="btfont">*</font>转化状态
					</div>
					<div class="frominput">
						<select class="hlselect" id="status" name="status">
							<option value="0">跟进中</option>
							<option value="1">转化成功</option>
						</select>
					</div>
				</div>

				<!--<div class="hlformrow">
					<div class="formlabel">
						<font class="btfont">*</font>型号
					</div>
					<div class="frominput">
						<select class="hlselect" id="devicemodel" name="deviceModel.id">
							<c:forEach var="dm" items="${dms}">
								<option value="${dm.id}">${dm.name}</option>
							</c:forEach>
						</select>
					</div>
					<div class="errortip">输入错误</div>
				</div>-->

				<!--<div class="hlformrow">
					<div class="formlabel">
						<font class="btfont">*</font> MAC地址
					</div>
					<div class="frominput">
						<input class="hlinput" id="mac" name="mac" required="required"
							maxlength="20" />
					</div>
					<div class="errortip">输入错误</div>
				</div>-->

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
	<script src="${ctx}/static/jquery-ui-1.12.0.custom/jquery-ui.min.js"></script>
	<script src="${ctx}/static/jquery-ui-1.12.0.custom/datepicker-zh.js"></script>
	<script type="text/javascript">
		var pagin;
		var dataurl = "${ctx}/manage/will/willoklist";
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
				var willper="";
				var willstatus="";
				if(data.content[i].per==0){
					willper="未知";
				}else if(data.content[i].per==1){
					willper="低";
				}else if(data.content[i].per==2){
					willper="中";
				}else if(data.content[i].per==3){
					willper="高";
				}
				if(data.content[i].status==1){
					willstatus="转化成功";
				}else if(data.content[i].status==0){
					willstatus="跟进中";
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
										+ willper
										+ '</td><td>'
										+ data.content[i].course.name
										+ '</td><td>'
										+ data.content[i].sales.name
										+ '</td><td>'
										+ willstatus
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
										+ '" per="'
										+ willper
										+ '" course="'
										+ data.content[i].course.id
										+ '" salesname="'
										+ data.content[i].sales.id
										+ '" status="'
										+ willstatus
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
								$(this).attr("per"),
								$(this).attr("course"),$(this).attr("salesname"),
								$(this).attr("status"),$(this).attr("note"));
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
			$(".hlframe input").val("");
			$(".hlframe #name").parent().parent().show()
			//$(".hlframe select").val(1);
			$(".errortip").hide();
			$(".hlframe").showhlframe();
			$(".hlframe .hlinput")[0].focus();
			actiontype = "save";
		}
		function showmodify(id, name, sex, phone,per,course,salesname,status,note) {
			$(".backcover").show();
			$(".hlframe #id").val(id);
			$(".hlframe #name").val(name);
			$(".hlframe #sex").val(sex);
			$(".hlframe #phone").val(phone);
			$(".hlframe #per").val(per);
			$(".hlframe #course").val(course);
			$(".hlframe #sales").val(salesname);
			$(".hlframe #status").val(status);
			$(".hlframe #note").val(note);
			$(".errortip").hide();
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
			}
			if (param.phone == "") {
				showerror("phone", "不能为空");
				return;
			} else {
				hideerror("phone");
			}
			submit();
		}
		function submit() {
			var saveurl = actiontype == "save" ? "${ctx}/manage/will/save"
					: "${ctx}/manage/will/update";
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
			$.post('${ctx}/manage/will/delete', {
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