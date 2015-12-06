var matchInfoBlockTpl ='{{#data}}<div class="match-wrap" id="match_card_{{matchId}}" _homeTeamId="{{homeTeamId}}" _guestTeamId="{{guestTeamId}}" _matchcard="_matchcard" style="position: absolute;">'
  +'	<div class="match-wrap-top">'
  +'       <div class="match-wrap-top-left">'
  +'		<img src="{{homeTeamLogoUrl}}" class="img_left"/>'
  +'		<span class="name_left">{{homeTeamNameView}}</span>'
  +'      </div>'
  +'		      <div class="match-wrap-detail">'
  +'             <p class="score">'
  +'		 	      <span class="score_left">{{homeTeamScoreView}}</span>'
  +'		 	      <span class="vs">VS</span>'
  +'		 	      <span class="score_right">{{guestTeamScoreView}}</span>'
  +'              </p>'
  +'		 	      <p class="time"><label>{{matchTimeView}}</label></p>'
  +'		          <p class="first-half">上半场&nbsp;&nbsp;<label>25</label></p>'
  +'				  <p class="rest">中场休息</p>'
  +'				  <p class="second-half">上半场&nbsp;&nbsp;<label>45</label></p>'
  +'				  <p class="end">完场</p>'
  +'				  <p class="delay">延迟</p>'
  +'		 	      <p class="odds">{{odd3}}&nbsp&nbsp&nbsp{{odd1}}&nbsp&nbsp&nbsp{{odd0}}</p>'
  +'		     </div>'
  +'      <div class="match-wrap-top-right">'
  +'		<img src="{{guestTeamLogoUrl}}" class="img_right"/>'
  +'		<span class="name_right">{{guestTeamNameView}}</span>' 
  +'      </div>'
  +'	</div>'
  +'	<div class="match-wrap-bottom">'
  +'	  <div class="zan">'
  +'	  	  	<p class="shidan">实单：<span>{{realCount}}</span></p>'
  +'	  	  	<a href="javascript:void(0);" _id="favor_team_btn" isfavored="{{favorHomeTeam}}" teamId="{{homeTeamId}}" matchId="{{matchId}}" lotteryType="{{lotteryType}}">'
  +'		   		<p class="pop {{homeSelectedView}}"  _selectedColor="_selectedColor"><span class="hand"></span><label _id="favor_count_label">{{favorHomeTeamCount}}</label></p>'
  +'			</a>'
  +'	  </div>'
  +'	   <div class="zan">'
  +'	  	   <p class="shidan">推荐：<span>{{recCount}}</span></p>'
  +'	  	   <a href="javascript:void(0);" _id="favor_team_btn" isfavored="{{favorGuestTeam}}" teamId="{{guestTeamId}}" matchId="{{matchId}}" lotteryType="{{lotteryType}}">'
  +'		   		<p class="pop {{guestSelectedView}}" _selectedColor="_selectedColor"><span class="hand"></span><label _id="favor_count_label">{{favorGuestTeamCount}}</label></p>'
  +'		   </a>'
  +'	  </div>'
  +'	</div>'
  +'</div>{{/data}}';
var matchCardShowTimer;
var matchCardHideTimer;
function getFBMatchStatusName(matchStatus) {
		var status = "--";
		switch (matchStatus) {
		case 0:
			status = "未开";
			break;
		case 1:
			status = "上半场";
			break;
		case 2:
			status = "中场";
			break;
		case 3:
			status = "下半场";
			break;
		case -11:
			status = "待定";
			break;
		case -12:
			status = "腰斩";
			break;
		case -13:
			status = "中断";
			break;
		case -14:
			status = "推迟";
			break;
		case -1:
			status = "完场";
			break;
		}
		return status;
}
function getBBMatchStatusName(matchStatus) {
	var status = "--";
	switch (matchStatus) {
	case 0:
		status = "未开";
		break;
	case 1:
		status = "第一节";
		break;
	case 2:
		status = "第二节";
		break;
	case 3:
		status = "第三节";
		break;
	case 4:
		status = "第四节";
		break;
	case -1:
		status = "完场";
		break;
	case -2:
		status = "待定";
		break;
	case -3:
		status = "中断";
		break;
	case -4:
		status = "取消";
		break;
	case -5:
		status = "推迟";
		break;
	case 50:
		status = "中场";
		break;
	}
	return status;
}
function rendJCZQMatch(matchId,loteryType,left,top){
	$.getJSON("/ajax_match_float_card?matchId=" + matchId + "&lotteryType="+loteryType,function (data){
		var html = $.mustache(matchInfoBlockTpl,{
			data:data,
			matchTimeView: function (){
				if(this.matchStatus == 0 && this.matchTime){
					var time = this.matchTime.replace("T","  ");
					time = time.substring(0,time.length -3);
					return time;
				} else if(!this.matchTime){
					return ' ';
				} else {
					return getFBMatchStatusName(this.matchStatus);
				}
			},
			odd3:function (){
				if(this.odds){
					var odd = this.odds.split(",");
					return "胜：" + odd[0];
				} else {
					return "胜：--";
				}
			},
			odd1:function (){
				if(this.odds){
					var odd = this.odds.split(",");
					return "平：" + odd[1];
				} else {
					return "平：--";
				}
			},
			odd0:function (){
				if(this.odds){
					var odd = this.odds.split(",");
					return "负：" + odd[2];
				} else {
					return "负：--";
				}
			},
			homeTeamNameView:function (){
				return this.homeTeamName;
			},
			guestTeamNameView:function (){
				return this.guestTeamName;
			},
			homeTeamScoreView:function (){
				return this.homeTeamScore;
			},
			guestTeamScoreView:function (){
				return this.guestTeamScore;
			},
			homeSelectedView:function (){
				if(this.favorHomeTeam){
					return "selected";
				}
			},
			guestSelectedView:function (){
				if(this.favorGuestTeam){
					return "selected";
				}
			}
		});
		$(document.body).append(html);
		bindMatchCardAndShow(matchId,left,top);
	});
}
function rendJCLQMatch(matchId,loteryType,left,top){
	$.getJSON("/ajax_match_float_card?matchId=" + matchId + "&lotteryType="+loteryType,function (data){
		var html = $.mustache(matchInfoBlockTpl,{
			data:data,
			matchTimeView: function (){
				if(this.matchStatus == 0 && this.matchTime){
					var time = this.matchTime.replace("T","  ");
					time = time.substring(0,time.length -3);
					return time;
				} else if(!this.matchTime){
					return ' ';
				} else {
					return getBBMatchStatusName(this.matchStatus);
				}
			},
			odd3:function (){
				if(this.odds){
					var odd = this.odds.split(",");
					return "胜：" + odd[0];
				} else {
					return "胜：--";
				}
			},
			odd1:function (){
				return "";
			},
			odd0:function (){
				if(this.odds){
					var odd = this.odds.split(",");
					return "负：" + odd[1];
				} else {
					return "负：--";
				}
			},
			homeTeamNameView:function (){
				return this.homeTeam;
			},
			guestTeamNameView:function (){
				return this.guestTeam;
			},
			homeTeamScoreView:function (){
				if(this.matchStatus == 1){
					return this.homeOne;
				} else if(this.matchStatus == 2){
					return this.homeTwo;
				} else if(this.matchStatus == 3){
					return this.homeThree;
				} else if(this.matchStatus == 4){
					return this.homeFour;		
				} else if(this.matchStatus == -1){
					return this.homeScore;
				} else {
					return 0;
				} 
			},
			guestTeamScoreView:function (){
				if(this.matchStatus == 1){
					return this.guestOne;
				} else if(this.matchStatus == 2){
					return this.guestTwo;
				} else if(this.matchStatus == 3){
					return this.guestThree;
				} else if(this.matchStatus == 4){
					return this.guestFour;		
				} else if(this.matchStatus == -1){
					return this.guestScore;
				} else {
					return 0;
				}
			},homeSelectedView:function (){
				if(this.favorHomeTeam){
					return "selected";
				}
			},
			guestSelectedView:function (){
				if(this.favorGuestTeam){
					return "selected";
				}
			}
		});
		$(document.body).append(html);
		bindMatchCardAndShow(matchId,left,top);
	});
}

function bindMatchCardAndShow(matchId,left,top){
	var $block = $("#match_card_"+matchId);
	$block.mouseover(function (){ //浮框事件
		if(matchCardHideTimer){
			clearTimeout(matchCardHideTimer);
		}
		$(this).show();
	}).mouseout(function (){
		var $this = $(this);
		matchCardHideTimer = setTimeout(function (){
			$this.hide();
		},200);
	});
	bindFavorTeamEvent($("[_id='favor_team_btn']",$block));
	$block.css("left",left + "px");
	$block.css("top",top + "px");
	$("[_matchcard]").hide();
	$block.show();
}
function bindFavorTeamEvent($btn){
	$btn.click(function (e){
		var $this = $(this);
		$this.unbind("click");
		var data = {
			lotteryType:$this.attr("lotteryType"),
			teamId:$this.attr("teamId"),
			matchId:$this.attr("matchId")
		};
		if($this.attr("isfavored") == "true"){
			sendUnFavorMatchTeam(data,$this);
		} else {
			sendFavorMatchTeam(data,$this);
		}
	});
}

function sendFavorMatchTeam(data,$this){
	$.ajax({
		type:"post",
		dataType:"json",
		url : "/ajax_like_team.do",
		data:data,
		success : function(result) {
			if(result && result.success == true) {
				$.msgbox('支持成功', {anchor:$this,success:true});
				var $countLabel = $("[_id='favor_count_label']",$this);
				var $color = $("[ _selectedColor='_selectedColor']",$this);
				var count = $countLabel.text();
				count = parseInt(count) + 1;
				$countLabel.html(count);
				$color.addClass("selected");
				$this.attr("isfavored","true");
			} else {
				$.msgbox('支持失败：' + result.data, {anchor:$this,success:false});
			}
			bindFavorTeamEvent($this);
		}
	});
}
function sendUnFavorMatchTeam(data,$this){
	$.ajax({
		type:"post",
		dataType:"json",
		url : "/ajax_unlike_team",
		data:data,
		success : function(result) {
			if(result && result.success == true) {
				$.msgbox('已取消支持', {anchor:$this,success:true});
				var $countLabel = $("[_id='favor_count_label']",$this);
				var $color = $("[ _selectedColor='_selectedColor']",$this);
				var count = $countLabel.text();
				count = parseInt(count) - 1;
				$countLabel.html(count <= 0 ? 0:count);
				$color.removeClass("selected");
				$this.attr("isfavored","false");
			} else {
				$.msgbox('取消支持失败：' + result.data, {anchor:$this,success:false});
			}
			bindFavorTeamEvent($this);
		}
	});
}
function rendMatchCard(matchId,loteryType,event,iframeid){
	var $block = $("#match_card_"+matchId);
	var iframe = $("#"+ iframeid);
	var e = event.target;
	var result =  getElementPosition(e,iframe[0]);
	if($block.length == 0){
		if(loteryType == "JCZQ" || loteryType == "BJDC"){
			rendJCZQMatch(matchId,loteryType,result.x,result.y);
		} else if(loteryType == "JCLQ"){
			rendJCLQMatch(matchId,loteryType,result.x,result.y);
		} else if(loteryType == "CTZC"){
			
		} else {
			
		}
	} else {
		$block.css("left",result.x + "px");
		$block.css("top",result.y + "px");
		$("[_matchcard]").hide();
		$block.show();
	}
}

function bindMouseInMatch$StringEvent($context){
	$matchs = $("a[_matchFloatCardType]",$context);
	$matchs.each(function (index){
		$(this).mouseover(function (e){
			var $this = $(this);
			if(matchCardHideTimer){
				clearTimeout(matchCardHideTimer);
			}
			matchCardShowTimer = setTimeout(function (){
				var matchId = $this.attr("_matchId");
				var loteryType = $this.attr("_matchFloatCardType");
				rendMatchCard(matchId,loteryType,e,$this.attr("iframeid"));
			},200);
		}).mouseout(function (e){
			var $this = $(this);
			if(matchCardShowTimer){
				clearTimeout(matchCardShowTimer);
			}
			matchCardHideTimer = setTimeout(function (){
				var matchId = $this.attr("_matchId");
				$("#match_card_"+matchId).hide();
			},200);
		});
	});
}

function getElementPosition(el,iframe) { 
	var obj=el,offset=new Object(),x=0,y=0; 
	while(obj&&obj.tagName != "BODY") { 
		x+=obj.offsetLeft; 
		y+=obj.offsetTop; 
		obj=obj.offsetParent;
	}
	obj = iframe;
	while(obj&&obj.tagName != "BODY") { 
		x+=obj.offsetLeft; 
		y+=obj.offsetTop; 
		obj=obj.offsetParent;
	}
	offset.x=x-320;
	offset.y=y+15;
	return offset; 
}