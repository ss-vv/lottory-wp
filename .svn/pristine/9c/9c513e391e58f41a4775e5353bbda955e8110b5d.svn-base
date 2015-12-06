//热门讨论  最新发表  中的热门赛事
$(function(){
  $.ajax({  
		type : "post",  
        url : "/ajax_hot_recommend_match_by_weibo",  
        dataType : 'json',
        success : function(data){
        	if(data && data.length>0){
        		appendElementHotRecommend_(data);
        	}
        }  
    });
})
function appendElementHotRecommend_(data){
	$("#hot_recommend_weibo_count").append(general_hot_recommend_);
	for(var i=0;i<data.length;i++){
		var match=data[i];
		$("#hot_recommend_weibo_count").append('<ul class="davcai-popular-race" id=ul_weibo_'+i+'></ul>');
		$("#hot_recommend_weibo_count  #ul_weibo_"+i).append('<li class="davcai-popular-race-son" id="li_weibo_'+i+'"></li>');
		var host='';
		var guest='';
		var hostposition='';
		var guestposition=''
		if(match.lottery == "JCLQ"){
			host=match.guestTeamName;
			guest=match.hostTeamName;
			hostposition=match.guestTeamPostion;
			guestposition=match.hostTeamPosition;
		}else{
			host=match.hostTeamName;
			guest=match.guestTeamName;
			hostposition=match.hostTeamPosition;
			guestposition=match.guestTeamPostion;
		}
		hostposition=appendPosition_(hostposition);
		guestposition=appendPosition_(guestposition);
		var f=$("#hot_recommend_weibo_count  #ul_weibo_"+i+" #li_weibo_"+i);
		f.append('<span class="popular-race-match">'+match.leagueName+'</span>');
		f.append('<span class="race-team-left"><i>'+hostposition+'</i><a href="http://www.davcai.com/matches/'+match.lottery+'/'+match.matchId+'">'+host+'</a></span>');
	    f.append('<label class="popular-race-vs">VS</label>');
	    f.append('<span class="race-team-right"><a href="http://www.davcai.com/matches/'+match.lottery+'/'+match.matchId+'">'+guest+'</a><i>'+guestposition+'</i></span>');
	    //f.append('<em class="popular-race-list">'+match.recommendCount+'单</em>');
	}
}
function appendPosition_(position){
	var p='';
	if(position){
		
		p="["+position+"]";
	}
	return p;
	
}
var general_hot_recommend_='<h4 class="title">热门推荐</h4>'
                         +' <hr class="hr-solid">';
