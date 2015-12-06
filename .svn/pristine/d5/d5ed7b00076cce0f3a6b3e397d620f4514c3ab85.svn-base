;(function($, undefined) {
	var MY_FOLLOWING_IDS;//插件全局使用对象
	var GlobalPostFollowersArray = {}; //全局微博实单跟单用户集合数组 GlobalPostFollowersArray[postId]=[followers1,followers2];
	var REAL_FOLLOWER_PAGE_SIZE = 15;
	/**
	 * 实单微博跟单用户
	 */
	var realWeiboFollowUsers = function(element,options){
	    this.$el = $(element);
	    this.options = $.extend({}, $.fn.realWeiboFollowUsers.defaults, options);
	    this.weiboType = options.weiboType;
	    this.weiboTypeName = (3 == options.weiboType) ? "合买" : "跟单"; 
	    this.$followUserTmpl = $("#real-weibo-follow-user-tmpl");
	    this.followStatisticTmpl = '<div style="margin:8px 10px 0px">' + this.weiboTypeName 
	    		+ '总人数：{{followCount}}，' + this.weiboTypeName 
	    		+ '总金额：{{buyCount}}{{totalAward}}</div>';
	    this.postPagerTmpl = '<div style="clear: both;" _followerPager id="postId_{{postId}}_follower_pager" _postId="{{postId}}">{{{pagerHtml}}}</div>';
	    this.initialized();
	};
	realWeiboFollowUsers.prototype = {
	    initialized : function(){
	    	if(!MY_FOLLOWING_IDS){
		    	$.ajax("http://www.davcai.com/aj_get_followingsids",{
		    		async:false,
		    		success:function (data){
		    			MY_FOLLOWING_IDS = data;
		    		},
		    		dataType:'json'
		    	});
	    	}
	    	if(!MY_FOLLOWING_IDS){//获取我关注的用户失败(可能是未登录)
	    		MY_FOLLOWING_IDS = [];//把我关注的人设置为0
	    	}
	    	this.settings = {};
	    	$.extend(this.settings, LT);
	    	this.settings.delegateEvents(this);//继承events事件
	    	this.$post = this.$el.closest('li[weiboMsgLi]');
	    	this.rend();
	    },
	    rend : function(){
	    	this.$myRealFollowersView = this.$el.find('#real_post_'+this.options.postId+'_my_followers');
	    	this.$realFollowersView = this.$el.find('#real_post_'+this.options.postId+'_followers');
	    	this.$myRealFollowersView.html(this.myRealFollowersView());
	    	this.$realFollowersView.html(this.realFollowersView());
	    	this.bind();
	    },
	    bind:function (){
	    	var self = this;
	    	//实单微博查看更多事件
			$("a[_muchUserLink]",this.$post).click(function (){
				var $this = $(this);
				var followersDiv = self.$realFollowersView;
				var myFollowersDiv = self.$myRealFollowersView;
				if(followersDiv.is(":hidden")){
					followersDiv.show();
					myFollowersDiv.hide();
				} else {
					myFollowersDiv.show();
					followersDiv.hide();
				}
			});
	    	$("a[data-page]",$("[_followerPager]",this.$post)).click(function (){
				var $this = $(this);
				self.bindFollowerPageEvent($this);
			});
	    },
	    reset : function(option){
	    	this.options = $.extend({}, this.options, option);
	    	this.rend();
	    },
	    events : {
	    	
	    },
	    myRealFollowersView:function (){
	    	var usersInfo = this.calRealFollowerByMyFollowings(this.options.realFollowers);
			if(usersInfo){
				var realFollowers = usersInfo.followUsers;
				if(realFollowers.length > 0){
					var html = "";
					for ( var i = 0; i < realFollowers.length && i < 3; i++) {
						if(realFollowers[i].afterTaxBonus && realFollowers[i].afterTaxBonus > 0){
							realFollowers[i].awardAmount = "奖金:<i>" + LTMath.roundNumber(realFollowers[i].afterTaxBonus,2) + "元</i>";
						} else {
							realFollowers[i].awardAmount ="";
						}
						if(!realFollowers[i].nickName){
							realFollowers[i].nickName = "匿名";
						}
						if(!realFollowers[i].headImageURL){
							realFollowers[i].headImageURL=LT.Settings.RESOURCE.DEFAULT_HEADER;
						}
						realFollowers[i].weiboType = this.weiboTypeName;
						html+=$.mustache(this.$followUserTmpl.html(),realFollowers[i]);
					}
					var htmlSecond = this.getMyFollowStatisticHtml(usersInfo.followUsersName);;
					htmlSecond += this.getMutchFollowHtml(this.options.postId);
					html= htmlSecond + "<ul class='list-open-form'>"+ html + "</ul>";
					return '<div class="list-open">' + html + '</div>';
				} else if(usersInfo.notFollowUsers && usersInfo.notFollowUsers.length >0){
					var html = "";
					realFollowers = usersInfo.notFollowUsers;
					for ( var i = 0; i < realFollowers.length && i < 3; i++) {
						if(realFollowers[i].afterTaxBonus && realFollowers[i].afterTaxBonus > 0){
							realFollowers[i].awardAmount = "奖金:<i>" + LTMath.roundNumber(realFollowers[i].afterTaxBonus,2) + "元</i>";
						} else {
							realFollowers[i].awardAmount ="";
						}
						if(!realFollowers[i].nickName){
							realFollowers[i].nickName = "匿名";
						}
						if(!realFollowers[i].headImageURL){
							realFollowers[i].headImageURL="http://www.davcai.com/images/default_face.jpg";
						}
						realFollowers[i].weiboType = this.weiboTypeName;
						html+=$.mustache(this.$followUserTmpl.html(),realFollowers[i]);
					}
					var htmlSecond = "";
					if(realFollowers.length > 3){
						htmlSecond = this.getMutchFollowHtml(this.options.postId);
					}
					html= this.getFollowStatisticHtml(realFollowers) + htmlSecond + "<ul class='list-open-form'>"+ html + "</ul>";
					return '<div class="list-open">' + html + '</div>';
				}
				return '';
			}
			return '';
	    },
	    realFollowersView:function (){ //全部跟单用户信息
			var usersInfo = this.calRealFollowerByMyFollowings(this.options.realFollowers);
			if(usersInfo){
				var realFollowers = usersInfo.followUsers.concat(usersInfo.notFollowUsers);
				GlobalPostFollowersArray[this.options.postId] = realFollowers; //把该实单微博跟单用户存入全局
				if(realFollowers.length > 0 && realFollowers.length <= REAL_FOLLOWER_PAGE_SIZE){//小于1页，不分页
					return '<div class="list-open">' + this.renderRealFollowers(realFollowers,usersInfo.followUsersName,this.options.postId) + '</div>';
				} else if(realFollowers.length > 0 && realFollowers.length > REAL_FOLLOWER_PAGE_SIZE){ //长度大于每页最大值，分页
					return '<div class="list-open">' +  this.renderRealFollowersByPage(realFollowers,usersInfo.followUsersName,this.options.postId)  + '</div>';
				} else {
					return '';
				}
				return '';
			}
			return '';
		},
		calRealFollowerByMyFollowings : function (realFollowers){//计算跟单用户中我关注的用户和我未关注的用户			
	    	var usersInfo = {};
			var followUsers = new Array();
			var followUsersName = new Array();//存储我关注的人也跟了此单:昵称
			var followUsersNameMap = {};//存储我关注的人也跟了此单:userId-昵称
			var notFollowUsers = new Array();
			
			if(realFollowers && realFollowers.length > 0){
				var myFollowingsIdMap = {};
				if(MY_FOLLOWING_IDS && MY_FOLLOWING_IDS.length > 0){
					for (var i = 0; i < MY_FOLLOWING_IDS.length; i++) {
						myFollowingsIdMap[MY_FOLLOWING_IDS[i]]=MY_FOLLOWING_IDS[i];
					}
				}
				for ( var i = 0; i < realFollowers.length; i++) {
					var userId = realFollowers[i].weiboUserId;
					if(myFollowingsIdMap[userId] && myFollowingsIdMap[userId] > 0){
						followUsers.push(realFollowers[i]);
						if(followUsersNameMap[userId]){
							
						} else {
							followUsersNameMap[userId] = realFollowers[i].nickName;
							followUsersName.push(realFollowers[i].nickName);
						}
					} else {
						notFollowUsers.push(realFollowers[i]);
					}
				}
			}
			usersInfo.followUsers = followUsers;
			usersInfo.notFollowUsers = notFollowUsers;
			usersInfo.followUsersName = followUsersName;
			return usersInfo;
		},
		getMutchFollowHtml:function(postId){
			return '<a href="javascript:void(0);"  style="float:right;margin-top: -20px;margin-right: 25px;"  _muchUserLink _postId="'+postId+'">查看更多</a>';
		},
		getLessFollowHtml:function(postId){
			return '<a style="float:right;margin-top: -20px;margin-right: 25px;" href="javascript:void(0);" _muchUserLink _postId="'+postId+'">收起</a>';
		},
		getMyFollowStatisticHtml:function(followUsersName){
			var userNames = followUsersName;
			var htmlSecond = "";
			if(userNames && userNames.length > 0){
				htmlSecond += '<div  style="margin:5px 10px 0px">我关注的：';
				for(var i=0; i< userNames.length - 1 && i < 2; i++){
					htmlSecond += userNames[i] + "、";
				}
				if(userNames.length >= 3 ){
					htmlSecond += userNames[2];
				} else {
					htmlSecond += userNames[userNames.length -1];
				}
				if(this.weiboType == 3) {
					htmlSecond += "等" + userNames.length + "人参与合买了此单。</div>";
				} else {
					htmlSecond += "等" + userNames.length + "人跟了此单。</div>";
				}
			}
			return htmlSecond;
		},
		getFollowStatisticHtml:function(realFollowers){
			var followCount = 0;
			var buyCount = 0;
			var totalAward = 0;
			for ( var i = 0; i < realFollowers.length; i++) {
				followCount++;
				buyCount += parseInt(realFollowers[i].buyAmount);
				totalAward += realFollowers[i].afterTaxBonus;
			}
			var htmlFirst = $.mustache(this.followStatisticTmpl,{
				followCount:followCount,
				buyCount:buyCount + " 元",
				totalAward:totalAward && totalAward > 0 ? "," + this.weiboTypeName + "总奖金：￥"+LTMath.roundNumber(totalAward,2):""
			});
			return htmlFirst;
		},
		renderRealFollowers:function(realFollowers,followUsersName,postId){
			var html = "";
			for ( var i = 0; i < realFollowers.length; i++) {
				if(realFollowers[i].afterTaxBonus && realFollowers[i].afterTaxBonus > 0){
					realFollowers[i].awardAmount = "奖金:<i>" + LTMath.roundNumber(realFollowers[i].afterTaxBonus,2) + "元</i>";
				} else {
					realFollowers[i].awardAmount ="";
				}
				if(!realFollowers[i].nickName){
					realFollowers[i].nickName = "匿名";
				}
				if(!realFollowers[i].headImageURL){
					realFollowers[i].headImageURL="http://www.davcai.com/images/default_face.jpg";
				}
				realFollowers[i].weiboType = this.weiboTypeName;
				html+=$.mustache(this.$followUserTmpl.html(),realFollowers[i]);
			}
			html= this.getFollowStatisticHtml(realFollowers) +
			 	this.getLessFollowHtml(postId) +
				this.getMyFollowStatisticHtml(followUsersName) + 
				"<ul  class='list-open-form'>"+ html + "</ul>";
			return html;
		},
		renderRealFollowersByPage:function (realFollowers,followUsersName,postId){
			var html = "";
			for ( var i = 0; i < REAL_FOLLOWER_PAGE_SIZE; i++) {
				if(realFollowers[i].afterTaxBonus && realFollowers[i].afterTaxBonus > 0){
					realFollowers[i].awardAmount = "奖金:<i>" + LTMath.roundNumber(realFollowers[i].afterTaxBonus,2) + "元</i>";
				} else {
					realFollowers[i].awardAmount ="";
				}
				html+=$.mustache(this.$followUserTmpl.html(),realFollowers[i]);
			}
			var totalPages = parseInt((realFollowers.length + REAL_FOLLOWER_PAGE_SIZE -1)/REAL_FOLLOWER_PAGE_SIZE);
			var pageHtml = $.mustache(this.postPagerTmpl,{
				postId:postId,
				pagerHtml:ajaxPager("javascript:void(0);",1,totalPages)
			});
			html= this.getFollowStatisticHtml(realFollowers) +  
				this.getLessFollowHtml(postId) +
				this.getMyFollowStatisticHtml(followUsersName) + 
				"<div><ul  class='list-open-form' id='"+postId+"_follow_users_div'>"+ html + "</ul></div>" + pageHtml;
			return html;
		},
		bindFollowerPageEvent:function ($this){
			var self = this;
			var followersDiv =  $this.closest("li[weiboMsgLi]").find("[realPostFollowers] ul[class='list-open-form']");
			var pageDiv = $this.closest("div[_followerPager]");
			var postId = pageDiv.attr("_postId");
			var pageNum = parseInt($this.attr("data-page"));
			var realFollowers = GlobalPostFollowersArray[postId];
			
			var html = "";
			for (var i = REAL_FOLLOWER_PAGE_SIZE*(pageNum-1),j=0;i < realFollowers.length && j<REAL_FOLLOWER_PAGE_SIZE; i++,j++) {
				if(realFollowers[i].afterTaxBonus && realFollowers[i].afterTaxBonus > 0){
					realFollowers[i].awardAmount = "奖金:<i>" + LTMath.roundNumber(realFollowers[i].afterTaxBonus,2) + "元</i>";
				} else {
					realFollowers[i].awardAmount ="";
				}
				html+=$.mustache(this.$followUserTmpl.html(),realFollowers[i]);
			}
			var totalPages = parseInt((realFollowers.length + REAL_FOLLOWER_PAGE_SIZE -1)/REAL_FOLLOWER_PAGE_SIZE);
			var pageHtml = ajaxPager("javascript:void(0);",pageNum,totalPages);
			followersDiv.html(html);
			pageDiv.html(pageHtml);
			bindMouseInHeadOrNicknameEvent(followersDiv);
			$("a[data-page]",pageDiv).click(function (){
				var $this = $(this);
				self.bindFollowerPageEvent($this);
			});
		}
	};
  
	$.fn.realWeiboFollowUsers = function (option) {
		return this.each(function(input_field) {
			var $this = $(this), data = $this.data('realWeiboFollowUsers');
		    if (!data) {
		    	$this.data('realWeiboFollowUsers', (data = new realWeiboFollowUsers(this, option)));
		    } else {
		    	data.reset(option);
		    }
		    //option 是方法名
		    if (typeof option == 'string'){
		    	if(!data[option]){
		    		console.log( 'Method ' + option + ' does not exist on jQuery.realWeiboFollowUsers' );
		    	} else {
		    		data[option]();
		    	}
		    }
		});
	};
	$.fn.realWeiboFollowUsers.defaults = {
		postId:null,
		realFollowers:null
	};
})(window.jQuery);
