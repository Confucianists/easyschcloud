<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<html>
<head>
<meta charset="utf-8" />
<title>校易云</title>

</head>
<style type="text/css">
#contentTable td {
	WORD-WRAP: break-word;
}
</style> 
<body>
	<div class="hlrow">
		<a>我的学员</a><a class="nowpage"></a>
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
								<label>在读/结束</label>
							</div>
							<div class="tdright">
								<select class="hlselect" id="teacherdel" name="search_EQ_teacherdel">
									<option value="0">在读</option>
									<option value="1">已结束</option>
									
								</select>
							</div>
						</div>
						<div class="hltd hltd33">
							<div class="tdleft">
								<label>科目</label>
							</div>
							<div class="tdright">
								<select class="hlselect" id="course"
									name="search_EQ_course.id">
									<option value="0">全部</option>
									<c:forEach var="course" items="${courselist}">
										<option value="${course.id}">${course.name}</option>
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
						<th style="width: 150px;">学生姓名</th>
						<th style="width: 100px;">性别</th>
						<th style="width: 150px;">手机号</th>
						<th style="width: 150px;">已上课时</th>
						<th style="width: 80px;">剩余课时</th>
						<th style="width: 200px;">成绩单</th>
						<th style="width: 150px;">课程表现</th>
						<th style="width: 150px;">课消</th>
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
	

	

	<!-- 新增成绩弹出框 -->
	<div class="addscorediv">
		<div class="addscoretitle">新增成绩</div>
		<div class="addscorecontent">
			<form action="">
				<input type="hidden" name="id" id="id" />
				<input type="hidden" name="studentid" id="studentid1" />
				<div class="formrow">
					<div class="rowlabel">姓名</div>
					<div class="rowinput">
						<input type="" name="" id="scorename" readonly="readonly">
					</div>
				</div>

				<div class="formrow">
					<div class="rowlabel">考试名称</div>
					<div class="rowinput">
						<input type="" name="name" id="name">
					</div>
				</div>

				<div class="formrow">
					<div class="rowlabel">考试分数</div>
					<div class="rowinput">
						<input type="" name="score" id="score">
					</div>
				</div>

				<div class="formrow">
					<div class="rowlabel">备注</div>
					<div class="rowinput">
						<textarea rows="" cols="" id="note" name="note">
						</textarea>
					</div>
				</div>
			</form>
		</div>
		<div class="addscorefoot">
			<a class="actionbtn bluebtn" id="savescorebtn">保存</a> <a
				class="actionbtn redbtn" id="cancelscorebtn">取消</a>
		</div>
	</div>

	<!-- 新增表现弹出框 -->
	<div class="addstudydiv">
		<div class="addstudytitle">新增课堂表现</div>
		<div class="addstudycontent">
			<form action="">
				<input type="hidden" name="id" id="id" />
				<input type="hidden" name="studentid" id="studentid2" />
				<div class="formrow">
					<div class="rowlabel">姓名</div>
					<div class="rowinput">
						<input type="" name="" id="studyname" readonly="readonly">
					</div>
				</div>

				<div class="formrow">
					<div class="rowlabel">备注</div>
					<div class="rowinput">
						<textarea rows="" cols="" id="note" name="note">
						</textarea>
					</div>
				</div>
			</form>
		</div>
		<div class="addstudyfoot">
			<a class="actionbtn bluebtn" id="savestudybtn">保存</a> <a
				class="actionbtn redbtn" id="cancelstudybtn">取消</a>
		</div>
	</div>
	
	<!-- 新增课消弹出框 -->
	<div class="addcheckindiv">
		<div class="addcheckintitle">新增课消</div>
		<div class="addcheckincontent">
			<form action="">
				<input type="hidden" name="id" id="id" />
				<input type="hidden" name="studentid" id="studentid3" />
				<div class="formrow">
					<div class="rowlabel">姓名</div>
					<div class="rowinput">
						<input type="" name="" id="checkinname" readonly="readonly">
					</div>
				</div>
				<div class="formrow">
					<div class="rowlabel">课消次数</div>
					<div class="rowinput">
						<input type="" name="times" id="times">
					</div>
				</div>
				<div class="formrow">
					<div class="rowlabel">时间</div>
					<div class="rowinput">
						<input name="" id="checkintime">
					</div>
				</div>
				<div class="formrow">
					<div class="rowlabel">备注</div>
					<div class="rowinput">
						<textarea rows="" cols="" id="checkinnote" name="note">
						</textarea>
					</div>
				</div>
			</form>
		</div>
		<div class="addcheckinfoot">
			<a class="actionbtn bluebtn" id="savecheckinbtn">保存</a> <a
				class="actionbtn redbtn" id="cancelcheckinbtn">取消</a>
		</div>
	</div>

	<!-- 以往成绩弹出框 -->
	<div class="scorecontentdiv">
		<div class="scoredivtitle">成绩列表/<label id="studentname"></label><a class="canbtn" id="scorelistcancel">取消</a></div>
		<div class="scorecontent">
				<table>
					<thead>
						<th style="width: 10%;">序号</th>
						<th style="width: 25%;">名称</th>
						<th style="width: 10%;">分数</th>
						<th style="width: 20%;">时间</th>
						<th style="width: 35%;">备注</th>
					</thead>
					<tbody id="sclist">
					</tbody>
				</table>
		</div>
	</div>

	<!-- 以往表现弹出框 -->
	<div class="studycontentdiv">
		<div class="studydivtitle">成绩列表/<label id="stname"></label><a class="canbtn" id="studylistcancel">取消</a></div>
		<div class="studycontent">
				<table>
					<thead>
						<th style="width: 10%;">序号</th>
						<th style="width: 50%;">备注</th>
						<th style="width: 20%;">时间</th>
						<th style="width: 20%;">备注人</th>
					</thead>
					<tbody id="stlist">
					</tbody>
				</table>
		</div>
	</div>

	<!-- 以往课消弹出框 -->
	<div class="checkincontentdiv">
		<div class="checkindivtitle">成绩列表/<label id="chistname"></label><a class="canbtn" id="checkinlistcancel">取消</a></div>
		<div class="checkincontent">
				<table>
					<thead>
						<th style="width: 10%;">序号</th>
						<th style="width: 50%;">课消数</th>
						<th style="width: 20%;">时间</th>
						<th style="width: 20%;">备注</th>
					</thead>
					<tbody id="chilist">
					</tbody>
				</table>
		</div>
	</div>




	<link rel="stylesheet"
		href="${ctx}/static/jquery-ui-1.12.0.custom/jquery-ui.min.css">
	<link rel="stylesheet" href="${ctx}/static/manage/teacher/css/studentlist.css">   
	<script src="${ctx}/static/jquery-ui-1.12.0.custom/jquery-ui.min.js"></script>
	<script src="${ctx}/static/jquery-ui-1.12.0.custom/datepicker-zh.js"></script>
	<script type="text/javascript">
		var pagin;
		var dataurl = "${ctx}/manage/teacher/studentlist";
		$(function() {
			init();
		});

		function init() {
			$("#checkintime").datepicker({
				"dateFormat" : "yy-mm-dd"
			}, $.datepicker.regional["zh"]);
			
			var opt = {
				callback : pageselectCallback,
				items_per_page : 10,
			};
			var firstparams=getMap(decodeURIComponent($("#searchform").serialize(),true));
			pagin = $("#Pagination").pagination(dataurl, opt,firstparams);

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
				var remain=(data.content[i].course.totalcount-data.content[i].usecount)<0?0:(data.content[i].course.totalcount-data.content[i].usecount);
				
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
										+ data.content[i].usecount
										+ '</td><td>'
										+ remain
										+ '</td><td><a aid="'+data.content[i].id
										+'" class="actiona newscore">新增</a><a aid="'+data.content[i].id
										+'" class="actiona scorelist">以往</a>'
										+ '</td><td><a aid="'+data.content[i].id
										+'" class="actiona newstudy">新增</a><a aid="'
										+data.content[i].id+'" class="actiona studylist">以往</a>'
										+ '</td><td><a aid="'+data.content[i].id
										+'" class="actiona addcheckin">新增</a><a aid="'+data.content[i].id+'" class="actiona checkinlist">以往</a>'
										+ '</td><td>'
										+ '<a aid="'+data.content[i].id+'" class="actiona deletea">删除</a></td></tr>');
				/* setTimeout('window.parent.iFrameHeight();',100); */
			}
			/* initcheckbox("#list"); */
			initbableaction();
		}
		function initbableaction() {
			// $(".modify").bind(
			// 		"click",
			// 		function() {
			// 			// showmodify($(this).attr("aid"), $(this).attr("number"), 
			// 			// 		$(this).attr("name"), $(this).attr("sex"),
			// 			// 		$(this).attr("phone"),
			// 			// 		$(this).attr("position"),$(this).attr("status"),
			// 			// 		$(this).attr("isaccount"),$(this).attr("note"));
			// 		});
			$(".deletea").bind("click", function() {
				showdelete($(this).attr("aid"));
			});
			$(".scorelist").bind("click", function() {
				showscorelist($(this).attr("aid"));
			});
			$(".studylist").bind("click", function() {
				showstudylist($(this).attr("aid"));
			});
			$(".newscore").bind("click", function() {
				shownewscore($(this).attr("aid"));
			});
			$("#savescorebtn").bind("click",function(r){
				checkscore();
			});
			$("#cancelscorebtn").bind("click",function(r){
				$(".backcover").hide();
				$(".addscorediv").hide();
			});
			$(".addcheckin").bind("click", function() {
				shownewcheckin($(this).attr("aid"));
			});
			$(".checkinlist").bind("click", function() {
				checkinlist($(this).attr("aid"));
			});
			
			$(".newstudy").bind("click", function() {
				shownewstudy($(this).attr("aid"));
			});
			$("#savestudybtn").bind("click",function(r){
				checkstudy();
			});
			$("#cancelstudybtn").bind("click",function(r){
				$(".backcover").hide();
				$(".addstudydiv").hide();
			});
			$("#savecheckinbtn").bind("click",function(r){
				checkcheckin();
			});
			$("#cancelcheckinbtn").bind("click",function(r){
				$(".backcover").hide();
				$(".addcheckindiv").hide();
			});
			
			
		}
		function resetAll() {
			$("#searchform #name").val("");
			$("#searchform #course").val("0");
			$("#searchform #teacherdel").val("0");
		}

		//展示添加框
		function shownew() {
			$(".backcover").show();
			
			
			
			$("#titlediv").html("新增员工");
			$(".hlframe").showhlframe();
			$(".hlframe .hlinput")[0].focus();
			actiontype = "save";

			
		}
		function showmodify(id, number, name, sex,phone,position,status,isaccount,note) {
			$(".backcover").show();
			$(".hlframe #id").val(id);
			$(".hlframe #number").val(number);
			$(".hlframe #name").val(name);
			$(".hlframe #sex").val(sex);
			$(".hlframe #phone").val(phone);
			$(".hlframe #position").val(position);
			$(".hlframe #status").val(status);
			$(".hlframe #isaccount").val(isaccount);
			$(".hlframe #note").val(note);
			$(".errortip").hide();
			$("#titlediv").html("修改员工");
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
				if (param.sex == "") {
					showerror("price", "不能为空");
					return;
				} else {
					hideerror("price");
				}
			}
			
			submit();
		}
		function submit() {
			var saveurl = actiontype == "save" ? "${ctx}/manage/teacher/save"
					: "${ctx}/manage/teacher/update";
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
			$.post('${ctx}/manage/teacher/teacherdel', {
				studentid : id
			}, function(r) {
				if (r.result == "1") {
					pagin.refresh(1);
					showAlert("删除成功");
				} else {
					showAlert("删除失败");
				}
			});
		}
		/**显示以往成绩*/
		function showscorelist(id){
			$("#sclist").empty();
			$.post("${ctx}/manage/teacher/scorereportlist",{studentid:id},function(r){
				if(r.result==1){
					$("#studentname").text(r.name);
					for(var i=0;i<r.data.length;i++){
						$("#sclist").append(
						'<tr><td>'
						+(i+1)
						+'</td><td>'
						+r.data[i].name
						+'</td><td>'
						+r.data[i].score
						+'</td><td>'
						+formattimeDate(r.data[i].createtime)
						+'</td><td>'
						+r.data[i].note
						+'</td></tr>'
						);
					}
					
				}
			});
			$(".backcover").show();
			$(".scorecontentdiv").showhlframe();
			$("#scorelistcancel").bind("click",function(){
				$(".backcover").hide();
				$(".scorecontentdiv").hide();
			});
		}
		/**显示以往表现*/
		function showstudylist(id){
			$("#stlist").empty();
			$.post("${ctx}/manage/teacher/studyreportlist",{studentid:id},function(r){
				if(r.result==1){
					$("#stname").text(r.name);
					for(var i=0;i<r.data.length;i++){
						$("#stlist").append(
						'<tr><td>'
						+(i+1)
						+'</td><td>'
						+r.data[i].note
						+'</td><td>'
						+ formattimeDate(r.data[i].createtime)
						+'</td><td>'
						+(r.data[i].tag==1?"老师":"学管师")
						+'</td></tr>'
						);
					}
					
				}
			});
			$(".backcover").show();
			$(".studycontentdiv").showhlframe();
			$("#studylistcancel").bind("click",function(){
				$(".backcover").hide();
				$(".studycontentdiv").hide();
			});
		}


		/**显示以往课消*/
		function checkinlist(id){
			$("#chilist").empty();
			$.post("${ctx}/manage/teacher/checkinlist",{studentid:id},function(r){
				if(r.result==1){
					$("#chistname").text(r.name);
					for(var i=0;i<r.data.length;i++){
						$("#chilist").append(
							'<tr><td>'
							+(i+1)
							+'</td><td>'
							+r.data[i].times
							+'</td><td>'
							+ formattimeDate(r.data[i].createtime)
							+'</td><td>'
							+r.data[i].note
							+'</td></tr>'
						);
					}
				}
				
			});

			$(".backcover").show();
			$(".checkincontentdiv").showhlframe();
			$("#checkinlistcancel").bind("click",function(){
				$(".backcover").hide();
				$(".checkincontentdiv").hide();
			});
		}

		/**显示新增成绩框*/
		function shownewscore(id){
			$(".addscorediv input").val("");
			$(".addscorediv textarea").val("");
			$("#studentid1").val(id);
			getstudentname1(id);
			$(".backcover").show();
			$(".addscorediv").showhlframe();
			
		}
		/**提交新增成绩*/
		function checkscore() {
			var param = getMap($(".addscorediv form").serialize());
			
				if (param.name == "") {
					showerror("name", "不能为空");
					return;
				} else {
					hideerror("name");
				}
				if (param.score == "") {
					showerror("score", "不能为空");
					return;
				} else {
					hideerror("score");
				}
			submitscore();
		}
		function submitscore() {
			var saveurl ="${ctx}/manage/teacher/addscorereport";
					
			var options = {
				type : 'post',
				url : saveurl,
				dataType : 'json',
				success : function(ret) {
					if (ret.result == "1") {
						$(".backcover").hide();
						$(".addscorediv").hide();
						showAlert("保存成功");
					}
				},
				error : function(ret) {

				}
			};
			$('.addscorediv form').ajaxForm(options);
			$('.addscorediv form').submit();
		}

		/**显示新增表现框*/
		function shownewstudy(id){
			$(".addstudydiv input").val("");
			$(".addstudydiv textarea").val("");
			$("#studentid2").val(id);
			getstudentname2(id);
			$(".backcover").show();
			$(".addstudydiv").showhlframe();
		}
		/**提交新增表现*/
		function checkstudy() {
			var param = getMap($(".addstudydiv form").serialize());
			
				if (param.note == "") {
					showerror("note", "不能为空");
					return;
				} else {
					hideerror("note");
				}
			submitstudy();
		}
		function submitstudy() {
			var saveurl ="${ctx}/manage/teacher/addstudyreport";
					
			var options = {
				type : 'post',
				url : saveurl,
				dataType : 'json',
				success : function(ret) {
					if (ret.result == "1") {
						$(".backcover").hide();
						$(".addstudydiv").hide();
						showAlert("保存成功");
					}
				},
				error : function(ret) {

				}
			};
			$('.addstudydiv form').ajaxForm(options);
			$('.addstudydiv form').submit();
		}

		/**获取学生名字*/
		function getstudentname1(stid){
			$.post("${ctx}/manage/teacher/getstudentname",{studentid:stid},function(r){
				$('#scorename').val(r.name);
			});
		}
		/**获取学生名字*/
		function getstudentname2(stid){
			$.post("${ctx}/manage/teacher/getstudentname",{studentid:stid},function(r){
				$('#studyname').val(r.name);
			});
		}
		/**获取学生名字*/
		function getstudentname3(stid){
			$.post("${ctx}/manage/teacher/getstudentname",{studentid:stid},function(r){
				$('#checkinname').val(r.name);
			});
		}

		/**弹出新增课消*/
		function shownewcheckin(id){
			$(".addcheckindiv input").val("");
			$(".addcheckindiv textarea").val("");
			$("#studentid3").val(id);
			getstudentname3(id);
			$(".backcover").show();
			$(".addcheckindiv").showhlframe();
		}
		/**检查新增课消*/
		function checkcheckin(){
			var param = getMap($(".addcheckindiv form").serialize());
			
				if (param.note == "") {
					showerror("note", "不能为空");
					return;
				} else {
					hideerror("note");
				}
			submitcheckin();
		}



		/**提交新增课消*/
		function submitcheckin(){
			var subtimes=$('#times').val();
			var subcreatetime=$('#checkintime').val();
			var substudentid=$('#studentid3').val();
			var subnote=$('#checkinnote').val();
			$.post("${ctx}/manage/teacher/addcheckin",{times:subtimes,createtime:subcreatetime,studentid:substudentid,note:subnote},function(r){
				if (r.result == "1") {
						$(".backcover").hide();
						$(".addcheckindiv").hide();
						showAlert("保存成功");
					}
			});

			
					
			
				
				
			
			
		}
		
	</script>
</body>
</html>