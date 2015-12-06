// 模板
var commentPostAreaTemplate = 
'<div class="commentPostArea">'+
	'<div class="arrow-top">'+
		'<span class="arrow-top-out"></span>'+
		'<span class="arrow-top-in"></span>'+
	'</div>'+
	'<div class="inputArea">'+
		'<textarea class="commentEditor"'+
			'style="min-height: 50px; height: 60.21875px;font-family:Microsoft YaHei;font-size:13px;"></textarea>'+
	'</div>'+
	'<p class="forbidden" style="display: none">由于用户的设置，你不能对主帖进行评论</p>'+
	'<span class="showFaceButton">&nbsp;</span>'+
	'<span class="addStock">&nbsp;</span>'+
	
	
	'<div class="editor fn-btn">'+
		'<div class="emotion selector">'+
			'<span title="插入表情" class="face" comment_face><s></s><span>表情</span></span>'+
			'<span title="过滤赛事" class="match matchFindForward game_show_btn"><s></s><span>赛事</span></span>'+
		'</div>'+
	'</div>'+
	
	'<label class="forward-home"><input type="checkbox" name="forward"> 同时转发到我的首页 </label>'+
	'<form class="replyForm" action="" method="get">'+
		'<input type="submit" class="commentSubmit button" value="发布">'+
	'</form>'+
	'<div class="fixit"></div>'+
'</div>';

// 使用非入侵模式安装“评论”连接的点击事件，并显示评论框
function onComment(commentLink){
	var commentContainer = commentLink.closest('li').find('.commentContainer');
	if(0 != commentContainer.length) {
		commentContainer.toggle();
		// 自动调整箭头位置
		adjustArrowPos(commentContainer, commentLink);
	} else {
		showComments(commentLink);
	}
	
	//点击评论链接，隐藏跟单form
	var followFormDiv = commentLink.closest('li').find('[weiboBetFormDiv]');
	if(followFormDiv){
		followFormDiv.hide();
	}
}

function validateCommentText(commentText){
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

function showComments(commentLink){
	// text area id 不能重复，否则会出现内容重复追加的bug
	var postId = commentLink.attr('_postid');
	var post = commentLink.closest('li');
	
	var posi = commentLink.closest('.content.status_content');
	
	var commentContainer =
	$('<div class="commentContainer display_textarea" style="display: block;">'+
		commentPostAreaTemplate +
		'<div class="loading" style="display: none;"></div>'+
	'</div>');
	
	//post.append(commentContainer);
	posi.after(commentContainer);
	
	var commentTextArea = commentContainer.find("textarea");
	bindWeiboButtonEvent(commentTextArea);
	
	// 自动调整箭头位置
	adjustArrowPos(commentContainer, commentLink);
	var commentsLoading = post.find('.loading');

	// 评论发布按钮事件绑定
	var form = commentContainer.find('form');
	form.submit(function(event){
		event.preventDefault();
		var textarea = commentContainer.find('textarea');
		if (textarea.val()==''){
			return;
		}
		var commentText = textarea.val();
		if (!validateCommentText(commentText)){
			return;
		}
		commentsLoading.show();
		$.ajax('http://www.davcai.com/post_cmnt', {
			dataType: 'jsonp',
			method: 'post',
			cache: false,
			data: {
				pid: postId,
				forward: commentContainer.find('input:checkbox').is(':checked'),
				comment: textarea.val()
			},
			success: function(result){
				updateCmtCountInWeiboMeta(commentContainer,1);
				loadComments(postId, commentsLoading, post);
			},
			error: function(){
				commentsLoading.hide();
				$.msgBox('抱歉，因网络问题操作失败。', null, {pos:'center', success: false});
				//alert('抱歉，因网络问题操作失败。');
			}
		});
		textarea.val('');
	});
	// 加载历史评论
	commentsLoading.show();
	loadComments(postId, commentsLoading, post);
}

function adjustArrowPos(commentContainer, commentLink){
	var linkLeft = commentLink.offset().left;
	var textAreaLeft = commentContainer.find('.commentPostArea').offset().left;
	var left = linkLeft - textAreaLeft + commentLink.width()/2;
	commentContainer.find('.arrow-top-out,.arrow-top-in').css({'left':left});
}

function loadComments(postId, commentsLoading, post){
	// 最后一个li需要加上 "last" class
	var comments =
	'<div class="commentInfo" style="">'+
		'<div class="hd">'+
//			'<span class="sort"><span>排序：</span>'+
//			'<a href="javascript:;" sort="false" class="active" title="最近">最近</a>'+
//			'<a href="javascript:;" sort="true" title="最早">最早</a>'+
//			'<a href="javascript:;" sort="like" title="赞" class="last">赞</a>'+
//			'</span>'+
			'<span class="commentNum">全部评论<span class="counts">（{{commentCount}}）</span></span>'+
		'</div>'+
		'<div class="loading" style="display: none;"></div>'+
		'<ul class="commentList">{{#comments}}'+
			'<li id="comment_{{id}}" _comment="{{id}}">'+
				'<div class="headpic" _userid="{{author.weiboUserId}}">'+
					'<a href="{{authorHomeURL}}" data-name="{{author.nickName}}">'+
					'<img width="30px" height="30px" src="{{author.headImageURL}}">'+
					'</a>'+
				'</div>'+
				'<div class="content" style="display: block;">'+
					'<div class="comment">'+
						'<a href="{{authorHomeURL}}" data-name="{{author.nickName}}">{{author.nickName}}<span '+
							'class="user_remark" data-name="{{author.nickName}}" style="display: none">()</span>'+
							'</a>{{{replyTo}}}'+
					'</div>'+
					'<div class="cmt_con summary">'+
						'<span>{{content}}</span>'+
					'</div>'+
					'<div class="ops">'+
						'<div class="infos">'+
							'<span class="createAt" createdat="{{createTimeLong}}">{{createTimeSpan}}</span><a'+
								'href="#" class="reportSpam_comment last" style="display: none;">举报</a>'+
						'</div>'+
						'{{#isOwner}}<a href="#" class="deleteComment">删除</a>{{/isOwner}}' +
						'<a href="#" class="{{like}}" like_count="{{praisedCount}}">{{praiseText}}</a><a href="#" '+
							'class="reply last">回复</a>'+
					'</div>'+
				'</div>'+
			'</li>{{/comments}}'+
		'</ul>'+
	'</div>';
	
	function authorHomeURLById(weiboUserId){
		return '/'+weiboUserId+'/profile';
	}
	
	$.ajax('http://www.davcai.com/list_cmnts?pid='+postId, {
		dataType: 'jsonp',
		cache: false,
		success: function(result) {
			commentsLoading.hide();
			var loginUserId = result.userId;
			var cmnts = {
				comments: result.data,
				commentCount: result.data.length, 
				like: function(){
					return this.praised?"unlike":"like";
				},
				authorHomeURL: function(){
					return authorHomeURLById(this.author.weiboUserId);
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
						var homeURL = authorHomeURLById(this.repliedUser.weiboUserId);
						return ' 回复 '+ '<a href="'+homeURL+'">'+this.repliedUser.nickName+'</a>';
					}else{
						return '';
					}
				}
			};
			var tpl = $($.mustache(comments, cmnts));
			
			//转换评论中的@用户
			tpl.find(".cmt_con").each(function(index, elt) {
				$(elt).at();
			})
			//转换赛事链接
			tpl.match$ToHtml();
			//转换表情图标
			tpl.emotionsToHtml();
			
			tpl.find('li:last').addClass('last');
			addLikeHandler(tpl);
			addReplyHandler(tpl, post, postId);
			addDeleteHandler(tpl);
			addHoverHandler(tpl);
			// 插入或替换DOM树
			var commentList = post.find('.commentInfo');
			var hasComments = result.data.length;
			if (commentList.length > 0){
				commentList.replaceWith(tpl);
			}else if (hasComments>0){
				post.find('.commentContainer').append(tpl);
			}
		},
		error: function(){
			commentsLoading.hide();
			$.msgBox('抱歉，因网络问题，操作失败。', null, {pos:'center', success: false});
			//alert('抱歉，因网络问题，操作失败。');
		}
	});
}

function addHoverHandler(comments){
	comments.find('.commentList li').hover(
		function(){
			$(this).find('.deleteComment').css({'visibility': 'visible'});
		},
		function(){
			$(this).find('.deleteComment').css({'visibility': 'hidden'});
		}
	);
}

function addLikeHandler(comments){
	var like = comments.find('.like,.unlike');
	like.on('click', function(event){
		event.preventDefault();
		var self = $(this);
		var commentId = self.closest('li').attr('_comment');
		var praiseOrUnpraise = self.hasClass('like');
		praiseComment(commentId, praiseOrUnpraise, self);
	});
}

function addDeleteHandler(comments) {
	var deleteLink = comments.find('.deleteComment');
	deleteLink.on('click', function(event){
		event.preventDefault();
		var self = $(this);
		var commentId = self.closest('li').attr('_comment');
		deleteComment(commentId, self);
	});
}

function deleteComment(commentId, deleteLink) {
	var cmntLi = deleteLink.closest('li');
	var allCmnt = cmntLi.closest('.commentInfo');
	$.ajax('http://www.davcai.com/del_cmnt', {
		dataType: 'jsonp',
		data: {
			cid: commentId
		},
		success: function(result){
			if(result.success){
				$.msgBox('删除成功！', null, {pos:'center'});
				updateCmtCountInWeiboMeta(allCmnt, -1);
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
}

// 更新“全部评论（2）”中的数字
// 返回：更新后的评论数
function updateCommentCount(allCmnt) {
	var countEl = allCmnt.find('.commentNum .counts');
	var cmntCountText = countEl.text();
	var pt = /（(\d+)）/;
	var groups = pt.exec(cmntCountText);
	var count = parseInt(groups[1]);
	count -= 1;
	countEl.text("（"+count+"）");
	return count;
}

function praiseComment(commentId, praiseOrUnpraise, praiseLink){
	$.ajax('http://www.davcai.com/prs_cmnt',{
		dataType: 'jsonp',
		data: {
			cid: commentId,
			add: praiseOrUnpraise
		},
		success: function(result){
			var count = parseInt(praiseLink.attr('like_count'));
			count = praiseOrUnpraise ? count+1 : Math.max(0,count-1);
			var likeText = '赞';
			if (praiseOrUnpraise){
				praiseLink.removeClass('like');
				praiseLink.addClass('unlike');
				likeText = '已赞';
			}else{
				praiseLink.removeClass('unlike');
				praiseLink.addClass('like');
				likeText = '赞';
			}
			praiseLink.attr('like_count', count);
			if (count>0){
				praiseLink.text(likeText+'('+count+')');
			}else {
				praiseLink.text(likeText);
			}
		},
		error: function(){
			$.msgBox('抱歉，因网络问题操作失败。', null, {pos:'center', success: false});
			//alert('抱歉，因网络问题操作失败。');
		}
	});
}

function addReplyHandler(commentInfo, post, postId){
	var replies = commentInfo.find('.reply');
	var commentsLoading = post.find('.loading');
	
	replies.on('click', function(event){
		event.preventDefault();
		var replyLink = $(this);
		var cid = replyLink.closest('li').attr('_comment');
		var comment = replyLink.closest('li');
		var commentPostArea = comment.find('.commentPostArea');
		// 显示“回复”文本框
		if (commentPostArea.length>0){
			commentPostArea.toggle();
		}else{
			comment.append(commentPostAreaTemplate);
		}
		var replyArea = comment.find("textarea");
		bindWeiboButtonEvent(replyArea);
		
		// 自动调整箭头位置
		adjustArrowPos(comment, replyLink);
		// 绑定回复按钮事件处理
		comment.find('form').submit(function(event){
			event.preventDefault();
			var textarea = comment.find('textarea');
			if (textarea.val()==''){
				return;
			}
			var commentText = textarea.val();
			if (!validateCommentText(commentText)){
				return;
			}
			commentsLoading.show();
			commentPostArea.hide();
			$.ajax('http://www.davcai.com/post_cmnt', {
				dataType: 'jsonp',
				method: 'post',
				data: {
					pid: postId,
					cid: cid,
					forward: comment.find('input:checkbox').is(':checked'),
					comment: textarea.val()
				},
				success: function(result){
					updateCmtCountInWeiboMeta(commentPostArea,1);
					loadComments(postId, commentsLoading, post);
				},
				error: function(){
					commentsLoading.hide();
					$.msgBox('抱歉，因网络问题操作失败。', null, {pos:'center', success: false});
					//alert('抱歉，因网络问题回复失败。');
				}
			});
			textarea.val('');
		});
	});
}


// ================= 我的评论页面中直接回复按钮响应方法 =====================

function onCommentMeReplyClick(link){
	var post = link.closest('li').find('.commentPostArea');
	if(!post.isEmpty()) {
		post.toggle();
	} else {
		var li = link.closest('li');
		li.append(commentPostAreaTemplate);
		bindCommentSubmit(li);
	}
}

function bindCommentSubmit(li){
	var form = li.find('form');
	var postId = li.attr('_post');
	var link = li.find('.ops .toggleComment');
	var cid = link.attr('_cid');
	var commentArea = li.find('.commentPostArea');
	var textarea = li.find('textarea');
	textarea.attr("id",cid+"textarea");
	//绑定插入表情事件
	$(".editor>.selector>.face[comment_face]").jqfaceedit({
    	txtAreaObj:textarea,
    	containerObj:$('#emotion_icons'),
    	textareaid:cid+"textarea",
    	top:30,
    	left:-40
    });
	
	//绑定$事件
	WB_API.addAtWhoEvent(textarea[0]);
	//绑定@事件
	WB_API.addNickNameAtWho(textarea[0]);
	//绑定点击赛事按钮事件
	WB_API.showMatchAreaDelegateEvents();
	
	form.submit(function(event){
		event.preventDefault();
		var textarea = li.find('textarea');
		if (textarea.val()==''){
			return;
		}
		$.ajax('http://www.davcai.com/post_cmnt', {
			dataType: 'jsonp',
			method: 'post',
			data: {
				pid: postId,
				cid: cid,
				forward: li.find('input:checkbox').is(':checked'),
				comment: textarea.val()
			},
			success: function(result){
				commentArea.hide();
				$.msgbox('回复成功。', link);
			},
			error: function(){
				$.msgbox('抱歉，因网络问题回复失败。', link, {success: false});
			}
		});
		textarea.val('');
	});
}


//========== 在微博的meta中更新微博数量 ============
function updateCmtCountInWeiboMeta(commentContainer,i){
	var $li = commentContainer.closest("li");
	var $cmt = $('[_n="comment"]',$li);
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


//========== 绑定@人，$赛事，加表情的事件 ============
var bindWeiboButtonEvent = function(commentTextArea) {
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
	$(".editor>.selector>.face[comment_face]").jqfaceedit({
    	txtAreaObj:$("#" + commentTextAreaId),
    	containerObj:$('#emotion_icons'),
    	textareaid:commentTextAreaId,
    	top:30,
    	left:-40
    });
};