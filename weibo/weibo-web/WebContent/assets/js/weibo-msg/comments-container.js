;(function($, undefined) {
	/**
	 * 评论容器
	 */
	var commentContainer = function(element,options){
	    this.$el = $(element);
	    this.options = $.extend({}, $.fn.commentContainer.defaults, options); 
	    this.$commentContainerTmpl = $("#comments-container-tmpl");
	    this.$loading = $($("#loading-tmpl").html());
	    this.initialized();
	};
	commentContainer.prototype = {
	    initialized : function(){
	    	this.settings = {};
	    	$.extend(this.settings, LT);
	    	this.settings.delegateEvents(this);//继承events事件
	    	this.rend();
	    },
	    rend : function(){
	    	this.$commentContainer = $($.mustache(this.$commentContainerTmpl.html(),{}));
	    	this.options.appendPosi.append(this.$commentContainer);
	    	if(this.$commentContainer.parent().hasClass('commentContainer')){
	    		this.options.appendPosi.parent().children("div[weibo-footer-element]").hide();
	    		this.$commentContainer.parent().show();//评论微博的评论框
	    	} else {
	    		this.$commentContainer.show();
	    	}
	    	this.adjustArrowPos();
	    	if(this.options.isLoadCommentList){//如果为true，则加载评论列表，默认false
		    	this.$commentContainer.commentList({
		    		$sourceTarget:this.$el,
		    		postId:this.options.postId
		    	});
	    	}
	    	this.bind();//绑定commentContainer事件
	    },
	    bind:function (){
	    	var commentTextArea = this.$commentContainer.find("textarea");
	    	//绑定$事件
	    	WB_API.addAtWhoEvent(commentTextArea[0]);
	    	//绑定@事件
	    	WB_API.addNickNameAtWho(commentTextArea[0]);
	    	//绑定点击赛事按钮事件
	    	WB_API.showMatchAreaDelegateEventsWith(commentTextArea.closest(".commentPostArea"));
	    	
	    	//为每一个评论框生成一个唯一ID
	    	var commentTextAreaId = commentTextArea.attr("id");
	    	if(!commentTextAreaId) {
	    		commentTextAreaId = new Date().getTime();
	    		commentTextArea.attr("id", commentTextAreaId);
	    	}
	    	//绑定插入表情事件
	    	commentTextArea.closest('.commentPostArea')
	    		.find(".editor>.selector>.face[comment_face]").jqfaceedit({
		        	txtAreaObj:$("#" + commentTextAreaId),
		        	containerObj:$('#emotion_icons'),
		        	textareaid:commentTextAreaId,
		        	top:30,
		        	left:-40
	        });
	    	//单击发布评论按钮事件
	    	this.$commentContainer.find('[_id="submit-comment-content"]').click({
	    		self:this
	    	},this.submitComment);
	    	this.$commentContainer.find('textarea').val(this.options.commentContent);
	    },
	    events : {
	    	
	    },
	    reset:function (option){
	    	if(this.$commentContainer.parent().hasClass('commentContainer')){
	    		this.options.appendPosi.parent().children(':not([comment-container-div])').hide();
	    		this.$commentContainer.parent().toggle();//评论微博的评论框
	    	} else {
	    		this.$commentContainer.toggle();//回复评论的回复框
	    	}
	    	this.adjustArrowPos();
	    },
	    adjustArrowPos : function (){
	    	var linkLeft = this.$el.offset().left;
	    	var offset = {};
	    	var textAreaLeft = 0;
	    	offset = this.$commentContainer.offset();
	    	if(offset){
	    		textAreaLeft = offset.left;
	    	} else {
	    		textAreaLeft = 500;
	    	}
	    	var left = linkLeft - textAreaLeft + this.$el.width()/2;
	    	this.$commentContainer.find('.arrow-top-out,.arrow-top-in').css({'left':left});
	    },
	    submitComment:function (event){
	    	var self = event.data.self;
    		var textarea = self.$commentContainer.find('textarea');
    		if (textarea.val()==''){
    			return;
    		}
    		var commentText = textarea.val();
    		if (!self.validateCommentText(commentText)){
    			return;
    		}
    		var data = {
    			pid: self.options.postId,
				forward: self.$commentContainer.find('input:checkbox').is(':checked'),
				comment: textarea.val()
			};
    		if(self.options.cid){
    			data = $.extend({},data,{cid:self.options.cid});
    		}
    		$.ajax(self.options.postCommentURL, {
    			dataType: 'jsonp',
    			method: 'post',
    			cache: false,
    			data: data,
    			success: function(result){
    				//评论完成后重新渲染评论列表
			    	self.$commentContainer.commentList({
			    		$sourceTarget:self.$el,
			    		postId:self.options.postId
			    	});
    			},
    			error: function(){
    				self.$loading.hide();
    				$.msgBox('评论发布失败', null, {pos:'center', success: false});
    			}
    		});
    		textarea.val('');
	    },
	    validateCommentText : function (commentText){
	    	if ($.isBlankStr(commentText)) {
	    		$.msgBox('请输入内容后再提交。', null, 
	    				{pos:'center', success: false});
	    		return false;
	    	}
	    	if (commentText.length>600){
	    		$.msgBox('评论字数超长，请节约用字，不要超过600字。', null, 
	    				{pos:'center', success: false});
	    		return false;
	    	}
	    	return true;
	    }
	};
  
	$.fn.commentContainer = function (option) {
		return this.each(function(input_field) {
			var $this = $(this), data = $this.data('commentContainer');
		    if (!data) {
		    	$this.data('commentContainer', (data = new commentContainer(this, option)));
		    } else {
		    	data.reset(option);
		    }
		    //option 是方法名
		    if (typeof option == 'string'){
		    	if(!data[option]){
		    		console.log( 'Method ' + option + ' does not exist on jQuery.commentContainer' );
		    	} else {
		    		data[option]();
		    	}
		    }
		});
	};
	$.fn.commentContainer.defaults = {		
		isLoadCommentList:false,
		postCommentURL:LT.Settings.URLS.comment.postComment,
		postId:null,
		cid:null,
		appendPosi:null,
		commentContent:''
	};
})(window.jQuery);
