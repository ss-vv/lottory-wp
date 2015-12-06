/**
 * 显示彩种用户以及和当前登录用户的关注关系
 */
$(function() {
	ajax_load_lotteryUser();
});

var lotteryUserTemplate =
	'<div class="cp_type_wrap">'+
		'<div class="gray_bg">'+
			'<div>'+
				'<ul class="list clearfix">{{#weiboUsers}}'+
					'<li class="clearfix">'+
						'<img src="{{headImageURL}}">'+
						'<div>'+
							'<h3>{{nickName}}</h3>'+
							'{{#beFollowed}}'+
								'<div title="{{followStatus}}" class="sprites cp_type_btn cp_unfollow cp_add_friend"'+
									'_userid="{{weiboUserId}}" lottery="{{lotteryId}}"'+
									'flag="unFollowFlag">'+
									'<i class="sprites"></i><span>{{followStatus}}</span>'+
									'<div class="sprites"></div>'+
								'</div>'+
							'{{/beFollowed}}'+
							'{{^beFollowed}}'+
							'	<div title="{{followStatus}}" class="sprites cp_type_btn cp_follow cp_add_friend"'+
									'_userid="{{weiboUserId}}" lottery="{{lotteryId}}"'+
									'flag="followFlag">'+
							'		<i class="sprites"></i><span>{{followStatus}}</span>'+
							'		<div class="sprites"></div>'+
							'	</div>'+
							'{{/beFollowed}}'+
							
						'</div>'+
					'</li>{{/weiboUsers}}'+
				'</ul>'+
			'</div>'+
		'</div>'+
	'</div>';

var bindFollowAndUnFollow = function() {
	$(".cp_add_friend").click(function (event) {
		followAndUnFollowLotteryUser($(this));
		
		var lottery = $(event.target).parent().find('div').attr("lottery");
		var flag =  $(event.target).parent().find('div').attr("flag");
		if(!lottery) {
			lottery = $(event.target).parent().attr("lottery");
		}
		if(!flag) {
			flag = $(event.target).parent().attr("flag");
		}
		
		//显示或者隐藏彩种标签
		if("unFollowFlag" == flag) {
			hideLotteryList([lottery]);
		} else {
			showLotteryList([lottery]);
		}
		$(window).trigger("clearWeiboCache");
	});
};

var renderLotteryUser = function(result) {
	var lott = new lottery();
	var segment = $.mustache(lotteryUserTemplate, {
		weiboUsers: result,
		followStatus: function() {
			return this.beFollowed === true ? "取消关注" : "关注";
		},
		lotteryId: function() {
			return lott.getId(this.nickName);
		}
	});
	var lotterySetParam = $("#lotterySetParam");
	lotterySetParam.append(segment);
	bindFollowAndUnFollow();
};

//异步加载彩种用户
var ajax_load_lotteryUser = function() {
	showLoading($("#userFollowLottery"));
	$.ajax('user-setting/ajax_load_lotterySet', {
		dataType: 'json',
		success: function(result, status, xhr) {
			if(result && result.data) {
				var data = result.data;
				if(data.length > 0) {
					var lotteryList = [];
					var lott = new lottery();
					for(var i = 0; i<data.length; i++) {
						var lotteryObj = {};
						lotteryObj.name = lott.getId(data[i].nickName);
						lotteryObj.viewName = data[i].nickName;
						lotteryObj.beFollowed = data[i].beFollowed;
						lotteryList.push(lotteryObj);
					}
					lotteryShowView(lotteryList);
					renderLotteryUser(data);
				}
			}
			hideLoading($("#userFollowLottery"));
		}, error: function(result, status, xhr) {
			hideLoading($("#userFollowLottery"));
		}
	});
};

//显示彩种用户列表
var lotteryShowView = function(data) {
	var groupTab = $("#lotteryGroup");
	var liTpl = '<li id="{0}" class="{1}"><a title="{2}">{3}</a></li>';
	groupTab.empty();
	var isHaveLottery = false;//表示是否有关注彩种用户
	data = data.reverse();
	var ltNames = ["JCZQ","JCLQ","BJDC","CTZC","SSQ"];//确定标签顺序，如需调整，只需要修改这个顺序就可以了。
	var tmpData = new Array(ltNames.length);
	for(var i=0; i<data.length; i++) {
		var name = data[i].name;
		if(name == ltNames[0]){
			tmpData[0] = data[i];
		} else if(name == ltNames[1]){
			tmpData[1] = data[i];
		} else if(name == ltNames[2]){
			tmpData[2] = data[i];
		} else if(name == ltNames[3]){
			tmpData[3] = data[i];
		} else if(name == ltNames[4]){
			tmpData[4] = data[i];
		}
	}
	data = tmpData;
	for(var i=0; i<data.length; i++) {
		if(!data[i]){
			continue;
		}
		var active = "";		//是否需要激活
		//异步加载给定彩种的赛事数据/期信息
		sendAjaxLoadMatch(data[i].name);
		if(!LotteryTab.tellMeCurrentId()) {
			active = (0 == i) ? "active" : "";
		}
		if(true !== data[i].beFollowed) {
			active = "hide";
		}
		//加载当前激活彩种标签
		if(true === data[i].beFollowed && false === isHaveLottery) {
			var id = getDigitalLotteryLabel(data[i].name);
			
			var lotteryTabId = LotteryTab.tellMeCurrentId();
			if(lotteryTabId == id || !lotteryTabId) {
				$("#match_" + id).css({display:"block"});
				$("#match_" + id).addClass(active);
				isHaveLottery = true;
				active = "active";
			}
		}
		
		var template = null;
		var li = null;
		if(data[i].name == "JX11" || data[i].name == "SSQ" || data[i].name == "CQSS") {
			if(groupTab.find("#sz").length <= 0) {
				template = replacePlaceHolder(liTpl, ["sz", active, "数字彩", "数字彩"]);	
				li = $(template);
			}
		} else {
			if(data[i].name) {
				template = replacePlaceHolder(liTpl, 
						[data[i].name, active, data[i].viewName, data[i].viewName]);
				li = $(template);
			}
		}
		if(li) {
			groupTab.append(li);
		}
	}
	if(false == isHaveLottery) {
		active = "active";
	} else {
		active = "";
	}
	var template = replacePlaceHolder(liTpl, ["setLottery", active, "关注彩种", "关注彩种"]);
	groupTab.append($(template));
	if(false == isHaveLottery) {
		$(template).addClass("lotteryButton");
		$("#match_setLottery").show();
	}
	bindSwitchLottery();
};

//彩种标签切换
var bindSwitchLottery = function() {
	var groupTab = $("#lotteryGroup");
	groupTab.find("li").click(function() {
		var $this = $(this);
		var lotteryId = $(this).attr("id");
		$.each($(".groups > li"	), function(i, n) {
			if(lotteryId == $(n).attr("id")) {
				$this.addClass("lotteryButton");
				$("#match_" + $(n).attr("id")).show();
			} else {
				$(n).removeClass("lotteryButton");
				$(n).removeClass("active");
				$("#match_" + $(n).attr("id")).hide();
			}
		});
		LotteryTab.remember($(this));
	});
};

var getDigitalLotteryLabel = function(id) {
	var lott = new lottery();
	var ret = lott.isDigital(id);
	if(ret) {
		id = "sz";
	}
	return id;
};

var showLotteryList = function(lotteryList) {
	var groupTab = $("#lotteryGroup");
	for(var i=0; i<lotteryList.length; i++) {
		var id = lotteryList[i];
		id = getDigitalLotteryLabel(id);
		$("#" + id).removeClass("hide");
	}
};
var hideLotteryList = function(lotteryList) {
	var lott = new lottery();
	var groupTab = $("#lotteryGroup");
	for(var i=0; i<lotteryList.length; i++) {
		var id = lotteryList[i];
		id = getDigitalLotteryLabel(id);
		$("#" + id).addClass("hide");
	}
};

function followAndUnFollowLotteryUser(followBtn){
	var followFlag = "followFlag";
	var unFollowFlag = "unFollowFlag";
	var followFlagTitle = "加关注";
	var unFollowFlagTitle = "取消关注";
	
	var followString='<i class="sprites"></i><span>关注</span><div class="sprites"></div>';
	var unFollowString='<i class="sprites"></i><span>取消关注</span><div class="sprites"></div>';
	var flag = followBtn.attr("flag");
	if(followFlag == flag) {
		var userId = followBtn.attr("_userId");
		$.post("ajax_rltship_follow",{followIds:userId}, function(data , e) {
			if (data != null) {
				if (data.success) {
					followBtn.attr("title", unFollowFlagTitle);
					followBtn.attr("flag",unFollowFlag);
					followBtn.removeClass("cp_follow");
					followBtn.addClass("cp_unfollow");					
					followBtn.html(unFollowString);
				} else {
					alert(data.desc);
				}
			}
		}, 'json');
	} else {
		var userId = followBtn.attr("_userId");
		$.post("ajax_rltship_unfollow",{unfollowIds:userId}, function(data , e) {
			if (data != null) {
				if (data.success) {
					followBtn.attr("title",followFlagTitle);
					followBtn.attr("flag",followFlag);
					followBtn.addClass("cp_follow");
					followBtn.removeClass("cp_unfollow");
					followBtn.html(followString);
				} else {
					alert(data.desc);
				}
			}
		}, 'json');
	}
}

var log = function(){
	if(console && console.log) {
		console.log(arguments[0]);
	}
};