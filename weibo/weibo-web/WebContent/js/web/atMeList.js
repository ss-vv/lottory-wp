$(document).ready(function() {
	var atMeUrl = "http://www.davcai.com/ajaxLoadAtMe";
	function ajaxLoadAtMe(page){
		$('body,html').animate({scrollTop:0},0);
		$('.status-list[atMe]').html('<div class="loading" style="height: 40px;"></div>');
		var url = typeof(page) == "undefined" ? atMeUrl : atMeUrl+'?pageRequest.pageIndex='+page;
		$.post(url, {}, function(result, e) {
			if (result != null) {
				if (result.success) {
					$('.status-list[atMe]').weiboList({
						posts:result.results,
						weiboUserId:result.userId
					});
					var curPage = result.pageRequest.pageIndex;
					var totalPages = result.pageRequest.pageCount;
					$(".status-list[atMe]").find('.loading').remove();
					$('.status-list[atMe]').append(ajaxPager("#",curPage,totalPages));
					var a = $('.status-list[atMe]').find("ul.pager > li > a");
					if(a.length > 0){
						a.click(function(e) {
							e.preventDefault();
							var page = $(this).attr("data-page");
							ajaxLoadAtMe(parseInt(page));
						});
					}
				} else {
					$.showMessage(result.desc);
				}
			}
		}, 'json');																								
	}
	ajaxLoadAtMe(1);
});