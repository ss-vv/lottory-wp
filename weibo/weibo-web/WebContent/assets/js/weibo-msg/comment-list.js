;(function($, undefined) {
	/**
	 * 评论列表
	 */
	var commentList = function(element,options){
	    this.$el = $(element);
	    this.options = $.extend({}, $.fn.commentList.defaults, options); 
	    this.$commentListTmpl = $("#comment-list-tmpl");
	    this.$loading = $($("#loading-tmpl").html());
	    this.initialized();
	};
	commentList.prototype = {
	    initialized : function(){
	    	this.settings = {};
	    	$.extend(this.settings, LT);
	    	this.settings.delegateEvents(this);//继承events事件
	    	this.$weiboMetaComment = this.options.$sourceTarget.closest('li[weiboMsgLi]').find('a[_n="comment"]');
	    	this.rend();
	    },
	    rend : function(){
	    	if(this.$el.find('.loading').length < 1){
	    		this.$el.append(this.$loading);
	    	}
	    	this.$loading.show();
	    	this.$post = this.$el.closest('li[weiboMsgLi]');
	    	this.loadComments(this.options.postId);
	    },
	    events:function (){
	    	
	    },
	    reset:function (option){
	    	this.options = $.extend({}, this.options, option);
	    	this.rend();
	    },
	    loadComments: function (postId){
	    	var self = this;
		    $.ajax(this.options.loadCommentUrl + '?pid='+postId, {
				dataType: 'jsonp',
				cache: false,
				success: function(result) {//评论获取成功后，渲染和绑定评论列表事件
					self.$loading.hide();
					//更新微博meta中评论数
					if(result.data.length > 0) {
						self.$weiboMetaComment.text("评论("+result.data.length+")");
		    		} else {
		    			self.$weiboMetaComment.text("评论");
		    		}
					var loginUserId = result.userId;
					var cmnts = {//渲染评论列表的数据对象
						comments: result.data,
						commentCount: result.data.length, 
						like: function(){
							return this.praised?"unlike":"like";
						},
						authorHomeURL: function(){
							return self.authorHomeURLById(this.author.weiboUserId);
						},
						praiseText: function(){
							var text = this.praised ? '已赞' : '赞';
							if (this.praisedCount>0){
								text += '(' + this.praisedCount + ')';
							}
							return text;
						},
						isOwner: function(){
							return this.author.weiboUserId == loginUserId;
						},
						createTimeLong: function(){
							var date = $.strToDate(this.createTime);
							return date.getTime();
						},
						createTimeSpan: function(){
							return $.shortTime(this.createTime);
						},
						replyTo: function(){
							if (this.repliedComment){
								var homeURL = self.authorHomeURLById(this.repliedUser.weiboUserId);
								return ' 回复 '+ '<a href="'+homeURL+'">'+this.repliedUser.nickName+'</a>';
							}else{
								return '';
							}
						}
					};
					var tpl = $($.mustache(self.$commentListTmpl.html(), cmnts));
					
					//转换评论中的@用户
					tpl.find(".cmt_con").each(function(index, elt) {
						$(elt).at();
					});
					//转换赛事链接
					tpl.match$ToHtml();
					//转换表情图标
					tpl.emotionsToHtml();
					
					tpl.find('li:last').addClass('last');
					self.addLikeHandler(tpl);
					self.addReplyHandler(tpl, self.$post, postId);
					self.addDeleteHandler(tpl);
					self.addHoverHandler(tpl);
					// 插入或替换DOM树
					var commentList = self.$post.find('.commentInfo');
					var hasComments = result.data.length;
					if (commentList.length > 0){
						commentList.replaceWith(tpl);
					}else if (hasComments>0){
						self.$post.find('.commentContainer').append(tpl);
					}
				},
				error: function(){
					self.$loading.hide();
					$.msgBox('评论列表加载失败', null, {pos:'center', success: false});
				}
			});
	    },
	    authorHomeURLById : function(weiboUserId){
			return '/'+weiboUserId+'/profile';
		},
	    addHoverHandler:function(comments){
	    	comments.find('.commentList li').hover(
	    		function(){
	    			$(this).find('.deleteComment').css({'visibility': 'visible'});
	    		},
	    		function(){
	    			$(this).find('.deleteComment').css({'visibility': 'hidden'});
	    		}
	    	);
	    },
	    addLikeHandler:function(comments){
	    	var like = comments.find('.like,.unlike');
	    	like.on('click', function(event){
	    		event.preventDefault();
	    		var self = $(this);
	    		var commentId = self.closest('li').attr('_comment');
	    		var praiseOrUnpraise = self.hasClass('like');
	    		$.ajax('http://www.davcai.com/prs_cmnt',{
	    			dataType: 'jsonp',
	    			data: {
	    				cid: commentId,
	    				add: praiseOrUnpraise
	    			},
	    			success: function(result){
	    				var count = parseInt(self.attr('like_count'));
	    				count = praiseOrUnpraise ? count+1 : Math.max(0,count-1);
	    				var likeText = '赞';
	    				if (praiseOrUnpraise){
	    					self.removeClass('like');
	    					self.addClass('unlike');
	    					likeText = '已赞';
	    				}else{
	    					self.removeClass('unlike');
	    					self.addClass('like');
	    					likeText = '赞';
	    				}
	    				self.attr('like_count', count);
	    				if (count>0){
	    					self.text(likeText+'('+count+')');
	    				}else {
	    					self.text(likeText);
	    				}
	    			},
	    			error: function(){
	    				$.msgBox('赞失败。', null, {pos:'center', success: false});
	    			}
	    		});
	    	});
	    },
	    addDeleteHandler:function(comments) {
	    	var self = this;
	    	var deleteLink = comments.find('.deleteComment');
	    	deleteLink.on('click', function(event){
	    		event.preventDefault();
	    		var $this = $(this);
	    		var commentId = $this.closest('li').attr('_comment');
	    		var cmntLi = $this.closest('li');
		    	var allCmnt = cmntLi.closest('.commentInfo');
		    	$.ajax('http://www.davcai.com/del_cmnt', {
		    		dataType: 'jsonp',
		    		data: {
		    			cid: commentId
		    		},
		    		success: function(result){
		    			if(result.success){
		    				$.msgBox('删除成功！', null, {pos:'center'});
		    				self.updateCmtCountInWeiboMeta(-1);
		    				var cmntCount = updateCommentCount(allCmnt);
		    				if (cmntCount == 0)
		    					allCmnt.remove();
		    				else
		    					cmntLi.remove();
		    			}else{
		    				$.msgBox(result.desc, null, {pos:'center', success: false});
		    			}
		    		},
		    		error: function(result){
		    			$.msgBox('因网络问题，删除失败。', null, {pos:'center', success: false});
		    		}
		    	});
	    	});
	    },
	    addReplyHandler:function(commentInfo, post, postId){
	    	var replies = commentInfo.find('.reply');
	    	var commentsLoading = post.find('.loading');
	    	replies.click(function (event){
	    		event.preventDefault();
	    		var $commentLi = $(this).closest('li');
	    		var commentContent = '回复@' + $commentLi.find('a[data-name]').attr('data-name') + ':';
		    	$(this).commentContainer({
		    		cid:$commentLi.attr('_comment'),
		    		postId:postId,
		    		appendPosi:$(this).closest('li'),
		    		isLoadCommentList:false,
		    		commentContent:commentContent
		    	});
	    	});
	    },
	    updateCmtCountInWeiboMeta : function(i){
	    	var $cmt = this.$weiboMetaComment;
	    	var cmntCountText = $cmt.text();
	    	var pt = /\((\d+)\)/;
	    	var groups = pt.exec(cmntCountText);
	    	var count = 0;
	    	if(null != groups){
	    		count = parseInt(groups[1]);
	    		count += i;
	    		if(count > 0) {
	    			$cmt.text("评论("+count+")");
	    		} else {
	    			$cmt.text("评论");
	    		}
	    	} else {
	    		$cmt.text("评论("+1+")");
	    	}
	    	return count;
	    }
	};
  
	$.fn.commentList = function (option) {
		return this.each(function(input_field) {
			var $this = $(this), data = $this.data('commentList');
		    if (!data) {
		    	$this.data('commentList', (data = new commentList(this, option)));
		    } else {
		    	data.reset(option);
		    }
		    //option 是方法名
		    if (typeof option == 'string'){
		    	if(!data[option]){
		    		console.log( 'Method ' + option + ' does not exist on jQuery.commentList' );
		    	} else {
		    		data[option]();
		    	}
		    }
		});
	};
	$.fn.commentList.defaults = {		
		loadCommentUrl:LT.Settings.URLS.comment.loadComments,
		postId:null,
		$sourceTarget:null
	};
})(window.jQuery);
