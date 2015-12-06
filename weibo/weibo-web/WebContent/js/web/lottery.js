/**
 * 前端彩种的js对象
 * @returns
 */
function lotteryId() {
}
function lotteryId(id, name) {
	this.id = id;
	this.name = name;
}
lotteryId.prototype.id = "";
lotteryId.prototype.name = "";

var lotteryName = {
	JCZQ : "JCZQ",
	JCLQ : "JCLQ",
	CTZC : "CTZC",
	SSQ :  "SSQ",
	JX11 : "JX11",
	BJDC : "BJDC"
};

function lottery() {
}
lottery.prototype.list = function() {
	var lotteryList = [];
	lotteryList.push(new lotteryId("JCZQ", "竞足"));
	lotteryList.push(new lotteryId("JCLQ", "竞篮"));
	lotteryList.push(new lotteryId("CTZC", "足彩"));
	lotteryList.push(new lotteryId("SSQ", "双色球"));
	lotteryList.push(new lotteryId("BJDC", "北单"));
	lotteryList.push(new lotteryId("JX11", "11选五"));
	return lotteryList;
};
lottery.prototype.getId = function(name) {
	var id = null;
	var arr = this.list();
	for(var i=0; i<arr.length; i++) {
		var lott = arr[i];
		if(name == lott.name) {
			id = lott.id;
			break;
		}
	}
	return id;
};
lottery.prototype.getName = function(id) {
	var name = '未知';
	var arr = this.list();
	for(var i=0; i<arr.length; i++) {
		var lott = arr[i];
		if(id == lott.id) {
			name = lott.name;
			break;
		}
	}
	return name;
};

lottery.prototype.isDigital = function(id) {
	if(lotteryName.SSQ == id || lotteryName.JX11 == id) {
		return true;
	} else {
		return false;
	}
};

lottery.prototype.isZC = function(lotteryType) {
	return lotteryName.JCZQ == lotteryType ||
	lotteryName.CTZC == lotteryType ||
	lotteryName.BJDC == lotteryType;
};

lottery.prototype.isLC = function(lotteryType) {
	return lotteryName.JCLQ == lotteryType;
};

lottery.prototype.isBJDC = function(lotteryType) {
	return lotteryName.BJDC == lotteryType;
};