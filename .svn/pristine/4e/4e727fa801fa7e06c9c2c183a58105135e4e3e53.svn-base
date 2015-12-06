var ajaxRendRecommendWeibo = function(page,url,type,sort) {
	$('body,html').animate({scrollTop:0},0);
	var pager = {page:1};
	if(null != page && page > 0) {
		pager.page = page;
	}
	var $statusList = $(".status-list["+type+"]"); 
	$statusList.html('<div class="loading" style="height: 40px;"></div>');
	$.ajax(url + "?sortTime=" + sort.time,{
		data:pager,
		success: function(data) {
			$(".status-list["+type+"]").weiboList({
				posts:data.results,
				weiboUserId:data.userId,
				formatWeiboDataFunction:null
			});//渲染微博列表
			$statusList.find('.loading').remove();
			$statusList.append(ajaxPager("#", page, data.pageRequest.pageCount));
			$statusList.find("ul.pager > li > a").click(function(e) {
				e.preventDefault();
				var page = $(this).attr("data-page");
				ajaxRendRecommendWeibo(parseInt(page),url,type,sort);
			});
		},
		dataType:'json'
	});
};