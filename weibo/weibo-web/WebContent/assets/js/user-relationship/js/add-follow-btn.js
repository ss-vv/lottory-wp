(function ($){
	var followTitle = '加关注';
	var unfollowTitle = '取消关注';
	var followView = '+加关注';
	var unfollowView = '取消关注';
	
	var addFollowBtn = function ($el,option){
		this.options = $.extend({},$.fn.addFollowBtn.defaults,option);
		this.$el = $el;
		this.$tmpl = $("#follow-btn-tmpl");
		this.init();
	}
	addFollowBtn.prototype = {
		init : function (){
			this.render();
			this.bind();
		},
		render : function (){
			var html=$.mustache(this.$tmpl.html(),{
				title: this.options.flag == 'follow'? followTitle:unfollowTitle,
				view: this.options.flag == 'follow'? followView:unfollowView
			});
			this.$el.html(html);
		},
		bind : function (){
			var $this = this;
			this.$el.find(".addFriendBtn").click(function (){
				if($this.options.flag == 'follow'){
					var userId = $this.options.userId;
					if(logind == false){
						//弹出登录框
						displayLogin();
						
					}
					$.post("/ajax_rltship_follow",{followIds:userId}, function(data , e) {
						if (data != null) {
							if (data.success) {
								$this.options.flag = 'unfollow';
								$this.$el.addFollowBtn($this.options);
								//回调成功后的方法
								$this.options.success($this.$el,$this.options.display);
							} else {
								//alert(data.desc);
								if(data.resultCode == "notlogin"){
									//window.location.href="http://www.davcai.com/";
									displayLogin();
								}
								$this.options.fail($this.$el);
							}
						}
					}, 'json');
				} else {
					var userId = $this.options.userId;
					$.post("/ajax_rltship_unfollow",{unfollowIds:userId}, function(data , e) {
						if (data != null) {
							if (data.success) {
								$this.options.flag = 'follow';
								$this.$el.addFollowBtn($this.options);
							} else {
								//alert(data.desc);
								if(data.resultCode == "notlogin"){
									//window.location.href="http://www.davcai.com/";
									displayLogin();
								}
							}
						}
					}, 'json');
				}
			});
		},
		refresh : function (){
			this.render();
			this.bind();
		}
	}
	$.fn.addFollowBtn = function (option) {
		return this.each(function() {
			var $this = $(this),
				data = $this.data('addFollowBtn');
	          if (!data) {
	        	  $this.data('addFollowBtn', (data = new addFollowBtn($this, option)));
	          } else {
	        	  data.refresh(option);
	          }
        });
	};
	$.fn.addFollowBtn.defaults = {
		flag:'follow',
		userId:'',
		success : function ($el){
			return "";
		},
		fail : function ($el){
			return "";
		}
	};
})(window.jQuery);