//第一行tab点击事件
$(function(){
	$(".tabCon:first").show();
	$(".bet-data-content .bdc-menu td a").each(function(i){
		$(this).click(function(){
			$(".tabCon:visible").hide();
			$(".bdc-menu td .active").removeClass("active");
			$(this).attr("class","active");
			$(".tabCon").eq(i).show();
		});
	});
	//积分榜tab点击事件
	$("#leagueScore td a").each(function(i){
		$(this).click(function(){
			$(".lsjfb-menu-list:visible").hide();
			$(".lsjfb-menu-nav td .active").removeClass("active");
			$(this).attr("class","active");
			if($(".lsjfb-menu-list").eq(i)){
				if(i==0){
					$("#rank_total_rank").show();
				}else if(i==1){
					$("#rank_zc_rank").show();
				}else if(i==2){
					$("#rank_kc_rank").show();
				}else if(i==3){
					$("#rank_half_total_rank").show();
				}else if(i==4){
					$("#rank_half_zc_rank").show();
				}else if(i==5){
					$("#rank_half_kc_rank").show();
				}
			}else{
				$(".lsjfb-menu-list").eq(i).append("loading");
			}
			
		});
		
	});
});
/**
 * 时间对象的格式化
 */  
Date.prototype.format = function(format)  
{  
/*
 * format="yyyy-MM-dd hh:mm:ss";
 */  
	var o = {  
			"M+" : this.getMonth() + 1,  
			"d+" : this.getDate(),  
			"h+" : this.getHours(),  
			"m+" : this.getMinutes(),  
			"s+" : this.getSeconds(),  
			"q+" : Math.floor((this.getMonth() + 3) / 3),  
			"S" : this.getMilliseconds()  
	}  
  
	if (/(y+)/.test(format))  
	{  
		format = format.replace(RegExp.$1, (this.getFullYear() + "").substr(4  
				- RegExp.$1.length));  
	}  
  
	for (var k in o)  
	{  
		if (new RegExp("(" + k + ")").test(format))  
		{  
			format = format.replace(RegExp.$1, RegExp.$1.length == 1  
					? o[k]  
			: ("00" + o[k]).substr(("" + o[k]).length));  
		}  
	}  
	return format;
}
//半全场比分计算
function halfAndAll(homeTeamScore,guestTeamScore,homeTeamHalfScore,guestTeamHalfScore){
	result1 = homeTeamScore>guestTeamScore?'胜胜':homeTeamScore==guestTeamScore?'胜平':'胜负';
	result2 = homeTeamScore>guestTeamScore?'平胜':homeTeamScore==guestTeamScore?'平平':'平负';
	result3 = homeTeamScore>guestTeamScore?'负胜':homeTeamScore==guestTeamScore?'负平':'负负';
	return homeTeamHalfScore>guestTeamHalfScore?result1:homeTeamHalfScore==guestTeamHalfScore?result2:result3;
}
//上下单双计算
function singleMult(homeTeamScore,guestTeamScore){
	var score = homeTeamScore+guestTeamScore;
	var singeDouble=["上双","下双","上<font color=blue>单</font>","下<font color=blue>单</font>"]; 
	if(score>=3){
		if(score%2==0){
			return singeDouble[0];
		}else{
			return singeDouble[2];
		}
	}else{
		if(score%2==0){
			return singeDouble[1];
		}else{
			return singeDouble[3];
		}
	}
}
//相对本场比赛主队的胜平负
function winLoseOrDrawForHomeTeam(homeTeamScore,guestTeamScore,homeTeamId){
	if(homeTeamId==matchHomeTeam){
		return winLoseOrDraw(homeTeamScore,guestTeamScore);
	}else{
		return winLoseOrDraw(guestTeamScore,homeTeamScore)
	}
	
}
//相对于本场比赛客队的胜平负
function winLoseOrDrawForGuestTeam(homeTeamScore,guestTeamScore,guestTeamId){
	if(guestTeamId!=matchGuestTeam){
		return winLoseOrDraw(homeTeamScore,guestTeamScore);
	}else{
		return winLoseOrDraw(guestTeamScore,homeTeamScore)
	}
	
}
function winLoseOrDraw(homeTeamScore,guestTeamScore){
	if(homeTeamScore>guestTeamScore){
		return '<td class="color-red"><b>胜</b></td>';
	}else if(homeTeamScore==guestTeamScore){
		return '<td class="color-green"><b>平</b></td>';;
	}else{
		return '<td class="color-blue"><b>负</b></td>';;
	}

}
function percentage(num, total) { 
    return ((num*1.0/total*100).toFixed(1)+"%");// 小数点后一位百分比
   
}