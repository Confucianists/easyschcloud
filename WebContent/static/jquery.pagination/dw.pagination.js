jQuery.fn.pagination = function(url, opts, params) {
	var maxentries = 1;
	var posturl = url;
	var self = new Object();
	opts = jQuery.extend({
		items_per_page : 10,
		current_page : 0,
		num_edge_entries : 1, // 边缘页数
		num_display_entries : 3, // 主体页数
		link_to : "javascript:void(0);",
		prev_text : "上一页",
		next_text : "下一页",
		ellipse_text : "...",
		prev_show_always : true,
		next_show_always : true,
		pagesize_list : [ 5, 10, 15, 20 ],
		callback : function() {
			return false;
		}
	}, opts || {});
	self.opts = opts;
	var loadhtml = '<div id="loadingToast" class="" ><div class="mask_transparent"></div><div class="toast"><div class="pagin_loading"><div class="loading_leaf loading_leaf_0"></div><div class="loading_leaf loading_leaf_1"></div><div class="loading_leaf loading_leaf_2"></div><div class="loading_leaf loading_leaf_3"></div><div class="loading_leaf loading_leaf_4"></div><div class="loading_leaf loading_leaf_5"></div><div class="loading_leaf loading_leaf_6"></div><div class="loading_leaf loading_leaf_7"></div><div class="loading_leaf loading_leaf_8"></div><div class="loading_leaf loading_leaf_9"></div><div class="loading_leaf loading_leaf_10"></div><div class="loading_leaf loading_leaf_11"></div></div></div></div>'

	/**
	 * 计算最大分页显示数目
	 */
	function numPages() {
		return Math.ceil(maxentries / opts.items_per_page);
	}
	/**
	 * 极端分页的起始和结束点，这取决于current_page 和 num_display_entries.
	 * 
	 * @返回 {数组(Array)}
	 */
	function getInterval() {
		var ne_half = Math.ceil(opts.num_display_entries / 2);
		var np = numPages();
		var upper_limit = np - opts.num_display_entries;
		var start = current_page > ne_half ? Math.max(Math.min(current_page
				- ne_half, upper_limit), 0) : 0;
		var end = current_page > ne_half ? Math.min(current_page + ne_half, np)
				: Math.min(opts.num_display_entries, np);
		return [ start, end ];
	}
	// 数据处理 LYF
	function dodata(pageid, jq, size) {
		if (!params) {
			params = {};
		}
		params.page = pageid + 1;
		params.pagesize = size
		showload();
		$.post(posturl, params, function(d) {
			if (d.totalElements == undefined) {
				window.location.reload();
			}
			maxentries = d.totalElements;
			// 所有初始化完成，绘制链接
			drawLinks();
			opts.callback(d);
			hidload();
		});
	}

	/**
	 * 分页链接事件处理函数
	 * 
	 * @参数 {int} page_id 为新页码
	 */
	function pageSelected(page_id, evt) {
		current_page = page_id;
		// drawLinks();
		var continuePropagation = dodata(page_id, panel, opts.items_per_page);
		if (!continuePropagation) {
			if (evt.stopPropagation) {
				evt.stopPropagation();
			} else {
				evt.cancelBubble = true;
			}
		}
		return continuePropagation;
	}

	/**
	 * 此函数将分页链接插入到容器元素中
	 */
	function drawLinks() {
		panel.empty();
		var fenye = $("<div class='fenye'></div>").appendTo(panel);
		var yeshu = $("<div class='yeshu'></div>").appendTo(panel);
		var interval = getInterval();
		var np = numPages();
		// 这个辅助函数返回一个处理函数调用有着正确page_id的pageSelected。
		var getClickHandler = function(page_id) {
			return function(evt) {
				return pageSelected(page_id, evt);
			}
		}
		// 辅助函数用来产生一个单链接(如果不是当前页则产生span标签)
		var appendItem = function(page_id, appendopts) {
			page_id = page_id < 0 ? 0 : (page_id < np ? page_id : np - 1); // 规范page
			// id值
			appendopts = jQuery.extend({
				text : page_id + 1,
				classes : ""
			}, appendopts || {});
			if (page_id == current_page) {
				var lnk = jQuery("<span class='current'>" + (appendopts.text)
						+ "</span>");
			} else {
				var lnk = jQuery("<a>" + (appendopts.text) + "</a>").bind(
						"click", getClickHandler(page_id)).attr('href',
						opts.link_to.replace(/__id__/, page_id));
			}
			if (appendopts.classes) {
				lnk.addClass(appendopts.classes);
			}
			fenye.append(lnk);
		}

		/**
		 * // 产生起始点 if (interval[0] > 0 && opts.num_edge_entries > 0) { var end =
		 * Math.min(opts.num_edge_entries, interval[0]); for(var i=0; i<end;
		 * i++) { appendItem(i); } if(opts.num_edge_entries < interval[0] &&
		 * opts.ellipse_text) { jQuery("<span>"+opts.ellipse_text+"</span>").appendTo(fenye); } } //
		 * 产生内部的些链接 for(var i=interval[0]; i<interval[1]; i++) { appendItem(i); } //
		 * 产生结束点 if (interval[1] < np && opts.num_edge_entries > 0) {
		 * if(np-opts.num_edge_entries > interval[1]&& opts.ellipse_text) {
		 * jQuery("<span>"+opts.ellipse_text+"</span>").appendTo(fenye); } var
		 * begin = Math.max(np-opts.num_edge_entries, interval[1]); for(var
		 * i=begin; i<np; i++) { appendItem(i); } }
		 */

		if (maxentries == 0) {
			fenye.append("总共0条记录。");

		} else {
			// 产生"Previous"-链接
			if (opts.prev_text && (current_page > 0 || opts.prev_show_always)) {
				appendItem(current_page - 1, {
					text : opts.prev_text,
					classes : "prev"
				});
			}

			var inpt = jQuery("<input value='" + (current_page + 1) + "' > ")
					.appendTo(fenye);
			inpt.after("/" + numPages());
			var tzz = "<a href=\"javascript:void(0);\" class=\"mytzz\">跳转</a>";
			var $this = this;
			jQuery(tzz).bind("click", function(evt) {
				var pageid = $(this).prev().val();
				if (/^\+?[1-9][0-9]*$/.test(pageid)) {
					pageid -= 1;
					if (pageid <= (numPages() - 1)) {
						pageSelected(pageid, evt);
					}
				}

			}).appendTo(fenye);
			// 产生 "Next"-链接
			if (opts.next_text
					&& (current_page < np - 1 || opts.next_show_always)) {
				appendItem(current_page + 1, {
					text : opts.next_text,
					classes : "next"
				});
			}

			fenye.append("总共"
					+ maxentries
					+ "条记录，当前显示"
					+ (current_page * opts.items_per_page + 1)
					+ "~"
					+ ((current_page + 1) == numPages() ? maxentries
							: ((current_page + 1) * opts.items_per_page))
					+ "条记录。");

		}

		jQuery("<span class='myspan'>每页</span>").appendTo(yeshu);

		var select = "<select class=''>";
		var sizes = opts.pagesize_list;
		for (var i = 0; i < sizes.length; i++) {
			select += "<option value='"
					+ sizes[i]
					+ "' "
					+ (sizes[i] == opts.items_per_page ? "selected='true'" : "")
					+ "  >" + sizes[i] + "</option>";
		}
		select += "</select>";
		jQuery(select).bind("change", function(evt) {
			var pagesize = $(this).val();
			opts.items_per_page = pagesize;
			pageSelected(0, evt);
		}).appendTo(yeshu);

		jQuery("<span style='  margin-left: 0;' class='myspan'>条</span>")
				.appendTo(yeshu);

	}

	// 从选项中提取current_page
	var current_page = opts.current_page;
	// 创建一个显示条数和每页显示条数值
	maxentries = (!maxentries || maxentries < 0) ? 1 : maxentries;
	opts.items_per_page = (!opts.items_per_page || opts.items_per_page < 0) ? 1
			: opts.items_per_page;
	// 存储DOM元素，以方便从所有的内部结构中获取
	var panel = jQuery(this);
	// 获得附加功能的元素
	this.selectPage = function(page_id) {
		pageSelected(page_id);
	}
	this.prevPage = function() {
		if (current_page > 0) {
			pageSelected(current_page - 1);
			return true;
		} else {
			return false;
		}
	}
	this.nextPage = function() {
		if (current_page < numPages() - 1) {
			pageSelected(current_page + 1);
			return true;
		} else {
			return false;
		}
	}

	// 回调函数
	dodata(current_page, this, opts.items_per_page);

	self.refresh = function(delcount) {

		if (delcount) {
			if ((maxentries - delcount) % (current_page * opts.items_per_page) <= 0) {
				current_page = current_page - 1;
			}
		}
		current_page = current_page < 0 ? 0 : current_page;
		dodata(current_page, jQuery(this), opts.items_per_page);
	}
	var loading;
	function showload() {
		loading = $(loadhtml).appendTo($(document.body));
	}
	function hidload() {
		loading.remove();
	}
	return self;
}
