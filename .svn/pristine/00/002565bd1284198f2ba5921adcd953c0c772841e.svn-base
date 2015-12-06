;(function($, undefined) {
	/**
	 * 微博实单跟单窗口
	 */
	var followRealWeiboDialog = function(element,options){
	    this.$el = $(element);
	    this.options = $.extend({}, $.fn.followRealWeiboDialog.defaults, options); 
	    this.$followRealWeiboDialogTmpl = $("#weibo-real-follow-form-tmpl");
	    this.initialized();
	};
	followRealWeiboDialog.prototype = {
	    initialized : function(){
	    	this.settings = {};
	    	$.extend(this.settings, LT);
	    	this.settings.delegateEvents(this);//继承events事件
	    	this.rend();
	    },
	    rend : function(){
	    	var post = this.options.post;
	    	this.$followRealWeiboDialog = $($.mustache(this.$followRealWeiboDialogTmpl.html(),{
				postId:post.id,
				followRealWeiboContent:"我跟了  @" + post.nickName +" 发起的晒单方案。",
				schemeId:post.betScheme.id,
				maxBonus:LTMath.roundNumber(post.betScheme.maxBonus,2),
				totalAmount:LTMath.roundNumber(post.betScheme.totalAmount,2),
				danbeiAmount:post.betScheme.totalAmount/post.betScheme.multiple
			}));
	    	this.options.appendPosi.append(this.$followRealWeiboDialog);
	    	this.options.appendPosi.children("div[weibo-footer-element]").hide();
	    	this.$followRealWeiboDialog.show();
	    	this.bind();//绑定followRealWeiboDialog事件
	    },
	    bind:function (){
	    	this.bindFollowFormEvent(this.$followRealWeiboDialog);
	    },
	    events : {
	    	
	    },
	    reset:function (option){
	    	this.options.appendPosi.children(':not([weibobetformdiv])').hide();
	    	this.$followRealWeiboDialog.toggle();
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
	    	$('input[_n="follow_multiple"]',$context).keydown(function (e){
	    		if(e.keyCode==13){
	    			e.preventDefault();
	    		}
	    	});
	    	//跟单倍数改变时，计算购买金额
	    	$('input[_n="follow_multiple"]',$context).keyup(function (e){
	    		var $this = $(this);
	    		var totalAmount = $this.attr("_danbeiAmount");
	    		var mul = $this.attr("value");
	    		if(!LTMath.isInteger(mul)){
	    			$this.attr("value","");
	    			$this.focus();
	    			return false;
	    		} else {
	    			if(parseInt(mul) > 9999){
	    				$this.attr("value","");
	    				alert("不能大于9999倍");
		    			$this.focus();
		    			return false;
	    			}
	    			var followAmount = parseInt(mul) *  totalAmount;
	    			if(followAmount > 1000000){
	    				var ex = (followAmount-1000000)/totalAmount;
	    				var left = (followAmount-1000000)%totalAmount;
	    				mul = parseInt(mul) - parseInt(ex) - (left > 0 ? 1:0);
	    				$this.attr("value",mul);
	    				alert("投注金额超出100万元，\n已将倍数重置为当前可投注的最大倍数");
		    			$this.focus();
		    			followAmount = mul * totalAmount;
	    			}
	    			var $form = $this.closest("form");
	    			$("[_followAmount]",$form).html(followAmount + "&nbsp;元");
	    		}
	    		if(e.keyCode==13){//通过触发“立即投注”按钮点击事件来提交表单
	    			e.preventDefault();
	    			$('input[_n="follow_schema_confirm_btn"]',$context).trigger("click");
	    		}
	    	});
	    	//实单微博确认跟单事件
	    	$('input[_n="follow_schema_confirm_btn"]',$context).click(function (){
	    		var $this = $(this);
	    		var $form = $this.closest("form");
	    		var mulEle = $('input[name="multiple"]',$form);
	    		var mul = mulEle.attr("value");
	    		if(!LTMath.isInteger(mul)){
	    			mulEle.attr("value","");
	    			mulEle.focus();
	    			return false;
	    		}
	    		if(!confirm("确认跟单？")){
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
	    					var rs = json.data;
	    					$.unblockUI({
	    						onUnblock:function (){
	    							if(rs) {
	    								$.msgBox(rs.data, null, {pos:'center', success: rs.success});
	    							} else {
	    								$.msgBox('操作失败！', null, {pos:'center', success: false});
	    							}
	    							$context.hide();
	    							if(true == rs.success){
	    								self.updateFollowBtnCountInWeibo(1);
	    								self.updateFollowersList($context,$form);
	    							}
	    						}
	    					});
	    				},
	    				dataType:"jsonp"
	    			});
	    		}
	    	});
	    },
	    updateFollowBtnCountInWeibo : function(i){
	    	this.$weiboFollowBtn = this.options.appendPosi.closest("li[weiboMsgLi]").find('.follow-button');
	    	this.$schemeFollowBtn = this.options.appendPosi.closest("li[weiboMsgLi]").find('.bet-follow');
	    	var value = this.$weiboFollowBtn.val();
	    	var pt = /\((\d+)\)/;
	    	var groups = pt.exec(value);
	    	var count = 0;
	    	if(null != groups){
	    		count = parseInt(groups[1]);
	    		count += i;
	    		if(count > 0) {
	    			this.$weiboFollowBtn.val("跟单("+count+")");
	    			this.$schemeFollowBtn.html("我要跟单("+count+")");
	    		} else {
	    			this.$weiboFollowBtn.val("跟单");
	    			this.$schemeFollowBtn.html("我要跟单");
	    		}
	    	} else {
	    		this.$weiboFollowBtn.val("跟单("+1+")");
	    		this.$schemeFollowBtn.html("我要跟单("+1+")");
	    	}
	    	return count;
	    },
	    updateFollowersList : function ($context,$form){
	    	var self = this;
	    	var $realFollowUserDiv = $context.closest("li[weiboMsgLi]").find('[_id="real-follower-div"]');
			$.getJSON('http://login.davcai.com/ajax_get_weibouser.do?jsonp=?', function(data){
	    		if (data != null) {
				   var weiboUser = data.weiboUser;
				   var danbei = $form.find('input[_n="follow_multiple"]').attr('_danbeiAmount');
				   var mul = $form.find('input[_n="follow_multiple"]').val();
				   self.options.post.realFollowers.push({
						nickName:weiboUser.nickName,
						headImageURL:weiboUser.headImageURL,
						buyAmount:danbei * mul,
						weiboUserId:weiboUser.weiboUserId
					});
					$realFollowUserDiv.realWeiboFollowUsers({
						postId:self.options.post.id,
						realFollowers:self.options.post.realFollowers
					});
	    		}
			});
	    }
	};
  
	$.fn.followRealWeiboDialog = function (option) {
		return this.each(function(input_field) {
			var $this = $(this), data = $this.data('followRealWeiboDialog');
		    if (!data) {
		    	$this.data('followRealWeiboDialog', (data = new followRealWeiboDialog(this, option)));
		    } else {
		    	data.reset(option);
		    }
		    //option 是方法名
		    if (typeof option == 'string'){
		    	if(!data[option]){
		    		console.log( 'Method ' + option + ' does not exist on jQuery.followRealWeiboDialog' );
		    	} else {
		    		data[option]();
		    	}
		    }
		});
	};
	$.fn.followRealWeiboDialog.defaults = {		
		post:null,
		appendPosi:null,
		currentUserId:null
	};
})(window.jQuery);
