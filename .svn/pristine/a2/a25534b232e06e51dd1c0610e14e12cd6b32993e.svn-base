;
(function($, undefined) {
	/**
	 * 转发微博对话框
	 */
	var forwardDialog = function($this,options,$sourceTarget) {
		this.$body = $this;
		this.$sourceTarget = $sourceTarget;
		this.options = $.extend({}, $.fn.forwardDialog.defaults, options);
		this.$forwardDialogTmpl = $("#forward-dialog-tmpl").html();
		this.initialized();
	};
	forwardDialog.prototype = {
		initialized : function() {
			this.settings = {};
			$.extend(this.settings, LT);
			this.rend();
			this.settings.delegateEvents(this);
		},
		rend : function() {
			this.$body.append($.mustache(this.$forwardDialogTmpl,{}));//将转发模板添加到body
			this.$el = $('[_id="forwardDialog"]');//获取转发对话框
			this.$forwardWindow = this.$el.find('[_id="forwardModal"]'); // 具体的转发窗口
			this.$forwardContent = this.$el.find('[_id="forwardContent"]');
			$('[_id="message_face_forward"]').jqfaceedit({ //一次绑定转发表情
		    	txtAreaObj:this.$forwardContent,
		    	containerObj:$('#emotion_icons'),
		    	textareaid:"forwardContent",
		    	top:30,
		    	left:-40
	    	});
			WB_API.addAtWhoEvent(this.$forwardContent);//一次绑定@用户功能
			this.reset(this.options,this.$sourceTarget);
		},
		events : {
			'change [_id="forwardContent"]':'checkText', //绑定转发内容检查
			'blur [_id="forwardContent"]':'checkText',   //绑定转发内容检查
			'focus [_id="forwardContent"]':'checkText',  //绑定转发内容检查
			'keyup [_id="forwardContent"]':'checkText',  //绑定转发内容检查
			'click #forwardBtn':'forward'				 //绑定转发按钮
		},
		reset : function(option,$sourceTarget) {
			this.$sourceTarget = $sourceTarget;
			this.options = $.extend({}, this.options, option);
			this.show();
			this.checkText();
		},
		show : function (){
			var forwardWeibo = this.options.forwardWeibo;
			this.$el.find('[_id=postId]').val(forwardWeibo.id);
			if (forwardWeibo.postId > 0) {
				this.$forwardContent.val("​ //@" + forwardWeibo.user.nickName + " : "
						+ forwardWeibo.content);
			} else {
				this.$forwardContent.val("");
			}
			this.$forwardWindow.modal('show');
			this.$forwardContent.focus();
		},
		checkText:function (){//检查转发字数
			var _remainingWords =  this.options.forwardWordsMaxLength - this.$forwardContent.val().length;
			if(_remainingWords >= 0){
				this.$el.find('[_id="remainingWords"]').html("还可输入<em style='color:red;'>" + _remainingWords + "</em>字");
			} else {
				this.$el.find('[_id="remainingWords"]').html("<em style='color:red;'>已超出&nbsp" + (0-_remainingWords) + "</em>字");
			}
			return _remainingWords < 0;
		},
		// 转发微博
		forward : function(){
			var $forwradBtn = $("#forwardBtn");
			if(this.checkText()){
				this.$forwardContent.focus();
				$forwradBtn.msgbox('转发字数超出范围了', {success:false});
				return;
			}
			var _this = this;
			var _postId = this.options.forwardWeibo.id;
//			this.$forwardContent.val($("<div/>").text(this.$forwardContent.val()).html());
			var par = $('[_id="forwardDialog"]').serialize();
			$forwradBtn.attr("disabled",true);
			$.post("http://www.davcai.com/publish", par, function(result, e) {
				if (result != null) {
					if (result.success) {
						var postresult = result.results;
						if(postresult[0].id < 1){
							$forwradBtn.msgbox('转发失败', {success:false});
							return;
						}
						var $sbtn = _this.$sourceTarget;
						if($sbtn.length > 0){
							var count = $sbtn.attr("_s");
							count++;
							$sbtn.attr("_s",count);
							$sbtn.text("转发("+count+")");
						}
						$forwradBtn.msgbox('转发成功', {success:true});
						setTimeout(function (){
							_this.$forwardWindow.modal('hide')
						},300);
					} else {
						$forwradBtn.msgbox('转发失败', {success:false});
					}
				}
				$forwradBtn.attr("disabled",false);
			}, 'jsonp').error(function(e) {
				$forwradBtn.msgbox('转发失败', {success:false});;
				$forwradBtn.attr("disabled",false);
			});
		}
	};

	$.fn.forwardDialog = function(option) {
		return this.each(function(input_field) {
			var $this = $('body'),
				data = $this.data('forwardDialog');
		    if (!data) {
		    	$this.data('forwardDialog', (data = new forwardDialog($this, option,$(this))));
		    } else {
		    	data.reset(option,$(this));
		    }
			// option 是方法名
			if (typeof option == 'string') {
				if (!data[option]) {
					console.log('Method ' + option
							+ ' does not exist on jQuery.forwardDialog');
				} else {
					data[option]();
				}
			}
		});
	};
	$.fn.forwardDialog.defaults = {
		forwardWeibo : null,
		forwardWordsMaxLength : 1000
	};
})(window.jQuery);
