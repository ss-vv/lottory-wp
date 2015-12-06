//======根据投注选项组装推荐内容======
var BetContent = {};

BetContent["cookieConfig"] = {
	"expires":1,
	"domain":".davcai.com", 
	"path":"/"
};

var matchIdMap = {};

/**显示对话框*/
var showModel = function(modelId) {
	var w = $("#"+modelId);
	w.modal('show');
};

/**隐藏对话框*/
var hideModel = function(modelId) {
	var shareWindow = $("#"+modelId);
	shareWindow.modal('hide');
};

//当前页面使用cookie保存赛事推荐的key
var recCookieKey = "";
var _initMatch = function() {
	recCookieKey = "recCookieKey";
};

/**
 * 显示加注释的对话框，并绑定数据
 * 数据放在this对象的_matchStr属性上。
 */
var _matchId = "";
var matchIndex = "";
var showAddRecMatchDialog = function(mId,i) {
	_matchId = mId;
	matchIndex = i;
	$("#commentContent").val("");
	//存cookie
	var matchId = $.cookie(recCookieKey + "_" + mId);
	if(matchId) {
		$("#commentContent").val(matchId);
	}
	showModel("commentModal");
};

/**
 * 保存用户注释内容
 * 使用Cookie方式存储推荐内容；每场比赛推荐的key使用('玩法_比赛ID')
 * 玩法页的Cookie列表的key使用玩法ID，值为每场比赛的key集合并使用','号分割
 */
var saveComment = function() {
	if(!_matchId) return;
	var ckey = _matchId;
	var commentContent = $("#commentContent").val();
	var matchKey = recCookieKey + "_" + ckey;
	if(commentContent) {
		if(commentContent.length > 1000) {
			$("#modal-tips").text("注释内容不能超过1000个字符！");
		}
	}
	$.cookie(matchKey, commentContent, BetContent.cookieConfig);
	addValue(matchKey);
	hideModel("commentModal");
	$("#modal-tips").text("");
	$('[_n="bet-content-table"]').jcBetPanel({
		betComment:commentContent,
		i:matchIndex,
		method:'addBetMatchComment'
	});
};

var isExistsMatchIdKey = function(matchId) {
	if(!recCookieKey) return;
	var isExists = false;
	var val = $.cookie(recCookieKey);
	if(val) {
		var matchIdArr = val.split(",");
		for(var i=0; i<matchIdArr.length; i++) {
			var mid = matchIdArr[i];
			if(matchId == mid) {
				isExists = true;
				break;
			}
		}
	}
	return isExists;
};

var addValue = function(matchId) {
	var val = $.cookie(recCookieKey);
	if(!val) {
		$.cookie(recCookieKey, matchId, BetContent.cookieConfig);
	} else {
		if(!isExistsMatchIdKey(matchId)) {
			$.cookie(recCookieKey, val + "," + matchId, BetContent.cookieConfig);
		}
	}
};

//读取并显示推荐内容
var readShowRecommendContent = function(betMatchs) {
	showModel("recommendModal");
};

/***
 * 显示发布推荐对话框内容
 */
var type;
var _playId;
var _playType;
var showPublishRecommendDialog = function(betMatchs,t,p) {
	type = t;
	_playId = p;
	_playType = t+'_'+p.toLowerCase();
	matchIdMap = {};
	if(betMatchs) {
		readShowRecommendContent(betMatchs);
	}
};

/**
 * 拆分选择的比赛
 * 201404022302-23021-false-sf,201404022303-23031-false-sf
 */
var splitSelectMatchs = function(betMatchs) {
	var matchIdList = [];
	if(betMatchs) {
		var mStr = betMatchs.split(",");
		for(var i=0; i<mStr.length; i++) {
			var match = mStr[i];
			var mId = match.split("-")[0];
			if(mId) {
				matchIdList.push(mId);
			}
		}
	}
	return matchIdList;
};

/**
 * 发布完毕，删除本次所有Cookie
 */
var removeRecommendCookie = function() {
	var option = {
		"domain":".davcai.com",
		"path":"/"
	}
	var recKey = $.cookie(recCookieKey);
	if(recKey) {
		var keyArr = recKey.split(",");
		for(var i=0; i<keyArr.length; i++) {
			$.removeCookie(keyArr[i], option);
		}
	}
	$.removeCookie(recCookieKey);
};

/**
 * 发推荐
 * 提交到后台
 * @param playId 玩法ID
 * @param betMatchs 投注内容
 * @param passTypes 过关方式
 */
var publishRecommend = function(playId, betMatchs, 
		passTypes, money, multi, bonus,annotations) {
	if(IS_DEBUG == true) {
		console.log(playId);
	    console.log(betMatchs);
	    console.log(passTypes);
	    console.log(money);
	    console.log(multi);
	    console.log(bonus);
	}
    var param = {
    	"playId":playId,
    	"betMatchs":betMatchs,
    	"passTypes":passTypes,
    	"multiple":multi,
    	"money":money,
    	"bonus":bonus,
    	"annotations":annotations
    };
    ajaxPostRecomScheme(param);
};

var clearRecommendAndHide = function() {
	$("#recommendContent").val("");
	removeRecommendCookie();
	hideModel("recommendModal");
};

var statusTips = function(cont) {
	$("#statusModel").find(".tipsCont").html(cont);
	showModel("statusModel");
};

/**
 * 异步保存推荐方案
 */
var ajaxPostRecomScheme = function(param) {
	var cont = $("#recommendContent").val();
	if(cont.length == 0){
		cont =  "我发起了比赛推荐，速来围观！";
	}
	var isValidReq = true;
	if(null == cont || !cont) {
		$.msgbox('推荐内容不能为空', {success:false});
		return;
	}
	if(cont.length > 1000) {
		$.msgbox('推荐字数过多，请删减后再发（不能超过1000个字符）', {success:false});
		isValidReq = false;
	}
	if(!isValidReq) {
		return;
	}
	
	param.content = cont;
	var url = "http://trade.davcai.com/ac/recommend/ajax_post_recom.do";
	$.ajax({
		type:"post",
		dataType:"jsonp",
		url : url,
		data:param,
		jsonp:"jsonp",
		jsonpCallback:"jsonp",
		success:function(rs) {
			if(rs && rs.success == true) {
				clearRecommendAndHide();
				$.msgbox('推荐成功', {success:true});
			} else {
				statusTips(rs.data);
			}
		},
		error:function(result) {
			$.msgbox('推荐请求处理错误', {success:false});
		}
	});
};
IS_DEBUG = false;

function checkTextLength($content){
	if(!$content.val()){
		return true;
	}
	var _remainingWords =  1000 - $content.val().length;
	if(_remainingWords >= 0){
		$content.closest(".modal-content").find('#modal-tips').html("还可输入<em style='color:red;'>" + _remainingWords + "</em>字");
	} else {
		$content.closest(".modal-content").find('#modal-tips').html("<em style='color:red;'>已超出&nbsp" + (0 - _remainingWords) + "</em>字");
	}
	return _remainingWords < 0;
}

$(function() {
	_initMatch();
	
	//提交注释
	$("#commentPostBtn").click(function() {
		if($("#commentContent").val().length > 1000){
			return false;
		} else {
			saveComment();
		}
	});
	$("#commentContent").keyup(function (){
		checkTextLength($(this));
	});
	$("#commentContent").change(function (){
		checkTextLength($(this));
	});
	//表情
	$("#message_face_recommend").jqfaceedit({
		txtAreaObj: $("#recommendContent"),
		containerObj: $('#emotion_icons'),
		textareaid: "recommendContent",
    	top:30,
    	left:-40
	});
	
	$("#recommendPostBtn").click(function (){
		var form = $("#scheme-form");
		var playId = form.find('[name="playId"]').val();
		var betMatchs = form.find('[name="betMatches"]').val();
		var passTypes = form.find('[name="passTypes"]').val();
		var money = form.find('[name="money"]').val();
		var multi = form.find('[name="multiple"]').val();
		var bonus = form.find('[name="bonus"]').val();
		var annotations = form.find('[name="annotations"]').val();
		publishRecommend(playId, betMatchs, passTypes, money, multi, bonus,annotations);
	});
});
