var JCLQSelectedOption = function (){};
JCLQSelectedOption.prototype = {
	init:function (){
		this.lqZhuShengList = [];
		this.lqKeShengList = [];
		this.lqZhuShengOption;
		this.lqKeShengOption;
		this.lqRfZhuShengOption;
		this.lqRfKeShengOption;
		this.dxfDaOption;
		this.dxfXiaoOption;
	}	
};

var jclqOptionsMap = new Map();
var posibleListMap = new Map();
var maxListMap = new Map();
var minListMap = new Map();
function printList(){
	for (var i = 0; i < jclqOptionsMap.keys().length; i++) {
		var key = jclqOptionsMap.keys()[i];
		jclqOption = jclqOptionsMap.get(key);
		console.log("-------------------"+key+"------------------");
		if(jclqOption.lqZhuShengOption){
			console.log("lqZhuShengOption："+"{"+jclqOption.lqZhuShengOption.playType +","+jclqOption.lqZhuShengOption.option +","+jclqOption.lqZhuShengOption.odd +","+"},");
		} else {
			console.log("lqZhuShengOption：");
		}
		if(jclqOption.lqKeShengOption){
			console.log("lqKeShengOption："+"{"+jclqOption.lqKeShengOption.playType +","+jclqOption.lqKeShengOption.option +","+jclqOption.lqKeShengOption.odd +","+"},");
		} else {
			console.log("lqKeShengOption：");
		}
		if(jclqOption.lqRfZhuShengOption){
			console.log("lqRfZhuShengOption："+"{"+jclqOption.lqRfZhuShengOption.playType +","+jclqOption.lqRfZhuShengOption.option +","+jclqOption.lqRfZhuShengOption.odd +","+"},");
		} else {
			console.log("lqRfZhuShengOption：");
		}
		if(jclqOption.lqRfKeShengOption){
			console.log("lqRfKeShengOption："+"{"+jclqOption.lqRfKeShengOption.playType +","+jclqOption.lqRfKeShengOption.option +","+jclqOption.lqRfKeShengOption.odd +","+"},");
		} else {
			console.log("lqRfKeShengOption：");
		}
		if(jclqOption.dxfDaOption){
			console.log("dxfDaOption："+"{"+jclqOption.dxfDaOption.playType +","+jclqOption.dxfDaOption.option +","+jclqOption.dxfDaOption.odd +","+"},");
		} else {
			console.log("dxfDaOption：");
		}
		if(jclqOption.dxfXiaoOption){
			console.log("dxfXiaoOption："+"{"+jclqOption.dxfXiaoOption.playType +","+jclqOption.dxfXiaoOption.option +","+jclqOption.dxfXiaoOption.odd +","+"},");
		} else {
			console.log("dxfXiaoOption：");
		}
		var a= "lqZhuShengList:";
		for (var j = 0; j < jclqOption.lqZhuShengList.length; j++) {
			a+="{"+jclqOption.lqZhuShengList[j].playType +","+jclqOption.lqZhuShengList[j].option +","+jclqOption.lqZhuShengList[j].odd +","+"},";
		}
		console.log(a);
		a="lqKeShengList:";
		for (var j = 0; j < jclqOption.lqKeShengList.length; j++) {
			a+="{"+jclqOption.lqKeShengList[j].playType +","+jclqOption.lqKeShengList[j].option +","+jclqOption.lqKeShengList[j].odd +","+"},";
		}
		console.log(a);
	}
}
/**
 * 返回一个列表中最大赔率的选项
 * @param list
 * @returns
 */
function findListMaxBet(list){
	if(list.length < 1){
		return "";
	}
	var max = 0;
	var ret = 0;
	for (var i = 0; i < list.length; i++) {
		var bet = list[i];
		var odd = parseFloat(bet.odd);
		if(max==0 || max < odd){
			max=odd;
			ret=i;
		}
	}
	return list[ret];
}

function combineMinPossibleList(i){
	jclqOption = jclqOptionsMap.get(i);
	var arr = [];
	if(null ==jclqOption){
		return;
	}
	if(null != jclqOption.dxfXiaoOption){
		arr.push(jclqOption.dxfXiaoOption);
	}
	if(null != jclqOption.dxfDaOption){
		arr.push(jclqOption.dxfDaOption);
	}
	if(null != jclqOption.lqKeShengOption){
		arr.push(jclqOption.lqKeShengOption)
	}
	if(null != jclqOption.lqZhuShengOption){
		arr.push(jclqOption.lqZhuShengOption)
	}
	if(null != jclqOption.lqRfKeShengOption){
		arr.push(jclqOption.lqRfKeShengOption);
	}
	if(null != jclqOption.lqRfZhuShengOption){
		arr.push(jclqOption.lqRfZhuShengOption);
	}
	if(null != jclqOption.lqKeShengList && jclqOption.lqKeShengList.length > 0){
		arr=arr.concat(jclqOption.lqKeShengList);
	} 
	if(null != jclqOption.lqZhuShengList && jclqOption.lqZhuShengList.length > 0){
		arr=arr.concat(jclqOption.lqZhuShengList);
	}
	if(arr.length > 0){
		var min = 0;
		var minBet = {};
		for (var j = 0; j < arr.length; j++) {
			var bet = arr[j];
			if(min==0 || bet.odd < min){
				minBet=bet;
				min=bet.odd;
			}
		}
		minListMap.put(i, [minBet]);
	} else {
		minListMap.remove(i);
	}
}

function combineMaxPossibleList(i){
	jclqOption = jclqOptionsMap.get(i);
	if(null == posibleListMap.get(i)){
		posibleListMap.put(i, new Array());
	}
	var possibleLists = posibleListMap.get(i);
	possibleLists=[];//重新计算可能的集合
	combineZhuSheng(jclqOption,possibleLists);
	combineKeSheng(jclqOption,possibleLists);
	if(jclqOption.lqRfZhuShengOption){
		combineRfZhuSheng(jclqOption,possibleLists);
	}
	if(jclqOption.lqRfKeShengOption){
		combineRfKeSheng(jclqOption,possibleLists);
	}
	maxListMap.put(i, new Array());
	minListMap.put(i, new Array());
	var maxList = maxListMap.get(i);
	var max=0;
	for (var k = 0; k < possibleLists.length; k++) {
		var possibleList = possibleLists[k];
		var sum = 0;
		var sfMaxOption=null;
		var rfsfMaxOption=null;
		var dxfMaxOption=null;
		var sfcMaxOption=null;
		for (var j = 0; j < possibleList.length; j++) {
			var bet = possibleList[j];
			var odd = parseFloat(bet.odd);
			if(bet.playType == "06LC2"){//让分胜负
				if(!rfsfMaxOption || parseFloat(rfsfMaxOption.odd) <odd){
					rfsfMaxOption = bet;
				}
			} else if(bet.playType == "07LC2"){//胜负
				if(!sfMaxOption || parseFloat(sfMaxOption.odd) <odd){
					sfMaxOption = bet;
				}
			} else if(bet.playType == "08LC2"){//胜分差
				if(!sfcMaxOption || parseFloat(sfcMaxOption.odd) <odd){
					sfcMaxOption = bet;
				}
			} else if(bet.playType == "09LC2"){//大小分
				if(!dxfMaxOption || parseFloat(dxfMaxOption.odd) <odd){
					dxfMaxOption = bet;
				}
			}
		}
		sum = (sfMaxOption ? parseFloat(sfMaxOption.odd):0) +
			(rfsfMaxOption ? parseFloat(rfsfMaxOption.odd):0) +
			(dxfMaxOption ? parseFloat(dxfMaxOption.odd):0) +
			(sfcMaxOption ? parseFloat(sfcMaxOption.odd):0);
		if(max==0 || sum > max){
			max=sum;
			var tmpList = new Array();
			sfMaxOption ? tmpList.push(sfMaxOption):0;
			rfsfMaxOption ? tmpList.push(rfsfMaxOption):0;
			dxfMaxOption ? tmpList.push(dxfMaxOption):0;
			sfcMaxOption ? tmpList.push(sfcMaxOption):0;
			maxList=tmpList;
		}
	}
	if(maxList && maxList.length > 0){
		maxListMap.put(i, maxList);
	} else {
		maxListMap.remove(i);
	}
}

function calculateRelation(pArray,pArray2,defaultScore,bet){
	if(Math.abs(defaultScore) < 6){
		pArray.push(bet);
		if(parseInt(bet.option.charAt(1)) < 2){
			pArray2.push(bet);
		}
	} else if(Math.abs(defaultScore) < 11){
		if(parseInt(bet.option.charAt(1)) > 1){
			pArray.push(bet);
		}
		if(parseInt(bet.option.charAt(1)) < 3){
			pArray2.push(bet);
		}
	} else if(Math.abs(defaultScore) < 16){
		if(parseInt(bet.option.charAt(1)) > 2){
			pArray.push(bet);
		}
		if(parseInt(bet.option.charAt(1)) < 4){
			pArray2.push(bet);
		}
	} else if(Math.abs(defaultScore) < 21){
		if(parseInt(bet.option.charAt(1)) > 4){
			pArray.push(bet);
		}
		if(parseInt(bet.option.charAt(1)) < 5){
			pArray2.push(bet);
		}
	} else if(Math.abs(defaultScore) < 26){
		if(parseInt(bet.option.charAt(1)) > 4){
			pArray.push(bet);
		}
		if(parseInt(bet.option.charAt(1)) < 6){
			pArray2.push(bet);
		}
	} else {
		if(parseInt(bet.option.charAt(1)) > 5){
			pArray.push(bet);
		}
		if(parseInt(bet.option.charAt(1)) < 7){
			pArray2.push(bet);
		}
	}
}
function pushDaXiaoFen(pArray){
	if(jclqOption.dxfDaOption && !jclqOption.dxfXiaoOption){
		pArray.push(jclqOption.dxfDaOption);
	} else if(!jclqOption.dxfDaOption && jclqOption.dxfXiaoOption){
		pArray.push(jclqOption.dxfXiaoOption);
	} else if(jclqOption.dxfDaOption && jclqOption.dxfXiaoOption){
		if(parseFloat(jclqOption.dxfDaOption.odd) > parseFloat(jclqOption.dxfXiaoOption.odd)){
			pArray.push(jclqOption.dxfDaOption);
		} else {
			pArray.push(jclqOption.dxfXiaoOption);
		}
	}
}

function combineZhuSheng(jclqOption,possibleList){
	var pArray = new Array();
	pushDaXiaoFen(pArray);
	if(jclqOption.lqZhuShengOption){
		pArray.push(jclqOption.lqZhuShengOption);
	}
	if(jclqOption.lqZhuShengList.length > 0){
		//pArray=pArray.concat(jclqOption.lqZhuShengList);
		pArray.push(findListMaxBet(jclqOption.lqZhuShengList));//只放入可能的最大选项
		if(jclqOption.lqRfZhuShengOption){
			var defaultScore = parseFloat(jclqOption.lqRfZhuShengOption.defaultScore);
			if(defaultScore > 0 || Math.abs(defaultScore) < 6){
				pArray.push(jclqOption.lqRfZhuShengOption);
			}
		}
	} else {
		if(jclqOption.lqRfKeShengOption && jclqOption.lqRfZhuShengOption){
			if(parseFloat(jclqOption.lqRfZhuShengOption.defaultScore)<0){
				if(parseFloat(jclqOption.lqRfKeShengOption.odd)>
					parseFloat(jclqOption.lqRfZhuShengOption.odd)){
					pArray.push(jclqOption.lqRfKeShengOption);
				} else {
					pArray.push(jclqOption.lqRfZhuShengOption);
				}
			} else {
				pArray.push(jclqOption.lqRfZhuShengOption);
			}
		} else if (jclqOption.lqRfKeShengOption){
			if(parseFloat(jclqOption.lqRfKeShengOption.defaultScore)<0){
				pArray.push(jclqOption.lqRfKeShengOption);
			}
		} else if (jclqOption.lqRfZhuShengOption){
			pArray.push(jclqOption.lqRfZhuShengOption);
		}
	}
	if(pArray.length > 0){
		possibleList.push(pArray);
	}
}

function combineRfZhuSheng(jclqOption,possibleList){
	var pArray = new Array();
	var pArray2 = new Array();
	pushDaXiaoFen(pArray);
	pushDaXiaoFen(pArray2);
	if(jclqOption.lqZhuShengOption){
		pArray.push(jclqOption.lqZhuShengOption);
		pArray2.push(jclqOption.lqZhuShengOption);
	}
	if(jclqOption.lqRfZhuShengOption){
		pArray.push(jclqOption.lqRfZhuShengOption);
	} 
	if(jclqOption.lqRfKeShengOption){
		pArray2.push(jclqOption.lqRfKeShengOption);
	} 
	var defaultScore = parseFloat(jclqOption.lqRfZhuShengOption.defaultScore);
	for (var i = 0; i < jclqOption.lqZhuShengList.length; i++) {
		var bet = jclqOption.lqZhuShengList[i];
		if(defaultScore < 0){
			calculateRelation(pArray,pArray2,defaultScore,bet);
		} else {
			pArray = pArray.concat(jclqOption.lqZhuShengList);
			break;
		}
	}
	if(pArray.length > 0){
		possibleList.push(pArray);
	}
	if(pArray2.length > 0){
		possibleList.push(pArray2);
	}
}

function combineKeSheng(jclqOption,possibleList){
	var pArray = new Array();
	pushDaXiaoFen(pArray);
	if(jclqOption.lqKeShengOption){
		pArray.push(jclqOption.lqKeShengOption)
	}
	if(jclqOption.lqKeShengList.length > 0){
		//pArray=pArray.concat(jclqOption.lqKeShengList);
		pArray.push(findListMaxBet(jclqOption.lqKeShengList));//只放入可能的最大选项
		if(jclqOption.lqRfKeShengOption){
			var defaultScore = parseFloat(jclqOption.lqRfKeShengOption.defaultScore);
			if(defaultScore < 0 || Math.abs(defaultScore) < 6){
				pArray.push(jclqOption.lqRfKeShengOption);
			}
		}
	} else {
		if(jclqOption.lqRfKeShengOption && jclqOption.lqRfZhuShengOption){
			if(parseFloat(jclqOption.lqRfZhuShengOption.defaultScore)>0){
				if(parseFloat(jclqOption.lqRfKeShengOption.odd)>
					parseFloat(jclqOption.lqRfZhuShengOption.odd)){
					pArray.push(jclqOption.lqRfKeShengOption);
				} else {
					pArray.push(jclqOption.lqRfZhuShengOption);
				}
			} else {
				pArray.push(jclqOption.lqRfKeShengOption);
			}
		} else if (jclqOption.lqRfKeShengOption){
			pArray.push(jclqOption.lqRfKeShengOption);
		} else if (jclqOption.lqRfZhuShengOption){
			if(parseFloat(jclqOption.lqRfZhuShengOption.defaultScore)>0){
				pArray.push(jclqOption.lqRfZhuShengOption);
			}
		}
	}
	if(pArray.length > 0){
		possibleList.push(pArray);
	}
}

function combineRfKeSheng(jclqOption,possibleList){
	var pArray = new Array();
	var pArray2 = new Array();
	pushDaXiaoFen(pArray);
	pushDaXiaoFen(pArray2);
	if(jclqOption.lqKeShengOption){
		pArray.push(jclqOption.lqKeShengOption);
		pArray2.push(jclqOption.lqKeShengOption);
	}
	if(jclqOption.lqRfKeShengOption){
		pArray.push(jclqOption.lqRfKeShengOption);
	} 
	if(jclqOption.lqRfZhuShengOption){
		pArray2.push(jclqOption.lqRfZhuShengOption);
	} 
	var defaultScore = parseFloat(jclqOption.lqRfKeShengOption.defaultScore);
	for (var i = 0; i < jclqOption.lqKeShengList.length; i++) {
		var bet = jclqOption.lqKeShengList[i];
		if(defaultScore > 0){
			calculateRelation(pArray,pArray2,defaultScore,bet);
		} else {
			pArray = pArray.concat(jclqOption.lqKeShengList);
			break;
		}
	}
	if(pArray.length > 0){
		possibleList.push(pArray);
	}
	if(pArray2.length > 0){
		possibleList.push(pArray2);
	}
}
function  getBetsData(){
	var notes=new Array();
	var max=new Array();
	var min=new Array();
	for (var i = 0; i < jclqOptionsMap.keys().length; i++) {
		var key = jclqOptionsMap.keys()[i];
		jclqOption = jclqOptionsMap.get(key);
		var sf=0,rfsf=0,dxf=0,sfc=0,arr=[];
		if(jclqOption.lqZhuShengOption){sf++;}
		if(jclqOption.lqKeShengOption){sf++;}
		if(jclqOption.lqRfZhuShengOption){rfsf++;}
		if(jclqOption.lqRfKeShengOption){rfsf++;}
		if(jclqOption.dxfDaOption){dxf++;}
		if(jclqOption.dxfXiaoOption){dxf++;}
		sfc +=jclqOption.lqZhuShengList.length;
		sfc +=jclqOption.lqKeShengList.length;
		if(sf>0){arr.push(sf);}
		if(rfsf>0){arr.push(rfsf);}
		if(dxf>0){arr.push(dxf);}
		if(sfc>0){arr.push(sfc);}
		if(arr.length > 0){
			notes[key]=arr;
		}
	}
	var keys = maxListMap.keys();
	for (var i = 0; i < keys.length; i++) {
		var k = keys[i];
		var list = maxListMap.get(k);
		var arr=new Array();
		var p={};
		var index=0;
		for (var j = 0; j < list.length; j++) {
			var bet = list[j];
			if(bet.playType in p){//同一种玩法
				var x = p[bet.playType];
				if(!arr[x]){
					arr[x]=bet.odd;
				} else {
					if(parseFloat(bet.odd)>parseFloat(arr[x])){
						arr[x]=bet.odd;
					}
				}
			} else {
				p[bet.playType]=index;
				arr[index]=bet.odd;
				index++;
			}
		}
		max[k]=arr;
	}
	keys = minListMap.keys();
	for (var i = 0; i < keys.length; i++) {
		var k = keys[i];
		var list = minListMap.get(k);
		if(list.length > 0){
			min[k]=list[0].odd;
		}
	}
	return [notes,max,min];
}

function dealWithAddJCLQBet(bet){
	if(bet.matchIndex){
		var jclqOption;
		if(null == jclqOptionsMap.get(bet.matchIndex)){
			jclqOption = new JCLQSelectedOption();
			jclqOption.init();
			jclqOptionsMap.put(bet.matchIndex, jclqOption);
		} else {
			jclqOption = jclqOptionsMap.get(bet.matchIndex);
		}
		var playType=bet.playType;//玩法
		var option=bet.option;//玩法选项
		var odd=bet.odd;//赔率
		var defaultScore=parseFloat(bet.defaultScore);//让分
		if("06LC2" == playType) {//让分胜负
			if(option == 2){//客胜
				jclqOption.lqRfKeShengOption=bet;
			} else {//主胜
				jclqOption.lqRfZhuShengOption=bet;
			}
		} else if("07LC2" == playType) {//胜负
			if(option == 2){//客胜
				jclqOption.lqKeShengOption = bet;
			} else {//主胜
				jclqOption.lqZhuShengOption = bet;
			}
		} else if("08LC2" == playType) {//胜负差
			if(option.charAt(0)==1){//客胜
				jclqOption.lqKeShengList.push(bet);
			} else {//主胜
				jclqOption.lqZhuShengList.push(bet);
			}
		} else if("09LC2" == playType) {//大小分
			if(option == 1){//大
				jclqOption.dxfDaOption=bet;
			} else {
				jclqOption.dxfXiaoOption=bet;
			}
		}
		//printList();
		combineMaxPossibleList(bet.matchIndex);
		combineMinPossibleList(bet.matchIndex);
	}
	return getBetsData();
}
function dealWithDelJCLQBet(bet){
	if(bet.matchIndex){
		var jclqOption;
		if(null == jclqOptionsMap.get(bet.matchIndex)){
			return ;
		} else {
			jclqOption = jclqOptionsMap.get(bet.matchIndex);
			var playType=bet.playType;//玩法
			var option=bet.option;//玩法选项
			var odd=bet.odd;//赔率
			var defaultScore=parseFloat(bet.defaultScore);//让分
			if("06LC2" == playType) {//让分胜负
				if(option==2){//客胜
					jclqOption.lqRfKeShengOption=null;
				} else {//主胜
					jclqOption.lqRfZhuShengOption=null;
				}
			} else if("07LC2" == playType) {//胜负
				if(option == 2){//客胜
					jclqOption.lqKeShengOption = null;
				} else {//主胜option == 2
					jclqOption.lqZhuShengOption = null;
				}
			} else if("08LC2" == playType) {//胜负差
				if(option.charAt(0)==1){//客胜
					for (var i = 0; i < jclqOption.lqKeShengList.length; i++) {
						var tmpBet = jclqOption.lqKeShengList[i];
						if(tmpBet.playType==playType && tmpBet.option==option){
							jclqOption.lqKeShengList.splice(i,1);
							break;
						}
					}
				} else {//主胜
					for (var i = 0; i < jclqOption.lqZhuShengList.length; i++) {
						var tmpBet = jclqOption.lqZhuShengList[i];
						if(tmpBet.playType==playType && tmpBet.option==option){
							jclqOption.lqZhuShengList.splice(i,1);
							break;
						}
					}
				}
			} else if("09LC2" == playType) {//大小分
				if(option == 1){//大
					jclqOption.dxfDaOption=null;
				} else {
					jclqOption.dxfXiaoOption=null;
				}
			}
		}
		//printList();
		combineMaxPossibleList(bet.matchIndex);
		combineMinPossibleList(bet.matchIndex);
	}
	return getBetsData();
}