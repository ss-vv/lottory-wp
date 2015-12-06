package com.unison.lottery.weibo.common.nosql;

import java.util.List;
import java.util.Map;

import com.unison.lottery.weibo.common.redis.RedisException;
import com.unison.lottery.weibo.data.RecentDateType;
import com.unison.lottery.weibo.data.WeiboMsg;
import com.unison.lottery.weibo.data.WeiboMsgVO;

/**
 * 微博消息dao
 * @author Wang Lei
 *
 */
public interface MessageDao extends BaseDao<WeiboMsg>{
	
	/**
	 * 发布新微博
	 * @return postId
	 */
	public long post(WeiboMsg weiboMsg);
	
	/**
	 * 获取微博
	 * @param id
	 */
	public WeiboMsg get(String id);
	
	/**
	 * 修改(长)微博内容
	 * @param key
	 * @param title
	 * @param content
	 * @return
	 */
	public String edit(String key, String title, String content);
	
	/**
	 * 获取微博list
	 * @param start
	 * @param count
	 * @return
	 * @throws RedisException
	 */
	public List<WeiboMsgVO> list(String[] keys);
	
	/**
	 * 删除微博
	 * @param id
	 * @return 
	 * @throws RedisException
	 */
	public Long delete(String id);
	
	/**
	 * 删除自己微博列表中的微博
	 * @param id
	 * @return
	 */
	Long deleteMyPosts(String id, String postId);
	
	/**
	 * 删除所有好友中推送过的微博
	 * @param ids
	 * @param postId
	 * @return
	 */
	public Long deleteAllposts(String[] ids, String postId);
	
	public Long deleteAllposts(List<Long> ids, String postId);
	
	/**
	 * 从热门推荐中移除微博
	 * @param postId
	 * @return
	 */
	Long deleteFromHotRecommend(String postId);
	
	/**
	 * 从热门讨论中移除微博
	 * @param recentDateType 日期类型
	 * @param postId
	 * @return
	 */
	Long deleteFromHotDiscuss(RecentDateType recentDateType, String postId);
	
	/**
	 * 从用户推荐实单时间线中移除微博
	 * @param postId
	 * @param userId 微博用户ID
	 * @param recentDateType
	 * @return
	 */
	Long deleteFromUserRecomReal(String postId, String userId);
	
	/**
	 * 从每场比赛的推荐/实单时间线移除微博
	 * @param matchId
	 * @param postId
	 * @return
	 */
	Long deleteFromMatchReal(String matchId, String postId);
	
	/**
	 * 从用户的所有彩种的比赛时间线移除微博
	 * @param userId
	 * @param postId
	 * @return
	 */
	Long deleteFromUserMatch(String userId, String postId);
	
	/**
	 * 从每场比赛全部内容时间线和每场比赛的数据时间线移除微博
	 * @param matchId
	 * @param postId
	 * @return
	 */
	Long deleteFromMatch(String matchId, String postId);
	
	/**
	 * 从粉丝的“个人所有彩种赛事时间线”中移除微博
	 * @param userId
	 * @param postId
	 * @return
	 */
	Long deleteFromFollowerMatch(String userId, String postId);
	
	/**
	 * 添加到我的全部微博队列
	 * @param uId
	 * @param score
	 * @param postId
	 * @return 
	 */
	public long postToMyPosts(final String uId, Double score, final String postId);
	
	/**
	 * 添加到我的所有微博队列
	 * @param uids
	 * @param score
	 * @param postId
	 * @return 
	 */
	public long postToAllposts(final String[] uids , final Double score, final String postId);
	
	/**
	 * 将微博添加到关注我的人的讨论时间线
	 * @param uids
	 * @param score
	 * @param postId
	 * @return 
	 */
	public long postToDisscussTimeline(final String[] uids , final Double score, final String postId);
	
	/**
	 * 修改微博的评论数
	 * @param postId
	 * @param value 1 or -1
	 * @return
	 */
	public long increaseCommentCount(String postId, long value);
	
	/**
	 * 增加微博的转发数
	 * @param postId
	 * @return
	 */
	public long increaseForwardCount(String postId);

	/**
	 * 修改微博收藏数
	 * @param postId
	 * @param value
	 * @return
	 */
	public long increaseFavoriateCount(String postId, long value);

	/**
	 * 修改微博赞数
	 * @param postId
	 * @param value
	 * @return
	 */
	public long increaseLikeCount(String postId, long value);
	
	/**
	 * 将userKey的个人所有微博加入到mykey的所有微博
	 * @param mykey
	 * @param userKey
	 * @param useruid
	 * @return 
	 */
	public long addMyAllPostFromUser(String mykey, String userKey, String useruid);
	
	/**
	 * 删除我的所有微博中某个用户的微博
	 * @param mykey
	 * @param userKey
	 * @param useruid
	 * @return
	 */
	public long delMyallPostFromUser(String mykey, String userKey, String useruid);
	
	/**
	 * 将userKey的个人'推荐/实单'微博加入到mykey的'推荐/实单'的timeline
	 * @param mykey
	 * @param userKey
	 * @param useruid
	 * @return 
	 */
	public long addRecomRealsFromUser(String mykey, String userKey);
	
	/**
	 * 删除mykey'推荐/实单'中的userKey的微博
	 * @param mykey
	 * @param userKey
	 * @param useruid
	 * @return 
	 */
	public long delRecomRealsFromUser(String mykey, String userKey);
	
	/**
	 * 增加微博分享数
	 * @param postId
	 * @return
	 */
	public long increaseShareCount(String postId);

	WeiboMsgVO getVO(String id);
	
	public Long getNewPostCountByScore(final String key, final Double score, double max);
	
	/**
	 * 将微博加入到全局时间线上
	 */
	long addToGlobalTimeline(String postId, double score);
	
	/**
	 * 发送新闻到所有关注者
	 * @param users
	 * @param score
	 * @param string
	 * @return
	 */
	long postNewsToAllposts(String[] uids, Double score, String postId);
	
	long postRecomRealToFollowers(List<Long> followerList, Double score, String postId);
	
	/**
	 * 赛事新闻微博
	 * @param matchId
	 * @param newScore
	 * @param max
	 * @return
	 */
	public Long getNewMatchNewsPostCountByScore(String matchId,double newScore, double max);
	
	/**
	 * 保存晒单方案到微博的索引
	 * @param schemeId
	 * @param score
	 * @return
	 */
	public void addShowSchemeToWeiboTimeline(String schemeId, String postId);
	
	/**
	 * 保存合买方案到微博的索引
	 * @param betRecordId	投注记录ID
	 * @param postId	微博ID
	 * @return
	 */
	public void addGroupBuySchemeToWeiboTimeline(String betRecordId, String postId);
	
	/**
	 * 保存推荐方案到微博的索引
	 * @param schemeId
	 * @param score
	 * @return
	 */
	public void addRecommendToWeiboTimeline(String schemeId, String postId);
	
	/**
	 * 根据晒单方案ID获取微博
	 * @param schemeId
	 * @return
	 */
	String getWeiboIdByShowSchemeId(String schemeId);
	
	/**
	 * 根据合买投注记录ID查询微博ID
	 * @param schemeId
	 * @return
	 */
	String getWeiboIdByGroupBuyRecordId(String betRecordId);

	public long addMyAllPostFromUserWithDiscuss(String userDiscussTimeline,
			String myPostKey, String followingUid);

	public String updateWeiboMsg(String weiboId, String type, int status);
	
	public void updateWeiboType(String postId, Map<String, String> hash);
}