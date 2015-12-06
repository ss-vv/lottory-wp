;(function($) {
	/**
	 * 投注面板
	 */
	var jcBetPanel = function(element,option){
		this.$el = $(element);
	    this.options = $.extend({}, $.fn.jcBetPanel.defaults, option);
	    this.options.betOptionTmpl = '<a href="javascript:void(0);" class="betting-three" _i="{{i}}" _j="{{j}}" _p="{{p}}">{{betOption}}</a>';
	    this.options.betStopTimeTmpl = '<tr class="end-time"><td colspan="4">{{stopTimeView}}</td></tr>';
	    this.options.betMatchContentTmpl = $('#bet-match-content-tmpl').html();
	    this.options.betMatchCommentTmpl = $('#bet-match-comment-tmpl').html();
	    this.options.betAmountViewTmpl = $('#bet-amount-view-tmpl').html();
	    this.options.betMatchTextTmpl = '<tr class="recommend-text-tr"><td colspan="4"><div class="recomend-text">{{betMatchText}}</div></td></tr>';
	    this.initialized();
	}; 
	jcBetPanel.prototype = {
	    initialized : function(){
	    	this.settings = {};
	    	/**
	    	 * 投注的赛事初始化:betMatchesData
	    	 * betMatchesData和allMatchesData索引一一对应
	    	 * betMatchesData某个索引对应的内容为空，表示某场赛事未投注
	    	 */
	    	this.options.betMatchesData = new Array();
	    	this.options.multiBet = 1;
	    	this.options.groupBuyRatio = 0.05;
	    	this.options.passTypeList = new Array();//过关方式数据初始化
	    	this.options.danList = new Array();
	    	for(var i=0;i<this.options.allMatchesData.length;i++){
	    		this.options.betMatchesData.push({bet:[]});//将所有投注内容初始化为空
	    	}
	    	this.rend();
	    	this.rendBalance();
	    	this.bind();
	    },
	    /**
	     * 更新过关方式和胆数据
	     */
	    updatePassAndDanData:function (){
	    	var danlist = this.options.danList;
	    	if(danlist){
		    	for(var i=0;i<this.options.betMatchesData.length;i++){
		    		if(this.options.betMatchesData[i].bet.length < 1){//未选的赛事
		    			for (var j = 0; j < danlist.length; j++) {//删除胆
		    				if(danlist[j]==i){
		    					danlist.splice(j, 1);
		    					break;
		    				}
		    	    	}
		    		}
		    	}
		    	for (var i = 0; i < danlist.length; i++) {
					$('[_n="set-dan-checkbox"][value="'+danlist[i]+'"]').each(function (){
						if($(this).attr("checked")!=true){
							$(this).attr("checked",true);
						}
					});
				}
	    	}
	    	var pass = this.options.passTypeList;
	    	if(pass){
		    	var maxpass = this.passMaxMatches();
		    	maxpass = Math.min(maxpass,parseInt(this.countBetMatches()));
		    	for (var i = maxpass+1; i < 9; i++) {//把已选择的不支持的玩法取消选择
		    		$('.pass-way-block').find('label[_m='+i+']>input[type="checkbox"]:checked').each(function (){
		    			$(this).attr("checked",false);
		    		});
				}
		    	var newpass = [];
		    	for (var i = 0; i < pass.length; i++) {
					if(parseInt(pass[i].charAt(0))<=maxpass){
						newpass.push(pass[i]);
					}
				}
		    	this.options.passTypeList = newpass;
	    	}
	    },
	    rend:function (){//渲染投注面板
	    	var self = this;
	    	self.$el.html('<tr class="bib-table-nav"><th style="width:66px">场次</th><th  style="width:64px">主队</th><th  style="width:122px">投注项</th><th  style="width:33px"><a href="javascript:void(0);" class="bib-question"><i>胆</i></a></th></tr>');
	    	var betMatches = self.options.betMatchesData;
	    	var allMatchesData = self.options.allMatchesData;
	    	var hasBetMatch = false;
	    	if(betMatches && betMatches.length > 0){
	    		for(var i=0;i<betMatches.length;i++){
	    			if(betMatches[i].bet.length > 0){
	    				var betOptionsHtml ='';
	    				for(var m=0;m<betMatches[i].bet.length;m++){
			    			var optionIndex = betMatches[i].bet[m].j;
			    			var playType = betMatches[i].bet[m].p; 
			    			var betOption = (allMatchesData[i][playType=='02ZC2'?'options':'options_' + playType]).split(',')[optionIndex];
			    			var betOdd = (allMatchesData[i][playType=='02ZC2'?'odds':'odds_' + playType]).split(',')[optionIndex];
			    			betOptionsHtml += $.mustache(self.options.betOptionTmpl,{
		    					betOption:$.getJCOptionView(betOption,playType,
		    								allMatchesData[i].defaultScore,allMatchesData[i].defaultScore_09LC2),
		    					i:i,
		    					j:optionIndex,
		    					p:playType
		    				});
		    			}
	    				var homeTeam = allMatchesData[i].homeTeamName;
	    				var betComment = "";//赛事注释
	    				if(betMatches[i].betComment){
	    					betComment = $.mustache(self.options.betMatchCommentTmpl,{
		    					comment:betMatches[i].betComment
		    				});
	    				}
		    			var $betMatchHtml = $($.mustache(self.options.betMatchContentTmpl,{
		    				code:allMatchesData[i].cnCode,
		    				homeTeam:homeTeam.substring(0,Math.min(4,homeTeam.length)),
		    				betOptions:betOptionsHtml,
		    				i:i,
		    				betComment:betComment
		    			}));
		    			self.$el.append($betMatchHtml);
		    			hasBetMatch = true;
	    			}
	    		}
	    	}
	    	if(hasBetMatch){
	    		self.$el.append($.mustache(self.options.betStopTimeTmpl,{
	    			stopTimeView:"本方案截止时间：" + self.calCurrentSchemeEntrustTime()
	    		}));
	    	} else {
	    		self.$el.append($.mustache(self.options.betStopTimeTmpl,{
	    			stopTimeView:"请在投注区选择赛果进行投注"
	    		}));
	    	}
	    	self.updatePassAndDanData();
//	    	self.options.danList = new Array();//清空胆码数据
//	    	self.options.passTypeList = new Array();//清空过关方式
	    	self.rendPassType();
	    	self.rendBetAmount();
	    },
	    calCurrentSchemeEntrustTime:function (){
	    	var self = this;
	    	var betMatches = self.options.betMatchesData;
	    	var allMatchesData = self.options.allMatchesData;
	    	var entrustTime;
	    	for (var i = 0; i < betMatches.length; i++) {
	    		if(betMatches[i].bet && betMatches[i].bet.length > 0){
	    			if(!entrustTime || entrustTime > allMatchesData[i].entrustDeadline){
	    				entrustTime = allMatchesData[i].entrustDeadline;
	    			}
	    		}
			}
	    	return entrustTime.replace("T","　");
	    },
	    rendBalance:function(){
	    	var self = this;
	    	$.getJSON('http://login.davcai.com/ajax_get_weibouser.do?jsonp=?', function(data){
	    		if (data != null && data.weiboUser) {
	    			var weiboUser = data.weiboUser;
	    			self.isLogin=true;
	    			$('[_n="balance"]').html('<i class="margin-lefta">'+weiboUser.freeMoney + "元" + '</i>' + '【<a href="http://trade.davcai.com/ac/recharge.do" target="_blank" class="color-red">充值</a>】');
	    		} else {
	    			$('[_n="balance"]').html('<i class="margin-lefta">未登录</i> 【<a _n="gotoLogin" href="javascript:void(0);" class="color-red">登录</a>】');
	    			self.isLogin=false;
	    			$('[_n="balance"]').find('a[_n="gotoLogin"]').click(function (){
	    	    		self.gotoLogin();
	    	    	});
	    		}
	    	}, 'json');
	    },
	    rendPassType:function (){//渲染过关方式
	    	var self = this;
	    	var mulPassTypeTmpl = '<label _m="{{requireMatchsNum}}" style="display:none"><input type="checkbox" name="mulPasstype" value="{{passType}}"/>{{passTypeView}}</label>';
	    	var norPassTypeTmpl = '<label _m="{{requireMatchsNum}}" style="display:none"><input type="radio" name="norPasstype"  value="{{passType}}"/>{{passTypeView}}</label>';
	    	$('p[_n="pass-type-tip"]').remove();//删除过关方式提示
	    	if(!self.passTypeData){//第一次加载过关方式
	    		//获取混合过关可选的串关方式
	    		$.ajax('http://trade.davcai.com/df/aj_lspasstype.do', {
	    			async:false,
	    			data:{playId: '10_LC_2', _: new Date().getTime()},
	    			success:function(json){
	    				json = eval("("+json+")");
		                if(json.success && json.data.length > 0){
		                	self.passTypeData = json.data;
		                	for(var i=0;i<json.data.length;i++){//将过关方式渲染到页面
		                		var t;
		                		if(json.data[i].note == 1){
		                			t = $($.mustache(mulPassTypeTmpl,{
		                    			passType:json.data[i].id,
		                    			passTypeView:json.data[i].name,
		                    			requireMatchsNum:json.data[i].id.charAt(0)
		                    		}));
		                			$('[_n="pass-type-multiple"]').append(t);
		                		}
	                			t = $($.mustache(norPassTypeTmpl,{
	                    			passType:json.data[i].id,
	                    			passTypeView:json.data[i].name,
	                    			requireMatchsNum:json.data[i].id.charAt(0)
	                    		}));
	                			$('[_n="pass-type-normal"]').append(t);
		                	}
		                	$(".pass-way-block").append('<p _n="pass-type-tip">请选择比赛进行投注</p>');
		                	self.bindPassTypeClick();
		                }
	    			}
	            });
	    	} else {//根据已选赛事数量渲染过关方式
	    		var count = parseInt(self.countBetMatches());
	    		count = Math.min(count,self.passMaxMatches());
	    		$('[_n="pass-type-multiple"]:visible > label').each(function (){
	    			if(parseInt($(this).attr('_m')) <= count){
	    				$(this).show();
	    			} else {
	    				$(this).hide();
	    			}
	    		});
	    		$('[_n="pass-type-normal"]:visible > label').each(function (){
	    			if(parseInt($(this).attr('_m')) == count){
	    				$(this).show();
	    			} else {
	    				$(this).hide();
	    			}
	    		});
	    		if(count > 1){//超过1赛事，不显示1@1
	    			$('[_t="pass-type-panel"] > label[_m=1]').hide();
	    		} else if (count == 1){
	    			if(self.isSupportSinglePass()){
	    				$('[_t="pass-type-panel"] > label[_m=1]').show();
	    			} else {
	    				$('[_t="pass-type-panel"] > label[_m=1]').hide();
	    				$(".pass-way-block").append('<p _n="pass-type-tip">请再选择一场比赛</p>');
	    			}
	    		} else {
	    			$(".pass-way-block").append('<p _n="pass-type-tip">请选择比赛进行投注</p>');
	    		}
	    		//判断胆的数量，显示可选串关方式，3个胆，不能2@1
	    		$('[_t="pass-type-panel"] > label[_m] > input[type="checkbox"]').attr("disabled",false);
	    		if(self.options.danList && self.options.danList.length > 0){
	    			var danCount = parseInt(self.options.danList.length);
	    			for (var i = 1; i < danCount; i++) {
	    				$('[_t="pass-type-panel"] > label[_m="'+i+'"] > input[type="checkbox"]')
	    					.attr("checked",false).attr("disabled",true);
					}
	    			if(self.options.passTypeList && self.options.passTypeList.length > 0){
	    				var passTypeList = self.options.passTypeList;
	    				var newPassTypeList = [];
	    				for (var i = 0; i < passTypeList.length; i++) {
	    					var m = parseInt(passTypeList[i].charAt(0));
	    					if(danCount <= m){
	    						newPassTypeList.push(passTypeList[i]);
	    					}
						}
	    				self.options.passTypeList=newPassTypeList;
	    			}
	    		}
	    		//没有选择串关方式时，清空所有选勾
	    		if(!self.options.passTypeList || self.options.passTypeList.length < 1){
	    			$('[_t="pass-type-panel"] > label[_m] > input').attr("checked",false);
	    		} 
	    	}
	    },
	    bind:function (){
	    	var self = this;
	    	//绑定设胆checkbox
	    	$('.bet-information-block').delegate('input[_n="set-dan-checkbox"]','click',function (){
	    		self.options.danList = new Array();//清空胆码数据
    			$('input[_n="set-dan-checkbox"]').each(function (){
	    			if($(this).attr("checked") == "checked"){
	    				self.options.danList.push($(this).val());
	    			}
    			});
    			self.rendPassType();
    			self.rendBetAmount();//每次选择串关方式后，重新计算金额
	    	});
	    	//绑定赛事checkbox点击删除赛事事件
	    	$('.bet-information-block').delegate('.ring-red-arrow','click',function (){
	    		var $this = $(this);
	    		var matchIndex = $this.closest("tr").find('.betting-three').attr("_i");
	    		var $options = $('.betting-three[_i="'+matchIndex+'"]');
	    		while($options.length>0){
	    			$($options[0]).trigger("click");
	    			$options = $('.betting-three[_i="'+matchIndex+'"]');
	    		}
	    	});
	    	//绑定投注选项点击删除事件
	    	$('.bet-information-block').delegate('.betting-three','click',function (){
	    		var $this = $(this);
	    		var i=$this.attr("_i");
	    		var j=$this.attr("_j");
	    		var p=$this.attr("_p");
	    		$('td.active[_i='+i+'][_j='+j+'][_p='+p+']').trigger("click");
	    	});
	    	//绑定全部删除事件
	    	$('.bib-all-delete').click(function (){
	    		if(self.countBetMatches() > 0 && confirm("确认删除全部选项吗？")){
	    			var $options = $('.betting-three');
		    		while($options.length>0){
		    			$($options[0]).trigger("click");
		    			$options = $('.betting-three');
		    		}
	    		}
	    	});
	    	//绑定过关方式tab选项事件
	    	$('[_id="pass-type-tab"]').click(function (){
	    		var type = $(this).attr('data');
	    		$('[_id="pass-type-tab"]').addClass("pass-type-span");
	    		$('[_id="pass-type-tab"]').removeClass("pass-type-span-active");
	    		$(this).removeClass("pass-type-span");
	    		$(this).addClass("pass-type-span-active");
	    		$('[_t="pass-type-panel"]').hide();
	    		$('[_n="pass-type-'+type+'"]').show();
	    		$('[_t="pass-type-panel"] input').removeAttr("checked");//每次切换情况所有已经选中的过关方式
	    		self.options.passTypeList = new Array();//清空过关方式
	    		self.rendPassType();
		    	self.rendBetAmount();
	    	});
	    	//绑定投注类型选择事件（代购、合买、仅推荐）
	    	$('input[name="bet-type"]').click(function (){
	    		if("groupBuy" == $(this).val()){
	    			$('[_n="group-buy-form-tr"]').show();
	    		} else {
	    			$('[_n="group-buy-form-tr"]').hide();
	    		}
	    		if("rec" == $(this).val()){
	    			$('[_n="not-rec-form-tr"]').hide();
	    		} else {
	    			$('[_n="not-rec-form-tr"]').show();
	    		}
	    	});
	    	self.bindMultiBetChange();
	    	self.bindBuyAmountAndFloor();
	    	self.bindSubmit();
	    	
	    	$(window).scroll(function () {
	    		var h = $(window).height() - 60;
	    		if($(".davcai-bet-right").height()> h){
	    			$(".davcai-bet-right").css("position","relative").css("margin-left","0");
	    		} else {
	    			$(".davcai-bet-right").css("position","fixed").css("margin-left","725px");
	    		}
	    		$(".davcai-bet-right").css("margin-top","0px");
	    		var diff = $(".foot").offset().top - $(".davcai-bet-right").offset().top-$(".davcai-bet-right").height()-70;
	    		if(diff<0){
	    			var top = $('.davcai-bet-left-title').offset().top;
		    		var margin = $(".foot").offset().top-top-$(".davcai-bet-right").height()+40;
	    			$(".davcai-bet-right").css("position","fixed")
	    				.css("margin-left","725px")
	    				.css("margin-top",margin+"px");
	    		} else {
	    			$(".davcai-bet-right").css("margin-top","0px");
	    		}
	    	});
	    },
	    bindPassTypeClick :function (){//选择过关方式事件
	    	var self = this;
	    	$('[_t="pass-type-panel"] input').click(function (){
	    		self.options.passTypeList = new Array();
	    		if($(this).attr("type") == "radio"){
	    			self.options.passTypeList.push($(this).val());
	    		} else {
	    			$('[_n="pass-type-multiple"] input').each(function (){
		    			if($(this).attr("checked") == "checked"){
		    				self.options.passTypeList.push($(this).val());
		    			}
	    			});
	    		}
	    		self.rendBetAmount();//每次选择串关方式后，重新计算金额
	    	});
	    },
	    bindSubmit:function(){
	    	var self = this;
	    	$("#scheme-submit").click(function (){
	    		if(!self.isLogin){
	    			self.gotoLogin();
	    			return false;
	    		}
	    		if(self.countBetMatches() < 1){
	    			alert("您还没有选择投注内容");
	    			return false;
	    		}
	    		if(self.options.passTypeList && self.options.passTypeList.length < 1){
	    			alert("请选择串关方式");
	    			return false;
	    		}
	    		var betType = $('[name="bet-type"]:checked').val();
	    		if(betType!="rec"){//不是推荐需要确认
	    			if(confirm("确认要投注此方案？")){
	    				if(betType == "alone"){
		    				betType = 0;
		    			} else if(betType == "groupBuy"){
		    				betType = 1;
		    			} else {
		    				return false;
		    			}
	    			} else {
	    				return false;
	    			}
	    		} else {
	    			betType = 2;
	    		}
    			var form = $("#scheme-form");
    			var schemeData = self.analyzeBetMatches();
    			console.log(schemeData[0]);
    			form.find('[name="matchs"]').val(schemeData[0]);
    			form.find('[name="betMatches"]').val(schemeData[0]);
    			form.find('[name="playId"]').val(schemeData[1]);
    			form.find('[name="passTypes"]').val(self.options.passTypeList.join());
    			form.find('[name="annotations"]').val(schemeData[2]);
    			form.find('[name="multiple"]').val(self.options.multiBet);
    			form.find('[name="buyAmount"]').val($('[_n="buyAmount"]').val());
    			form.find('[name="money"]').val($('[_n="buyAmount"]').val());
    			form.find('[name="bonus"]').val($($('[_n="bonus-div"] span')[1]).text());
    			form.find('[name="floorAmount"]').val($('[_n="floorAmount"]').val());
    			form.find('[name="followedRatio"]').val($('[_n="followedRatio"]').val());
    			form.find('[name="followSchemePrivacy"]').val($('[name="followSchemePrivacyView"]:checked').val());
    			form.find('[name="type"]').val(betType);
    			if(betType == 2){
    				showPublishRecommendDialog(schemeData[0],"jclq",schemeData[1]);
    				return ;
    			} else {
    				form.attr("action", "http://trade.davcai.com/ac/newBet.do");
    				form.submit();
    			}
	    	});
	    },
	    /**
	     * 返回[matches字符串,玩法类型字符串,评论]
	     */
	    analyzeBetMatches:function (){
	    	var self = this;
	    	var betMatches = self.options.betMatchesData;
	    	var allMatchesData = self.options.allMatchesData;
	    	var playTypeMap = {p06LC2:'rfsf',p07LC2:'sf',p08LC2:'fc',p09LC2:'dxf'};
	    	var danMap = {};
	    	if(self.options.danList){
	    		for (var i = 0; i < self.options.danList.length; i++) {
	    			danMap[""+self.options.danList[i]]=""+self.options.danList[i];
				}
	    	}
	    	var allMatchString = "";
	    	var isMultiPlayInMatch = false;//每场比赛是否有多个玩法
	    	var isMultiPlayInAll = false;//所有比赛是否有多个玩法
	    	var miltiPlayInAll = {};
	    	var miltiPlayInAllArray = [];
	    	var betComment = [];
	    	var count=0;
	    	for (var i = 0; i < betMatches.length; i++) {
	    		var havePlayMap = {};
	    		var havePlayArray = [];
	    		if(betMatches[i].bet && betMatches[i].bet.length > 0){
	    			count++;
	    			var matchString = "";
	    			for (var j = 0; j < betMatches[i].bet.length; j++) {
	    				var bet = (betMatches[i].bet)[j];
	    				var options;
	    				if(bet.p=="02ZC2"){
	    					options = allMatchesData[i].options.split(",");
	    				} else {
	    					options = allMatchesData[i]["options_"+bet.p].split(",");
	    				}
	    				if(matchString.indexOf("-"+playTypeMap["p"+bet.p]) != -1){//同一个玩法已经存在matchString中
	    					var matchStrings = matchString.split(",");
	    					for (var k = 0; k < matchStrings.length; k++) {
								if(matchStrings[k].indexOf("-"+playTypeMap["p"+bet.p]) != -1){
									var tmpMatchStringArr = matchStrings[k].split("-");
									tmpMatchStringArr[1]+=options[bet.j];
									matchStrings[k] = tmpMatchStringArr.join("-");
								}
							}
	    					matchString = matchStrings.join(",");
	    					continue;
	    				}
	    				matchString += allMatchesData[i].id +"-" 
	    						     + allMatchesData[i].code;
	    				matchString += options[bet.j] + "-";
	    				matchString += (i in danMap) + "-";
	    				matchString += playTypeMap["p"+bet.p] + ","
	    				if(!(bet.p in miltiPlayInAll)){
	    					miltiPlayInAll[bet.p]=bet.p;
	    					miltiPlayInAllArray.push(bet.p);
	    				}
	    				if(!(bet.p in havePlayMap)){
	    					havePlayMap[bet.p]=bet.p;
	    					havePlayArray.push(bet.p) ;
	    				}
					}
	    			if(betMatches[i].betComment){
	    				betComment.push(allMatchesData[i].id+"-"+betMatches[i].betComment);
	    			}
	    			allMatchString+=matchString;
	    		}
	    		if(havePlayArray.length > 1){//一场比赛有多个玩法
	    			isMultiPlayInMatch = true;
	    		}
			}
	    	if(miltiPlayInAllArray.length > 1){//所有比赛有多个玩法
	    		isMultiPlayInAll = true;
    		}
	    	var playId = "";
	    	if(isMultiPlayInMatch){
	    		playId = "666_LCFH"+"_"+(count>1?2:1);//新混合
	    	} else if(isMultiPlayInAll){
	    		playId = "10_LC"+"_"+(count>1?2:1);//旧混合
	    	} else {//不是混合
	    		playId = miltiPlayInAllArray[0];
	    		playId = playId.substring(0, 2)+"_"+playId.substring(2, 4)+"_"+(count>1?2:1);
	    		allMatchString=allMatchString.replace(/\-sf/g, "");
	    		allMatchString=allMatchString.replace(/\-rfsf/g, "");
	    		allMatchString=allMatchString.replace(/\-dxf/g, "");
	    		allMatchString=allMatchString.replace(/\-fc/g, "");
	    	}
	    	return [allMatchString.substring(0, allMatchString.length-1),playId,betComment.join()];
	    },
	    bindMultiBetChange:function (){
	    	var self=this;
	    	var addMulti = function (add){
	    		var mul = $('input[_n="multiBet"]').val();
	    		if(!mul){
	    			$('input[_n="multiBet"]').val(1);
	    		} else {
	    			$('input[_n="multiBet"]').val(Math.max(parseInt(mul)+add,1));
	    		}
	    	};
	    	$('img[_n="multiBetAdd"]').click(function (e){
	    		addMulti(1);
	    		$('input[_n="multiBet"]').trigger('keyup');
	    	});
	    	$('img[_n="multiBetCut"]').click(function (e){
	    		addMulti(-1);
	    		$('input[_n="multiBet"]').trigger('keyup');
	    	});
	    	$('input[_n="multiBet"]').keyup(function (e){
	    		var $this = $(this);
	    		if(e.keyCode==13){//通过触发“确认”按钮点击事件来提交表单
	    			e.preventDefault();
	    			//TODO
	    			return false;
	    		}
	    		var danbeiAmount = parseInt(self.options.notes) * 2;
	    		var mul = $this.val();
	    		if(!mul){//为空直接返回
	    			return false;
	    		}
	    		if(!self.isInteger(mul)){
	    			$this.attr("value","");
	    			$this.focus();
	    			self.options.multiBet = 1;
	    			return false;
	    		} else {
	    			if(parseInt(mul) > 9999){
	    				$this.attr("value","");
	    				alert("不能大于9999倍");
		    			$this.focus();
		    			self.options.multiBet = 1;
		    			return false;
	    			}
	    			var buyAmount = parseInt(mul) *  danbeiAmount;
	    			if(buyAmount > 1000000){
	    				var ex = (buyAmount-1000000)/danbeiAmount;
	    				var left = (buyAmount-1000000)%danbeiAmount;
	    				mul = parseInt(mul) - parseInt(ex) - (left > 0 ? 1:0);
	    				$this.attr("value",mul);
	    				alert("投注金额超出100万元，\n已将倍数重置为当前可投注的最大倍数");
		    			$this.focus();
		    			buyAmount = mul * danbeiAmount;
	    			}
	    			self.options.multiBet = mul;
	    		}
	    		self.rendBetAmount();//倍数改变
	    		$('input[_n="floorAmount"]').trigger("change");
	    	});
	    },
	    bindBuyAmountAndFloor:function (){
	    	var self = this;
	    	$('input[_n="buyAmount"]').change(function (e){
	    		var $this = $(this);
	    		var val = $this.val();
	    		var mul = self.options.multiBet;
	    		var notes = self.options.notes;
	    		var allAmount = notes*2*mul;
	    		if(!self.isInteger(val)){//输入的购买金额不是数字
	    			val = Math.ceil(allAmount*0.05);
	    		} else {
	    			val = Math.max(val,Math.ceil(allAmount*0.05));
	    			val = Math.min(val,allAmount);
	    		}
	    		$this.attr("value",val);
	    		self.options.buyAmount=val;
    			//设置当前购买份额
	    		if(allAmount == 0){
	    			$('[_n="currentBuyRatio"]').html(0);
	    		} else {
	    			$('[_n="currentBuyRatio"]').html(Math.max($.roundNumber(val*100.0/allAmount,4),5));
	    		}
	    		$('input[_n="floorAmount"]').trigger("change");
	    	});
	    	$('input[_n="floorAll"]').click(function (e){
	    		if($(this).attr("checked") == 'checked'){
	    			$('input[_n="floorAmount"]').val(10000000);
	    		} else {
	    			$('input[_n="floorAmount"]').val(0);
	    		}
	    		$('input[_n="floorAmount"]').trigger("change");
	    	});
	    	$('input[_n="floorAmount"]').change(function (e){
	    		var $this = $(this);
	    		var val = $this.val();
	    		var mul = self.options.multiBet;
	    		var notes = self.options.notes;
	    		var allAmount = notes*2*mul;
	    		var buyAmount = self.options.buyAmount;
	    		if(!self.isInteger(val)){//输入的购买金额不是数字
	    			val = 0;
	    		} else {
	    			val = Math.min(val,allAmount-buyAmount);
	    			val = Math.max(val,0);
	    		}
	    		$this.attr("value",val);
    			//设置当前保底份额
	    		if(allAmount == 0){
	    			$('[_n="currentFloorAmountRatio"]').html(0);
	    		} else {
	    			$('[_n="currentFloorAmountRatio"]').html(Math.max($.roundNumber(val*100.0/allAmount,4),0));
	    		}
	    		//如果是全额保底，设置checkbox勾上
	    		if(allAmount-buyAmount == val){
	    			$('input[_n="floorAll"]').attr("checked",true);
	    		} else {
	    			$('input[_n="floorAll"]').attr("checked",false);
	    		}
	    	});
	    },
	    reset : function(option){
	    	//this.options = $.extend({}, this.options, option);
	    },
	    gotoLogin:function (){
	    	$("#pop-outer").fadeIn(800);
			$("#all").show();
	    },
	    /**
	     * 添加一个投注选项，外部调用
	     * @param option eg:{
		 *		i:当前页面赛事索引,
		 *		j:玩法选项索引同时也可以表示该选项对应的赔率索引,
		 *		p:玩法,
		 *		method:"addBetMatch"
		 *	}
	     */
	    addBetMatch:function(option){
	    	var self = this;
	    	var bet = this.options.betMatchesData[option.i].bet;
	    	bet.push({j:option.j,p:option.p});
	    	self.options.betsNoteAndMaxAndMinData = dealWithAddJCLQBet({
	    		option:this.getOptionByBet(option.i,{j:option.j,p:option.p}),
	    		playType:option.p,
	    		odd:this.getOddByBet(option.i,{j:option.j,p:option.p}),
	    		defaultScore:this.options.allMatchesData[option.i].defaultScore,
	    		matchIndex:option.i
	    	});
	    	this.rend();
	    },
	    /**
	     * 删除一个投注选项，外部调用
	     * @param option eg:{
		 *		i:当前页面赛事索引,
		 *		j:玩法选项索引同时也可以表示该选项对应的赔率索引,
		 *		p:玩法,
		 *		method:"delBetMatch"
		 *	}
	     */
	    delBetMatch:function(option){
	    	var self = this;
	    	var bet = this.options.betMatchesData[option.i].bet;
	    	for(var i=0;i<bet.length;i++){
	    		if(bet[i].j == option.j && bet[i].p == option.p){
	    			bet.splice(i,1);
	    			break;
	    		}
	    	}
	    	self.options.betsNoteAndMaxAndMinData = dealWithDelJCLQBet({
	    		option:this.getOptionByBet(option.i,{j:option.j,p:option.p}),
	    		playType:option.p,
	    		odd:this.getOddByBet(option.i,{j:option.j,p:option.p}),
	    		defaultScore:this.options.allMatchesData[option.i].defaultScore,
	    		matchIndex:option.i
	    	});
	    	this.rend();
	    },
	    addBetMatchComment:function(option){
	    	this.options.betMatchesData[option.i].betComment = option.betComment;
	    	this.rend();
	    },
	    countBetMatches:function (){//计算当前投注赛事数量
	    	var self = this;
	    	var count = 0;
	    	for(var i=0;i<this.options.betMatchesData.length;i++){
	    		if(this.options.betMatchesData[i].bet.length > 0){
	    			count ++;
	    		}
	    	}
	    	return count;
	    },
	    passMaxMatches:function (){//计算当前投注赛事允许串关最大值
	    	var self = this;
	    	var min=8;
	    	for(var i=0;i<this.options.betMatchesData.length;i++){
	    		if(this.options.betMatchesData[i].bet.length > 0){
	    			for (var j = 0; j < this.options.betMatchesData[i].bet.length; j++) {
	    				var p = this.options.betMatchesData[i].bet[j].p;
						if(p=="08LC2"){
							min=Math.min(min,4);
						}
					}
	    		}
	    	}
	    	return min;
	    },
	    isSupportSinglePass : function(){
	    	var self = this;
	    	var count = this.countBetMatches();
	    	if( count > 1){//大于一场比赛，不支持单关
	    		return false;
	    	} else if (count = 1) {
	    		var bet;
	    		var matchIndex;
	    		var matchesData;
	    		for(matchIndex=0;matchIndex<this.options.betMatchesData.length;matchIndex++){
		    		if(this.options.betMatchesData[matchIndex].bet.length > 0){
		    			bet = this.options.betMatchesData[matchIndex].bet;//拿到当前投注的内容（只有一场比赛）
		    			matchesData = self.options.allMatchesData[matchIndex];
		    			break;
		    		}
		    	}
	    		for(var i=0;i<bet.length;i++){//查看投注内容是否支持单关
	    			var playType = bet[i].p;
	    			if(!matchesData["support"+playType+"SinglePass"]){
	    				return false;
	    			}
	    		}
	    		return true;
	    	}
	    },
	    rendBetAmount:function (){
	    	var self = this;
	    	var multiBet = self.options.multiBet;
	    	var notes = self.calculateBetNotesAndBonus();
	    	self.options.notes = notes[0];
	    	var buyAmountHtml = $.mustache(self.options.betAmountViewTmpl,{
	    		buyAmount:notes[0]*2*multiBet,
	    		matchCount:self.countBetMatches(),
	    		betNotesCount:notes[0]*multiBet,
	    	});
	    	$('[_n="multiBet"]').val(multiBet);
	    	$('[_n="totalAmountView"]').html(buyAmountHtml);
	    	var $spans = $('[_n="bonus-div"] span');
	    	$($spans[0]).html($.roundNumber(parseFloat(notes[2])* multiBet,2));
	    	$($spans[1]).html($.roundNumber(parseFloat(notes[1])* multiBet,2));
	    	$('input[_n="buyAmount"]').trigger("change");
	    },
	    calculateBetNotesAndBonus:function (){//返回[注数，最大奖金，最小奖金]
	    	var self = this;
	    	var passTypes = self.options.passTypeList;
	    	returnVal = [0,0,0];
	    	if(passTypes.length < 1){
	    		return returnVal;
	    	}
	    	var betsNoteAndMaxAndMinData = self.options.betsNoteAndMaxAndMinData;
	    	var matches =[];
	    	var bets=[];
	    	var tmp0=[];
			var tmp1=[];
			var tmp2=[];
	    	for (var i = 0; i < betsNoteAndMaxAndMinData[0].length; i++) {
	    		if(betsNoteAndMaxAndMinData[0][i]){
					matches.push(i);
					tmp0.push(betsNoteAndMaxAndMinData[0][i]);
					tmp1.push(betsNoteAndMaxAndMinData[1][i]);
					tmp2.push(betsNoteAndMaxAndMinData[2][i]);
				}
			}
	    	bets=[tmp0,tmp1,tmp2];
	    	self.options.danListIndex={};
	    	for (var h = 0; h < self.options.danList.length; h++){
	    		for (var y = 0; y < matches.length; y++) {
	    			if(matches[y]==self.options.danList[h]){
	    				self.options.danListIndex[""+y]=y;
	    				self.options.danListIndex["hasDan"]=true;
	    			}
				}
			}
	    	var excludeCompArray = [];//保存不合法的组合下标
	    	var danListIndex = []; 
	    	var count = 0;
	    	//计算注数
	    	var betOptionsVal = [];
	    	for (var i = 0; i < bets[0].length; i++) {//计算不合法的组合，并保存下标
	    		var excludeComp = [];
	    		var dan = [];//同场赛事不同玩法“胆”是或关系
	    		for (var j = 0; j < bets[0][i].length; j++) {
	    			if(bets[0][i].length > 1){
	    				excludeComp.push(count);
	    			}
	    			betOptionsVal.push(bets[0][i][j]);
	    			if((""+i) in self.options.danListIndex){
	    				dan.push(betOptionsVal.length-1);
	    			}
	    			count++;
				}
	    		excludeCompArray.push(excludeComp);
	    		danListIndex.push(dan);
			}
	    	for(var j = 0; j < passTypes.length; j++){//循环计算每个过关方式
	    		var mn = passTypes[j].split('@');//串关数组,eg:[3,1]表示3@1
	    		var ms = parseInt(betOptionsVal.length);//所选选项数
	            if(mn.length != 2){
	                throw new Error('无效过关方式：' + passTypes[i]);
	            }
	            if(mn[0] > ms){
	                break;
	            }
	            returnVal[0] += $.calculateBetData(mn,ms,betOptionsVal,excludeCompArray,danListIndex,"note");
	    	}
	    	//计算最大奖金
	    	//danListIndex数据结构对应：[[],[2,3],[4]]，
	    	//					 index 0↑   1↑   2↑
	    	//					index标识每场比赛是否设胆，空则没有设胆
	    	//					[2,3]表示该场比赛不同选项索引,index=1和2的赛事设置为胆了
	    	danListIndex = []; 
	    	excludeCompArray = [];
	    	count = 0;
	    	var betMaxOddsVal = [];
	    	for (var i = 0; i < bets[1].length; i++) {
	    		var excludeComp = [];
	    		var dan = [];//同场赛事不同玩法选项时，设胆的话，选项是或关系
	    		for (var j = 0; j < bets[1][i].length; j++) {
	    			if(bets[1][i].length > 1){
	    				excludeComp.push(count);
	    			}
	    			betMaxOddsVal.push(bets[1][i][j]);
	    			if((""+i) in self.options.danListIndex){
	    				dan.push(betMaxOddsVal.length-1);
	    			}
	    			count++;
				}
	    		excludeCompArray.push(excludeComp);
	    		danListIndex.push(dan);
	    	}
	    	for(var j = 0; j < passTypes.length; j++){//循环计算每个过关方式
	    		var mn = passTypes[j].split('@');//串关数组,eg:[3,1]表示3@1
	    		var ms = parseInt(betMaxOddsVal.length);//所选选项数
	            if(mn.length != 2){
	                throw new Error('无效过关方式：' + passTypes[i]);
	            }
	            if(mn[0] > ms){
	                break;
	            }
	            returnVal[1] += $.calculateBetData(mn,ms,betMaxOddsVal,excludeCompArray,danListIndex,"max")*2;
	    	}
	    	//计算最小奖金
	    	excludeCompArray = [];
	    	count = 0;
	    	danListIndex = []; 
	    	var betMinOddsVal = bets[2];
	    	for (var i = 0; i < betMinOddsVal.length; i++) {
	    		var dan = [];
	    		if((""+i) in self.options.danListIndex){
    				dan.push(i);
    			}
	    		danListIndex.push(dan);
	    	}
	    	for(var j = 0; j < passTypes.length; j++){//循环计算每个过关方式
	    		var mn = passTypes[j].split('@');//串关数组,eg:[3,1]表示3@1
	    		var ms = parseInt(betMinOddsVal.length);//所选选项数
	            if(mn.length != 2){
	                throw new Error('无效过关方式：' + passTypes[i]);
	            }
	            if(mn[0] > ms){
	                break;
	            }
	            var v = $.calculateBetData(mn,ms,betMinOddsVal,excludeCompArray,danListIndex,"min")*2;
	            if(returnVal[2] == 0 || v < returnVal[2]){
	            	returnVal[2] = v;
	            }
	    	}
	    	return returnVal;
	    },
	    getOddByBet:function(matchIndex,bet){
	    	var matchesData = this.options.allMatchesData[matchIndex];
			odd = matchesData[bet.p=='02ZC2'?"odds":"odds_"+bet.p].split(",")[bet.j];
			return odd;
		},
	    getOptionByBet:function(matchIndex,bet){
	    	var matchesData = this.options.allMatchesData[matchIndex];
	    	option = matchesData[bet.p=='02ZC2'?"options":"options_"+bet.p].split(",")[bet.j];
			return option;
		},
		isInteger : function (num){
			if(!num){
				return false;
			}
			if(!$.trim(num).match(/^[0-9]*[1-9][0-9]*$/)){
		  		return false;
			}else{
				return true;
			}
		}
	};
  
	$.fn.jcBetPanel = function (option) {
		return this.each(function(input_field) {
			var $this = $(this), data = $this.data('jcBetPanel');
		    if (!data) {//未初始化数据
		    	$this.data('jcBetPanel', (data = new jcBetPanel(this, option)));
		    } else {//已经初始化数据
			    if (option.method){
			    	if(option.method!='addBetMatch' &&
			    			option.method!='delBetMatch' &&
			    			option.method!='addBetMatchComment'){//对外只公开addBetMatch、delBetMatch和addBetMatchComment方法
			    		console.log( 'Method ' + option.method + ' does not exist on jQuery.jcBetPanel' );
			    		return false;
			    	} else {//方法调用
			    		data[option.method](option);
			    	}
			    } else {//默认调用
			    	data.reset(option);
			    }
		    }
		});
	};
	$.fn.jcBetPanel.defaults = {
		betMatchesData:null,
		allMatchesData:null,
		method:null//可选addBetMatch或delBetMatch
	};
})(window.jQuery);