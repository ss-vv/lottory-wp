var ALL_ODDS_DATA;
var ALL_LEAGUES=new Map();
;(function($) {
	/**
	 * 渲染赛事列表
	 */
	var rendLQBetMatches = function(element,option){
	    this.$el = $(element);
	    $.fn.rendLQBetMatches.defaults.matchDateTmpl=$("#jclq-match-date-tmpl").html();
	    $.fn.rendLQBetMatches.defaults.matchTmpl=$("#jclq-match-tmpl").html();
	    $.fn.rendLQBetMatches.defaults.oddsTmpl='<td style="width: 96px;" {{{saleStatusView}}} _i="{{i}}" _j="{{j}}" _p="{{p}}">{{{odd}}}</i>';
	    $.fn.rendLQBetMatches.defaults.sfcOddsTmpl=$("#jclq-sfc-odd-tmpl").html();
	    $.fn.rendLQBetMatches.defaults.offSaleNMatchBtnTmpl='<input type="checkbox" id="checkbox-end" value="checkbox"><label for="checkbox-end">显示已截止 <i class="color-red">{{offSaleCount}}</i> 场比赛</label>';
	    this.options = $.extend({}, $.fn.rendLQBetMatches.defaults, option);
	    this.initialized();
	};
	rendLQBetMatches.prototype = {
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
	    		var loading = $('<div class="loading" style="margin:0px auto;height: 40px;"></div>');
	    		self.$el.append(loading);
	    		var dateStrIndex = window.location.href.indexOf('time=');
	    		var dateStr="";
 				if(dateStrIndex != -1){
 					dateStr = window.location.href.substring(dateStrIndex+5);
 				}
		    	$.ajax("http://trade.davcai.com/df/bet/jclq_all_matches.do?time="+dateStr,{
		    		success:function (data){
		    			loading.remove();
		    			self.options.matches=data;
		    			//开始渲染赛事数据
		    			rendSetup(data);
		    			//渲染投注面板,同时把当前页面的赛事数据传给投注面板使用
		    			$('[_n="bet-content-table"]').jcBetPanel({allMatchesData:data,lotteryId:'JCLQ'});
		    			//绑定事件
		    			self.bind();
		    			self.clickRecOption();
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
		    			$.ajax("http://www.davcai.com/odds/bbOddPushInit.do?corpIds=1,2,3,8,9,4,12,15,16,17&callback=?&time="+dateStr,{
		    				success:function (data){
		    					//订阅渠道
		    					$.subMatchInstantOdds("JCLQ");
		    					if(!data || !data.data){
		    						return ;
		    					}
		    					ALL_ODDS_DATA=data.data;
		    					//触发默认赔率类型
				    			$('.instant-index-list-content [crop-id="1"]').trigger("click");
		    					//渲染初始化赔率数据
		    					self.renderInitMatchOdds();
		    				},
		    				dataType:'jsonp'
		    			});
		    		},
		    		error:function (jqXHR,textStatus,errorThrown){},
		    		dataType:'json'
		    	});
	    	} else {
	    		rendSetup(data);//开始渲染数据
	    	}
	    },
	    renderOffSaleNMatchBtn:function (offSaleMatchCount){
	    	var t = $(".basketball-bet-box-time");
	    	if(t.length > 0){
	    		$(t[0]).find("td").append($.mustache(this.options.offSaleNMatchBtnTmpl,{
	    			offSaleCount:offSaleMatchCount
	    		}));
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
	    			$('[league-name]').hide();
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
		    	var tmpl = self.options.matchDateTmpl;
		    	var $matchDate = $($.mustache(tmpl,{entrustTimeView:function (){
		    		return $.parseToYeardayAndWeekday(matchData.entrustDeadline);
		    	}}));
		    	self.$el.append($matchDate);
		    	$matchDate.find('[_n="hideMatch"]').data("matchGroupIndex",self.matchGroupIndex);
	    	}
	    },
	    renderInitMatchOdds:function (){
	    	var data = ALL_ODDS_DATA;
	    	if(!data){
	    		return ;
	    	}
	    	var currentCropId=$('.instant-index-list-content .current [crop-id]').attr('crop-id');
	    	if(!currentCropId){
	    		currentCropId=1;
	    	}
	    	var $matches = $('.basketball-bet-cont');
	    	for (var k = 0; k < $matches.length; k++) {
	    		$odds = $($matches[k]).find('[odd-match-id]');
	    		for (var i = 0; i < $odds.length; i++) {
	    			var $o = $($odds[i]);
	    			var matchId = $o.attr('odd-match-id');
		    		if(data && data['matchId'+matchId] && data['matchId'+matchId]['corpId'+currentCropId]){
		    			var $oa = $o.find('span');
		    			for (var j = 0; j < $oa.length; j++) {
		    				var type = $($oa[j]).attr("type");
		    				var dd = data['matchId'+matchId]['corpId'+currentCropId][type];
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
			}
	    },
	    renderMatch:function (matchData,i){
	    	var self = this;
	    	var oddStyle="";
	    	var matchWriteRecBtnStyle="";
	    	var matchStyle = "";
	    	if(matchData.status != 1){
	    		oddStyle="";
	    		matchWriteRecBtnStyle = ' class="gray-img" ';
	    		matchStyle = ' style="display:none" offsale="true" '
	    	} else {
	    		oddStyle = ' _n="odd" class="default" ';
	    		matchWriteRecBtnStyle = ' _n="write-rec" ';
	    		matchStyle = ' onsale="true" '
	    	}
	    	var $matchHtml = $($.mustache(self.options.matchTmpl,{
	    		match:matchData,
	    		matchString:matchString(matchData, 'JCLQ'),
	    		matchGroupIndex:self.matchGroupIndex,
	    		codeView:matchData.code.substring(1),
	    		entrustDeadlineView:matchData.entrustDeadline.substring(11,16),
	    		matchStartTimeView:matchData.playingTime.substring(11,16),
	    		i:i,
	    		sfOddsView:function (){
	    			if(!this.odds_07LC2){
	    				return '<td colspan="2" class="no-for-sale">此玩法未开售</td>';
	    			}
	    			var odds = this.odds_07LC2.split(",");
	    			return $.mustache(self.options.oddsTmpl,{
	    				odd:odds[0],
	    				i:i,
	    				j:0,
	    				p:'07LC2',
	    				saleStatusView:oddStyle
	    			}) + $.mustache(self.options.oddsTmpl,{
	    				odd:odds[1],
	    				i:i,
	    				j:1,
	    				p:'07LC2',
	    				saleStatusView:oddStyle
	    			});
	    		},
	    		rfsfOddsView:function (){
	    			if(!matchData.odds_06LC2){
	    				return '<td colspan="2" class="no-for-sale">此玩法未开售</td>';
	    			}
	    			var odds = matchData.odds_06LC2.split(",");
	    			var score = parseFloat(this.defaultScore);
	    			return $.mustache(self.options.oddsTmpl,{
	    				odd:'<i class="color-red">('+(score>0?-score:'+'+(-score))+')</i>&nbsp' + odds[0],
	    				i:i,
	    				j:0,
	    				p:'06LC2',
	    				saleStatusView:oddStyle
	    			}) + $.mustache(self.options.oddsTmpl,{
	    				odd:'<i class="color-red">('+(score>0?'+'+score:score)+')</i>&nbsp' + odds[1],
	    				i:i,
	    				j:1,
	    				p:'06LC2',
	    				saleStatusView:oddStyle
	    			});
	    		},
	    		dxfOddsView:function (){
	    			if(!matchData.odds_09LC2){
	    				return '<td colspan="2" class="no-for-sale">此玩法未开售</td>';
	    			}
	    			var odds = matchData.odds_09LC2.split(",");
	    			var score = parseFloat(this.defaultScore_09LC2);
	    			return $.mustache(self.options.oddsTmpl,{
	    				odd:'<i class="color-red">大于'+score+'</i>&nbsp' + odds[0],
	    				i:i,
	    				j:0,
	    				p:'09LC2',
	    				saleStatusView:oddStyle
	    			}) + $.mustache(self.options.oddsTmpl,{
	    				odd:'<i class="color-red">小于'+score+'</i>&nbsp' + odds[1],
	    				i:i,
	    				j:1,
	    				p:'09LC2',
	    				saleStatusView:oddStyle
	    			});
	    		},
	    		ksfcOddsView:function (){
	    			if(!matchData.odds_08LC2){
	    				var a='<table class="table_notborder" cellspacing="0" cellpadding="0" width="100%"><tbody><tr>'
			    			 +'		<td colspan="6" class="no-for-sale">此玩法未开售</td>'
			    			 +'</tr></tbody></table>'
	    				return a;
	    			}
	    			var odds = matchData.odds_08LC2.split(",");
	    			return $.mustache(self.options.sfcOddsTmpl,{
	    				a:odds[0],b:odds[2],c:odds[4],d:odds[6],e:odds[8],f:odds[10],
	    				i:i,
	    				opta:0,optb:2,optc:4,optd:6,opte:8,optf:10,
	    				saleStatusView:oddStyle
	    			});
	    		},
	    		zsfcOddsView:function (){
	    			if(!matchData.odds_08LC2){
	    				var a='<table class="table_notborder" cellspacing="0" cellpadding="0" width="100%"><tbody><tr>'
			    			 +'		<td colspan="6" class="no-for-sale">此玩法未开售</td>'
			    			 +'</tr></tbody></table>'
	    				return a;
	    			}
	    			var odds = matchData.odds_08LC2.split(",");
	    			return $.mustache(self.options.sfcOddsTmpl,{
	    				a:odds[1],b:odds[3],c:odds[5],d:odds[7],e:odds[9],f:odds[11],
	    				i:i,
	    				opta:1,optb:3,optc:5,optd:7,opte:9,optf:11,
	    				saleStatusView:oddStyle
	    			});
	    		},
	    		sfdg:function (){
	    			return matchData.support07LC2SinglePass == true? 'class="dg"':'';
	    		},
	    		rfsfdg:function (){
	    			return matchData.support06LC2SinglePass == true? 'class="dg"':'';
	    		},
	    		dxfdg:function (){
	    			return matchData.support09LC2SinglePass == true? 'class="dg"':'';
	    		},
	    		sfcdg:function (){
	    			return matchData.support08LC2SinglePass == true? 'class="dg"':'';
	    		},
	    		matchWriteRecBtnStyle:matchWriteRecBtnStyle,
	    		matchStyle:matchStyle,
	    		codePlayingTime:function (){
	    			var a=matchData.playingTime.replace(/\-/g,"");
	    			var a=a.replace(/\:/g,"");
	    			var a=a.replace(/T/g,"");
	    			return matchData.code+"-"+a;
	    		}
	    	}));
	    	if(matchData.status == 5 && matchData.score){//完场的比赛，渲染赛果
				var score = matchData.score.split(":");
				var c=parseInt(score[0]);
				var d=parseInt(score[1]);
				var opts = $matchHtml.find('[_p="07LC2"]');
				if(c>d){
					$(opts[0]).addClass("options-passed");
					var diffScoreIndex = Math.floor((c-d)/5);
					$($matchHtml.find('[_p="08LC2"]')[diffScoreIndex]).find('i').addClass("options-passed");
				} else {
					$(opts[1]).addClass("options-passed");
					var diffScoreIndex = Math.floor((d-c)/5);
					$($matchHtml.find('[_p="08LC2"]')[diffScoreIndex+6]).find('i').addClass("options-passed");
				}
				opts = $matchHtml.find('[_p="06LC2"]');
				d += parseInt(matchData.defaultScore);
				if(c>d){
					$(opts[0]).addClass("options-passed");
				} else
					$(opts[1]).addClass("options-passed");
				opts = $matchHtml.find('[_p="09LC2"]');
				if((c+d) > parseFloat(matchData.defaultScore_09LC2)){
					$(opts[0]).addClass("options-passed");
				} else {
					$(opts[1]).addClass("options-passed");
				}
			}
	    	self.$el.append($matchHtml);
	    },
	    bind:function (){
	    	//绑定即时指数弹出面板事件
	    	$(".instant-index-td").mouseenter(function (){
	    		$(".instant-index-list").show();
	    	}).mouseleave(function (){
	    		$(".instant-index-list").hide();
	    	});
	    	this.bindInstantOdds();
	    	this.bindOddsClick();
	    	this.bindMatchDateHide();
	    	this.bindMatchTimeView();
	    	$('[_n="write-rec"]').click(function (){
	    		var $this = $(this);
	    		var table = $this.closest("table");
	    		var oddCount = table.find('td[_n="odd"].active').length;
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
	    			$("[offsale].basketball-bet-cont").show();
	    		} else {
	    			$("[offsale]").hide();
	    		}
	    	});
	    	$('[odd-match-id]').mouseenter(function (){
	    		var data = ALL_ODDS_DATA;
	    		var $this = $(this);
	    		var mid = $this.attr("odd-match-id");
	    		var type= $($this.find("span")[0]).attr("type");
	    		var currentCropId=$('.instant-index-list-content .current [crop-id]').attr('crop-id');
		    	if(!currentCropId){
		    		currentCropId=1;
		    	}
		    	if(data && data['matchId'+mid] && data['matchId'+mid]['corpId'+currentCropId]){
		    		var dd = data['matchId'+mid]['corpId'+currentCropId][type];
		    		var tt = data['matchId'+mid]['corpId'+currentCropId][type+"_time"];
		    		if(dd && tt){
		    			var odds = dd.split(",");
		    			var times = tt.split(",");
		    			var $table = $("#OddsChangeTable");
		    			if(type!="europe"){//三个数据一组
		    				var headHtml = '<tr align="center" bgcolor="#E74C3C" height="20"><td colspan="4"><font color="#ffffff">近5次变化记录</font> &nbsp; </td></tr>'
		    							+  '<tr align="center" bgcolor="#E74C3C" height="20">'
		    							+  '	<td width="50"><b><font color="#ffffff">大分</font></b></td>'
		    							+  '	<td width="50"><b><font color="#ffffff">盘口</font></b></td>'
		    							+  '	<td width="50"><b><font color="#ffffff">小分</font></b></td>'
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
									d0:a,
									d1:b,
									d2:c,
									color0:a>a1?"red":a<a1?"green":"",
									color1:b>b1?"red":b<b1?"green":"",
									color2:c>c1?"red":c<c1?"green":"",
									time:new Date(parseInt(t)).Format("MM-dd hh:mm")
								});
							}
		    				$table.html(headHtml+html);
		    				$("#OddsChangeTable").show();
		    				$("#OddsChangeTable").offset({top:50+$this.offset().top,left:$this.offset().left});
		    			} else {//两个数据一组
		    				var headHtml = '<tr align="center" bgcolor="#E74C3C" height="20"><td colspan="4"><font color="#ffffff">近5次变化记录</font> &nbsp; </td></tr>'
		    							+  '<tr align="center" bgcolor="#E74C3C" height="20">'
		    							+  '	<td width="50"><b><font color="#ffffff">主</font></b></td>'
		    							+  '	<td width="50"><b><font color="#ffffff">客</font></b></td>'
		    							+  '	<td><font color="#ffffff"><b>变化时间</b></font></td>'
		    							+  '</tr>';
		    				var tmpl = '<tr align="center" bgcolor="#ffffff">'
		    						+  '	<td height="22"><font color="{{color0}}"><b>{{d0}}</font></b></td>'
		    						+  '	<td><b><font color="{{color1}}"><b>{{d1}}</font></b></td>'
		    						+  '	<td>{{time}}</td>'
		    						+  '</tr>';
		    				var html="";
		    				for (var i = 0; i < times.length && i<5; i++) {
								var t = times[i];
								var a=parseFloat(odds[i*2]);
								var b=parseFloat(odds[i*2+1]);
								var a1=a;
								var b1=b;
								if((i+1) < times.length){
									a1=parseFloat(odds[(i+1)*2]);
									b1=parseFloat(odds[(i+1)*2+1]);
								}
								html+=$.mustache(tmpl,{
									d0:a,
									d1:b,
									color0:a>a1?"red":a<a1?"green":"",
									color1:b>b1?"red":b<b1?"green":"",
									time:new Date(parseInt(t)).Format("MM-dd hh:mm")
								});
							}
		    				$table.html(headHtml+html);
		    				$("#OddsChangeTable").show();
		    				$("#OddsChangeTable").offset({top:50+$this.offset().top,left:$this.offset().left});
		    			}
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
	    			var curCropId = $current.find("span").attr("crop-id");
	    			var curOddType = $current.closest("li").attr("odd-type");
	    			$(".instant-index-list-content>li>span").removeClass("current");
	    			$this.addClass("current");
	    			var newCropId=$this.find("span").attr("crop-id");
	    			var newOddType=$this.closest("li").attr("odd-type");
	    			self.renderInitMatchOdds();
	    			$('[odd-match-id]>span[type]').removeClass("color-green").removeClass("color-red");
	    		}
	    	}); 
	    },
	    bindOddsClick:function (){
	    	$('[_n="odd"]').click(function (){
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
	    			$('table[matchGroupIndex="' + matchGroupIndex + '"]').hide();//隐藏全部
	    		} else {
	    			$this.text("隐藏");
	    			$('table[matchGroupIndex="' + matchGroupIndex + '"]'+showNMatchSelector).show();
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
	    		if(matchIndex || matchIndex==0){
	    			var p = betMatch.playId;
	    			var seed = betMatch.seed;
	    			var code = betMatch.code;
	    			var sf = ["","1","0"];
	    			var dxf = ["","0","1"];
	    			var shengfencha = "11,01,12,02,13,03,14,04,15,05,16,06".split(",");
	    			var sfc = {};
	    			for (var q = 0; q < shengfencha.length; q++) {
	    				sfc[shengfencha[q]]=q;
					}
	    			if(p.indexOf("06_LC") != -1){
	    				var opt = code.substring(4);
	    				var options = opt.split("");
	    				for (var j = 0; j < options.length; j++) {
							var oj=sf[options[j]];
							$('[_n="odd"][_i="'+matchIndex+'"][_j="'+oj+'"][_p="06LC2"]').trigger("click");
						}
	    			} else if(p.indexOf("07_LC") != -1){
	    				var opt = code.substring(4);
	    				var options = opt.split("");
	    				for (var j = 0; j < options.length; j++) {
							var oj=sf[options[j]];
							$('[_n="odd"][_i="'+matchIndex+'"][_j="'+oj+'"][_p="07LC2"]').trigger("click");
						}
	    			} else if(p.indexOf("08_LC") != -1){
	    				var opt = code.substring(4);
	    				var options = opt.split("");
	    				for (var j = 0; j < options.length;j=j+2) {
							var oj=sfc[options[j]+""+options[j+1]];
							$('[_n="odd"][_i="'+matchIndex+'"][_j="'+oj+'"][_p="08LC2"]').trigger("click");
						}
	    			} else if(p.indexOf("09_LC") != -1){
	    				var opt = code.substring(4);
	    				var options = opt.split("");
	    				for (var j = 0; j < options.length; j++) {
							var oj=dxf[options[j]];
							$('[_n="odd"][_i="'+matchIndex+'"][_j="'+oj+'"][_p="09LC2"]').trigger("click");
						}
	    			}
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
  
	$.fn.rendLQBetMatches = function (option) {
		return this.each(function(input_field) {
			var $this = $(this), data = $this.data('rendLQBetMatches');
		    if (!data) {
		    	$this.data('rendLQBetMatches', (data = new rendLQBetMatches(this, option)));
		    } else {
		    	data.reset(option);
		    }
		    //option 是方法名
		    if (typeof option == 'string'){
		    	if(!data[option]){
		    		console.log( 'Method ' + option + ' does not exist on jQuery.rendLQBetMatches' );
		    	} else {
		    		data[option]();
		    	}
		    }
		});
	};
	$.fn.rendLQBetMatches.defaults = {
		matches:null,//赛事数据
		matchTmpl:null,//渲染赛事的模板
		mixBetTmpl:null//渲染赛事混合投注选项(含赔率)的模板
	};
})(window.jQuery);