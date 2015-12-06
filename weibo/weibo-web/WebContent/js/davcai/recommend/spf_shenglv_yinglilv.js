$(function(){
	//默认
	initSPF7DayShengLv();
	initSPF50MatchShengLv();
	//initSPF7DayYingLi();
	//intiSPF50MatchYingLi();
	$("#spfshenglv a").click(function(){
		$("#spfshenglv a").removeClass("current");
		$(this).addClass("current");
		var _id=$(this).attr("_id");
		if(_id == "spf7dayshenglv"){
			$("#spf_shenglv50match_top5").hide();
			$("#spf_shenglv7day_top5").show();
		}else if(_id == "spf50matchshenglv"){
			$("#spf_shenglv50match_top5").show();
			$("#spf_shenglv7day_top5").hide();
		}
	})
/*	$("#spfyingli a").click(function(){
		$("#spfyingli a").removeClass("current");
		$(this).addClass("current");
		var _id=$(this).attr("_id");
		if(_id == "spf7dayyingli"){
			$("#spf_yingli7day_top5").show();
			$("#spf_yingli50match_top5").hide();
		}else if(_id == "spf50matchyingli"){
			$("#spf_yingli7day_top5").hide();
			$("#spf_yingli50match_top5").show();
		}
	})*/
})
//
function initSPF7DayShengLv(){
	//胜平负胜利 7日内
	$("#spf_shenglv7day_top5").interestUser({"id":"#spf_shenglv7day_top5","listSize":3,
		"hidden":false,"_url":LT.Settings.URLS.rankinglist.spf_shenglv_7day});
}
function initSPF50MatchShengLv(){
	//胜平负胜利 50场
	$("#spf_shenglv50match_top5").interestUser({"id":"#spf_shenglv50match_top5","listSize":3,
		"hidden":false,"_url":LT.Settings.URLS.rankinglist.spf_shenglv_50match});
}
/*function initSPF7DayYingLi(){
	$("#spf_yingli7day_top5").interestUser({"id":"#spf_yingli7day_top5","listSize":3,
		"hidden":false,"_url":LT.Settings.URLS.rankinglist.spf_yingli_7day});
}
function intiSPF50MatchYingLi(){
	$("#spf_yingli50match_top5").interestUser({"id":"#spf_yingli50match_top5","listSize":3,
		"hidden":false,"_url":LT.Settings.URLS.rankinglist.spf_yingli_50match});
}*/

