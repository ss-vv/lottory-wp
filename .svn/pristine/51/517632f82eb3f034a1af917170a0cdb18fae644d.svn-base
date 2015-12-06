

(function($){
	$.fn.myScroll = function(options){
		//默认配置
		var defaults = {
			speed:40,  //滚动速度,值越大速度越慢
			rowHeight:24 //每行的高度
		};
		
		var opts = $.extend({}, defaults, options),intId = [];
		
		function marquee(obj, step){
			obj.find("ul").animate({
				marginTop: '-=1'
			},0,function(){
				var s = Math.abs(parseInt($(this).css("margin-top")));
				if(s >= step){
					$(this).find("li").slice(0, 1).appendTo($(this));
					$(this).css("margin-top", 0);
				}
			});
		}
		this.each(function(i){
			var sh = opts["rowHeight"],speed = opts["speed"],_this = $(this);
			intId[i] = setInterval(function(){
				if(_this.find("ul").height()<=_this.height()){
					clearInterval(intId[i]);
				}else{
					marquee(_this, sh);
				}
			}, speed);
			_this.hover(function(){
				clearInterval(intId[i]);
			},function(){
				intId[i] = setInterval(function(){
					if(_this.find("ul").height()<=_this.height()){
						clearInterval(intId[i]);
					}else{
						marquee(_this, sh);
					}
				}, speed);
			});
		});
	}
})(jQuery);

$(function(){
	var winTmpl =  '<div  style="margin-bottom: 25px;"><span class="winners-title">最新中奖</span>'
				  +'<label class="winner-more"><a href="http://www.davcai.com/winningList"  target="_blank">更多》</a></label>'
				  +'</div>'
				  +'<div style="margin-top: 25px;height: 220px;overflow: hidden;width: 470px;"  class="marquee"><ul height="206" width="460">'
				  +'	{{{usersHtml}}}'
				  +'</ul></div>';
	
	var userTmpl = '	<li class="winners-content">'
				  +'	 	<span class="winners-content-name">{{nickName}}</span>'
				  +'	 	<span class="winners-content-money">{{bonus}}<i>元</i></span>'
				  +'	 	<span class="winners-content-prize">已派奖</span>'
				  +'	 	<span class="winners-content-kind">{{lotteryName}}</span>'
				  +'	 	<span class="winners-content-scheme"><a href="http://www.davcai.com/{{weiboUserId}}/{{postId}}" target="_blank">查看方案</a></span>'
				  +'	</li>';
	var $div = $("#winners");
	
	var lotteryMap = {
		JCLQ:"竞彩篮球",
		JCZQ:"竞彩足球",
		CTZC:"传统足彩",
		SSQ:"双色球",
	};
	$.getJSON("/last_win_weibo",function (data){
		var usersHtml = "";
		for ( var i = 0; i < data.length; i++) {
			if(data[i].betScheme){
				var afterTaxBonus = data[i].betScheme.afterTaxBonus;
				var nickName = data[i].user.nickName;
				var lotteryId = data[i].betScheme.lotteryId;
				var lotteryName = lotteryMap[lotteryId];
				var weiboUserId = data[i].user.weiboUserId;
				var postId = data[i].id;
				usersHtml += $.mustache(userTmpl,{
					nickName:nickName,
					bonus:afterTaxBonus,
					lotteryName:lotteryName,
					weiboUserId:weiboUserId,
					postId:postId
				});
			}
		}
		var winHtml = $.mustache(winTmpl,{
			usersHtml:usersHtml
		});
		$div.html(winHtml);
		$div.myScroll({
			speed:40,
			rowHeight:35
		});
	});
});
