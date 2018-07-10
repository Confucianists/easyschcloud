<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link rel="stylesheet" href="${ctx}/static/yjyupload/yjyupload.css" />
<title>Insert title here</title>
</head>
<body>
	<div id="mydiv111" style="position: static;"></div>

 
</body>
<script type="text/javascript"
	src="${ctx}/static/jquery/jquery-1.9.1.min.js"></script>
<script type="text/javascript"
	src="${ctx}/static/yjyupload/yjyupload.js"></script>
<script type="text/javascript"
	src="${ctx}/static/yjyupload/jquery.dragsort-0.5.1.min.js"></script>

<script type="text/javascript"
	src="${ctx}/static/yjyupload/js/plupload.full.min.js"></script>
<script>
	jQuery(document)
			.ready(
					function() {
						var d = new yjyupload(
								"${ctx}/",
								{
									contain : "mydiv111",
									type : 2,
									tip : "只支持图片,分辨率 1920*1920HAHAHAHHAHAHAHHAA11111111111111111111111111111111111111111111",
									selects : true,
									tx : 300,
									ty : 300

								});

						//alert(document.documentElement.clientWidth+":"+document.documentElement.clientHeight);
						//$("#co").animate({
						//	opacity : '0.0'
						//}, 5000, function() {
						//	$(this).css("display", "none"); 
						//});

					});
</script>
</html>