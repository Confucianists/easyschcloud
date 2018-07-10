var uploader_forfile = new plupload.Uploader(
		{
			runtimes : 'html5,flash,silverlight,html4',
			browse_button : "scbtn",
			container : document.getElementById("btndiv"),
			flash_swf_url : 'lib/plupload-2.1.2/js/Moxie.swf',
			silverlight_xap_url : 'lib/plupload-2.1.2/js/Moxie.xap',
			// filters : filters,
			url : 'http://oss.aliyuncs.com',
			multi_selection : false,
			init : {
				PostInit : function() {

				},
				FilesAdded : function(up, files) {
					var userData = '{"Vod":{"UserData":"{"IsShowWaterMark":"false","Priority":"7"}"}}';
					var naf = files[0].getNative();
					naf.fileid = files[0].id;
					videouploader.addFile(naf, null, null, null, userData);

					uploadvideo(files[0]);

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

// 取消上传
function videouploadcancel(fileid) {
	videouploader.stopUpload();
	var btna = $($(".filepart[data-id='" + fileid + "']").children()[2]);
	btna.attr("onclick", "");
	btna.html("已取消");
	$(".uploadprocess").hide();
	$(".backcover").hide();
}

uploader_forfile.init();
function uploadvideo(videofile) {

	$(".uploadcontent")
			.html(
					'<div data-id="'
							+ videofile.id
							+ '" class="filepart"><div class="filename"><i class="videoico"></i>'
							+ videofile.name
							+ '</div>	<div class="fileuploadprocess">	<div></div></div><a  onclick="videouploadcancel(\''
							+ videofile.id + '\')"  >取消</a></div>');
	$(".uploadprocess").showhlframe("", 50);
	$(".backcover").show();
	$.post(videourl, {
		name : videofile.name
	}, function(d) {
		uploadAuth = d.uploadauth;
		uploadAddress = d.uploadaddress;
		videoid = d.videoid;

		// videouploader.listFiles()[0].fileid= videofile.id;
		videouploader.listFiles()[0].file.videoid = videoid;
		videouploader.startUpload();
	});
}