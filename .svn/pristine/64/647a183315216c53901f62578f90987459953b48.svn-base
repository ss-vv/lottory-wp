/**
 * @desc 输入框的自动完成功能
 * @author lei.li@unison.net.cn
 * @createTime 2013-12-11
 * @version 1.0
 * */

/**
 * 定义自动完成对象
 */

function autocomplete() {
};
autocomplete.prototype.area = '';
autocomplete.prototype.clickButton = '';
autocomplete.prototype.dialog = '';
autocomplete.prototype.box = '';
autocomplete.prototype.list = '';
autocomplete.prototype.item = 'li';
autocomplete.prototype.highlighterLine = 'auto-complete-current-line';
autocomplete.prototype.history = 0;

/**
 * 自动完成方法
 * @param wd 自动完成过滤框jquery对象
 */
autocomplete.prototype.auto = function(wd) {
	if(null == wd || wd == "") {
		this.box.hide();
	} else {
		this.ajaxData(wd);
	}
};

/**
 * 初始化控件参数
 * 默认属性定义的顺序去初始化参数
 */
autocomplete.prototype.initParam = function(dest, clickButton, dialogId) {
	this.area = dest;
	this.clickButton = clickButton;
	this.dialog = dialogId;
};

/**
 * 初始化dialog，并渲染控件
 * @param dialog	对话框初始化json对象
 */
autocomplete.prototype.renderDialog = function(dialog) {
	if(dialog.title == undefined) {
		dialog.title = "请输入赛事编号/球队名称 过滤";
	}
	$("#"+this.dialog).dialog({
		title : dialog.title,
	    autoOpen: false,
	    minHeight: 100,
	    height:'auto',
	    width: 350,
	    modal: true,
	    position: {my:dialog.my, at:dialog.at, of:dialog.target}
	});
};

/**
 *创建用户自定义元素，并追加到对话框内
 */
autocomplete.prototype.createUserDefineElement = function() {
	var userDefind = 
	'<input type="text" value="" class="auto-input"/>' +
	'<div class="autocomplete-box">' +
		'<div class="autocomplete-list"></div>' +
	'</div>';
	
	$("#"+this.dialog).append($(userDefind));
	var box = $("#" + this.dialog).children()[1];
	this.box = $(box);
	this.list = this.box.children();
};

/**
 * 将用户选中的数据填充到文本框,并使用两个$$符号引住
 */
autocomplete.prototype.fillData = function(li) {
	var $this = this;
	li.click(function(event) {
		event.stopPropagation();
		var name = li.find("span");
		var id = li.find("div");
		var selItem = name.html() + id.html();
		$this.append(selItem + "$");
	});
};

autocomplete.prototype.insertText = function(obj,str) {
    if (document.selection) {
        var sel = document.selection.createRange();
        sel.text = str;
    } else if (typeof obj.selectionStart === 'number' && typeof obj.selectionEnd === 'number') {
        var startPos = obj.selectionStart,
            endPos = obj.selectionEnd,
            cursorPos = startPos,
            tmpStr = obj.value;
        obj.value = tmpStr.substring(0, startPos) + str + tmpStr.substring(endPos, tmpStr.length);
        cursorPos += str.length;
        obj.selectionStart = obj.selectionEnd = cursorPos;
    } else {
        obj.value += str;
    }
}

/**
 * 将内容追加到目标输入框
 */
autocomplete.prototype.append = function(html) {
	var txtArea = $("#"+this.area)[0];
	this.box.slideUp(300);
	$( "#" + this.dialog).dialog( "close" );
	this.insertText(txtArea, html);
	//this.moveEnd(txtArea);
};

/**
 * 高亮当前悬停的行,li是当前行的jquery对象
 */
autocomplete.prototype.highlight = function(li) {
	var $this = this;
	li.hover(function() {
		$this.list.find($this.item).removeClass($this.highlighterLine);
		li.addClass($this.highlighterLine);
	});
};

/**
 * 绑定显示过滤对话框的事件
 */
autocomplete.prototype.bindShowDialogEvent = function() {
	var $this = this;
	//点击过滤按钮显示弹出框
	$("."+this.clickButton).click(function() {
		$this.show();
	});
	$("#"+this.area).keypress(function(e) {
		var e = e||event; 
		var currKey = e.keyCode || e.which || e.charCode; 
		var mark = String.fromCharCode(currKey);
		if(mark == "$" || mark == "￥") {
			$this.show();
		}		
	})
};

/**
 * 判断是否需要进行自动获取数据
 */
autocomplete.prototype.isAuto = function(event) {
	var key = event.keyCode;
	var inputVal = this.getDialogInput().val();
	var inputLen = this.getDialogInputLen();
	var len = this.history;
	if(key != 13 && key != 38 && key != 40 
			&& "" != inputVal && (len != inputLen)) {
		this.setHistoryLen(inputVal.length);
		return true;
	}
	return false;
};

/**
 * 绑定用户特殊按键的事件监听
 */
autocomplete.prototype.bindKeyEvent = function() {
	var $this = this;
	var inputFilter = this.getDialogInput();
	inputFilter.keyup(function(event) {
		if($this.isAuto(event) == true) {
			setTimeout(function() {
				$this.auto(inputFilter.val());
			}, 500);
		}
	});
	inputFilter.keydown(function(event) {
		var highlighterLine = $this.highlighterLine;
		var key = event.keyCode;
		var currentLi = $("." + highlighterLine);
		switch (key) {
		case 13://回车
			if(currentLi.length > 0) {
        		currentLi.click();
        	} else {
        		var filter = inputFilter.val();
        		if(filter) {
        			$this.append(filter);
        		}
        	}
			break;
		case 38://向上按钮
			$this.list.find($this.item).removeClass(highlighterLine);
        	var prevLi = currentLi.prev();
        	if(prevLi.length == 0) {
        		$this.list.find($this.item + ':last-child').addClass(highlighterLine);
        	} else {
        		currentLi.prev().addClass(highlighterLine);
        	}
			break;
		case 40://向下按钮
			$this.list.find($this.item).removeClass(highlighterLine);
        	var nextLi = currentLi.next();
        	if(nextLi.length == 0) {
        		$this.list.find($this.item + ':first-child').addClass(highlighterLine);
        	} else {
        		nextLi.addClass(highlighterLine);
        	}
        	break;
		}
	});
};

/**
 * 获取对话框中input输入框jquery对象
 */
autocomplete.prototype.getDialogInput = function() {
	var inputFilter = $("#"+this.dialog).find("input");
	return inputFilter;
};


/**
 * 获取对话框中input框内容长度
 */
autocomplete.prototype.getDialogInputLen = function() {
	return this.getDialogInput().val().length;
};

autocomplete.prototype.setHistoryLen = function(len) {
	return this.history = len;
};

/**
 * 显示对话框并清除缓存数据
 */
autocomplete.prototype.show = function() {
	this.getDialogInput().val("");
	this.list.empty();
	$("#" + this.dialog).dialog( "open" );
};

/**
 * 移动光标到文本框末尾
 */
autocomplete.prototype.moveEnd = function(obj){
    var len = obj.value.length;
    if (document.selection) {
        var sel = obj.createTextRange();
        sel.moveStart('character',len);
        sel.collapse();
        sel.select();
    } else if (typeof obj.selectionStart == 'number' 
    	&& typeof obj.selectionEnd == 'number') {
    	obj.selectionStart = len;
    	obj.selectionEnd = len;
    }
    obj.focus();
};

/**
 * 初始化对话框控件,并绑定用户输入的事件监听
 * @param dest	目标内容输入框ID
 * @param clickButton	单击显示控件的按钮ID
 * @param dialog	对话框初始化json对象
 * @param dialogFilter	对话框ID
 */
autocomplete.prototype.init = function(dest, clickButton, dialog, dialogId) {
	this.initParam(dest, clickButton, dialogId);
	this.createUserDefineElement();
	this.renderDialog(dialog);
	this.bindShowDialogEvent();
	this.bindKeyEvent();
};

/**
 * @param li 简单的html标签字符串
 */
autocomplete.prototype.bindItemEvent = function(liList) {
	for(var i=0; i<liList.length; i++) {
		var li = liList[i];
		this.highlight($(li));
		this.fillData($(li));
	}
};

/**
 * 将获取的数据项追加到自动完成列表中
 * @param resultList 自动完成列表项集合
 */
autocomplete.prototype.appendItem = function(resultList) {
	//清除历史列表项数据
	this.list.empty();
	this.list.append($(resultList));
	this.list.find(this.item + ':first-child').addClass(this.highlighterLine);
	this.box.slideDown(300);
};

/**
 * 追加列表想到DOM并绑定上下切换事件
 * @param segment 是mustache渲染后的对象
 */
autocomplete.prototype.bindItemData = function(segment) {
	if("" != segment) {
		this.appendItem(segment);
		var liList = this.list.find(this.item);
		this.bindItemEvent(liList);
	}
};

/**
 * 异步加载给定关键字的自动完成数据列表
 * @param wd 搜索过滤的关键字
 */
autocomplete.prototype.ajaxData = function(wd) {
	var $this = this;
	$.post("/ajax_search_match?v=a", {"key": ""+wd}, function(data) {
		if(data && data.length > 0) {
			$this.renderMatchs(data);
		}
	},"json");
};

//===========================列表渲染的方法======================================
autocomplete.prototype.tpl = function() {
	var template = 
		"{{#matchs}}" +
		"<li>" +
			"<span>" +
				"{{lotteryName}}{{matchCode}} {{matchName}}" +
			"</span>" +
			"<div class='hide'>" +
				"({{matchIdStr}})" +
			"</div>" +
			"<em class='red'>&nbsp;{{isOnSale}}</em>" +
		"</li>" +
		"{{/matchs}}";
	return template;
};

autocomplete.prototype.renderMatchs = function(results) {
	var lotteryType = "";
	var segment = $.mustache(this.tpl(), {
		matchs: results,
		lotteryName: function() {//彩种名称
			var lott = new lottery();
			return lott.getName(this.lotteryType);
		},
		matchIdStr: function() {//赛事唯一标识
			var mId = this.lotteryType;
			switch (mId) {
			case lotteryName.JCZQ:
				mId = "JZ" + this.id.substr(2);
				break;
			case lotteryName.JCLQ:
				mId = "JL" + this.id.substr(2);
				break;
			case lotteryName.CTZC:
				mId = "CZ" + (this.matchCode + this.matchId);
				break;
			case lotteryName.BJDC:
				mId = "BD" + (this.id);
				break;
			}
			return mId;
		},
		isOnSale: function() {//是否在售
			var resuslt = "";
			var now = new Date();
			var d = new Date(this.entrustDeadline.replace("T"," "));
			if(now.getTime() <= d.getTime()) {
				resuslt = "在售";
			}
			return resuslt;
		}
	});
	this.bindItemData(segment);
};

//===========================绑定页面赛事过滤事件======================================
//初始化自动完成对话框
function bindAutoComplete() {
	var autoPublish = new autocomplete(); 
	var positionPublish  = {my:"left top+40", at:"left", target:"#main-editor"}
	autoPublish.init("weiboContent", "matchFilter", positionPublish, "filter_dialog_publish");
	
	var autoShare = new autocomplete();
	var positionShare = {my:"left top+85", at:"left", target:"#emotionAreaForShare"}
	autoShare.init("shareContent", "matchFindShare", positionShare, "filter_dialog_share");
	
	var autoForward = new autocomplete();
	var positionForward = {my:"left top+85", at:"left", target:"#forwardContent"}
	autoForward.init("forwardContent", "matchFindForward", positionForward, "filter_dialog_forward");
}

$(function() {
	bindAutoComplete();
});