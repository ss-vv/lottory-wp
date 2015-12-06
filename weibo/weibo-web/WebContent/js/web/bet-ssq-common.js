/**
 * author : Yang Bo (bob.yang.dev@gmail.com)
 **/

// debug function, 上线前需要注释掉，否则IE8以下报错。
function log(msg){
//	console.log(msg);
}

// ========= global =============
g_betList= new Array();				// 投注列表
g_currentBet = new SSQBet();			// 当前选中的投注内容
g_countDownTimer = null;			// 倒计时的计时器
g_issueInfo = null;				// 期信息

// 胆拖投注用
g_currentBetDT = new SSQBetDT();		// 当前选中的投注内容 

// ========= constants ==========
DISTANCE_DAY = 24*60*60*1000;			// 天
DISTANCE_MINUTE = 60*1000;			// 分钟
DISTANCE_SECOND = 1000;				// 秒

// ========= 普通投注页面JS ============
function init_ball_click_handler(){
	// 红球
	iterate_red_balls(function(ball){
		ball.click(function(){
			$(this).toggleClass('red_ball');
			if(count_red_balls()>16){
				alert("红球最多只能选16个");
				$(this).toggleClass('red_ball');
				return;
			}
			update_sel_prompt();
		});
	});
	// 蓝球
	iterate_blue_balls(function(ball){
		ball.click(function(){
			$(this).toggleClass('blue_ball');
			update_sel_prompt();
		});
	});
}

// 遍历红球元素
function iterate_red_balls(fn_callback){
	for(var i=1; i<=33; i++){
		var ball_id = "#rb"+i;
		fn_callback($(ball_id));
	}
}

// 遍历蓝球元素
function iterate_blue_balls(fn_callback, css_selector){
	for(var i=1; i<=16; i++){
		var ball_id = "#bb"+i;
		if(css_selector){
			ball_id = (css_selector + i);
		}
		fn_callback($(ball_id));
	}
}

// 更新选择多少红球、蓝球的提示
function update_sel_prompt(){
	var red_ball_num = count_red_balls();
	var blue_ball_num = count_blue_balls();
	$('#prompt>.red_text').html(red_ball_num);
	$('#prompt>.blue_text').html(blue_ball_num);
	// 计算注数
	update_bet_object();
	$('#notes').html(g_currentBet.notes);
	$('#money').html(g_currentBet.money);
}

function dt_update_sel_prompt(){
	dt_update_bet_object();
	var red_ball_num = g_currentBetDT.countRedBalls();
	var blue_ball_num = g_currentBetDT.countBlueBalls();
	$('#prompt_dt>.red_text').html(red_ball_num);
	$('#prompt_dt>.blue_text').html(blue_ball_num);
	// 计算注数
	$('#notes').html(g_currentBetDT.notes);
	$('#money').html(g_currentBetDT.money);
}

// 根据选择的红球、蓝球信息更新全局投注对象。
function update_bet_object(){
	var rb = g_currentBet.redBalls;
	var bb = g_currentBet.blueBalls;
	rb.splice(0, rb.length);
	bb.splice(0, bb.length);
	iterate_red_balls(function(ball){
		var ballId = getBallId(ball);
		if (ball.hasClass('red_ball')){
			rb.push(parseInt(ballId));
		}
	});
	iterate_blue_balls(function(ball){
		var ballId = getBallId(ball);
		if (ball.hasClass('blue_ball')){
			bb.push(parseInt(ballId));
		}
	});
	g_currentBet.resolve();
}

// 计算有多少红球被选中了
function count_red_balls(){
   var num = 0;
   for(var i=1; i<=33; i++){
    var redball_id = "rb"+i;
    if ($("#"+redball_id).hasClass('red_ball')){
       num++;
    }
  }
log('红球:'+num);
  return num;
}

// 计算有多少蓝球被选中了
function count_blue_balls(){
   var num = 0;
   for(var i=1; i<=16; i++){
    var ball_id = "bb"+i;
    if ($("#"+ball_id).hasClass('blue_ball')){
       num++;
    }
  }
  return num;
}

// 添加"机选红球"处理方法
function init_random_sel_handler(){
	$('#random_sel_red_btn').click(function(){
		// 机选红球
		clear_red_balls();
		var red_random_num = $('#random_red_select_id').val();
		var sel_num = parseInt(red_random_num);
		while(count_red_balls()<sel_num){
			var idx = Math.ceil(Math.random()*33);
			var ball = $('#'+'rb'+idx);
			ball.removeClass('red_ball');
			ball.addClass('red_ball');
		}
		update_sel_prompt();
	});
	$('#random_sel_blue_btn').click(function(){
		// 机选蓝球
		clear_blue_balls();
		var blue_random_num = $('#random_blue_select_id').val();
		var sel_num = parseInt(blue_random_num);
		while(count_blue_balls()<sel_num){
			var idx = Math.ceil(Math.random()*16);
			var ball = $('#'+'bb'+idx);
			ball.removeClass('blue_ball');
			ball.addClass('blue_ball');
		}
		update_sel_prompt();
	});
}

// 清除红球的被选中状态
function clear_red_balls(){
	iterate_red_balls(function(ball){
		ball.removeClass('red_ball');
	});
}

// 清除蓝球的被选中状态
function clear_blue_balls(){
	iterate_blue_balls(function(ball){
		ball.removeClass('blue_ball');
	});
}

function init_clear_nums(){
	$('#prompt a').click(function(){
		clear_red_balls();
		clear_blue_balls();
		update_sel_prompt();
		return false;
	});
}

// 添加投注到列表的 handler
function init_add_bet_list(){
	$('#add_list_btn').click(function(){
		update_bet_object();
		// 只加入有效选择投注，无效投注不处理
		if (g_currentBet.notes == 0){
			return;
		}
		g_betList.unshift(g_currentBet);
		g_currentBet = new SSQBet();
		update_bet_list_ui();
		clear_red_balls();
		clear_blue_balls();
		update_sel_prompt();
		update_scheme_prompt();
	});
}

// 渲染 g_betList 的内容
function update_bet_list_ui(){
	var rows = create_rows();
	$('#scheme_list').html(rows);
	// 鼠标悬浮效果
	$('.bet_scheme_row').hover(function(){$(this).css('backgroundColor','#E4F4FC');}, 
		function(){$(this).css('backgroundColor', 'white');});
	// 添加删除小图标 handler
	$('.bet_scheme_row span img').click(delete_row);
	init_bet_click_handler();
}

// 胆拖页面，用 g_betList 渲染投注方案列表框
function dt_update_bet_list_ui(){
	var rows = dt_create_rows();
	$('#scheme_list').html(rows);
	// 鼠标悬浮效果
	$('.bet_scheme_row').hover(function(){$(this).css('backgroundColor','#E4F4FC');}, 
		function(){$(this).css('backgroundColor', 'white');});
	// 添加删除小图标 handler
	$('.bet_scheme_row span img').click(dt_delete_row);
	dt_init_bet_click_handler();
}

function create_rows(){
	var rows = '';
	log('bet list: ' + g_betList.length);
	for(var i=0; i<g_betList.length; i++){
		var bet = g_betList[i];
		rows +=
		'<div class="bet_scheme_row">'+
		'	<span><img title="删除" src="http://trade.davcai.com/df/images/delete.gif" betidx="'+i+'"></span>'+
		'	<div class="scheme_row_content">'+
		'	<font color="#E1401E">红</font>：'+ format_nums(bet.redBalls).join(' ') +
		' + <font color="#4F87E3">蓝</font>：'+ format_nums(bet.blueBalls).join(' ')+' ['+bet.notes+'注 '+bet.money+'元]'+
		'	</div>'+
		'</div>';
	}
	return rows;
}

function dt_create_rows(){
	var rows = '';
	log('bet list: ' + g_betList.length);
	for(var i=0; i<g_betList.length; i++){
		var bet = g_betList[i];
		var danStr = '';
		if (bet.redDanBalls.length > 0){
			danStr = '('+ format_nums(bet.redDanBalls).join(' ') +')';
		}
		rows +=
		'<div class="bet_scheme_row">'+
		'	<span><img title="删除" src="http://trade.davcai.com/df/images/delete.gif" betidx="'+i+'"></span>'+
		'	<div class="scheme_row_content">'+
		'	<font color="#E1401E">红</font>：'+ danStr + format_nums(bet.redTuoBalls).join(' ') +
		' + <font color="#4F87E3">蓝</font>：'+ format_nums(bet.blueBalls).join(' ')+' ['+bet.notes+'注 '+bet.money+'元]'+
		'	</div>'+
		'</div>';
	}
	return rows;
}

// 格式化数字为01,02形式。输入、输出为数组。
function format_nums(nums){
	var fn = [];
	for(var i=0; i<nums.length; i++){
		var n = format_one_num(nums[i]);
		fn.push(n);
	}
	return fn;
}

function format_one_num(num){
	var n = num < 10 ? '0'+num : num;
	return n;
}

function delete_row(){
	var idx = parseInt($(this).attr('betidx'));
	g_betList.splice(idx,1);
	update_bet_list_ui();
	update_scheme_prompt();
}

function dt_delete_row(){
	var idx = parseInt($(this).attr('betidx'));
	g_betList.splice(idx,1);
	dt_update_bet_list_ui();
	update_scheme_prompt();
}

// 随机投注按钮处理
function init_random_bet(){
	$('.random_white_btn').click(function(){
		var txt = $(this).html();
		var match = /机选(.+)注/.exec(txt);
		if (!match) return;
		var cnNum = match[1];
		switch(cnNum){
			case '一': add_random_bet(1); break;
			case '五': add_random_bet(5); break;
			case '十': add_random_bet(10); break;
			default: break;
		}
		update_scheme_prompt();
	});
}

function dt_init_random_bet(){
	$('.random_white_btn').click(function(){
		var txt = $(this).html();
		var match = /机选(.+)注/.exec(txt);
		if (!match) return;
		var cnNum = match[1];
		switch(cnNum){
			case '一': dt_add_random_bet(1); break;
			case '五': dt_add_random_bet(5); break;
			case '十': dt_add_random_bet(10); break;
			default: break;
		}
		update_scheme_prompt();
	});
}

function add_random_bet(num){
	for(var i=0; i<num; i++){
		var bet = new SSQBet();
		bet.redBalls = random_balls(33, 6);
		bet.blueBalls = random_balls(13, 1);
		bet.resolve();
		g_betList.push(bet);
	}
	update_bet_list_ui();
}

function dt_add_random_bet(num){
	for(var i=0; i<num; i++){
		var bet = new SSQBetDT();
		bet.redTuoBalls = random_balls(33, 6);
		bet.blueBalls = random_balls(13, 1);
		bet.resolve();
		g_betList.push(bet);
	}
	dt_update_bet_list_ui();
}

// 随机生成 count 个数，数值范围在[0, max]之间
function random_balls(max, count){
	var ret = [];
	while(ret.length<count){
		var idx = Math.ceil(Math.random()*max);
		if (array_index(ret, idx) < 0){
			ret.push(idx);
		}
	}
	return ret.sort(sortNumber);
}

function sortNumber(a,b) {
	return a - b;
}

// 搜索 array 是否包含 target, 返回 target 的下标。
function array_index(array, target){
	for(var i=0; i<array.length; i++){
		if(array[i]==target) return i;
	}
	return -1;
}

// 清空列表
function init_clear_list(){
	$('.clear_list_btn').click(function(){
		g_betList.splice(0, g_betList.length);
		update_bet_list_ui();
		update_scheme_prompt();
	});
}

function dt_init_clear_list(){
	$('.clear_list_btn').click(function(){
		g_betList.splice(0, g_betList.length);
		dt_update_bet_list_ui();
		update_scheme_prompt();
	});
}

// 投注倍数，方案信息提示内容
function init_bet_multiple(){
	$('#minus_btn').click(function(){
		var m = getSchemeMultiple();
		if (m>1) {
			m -= 1;
			$('#multiple_text').val(m);
			update_scheme_prompt();
		}
	});
	$('#plus_btn').click(function(){
		var m = getSchemeMultiple();
		if (m<99) {
			m += 1;
			$('#multiple_text').val(m);
			update_scheme_prompt();
		}
	});
	// 限制倍数 input text field 只能输入99以下的数字
	$('#multiple_text').keyup(function(event){
		log('text: '+$(this).val());
		var m = $(this).val();
		if (/^\d+$/.test(m) ){
			var num = parseInt(m);
			if (num>99) $(this).val(99);
			if (num<=0) $(this).val(1);
		}else{
			$(this).val(1);
		}
		update_scheme_prompt();
	});
}

// 更新方案注数、金额信息
function update_scheme_prompt(){
	// 累加所有投注
	var totalNotes = 0;
	for (var i=0; i<g_betList.length; i++){
		var bet = g_betList[i];
		totalNotes += bet.notes;
	}
	var multiple = getSchemeMultiple();
	var totalMoney = multiple * totalNotes * 2;
	// 更新UI
	$('#scheme_notes').html(totalNotes);
	$('#scheme_money').html(totalMoney);
}

// 获取倍数值
function getSchemeMultiple(){
	return parseInt($('#multiple_text').val());
}

// 投注项点击事件处理函数
function init_bet_click_handler(){
	$('.bet_scheme_row').click(function(){
		var index = $(this).find('span img').attr('betidx');
		var bet = g_betList[parseInt(index)];
		select_balls_by(bet);
	});
}

// 胆拖页面，投注项点击事件处理函数
function dt_init_bet_click_handler(){
	$('.bet_scheme_row').click(function(){
		var index = $(this).find('span img').attr('betidx');
		var bet = g_betList[parseInt(index)];
		dt_select_balls_by(bet);
	});
}

// 用一个投注对象设置号码选择块的信息。
function select_balls_by(bet){
	clear_red_balls();
	clear_blue_balls();
	select_balls(bet.redBalls, '#rb', 'red_ball');
	select_balls(bet.blueBalls, '#bb', 'blue_ball');
	update_sel_prompt();
}

// 胆拖页面，用一个投注对象设置号码选择块的信息。
function dt_select_balls_by(bet){
	clear_red_dan_balls();
	clear_red_tuo_balls();
	clear_blue_balls();
	select_balls(bet.redDanBalls, '#dan_balls #rb', 'red_ball');
	select_balls(bet.redTuoBalls, '#tuo_balls #tuo_rb', 'red_ball');
	select_balls(bet.blueBalls, '#bb', 'blue_ball');
	dt_update_sel_prompt();
}

// 帮助方法,更新号码选择区球的选中状态
function select_balls(balls, ballIdPrefix, ballClass){
	for(var i=0; i<balls.length; i++){
		$(ballIdPrefix+balls[i]).addClass(ballClass);
	}
}

// 验证投注方案，提交投注表单信息
function init_bet_confirm(){
	$('#confirm_buy_form input:image').click(function(){
		if(!validate_scheme()){
			alert("请先选择投注号码！");
			return false;
		}
		insert_form_fields();
		$('#confirm_buy_form').submit();
	});
}

// 验证方案信息是否合法
function validate_scheme(){
	return g_betList.length > 0;
}

// 序列化form提交需要的hidden fields内容
function insert_form_fields(){
	$('#confirm_buy_form input[name=scheme]').val(encode_scheme());
	$('#confirm_buy_form input[name=issue]').val(g_issueInfo.issueNumber);
	$('#confirm_buy_form input[name=multiple]').val($('#multiple_text').val());
	//$('#confirm_buy_form input[name=buy_type]').val($('.row_content input[name=buy_type]:checked').val());
	//$('#confirm_buy_form input[name=secret_type]').val($('.row_content input[name=secret_type]:checked').val());
}

// 编码投注方案内容。
// 双色球前后台参数定义:
// 单式投注:  SSQ_DS  格式：01,02,16,19,27,31|01; 04,08,16,19,27,31|16;
// 复式投注:  SSQ_FS  格式：01,02,03,16,19,21,23,25,33|01,09
// 胆拖投注:  SSQ_DT  格式：01,02,03@04,05,06,07,08|01,09, 胆码最少1个,最多5个,拖码最多20个,必须是复式方案
function encode_scheme(){
	var encoded = "";
	for(var i=0; i<g_betList.length; i++){
		var bet = g_betList[i];
		if (bet.type == 'dantuo'){
			var dan = '';
			if (bet.redDanBalls.length>0){
				dan = format_nums(bet.redDanBalls).join(',');
				if(bet.redDanBalls.length+bet.redTuoBalls.length==6){
					dan += ",";	// 胆加拖只有6个数字，做成单式投注
				}else{
					dan += "@";
				}
			}
			var tuo = format_nums(bet.redTuoBalls).join(',');
			var bb = format_nums(bet.blueBalls).join(',');
			encoded += dan + tuo + '|' + bb + ';';
		}else{
			var rb = format_nums(bet.redBalls);
			var bb = format_nums(bet.blueBalls);
			encoded += rb.join(',') + '|' + bb.join(',') + ';';
		}
	}
	return encoded;
}


// ========= 双色球投注类 =========
function SSQBet(){
	this.type = 'normal';	// 普通投注
	// member properties
	this.redBalls = new Array();
	this.blueBalls = new Array();
	this.notes = 0;	// 注数
	this.money = 0; // 投注的总金额

	// member functions
	
	// 计算注数和钱数
	this.resolve = function(){
		var redBallNum = this.redBalls.length;
		var blueBallNum = this.blueBalls.length;
		log('红球:'+this.redBalls);
		log('蓝球:'+this.blueBalls);
		this.notes = combination(redBallNum, 6) * blueBallNum;
		this.money = this.notes * 2;	// 一注2元
	};
}

// 更新倒计时信息
function updateCountDownInfo(){
	var target = parseLocalTime(g_issueInfo.stopTimeForUser);
	var countDownDays = distanceTo(target, DISTANCE_DAY);
	var countDownTime = distanceToIgnoreDays(target);
	$('#remain_time_box').html(countDownDays+"天 "+
		format_one_num(countDownTime.getHours())+":"+
		format_one_num(countDownTime.getMinutes())+":"+
		format_one_num(countDownTime.getSeconds()));
}

// 倒计时 timer 函数
function countDownTimeout(){
	updateCountDownInfo();
	g_countDownTimer = setTimeout("countDownTimeout()", 1000);
}

// ========== 工具方法 ==========
//
// 计算组合数，从m中取n个数的组合数。
function combination(m, n){
	if (n<=0 || m<=0 || m<n) return 0;
	if (n > m - n) {
		n = m - n;
	}
	var cbn = 1;
	for (var i = m; i >= m - n + 1; i--) {
		cbn = cbn * i;
	}
	for (var i = n; i > 1; i--) {
		cbn = cbn/i;
	}
	return cbn;
}

// 调整时区
// timezoneOffset 要减掉的分钟, 如:GMT+0800 (中国标准时间) 是-480
function adjustTimeZone(time, timezoneOffset) {
	var t = time.getTime() + timezoneOffset*60*1000;
	return new Date(t);
}

// 用GMT+0800 (中国标准时间) 解析服务器传来的时间串 time_str.
function parseLocalTime(time_str) {
	if (! /.*\+\d{4}/.test(time_str)){
		time_str += "+0000";
	}
	var d = new Date(time_str);
	if (!d){
		d = parseDateForIE(time_str);
	}
	return adjustTimeZone(d, -480);
}

// 计算当前时间距离指定时间还有几天
// targetTime: 目标时间
// unit: 单位，可以是 DISTANCE_DAY, DISTANCE_MINUTE
function distanceTo(targetTime, unit){
	var now = new Date();
	var diff = Math.floor((targetTime - now.getTime() ) / unit);
	return diff;
}

// 计算当前时间距离指定 targetTime 时间还有多少秒，舍弃整数天。
// return: Date 对象，它的时、分、秒是距离target的时间
function distanceToIgnoreDays(targetTime, unit) {
	var now = new Date();
	var origin = new Date("1970/1/1 00:00:00");
	var diffTime = targetTime - now.getTime();
	var distance = new Date(origin.getTime()+diffTime);
	return distance;
}


var g_sysSecond;
var g_interValObj;

function startCountDown(countDownSeconds){
    g_sysSecond = parseInt(countDownSeconds); //这里获取倒计时的起始时间 
    g_interValObj = window.setInterval(setRemainTime, 1000); //间隔函数，1秒执行 
}

//将时间减去1秒，计算天、时、分、秒 
function setRemainTime() {
    if (g_sysSecond > 0) {
	g_sysSecond = g_sysSecond - 1;
	var second = Math.floor(g_sysSecond % 60);             // 计算秒     
	var minute = Math.floor((g_sysSecond / 60) % 60);      //计算分 
	var hour = Math.floor((g_sysSecond / 3600) % 24);      //计算小时 
	var day = Math.floor((g_sysSecond / 3600) / 24);       //计算天 
	$('#remain_time_box').html(day+"天 "+
		format_one_num(hour)+":"+
		format_one_num(minute)+":"+
		format_one_num(second));
    } else { //剩余时间小于或等于0的时候，就停止间隔函数 
	window.clearInterval(g_interValObj);
    }
} 


// ============== ajax ==============

// 获取期信息
// return IssueInfo obj.
function aj_ssq_issue_info(){
	$.ajax({
		url: 'http://trade.davcai.com/df/ssq/aj_issue.do?jsonp=?',
		cache: false,
		crossDomain: true,
		dataType: 'jsonp'
	}).done(function(data){
		log(data);
		if(!data.success){
			log("获取期信息失败！");
			return;
		}
		g_issueInfo = data.issue;
		countDown = data.countDownSeconds;
		$('#saling_text span').html(g_issueInfo.issueNumber);
		// 解析倒计时
		//updateCountDownInfo();
		//countDownTimeout();
		log('投注截止时间：'+g_issueInfo.stopTimeForUser);
		startCountDown(countDown);
		
		//构造双色球期信息内容
		compose_ssq_curr_issue_info(g_issueInfo.issueNumber);
	});
}

var compose_ssq_curr_issue_info = function(issueNumber) {
	var issueTpl = 
    '<li class="issue-ssq">' +
	    '<div class="sz-lottery-logo"><img src="images/lottery/ssq.png"/></div>' +
	    '<div id="saling_text" class="sz-lottery-curr">' +
			'双色球第<span class="red_text sz-higher-blue">{0}</span>期<br/>' +
			'距离截止投注还有：<span id="remain_time_box" class="sz-higher-red"></span>' +
		'</div>' +
		'<div class="pre-issue-info">' +
			'<span class="ssq-pre-issue">上期开奖号码：' +
			'</span>' +
			'<span>奖池滚存：<span class="sz-higher-red jackpot"></span></span>' +
			'<input type="button" id="sz_bet_btn" value="投注"/>' +
		'</div>' +
	'</li>';
	
	var issue = replacePlaceHolder(issueTpl, [issueNumber]);
	
	$("#match_sz").find("ul").append($(issue));
	$("#match_sz").find("#sz_bet_btn").click(function() {
		var ssqUrl = "http://trade.davcai.com/df/bet/ssq_general.html";
		document.location.href = ssqUrl;
	});
	aj_ssq_results($("#match_sz"));
};

// 获取双色球结果
function aj_ssq_results(wrapper){
	$.ajax({
		url: 'http://trade.davcai.com/df/ssq/aj_results.do?max=1&jsonp=?',
		cache: false,
		crossDomain: true,
		dataType: 'jsonp'
	}).done(function(results){
		if(results && results.length > 0) {
			var rs = results[0];
			var tpl = '<span class="sz-higher-red">{0}&nbsp;</span>' +
				'<span class="sz-higher-blue">{1}</span>'; 
			var preIssue = replacePlaceHolder(tpl, [rs.redBalls, rs.blueBall]);
			wrapper.find(".ssq-pre-issue").append($(preIssue));
			
			//奖池
			if(rs.jackpot) {
				var jackpot = addCommas(rs.jackpot);
				$(".jackpot").text(jackpot+"元");
			}
		}
	});
}

function addCommas(nStr) {
	nStr += '';
	x = nStr.split('.');
	x1 = x[0];
	x2 = x.length > 1 ? '.' + x[1] : '';
	var rgx = /(\d+)(\d{3})/;
	while (rgx.test(x1)) {
		x1 = x1.replace(rgx, '$1' + ',' + '$2');
	}
	return x1 + x2;
}

// 构造双色球结果行html.
// ssq_result 对应 SSQResult class.
function compose_result_row(ssq_result){
	var html = '<div class="board_row orange_text">'+
            '<span class="issue">'+ssq_result.shortIssueNum+'</span>'+
            '<span class="result"><span class="orange_text">'+ssq_result.redBalls+
	    ' <span class="blue_text">'+ssq_result.blueBall+'</span></span></span>'+
            '<span class="more_or_less">'+ssq_result.bigSmall+'</span>'+
            '<span class="sum">'+ssq_result.sum+'</span>'+
	'</div>';
	return html;
}

// =============== 胆拖投注 =================

// 胆拖区小球被点击时的事件响应
function dt_init_ball_click_handler(){
	// 红球胆区
	iterate_red_dan_balls(function(ball){
		ball.click(function(){
			var ball = $(this);
			ball.toggleClass('red_ball');
			dt_balls_change();
			if(g_currentBetDT.redDanBalls.length>5){
				ball.toggleClass('red_ball');
				dt_balls_change();
				alert("最多只能选择5个胆码");
				return;
			}
			dt_exclude(ball, 'tuo');
		});
	});
	// 红球拖区
	iterate_red_tuo_balls(function(ball){
		ball.click(function(){
			log('拖区被点击');
			var ball = $(this);
			ball.toggleClass('red_ball');
			dt_balls_change();
			if(g_currentBetDT.redTuoBalls.length>20){
				ball.toggleClass('red_ball');
				dt_balls_change();
				alert("最多只能选择20个拖码");
				return;
			}
			dt_exclude(ball, 'dan');
		});
	});
	// 蓝球
	iterate_blue_balls(function(ball){
		ball.click(function(){
			$(this).toggleClass('blue_ball');
			dt_balls_change();
		});
	}, "#blue_ball_dt #bb");
}

// 把 beExcludedType 中对应ball的序号的小球取消选中状态。
// beExcludedType 取值：dan,tuo 
function dt_exclude(ball, beExcludedType){
	var ballId = getBallId(ball);
	if (beExcludedType == 'dan'){
		log('dan excluded.'+ballId);
		$('#dan_balls #rb'+ballId).removeClass('red_ball');
	}else{
		log('tuo excluded.'+ballId);
		$('#tuo_balls #tuo_rb'+ballId).removeClass('red_ball');
	}
}

function iterate_red_dan_balls(fn_callback){
	for(var i=1; i<=33; i++){
		var ball_id = "#dan_balls #rb"+i;
		fn_callback($(ball_id));
	}
}

function iterate_red_tuo_balls(fn_callback){
	for(var i=1; i<=33; i++){
		var ball_id = "#tuo_balls #tuo_rb"+i;
		fn_callback($(ball_id));
	}
}

// 清空号码
function dt_init_clear_nums(){
	$('#prompt_dt a').click(function(){
		clear_red_dan_balls();
		clear_red_tuo_balls();
		clear_blue_balls();
		dt_balls_change();
		return false;
	});
}

function clear_red_dan_balls(){
	iterate_red_dan_balls(function(ball){
		ball.removeClass('red_ball');
	});
}

function clear_red_tuo_balls(){
	iterate_red_tuo_balls(function(ball){
		ball.removeClass('red_ball');
	});
}

// 确认选号按钮点击事件
function dt_init_add_bet_list(){
	$('#add_list_btn_dt').click(function(){
		dt_update_bet_object();
		// 只加入有效选择投注，无效投注不处理
		if (g_currentBetDT.notes == 0){
			return;
		}
		g_betList.unshift(g_currentBetDT);
		g_currentBetDT = new SSQBetDT();
		dt_update_bet_list_ui();
		clear_red_dan_balls();
		clear_red_tuo_balls();
		clear_blue_balls();
		dt_update_sel_prompt();
		update_scheme_prompt();
	});
}


// 用户点击了小球 
function dt_balls_change(){
	dt_update_bet_object();
	$('#prompt_dt>.red_text').html(g_currentBetDT.redDanBalls.length+g_currentBetDT.redTuoBalls.length);
	$('#prompt_dt>.blue_text').html(g_currentBetDT.blueBalls.length);
	// 计算注数
	$('#notes').html(g_currentBetDT.notes);
	$('#money').html(g_currentBetDT.money);
}

// 更新 g_currentBetDT
function dt_update_bet_object(){
	var danball = g_currentBetDT.redDanBalls;
	var tuoball = g_currentBetDT.redTuoBalls;
	var bb = g_currentBetDT.blueBalls;
	danball.splice(0, danball.length);
	tuoball.splice(0, tuoball.length);
	bb.splice(0, bb.length);
	iterate_red_dan_balls(function(ball){
		var ballId = getBallId(ball);
		if (ball.hasClass('red_ball')){
			danball.push(parseInt(ballId));
		}
	});
	iterate_red_tuo_balls(function(ball){
		var ballId = getBallId(ball);
		if (ball.hasClass('red_ball')){
			tuoball.push(parseInt(ballId));
		}
	});
	iterate_blue_balls(function(ball){
		var ballId = getBallId(ball);
		if (ball.hasClass('blue_ball')){
			bb.push(parseInt(ballId));
		}
	}, "#blue_ball_dt #bb");
	g_currentBetDT.resolve();
}

function getBallId(ballElement){
	var pattern = /.*(rb|bb)(\d+)/;
	var str = ballElement.attr('id');
	var result = pattern.exec(str);
	if (result){
		return result[2];
	}
	log('Can not get ball id! ' + str);
}

function count_red_dan_balls(){
   var num = 0;
   for(var i=1; i<=33; i++){
    var redball_id = "rb"+i;
    if ($("#dan_balls #"+redball_id).hasClass('red_ball')){
       num++;
    }
  }
  return num;
}

function count_red_tuo_balls(){
   var num = 0;
   for(var i=1; i<=33; i++){
    var redball_id = "tuo_rb"+i;
    if ($("#tuo_balls #"+redball_id).hasClass('red_ball')){
       num++;
    }
  }
  return num;
}

// ========= 双色球胆拖投注类 =========
function SSQBetDT(){
	this.type = 'dantuo';
	// member properties
	this.redDanBalls = new Array();
	this.redTuoBalls = new Array();
	this.blueBalls = new Array();
	this.notes = 0;	// 注数
	this.money = 0; // 投注的总金额

	// member functions
	
	// 计算注数和钱数
	this.resolve = function(){
		var danBallNum = this.redDanBalls.length;
		var tuoBallNum = this.redTuoBalls.length;
		var blueBallNum = this.blueBalls.length;
		this.notes = combination(tuoBallNum, 6-danBallNum) * blueBallNum;
		this.money = this.notes * 2;	// 一注2元
		log('resolving: ' + danBallNum + ", " + tuoBallNum + ", " + blueBallNum+ ", "+ this.notes);
	};
	this.countRedBalls = function(){
		return this.redDanBalls.length + this.redTuoBalls.length;
	};
	this.countBlueBalls = function(){
		return this.blueBalls.length;
	};
}

/*
 * vim: set ts=4
 */
