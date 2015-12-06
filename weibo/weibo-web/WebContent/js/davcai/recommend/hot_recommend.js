//id=hot_recommend_ 推荐页面热门推荐
$(function(){
  $.ajax({  
		type : "post",  
        url : "/ajax_hot_recommend_match",  
        dataType : 'json',
        success : function(data){
        	if(data && data.length>0){
        		appendElementHotRecommend(data);
        	}
        }  
    });
})
function appendElementHotRecommend(data){
	$("#hot_recommend_").append(general_hot_recommend);
	for(var i=0;i<data.length;i++){
		var match=data[i];
		$("#hot_recommend_").append('<ul class="davcai-popular-race" id=ul_match_'+i+'></ul>');
		$("#hot_recommend_  #ul_match_"+i).append('<li class="davcai-popular-race-son" id="li_'+i+'"></li>');
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
		hostposition=appendPosition(hostposition);
		guestposition=appendPosition(guestposition);
		var f=$("#hot_recommend_  #ul_match_"+i+" #li_"+i);
		f.append('<span class="popular-race-match">'+match.leagueName+'</span>');
		f.append('<span class="race-team-left"><i>'+hostposition+'</i><a href="http://www.davcai.com/matches/'+match.lottery+'/'+match.matchId+'" target="_blank">'+host+'</a></span>');
	    f.append('<label class="popular-race-vs">VS</label>');
	    f.append('<span class="race-team-right"><a href="http://www.davcai.com/matches/'+match.lottery+'/'+match.matchId+'" target="_blank">'+guest+'</a><i>'+guestposition+'</i></span>');
	    f.append('<em class="popular-race-list">'+match.recommendCount+'单</em>');
	}
	
}
function appendPosition(position){
	var p='';
	if(position){
		
		p="["+position+"]";
	}
	return p;
	
}
var general_hot_recommend='<h4 class="title">热门推荐</h4>'
                         +' <hr class="hr-solid">';
