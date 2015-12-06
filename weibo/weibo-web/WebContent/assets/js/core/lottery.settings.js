/**
 *  ttevents.js
 * 	author: fugang 
 * 	date: 2014-04-02
 */

;(function () {
	site_domain = "http://www.davcai.com/";//www.davcai.com
	LT = LT || {};
	LT.Settings = $.extend({}, {
		/**
		 * 资源目录名
		 */
		ASSETS : "",

		/**
		 * 静态资源
		 * @type {Object}
		 */
		RESOURCE : {
			//默认头像
			DEFAULT_HEADER 		: 'http://www.davcai.com/images/default_face.jpg',
			DEFAULT_VIDEO 		: '',
			DEFAULT_LINKICON 	: '',
			loading8 			: '',
			loading16 			: '',
			DEFAULT_IE 			:  ''
		},

		/**
		 * 接口集合
		 * @type {Object}
		 */
		URLS : {
			home : {
				aj_fb_selector 		: site_domain+'aj_fb_selector.do',
				aj_bb_selector 		: site_domain+'aj_bb_selector.do',
				aj_bjdc_selector	: site_domain+'aj_bjdc_selector'
			},
			weibo : {
				loadRecommends 		: site_domain+'loadRecommends',
				loadDiscussWeibo 	: site_domain+'loadDiscussWeibo',
				loadNews 			: site_domain+'loadNews',
				loadPost 			: site_domain+'loadPost',
				getfollowingsids 	: site_domain+'aj_get_followingsids',
				weibo_new_recommends: site_domain+'weibo_new_recommends',
				weibo_new_discuss 	: site_domain+'weibo_new_discuss',
				weibo_new_news 		: site_domain+'weibo_new_news',
				weibo_new_posts 	: site_domain+'weibo_new_posts',
				newPostTimer 		: site_domain+'newPostTimer',
				newRecTimer 		: site_domain+'newRecTimer',
				newMatchTimer 		: site_domain+'newMatchTimer',
				newNewsTimer 		: site_domain+'newNewsTimer',
				newDiscussWeiboTimer: site_domain+'newDiscussWeiboTimer',
				publish				: site_domain+'publish'
			},
			match_weibo : {
				loadRealMatchPost 		: site_domain+'loadRealMatchPost',
				loadMatchDiscussPost 	: site_domain+'loadMatchDiscussPost',
				loadMatchsNews 			: site_domain+'loadMatchsNews',
				loadMatchPost 			: site_domain+'loadMatchPost',
				load_new_matchs_realMatch: site_domain+'load_new_matchs_realMatch',
				load_new_matchs_discuss 	: site_domain+'load_new_matchs_discuss',
				load_new_matchs_news 		: site_domain+'load_new_matchs_news',
				load_new_matchs_posts 	: site_domain+'load_new_matchs_posts',
				newMatchPostTimer 		: site_domain+'newMatchPostTimer',
				newRealMatchPostTimer 		: site_domain+'newRealMatchPostTimer',
				newMatchNewsTimer 		: site_domain+'newMatchNewsTimer',
				newMatchDiscussTimer 		: site_domain+'newMatchDiscussTimer'
			},
			user_relationship : {
				interest_user 		: site_domain+'interest_user?size=60'
			},
			winningnews:{
				winningnew          : site_domain+'ajax/ajaxLoadWinningNews.do'
			},
			rankinglist:{
				spf_shenglv_7day    :site_domain+'ajax_day7ShengLv', //推荐页胜平负 7日胜率
				spf_shenglv_50match :site_domain+'ajax_match50ShengLv', //推荐页胜平负 50场胜率
				spf_yingli_7day     :site_domain+'/ajax_day7YingLi',  //推荐页胜平负 7日盈利
				spf_yingli_50match  :site_domain+'/ajax_match50YingLv', //推荐页胜平负 50场盈利
				showscheme_shenglv_7day :site_domain+'/newwinlist/ajax_showDay7SchemeWinShengLv', //晒单页面 7日晒单胜率
				showscheme_shenglv_50match:site_domain+'/newwinlist/ajax_showMatch50SchemeWinShengLv', //晒单页面 50场晒单胜率
				followscheme_shenglv_7day :site_domain+'/newwinlist/ajax_showDay7FollowSchemeWinYingliLv',//晒单页 跟单7日胜率
				followscheme_shenglv_50match:site_domain+'/newwinlist/ajax_showMatch50FollowShemeWinYingliLv',//晒单页 跟单50场胜率
				showscheme_prize_7day :site_domain+'/newwinlist/ajax_showDay7SchemeWin',//晒单页 晒单助人 7日
				showscheme_prize_50match :site_domain+'/newwinlist/ajax_showMatch50SchemeWin',//晒单页 晒单助人 50单
				followscheme_win_7day :site_domain+'/newwinlist/ajax_showDay7FollowSchemeWin',//晒单页 跟单中奖  7日
				followscheme_win_50match :site_domain+'/newwinlist/ajax_showMatch50FollowSchemeWin' //晒单页 跟单中奖  50单
				
			},
			bonus:{
				bonuslist           :site_domain+'/prize/ajax_winprize' //中奖喜报页面中奖排行版
			},
			active:{
				activeuser          :site_domain+'/activeuser/ajax_activeuser'//活跃用户
			},
			comment:{
				loadComments 		:site_domain+'list_cmnts',
				postComment 		:site_domain+'post_cmnt'
			},
			like:{
				like				:site_domain+'like',
				unlike				:site_domain+'unLike',
				ajaxLoadFavoriate	:site_domain+'ajaxLoadFavoriate'
			},
			marrow:{
				ajaxWinList				:site_domain+'ajax-winning-list',
				ajaxFollowingWinList	:site_domain+'ajax-following-winnings',
				ajaxHotDiscussList		:site_domain+'ajax-hot-discuss',
				ajaxLatestPublishList	:site_domain+'ajax-latest-publish'
			},
			betRecord:{
				ajaxUserBetRecord : site_domain+'/bet/userBetRecord',
				ajaxViewBetScheme : site_domain+'/bet/scheme',
			},
			recommend:{
				ajaxJCZQ	:site_domain+'recommend/ajax_JCZQ',
				ajaxJCLQ	:site_domain+'recommend/ajax_JCLQ',
				ajaxBJDC	:site_domain+'recommend/ajax_BJDC',
				ajaxALL		:site_domain+'recommend/ajax_ALL',
			},
			realfollow:{
				ajaxJCZQ	:site_domain+'realfollow/ajax_JCZQ',
				ajaxJCLQ	:site_domain+'realfollow/ajax_JCLQ',
				ajaxBJDC	:site_domain+'realfollow/ajax_BJDC',
				ajaxALL		:site_domain+'realfollow/ajax_ALL',
			}
		},
		
		//工程版本号
		VERSION : "",
		
		//网站访问连接
		LT_BASE_URL : site_domain,
	});
})();