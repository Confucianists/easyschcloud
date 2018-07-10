<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html>
<head>
<title>欢迎进入</title>
</head>
<body>
	<div
		style="width: 280px;
	/* text-align: center; */ position: absolute; top: 20%; left: 50%; margin-left: -140px;">
		<img style="width: 100%; margin-top: 5%;display: none;"
			src="${ctx}/static/manage/images/welcome.png" />
	</div>
</body>
</html> 