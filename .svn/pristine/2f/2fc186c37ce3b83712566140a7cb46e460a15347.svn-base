//id=score_top_list
$(function(){
	$("#score_top_list").append(general_score_list);
	ajax_load_score('');
	$("#score_top_list .ranking-list-tab a").live("click",function(){
		$("#score_top_list .ranking-list-tab a").removeClass("current");
		$(this).addClass("current");
		var lottery=$(this).attr("_id");
		ajax_load_score(lottery);
	})
})
function ajax_load_score(lottery){
	$("#score_top_list .davcai-red-people-list").children("*").remove();
	var general_url="/ajax_load_score";
	if(lottery){
		general_url+="?lottery="+lottery;
	}
	  $.ajax({  
			type : "post",  
	        url : general_url,  
	        dataType : 'json',
	        success : function(data){
	        	if(data && data.length>0){
	        		appendScoreElement(data);
	        	}
	        }  
	    });
}
function appendScoreElement(data){
	for(var i=0;i<data.length;i++){
		var userscore=data[i];
		$("#score_top_list .davcai-red-people-list").append('<li class="davcai-red-people-list-son" id="pic_'+i+'"></li>');
		$("#score_top_list #pic_"+i).append('<a href="javascript:void(0);" class="red-people-name">'+userscore.username+'</a>');
		$("#score_top_list #pic_"+i).append(splitImg(userscore));
	}
}
//拆分图片
function splitImg(data){
	var out='<span class="red-people-icon">';
	if(data.showPic){
		var pics=data.showPic.split('#');
		var img='';
		for(var i=0;i<pics.length;i++){
			 if(pics[i]){
				 img+='<i class="rank-icon-g"><img src="http://www.davcai.com/images/davcai/record/'
					 +pics[i]+'" alt="战绩_'+pics[i]+'"/></i>';
			 }
		}
		out=out+img;
	}
	out+='</span>';
	return out;
}
var general_score_list='<h4 class="title">晒单红人总战绩榜&nbsp;&nbsp;&nbsp;<i class="blue-mark-tips" tid="6"></i></h4>'
                       +'<hr class="hr-solid"> '
                       +'<div class="blue-mark-tips-tan-big" id="blue-mark-tips-tan-6">'
	                   +'金皇冠 &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 金钻&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 金星<br/>'
                       +'(注：战绩图标上的数字表示金皇冠/<br/>金钻/金星的数量)<br/>1金皇冠=10金钻 1金钻=10金星<br/>'
                       +'1金星：成功方案盈利≥1000且回报≥１倍 或成功方案盈利≥500且回报≥5倍 或回报≥20倍<br/>'
                       + '大额赢利奖励：<br/> 1 金钻：1万≤成功方案盈利＜50万；<br/>1 金冠：成功方案盈利≥50万'
                       + '</div>'
                       + '<p class="ranking-list-tab">'
	                   + '<a href="javascript:void(0);" class="all current" >总</a>'
	                   + '<a href="javascript:void(0);" class="rank-football" _id="JCZQ">竞足</a>'
	                   + ' <a href="javascript:void(0);" class="rank-basketball" _id="JCLQ">竞篮</a>'
                       + '</p>'
                       +'<ul class="davcai-red-people-list"></ul>';