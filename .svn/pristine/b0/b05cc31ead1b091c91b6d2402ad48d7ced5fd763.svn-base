//======根据投注选项组装推荐内容======
var BetContent = {};
/**
 * betMatchs	投注的比赛内容
 * playId		玩法ID
 * betOptions	投注选项
 * pagePlayId	当前页面的玩法ID：主要是给混合过关使用
 */
BetContent["compose"] = function(betMatchs) {
	this.betContent = [];
	this.splitBetOptions(betMatchs);
};

BetContent["betContent"] = [];

BetContent["cookieConfig"] = {
	"expires":1,
	"domain":".davcai.com",
	"path":"/"
};

BetContent["composeRecommendContent"] = function(matchId, betOpt, betPlay) {
	var matchSel = $("a[_matchid='" + matchId + "']");
	var matchStr = matchSel.attr("_matchstr");
	var content = null;
	var matchCont = $.cookie(_playType + "_" + matchId);
	if(!matchCont) matchCont = "";
	content = matchStr + "<br/>" + 
			"玩法：" + betPlay + "<br/>" + 
			"推荐：" + betOpt + "<br/>" +
			matchCont;
	if(content) {
		this.betContent.push(content);
		this.betContent.push("<br/>");
	}
};

BetContent["getBetContent"] = function(betMatchs) {
	var result = [];
	if(betMatchs) {
		var mStr = betMatchs.split(",");
		for(var i=0; i<mStr.length; i++) {
			var match = mStr[i];
			var arr = match.split("-");
			var matchId = arr[0];
			var matchAnnotation = $.cookie(_playType + "_" + matchId);
			if(matchAnnotation) {
				var mResult = matchId + "-" + matchAnnotation;
				result.push(mResult);
			}
		}
	}
	return result;
};

BetContent["splitBetOptions"] = function(betMatchs) {
	//201404022302-23021-false-sf,201404022303-23031-false-sf
	if(betMatchs) {
		var mStr = betMatchs.split(",");
		for(var i=0; i<mStr.length; i++) {
			var match = mStr[i];
			var arr = match.split("-");
			var matchId = arr[0];
			var betOpt = arr[1];
			var betPlay = arr[3];
			this.composeRecommendContent(matchId, this.convertBetOption(betOpt, betPlay), this.getPlayId(betPlay));
		}
	}
};

/**
 * 根据过关获取正确的玩法内容：区分是混合还是普通的竞彩过关
 */
BetContent["getPlayId"] = function(betPlay) {
	if(!betPlay) {
		betPlay = _playId;
	}
	var cont = "";
	//如果是混合给出的是:brqspf,spf等
	if(_playId == "10_LC_2" || _playId == "05_ZC_2") {
		switch (betPlay) {
			case "spf":
				cont = "让球胜平负";
				break;
			case "brqspf":
				cont = "胜平负";
				break;
			case "jqs":
				cont = "进球数";
				break;
			case "bf":
				cont = "比分";
				break;
			case "bqc":
				cont = "半全场";
				break;
			case "rfsf":
				cont = "让分胜负";
				break;
			case "sf":
				cont = "胜负";
				break;
			case "fc":
				cont = "胜分差";
				break;
			case "dxf":
				cont = "大小分";
				break;
		}
	} else {//普通竞彩过关给出的是：06_lc_2，07_lc_2等
		_playId = _playId.toUpperCase();
		if(_playId.indexOf("01_ZC") != -1) {
			cont = "让球胜平负";
		} else if(_playId.indexOf("80_ZC") != -1) {
			cont = "胜平负";
		} else if(_playId.indexOf("03_ZC") != -1) {
			cont = "总进球";
		} else if(_playId.indexOf("04_ZC") != -1) {
			cont = "半全场";
		} else if(_playId.indexOf("02_ZC") != -1) {
			cont = "比分";
		} else if(_playId.indexOf("08_LC") != -1) {
			cont = "胜分差";
		} else if(_playId.indexOf("06_LC") != -1) {
			cont = "让分胜负";
		} else if(_playId.indexOf("07_LC") != -1) {
			cont = "胜负";
		} else if(_playId.indexOf("09_LC") != -1) {
			cont = "大小分";
		} else if(_playType=="bjdc_spf"){
			cont="胜平负";
		} else if(_playType=="bjdc_bf"){
			cont="比分";	
		} else if(_playType=="bjdc_sxds"){
			cont="上下单双";		
		} else if(_playType=="bjdc_bqc"){
			cont="半全场";
		}else if(_playType=="bjdc_jqs"){
			cont="进球数";
		}else if(_playType=="bjdc_sf"){
			cont="胜负过关";
		}
	}
	return cont;
};

/**
 * 转换投注选项
 */
BetContent["convertBetOption"] = function(betOpt, betPlay) {
	if(!betPlay) {
		betPlay = _playId;
	}
	var index=4;
	if(type&&"bjdc"==type){
		
		index=3;
	}
	var options = betOpt.substr(index);
	var result = [];
	_playId = _playId.toUpperCase();
	//如果是混合给出的是:brqspf,spf等
	if(_playId == "10_LC_2" || _playId == "05_ZC_2") {
		switch (betPlay) {
			case "spf":
				result = getBetResult_JZ_SPF(options);
				break;
			case "brqspf":
				result = getBetResult_JZ_SPF(options);
				break;
			case "jqs":
				result = getBetResult_JZ_JQ(options);
				break;
			case "bf":
				result = getBetResult_JZ_BF(options);
				break;
			case "bqc":
				result = getBetResult_JZ_BQC(options);
				break;
			case "rfsf":
				result = getBetResult_JL_RFSF(options);
				break;
			case "sf":
				result = getBetResult_JL_SF(options);
				break;
			case "fc":
				result = getBetResult_JL_SFC(options);
				break;
			case "dxf":
				result = getBetResult_JL_DXF(options);
				break;
		}
	} else {//普通竞彩过关给出的是：06_lc_2，07_lc_2等
		if(_playId.indexOf("01_ZC") != -1) {
			result = getBetResult_JZ_SPF(options);
			
		} else if(_playId.indexOf("80_ZC") != -1) {
			result = getBetResult_JZ_SPF(options);
			
		} else if(_playId.indexOf("03_ZC") != -1) {
			result = getBetResult_JZ_JQ(options);
			
		} else if(_playId.indexOf("04_ZC") != -1) {
			result = getBetResult_JZ_BQC(options);
			
		} else if(_playId.indexOf("02_ZC") != -1) {
			result = getBetResult_JZ_BF(options);
			
		} else if(_playId.indexOf("08_LC") != -1) {
			result = getBetResult_JL_SFC(options);
			
		} else if(_playId.indexOf("06_LC") != -1) {
			result = getBetResult_JL_RFSF(options);
			
		} else if(_playId.indexOf("07_LC") != -1) {
			result = getBetResult_JL_SF(options);
			
		} else if(_playId.indexOf("09_LC") != -1) {
			result = getBetResult_JL_DXF(options);
		} else if("bjdc_spf"==_playType){
			result = getBetResult_JZ_SPF(options);
			
		} else if("bjdc_bf"==_playType){
			result=getBetResult_JZ_BF(options)
			
		} else if("bjdc_sxds"==_playType){
			result=getBetResult_BJDC_SXDS(options);
			
		} else if("bjdc_bqc"==_playType){
			result=getBetResult_JZ_BQC(options);
			
		} else if("bjdc_jqs"==_playType){
			result=getBetResult_JZ_JQ(options);
			
		} else if("bjdc_sf"==_playType){
			
			result=getBetResult_BJDC_SF(options);
		}
	}
	options = result.join(",");
	return options;
};
//===================北京单场=============================
var getBetResult_BJDC_SXDS = function(options){
	var result = [];
	for(var i=0;i<options.length;i+=2){
		
		var op=options.substring(i,i+2);
		switch(op){
		    case 11:
		    	result.push("上单");
		        break;
		    case 12:
		    	result.push("上双");
		        break;
		    case 01:
		    	result.push("下单");
		        break;
		    case 02:
		    	result.push("下双");
		        break;
		}
	}
	return result;
	
};
var getBetResult_BJDC_SF=function(options){
	var result = [];
	var arr = options.split("");
	for(var i=0;i<arr.length;i++){
		
		if("3"==arr[i]){
			result.push("胜");
			
		}else if("0"==arr[i]){
			result.push("负");
			
		}
		
	}
	return result;
};

//====================竞彩篮球==============================
var getBetResult_JL_RFSF = function(options) {
	var result = [];
	var arr = options.split("");
	for(var i=0; i<arr.length; i++) {
		if(2 == arr[i]) {
			result.push("让分主负");
		} else if(1 == arr[i]) {
			result.push("让分主胜");
		}
	}
	return result;
};
var getBetResult_JL_SF = function(options) {
	var result = [];
	var arr = options.split("");
	for(var i=0; i<arr.length; i++) {
		if(2 == arr[i]) {
			result.push("主负");
		} else if(1 == arr[i]) {
			result.push("主胜");
		}
	}
	return result;
};

var SFC_INDEX = {
    '01': '主胜1-5',
    '02': '主胜6-10',
    '03': '主胜11-15',
    '04': '主胜16-20',
    '05': '主胜21-25',
    '06': '主胜26+',
    '11': '客胜1-5',
    '12': '客胜6-10',
    '13': '客胜11-15',
    '14': '客胜16-20',
    '15': '客胜21-25',
    '16': '客胜26+'
};
var getBetResult_JL_SFC = function(options) {
	var result = [];
	var arr = options.split("");
	var rs = [];
	for(var i=0; i<arr.length; i++) {
		rs.push(options[i]);
		if(i > 0 && (i+1)%2 == 0) {
			var ret = SFC_INDEX[rs.join("")];
			result.push(ret+"分");
			rs = [];
		}
	}
	return result;
};
var getBetResult_JL_DXF = function(options) {
	var result = [];
	var arr = options.split("");
	for(var i=0; i<arr.length; i++) {
		if(1 == arr[i]) {
			result.push("大");
		} else if(2 == arr[i]) {
			result.push("小");
		}
	}
	return result;
};


//====================竞彩足球==============================
var getBetResult_JZ_SPF = function(options) {
	var result = [];
	var arr = options.split("");
	for(var i=0; i<arr.length; i++) {
		if(3 == arr[i]) {
			result.push("胜");
		} else if(1 == arr[i]) {
			result.push("平");
		} else if(0 == arr[i]) {
			result.push("负");
		}
	}
	return result;
};

var getBetResult_JZ_JQ = function(options) {
	var arr = options.split("");
	var result = [];
	for(var i=0; i<arr.length; i++) {
		result.push(arr[i] + "球");
	}
	return result;
};
var getBetResult_JZ_BQC = function(options) {
	var result = [];
	var arr = options.split("");
	var rs = [];
	for(var i=0; i<arr.length; i++) {
		rs.push(options[i]);
		if(i > 0 && (i+1)%2 == 0) {
			var ret = getBetResult_JZ_SPF(rs.join(""));
			result.push(ret.join(""));
			rs = [];
		}
	}
	return result;
};

var getBetResult_JZ_BF = function(options) {
	var result = [];
	var arr = options.split("");
	var rs = [];
	for(var i=0; i<arr.length; i++) {
		rs.push(options[i]);
		if(i > 0 && (i+1)%2 == 0) {
			var cn = rs.join("");
			if(cn == "90") {
				rs = "胜其他";
			} else if(cn == "99") {
				rs = "平其他";
			} else if(cn == "09") {
				rs = "负其他";
			} else {
				rs = rs.join(":");
			}
			result.push(rs);
			rs = [];
		}
	}
	return result;
};

/**显示对话框*/
var showModel = function(modelId) {
	var shareWindow = $("#"+modelId);
	shareWindow.modal('show');
};

/**隐藏对话框*/
var hideModel = function(modelId) {
	var shareWindow = $("#"+modelId);
	shareWindow.modal('hide');
};

//当前页面使用cookie保存赛事推荐的key
var recCookieKey = "";
var _initMatch = function() {
	recCookieKey = _playType;
};

/**
 * 显示加注释的对话框，并绑定数据
 * 数据放在this对象的_matchStr属性上。
 */
var _matchId = "";
var showAddCommentDialog = function($this, ctx) {
	_matchId = $this.attr("_matchId");
	//"201404092301-23011-false,201404092302-23021-false"
	var betMatchs = ctx.bet.toBetMatchs();
	var betMatchIdList = splitSelectMatchs(betMatchs);
	var exists = array_contains(betMatchIdList, _matchId);
	if(!exists) {
		$.msgbox('请先选择赛事推荐内容', {success:false});
		return;
	}
	$("#commentContent").val("");
	
	//存cookie
	var matchId = $.cookie(recCookieKey + "_" + _matchId);
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
	var cookie = $.cookie(ckey);
	var commentContent = $("#commentContent").val();
	var matchKey = recCookieKey + "_" + ckey;
	if(commentContent) {
		if(commentContent.length <= 1000) {
			$.cookie(matchKey, commentContent, BetContent.cookieConfig);
			addValue(matchKey);
			hideModel("commentModal");
			$("#modal-tips").text("");
		} else {
			$("#modal-tips").text("注释内容不能超过1000个字符！");
		}
	}
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
	var rs = null;
//	$("#recommendContent").val("");
	BetContent.compose(betMatchs);
	rs = BetContent.betContent.join("");
	if(rs) {
		$(document.getElementById('recomdFrame').
				contentWindow.document.body).html(rs);
		showModel("recommendModal");
	} else {
		$.msgbox('没有找到所选赛事的推荐内容', {success:false});
	}
};

/***
 * 显示发布推荐对话框内容
 */
var showPublishRecommendDialog = function(betMatchs) {
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
		passTypes, money, multi, bonus) {
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
    	"bonus":bonus
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
	var recomMatchList = $(document.getElementById('recomdFrame').
			contentWindow.document.body).html();
	var cont = $("#recommendContent").val();
	if(cont.length == 0){
		cont =  "我发起了比赛推荐，速来围观！";
	}
	
	var annotations = BetContent.getBetContent(param.betMatchs);
	param.annotations = annotations.join();
	
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
				if(BetCtx)
				 BetContent.clearMatch(BetCtx);
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

BetContent["clearMatch"] = function(ctx) {
	ctx.iClearMatch();
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
});
