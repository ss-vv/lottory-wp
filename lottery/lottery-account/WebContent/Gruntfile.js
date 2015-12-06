module.exports = function(grunt) {
	  grunt.initConfig({
		  concat: {
			weibo_detail_follow: {
			  src: [
			        "js/jquery.js",
			        "js/jquery-ui.js",
			        "js/tool.js",
			        "js/jquery.blockUI.js",
			        "js/jquery.validate.js",
			        "js/weibo/weibo_schame_detail.js",
			        "js/bootstrap.min.js"],
			  dest: 'js/weibo_detail_follow.js',
			},
			weibo_detail_recommend:{
				 src: [
				        "js/jquery.js",
				        "js/jquery-ui.js",
				        "js/tool.js",
				        "js/jquery.blockUI.js",
				        "js/jquery.validate.js",
				        "js/weibo/weibo_recommend_detail.js",
				        "js/bootstrap.min.js"],
				dest: 'js/weibo_detail_recommend.js',
			}
		  },
		  uglify: {
			  	weibo_detail_follow: {
			  		src: ["js/weibo_detail_follow.js"],
			  		dest: 'js/weibo_detail_follow.min.js',
				},
				weibo_detail_recommend:{
					src: ["js/weibo_detail_recommend.js"],
					dest: 'js/weibo_detail_recommend.min.js',
				}
		  },
		  cssmin: {
			  combine: {
				  files: {
					  'css/scheme_info.css': 
						  ["css/layout.css",
						   "css/ucenter.css",
						   "css/topNew.css",
						   "css/bootstrap-theme.min.css",
						   "css/bootstrap.min.css",
						   "css/bet_stamp.css",
						   "css/list-paper.css"]
				  }
			  }
		  }
	  });
	  grunt.loadNpmTasks('grunt-contrib-concat');
	  grunt.loadNpmTasks('grunt-contrib-uglify');
	  grunt.loadNpmTasks('grunt-contrib-cssmin');
	  grunt.registerTask('default', ['concat','uglify','cssmin']);
};