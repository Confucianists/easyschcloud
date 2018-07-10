var alluploadFiles = [];
var uploader_forfile;
var nomarlfiles = [];
var videofiles = [];
var isadd = false;

/** ****************用来选择文件的 主要负责文件检查**************** */
function upinit() {
	uploader_forfile = new plupload.Uploader({
		runtimes : 'html5,flash,silverlight,html4',
		browse_button : "uploadtip",
		container : document.getElementById("container"),
		flash_swf_url : flash_swf_url,
		silverlight_xap_url : silverlight_xap_url,
		// filters : filters,
		url : 'http://oss.aliyuncs.com',
		/**
		 * filters : { mime_types : [ // 只允许上传图片和zip文件 { title : "files",
		 * extensions :
		 * "jpg,gif,png,bmp,jpeg,doc,docx,xls,xlsx,ppt,pptx,pdf,avi,wmv,mpeg,mp4,mov,mkv,flv,3gp,rmvb,mp3,wma,wav,swf" } ],
		 * prevent_duplicates : true // },
		 */
		multi_selection : true,
		init : {
			PostInit : function() {

			},
			FilesAdded : function(up, files) {
				for (var i = 0; i < files.length; i++) {
					var filetype = files[i].name.substr(files[i].name
							.indexOf(".") + 1);
					if (gettype(filetype) == "") {
						showAlert("不支持文件格式");
						for (var j = 0; j < files.length; j++) {
							uploader_forfile.removeFile(files[i].id);
						}
						return false;
					}
				}
				uploadedfile = [];
				uploadednames = [];
				uploadedfiletypes = [];
				showupload();
			},
			UploadProgress : function(up, file) {

			},

			FileUploaded : function(up, file, info) {
				/**
				 * hideload(); console.log('uploaded');
				 * console.log(info.status); if (info.status == 200) { if
				 * (typeof param.Success == "function") {
				 * param.Success(info.response); } // alert("OK"); } else {
				 * alert(info.response); }
				 */
			},

			Error : function(up, err) {
				// hideload();
				var msg = '';
				if (err.code == -600) {// 文件太大
					msg = "请选择不大于" + plupload.formatSize(param.maxsize);
				} else if (err.code == -601) {// 文件格式不正确
					showAlert("不支持文件格式");
				} else {
					showAlert(err.msg);
				}

			}
		}
	});
}
upinit();
function showupload() {
	$(".uploadcontent").html("");
	nomarlfiles = [];
	videofiles = [];
	for (var i = 0; i < uploader_forfile.files.length; i++) {
		if (/video\//.test(uploader_forfile.files[i].type)) {
			videofiles.push(uploader_forfile.files[i]);
		} else {
			nomarlfiles.push(uploader_forfile.files[i]);
		}
		uploader_forfile.removeFile(uploader_forfile.files[i]);
	}

	for (var i = 0; i < nomarlfiles.length; i++) {
		var filetype = nomarlfiles[i].name.substr(nomarlfiles[i].name
				.indexOf(".") + 1);

		$(".uploadcontent")
				.append(
						'<div data-id="'
								+ nomarlfiles[i].id
								+ '" class="filepart"><div class="filename"><i class="'
								+ gettype(filetype)
								+ 'ico"></i>'
								+ nomarlfiles[i].name
								+ '</div>	<div class="fileuploadprocess">	<div></div></div><a onclick="uploadcancel(\''
								+ nomarlfiles[i].id + '\')" >取消</a></div>');
	}
	for (var i = 0; i < videofiles.length; i++) {
		$(".uploadcontent")
				.append(
						'<div data-id="'
								+ videofiles[i].id
								+ '" class="filepart"><div class="filename"><i class="videoico"></i>'
								+ videofiles[i].name
								+ '</div>	<div class="fileuploadprocess">	<div></div></div><a  onclick="videouploadcancel(\''
								+ videofiles[i].id + '\')"  >取消</a></div>');
	}

	// uploader_forfile.addFile(nomarlfiles);
	$(".uploadprocess").showhlframe("", 50);
	$(".backcoer").show();

	// $(".filepart a").bind("click", function() {
	// <a>取消</a>
	// });
	uploadfile();

};
function gettype(type) {
	type = type.toLowerCase();
	if (type == ("bmp") || type == ("jpg") || type == ("jpeg")
			|| type == ("png") || type == ("gif")) {
		return "img";
	} else if (type == ("doc") || type == ("docx")) {
		return "word";
	} else if (type == ("xls") || type == ("xlsx")) {
		return "excel";
	} else if (type == ("ppt") || type == ("pptx")) {
		return "ppt";
	} else if (type == ("pdf")) {
		return "pdf";
	} else if (type == ("avi") || type == ("wmv") || type == ("mpeg")
			|| type == ("mp4") || type == ("mov") || type == ("mkv")
			|| type == ("flv") || type == ("3gp") || type == ("rmvb")) {
		return "video";
	} else if (type == ("mp3") || type == ("wma") || type == ("wav")) {
		return "voice";
	} else if (type == ("swf")) {
		return "swf";
	} else
		return "";
}

// 取消上传
function uploadcancel(fileid) {
	for (var i = 0; i < nomarlfiles.length; i++) {
		if (nomarlfiles[i].id == fileid) {
			nomarlfiles.splice(i, 1);
			break;
		}
	}
	var btna = $($(".filepart[data-id='" + fileid + "']").children()[2]);
	btna.attr("onclick", "");
	btna.html("已取消");

	if (uploadingid == fileid) {
		nomaluploader.stop();
		uploadfile();
	}

}

// 取消上传
function videouploadcancel(fileid) {
	for (var i = 0; i < videofiles.length; i++) {
		if (videofiles[i].id == fileid) {
			videofiles.splice(i, 1);
			break;
		}
	}
	var btna = $($(".filepart[data-id='" + fileid + "']").children()[2]);
	btna.attr("onclick", "");
	btna.html("已取消");
	if (uploadingid == fileid) {
		videouploader.stopUpload();
		uploadfile();
	}

}

function checkpolicy() {

}
var uploadedfile = [];
var uploadednames = [];
var uploadedfiletypes = [];
function uploadfile() {
	if (nomarlfiles.length > 0) {
		if (policydata == null) {
			getpolicy();
			return;
		}
		nomaluploader.files = [];
		nomaluploader.addFile(nomarlfiles[0]);

		var type = "";
		if (nomarlfiles[0].name.indexOf(".") > -1) {
			type = nomarlfiles[0].name.substr(nomarlfiles[0].name.indexOf("."));
		}
		policydata.key = ossdir + new Date().getTime() + type;
		nomaluploader.setOption({
			'url' : osshost,
			'multipart_params' : policydata
		});
		uploadingid = nomarlfiles[0].id;
		nomaluploader.start();
	} else if (videofiles.length > 0) {
		uploadvideo(videofiles[0].name);

	} else {
		$(".uploadprocess").hide();
		$(".backcoer").hide();
		uploader_forfile.destroy();
		upinit();
		uploader_forfile.init();
		if (uploadedfile.length > 0) {
			sunmitcw();
		}
	}
}

function uploadvideo(name) {
	$
			.post(
					videourl,
					{
						name : name
					},
					function(d) {
						uploadAuth = d.uploadauth;
						uploadAddress = d.uploadaddress;
						videoid = d.videoid;
						var userData = '{"Vod":{"UserData":"{"IsShowWaterMark":"false","Priority":"7"}"}}';

						var ff = videofiles[0].getSource().getSource();;
						ff.fileid = videofiles[0].id;
						ff.videoid = videoid;
						
						var client = new OSS.Wrapper(
								{
									region : 'oss-cn-shanghai',
									//云账号AccessKey有所有API访问权限，建议遵循阿里云安全最佳实践，创建并使用STS方式来进行API访问
									accessKeyId : 'STS.KekMJLzuBPi4MpqrTa6d7VzyE',
									accessKeySecret : '3VyAe9Apc5vYjPo3iKkbREv5BaUZaoVudR7HufzNDb8e',
									stsToken : 'CAISsgR1q6Ft5B2yfSjIqqbeBvD4l6pj56vfT1bAllQ0Ousbub/Spzz2IHpKeXduAeAXs/o0mmhZ7/YYlrUqG8ceHhedMJoqsswPrVn+JpfZv8u84YADi5CjQYtyvK5Pn528Wf7waf+AUHPHCTmd5KEZo9bTcTGlQCYTWP/toJV7b9MRcxClZD5dfrl/LRdjr8lomBKzUPG2KUzSn3b3BkhlsRYe8mRk8vaa38mA6wLXlkHH0usSroiTUZ+/dJtNOpZjUt6p0clrcrbAyCdK9yVS8KB/gM4i/izc7OyFB15Y7y2PKfa2iNp0N11Efqw9EqJ8peLgvvpgq46gkJ/smT1KOPlRXjjSYZ2k3MrcEfm1C849cr/3WQTKycvdH5PyqR8BfHYHNRtDYcYcMnt3ABBOOjbBMf2D+UvWRxqnV6md2bsq7IJxyFzk5rjvIEOUEZGYyjogM4UgdDk8VXgs0Hfmb7UNfiFVblpjHMz1d4hoaw1Eoq6atW/kXyZ7nHZMpK+8NbGEu6ZaM92mAcoYi9VEOM4X6jV6EwiyRqupkF8ZczxhWPFI0aXgPtjiuO+pkM/CO6ufV9wsk3gCXW39hG/UGSsLaHaqv4BmOADEvpyJkPaTtZZ4TClWv4AAVl/aIYs18QU8v/PvsC7+qrGzDiqYmUMj4cTd8oV17k9pQ+ypmef6vESF5iLNP/dhyJaKBjYxGUXuKyZjvNmWh2MaoS4Gn1q9Mg0k90+r0mK9bDy1JTp/LbEvGoABnbuXKitcgwfDNQdlXulEM9WGLfj6HabwZvI4xeTMJf3lH4eKOdkJ9NjJRXE3/bRsP6noWfVpoCtaiGtJ1uzqPG6sinIRWC8cMzg5M7Hs2U+TZ/1WqcgsvlOgx8ZRnmHIvv4ypS45v+0jgcz2f1Z7sCvTQNqhvbNv4LscciiK0uw=',
									bucket : 'in-20170627142916844-eqorpeo8lq'
								});
						
						var key = "video/588D8B92-15DCAE0D5DC-1803-3315-924-00427.mp4";
						return client.multipartUpload(key, ff, {
						//	progress : progress
						}).then(function(res) {
							console.log('upload success: %j', res);
							//return listFiles(client);
						});
						
						
						
						
						//videouploader.cleanList();
						//videouploader.addFile(ff, null, null, null, userData);
						//videouploader.startUpload();
					});
}

function sunmitcw() {
	var rids = "", names = "", types = "";
	for (var i = 0; i < uploadedfile.length; i++) {
		rids += uploadedfile[i] + ",";
		names += uploadednames[i] + ",";
		types += uploadedfiletypes[i] + ",";
	}
	if (rids.endsWith(",")) {
		rids = rids.substr(0, rids.length - 1);
	}
	if (names.endsWith(",")) {
		names = names.substr(0, names.length - 1);
	}
	if (types.endsWith(",")) {
		types = types.substr(0, types.length - 1);
	}
	var param = {
		rids : rids,
		names : names,
		types : types,
		booknodeid : $(".actlesson").attr("data-id")
	}
	showload();
	$.post(savecwurl, param, function(d) {

		if (d.result == "1") {
			isloading = true;
			// params.pagesize = (parseInt($(window).height() / 180) + 1) *
			// 5;
			$(".sourcelist").html("");
			var param_ = params;
			param_.page = 1;
			param_.pagesize = params.page * params.pagesize;
			$.post(dataurl, param_, function(d) {
				hidload();
				if (d.content.length < params.pagesize) {
					stop = true;
				}
				needselected = [];
				for (var i = 0; i < allfile.size; i++) {
					needselected.push({
						id : allfile.files[i].id,
						kind : allfile.files[i].kind
					});
				}
				pageselectCallback(d);
				isloading = false;
			});
		} else {
			hidload();
			showAlert(d.msg);
		}
	});
}
var policydata = null;
var osshost = "";
var ossdir = "";
function getpolicy() {
	$.post(policyurl, function(d) {
		policydata = {
			OSSAccessKeyId : d.accessid,
			policy : d.policy,
			signature : d.signature,
			success_action_status : '200',
			callback : d.callback,
			key : ""
		};
		osshost = d.host;
		ossdir = d.dir;
		// nomaupload.init();
		uploadfile();
	});

}
uploader_forfile.init();

var nomaluploader = new plupload.Uploader({
	runtimes : 'html5,flash,silverlight,html4',
	browse_button : "hidetip",
	// container : document.getElementById("container"),
	flash_swf_url : flash_swf_url,
	silverlight_xap_url : silverlight_xap_url,
	// filters : filters,
	url : 'http://oss.aliyuncs.com',
	multi_selection : true,
	init : {
		PostInit : function() {

		},
		FilesAdded : function(up, files) {
			console.log("dasda");
			// startupload() ;
		},
		UploadProgress : function(up, file) {
			$(
					$($(".filepart[data-id='" + file.id + "']").children()[1])
							.children()[0]).css("width", file.percent + "%");

		},
		FileUploaded : function(up, file, info) {
			/**
			 * hideload(); console.log('uploaded'); console.log(info.status); if
			 * (info.status == 200) { if (typeof param.Success == "function") {
			 * param.Success(info.response); } // alert("OK"); } else {
			 * alert(info.response); }
			 */
			uploadingid = "";
			uploadedfile.push($.parseJSON(info.response).rid);
			uploadednames.push(file.name);
			uploadedfiletypes.push(1);
			nomarlfiles.splice(0, 1);
			uploadfile();

			$($(".filepart[data-id='" + file.id + "']").children()[2]).html(
					"完成");
		},
		Error : function(up, err) {
			// hideload();
			var msg = '上传失败';
			if (err.code == -600) {// 文件太大
				msg = "请选择不大于" + plupload.formatSize(param.maxsize);
			} else if (err.code == -601) {// 文件格式不正确
				msg = "文件格式不正确";
			} else {
				showAlert(err.msg);
			}

		}
	}
});
nomaluploader.init();
