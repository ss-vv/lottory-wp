;(function($) {
	var matchScoreSub = function(lotteryId, matchs) {
		this.lotteryId = lotteryId;
		this.matchs = matchs;
		this.sub();
	}
	matchScoreSub.prototype = {
		matchType:function() {
			return $.matchType({'lotteryId':this.lotteryId});
		},
		channel:function() {
			return '/public/match/' + this.matchType() + "/";
		},
		sub:function() {
			var len = this.matchs.length;
			var channels = [];
			for(var i=0; i<len; i++) {
				var matchStatus = this.matchs[i].status;
				var matchId = this.matchs[i].matchId;
				var matchType = this.matchType();
				var channel = this.channel() + matchId;
				var element = {
					"matchId":matchId + '',
					"matchType":matchType,
					"channel":channel
				};
				//赛事已结束则不发起订阅请求
				if(matchStatus == 5) {
					continue;
				}
				$("body").trigger("subscribeNewChannel", element);
			}
		}
	}
	
	$.extend({
		subMatchScore:function(lotteryId, matchIdList) {
			new matchScoreSub(lotteryId, matchIdList);
		}
	});
})(window.jQuery);