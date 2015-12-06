;(function($, undefined) {
	/**
	 * 微博列表
	 */
	var weiboList = function(element,options){
	    this.$el = $(element);
	    this.options = $.extend({}, $.fn.weiboList.defaults, options); 
	    this.initialized();
	};
	weiboList.prototype = {
	    initialized : function(){
	    	this.settings = {};
	    	$.extend(this.settings, LT);
	    	this.settings.delegateEvents(this);//继承events事件
	    	this.rend();
	    },
	    checkPost:function (){
	    	//渲染前先做一次数据过滤，过滤掉微博列表中已渲染过的数据
	    	var liMap = new Array();
	    	$("li[weibomsgli]",this.$el).each(function (){
	    		var liId = $(this).attr("_id").replace("post_","");
	    		liMap[liId] = liId;
	    	});
	    	var length = this.options.posts.length;
	    	var newPosts = new Array();
	    	for(var i=0; i < length; i++){
	    		if(!liMap[(this.options.posts)[i].id]){
	    			newPosts.push((this.options.posts)[i]);
	    		}
	    	}
	    	this.options.posts = newPosts;
	    	return this.options.posts.length;
	    },
	    rend : function(){
	    	if(!this.checkPost()){
	    		return ;//没有微博数据需要渲染
	    	}
	    	var weibos;
	    	if(this.options.formatWeiboDataFunction && 
	    			typeof(this.options.formatWeiboDataFunction) == 'function'){
	    		weibos = this.options.formatWeiboDataFunction(this.options.posts,this.options.weiboUserId);
	    	} else {
	    		weibos = this.formatWeiboData(this.options.posts,this.options.weiboUserId);
	    	}
	    	
	    	if(this.options.pendType && this.options.pendType == 'append'){
	    		
	    	} else {
	    		weibos.reverse();//使用prepend渲染微博需要把原有的时间降序改成升序
	    		this.$el.find("fieldset").remove();
	    		this.$el.prepend(this.options.lastUpdateView);//添加“XXX前，看到这里”
	    	}
	    	for(var i=0; i<weibos.length; i++){//渲染微博
	        	var $weibo = $($.mustache(this.options.$weiboMsgTmpl.html(),
	        			this.mustacheRendWeibo(weibos[i])));
	        	if(weibos[i].betScheme){//渲染微博方案
	        		$weibo.find('[data-type="scheme-view"]').schemeView({
	        			scheme:weibos[i].betScheme,
	        			tmpl:"#new-bet-scheme-tmpl",
	        			realFollowersCount:weibos[i].realFollowers,
	        			type:weibos[i].type
	        		});
	        	}
	        	if(weibos[i].srcwb && weibos[i].srcwb.sourceBetScheme){//转发情况：渲染源微博方案
	        		$weibo.find('[data-type="source-scheme-view"]').schemeView({
	        			scheme:weibos[i].srcwb.sourceBetScheme,
	        			tmpl:"#new-bet-scheme-tmpl",
	        			srcWeiboId:weibos[i].srcwb.id,
	        			srcWeiboUserId:weibos[i].srcwb.ownerId,
	        			type:weibos[i].srcwb.sourceType
	        		});
	        	}
	        	$weibo.hide();
	        	if(this.options.pendType && this.options.pendType == 'append'){
		    		this.$el.append($weibo);//渲染更多微博
		    	} else {
		        	this.$el.prepend($weibo);//渲染新消息提醒块
		        }
		        $weibo.fadeIn(500);
		        //渲染表情和渲染赛事会替换dom结构的内容，必须先渲染这部分再给对象绑定事件，否则绑定事件会丢失
		        $weibo.emotionsToHtml();//渲染表情
		        $weibo.match$ToHtml(); //渲染$赛事链接
		        this.hideText($weibo.find('[_hidediv]'));// 截取文字
				if(weibos[i].likeUsers){//渲染赞微博用户
	        		$weibo.find('[_n="like"]').weiboPraise({
	        			likeUsers:weibos[i].likeUsers,
	        			postId:weibos[i].id
	        		});
	        	}
				//判断跟单按钮是否显示
				var now = new Date().Format("yyyy-MM-dd hh:mm:ss");
				if(weibos[i].betScheme && weibos[i].betScheme.offtime &&
						weibos[i].betScheme.offtime.replace("T"," ") > now){
					if(1 == weibos[i].type) {//给微博跟单按钮绑定数据
						$weibo.find('[_id="follow-real-weibo-btn"]')
							.find('.follow-button').data('post',weibos[i]);
					} else if(3 == weibos[i].type) {//给微博合买按钮绑定数据
						$weibo.find('[_id="follow-real-weibo-btn"]')
							.find('.groupbuy-button').data('post',weibos[i]);
					}
				}
				//隐藏微博跟单按钮，新版跟单按钮显示在方案右下角
				$weibo.find('[_id="follow-real-weibo-btn"]').hide();
				//判断采纳按钮是否显示
				if(weibos[i].type != 2 || weibos[i].betScheme == null){//不是推荐或方案为空，不显示采纳
					$weibo.find('[_id="accept-recomend-weibo-btn"]').hide();
				}
				//渲染微博实单跟单用户
				if(weibos[i].type==1 && weibos[i].realFollowers
						&& weibos[i].realFollowers.length
						&& weibos[i].realFollowers.length> 0){
					var $realFollowUserDiv = $weibo.find('[_id="real-follower-div"]');
					$realFollowUserDiv.realWeiboFollowUsers({
						postId:weibos[i].id,
						realFollowers:weibos[i].realFollowers,
						weiboType:weibos[i].type
					});
				}
				//渲染微博实单合买用户
				if(weibos[i].type==3 && weibos[i].realFollowers
						&& weibos[i].realFollowers.length
						&& weibos[i].realFollowers.length> 0){
					var $groupBuyUserDiv = $weibo.find('[_id="real-follower-div"]');
					$groupBuyUserDiv.realWeiboFollowUsers({
						postId:weibos[i].id,
						realFollowers:weibos[i].realFollowers,
						weiboType:weibos[i].type
					});
				}
		        bindMouseInMatch$StringEvent($weibo);//绑定赛事浮框事件，必须放在hideText后面，否则绑定事件会失效
				bindMouseInHeadOrNicknameEvent($weibo);
	        }
	    },
	    reset : function(option){
	        this.options = $.extend({},this.options,option);
	        this.rend();
	    },
	    events : {
	    	'click [_n="forward"]' : 'forward',
	    	'click [_n="delete"]' : 'deleteWeibo',
	    	'click [_n="share"]' : 'share',
	    	'click [_n="favoriate"]' : 'favorite',
	    	'click [_n="comment"]' : 'comment',
	    	'click [_n="like"]' : 'like',
	    	'click .follow-button' : 'followRealWeibo',
	    	'click .groupbuy-button' : 'groupbuyWeibo',
	    	'click .accept-rec-button' : 'acceptRecWeibo',
	    	'click .bet-follow' : 'triggerFollowRealWeibo',
	    },
	    triggerFollowRealWeibo:function (event){
	    	var $this = $(event.target);
	    	if($this.attr("type")==1){
	    		$this.closest("li").find(".follow-button").trigger("click");
	    	} else if($this.attr("type")==3){
	    		$this.closest("li").find(".groupbuy-button").trigger("click");
	    	}
	    },
	    forward : function (event){
	    	if(!this.options.weiboUserId ||
	    			this.options.weiboUserId < 1 ){
	    		this.gotoLogin();
	    		return ;
	    	}
			var $this = $(event.target).closest('[_n="forward"]');
			var _postId = $this.attr("_postId");
			$.post("http://www.davcai.com/showForward", {postId:_postId}, function(result, e) {
				if (result != null) {
					if (result.success) {
						$this.forwardDialog({
							forwardWeibo:result.results[0]
						});
					} else {
						alert(result.desc);
					}
				}
			}, 'jsonp').error(function(e) {
				alert( "操作失败，请刷新页面重试！");});
	    },
	    deleteWeibo : function (event){
	    	if(!this.options.weiboUserId ||
	    			this.options.weiboUserId < 1 ){
	    		this.gotoLogin();
	    		return ;
	    	}
	    	if(confirm("确定删除吗？")){
				var $this = $(event.target);
				var _$el = this.$el;
				var _postId = $this.attr("_postId");
				$.post("http://www.davcai.com/ajaxDelPost", {postId:_postId}, function(result, e) {
					if (result != null) {
						if (result.success) {
							$.msgbox("删除成功",$this,{success:false});
							setTimeout(function (){
								$("[_id=post_" + _postId + "]").each(function (){
									$(this).fadeOut(300,function (){
										var $next = $(this).next();
										if($next.is('fieldset')){
											$next.remove();
										}
										$(this).remove();
									});
									var a = $("li",_$el).length < 1;
									var b = /\/(\d{15})\/(\d{15})/.test(location.href);
									if( a  && b){
										window.location.href="/home";
									}
								});
							},300);
						} else {
							$.msgbox(result.desc,$this,{success:false});
						}
					}
				}, 'jsonp');
			}
	    },
	    share : function (event){
	    	if(!this.options.weiboUserId ||
	    			this.options.weiboUserId < 1 ){
	    		this.gotoLogin();
	    		return ;
	    	}
	    	var $this = $(event.target).closest('[_n="share"]');
			var $li = $this.closest('li');
	    	var weiboContent = $li.find('[_n="content"][_sourcediv]').text();
			$this.shareDialog({
				shareWeibo:weiboContent,
				weiboLinkUrl:$this.attr("_share_link")
			});
	    },
	    favorite : function (event){
	    	if(!this.options.weiboUserId ||
	    			this.options.weiboUserId < 1 ){
	    		this.gotoLogin();
	    		return ;
	    	}
	    	var $this = $(event.target), status = $this.attr("_s"), count = $this.attr("_count");
	    	$this.attr("disabled",true);
			if("favoriate" == status){
				$.post("http://www.davcai.com/addFavoriate", {postId : $this.attr("_postId")}, function(result, e) {
					if (result != null) {
						if (result.success) {
							if(result.desc=="true"){
								count++;
								$this.attr("_count",count);
							}else{
								$.msgbox("已经收藏过了",$this,{success:false});
							}
							$this.text('已收藏('+count+')');
							$this.attr("_s","unfavoriate");
							$.msgbox("已收藏",$this,{success:true});
						} else {
							$.msgbox(result.desc,$this,{success:false});
						}
					}
					$this.attr("disabled",false);
				}, 'jsonp');
			}else if("unfavoriate" == status){
				$.post("http://www.davcai.com/delFavoriate", {postId : $this.attr("_postId")}, function(result, e) {
					if (result != null) {
						if (result.success) {
							if (result.desc == "true") {
								count--;
								$this.attr("_count",count);
							}else{
								$.msgbox("未收藏",$this,{success:false});
							}
							if(count < 1){
								$this.text('收藏');
							}else{
								$this.text('收藏('+count+')');
							}
							$this.attr("_s","favoriate");
						} else {
							$.msgbox(result.desc,$this,{success:false});
						}
					}
					$this.attr("disabled",false);
				}, 'jsonp');
			}
			return;
	    },
	    comment : function (event){
	    	if(!this.options.weiboUserId ||
	    			this.options.weiboUserId < 1 ){
	    		this.gotoLogin();
	    		return ;
	    	}
	    	var cmt = $(event.target);
	    	var liTag = cmt.closest("li[weiboMsgLi]");
	    	var posi = liTag.find('[_id="weibo-footer-container"] .commentContainer');
	    	this.options.isLoadCommentList = true;//告诉commentContainer加载微博列表
	    	this.options.appendPosi = posi;//commentContainer渲染的位置
	    	this.options.postId = cmt.attr("_postid");//评论那一条微博的微博id
	    	$(event.target).commentContainer(this.options);
	    },
	    like : function (event){
	    	if(!this.options.weiboUserId ||
	    			this.options.weiboUserId < 1 ){
	    		this.gotoLogin();
	    		return ;
	    	}
	    	var $this = $(event.target), status = $this.attr("_s"), count = $this.attr("_count");
			var postId = $this.attr("_postId");
			if("like" == status){
				$.post(LT.Settings.URLS.like.like, {postId : postId}, function(result, e) {
					if (result != null) {
						if (result.success) {
							if(result.desc=="true"){
								count++;
								$this.attr("_count",count);
								$this.msgbox('已赞');
							}else{
								$this.msgbox('已赞过！');
							}
							$this.text('已赞('+count+')');
							$this.attr("_s","unLike");
							$this.weiboPraise({
								postId:postId,
								addMe:result.weiboUser,
								deleteMe:null
							});
						} else {
							$this.msgbox(result.desc);
						}
					}
					$this.attr("disabled",false);
				}, 'jsonp');
			} else if("unLike" == status){
				$.post(LT.Settings.URLS.like.unlike, {postId : postId}, function(result, e) {
					if (result != null) {
						if (result.success) {
							if (result.desc == "true") {
								count--;
								$this.attr("_count",count);
							} else {
								$this.msgbox('未赞！');
							}
							if(count < 1){
								$this.text('赞');
							} else {
								$this.text('赞('+count+')');
							}
							$this.attr("_s","like");
							$this.weiboPraise({
								postId:postId,
								deleteMe:result.weiboUser,
								addMe:null
							});
						} else {
							$this.msgbox(result.desc);
						}
					}
					$this.attr("disabled",false);
				}, 'jsonp');
			}
	    },
	    followRealWeibo : function (event){
	    	if(!this.options.weiboUserId ||
	    			this.options.weiboUserId < 1 ){
	    		this.gotoLogin();
	    		return ;
	    	}
	    	var $this = $(event.target);
	    	if(!$this.data("post")){//没有数据什么都不做
	    		return ;
	    	}
	    	$this.followRealWeiboDialog({
	    		post:$this.data("post"),
	    		appendPosi:$this.closest('li[weiboMsgLi]').find('[_id="weibo-footer-container"]'),
	    		currentUserId:this.options.weiboUserId
	    	});
	    },
	    groupbuyWeibo : function (event){//参与合买
	    	if(!this.options.weiboUserId ||
	    			this.options.weiboUserId < 1 ){
	    		this.gotoLogin();
	    		return ;
	    	}
	    	var $this = $(event.target);
	    	if(!$this.data("post")){//没有数据什么都不做
	    		return ;
	    	}
	    	$this.groupBuyRealWeiboDialog({
	    		post:$this.data("post"),
	    		appendPosi:$this.closest('li[weiboMsgLi]').find('[_id="weibo-footer-container"]'),
	    		currentUserId:this.options.weiboUserId
	    	});
	    },
	    acceptRecWeibo : function (event){
	    	if(!this.options.weiboUserId ||
	    			this.options.weiboUserId < 1 ){
	    		this.gotoLogin();
	    		return ;
	    	}
	    	var $this = $(event.target);
	    	var url = $this.attr("_url");
	    	window.open(url,'_blank');
	    },
	    formatWeiboData : function(posts,userId){//格式化微博数据，使得json数据符合模板
			var results = [];
			for(var i=0 ; i < posts.length ; i++ ){
				var obj = posts[i];
				var srcwb = false;
				if(!obj.user){
					continue;
				}
				if (obj.sourceWeiboMsg != null && obj.sourceWeiboMsg.id > 0) {
					srcwb = {
						id : obj.sourceWeiboMsg.id,
						ownerId : obj.sourceWeiboMsg.ownerId,
						nickName : obj.sourceUser.nickName,
						title : obj.sourceWeiboMsg.title || '',
						content : obj.sourceWeiboMsg.content || '',
						createTime : obj.sourceCreateTimeFmt,
						commentCount : obj.sourceWeiboMsg.commentCount,
						from : obj.sourceWeiboMsg.from || '',
						fromUrl : obj.sourceWeiboMsg.fromUrl || '',
						sourceBetScheme:obj.sourceBetScheme,
						sourceRealFollowers:obj.sourceRealFollowers,
						sourceType:obj.sourceType,
						certificationType:obj.user.certificationType
					};
				}
				results.push({
					id : obj.id,
					ownerId : obj.ownerId,
					nickName : obj.user.nickName || '',
					headImageURL : obj.user.headImageURL || '',
					title : obj.title || '',
					content : obj.content || '',
					srcwb : srcwb,
					createTime : obj.createTimeFmt,
					timeLine : obj.createTime,
					from :  obj.from || '',
					fromUrl : obj.fromUrl || '',
					forwardCount : obj.forwardCount,
					shareCount : obj.shareCount,
					favoriateCount : obj.favoriateCount,
					commentCount : obj.commentCount,
					likeCount : obj.likeCount,
					isFavourited : obj.favourited,
					isLike : obj.like,
					isCurrentUser : userId !=0 && userId == obj.ownerId,
					type : obj.type,
					schameId : obj.schameId,
					tags:obj.tags,
					likeUsers:obj.likeUsers,
					realFollowers:obj.realFollowers,
					betScheme:obj.betScheme,
					userRecords:obj.userRecords,
					certificationType:obj.user.certificationType,
					user:obj.user
				});
			}
			return results;
		},
	    mustacheRendWeibo:function(wb){
			return {
				posts: wb,
				forward: function(){
					return this.forwardCount > 0? '转发(' + this.forwardCount + ')' : '转发';
				},
				share: function(){
					return this.shareCount > 0 ? "分享("+ this.shareCount + ")": "分享";
				},
				favoriate: function(){
					var text = this.isFavourited ? '已收藏' : '收藏';
					return text + (this.favoriateCount > 0 ? "("+ this.favoriateCount + ")": '');
				},
				isfavoriated : function(){
					return this.isFavourited ? 'unfavoriate' : 'favoriate' ;
				},
				comment: function(){
					return this.commentCount > 0 ? "评论("+ this.commentCount + ")": "评论";
				},
				like: function(){
					var text = this.isLike ? '已赞' : '赞';
					return text + (this.likeCount > 0 ? "("+ this.likeCount + ")": '');
				},
				isliked: function(){
					return  this.isLike ? 'unLike' :  'like' ;
				},
				fromUrlPosi: function (){
					if(this.fromUrl){
						return '<a href="' + this.fromUrl +'" target="_blank">' + this.from + '</a>';
					} else {
						var from = this.from || '';
						return '<label>' + from + '</label>';
					}
				},
				srcFromUrl: function(){
					if(this.fromUrl){
						return '<a href="' + this.fromUrl +'" target="_blank">' + this.from + '</a>';
					} else {
						var from = this.from || '';
						return '<label>' + from + '</label>';
					}
				},
				shortCreateTime: function(){
					return $.shortTime(this.createTime);
				},
				followOrPartnerView:function() {
					var tpl = '<input class="btn-name" type="button" value="btn-view"/>';
					var btnName = "";
					var btnView = '';
					if(1 == this.type) {
						btnName = "follow-button";
						if(this.realFollowers && this.realFollowers.length > 0){
							btnView = '跟单('+this.realFollowers.length+')';
						} else {
							btnView = '跟单';
						}
					} else if(3 == this.type && this.betScheme && 
							(this.betScheme.saleStatus == 0)) {
						btnName = "groupbuy-button";
						btnView = '合买';
					} else {
						return "";
					}
					tpl = tpl.replace("btn-name", btnName);
					tpl = tpl.replace("btn-view", btnView);
					return tpl;
				},
//				followCountView: function (){
//					if(1 == this.type) {
//						if(this.realFollowers && this.realFollowers.length > 0){
//							return '跟单('+this.realFollowers.length+')';
//						} else {
//							return '跟单';
//						}
//					} else if(3 == this.type) {
//						return '合买';
//					}
//				},
				acceptWeiboBtnUrl:function (){
					if(this.type == 2 && this.betScheme != null){
						var url
						if(this.betScheme.lotteryId == "JCZQ"){
							url = 'http://trade.davcai.com/df/bet/jczq.do?';
						} else if(this.betScheme.lotteryId == "JCLQ"){
							url = 'http://trade.davcai.com/df/bet/jclq.do?';
						}
						//url +=  this.betScheme.lotteryId.toLowerCase() + "_" + this.betScheme.playId.toLowerCase() + ".html?";
						url += 'schemeId=' +  this.betScheme.id;
						return url; 
					} else {
						return '';
					}
				},
				_tags:function() {
					var tags = this.tags;
					var result = [];
					for(index in tags) {
						if(tags[index].name == "过单率" && !tags[index].value) {
							continue;
						}
						result.push(tags[index]);
					}
					return result;
				}
			};
		},
		hideText : function($doms){
			$doms.each(function(){
				$(this).mlellipsis(7);
			});
		},
		gotoLogin : function (){
			$("#pop-outer").fadeIn(800);
			$("#all").show();
		}
	};
  
	$.fn.weiboList = function (option) {
		return this.each(function(input_field) {
			var $this = $(this), data = $this.data('weiboList');
		    if (!data) {
		    	$this.data('weiboList', (data = new weiboList(this, option)));
		    } else {
		    	data.reset(option);
		    }
		    //option 是方法名
		    if (typeof option == 'string'){
		    	if(!data[option]){
		    		console.log( 'Method ' + option + ' does not exist on jQuery.weiboList' );
		    	} else {
		    		data[option]();
		    	}
		    }
		});
	};
	$.fn.weiboList.defaults = {
		posts : null,
		pendType: 'prepend',
		weiboUserId:null,
		formatWeiboDataFunction:null,  //格式化微博数据方法，默认用formatWeiboData
		$weiboMsgTmpl:$("#weibo-msg-tmpl")  //渲染微博消息模版，默认用$("#weibo-msg-tmpl");
	};
})(window.jQuery);
