;(function($, undefined) {
	/**
	 * 赞
	 */
	var weiboPraise = function(element,options){
	    this.$el = $(element);
	    this.options = $.extend({}, $.fn.weiboPraise.defaults, options); 
	    this.$praiseUserTmpl = $("#praise-user-tmpl");
	    this.$praiseContainerTmpl = $("#praise-container-tmpl");
	    this.initialized();
	};
	weiboPraise.prototype = {
	    initialized : function(){
	    	this.settings = {};
	    	$.extend(this.settings, LT);
	    	this.settings.delegateEvents(this);//继承events事件
	    	this.$post = this.$el.closest('li[weiboMsgLi]');
	    	this.rend();
	    },
	    rend : function(){
	    	var postId = this.options.postId;
	    	this.$container = this.$post.find('.list-open-praise');
	    	if(this.$container.length < 1){
	    		this.$container = $($.mustache(this.$praiseContainerTmpl.html(),{postId:postId}));
	    		this.$post.append(this.$container);
			}
			this.$container.hide();
	    	var users = this.options.likeUsers;
	    	if(!users){
	    		return '';
	    	}
			if(users.length > 0){
				var likeUserHtml = '这些人点了赞：';
				for ( var i = 0; i < users.length; i++) {
					if(i==0){ //第一位不需要顿号
						users[i].dot = "";
						users[i].postId = postId;
						likeUserHtml += $.mustache(this.$praiseUserTmpl.html(),users[i]);
					} else {
						users[i].dot = "、";
						users[i].postId = postId;
						likeUserHtml += $.mustache(this.$praiseUserTmpl.html(),users[i]);
					}
				}
				this.$container.html(likeUserHtml);
				this.$container.show();
			} else {
				this.$container.hide();
			}
	    },
	    reset : function(option){
	        this.options = $.extend({},this.options,option);
	        var users = this.options.likeUsers;
	        if(!users){
	        	users = this.options.likeUsers = new Array();
	        }
	        if(this.options.addMe){
	        	users.push(this.options.addMe);
	        } else if(this.options.deleteMe){
	        	for(var i=0; i<users.length; i++){
			        if(users[i].weiboUserId == this.options.deleteMe.weiboUserId){
			        	users.splice(i,1);
			        }
		        }
	        }
	        this.options.likeUsers = users;
	        this.rend();
	    },
	    events : {
	    	
	    }
	};
  
	$.fn.weiboPraise = function (option) {
		return this.each(function(input_field) {
			var $this = $(this), data = $this.data('weiboPraise');
		    if (!data) {
		    	$this.data('weiboPraise', (data = new weiboPraise(this, option)));
		    } else {
		    	data.reset(option);
		    }
		    //option 是方法名
		    if (typeof option == 'string'){
		    	if(!data[option]){
		    		console.log( 'Method ' + option + ' does not exist on jQuery.weiboPraise' );
		    	} else {
		    		data[option]();
		    	}
		    }
		});
	};
	$.fn.weiboPraise.defaults = {
		likeUsers:null,
		postId:null,
		deleteMe:null,
		addMe:null
	};
})(window.jQuery);
