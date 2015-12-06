$(document).ready(function() {
	// 加载微博全部面板
	$("[_allposts]").find(".status-list").weiboListPanel({
		loadWeiboUrl : LT.Settings.URLS.weibo.loadPost,
		newWeiboUrl : LT.Settings.URLS.weibo.weibo_new_posts,
		newWeiboTimerUrl : LT.Settings.URLS.weibo.newPostTimer
	});	
	
	$('#allposts').click(function (){
		if($(this).hasClass("active")){
			return ;
		}
		$("[_weibotab]").removeClass("active");
		$(this).addClass("active");
		$("[_weibopanel]").hide();
		$("[_allposts]").show();
		$("[_allposts]").html('<ul class="status-list"></ul>');
		// 加载微博全部面板
		$("[_allposts]").find(".status-list").weiboListPanel({
			loadWeiboUrl : LT.Settings.URLS.weibo.loadPost,
			newWeiboUrl : LT.Settings.URLS.weibo.weibo_new_posts,
			newWeiboTimerUrl : LT.Settings.URLS.weibo.newPostTimer
		});	
	});
	$('#rec').click(function (){
		if($(this).hasClass("active")){
			return ;
		}
		$("[_weibotab]").removeClass("active");
		$(this).addClass("active");
		$("[_weibopanel]").hide();
		$("[_rec]").show();
		$("[_rec]").html('<ul class="status-list"></ul>');
		// 加载微博实单推荐面板
		$("[_rec]").find(".status-list").weiboListPanel({
			loadWeiboUrl : LT.Settings.URLS.weibo.loadRecommends,
			newWeiboUrl : LT.Settings.URLS.weibo.weibo_new_recommends,
			newWeiboTimerUrl : LT.Settings.URLS.weibo.newRecTimer
		});
	});
	$('#discuss').click(function (){
		if($(this).hasClass("active")){
			return ;
		}
		$("[_weibotab]").removeClass("active");
		$(this).addClass("active");
		$("[_weibopanel]").hide();
		$("[_discuss]").show();
		$("[_discuss]").html('<ul class="status-list"></ul>');
		// 加载微博讨论面板
		$("[_discuss]").find(".status-list").weiboListPanel({
			loadWeiboUrl : LT.Settings.URLS.weibo.loadDiscussWeibo,
			newWeiboUrl : LT.Settings.URLS.weibo.weibo_new_discuss,
			newWeiboTimerUrl : LT.Settings.URLS.weibo.newDiscussWeiboTimer
		});	
	});
	$('#news').click(function (){
		if($(this).hasClass("active")){
			return ;
		}
		$("[_weibotab]").removeClass("active");
		$(this).addClass("active");
		$("[_weibopanel]").hide();
		$("[_news]").show();
		$("[_news]").html('<ul class="status-list"></ul>');
		// 加载微博新闻面板
		$("[_news]").find(".status-list").weiboListPanel({
			loadWeiboUrl : LT.Settings.URLS.weibo.loadNews,
			newWeiboUrl : LT.Settings.URLS.weibo.weibo_new_news,
			newWeiboTimerUrl : LT.Settings.URLS.weibo.newNewsTimer
		});	
	});
});