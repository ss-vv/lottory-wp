package com.unison.lottery.weibo.msg.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;
import org.apache.solr.common.SolrInputDocument;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.unison.lottery.newsextract.lang.NewsType;
import com.unison.lottery.weibo.common.nosql.BaseDao;
import com.unison.lottery.weibo.common.nosql.FavouriteDao;
import com.unison.lottery.weibo.common.nosql.MatchDao;
import com.unison.lottery.weibo.common.nosql.MessageDao;
import com.unison.lottery.weibo.common.nosql.PraiseDao;
import com.unison.lottery.weibo.common.nosql.TagDao;
import com.unison.lottery.weibo.common.nosql.impl.Keys;
import com.unison.lottery.weibo.common.service.LotteryService;
import com.unison.lottery.weibo.common.service.NotificationService;
import com.unison.lottery.weibo.common.service.SchemeService;
import com.unison.lottery.weibo.common.service.TagService;
import com.unison.lottery.weibo.common.service.impl.BaseServiceImpl;
import com.unison.lottery.weibo.common.solrj.SolrJ;
import com.unison.lottery.weibo.data.PageRequest;
import com.unison.lottery.weibo.data.PageResult;
import com.unison.lottery.weibo.data.RealFollower;
import com.unison.lottery.weibo.data.RealFollowerResult;
import com.unison.lottery.weibo.data.RecentDateType;
import com.unison.lottery.weibo.data.SpecialUser;
import com.unison.lottery.weibo.data.WeiboMsg;
import com.unison.lottery.weibo.data.WeiboMsgVO;
import com.unison.lottery.weibo.data.WeiboTag;
import com.unison.lottery.weibo.data.WeiboUser;
import com.unison.lottery.weibo.exception.LogicException;
import com.unison.lottery.weibo.lang.Constant;
import com.unison.lottery.weibo.lang.LotteryIdParser;
import com.unison.lottery.weibo.lang.LotteryUser;
import com.unison.lottery.weibo.lang.SpecialTag;
import com.unison.lottery.weibo.lang.WeiboType;
import com.unison.lottery.weibo.msg.persist.dao.WeiboNewsDao;
import com.unison.lottery.weibo.msg.service.AtUserService;
import com.unison.lottery.weibo.msg.service.MessageService;
import com.unison.lottery.weibo.uc.service.RelationshipService;
import com.unison.lottery.weibo.uc.service.UserAccountService;
import com.unison.lottery.weibo.utils.DateUtil;
import com.unison.lottery.weibo.utils.Tool;
import com.unison.thrift.client.bet.BetSchemeClient;
import com.unison.thrift.client.bet.RecoSchemeClient;
import com.unison.thrift.scheme.service.gen.BetSchemeData;
import com.xhcms.commons.mq.MessageSender;
import com.xhcms.exception.XHRuntimeException;
import com.xhcms.lottery.commons.data.BetPartner;
import com.xhcms.lottery.commons.data.BetScheme;
import com.xhcms.lottery.commons.data.weibo.DeleteWeiboHandle;
import com.xhcms.lottery.commons.data.weibo.PublishFollowersHandle;
import com.xhcms.lottery.commons.data.weibo.PublishWeiboNewsHandle;
import com.xhcms.lottery.commons.persist.entity.BetSchemePO;
import com.xhcms.lottery.commons.persist.service.BetPartnerService;
import com.xhcms.lottery.commons.persist.service.BetSchemeRecService;
import com.xhcms.lottery.lang.EntityType;
import com.xhcms.lottery.lang.LotteryId;
import com.xhcms.lottery.utils.CombineBetMatchUtil;

/**
 * 微博消息服务。
 * 
 * @author Wang Lei
 */
@Service
public class MessageServiceImpl extends BaseServiceImpl implements MessageService {
	
	private Logger log = LoggerFactory.getLogger(getClass());
	@Autowired
	private AtUserService atUserService;
	@Autowired
	private PraiseDao praiseDao;
	@Autowired
	private FavouriteDao favouriteDao;
	@Autowired
	private MessageDao messageDao;
	@Autowired
	private UserAccountService userAccountService;
	@Autowired
	private SolrJ solrJ;
	@Autowired
	private LotteryService lotteryService;
	@Autowired(required=false)
    private MessageSender messageSender;
	
	@Autowired
	private MatchDao matchDao;
	
	@Autowired
	private NotificationService notificationService;
	
	@Autowired
	private WeiboNewsDao weiboNewsDao;
	
	@Autowired
	private RelationshipService relationshipService;
	
	@Autowired
	private TagService tagService;
	
	@Autowired
	private BetSchemeClient betSchemeClient;
	
	@Autowired
	private RecoSchemeClient recoSchemeClient;
	
	@Autowired
	private TagDao tagDao;
	
	@Autowired
	BetSchemeRecService betSchemeRecService;
	
	@Autowired
	private SchemeService schemeService;
	
	@Autowired
	private UserSessionBean sessionBean;
	@Autowired
	BetPartnerService betpartnerService;
	
	@Override
	public WeiboMsg getWeiboById(long postId){
		return messageDao.get(postId + "");
	}

	private boolean checkPostWeibo(WeiboMsg weiboMsg) {
		weiboMsg.setPostId(0);
		if(StringUtils.isNotBlank(weiboMsg.getContent())){
			Document d = Jsoup.parseBodyFragment(weiboMsg.getContent());
			String s = d.text();
			int count = s.replaceAll("/[\t\r\n]+/g", "").length();
			if(count > Constant.WeiboContentLength.POST){
				log.info("微博内容字数={} , 不能大于{}字！" , weiboMsg.getContent().length(), Constant.WeiboContentLength.POST);
				return false;
			}
		}
		return true;
	}
	
	@Override
	public WeiboMsgVO publish(WeiboMsg weiboMsg) {
		if(!checkPostWeibo(weiboMsg)) {
			return new WeiboMsgVO();
		}
		
		return post(weiboMsg);
	}
	
	@Override
	public WeiboMsgVO publishWithoutScheme(WeiboMsg weiboMsg) {
		if(!checkPostWeibo(weiboMsg)) {
			return new WeiboMsgVO();
		}
		
		long postId = postWithoutScheme(weiboMsg);
		WeiboMsgVO weiboMsgVO = messageDao.getVO(postId+"");
		
		return weiboMsgVO;
	}
	
	@Override
	public PageResult<WeiboMsgVO> findMatchDiscussPost(long uid , String matchId,
			PageRequest pageRequest) {
		if(StringUtils.isBlank(matchId)){
			return new PageResult<WeiboMsgVO>(pageRequest, new ArrayList<WeiboMsgVO>());
		}
		return  checkFavoriateAndLike(uid, list(Keys.matchDiscussKey(matchId), pageRequest));
	}
	
	//-------------------------新闻相关 开始---------------------------
	@Override
	public PageResult<WeiboMsgVO> findAllMatchNewsPost(long uid , String matchId,
			PageRequest pageRequest) {
		if(StringUtils.isBlank(matchId)){
			log.error("查询所有赛事新闻微博出错， matchId is null!");
			return new PageResult<WeiboMsgVO>(pageRequest, new ArrayList<WeiboMsgVO>());
		}
		return  checkFavoriateAndLike(uid, list(Keys.getTeamNewsTimelineKey(matchId), pageRequest));
	}
	
	@Override
	public long getNewMatchNewsPostCount(String matchId, String score,
			int timeLineOffset){
		if(StringUtils.isBlank(score)){
			return 0;
		}
		BigDecimal timeLine = new BigDecimal(score);
		double newScore = timeLine.doubleValue();
		double max = System.currentTimeMillis();
		Long count = messageDao.getNewMatchNewsPostCountByScore(matchId,newScore, max);
		if(count == null){
			count = 0l;
		}
		return count - timeLineOffset;
	}
	@Override
	public WeiboMsgVO publishAsLotteryNews(WeiboMsg weiboMsg,NewsType newsType) {
		weiboMsg.setPostId(0);
		if(StringUtils.isBlank(weiboMsg.getContent())){
			return new WeiboMsgVO();
		}
		return postAsNews(weiboMsg,newsType);
	}
	
	private void publishAsTeamNews(WeiboMsg weiboMsg){
		List<String> teamIds = analyzeMatchIdBaseWeiboMsg(weiboMsg);
		if(teamIds.size() < 1){
			return;
		}
		long postid = weiboMsg.getId();
		for (String teamId : teamIds) {
			//添加到球队新闻时间线
			weiboNewsDao.addTeamNewsTimeline(teamId, postid, weiboMsg.getCreateTime());
		}
		return ;
	}
	// 发布微博
	private void postWeiboNews(WeiboMsg weiboMsg){
		weiboMsg.setCreateTime(System.currentTimeMillis());
		long postid = messageDao.post(weiboMsg);
		log.info("publish new 'news weibo' success! postid = {}", postid);
	}
	// 发布到我的微博
	private void postToMyPosts(WeiboMsg weiboMsg){
		BigDecimal timeLine = new BigDecimal(String.valueOf(weiboMsg.getCreateTime()));
		double score = timeLine.doubleValue();
		messageDao.postToMyPosts(""+weiboMsg.getOwnerId(), score,  ""+weiboMsg.getId());
	}
	private WeiboMsgVO postAsNews(WeiboMsg weiboMsg,NewsType newsType) {
		postWeiboNews(weiboMsg);
		postToMyPosts(weiboMsg);
		BigDecimal timeLine = new BigDecimal(String.valueOf(weiboMsg.getCreateTime()));
		double score = timeLine.doubleValue();
		long postid = weiboMsg.getId();
		long ownerId = weiboMsg.getOwnerId();
		//将微博加入到全局时间线
		messageDao.addToGlobalTimeline(postid+"", score);
		
		//添加到彩种新闻时间线
		weiboNewsDao.addLotteryNewsTimeline(newsType, postid, score);
		
		//将微博加入到赛事时间线
		addToMatchTimeline(weiboMsg);
		
		// @用户
		atUserService.atUserByPost(weiboMsg);
		
		// 发送后处理(推送给关注我的人)
		messageSender.send(new PublishWeiboNewsHandle(postid, ownerId, score,newsType.name(),null));
		
		saveWeiboIndex(String.valueOf(postid), weiboMsg.getTitle(),
				weiboMsg.getContent(), String.valueOf(weiboMsg.getCreateTime()),
				weiboMsg.getType());
		// 处理关联比赛的微博内容
		publishAsTeamNews(weiboMsg);
		return getWeiboVoById(postid);
	}
	private List<String> analyzeMatchIdBaseWeiboMsg(WeiboMsg weiboMsg){
		List<String> matchIdList = new ArrayList<String>();
		if(null != weiboMsg &&
				StringUtils.isNotBlank(weiboMsg.getContent())) {
			String content = weiboMsg.getContent();
			String matchPattern = "\\$.*?\\(([a-zA-Z]+\\d+)\\)\\$";
			Pattern pattern = Pattern.compile(matchPattern);
			Matcher matcher = pattern.matcher(content);
			while (matcher.find()) {
				String str = matcher.group(1);
				matchIdList.add(str);
			}
		}
		log.info("从微博内容中解析出的赛事id list = {}", matchIdList);
		return matchIdList;
	}
	//-------------------------新闻相关 结束---------------------------
	
	
	/**
	 * @param WeiboMsg 要创建的新微博对象，需要 ownerId、postId 和 content。
	 */
	@Override
	public WeiboMsgVO forward(WeiboMsg weiboMsg) {
		LogicException e = null;
		if(weiboMsg == null || weiboMsg.getPostId() < 1 || weiboMsg.getOwnerId() < 1){
			e = new LogicException("forward post error !" + (weiboMsg == null ? 
					"weiboMsg =null" : "postId="+weiboMsg.getPostId() + ", ownerId=" +weiboMsg.getOwnerId()));
			log.error("参数错误！",e);
			throw e;
		}
		if(StringUtils.isNotBlank(weiboMsg.getContent())){
			if(weiboMsg.getContent().length() > Constant.WeiboContentLength.FORWARD){
				log.info("微博转发字数={} , 不能大于{}字！" , weiboMsg.getContent().length(), Constant.WeiboContentLength.FORWARD);
				return new WeiboMsgVO();
			}
		} else {
			weiboMsg.setContent("转发");
		}
		// 找到要转发的微博
		String postId = weiboMsg.getPostId()+"";
		WeiboMsg  forwardMsg = messageDao.get(postId);
		if(forwardMsg == null){
			e = new LogicException("forward post error ! forwardMsg is not found !" +
					" postId= " + postId);
			log.error("参数错误！", e);
			throw e;
		}
		if(forwardMsg.getPostId() > 0){
			weiboMsg.setPostId(forwardMsg.getPostId());
		}
		log.info("forward post id = {}", weiboMsg.getPostId());
		WeiboMsgVO result = post(weiboMsg);
		log.info("forward post id = {} success! new post id = {}", postId, result.getId());
		if(result != null && result.getId() > 0){
			increaseForwardCount(Long.parseLong(postId));
			if(forwardMsg.getPostId() > 0){
				increaseForwardCount(forwardMsg.getPostId());
			}
		}
		return result;
	}
	
	@Override
	public String editLong(long userId, WeiboMsg weiboMsg){
		if(weiboMsg == null || userId < 1){
			log.error("editLong faild! weiboMsg is null or userId is wrong! userId = {}", userId);
			return null;
		}
		return messageDao.edit(weiboMsg.getId()+"", weiboMsg.getTitle(), weiboMsg.getContent());
	}
	
	/**
	 * 发微博（核心方法）
	 * @param weiboMsg
	 * @return
	 */
	private WeiboMsgVO post(WeiboMsg weiboMsg){
		long postId = postWithoutScheme(weiboMsg);
		
		return getWeiboVoById(postId);
	}
	
	private long postWithoutScheme(WeiboMsg weiboMsg) {
		boolean isSpecialUser = false;
		isSpecialUser = lotteryService.isSpecialUser(""+weiboMsg.getOwnerId(), 
				SpecialUser.LOTTERY_USER);

		// 特殊用户改变微博内容
		String mark = lotteryService.beforePostForLotteryUser(weiboMsg, isSpecialUser);
		
		// 发布微博
		weiboMsg.setCreateTime(System.currentTimeMillis());
		long postid = messageDao.post(weiboMsg);
		long ownerId = weiboMsg.getOwnerId();
		log.info("publish new " + (StringUtils.isNotBlank(weiboMsg.getTitle()) ? "long" : "") 
				+ " post success! postid = {}", postid);
		
		// 发布到我的微博
		BigDecimal timeLine = new BigDecimal(String.valueOf(weiboMsg.getCreateTime()));
		double score = timeLine.doubleValue();
		messageDao.postToMyPosts(""+ownerId, score,  ""+postid);
		
		//将微博加入到全局时间线
		messageDao.addToGlobalTimeline(postid+"", score);
		
		//将微博加入到赛事时间线
		addToMatchTimeline(weiboMsg);
		
		// @用户
		atUserService.atUserByPost(weiboMsg);
		
		//加标签
		addWeiboTag(weiboMsg);
		
		// 发送后处理(赛事、公告等)
		lotteryService.afterPostForLotteryUser(weiboMsg, isSpecialUser, mark);
		
		// 发送后处理(推送给关注我的人)
		messageSender.send(new PublishFollowersHandle(postid, ownerId, score));
		
		saveWeiboIndex(String.valueOf(postid), weiboMsg.getTitle(),
				weiboMsg.getContent(), 
				String.valueOf(weiboMsg.getCreateTime()),
				weiboMsg.getType());
		
		return postid;
	}
	
	/**
	 * 将“实单/推荐”微博推荐给粉丝
	 * @param weiboMsg
	 */
	protected void handlePostFollowers(WeiboMsg weiboMsg) {
		long ownerId = weiboMsg.getOwnerId();
		long postId = weiboMsg.getId();
		try {
			List<Long> followerList = relationshipService.myFollowers(ownerId);
			
			double score = Double.valueOf(weiboMsg.getCreateTime());
			postRecomRealToFollowers(followerList,
					score, postId);
		} catch (Exception e) {
			log.error("处理将用户发的‘推荐/实单微博推给粉丝’失败！微博用户id={}, 微博id={}", ownerId,
					postId, e);
		}
	}
	
	@Override
	// 发送到关注我的人
	public void postToAllposts(String[] users, double score , long postid){
		messageDao.postToAllposts(users , score, ""+postid);
		WeiboMsg w = messageDao.get(postid);
		if(null != w && StringUtils.isBlank(w.getType()) &&
				!(w.getOwnerId()+"").equals(LotteryUser.JCZQ.getWeiboUserId()) &&
				!(w.getOwnerId()+"").equals(LotteryUser.JCLQ.getWeiboUserId()) &&
				!(w.getOwnerId()+"").equals(LotteryUser.CTZC.getWeiboUserId()) &&
				!(w.getOwnerId()+"").equals(LotteryUser.SSQ.getWeiboUserId()) ){ //微博类型为用户自己发的 且不是彩种用户发的
			messageDao.postToDisscussTimeline(users , score, ""+postid);
		}
	}
	@Override
	// 发送到关注我的人
	public void deleteWeiboFromTimeline(String[] users, long postid,String content){
		WeiboMsg weiboMsg = new WeiboMsg();
		weiboMsg.setContent(content);
		List<String> matchIdList = analyzeMatchIdBaseWeiboMsg(weiboMsg);
		if(null !=matchIdList && matchIdList.size() > 0) {
			for (String matchId : matchIdList) {
				messageDao.zrem(Keys.matchAllContentKey(matchId), postid + "");
				messageDao.zrem(Keys.matchDataKey(matchId), postid + "");
				messageDao.zrem(Keys.matchDiscussKey(matchId), postid + "");
				messageDao.zrem(Keys.matchRealDataKey(matchId), postid + "");
			}
		}
		for (String userId : users) {
			messageDao.zrem(Keys.userMainTimeline(userId), postid + "");
			messageDao.zrem(Keys.userDiscussTimeline(userId), postid + "");
			messageDao.zrem(Keys.userRecomRealsTimelineKey(userId), postid + "");
			messageDao.zrem(Keys.userSelfRecomRealsKey(userId), postid + "");
			messageDao.zrem(Keys.userRecommendsKey(userId), postid + "");
			messageDao.zrem(Keys.userMatchesKey(userId), postid + "");
			messageDao.zrem(Keys.userNewsKey(userId), postid + "");
			messageDao.zrem(Keys.favorites(Long.parseLong(userId)), postid + "");
			messageDao.zrem(Keys.atMeKey(userId), postid + "");
			messageDao.zrem(Keys.HOTRECOMMEND, postid + "");
			messageDao.zrem(Keys.matchRecommendLottery(LotteryId.JCLQ.name()), postid + "");
			messageDao.zrem(Keys.matchRecommendLottery(LotteryId.JCZQ.name()), postid + "");
			messageDao.zrem(Keys.matchRecommendLottery(LotteryId.CTZC.name()), postid + "");
			messageDao.zrem(Keys.matchRecommendLottery(LotteryId.BJDC.name()), postid + "");
			messageDao.zrem(Keys.matchRecommendLottery(LotteryId.SSQ.name()), postid + "");

			messageDao.zrem(Keys.getLotteryRealFollowTimeline(LotteryId.JCLQ.name()), postid + "");
			messageDao.zrem(Keys.getLotteryRealFollowTimeline(LotteryId.JCZQ.name()), postid + "");
			messageDao.zrem(Keys.getLotteryRealFollowTimeline(LotteryId.CTZC.name()), postid + "");
			messageDao.zrem(Keys.getLotteryRealFollowTimeline(LotteryId.BJDC.name()), postid + "");
			messageDao.zrem(Keys.getLotteryRealFollowTimeline(LotteryId.SSQ.name()), postid + "");
			messageDao.zrem(Keys.getLotteryRealFollowTimeline("ALL"), postid + "");

			messageDao.zrem(Keys.getLotteryRecommendTimeline(LotteryId.JCLQ.name()), postid + "");
			messageDao.zrem(Keys.getLotteryRecommendTimeline(LotteryId.JCZQ.name()), postid + "");
			messageDao.zrem(Keys.getLotteryRecommendTimeline(LotteryId.CTZC.name()), postid + "");
			messageDao.zrem(Keys.getLotteryRecommendTimeline(LotteryId.BJDC.name()), postid + "");
			messageDao.zrem(Keys.getLotteryRecommendTimeline(LotteryId.SSQ.name()), postid + "");
			messageDao.zrem(Keys.getLotteryRecommendTimeline("ALL"), postid + "");
			
			messageDao.zrem(Keys.haveUnreadMentions(Long.parseLong(userId)) , postid + "");
			messageDao.srem(Keys.praise(Long.parseLong(userId)) , postid + "");
			messageDao.zrem(Keys.discussKey(RecentDateType.DAY) , postid + "");
			messageDao.zrem(Keys.ANNOUNCE , postid + "");
			messageDao.zrem(Keys.getLotteryNewsTimelineKey(NewsType.FootBall.name()) , postid + "");
			messageDao.zrem(Keys.getLotteryNewsTimelineKey(NewsType.BasketBall.name()) , postid + "");
		}
	}
	@Override
	// 发送新闻到所有关注者
	public void postNewsToAllposts(String[] users, double score , long postid){
		messageDao.postNewsToAllposts(users , score, ""+postid);
	}

	@Override
	public void postRecomRealToFollowers(List<Long> followerList, double score,
			long postId) {
		messageDao.postRecomRealToFollowers(followerList, score, postId+"");
	}
	
	/**添加微博内容索引*/
	private void saveWeiboIndex(String id, String title,
			String content, String createTime,String weiboType){
		SolrInputDocument doc1 = new SolrInputDocument();
		doc1.addField("id", "id:WeiboMsg:" + id );
		if(StringUtils.isNotBlank(title)){
			doc1.addField("title", title);
		}
	    doc1.addField("weiboContent", content);
	    doc1.addField("createTime", createTime);
	    doc1.addField("contentType", Constant.SolrConfig.SEARCH_TYPE_WEIBO);
	    doc1.addField("weiboType", weiboType == null ? '0' : weiboType);
	    
	    Collection<SolrInputDocument> docs = new ArrayList<SolrInputDocument>();
	    docs.add( doc1 );
		try {
			solrJ.add(docs);
		} catch (Exception e) {
			log.error("添加微博内容索引失败！", e);
		}
	}
	
	@Override
	public Long delete(long userId, long messageId) {
		long resule = 0l;
		if(userId < 1 || messageId < 1 ){
			log.error("delete error, userId={},postId={}",userId, messageId);
			return 0l;
		}
		WeiboMsg weiboMsg = messageDao.get(""+messageId);
		if(weiboMsg == null){
			log.error("delete faild ! postId = {} not found", messageId);
			return 0l;
		}
		if(weiboMsg.getOwnerId() != userId){
			LogicException e = new LogicException("delete faild ! cannot delete postId " +
					"="+messageId+", operatorId="+userId);
			log.error("",e);
			throw e;
		}
		if(messageDao.deleteMyPosts(""+userId, ""+messageId) != 1){
			log.error("deleteMyPosts error ! userId = {} ,postId = {} not found !", userId,messageId);
			resule = 0l;
		}
		if(messageDao.deleteAllposts(new String[]{""+userId}, ""+messageId) != 1){
			log.error("deleteAllposts error ! userId = {} ,postId = {} not found !", userId,messageId);
			resule = 0l;
		}
		
		//删除微博时，将微博中提到的用户在自己未读“提到我的”时间线上删除
		Set<String> users = atUserService.findAtUsers(weiboMsg.getContent());
		if(null != users && users.size() > 0) {
			String[] uids = userAccountService.findWeiboUserIdsByNickNames(users);
			if(null != uids) {
				notificationService.deleteUnreadMetion(uids, weiboMsg.getId());
			}
		}
		
		if("OK".equals(messageDao.edit(""+messageId, "", Constant.DeleteContent.USER_DELETED))){
			solrJ.deleteIndexById("id:WeiboMsg:" + messageId);//同时删除此条微博在solr中的索引
			
			//重置微博的类型和状态
			String type = WeiboType.NORMAL.getType();
			int status = 9;
			messageDao.updateWeiboMsg(""+messageId, type,status);
			
			messageSender.send(new DeleteWeiboHandle(messageId, weiboMsg.getOwnerId(), 0,weiboMsg.getContent()));
			
			//删除微博标签
			tagDao.delTagListByWeiboId(messageId);
			
			log.info("用户uid = {} ,删除POST微博 postId = {} ,成功！",userId,messageId);
		}else{
			log.error("用户uid = {} ,删除POST微博 postId = {} ,失败！",userId,messageId);
		}
		return resule;
	}

	@Override
	public long increaseCommentCount(long postId) {
		return messageDao.increaseCommentCount(""+postId, 1);
	}
	
	@Override
	public long decreaseCommentCount(long postId) {
		return messageDao.increaseCommentCount(""+postId, -1);
	}
	
	@Override
	public long increaseForwardCount(long postId) {
		return messageDao.increaseForwardCount(""+postId);
	}
	
	@Override
	public  PageResult<WeiboMsgVO> findRecommends(long uid, PageRequest pageRequest) {
		log.info("查询大V彩微博uid={}所有推荐。", uid);
		if(uid < 1){
			log.error("findRecommends error ! uid is wrong! uid = {}", uid);
			return new PageResult<WeiboMsgVO>(pageRequest, new ArrayList<WeiboMsgVO>());
		}
		return  checkFavoriateAndLike(uid, list(Keys.userRecommendsKey(uid+""), pageRequest));
	}
	
	@Override
	public PageResult<WeiboMsgVO> findRecommends(long uid, String score) {
		log.info("查询大V彩微博uid={}新推荐。", uid);
		if(uid < 1){
			log.error("findRecommends error ! uid is wrong! uid = {}", uid);
			return new PageResult<WeiboMsgVO>(new PageRequest(), new ArrayList<WeiboMsgVO>());
		}
		return checkFavoriateAndLike(uid, list(Keys.userRecommendsKey(uid+""), Long.parseLong(score)));
	}
	
	@Override
	public  PageResult<WeiboMsgVO> findMatches(long uid, PageRequest pageRequest) {
		log.info("查询大V彩微博uid={}所有赛事。", uid);
		if(uid < 1){
			log.error("findMatches error ! uid is wrong! uid = {}", uid);
			return new PageResult<WeiboMsgVO>(pageRequest, new ArrayList<WeiboMsgVO>());
		}
		return  checkFavoriateAndLike(uid, list(Keys.userMatchesKey(uid+""), pageRequest));
	}
	
	@Override
	public PageResult<WeiboMsgVO> findMatches(long uid, String score) {
		log.info("查询大V彩微博uid={}新赛事。", uid);
		if(uid < 1){
			log.error("findMatches error ! uid is wrong! uid = {}", uid);
			return new PageResult<WeiboMsgVO>(new PageRequest(), new ArrayList<WeiboMsgVO>());
		}
		return checkFavoriateAndLike(uid, list(Keys.userMatchesKey(uid+""), Long.parseLong(score)));
	}
	
	@Override
	public  PageResult<WeiboMsgVO> findNews(long uid, PageRequest pageRequest) {
		log.info("查询大V彩微博uid={}所有新闻。", uid);
		if(uid < 1){
			log.error("findNews error ! uid is wrong! uid = {}", uid);
			return new PageResult<WeiboMsgVO>(pageRequest, new ArrayList<WeiboMsgVO>());
		}
		return  checkFavoriateAndLike(uid, list(Keys.userNewsKey(uid+""), pageRequest));
	}
	
	@Override
	public PageResult<WeiboMsgVO> findNews(long uid, String score) {
		log.info("查询大V彩微博uid={}新新闻。", uid);
		if(uid < 1){
			log.error("findNews error ! uid is wrong! uid = {}", uid);
			return new PageResult<WeiboMsgVO>(new PageRequest(), new ArrayList<WeiboMsgVO>());
		}
		return checkFavoriateAndLike(uid, list(Keys.userNewsKey(uid+""), Long.parseLong(score)));
	}

	@Override
	public PageResult<WeiboMsgVO> findAllPost(long uid, PageRequest pageRequest) {
		log.info("查询大V彩微博uid={}所有微博。", uid);
		if(uid < 1){
			log.error("findAllPost error ! uid is wrong! uid = {}", uid);
			return new PageResult<WeiboMsgVO>(pageRequest, new ArrayList<WeiboMsgVO>());
		}
		return  checkFavoriateAndLike(uid, list(Keys.userMainTimeline(""+uid), pageRequest));
	}
	
	@Override
	public PageResult<WeiboMsgVO> findAllRecommendsPost(long uid, PageRequest pageRequest) {
		log.info("查询大V彩微博uid={}所有推荐微博。", uid);
		if(uid < 1){
			log.error("findAllRecommendsPost error ! uid is wrong! uid = {}", uid);
			return new PageResult<WeiboMsgVO>(pageRequest, new ArrayList<WeiboMsgVO>());
		}
		return  checkFavoriateAndLike(uid, list(Keys.userRecomRealsTimelineKey(""+uid), pageRequest));
	}
	
	@Override
	public PageResult<WeiboMsgVO> findRecommendListByLotteryCategory(
			String lotteryId, PageRequest pageRequest) {
		log.debug("查询彩种:{}，推荐列表.", lotteryId);
		PageResult<WeiboMsgVO> result = null;
		if (StringUtils.isNotBlank(lotteryId)) {
			result = checkFavoriateAndLike(0L, list(Keys.getLotteryRecommendTimeline(lotteryId), pageRequest));
		} else {
			result = new PageResult<WeiboMsgVO>(pageRequest, new ArrayList<WeiboMsgVO>());
		}
		return result;
	}
	@Override
	public PageResult<WeiboMsgVO> findRealFollowListByLotteryCategory(
			String lotteryId, PageRequest pageRequest) {
		log.debug("查询彩种:{}，实单/跟单列表.", lotteryId);
		PageResult<WeiboMsgVO> result = null;
		if (StringUtils.isNotBlank(lotteryId)) {
			result = checkFavoriateAndLike(0L, list(Keys.getLotteryRealFollowTimeline(lotteryId), pageRequest));
		} else {
			result = new PageResult<WeiboMsgVO>(pageRequest, new ArrayList<WeiboMsgVO>());
		}
		return result;
	}
	
	@Override
	public PageResult<WeiboMsgVO> findAllMatchsPost(long uid, PageRequest pageRequest) {
		log.info("查询大V彩微博uid={}所有赛事微博。", uid);
		if(uid < 1){
			log.error("findAllMatchsPost error ! uid is wrong! uid = {}", uid);
			return new PageResult<WeiboMsgVO>(pageRequest, new ArrayList<WeiboMsgVO>());
		}
		return  checkFavoriateAndLike(uid, list(Keys.userMatchesKey(""+uid), pageRequest));
	}
	
	@Override
	public PageResult<WeiboMsgVO> findAllNewsPost(long uid, PageRequest pageRequest) {
		log.info("查询大V彩微博uid={}所有新闻微博。", uid);
		if(uid < 1){
			log.error("findAllNewsPost error ! uid is wrong! uid = {}", uid);
			return new PageResult<WeiboMsgVO>(pageRequest, new ArrayList<WeiboMsgVO>());
		}
		return  checkFavoriateAndLike(uid, list(Keys.userNewsKey(""+uid), pageRequest));
	}
	@Override
	public PageResult<WeiboMsgVO> findAllDiscussPost(long uid, PageRequest pageRequest) {
		log.info("查询大V彩微博uid={}所有讨论微博。", uid);
		if(uid < 1){
			log.error("findAllDiscussPost error ! uid is wrong! uid = {}", uid);
			return new PageResult<WeiboMsgVO>(pageRequest, new ArrayList<WeiboMsgVO>());
		}
		return  checkFavoriateAndLike(uid, list(Keys.userDiscussTimeline(""+uid), pageRequest));
	}
	
	@Override
	public PageResult<WeiboMsgVO> findAllPost(long uid, String score) {
		log.info("查询大V彩微博uid={}新微博。", uid);
		if(uid < 1){
			log.error("findAllPost error ! uid is wrong! uid = {}", uid);
			return new PageResult<WeiboMsgVO>(new PageRequest(), new ArrayList<WeiboMsgVO>());
		}
		return checkFavoriateAndLike(uid, list(Keys.userMainTimeline(""+uid), Long.parseLong(score)));
	}
	
	@Override
	public PageResult<WeiboMsgVO> checkFavoriateAndLike(long uid, PageResult<WeiboMsgVO> result){
		if(uid < 1 || result == null || result.getResults().isEmpty()){
			return result;
		}
		Set<String> likes = praiseDao.list(uid);
		Set<String> favoriates = favouriteDao.loadAll(uid, 0, -1);
		if(likes == null || likes.isEmpty()){
			return result;
		}
		for(WeiboMsgVO msg : result.getResults()){
			if(likes != null && likes.contains(msg.getId()+"")){
				msg.setLike(true);
			}
			if(favoriates != null && favoriates.contains(msg.getId()+"")){
				msg.setFavourited(true);
			}
		}
		//this.addMyFollowingData(uid, result);
		return result;
	}
	@Override
	public PageResult<WeiboMsgVO> addMyFollowingData(long uid, PageResult<WeiboMsgVO> result){
		if(uid < 1 || result == null || result.getResults().isEmpty()){
			return result;
		}
		String[] myFollowingsIds = relationshipService.findFollowingByUid(uid).getFollowings();
		for(WeiboMsgVO msg : result.getResults()){
			msg.setMyFollowingsIds(myFollowingsIds);
		}
		return result;
	}
	
	@Override
	public PageResult<WeiboMsgVO> checkLikeSetFavoriate(long uid, PageResult<WeiboMsgVO> result){
		if(uid < 1 || result == null || result.getResults().isEmpty()){
			return result;
		}
		Set<String> likes = praiseDao.list(uid);
		for(WeiboMsgVO msg : result.getResults()){
			if(likes != null && likes.contains(msg.getId()+"")){
				msg.setLike(true);
			}
			msg.setFavourited(true);
		}
		return result;
	}
	
	@Override
	public String getMyAllPostScoreByValue(long postId, String value){
		log.info("查询微博id={}的时间线, key={},value={}", postId, Keys.userMainTimeline(postId+""), value);
		Double score = messageDao.zscore(Keys.userMainTimeline(postId+""), value);
		log.info("微博id={}的时间线={}",postId, score.longValue());
		return score != null ? score.longValue()+"" : null;
	}
	
	@Override
	public PageResult<WeiboMsgVO> findMyPost(long uid, PageRequest pageRequest) {
		log.info("查询我的微博。uid = {}", uid);
		if(uid < 1){
			log.error("findMyPost error ! uid is wrong! uid = {}", uid);
			return new PageResult<WeiboMsgVO>(pageRequest, new ArrayList<WeiboMsgVO>());
		}
		return  checkFavoriateAndLike(uid, list(Keys.myPostKey(""+uid), pageRequest));
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public PageResult<WeiboMsgVO> list(String key, PageRequest pageRequest){
		PageResult<String> result;
		if(PageRequest.SORT_ORDER_ASC.equals(pageRequest.getSortOrder())){
			result = (PageResult<String>) ascListSortedSetByPageRequest(key, pageRequest);
		} else {//默认降序
			result = (PageResult<String>) descListSortedSetByPageRequest(key, pageRequest);
		}
		String[] keys = new String[result.getResults().size()];
		int i = 0;
		for(String id : result.getResults()){
			keys[i] = Keys.postKey(id);
			i++;
		}
		return new PageResult<WeiboMsgVO>(pageRequest, list(keys));
	}

	private PageResult<WeiboMsgVO> list(String key, long score){
		Set<String> result = messageDao.zrangeByScoreLimt(key, "(" + score , System.currentTimeMillis() +"", 0, 14);
		String[] keys = new String[result.size()];
		String[] results = result.toArray(new String[result.size()]);
		Arrays.sort(results, Collections.reverseOrder());
		int i = 0;
		for(String id : results){
			keys[i] = Keys.postKey(id);
			i++;
		}
		return new PageResult<WeiboMsgVO>(new PageRequest(), list(keys));
	}
	
	@Override
	public PageResult<WeiboMsgVO> listPost(Long[] postIds, PageRequest pageRequest){
		String[] keys = new String[postIds.length];
		for(int i = 0 ; i < postIds.length ; i++){
			keys[i] = Keys.postKey(postIds[i].toString());
		}
		return new PageResult<WeiboMsgVO>(pageRequest, list(keys));
	}
	
	@Override
	public PageResult<WeiboMsgVO> listPost(String[] postIds, PageRequest pageRequest){
		String[] keys = new String[postIds.length];
		for(int i = 0 ; i < postIds.length ; i++){
			keys[i] = Keys.postKey(postIds[i]);
		}
		return new PageResult<WeiboMsgVO>(pageRequest, list(keys));
	}
	
	private List<WeiboMsgVO> list(String[] keys){
		log.info("查询结果，返回{}条微博id。", keys.length);
		List<WeiboMsgVO> weiboMsgs = messageDao.list(keys);
		log.info("查询结果，返回{}条微博。", weiboMsgs.size());
		// 查询微博对应的用户信息
		Map<Long, WeiboUser> usersMap = userAccountService.findWeiboUserByWeiboUids(Tool
				.findUserIds(weiboMsgs));
		//  循环写入微博
		for(WeiboMsgVO wb : weiboMsgs){
			wb.setUser(usersMap.get(wb.getOwnerId()));
			wb.setCreateTimeFmt(DateUtil.format(wb.getCreateTime()));
			if(wb.getSourceWeiboMsg() != null && wb.getSourceWeiboMsg().getOwnerId()> 0){
				wb.setSourceUser(usersMap.get(wb.getSourceWeiboMsg().getOwnerId()));
				wb.setSourceCreateTimeFmt(DateUtil.format(wb.getSourceWeiboMsg().getCreateTime()));
			}
			wb.setLikeUsers(this.findLikeWeiboUser("" + wb.getId()));
			loadWeiboSchemeInfo(wb);
			loadSourceWeiboSchemeInfo(wb);
		}
		return weiboMsgs;
	}
	
	@Override
	public void loadWeiboSchemeInfo(WeiboMsgVO wb){
		if(WeiboType.SHOW_SCHEME.getType().equals(wb.getType()) 
				&& wb.getSchameId() > 0){
			try {
				RealFollowerResult realFollowerResult = this.findRealFollower(wb.getSchameId());
				wb.setRealFollowers(realFollowerResult.getRealFollowers());
				wb.setBetScheme(realFollowerResult.getBetScheme());
			} catch (XHRuntimeException e) {
				//实单方案不存在
				log.error("获取微博关联的方案异常，实单方案不存在，微博信息={},异常信息:", wb, e);
			}
		} else if(WeiboType.RECOMMEND.getType().equals(wb.getType()) && wb.getSchameId() > 0){
			try {
				BetScheme betScheme  = betSchemeRecService.viewRecScheme(wb.getSchameId());
				wb.setBetScheme(betScheme);
			} catch (XHRuntimeException e) {
				//推荐方案不存在
				log.error("推荐方案不存在",e);
			}
		} else if(WeiboType.GROUP.getType().equals(wb.getType()) && wb.getSchameId() > 0) {
			RealFollowerResult result = this.groupBuyUserRecord(wb.getSchameId());
			wb.setRealFollowers(result.getRealFollowers());
			wb.setBetScheme(result.getBetScheme());
		}
	}
	@Override
	public void loadSourceWeiboSchemeInfo(WeiboMsgVO wbVO){
		if(wbVO.getSourceWeibo() != null){ //兼容源微博保存在sourceWeibo属性而没有在sourceWeiboMsg属性中
			WeiboMsg w = new WeiboMsg();
			BeanUtils.copyProperties(wbVO.getSourceWeibo(), w);
			wbVO.setSourceWeiboMsg(w);
		}
		if(wbVO.getSourceWeiboMsg() != null && wbVO.getSourceWeiboMsg().getOwnerId()> 0){
			WeiboMsg sourceWB = wbVO.getSourceWeiboMsg();
			wbVO.setSourceType(sourceWB.getType());
			if(WeiboType.SHOW_SCHEME.getType().equals(sourceWB.getType()) && sourceWB.getSchameId() > 0){
				RealFollowerResult realFollowerResult = this.findRealFollower(sourceWB.getSchameId());
				wbVO.setSourceRealFollowers(realFollowerResult.getRealFollowers());
				try {
					wbVO.setSourceBetScheme(realFollowerResult.getBetScheme());
				} catch (XHRuntimeException e) {
					//实单方案不存在
					log.info("实单方案不存在",e);
				}
			} else if(WeiboType.RECOMMEND.getType().equals(sourceWB.getType()) && sourceWB.getSchameId() > 0){
				try {
					BetScheme betScheme  = betSchemeRecService.viewRecScheme(sourceWB.getSchameId());
					wbVO.setSourceBetScheme(betScheme);
				} catch (XHRuntimeException e) {
					//推荐方案不存在
					log.info("推荐方案不存在",e);
				}
			} else if(WeiboType.GROUP.getType().equals(sourceWB.getType())) {
				RealFollowerResult result = this.groupBuyUserRecord(sourceWB.getSchameId());
				wbVO.setSourceRealFollowers(result.getRealFollowers());
				wbVO.setSourceBetScheme(result.getBetScheme());
			}
		}
	}
	
	@Override
	public RealFollowerResult groupBuyUserRecord(long schemeId) {
		long userId = sessionBean.getLcUserId();
		BetScheme betScheme = schemeService.viewSchemeCache(schemeId, userId, EntityType.DISPLAY_GROUPBUY);
		List<RealFollower> realFollowers = new ArrayList<RealFollower>();
		
		if(EntityType.BETTING_PARTNER == betScheme.getType() &&
    			betScheme.getPartnerCount() > 0) {
			List<BetPartner> betPartnerList = betScheme.getGroupbuyPartners();
			if(null != betPartnerList) {
				for (BetPartner betPartner : betPartnerList) {
	    			RealFollower r = new RealFollower();
					r.setAfterTaxBonus(betPartner.getWinAmount());
					r.setBuyAmount(betPartner.getBetAmount());
					r.setFollowSchemeId(betPartner.getSchemeId());
					r.setSponsor(betPartner.getUsername());
					r.setSponsorId(betPartner.getUserId());
					WeiboUser w = userAccountService.findWeiboUserByLotteryUidsNotLoadUser(betPartner.getUserId()+"");
					if(null != w){
						BeanUtils.copyProperties(w, r);
					}
					realFollowers.add(r);
				}
			}
    	}
		
		RealFollowerResult result = new RealFollowerResult();
    	result.setRealFollowers(realFollowers);
    	//将同一赛事不同玩法合并
		betScheme = CombineBetMatchUtil.combine(betScheme);
    	result.setBetScheme(betScheme);
		return result;
	}
	
	@Override
	public RealFollowerResult findRealFollower(long schemeId) {
		long userId = sessionBean.getLcUserId();
		BetScheme betScheme = schemeService.viewSchemeCache(schemeId, userId, EntityType.DISPLAY_SHOW);
		List<RealFollower> realFollowers = new ArrayList<RealFollower>();
		List<BetScheme>  followSchemes = new ArrayList<BetScheme>();
    	if(betScheme.getFollowingCount()>0){//跟单人数大于0
    		followSchemes = betScheme.getFollowSchemes();
    		for (BetScheme followSchemePO : followSchemes) {
    			RealFollower r = new RealFollower();
				r.setAfterTaxBonus(followSchemePO.getAfterTaxBonus());
				r.setMultiple(followSchemePO.getMultiple());
				r.setBuyAmount(followSchemePO.getBuyAmount());
				r.setFollowSchemeId(followSchemePO.getId());
				r.setSponsor(followSchemePO.getSponsor());
				r.setSponsorId(followSchemePO.getSponsorId());
				WeiboUser w = userAccountService.findWeiboUserByLotteryUidsNotLoadUser(followSchemePO.getSponsorId()+"");
				if(null != w){
					BeanUtils.copyProperties(w, r);
				}
				realFollowers.add(r);
			}
    	}
    	
    	RealFollowerResult result = new RealFollowerResult();
    	result.setRealFollowers(realFollowers);
    	//将同一赛事不同玩法合并
		betScheme = CombineBetMatchUtil.combine(betScheme);
    	result.setBetScheme(betScheme);
		return result;
	}
	
	public WeiboMsgVO addedInfo(WeiboMsgVO weiboMsgs){
		// 查询微博对应的用户信息
		Map<Long, WeiboUser> usersMap = userAccountService.findWeiboUserByWeiboUids(
				Tool.findUserIds(weiboMsgs));
		weiboMsgs.setUser(usersMap.get(weiboMsgs.getOwnerId()));
		weiboMsgs.setCreateTimeFmt(DateUtil.format(weiboMsgs.getCreateTime()));
		if(weiboMsgs.getSourceWeiboMsg() != null && weiboMsgs.getSourceWeiboMsg().getOwnerId()> 0){
			weiboMsgs.setSourceUser(usersMap.get(weiboMsgs.getSourceWeiboMsg().getOwnerId()));
			weiboMsgs.setSourceCreateTimeFmt(DateUtil.format(weiboMsgs.getSourceWeiboMsg().getCreateTime()));
		}
		loadWeiboSchemeInfo(weiboMsgs);
		loadSourceWeiboSchemeInfo(weiboMsgs);
		weiboMsgs.setLikeUsers(this.findLikeWeiboUser("" + weiboMsgs.getId()));
		return weiboMsgs;
	}
	
	@Override
	protected BaseDao<?> getBaseDao() {
		return messageDao;
	}

	@Override
	public long increaseFavoriateCount(long postId) {
		return messageDao.increaseFavoriateCount(""+postId, 1);
	}

	@Override
	public long increaseFavoriateCountDown(long postId) {
		return messageDao.increaseFavoriateCount(""+postId, -1);
	}

	@Override
	public long increaseShareCount(long postId) {
		return messageDao.increaseShareCount(""+postId);
	}
	
	@Override
	public boolean like(long uid, long pid){
		if(praiseDao.praise(uid, pid+"") == 1){
			messageDao.increaseLikeCount(""+pid, 1);
			messageDao.zadd(new Date().getTime(), Keys.weiboLike(""+pid), ""+uid);
			return true;
		}
		return false;
	}
	
	@Override
	public boolean unLike(long uid, long pid){
		if(praiseDao.unPraise(uid, pid+"") == 1){
			messageDao.increaseLikeCount(""+pid, -1);
			messageDao.zrem(Keys.weiboLike(""+pid), ""+uid);
			return true;
		}
		return false;
	}
	@Override
	public List<WeiboUser> findLikeWeiboUser(String pid){
		Set<String> uids = messageDao.zrange(Keys.weiboLike(pid), 0, -1);
		List<WeiboUser> weiboUsers = new ArrayList<WeiboUser>();
		for (String id : uids) {
			weiboUsers.add(userAccountService.findWeiboUserByWeiboUid(id));
		}
		return weiboUsers;
	}
	@Override
	public WeiboMsgVO getWeiboVoById(long postId) {
		if(postId < 1){
			return new WeiboMsgVO();
		}
		return addedInfo(messageDao.getVO(postId+""));
	}

	@Override
	public Long weiboCount(String weiboUserId) {
		return messageDao.zcard(Keys.myPostKey(weiboUserId));
	}

	@Override
	public long getNewPostCount(long uid, String score, int timeLineOffset) {
		if(StringUtils.isBlank(score)){
			return 0;
		}
		BigDecimal timeLine = new BigDecimal(score);
		double newScore = timeLine.doubleValue();
		double max = System.currentTimeMillis();
		Long count = messageDao.getNewPostCountByScore(Keys.userMainTimeline(""+uid), newScore, max);
		if(count == null){
			count = 0l;
		}
		log.info("查询用户uid={}新微博数量：{} , min={} , max={}", new Object[]{uid, count - timeLineOffset , score , new BigDecimal(max).longValue()});
		return count - timeLineOffset;
	}
	
	@Override
	public long getNewRecPostCount(long uid, String score, int timeLineOffset) {
		if(StringUtils.isBlank(score)){
			return 0;
		}
		BigDecimal timeLine = new BigDecimal(score);
		double newScore = timeLine.doubleValue();
		double max = System.currentTimeMillis();
		Long count = messageDao.getNewPostCountByScore(Keys.userRecommendsKey(""+uid), newScore, max);
		if(count == null){
			count = 0l;
		}
		log.info("查询用户uid={}新推荐微博数量：{} , min={} , max={}", new Object[]{uid, count - timeLineOffset , score , new BigDecimal(max).longValue()});
		return count - timeLineOffset;
	}
	
	@Override
	public long getNewMatchPostCount(long uid, String score, int timeLineOffset) {
		if(StringUtils.isBlank(score)){
			return 0;
		}
		BigDecimal timeLine = new BigDecimal(score);
		double newScore = timeLine.doubleValue();
		double max = System.currentTimeMillis();
		Long count = messageDao.getNewPostCountByScore(Keys.userMatchesKey(""+uid), newScore, max);
		if(count == null){
			count = 0l;
		}
		log.info("查询用户uid={}新赛事微博数量：{} , min={} , max={}", new Object[]{uid, count - timeLineOffset , score , new BigDecimal(max).longValue()});
		return count - timeLineOffset;
	}
	
	@Override
	public long getNewNewsPostCount(long uid, String score, int timeLineOffset) {
		if(StringUtils.isBlank(score)){
			return 0;
		}
		BigDecimal timeLine = new BigDecimal(score);
		double newScore = timeLine.doubleValue();
		double max = System.currentTimeMillis();
		Long count = messageDao.getNewPostCountByScore(Keys.userNewsKey(""+uid), newScore, max);
		if(count == null){
			count = 0l;
		}
		log.info("查询用户uid={}新新闻微博数量：{} , min={} , max={}", new Object[]{uid, count - timeLineOffset , score , new BigDecimal(max).longValue()});
		return count - timeLineOffset;
	}
	@Override
	public long getNewDiscussPostCount(long uid, String score, int timeLineOffset) {
		if(StringUtils.isBlank(score)){
			return 0;
		}
		BigDecimal timeLine = new BigDecimal(score);
		double newScore = timeLine.doubleValue();
		double max = System.currentTimeMillis();
		Long count = messageDao.getNewPostCountByScore(Keys.userDiscussTimeline(""+uid), newScore, max);
		if(count == null){
			count = 0l;
		}
		log.info("查询用户uid={}新讨论微博数量：{} , min={} , max={}", new Object[]{uid, count - timeLineOffset , score , new BigDecimal(max).longValue()});
		return count - timeLineOffset;
	}
	@Override
	public long getNewRealMatchPostTimer(String matchId, String score, int timeLineOffset) {
		if(StringUtils.isBlank(score)){
			return 0;
		}
		BigDecimal timeLine = new BigDecimal(score);
		double newScore = timeLine.doubleValue();
		double max = System.currentTimeMillis();
		Long count = messageDao.getNewPostCountByScore(Keys.matchRealDataKey(matchId), newScore, max);
		if(count == null){
			count = 0l;
		}
		return count - timeLineOffset;
	}
	@Override
	public long getNewMatchDiscussTimer(String matchId, String score, int timeLineOffset) {
		if(StringUtils.isBlank(score)){
			return 0;
		}
		BigDecimal timeLine = new BigDecimal(score);
		double newScore = timeLine.doubleValue();
		double max = System.currentTimeMillis();
		Long count = messageDao.getNewPostCountByScore(Keys.matchDiscussKey(matchId), newScore, max);
		if(count == null){
			count = 0l;
		}
		return count - timeLineOffset;
	}

	@Override
	public WeiboMsgVO publish(long authorId, String title, String content) {
		WeiboMsg msg = new WeiboMsg();
		msg.setOwnerId(authorId);
		msg.setTitle(title);
		msg.setContent(content);
		msg.setCreateTime(System.currentTimeMillis());
		return publish(msg);
	}

	@Override
	public void addToMatchTimeline(WeiboMsg weiboMsg) {
		List<String> matchIdList = analyzeMatchIdBaseWeiboMsg(weiboMsg);
		
		boolean isPostGroup = WeiboType.GROUP.getType().equals(weiboMsg.getType()) 
				&& weiboMsg.getPostId() <= 0;
		
		if(null != matchIdList && matchIdList.size() > 0) {
			for(String mId : matchIdList) {
				long postId = weiboMsg.getId();
				long createdTime = weiboMsg.getCreateTime();
				double score = Double.valueOf(String.valueOf(createdTime));
				matchDao.addToMatchGlobalTimeline(mId, postId, score);
				matchDao.addToMatchTimeline(mId, postId, score);
				
				WeiboMsg w = weiboMsg;
				//微博类型为用户自己发的 且不是彩种用户发的，添加到赛事讨论时间线
				if(null != w && StringUtils.isBlank(w.getType()) &&
						!(w.getOwnerId()+"").equals(LotteryUser.JCZQ.getWeiboUserId()) &&
						!(w.getOwnerId()+"").equals(LotteryUser.JCLQ.getWeiboUserId()) &&
						!(w.getOwnerId()+"").equals(LotteryUser.CTZC.getWeiboUserId()) &&
						!(w.getOwnerId()+"").equals(LotteryUser.SSQ.getWeiboUserId()) ){ 
					matchDao.addToMatchDiscussTimeline(mId, postId, score);
				}
				
				if(StringUtils.isNotBlank(weiboMsg.getType())){
					if(WeiboType.SHOW_SCHEME.getType().equals(weiboMsg.getType()) ||
							WeiboType.RECOMMEND.getType().equals(weiboMsg.getType()) ||
							isPostGroup) {
						
						matchDao.addToMatchRealTimeline(mId, postId, score);// 赛事的实单和推荐都放这里面
						matchDao.addToRecomRealTimeline(weiboMsg.getOwnerId(), weiboMsg.getId(), score);
						
						//分析微博中的赛事串，将微博加入到指定彩种推荐时间线
						List<LotteryId> lotteryList = LotteryIdParser.parse(mId);
						if(lotteryList.size() > 0) {
							for(LotteryId lotteryId : lotteryList) {
								matchDao.addToMatchRecommendLotteryTimeline(lotteryId, postId, score);
							}
						}
					}
				}
			}	
		} else {
			if(WeiboType.SHOW_SCHEME.getType().equals(weiboMsg.getType()) ||
					WeiboType.RECOMMEND.getType().equals(weiboMsg.getType()) ||
					isPostGroup) {
				double score = Double.valueOf(String.valueOf(weiboMsg.getCreateTime()));
				matchDao.addToRecomRealTimeline(weiboMsg.getOwnerId(), weiboMsg.getId(), score);
				handlePostFollowers(weiboMsg);
			}
		}
	}

	@Override
	public void addToMatchTimeline(WeiboMsg weiboMsg, String lotteryId,
			List<String> matchIdList) {
		if(null != matchIdList && matchIdList.size() > 0) {
			for(String mId : matchIdList) {
				long postId = weiboMsg.getId();
				long createdTime = weiboMsg.getCreateTime();
				double score = Double.valueOf(String.valueOf(createdTime));
				matchDao.addToMatchTimeline(mId, postId, score);
				
				WeiboMsg w = weiboMsg;
				//微博类型为用户自己发的 且不是彩种用户发的，添加到赛事讨论时间线
				if(null != w && StringUtils.isBlank(w.getType()) &&
						!(w.getOwnerId()+"").equals(LotteryUser.JCZQ.getWeiboUserId()) &&
						!(w.getOwnerId()+"").equals(LotteryUser.JCLQ.getWeiboUserId()) &&
						!(w.getOwnerId()+"").equals(LotteryUser.CTZC.getWeiboUserId()) &&
						!(w.getOwnerId()+"").equals(LotteryUser.BJDC.getWeiboUserId()) &&
						!(w.getOwnerId()+"").equals(LotteryUser.SSQ.getWeiboUserId()) ){ 
					matchDao.addToMatchDiscussTimeline(mId, postId, score);
				}
				
				if(StringUtils.isNotBlank(weiboMsg.getType())){
					if(WeiboType.SHOW_SCHEME.getType().equals(weiboMsg.getType()) ||
							WeiboType.RECOMMEND.getType().equals(weiboMsg.getType())) {
						
						matchDao.addToMatchRealTimeline(mId, postId, score);// 赛事的实单和推荐都放这里面
						matchDao.addToRecomRealTimeline(weiboMsg.getOwnerId(), weiboMsg.getId(), score);
						
						//分析微博中的赛事串，将微博加入到指定彩种推荐时间线
						matchDao.addToMatchRecommendLotteryTimeline(LotteryId.get(lotteryId), postId, score);
						matchDao.addToLotteryRealFollowTimeline(LotteryId.get(lotteryId), postId, score);
						matchDao.addToLotteryRealFollowTimeline(LotteryId.UNKNOWN, postId, score);
					}
				}
			}	
		} else {
			if(WeiboType.SHOW_SCHEME.getType().equals(weiboMsg.getType()) ||
					WeiboType.RECOMMEND.getType().equals(weiboMsg.getType())) {
				double score = Double.valueOf(String.valueOf(weiboMsg.getCreateTime()));
				matchDao.addToRecomRealTimeline(weiboMsg.getOwnerId(), weiboMsg.getId(), score);
				handlePostFollowers(weiboMsg);
			}
		}
	}
	
	@Override
	public PageResult<WeiboMsgVO> findAllMatchPost(long uid, String matchId, String score) {
		log.info("查询赛事matchId={} 的微博。", matchId);
		if(StringUtils.isBlank(matchId)){
			log.error("findAllMatchPost error ! matchId is null!");
			return new PageResult<WeiboMsgVO>(new PageRequest(), new ArrayList<WeiboMsgVO>()); 
		}
		if(uid < 1){
			log.info("未登录用户查看赛事数据页面");
			return list(Keys.matchDataKey(matchId), Long.parseLong(score));
		} else {
			return checkFavoriateAndLike(uid, list(Keys.matchDataKey(matchId), Long.parseLong(score)));
		}
		
	}
	
	@Override
	public PageResult<WeiboMsgVO> findAllRealMatchPost(long uid, String matchId, String score) {
		log.info("查询赛事matchId={} 的实单微博。", matchId);
		if(StringUtils.isBlank(matchId)){
			log.error("findAllRealMatchPost error ! matchId is null!");
			return new PageResult<WeiboMsgVO>(new PageRequest(), new ArrayList<WeiboMsgVO>()); 
		}
		if(uid < 1){
			log.info("未登录用户查看赛事数据页面");
			return list(Keys.matchRealDataKey(matchId), Long.parseLong(score));
		} else {
			return checkFavoriateAndLike(uid, list(Keys.matchRealDataKey(matchId), Long.parseLong(score)));
		}
		
	}

	@Override
	public long getNewMatchPostCount(String matchId, long id, String score,
			int timeLineOffset) {
		if(StringUtils.isBlank(score)){
			return 0;
		}
		BigDecimal timeLine = new BigDecimal(score);
		double newScore = timeLine.doubleValue();
		double max = System.currentTimeMillis();
		Long count = messageDao.getNewPostCountByScore(Keys.matchDataKey(matchId), newScore, max);
		if(count == null){
			count = 0l;
		}
		return count - timeLineOffset;
	}
	
	@Override
	public long getNewRealMatchPostCount(String matchId, long id, String score,
			int timeLineOffset) {
		if(StringUtils.isBlank(score)){
			return 0;
		}
		BigDecimal timeLine = new BigDecimal(score);
		double newScore = timeLine.doubleValue();
		double max = System.currentTimeMillis();
		Long count = messageDao.getNewPostCountByScore(Keys.matchRealDataKey(matchId), newScore, max);
		if(count == null){
			count = 0l;
		}
		return count - timeLineOffset;
	}

	@Override
	public PageResult<WeiboMsgVO> findAllMatchPost(long uid, String matchId,
			PageRequest pageRequest) {
		log.info("查询赛事matchId={} 的微博。", matchId);
		if(StringUtils.isBlank(matchId)){
			log.error("findAllMatchPost error ! matchId is null!");
			return new PageResult<WeiboMsgVO>(new PageRequest(), new ArrayList<WeiboMsgVO>()); 
		}
		if(uid < 1){
			log.info("未登录用户查看赛事数据页面");
			return list(Keys.matchDataKey(matchId), pageRequest);
		} else {
			return checkFavoriateAndLike(uid, list(Keys.matchDataKey(matchId), pageRequest));
		}
	}
	@Override
	public PageResult<WeiboMsgVO> findAllMatchDiscussPost(long uid, String matchId,
			PageRequest pageRequest) {
		log.info("查询赛事讨论微博，matchId={}", matchId);
		if(StringUtils.isBlank(matchId)){
			log.error("findAllMatchDiscussPost error ! matchId is null!");
			return new PageResult<WeiboMsgVO>(new PageRequest(), new ArrayList<WeiboMsgVO>()); 
		}
		if(uid < 1){
			return list(Keys.matchDiscussKey(matchId), pageRequest);
		} else {
			return checkFavoriateAndLike(uid, list(Keys.matchDataKey(matchId), pageRequest));
		}
	}
	
	@Override
	public PageResult<WeiboMsgVO> findAllRealMatchPost(long uid, String matchId,
			PageRequest pageRequest) {
		log.info("查询赛事matchId={} 的实单微博。", matchId);
		if(StringUtils.isBlank(matchId)){
			log.error("findAllMatchPost error ! matchId is null!");
			return new PageResult<WeiboMsgVO>(new PageRequest(), new ArrayList<WeiboMsgVO>()); 
		}
		if(uid < 1){
			log.info("未登录用户查看赛事数据页面");
			return list(Keys.matchRealDataKey(matchId), pageRequest);
		} else {
			return checkFavoriateAndLike(uid, list(Keys.matchRealDataKey(matchId), pageRequest));
		}
	}
	
	//--------------------------- 微博新消息 开始----------------------------
	@Override
	public PageResult<WeiboMsgVO> findNewPost(long uid, long timeline) {
		if(uid < 1){
			log.error("findNewPost error ! uid is wrong! uid = {}", uid);
			return new PageResult<WeiboMsgVO>(new PageRequest(), new ArrayList<WeiboMsgVO>());
		}
		return  checkFavoriateAndLike(uid, listForNewMsg(Keys.userMainTimeline(""+uid), timeline));
	}

	@Override
	public PageResult<WeiboMsgVO> findNewRecommends(long uid, long timeline) {
		if(uid < 1){
			log.error("findNewPost error ! uid is wrong! uid = {}", uid);
			return new PageResult<WeiboMsgVO>(new PageRequest(), new ArrayList<WeiboMsgVO>());
		}
		return  checkFavoriateAndLike(uid, listForNewMsg(Keys.userRecommendsKey(uid+""), timeline));
	}

	@Override
	public PageResult<WeiboMsgVO> findNewMatchs(long uid, long timeline) {
		if(uid < 1){
			log.error("findNewPost error ! uid is wrong! uid = {}", uid);
			return new PageResult<WeiboMsgVO>(new PageRequest(), new ArrayList<WeiboMsgVO>());
		}
		return  checkFavoriateAndLike(uid, listForNewMsg(Keys.userMatchesKey(uid+""), timeline));
	}

	@Override
	public PageResult<WeiboMsgVO> findNewNews(long uid, long timeline) {
		if(uid < 1){
			log.error("findNewPost error ! uid is wrong! uid = {}", uid);
			return new PageResult<WeiboMsgVO>(new PageRequest(), new ArrayList<WeiboMsgVO>());
		}
		return  checkFavoriateAndLike(uid, listForNewMsg(Keys.userNewsKey(uid+""), timeline));
	}
	@Override
	public PageResult<WeiboMsgVO> findNewDiscuss(long uid, long timeline) {
		if(uid < 1){
			log.error("findNewDiscuss error ! uid is wrong! uid = {}", uid);
			return new PageResult<WeiboMsgVO>(new PageRequest(), new ArrayList<WeiboMsgVO>());
		}
		return  checkFavoriateAndLike(uid, listForNewMsg(Keys.userDiscussTimeline(uid+""), timeline));
	}

	@Override
	public PageResult<WeiboMsgVO> findNewMatchsNews(long uid,String matchId, long timeline) {
		if(StringUtils.isBlank(matchId)){
			log.error("查询新的赛事新闻微博出错， matchId is null!");
			return new PageResult<WeiboMsgVO>(new PageRequest(), new ArrayList<WeiboMsgVO>());
		}
		return  checkFavoriateAndLike(uid, listForNewMsg(Keys.getTeamNewsTimelineKey(matchId), timeline));
	}
	@Override
	public PageResult<WeiboMsgVO> findNewWeiboByKey(long uid,String matchId, long timeline,String key) {
		if(StringUtils.isBlank(matchId)){
			log.error("查询新的赛事新闻微博出错， matchId is null!");
			return new PageResult<WeiboMsgVO>(new PageRequest(), new ArrayList<WeiboMsgVO>());
		}
		return  checkFavoriateAndLike(uid, listForNewMsg(key, timeline));
	}
	
	private PageResult<WeiboMsgVO> listForNewMsg(String key, long score){
		Set<String> result = messageDao.zrangeByScore(key, score + "", System.currentTimeMillis() + "");
		String[] keys = new String[result.size()];
		String[] results = result.toArray(new String[result.size()]);
		Arrays.sort(results, Collections.reverseOrder());
		int i = 0;
		for(String id : results){
			keys[i] = Keys.postKey(id);
			i++;
		}
		return new PageResult<WeiboMsgVO>(new PageRequest(), list(keys));
	}
	//--------------------------- 微博新消息 结束----------------------------

	@Override
	public void addShowSchemeToWeiboTimeline(long schemeId, long postId) {
		if(schemeId <= 0 || postId <= 0) {
			log.error("增加晒单方案ID={}, 微博ID={}的索引失败.", schemeId, postId);
		} else {
			messageDao.addShowSchemeToWeiboTimeline(schemeId+"", postId+"");
		}
	}
	
	@Override
	public void addGroupBuySchemeToWeiboTimeline(long betRecordId, long postId) {
		if(betRecordId <= 0 || postId <= 0) {
			log.error("增加合买方案ID={}, 微博ID={}的索引失败.", betRecordId, postId);
		} else {
			messageDao.addGroupBuySchemeToWeiboTimeline(betRecordId+"", postId+"");
		}
	}
	
	@Override
	public void addRecommendSchemeToWeiboTimeline(long schemeId, long postId) {
		if(schemeId <= 0 || postId <= 0) {
			log.error("增加推荐方案ID={}, 微博ID={}的索引失败.", schemeId, postId);
		} else {
			messageDao.addRecommendToWeiboTimeline(schemeId+"", postId+"");
		}
	}
	
	@Override
	public long getWeiboIdByShowSchemeId(long schemeId) {
		long postId = -1;
		if(schemeId <= 0) {
			
		} else {
			String postIdStr = messageDao.getWeiboIdByShowSchemeId(schemeId+"");
			if(StringUtils.isNotBlank(postIdStr)) {
				postId = Long.valueOf(postIdStr);
			}
		}
		return postId;
	}
	
	@Override
	public long getWeiboIdByGroupBuyRecordId(long betRecordId) {
		long postId = -1;
		if(betRecordId > 0) {
			String postIdStr = messageDao.getWeiboIdByGroupBuyRecordId(betRecordId+"");
			if(StringUtils.isNotBlank(postIdStr)) {
				postId = Long.valueOf(postIdStr);
			}
		}
		return postId;
	}

	@Override
	public void addWeiboTag(WeiboMsg weiboMsg) {
		log.debug("准备给微博ID={},加标签。", weiboMsg.getId());
		String type = weiboMsg.getType();
		if(WeiboType.SHOW_SCHEME.getType().equals(type) ||
				WeiboType.RECOMMEND.getType().equals(type) ||
				WeiboType.GROUP.getType().equals(type) ||
				WeiboType.FOLLOW.getType().equals(type)) {
			
			List<WeiboTag> tagList = parseToTag(weiboMsg);
			for(WeiboTag tag : tagList) {
				if(null != tag && tag.getId() > 0) {
					messageDao.zadd(Double.valueOf(tag.getIndex()).doubleValue(), 
							Keys.WeiboTags(weiboMsg.getId()), tag.getId()+"");
				}
			}
		}
	}
	
	protected List<WeiboTag> parseToTag(WeiboMsg weiboMsg) {
		List<WeiboTag> tagList = new ArrayList<WeiboTag>();
		String weiboType = weiboMsg.getType();
		long schemeId = weiboMsg.getSchameId();
		long betRecordId = weiboMsg.getBetRecordId();
		if(WeiboType.GROUP.getType().equals(weiboType) &&
			weiboMsg.getPostId() > 0) {
			WeiboMsgVO msgVO = messageDao.getVO(weiboMsg.getPostId() + "");
			if(msgVO != null && msgVO.getTags() != null) {
				List<WeiboTag> tags = msgVO.getTags();
				for(WeiboTag tag : tags) {
					if(!tag.isFinalStatus() && tag.getSchemeId() > 0 &&
							tag.getName().equals(SpecialTag.PROGRESS.getTagName())) {
						BetSchemeData betSchemeData = betSchemeClient.getSchemeById(tag.getSchemeId());
						tagService.updateGroupProgress(tag.getId(), betSchemeData.getTotalAmount(), 
								betSchemeData.getPurchasedAmount());
						break;
					}
				}
			}
		}
		if(schemeId > 0 || betRecordId > 0) {
			BetSchemeData betSchemeData = null;
			String lotteryId = "";
			BetPartner betPartner = new BetPartner();
			if(WeiboType.GROUP.getType().equals(weiboType)) {
				betPartner = betpartnerService.findById(betRecordId);
				lotteryId = betPartner.getBetScheme().getLotteryId();
			} else if(WeiboType.SHOW_SCHEME.getType().equals(weiboType) ||
				WeiboType.FOLLOW.getType().equals(weiboType)) {
				betSchemeData = betSchemeClient.getSchemeById(schemeId);
				lotteryId = betSchemeData.getLotteryId();
			} else if(WeiboType.RECOMMEND.getType().equals(weiboType)) {
				betSchemeData = recoSchemeClient.viewScheme(schemeId);
				lotteryId = betSchemeData.getLotteryId();
			}
			log.info("通过微博关联到的方案信息给微博加标签！" +
					"微博ID={}, 微博类型={}, 方案ID={}, betRecordId={}, 取到的方案信息={}", 
					new Object[]{weiboMsg.getId(), WeiboType.getByValue(weiboType), 
				weiboMsg.getSchameId(), betSchemeData
			});
			
			//彩种标签
			WeiboTag lotteryTag = tagService.createTagByLottery(lotteryId);
			tagList.add(lotteryTag);
			
			//方案类型标签
			WeiboTag schemeTypeTag = tagService.createTagByWeiboType(weiboMsg.getType());
			tagList.add(schemeTypeTag);
			
			//方案状态标签
			if(null != betSchemeData && betSchemeData.getSchemeId() > 0) {
				BetScheme bs = new BetScheme();
				if(WeiboType.RECOMMEND.getType().equals(weiboType)) {
					bs.setId(betSchemeData.getSchemeId());
					betPartner.setBetScheme(bs);
				} else {
					BetSchemePO po = schemeService.getScheme(schemeId);
					BeanUtils.copyProperties(po, bs);
					betPartner.setBetScheme(bs);
				}
			}
			if(betPartner.getBetScheme() != null || betPartner.getId() > 0) {
				WeiboTag schemeStatusTag = tagService.createTagBySchemeStatus(weiboType, betPartner);
				if(null != schemeStatusTag) {
					tagList.add(schemeStatusTag);
				}
			}
		}
		return tagList;
	}
	
	@Override
	public String showSchemeWithWeiboAddress(Long schemeId) {
		String weiboIdStr = messageDao.getWeiboIdByShowSchemeId(schemeId + "");
		if(StringUtils.isNotBlank(weiboIdStr)) {
			BetSchemePO po = schemeService.getScheme(schemeId);
			if(null != po && po.getId() > 0 && po.getSponsorId() > 0) {
				String weiboUserId = userAccountService.getWeiboUidByLotteryUid(po.getSponsorId() + "");
				if(StringUtils.isNotBlank(weiboUserId)) {
					return weiboUserId + "/" + weiboIdStr;
				} else {
					log.error("通过方案ID={}，找到方案方案发起人ID={}，不是微博用户", schemeId, po.getSponsorId());
				}
			} else {
				log.error("无法通过方案ID={}，找到对应方案信息", schemeId);
			}
		} else {
			log.error("无法通过方案ID={}，获取对应微博ID", schemeId);
		}
		return "";
	}
}
