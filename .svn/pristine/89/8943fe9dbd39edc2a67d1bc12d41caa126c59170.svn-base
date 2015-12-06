function renderMatchWeiboBlock(weiboMatchLotteryType, weiboMatchMatchId) {
	weiboMatchMatchId = weiboMatchRenderMatchString();
	var matchGetUrl = '?matchId=' + weiboMatchMatchId;
	// 加载微博全部面板
	$("[match_allposts]").find(".status-list").weiboListPanel({
		loadWeiboUrl : LT.Settings.URLS.match_weibo.loadMatchPost + matchGetUrl,
		newWeiboUrl : LT.Settings.URLS.match_weibo.load_new_matchs_posts + matchGetUrl,
		newWeiboTimerUrl : LT.Settings.URLS.match_weibo.newMatchPostTimer + matchGetUrl
	});	
	$('#match_allposts').click(function (){
		if($(this).hasClass("active")){
			return ;
		}
		$("[match_weibotab]").removeClass("active");
		$(this).addClass("active");
		$("[match_weibopanel]").hide();
		$("[match_allposts]").show();
		$("[match_allposts]").html('<ul class="status-list"></ul>');
		// 加载微博全部面板
		$("[match_allposts]").find(".status-list").weiboListPanel({
			loadWeiboUrl : LT.Settings.URLS.match_weibo.loadMatchPost + matchGetUrl,
			newWeiboUrl : LT.Settings.URLS.match_weibo.load_new_matchs_posts + matchGetUrl,
			newWeiboTimerUrl : LT.Settings.URLS.match_weibo.newMatchPostTimer + matchGetUrl
		});	
	});
	$('#match_rec').click(function (){
		if($(this).hasClass("active")){
			return ;
		}
		$("[match_weibotab]").removeClass("active");
		$(this).addClass("active");
		$("[match_weibopanel]").hide();
		$("[match_rec]").show();
		$("[match_rec]").html('<ul class="status-list"></ul>');
		// 加载微博实单推荐面板
		$("[match_rec]").find(".status-list").weiboListPanel({
			loadWeiboUrl : LT.Settings.URLS.match_weibo.loadRealMatchPost + matchGetUrl,
			newWeiboUrl : LT.Settings.URLS.match_weibo.load_new_matchs_realMatch + matchGetUrl,
			newWeiboTimerUrl : LT.Settings.URLS.match_weibo.newRealMatchPostTimer + matchGetUrl
		});	
	});
	$('#match_discuss').click(function (){
		if($(this).hasClass("active")){
			return ;
		}
		$("[match_weibotab]").removeClass("active");
		$(this).addClass("active");
		$("[match_weibopanel]").hide();
		$("[match_discuss]").show();
		$("[match_discuss]").html('<ul class="status-list"></ul>');
		// 加载微博讨论面板
		$("[match_discuss]").find(".status-list").weiboListPanel({
			loadWeiboUrl : LT.Settings.URLS.match_weibo.loadMatchDiscussPost + matchGetUrl,
			newWeiboUrl : LT.Settings.URLS.match_weibo.load_new_matchs_discuss + matchGetUrl,
			newWeiboTimerUrl : LT.Settings.URLS.match_weibo.newMatchDiscussTimer + matchGetUrl
		});	
	});
	$('#match_news').click(function (){
		if($(this).hasClass("active")){
			return ;
		}
		$("[match_weibotab]").removeClass("active");
		$(this).addClass("active");
		$("[match_weibopanel]").hide();
		$("[match_news]").show();
		$("[match_news]").html('<ul class="status-list"></ul>');
		// 加载微博新闻面板
		$("[match_news]").find(".status-list").weiboListPanel({
			loadWeiboUrl : LT.Settings.URLS.match_weibo.loadMatchsNews + matchGetUrl,
			newWeiboUrl : LT.Settings.URLS.match_weibo.load_new_matchs_news + matchGetUrl,
			newWeiboTimerUrl : LT.Settings.URLS.match_weibo.newMatchNewsTimer + matchGetUrl
		});	
	});
	function weiboMatchRenderMatchString(){
			var macthTipString = "";
			if(weiboMatchLotteryType == "JCZQ"){
				return macthTipString += "JZ" + weiboMatchMatchId.substring(2);
			} else if(weiboMatchLotteryType == "JCLQ") {
				return macthTipString += "JL" + weiboMatchMatchId.substring(2);
			} else if(weiboMatchLotteryType == "BJDC") {
				return macthTipString = "BD" + weiboMatchMatchId;
			}
			return macthTipString;
	}
}