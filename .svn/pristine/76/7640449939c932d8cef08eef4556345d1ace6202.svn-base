var ALL_ODDS_DATA;
var ALL_LEAGUES=new Map();
;(function($) {
	/**
	 * 渲染赛事列表
	 */
	var rendBetMatches = function(element,option){
	    this.$el = $(element); 
	    $.fn.rendBetMatches.defaults.matchDateTmpl=$("#match-date-tmpl").html();
	    $.fn.rendBetMatches.defaults.matchTmpl=$("#jczq-match-tmpl").html();
	    $.fn.rendBetMatches.defaults.mixBetTmpl=$("#mix-bet-tmpl").html();
	    $.fn.rendBetMatches.defaults.oddsOptionTmpl='<td {{{oddsStatus}}} _i="{{i}}" _j="{{j}}" _p="{{p}}"><a href="javascript:void(0);"><i class="color-green01">{{option}}</i><br/><i class="color-grey01">{{odds}}</i></a></td>';
	    $.fn.rendBetMatches.defaults.offSaleNMatchBtnTmpl='<input type="checkbox" id="checkbox-end" value="checkbox"><label for="checkbox-end">显示已截止 <i class="color-red">{{offSaleCount}}</i> 场比赛</label>';
	    this.options = $.extend({}, $.fn.rendBetMatches.defaults, option);
	    this.initialized();
	};
	rendBetMatches.prototype = {
	    initialized : function(){
	    	this.settings = {};
	    	this.rend();
	    },
	    rend : function(){
	    	var self = this;
	    	var rendSetup = function (data){
	    		if(data.length > 0){
	    			self.matchGroupIndex = 0;
	    			var offSaleMatchCount = 0;
    				for(var i=0;i<data.length;i++){
    					var preMatchCode;
    					if(i>0){
    						preMatchCode = data[i-1].code;
    					} else {
    						preMatchCode = null;
    					}
    					self.renderMatchDay(data[i],preMatchCode);
    					self.renderMatch(data[i],i);
    					self.renderMixOdds(data[i],i);
    					if(data[i].status != 1){
    						offSaleMatchCount++;
    		    		} else {
    		    			//联赛筛选仅存储在售
    		    			ALL_LEAGUES.put(data[i].leagueName, data[i].leagueName);
    		    		}
    				}
    				self.renderOffSaleNMatchBtn(offSaleMatchCount);
    				self.renderFilterMatch();
    			}
	    	};
	    	if(!self.options.matches){//没有传送赛事数据，则异步请求获取
	    		var loading = $('<tr><td colspan="10"><div class="loading" style="margin:0px auto;height: 40px;"></div></td></tr>');
	    		self.$el.append(loading);
	    		var dateStrIndex = window.location.href.indexOf('time=');
	    		var dateStr="";
 				if(dateStrIndex != -1){
 					dateStr = window.location.href.substring(dateStrIndex+5);
 				}
		    	$.ajax("http://trade.davcai.com/df/bet/jczq_all_matches.do?time="+dateStr,{
		    		success:function (data){
		    			loading.remove();
		    			self.options.matches = data;
		    			//开始渲染赛事数据
		    			rendSetup(data);
		    			//渲染投注面板,同时把当前页面的赛事数据传给投注面板使用
		    			$('[_n="bet-content-table"]').jcBetPanel({allMatchesData:data,lotteryId:'JCZQ'});
		    			//绑定事件
		    			self.bind();
		    			self.clickRecOption();
		    			//如果是赛事回查，默认触发展开隐藏的停售赛程
		    			var getDate = function(date){
	   	    	 			var str = "yyyy-MM-dd";
	   	    	 			str=str.replace(/yyyy|YYYY/,date.getFullYear());
	   	    	 			var mon = parseInt(date.getMonth())+1;  
	   	    	 			str=str.replace(/MM/,mon>9?mon:'0' + mon);     
	   	    	 			str=str.replace(/dd|DD/,date.getDate()>9?date.getDate().toString():'0' + date.getDate());
	   	    	 			return str;
	    	 			};
		    			if(dateStr && dateStr < getDate(new Date())){
		    				$("#checkbox-end").attr("checked","checked");
		    				$("#checkbox-end").triggerHandler("click");
		    			}
		    			//ajax初始化即时赔率数据
		    			$.ajax("http://www.davcai.com/odds/fbOddsPushInit.do?corpIds=1,3,4,8,12,14,17,22,23,24,31,35&callback=?&time="+dateStr,{
		    				success:function (data){
		    					//订阅渠道
		    					$.subMatchInstantOdds("JCZQ");
		    					if(!data || !data.data){
		    						return ;
		    					}
		    					ALL_ODDS_DATA=data.data;
		    					//触发默认赔率类型
				    			$('.instant-index-list-content>li[odd-type="europe"]').find('[crop-id="1"]').trigger("click");
		    					//渲染初始化赔率数据
		    					self.renderInitMatchOdds();
		    				},
		    				dataType:'jsonp',
		    				error:function (j,t,r){
		    					console.log(r);
		    				}
		    			});
		    			//查询联赛排名
		    			$.ajax("http://www.davcai.com/analyse/ajax_jczq_matchTeam_position.do?time="+dateStr,{
		    				success:function (data){
		    					if(data.success){
		    						data = data.data;
		    						$('td[league-range-id]').each(function (){
		    							var home = $($(this).find(".bet-match-league-range")[0]);
		    							var guest = $($(this).find(".bet-match-league-range")[1]);
		    							var hgData = data["matchId"+$(this).attr("league-range-id")];
		    							if(hgData){
		    								home.html("["+hgData.homeTeamPosition+"]");
		    								guest.html("["+hgData.guestTeamPosition+"]");
		    							}
		    						});
		    					}
		    				},
		    				dataType:'jsonp',
		    				error:function (j,t,r){
		    					console.log(r);
		    				}
		    			});
		    		},
		    		error:function (jqXHR,textStatus,errorThrown){},
		    		dataType:'json'
		    	});
	    	} else {
	    		rendSetup(data);//开始渲染数据
	    	}
	    },
	    renderInitMatchOdds:function (){
	    	var data = ALL_ODDS_DATA;
	    	if(!data){
	    		return ;
	    	}
	    	var currentCropId=$('.instant-index-list-content .current [crop-id]').attr('crop-id');
	    	var currentOddType = $('.instant-index-list-content .current [crop-id]').closest('li').attr("odd-type");
	    	if(!currentCropId){
	    		currentCropId=1;
	    	}
	    	if(!currentOddType){
	    		currentOddType="europe";
	    	}
	    	var $odds = $('[odd-match-id]');
    		for (var i = 0; i < $odds.length; i++) {
    			var $o = $($odds[i]);
    			var matchId = $o.attr('odd-match-id');
    			var typeField="";
    			if($o.attr('odd-type')!="instant"){//非即时
    				typeField="_init";
    			}
	    		if(data && data['matchId'+matchId] && data['matchId'+matchId]['corpId'+currentCropId]){
	    			var $oa = $o.find('span');
	    			for (var j = 0; j < $oa.length; j++) {
	    				var dd = data['matchId'+matchId]['corpId'+currentCropId][currentOddType+typeField];
	    				if(dd){
	    					var odd = dd.split(",");
		    				$($oa[j]).html(odd[j]);
	    				} else {
	    					$($oa[j]).html("--");
	    				}
					}
	    		} else {
	    			var $oa = $o.find('span');
	    			$oa.html("--");
	    		}
			}
	    },
	    renderFilterMatch:function (){
	    	var keys = ALL_LEAGUES.keys();
	    	$("#filter-match-selector").append('<label><input name="leagueName" type="checkbox" value="全部"></input>全部</label>');
	    	for (var i = 0; i < keys.length; i++) {
	    		var leagueName = ALL_LEAGUES.get(keys[i]);
	    		$("#filter-match-selector").append('<label><input name="leagueName" type="checkbox" value="'+leagueName+'"></input>'+leagueName+'</label>');
			}
	    	$("#filter-match-selector").mouseenter(function (){
	    		$(this).show();
	    		$("#filter-match-selector-span").find("i").addClass("up-arrow");
	    		$("#filter-match-selector-span").find("i").removeClass("down-arrow");
	    	}).mouseleave(function (){
	    		$(this).hide();
	    		$("#filter-match-selector-span").find("i").addClass("down-arrow");
	    		$("#filter-match-selector-span").find("i").removeClass("up-arrow");
	    	});
	    	$("#filter-match-selector-span").mouseenter(function (){
	    		$("#filter-match-selector").show();
	    		$(this).find("i").addClass("up-arrow");
	    		$(this).find("i").removeClass("down-arrow");
	    	}).mouseleave(function (){
	    		$("#filter-match-selector").hide();
	    		$(this).find("i").addClass("down-arrow");
	    		$(this).find("i").removeClass("up-arrow");
	    	});
	    	$("#filter-match-selector").find('input[name="leagueName"]').click(function (){
	    		var $input = $('[name="leagueName"]');
	    		if($(this).val()!="全部"){
	    			$('[name="leagueName"][value="全部"]').attr("checked",false);
	    		}
	    		if($('[name="leagueName"][value="全部"]').attr("checked")=="checked"){
	    			$input.attr("checked",false);
	    			$('[name="leagueName"][value="全部"]').attr("checked",true);
	    		}
	    		var leagueNames=[];
	    		for (var i = 0; i < $input.length; i++) {
	    			if($($input[i]).attr("checked")=="checked"){
	    				leagueNames.push($($input[i]).val());
	    			}
				}
	    		var showNMatchSelector = $("#checkbox-end").attr("checked") == "checked"?'':'[onsale]';
	    		if(leagueNames.length ==1 && leagueNames[0] == "全部"){
	    			$('[league-name]'+showNMatchSelector).show();
	    		} else {
	    			$('[league-name]').each(function (){
	    				$(this).hide();
	    				if($(this).next().next('[_n="mix-odds-all"]').is(":visible")){
	    					$(this).find('[_n="mix-odds-btn"]').trigger("click");
	    				}
	    			});
	    			for (var i = 0; i < leagueNames.length; i++) {
		    			$('[league-name][league-name="'+leagueNames[i]+'"]'+showNMatchSelector).show();
					}
	    		}
	    	});
	    },
	    renderMatchDay:function (matchData,preMatchCode){
	    	var self = this;
	    	if(!self.currentMatchDateMap){
	    		self.currentMatchDateMap = new Map();
	    	}
	    	var key = $.parseToYeardayWithPrefix(matchData.entrustDeadline,"");
	    	var curMatchCode = parseInt(matchData.code.substring(1));
	    	preMatchCode = preMatchCode == null ? 99999:parseInt(preMatchCode.substring(1));
	    	if(!self.currentMatchDateMap.get(key) && preMatchCode > curMatchCode){
	    		self.currentMatchDateMap.put(key,key);
	    		self.matchGroupIndex++;
		    	var tmpl = this.options.matchDateTmpl;
		    	var $matchDate = $($.mustache(tmpl,{entrustTimeView:function (){
		    		return $.parseToYeardayAndWeekday(matchData.entrustDeadline);
		    	}}));
		    	self.$el.append($matchDate);
		    	$matchDate.find('[_n="hideMatch"]').data("matchGroupIndex",self.matchGroupIndex);
	    	}
	    },
	    renderMatch:function (matchData,i){
	    	var self = this;
	    	var spfOdds = matchData.odds_80ZC2.split(",");
			var rqspfOdds = matchData.odds_01ZC2.split(",");
			var $matchHtml = $($.mustache(self.options.matchTmpl,{
	    		match:matchData,
	    		matchGroupIndex:self.matchGroupIndex,
	    		codeView:matchData.code.substring(1),
	    		entrustDeadlineView:matchData.entrustDeadline.substring(11,16),
	    		matchStartTimeView:matchData.playingTime.substring(11,16),
	    		spf3odd:spfOdds[0],
	    		spf1odd:spfOdds[1],
	    		spf0odd:spfOdds[2],
	    		rqspf3odd:rqspfOdds[0],
	    		rqspf1odd:rqspfOdds[1],
	    		rqspf0odd:rqspfOdds[2],
	    		i:i,
	    		sfpweikaiHtml:parseInt(spfOdds[0])==0?'<td colspan="3" class="no-sale">此玩法未开售</td>':'',
	    		sfpdisplay:parseInt(spfOdds[0])==0?'display:none':'',
	    		matchString:matchString(matchData, 'JCZQ'),
	    		defaultScoreView:function (){
	    			return this.defaultScore>0?"+"+this.defaultScore:this.defaultScore;
	    		},
	    		codePlayingTime:function (){
	    			var a=matchData.playingTime.replace(/\-/g,"");
	    			var a=a.replace(/\:/g,"");
	    			var a=a.replace(/T/g,"");
	    			return matchData.code+"-"+a;
	    		},
	    		oddsStatus:function (){
	    			return this.status == 1 ? 'class="default" _n="odds-td"':'class="offsale"' 
	    		},
	    		displayMatch:function (){
	    			return this.status == 1 ? 'onsale="true"':'style="display:none" offsale="true"'
	    		},
	    		writeRecClass:function (){
	    			return this.status == 1 ? '_n="write-rec"':'class="gray-img"'
	    		}
	    	}));
			self.$el.append($matchHtml);
			if(matchData.support01ZC2SinglePass){//支持让球单关
				var $divs = $matchHtml.find('[odds-type="rqspf"]');
				$($divs[0]).addClass("red-border-l");
				$($divs[1]).addClass("red-border-m");
				$($divs[1]).append('<div class="spf-danguan"></div>');
				$($divs[2]).addClass("red-border-r");
			}
			if(matchData.support80ZC2SinglePass){//支持不让球单关
				var $divs = $matchHtml.find('[odds-type="spf"]');
				$($divs[0]).addClass("red-border-l");
				$($divs[1]).addClass("red-border-m");
				$($divs[1]).append('<div class="spf-danguan"></div>');
				$($divs[2]).addClass("red-border-r");
			}
			if(matchData.status == 5 && matchData.score){//完场的比赛，渲染赛果
				var score = matchData.score.split(":");
				var c=parseInt(score[0]);
				var d=parseInt(score[1]);
				var opts = $matchHtml.find('[_p="80ZC2"]');
				if(c>d){
					$(opts[0]).find("a").addClass("options-passed");
				} else if(c==d){
					$(opts[1]).find("a").addClass("options-passed");
				} else {
					$(opts[2]).find("a").addClass("options-passed");
				}
				opts = $matchHtml.find('[_p="01ZC2"]');
				c += parseInt(matchData.defaultScore);
				if(c>d){
					$(opts[0]).find("a").addClass("options-passed");
				} else if(c==d){
					$(opts[1]).find("a").addClass("options-passed");
				} else {
					$(opts[2]).find("a").addClass("options-passed");
				}
			}
	    },
	    renderOffSaleNMatchBtn:function (offSaleMatchCount){
	    	var t = $(".table-bet-box-time");
	    	if(t.length > 0){
	    		$(t[0]).find("td").append($.mustache(this.options.offSaleNMatchBtnTmpl,{
	    			offSaleCount:offSaleMatchCount
	    		}));
	    	}
	    },
	    renderMixOdds:function (matchData,i){
	    	var self = this;
	    	var $scoreHtml = $($.mustache(self.options.mixBetTmpl,{
	    		matchGroupIndex:self.matchGroupIndex,
				scoreOddsWinOptions:function (){
					var options = matchData.options.split(",");
					var odds = matchData.odds.split(",");
					var scoreOddsWinOptionsHtml = "";
					for(var j=0;j<options.length && j<13;j++){
						scoreOddsWinOptionsHtml += $.mustache(self.options.oddsOptionTmpl,{
							option:$.getJCOptionView(options[j],"02ZC2"),
							odds:odds[j],
							i:i,
							j:j,
							p:"02ZC2",
							oddsStatus:function (){
				    			return matchData.status == 1 ? 'class="default" _n="mix-odds-td"':'class="offsale"'
							}
						});
					}
					return scoreOddsWinOptionsHtml;
				},
				scoreOddsDrawnOptions:function (){
					var options = matchData.options.split(",");
					var odds = matchData.odds.split(",");
					var scoreOddsDrawnOptionsHtml = "";
					for(var j=13;j<options.length && j<18;j++){
						scoreOddsDrawnOptionsHtml += $.mustache(self.options.oddsOptionTmpl,{
							option:$.getJCOptionView(options[j],"02ZC2"),
							odds:odds[j],
							i:i,
							j:j,
							p:"02ZC2",
							oddsStatus:function (){
				    			return matchData.status == 1 ? 'class="default" _n="mix-odds-td"':'class="offsale"'
							}
						});
					}
					return scoreOddsDrawnOptionsHtml;
				},
				scoreOddsLoseOptions:function (){
					var options = matchData.options.split(",");
					var odds = matchData.odds.split(",");
					var scoreOddsLoseOptionsHtml = "";
					for(var j=18;j<options.length && j<31;j++){
						scoreOddsLoseOptionsHtml += $.mustache(self.options.oddsOptionTmpl,{
							option:$.getJCOptionView(options[j],"02ZC2"),
							odds:odds[j],
							i:i,
							j:j,
							p:"02ZC2",
							oddsStatus:function (){
				    			return matchData.status == 1 ? 'class="default" _n="mix-odds-td"':'class="offsale"'
							}
						});
					}
					return scoreOddsLoseOptionsHtml;
				},
				jinqiushuOptions:function (){
					var options = matchData.options_03ZC2.split(",");
					var odds = matchData.odds_03ZC2.split(",");
					var jinqiushuOptionsHtml = "";
					for(var j=0;j<options.length;j++){
						jinqiushuOptionsHtml += $.mustache(self.options.oddsOptionTmpl,{
							option:$.getJCOptionView(options[j],"03ZC2"),
							odds:odds[j],
							i:i,
							j:j,
							p:"03ZC2",
							oddsStatus:function (){
				    			return matchData.status == 1 ? 'class="default" _n="mix-odds-td"':'class="offsale"'
							}
						});
					}
					return jinqiushuOptionsHtml;
				},
				banquanchangOptions:function (){
					var options = matchData.options_04ZC2.split(",");
					var odds = matchData.odds_04ZC2.split(",");
					var banquanchangOptionsHtml = "";
					for(var j=0;j<options.length;j++){
						banquanchangOptionsHtml += $.mustache(self.options.oddsOptionTmpl,{
							option:$.getJCOptionView(options[j],"04ZC2"),
							odds:odds[j],
							i:i,
							j:j,
							p:"04ZC2",
							oddsStatus:function (){
				    			return matchData.status == 1 ? 'class="default" _n="mix-odds-td"':'class="offsale"'
							}
						});
					}
					return banquanchangOptionsHtml;
				},
				oddsStatus:function (){
	    			return matchData.status == 1 ? 'onsale="true"':'offsale="true"'
				},
				leagueName:matchData.leagueName
			}));
			self.$el.append($scoreHtml);
			if(matchData.support02ZC2SinglePass){//支持全场比分单关
				$scoreHtml.find('[odds-type="qcbf"]').addClass("dg");
			}	
			if(matchData.support03ZC2SinglePass){//支持总进球单关
				$scoreHtml.find('[odds-type="zjq"]').addClass("dg");
			}
			if(matchData.support04ZC2SinglePass){//支持半全场单关
				$scoreHtml.find('[odds-type="bqc"]').addClass("dg");
			}
			if(matchData.status == 5){//完场的比赛，渲染赛果
				var score = matchData.score.split(":");
				var halfScore = matchData.halfScore.split(":");
				var a=parseInt(halfScore[0]);
				var b=parseInt(halfScore[1]);
				var c=parseInt(score[0]);
				var d=parseInt(score[1]);
				var spf = ["负","平","","胜"];
				var halfScoreView="";
				var scoreView="";
				if(a>b){
					halfScoreView=spf[3];
				} else if(a==b){
					halfScoreView=spf[1];
				} else {
					halfScoreView=spf[0];
				}
				if(c>d){
					scoreView=spf[3];
				} else if(c==d){
					scoreView=spf[1];
				} else {
					scoreView=spf[0];
				}
				var opts = $scoreHtml.find('[_p="04ZC2"]');
				var v = halfScoreView + "/" + scoreView;
				for (var i = 0; i < opts.length; i++) {
					var is = $(opts[i]).find('i');
					if($.trim($(is[0]).text())==v){
						$(opts[i]).find('i').addClass("options-passed");
						break;
					}
				}
				opts = $scoreHtml.find('[_p="03ZC2"]');
				v = (c+d)>=7?"7+球":(c+d)+"球";
				for (var i = 0; i < opts.length; i++) {
					var is = $(opts[i]).find('i');
					if($.trim($(is[0]).text())==v){
						$(opts[i]).find('i').addClass("options-passed");
						break;
					}
				}
				opts = $scoreHtml.find('[_p="02ZC2"]');
				if(c > 5 || d>5){
					if(c>d){
						v="胜其他";
					} else if(c==d){
						v="平其他";
					} else {
						v="负其他";
					}
				} else {
					if(c==d && c>3){
						v="平其他";
					} else {
						v=c+"-"+d;
					}
				}
				for (var i = 0; i < opts.length; i++) {
					var is = $(opts[i]).find('i');
					if($.trim($(is[0]).text())==v){
						$(opts[i]).find('i').addClass("options-passed");
						break;
					}
				}
			}
	    },
	    bind :function (){
	    	var self = this;
	    	self.bindOddsClick();
	    	self.bindMatchDateHide();
	    	//绑定即时指数弹出面板事件
	    	$(".instant-index-td").mouseenter(function (){
	    		$(".instant-index-list").show();
	    	}).mouseleave(function (){
	    		$(".instant-index-list").hide();
	    	});
	    	self.bindInstantOdds();
	    	self.bindMatchTimeView();
	    	$('[_n="write-rec"]').click(function (){
	    		var $this = $(this);
	    		var tr1 = $this.closest("tr");
	    		var tr2 = tr1.next();
	    		var tr3 = tr2.next();
	    		var oddCount = tr1.find('td[_n="odds-td"].active').length+
	    			tr2.find('td[_n="odds-td"].active').length+
	    			tr3.find('td[_n="mix-odds-td"].active').length;
	    		if(oddCount > 0){//判断是否选择了该场比赛选项
	    			showAddRecMatchDialog($this.attr("match-id"),$this.attr("_i"));
	    		} else {
	    			alert("请先选择投注内容");
	    		}
	    	});
	    	$("#checkbox-end").click(function (){
	    		if($(this).attr("checked")=="checked"){
	    			if($(this).prev('[_n="hideMatch"]').text()=="显示"){
	    				$(this).prev('[_n="hideMatch"]').trigger("click");
	    			}
	    			$("[offsale].table-bet-box-match").show();
	    		} else {
	    			$("[offsale]").hide();
	    			//删除混合按钮激活状态
	    			var $mixOptinos = $('.table-bet-box-match[offsale]').find('.mixed-active');
	    			$mixOptinos.removeClass("mixed-active");
	    			$mixOptinos.children("div").removeClass("active");
	    			$mixOptinos.addClass("mixed-default");
	    		}
	    	});
	    	$('[odd-type="instant"]').mouseenter(function (){
	    		var data = ALL_ODDS_DATA;
	    		var $this = $(this);
	    		var mid = $this.attr("odd-match-id");
	    		var currentCropId=$('.instant-index-list-content .current [crop-id]').attr('crop-id');
		    	var type = $('.instant-index-list-content .current [crop-id]').closest('li').attr("odd-type");
		    	if(!currentCropId){
		    		currentCropId=1;
		    	}
		    	if(!type){
		    		type="europe";
		    	}
		    	var fixFloatPoiont = function (num){
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
		    	if(data && data['matchId'+mid] && data['matchId'+mid]['corpId'+currentCropId]){
		    		var dd = data['matchId'+mid]['corpId'+currentCropId][type];
		    		var tt = data['matchId'+mid]['corpId'+currentCropId][type+"_time"];
		    		if(dd && tt){
		    			var odds = dd.split(",");
		    			var times = tt.split(",");
		    			var $table = $("#OddsChangeTable");
		    			var floatTitle0="主队";
		    			var floatTitle1="和局";
		    			var floatTitle2="客队";
		    			if(type=="bigsmall"){//大小分
		    				floatTitle0="大球";
		    				floatTitle1="盘口";
			    			floatTitle2="小球";
		    			} else if(type=="asian"){//亚赔
		    				floatTitle1="盘口";
		    			}
	    				var headHtml = '<tr align="center" bgcolor="#E74C3C" height="20"><td colspan="4"><font color="#ffffff">近5次变化记录</font> &nbsp; </td></tr>'
	    							+  '<tr align="center" bgcolor="#E74C3C" height="20">'
	    							+  '	<td width="50"><b><font color="#ffffff">'+floatTitle0+'</font></b></td>'
	    							+  '	<td width="50"><b><font color="#ffffff">'+floatTitle1+'</font></b></td>'
	    							+  '	<td width="50"><b><font color="#ffffff">'+floatTitle2+'</font></b></td>'
	    							+  '	<td><font color="#ffffff"><b>变化时间</b></font></td>'
	    							+  '</tr>';
	    				var tmpl = '<tr align="center" bgcolor="#ffffff">'
	    						+  '	<td height="22"><b><font color="{{color0}}">{{d0}}</font></b></td>'
	    						+  '	<td><b><font color="{{color1}}"><b>{{d1}}</font></b></td>'
	    						+  '	<td><b><font color="{{color2}}"><b>{{d2}}</font></b></td>'
	    						+  '	<td>{{time}}</td>'
	    						+  '</tr>';
	    				var html="";
	    				for (var i = 0; i < times.length && i<5; i++) {
							var t = times[i];
							var a=parseFloat(odds[i*3]);
							var b=parseFloat(odds[i*3+1]);
							var c=parseFloat(odds[i*3+2]);
							var a1=a;
							var b1=b;
							var c1=c;
							if((i+1) < times.length){
								a1=parseFloat(odds[(i+1)*3]);
								b1=parseFloat(odds[(i+1)*3+1]);
								c1=parseFloat(odds[(i+1)*3+2]);
							}
							html+=$.mustache(tmpl,{
								d0:fixFloatPoiont(a),
								d1:fixFloatPoiont(b),
								d2:fixFloatPoiont(c),
								color0:a>a1?"red":a<a1?"green":"",
								color1:b>b1?"red":b<b1?"green":"",
								color2:c>c1?"red":c<c1?"green":"",
								time:new Date(parseInt(t)).Format("MM-dd hh:mm")
							});
						}
	    				$table.html(headHtml+html);
	    				$("#OddsChangeTable").show();
	    				$("#OddsChangeTable").offset({top:50+$this.offset().top,left:$this.offset().left});
		    		}
		    	}
	    	}).mouseleave(function (){
	    		$("#OddsChangeTable").html("");
	    		$("#OddsChangeTable").hide();
	    	});
	    },
	    bindInstantOdds:function (){
	    	var self = this;
	    	$(".instant-index-list-content>li>span").click(function (){
	    		$this=$(this);
	    		if($this.hasClass("current")){
	    			return ;
	    		} else {
	    			var $current = $(".instant-index-list-content>li>span.current");
	    			var curCropId = $current.find("a").attr("crop-id");
	    			var curOddType = $current.closest("li").attr("odd-type");
	    			$(".instant-index-list-content>li>span").removeClass("current");
	    			$this.addClass("current");
	    			var newCropId=$this.find("a").attr("crop-id");
	    			var newOddType=$this.closest("li").attr("odd-type");
	    			self.renderInitMatchOdds();
	    			$('[odd-match-id]>span').removeClass("color-green").removeClass("color-red");
	    		}
	    	});
	    },
	    bindMatchTimeView:function (){
	    	$(".section-sale-td").mouseenter(function (){
	    		$(".section-sale-list").show();
	    	}).mouseleave(function (){
	    		$(".section-sale-list").hide();
	    	});
	    	$(".section-sale-list a").click(function (){
	    		var status = $(this).attr("data");
	    		if(status == "offsale"){
	    			$($(".section-sale-list a")[0]).attr("data","offsale");
	    			$($(".section-sale-list a")[0]).html("截售");
	    			$($(".section-sale-list a")[1]).attr("data","palytime");
	    			$($(".section-sale-list a")[1]).html("开赛");
	    			$(".section-sale-td > a").html("截售");
	    			$('[_n="entrustDeadline"]').show();
	    			$('[_n="matchStartTime"]').hide();
	    		} else if(status == "palytime"){
	    			$($(".section-sale-list a")[0]).attr("data","palytime");
	    			$($(".section-sale-list a")[0]).html("开赛");
	    			$($(".section-sale-list a")[1]).attr("data","offsale");
	    			$($(".section-sale-list a")[1]).html("截售");
	    			$(".section-sale-td > a").html("开赛");
	    			$('[_n="entrustDeadline"]').hide();
	    			$('[_n="matchStartTime"]').show();
	    		}
	    	});
	    },
	    bindMatchDateHide:function (){
	    	var self = this;
	    	$('[_n="hideMatch"]').click(function (){
	    		var $this = $(this);
	    		var matchGroupIndex = $this.data("matchGroupIndex");
	    		var showNMatchSelector = $("#checkbox-end").attr("checked") == "checked"?'':'[onsale]';
	    		if($this.text() == "隐藏"){
	    			$this.text("显示");
	    			$('[matchGroupIndex="' + matchGroupIndex + '"]'+showNMatchSelector).hide();//隐藏全部
	    		} else {
	    			$this.text("隐藏");
	    			$('.table-bet-box-match[matchGroupIndex="' + matchGroupIndex + '"]'+showNMatchSelector).show();//只展开赛事，不展开混合投注选项
	    			//删除混合按钮激活状态
	    			var $mixOptinos = $('.table-bet-box-match[matchGroupIndex="' + matchGroupIndex + '"]').find('.mixed-active');
	    			$mixOptinos.removeClass("mixed-active");
	    			$mixOptinos.children("div").removeClass("active");
	    			$mixOptinos.addClass("mixed-default");
	    		}
	    	});
	    },
	    bindOddsClick:function (){
	    	$('[_n="odds-td"],[_n="mix-odds-td"]').click(function (){
	    		var $this = $(this);
	    		if($this.hasClass("default")){
	    			$(this).removeClass("default");
		    		$(this).addClass("active");
		    		$('[_n="bet-content-table"]').jcBetPanel({
		    			i:$this.attr("_i"),
		    			j:$this.attr("_j"),
		    			p:$this.attr("_p"),
		    			method:"addBetMatch"
		    		});
	    		} else {
	    			$(this).removeClass("active");
		    		$(this).addClass("default");
		    		$('[_n="bet-content-table"]').jcBetPanel({
		    			i:$this.attr("_i"),
		    			j:$this.attr("_j"),
		    			p:$this.attr("_p"),
		    			method:"delBetMatch"
		    		});
	    		}
	    		//计算当前混合投注选项数量并显示
	    		var selectBetCount=0;
	    		$this.closest('tr[_n="mix-odds-all"]').find('[_n="mix-odds-td"]').each(function (){
	    			if($(this).hasClass("active")){
	    				selectBetCount ++;
	    			}
	    		});
	    		if(selectBetCount > 0){
	    			var itag = $this.closest('tr[_n="mix-odds-all"]').prev().prev().find('[_n="select-hunhe-bet-count"]');
	    			itag.addClass("select-hunhe-bet-count");
	    			itag.html(selectBetCount);
	    		} else {
	    			var itag = $this.closest('tr[_n="mix-odds-all"]').prev().prev().find('[_n="select-hunhe-bet-count"]');
	    			itag.removeClass("select-hunhe-bet-count");
	    			itag.html("");
	    		}
	    	});
	    	$('[_n="mix-odds-btn"]').click(function (){
	    		$this = $(this);
	    		if($this.hasClass('mixed-default')){
	    			$this.addClass("mixed-active");
	    			$this.children("div").addClass("active");
	    			$this.removeClass("mixed-default");
	    			$this.closest("tr").next().next().show();
	    		} else {
	    			$this.removeClass("mixed-active");
	    			$this.children("div").removeClass("active");
	    			$this.addClass("mixed-default");
	    			$this.closest("tr").next().next().hide();
	    		}
	    	});
	    },
	    reset : function(option){
	    	this.options = $.extend({}, this.options, option);
	    },
	    clickRecOption:function (){
	    	if(_betMatchList.length<1){
	    		return ;
	    	}
	    	var allMatchesMap = new Map();
	    	for (var i = 0; i < this.options.matches.length; i++) {
	    		allMatchesMap.put(this.options.matches[i].id, i);
			}
	    	var _betCountMap = new Map();
	    	var _betCount=0;
	    	for (var i = 0; i < _betMatchList.length; i++) {
	    		var betMatch = _betMatchList[i];
	    		var matchIndex = allMatchesMap.get(betMatch.matchId);
	    		if(!_betCountMap.get(matchIndex)){
	    			_betCountMap.put(matchIndex, "1");
	    			_betCount++;
	    		}
	    		var mixBtnIsNeedOpen = false;
	    		if(matchIndex || matchIndex==0){
	    			var p = betMatch.playId;
	    			var seed = betMatch.seed;
	    			var code = betMatch.code;
	    			var oindex = ["2","1","","0"];
	    			var bf = "10,20,21,30,31,32,40,41,42,50,51,52,90,00,11,22,33,99,01,02,12,03,13,23,04,14,24,05,15,25,09".split(",");
	    			var bifen = {};
	    			for (var q = 0; q < bf.length; q++) {
	    				bifen[bf[q]]=q;
					}
	    			var bqc = "33,31,30,13,11,10,03,01,00".split(",");
	    			var banquanchang = {};
	    			for (var q = 0; q < bqc.length; q++) {
	    				banquanchang[bqc[q]]=q;
	    			}
	    			if(p.indexOf("01_ZC") != -1){
	    				var opt = code.substring(4);
	    				var options = opt.split("");
	    				for (var j = 0; j < options.length; j++) {
							var oj=oindex[options[j]];
							$('[_n="odds-td"][_i="'+matchIndex+'"][_j="'+oj+'"][_p="01ZC2"]').trigger("click");
						}
	    			} else if(p.indexOf("02_ZC") != -1){
	    				var opt = code.substring(4);
	    				var options = opt.split("");
	    				for (var j = 0; j < options.length;j=j+2) {
							var oj=bifen[options[j]+options[j+1]];
							$('[_n="mix-odds-td"][_i="'+matchIndex+'"][_j="'+oj+'"][_p="02ZC2"]').trigger("click");
						}
	    				mixBtnIsNeedOpen = true;
	    			} else if(p.indexOf("03_ZC") != -1){
	    				var opt = code.substring(4);
	    				var options = opt.split("");
	    				for (var j = 0; j < options.length; j++) {
							var oj=options[j];
							$('[_n="mix-odds-td"][_i="'+matchIndex+'"][_j="'+oj+'"][_p="03ZC2"]').trigger("click");
						}
	    				mixBtnIsNeedOpen = true;
	    			} else if(p.indexOf("04_ZC") != -1){
	    				var opt = code.substring(4);
	    				var options = opt.split("");
	    				for (var j = 0; j < options.length;j=j+2) {
							var oj=banquanchang[options[j]+options[j+1]];
							$('[_n="mix-odds-td"][_i="'+matchIndex+'"][_j="'+oj+'"][_p="04ZC2"]').trigger("click");
						}
	    				mixBtnIsNeedOpen = true;
	    			} else if(p.indexOf("80_ZC") != -1){
	    				var opt = code.substring(4);
	    				var options = opt.split("");
	    				for (var j = 0; j < options.length; j++) {
							var oj=oindex[options[j]];
							$('[_n="odds-td"][_i="'+matchIndex+'"][_j="'+oj+'"][_p="80ZC2"]').trigger("click");
						}
	    			}
	    		}
	    		if(mixBtnIsNeedOpen){
	    			$('[_n="mix-odds-btn"][_i="'+matchIndex+'"].mixed-default').trigger("click");
	    		}
	    	}
	    	//选择串关
	    	var passTypes = _passTypeIds.split(",");
	    	for (var i = 0; i < passTypes.length; i++) {
	    		var $c;
	    		if(_betCount == passTypes[i].charAt(0) && parseInt(passTypes[i].charAt(2)) > 1){
	    			$('[_id="pass-type-tab"][data="normal"]').trigger("click");
					$c=$('[name="norPasstype"][value="'+passTypes[i]+'"]');
				} else {
					$c=$('[name="mulPasstype"][value="'+passTypes[i]+'"]');
				}
				$c.attr("checked","checked");
				$c.triggerHandler("click");
			}
	    }
	};
  
	$.fn.rendBetMatches = function (option) {
		return this.each(function(input_field) {
			var $this = $(this), data = $this.data('rendBetMatches');
		    if (!data) {
		    	$this.data('rendBetMatches', (data = new rendBetMatches(this, option)));
		    } else {
		    	data.reset(option);
		    }
		    //option 是方法名
		    if (typeof option == 'string'){
		    	if(!data[option]){
		    		console.log( 'Method ' + option + ' does not exist on jQuery.rendBetMatches' );
		    	} else {
		    		data[option]();
		    	}
		    }
		});
	};
	$.fn.rendBetMatches.defaults = {
		matches:null,//赛事数据
		matchTmpl:null,//渲染赛事的模板
		mixBetTmpl:null//渲染赛事混合投注选项(含赔率)的模板
	};
})(window.jQuery);
