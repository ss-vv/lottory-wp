$(function(){
    $.getJSON('http://login.davcai.com/loadmsg.do?jsonp=?', function(json){
        if(json.success && json.data.id > 0){
            var html = '<div class="fl">欢迎您，' + json.data.username + '&nbsp;</div>' +
		                '<div class="fl">' +
		                '  <a href="http://trade.davcai.com/ac/balance.do">我的账户</a><cite class="line"> | </cite>' +
		                '  <a href="http://trade.davcai.com/ac/recharge.do">充值</a><cite class="line"> | </cite>' +
		                '  <a href="http://trade.davcai.com/ac/withdraw.do">提现</a><cite class="line"> | </cite>' +
		                '  <a href="http://trade.davcai.com/ac/record/bet.do">我的投注</a><cite class="line"> | </cite>' +
		                 ' <a href="http://login.davcai.com/sso/logout.do">退出</a>' +
		                '</div>';
            $('#_top_ubar').html(html);
        }
    });
});