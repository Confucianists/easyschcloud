<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html>
<head>
<style type="text/css">
</style>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />

<title></title>

<style type="text/css">
</style>
</head>
<body>
	<div class="video" id="HLSPlayer">
		<SCRIPT LANGUAGE=JavaScript>
			/*
			 * HLSPlayer参数应用=======================
			 * @param {Object} vID ID
			 * @param {Object} vWidth 播放器宽度设置
			 * @param {Object} vHeight 播放器宽度设置
			 * @param {Object} vPlayer 播放器文件
			 * @param {Object} vHLSset HLS配置
			 * @param {Object} vPic 视频缩略图
			 * @param {Object} vCssurl 移动端CSS应用文件
			 * HLSPlayer参数应用=======================
			 */
			var vID = "";
			var vWidth = "100%";
			var vHeight = "auto";
			var vPlayer = "${ctx}/static/hls/HLSplayer.swf";
			var vHLSset = "${ctx}/static/hls/HLS.swf";
			var vPic = "${ctx}/static/hls/images/start.jpg";
			var vCssurl = "${ctx}/static/hls/images/mini.css";
			//HLS(m3u8)地址,适配PC,安卓,iOS,WP
			var vHLSurl = "${imc.hlspullurl}";
		//-->
		</SCRIPT>
		<script type="text/javascript" src="${ctx}/static/hls/hls.js"></script>
	</div>
</body>
</html>