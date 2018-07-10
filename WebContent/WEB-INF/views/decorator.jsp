<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ page import="com.ymy.entity.*"%>
<%
User user = (User) request.getSession().getAttribute("user");
%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />

<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<link rel="shortcut icon"
	href="${ctx}/static/homepage/image/favicon.ico" />
<link rel="stylesheet" href="${ctx}/static/manage/default/css/index.css">
<link rel="stylesheet"
	href="${ctx}/static/manage/default/css/jquery-accordion-menu.css">
<title>aaa</title>
</head>
<%@ include file="base.jsp"%>
<script src="${ctx}/static/manage/default/js/jquery-accordion-menu.js"
	type="text/javascript"></script>
<body>

	<div class="body">
		<!-- head  -->
		<div class="head">
			<div class="headlogo">
				<!-- <img src="${ctx}/static/manage/images/eslogo.png"> -->
			</div>
			<div class="headtitle"><!-- 校易云培训学校管理系统 --></div>
			<div class="statediv">
				<span class="realname">欢迎&nbsp;<%=user.getRealname()%></span> 
				<a id="editpwd" class="editpwdbtn">修改密码</a>
				<a class="logoutbtn" href="${ctx}/logout">退出</a>
				
			</div>
		</div>
		<!-- head end -->

		<!-- content  -->
		<div class="content">
			<div id="jquery-accordion-menu"
				class="menutree jquery-accordion-menu blue">
				<!-- <div class="jquery-accordion-menu-header" id="form"></div> -->
				<ul id="demo-list">

					<c:forEach items="${menuList}" var="one">
						<c:forEach items="${one}" var="entry">
							<li class=""><c:if test="${fn:length(entry.value) != 0}">
									<a href="#"><span class="menu-logo"><i
											style="background: url('${ctx}/static/manage/images/${entry.key.logo}') no-repeat;"
											class="fa fa-suitcase"></i></span>${entry.key.name} </a>
									<ul class="submenu">
										<c:forEach items="${entry.value}" var="two">
											<li><a href="${ctx}${two.url}">${two.name}<span></span></a></li>
										</c:forEach>
									</ul>
								</c:if> <c:if test="${fn:length(entry.value) == 0}">
									<a hashdata="#${entry.key.url}" href="${ctx}${entry.key.url}"><span class="menu-logo"><i
											style="background: url('${ctx}/static/manage/images/${entry.key.logo}') no-repeat;"
											class="fa fa-suitcase"></i></span>${entry.key.name}</a>
								</c:if></li>
						</c:forEach>
					</c:forEach>

				</ul>

			</div>


			<div class="contentbody">
				<sitemesh:write property='body' />
			</div>
		</div>
		<!-- content end -->

		<!-- foot 
		<div class="foot">foot</div>
		 -->
		<!-- foot end -->

		<!-- 提示确认框 -->
		<div class="backcover"></div>
		<div class="confirmdiv">
			<div class="confirmtitle"></div>
			<div class="confirmcontent"></div>
			<div class="confirmaction">
				<a class="actionbtn bluebtn confitmok" onclick="callChild()">确定</a>
				<a class="actionbtn redbtn confitmcancel">取消</a>
			</div>
		</div>

		<!-- 提示框 -->
		<div class="tipdiv">
			<div class="tiptitle"></div>
			<div class="tipcontent"></div>
			<div class="tipaction">
				<a class="actionbtn bluebtn tipok" onclick="hideAlert()">确定</a>
			</div>
		</div>

		<!-- 修改密码弹出框 -->
	<div id="editdiv" class="formdiv">
		<div class="formtitle">修改密码</div>
		<div class="formcontent" style="height: 72px !important;">
			<form action="">
				<div class="formrow">
					<div class="rowlabel">原密码</div>
					<div class="rowinput">
						<input id="oldpassword">
					</div>
				</div>

				<div class="formrow">
					<div class="rowlabel">新密码</div>
					<div class="rowinput">
						<input id="newpassword">
					</div>
				</div>
			</form>
		</div>
		<div class="errortip1"></div>
		<div class="formfoot">
			<a class="actionbtn bluebtn" id="savepwd">确定</a> 
			<a class="actionbtn redbtn" id="cancelpwd">取消</a>
		</div>
	</div>



	</div>
	<script>
		jQuery(document).ready(function() {
			jQuery("#jquery-accordion-menu").jqueryAccordionMenu();
			$("#demo-list li").click(function() {
				$("#demo-list li.active").removeClass("active")
				$(this).addClass("active");
			});
			setAct();

			//add by ymy
			$("#editpwd").bind("click",function(){
				showeditpwd();
			});
			$("#savepwd").bind("click",function(){
				check();
			});
			$("#cancelpwd").bind("click",function(){
				hideeditpwd();
			});
			
		});

		//设置菜单焦点
		function setAct(hash) {
			var hash = window.location.pathname;
			$("#demo-list").children().each(function() {
				if ($(this).children(0).attr("href") == hash) {
					$(this).addClass("active");
					return;
				}

			});
			$(".submenu li").each(
					function() {
						if ($(this).children(0).attr("href") == hash) {
							$(this).addClass("act");
							$(this).parent().css("display", "block");
							$(this).parent().prev().addClass(
									"submenu-indicator-minus");
							$(this).parent().parent().addClass("active");
							return;
						}
					});
		}

		//确认框操作
		var fntemp;
		var fncanceltemp;
		function showConfirm(confirmMsg, fn, title, fncancel) {
			$(".confirmcontent").html(confirmMsg);
			$('.confirmdiv').show();
			$(".backcover").show();
			if (title) {
				$(".confirmtitle").html(title);
			} else {
				$(".confirmtitle").html("提示");
			}
			//$(".confitmok").attr("onclick", "callChild()");
			fntemp = fn;
			if (fncancel) {
				fncanceltemp = fncancel;
				$(".confitmcancel").attr("onclick", "callCancel()");
			} else {
				$(".confitmcancel").attr("onclick", "hideconfirm()");
			}

		}
		function callChild() {
			$('.confirmdiv').hide();
			$(".backcover").hide();
			fntemp();
		}
		function callCancel() {
			$('.confirmdiv').hide();
			$(".backcover").hide();
			fncanceltemp();
		}
		function hideconfirm() {
			$('.confirmdiv').hide();
			$(".backcover").hide();
		}

		//提示框
		function showAlert(msg, tip) {
			$(".tipcontent").html(msg);
			$('.tipdiv').show();
			$(".backcover").show();
			if (tip) {
				$(".tiptitle").html(tip);
			} else {
				$(".tiptitle").html("提示");
			}
		}
		function hideAlert() {
			$('.tipdiv').hide();
			$(".backcover").hide();
		}


		//弹出密码修改框
		function showeditpwd(){
			$('#editdiv').showhlframe();
			$(".backcover").show();
		}
		function hideeditpwd(){
			$('#editdiv').hide();
			$(".backcover").hide();
			$("#oldpassword").val("");
			$("#newpassword").val("");
			$(".errortip1").html("");
			$(".errortip1").hide();
		}

		function check(){
			if($("#oldpassword").val().trim()==""){
				$(".errortip1").html("原密码不能为空");
				$(".errortip1").show();
				return;
			}else{
				$(".errortip1").html("");
				$(".errortip1").hide();
			}
			if($("#newpassword").val().trim()==""){
				$(".errortip1").html("新密码不能为空");
				$(".errortip1").show();
				return;
			}else{
				$(".errortip1").html("");
				$(".errortip1").hide();
			}

				
			submit();
		}

		function submit(){
			var subinput=$("#oldpassword").val();
			var subnew=$("#newpassword").val();
			$.post("${ctx}/userlogin/editpwd",{inputpassword:subinput,newpassword:subnew},function(r){
				hideeditpwd();
				if(r.result==1){
					showAlert("密码修改成功");
				}else{
					showAlert("密码修改失败，原密码输入错误");
				}
			});
		}
		
	</script>

</body>
</html>