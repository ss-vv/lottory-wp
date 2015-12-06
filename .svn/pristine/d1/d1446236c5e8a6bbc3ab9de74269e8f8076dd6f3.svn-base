module.exports = function(grunt) {
	  grunt.initConfig({
		  concat: {
			davcaiAllJS:{
				src: [  "js/web/plugins/cookie/jquery.cookie.js",
						"js/web/jquery.blockUI.js",
						"js/widgets/jquery.matchurl.js",
						"js/web/placeholder.js",
						"js/web/bootstrap.min.js",
						"js/ui.core.js",
						"js/web/load-header.js",
						"js/web/common.js",
						"js/web/ye_dialog.js",
						"js/web/jquery-ias.js",
						"js/web/jquery.mustache.js",
						"js/web/jquery.util.js",
						"js/web/search.js",
						"js/web/lottery.js",
						//"js/web/load-header-background.js",
						"js/widgets/jquery.atwho.js",
						"js/widgets/jquery.autocomplete.js",
						"js/web/atDemo.js",
						"js/web/label-slide.js",
						"js/web/jquery.dateFormat-1.0.js",
						"js/web/daily_topics.js",
						"js/web/relationship.js",
						"js/web/float-dialog/float-user-info.js",
						"js/web/float-dialog/float-match-info.js",
						
						"js/web/recommend-user.js",
						"js/web/login.js",
						
						"js/web/jquery-ui/jquery.ui.datepicker.js", 
						"js/web/match/match-schedule.js", 
						"js/web/pager.js", 
						"js/web/jquery.form.min.js", 
						 
						"js/web/at.js", 
						"js/web/share.js", 
						"js/web/mlellipsis.js", 
						"js/web/bet-ssq-common.js", 
						"js/web/bet-jx11-common.js",
						"js/web/comments.js", 
						"js/web/home/announceAndWinning.js",
						"js/web/home/interest_user.js",
						"js/web/home.js",
						
						//微博消息
						"assets/js/core/base.js",
						"assets/js/core/lottery.events.js",
						"assets/js/core/lottery.settings.js",
						"assets/js/user-relationship/js/add-follow-btn.js",
						"assets/js/user-relationship/js/interest-user.js",
						"assets/js/cometd/lib/cometd.js",
						"assets/js/cometd/jquery/jquery.cometd.js",
						"assets/js/bet/match-score-sub.js",
						"assets/js/weibo-msg/weibo-list.js",
						"assets/js/weibo-msg/weibo-list-panel.js",
						"assets/js/weibo-msg/forward-dialog.js",
						"assets/js/weibo-msg/share-dialog.js",
						"assets/js/weibo-msg/comments-container.js",
						"assets/js/weibo-msg/comment-list.js",
						"assets/js/weibo-msg/praise.js",
						"assets/js/weibo-msg/real-weibo-follow-users.js",
						"assets/js/bet/scheme-view.js",
						"assets/js/bet/follow-real-weibo-dialog.js",
						"assets/js/bet/groupbuy-real-weibo-dialog.js",
						//投注记录
						"assets/js/match/push-match-type.js",
						"assets/js/bet/status-tool.js",
						"assets/js/bet/lottery-tab.js",
						"assets/js/bet/bet-record-panel.js",
						"assets/js/bet/bet-record-list.js",
						//精华
						"assets/js/marrow/marrow.js",
						"assets/js/marrow/winning.js",
						"assets/js/marrow/hot-discuss.js",
						"assets/js/marrow/latest-publish.js",
						//推荐
						"assets/js/recommend/recommend.js",
						"assets/js/realfollow/real-and-follow.js",
						"assets/js/backtop/backtop.js",
						
						//通知
						"js/web/notification.js"
					 ],
				dest: 'js/davcai-web.js'
			}
		  },
		  uglify: {
			  davcaiAllJS:{
				  src: ['js/davcai-web.js'],
				  dest: 'js/davcai-web.min.js'
			  }
		  },
		  cssmin: {
			  combine: {
				  files: {
					  'css/davcai-web.css': 
						  ['css/bootstrap.css',
						   'css/home.css',
						   'css/emoticon.css',
						   'css/jquery.ias.css',
						   'css/index.css',
						   'css/widgets.css',
						   'css/search.css',
						   'css/base.css',
						   'css/header.css',
						   'css/body.css',
						   'css/cp_type.css',
						   'css/wb_list.css',
						   'css/footer.css',
						   'css/jquery.atwho.css',
						   'css/jquery.autocomplete.css',
						   'css/label-slide.css',
						   'css/float-left.css',
						   'css/float-match.css',
						   'css/follow-page.css',
						   'css/ui-lightness/jquery-ui.css',
						   'css/match-schedule.css',
						   'css/list-open.css',
						   'css/roll-winners.css',
						   'css/pop.css',
						   'css/post-and-share.css',
						   'assets/js/user-relationship/css/relationship.css',
						   'assets/js/bet/css/base.css',
						   'assets/js/bet/css/module.css',
						   'assets/js/bet/css/bet-record.css',
						   'assets/js/bet/css/new-scheme.css',
						   'css/police.css',
						   //分析页
						   'css/davcai/analyse/betting-data.css',
						   'css/davcai/analyse/base.css',
						   //赔率页
						   'css/davcai/odds/fb-odds.css',
						   'css/davcai/odds/fb-odds-base.css',
						   'assets/js/backtop/backtop.css']
				  }
			  }
		  }
	  });
	  grunt.loadNpmTasks('grunt-contrib-concat');
	  grunt.loadNpmTasks('grunt-contrib-uglify');
	  grunt.loadNpmTasks('grunt-contrib-cssmin');
	  grunt.registerTask('default', ['concat','uglify','cssmin']);
};
