/**
 * 异步加载首页公告和中奖喜报内容
 * */
$(function() {
	//异步显示"公告"列表
	ajaxMarrowData($("#announceList"), 1, "/ajax/ajaxLoadAnnounce", render_announce, null);
	
	//异步显示"中间喜报"列表
	//ajaxMarrowData($("#winningNewsList"), 1, "/ajax/ajaxLoadWinningNews", render_winning, null);
});

/**显示和隐藏加载进度*/
var showLoading = function(container) {
	container.next('.loading').show();
};
var hideLoading = function(container) {
	container.next('.loading').hide();
};

/**
 * page:页号
 * url:请求地址
 * render:渲染方法
 * ajaxPagerFn:分页按钮点击事件
 */
var ajaxMarrowData = function(container, page, url, render, ajaxPagerFn) {
	showLoading(container);
	var pager = {page:1};
	if(null != page && page > 0) {
		pager.page = page;
	}
	$.ajax({
		url: url,
		dataType: 'json',
		data: pager,
		cache: false,
		success: function(json){
			hideLoading(container);
			if(json != null && json.success == true
					&& json.data.results != null
					&& container.length > 0) {
				render.call(this, json, container);
			}
		}
	});
}

var lotteryName = {
	JCZQ : "JCZQ",
	JCLQ : "JCLQ",
	CTZC : "CTZC",
	SSQ : "SSQ",
	JX11 : "JX11",
	BJDC :"BJDC",
	getViewName : function(lottery) {
		var name = null;
		switch (lottery) {
		case this.JCZQ:
			name = "竞彩足球";
			break;
		case this.JCLQ:
			name = "竞彩蓝球";
			break;
		case this.CTZC:
			name = "传统足彩";
			break;
		case this.SSQ:
			name = "双色球";
			break;
		case this.JX11:
			name = "江西十一选五";
			break;
		case this.BJDC:
			name = "北京单场";
			break;
		}
		return name;
	}
};

//====================================精华列表渲染===================================================
//将渲染好的数据追加到DOM中，同时绑定系列事件
var appendDomAndEventBind = function(container, data) {
	container.html(data);
	container.emotionsToHtml();
	bindMouseInHeadOrNicknameEvent(container);
};
//渲染公告列表
function render_announce(json, container){
	var results = json.data.results;
	var segment = $.mustache(announceTemplate, {
		announces: results.splice(0,5)
	});
	appendDomAndEventBind(container, segment);
}

//渲染中奖喜报列表
function render_winning(json, container){
	var results = json.data.results;
	var segment = $.mustache(winningTemplate, {
		winnings: results.splice(0,5),
		lotteryName: function(){
			var lotteryId = this.lotteryId;
			return lotteryName.getViewName(lotteryId);
		},
		certificationTypeView:function (){
			if(this.weiboUser.certificationType == 1){
				return '<i class="niu" title="大V彩牛人" style="background:url(\'../images/niu.png\') 50% no-repeat;width: 32px;height: 15px;display: inline-block;"></i>';
			} else {
				return '';
			}
		}
	});
	appendDomAndEventBind(container, segment);
}

//====================================列表相关模板===================================================
//公告列表模板
var announceTemplate =
'<ul class="tipstopic-list">{{#announces}}'+
'	<li><span class="item">' +
'		<a href="/{{ownerId}}/{{id}}" target="_blank">{{&content}}</a>'+
'	</span></li>'+
'{{/announces}}</ul>';

//中奖喜报列表模板
var winningTemplate =
'{{#winnings}}' +
	'<li>' +
	    '<div class="headpic" _userid="{{userId}}">' +
	        '<a href="/{{userId}}/profile" target="_blank"><img width="50px" height="50px" src="{{weiboUser.headImageURL}}"></a>' +
	    '</div>' +
	    '<div class="content winning">' +
	        '<a href="/{{userId}}/profile" target="_blank"><span _nickname_block="_nickname_block" _userid="{{userId}}">{{weiboUser.nickName}}{{{certificationTypeView}}}</span></a>' +
	        '<div class="userDes">{{lotteryName}}</div>' +
	        '<div class="userDes"><a target="_blank" href="/{{userId}}/{{weiboId}}"><span class="winColor">中奖：{{bonus}}元</span></a></div>' +
	    '</div>' +
	'</li>' +
'{{/winnings}}';


