<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<html>
<head>
<meta charset="UTF-8">
<title>${name}</title>
<link href="${ctx}/static/teacher/image/icologo.png" rel="shortcut icon">
<style type="text/css">
body {
	margin: 0;
	padding: 0;
	text-align: center;
}

#a1 {
	margin: auto;
}
</style>
</head>
<body>
	<div
		style="width: 800px; margin: auto; text-align: left; margin-top: 20px; margin-bottom: 20px; font-size: 16px;">产品安装流程
	</div>
	<div style="margin-bottom: 50px;" id="a1"></div>
	<script type="text/javascript" src="${ctx}/static/ckplayer/ckplayer.js"
		charset="utf-8"></script>
	<script type="text/javascript">
		var flashvars = {
			f : '${ctx}/static/video/b.mp4',
			c : 0,
			b : 1
		};
		var params = {
			bgcolor : '#FFF',
			allowFullScreen : true,
			allowScriptAccess : 'always',
			wmode : 'transparent'
		};
		CKobject.embedSWF('${ctx}/static/ckplayer/ckplayer.swf', 'a1',
				'ckplayer_a1', '800', '600', flashvars, params);
	</script>
</body>