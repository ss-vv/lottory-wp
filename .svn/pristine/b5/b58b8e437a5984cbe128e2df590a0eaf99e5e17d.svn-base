package com.unison.lottery.weibo.msg.service;

import java.util.List;

import com.unison.lottery.newsextract.lang.NewsType;
import com.unison.lottery.weibo.data.PageRequest;
import com.unison.lottery.weibo.data.PageResult;
import com.unison.lottery.weibo.data.RealFollowerResult;
import com.unison.lottery.weibo.data.WeiboMsg;
import com.unison.lottery.weibo.data.WeiboMsgVO;
import com.unison.lottery.weibo.data.WeiboUser;

/**
 * 微博消息服务
 * @author Wang Lei
 *
 */
public interface MessageService {
	
	/**
	 * 发布微博
	 * @param weiboMsg
	 * @return 
	 */
	public WeiboMsgVO publish(WeiboMsg weiboMsg);
	
	/**
	 * 区别于相同参数的publish方法在于：微博发送完之后不去加载微博关联的方案信息。
	 * @param weiboMsg
	 * @return
	 */
	public WeiboMsgVO publishWithoutScheme(WeiboMsg weiboMsg);
	
	/**
	 * 发布彩种新闻微博
	 * @param weiboMsg
	 * @param newsType
	 * @return 
	 */
	public WeiboMsgVO publishAsLotteryNews(WeiboMsg weiboMsg,NewsType newsType);
	
	/**
	 * 发布新微博，不支持转发。
	 * @param authorId 作者id
	 * @param title 标题
	 * @param content 微博内容
	 * @return 新生成的微博对象，id已赋值。
	 */
	public WeiboMsgVO publish(long authorId, String title, String content);
	
	/**
	 * 分页查询全部微博
	 * @param uid
	 * @param pageRequest
	 * @return
	 */
	public PageResult<WeiboMsgVO> findAllPost(long uid, PageRequest pageRequest);
	
	/**
	 * 分页查询我发的所有微博
	 * @param uid
	 * @param pageResult
	 */
	public PageResult<WeiboMsgVO> findMyPost(long uid, PageRequest pageRequest);
	
	/**
	 * 转发微博
	 * @param msgId
	 */
	public WeiboMsgVO forward(WeiboMsg weiboMsg);
	
	/**
	 * 删除微博消息
	 * @param userId
	 * @param messageId
	 * @return 
	 */
	public Long delete(long userId, long messageId);
	
	/**
	 * 增加某微博的评论数。+1
	 * @param postId 微博id
	 * @return 
	 */
	public long increaseCommentCount(long postId);

	/**
	 * 减少微博的评论数 -1
	 * @param postId
	 * @return
	 */
	public long decreaseCommentCount(long postId);

	/**
	 * 增加微博的转发次数 +1
	 * @param postId
	 * @return
	 */
	public long increaseForwardCount(long postId);
	
	/**
	 * 微博收藏数加1
	 * @param postId
	 * @param value
	 * @return
	 */
	public long increaseFavoriateCount(long postId);
	
	/**
	 * 取消收藏，收藏数-1
	 * @param postId
	 * @param value
	 * @return
	 */
	public long increaseFavoriateCountDown(long postId);

	/**
	 * 增加微博分享数 +1
	 * @param postId
	 * @return
	 */
	public long increaseShareCount(long postId);

	/**
	 * 查询微博源数据
	 * @param postId
	 * @return
	 */
	public WeiboMsg getWeiboById(long postId);
	
	/**
	 * 查询用来显示的微博
	 * @param postId
	 * @return
	 */
	public WeiboMsgVO getWeiboVoById(long postId);

	public PageResult<WeiboMsgVO> listPost(Long[] postIds, PageRequest pageRequest);

	/**
	 * 修改（长）微博
	 * @param userId
	 * @param weiboMsg
	 * @return
	 */
	public String editLong(long userId, WeiboMsg weiboMsg);
	
	/**
	 * 计算用户微博数量
	 * @param weiboUserId
	 * @return
	 */
	public Long weiboCount(String weiboUserId);

	/**
	 * 使用value值获取key为myallpost的微博的分数
	 * @param value
	 * @return
	 */
	public String getMyAllPostScoreByValue(long postId, String value);
	
	/**
	 * 使用权重查询新微博数量
	 * @param timeLine
	 * @param timeLineOffset
	 * @return
	 */
	public long getNewPostCount(long uid, String score, int timeLineOffset);

	public PageResult<WeiboMsgVO> findAllPost(long uid, String score);

	public PageResult<WeiboMsgVO> listPost(String[] postIds, PageRequest pageRequest);

	/**
	 * 采纳微博
	 * @param uid
	 * @param pid
	 * @return
	 */
	public boolean like(long uid, long pid);

	/**
	 * 取消采纳微博
	 * @param uid
	 * @param pid
	 * @return
	 */
	public boolean unLike(long uid, long pid);
	
	/**
	 * 获取微博赞用户集合
	 * @param pid
	 * @return
	 */
	public List<WeiboUser> findLikeWeiboUser(String pid);

	public PageResult<WeiboMsgVO> checkFavoriateAndLike(long uid, PageResult<WeiboMsgVO> result);

	public PageResult<WeiboMsgVO> checkLikeSetFavoriate(long uid,
			PageResult<WeiboMsgVO> result);

	/**
	 * 我的新闻
	 * @param uid
	 * @param pageRequest
	 * @return
	 */
	public PageResult<WeiboMsgVO> findNews(long uid, PageRequest pageRequest);

	/**
	 * 我的赛事
	 * @param uid
	 * @param pageRequest
	 * @return
	 */
	public PageResult<WeiboMsgVO> findMatches(long uid, PageRequest pageRequest);

	/**
	 * 我的推荐
	 * @param uid
	 * @param pageRequest
	 * @return
	 */
	public PageResult<WeiboMsgVO> findRecommends(long uid, PageRequest pageRequest);

	public PageResult<WeiboMsgVO> findNews(long uid, String score);

	public PageResult<WeiboMsgVO> findMatches(long uid, String score);

	public PageResult<WeiboMsgVO> findRecommends(long uid, String score);

	/**
	 * 查询用户的推荐列表 
	 * @param uid
	 * @param pageRequest
	 * @return
	 */
	public PageResult<WeiboMsgVO> findAllRecommendsPost(long uid,
			PageRequest pageRequest);
	
	/**
	 * 按彩种类别查询推荐微博
	 * @param lotteryId
	 * @param pageRequest
	 * @return
	 */
	public PageResult<WeiboMsgVO> findRecommendListByLotteryCategory(
			String lotteryId, PageRequest pageRequest);

	/**
	 * 查询用户的赛事列表 
	 * @param uid
	 * @param pageRequest
	 * @return
	 */
	public PageResult<WeiboMsgVO> findAllMatchsPost(long uid, PageRequest pageRequest);

	/**
	 * 查询用户的新闻列表 
	 * @param uid
	 * @param pageRequest
	 * @return
	 */
	public PageResult<WeiboMsgVO> findAllNewsPost(long uid, PageRequest pageRequest);

	long getNewNewsPostCount(long uid, String score, int timeLineOffset);

	long getNewMatchPostCount(long uid, String score, int timeLineOffset);

	long getNewRecPostCount(long uid, String score, int timeLineOffset);
	
	/**
	 * 解析用户发布的微博
	 * <p>0.赛事全部内容’时间线</p>
	 * <p>1.加入到每场比赛的赛事时间线</p>
	 * <p>2.如果是实单加入到总的实单时间线</p>
	 * <p>3.加入到个人“推荐/实单时间线”</p>
	 * <p>4.解析赛事串，加入到指定彩种推荐时间线</p>
	 * @param weiboMsg
	 * @return
	 */
	void addToMatchTimeline(WeiboMsg weiboMsg);
	
	/**
	 * 不依赖$$赛事串来判断将微博加入赛事时间线，通过赛事ID来转换
	 * @param weiboMsg
	 * @param lotteryId
	 * @param matchIdList
	 */
	void addToMatchTimeline(WeiboMsg weiboMsg, String lotteryId, List<String> matchIdList);
	
	/**
	 * 查找相关赛事的所有微博
	 * @param score
	 * @return
	 */
	public PageResult<WeiboMsgVO> findAllMatchPost(long uid, String matchId, String score);
	public PageResult<WeiboMsgVO> findAllRealMatchPost(long uid, String matchId, String score);
	
	/**
	 * 获取赛事相关的新的微博数量
	 * @param matchId
	 * @param score
	 * @param timeLineOffset
	 * @return
	 */
	public long getNewMatchPostCount(String matchId, long id ,String score, int timeLineOffset);
	public long getNewRealMatchPostCount(String matchId, long id ,String score, int timeLineOffset);
	
	/**
	 * 分页查找相关赛事的所有微博
	 * @param uid
	 * @param pageRequest
	 * @return
	 */
	public PageResult<WeiboMsgVO> findAllMatchPost(long uid, String matchId, PageRequest pageRequest);
	public PageResult<WeiboMsgVO> findAllRealMatchPost(long uid, String matchId, PageRequest pageRequest);

	void postToAllposts(String[] users, double score, long postid);
	void postNewsToAllposts(String[] users, double score, long postid);
	
	/**
	 * 将“推荐/实单”微博推送到粉丝的“推荐/实单”时间线上.
	 * @param followerList
	 * @param score
	 * @param postid
	 */
	void postRecomRealToFollowers(List<Long> followerList, double score, long postid);
	
	/**
	 * 计算新的赛事新闻微博数量
	 * @param matchId
	 * @param timeLine
	 * @param timeLineOffset
	 * @return
	 */
	public long getNewMatchNewsPostCount(String matchId, String timeLine,
			int timeLineOffset);
	/**
	 * 查询比赛新闻
	 * @param matchId
	 * @param pageRequest
	 * @return
	 */
	public PageResult<WeiboMsgVO> findAllMatchNewsPost(long uid , String matchId,
			PageRequest pageRequest);
	
	/**
	 * 新的微博消息
	 * @param uid
	 * @param timeline
	 * @return
	 */
	public PageResult<WeiboMsgVO> findNewPost(long uid, long timeline);
	/**
	 * 新的推荐消息
	 * @param uid
	 * @param timeline
	 * @return
	 */
	public PageResult<WeiboMsgVO> findNewRecommends(long uid, long timeline);
	/**
	 * 新的赛事消息
	 * @param uid
	 * @param timeline
	 * @return
	 */
	public PageResult<WeiboMsgVO> findNewMatchs(long uid, long timeline);
	/**
	 * 新的新闻消息
	 * @param uid
	 * @param timeline
	 * @return
	 */
	public PageResult<WeiboMsgVO> findNewNews(long uid, long timeline);
	/**
	 * 新的赛事相关新闻消息
	 * @param uid
	 * @param matchId
	 * @param timeline
	 * @return
	 */
	public PageResult<WeiboMsgVO> findNewMatchsNews(long uid,String matchId, long timeline);
	
	/**
	 * 加入晒单方案到微博的时间线索引
	 * @param schemeId	晒单方案ID
	 * @param postId	微博ID
	 * @return
	 */
	void addShowSchemeToWeiboTimeline(long schemeId, long postId);
	
	/**
	 * 加入合买方案到微博的时间线索引
	 * @param schemeId	投注记录ID
	 * @param postId	微博ID
	 * @return
	 */
	void addGroupBuySchemeToWeiboTimeline(long betRecordId, long postId);
	
	/**
	 * 加入推荐方案到微博的时间线索引
	 * @param schemeId	晒单方案ID
	 * @param postId	微博ID
	 * @return
	 */
	void addRecommendSchemeToWeiboTimeline(long schemeId, long postId);
	
	/**
	 * 根据晒单方案ID获取微博
	 * @param schemeId
	 * @return
	 */
	long getWeiboIdByShowSchemeId(long schemeId);
	
	long getWeiboIdByGroupBuyRecordId(long betRecordId);
	
	void addWeiboTag(WeiboMsg weiboMsg);

	public long getNewDiscussPostCount(long userLaicaiWeiboId,
			String timeLine, int timeLineOffset);

	public PageResult<WeiboMsgVO> findAllDiscussPost(long userLaicaiWeiboId,
			PageRequest pageRequest);

	public PageResult<WeiboMsgVO> findNewDiscuss(long userLaicaiWeiboId,
			long parseLong);

	public void deleteWeiboFromTimeline(String[] users, long postid,String content);

	public PageResult<WeiboMsgVO> findAllMatchDiscussPost(
			long userLaicaiWeiboId, String matchId, PageRequest pageRequest);

	public long getNewRealMatchPostTimer(String matchId,
			String timeLine, int timeLineOffset);

	public long getNewMatchDiscussTimer(String matchId, String timeLine,
			int timeLineOffset);

	public PageResult<WeiboMsgVO> findMatchDiscussPost(long userLaicaiWeiboId,
			String matchId, PageRequest pageRequest);

	public PageResult<WeiboMsgVO> findNewWeiboByKey(long uid, String matchId,
			long timeline, String key);
	
	public RealFollowerResult findRealFollower(long schemeId);
	
	RealFollowerResult groupBuyUserRecord(long schemeId);
	
	public PageResult<WeiboMsgVO> addMyFollowingData(long uid,
			PageResult<WeiboMsgVO> result);

	public void loadWeiboSchemeInfo(WeiboMsgVO wb);

	public void loadSourceWeiboSchemeInfo(WeiboMsgVO wbVO);
	
	public WeiboMsgVO addedInfo(WeiboMsgVO weiboMsgs);
	
	public PageResult<WeiboMsgVO> findRealFollowListByLotteryCategory(
			String lotteryId, PageRequest pageRequest);
	
    /**
     * 获取晒单方案对应的微博地址
     * @param schemeId
     * @return
     */
	String showSchemeWithWeiboAddress(Long schemeId);
	
	PageResult<WeiboMsgVO> list(String key, PageRequest pageRequest);
}
