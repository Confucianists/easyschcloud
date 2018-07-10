<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page import="com.ymy.entity.*"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	User user = (User) request.getSession().getAttribute("user");
%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />

<!doctype html>
<html>
<head>
<link rel="shortcut icon"
	href="${ctx}/static/homepage/image/favicon.ico" />
<style type="text/css">
</style>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />

<title>登录</title>

<script src="static/jquery/jquery-1.9.1.min.js" type="text/javascript"></script>
<script type="text/javascript">
	$(document).ready(function() {
		$("#form-id").submit(function() {
			$("#error").html("");
			if ($("#telephone").val().trim() == "") {
				$("#error").html("请输入账号");
				return false;
			}
			if ($("#password").val().trim() == "") {
				$("#error").html("请输入密码");
				return false;
			}
		});
		$("#issave").val("0");
		$("#issave").bind("change", function() {
			if ($("#issave").val() == "0") {
				$("#issave").val("1");
				$(".checkbox i").css("background-position", "-76px -260px");
			} else {
				$("#issave").val("0");
				$(".checkbox i").css("background-position", "0px -260px");
			}
		});

		$(".inputdiv input").bind("focus", function() {
			$(".inputdiv").removeClass("inputdivfocus");
			$(this).parent().addClass("inputdivfocus");
		});
	});

	function forgetpd() {
		$("#tokenform").submit();
	}
</script>
<style type="text/css">
body {
	margin: 0;
	font-family: 'Microsoft YaHei';
	min-width: 800px;
}

.backdiv {
	background: url('${ctx}/static/manage/images/indexback.jpg') no-repeat
		center;
	min-width: 938px;
	width: 100%;
	height: 700px;
	background-size: cover;
}

.centerdiv {
	background-color: white;
	border-radius: 10px;
	left: 50%;
	margin-left: 143px;
	padding: 15px 15px;
	position: absolute;
	top: 220px;
	width: 400px;
	box-shadow: 3px 9px 33px 2px;
}

.topdiv {
	height: 50px;
	margin-top: 25px;
}

.bottomdiv {
	text-align: center;
	color: #ff5e30;
	margin-top: 8px;
}

input {
	border: medium none;
	box-shadow: inset 0 0 0 1000px #fff;;
	font-size: 20px;
	height: 38px;
	margin-left: 0;
	outline: none;
	width: 99%;
}

.submitbtn {
	background-color: #3e82da;
	border: 0 none;
	border-radius: 3px;
	color: white;
	cursor: pointer;
	font-size: 20px;
	width: 100%;
	height: 40px;
}

.headtitle {
	background: rgba(0, 0, 0, 0)
		url("${ctx}/static/manage/images/indextitle.png") no-repeat scroll
		215px 30px;
	height: 123px;
	width: 100%;
}

.dltitle {
	border-bottom: 1px solid #aaaaaa;
	font-size: 25px;
	height: 50px;
	line-height: 45px;
	padding-left: 40px;
	color: #3e82da;
}

.dltitle p {
	margin-left: 60px;
}

.formdiv {
	width: 100%;
	margin-bottom: 40px;
}

.formrow {
	margin-top: 30px;
	text-align: center;
	width: 100%;
}

.inputdiv {
	border: 1px solid #e8e8e8;
	height: 80%;
	margin: auto;
	padding: 3px 0 3px 52px;
	position: relative;
	width: 75%;
	border-radius: 5px;
}

.inputdivfocus {
	border-color: #3e82da !important;
}

.odiv {
	height: 80%;
	margin: auto;
	padding-left: 25px;
	padding-right: 25px;
	text-align: left;
	color: #464646;
}

.namei {
	background: rgba(0, 0, 0, 0)
		url("${ctx}/static/manage/images/indexname.png") no-repeat scroll
		center center;
	display: inline-block;
	height: 18px;
	left: 18px;
	position: absolute;
	top: 14px;
	width: 18px;
}

.passwordi {
	background: url('${ctx}/static/manage/images/indexpassword.png')
		no-repeat center;
	display: inline-block;
	position: absolute;
	top: 12px;
	width: 18px;
	height: 18px;
	left: 18px;
}

.checkbox {
	width: 19px;
	cursor: pointer;
}

.checkbox i {
	background-image: url("${ctx}/static/manage/images/sprite.png");
	background-position: 0 -260px;
	display: inline-block;
	height: 20px;
	margin-top: -2px;
	vertical-align: middle;
	width: 20px;
}

.forget {
	margin-left: 135px;
	color: #848585;
	cursor: pointer;
}

.forget:hover {
	color: #626262;
}

.foot {
	padding: 0;
	width: 423px;
	margin: 10px auto 0px auto;
	color: #7a7a7a;
	padding: 35px 0 10px 0;
	line-height: 12px;
	text-align: center;
}

.nopass {
	float: right;
	text-decoration: none;
	color: #898989;
}

#error {
	text-align: left;
	color: red;
	font-size: 15px;
	width: 75%;
	margin-left: 30px;
	margin-top: 10px;
}
</style>
</head>
<body>
	<div class="headtitle"></div>
	<div id="filldiv" class="backdiv"></div>
	<div class="centerdiv">
		<div class="dltitle">登录</div>
		<div class="formdiv">
			<form id="form-id" action="${ctx}/managelogin" method="post"
				id="loginForm">
				<div class="formrow">
					<div class="inputdiv">
						<i class="namei"></i> <input type="text" value=""
							placeholder="请输入用户名" name="telephone" id="telephone" />
					</div>
				</div>
				<div class="formrow">
					<div class="inputdiv">
						<i class="passwordi"></i><input type="password"
							placeholder="请输入密码" name="password" id="password" value="" />
					</div>
				</div>
				<div id="error">
					<c:if test="${message!=null}">${message}</c:if>
				</div>
				<div class="formrow">
					<div class="odiv">
						<!-- 
						<label class="checkbox"><i></i><input type="checkbox"
							id="issave" name="issave" style="display: none">记住账号密码</label>
							 -->
						<!-- 
							<label
							class="forget" onclick="forgetpd()">忘记密码？</label>
							 -->
					</div>
				</div>

				<div class="formrow">
					<div class="odiv">
						<button class="submitbtn" style="">登 录</button>
					</div>
				</div>
			</form>
		</div>
	</div>
	<div class="foot">

		<br> 浙江翰林博德信息技术有限公司 版权所有 <br>

	</div>

	<script type="text/javascript">
		if (self != top) {
			window.top.location = window.location;
		}
	</script>
	<form action="${ctx}/forget" id="tokenform" style="display: none"
		method="post">
		<input type="hidden" name="token" value="nshpwd" />
	</form>
</body>