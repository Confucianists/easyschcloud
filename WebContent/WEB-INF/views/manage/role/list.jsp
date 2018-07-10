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
		<a>系统管理 </a> / <a class="nowpage"> 角色管理</a>
	</div>

	<div class="hltable">
		<div class="hltitle">
			<div class="titlename">列表</div>
			<div class="titleactions">
				<a class="actionbtn bluebtn newbtn">新增</a>
			</div>
		</div>
		<div class="hltablecontent">
			<table border="0" cellpadding="2" cellspacing="0">
				<thead>
					<tr>
						<th class="hlsort">序号</th>
						<th>角色名称</th>
						<th>角色描述</th>
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


	<div class="hlframe" id="saveframe">
		<div class="hlframetitle">新增/修改角色</div>
		<div class="hlframecontent">
			<form action="">
				<input type="hidden" name="id" id="id" />
				<div class="hlformrow">
					<div class="formlabel">
						<font class="btfont">*</font> 角色名称
					</div>
					<div class="frominput">
						<input class="hlinput" id="name" name="name" required="required"
							maxlength="10" />
					</div>
					<div class="errortip">输入错误</div>
				</div>
				<div class="hlformrow">
					<div class="formlabel">角色简介</div>
					<div class="frominput">
						<input class="hlinput" id="roledesc" name="roledesc"
							required="required" maxlength="20" />
					</div>

				</div>
			</form>
		</div>
		<div class="hlframeaction">
			<a class="actionbtn bluebtn savebtn">保存</a> <a
				class="actionbtn redbtn cancelbtn">取消</a>
		</div>
	</div>

	<div class="hlframe" id="roleframe">
		<div class="hlframetitle">修改权限</div>
		<div class="hlframecontent"></div>
		<div class="hlframeaction">
			<a class="actionbtn bluebtn rolesavebtn">保存</a> <a
				class="actionbtn redbtn rolecancelbtn">取消</a>
		</div>
	</div>
	<input type="hidden" id="roleId" name="roleId" />
	<link rel="stylesheet"
		href="${ctx}/static/jstree/dist/themes/default/style.min.css" />
	<script src="${ctx}/static/jstree/dist/jstree.min.js"></script>
	<script type="text/javascript">
		var pagin;
		var dataurl = "${ctx}/manage/rolemanage/list";
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
			$(".rolesavebtn").bind("click", function() {
				saverole();
			});
			$(".rolecancelbtn").bind("click", function() {
				hiderole();
			});

		}
		//展示添加框
		function shownew() {
			$(".backcover").show();
			$("#saveframe input").val("");
			//$(".hlframe select").val(1);
			$("#saveframe .errortip").hide();
			$("#saveframe").showhlframe();
			$("#saveframe .hlinput")[0].focus();
			actiontype = "save";
		}
		function showmodify(id, name, desc) {
			$(".backcover").show();
			$("#saveframe #id").val(id);
			$("#saveframe #name").val(name);
			$("#saveframe #roledesc").val(desc);
			$("#saveframe .errortip").hide();
			$("#saveframe").showhlframe();
			$("#saveframe .hlinput")[0].focus();
			actiontype = "update";
		}
		function showrole(id) {
			$(".backcover").show();
			$("#roleId").val(id);
			$("#roleframe").showhlframe("max");
			getTree(id);
		}

		function getTree(id) {
			$("#roleframe .hlframecontent").html(
					'<div id="jstree_demo_div"></div>');
			$.post("${ctx}/manage/resourcemanage/treedata?rid=" + id, function(
					data) {
				var fjdata = {};
				fjdata.text = "所有资源";
				fjdata.id = -1;
				fjdata.children = totreedata(data);
				fjdata.state = {
					"opened" : true
				};
				pagin = $('#jstree_demo_div').jstree({
					'plugins' : [ "wholerow", "checkbox", "types" ],
					'core' : {
						"themes" : {
							"responsive" : false
						},
						'data' : fjdata
					}
				});
				dddd=fjdata;
			});
		}
		var dddd={};
		function totreedata(list) {
			var treedata = [];
			for (var i = 0; i < list.length; i++) {
				var da = {};
				da.id = list[i].id;
				da.text = list[i].name;
				if (list[i].resources != null) {
					da.children = totreedata(list[i].resources);
				}
				da.state = list[i].state;
				treedata.push(da);
			}
			return treedata;
		}

		function check() {
			var param = getMap($("#saveframe form").serialize());
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
			var saveurl = actiontype == "save" ? "${ctx}/manage/rolemanage/save"
					: "${ctx}/manage/rolemanage/update";
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
			$('#saveframe form').ajaxForm(options);
			$('#saveframe form').submit();
		}
		function saverole() {
			var str = "";
			$(".jstree-wholerow-clicked").each(function() {
				str += $(this).parent().attr("id") + "#";
			});
			if ($.trim(str).length > 0) {
				$.post("${ctx}/manage/rolemanage/saveroleres", {
					roleId : $("#roleId").val(),
					ids : str.substring(0, str.length - 1)
				}, function(d) {
					hiderole();
					if (d == "1") {
						showAlert("操作成功");
					} else {
						showAlert("操作失败");
					}
				});

			} else {
				showAlert("你没有选择任何资源!");
				return;
			}
		}
		function hidenew() {
			$(".backcover").hide();
			$("#saveframe").hide();
		}
		function hiderole() {
			$(".backcover").hide();
			$("#roleframe").hide();
		}
		function pageselectCallback(data) {
			$("#list").empty();
			for (var i = 0; i < data.content.length; i++) {
				$("#list")
						.append(
								'<tr><td>'
										+ (i + 1)
										+ '</td><td>'
										+ data.content[i].name
										+ '</td><td>'
										+ data.content[i].roledesc
										+ '</td><td><a aid="'+data.content[i].id+'"  name="'+data.content[i].name+'" desc="'+data.content[i].roledesc+'" class="actiona modify">修改</a><a  aid="'+data.content[i].id+'" class="actiona rolebtn">修改权限</a><a aid="'+data.content[i].id+'" class="actiona deletea">删除</a></td></tr>');
			}
			initbableaction();
		}
		//初始化表格操作按钮
		function initbableaction() {
			$(".modify").bind(
					"click",
					function() {
						showmodify($(this).attr("aid"), $(this).attr("name"),
								$(this).attr("desc"));
					});
			$(".deletea").bind("click", function() {
				showdelete($(this).attr("aid"));
			});
			$(".rolebtn").bind("click", function() {
				showrole($(this).attr("aid"));
			});
		}
		function showdelete(id) {
			showConfirm("是否要删除所选记录", function() {
				dodelete(id);
			});
		}
		function dodelete(id) {
			$.post('${ctx}/manage/rolemanage/delete', {
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