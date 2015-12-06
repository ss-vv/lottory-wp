<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title>2012来彩活动专题_竞彩推荐_五大联赛赛程_竞彩网上投注-竞彩足球-来彩网</title>
		<meta content="text/html; charset=utf-8" http-equiv="Content-Type" />
		<meta content="2012五大联赛，五大联赛投注，注册充值送10元，竞彩加奖，竞彩足球，竞彩篮球，来彩网，英超，德甲，西甲，意甲，法甲，竞彩，五大联赛赛程" name="keywords" />
		<meta content="来彩网迎五大联赛注册充值中奖活动火热来袭，注册充值送10元购彩金，竞彩足球奖上加奖，竞彩篮球奖上加奖,晒单跟单中奖有赏金" name="description" />
		<link href="http://www.davcai.com/style/layout.css" rel="stylesheet" type="text/css" />
		<link href="http://www.davcai.com/dactivity/css/style.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="http://www.davcai.com/js/jquery.js"></script><script type="text/javascript">

$(function(){
	//元月竞彩状元榜 start
	var showfollowList = $('#showfollow');
	var showfollowGrid = $('table[_type=sfgrid]', showfollowList);
	
	loadShowFollow('http://localhost/aj_pmshowtop.do?promotionId=29');
	function loadShowFollow(url){
		$.getJSON(url, function(data){
			if(data.success){
				var html = [];
				var hot = '';
				var last = '';
				html.push('<tr>' +
							'<th class="thl" style="width:35px;"><div class="inth">排行</div></th>' +
							'<th><div class="inth">用户名</div></th>' +
							'<th style="width:50px;"><div class="inth">晒单数量</div></th>' +
							'<th style="width:55px;"><div class="inth">中奖额</div></th>' +
							'<th class="thr" style="width:55px;"><div class="inth">返奖率</div></th>' +
						'</tr>');
				$.each(data.data, function(i, d){
					if(i < 3){
						hot = 'num hot';
					} else if(i>=3 &&i<=9) {
						hot = 'num1 hot1';
					} else {
						hot = 'num'; 
					}
					if(i == data.data.length -1){
						last = 'last';
					}
					html.push('<tr class="' + last + '">'+
							'<td><span class="' + hot + '">' + (i+1) + '</span></td>'+
							'<td><div class="intd"><a href="http://www.davcai.com/follow/oss.do?uid='+d.userId+'" title="'+d.fullname+'" target="_blank"><span class="c-1">' + d.username + '</span></a></div></td>'+
							'<td><div class="intd c-2">' + d.showNum + '</div></td>'+
							'<td><div class="intd c-2">' + d.winAmount + '</div></td>'+
							'<td class="tdr"><div class="intd c-2">' + d.returnRatio + '</div></td>'+
						'</tr>');
				});
				showfollowGrid.html(html.join(''));
			}
		});
	}
	//元月竞彩状元榜 end
	
	var wingRankPanel = $('#wingRank');
	var zcTabBtn = $('a[_op=zcTab]', wingRankPanel);
	var lcTabBtn = $('a[_op=lcTab]', wingRankPanel);
	var wingRankGrid = $('table[_type=grid]', wingRankPanel);
	
	loadRank('http://www.davcai.com/pm/aj_pmwingrant.do?promotionId=7&jsonp=?');
	
	//竞彩足球排行
	zcTabBtn.bind('click', function(){
		lcTabBtn.removeClass('cur');
		$(this).addClass('cur');
		loadRank('http://www.davcai.com/pm/aj_pmwingrant.do?promotionId=7&jsonp=?');
	});
	
	//竞彩篮球排行
	lcTabBtn.bind('click', function(){
		zcTabBtn.removeClass('cur');
		$(this).addClass('cur');
		loadRank('http://www.davcai.com/pm/aj_pmwingrant.do?promotionId=8&jsonp=?');
	});
	
	function loadRank(url){
		$.getJSON(url, function(data){
			if(data.success){
				var html = [];
				var hot = '';
				var last = '';
				html.push('<tr>' +
							'<th class="thl" style="width:40px;"><div class="inth">排名</div></th>' +
							'<th><div class="inth">用户名</div></th>' +
							'<th style="width:70px;"><div class="inth">累积奖金</div></th>' +
							'<th class="thr" style="width:70px;"><div class="inth">累积加奖</div></th>' +
						'</tr>');
				$.each(data.data, function(i, d){
					if(i < 3){
						hot = 'hot';
					} else {
						hot = '';
					}
					if(i == data.data.length -1){
						last = 'last';
					}
					html.push('<tr class="' + last + '">'+
							'<td class="tdl"><span class="num ' + hot + '">' + (i+1) + '</span></td>'+
							'<td><div class="intd"><a href="http://www.davcai.com/follow/oss.do?uid='+d.uid+'" target="_blank"><span class="c-1">' + d.username + '</span></a></div></td>'+
							'<td><div class="intd c-2">' + d.totalAfterTaxBonus + '</div></td>'+
							'<td class="tdr"><div class="intd c-2">' + d.totalGrant + '</div></td>'+
						'</tr>');
				});
				wingRankGrid.html(html.join(''));
			}
		});
		}




	var _timer = $('#timer');
	var _days = $('.days', _timer);
	var _hours = $('.hours', _timer);
	var _minutes = $('.minutes', _timer);
	var _seconds = $('.seconds', _timer);
	
	drawTime();
	setInterval(drawTime, 1000);
	
	function drawTime(){
		var start = new Date(2012, 6, 28, 4, 0, 0);
		var differ = start.getTime() - new Date().getTime();
		if(differ <= 0){
		   return false;
		}
		differ = Math.floor(differ / 1000);
		var s = differ % 60;
		differ = Math.floor(differ / 60);
		var m = differ % 60;
		differ = Math.floor(differ / 60);
		var h = differ % 24;
		var d = Math.floor(differ / 24);
		
		_days.text(_fill(d) + '天');
		_hours.text(_fill(h) + '小时');
		_minutes.text(_fill(m) + '分');
		_seconds.text(_fill(s) + '秒');
		
	}
	
	function _fill(num){
		if(num < 10){
			return '0' + num;
		}else {
			return num;
		}
		
	}


	
});
	
</script>	</head>
	<body>
<!-- top -->		<p>
			[#xh:template id="36" /]</p>
		<div class="wrap">
			<div class="inwrap">
				<div class="head">
					<div class="tips-1">
						 </div>
					<div class="tips-2">
						活动时间8月13日至10月31日 注册充值中奖全都送啦! 晒单跟单中奖有赏金!</div>
				</div>
				<div class="main">
					<div class="box">
						<div class="lyl lyw-1 fl">
							<div class="mod mod-2">
								<div class="mm">
									<div class="listbox textlist-2 textlist-2-2">
										<div class="row">
											<div class="title-1">
												新人大礼</div>
											<div class="ctt">
												<b>注册充值送10元彩金</b><br />
												<b>活动期间新注册的用户，首次充值满50元的赠送10元购彩金。</b></div>
										</div>
									</div>
									<div class="sharebar">
<!-- Baidu Button BEGIN -->										<div class="bdshare_t bds_tools get-codes-bdshare" id="bdshare" style="line-height: 1">
											<span class="bds_more">分享到：</span></div>
<script type="text/javascript" id="bdshare_js" data="type=tools" ></script><script type="text/javascript" id="bdshell_js"></script><script type="text/javascript">
						document.getElementById("bdshell_js").src = "http://bdimg.share.baidu.com/static/js/shell_v2.js?cdnversion=" + new Date().getHours();
</script><!-- Baidu Button END -->									</div>
									<div class="tipsbox">
										<div class="tt">
											参与规则说明：</div>
										<div class="ctt">
											活动时间：2012年9月1日00：00到2012年10月31日23:59<br />
											1、仅限活动期间注册的新用户参加，每个自然人只能注册一个来彩网帐号参加本次活动。<br />
											2、参加活动必须经过电子邮箱验证，电子邮箱验证未成功用户不能参加活动；每个真实有效的电子邮箱只能绑定一个<br />
											   账户参加活动。<br />
											3、参加活动必须经过短信验证，短信验证未成功用户不能参加活动；每个真实有效的手机号码只能绑定一个账户参加<br />
											   活动。<br />
											4、活动期间内注册成功并通过手机短信验证的新用户首次充值满50元的可获得10元购彩金。<br />
											5、以上赠送彩金会在绑定成功后15分钟内自动派发到会员赠款账户（每会员账号﹑每一人（相同身份证号）﹑每一电子<br />
											   邮箱地址﹑每一手机号及同一活动只能享有一次优惠），赠送的购彩金只能用于投注，不能提现。<br />
											6、如有作弊行为，将取消参加活动资格；情节严重的，将追究其法律责任。</div>
									</div>
									<div class="btnbox">
										<a class="btn-1" href="http://uc.davcai.com/reg.do"><span>免费注册</span></a><a class="btn-2" href="http://ac.davcai.com/recharge.do"><span>充值</span></a></div>
								</div>
							</div>
<!--活动2 begin-->							<div class="mod mod-2">
								<div class="mm">
									<div class="listbox textlist-2 textlist-2-2">
										<div class="row">
											<div class="title-2">
												晒单跟单送礼</div>
											<div class="ctt">
												<b>晒单跟单中奖有赏金</b></div>
										</div>
									</div>
									<div class="tipsbox">
										<div class="tt">
											参与规则说明：</div>
										<div class="ctt">
											1、活动时间：2012年9月30日到2012年10月31日。<br />
											2、活动期间，本站会员参与竞彩任意玩法，晒单（含合买并晒单计入发起人）和跟单中奖奖金累计计算，最终晒单和<br />
											    跟单中奖奖金累计总排名的前三名分别获得3888元、2888元、1888元的十月大红包；排名4至10位的会员，本站每人     送出288元红包；排名11至20位的会员，本站送出88元红包。<br />
											3、奖金计算方法：<br />
											    中奖总额=晒单方案中奖总额+跟单方案中奖总额<br />
											4、奖品等级：<br />
											    第1名：3888元红包<br />
											    第2名：2888元红包<br />
											    第3名：1888元红包<br />
											    第4-10名：各获得288元红包<br />
											    第11-20名：各获得88元红包<br />
											5、活动结束后，本站会在3个工作日内通过赠款方式将红包对应金额打入获奖用户账户中。<br />
											6、来彩网拥有对本次活动最终解释权。<br />
											注：“来彩老吴”“来彩平凡”“来彩穿越”不计入晒单跟单排行榜。</div>
									</div>
									<div class="btnbox">
										<a class="btn-1" href="http://uc.davcai.com/reg.do"><span>免费注册</span></a><a class="btn-2" href="http://ac.davcai.com/recharge.do"><span>充值</span></a></div>
								</div>
							</div>
<!--活动2 end-->							<div class="mod mod-2">
								<div class="mm">
									<div class="listbox textlist-2 textlist-2-2">
										<div class="row">
											<div class="title-3">
												中奖再加奖</div>
											<div class="ctt">
												<b>竞彩奖上再加奖　最高奖8.8%</b><br />
												<b>活动期间任意玩法的过关投注（除合买方案），单个方案税后中奖金额超过100元,即可获一定加奖</b></div>
										</div>
									</div>
									<div class="tipsbox">
										<div class="tt">
											活动规则：</div>
										<div class="ctt">
											活动时间：2012年8月13日00：00到2012年10月31日23:59</div>
										<div class="ctt">
											    单个方案中奖税后奖金 加奖<br />
											    100（含）-500        8<br />
											    500（含）-1000       28<br />
											    1000（含）-5000      88<br />
											    5000（含）-10000     288<br />
											    10000（含）-50000    888<br />
											    50000（含）-100000   2888<br />
											    10万（含）以上       8888<br />
											1、活动期间竞彩足球任意玩法、过关投注订单(以最终出票成功方案的投注发起时间为准)，单关无效。<br />
											2、单个方案税后中奖金额且税后中奖金额超过100元（含），即可获加奖！<br />
											3、用户每天享受的加奖方案个数不受限制.<br />
											4、加奖奖金于开奖次日14:00前派发。加奖奖金直接存放到用户的赠款账户中，只能用于投注，不能提现。</div>
									</div>
									<div class="btnbox">
										<a class="btn-3" href="http://www.davcai.com/bet/zc/189.html" target="_blank"><span>足球投注</span></a><a class="btn-4" href="http://www.davcai.com/bet/lc/193.html" target="_blank"><span>篮球投注</span></a></div>
								</div>
							</div>
						</div>
						<div class="lyr lyw-2 fr">
							<div class="mod mod-1">
								<div class="mh">
									<div class="tt">
										中奖喜报</div>
									<span class="more"><a href="http://www.davcai.com/zjxb/" target="-blank" title="">更多</a></span></div>
								<div class="mm">
									<div class="listbox textlist-1">
										[#xh:documents channelId="24" num="5"]
										<div 2="=0]" class="row" if="" style="color: #f72c05">
											<div class="tt">
												·[#xh:document field="createtime" dateformat="MM/dd"/] [#xh:document field="title" autolink="true" target="_blank" max="20"/]</div>
										</div>
										[/#xh:documents]</div>
									<div class="morebox">
										<a href="http://www.davcai.com/bet/zc/189.html" target="-blank" title="">购买竞彩</a></div>
								</div>
							</div>
<!--晒单跟单中奖排行榜begin-->							<div class="mod mod-1" id="showfollow">
								<div class="mh">
									<div class="tt">
										2012年10月晒单跟单中奖排行榜
                                                                        </div>
								</div>
								<div class="mm">
									<div class="tablebox tablebox-1">
										<table _type="sfgrid" border="0" cellpadding="0" cellspacing="0" class="table">
											<tbody>
											</tbody>
										</table>
									</div>
								</div>
							</div>
<!--晒单跟单中奖排行榜end-->							<div class="mod mod-1" id="wingRank">
								<div class="mh">
									<div class="tabh-1">
										<a _op="zcTab" class="cur" href="javascript:void(0);">竞彩足球中奖排行</a> <a _op="lcTab" href="javascript:void(0);">竞彩篮球中奖排行</a></div>
								</div>
								<div class="mm">
									<div class="tablebox tablebox-1">
										<table _type="grid" border="0" cellpadding="0" cellspacing="0" class="table">
											<tbody>
											</tbody>
										</table>
									</div>
								</div>
							</div>
							<div class="lyr lyw-2 fr">
<!--专家推荐begin-->								<div class="lyr lyw-2 fr">
									<div class="mod mod-1">
										<div class="mh">
											<div class="tt">
												专家推荐</div>
											<span class="more"><a href="http://www.davcai.com/rec/" target="-blank" title="">更多</a></span></div>
										<div class="mm">
											<div class="listbox textlist-1">
												[#xh:documents channelId="17" num="5"]
												<div 2="=0]" class="row" if="" style="color: #f72c05">
													<div class="tt">
														·[#xh:document field="createtime" dateformat="MM/dd"/] [#xh:document field="title" autolink="true" target="_blank" max="20"/]</div>
												</div>
												[/#xh:documents]</div>
										</div>
									</div>
<!--专家推荐end-->								</div>
							</div>
						</div>
					</div>
				</div>
				<div class="links">
					<div class="row mp-a">
						<span class="tt"><b>快速通道：</b></span>
						<div class="ctt">
							<a href="http://www.davcai.com" title="竞彩首页">竞彩首页</a> | <a href="http://www.davcai.com/bet/zc/188.html" title="竞彩足球">竞彩足球</a> | <a href="http://www.davcai.com/bet/lc/193.html" title="竞彩篮球">竞彩篮球</a> | <a href="http://www.davcai.com/follow/index.html" title="晒单跟单">晒单跟单</a> | <a href="http://www.davcai.com/groupbuy/index.html" title="合买大厅">合买大厅</a> | <a href="http://www.davcai.com/result/" title="赛果开奖">赛果开奖</a> | <a href="http://www.davcai.com/rec/" title="专家推荐">专家推荐</a> | <a href="http://www.davcai.com/knack/" title="投注技巧">投注技巧</a></div>
					</div>
				</div>
<!-- footer -->				<div class="foot">
					<div class="about">
						<a href="http://www.davcai.com/about/37.html" target="_blank">关于我们</a> | <a href="http://www.davcai.com/about/36.html" target="_blank">商务合作</a> | <a href="http://www.davcai.com/about/34.html" target="_blank">联系我们</a> | <a href="http://www.davcai.com/about/35.html" target="_blank">法律声明</a></div>
				</div>
			</div>
		</div>
	</body>
</html>


