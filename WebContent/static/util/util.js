/* 时间格式 */
function formattime(timestr) {
	var date = new Date(timestr);
	var year = date.getFullYear();
	var month = date.getMonth() + 1;
	var dd = date.getDate();
	var hour = date.getHours();
	var minute = date.getMinutes();
	var second = date.getSeconds();

	if (month < 10) {
		month = "0" + month;
	}
	if (dd < 10) {
		dd = "0" + dd;
	}
	if (hour < 10) {
		hour = "0" + hour;
	}
	if (minute < 10) {
		minute = "0" + minute;
	}
	if (second < 10) {
		second = "0" + second;
	}
	return year + "-" + month + "-" + dd + " " + hour + ":" + minute;
	/*
	 * return year + "-" + month + "-" + dd + " " + hour + ":" + minute + ":" +
	 * second;
	 */
}

function formattimeDate(timestr) {
	var date = new Date(timestr);
	var year = date.getFullYear();
	var month = date.getMonth() + 1;
	var dd = date.getDate();
	var hour = date.getHours();
	var minute = date.getMinutes();
	var second = date.getSeconds();

	if (month < 10) {
		month = "0" + month;
	}
	if (dd < 10) {
		dd = "0" + dd;
	}
	if (hour < 10) {
		hour = "0" + hour;
	}
	if (minute < 10) {
		minute = "0" + minute;
	}
	if (second < 10) {
		second = "0" + second;
	}
	return year + "-" + month + "-" + dd;
	/*
	 * return year + "-" + month + "-" + dd + " " + hour + ":" + minute + ":" +
	 * second;
	 */
}

jQuery.fn.showhlframe = function(type, subheight) {
	var maxheight = getWH() * 0.75;
	$($(this).children()[1]).css("height", "auto");
	$($(this).children()[1]).height(
			(type == "max" ? maxheight
					: ($(this).height() > maxheight ? maxheight : $(this)
							.height()))
					- (subheight == undefined ? 110 : subheight));
	$(this).css("margin-top", "-" + ($(this).height() / 2) + "px");
	$(this).show();
}

// 获取窗口宽度
function getWW() {
	var winWidth;
	// 获取窗口宽度
	if (window.innerWidth)
		winWidth = window.innerWidth;
	else if ((document.body) && (document.body.clientWidth))
		winWidth = document.body.clientWidth;
	// 通过深入 Document 内部对 body 进行检测，获取窗口大小
	if (document.documentElement && document.documentElement.clientHeight
			&& document.documentElement.clientWidth) {
		winWidth = document.documentElement.clientWidth;
	}
	return winWidth;
}

function getWH() {
	var winHeight;
	// 获取窗口宽度
	// 获取窗口高度
	if (window.innerHeight)
		winHeight = window.innerHeight;
	else if ((document.body) && (document.body.clientHeight))
		winHeight = document.body.clientHeight;
	// 通过深入 Document 内部对 body 进行检测，获取窗口大小
	if (document.documentElement && document.documentElement.clientHeight
			&& document.documentElement.clientWidth) {
		winHeight = document.documentElement.clientHeight;
	}
	return winHeight;
}

// xxx=xxx&xxx=xxx&aaa=111 转成对象
function getMap(queryString) {
	if (null != queryString) {
		parameters = {};
		var parameterArray = queryString.split("&");
		var length = parameterArray.length;
		for (var i = 0; i < length; i++) {
			var parameter = parameterArray[i];
			index = parameter.indexOf("=");
			var key = parameter.substring(0, index);
			var value = parameter.substring(index + 1);
			if (null != key && key.length > 0) {
				parameters[key] = value;
			}
		}
		return parameters;

	}
}
// 弹出框部分 根据name 提示错误
function showerror(name, str) {
	$(".hlframe form [name='" + name + "']").parent().next().show();
	$(".hlframe form [name='" + name + "']").parent().next().html(str);
	$(".hlframe form [name='" + name + "']").focus();
}

function hideerror(name) {
	$(".hlframe form [name='name']").parent().next().hide();
}

function hlinitcheckbox(name) {
	$(name).bind("change", function() {
		if ($(this).val() == "0") {
			$(this).val("1");
			$(this).parent().addClass("hlchecked");
		} else {
			$(this).val("0");
			$(this).parent().removeClass("hlchecked");
		}
	});
}
// 初始化多选框
function init_Bootcheckbox(class_all, class_item) {
	$(class_all).unbind("click");
	$(class_item).unbind("click");
	$(class_all).val("0");
	$(class_item).val("0");
	$(class_all).parent().removeClass('hlchecked');
	$(class_item).parent().removeClass('hlchecked');

	$(class_all).bind("click", function() {
		if ($(this).val() == "0") {
			$(this).val("1");
			$(this).parent().addClass("hlchecked");
			$(class_item).each(function() {
				$(this).val("1");
				$(this).parent().addClass('hlchecked');
			});

		} else {
			$(this).val("0");
			$(this).parent().removeClass("hlchecked");
			$(class_item).each(function() {
				$(this).val("0");
				$(this).parent().removeClass('hlchecked');
			});
		}
	});
	$(class_item).bind("click", function() {
		if ($(this).val() == "0") {
			$(this).val("1");
			$(this).parent().addClass("hlchecked");
		} else {
			$(this).val("0");
			$(this).parent().removeClass("hlchecked");
		}
		var flag = true;
		$(class_item).each(function() {
			if ($(this).val() == "0") {
				flag = false;
			}
		});
		if (flag) {
			$(class_all).val("1");
			$(class_all).parent().addClass('hlchecked');
		} else {
			$(class_all).val("0");
			$(class_all).parent().removeClass('hlchecked');
		}
	});
}

// 获取全选id生成字符串
function getIds(class_item) {
	var ids = "";
	var cbs = $(class_item);
	for (var i = 0; i < cbs.length; i++) {
		if ($(cbs[i]).val() == "1") {
			ids += ("," + $(cbs[i]).attr("checkid"));
		}
	}
	return ids.substr(1);
}

// 获取cookie的值
function getCookie(name) {
	var cookieArray = document.cookie.split("; "); // 得到分割的cookie名值对
	var cookie = new Object();
	for (var i = 0; i < cookieArray.length; i++) {
		var arr = cookieArray[i].split("="); // 将名和值分开
		if (arr[0] == name)
			return unescape(arr[1]); // 如果是指定的cookie，则返回它的值
	}
	return "";
}
// 设置cookie方法
function setCookie(key, val, time) {
	var date = new Date();
	var expiresDays = (time == undefined ? 99999999 : time);
	date.setTime(date.getTime() + expiresDays);
	document.cookie = key + "=" + val + ";expires=" + date.toGMTString();
}

function delCookie(name) {// 为了删除指定名称的cookie，可以将其过期时间设定为一个过去的时间
	var date = new Date();
	date.setTime(date.getTime() - 10000);
	document.cookie = name + "=a; expires=" + date.toGMTString();
}

// 获取当前相对路径的方法
function GetUrlRelativePath() {
	var url = document.location.toString();
	var arrUrl = url.split("//");
	var start = arrUrl[1].indexOf("/");
	var relUrl = arrUrl[1].substring(start);// stop省略，截取从start开始到结尾的所有字符
	if (relUrl.indexOf("?") != -1) {
		relUrl = relUrl.split("?")[0];
	}
	return relUrl;
}

// 检查时间格式
function checkTimeFormat(date) {
	var result = date
			.match(/((^((1[8-9]\d{2})|([2-9]\d{3}))(-)(10|12|0?[13578])(-)(3[01]|[12][0-9]|0?[1-9])$)|(^((1[8-9]\d{2})|([2-9]\d{3}))(-)(11|0?[469])(-)(30|[12][0-9]|0?[1-9])$)|(^((1[8-9]\d{2})|([2-9]\d{3}))(-)(0?2)(-)(2[0-8]|1[0-9]|0?[1-9])$)|(^([2468][048]00)(-)(0?2)(-)(29)$)|(^([3579][26]00)(-)(0?2)(-)(29)$)|(^([1][89][0][48])(-)(0?2)(-)(29)$)|(^([2-9][0-9][0][48])(-)(0?2)(-)(29)$)|(^([1][89][2468][048])(-)(0?2)(-)(29)$)|(^([2-9][0-9][2468][048])(-)(0?2)(-)(29)$)|(^([1][89][13579][26])(-)(0?2)(-)(29)$)|(^([2-9][0-9][13579][26])(-)(0?2)(-)(29)$))/);
	if (result == null) {
		return false;
	}
	return true;

}

function bytesToSize(bytes) {
	if (bytes === 0)
		return '0';
	var k = 1000, // or 1024
	sizes = [ 'B', 'KB', 'MB', 'GB', 'TB', 'PB', 'EB', 'ZB', 'YB' ], i = Math
			.floor(Math.log(bytes) / Math.log(k));

	return (bytes / Math.pow(k, i)).toPrecision(3) + ' ' + sizes[i];
}

String.prototype.trim = function() {
	return this.replace(/(^\s*)|(\s*$)/g, "");
}
String.prototype.ltrim = function() {
	return this.replace(/(^\s*)/g, "");
}
String.prototype.rtrim = function() {
	return this.replace(/(\s*$)/g, "");
}

String.prototype.startsWith = function(prefix) {
	return this.slice(0, prefix.length) === prefix;
}

String.prototype.endsWith = function(suffix) {
	return this.indexOf(suffix, this.length - suffix.length) !== -1;
}
// 手机正则
function checkPhone(phone) {
	if (!(/^1(3|4|5|7|8)\d{9}$/.test(phone))) {
		return false;
	}
	return true;
}
// 邮箱正则
function isEmail(str) {
	var reg = /^(\w-*\.*)+@(\w-?)+(\.\w{2,})+$/;
	return reg.test(str);
}

function tounicode(data) {
	if (data == '')
		return '';
	var str = '';
	for (var i = 0; i < data.length; i++) {
		str += "\\u" + parseInt(data[i].charCodeAt(0), 10).toString(16);
	}
	return str;
}

function tohanzi(data) {
	return eval("'" + data + "'");
}

function isIE() { // ie?
	if (!!window.ActiveXObject || "ActiveXObject" in window)
		return true;
	else
		return false;
}