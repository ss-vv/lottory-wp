;(function($) {
	/**
	 * 微博列表面板
	 */
	var weiboListPanel = function(element,options){
	    this.$el = $(element);
	    this.$newWeiboMsgTipTmpl = $("#new-weibo-msg-tip-tmpl");
	    this.$loadMoreWeiboTmpl = $("#load-more-weibo-tmpl");
	    this.$timeFieldSetTmpl = $("#time-field-set-tmpl");
	    this.$upLoading = $($("#loading-tmpl").html());
	    this.$downLoading = $($("#loading-tmpl").html());
	    this.pageIndex = 1;
	    this.isReadyAutoLoad = false;//微博初始化时，异步请求微博数据，此时不自动（滚动到底部触发）加载微博
	    this.options = $.extend({}, $.fn.weiboListPanel.defaults, options);
	    this.initialized();
	};
	weiboListPanel.prototype = {
	    initialized : function(){
	    	this.settings = {};
	    	$.extend(this.settings, LT);
	    	this.settings.delegateEvents(this);//绑定元素自身事件
	    	this.rend();
	    	this.bind();//绑定其他事件
	    },
	    rend : function(){
	        this.$newWeiboMsgTipBtn = $($.mustache(this.$newWeiboMsgTipTmpl.html(), {
	        	newWeiboCount:0
	        }));
	        this.$loadMoreWeiboBtn = $($.mustache(this.$loadMoreWeiboTmpl.html(), {
	        	newWeiboCount:0
	        }));
	        this.$el.parent().prepend(this.$newWeiboMsgTipBtn);//渲染新消息提醒块
	        this.$el.parent().append(this.$loadMoreWeiboBtn);//渲染加载更多微博块
	        this.$upLoading.show();
	        this.$el.parent().prepend(this.$upLoading);//上方添加loading图标
	        this.$el.parent().append(this.$downLoading);//下方添加loading图标
	        this.$downLoading.hide();
	        var self = this;
	        $.ajax({ //加载微博数据
	    		url:this.options.loadWeiboUrl,
	    		success : function(data, e) {
	    			self.options.posts = data.results;
	    			self.options.weiboUserId = data.userId;
	    			self.$upLoading.hide();//隐藏loading图标
	    			self.$el.weiboList(self.options);//渲染微博列表
	    			self.setTimeLine();
	    			self.lastUpdateTime = new Date().getTime();//设置最后更新时间
	    			self.isReadyAutoLoad = true;//加载微博完成，允许滚动到底部自动加载
	    		},
	    		error:function (data, e){
	    			$.showMessage(data.desc);
	    		},
	    		dataType:'json'
	    	});
	    },
	    bind :function (){
	    	var self = this;
	    	self.lastCheckTime = new Date().getTime();
	    	$(window).scroll({self:self},self.checkNewPost);
	    	$(window).scroll({self:self},self.autoLoadMorePost);
	    	$(window).mousemove({self:self},self.checkNewPost);
	    	this.$newWeiboMsgTipBtn.click({self:self},self.loadNewPost);
	    	this.$loadMoreWeiboBtn.click({self:self},self.loadMorePost);
	    },
	    events : {
	    	
	    },
	    reset : function(){
	        this.options = option;
	    },
	    // 初始化时间线
		setTimeLine : function(){
			if(this.$newWeiboMsgTipBtn.is(":hidden")){//如果是隐藏状态
				var $timeLine = this.$el.find('input[name="timeLine"]').first();
				if($timeLine.length ==0) {
					this.timeLine =  this.options.timeline;
				} else {//使用从微博列表中获取的timeline
					this.timeLine = parseInt($timeLine.val()) + 1;
					this.options.timeline = this.timeline;
				}
			};
		},
		// 检查新微博数
		checkNewPost:function(event){
			var self = event.data.self;
			self.setTimeLine(); //使用self因为调用该方法的有可能是$(window)对象
			var nowTime = new Date().getTime();
			if(self.options.timeOut > (nowTime - self.lastCheckTime)){
				return ;
			}
			self.lastCheckTime = nowTime;
			$.post(self.options.newWeiboTimerUrl, {timeLine : self.timeLine}, function(result, e) {
				if (result != null) {
					if (result.success) {
						if(result.resultCode > 0){
							var newPostCount = result.resultCode;
							self.$newWeiboMsgTipBtn.show();
							self.$newWeiboMsgTipBtn.find('[_id="new-weibo-count"]').html(newPostCount);
						}
					} else {
						$.showMessage(result.desc);
					}
				}
			}, 'jsonp');
		},
		loadNewPost:function (event){
			var self = event.data.self;
			$(this).hide();
			self.$upLoading.show();
			$.post(self.options.newWeiboUrl , {timeLine:self.timeLine}, function(result, e) {
				if (result != null) {
					if (result.success) {
						self.options.posts = result.results;
		    			self.options.weiboUserId = result.userId;
		    			self.options.pendType = 'prepend';
		    			self.options.lastUpdateView = self.rendLastUpdateTimeHtml();//取得“XXX前看到这里”html
						self.$el.weiboList(self.options);//渲染微博列表
						self.$upLoading.hide();
						self.lastUpdateTime = new Date().getTime();//刷新最后更新时间
					} else {
						$.showMessage(result.desc);
					}
				}
			}, 'jsonp');
		},
		autoLoadMorePost:function (event){
			var self = event.data.self;
			if(self.allLoadFinished){//已经全部加载完成
				return ;
			}
			if(self.isLoadingMorePost){
				return ;
			}
			if(!self.isReadyAutoLoad){
				return ;
			}
			if(self.isAtBottom() && 
					(self.pageIndex < self.options.autoLoadWeiboMaxPageIndex)){
				self.loadMorePost(event);
			} else if(self.isAtBottom() && self.pageIndex >= self.options.autoLoadWeiboMaxPageIndex){
				self.$loadMoreWeiboBtn.show();
			}
		},
		loadMorePost:function (event){
			var self = event.data.self;
			self.isLoadingMorePost = true;//设置为正在加载更多微博状态，防止不断加载
			self.$downLoading.show();
			self.$loadMoreWeiboBtn.hide();
			$.post(self.options.loadWeiboUrl, {"pageRequest.pageIndex" :self.pageIndex+1}, function(result, e) {
				if (result != null) {
					if (result.success) {
						self.pageIndex++;//当前页数+1
						self.options.posts = result.results;
		    			self.options.weiboUserId = result.userId;
		    			self.options.pendType = 'append'; //告诉weiboList插件渲染方式为append，默认是prepend
						self.$el.weiboList(self.options);//渲染微博列表
						self.$downLoading.hide();
						if(self.pageIndex > self.options.autoLoadWeiboMaxPageIndex){
							self.$loadMoreWeiboBtn.show();//当页面微博列表已经加载的页数等于autoLoadWeiboMaxPageIndex时，显示“加载更多微博”按钮
						}
						self.isLoadingMorePost = false;//设置为加载完成
						if(result.results.length < 1){
							self.allLoadFinished = true;
							self.$loadMoreWeiboBtn.show();
							self.$loadMoreWeiboBtn.html("没有更多微博了");
							return ;
						}
					} else {
						$.showMessage(result.desc);
					}
				}
			}, 'jsonp');
		},
		isAtBottom : function (){
			return $(window).scrollTop() + $(window).height() > $(document).height() - 110;
		},
		rendLastUpdateTimeHtml : function(){
			if(!this.lastUpdateTime){
				return '';
			}
			var millisecond = new Date().getTime() - this.lastUpdateTime;
			var timeView = "";
			if(millisecond < 60000){
				timeView = parseInt(millisecond /1000) + "秒";
			} else if(millisecond < 360000){
				timeView = parseInt(millisecond /60000) + "分钟";
			} else{
				timeView = parseInt(millisecond /360000) + "小时";
			}
			return $.mustache(this.$timeFieldSetTmpl.html(),{
				interval:timeView
			});
		}
	};
  
	$.fn.weiboListPanel = function (option) {
		return this.each(function(input_field) {
			var $this = $(this), data = $this.data('weiboListPanel');
		    if (!data) {
		    	$this.data('weiboListPanel', (data = new weiboListPanel(this, option)));
		    } else {
		    	data.reset(option);
		    }
		    //option 是方法名
		    if (typeof option == 'string'){
		    	if(!data[option]){
		    		console.log( 'Method ' + option + ' does not exist on jQuery.weiboListPanel' );
		    	} else {
		    		data[option]();
		    	}
		    }
		});
	};
	$.fn.weiboListPanel.defaults = {
		loadWeiboUrl : LT.Settings.URLS.weibo.loadPost,
		newWeiboUrl : LT.Settings.URLS.weibo.weibo_new_posts,
		timeline : new Date().getTime(),
		newWeiboTimerUrl : LT.Settings.URLS.weibo.newPostTimer,
		timeOut:5000,
		autoLoadWeiboMaxPageIndex:3,
		weiboUserId:null
	};
})(window.jQuery);
