var realWeiboPageReadyFunction  = function (ajaxUrl,pageUrl,type){
	var followCountSortVal = jQuery('.sortText[followCountSort]').attr("followCountSort");
	var timeSortVal = jQuery('.sortText[timeSort]').attr("timeSort");
	var buyAmountSortVal = jQuery('.sortText[buyAmountSort]').attr("buyAmountSort");
	var rateOfReturnSortVal = jQuery('.sortText[rateOfReturnSort]').attr("rateOfReturnSort");
	var sort = {
		followCountSort: followCountSortVal == "null" ? null : followCountSortVal,
		timeSort:timeSortVal == "null" ? null : timeSortVal,
		buyAmountSort:buyAmountSortVal == "null" ? null : buyAmountSortVal,
		rateOfReturnSort:rateOfReturnSortVal == "null" ? null : rateOfReturnSortVal,
		recentDays:jQuery("#date_range").val()
	};
	ajaxRendRealFollowWeibo(1,ajaxUrl,type,sort);
    jQuery('.sortText[sort_option]').click(function (){
    	var $this = $(this);
    	var sortType = $this.attr("sort_option");
    	var sortValue = $this.attr(sortType);
    	var sort = {
			followCountSort: sortType == "followCountSort" ? (sortValue == "desc" ? "asc":"desc" ) :null,
			timeSort:sortType == "timeSort" ? (sortValue == "desc" ? "asc":"desc" ) :null,
			buyAmountSort:sortType == "buyAmountSort" ? (sortValue == "desc" ? "asc":"desc" ) :null,
			rateOfReturnSort:sortType == "rateOfReturnSort" ? (sortValue == "desc" ? "asc":"desc" ) :null
		};
    	doRealWeiboSortFunction(sort,pageUrl);
    });
    jQuery("#date_range").change(function (){
    	var $this = $("[sort_option] > .current").parent();
    	var sortType = $this.attr("sort_option");
    	var sortValue = $this.attr(sortType);
    	var sort = {
			followCountSort: sortType == "followCountSort" ? sortValue:null,
			timeSort:sortType == "timeSort" ? sortValue:null,
			buyAmountSort:sortType == "buyAmountSort" ? sortValue:null,
			rateOfReturnSort:sortType == "rateOfReturnSort" ? sortValue:null
		};
    	doRealWeiboSortFunction(sort,pageUrl);
    });
};

var doRealWeiboSortFunction = function(sort,pageUrl){//执行排序筛选功能
	var recentDays = jQuery("#date_range").val();
	if(sort.followCountSort){
		window.location.href=pageUrl+"?followCountSort=" + sort.followCountSort + "&recentDays=" + recentDays;			
	} else if(sort.timeSort){
		window.location.href=pageUrl+"?timeSort=" + sort.timeSort+ "&recentDays=" + recentDays;
	} else if(sort.buyAmountSort){
		window.location.href=pageUrl+"?buyAmountSort=" + sort.buyAmountSort+ "&recentDays=" + recentDays;
	} else if(sort.rateOfReturnSort){
		window.location.href=pageUrl+"?rateOfReturnSort=" + sort.rateOfReturnSort+ "&recentDays=" + recentDays;
	}
}

var ajaxRendRealFollowWeibo = function(page,url,type,sort) {//ajax获取并渲染实单，带分页
	$('body,html').animate({scrollTop:0},0);
	var data = {page:1};
	if(null != page && page > 0) {
		data.page = page;
	}
	data = $.extend({},data,sort);
	var $statusList = $(".status-list["+type+"]"); 
	$statusList.html('<div class="loading" style="height: 40px;"></div>');
	$.ajax(url,{
		data:data,
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
				ajaxRendRealFollowWeibo(parseInt(page),url,type,sort);
			});
		},
		dataType:'json'
	});
};
