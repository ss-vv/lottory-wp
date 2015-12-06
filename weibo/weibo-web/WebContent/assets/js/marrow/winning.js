var ajaxRendWinList = function(page) {
	$('body,html').animate({scrollTop:0},0);
	var pager = {page:1};
	if(null != page && page > 0) {
		pager.page = page;
	}
	$(".status-list[all]").html('<div class="loading" style="height: 40px;"></div>');
	$.ajax(LT.Settings.URLS.marrow.ajaxWinList,{
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
				ajaxRendWinList(parseInt(page));
			});
		},
		dataType:'json'
	});
};
var ajaxRendFollowingWinList = function(page) {
	$('body,html').animate({scrollTop:0},0);
	var pager = {page:1};
	if(null != page && page > 0) {
		pager.page = page;
	}
	$(".status-list[myfollowing]").html('<div class="loading" style="height: 40px;"></div>');
	$.ajax(LT.Settings.URLS.marrow.ajaxFollowingWinList,{
		data:pager,
		success: function(data) {
			if(!data.userId){
				$("#pop-outer").fadeIn(800);
				$("#all").show();
				$(".status-list[myfollowing]").find(".loading").remove();
				return ;
			}
			$(".status-list[myfollowing]").weiboList({
				posts:data.results,
				weiboUserId:data.userId,
				formatWeiboDataFunction:marrowFormatData
			});//渲染微博列表
			$(".status-list[myfollowing]").find(".loading").remove();
			$(".status-list[myfollowing]").append(ajaxPager("#", page, data.totalPages));
			$(".status-list[myfollowing]").find("ul.pager > li > a").click(function(e) {
				e.preventDefault();
				var page = $(this).attr("data-page");
				ajaxRendFollowingWinList(parseInt(page));
			});
		},
		dataType:'json'
	});
};
$(document).ready(function (){
	$(".davcai-nav-classify").find("label").each(function (){
		$(this).click(function (event){
			$(".davcai-nav-classify").find("label").removeClass("current");
			var $this = $(event.target);
			$this.addClass("current");
			if($this.attr("_t") == "all"){
				$(".status-list[all]").show();
				$(".status-list[myfollowing]").hide();
				if($(".status-list[all]").html() == ""){
					ajaxRendWinList(1);
				}
			} else if($this.attr("_t") == "myfollowing"){
				$(".status-list[myfollowing]").show();
				$(".status-list[all]").hide();
				if($(".status-list[myfollowing]").html() == ""){
					ajaxRendFollowingWinList(1);
				}
			}
		});
	});
});