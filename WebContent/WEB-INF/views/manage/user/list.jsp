<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<html>
<head>
<meta charset="utf-8" />
<title>用户管理</title>

</head>
<body>
	<div class="hlrow">
		<a>系统管理 </a> / <a class="nowpage"> 用户管理</a>
	</div>
	<div class="hltable">
		<div class="hltitle">
			<div class="titlename">搜索</div>
		</div>
		<div class="hltablecontent">
			<form id="searchform" action="">
				<div class="hltoolbar">
					<div class="hlrowsec">
						<div class="hltd hltd33">
							<div class="tdleft">
								<label>账号</label>
							</div>
							<div class="tdright">
								<input id="name" name="search_LIKE_name" class="hlinput" />
							</div>
						</div>
						<div class="hltd hltd33">
							<div class="tdleft">
								<label>名称</label>
							</div>
							<div class="tdright">
								<input id="realname" name="search_LIKE_realname" class="hlinput" />
							</div>
						</div>
						<div class="hltd hltd33">
							<div class="tdleft">
								<label>角色</label>
							</div>
							<div class="tdright">
								<select class="hlselect" id="role" name="search_EQ_role.id">
									<option value="0">全部</option>
									<c:forEach var="role" items="${roles}">
										<option value="${role.id}">${role.name}</option>
									</c:forEach>
								</select>
							</div>
						</div>
					</div>
					<div class="hlrowsec">
						<div class="hltd hltd33"></div>
						<div class="hltd hltd33"></div>
						<div class="hltd hltd33">
							<div class="tdleft"></div>
							<div class="tdright" style="text-align: right;">
								<a class="actionbtn bluebtn searchbtn">查询</a> <a
									class="actionbtn redbtn resetbtn">重置</a>
							</div>

						</div>
					</div>
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
						<th class="hlsort"><input type="checkbox" /></th>
						<th class="hlsort">序号</th>
						<th>账号</th>
						<th>真实姓名</th>
						<th>角色</th>
						<th style="width: 200px;">操作</th>
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
		<div class="hlframetitle">新增/修改用户</div>
		<div class="hlframecontent">
			<form action="">
				<input type="hidden" name="id" id="id" />
				<div class="hlformrow">
					<div class="formlabel">
						<font class="btfont">*</font> 账号
					</div>
					<div class="frominput">
						<input class="hlinput" id="name" name="name" required="required"
							maxlength="20" />
					</div>
					<div class="errortip">输入错误</div>
				</div>
				<div class="hlformrow">
					<div class="formlabel">
						<font class="btfont">*</font> 真实姓名
					</div>
					<div class="frominput">
						<input class="hlinput" id="realname" name="realname"
							required="required" maxlength="10" />
					</div>
					<div class="errortip">输入错误</div>
				</div>
				<div class="hlformrow">
					<div class="formlabel">
						<font class="btfont">*</font> 角色
					</div>
					<div class="frominput">
						<select class="hlselect" id="role" name="role.id">
							<c:forEach var="role" items="${roles}">
								<option value="${role.id}">${role.name}</option>
							</c:forEach>
						</select>
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
		var dataurl = "${ctx}/manage/usermanage/list";
		$(function() {
			init();
		});

		function init() {
			var opt = {
				callback : pageselectCallback,
				items_per_page : 10,
			};
			pagin = $("#Pagination").pagination(dataurl, opt);

			$(".searchbtn").bind("click", function() {
				var params = getMap($("#searchform").serialize());
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
		function showmodify(id, realname, roleid) {
			$(".backcover").show();
			$(".hlframe #id").val(id);
			$(".hlframe #name").parent().parent().hide()
			$(".hlframe #realname").val(realname);
			$(".hlframe #role").val(roleid);
			$(".errortip").hide();
			$(".hlframe").showhlframe();
			$(".hlframe .hlinput")[0].focus();
			actiontype = "update";
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
			if (param.realname == "") {
				showerror("realname", "不能为空");
				return;
			} else {
				hideerror("realname");
			}
			submit();
		}
		function submit() {
			var saveurl = actiontype == "save" ? "${ctx}/manage/usermanage/save"
					: "${ctx}/manage/usermanage/update";
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
		function hidenew() {
			$(".backcover").hide();
			$(".hlframe").hide();
		}
		function pageselectCallback(data) {
			$("#list").empty();
			for (var i = 0; i < data.content.length; i++) {
				$("#list")
						.append(
								'<tr><td><input type="checkbox" /></td><td>'
										+ (i + 1)
										+ '</td><td>'
										+ data.content[i].name
										+ '</td><td>'
										+ data.content[i].realname
										+ '</td><td>'
										+ data.content[i].role.name
										+ '</td><td><a aid="'+data.content[i].id+'" rid="'+data.content[i].role.id+'" realname="'+data.content[i].realname+'" class="actiona modify">修改</a><a aid="'+data.content[i].id+'" class="actiona deletea">删除</a></td></tr>');
			}
			initbableaction();
		}
		//初始化表格操作按钮
		function initbableaction() {
			$(".modify").bind(
					"click",
					function() {
						showmodify($(this).attr("aid"), $(this)
								.attr("realname"), $(this).attr("rid"));
					});
			$(".deletea").bind("click", function() {
				showdelete($(this).attr("aid"));
			});
		}
		function showdelete(id) {
			showConfirm("是否要删除所选记录", function() {
				dodelete(id);
			});
		}
		function dodelete(id) {
			$.post('${ctx}/manage/usermanage/delete', {
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
		function resetAll() {
			$("#searchform #name").val("");
			$("#searchform #realname").val("");
			$("#searchform #role").val(0);
		}
	</script>
</body>
</html>