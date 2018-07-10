var hlbdupload = function(param) {

	if (param.div == undefined) {
		alert("无容器");
		return;
	}
	var timel = new Date().getTime() + parseInt(10000 * Math.random());
	var filters = {};
	if (param.filetype == "img") {
		filters.mime_types = [];
		filters.mime_types.push({
			title : "图片",
			extensions : "jpg,gif,png"
		});
	}

	if (param.maxsize != undefined) {
		filters.max_file_size = param.maxsize;
	}

	var uploader = new plupload.Uploader({
		runtimes : 'html5,flash,silverlight,html4',
		browse_button : param.div,
		container : document.getElementById(param.container),
		flash_swf_url : 'lib/plupload-2.1.2/js/Moxie.swf',
		silverlight_xap_url : 'lib/plupload-2.1.2/js/Moxie.xap',
		filters : filters,
		url : 'http://oss.aliyuncs.com',
		multi_selection : param.select == undefined ? false : param.select,
		init : {
			PostInit : function() {
			},
			FilesAdded : function(up, files) {
				if (typeof param.FilesAdded == "function") {
					param.FilesAdded(up, files);
				}
			},

			UploadProgress : function(up, file) {
				$('.loadtext').html(file.percent + "%");
			},

			FileUploaded : function(up, file, info) {
				hideload();
				console.log('uploaded');
				console.log(info.status);
				if (info.status == 200) {
					if (typeof param.Success == "function") {
						param.Success(info.response);
					}
					// alert("OK");
				} else {
					alert(info.response);
				}
			},

			Error : function(up, err) {
				hideload();
				var msg = '上传失败';
				if (err.code == -600) {// 文件太大
					msg = "请选择不大于" + plupload.formatSize(param.maxsize);
				} else if (err.code == -601) {// 文件格式不正确
					msg = "文件格式不正确";
				}
				if (typeof param.Error == "function") {
					param.Error(msg);
				}

			}
		}
	});

	this.start = function(type) {
		showload();
		$.post(param.policyurl, function(d) {
			var data = {
				OSSAccessKeyId : d.accessid,
				policy : d.policy,
				signature : d.signature,
				success_action_status : '200',
				key : d.dir + new Date().getTime() + "." + type,
				callback : d.callback
			};
			uploader.setOption({
				'url' : d.host,
				'multipart_params' : data
			});
			uploader.start();
		});
	}
	uploader.init();

	this.stop = function() {
		uploader.stop();
	}
	
	this.destroy = function() {
		uploader.destroy();
	}
}

function showload() {
	$(document.body)
			.append(
					'<div id="fileloadingToast" >	<div class="mask_transparent"></div><div class="toast">	<div class="fileload">	<div class="loading_leaf loading_leaf_0"></div>	<div class="loading_leaf loading_leaf_1"></div>	<div class="loading_leaf loading_leaf_2"></div>	<div class="loading_leaf loading_leaf_3"></div>	<div class="loading_leaf loading_leaf_4"></div>	<div class="loading_leaf loading_leaf_5"></div>	<div class="loading_leaf loading_leaf_6"></div>	<div class="loading_leaf loading_leaf_7"></div>	<div class="loading_leaf loading_leaf_8"></div>	<div class="loading_leaf loading_leaf_9"></div>	<div class="loading_leaf loading_leaf_10"></div><div class="loading_leaf loading_leaf_11"></div></div><div class="loadtext">0%</div><div class="loading_cancel_div"><a href="#" onclick="uploadstop()"  class="loading_cancel">取消</a></div>  </div>	</div>');
}

function uploadstop() {
	upload.stop();
	hideload();
	window.location.reload();

}
function hideload() {
	$("#fileloadingToast").remove();
}
