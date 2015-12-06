/**
 * 提供公共调用方法
 * 异步渲染页面数据
 */
var AjaxRender = {};

/**
 * 完成初始化工作
 * 1.指定数据加载到那个容器(ID)
 * 2.指定pager文档对象的ID
 * 3.需要再页面加载完成调用
 */
AjaxRender.init = function(container, pagerWrapper) {
	var container = (container == null) ? "contentList" : container;
	var pagerWrapper = (pagerWrapper == null) ? "pager-wrapper" : pagerWrapper;
	AjaxRender.panel = $("#"+container);
	AjaxRender.pagerWrapper = $("#"+pagerWrapper);
};

//清除页面数据缓存
AjaxRender.clearTopicCache = function(container, pagerWrapper) {
	container.empty();
	pagerWrapper.empty();
	this.showLoading(container);
};
AjaxRender.showLoading = function(container) {
	container.next('.loading').show();
};
AjaxRender.hideLoading = function(container) {
	container.next('.loading').hide();
};
//渲染分页数据
AjaxRender.renderPager = function(url, page, totalPages, bindLoadEvent) {
	if(undefined == page || null == page || page < 0) {
		page = 1;
	}
	$("#pager-wrapper").html(ajaxPager(url+"?", page, totalPages));
	$(".pager-wrapper > ul > li > a").click(function(e) {
		e.preventDefault();
		var page = $(this).attr("data-page");
		if(bindLoadEvent) {
			bindLoadEvent.call(this, parseInt(page), url);
		}
	});
};
AjaxRender.post = function(page, url, ajaxPagerFn, parse, render, callback) {
	panel = AjaxRender.panel;
	pagerWrapper = AjaxRender.pagerWrapper;
	AjaxRender.clearTopicCache(panel,pagerWrapper);
	var pager = {page:1};
	if(null != page && page > 0) {
		pager.page = page;
	}
	$.post(url, pager, function(result, e) {
		if (result != null && result.results != null) {
			if (result.success) {
				AjaxRender.clearTopicCache(panel,pagerWrapper);
				var weiboPane = new WeiboUI({
					userId : result.userId,
					panel : panel,
					posts : result.results,
					parse : parse,
					renderer: render
				});
				$weiboList = weiboPane.$weiboList;
				AjaxRender.renderPager(url, pager.page, result.totalPages, ajaxPagerFn);
			} else {
				$.showMessage(result.desc);
			}
		}
		AjaxRender.hideLoading(panel);
	}, 'json').error(function(e) {
		return;
	});
	return render;
};