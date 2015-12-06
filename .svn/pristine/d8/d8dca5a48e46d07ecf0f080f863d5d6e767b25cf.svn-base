//id=hotrecommend 中奖喜报页面 最红赛事
$(function(){
  $.ajax({  
		type : "post",  
        url : "/hotrecommend/ajax_hotrecommend",  
        dataType : 'json',
        success : function(data){
        	if(data && data.results.length>0){
        		
        		appendElementHotRecommend_(data.results);
        	}
        }  
    });
})
function appendElementHotRecommend_(data){
	$("#hotrecommend").append(general_hot_recommend_);
	for(var i=0;i<data.length;i++){
		var match=data[i];
		$("#hotrecommend").append('<ul class="davcai-popular-race" id=ul_match_'+i+'></ul>');
		$("#hotrecommend  #ul_match_"+i).append('<li class="davcai-popular-race-son" id="li_'+i+'"></li>');
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
		var f=$("#hotrecommend  #ul_match_"+i+" #li_"+i);
		f.append('<span class="popular-race-match">'+match.leagueName+'</span>');
		f.append('<span class="race-team-left"><i>'+hostposition+'</i><a href="http://www.davcai.com/matches/'+match.lottery+'/'+match.matchId+'" target="_blank">'+host+'</a></span>');
	    f.append('<label class="popular-race-vs">VS</label>');
	    f.append('<span class="race-team-right"><a href="http://www.davcai.com/matches/'+match.lottery+'/'+match.matchId+'" target="_blank">'+guest+'</a><i>'+guestposition+'</i></span>');
	    f.append('<em class="popular-race-list">'+match.recommendCount+'单</em>');
	}
}
function appendPosition_(position){
	var p='';
	if(position){
		
		p="["+position+"]";
	}
	return p;
	
}
var general_hot_recommend_='<h4 class="title">最红赛事</h4>'
                         +' <hr class="hr-solid">';