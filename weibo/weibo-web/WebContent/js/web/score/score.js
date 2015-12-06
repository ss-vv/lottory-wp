function ajaxLoadScore(date, fn){
		$.post($("#url").val(), {date : date}, function(result, e) {
			$(".loading").hide();
			if (result != null) {
				if (result.success) {
					fn.ui(result.results);
				} else {
					alert(result.desc);
					return ;
				}
			}
			return ;
		}, 'json').error(function(e) {
			return ;
		});
	}

//定时更新比分
function scoreTimer(date){
	ajaxLoadScore(date, new updateScore());
}

function updateScore(){
	this.ui = function(scores){
		for(var i =0; i< scores.length ;i++){
			var score = scores[i];
			var $tr=$("#score"+score.id);
			$tr.find("[_matchTime]").html(score.matchTime);
			$tr.find("[_mStatus]").html(mStatus(score.matchStatus));
			$tr.find("[_score]").html(score.score);
			$tr.find("[_halfScore]").html(score.halfScore);
		}
	}
}

function  mStatus(matchStatus){
	if(matchStatus == -1){
		return '<b style="color:blue">完场</b>';
	}
	if(matchStatus == 0){
		return '<b>未开</b>';
	}
	if(matchStatus == 1){
		return '<b>上半场</b>';
	}
	if(matchStatus == 2){
		return '<b style="color:red">中场</b>';
	}
	if(matchStatus == 3){
		return '<b>下半场</b>';
	}
	if(matchStatus == -11){
		return '<b>待定</b>';
	}
	if(matchStatus == -12){
		return '<b>腰斩</b>';
	}
	if(matchStatus == -13){
		return '<b>中断</b>';
	}
	if(matchStatus == -14){
		return '<b style="color:red">推迟</b>';
	}
	return '';
}

var countdownStart = 60;
var clearTimeoutId = "";
function countdown(){
	document.getElementById("countdown").innerHTML = countdownStart;
	countdownStart += -1;
	if(countdownStart < 0){
		countdownStart=60;
	}
	clearTimeoutId = setTimeout("countdown()",1000);
}

$(document).ready(function() {
	var clearIntervalId="";
	var $totalMatchCount = $("#totalMatchCount");
	var $totalMatchHideCount = $("#totalMatchHideCount");
	var $loading = $(".loading");
	if($loading.length==0){
		$loading = $('<div class="loading" style="height: 40px; display: block;width:814px;"></div>');
		$loading.appendTo($(".scores_list"));
	}else{
		$loading.show();
	}
	
	createDateSelectUI();
	ajaxLoadScore('', new createScoreUI(''));
	// 生成比分直播UI
	function createScoreUI(date){
		var count = 1;
		this.ui = function (scores){
			var socreRenderParse ={
					scores : scores,
					bgcolor : function(){
						count++;
						return count%2 == 0 ? '#ffffff' : '#f2f2f2';
					},
					mStatus : function(){
						return mStatus(this.matchStatus);
					}
				};

				var tpl ='{{#scores}}' 
					+'<tr bgcolor="{{bgcolor}}" id="score{{id}}">'
		            +'<td align="center" height="36">'
		            +'<label>'
		            +'<div class="checkbox">'
		            +'<input type="checkbox">'
		            +'<span class="icon sprites"></span>'
		            +'<span class="label">{{cnCode}}</span>'
		            +'</div>'
		            +'</label>'
		            +'</td>'
		            +'<td align="center" bgcolor="{{color}}"><span class="fc_white">{{leagueShortName}}</span></td>'
		            +'<td align="center"><b _matchTime>{{matchTime}}</b></td>'
		            +'<td align="center" _mStatus>{{{mStatus}}}</td>'
		            +'<td align="center"><b>[{{homeTeamPosition}}]{{homeTeamName}}</b></td>'
		            +'<td align="center" _score>{{score}}</td>'
		            +'<td align="center">{{guestTeamName}}[{{guestTeamPosition}}]</td>'
		            +'<td align="center" _halfScore>{{halfScore}}</td>'
		            +'</tr>'
		            +'{{/scores}}' ;
				
				var $socreHtml = $($.mustache(tpl, socreRenderParse));
				$socreHtml.appendTo($("#scoreTbody"));
				$totalMatchCount.text($socreHtml.length);
				if(date == '' && scores.length > 0){
					date = "2014-" + scores[0].matchTime.substring(0,5);
				}
				// 每60秒更新一次比分
				clearIntervalId = setInterval("scoreTimer('"+ date +"')", 60000);
				countdown();
		}
	}
	
	// 生产日期下拉菜单
	function createDateSelectUI(){
		if($("#issueselect option").length==0){
			var $issue = $("#issueselect");
			for(var i=0; i <30 ; i++){
				var sourceDate = new Date();
				sourceDate.setTime(sourceDate.getTime() - ( i * 86400000));
				var month = (sourceDate.getMonth()+1) <= 9 ? "0"+(sourceDate.getMonth()+1) : (sourceDate.getMonth()+1) ;
				var day = sourceDate.getDate() <= 9 ? "0" + sourceDate.getDate() : sourceDate.getDate();
				var date = sourceDate.getFullYear()+"-" + month + "-" + day;
				$issue.append("<option value='"+date+"'>"+date+"</option>");
			}
			
		}
	}
	
	$("#issueselect").change(function (e) {
		var $this = $(this);
		$("#scoreTbody tr").remove();
		clearTimeout(clearTimeoutId);
		clearInterval(clearIntervalId);
		countdownStart=60;
		$totalMatchHideCount.text(0);
		ajaxLoadScore($this.val(), new createScoreUI($this.val()));
	});
	
	$("#saveBtn").click(function (e) {
		if($("input:checkbox:checked:not(:hidden)").length ==0){
			alert("请选择需要保留的赛事");
			return;
		}
		var $hideScore=$("input:checkbox:not(:checked)").closest("tr");
		$hideScore.hide();
		$totalMatchHideCount.text($("input:checkbox:hidden").length);
	});
	
	$("#hideBtn").click(function (e) {
		if($("input:checkbox:checked:not(:hidden)").length ==0){
			alert("请选择需要隐藏的赛事");
			return;
		}
		var $hideScore=$("input:checkbox:checked").closest("tr");
		$hideScore.hide();
		$totalMatchHideCount.text($("input:checkbox:hidden").length);
	});
	
	$("#reSetBtn").click(function (e) {
		var $hideScore=$("input:checkbox").closest("tr");
		$hideScore.show();
		$totalMatchHideCount.text(0);
	});
});