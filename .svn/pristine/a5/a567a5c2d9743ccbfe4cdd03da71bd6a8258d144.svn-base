//篮球联赛积分榜
function bb_leagueScoreRank(rankType){
	if(subLeagueId){
		
	}else{
		subLeagueId=-1;
	}
	$.ajax({
		type:"post",
		url:"ajax_bb_leagueScoreRank.do",
		dataType:'json',
		data:{
			leagueId:sclassID,
			rankType:rankType,
			subLeagueId:subLeagueId
		},
		success:function(data){
			leagueScore_data=eval("("+data.data+")");
//			console.log(leagueScore_data.leagueScoreOverall);
			init_bb_leagueScore(leagueScore_data.leagueScoreRule,leagueScore_data.leagueScoreOverall,rankType);
		}
	});
}
function bb_twoSideAgainstHistory(homeTeamId,guestTeamId){
	$ .ajax({  
		type : "post",  
        url : "ajax_lq_twoSideAgainstHistory.do",  
        dataType : 'json',
        data:{
        	homeTeamId:homeTeamId,
        	guestTeamId:guestTeamId
        },
        success : function(data){
        	v_data=eval(data.data);
        	init_lq('v_against');
        }  
    });
}
function init_bb_leagueScore(leagueScoreRule,leagueScoreOverall,rankType){
	var sb = [];
	sb.push('<table class="lsjfb-menu-list" cellpadding="0" id="rank_'+rankType+'" '+(rankType=='total_rank'?'style="display:table;"':'style="display:none;"')+' cellspacing="0" border="0"><tr bgcolor="#F6EEE0"><td><b>排名</b></td><td><b>球队名称</b></td>'+
			'<td><b>&nbsp;赛&nbsp;</b></td>'+
			'<td><b>&nbsp;胜&nbsp;</b></td>'+
			'<td><b>&nbsp;负&nbsp;</b></td>'+
			'<td><b>&nbsp;得&nbsp;</b></td>'+
			'<td><b>&nbsp;失&nbsp;</b></td>'+
			'<td><b>&nbsp;净&nbsp;</b></td>'+
			'<td><b>胜%</b></td>'+
			'<td><b>负%</b></td>'+
			'<td><b>近十场</b></td>'+
			'</tr>');
	if(rankType=='total_rank'){
		for(i=0;i<leagueScoreOverall.length;i++){
			if(leagueScoreOverall[i]!=null){
				sb.push('<tr bgcolor="#ffffff">');
				sb.push('<td>'+(i+1)+'</td>');
				sb.push('<td align="left">');
				sb.push('	<a href="javascript:void(0);" class="color-brown">'+leagueScoreOverall[i].teamName+'</a>');
				sb.push('</td>');
				sb.push('<td>'+(leagueScoreOverall[i].winMatches+leagueScoreOverall[i].loseMatches)+'</td>');
				sb.push('<td>'+leagueScoreOverall[i].winMatches+'</td>');
				sb.push('<td>'+leagueScoreOverall[i].loseMatches+'</td>');
				if(leagueScoreOverall[i].averageGoal){
					sb.push('<td>'+leagueScoreOverall[i].averageGoal+'</td>');
					sb.push('<td>'+leagueScoreOverall[i].averageLose+'</td>');
					sb.push('<td>'+(leagueScoreOverall[i].averageGoal-leagueScoreOverall[i].averageLose)+'</td>');
				}else{
					sb.push('<td></td>');
					sb.push('<td></td>');
					sb.push('<td></td>');
				}
				
				
				sb.push('<td bgcolor="#F6DBD4">'+percentage(leagueScoreOverall[i].winMatches,(leagueScoreOverall[i].winMatches+leagueScoreOverall[i].loseMatches))+'</td>');
				sb.push('<td bgcolor="#F8EDC5">'+percentage(leagueScoreOverall[i].loseMatches,(leagueScoreOverall[i].winMatches+leagueScoreOverall[i].loseMatches))+'</td>');
				if(leagueScoreOverall[i].pastTen){
					sb.push('<td bgcolor="#FDFDCB">'+leagueScoreOverall[i].pastTen+'</td>');
				}else{
					sb.push('<td bgcolor="#FDFDCB"></td>');
				}
				sb.push('</tr>');
			}
			
		}
	}else{
		for(i=0;i<leagueScoreOverall.length;i++){
			if(leagueScoreOverall[i]!=null){
				sb.push('<tr bgcolor="#ffffff">');
				sb.push('<td>'+(leagueScoreOverall[i].rank==null?'':leagueScoreOverall[i].rank)+'</td>');
				sb.push('<td align="left">');
				sb.push('	<a href="javascript:void(0);" class="color-brown">'+leagueScoreOverall[i].teamId+'</a>');
				sb.push('</td>');
				sb.push('<td>'+leagueScoreOverall[i].totalMatches+'</td>');
				sb.push('<td>'+leagueScoreOverall[i].winMatches+'</td>');
				sb.push('<td>'+leagueScoreOverall[i].drawMatches+'</td>');
				sb.push('<td>'+leagueScoreOverall[i].loseMatches+'</td>');
				sb.push('<td>'+leagueScoreOverall[i].goal+'</td>');
				sb.push('<td>'+leagueScoreOverall[i].lose+'</td>');
				sb.push('<td>'+(leagueScoreOverall[i].goal-leagueScoreOverall[i].lose)+'</td>');
				sb.push('<td bgcolor="#F6DBD4">'+percentage(leagueScoreOverall[i].winMatches,leagueScoreOverall[i].totalMatches)+'</td>');
				sb.push('<td bgcolor="#F8EDC5">'+percentage(leagueScoreOverall[i].drawMatches,leagueScoreOverall[i].totalMatches)+'</td>');
				sb.push('<td bgcolor="#F8EDC5">'+percentage(leagueScoreOverall[i].loseMatches,leagueScoreOverall[i].totalMatches)+'</td>');
				sb.push('<td bgcolor="#EFFFDD">'+(leagueScoreOverall[i].goal/leagueScoreOverall[i].totalMatches).toFixed(2)+'</td>');
				sb.push('<td bgcolor="#E3F1FD">'+(leagueScoreOverall[i].lose/leagueScoreOverall[i].totalMatches).toFixed(2)+'</td>');
				sb.push('<td bgcolor="#FDFDCB">'+(leagueScoreOverall[i].drawMatches+leagueScoreOverall[i].winMatches*3)+'</td>');
				sb.push('</tr>');
			}
		}
	}
	sb.push('<tr bgcolor="#ffffff"><td colspan="15"><div align="center" id="leagueScoreRule">');
	if(leagueScoreRule){
		$.each(leagueScoreRule,function(index,content){
			sb.push('<font color="'+content.ruleColor+'">■</font>&nbsp;&nbsp;'+content.ruleName);
		});
	}
	sb.push('</div></td></tr></table>');
	$("#leagueScore").append(sb.join(''));
}
function init_lq(id,count){
	new_data = new Array();
	j = 0;
	$("#"+id).empty();
	data = [];
	switch(id){
		case 'v_against':data = v_data;break;
		case 'h_against':data = h_data;break;
		case 'g_against':data = g_data;break;
	}
	if(data){
		for(i=0;i<data.length;i++){
			if($("input[name=category_"+id+"]:checked").val()=='v_against_qb'){
				new_data[j] = data[i];
				initSelector(id,j+1,j+1);
				j++;
				continue;
			}else if($("input[name=category_"+id+"]:checked").val()=='v_against_zkx'){
				if(data[i].homeTeam==matchHomeTeam&&data[i].guestTeam==matchGuestTeam){
					new_data[j] = data[i];
					initSelector(id,j+1,j+1);
					j++;
				}
				continue;
			}else if($("input[name=category_"+id+"]:checked").val()=='v_against_tlx'){
				if(data[i].leagueId==sclassID){
					new_data[j] = data[i];
					initSelector(id,j+1,j+1);
					j++;
				}
				continue;
			}else if($("input[name=category_"+id+"]:checked").val()=='v_against_zc'){
				if(data[i].homeTeam==matchHomeTeam){
					new_data[j] = data[i];
					initSelector(id,j+1,j+1);
					j++;
				}
				continue;
			}else if($("input[name=category_"+id+"]:checked").val()=='v_against_kc'){
				if(data[i].guestTeam==matchGuestTeam){
					new_data[j] = data[i];
					initSelector(id,j+1,j+1);
					j++;
				}
				continue;
			}
			
		}
	}
	var c = count?count:new_data.length;
	$("#"+id).val(c);
	init_sv_lq(id,c);
}
function init_sv_lq(id,count){
	var sb = [];
	
	if(count){
		$("#"+id+"_table").remove();
	}
	data = new_data;
	sb.push('<table class="BothSide-table" id="'+id+'_table" cellpadding="0" cellspacing="0" border="1" >');
	sb.push('<tr bgcolor="#F6EEE0" height="30px"><td rowspan="2" width="60px">赛事</td>'+
			'<td rowspan="2">比赛时间</td><td rowspan="2">主队</td><td rowspan="2">比分</td><td rowspan="2">客队</td><td rowspan="2">胜负</td>'+
			'<td rowspan="2">分差</td>'+
			'<td rowspan="2">让分</td>'+
			'<td rowspan="2">让分胜负</td>'+
			'</tr><tr bgcolor="#F6EEE0" height="30px"></tr>');
	
		
// foreach()
	for(i=0;i<count;i++){
		
		sb.push('<tr bgcolor="#ffffff">');
		sb.push('<td height="26px" bgcolor="'+message(data[i].color)+'" class="color-white">'+data[i].name+'</td>');
		sb.push('<td>'+(new Date(data[i].matchTime)).format('yy-MM-dd')+'</td>');
		sb.push('<td><a href="javascript:void(0);" target="_blank" class="'+(id=="g_against"?(matchGuestTeam==data[i].homeTeam?"color-green":"color-black"):matchHomeTeam==data[i].homeTeam?"color-green":"color-black")+'">'+data[i].homeTeam+'</a>');
		sb.push('</td>');
		sb.push('<td><i class="color-red">'+data[i].homeScore+':'+data[i].guestScore+'</i></td>');
		sb.push('<td><a href="javascript:void(0);" target="_blank" class="'+(id=="g_against"?(matchGuestTeam==data[i].guestTeam?"color-green":"color-black"):matchHomeTeam==data[i].guestTeam?"color-green":"color-black")+'">'+data[i].guestTeam+'</a>');
		sb.push('</td>');
		sb.push((id=="g_against"?winLoseOrDrawForGuestTeam(data[i].homeTeamScore,data[i].guestScore,data[i].guestTeam):winLoseOrDrawForHomeTeam(data[i].homeScore,data[i].guestScore,data[i].homeTeam)));
		sb.push('<td class="color-red"><b>'+(data[i].homeScore-data[i].guestScore)+'</b></td>');
		sb.push('<td class="color-blue">'+message(data[i].letBallNum)+'</td>');
		sb.push(winOrloseForLetBall(data[i].letBallNum,data[i].homeScore,data[i].guestScore,data[i].homeTeam,data[i].guestTeam,matchHomeTeam));
		sb.push('</tr>');
	}
	
	$("#s_"+id).append(sb.join(''));
	
}
function winOrloseForLetBall(letNum,homeScore,guestScore,homeTeam,guestTeam,matchHome){
	if(letNum){
		var fencha = homeScore-guestScore-letNum;
		if(matchHome==homeTeam){
			if(fencha>0){
				return '<td class="color-red">赢</td>';
			}else{
				return '<td class="color-green">输</td>';
			}
		}else{
			if(fencha<0){
				return '<td class="color-red">赢</td>';
			}else{
				return '<td class="color-green">输</td>';
			}
		}
	}else{
		return '<td class="color-red"></td>';
	}
}
function message(mess){
	if(mess){
		return mess;
	}else{
		return '';
	}
}
function initSelector(id,text,value){
	$("#"+id).append("<option value='"+value+"'>"+text+"</option>");
}
function v_clicklx(matchlx){
	init($(matchlx).parent().parent().find("select").attr("id"));
}
function v_clicklx_lq(matchlx){
	init_lq($(matchlx).parent().parent().find("select").attr("id"));
}
function s_change_lq(id,count){
	init_lq(id,count);
}

function winOrloseForLetBall(letNum,homeScore,guestScore,homeTeam,guestTeam,matchHome){
	if(letNum){
		var fencha = homeScore-guestScore-letNum;
		if(matchHome==homeTeam){
			if(fencha>0){
				return '<td class="color-red">赢</td>';
			}else{
				return '<td class="color-green">输</td>';
			}
		}else{
			if(fencha<0){
				return '<td class="color-red">赢</td>';
			}else{
				return '<td class="color-green">输</td>';
			}
		}
	}else{
		return '<td class="color-red"></td>';
	}
}
