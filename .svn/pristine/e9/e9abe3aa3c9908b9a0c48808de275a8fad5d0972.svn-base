var BetContent = {};

/**
 * 根据过关获取正确的玩法内容：区分是混合还是普通的竞彩过关
 */
BetContent["getPlayName"] = function(_playId, isMix) {
	var cont = "";
	if(isMix == true) {
		switch (_playId) {
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
	} else {
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
BetContent["convertBetOption"] = function(betOpt, _playId, isMix) {
	var index=4;
	var options = betOpt + "";
	var result = [];
	if(isMix == true) {
		switch (_playId.toLowerCase()) {
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
	} else {
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
		rs.push(arr[i]);
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
		rs.push(arr[i]);
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
		rs.push(arr[i]);
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

BetContent["getWeek"] = function(index) {
	var rs = "";
	switch (parseInt(index)) {
	case 1:
		rs = "周一";
		break;
	case 2:
		rs = "周二";
		break;
	case 3:
		rs = "周三";
		break;
	case 4:
		rs = "周四";
		break;
	case 5:
		rs = "周五";
		break;
	case 6:
		rs = "周六";
		break;
	case 7:
		rs = "周日";
		break;
	}
	return rs;
};

IS_DEBUG = false;
$(function() {
	
});
