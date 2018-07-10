var wxyt = function(param) {
	if (param.div == undefined) {
		alert("无容器");
		return;
	}
	var timel = new Date().getTime() + parseInt(10000 * Math.random());
	var options;
	this.initform = function() {
		// 添加HTML
		// $("#" + param.div).html(
		// '<a class="btna bluebtn" value="选择文件" id="btn' + timel
		// + '" onclick="javascript:openBrowse(\'file' + timel
		// + '\');" type="button" name="FileContent">上传文件</a>');
		$("#" + param.div).attr("onclick",
				'javascript:openBrowse(\'file' + timel + '\');');
		$("#" + param.div).attr("name", 'FileContent');
		$(document.body).append(
				'<input style="display:none"  multiple="multiple"  id="file'
						+ timel + '" type="file"  name="file"> ');
		// 绑定事件
		$("#file" + timel).bind(
				"change",
				function() {
					var filepath = $(this).val();
					var extStart = filepath.lastIndexOf(".");
					var ext = filepath.substring(extStart, filepath.length)
							.toUpperCase();
					if (ext != ".PNG" && ext != ".JPG") {
						if (typeof param.fail == "function") {
							param.fail("只支持jpg,png的图片!");
						}
						return;
					}

					if (param.maxsize != undefined) {
						if (getFileSize("file" + timel) > param.maxsize) {
							if (typeof param.fail == "function") {
								param.fail("请上传的1M以内的图片");
							}
							return;
						}
					}

					$(".infolog").css(
							"background",
							"url("
									+ window.URL
											.createObjectURL(document
													.getElementById("file"
															+ timel).files[0])
									+ ") center")
					$("#upload").show();

				});

		var url = param.url;
		options = {
			type : 'post',
			url : url,
			dataType : 'json',
			success : function(ret) {
				hideload();
				if (ret.result == 1) {
					if (typeof param.sucess == "function") {
						param.sucess(ret.data);
					}
				} else {
					if (typeof param.fail == "function") {
						param.fail();
					}
				}
				// $('#downloadUrl').html(ret.data.download_url);
				// $('#fileid').text(ret.data.fileid);
				// $('#url').text(ret.data.url);
				// $('#downloadRet').show();
			},
			error : function(ret) {
				hideload();
				if (typeof param.fail == "function") {
					param.fail(ret.responseText);
				}
				// alert(ret.responseText);
			}
		};

	}

	this.startupload = function() {
		if (param.policyurl != undefined) {
			$.post(param.policyurl, function(d) {

				var data = {
					OSSAccessKeyId : d.accessid,
					policy : d.policy,
					signature : d.signature,
					success_action_status : '200',
					key : d.dir + '${filename}',
					callback : d.callback
				};
				var url = d.host;
				showload();

				var oMyForm = new FormData();

				for ( var field_name in data) {
					oMyForm.append(field_name, data[field_name]);
				}

				oMyForm.append("file",
						document.getElementById("file" + timel).files[0]);

				var oReq = new XMLHttpRequest();
				// 上传进度监听
				oReq.upload.onprogress = function(e) {
					if (e.type == 'progress') {
						var percent = Math.round(e.loaded / e.total * 100, 2)
								+ '%';
						$('.loadtext').html(percent);// 显示进度的容器 自行修改
					}
				};
				// 上传结果
				oReq.onreadystatechange = function(e) {

					if (oReq.readyState == 4) {
						hideload();
						if (oReq.status == 200)// 这里如果成功返回的是
							// success_action_status设置的值
							if (typeof param.sucess == "function") {
								param.sucess(oReq.responseText);
							} else {
								if (typeof param.fail == "function") {
									param.fail();
								}
							}
					}
				};
				oReq.open("POST", url);
				oReq.send(oMyForm);

			});
		}

	}

	function getFileSize(sourceId) {
		return document.getElementById(sourceId).files.item(0).size;
	}

}
function showload() {
	$(document.body)
			.append(
					'<div id="fileloadingToast" >	<div class="mask_transparent"></div><div class="toast">	<div class="fileload">	<div class="loading_leaf loading_leaf_0"></div>	<div class="loading_leaf loading_leaf_1"></div>	<div class="loading_leaf loading_leaf_2"></div>	<div class="loading_leaf loading_leaf_3"></div>	<div class="loading_leaf loading_leaf_4"></div>	<div class="loading_leaf loading_leaf_5"></div>	<div class="loading_leaf loading_leaf_6"></div>	<div class="loading_leaf loading_leaf_7"></div>	<div class="loading_leaf loading_leaf_8"></div>	<div class="loading_leaf loading_leaf_9"></div>	<div class="loading_leaf loading_leaf_10"></div><div class="loading_leaf loading_leaf_11"></div></div><div class="loadtext">0%</div></div>	</div>');
}
function hideload() {
	$("#fileloadingToast").remove();
}

function openBrowse(fileinput) {
	var ie = navigator.appName == "Microsoft Internet Explorer" ? true : false;
	if (ie) {
		document.getElementById(fileinput).click();
		// document.getElementById("filename").value = document
		// .getElementById(fileinput).value;
	} else {
		var a = document.createEvent("MouseEvents");// FF的处理
		a.initEvent("click", true, true);
		document.getElementById(fileinput).dispatchEvent(a);
	}
}
