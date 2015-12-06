package com.unison.lottery.weibo.common.nosql.impl;

import com.unison.lottery.weibo.data.RecentDateType;
import com.unison.lottery.weibo.data.Top5Recommend;
import com.unison.lottery.weibo.lang.BetRecordConstant;
import com.unison.lottery.weibo.utils.MD5Util;
import com.xhcms.commons.util.Text;
import com.xhcms.lottery.commons.data.HotAndRecommendMatch;
import com.xhcms.lottery.lang.CertificationUserType;


/**
 * Laicai weibo Redis keys.
 * 
 * @author Yang Bo
 */
public class Keys {

	// 微博
	public final static String GLOBAL_NEXT_POST_ID = "global:nextId:WeiboMsg";
	public final static String ATME = "uid:%s:atMe";
	public final static String POST = "id:WeiboMsg:%s";
	public final static String MY_POSTS = "uid:%s:posts";
	public final static String USER_MAIN_TIMELINE = "uid:%s:timeline";
	public final static String GLOBAL_TIMELINE = "global:timeline";
	public final static String USER_DISCUSS_TIMELINE = "uid:%s:discuss";

	// 评论及回复
	public final static String COMMENTS_OF_POST = "pid:%s:comments";
	public final static String GLOBAL_NEXT_COMMENT_ID = "global:nextId:Comment";
	public final static String ALL_COMMENTS_OF_UID = "uid:%s:allComments";
	public final static String COMMENT = "id:Comment:%s";
	public final static String DIRECT_REPLIES = "uid:%s:followingComments";
	public final static String FOLLOWING_COMMENTS = "uid:%s:followingComments";
	public final static String PRAISE_COMMENT_USERS = "praise:comment:%s";
	public final static String MY_COMMENTS = "uid:%s:myComments";
	
	//微博赞统计
	public final static String WEIBO_LIKE = "pid:%s:likes";
	
	//私信相关
	/**私信对象*/
	public final static String PRIVATE_MSG = "privateMsg:%d";
	/**私信会话用户ID集合*/
	public final static String PRIVATE_SESSION_USERS = "uid:%d:privateSessionUser";
	/**分组用户私信ID集合*/
	public final static String PRIVATE_PEER_MSGS = "uid:%d:peerId:%d:privateMsgs";
	/**个人私信收件箱*/
	public final static String PRIVATE_MSG_RECEIVED = "uid:%d:receivedPrivateMsg";
	
	/**发布过的全部私信*/
	public final static String PRIVATE_MSG_POSTED = "uid:%d:postedPrivateMsg";
	
	
	public final static String FAVOURITES = "uid:%d:favorites";
	
	/** 大V彩微博用户 */
	public final static String GLOBAL_NEXT_USER_ID = "global:nextUserId";
	public final static String USER = "user:%s";
	public final static String NICKNAME = "nickname:%s";
	public final static String WEIBO_USERS = "weiboUsers";
	public final static String WeiboUserStatis = "user:%s:statis";

	/**近7天中奖金额前十*/
	public final static String Last7TotalBonusTop10 = "Last7TotalBonusTop10";
	
	/**近7天过单率前十*/
	public final static String Last7GuoDanLvTop10 = "Last7GuoDanLvTop10";

	/** 微博发帖量排行*/
	public final static String WeiboCountRankList = "WeiboCountRankList";
	
	/** 近7天中奖微博方案集合*/
	public final static String WeiboWinRankList = "WeiboWinRankList";
	
	
	
	/** 我的粉丝*/
	public final static String FOLLOWERS = "uid:%d:followers";
	
	/** 我关注的人*/
	public final static String FOLLOWING = "uid:%d:following";
	/** 用户采纳微博集合 */
	public final static String POST_PRAISE = "uid:%d:postPraise";
	
	/** lt_user id 
	 * 参考 com.xhcms.ucenter.persist.redis.dao.Keys
	 * */
	public final static String LOTTERY_USER_ID= "lotteryUserId:%d";
	public final static String SINA_WEIBO_UID = "sinaWeiboUid:%s";
	public final static String QQ_WEIBO_UID = "qqWeiboUid:%s";
	private static final String QQ_CONNECT_UID = "qqConnectUid:%s";
	private static final String WEIXIN_OPEN_UID = "weixinOpenUid:%s";
	
	/** 微博评论数字段 commentCount */
	public final static String COMMENT_COUNT = "commentCount";
	/** 微博转发数字段 forwardCount */
	public final static String FORWARD_COUNT = "forwardCount";
	/** 微博分享数字段 shareCount */
	public final static String SHARE_COUNT = "shareCount";
	/** 微博收藏数字段 favoriateCount */
	public final static String FAVORIATE_COUNT = "favoriateCount";
	/** 微博赞数字段 praiseCount */
	public final static String LIKE_COUNT_FIELD = "likeCount";
	
	/** 今日话题列表 */
	public final static String DAILY_TOPICS = "dailyTopics";
	
	/** 话题 */
	public final static String TOPIC = "id:Topic:%s";
	
	public final static String GLOBAL_NEXT_TOPIC_ID = "global:nextId:Topic";
	
	/**用户设置的彩种**/
	public final static String USER_LOTTERY_SETTINGS = "uid:%s:lotterys";
	
	public final static String BBS_IMPORT_THREAD_ID = "bbsImportThreadId:%d";
	
	/**用户最近投注*/
	public final static String USER_BET_RECORD = "uid:%s:%s:betRecord";
	
	/**方案ID缓存的值*/
	public final static String SCHEME_ID_CACHE = "%s" + BetRecordConstant.SCHEME_CUT 
			+ "%s" + BetRecordConstant.SCHEME_CUT + "%s";
	
	/** 彩种的推荐时间线 */
	//public final static String LOTTERY_RECOMMENDS = "lottery:%s:recommends";

	/** 彩种的比赛时间线 */
	//public final static String LOTTERY_MATCHES = "lottery:%s:matches";
	
	/** 彩种的新闻时间线 */
	//public final static String LOTTERY_NEWS = "lottery:%s:news";
	
	/** 用户的各种timeline第一次加入时的最后位置（时间戳） */
	public final static String USER_TIMELINE_LAST_POS = "uid:%s:timelineLastPos";

	/** 用户的所有彩种的推荐timeline, UR */
	public final static String USER_RECOMMENDS = "uid:%s:recommends";
	
	/** 用户（包括关注的人）推荐/实单timeline*/
	public final static String USER_RECOM_REALS_TIMELINE = "uid:%s:recomReals:all";
	
	/** 用户自己的推荐/实单timeline */
	public final static String USER_SELF_RECOM_REALS = "uid:%s:recomReals:my";

	/** 用户的所有彩种的比赛timeline, UM */
	public final static String USER_MATCHES = "uid:%s:matches";

	/** 用户的所有彩种的新闻timeline, UN */
	public final static String USER_NEWS = "uid:%s:news";

	/** 最近24小时的热门讨论 */
	public final static String DISCUSS = "discuss:%s";
	
	// --------------- 关注彩种 --------------- 
	/** 某彩种的彩种用户 */
	public final static String LOTTERYID_UID = "lotteryId:%s:uid";
	
	/** 微博用户的彩种 */
	public final static String UID_LOTTERYID = "uid:%s:lotteryId";
	
	/** 彩种用户类型的特殊用户列表 */
	public final static String SPECIAL_USERS_LOTTERY = "specialUsers:lottery";

	/**公告*/
	public final static String ANNOUNCE = "announce";
	
	/**中奖喜报*/
	public final static String WINNINGNEWS = "winningNews";
	public final static String GLOBAL_NEXT_WINNINGNEW_ID = "global:nextId:WinningNew";
	public final static String WINNINGNEW = "id:WinningNew:%s";
	public final static String UID_WINNINGS = "userWinnings:%s";
	
	/**热门推荐*/
	public final static String HOTRECOMMEND = "hotRecommend";
	
	/**最新发表*/
	public final static String LATESTPUBLISH = "latestPublish";
	
	/**比赛全部数据*/
	public final static String MATCH_ALL_CONTENT = "match:global:%s";
	/**每场比赛*/
	public final static String MATCH_ID = "match:%s";
	/**实单比赛*/
	public final static String MATCH_REAL_ID = "matchReal:%s";
	
	/**	赛事讨论时间线 */
	public final static String MATCH_DISCUSS = "match:discuss:%s";
	
	/**微博系统通知*/
	/**新增粉丝*/
	public final static String FOLLOWERS_UNREAD = "user:%s:followers:unread";
	/** 提到我的 */
	public final static String MENTIONS_UNREAD = "user:%s:mentions:unread";
	/** 评论我的 */
	public final static String COMMENTS_UNREAD = "user:%s:comments:unread";
	/** 未读私信 */
	public final static String PRIVATEMSGS_UNREAD = "user:%s:privateMsgs:unread";

	/** 抓取的新闻 */
	public final static String NEWS_CONTENT = "news:%s";
	/** 未发布的新闻 */
	public final static String UNPUBLISH_NEWS = "news:%s:unpublish";
	/** 已发布的新闻 */
	public final static String PUBLISHED_NEWS = "news:%s:published";

	public final static String NEWS_TIMELINE_LOTTERY = "global:LotteryNewsTimeline:%s";
	public final static String NEWS_TIMELINE_TEAM = "global:TeamNewsTimeline:%s";

	/**
	 * 对比赛中球队的赞
	 */
	public final static String MATCH_TEAM_PRAISE = "matchTeamPraise:%s:%s:%s";
	public final static String GLOBAL_TEAM_PRAISE = "global:nextMatchTeamPraiseId";

	/** 本场比赛晒单和推荐的人 */
	public final static String MATCH_SHOW_RECOMMEND_USERS = "matchShowRecommendUsers:%s:%s";

	/** 本场比赛有多少条晒单微博 */
	public final static String MATCH_SHOW_SCHEME = "matchShowScheme:%s:%s";

	/** 本场比赛有多少条推荐微博 */
	public final static String MATCH_RECOMMEND = "matchRecommend:%s:%s";

	/** 和彩种相关的推荐微博 */
	public final static String MATCH_RECOMMEND_LOTTERY = "matchRecommendLottery:%s";

	/** 晒单方案到微博的索引 */
	public final static String SHOW_SCHEME_TO_WEIBO = "showSchemeToWeibo:%s";
	
	/**合买方案到微博的索引*/
	public final static String GROUPBUY_SCHEME_TO_WEIBO = "groupBuySchemeToWeibo:%s";
	
	/**推荐方案到微博的索引*/
	public final static String RECOMMEND_SCHEME_TO_WEIBO = "recommendSchemeToWeibo:%s";

	/** 微博标签 */
	public final static String TAG = "id:WeiboTag:%s";
	public final static String WEIBO_CONTAINS_TAG = "weibo:%s:tags";
	public final static String TAG_NAME = "tag:%s";
	public final static String DYNAMIC_TAG = "tag:dynamic";

	/** 微博认证用户类型索引 */
	public final static String ANALYSIS_EXPERT = "certificationUser:"
			+ CertificationUserType.ANALYSIS_EXPERT.getType();

	public final static String WEIBO_RECOMEND_USER = "weiboRecomendUser";
	public final static String WEIBO_LOTTERY_USER = "weiboLotteryUser";

	/** 实单方案缓存的key(realSchemeCache:schemeId:schemeType) */
	public final static String REAL_SCHEME_CACHE_KEY = "realSchemeCache:%s:%s";
	/** 推荐方案缓存的key */
	public final static String RECOM_SCHEME_CACHE_KEY = "recomSchemeCache:%s";

	/** 未达到最终状态的缓存实单方案集合 */
	public final static String REAL_SCHEME_CACHE_NOT_FINAL = "realSchemeCacheNotFinal";

	/** 首页赛事推荐对象 */
	public final static String HOME_MATCH_RECOMMEND = "id:HomeMatchRecommend:%s";
	/** 首页彩种赛事推荐列表集合 */
	public final static String HOME_MATCH_RECOMMEND_LIST = "homeMatchRecommendList:%s";
	
	/**红包id**/
	public final static String RED_ENVALOPE_LIST = "userId:%s:redEnvalopeList:%s";
	/**抢红包用户set key**/
	public final static String GRAB_RED_ENVALOPE_USER_LIST = "userId:%s:envalopeId:%s:userList";
	/**已抢红包的用户队列
	 */
	public final static String USER_CONSUMED_REDENVALOPE_LIST = "userId:%s:envalopeId:%s:userConsumedEnvalopeList";
	
	public final static String JCZQ_NUM = "jczq_num";
	public final static String JCLQ_NUM = "jclq_num";
	public final static String CTZQ_NUM = "ctzq_num";
	public final static String BJDC_NUM = "bjdc_num";
	public final static String SSQ_NUM = "ssq_num";
	private static final String PUSH_MESSAGE = "id:%s:%s";

	public final static String DAY_7_SHENGLV = "id:Top5Recommend:shenglv:7day";
	public final static String MATCH_50_SHENGLV = "id:Top5Recommend:shenglv:50";
	public final static String DAY_7_YINGLV = "id:Top5Recommend:yinglilv:7day";
	public final static String MATCH_50_YINGLV = "id:Top5Recommend:yinglilv:50";
	public final static String MATCH_50_FOLLOW_SCHEME_BONUS = "id:Top5Recommend:followSchemeBonus:50";// 50场跟单奖金排行
	public static final String DAY_7_FOLLOW_SCHEME_BONUS = "id:Top5Recommend:followSchemeBonus:7day";// 7天跟单奖金排行
	public static final String MATCH_50_FOLLOW_SCHEME_SHENGLV = "id:Top5Recommend:followSchemeShenglv:50";// 50场跟单胜率排行
	public static final String DAY_7_FOLLOW_SCHEME_SHENGLV = "id:Top5Recommend:followSchemeShenglv:7day";// 7天跟单胜率排行
	public final static String MATCH_50_SHOW_SCHEME_HELP_BONUS = "id:Top5Recommend:showSchemeHelp:50";// 50场晒单助人奖金排行
	public static final String DAY_7_SHOW_SCHEME_HELP_BONUS = "id:Top5Recommend:showSchemeHelp:7day";// 7天晒单助人奖金排行
	public static final String MATCH_50_SHOW_SCHEME_SHENGLV = "id:Top5Recommend:showSchemeShenglv:50";// 50场晒单胜率排行
	public static final String DAY_7_SHOW_SCHEME_SHENGLV = "id:Top5Recommend:showSchemeShenglv:7day";// 7天晒单胜率排行
	public static final String HOT_AND_RECOMMEND_MATCH = "id:HotAndRecommendMatch";// 最红赛事排行

	public static final String FB_MATCH_EUROPEODDS = "fb:matchId:%s:europeOdds";
	public static final String FB_MATCH_ASIAODDS = "fb:matchId:%s:asiaOdds";
	public static final String FB_MATCH_BIGORSMALLODDS = "fb:matchId:%s:BigOrSmallOdds";
	public static final String BB_MATCH_EUROPEODDS = "bb:matchId:%s:europeOdds";
	public static final String BB_MATCH_ASIAODDS = "bb:matchId:%s:asiaOdds";
	public static final String BB_MATCH_BIGORSMALLODDS = "bb:matchId:%s:BigOrSmallOdds";

	public static String getFbMatchEuropeodds(String matchId) {
		return String.format(FB_MATCH_EUROPEODDS, matchId);
	}

	public static String getFbMatchAsiaOdds(String matchId) {
		return String.format(FB_MATCH_ASIAODDS, matchId);
	}

	public static String getFbMatchBigOrSmallOdds(String matchId) {
		return String.format(FB_MATCH_BIGORSMALLODDS, matchId);
	}

	public static String getBbMatchEuropeodds(String matchId) {
		return String.format(BB_MATCH_EUROPEODDS, matchId);
	}

	public static String getBbMatchAsiaOdds(String matchId) {
		return String.format(BB_MATCH_ASIAODDS, matchId);
	}

	public static String getBbMatchBigOrSmallOdds(String matchId) {
		return String.format(BB_MATCH_BIGORSMALLODDS, matchId);
	}

	public final static String LOTTERY_RECOMMEND_TIMELINE = "id:%s:LOTTERY_RECOMMEND_TIMELINE";

	public final static String LOTTERY_REAL_FOLLOW_TIMELINE = "id:%s:LOTTERY_REAL_FOLLOW_TIMELINE";
	private static final String TOP5_RECOMMEND_LIST = "id:Top5Recommend:%s";

	private static final String ALIPAY_UID = "alipay_uid:%s";
	private static final String HOT_AND_RECOMMEND_MATCH_OBJ = "id:HotAndRecommendMatch:%s";
	// 足球球队联赛排名分析统计情况信息
	/** 总排名 */
	private static final String FB_TEAM_LEAGUE_SCORE_RANK_ANALYSE_TOTAL = "leagueId:%s:seasonName:%s:teamId:%s:FBTOTAL";
	/** 主场 */
	private static final String FB_TEAM_LEAGUE_SCORE_RANK_ANALYSE_ZC = "leagueId:%s:seasonName:%s:teamId:%s:FBZC";
	/** 客场 */
	private static final String FB_TEAM_LEAGUE_SCORE_RANK_ANALYSE_KC = "leagueId:%s:seasonName:%s:teamId:%s:FBKC";
	/** 近6场 */
	private static final String FB_TEAM_LEAGUE_SCORE_RANK_ANALYSE_LATEST_6 = "leagueId:%s:seasonName:%s:teamId:%s:FBLATEST_6";

	// 足球球队联赛半场排名分析统计情况信息
	/** 总排名 */
	private static final String FB_TEAM_LEAGUE_HALF_SCORE_RANK_ANALYSE_TOTAL = "leagueId:%s:seasonName:%s:teamId:%s:FB_HALF_TOTAL";
	/** 主场 */
	private static final String FB_TEAM_LEAGUE_HALF_SCORE_RANK_ANALYSE_ZC = "leagueId:%s:seasonName:%s:teamId:%s:FB_HALF_ZC";
	/** 客场 */
	private static final String FB_TEAM_LEAGUE_HALF_SCORE_RANK_ANALYSE_KC = "leagueId:%s:seasonName:%s:teamId:%s:FB_HALF_KC";
	/** 近6场 */
	private static final String FB_TEAM_LEAGUE_HALF_SCORE_RANK_ANALYSE_LATEST_6 = "leagueId:%s:seasonName:%s:teamId:%s:FB_HALF_LATEST_6";

	// 篮球球队联赛排名分析统计情况信息
	/** 总排名 */
	private static final String BB_TEAM_LEAGUE_SCORE_RANK_ANALYSE_TOTAL = "leagueId:%s:seasonName:%s:teamId:%s:BBTOTAL";
	/** 主场 */
	private static final String BB_TEAM_LEAGUE_SCORE_RANK_ANALYSE_ZC = "leagueId:%s:seasonName:%s:teamId:%s:BBZC";
	/** 客场 */
	private static final String BB_TEAM_LEAGUE_SCORE_RANK_ANALYSE_KC = "leagueId:%s:seasonName:%s:teamId:%s:BBKC";
	/** 近6场 */
	private static final String BB_TEAM_LEAGUE_SCORE_RANK_ANALYSE_LATEST_10 = "leagueId:%s:seasonName:%s:teamId:%s:BBLATEST_10";

	/**
	 * 构建阿里key
	 * 
	 * @param alipayUid
	 * @return
	 */
	public static String getAlipayUidKay(String alipayUid) {
		return String.format(ALIPAY_UID, alipayUid);
	}

	public static String getRedEnvalopeListKey(String userId,String envalopeId){
		return String.format(RED_ENVALOPE_LIST, userId,envalopeId);
	}
	
	public static String getUserGrabSetKey(String userId,String envalopeId){
		return String.format(GRAB_RED_ENVALOPE_USER_LIST, userId,envalopeId);
	}
	public static String getUserConsumedRedEnvalopeList(String userId,String envalopeId){
		return String.format(USER_CONSUMED_REDENVALOPE_LIST, userId,envalopeId);
	}
	/** 彩种的推荐方案时间线 */
	public static String getLotteryRecommendTimeline(String lotteryId) {
		return String.format(LOTTERY_RECOMMEND_TIMELINE, lotteryId);
	}

	/** 彩种的实单/跟单方案时间线 */
	public static String getLotteryRealFollowTimeline(String lotteryId) {
		return String.format(LOTTERY_REAL_FOLLOW_TIMELINE, lotteryId);
	}

	public static String getJCZQKey() {

		return JCZQ_NUM;
	}

	public static String getJCLQKey() {
		return JCLQ_NUM;
	}

	public static String getCTZQKey() {
		return CTZQ_NUM;
	}

	public static String getBJDCKey() {
		return BJDC_NUM;
	}

	public static String getSSQKey() {

		return SSQ_NUM;
	}

	public static String getWeiboUserStatisKey(String weiboUserId) {
		return String.format(WeiboUserStatis, weiboUserId);
	}

	public static String getWeiboUsers() {
		return WEIBO_USERS;
	}

	public static String getWeiboRecomendUser() {
		return WEIBO_RECOMEND_USER;
	}

	public static String getWeiboLotteryUser() {
		return WEIBO_LOTTERY_USER;
	}

	public static String getLotteryNewsTimelineKey(String lotteryType) {
		return String.format(NEWS_TIMELINE_LOTTERY, lotteryType);
	}

	public static String getTeamNewsTimelineKey(String teamId) {
		return String.format(NEWS_TIMELINE_TEAM, teamId);
	}

	/** 获取新闻内容key */
	public static String getNewsContentKey(String url) {
		// url转换成md5序列作为唯一id标识
		return String.format(NEWS_CONTENT, Text.MD5Encode(url));
	}

	/** 获取未发布的新闻key */
	public static String getUnpublishNewsKey(String newsType) {
		return String.format(UNPUBLISH_NEWS, newsType);
	}

	/** 获取已发布的新闻key */
	public static String getPublishedNewsKey(String newsType) {
		return String.format(PUBLISHED_NEWS, newsType);
	}

	/** 我的所有评论 */
	public static String myComments(String uid) {
		return String.format(MY_COMMENTS, uid);
	}

	public static String getGlobalNextWinningnewId() {
		return GLOBAL_NEXT_WINNINGNEW_ID;
	}

	public static String userWinning(String uid) {
		return String.format(UID_WINNINGS, uid);
	}

	public static String uidLotteryId(String userId) {
		return String.format(UID_LOTTERYID, userId);
	}

	public static String lotteryIdUid(String lotteryId) {
		return String.format(LOTTERYID_UID, lotteryId);
	}

	public static String userNewsKey(String userId) {
		return String.format(USER_NEWS, userId);
	}

	public final static String userMatchesKey(String userId) {
		return String.format(USER_MATCHES, userId);
	}

	public final static String userRecommendsKey(String userId) {
		return String.format(USER_RECOMMENDS, userId);
	}

	/**
	 * 用户推荐/实单timeline的key
	 * 
	 * @param userId
	 * @return
	 */
	public final static String userRecomRealsTimelineKey(String userId) {
		return String.format(USER_RECOM_REALS_TIMELINE, userId);
	}

	public final static String userSelfRecomRealsKey(String userId) {
		return String.format(USER_SELF_RECOM_REALS, userId);
	}

	// public final static String lotteryNewsKey(String lotteryId){
	// return String.format(LOTTERY_NEWS, lotteryId);
	// }
	//
	// public final static String lotteryMatchesKey(String lotteryId){
	// return String.format(LOTTERY_MATCHES, lotteryId);
	// }
	//
	// public final static String lotteryRecommendsKey(String lotteryId){
	// return String.format(LOTTERY_RECOMMENDS, lotteryId);
	// }

	public static String globalNextTopicIdKey() {
		return GLOBAL_NEXT_TOPIC_ID;
	}

	/** 今日话题列表 */
	public static String dailyTopicsKey() {
		return DAILY_TOPICS;
	}

	/** 话题key */
	public static String topicKey(String topicId) {
		return String.format(TOPIC, topicId);
	}

	/** 喜报KEY */
	public static String winKey(String winId) {
		return String.format(WINNINGNEW, winId);
	}

	/**
	 * pid:%d:comments
	 * 
	 * @param postId
	 *            微博id
	 */
	public static String commentsOfPostKey(String postId) {
		return String.format(COMMENTS_OF_POST, postId);
	}

	/**
	 * pid:%s:likes
	 * 
	 * @param postId
	 *            微博id
	 */
	public static String weiboLike(String postId) {
		return String.format(WEIBO_LIKE, postId);
	}

	/**
	 * global:nextCommentId
	 */
	public static String globalNextCommentIdKey() {
		return GLOBAL_NEXT_COMMENT_ID;
	}

	/**
	 * uid:%d:allComments
	 * 
	 * @param uid
	 */
	public static String allCommentsOfUidKey(String uid) {
		return String.format(ALL_COMMENTS_OF_UID, uid);
	}

	public static String commentKey(String commentId) {
		return String.format(COMMENT, commentId);
	}

	public static String getGlobalNextPostId() {
		return GLOBAL_NEXT_POST_ID;
	}

	public static String getGlobalNextUserId() {
		return GLOBAL_NEXT_USER_ID;
	}

	/**
	 * atMeKey 提到我的
	 * 
	 * @param uid
	 * @return
	 */
	public static String atMeKey(String uid) {
		return String.format(ATME, uid);
	}

	/**
	 * postKey 微博msg
	 * 
	 * @param postId
	 * @return
	 */
	public static String postKey(String postId) {
		return String.format(POST, postId);
	}

	/**
	 * 我发布的微博key
	 * 
	 * @param uid
	 * @return
	 */
	public static String myPostKey(String uid) {
		return String.format(MY_POSTS, uid);
	}

	/**
	 * 我所有的微博key
	 * 
	 * @param uid
	 * @return
	 */
	public static String userMainTimeline(String uid) {
		return String.format(USER_MAIN_TIMELINE, uid);
	}

	/**
	 * 用户讨论微博timeline key
	 * 
	 * @param uid
	 * @return
	 */
	public static String userDiscussTimeline(String uid) {
		return String.format(USER_DISCUSS_TIMELINE, uid);
	}

	/**
	 * 
	 */
	public static String timelineLastPos(String uid) {
		return String.format(USER_TIMELINE_LAST_POS, uid);
	}

	/**
	 * 构建新浪微博Uid，通过该key获取大V彩微博用户的id
	 * 
	 * @param sinaWeiboUid
	 * @return
	 */
	public static String getSinaWeiboUidKey(String sinaWeiboUid) {
		return String.format(SINA_WEIBO_UID, sinaWeiboUid);
	}

	/**
	 * 构建微信Uid，通过该key获取大V彩微博用户的id
	 * 
	 * @param weixinOpenUid
	 * @return
	 */
	public static String getWeixinUidKey(String weixinOpenUid) {
		return String.format(WEIXIN_OPEN_UID, weixinOpenUid);
	}

	/**
	 * 构建腾讯微博Uid，通过该key获取大V彩微博用户的id
	 * 
	 * @param sinaWeiboUid
	 * @return
	 */
	public static String getQQWeiboUidKey(String qqWeiboUid) {
		return String.format(QQ_WEIBO_UID, qqWeiboUid);
	}

	/**
	 * 构建 lt_user id key ，用于根据 lt_user id 获取 redis user id
	 * 
	 * @return
	 */
	public static String getLotteryUserIdKey(Long ltUserId) {
		return String.format(LOTTERY_USER_ID, ltUserId);
	}

	/**
	 * 构建用户key
	 * 
	 * @param uid
	 * @return
	 */
	public static String getUserKey(long uid) {
		return String.format(USER, "" + uid);
	}

	/**
	 * 构建昵称key ，用于根据昵称获取redis user id
	 * 
	 * @param nickname
	 * @return
	 */
	public static String getNicknameKey(String nickname) {
		return String.format(NICKNAME, nickname);
	}

	/**
	 * 构建“我的粉丝”集合的key
	 * 
	 * @param uid
	 * @return
	 */
	public static String getFollowerKey(Long uid) {
		return String.format(FOLLOWERS, uid);
	}

	/**
	 * 构建“我关注的人”集合的key
	 * 
	 * @param uid
	 * @return
	 */
	public static String getFollowingKey(Long uid) {
		return String.format(FOLLOWING, uid);
	}

	/**
	 * 私信的key,格式：privateMsg:privateMsgId
	 * 
	 * @param privateMsgId
	 * @return
	 */
	public static String privateMsg(long privateMsgId) {
		return String.format(PRIVATE_MSG, privateMsgId);
	}

	public static String privateSessionUserId(long uid) {
		return String.format(PRIVATE_SESSION_USERS, uid);
	}

	public static String privatePeerMsgs(long uid, long peerId) {
		return String.format(PRIVATE_PEER_MSGS, uid, peerId);
	}

	public static String privateMsgReceived(long uid) {
		return String.format(PRIVATE_MSG_RECEIVED, uid);
	}

	public static String privateMsgPosted(long uid) {
		return String.format(PRIVATE_MSG_POSTED, uid);
	}

	public static String favorites(long uid) {
		return String.format(FAVOURITES, uid);
	}

	/**
	 * 用户的直接回复列表key
	 */
	public static String directRepliesKey(String uid) {
		return String.format(DIRECT_REPLIES, uid);
	}

	/**
	 * 我关注的用户给我的评论列表key
	 */
	public static String followingCommentsKey(String uid) {
		return String.format(FOLLOWING_COMMENTS, uid);
	}

	/**
	 * 用户采纳的微博集合
	 */
	public static String praise(Long uid) {
		return String.format(POST_PRAISE, uid);
	}

	/**
	 * 用户彩种设置的key
	 * 
	 * @param uid
	 * @return
	 */
	public static String userLotterySetKey(String uid) {
		return String.format(USER_LOTTERY_SETTINGS, uid);
	}

	public static String userBetRecord(String uid, String lotteryId) {
		return String.format(USER_BET_RECORD, uid, lotteryId);
	}

	/**组合方案缓存的ID
	 * 格式：schemeId:displayMode
	 * @param betRecordId 投注记录ID
	 * **/
	public static String schemeIdCacheValue(long schemeId, int displayMode, long betRecordId) {
		return String.format(SCHEME_ID_CACHE, schemeId, displayMode, betRecordId);
	}

	/**
	 * 拆分方案缓存的ID 格式：schemeId:displayMode
	 * **/
	public static String[] splitSchemeIDCacheValue(String value) {
		return value.split(BetRecordConstant.SCHEME_CUT);
	}

	/**
	 * 用模板生成id对应的key。
	 * 
	 * @param idKeyTemplate
	 *            模板必须只有一个 "%s" 占位符。
	 * @param id
	 *            对象id
	 * @return key
	 */
	public static String idKey(String idKeyTemplate, String id) {
		return String.format(idKeyTemplate, id);
	}

	/**
	 * 彩票用户帖子id的key
	 * 
	 * @param ltUserId
	 * @return
	 */
	public static String getBbsImportThreadIdKey(Long ltUserId) {
		return String.format(BBS_IMPORT_THREAD_ID, ltUserId);
	}

	public static String praiseCommentUserKey(String commentId) {
		return String.format(PRAISE_COMMENT_USERS, commentId);
	}

	/**
	 * 足球联赛球队主场积分统计情况分析key
	 * 
	 * @param leagueId
	 * @param teamId
	 * @return
	 */
	public static String getFbLeagueTeamScoreRankAnalyseKeyZC(String leagueId,
			String seasonName, String teamId) {
		return String.format(FB_TEAM_LEAGUE_SCORE_RANK_ANALYSE_ZC, leagueId,
				seasonName, teamId.equals("*")?"*":teamId);
	}

	/**
	 * 足球联赛球队客场积分统计情况分析key
	 * 
	 * @param leagueId
	 * @param teamId
	 * @return
	 */
	public static String getFbLeagueTeamScoreRankAnalyseKeyKC(String leagueId,
			String seasonName, String teamId) {
		return String.format(FB_TEAM_LEAGUE_SCORE_RANK_ANALYSE_KC, leagueId,
				seasonName, teamId);
	}

	/**
	 * 足球联赛球队近6场积分统计情况分析key
	 * 
	 * @param leagueId
	 * @param teamId
	 * @return
	 */
	public static String getFbLeagueTeamScoreRankAnalyseKeyLatest_6(
			String leagueId, String seasonName, String teamId) {
		return String.format(FB_TEAM_LEAGUE_SCORE_RANK_ANALYSE_LATEST_6,
				leagueId, seasonName, teamId);
	}

	/**
	 * 足球联赛球队总积分统计情况分析key
	 * 
	 * @param leagueId
	 * @param seasonName
	 * @param teamId
	 * @return
	 */
	public static String getFbLeagueTeamScoreRankAnalyseKeyTotal(
			String leagueId, String seasonName, String teamId) {
		return String.format(FB_TEAM_LEAGUE_SCORE_RANK_ANALYSE_TOTAL, leagueId,
				seasonName, teamId);
	}
	/**
	 * 足球联赛球队半场主场积分统计情况分析key
	 * 
	 * @param leagueId
	 * @param teamId
	 * @return
	 */
	public static String getFbLeagueTeamHalfScoreRankAnalyseKeyZC(
			String leagueId, String seasonName, String teamId) {
		return String.format(FB_TEAM_LEAGUE_HALF_SCORE_RANK_ANALYSE_ZC,
				leagueId, seasonName, teamId);
	}

	/**
	 * 足球联赛球队半场客场积分统计情况分析key
	 * 
	 * @param leagueId
	 * @param teamId
	 * @return
	 */
	public static String getFbLeagueTeamHalfScoreRankAnalyseKeyKC(
			String leagueId, String seasonName, String teamId) {
		return String.format(FB_TEAM_LEAGUE_HALF_SCORE_RANK_ANALYSE_KC,
				leagueId, seasonName, teamId);
	}

	/**
	 * 足球联赛球队半场近6场积分统计情况分析key
	 * 
	 * @param leagueId
	 * @param teamId
	 * @return
	 */
	public static String getFbLeagueTeamHalfScoreRankAnalyseKeyLatest_6(
			String leagueId, String seasonName, String teamId) {
		return String.format(FB_TEAM_LEAGUE_HALF_SCORE_RANK_ANALYSE_LATEST_6,
				leagueId, seasonName, teamId);
	}

	/**
	 * 足球联赛球队半场总积分统计情况分析key
	 * 
	 * @param leagueId
	 * @param teamId
	 * @return
	 */
	public static String getFbLeagueTeamHalfScoreRankAnalyseKeyTotal(
			String leagueId, String seasonName, String teamId) {
		return String.format(FB_TEAM_LEAGUE_HALF_SCORE_RANK_ANALYSE_TOTAL,
				leagueId, seasonName, teamId);
	}

	/**
	 * 篮球联赛球队主场积分统计情况分析key
	 * 
	 * @param leagueId
	 * @param teamId
	 * @return
	 */
	public static String getBbLeagueTeamScoreRankAnalyseKeyZC(String leagueId,
			String seasonName, String teamId) {
		return String.format(BB_TEAM_LEAGUE_SCORE_RANK_ANALYSE_ZC, leagueId,
				seasonName, teamId);
	}

	/**
	 * 篮球联赛球队客场积分统计情况分析key
	 * 
	 * @param leagueId
	 * @param teamId
	 * @return
	 */
	public static String getBbLeagueTeamScoreRankAnalyseKeyKC(String leagueId,
			String seasonName, String teamId) {
		return String.format(BB_TEAM_LEAGUE_SCORE_RANK_ANALYSE_KC, leagueId,
				seasonName, teamId);
	}

	/**
	 * 篮球联赛球队近10场积分统计情况分析key
	 * 
	 * @param leagueId
	 * @param teamId
	 * @return
	 */
	public static String getBbLeagueTeamScoreRankAnalyseKeyLatest_10(
			String leagueId, String seasonName, String teamId) {
		return String.format(BB_TEAM_LEAGUE_SCORE_RANK_ANALYSE_LATEST_10,
				leagueId, seasonName, teamId);
	}

	/**
	 * 篮球联赛球队总积分统计情况分析key
	 * 
	 * @param leagueId
	 * @param teamId
	 * @return
	 */
	public static String getBbLeagueTeamScoreRankAnalyseKeyTotal(
			String leagueId, String seasonName, String teamId) {
		return String.format(BB_TEAM_LEAGUE_SCORE_RANK_ANALYSE_TOTAL, leagueId,
				seasonName, teamId);
	}

	/**
	 * 热门讨论的KEY
	 * 
	 * @param recentDateType
	 * @return
	 */
	public static String discussKey(RecentDateType recentDateType) {
		return String.format(DISCUSS, recentDateType.name());
	}

	/**
	 * 每场比赛全部内容时间线的key
	 * 
	 * @param matchIdStr
	 * @return
	 */
	public static String matchAllContentKey(String matchIdStr) {
		return String.format(MATCH_ALL_CONTENT, matchIdStr);
	}

	/**
	 * 每场比赛赛事数据的key
	 * 
	 * @param matchIdStr
	 * @return
	 */
	public static String matchDataKey(String matchIdStr) {
		return String.format(MATCH_ID, matchIdStr);
	}

	/**
	 * 每场实单比赛赛事数据的key
	 * 
	 * @param matchIdStr
	 * @return
	 */
	public static String matchRealDataKey(String matchIdStr) {
		return String.format(MATCH_REAL_ID, matchIdStr);
	}

	/**
	 * 赛事讨论时间线的key
	 * 
	 * @param matchIdStr
	 * @return
	 */
	public static String matchDiscussKey(String matchIdStr) {
		return String.format(MATCH_DISCUSS, matchIdStr);
	}

	/**
	 * 新增粉丝的key
	 * 
	 * @param uid
	 *            被关注者id
	 * @return
	 */
	public static String haveNewFollowers(Long uid) {
		return String.format(FOLLOWERS_UNREAD, uid);
	}

	/**
	 * 未读“提到我的”
	 * 
	 * @param uid
	 * @return
	 */
	public static String haveUnreadMentions(Long uid) {
		return String.format(MENTIONS_UNREAD, uid);
	}

	/**
	 * 未读“评论我的”
	 * 
	 * @param uid
	 * @return
	 */
	public static String haveUnreadComments(Long uid) {
		return String.format(COMMENTS_UNREAD, uid);
	}

	/**
	 * 未读“私信我的”
	 * 
	 * @param uid
	 * @return
	 */
	public static String haveUnreadPrivateMsgs(Long uid) {
		return String.format(PRIVATEMSGS_UNREAD, uid);
	}

	/**
	 * 对于球队的赞
	 * 
	 * @param qtMatchId
	 * @param teamId
	 * @param lotteryId
	 * @return
	 */
	public static String teamPraise(long qtMatchId, long teamId,
			String lotteryId) {
		return String.format(MATCH_TEAM_PRAISE, qtMatchId, teamId, lotteryId);
	}

	public static String matchShowRecommendUsers(long qtMatchId,
			String lotteryId) {
		return String.format(MATCH_SHOW_RECOMMEND_USERS, qtMatchId, lotteryId);
	}

	public static String matchShowScheme(long qtMatchId, String lotteryId) {
		return String.format(MATCH_SHOW_SCHEME, qtMatchId, lotteryId);
	}

	public static String matchRecommend(long qtMatchId, String lotteryId) {
		return String.format(MATCH_RECOMMEND, qtMatchId, lotteryId);
	}

	/**
	 * 和彩种有关的推荐微博时间线的key
	 * @param lotteryId 彩种ID
	 * @return
	 */
	public static String matchRecommendLottery(String lotteryId) {
		return String.format(MATCH_RECOMMEND_LOTTERY, lotteryId);
	}

	/**
	 * 晒单方案到微博索引的key
	 * @param schemeId
	 * @return
	 */
	public static String showSchemeToWeibo(String schemeId) {
		return String.format(SHOW_SCHEME_TO_WEIBO, schemeId);
	}
	
	/**
	 * 合买方案到微博索引的Key
	 * @param betRecordId	投注记录ID
	 * @return
	 */
	public static String groupBuySchemeToWeibo(String betRecordId) {
		return String.format(GROUPBUY_SCHEME_TO_WEIBO, betRecordId);
	}
	
	/**
	 * 推荐方案到微博索引的key
	 * @param schemeId
	 * @return
	 */
	public static String recommendSchemeToWeibo(String schemeId) {
		return String.format(RECOMMEND_SCHEME_TO_WEIBO, schemeId);
	}
	
	/**微博包含标签的KEY*/
	public static String WeiboTags(long postId) {
		return String.format(WEIBO_CONTAINS_TAG, postId);
	}
	/**微博标签*/
	public static String tag(String tagId) {
		return String.format(TAG, tagId);
	}
	/**标签名称的索引key*/
	public static String tagNameKey(String tagName) {
		return String.format(TAG_NAME, tagName);
	}
	
	/**实单方案缓存的key*/
	public static String realSchemeCacheKey(long schemeId, int schemeType) {
		return String.format(REAL_SCHEME_CACHE_KEY, schemeId+"", schemeType+"");
	}
	/**推荐方案缓存的key*/
	public static String recomSchemeCacheKey(long schemeId) {
		return String.format(RECOM_SCHEME_CACHE_KEY, schemeId+"");
	}
	public static String getQQConnectUidKey(String qqConnectUid){
		return String.format(QQ_CONNECT_UID, qqConnectUid);
	}
	/**赛事推荐的key*/
	public static String homeMatchRecommend(String matchRecommendId) {
		return String.format(HOME_MATCH_RECOMMEND, matchRecommendId);
	}
	/**彩种赛事推荐列表*/
	public static String lotteryMatchRecommendList(String lotteryId) {
		return String.format(HOME_MATCH_RECOMMEND_LIST, lotteryId);
	}
	public static String top5RecommendListKey(String typeAndDimension) {
		return String.format(TOP5_RECOMMEND_LIST, typeAndDimension);
	}
	public static String top5RecommendObjectKey(Top5Recommend top5Recommend) {
		
		if(null!=top5Recommend){
			String typeAndDimensionAndSequenceNumber=top5Recommend.getTopType()+":"+top5Recommend.getDimension()+":"+top5Recommend.getId();
			return String.format(TOP5_RECOMMEND_LIST, typeAndDimensionAndSequenceNumber);
		}
		return null;
		
	}
	@SuppressWarnings("rawtypes")
	public static String pushMessageKey(String pushMessageId,Class className) {
		String simpleName = className.getSimpleName();
		return String.format(PUSH_MESSAGE, simpleName,pushMessageId);
	}

	public static String hotAndRecommendMatchKey(
			HotAndRecommendMatch hotAndRecommendMatch) {
		long id = hotAndRecommendMatch.getId();
		return String.format(HOT_AND_RECOMMEND_MATCH_OBJ,id);
	}
}
