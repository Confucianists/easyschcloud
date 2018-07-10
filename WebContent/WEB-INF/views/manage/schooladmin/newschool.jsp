<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>初始化</title>

</head>
<body>
<link rel="stylesheet" href="${ctx}/static/manage/schooladmin/css/newschool.css">
		<h3>创建学校>创建课程>导入用户</h3>
		<div class="contentdiv">
			<form action="">
			<input type="hidden" name="id" id="id" />
				<div class="createrow">
					<label>学校名称 ：</label><input class="subinput" type="text" name="name">
				</div>
				<div class="createrow">
					<label>学校地址 : </label> <input class="subinput" type="text" name="address">
				</div>
				<div class="createrow">
					<label>备注 ：</label><textarea  rows="3" cols="20" class="subtextarea" name="note"></textarea>
				</div>
				<div class="errortip">输入错误</div>
			</form>
				<div class="subbtnrow">
					<a class="subbtn">创建学校</a>
				</div>
		</div>
	<script type="text/javascript">
		$(function(){
			init();
		});

		function init(){
			$('.subbtn').bind('click',function(){
				checkname();
			});
		}

		function checkname(){
			var param = getMap($(".contentdiv form").serialize());
			
			if (param.name == "") {
				alert("不能为空");
				return;
			} 
			submitschool();
		}

		function submitschool(){
			var saveurl = "${ctx}/manage/schooladmin/createschool";
			var options = {
				type : 'post',
				url : saveurl,
				dataType : 'json',
				success : function(ret) {
					if (ret.result == "1") {
						alert("创建成功");
						window.location.href="${ctx}/manage/schooladmin/newsubject"
					}
				},
				error : function(ret) {

				}
			};
			$('.contentdiv form').ajaxForm(options);
			$('.contentdiv form').submit();
		}
	</script>
	
</body>
</html>