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
		<a>系统管理 </a> > 资源管理
	</div>

	<div class="hltable">
		<div class="hltitle">
			<div class="titlename">列表</div>
			<div class="titleactions">
				<a class="actionbtn bluebtn newbtn">新增</a> <a
					class="actionbtn bluebtn modifybtn">修改</a> <a
					class="actionbtn redbtn deletebtn">删除</a>
			</div>
		</div>
		<div class="hltablecontent"></div>
	</div>


	<div class="hlframe">
		<div class="hlframetitle">新增/修改资源</div>
		<div class="hlframecontent">
			<form action="">
				<input type="hidden" name="id" id="id" /> <input type="hidden"
					name="resource.id" id="pid" />
				<div class="hlformrow">
					<div class="formlabel">
						<font class="btfont">*</font> 资源名称
					</div>
					<div class="frominput">
						<input class="hlinput" id="name" name="name" required="required"
							maxlength="10" />
					</div>
					<div class="errortip">输入错误</div>
				</div>
				<div class="hlformrow">
					<div class="formlabel">父级资源</div>
					<div class="frominput">
						<input class="hlinput" id="fjname" readonly="readonly" />
					</div>
				</div>
				<div class="hlformrow">
					<div class="formlabel">
						资源地址
					</div>
					<div class="frominput">
						<input class="hlinput" id="url" name="url"  />
					</div>
					<div class="errortip">输入错误</div>
				</div>
				<div class="hlformrow">
					<div class="formlabel">
						logo
					</div>
					<div class="frominput">
						<input class="hlinput" id="logo" name="logo"  />
					</div>
					
				</div>
				<div class="hlformrow">
					<div class="formlabel">
						<font class="btfont">*</font>排序
					</div>
					<div class="frominput">
						<input class="hlinput" id="sorts" name="sorts" required="required" />
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
	<input type="hidden" id="hideid">
	<input type="hidden" id="hidetext">
	<link rel="stylesheet"
		href="${ctx}/static/jstree/dist/themes/default/style.min.css" />
	<script src="${ctx}/static/jstree/dist/jstree.min.js"></script>
	<script type="text/javascript">
		var pagin;
		var dataurl = "${ctx}/manage/rolemanage/list";
		$(function() {
			//$('#jstree_demo_div').jstree();
			getTree();
			init();
		});

		function init() {

			$(".newbtn").bind("click", function() {
				shownew();
			});
			$(".savebtn").bind("click", function() {
				check();
			});
			$(".cancelbtn").bind("click", function() {
				hidenew();
			});

			$(".modifybtn").bind("click", function() {
				showmodify();
			});
			$(".deletebtn").bind("click", function() {
				showdelete();
			});
		}
		function getTree() {
			$(".hltablecontent").html('<div id="jstree_demo_div"></div>');
			$.post("${ctx}/manage/resourcemanage/treedata", function(data) {
				var fjdata = {};
				fjdata.text = "所有资源";
				fjdata.id = -1;
				fjdata.children = totreedata(data);
				fjdata.state = {
					"opened" : true
				};
				pagin = $('#jstree_demo_div').jstree({
					'plugins' : [ "wholerow", "types" ],
					'core' : {
						"themes" : {
							"responsive" : false
						},
						'data' : fjdata
					}
				});
				$('#jstree_demo_div').on("select_node.jstree",
						function(e, data) {
							$("#hideid").val(data.node.id); //选择的node id  
							$("#hidetext").val(data.node.text); //选择的node text  

						});
			});
		}
		function totreedata(list) {
			var treedata = [];
			for (var i = 0; i < list.length; i++) {
				var da = {};
				da.id = list[i].id;
				da.text = list[i].name;
				if (list[i].resources != null) {
					da.children = totreedata(list[i].resources);
				}
				da.state = {
					"opened" : true
				};
				treedata.push(da);
			}
			return treedata;
		}
		//展示添加框
		function shownew() {
			if ($("#hideid").val() == "") {
				showAlert("请选择一条记录");
				return;
			}
			$(".backcover").show();
			$(".hlframe input").val("");
			$(".hlframe #pid").val($("#hideid").val());
			$(".hlframe #fjname").val($("#hidetext").val());
			//$(".hlframe select").val(1);
			$(".errortip").hide();
			$(".hlframe").showhlframe();
			$(".hlframe .hlinput")[0].focus();
			actiontype = "save";
		}
		function showmodify() {
			if ($("#hideid").val() == "") {
				showAlert("请选择一条记录");
				return;
			}
			$(".backcover").show();
			$(".hlframe input").val("");
			$(".errortip").hide();
			$(".hlframe").showhlframe();

			$.post("${ctx}/manage/resourcemanage/getdata?id="
					+ $("#hideid").val(), function(d) {
				$(".hlframe #id").val(d.id);
				$(".hlframe #name").val(d.name);
				if (d.resource == null) {
					$(".hlframe #pid").val(-1);
					$(".hlframe #fjname").val("所有资源");
				} else {
					$(".hlframe #pid").val(d.resource.id);
					$(".hlframe #fjname").val(d.resource.name);
				}

				$(".hlframe #url").val(d.url);
				$(".hlframe #logo").val(d.logo);
				$(".hlframe #sorts").val(d.sorts);
				$(".hlframe .hlinput")[0].focus();
				actiontype = "update";
			});

		}
		//初始化表格操作按钮
		function showdelete() {
			if ($("#hideid").val() == "") {
				showAlert("请选择一条记录");
				return;
			}
			showConfirm("是否要删除所选记录", function() {
				dodelete();
			});
		}
		function dodelete() {
			$.post('${ctx}/manage/resourcemanage/delete', {
				"id" : $("#hideid").val()
			}, function(data) {
				if (data.result == "1") {
					getTree();
				} else {
					getTree();
					showAlert("删除失败");
				}
			});
		}

		function check() {
			var param = getMap($(".hlframe form").serialize());
			if (param.name == "") {
				showerror("name", "不能为空");
				return;
			} else {
				hideerror("name");
			}
			
			if (param.sorts == "") {
				showerror("sorts", "不能为空");
				return;
			} else {
				hideerror("sorts");
			}
			if ($("#pid").val() == "-1") {
				$("#pid").val("");
			}
			submit();
		}
		function submit() {
			var saveurl = actiontype == "save" ? "${ctx}/manage/resourcemanage/save"
					: "${ctx}/manage/resourcemanage/save";
			var options = {
				type : 'post',
				url : saveurl,
				dataType : 'json',
				success : function(ret) {
					if (ret.result == "1") {
						getTree();
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
		
	</script>
</body>
</html>