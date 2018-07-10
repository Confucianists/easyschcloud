function yjyupload(baseurl, param) {
	initevent();
	var myparam = param; // 配置参数 ，包括plupload参数和自己封装内容参数

	if (param.type == undefined) // 默认情况
	{
		myparam.url = param.url == undefined ? baseurl + "/media/upload"
				: param.url;
		// 文件参数名
		myparam.paramname = param.paramname == undefined ? "file"
				: param.paramname;
		// 其它参数
		myparam.otherparam = param.otherparam == undefined ? {}
				: param.otherparam;

	} else if (param.type == '2') // 上传到七牛
	{
		myparam.url = param.url == undefined ? "http://up.qiniu.com"
				: param.url;
		// 文件参数名
		myparam.paramname = param.paramname == undefined ? "file"
				: param.paramname;
		// 其它参数
		myparam.otherparam = param.otherparam == undefined ? {}
				: param.otherparam;
		// 其它参数
		getToken();

	} else if (param.type == '1') // 上传到服务器
	{
		myparam.url = param.url == undefined ? baseurl + "/media/upload"
				: param.url;
		// 文件参数名
		myparam.paramname = param.paramname == undefined ? "file"
				: param.paramname;
		// 其它参数
		myparam.otherparam = param.otherparam == undefined ? {}
				: param.otherparam;

	}
	// 缩略图大小
	myparam.tx = myparam.otherparam.tx = param.tx == undefined ? 200 : param.tx;
	myparam.ty = myparam.otherparam.ty = param.ty == undefined ? 200 : param.ty;
	
	// 限制图大小
	myparam.x  = param.x == undefined ? 0 : param.x;
	myparam.y =  param.y == undefined ? 0 : param.y;
	// 多选
	myparam.selects = param.selects == undefined ? false : param.selects;
	// 文件类型
	myparam.filters = param.filters == undefined ? {
		max_file_size : '500mb'
	} : {
		 max_file_size : '500mb',
		 mime_types : param.filters
	 }
		
	//是否预览模式
	myparam.view=param.view==undefined?false:param.view;
//	 {
//		 max_file_size : '10mb',
//		 mime_types : [ {
//			 title : "Image files",
//			 extensions : "jpg,gif,png"
//		 }, {
//			 title : "Zip files",
//			 extensions : "zip"
//		 } ]
//	 }
	var uploader; // plupload 对象
	var selectFileBtnName; // 选择文件按钮对象
	var mycontain; // 容器对象
	var processdata = 0;// 进度条数据
	var currentID;// 当前正在上传的显示框的ID
	var showqiniu = "http://lyfpub.qiniudn.com/";
	var FileData = [];
	init();

	function init() {
		if (myparam.contain == 'undefined'
				|| $("#" + myparam.contain).size() == 0) {
			if(typeof(window.parent.showAlert)!="undefined")
			{
			window.parent.showAlert("容器不存在!");
			}
		else{
			alert("容器不存在!")
			}
			return;
		}
		mycontain = $("#" + myparam.contain);
		var sfname = "upload" + new Date().getTime();
		var stname = "start" + new Date().getTime();
		var deletename="delete"+ new Date().getTime();
		// 添加内容
		mycontain
				.html('<div class="uploaddiv"> '+
						(myparam.view?"":('<div class="tooldiv"> <div class="titlediv"> <a id="'
						+ sfname
						+ '" href="javascript:;" class="uploadbtn" style="background-color: #4d90fe;">选择文件</a> <a id="'
						+ stname
						+ '" href="javascript:;" class="uploadbtn" style="background-color: #4d90fe; margin-left: 110px;">开始上传</a> <a href="javascript:;" id="'
						+deletename+
						'" class="uploadbtn" style="background-color: #d84a38; margin-left: 220px;">删除</a> </div> </div>'))
						+' <div style="width: 100%"> <div style="display:none" class="tipdiv">格式: bmp, png, jpeg, jpg, gif</div>'
						+ (myparam.selects ? '<ul class="contentul"></ul>'
								: '<div class="contentdiv"></div>')
						+ '</div></div><div id="allcover" class="allcover"></div> <div class="allimg" onclick="hide()"> <div id="showimgdiv" >'
						+ '<div class="leftdiv" onclick="previousimg(\''+myparam.contain+'\',\''+baseurl+'\')"></div> <div class="rightdiv" onclick="nextimg(\''+myparam.contain+'\',\''+baseurl+'\')"></div>'
						+ ' <img id="showimg" onclick="stopParentClick()" src="" style="width: 100%; height: 100%;z-index: 200"> </div> <img id="loadimg" src="'+baseurl+'/static/yjyupload/img/load.jpg" style="width: 50px; height: 50px; top: 50%; left: 50%; margin-left: -25px; margin-top: -25px; position: absolute;"></div>')
		if (myparam.selects) {
			var stylename = "placeHolderwh" + new Date().getTime();
			addCSS('.' + stylename + '{width:' + myparam.tx + 'px;height:'
					+ (myparam.ty + 30) + 'px;}')
			$("#"+myparam.contain+" .contentul")
					.dragsort(
							{
								dragSelector : "div.divs div.movediv",
								dragBetween : false,
								placeHolderTemplate : "<li class=\"contentli\"><div class='placeHolder "
										+ stylename + "'></div></li>"
							});

		}
		if (param.tip != undefined) {
			$("#"+myparam.contain+" .tipdiv").css("display", "block");
			$("#"+myparam.contain+" .tipdiv").html(param.tip);
		}
		// mycontain.html("<a id="+sfname+" >选择文件</a><a id="+stname+" >开始上传</a>
		// ");
		selectFileBtnName = $("#" + sfname);
		if(!myparam.view){
		document.getElementById(stname).onclick = function() {
			uploader.start();
			return false;
		};
		$("#"+deletename).bind("click",function (){
			if (myparam.selects){
			$("#"+myparam.contain+" .selectdiv").each(function(){
				if($(this).attr("isselect")=="1")
					{
					uploader.removeFile($(this).attr("fileid"));
					$(this).parent().parent().parent().remove();
					}
			});
			}
			else
				{ 
				if($("#"+myparam.contain+" .contentdiv").html()!=""){
				var fileid=$($("#"+myparam.contain+" .contentdiv").children()[0]).attr("fileid");
				if(fileid!=undefined){
					uploader.removeFile(fileid);
					}
					$("#"+myparam.contain+" .contentdiv").html("");
				}
				
				}
		});
		// alert(myparam);
		uploader = new plupload.Uploader(
				{
					runtimes : 'html5,flash,silverlight,html4',
					browse_button : sfname, // you can pass in id...
					container : document.getElementById(myparam.contain), // ...
					// or
					// DOM
					// Element
					// itself
					url : myparam.url, // 上传URL
					flash_swf_url : 'js/Moxie.swf',
					silverlight_xap_url : 'js/Moxie.xap',
					file_data_name : myparam.paramname, // 上传文件参数名 (上传七牛时为file)
					multi_selection : myparam.selects, // 多选
					multipart_params : myparam.otherparam,
					// unique_names:myparam.isradomname,
					 filters :myparam.filters,

					init : {
						PostInit : function() {

						},

						FilesAdded : function(up, files) {// 当选择图片时触发
							plupload
									.each(
											files,
											function(file) {
												var _url;
												var type = 3;
												// 判断文件类型
												// ===================================
												if (file.type.indexOf("image") != -1) {
													_url = getlocalimgSRC(file
															.getSource()
															.getSource());
													var tmpimg=new Image();
													type = 1;
													tmpimg.onload=function(){
														if(myparam.x!=0&&myparam.y!=0)
															{
															if(tmpimg.width!=myparam.x ||tmpimg.height!=myparam.y )
																{
																if(typeof(window.parent.showAlert)!="undefined")
																{
																window.parent.showAlert("图片分辨率错误,请重新上传!!");
																}
															else{
																alert("图片分辨率错误,请重新上传!!")
																}
																return;
																}
															}
														addfile(file,type,_url);
													};
													tmpimg.src=_url;
												} else if (file.type
														.indexOf("video") != -1) {
													_url = baseurl
															+ "/static/yjyupload/img/video.png";
													type = 2;
													addfile(file,type,_url);
												} else {
													_url = baseurl
															+ "/static/yjyupload/img/file.jpg";
													type = 3;
													addfile(file,type,_url);
												}
												
												 
											});
							
						},
						UploadFile : function(up, file) {
							$("#co" + file.id).css("display", "block");
						},
						UploadProgress : function(up, file) {
							
							// alert(new Date().getTime());
//							$("#co" + file.id).animate({
//								opacity : (100 - file.percent) / 100
//							}, 500, function() {
//								//if (file.percent == 100) {
//								//	$("#co" + file.id).css("display", "none");
//								//}
//							});
							$("#co" + file.id).css("opacity",(100 - file.percent) / 100);
							if (file.percent == 100) {
									$("#co" + file.id).css("display", "none");
								}
						},
						FileUploaded : function(up, file, res) {

							finish(file, res);
						},
						Error : function(up, err) {
							if(err.code=-601)
								{
								if(typeof(window.parent.showAlert)!="undefined")
									{
									window.parent.showAlert("文件格式不正确!");
									}
								else{
									alert("文件格式不正确!")
									}
								}else
									{
										if(typeof(window.parent.showAlert)!="undefined")
										{
											window.parent.showAlert("发生错误!"+err.message);
										}
										else
											{
											alert("发生错误!"+err.message);
											}
									}
							
							//document.getElementById('console').innerHTML += "\nError #"
									//+ err.code + ": " + err.message; 
						}
					}

				});
		uploader.init();
		}

	
		mycontain.css("position", "static");

	}

	function addfile(file,type,_url)
	{
		if (myparam.selects) // 多选
		{
			$("#"+myparam.contain+" .contentul")
					.append(
							'<li class="contentli"> <div class="divs" style="width: '
									+ myparam.tx
									+ 'px; height: '
									+ (myparam.ty + 30)
									+ 'px;"><div class="movediv" style="width: '
									+ myparam.tx
									+ 'px; height: '
									+ myparam.ty
									+ 'px;"> <img id="img'
									+ file.id
									+ '" isok="0" type="'
									+ type
									+ '" class="upimglist"  src="'
									+ _url
									+ '"> <div id="co'
									+ file.id
									+ '" style="display: none" class="cover"></div> <div id="okdiv'
									+ file.id
									+ '" class="okdiv" style="display: none"></div> </div> <div class="imgtool"> <div id="selectdiv'
									+file.id+
									'" class="selectdiv" fileid="'
									+ file.id
									+ '" isselect="0" style=""></div> <div class="viewdiv" onclick="showimgview(\''
									+ file.id
									+ '\',\''
									+ baseurl
									+ '\',\''+myparam.contain+'\')" ></div> <div id="deletediv'
									+ file.id
									+ '" isselect="0" fileid="'
									+ file.id
									+ '"  class="deletediv" ></div> </div> </div> </li>');
			$("#deletediv" + file.id)
					.bind("click",function() {
								uploader.removeFile($(this).attr("fileid"));
								$(this)
										.parent()
										.parent()
										.parent()
										.remove();
							});
			$("#selectdiv" + file.id)
			.bind("click",function() {
				if($(this).attr("isselect")!="1"){
						$(this).attr("isselect","1");
						$(this).css("background","url('"+baseurl+"/static/yjyupload/img/check2.png') no-repeat center");
				}
				else
					{
						$(this).attr("isselect","0");
						$(this).css("background","url('"+baseurl+"/static/yjyupload/img/check1.png') no-repeat center");
					}
					});
			$("#selectdiv" + file.id)
			.bind("mouseover",function() {
						if($(this).attr("isselect")!="1")
							{
							$(this).css("background","url('"+baseurl+"/static/yjyupload/img/check2.png') no-repeat center");
							}
					});
			$("#selectdiv" + file.id)
			.bind("mouseout",function() {
						if($(this).attr("isselect")!="1")
							{
							$(this).css("background","url('"+baseurl+"/static/yjyupload/img/check1.png') no-repeat center");
							}
					});
		} else {
			$( "#"+myparam.contain+" .contentdiv")
					.html(
							'<div fileid="'+
							file.id
							+'" style="width: '
									+ myparam.tx
									+ 'px; height: '
									+ myparam.ty
									+ 'px; border: 1px solid #e7e7eb; margin: 20px auto;"> <div  style="position: absolute; width: '
									+ myparam.tx
									+ 'px; height: '
									+ myparam.ty
									+ 'px;"> <img id="img'
									+ file.id
									+ '" isok="0" class="upimglist" type="'
									+ type
									+ '"  onclick="showimgview(\''
									+ file.id
									+ '\',\''
									+ baseurl
									+ '\',\''+myparam.contain+'\')" src="'
									+ _url
									+ '">  <div id="co'
									+ file.id
									+ '" class="cover" style="display: none"></div><div id="okdiv'
									+ file.id
									+ '" style="display:none" class="okdiv"></div> </div>  </div>');
		}
		if(typeof(window.parent.iFrameHeight)=="function")
		{
		window.parent.iFrameHeight();
		}
	}
	
	// 上传完成后
	function finish(file, res) {
		if (param.type == undefined) {

			data.fileid = file.id;
			FileData.push(data);
			$("#img" + file.id).attr("isok", "1");
			$("#img" + file.id).attr("url", baseurl + data.url);
			$("#img" + file.id).attr("thurl", baseurl + data.thurl);
			$("#img" + file.id).attr("mid", data.mid);
			if (file.type.indexOf("image") != -1) {
				$("#img" + file.id).attr("type", "1");
			} else if (file.type.indexOf("video") != -1) {
				$("#img" + file.id).attr("type", "2");
				$("#img" + file.id).attr("src", baseurl + data.thurl);
			} else {
				$("#img" + file.id).attr("type", "3");
			}
			$("#okdiv" + file.id).css("display", "block");
		} else if (param.type == "2") {
			var data = $.parseJSON(res.response);
			$.post(baseurl + "/media/save", {
				url : data.key,
				thurl : data.key,
				type : file.type,
				isqiniu : 1,
				tx : myparam.tx,
				ty : myparam.ty
			}, function(d) {
				if (d) {
					d.fileid = file.id;
					FileData.push(d);
					$("#img" + file.id).attr("isok", "1");
					$("#img" + file.id).attr("url", d.url);
					$("#img" + file.id).attr("thurl", d.thurl);
					$("#img" + file.id).attr("mid", d.mid);
					if (file.type.indexOf("image") != -1) {
						$("#img" + file.id).attr("type", "1");
					} else if (file.type.indexOf("video") != -1) {
						$("#img" + file.id).attr("type", "2");
					} else {
						$("#img" + file.id).attr("type", "3");
					}
					$("#okdiv" + file.id).css("display", "block");
				} else {
					if(typeof(window.parent.showAlert)!="undefined")
					{
					window.parent.showAlert("保存记录失败");
					}
				else{
					alert("保存记录失败")
					}
				}
			});
		} else if (param.type == "1") {
			var data = $.parseJSON(res.response);
			data.fileid = file.id;
			FileData.push(data);
			$("#img" + file.id).attr("isok", "1");
			$("#img" + file.id).attr("url", baseurl + data.url);
			$("#img" + file.id).attr("thurl", baseurl + data.thurl);
			$("#img" + file.id).attr("mid", data.mid);
			if (file.type.indexOf("image") != -1) {
				$("#img" + file.id).attr("type", "1");
			} else if (file.type.indexOf("video") != -1) {
				$("#img" + file.id).attr("type", "2");
				$("#img" + file.id).attr("src", baseurl + data.thurl);
			} else {
				$("#img" + file.id).attr("type", "3");
			}
			$("#okdiv" + file.id).css("display", "block");
		}

	}

	function getToken() {
		$.post(baseurl + "/qiniu/uptoken", function(d) {
			if (d) {
				myparam.otherparam.token = d.uptoken;
			} else {
				if(typeof(window.parent.showAlert)!="undefined")
				{
				window.parent.showAlert("获取token失败!");
				}
			else{
				alert("获取token失败!")
				}
			}

		});
	}

	function getlocalimgSRC(file) {
		return window.URL.createObjectURL(file);
	}

	this.getData=function ()
	{
		var data=[];
		$("#"+myparam.contain+" .upimglist").each(function(){
			if($(this).attr("isok")=="1")
				{
				var d={};
				d.id=$(this).attr("mid");
				d.url=$(this).attr("url");
				d.thurl=$(this).attr("thurl");
				d.type=$(this).attr("type");
				data.push(d);
				}
		});
		return data;
	}
	this.isfinish=function(){ 
	var data=$("#"+myparam.contain+" .upimglist");
		for(var i=0;i<data.length;i++)
		{
		 	if($(data[i]).attr("isok")=="0")
		 	{
		 	return false;
		 	}
		}
		return true;
	}
	this.setData=function (data)
	{
	if(data!=null){
		if (myparam.selects) // 多选
		{
			for(var i=0;i<data.length;i++)
			{
			$("#"+myparam.contain+" .contentul").append('<li class="contentli"> <div class="divs" style="width: '
			+ myparam.tx
																			+ 'px; height: '
																			+ (myparam.ty + 30)
																			+ 'px;"><div class="movediv" style="width: '
																			+ myparam.tx
																			+ 'px; height: '
																			+ myparam.ty
																			+ 'px;"> <img id="img'
																			+ data[i].id
																			+ '" mid="'+data[i].id+'" isok="1" type="'
																			+ data[i].type
																			+ '" class="upimglist"  src="'
																			+ isqiniu(data[i].thumbnailurl,baseurl)
																			+ '" url="'+isqiniu(data[i].url,baseurl)+'" thurl="'+isqiniu(data[i].thumbnailurl,baseurl)+'"> <div id="co'
																			+ data[i].id
																			+ '" style="display: none" class="cover"></div> <div id="okdiv'
																			+ data[i].id
																			+ '" class="okdiv" style="display: block"></div> </div> <div class="imgtool"> <div id="selectdiv'
																			+data[i].id+
																			'" class="selectdiv" fileid="'
																			+ data[i].id
																			+ '" isselect="0" style="'+(myparam.view==true?'visibility: hidden':'')+'"></div> <div class="viewdiv" onclick="showimgview(\''
																			+ data[i].id
																			+ '\',\''
																			+ baseurl
																			+ '\',\''+myparam.contain+'\')" ></div> <div id="deletediv'
																			+ data[i].id
																			+ '" isselect="0" fileid="'
																			+ data[i].id 
																			+ '"  class="deletediv" style="'+(myparam.view==true?'visibility: hidden':'')+'"></div> </div> </div> </li>');
													$("#deletediv" + data[i].id)
															.bind("click",function() {
																		uploader.removeFile($(this).attr("fileid"));
																		$(this)
																				.parent()
																				.parent()
																				.parent()
																				.remove();
																	});
													$("#selectdiv" + data[i].id)
													.bind("click",function() {
														if($(this).attr("isselect")!="1"){
																$(this).attr("isselect","1");
																$(this).css("background","url('"+baseurl+"/static/yjyupload/img/check2.png') no-repeat center");
														}
														else
															{
																$(this).attr("isselect","0");
																$(this).css("background","url('"+baseurl+"/static/yjyupload/img/check1.png') no-repeat center");
															}
															});
													$("#selectdiv" + data[i].id)
													.bind("mouseover",function() {
																if($(this).attr("isselect")!="1")
																	{
																	$(this).css("background","url('"+baseurl+"/static/yjyupload/img/check2.png') no-repeat center");
																	}
															});
													$("#selectdiv" + data[i].id)
													.bind("mouseout",function() {
																if($(this).attr("isselect")!="1")
																	{
																	$(this).css("background","url('"+baseurl+"/static/yjyupload/img/check1.png') no-repeat center");
																	}
															});
															}
												} else {
													if(data.length>0){
													$(".contentdiv")
															.html(
																	'<div style="width: '
																			+ myparam.tx
																			+ 'px; height: '
																			+ myparam.ty
																			+ 'px; border: 1px solid #e7e7eb; margin: 20px auto;"> <div style="position: absolute; width: '
																			+ myparam.tx
																			+ 'px; height: '
																			+ myparam.ty
																			+ 'px;"> <img id="img'
																			+ data[0].id
																			+ '" isok="1" mid="'+data[0].id+'" url="'+isqiniu(data[0].url,baseurl)+
																			'" thurl="'+isqiniu(data[0].thumbnailurl,baseurl)+'" class="upimglist" type="'
																			+ data[0].type
																			+ '"  onclick="showimgview(\''
																			+ data[0].id
																			+ '\',\''
																			+ baseurl
																			+ '\',\''+myparam.contain+'\')" src="'
																			+ isqiniu(data[0].thumbnailurl,baseurl)
																			+ '">  <div id="co'
																			+ data[0].id
																			+ '" class="cover" style="display: none"></div><div id="okdiv'
																			+ data[0].id
																			+ '" style="display:block" class="okdiv"></div> </div>  </div>');
												}
												}
	}
	}
}

function nextimg(basediv,baseurl) {
	stopParentClick();
	var isend=false;
	$("#"+basediv+" .upimglist").each(function(i,item) {
		
		if(!isend){
			if(currentimgid==$(this).attr("id"))
			{
				isend=true;
				
				if(i==$("#"+basediv+" .upimglist").size()-1)
					{showimgview($($("#"+basediv+" .upimglist")[0]).attr("id").substring(3,$($("#"+basediv+" .upimglist")[0]).attr("id").length),baseurl,basediv);
					}
				else
						{
				showimgview($($("#"+basediv+" .upimglist")[i+1]).attr("id").substring(3,$($("#"+basediv+" .upimglist")[i+1]).attr("id").length),baseurl,basediv);
						}
			}
		}
	});
}
function previousimg(basediv,baseurl) {
	stopParentClick();
	var isend=false;
	$("#"+basediv+" .upimglist").each(function(i,item) {
		if(!isend){
			if(currentimgid==$(this).attr("id"))
			{
				isend=true;
				
				if(i==0)
					{showimgview($($("#"+basediv+" .upimglist")[$("#"+basediv+" .upimglist").size()-1]).attr("id").substring(3,$($("#"+basediv+" .upimglist")[$("#"+basediv+" .upimglist").size()-1]).attr("id").length),baseurl,basediv);
					}
				else
						{
				showimgview($($("#"+basediv+" .upimglist")[i-1]).attr("id").substring(3,$($("#"+basediv+" .upimglist")[i-1]).attr("id").length),baseurl,basediv);
						}
			}
		}	
			
	});
		
}
var currentimgid;

// 显示预览图
function showimgview(imgid, baseurl,basediv) {
	var type = $("#"+basediv+" #img" + imgid).attr("type");
	if (type == "1") {
		if ($("#"+basediv+" .upimglist").size() > 1) {
			$("#"+basediv+" .leftdiv").css("display", "block");
			$("#"+basediv+" .rightdiv").css("display", "block");
		} else {
			$( "#"+basediv+" .leftdiv").css("display", "none");
			$("#"+basediv+" .rightdiv").css("display", "none");
		}
		currentimgid = "img"+imgid;
		var img = new Image();
		$("#"+basediv+" #loadimg").css("display", "block");
		img.onload = function() {
			setShowImage(this, img,basediv);
		}
		if ($("#"+basediv+" #img" + imgid).attr("isok") == "1") {
			img.src = $("#img" + imgid).attr("url");
		} else {
			img.src = $("#img" + imgid).attr("src");
		}

		getWH();

		$("#"+basediv+" .allimg").css("display", "block");
		$("#"+basediv+" .allcover").css("display", "block");
	} else if (type == "2") {
		if ($("#img" + imgid).attr("isok") == "1") {
			pname = [];
			pname.push("url");

			pv = [];
			pv.push($("#img" + imgid).attr("url"))
			// openNewPageWithPostData(baseurl+"media/showvideo",'_blank',pname,pv);
			openPostWindow(baseurl + "media/showvideo", "aaa",
					$("#img" + imgid).attr("url"));
			// window.open($("#img" + imgid).attr("url"), '_blank');
		} else {
			// window.open($("#img"+imgid).attr("src"),'_blank');
		}
	} else {
		_url = baseurl + "/static/yjyupload/img/file.jpg";
	}

}
function setShowImage($this, img,basediv) {
	var nowwith;
	var nowheight;
	if (img.width < winWidth && img.height <= winHeight) {
		nowwith = img.width;
		nowheight = img.height;
	} else if (img.width >= winWidth && img.height <= winHeight) {
		nowwith = winWidth * 0.8;
		nowheight = nowwith * (img.height / img.width);
	} else if (img.width < winWidth && img.height > winHeight) {
		nowheight = winHeight * 0.8;
		nowwith = nowheight * (img.width / img.height);
	} else if (img.width >= winWidth && img.height > winHeight) {
		if (img.width / img.height >= winWidth / winHeight) {
			nowwith = winWidth * 0.8;
			nowheight = nowwith * (img.height / img.width);
		} else {
			nowheight = winHeight * 0.8;
			nowwith = nowheight * (img.width / img.height);
		}
	}
	$("#"+basediv+" #showimgdiv").css("width", nowwith + "px");
	$("#"+basediv+" #showimgdiv").css("height", nowheight + "px");
	$("#"+basediv+" #showimgdiv").css("margin-left", "-" + (nowwith / 2) + "px");
	$("#"+basediv+" #showimgdiv").css("margin-top", "-" + (nowheight / 2) + "px");
	$("#"+basediv+" #showimg").attr("src", $this.src);
	$("#"+basediv+" #loadimg").css("display", "none");
	$("#"+basediv+" #showimgdiv").css("display", "block");
}
function hide() {
	$(".allimg").css("display", "none");
	$(".allcover").css("display", "none");
}
var winWidth;
var winHeight;
function getWH() {
	// 获取窗口宽度
	if (window.innerWidth)
		winWidth = window.innerWidth;
	else if ((document.body) && (document.body.clientWidth))
		winWidth = document.body.clientWidth;
	// 获取窗口高度
	if (window.innerHeight)
		winHeight = window.innerHeight;
	else if ((document.body) && (document.body.clientHeight))
		winHeight = document.body.clientHeight;
	// 通过深入 Document 内部对 body 进行检测，获取窗口大小
	if (document.documentElement && document.documentElement.clientHeight
			&& document.documentElement.clientWidth) {
		winHeight = document.documentElement.clientHeight;
		winWidth = document.documentElement.clientWidth;
	}
}

function stopParentClick() {
	var e = window.event || e;
	if (e.stopPropagation)
		e.stopPropagation();
	else
		e.cancelBubble = true;
}
// 兼容FF一些方法 配合stopParentClick使用
function initevent() {
	var html;
	// 判断是不是火狐
	if (/Firefox/.test(window.navigator.userAgent)) {

		window.__defineGetter__("event", function() {
			// 兼容Event对象
			var o = arguments.callee;
			do {
				if (o.arguments[0] instanceof Event)
					return o.arguments[0];
			} while (o = o.caller);
			return null;
		});

	}

}
function openNewPageWithPostData(postAddress, opentype, paramNames, paramValues) {
	var newWindow = window.open(postAddress, opentype);
	if (!newWindow) {
		return false;
	}

	var postDataHtml = "<html><head></head><body>";
	postDataHtml = postDataHtml
			+ "<form id='postDataForm' method='post' action='" + postAddress
			+ "'>";

	if (paramNames && paramValues && (paramNames.length == paramValues.length)) {
		for (var i = 0; i < paramNames.length; i++) {
			postDataHtml = postDataHtml + "<input type='hidden' name='"
					+ paramNames[i] + "' value='" + paramValues[i] + "'/>";
		}
	}

	postDataHtml = postDataHtml
			+ "</form><script type=\"text/javascript\"> document.getElementById(\"postDataForm\").submit()<script><body><html>";
	newWindow.document.write(html);
	return newWindow;
}

function openWindow(name) {
	window
			.open(
					'about:blank',
					name,
					'height=500,width=600,top=0,left=0,toolbar=no,menubar=no,scrollbars=yes, resizable=no,location=no, status=no')
}
function openPostWindow(url, name, data1) {
	var tempForm = document.createElement("form");
	tempForm.id = "tempForm1";
	tempForm.method = "post";
	tempForm.action = url;
	tempForm.target = name;

	var hideInput = document.createElement("input");
	hideInput.type = "hidden";
	hideInput.name = "url"
	hideInput.value = data1;
	tempForm.appendChild(hideInput);

	if (window.addEventListener) {
		tempForm.addEventListener("onsubmit", function() {
			openWindow(name);
		});
	} else if (window.attachEvent) {
		tempForm.attachEvent("onsubmit", function() {
			openWindow(name);
		});
	}
	document.body.appendChild(tempForm);

	// tempForm.fireEvent("onsubmit");
	tempForm.submit();
	document.body.removeChild(tempForm);
}

function addCSS(cssText) {
	var style = document.createElement('style'), // 创建一个style元素
	head = document.head || document.getElementsByTagName('head')[0]; // 获取head元素
	style.type = 'text/css'; // 这里必须显示设置style元素的type属性为text/css，否则在ie中不起作用
	if (style.styleSheet) { // IE
		var func = function() {
			try { // 防止IE中stylesheet数量超过限制而发生错误
				style.styleSheet.cssText = cssText;
			} catch (e) {

			}
		}
		// 如果当前styleSheet还不能用，则放到异步中则行
		if (style.styleSheet.disabled) {
			setTimeout(func, 10);
		} else {
			func();
		}
	} else { // w3c
		// w3c浏览器中只要创建文本节点插入到style元素中就行了
		var textNode = document.createTextNode(cssText);
		style.appendChild(textNode);
	}
	head.appendChild(style); // 把创建的style元素插入到head中
}

function isqiniu(str,baseurl)
{
	if(str.indexOf("http:")==0)
	{
		return str;
	}
	else
	{
		return baseurl+"/"+str;
	}
}
// function setImagePreview(id, file) {
//
// var imgObjPreview = document.getElementById(id);
// if (file) {
//
// // 火狐7以上版本不能用上面的getAsDataURL()方式获取，需要一下方式
// imgObjPreview.src = window.URL.createObjectURL(file);
// } else {
// alert("IE");
// // IE下，使用滤镜
// docObj.select();
// var imgSrc = document.selection.createRange().text;
// var localImagId = document.getElementById(id);
// // 必须设置初始大小
// localImagId.style.width = "150px";
// localImagId.style.height = "180px";
// // 图片异常的捕捉，防止用户修改后缀来伪造图片
// try {
// localImagId.style.filter =
// "progid:DXImageTransform.Microsoft.AlphaImageLoader(sizingMethod=scale)";
// localImagId.filters
// .item("DXImageTransform.Microsoft.AlphaImageLoader").src = imgSrc;
// } catch (e) {
// alert("您上传的图片格式不正确，请重新选择!");
// return false;
// }
// imgObjPreview.style.display = 'none';
// document.selection.empty();
// }
// return true;
// }
