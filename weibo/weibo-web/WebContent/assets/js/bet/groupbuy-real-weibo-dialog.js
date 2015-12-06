;(function($, undefined) {
	/**
	 * 微博参与合买窗口
	 */
	var groupBuyRealWeiboDialog = function(element,options){
	    this.$el = $(element);
	    this.options = $.extend({}, $.fn.groupBuyRealWeiboDialog.defaults, options); 
	    this.$groupBuyRealWeiboDialogTmpl = $("#weibo-groupbuy-form-tmpl");
	    this.initialized();
	};
	groupBuyRealWeiboDialog.prototype = {
	    initialized : function(){
	    	this.settings = {};
	    	$.extend(this.settings, LT);
	    	this.settings.delegateEvents(this);//继承events事件
	    	this.rend();
	    },
	    rend : function(){
	    	var post = this.options.post;
	    	this.$groupBuyRealWeiboDialog = $($.mustache(this.$groupBuyRealWeiboDialogTmpl.html(),{
				postId:post.id,
				followRealWeiboContent:"我参与了  @" + post.nickName +" 发起的合买方案，",
				schemeId:post.betScheme.id,
				maxBonus:LTMath.roundNumber(post.betScheme.maxBonus,2),
				totalAmount:LTMath.roundNumber(post.betScheme.totalAmount,2),
				canBuyAmount:post.betScheme.totalAmount - post.betScheme.purchasedAmount,
				floorAmount:function() {
					if(post.betScheme.floorAmount <= 0) {
						return "无";
					}
					return post.betScheme.floorAmount + "元";
				},
				shareRatio:post.betScheme.shareRatio
			}));
	    	this.options.appendPosi.append(this.$groupBuyRealWeiboDialog);
	    	this.options.appendPosi.children("div[weibo-footer-element]").hide();
	    	this.$groupBuyRealWeiboDialog.show();
	    	this.bind();//绑定groupBuyRealWeiboDialog事件
	    },
	    bind:function (){
	    	this.bindFollowFormEvent(this.$groupBuyRealWeiboDialog);
	    },
	    events : {
	    	
	    },
	    reset:function (option){
	    	this.options.appendPosi.children(':not([weibobetformdiv])').hide();
	    	this.$groupBuyRealWeiboDialog.toggle();
	    },
	    bindFollowFormEvent:function ($context){
	    	var self = this;
	    	//绑定插入表情事件、赛事事件、统计字数
	    	$(".editor>.selector>.face[follow_face]",$context).each(function (){
	    		var $this = $(this);
	    		var $textArea = $context.find("[weiboBetTextarea]");
	    		var txtId = $textArea.attr("id");
	    		if(!txtId) {
	    			txtId = new Date().getTime();
	    			$textArea.attr("id", txtId);
	    		}
	    		var postId = $this.attr("_postid");
	    		$this.jqfaceedit({
	    	    	txtAreaObj:$("#"+txtId),
	    	    	containerObj:$('#emotion_icons'),
	    	    	textareaid:txtId,
	    	    	top:30,
	    	    	left:-40
	    	    });
	    		//绑定$事件
	    		WB_API.addAtWhoEvent($("#"+txtId)[0]);
	    		//绑定@事件
	    		WB_API.addNickNameAtWho($("#"+txtId)[0]);
	    		//绑定点击赛事按钮事件
	    		WB_API.showMatchAreaDelegateEventsWith($("#"+txtId).closest("form"));
	    		
	    		var $restWordsDiv = $textArea.next();
	    		var followTextareaRestwords = function ($this){
	    			var length = $this.val().length;
	    			if(length > 1000){
	    				$restWordsDiv.html("还可以输入0个字符");
	    				$this.val($this.val().substring(0,1000));
	    			} else {
	    				$restWordsDiv.html("还可以输入"+(1000-length)+"个字符");
	    			}
	    		};
	    		$restWordsDiv.html("还可以输入"+(1000 - $textArea.val().length)+"个字符");
	    		$textArea.keyup(function (e){
	    			followTextareaRestwords($(this));
	    		});
	    		$textArea.change(function (e){
	    			followTextareaRestwords($(this));
	    		});
	    		$textArea.keydown(function (e){
	    			followTextareaRestwords($(this));
	    		});
	    		$textArea.click(function (){
	    			$(this).css("color","#000");
	    		});
	    	});
	    	
	    	//阻止浏览器回车自动提交表单
	    	$("input[name='buyAmount']",$context).keydown(function (e){
	    		if(e.keyCode==13){
	    			e.preventDefault();
	    		}
	    	});
	    	
	    	//合买认购金额改变事件触发
	    	$("input[name='buyAmount']",$context).keyup(function (e){
	    		var $this = $(this);
	    		var canBuy = $("span[_n='canBuy']",$context).text(); 
	    		var buyAmount = $this.val();
	    		if(!/^\d+$/.test(buyAmount) && buyAmount) {
	    			$this.val(1);
	    		} else if(parseInt(buyAmount) > parseInt(canBuy)) {
	    			$this.val(canBuy);
	    		}
	    		
	    		if(e.keyCode==13){//通过触发“立即投注”按钮点击事件来提交表单
	    			e.preventDefault();
	    			$('input[_n="groupbuy_schema_confirm_btn"]',$context).trigger("click");
	    		}
	    	});
	    	//实单微博确认合买事件
	    	$('input[_n="groupbuy_schema_confirm_btn"]',$context).click(function (){
	    		var $this = $(this);
	    		var $form = $this.closest("form");
	    		var groupbuyAmountElt = $('input[name="buyAmount"]',$form);
	    		var buyAmount = groupbuyAmountElt.val();
	    		if(!buyAmount || buyAmount <= 0){
	    			groupbuyAmountElt.attr("value","1");
	    			groupbuyAmountElt.focus();
	    			return false;
	    		}
	    		if(!confirm("您确认参与合买？")){
	    			return false;
	    		} else {
	    			$.blockUI({  
	    			     overlayCSS:{backgroundColor:'#FFFFFF'},  
	    			     message:'方案已提交，正在处理中，请勿重复提交！',  
	    			     css:{  
	    				      backgroundColor:'#FFFFFF',  
	    				      height:50  
	    			     }  
	    		     }); 
	    			$.ajax({
	    				url: "http://trade.davcai.com/ac/betconfirm_in_weibo.do?jsonp=?", 
	    				data: $form.serialize(),
	    				success: function (json){
	    					var result = json.data;
	    					if(result && result.success == true) {
	    						var canBuy = $("span[_n='canBuy']",$context);
	    						var amount = canBuy.text() - buyAmount;
	    						canBuy.text(amount);
	    						$('input[name="buyAmount"]',$form).val(amount);
	    					}
	    					$.unblockUI({
	    						onUnblock:function (){
	    							if(result && result.success == true) {
	    								$.msgBox(result.data, null, {pos:'center', success: result.success});
	    								if(json.appCode == -1 || json.appCode == 0 || json.saleStatus != 0) {
	    									self.updateGroupTagProgress(json);
	    								}
	    								if(json.appCode == -1 || json.saleStatus != 0) {
	    									self.updateGroupBuyBtnInWeibo();
	    								} else {//更新合买人数
	    									self.$schemeGroupBuyBtn = self.options.appendPosi.closest("li[weiboMsgLi]").find('.bet-follow');
	    							    	var value = self.$schemeGroupBuyBtn.text();
	    							    	var pt = /\((\d+)\)/;
	    							    	var groups = pt.exec(value);
	    							    	var count = 0;
	    							    	if(null != groups){
	    							    		count = parseInt(groups[1]);
	    							    		count += 1;
	    							    		if(count > 0) {
	    							    			self.$schemeGroupBuyBtn.html("我要合买("+count+")");
	    							    		} else {
	    							    			self.$schemeGroupBuyBtn.html("我要合买");
	    							    		}
	    							    	} else {
	    							    		self.$schemeFollowBtn.html("我要合买("+1+")");
	    							    	}
	    								}
	    								self.updateFollowersList($context,$form,buyAmount);
	    							} else {
	    								$.msgBox('操作失败！', null, {pos:'center', success: false});
	    							}
	    							$context.hide();
	    						}
	    					});
	    				},
	    				dataType:"jsonp"
	    			});
	    		}
	    	});
	    },
	    updateGroupTagProgress : function(json){
	    	var progressTag = $("span[_tagToSchemeId=" + json.schemeId + "]");
			if(progressTag && progressTag.length > 0) {
				var tagLabel = "进度";
				for(var index=0; index<progressTag.length; index++) {
					var tag = progressTag[index];
					var content = $(tag).text();
					if(content && content.indexOf(tagLabel) >= 0) {
						$(tag).text(tagLabel + json.progress + "%");
					}
				}
			}
	    },
	    updateGroupBuyBtnInWeibo : function(){
	    	this.$weiboGroupBuyBtn = this.options.appendPosi.closest("li[weiboMsgLi]").find('.groupbuy-button');
	    	this.$schemeGroupBuyBtn = this.options.appendPosi.closest("li[weiboMsgLi]").find('.bet-follow');
	    	var $td = this.$schemeGroupBuyBtn.closest("td");
    		$td.prev().attr("colspan",4);
    		$td.remove();
	    	this.$weiboGroupBuyBtn.hide();
	    },
	    updateFollowersList : function ($context,$form,buyAmount){
	    	var self = this;
	    	var $realFollowUserDiv = $context.closest("li[weiboMsgLi]").find('[_id="real-follower-div"]');
			$.getJSON('http://login.davcai.com/ajax_get_weibouser.do?jsonp=?', function(data){
	    		if (data != null) {
				   var weiboUser = data.weiboUser;
				   self.options.post.realFollowers.push({
						nickName:weiboUser.nickName,
						headImageURL:weiboUser.headImageURL,
						weiboUserId:weiboUser.weiboUserId,
						buyAmount:buyAmount
					});
					$realFollowUserDiv.realWeiboFollowUsers({
						postId:self.options.post.id,
						realFollowers:self.options.post.realFollowers
					});
	    		}
			});
	    }
	};
	
	$.fn.groupBuyRealWeiboDialog = function (option) {
		return this.each(function(input_field) {
			var $this = $(this), data = $this.data('groupBuyRealWeiboDialog');
		    if (!data) {
		    	$this.data('groupBuyRealWeiboDialog', (data = new groupBuyRealWeiboDialog(this, option)));
		    } else {
		    	data.reset(option);
		    }
		    //option 是方法名
		    if (typeof option == 'string'){
		    	if(!data[option]){
		    		console.log( 'Method ' + option + ' does not exist on jQuery.groupBuyRealWeiboDialog' );
		    	} else {
		    		data[option]();
		    	}
		    }
		});
	};
	$.fn.groupBuyRealWeiboDialog.defaults = {		
		post:null,
		appendPosi:null,
		currentUserId:null
	};
})(window.jQuery);
