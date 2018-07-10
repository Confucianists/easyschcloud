<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE html >
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" href="${ctx}/static/homepage/css/index.css">
<link />
<title>真会云课堂</title>
</head>
<body>
	<div class="top">
		<div class="menubar">
			<a class="logobar" href="#hash0"><img
				src="${ctx}/static/homepage/image/indexlogo.png" /></a><a href="#hash1"
				class="navbar firstnavbar">白板5.0</a><a href="#hash2" class="navbar">答题卡</a><a
				class="navbar" href="#hash3">硬件</a><a
				class="btnbar loginbtn firstloginbtn" href="${ctx}/teacher/login" target="_blank">老师登录</a>
			<a class="btnbar loginbtn">学生登录</a> <a class="btnbar registerbtn">免费注册</a>
		</div>
	</div>
	<div class="banner" id="hash0">
		<img src="${ctx}/static/homepage/image/banner.png">
	</div>
	<div class="content">
		<div class="partdiv" id="hash1">
			<img src="${ctx}/static/homepage/image/js1.png" />
		</div>
		<div class="partdiv" id="hash2">
			<img src="${ctx}/static/homepage/image/js1.png" />
		</div>
		<div class="partdiv" id="hash3">
			<img src="${ctx}/static/homepage/image/js1.png" />
		</div>
	</div>
	<div class="foot"></div>
</body>
<script type="text/javascript"
	src="${ctx}/static/jquery/jquery-1.9.1.min.js"></script>
<script type="text/javascript" src="${ctx}/static/homepage/js/index.js"></script>
</html>
