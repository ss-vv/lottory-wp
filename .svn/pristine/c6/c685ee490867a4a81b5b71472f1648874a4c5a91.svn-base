//竞彩足球双方对阵历史
function twoSideAgainstHistory(homeTeamId,guestTeamId){
	$ .ajax({  
		type : "post",  
        url : "ajax_twoSideAgainstHistory.do",  
        dataType : 'json',
        data:{
        	homeTeamId:homeTeamId,
        	guestTeamId:guestTeamId
        },
        success : function(data){
        	v_data=eval(data.data);
        	init('v_against');
        }  
    });
}
//主队近期战绩
function homeRecentRecord(homeTeamId){
	$.ajax({
		type:"post",
		url:"ajax_teamRecentRecord.do",
		dataType:'json',
		data:{
			teamId:homeTeamId
		},
		success:function(data){
			h_data=eval(data.data);
			init('h_against');
		}
	});
}
//客队近期战绩
function guestRecentRecord(guestTeamId){
	$.ajax({
		type:"post",
		url:"ajax_teamRecentRecord.do",
		dataType:'json',
		data:{
			teamId:guestTeamId
		},
		success:function(data){
			g_data=eval(data.data);
			init('g_against');
		}
	});
}
//联赛积分榜
/**
 * @param rankType 排行榜类型{total_rank:总积分榜,zc_rank:主场积分榜,kc_rank:客场积分榜,half_total_rank:半场总积分榜,half_zc_rank:主场半场积分榜,half_kc_rank:客场半场积分榜}
 */
function leagueScoreRank(rankType){
	$.ajax({
		type:"post",
		url:"ajax_leagueScoreRank.do",
		dataType:'json',
		data:{
			leagueId:sclassID,
			rankType:rankType
		},
		success:function(data){
			leagueScore_data=eval("("+data.data+")");
//			console.log(leagueScore_data.leagueScoreOverall);
			init_leagueScore(leagueScore_data.leagueScoreRule,leagueScore_data.leagueScoreOverall,rankType);
		}
	});
}


function init_leagueScore(leagueScoreRule,leagueScoreOverall,rankType){
	var sb = [];
	sb.push('<table class="lsjfb-menu-list" cellpadding="0" id="rank_'+rankType+'" '+(rankType=='total_rank'?'style="display:table;"':'style="display:none;"')+' cellspacing="0" border="0"><tr bgcolor="#F6EEE0"><td><b>排名</b></td><td><b>球队名称</b></td>'+
			'<td><b>&nbsp;赛&nbsp;</b></td>'+
			'<td><b>&nbsp;胜&nbsp;</b></td>'+
			'<td><b>&nbsp;平&nbsp;</b></td>'+
			'<td><b>&nbsp;负&nbsp;</b></td>'+
			'<td><b>&nbsp;得&nbsp;</b></td>'+
			'<td><b>&nbsp;失&nbsp;</b></td>'+
			'<td><b>&nbsp;净&nbsp;</b></td>'+
			'<td><b>胜%</b></td>'+
			'<td><b>平%</b></td>'+
			'<td><b>负%</b></td>'+
			'<td><b>均得</b></td>'+
			'<td><b>均失</b></td>'+
			'<td><b>积分</b></td>'+
			'</tr>');
	if(rankType=='total_rank'){
		for(i=0;i<leagueScoreOverall.length;i++){
			if(leagueScoreOverall[i]!=null){
				sb.push('<tr bgcolor="#ffffff">');
				sb.push('<td>'+(i+1)+'</td>');
				sb.push('<td bgcolor="'+(leagueScoreOverall[i].ruleNum!=-1?leagueScoreRule[leagueScoreOverall[i].ruleNum].ruleColor:'')+'" align="left">');
				sb.push('	<a href="javascript:void(0);" class="color-brown">'+leagueScoreOverall[i].teamName+'</a>');
				sb.push('</td>');
				sb.push('<td>'+leagueScoreOverall[i].totalMatches+'</td>');
				sb.push('<td>'+leagueScoreOverall[i].winMatches+'</td>');
				sb.push('<td>'+leagueScoreOverall[i].drawMatches+'</td>');
				sb.push('<td>'+leagueScoreOverall[i].loseMatches+'</td>');
				sb.push('<td>'+leagueScoreOverall[i].goal+'</td>');
				sb.push('<td>'+leagueScoreOverall[i].miss+'</td>');
				sb.push('<td>'+(leagueScoreOverall[i].goal-leagueScoreOverall[i].miss)+'</td>');
				sb.push('<td bgcolor="#F6DBD4">'+percentage(leagueScoreOverall[i].winMatches,leagueScoreOverall[i].totalMatches)+'</td>');
				sb.push('<td bgcolor="#F8EDC5">'+percentage(leagueScoreOverall[i].drawMatches,leagueScoreOverall[i].totalMatches)+'</td>');
				sb.push('<td bgcolor="#F8EDC5">'+percentage(leagueScoreOverall[i].loseMatches,leagueScoreOverall[i].totalMatches)+'</td>');
				sb.push('<td bgcolor="#EFFFDD">'+(leagueScoreOverall[i].goal/leagueScoreOverall[i].totalMatches).toFixed(2)+'</td>');
				sb.push('<td bgcolor="#E3F1FD">'+(leagueScoreOverall[i].miss/leagueScoreOverall[i].totalMatches).toFixed(2)+'</td>');
				sb.push('<td bgcolor="#FDFDCB">'+leagueScoreOverall[i].score+'</td>');
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

function init(id,count){
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
				if(data[i].homeTeamId==matchHomeTeam&&data[i].guestTeamId==matchGuestTeam){
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
				if(data[i].homeTeamId==matchHomeTeam){
					new_data[j] = data[i];
					initSelector(id,j+1,j+1);
					j++;
				}
				continue;
			}else if($("input[name=category_"+id+"]:checked").val()=='v_against_kc'){
				if(data[i].guestTeamId==matchGuestTeam){
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
	init_sv(id,c);
}
function init_sv(id,count){
	var sb = [];
	
	if(count){
		$("#"+id+"_table").remove();
	}
	data = new_data;
	sb.push('<table class="BothSide-table" id="'+id+'_table" cellpadding="0" cellspacing="0" border="1" >');
	sb.push('<tr bgcolor="#F6EEE0" height="30px"><td rowspan="2" width="60px">赛事</td><td rowspan="2">比赛时间</td><td rowspan="2" width="70px">主队</td>'+
			'<td rowspan="2">比分(半场)</td><td rowspan="2" width="70px">客队</td><td rowspan="2">胜负</td><td colspan="3" width="128px">'+
			'	<select class="opSelect-v"><option value="1">竞彩让球</option><option value="1">竞彩胜平负</option><option value="1" selected="selected">平均欧赔</option>'+
			'<option value="1">澳门欧赔</option></select><select class="opType-v"><option value="1">初盘</option><option value="1" selected="selected">终盘</option></select>'+
			'</td><td colspan="3" width="128px"><select class="ypSelect-v"><option value="1" selected="selected">皇冠亚赔</option></select>'+
			'	<select class="ypType-v"><option value="1">初盘</option></select></td><td rowspan="2">竞彩<br>胜负</td><td rowspan="2">竞彩<br>让球</td><td rowspan="2">进球</td>'+
			'<td title="上下单双" rowspan="2">上下<br>单双</td><td rowspan="2">半全</td></tr>'+
			'<tr bgcolor="#F6EEE0" height="30px"><td width="54px">胜</td><td width="54px">平</td><td width="54px">负</td><td width="54px">主水</td><td width="54px">盘口</td><td width="54px">客水</td>'+
			'</tr>');
	
		
// foreach()
	for(i=0;i<count;i++){
		
		sb.push('<tr bgcolor="#ffffff">');
		sb.push('<td height="26px" bgcolor="'+data[i].colour+'" class="color-white">'+data[i].leagueShortName+'</td>');
		sb.push('<td>'+(new Date(data[i].matchTime)).format('yy-MM-dd')+'</td>');
		sb.push('<td><a href="javascript:void(0);" target="_blank" class="'+(id=="g_against"?(matchGuestTeam==data[i].homeTeamId?"color-green":"color-black"):matchHomeTeam==data[i].homeTeamId?"color-green":"color-black")+'">'+data[i].homeTeamId+'</a>');
		sb.push('</td>');
		sb.push('<td><i class="color-red">'+data[i].homeTeamScore+'-'+data[i].guestTeamScore+'</i> ('+data[i].homeTeamHalfScore+'-'+data[i].guestTeamHalfScore+')</td>');
		sb.push('<td><a href="javascript:void(0);" target="_blank" class="'+(id=="g_against"?(matchGuestTeam==data[i].guestTeamId?"color-green":"color-black"):matchHomeTeam==data[i].guestTeamId?"color-green":"color-black")+'">'+data[i].guestTeamId+'</a>');
		sb.push('</td>');
		sb.push((id=="g_against"?winLoseOrDrawForGuestTeam(data[i].homeTeamScore,data[i].guestTeamScore,data[i].guestTeamId):winLoseOrDrawForHomeTeam(data[i].homeTeamScore,data[i].guestTeamScore,data[i].homeTeamId)));
		sb.push('<td bgcolor="#E3F1FD">0</td>');
		sb.push('<td bgcolor="#E3F1FD">0</td>');
		sb.push('<td bgcolor="#E3F1FD">0</td>');
		sb.push('<td bgcolor="#F4FCED">0</td>');
		sb.push('<td bgcolor="#F4FCED">0</td>');
		sb.push('<td bgcolor="#F4FCED">0</td>');
		sb.push(typeof(data[i].jingcaiId)=='undefined'?'<td></td>':winLoseOrDraw(data[i].homeTeamScore,data[i].guestTeamScore));
		sb.push('<td class="color-blue"></td>');
		sb.push('<td class="color-red">'+(data[i].homeTeamScore+data[i].guestTeamScore)+'</td>');
		sb.push('<td class="color-red">'+singleMult(data[i].homeTeamScore,data[i].guestTeamScore)+'</td>');
		sb.push('<td class="color-blue">'+halfAndAll(data[i].homeTeamScore,data[i].guestTeamScore,data[i].homeTeamHalfScore,data[i].guestTeamHalfScore)+'</td>');
		sb.push('</tr>');
	}
	
	$("#s_"+id).append(sb.join(''));
	
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
function s_change(id,count){
	init(id,count);
}

