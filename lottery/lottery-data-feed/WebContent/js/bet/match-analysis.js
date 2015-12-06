/**
 *	赛事分析的JS
 *	by lei.li@unison.net.cn 
 */

/**
 * 根据赛事ID和彩种，给出赛事分析的url：亚欧析
 * @param lcMatchId
 * @param lotteryId
 */
var matchForwardUrl = function(lcMatchId, lotteryId) {
	if(!lcMatchId || !lotteryId) {
		return {};
	}
	var weiboMatchBase = "http://www.davcai.com/matches/";
    var asianOdds = "http://www.davcai.com/asian_odds?matchId=" + 
    	lcMatchId + "&lotteryType=" + lotteryId;
    var europeOdds = "http://www.davcai.com/europe_odds?matchId=" + 
    	lcMatchId + "&lotteryType=" + lotteryId;
    var matchOverview = weiboMatchBase + lotteryId + "/" + lcMatchId;
    return {
    	aisanUrl:asianOdds,
    	europeUrl:europeOdds,
    	overview:matchOverview
    };
};

/**
 * 绑定推荐赛事的选项内容，数据来自_betMatchList
 * 格式：
 * [
 * {"code":"", "matchId":"", "playId":"", "seed":""},
 * {"code":"", "matchId":"", "playId":"", "seed":""}
 * ]
 * code表示当前比赛的选项内容，seed为true表示设胆，反之.
 * 此方法需要在页面赛事数据加载完之后绑定才会有效
 * 绑定赛事选项方法：ctx.iClickOption(0, 0);第一个参数表示赛事在页面行号， 第二个参数表示选项的序号.
 * 绑定串关类型：
 * 
 */
var BetCtx = null;
var bindRecommendMatchOptions = function(ctx) {
	BetCtx = ctx;
	var matchs = ctx.bet.matchs;
	for(var i=0; i<matchs.length; i++) {
		var m = matchs[i];
		var m_code = m.code;
		var m_id = m.id;
		var result = getBetMatchOptions(m_id);
		if(result) {
			if(ctx.playId == "05_ZC_2") {//竞足混合玩法
				openOrCloseOption(i);
				var offset = MixBetOption.expansionPlayOptions(i, result.playId);
				JcBetOption.expansionPlayOptions(ctx, i, result, true, offset);
			} else if(ctx.playId == "10_LC_2") {//竞篮混合玩法
				openOrCloseOption(i);
				var offset = MixBetOption.expansionPlayOptions(i, result.playId);
				JcBetOption.expansionPlayOptions(ctx, i, result, true, offset);
			} else if(result.playId == ctx.playId) {//选择的赛事玩法必须和页面当前的玩法要一致.
				openOrCloseOption(i);
				JcBetOption.expansionPlayOptions(ctx, i, result, null);
			}
		}
	}
}

//=============混合投注投注选项展开/选上等操作===================
var MixBetOption = {};
/**
 * 根据赛事所在序号，玩法ID，展开玩法选项
 * 支持：混合过关玩法
 */
MixBetOption["expansionPlayOptions"] = function(index, playId) {
	var _mTR = $("#matchGrid").find("tr[_m='" + index + "']");
	var jcStyle = "";
	if(playId.indexOf("01_ZC") != -1) {
		jcStyle = "spf";
	} else if(playId.indexOf("80_ZC") != -1) {
		jcStyle = "brqspf";
	} else if(playId.indexOf("03_ZC") != -1) {
		jcStyle = "jqs";
	} else if(playId.indexOf("02_ZC") != -1) {
		jcStyle = "bf";
	} else if(playId.indexOf("04_ZC") != -1) {
		jcStyle = "bqc";
	} else if(playId.indexOf("06_LC") != -1) {
		jcStyle = "rfsf";
	} else if(playId.indexOf("07_LC") != -1) {
		jcStyle = "sf";
	} else if(playId.indexOf("08_LC") != -1) {
		jcStyle = "fc";
	} else if(playId.indexOf("09_LC") != -1) {
		jcStyle = "dxf";
	}
	var aSh = _mTR.prev("tr").find(".intd > a[_play=" + jcStyle + "]");
	
	var td = _mTR.find("td[_jcstyle='" + jcStyle + "']");
	var _o = $(td.children()[0]).attr("_o");
	var offset = parseInt(_o);
	
	if(aSh && aSh.length > 0) {
		aSh.click();
	}
	return offset;
}; 


//=============竞彩投注选项展开/选上等操作===================
var JcBetOption = {};
/**
 * isFromMix boolean 是否是来自混合拆分后的玩法
 */
JcBetOption["expansionPlayOptions"] = function(ctx, index, result, isFromMix, offset) {
	var playId = result.playId;
	if(playId.indexOf("01_ZC") != -1 || playId.indexOf("80_ZC") != -1) {
		paintJCZQ_SPF(ctx, index, result, isFromMix, offset);
	} else if(playId.indexOf("03_ZC") != -1) {
		paintJCZQ_JQ(ctx, index, result, isFromMix, offset);
	} else if(playId.indexOf("04_ZC") != -1) {
		paintJCZQ_BQ(ctx, index, result, isFromMix, offset);
	} else if(playId.indexOf("02_ZC") != -1) {
		paintJCZQ_BF(ctx, index, result, isFromMix, offset);
	} else if(playId.indexOf("08_LC") != -1) {
		paintJCLQ_SFC(ctx, index, result, isFromMix, offset);
	} else if(playId.indexOf("06_LC") != -1 || 
			playId.indexOf("07_LC") != -1 || 
			playId.indexOf("09_LC") != -1) {
		paintJCLQ_DXF_SF(ctx, index, result, isFromMix, offset);
		//北京单场
	}else if(playId.indexOf("91_BJDC")!=-1){
		paintBJDC_SPF(ctx, index, result, isFromMix, offset);
	}
};
//胜平负 北京单场
var paintBJDC_SPF=function(ctx, index, match, isFromMix, offset){
		var optionsArr = getSelectOptionsBD(match.code);
		for(var i=0; i<optionsArr.length; i++) {
			var pos = -1;
			var option = parseInt(optionsArr[i]);
			pos = getPositionByResult(option);
			if(isFromMix) {
				pos = parseInt(pos) + offset;
			}
			if(pos >= 0)
				ctx.iClickOption(index, pos);
		}
};
/**
 * 拆分投注内容的选项结果，返回list数组
 */
var getSelectOptionsBD = function(code) {
	var code = code.substr(3);
	var arr = code.split("");
	return arr;
};
var paintJCLQ_SFC = function(ctx, index, match, isFromMix, offset) {
	var options = getSelectOptions(match.code);
		var rs = "";
		for(var m=0; m<options.length; m++) {
			rs = rs + options[m];
			if(m > 0 && (m+1)%2 == 0) {
				var pos = -1;
				pos = JCLQ_SFC_NAMES_INDEX[rs];
				if(isFromMix) {
					pos = parseInt(pos) + offset;
				}
//				else {
//					parseInt(rs) - 11;
//					pos = pos * 2;
//				}
				ctx.iClickOption(index, pos);
				rs = "";
			}
		}
	
};

/**
 * 判断是否有需要打开的父级别层次关系
 */
var openOrCloseOption = function(index) {
	var _mTR = $("#matchGrid").find("tr[_m='" + index + "']");
	var title = _mTR.prevAll("tr.trcs").first();
	var clickOpt = title.find("a[_w]");
	var _v = clickOpt.attr('_v');
	if(_v == '1'){
		title.find("a[_w]").click();
	}
};

var paintJCLQ_DXF_SF  = function(ctx, index, match, isFromMix, offset) {
	var playId = match.playId;
	var optionsArr = getSelectOptions(match.code);
	for(var i=0; i<optionsArr.length; i++) {
		var pos = -1;
		var option = parseInt(optionsArr[i]);
		if(option == 2) {
			if(playId.indexOf("06_LC") != -1 || 
					playId.indexOf("07_LC") != -1) {
				pos = 0;
			} else if(playId.indexOf("09_LC") != -1) {
				pos = 1;
			}
		} else if(option == 1) {
			if(playId.indexOf("06_LC") != -1 || 
					playId.indexOf("07_LC") != -1) {
				pos = 1;
			} else if(playId.indexOf("09_LC") != -1) {
				pos = 0;
			}
		}
		if(isFromMix) {
			pos = parseInt(pos) + offset;
		}
		if(pos >= 0)
			ctx.iClickOption(index, pos);
	}
};

//竞足比分
var paintJCZQ_BF  = function(ctx, index, match, isFromMix, offset) {
	//显示选项
	var _mTR = $("#matchGrid").find("tr[_m='" + index + "']");
	if(!isFromMix) {
		_mTR.prev().find("a[_sh]").click();
	}
	
	var aList = _mTR.find("a[_o]");
	for(var i=0; i<aList.length; i++) {
		var _o = $(aList[i]).attr("_o");
		var bif = $(aList[i]).find(".bif").text();
		if(bif.indexOf(":") > 0) {
			bif = bif.replace(/:/g, "");
		}
		if(bif == "胜其他") {
			bif = "90";
		}
		if(bif == "平其他") {
			bif = "99";
		}
		if(bif == "负其他") {
			bif = "09";
		}
//		log(bif + "----" + _o)
		JCZQ_BF_NAMES_INDEX[bif] = _o;
	}
	
	//绑定选项值
	var options = getSelectOptions(match.code);
	if(options && options.length >= 2) {
		var rs = "";
		for(var m=0; m<options.length; m++) {
			rs = rs + options[m];
			if(m > 0 && (m+1)%2 == 0) {
				var pos = JCZQ_BF_NAMES_INDEX[rs];
				ctx.iClickOption(index, pos);
				rs = "";
			}
		}
	}
};

//竞足半全
var paintJCZQ_BQ  = function(ctx, index, match, isFromMix, offset) {
	var options = getSelectOptions(match.code);
	if(options && options.length >= 2) {
		var rs = "";
		for(var m=0; m<options.length; m++) {
			rs = rs + options[m];
			if(m > 0 && (m+1)%2 == 0) {
				var pos = JCZQ_BQ_PAGE_INDEX(rs);
				if(isFromMix) {
					pos = parseInt(pos) + offset;
				}
				ctx.iClickOption(index, pos);
				rs = "";
			}
		}
	}
};

//竞足胜平负
var paintJCZQ_SPF  = function(ctx, index, match, isFromMix, offset) {
	var optionsArr = getSelectOptions(match.code);
	for(var i=0; i<optionsArr.length; i++) {
		var pos = -1;
		var option = parseInt(optionsArr[i]);
		pos = getPositionByResult(option);
		if(isFromMix) {
			pos = parseInt(pos) + offset;
		}
		if(pos >= 0)
			ctx.iClickOption(index, pos);
	}
};

//竞足进球
var paintJCZQ_JQ  = function(ctx, index, match, isFromMix, offset) {
	var optionsArr = getSelectOptions(match.code);
	for(var i=0; i<optionsArr.length; i++) {
		var pos = -1;
		var option = parseInt(optionsArr[i]);
		pos = option - 1;
		if(isFromMix) {
			pos = parseInt(pos) + offset;
		}
		if(pos >= 0)
			ctx.iClickOption(index, pos);
	}
};

var JCZQ_BQ_PAGE_INDEX = function(opt) {
	var pos = -1;
	switch (opt) {
	case "33":
		pos = 0;
		break;
	case "31":
		pos = 1;
		break;
	case "30":
		pos = 2;
		break;
	case "13":
		pos = 3;
		break;
	case "11":
		pos = 4;
		break;
	case "10":
		break;
		pos = 5;
	case "03":
		pos = 6;
		break;
	case "01":
		pos = 7;
		break;
	case "00":
		pos = 8;
		break;
	}
	return pos;
};

/**
 * 绑定串关类型
 */
var bindPassTypeId = function(ctx) {
	if(_passTypeIds) {
		var passTypeIdArr = _passTypeIds.split(",");
		var checkboxList = ctx.$pRow.find(':checkbox');
		for(var i=0; i<checkboxList.length; i++) {
			var checkbox = $(checkboxList[i]).next().text().replace("串", "@");
			if(array_contains(passTypeIdArr, checkbox)) {
				$(ctx.$pRow.find(':checkbox')[i]).click();
			}
		}
	}
};

/**
 * 绑定倍数
 */
var bindMultiple = function(ctx) {
	// 检查投注倍数
	if(!(/^\d+$/g.test(_multiple))){
		alert('投注倍数必须是正整数');
		$(this).val('1');
		return false;
	}
	var multiple = parseInt(_multiple);
	if(multiple > 0 && multiple < 100001){
		if(typeof(Element) != "undefined"){
			Element.prototype.fireEvent = function(name){
				var ev = document.createEvent("UIEvents");
				ev.initEvent(name.replace(/^on/i, ""), true, true);
				this.dispatchEvent(ev);
			}
		}
		var mutipleInput = $('input[name="multiple"]', this.$form)[0];
		$('input[name="multiple"]', this.$form).val(multiple);
		mutipleInput.fireEvent('onchange');
	}
	
};

/**
 * 获取指定赛事ID的投注玩法选项
 */
var getBetMatchOptions = function(mid) {
	if(!_betMatchList || !mid) return;
	var rs = null;
	for(var i=0; i<_betMatchList.length; i++) {
		var match = _betMatchList[i];
		if(mid == match.matchId) {
			rs = match;
			break;
		}
	}
	return rs;
};

/**
 * 拆分投注内容的选项结果，返回list数组
 */
var getSelectOptions = function(code) {
	var code = code.substr(4);
	var arr = code.split("");
	return arr;
};

/**
 * 根据选项结果（胜平负），返回对应序号：0,1,2（注意需要是和页面布局结构相关，也就是胜平负的顺序有关）
 */
var getPositionByResult = function(option) {
	var pos = -1;
	switch (option) {
	case 3:
		pos = 0;
		break;
	case 1:
		pos = 1;
		break;
	case 0:
		pos = 2;
		break;
	}
	return pos;
};

/**
 * 比分对应索引
 */
var JCZQ_BF_NAMES_INDEX = {};

var JCLQ_SFC_NAMES_INDEX = {
    '01': 0,
    '02': 2,
    '03': 4,
    '04': 6,
    '05': 8,
    '06': 10,
    '11': 1,
    '12': 3,
    '13': 5,
    '14': 7,
    '15': 9,
    '16': 11
};