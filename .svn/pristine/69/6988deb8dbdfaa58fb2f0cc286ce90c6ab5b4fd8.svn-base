var array_contains = function(source, obj) {
    var i = source.length;
    while (i--) {
        if (source[i] === obj) {
            return true;
        }
    }
    return false;
};

var _endsWith = function(str, suffix) {
    return str.indexOf(suffix, str.length - suffix.length) !== -1;
};
//======================================================

/**
 * 根据赛事信息和彩种，拼出赛事串内容
 */
var matchString = function(match, lottery) {
	var str = "";
	var id = match.id + "";
	if(lottery == "JCZQ") {
		str += "$竞彩足球 ";
		str += match.cnCode;
		str += (" " + match.homeTeamName + " VS " +  match.guestTeamName);
		str += "(JZ" + id.substr(2) + ")$";
	} else if(lottery == "JCLQ") {
		str += "$竞彩篮球 ";
		str += match.cnCode;
		str += (" " + match.homeTeamName + " VS " +  match.guestTeamName);
		str += "(JL" + id.substr(2) + ")$";
	} else if(lottery == "BJDC") {
		str += "$北京单场 ";
		str += match.cnCode;
		str += (" " + match.homeTeamName + " VS " +  match.guestTeamName);
		str += "(BJDC" + id.substr(2) + ")$";
	}
	return str;
};
/**
 * 根据赛事信息和彩种，拼出赛事串内容
 */
var matchStr = function(match, lottery, playId) {
	var str = "";
	var id = match.id + "";
	if(lottery == "JCZQ") {
		str += "$竞彩足球 ";
		str += match.cnCode;
		str += (" " + match.hName + " VS " +  match.gName);
		str += "(JZ" + id.substr(2) + ")$";
	} else if(lottery == "JCLQ") {
		str += "$竞彩篮球 ";
		str += match.cnCode;
		str += (" " + match.hName + " VS " +  match.gName);
		str += "(JL" + id.substr(2) + ")$";
	} else if(lottery == "BJDC") {
		str += "$北京单场 ";
		str += match.cnCode;
		str += (" " + match.hName + " VS " +  match.gName);
		str += "(BJDC" + id.substr(2) + ")$";
	}
	return str;
};

var playTypeConvert = function(opts) {
	return opts;
};