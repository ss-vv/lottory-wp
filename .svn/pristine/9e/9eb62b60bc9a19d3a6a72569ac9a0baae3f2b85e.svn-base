// 追号
function log(msg){
	if(console && console.log) {
		console.log(msg);
	}
}

/**
 * 追号常量定义
 */
var repeat_constant = {
	maxNote:99,		/**最大投注倍数*/
	maxBetIssue:13,	/**最大投注期数*/
	betNote:1		/**默认投注倍数*/
};

/***
 * 用户的追号信息
 */
var repeat_user_profile = {
	issueNumber:0,		/**当前在售期号*/
	nextIssueNumber:0,	/**当前在售期的下一期*/
	buyAmount:0,		/**当前在售期用户投注的金额*/
	betNote:0,			/**当期投注的倍数*/
	singleAmount:0		/**投注的单倍金额*/
};

$(function(){
    $("#repeat_switch :radio").click(function(){
    	var repeatValue = $(this).attr('value');
    	switch(repeatValue){
    	case "0":
    		hide('stop_settings');
    		hide('issue_settings');
    		hide('issue_list');
    		hide('repeatTotal');
    		hide('repeatMeal');
    		hide('repeatPrivilege');
    		break;
    	case "1":
    		show('stop_settings');
    		show('issue_settings');
    		show('issue_list');
    		showRepeatIssueData($("#repeatIssueCount").val());
    		show('repeatTotal');
    		show('repeatPrivilege');
    		hide('repeatMeal');
    		show('repeatPrivilege');
    		break;
    	case "3":
    		hide('stop_settings');
    		show('repeatMeal');
    		hide('issue_settings');
    		hide('issue_list');
    		hide('repeatTotal');
    		hide('repeatPrivilege');
    		break;
    	}
    });
    
    // 中奖后停止checkbox控制后面控件是否可点。
    $("#stop_settings :checkbox[_op='stop_when_win']").change(function(){
    	stop_setting("stop_settings", this);
    });
    $("#meal_stop_settings :checkbox[_op='stop_when_win']").change(function(){
    	stop_setting("meal_stop_settings", this);
    });
    
    // 盈利 radio 控制盈利输入文本框
    $("#stop_settings :radio").change(function(){
    	stop_setting_win("stop_settings");
    });
    
    $("#meal_stop_settings :radio").change(function(){
    	stop_setting_win("meal_stop_settings");
    });
    
    /**
     * 保证此class上的输入框只能输入正整数
     */
    $(".positiveNum").bind("keyup", function() {
    	validInputToPositive($(this)[0]);
    });
    
    /**根据追加的期数，动态增加追号列表*/
    $("#repeatIssueCount").bind("keyup", function() {
    	var issueCount = $(this).val();
    	if(issueCount == 0 || "" == issueCount) {
    		$("#issue_list").empty();
    	} else if(issueCount > repeat_constant.maxBetIssue) {
    		issueCount = repeat_constant.maxBetIssue;
    		$(this).val(issueCount);
    	}
    	showRepeatIssueData(issueCount);
    });
    
    /**倍数递增*/
    $("#noteIncrease").click(function() {
    	updateBetNote($(this));
    });
    /**倍数翻倍*/
    $("#noteDouble").click(function() {
    	updateBetNote($(this));
    });
});

function hide(id){
	$('#'+id).hide();
}

function show(id){
	$('#'+id).show();
}

function enable(selector){
	$(selector).prop('disabled', false);
}

function disable(selector){
	$(selector).prop('disabled', true);
}

//中奖后停止checkbox控制后面控件是否可点。
var stop_setting = function(id, ck) {
	if($(ck).is(':checked')) {
		enable('#' + id + ' :radio');
		var stop_by_money_enabled = 
    		$("#" + id + " :radio[value='stop_by_money']").is(':checked');
		if (stop_by_money_enabled){
			enable('#' + id + ' :text');
		}
		$("#" + id + " :radio[value='stop_by_win']").attr("checked", "checked");
	} else{
		disable('#' + id + ' :radio');
		disable('#' + id + ' :text');
	}
};

//盈利 radio 控制盈利输入文本框
var stop_setting_win = function(id) {
	var stop_by_money_enabled = 
		$("#" + id + " :radio[value='stop_by_money']").is(':checked');
	if ( stop_by_money_enabled ) {
		enable('#' + id + ' :text');
	}else{
		disable('#' + id + ' :text');
	}
};

/**
 * 根据期数动态加载期列表
 */
var showRepeatIssueData = function(issueCount) {
	if("" == issueCount) {
		issueCount = 0;
	}
	var totalMount = 0;//总的追号投注金额
	var totalIssueCount = issueCount;//总的追号期数
	
	var issueList = $("#issue_list");
	issueList.empty();
	for(var i = 0; i < issueCount; i++) {
		var issueNb = (repeat_user_profile.nextIssueNumber+i);
		
		var row = $("<div class='row-item'></div>");
		var seq = $('<div class="iptchk" style="width:40px;"><span>' + (i+1) + '.&nbsp;</span></div>');
		row.append(seq);
		var issueNum = $('<div class="iptchk issueNum"></div>');
		var issueInfo = $('<label title="' + issueNb + '"><input type="checkbox" checked="checked" class="iniptchk issueNo"/><span>' + issueNb + '</span></label>');
		issueNum.append(issueInfo);
		row.append(issueNum);
		
		var issueMultiple = $('<div class="ipttxt ipttxt-b wh-txtb issueMultiple"></div>');
		var issueMulInput = $('<input type="text" size="2" value="' + repeat_user_profile.betNote + '" maxlength="2" class="inipttxt positiveNum betNote" onkeyup="validInputToPositive(this)" style="width:30px;text-align:center;color:black;"/>');
		issueMultiple.append(issueMulInput);
		row.append(issueMultiple);
		
		var betNote = $('<div class="tips">倍</div>');
		var betAmount = $('<div class="issueBetAmount">￥<label>' + 
				(repeat_user_profile.singleAmount * repeat_user_profile.betNote) + 
				'</label></div>');
		row.append(betNote);
		row.append(betAmount);
		issueList.append(row);

		totalMount = totalMount + (repeat_user_profile.singleAmount * repeat_user_profile.betNote);
		
		/**修改倍数时同时更新对应期的投注金额*/
		issueMulInput.bind("keyup", function() {
			var betNote = $(this).val();
			$(this).parent().parent().children(".issueBetAmount").children("label").empty();
			$(this).parent().parent().children(".issueBetAmount").children("label")
			.append(betNote * repeat_user_profile.singleAmount);
			calculateRepeatDetailData();
		});
		/**倍数填写失去焦点，则重置倍数*/
		issueMulInput.bind("blur", function() {
			var betNote = $(this).val();
			if("" == betNote) {
				betNote = repeat_constant.betNote;
				$(this).val(betNote);
			}
			$(this).parent().parent().children(".issueBetAmount").children("label").empty();
			$(this).parent().parent().children(".issueBetAmount").children("label")
			.append(betNote * repeat_user_profile.singleAmount);
			calculateRepeatDetailData();
		});
	}
	//加入期选择单击事件监听
	cancelIssueAndCalculateRepeatData();
	resetRepeatDetail(totalIssueCount, totalMount);
};


/**
 * 用于倍数递增和倍数翻倍
 */
var updateBetNote = function() {
	var sourceObj = arguments[0];
	var sourceId = sourceObj.attr("id");
	
	var issueList = $("#issue_list .row-item");
	if(issueList && issueList.length > 0) {
		var len = issueList.length;
		for(var i = 0; i < len; i++) {
			var issue = issueList[i];
			var betNote = i+1;
			if(betNote > repeat_constant.maxNote) {
				betNote = repeat_constant.maxNote;
			}
			var betNote = "";
			var currIssueBetAmount = "";
			if(sourceId == "noteIncrease") {
				betNote = i+1;
    			if(betNote > repeat_constant.maxNote) {
    				betNote = repeat_constant.maxNote;
    			}
			} else if(sourceId == "noteDouble") {
				betNote = Math.pow(2, i);
    			if(betNote > repeat_constant.maxNote) {
    				betNote = repeat_constant.maxNote;
    			}
			}
			$(issue).find(".betNote").val(betNote);
			currIssueBetAmount = repeat_user_profile.singleAmount * betNote;
			
			$(issue).find(".issueBetAmount").children("label").empty();
			$(issue).find(".issueBetAmount").children("label").append(currIssueBetAmount);
		}
	}
	calculateRepeatDetailData();
};

/**
 * 验证用户的输入内容，保证为非负整数
 */
var validInputToPositive = function() {
	var em = arguments[0];
	var profit = $(em).val();
	var len = profit.length;
	for ( var i = 0; i < len; i++) {
		var ch = profit.substr(i, 1);
		var chEncode = escape(ch);
		var codeIntVal = parseInt(chEncode.substring(0, 1));
		if (isNaN(codeIntVal) || codeIntVal > 9 || codeIntVal < 0) {
			var str = profit.substr(0, i);
			if("" == str) {
				str = 1;
			}
			$(em).val(str);
			break;
		}
		var emVal = $(em).val();
		var emIntVal = parseInt(emVal);
		if(0 == emIntVal || isNaN(emIntVal)) {
			$(em).val(1);
		}
	}
	calculateRepeatDetailData();
};

/**计算用户追号的详情数据：追的期数和总的投入金额*/
var calculateRepeatDetailData = function() {
	var totalMount = 0;//总的追号投注金额
	var totalIssueCount = 0;//总的追号期数
	
	var issueList = $("#issue_list .row-item");
	var len = issueList.length;
	for(var i = 0; i < len; i++) {
		var row = issueList[i];
		var issueNum = $(row).find(".issueNo");
		var checked = issueNum.attr("checked");
		var betNote = $(row).find(".betNote");
		
		if("checked" == checked) {
			totalMount = totalMount + (betNote.val() * repeat_user_profile.singleAmount);
			totalIssueCount = totalIssueCount + 1;
		}
	}
	resetRepeatDetail(totalIssueCount, totalMount);
};

/**重置用户追号结果信息*/
var resetRepeatDetail = function() {
	$("#totalBetIssue").empty();
	$("#totalBetIssue").append(arguments[0]);
	$("#totalBetMount").empty();
	$("#totalBetMount").append(arguments[1]);
};

/**用户取消指定期则重新计算追号结果*/
var cancelIssueAndCalculateRepeatData = function() {
	var issueList = $("#issue_list .row-item");
	var len = issueList.length;
	for(var i = 0; i < len; i++) {
		var row = issueList[i];
		var issueNo = $(row).find(".issueNo");
		issueNo.click(function() {
			var checked = $(this).attr("checked");
			var issueBetAmount = 0;
			if("checked" == checked) {
				var betNote = $(this).parents(".row-item").find(".betNote").val();
				issueBetAmount = repeat_user_profile.singleAmount * betNote;
			} else {
				issueBetAmount = "<span style='color:red;font-weight:bold;'>" + issueBetAmount + "</span>";
			}
			$(this).parents(".row-item").find(".issueBetAmount").children("label").empty();
			$(this).parents(".row-item").find(".issueBetAmount").children("label").append(issueBetAmount);
			calculateRepeatDetailData();
		});
	}
};

/**追号套餐JS*/
var setProgram = function() {
	var dest = $(arguments[0]).val();
	$("#mealType").find("input").each(function() {
		if($(this).val() == dest) {
			$(this).next().css({color:"red"});
		} else {
			$(this).next().css({color:"black"});
		}
		$("#repeatMeal").find(".mealMonth").empty();
		$("#repeatMeal").find(".mealMonth").append(dest);
		
		var issueCnt = 0;
		switch(parseInt(dest)) {
			case 1: issueCnt = 14;
				break;
			case 3: issueCnt = 40;
				break;
			case 6: issueCnt = 79;
				break;
			case 12: issueCnt = 154;
				break;
		}
		$("#repeatMeal").find(".mealBetIssue").empty();
		$("#repeatMeal").find(".mealBetIssue").append(issueCnt);
		
		var issueBetAmount = $("#repeatMeal").find(".currIssueBetAmount").text();
		$("#repeatMeal").find(".mealTotalAmount").empty();
		$("#repeatMeal").find(".mealTotalAmount").append(issueCnt * parseInt(issueBetAmount));
	});
};

//验证中奖停止类型
var validStopTypeOfWin = function() {
	var no_repeat = $("#no_repeat").val();
	if(0 != no_repeat) {
		var stopWhenWin = $("#stop_settings :checkbox[_op='stop_when_win']");
		var isChecded = stopWhenWin[0].checked;
		var profitStandardStop = $("input[name='profitStandardStop']").val();
		if(isChecded === true) {
			var isCheckStopByMoney = $("#stop_settings :radio[value='stop_by_money']").is(':checked');
			if(isCheckStopByMoney) {
				log(/^\d+$/g.test(profitStandardStop));
				if(!/^\d+$/g.test(profitStandardStop))return false;
			}
		}
	}
	return true;
};

//获取追号期信息列表
var getRepeatIssueList = function() {
	var isRepeatChecked = $("#repeat_switch :radio[value='1']").is(":checked");
	var isRepeatMealChecked = $("#repeat_switch :radio[value='3']").is(":checked");
	var betNoteList = [];
	var betNoteListStr = $('input[name="betNoteList"]');
	
	if(isRepeatChecked) {
		var row = $("#issue_list").find(".row-item");
		var len = row.length;
		if(len > 0) {
			row.each(function() {
				if($(this).find('input[type="checkbox"]').is(":checked")) {
					var betNote = $(this).find('.betNote').val();
					var betNoteIsValid = /^\d+$/g.test(betNote);
					if(betNoteIsValid) {
						betNoteList.push(betNote);
					}
				}
			});
		}
		//log(betNoteList);
		betNoteListStr.val(betNoteList.join(","));
	}
	if(isRepeatMealChecked) {
		betNoteListStr.val(repeat_user_profile.betNote);
	}
	//log(betNoteListStr.val());
	return betNoteList.length > 0 ? true :false;
};
