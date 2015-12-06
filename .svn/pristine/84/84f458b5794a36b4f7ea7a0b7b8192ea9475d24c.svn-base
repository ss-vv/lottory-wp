package com.unison.lottery.weibo.common.service;

import java.util.Set;
import com.unison.lottery.weibo.common.data.TimeLineType;
import com.unison.lottery.weibo.data.PageRequest;
import com.unison.lottery.weibo.data.PageResult;
import com.unison.lottery.weibo.data.RecentDateType;
import com.unison.lottery.weibo.data.SpecialUser;
import com.unison.lottery.weibo.data.SpecialUserBean;
import com.unison.lottery.weibo.data.WeiboMsg;
import com.xhcms.lottery.commons.persist.entity.BetSchemePO;
import com.xhcms.lottery.lang.LotteryId;

/**
 * 关注彩种的相关服务.
 * 
 * @author Yang Bo
 */
public interface LotteryService {
	
	/**
	 * 微博发送前被调用。过滤 "#xxx#"标记串。
	 * @param weiboMsg 发布的微博
	 * @param isSpecialUser 是否特殊用户。
	 * @return 被过滤掉的标记串。
	 */
	public String beforePostForLotteryUser(WeiboMsg weiboMsg, boolean isSpecialUser);
	
	/**
	 * 微博发送后，通知粉丝前被调用。用来添加“彩种用户的三个时间线”。
	 * @param weiboMsg 发布的微博
	 * @param isSpecialUser 是否特殊用户
	 * @param markString 标记串
	 */
	public void afterPostForLotteryUser(WeiboMsg weiboMsg, boolean isSpecialUser,
			String markString);
	
	/**
	 * 将用户设置为指定彩种的彩种用户。
	 * @param weiboUserId 微博用户id
	 * @param lotteryId 彩种id
	 */
	void addLotteryUser(String weiboUserId, LotteryId lotteryId);
	
	/**
	 * 将用户的彩种用户身份删除。
	 * @param weiboUserId 微博用户id
	 * @return true 成功；false 用户不是彩种用户。
	 */
	boolean deleteLotteryUser(String weiboUserId);
	
	/**
	 * 查找特殊用户代表的彩种。
	 * @param weiboUserId 微博用户id
	 * @return 彩种id
	 */
	LotteryId lotteryIdOfSpecialUser(String weiboUserId);
	
	/**
	 * 查询彩种id对应的“彩种用户id”.
	 * @param lotteryId 彩种id
	 * @return 彩种用户id。如果没有对应的彩种用户则返回0.
	 */
	long lotteryUserOf(LotteryId lotteryId);
	
	/**
	 * 指定用户是否是“特殊用户”。
	 * @param weiboUserId 大V彩微博用户id
	 * @param lotteryUserType 特殊用户类型
	 * @return true 是特殊用户；false 不是
	 */
	boolean isSpecialUser(String weiboUserId, SpecialUser specialUserType);
	
	/**
	 * 关注某彩种用户。<br/>
	 * 注意：“彩种用户”A关注另一个“彩种用户”B时，A不能拷贝B的“推荐、赛事、新闻”时间线。
	 * @param followerUid 粉丝用户id
	 * @param lotteryUserId 彩种用户Id
	 */
	void followingLottery(String followerUid, String lotteryUserId);

	/**
	 * 取消对彩种用户(即彩种)的关注
	 * @param followerUid 粉丝用户id
	 * @param lotteryUserId 彩种用户昵称
	 */
	void unFollowingLottery(String followerUid, String lotteryUserId);
	
	/**
	 * 1. 扫描微博中的比赛标记串，如果有就把此微博加入对应彩种的LA和LM中。<br/> 
	 * 2. 遍历对应彩种的LF，把微博加到每个粉丝的UT和UM中。<br/>
	 * 是否“特殊用户”需要在调用前做检查.
	 * 
	 * @param weibo
	 * @return true 包含比赛串; false 不包含
	 */
	boolean scanMatches(WeiboMsg weibo);
	
	/**
	 * 彩种用户转发带“#推荐#”标记串的微博。<br/>
	 * 是否“特殊用户”需要在调用前做检查.
	 * 
	 * @param weibo 被转发的微博
	 * @param mark 已经被过滤掉的特殊标记串，不包括'#'号
	 * @return true 包含“#推荐#”串，并已经处理。
	 */
	boolean forwardRecommends(WeiboMsg weibo, String mark);
	
	/**
	 * 彩种用户转发带“#新闻#”标记串的微博。<br/>
	 * 是否“特殊用户”需要在调用前做检查.
	 * 
	 * @param weibo 被转发的微博
	 * @param mark 已经被过滤掉的特殊标记串，不包括'#'号
	 * @return true 本微博已经按照转发新闻逻辑处理。
	 */
	boolean forwardNews(WeiboMsg weibo, String mark);
	
	/**
	 * 彩种用户发表带“#公告#”标记串的微博.<br/>
	 * 是否“特殊用户”需要在调用前做检查.
	 * 
	 * @param weibo 被发表的微博
	 * @param mark 已经被过滤掉的特殊标记串，不包括'#'号
	 * @return true 本微博已经按照公告微博逻辑处理。
	 */
	boolean postAnnouncement(WeiboMsg weibo, String mark);
	
	/**
	 * 彩种用户发表带“#中奖喜报#”标记串的微博.<br/>
	 * 是否“特殊用户”需要在调用前做检查.
	 * 
	 * @param weibo 微博
	 * @param mark 已经被过滤掉的特殊标记串，不包括'#'号
	 */
	void postWinningNews(WeiboMsg weibo, String mark);
	
	/**
	 * 解析出方案ID
	 * 根据标记串判断是否为一条喜报微博
	 * @param markString
	 * 		格式：微博内容#喜报(60496)#微博内容
	 * @return
	 */
	long isWinningNew(String markString);
	
	/**
	 * 根据方案信息创建喜报
	 * 方案类型：必须是“跟单”，“赛单”，“合买”
	 * @param scheme
	 * @param weibo
	 * @return 
	 */
	boolean createWinningNew(BetSchemePO scheme, WeiboMsg weibo);
	
	/**
	 * 自动发中奖喜报
	 * @param schemeId
	 * @return
	 */
	boolean autoSendWinning(Long schemeId);

	/**
	 * 把 weibo 加入到粉丝(flwr)的时间线中。<br/>需要在后台任务线程中被调用。
	 * @param weibo 微博
	 * @param flwrId 粉丝id
	 * @param destTimelines 时间线集合
	 */
	void addWeiboToTimelines(WeiboMsg weibo, String flwrId, 
			Set<TimeLineType> destTimelines);

	/**
	 * 列出所有特殊用户
	 * @param pageRequest 请求对象。
	 * @return 结果页
	 */
	public PageResult<SpecialUserBean> listAllSpecialUsers(PageRequest pageRequest);
	
	/**
	 * 获取最近时间的彩种用户的推荐
	 * @param weiboUserId 微博用户id
	 * @param recentDateType 近期时间枚举
	 */
	public Set<String> lotteryUserRecommend(String weiboUserId, RecentDateType recentDateType);
	
	/**
	 * 遍历彩种查找对应的微博用户ID
	 * @return
	 */
	Set<String> findWeiboUserByLottery();
	
	/**
	 * 根据赛事ID和彩种查找比赛
	 * @param matchId
	 * @param lotteryId
	 * @return
	 */
	Object findMatch(String matchId, String lotteryId);
	
	/**
	 * 解析赛事串格式
	 * 
	 * @param matchId
	 * @param lotteryId
	 * @return
	 */
	String getMatchContent(String matchId, String lotteryId);
}
