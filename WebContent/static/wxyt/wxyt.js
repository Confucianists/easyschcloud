function wxyt(param) {
	if (param.div == undefined) {
		alert("无容器");
		return;
	}
	var timel = new Date().getTime() + parseInt(10000 * Math.random());
	initform();

	function initform() {
		// 添加HTML
		// $("#" + param.div).html(
		// '<a class="btna bluebtn" value="选择文件" id="btn' + timel
		// + '" onclick="javascript:openBrowse(\'file' + timel
		// + '\');" type="button" name="FileContent">上传文件</a>');
		$("#" + param.div).attr("onclick",
				'javascript:openBrowse(\'file' + timel + '\');');
		$("#" + param.div).attr("name", 'FileContent');
		$(document.body)
				.append(
						'<form id="uploadForm'
								+ timel
								+ '" style="display:none;"  method="post"><input id="file' 
								+ timel
								+ '" type="file"  name="file"><input id="folderid" value="0"   name="folderid"></form>');
		// 绑定事件
		$("#file" + timel).bind(
				"change",
				function() {
					var filepath = $(this).val();
					var extStart = filepath.lastIndexOf(".");
					var ext = filepath.substring(extStart, filepath.length)
							.toUpperCase();
					// if (ext != ".PNG" && ext != ".JPG") {
					// if (typeof param.fail == "function") {
					// param.fail("只支持jpg,png的图片!");
					// }
					// return;
					// }
					if (typeof param.check == "function") {
						if (!param.check(ext)) {
							return;
						}
					}

					if (param.maxsize != undefined) {
						if (getFileSize("file" + timel) > param.maxsize) {
							if (typeof param.fail == "function") {
								param.fail("文件太大!请重新上传!");
							}
							return;
						}
					}
					$('#uploadForm' + timel).submit();
					showload();
					processdata = 0;
					doProgressLoop();

				});

		var url = param.url;
		var options = {
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
		$('#uploadForm' + timel).ajaxForm(options);
	}
	function showload() {
		$(document.body)
				.append(
						'<div id="fileloadingToast" >	<div class="mask_transparent"></div><div class="toast">	<div class="fileload">	<div class="loading_leaf loading_leaf_0"></div>	<div class="loading_leaf loading_leaf_1"></div>	<div class="loading_leaf loading_leaf_2"></div>	<div class="loading_leaf loading_leaf_3"></div>	<div class="loading_leaf loading_leaf_4"></div>	<div class="loading_leaf loading_leaf_5"></div>	<div class="loading_leaf loading_leaf_6"></div>	<div class="loading_leaf loading_leaf_7"></div>	<div class="loading_leaf loading_leaf_8"></div>	<div class="loading_leaf loading_leaf_9"></div>	<div class="loading_leaf loading_leaf_10"></div><div class="loading_leaf loading_leaf_11"></div></div><div class="loadtext">0%</div></div>	</div>');
	}
	function hideload() {
		$("#fileloadingToast").remove();
	}

	function getFileSize(sourceId) {
		return document.getElementById(sourceId).files.item(0).size;
	}
	var processdata;
	function getProgress() {
		$.ajax({
			type : "post",
			dataType : 'json',
			url : param.baseurl + "/api/process/getprocess",
			data : "",
			success : function(data) {
				if (data != null) {
					processdata = parseInt(parseInt(data.process.pBytesRead)
							* 100 / parseInt(data.process.pContentLength));
					$(".loadtext").html(processdata + "%");
				}
			},
			error : function(err) {
				alert("读取进度失败");
				processdata = 100;
			}
		});
	}

	function doProgressLoop() {
		if (processdata < 100) {
			setTimeout(function() {
				getProgress();
			}, 500);
			setTimeout(function() {
				doProgressLoop();
			}, 500);
		}
	}

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