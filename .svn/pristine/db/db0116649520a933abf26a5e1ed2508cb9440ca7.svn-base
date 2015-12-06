//晒单助人和跟单中奖 晒单页面 和中奖喜报页面公用
$(function(){
	showSchemePrize7Day();
	showSchemePrize50Match();
	followSchemeWin7Day();
	followSchemeWin50Match();
	showSchemePrize7Day50MatchSwitch();
	followSchemPrize7Day50MatchSwitch();
})
//晒单页 晒单助人 7日奖金榜
function showSchemePrize7Day(){
	$("#showschemeprize7day").interestUser({"id":"#showschemeprize7day","listSize":3,
		"hidden":false,"_url":LT.Settings.URLS.rankinglist.showscheme_prize_7day});
}

//晒单页 晒单助人 50单奖金榜
function  showSchemePrize50Match(){
	$("#showschemeprize50match").interestUser({"id":"#showschemeprize50match","listSize":3,
		"hidden":false,"_url":LT.Settings.URLS.rankinglist.showscheme_prize_50match});
}
//晒单页 跟单中奖  7日
function followSchemeWin7Day(){
	$("#followschemewin7day").interestUser({"id":"#followschemewin7day","listSize":3,
		"hidden":false,"_url":LT.Settings.URLS.rankinglist.followscheme_win_7day});
}
//晒单页 跟单中奖  50单
function followSchemeWin50Match(){
	$("#followschemewin50match").interestUser({"id":"#followschemewin50match","listSize":3,
		"hidden":false,"_url":LT.Settings.URLS.rankinglist.followscheme_win_50match});
}
//晒单助人 7日 50场 切换
function showSchemePrize7Day50MatchSwitch(){
	$("#showschemeprize a").click(function(){
		$("#showschemeprize a").removeClass("current");
		$(this).addClass("current");
		var _id=$(this).attr("_id");
		if(_id == "showschemeprize7day"){
			$("#showschemeprize7day").show();
			$("#showschemeprize50match").hide();
		}else if(_id == "showschemeprize50match"){
			$("#showschemeprize7day").hide();
			$("#showschemeprize50match").show();
		}
	})
}
//跟单中奖 7日 50场 切换 
function followSchemPrize7Day50MatchSwitch(){
	$("#followschemewin a").click(function(){
		$("#followschemewin a").removeClass("current");
		$(this).addClass("current");
		var _id=$(this).attr("_id");
		if(_id == "followschemewin7day"){
			$("#followschemewin7day").show();
			$("#followschemewin50match").hide();
		}else if(_id == "followschemewin50match"){
			$("#followschemewin7day").hide();
			$("#followschemewin50match").show();
		}
	})
}