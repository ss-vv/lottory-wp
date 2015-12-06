;
(function($, undefined) {
	/**
	 * 分享微博对话框
	 */
	var shareDialog = function($this,options,$sourceTarget) {
		this.$body = $this;
		this.$sourceTarget = $sourceTarget;
		this.options = $.extend({}, $.fn.shareDialog.defaults, options);
		this.$shareDialogTmpl = $("#share-dialog-tmpl").html();
		this.initialized();
	};
	shareDialog.prototype = {
		initialized : function() {
			this.settings = {};
			$.extend(this.settings, LT);
			this.rend();
			this.settings.delegateEvents(this);
		},
		rend : function() {
			this.$body.append($.mustache(this.$shareDialogTmpl,{}));//将分享模板添加到body
			this.$el = $('[_id="shareDialog"]');//获取分享对话框
			this.$shareWindow = this.$el.find('[_id="shareModal"]'); // 具体的分享窗口
			this.$shareContent = this.$el.find('#shareContent');
			$('[_id="message_face_share"]').jqfaceedit({ //一次绑定分享表情
		    	txtAreaObj:this.$shareContent,
		    	containerObj:$('#emotion_icons'),
		    	textareaid:"shareContent",
		    	top:30,
		    	left:-40
	    	});
			WB_API.addAtWhoEvent(this.$shareContent);//一次绑定@用户功能
			this.reset(this.options,this.$sourceTarget);
		},
		events : {
			'change #shareContent':'checkText', //绑定分享内容检查
			'blur #shareContent':'checkText',   //绑定分享内容检查
			'focus #shareContent':'checkText',  //绑定分享内容检查
			'keyup #shareContent':'checkText',  //绑定分享内容检查
			'click #sharePostBtn':'share'				 //绑定分享按钮
		},
		reset : function(option,$sourceTarget) {
			this.$sourceTarget = $sourceTarget;
			this.options = $.extend({}, this.options, option);
			var shareWeibo = $.trim(this.options.shareWeibo);//微博内容
			if(this.options.weiboLinkUrl.length > 0 && "/" == this.options.weiboLinkUrl.substring(0,1)){
				this.options.weiboLinkUrl = this.options.weiboLinkUrl.substring(1);
			}
			this.tail = LT.Settings.LT_BASE_URL + this.options.weiboLinkUrl +  " (分享自@大V彩)";//分享的尾巴
			var beyondLength = shareWeibo.length + this.tail.length - this.options.shareWordsMaxLength;
			if(beyondLength > 0){
				shareWeibo = shareWeibo.substr(0,shareWeibo.length-beyondLength -3) + "…";
			}
			this.$shareContent.val(shareWeibo+ "  " + this.tail);
			this.show();
			this.checkText();
		},
		show : function (){
			this.$shareWindow.modal('show');
			this.$shareContent.focus();
		},
		checkText:function (){//检查分享字数
			if(!this.$shareContent.val()){
				return true;
			}
			var _remainingWords =  this.options.shareWordsMaxLength - this.$shareContent.val().length;
			if(_remainingWords >= 0){
				this.$el.find('#shareContentRemainingWords').html("还可输入<em style='color:red;'>" + _remainingWords + "</em>字");
			} else {
				this.$el.find('#shareContentRemainingWords').html("<em style='color:red;'>已超出&nbsp" + (0 - _remainingWords) + "</em>字");
			}
			return _remainingWords < 0;
		},
		// 分享微博
		share : function(event){
			var $forwradBtn = $("#shareBtn");
			if(this.checkText()){
				this.$shareContent.focus();
				return;
			}
			var sinaCheck = $('[_id="sinaWeiboCheck"]').attr("checked");
			if(sinaCheck != 'checked'){
				$('[_id="tipBindInfo"]').html("去绑定帐号或选勾已绑定帐号");
				return;
			}
			if($.trim(this.$shareContent.val()) == ""){
				this.$shareContent.focus();
				return;
			}
			var $shareBtn = $(event.target);
			var par = $('[_id="shareDialog"]').serialize();
			$shareBtn.attr("disabled",true);
			var _this = this;
			$.post("share", par, function(result, e) {
				if(result.success == true){
					$.msgbox(result.desc,$shareBtn,{success:true});
					setTimeout(function (){
						_this.$shareWindow.modal('hide');
					},300);
				} else {
					$.msgbox(result.desc,$shateBtn,{success:false});
				}
				$shareBtn.attr("disabled",false);
			},'json');
		}
	};

	$.fn.shareDialog = function(option) {
		return this.each(function(input_field) {
			var $this = $('body'),
				data = $this.data('shareDialog');
		    if (!data) {
		    	$this.data('shareDialog', (data = new shareDialog($this, option,$(this))));
		    } else {
		    	data.reset(option,$(this));
		    }
			// option 是方法名
			if (typeof option == 'string') {
				if (!data[option]) {
					console.log('Method ' + option
							+ ' does not exist on jQuery.shareDialog');
				} else {
					data[option]();
				}
			}
		});
	};
	$.fn.shareDialog.defaults = {
		shareWeibo : '',
		shareWordsMaxLength : 140,
		weiboLinkUrl:LT.Settings.LT_BASE_URL
	};
})(window.jQuery);
