var ajaxLoadHotDiscuss = function(page) {
	$('body,html').animate({scrollTop:0},0);
	var pager = {page:1};
	if(null != page && page > 0) {
		pager.page = page;
	}
	$(".status-list[all]").html('<div class="loading" style="height: 40px;"></div>');
	$.ajax(LT.Settings.URLS.marrow.ajaxHotDiscussList,{
		data:pager,
		success: function(data) {
			$(".status-list[all]").weiboList({
				posts:data.results,
				weiboUserId:data.userId,
				formatWeiboDataFunction:marrowFormatData
			});//渲染微博列表
			$(".status-list[all]").find('.loading').remove();
			$(".status-list[all]").append(ajaxPager("#", page, data.totalPages));
			$(".status-list[all]").find("ul.pager > li > a").click(function(e) {
				e.preventDefault();
				var page = $(this).attr("data-page");
				ajaxLoadHotDiscuss(parseInt(page));
			});
		},
		dataType:'json'
	});
};