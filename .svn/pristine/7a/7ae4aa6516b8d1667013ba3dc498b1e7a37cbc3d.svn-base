;(function($, undefined) {
	/**
	 * 方案视图
	 */
	var schemeView = function(element,options){
	    this.$el = $(element);
	    this.options = $.extend({}, $.fn.schemeView.defaults, options);
	    this.$schemeTmpl = $(this.options.tmpl);
	    this.initialized();
	}; 
	schemeView.prototype = {
	    initialized : function(){
	    	this.settings = {};
	    	$.extend(this.settings, LT);
	    	this.settings.delegateEvents(this);//继承events事件
	    	this.rend();
	    },
	    rend : function(){
	    	var betScheme = this.options.scheme;
	    	var matchs = betScheme.matchs;
	    	if(!betScheme || betScheme.id <= 0 || !matchs || matchs.length <= 0) {
	    		return;
	    	}
	    	
	    	var self = this;
	    	var betScheme = this.options.scheme;
	    	var showConcede = betScheme.showConcede;
	    	var playId = betScheme.playId;
	    	var betWidthRatio = '';
	    	var matchWidthRatio = '';
	    	if(playId.indexOf("05_ZC") != 0 &&
					playId.indexOf("10_LC") != 0) {
	    		if(showConcede != true) {
	    			betWidthRatio = 'bet-long match-long';
	    		} else {
	    			betWidthRatio = 'bet-short match-short';
	    		}
			} else {
				if(showConcede != true) {
					betWidthRatio = 'bet-min';
				}
			}
	    	
	    	var $scheme = $.mustache(this.$schemeTmpl.html(), {
	    		"scheme" : betScheme,
	    		"_pushMatchType":function() {
	    			var matchType = $.matchType({'lotteryId':betScheme.lotteryId});
	    			return matchType;
	    		},
	    		"_score":function() {
	    			if(this.status == 1) {
	    				return '未开赛';
	    			}
	    			return this.score;
	    		},
	    		"_resetScoreCss":function() {
	    			if(this.status == 1) {
	    				return '';
	    			}
	    			return true;
	    		},
	    		"_concedePoints":function() {//显示让球数
	    			if(!this.concedePoints || 
	    					'null' == this.concedePoints) {
	    				return '';
	    			}
	    			if(this.playId.indexOf("01_ZC") != 0 &&
	    					this.playId.indexOf("06_LC") != 0) {
	    				return '';
	    			}
	    			if(this.concedePoints > 0) {
	    				return "+" + this.concedePoints;
	    			}
	    			return this.concedePoints;
	    		},
	    		"_betContent" : function() {//对于保密方案，投注内容的显示
	    			if(!this.betCode) {
	    				return "保密";
	    			}
	    			return self.rendBetMatchContent(this).replace(/<br\/>/g,"\n");
	    		},
	    		"_betContentHtml" : function() {//对于保密方案，投注内容的显示
	    			if(!this.betCode) {
	    				return "保密";
	    			}
	    			return self.rendBetMatchContent(this);
	    		},
	    		"throughResult" : function() {//实单方案投注选项命中赛果
	    			if(this.matchWin == true) {
	    				if(playId.indexOf("05_ZC") != 0 &&
	    						playId.indexOf("10_LC") != 0 &&
	    						showConcede != true) {
	    					if(this.betOptions.length <= 10) {
	    						return 'red-through-max';
	    					} else if(this.betOptions.length <= 22) {
	    						return "red-through-middle";
	    					}
	    				}
	    				return "red-through";
	    			}
	    		},
	    		"winprize":function() {//判断是否显示中奖图标
	    			if(this.status == 5203 || this.status == 12) {
	    				return true;
	    			}
	    			return '';
	    		},
	    		"bravery":function() {//胆码角标显示
	    			if(this.seed == true) {
	    				return "dan";
	    			}
	    			return '';
	    		},
	    		//推荐不显示“过关，倍数，方案金额”
	    		"_realScheme":function() {
	    			if(this.realScheme == true) {
	    				return '';
	    			}
	    			return true;
	    		},
	    		"_showConcede":function() {//是否显示让球
	    			if(showConcede == true) {
	    				return '';
	    			}
	    			return 'true';
	    		},
	    		"isMixPlay":function() {
	    			if(playId.indexOf("05_ZC") == 0 ||
	    					playId.indexOf("10_LC") == 0) {
	    				return '';
	    			}
	    			return true;
	    		},
	    		"formatCss":function() {
	    			return betWidthRatio;
	    		},
	    		"_type":function() {
	    			if(this.type == 1) {return '';}
	    			return true;
	    		},
	    		"_floorRatio":function() {
	    			var result = (this.floorAmount*100)/this.totalAmount + "";
	    			if(result && result > 0) {
	    				if(result.length > 2) {
	    					result = new Number(result).toFixed(2);
	    				}
	    				result += "%";
	    			} else {
	    				result = "无";
	    			}
	    			return result;
	    		},
	    		switchHomeGuest:function() {
	    			if(this.lotteryId == "JCLQ") {
	    				return '客队VS主队';
	    			}
	    			return '主队VS客队';
	    		},
	    		weibotype:function (){
	    			return self.options.type;
	    		},
	    		realFollowersCountView:function (){
	    			var a = "";
	    			if(self.options.type==3){
	    				a="我要合买";
	    			}
	    			if(self.options.type==1){
	    				a="我要跟单";
	    			}
	    			if(self.options.realFollowersCount && self.options.realFollowersCount.length > 0){
						return a+'('+self.options.realFollowersCount.length+')';
					}
	    			return a;
	    		},
	    		srcWeiboUrl:function (){
	    			if(self.options.srcWeiboId){
	    				var type=self.options.type;
	    				return 'http://www.davcai.com/'+self.options.srcWeiboUserId+'/'+self.options.srcWeiboId +
	    					(type==1?"#follow":(type==3?"#groupbuy":""));
	    			} else {
	    				return 'javascript:void(0);';
	    			}
	    		}
	    	});
	    	$scheme = $($scheme);
	    	this.$el.append($scheme);
	    	//判断是否显示跟单按钮
	    	var now = new Date().Format("yyyy-MM-dd hh:mm:ss");
	    	if(betScheme && betScheme.offtime &&
					betScheme.offtime.replace("T"," ") <= now){
	    		var $td = $scheme.find(".bet-follow").closest("td");
	    		$td.prev().attr("colspan",4);
	    		$td.remove();
	    	}
	    	if(self.options.type==3&&betScheme.saleStatus != 0){//合买满员
	    		var $td = $scheme.find(".bet-follow").closest("td");
	    		$td.prev().attr("colspan",4);
	    		$td.remove();
	    	}
	    	new $.subMatchScore(betScheme.lotteryId, betScheme.matchs);
	    },
	    reset : function(option){
	        this.options = $.extend({},this.options,option);
	        this.rend();
	    },
	    events : {
	    	
	    },
	    /**
	     * 渲染每场比赛的投注内容
	     * 格式为从上倒下选项数量递增
	     * @param playMatch
	     * @return
	     */
	    rendBetMatchContent:function (playMatch){
	    	var self = this;
	    	var playIds = playMatch.playId.split(",");
	    	var options = playMatch.betOptions.split(",");
	    	var odds;
	    	if(playMatch.odds){
	    		 odds = playMatch.odds.split(",");
	    	} else {
	    		odds = [];
	    		for (var i = 0; i < playIds.length-1; i++) {
	    			odds[i]="";
	    		}
	    		odds[playIds.length]="";
	    	}
	    	var playCountMap = new Map();
	    	var playOptionViewMap = new Map();
	    	for (var i = 0; i < playIds.length; i++) {
	    		var pId = playIds[i];
	    		var opt = options[i];
	    		var odd = odds[i];
				if(null == playOptionViewMap.get(pId)){
					playOptionViewMap.put(pId, "");
					playCountMap.put(pId, 1);
					playOptionViewMap.put(pId, playOptionViewMap.get(pId)+self.getOptionViewBybet(pId, opt, odd,playMatch));
				} else {
					var count = playCountMap.get(pId);
					count = count + 1;
					playCountMap.put(pId, count);
					playOptionViewMap.put(pId, playOptionViewMap.get(pId)+","+self.getOptionViewBybet(pId, opt, odd,playMatch));
				}
			}
	    	var retMap = SortMapByValue(playCountMap);
	    	var content = "";
	    	var keys = retMap.keys();
	    	for (var i=0;i<keys.length;i++) {
	    		content+=playOptionViewMap.get(keys[i]);
	    		content+="<br/>";
			}
	    	return content;
	    },
	    getOptionViewBybet:function (pId,opt,odd,playMatch){
	    	var concedePoints=playMatch.concedePoints;
	    	var halfscore=playMatch.score1;
	    	var score=playMatch.score;
	    	var status=playMatch.status;
	    	var defaultScore=playMatch.defaultScore;
	    	var isPass = false;
	    	if(score){
	    		isPass = this.calculateIsPass(pId,opt,score,halfscore,concedePoints);
	    	}
	    	var returnVal="";
	    	if(-1 != pId.indexOf('01_ZC') 
	    			|| -1 != pId.indexOf('06_LC')
	    			|| -1 != pId.indexOf('09_LC')){
	    		var a = "";
	    		if(-1 != pId.indexOf('06_LC')){
	    			a = parseFloat(defaultScore);
	    		} else {
	    			a = parseFloat(concedePoints);
	    		}
	    		returnVal = (a > 0 ? "+"+a:a) + opt + (odd && odd!='null' ? ("@" + odd):'');
	    	} else {
	    		returnVal = opt + (odd && odd!='null' ? ("@" + odd):'');
	    	}
	    	if(isPass && status==5){
	    		return '<i class="color-red">'+ returnVal + '</i>';
	    	} else {
	    		return returnVal;
	    	}
	    },
	    calculateIsPass:function (pId,opt,score,halfscore,concedePoints){
	    	var isPass = false;
	    	var getSpfOptViewByScore = function (score1,score2){
	    		if(score1 > score2){
	    			return "胜";
	    		} else if(score1 == score2){
	    			return "平";
		    	} else {
		    		return "负";
		    	}
	    	};
	    	var scores = score.split(":");
	    	scores[0]=parseInt(scores[0]);
	    	scores[1]=parseInt(scores[1]);
	    	var halfscores = halfscore.split(":");
	    	halfscores[0]=parseInt(halfscores[0]);
	    	halfscores[1]=parseInt(halfscores[1]);
	    	if(-1 != pId.indexOf('01_ZC') || -1 != pId.indexOf('80_ZC')
	    			|| -1 != pId.indexOf('91_BJDC_SPF')){
	    		if(-1 != pId.indexOf('01_ZC') || -1 != pId.indexOf('91_BJDC_SPF')){
	    			scores[0]=scores[0]+parseInt(concedePoints);
	    		} else {
	    			scores[0]=scores[0];
	    		}
    			if(getSpfOptViewByScore(scores[0],scores[1]) == opt){
    				isPass=true;
    			}
	    	} else if(-1 != pId.indexOf('02_ZC') || -1 != pId.indexOf('94_BJDC_BF')){//比分
	    		if(opt==score){
	    			isPass = true;
	    		}
	    	} else if(-1 != pId.indexOf('03_ZC') || -1 != pId.indexOf('92_BJDC_JQS')){//进球数
	    		if(opt.charAt(0)==(scores[0]+scores[1])){
	    			isPass = true;
	    		}
	    	} else if(-1 != pId.indexOf('04_ZC')  || -1 != pId.indexOf('95_BJDC_BQC')){//半全场
	    		var val = getSpfOptViewByScore(halfscores[0],halfscores[1])+
	    				  getSpfOptViewByScore(scores[0],scores[1]);
	    		if(opt==val){
	    			isPass=true;
	    		}
	    	} else if(-1 != pId.indexOf('06_LC') ||
	    				-1 != pId.indexOf('07_LC')){
	    		var preFix = "";
	    		if(-1 != pId.indexOf('06_LC')){
	    			scores[1]=scores[1]+parseFloat(concedePoints);
	    			preFix = "让分";
	    		}
	    		if(opt == (preFix+(scores[1]>scores[0]?"主胜":"主负"))){
	    			isPass=true;
	    		}
	    	} else if(-1 != pId.indexOf('08_LC')){
	    		var val = scores[1]>scores[0]?"主胜":"客胜";
	    		var difference = Math.abs(scores[0]-scores[1]);
	    		var pre = parseInt(difference - difference%5 + 1);
	    		var next = pre + 4;
	    		var result = val+(next>=26?"26+":(pre+"-"+next))+"分";
	    		if(opt==result){
	    			isPass=true;
	    		}
	    	} else if(-1 != pId.indexOf('09_LC')){
	    		if(opt == ((scores[0]+scores[1]) > parseFloat(concedePoints)?"大":"小")){
	    			isPass=true;
	    		}
	    	}
	    	return isPass;
	    }
	};
  
	$.fn.schemeView = function (option) {
		return this.each(function(input_field) {
			var $this = $(this), data = $this.data('schemeView');
		    if (!data) {
		    	$this.data('schemeView', (data = new schemeView(this, option)));
		    } else {
		    	data.reset(option);
		    }
		    //option 是方法名
		    if (typeof option == 'string'){
		    	if(!data[option]){
		    		console.log( 'Method ' + option + ' does not exist on jQuery.schemeView' );
		    	} else {
		    		data[option]();
		    	}
		    }
		});
	};
	$.fn.schemeView.defaults = {
		scheme : null,
	};
})(window.jQuery);
