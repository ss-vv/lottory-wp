;(function($) {
	var chanelType="match_odds";
	function fixFloatPoiont(num){
		var s = (num+"");
		var prefixArr = ["00","0",""];
		var index = s.indexOf(".");
		if(index != -1){
			s+=prefixArr[s.substring(index + 1).length];
		} else {
			s+=".00";
		}
		return s;
	}
	var rendUpOrDownOdds=function ($odds,data){
		for (var i = 0; i < $odds.length; i++) {
			var $odd = $($odds[i]);
			var curodd = 0;
			try {
				curodd = parseFloat($odd.text());
			} catch (e){
				curodd = 0;
			}
			var newOddsArr=data.split(",");
			var newOdd = parseFloat(newOddsArr[i]);
			if(newOdd > curodd){
				$odd.addClass('color-red');
				$odd.removeClass('color-green');
				$odd.text(fixFloatPoiont(newOdd));
			} else if(newOdd < curodd){
				$odd.addClass('color-green');
				$odd.removeClass('color-red');
				$odd.text(fixFloatPoiont(newOdd));
			} else {
				$odd.removeClass('color-green');
				$odd.removeClass('color-red');
				$odd.text(fixFloatPoiont(newOdd));
			}
		}
	}
	var rendOdd = function (matchOdd,lotteryId){
    	if(matchOdd && matchOdd.matchId && matchOdd.corpId && matchOdd.oddsType && matchOdd.data){
    		//将数据更新到ALL_ODDS_DATA
    		if(!ALL_ODDS_DATA["matchId"+matchOdd.matchId]){
    			ALL_ODDS_DATA["matchId"+matchOdd.matchId]={};
    		}
    		if(!ALL_ODDS_DATA["matchId"+matchOdd.matchId]["corpId"+matchOdd.corpId]){
    			ALL_ODDS_DATA["matchId"+matchOdd.matchId]["corpId"+matchOdd.corpId]={};
    		}
    		if(!ALL_ODDS_DATA["matchId"+matchOdd.matchId]["corpId"+matchOdd.corpId][matchOdd.oddsType]){
    			ALL_ODDS_DATA["matchId"+matchOdd.matchId]["corpId"+matchOdd.corpId][matchOdd.oddsType]="";
    		}
    		ALL_ODDS_DATA["matchId"+matchOdd.matchId]["corpId"+matchOdd.corpId][matchOdd.oddsType]
    			= matchOdd.data + "," + ALL_ODDS_DATA["matchId"+matchOdd.matchId]["corpId"+matchOdd.corpId][matchOdd.oddsType];
    		if(!ALL_ODDS_DATA["matchId"+matchOdd.matchId]["corpId"+matchOdd.corpId][matchOdd.oddsType+"_time"]){
    			ALL_ODDS_DATA["matchId"+matchOdd.matchId]["corpId"+matchOdd.corpId][matchOdd.oddsType+"_time"]="";
    		}
    		ALL_ODDS_DATA["matchId"+matchOdd.matchId]["corpId"+matchOdd.corpId][matchOdd.oddsType+"_time"]
    			= matchOdd.time +","+ ALL_ODDS_DATA["matchId"+matchOdd.matchId]["corpId"+matchOdd.corpId][matchOdd.oddsType+"_time"];
    		//判断是否渲染
    		var currentCropId=$('.instant-index-list-content .current [crop-id]').attr('crop-id');
        	if(!currentCropId){
        		currentCropId=1;
        	}
        	if(parseInt(matchOdd.corpId) != parseInt(currentCropId)){
        		return;
    		} else {
    			var $oa;
    			if("JCLQ" == lotteryId){
    				$oa = $('[odd-match-id="'+matchOdd.matchId+'"]>span[type="'+matchOdd.oddsType+'"]');
    			} else {
    				var currentOddType = $('.instant-index-list-content .current [crop-id]').closest('li').attr("odd-type");
    				if(currentOddType != matchOdd.oddsType){
    					return ;
    				}
    				$oa = $('[odd-match-id="'+matchOdd.matchId+'"][odd-type="instant"]>span'); 
    			}
	    		rendUpOrDownOdds($oa,matchOdd.data);
    		}
    	}
	};
	//绑定新推送消息事件到使用推送消息的模块
	$("body").bind("JCZQ"+chanelType+"_handeler",function(e,matchOdd){
		if(matchOdd){
			rendOdd(matchOdd,"JCZQ");
		}
	});
	$("body").bind("JCLQ"+chanelType+"_handeler",function(e,matchOdd){
		if(matchOdd){
			rendOdd(matchOdd,"JCLQ");
		}
	});
	var MatchInstantOdds = function(lotteryId,method) {
		if(!lotteryId){
			return ;
		}
		this.lotteryId = lotteryId;
		if(method == "sub"){
			this.sub();
		} else {
			this.unsub();
		}
	}
	MatchInstantOdds.prototype = {
		channel:function() {
			return '/publish/odds/' + this.lotteryId;
		},
		sub:function() {
			var channel = this.channel();
			var element = {
				"channel":channel,
				"lotteryId":this.lotteryId,
				"handelerName":this.lotteryId+chanelType+"_handeler",
			};
			console.log("开始订阅:"+element.channel);
			$("body").trigger("subscribeNewChannel", element);
		},
		unsub:function() {
			var channel = this.channel();
			var element = {
				"channel":channel,
				"lotteryId":this.lotteryId,
				"handelerName":this.lotteryId+chanelType+"_handeler",
			};
			console.log("取消订阅:"+element.channel);
			$("body").trigger("unsubscribeChannel", element);
		}
	}
	
	$.extend({
		subMatchInstantOdds:function(lotteryId) {
			new MatchInstantOdds(lotteryId,"sub");
		},
		/**
		 * 如果有新的赔率类型和新公司参数，则在取消旧订阅后，添加新订阅
		 */
		unsubMatchInstantOdds:function(lotteryId) {
			new MatchInstantOdds(lotteryId,"unsub");
		}
	});
})(window.jQuery);