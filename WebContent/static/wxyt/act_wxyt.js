function wxyt(param) {
	if (param.div == undefined) {
		alert("无容器");
		return;
	}
	if (param.baseurl == undefined) {
		alert("未设置项目路径");
		return;
	}
	var timel = new Date().getTime() + parseInt(10000 * Math.random());
	initform();

	function initform() {
		// 添加HTML
		$("#" + param.div)
				.html(
						'<input value="选择文件" id="btn'
								+ timel
								+ '" onclick="javascript:openBrowse(\'file'
								+ timel
								+ '\');" type="button" style="width: 72px;" name="FileContent"><span name="easyTip" style="margin-left: 10px;color: red; font-size: 10px;">'
								+ (param.tip == undefined ? '' : param.tip)
								+ '</span>');

		$(document.body)
				.append(
						'<form id="uploadForm'
								+ timel
								+ '" style="display:none;"  method="post"><input id="file'
								+ timel
								+ '" type="file" style="width: 72px;" name="FileContent"></form>');
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
								param.fail("图片太大!请重新上传!");
							}
							return;
						}
					}
					$('#uploadForm' + timel).submit();
					showloading();
				});

		// 获取设置上传参数
		$.getJSON(param.baseurl + '/wxyt/actconfig', function(data) {
			if (data.result == "1") {
				var sign = data.sign;// "ZkXygoKlOhG1lUq5e6nvDqB8DN1hPTEwMDEzNDE5JmI9bHV5ZnRlc3Qmaz1BS0lERTlqdVZ0REtHSWloQmVzRDhIU3pyODVlNkNNN3ZMcmkmZT0xNDQ5MTMyNDQzJnQ9MTQ0OTEyODg5MiZyPTE1MzkxODE4MjImdT0wJmY9"
				var url = data.url + '?sign=' + encodeURIComponent(sign);
				var options = {
					type : 'post',
					url : url,
					dataType : 'json',
					success : function(ret) {
						hideloading();
						if (ret.code == 0) {
							if (typeof param.sucess == "function") {
								param.sucess(ret.data.download_url,
										ret.data.fileid, ret.data.url);
							}
						} else {
							if (typeof param.fail == "function") {
								param.fail(ret.message);
							}
						}
						// $('#downloadUrl').html(ret.data.download_url);
						// $('#fileid').text(ret.data.fileid);
						// $('#url').text(ret.data.url);
						// $('#downloadRet').show();
					},
					error : function(ret) {
						hideloading();
						if (typeof param.fail == "function") {
							param.fail(ret.responseText);
						}
						// alert(ret.responseText);
					}
				};
				$('#uploadForm' + timel).ajaxForm(options);
			}
		});
	}
	function showloading() {
		$(document.body)
				.append(
						'<div class="loaddiv"></div><div class="ball-pulse balldiv"><div></div><div></div><div></div></div>');
	}
	function hideloading() {
		$(".loaddiv").remove();
		$(".balldiv").remove();
	}

	function getFileSize(sourceId) {
		return document.getElementById(sourceId).files.item(0).size;
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