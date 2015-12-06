$(function(){
	showSchemeShengLv7Day();
	showSchemeShengLv50Match();
	followSchemeShengLv7Day();
	followSchemeShengLv50Match();
	showSchemeShengLv7Day50MatchSwitch();
	followSchemeShengLv7Day50MatchSwitch();
})
var globalOption={"listSize":3,"hidden":false};
//晒单页面 7日晒单胜率
function showSchemeShengLv7Day(){
	var op=$.extend(globalOption,{"id":"#showscheme7dayshenglv",
		"_url":LT.Settings.URLS.rankinglist.showscheme_shenglv_7day});
	$("#showscheme7dayshenglv").interestUser(op);
}
//晒单页面 50场晒单胜率
function showSchemeShengLv50Match(){
	var op=$.extend(globalOption,{"id":"#showscheme50matchshenglv",
		"_url":LT.Settings.URLS.rankinglist.showscheme_shenglv_50match});
	$("#showscheme50matchshenglv").interestUser(op);
}
//晒单页 跟单7日胜率
function followSchemeShengLv7Day(){
    var op=$.extend(globalOption,{"id":"#followscheme7dayyingli",
    	"_url":LT.Settings.URLS.rankinglist.followscheme_shenglv_7day});
    $("#followscheme7dayyingli").interestUser(op);
}
//晒单页 跟单50场胜率
function followSchemeShengLv50Match(){
	var op=$.extend(globalOption,{"id":"#followscheme50matchyingli",
		"_url":LT.Settings.URLS.rankinglist.followscheme_shenglv_50match});
	$("#followscheme50matchyingli").interestUser(op);
}
//晒单页面 7日晒单胜率 50单 切换
function showSchemeShengLv7Day50MatchSwitch(){
	$("#showschemeshenglv a").click(function(){
		$("#showschemeshenglv a").removeClass("current");
		$(this).addClass("current");
		var _id=$(this).attr("_id");
		if(_id == "showscheme7dayshenglv"){
			$("#showscheme7dayshenglv").show();
			$("#showscheme50matchshenglv").hide();
		}else if(_id == "showscheme50matchshenglv"){
			$("#showscheme7dayshenglv").hide();
			$("#showscheme50matchshenglv").show();
		}
	})
}
//晒单页面 跟单胜率 7日 50场切换
function followSchemeShengLv7Day50MatchSwitch(){
	$("#followschemeyingli a").click(function(){
		$("#followschemeyingli a").removeClass("current");
		$(this).addClass("current");
		var _id=$(this).attr("_id");
		if(_id == "followscheme7dayyingli"){
			$("#followscheme7dayyingli").show();
			$("#followscheme50matchyingli").hide();
		}else if(_id == "followscheme50matchyingli"){
			$("#followscheme7dayyingli").hide();
			$("#followscheme50matchyingli").show();
		}
	})
}