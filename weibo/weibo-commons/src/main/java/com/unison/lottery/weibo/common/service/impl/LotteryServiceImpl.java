package com.unison.lottery.weibo.common.service.impl;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import redis.clients.jedis.Tuple;

import com.unison.lottery.weibo.common.data.TimeLineType;
import com.unison.lottery.weibo.common.nosql.BaseDao;
import com.unison.lottery.weibo.common.nosql.LotteryDao;
import com.unison.lottery.weibo.common.nosql.MessageDao;
import com.unison.lottery.weibo.common.nosql.impl.Keys;
import com.unison.lottery.weibo.common.redis.RedisConstant;
import com.unison.lottery.weibo.common.service.AsyncService;
import com.unison.lottery.weibo.common.service.LotteryService;
import com.unison.lottery.weibo.common.service.SchemeService;
import com.unison.lottery.weibo.common.service.TagService;
import com.unison.lottery.weibo.data.LotteryLetter;
import com.unison.lottery.weibo.data.PageRequest;
import com.unison.lottery.weibo.data.PageResult;
import com.unison.lottery.weibo.data.RecentDateType;
import com.unison.lottery.weibo.data.SpecialUser;
import com.unison.lottery.weibo.data.SpecialUserBean;
import com.unison.lottery.weibo.data.WeiboMsg;
import com.unison.lottery.weibo.data.WeiboTag;
import com.unison.lottery.weibo.data.WeiboUser;
import com.unison.lottery.weibo.data.WinningNew;
import com.unison.lottery.weibo.exception.ServiceException;
import com.unison.lottery.weibo.lang.Constant;
import com.unison.lottery.weibo.lang.WeiboType;
import com.unison.lottery.weibo.utils.NumberUtils;
import com.xhcms.lottery.commons.data.BetPartner;
import com.xhcms.lottery.commons.data.BetScheme;
import com.xhcms.lottery.commons.persist.dao.BBMatchDao;
import com.xhcms.lottery.commons.persist.dao.BJDCMatchDao;
import com.xhcms.lottery.commons.persist.dao.CTFBMatchDao;
import com.xhcms.lottery.commons.persist.dao.FBMatchDao;
import com.xhcms.lottery.commons.persist.entity.BetSchemePO;
import com.xhcms.lottery.commons.persist.entity.CTFBMatchPO;
import com.xhcms.lottery.lang.Constants;
import com.xhcms.lottery.lang.EntityStatus;
import com.xhcms.lottery.lang.EntityType;
import com.xhcms.lottery.lang.LotteryId;
import com.xhcms.lottery.lang.PlayType;

/**
 * 彩种相关服务.
 * 
 * @author Yang Bo
 */
@Service
public class LotteryServiceImpl extends BaseServiceImpl implements LotteryService {

	private Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private LotteryDao lotteryDao;
	
	@Autowired
	private AsyncService asyncService;
	
	@Autowired
	private SchemeService schemeService;
	
	@Autowired
	private BJDCMatchDao bjdcMatchDao;
	
	@Autowired
	private FBMatchDao fbMatchDao;

	@Autowired
	private BBMatchDao bbMatchDao;

	@Autowired
	private CTFBMatchDao ctfbMatchDao;
	
	@Autowired
	private MessageDao messageDao;
	
	@Autowired
	private TagService tagService;
	
	@Override
	protected BaseDao<?> getBaseDao() {
		return lotteryDao;
	}
	private Pattern matchPattern = Pattern.compile("\\$.*?\\(([a-zA-Z]+)\\d+\\)\\$");

	public String beforePostForLotteryUser(WeiboMsg weiboMsg, boolean isSpecialUser) {
		if (isSpecialUser) {
			// 去掉 "#xxx#"串
			Pattern markPattern = Pattern.compile("(#.*?#)");
			Matcher matcher = markPattern.matcher(weiboMsg.getContent());
			if (matcher.find()){
				String mark = matcher.group(1);
				String content = weiboMsg.getContent();
				if (content.length()>0){
					String removed = content.replace(mark, "");
					weiboMsg.setContent(removed);
				}
				return mark;
			}
		}
		return "";
	}

	public void afterPostForLotteryUser(WeiboMsg weiboMsg, boolean isSpecialUser,
			String markString) {
		// 扫描赛事
		scanMatches(weiboMsg);
		if (isSpecialUser){
			// 推荐
			if (forwardRecommends(weiboMsg, markString))
				return;
			// 新闻
			if (forwardNews(weiboMsg, markString))
				return;
			// 公告
			if (postAnnouncement(weiboMsg, markString))
				return;
			// 中奖喜报
			postWinningNews(weiboMsg, markString);
		}
	}

	@Override
	public boolean scanMatches( WeiboMsg weibo ) {
		String content = weibo.getContent();
		Matcher matcher = matchPattern.matcher(content);
		boolean findMatches = false;
		Set<Long> lotteryUidSet = new HashSet<>();
		while( matcher.find() ) {
			String lotteryLetters = matcher.group(1);
			LotteryLetter letterEnum = LotteryLetter.fromString(lotteryLetters);
			if (letterEnum == LotteryLetter.UNKNOWN){
				logger.debug("未知彩种字母串：{}", matcher.group(0));
				continue;
			}
			// 添加到彩种的比赛时间线中
			LotteryId matchLotteryId = letterEnum.getLotteryId();
			// 从彩种取彩种用户uid
			long lotteryUid = lotteryUserOf(matchLotteryId);
			if (lotteryUid == 0){
				logger.error("没有定义 LotteryUser，Lottery：{}", matchLotteryId);
				continue;
			}
			lotteryUidSet.add(lotteryUid);
			// 添加到彩种用户的比赛时间线
			String key = Keys.userMatchesKey(lotteryUid+"");
			getBaseDao().zadd(weibo.getCreateTime(), key, ""+weibo.getId());
			findMatches = true;
		}
		if (findMatches) {
			changeWeiboOwnerAndNotify(weibo, lotteryUidSet);
		}
		return findMatches;
	}

	/**
	 * 修改微博作者为“彩种用户id”，以便异步的通知“彩种用户的粉丝”。
	 * 参考：AsyncPublishHandler
	 */
	private void changeWeiboOwnerAndNotify(WeiboMsg weibo,
			Set<Long> lotteryUidSet) {
		WeiboMsg weiboCopy = new WeiboMsg();
		BeanUtils.copyProperties(weibo, weiboCopy);
		for (Long lotteryUid : lotteryUidSet){
			weiboCopy.setOwnerId(lotteryUid);
			asyncService.notifyFollowers(weiboCopy, TimeLineType.MATCHES);
		}
	}

	@Override
	public boolean forwardRecommends(WeiboMsg weibo, String mark) {
		if (mark.equals(Constant.Mark.RECOMMEND)){
			// 把微博id加入 uid:<uid>:recommends
			String key = Keys.userRecommendsKey(weibo.getOwnerId()+"");
			lotteryDao.zadd(weibo.getCreateTime(), key, weibo.getId()+"");
			
			asyncService.notifyFollowers(weibo, TimeLineType.RECOMMENDS);
			return true;
		}
		return false;
	}

	@Override
	public boolean forwardNews(WeiboMsg weibo, String mark) {
		if (mark.equals(Constant.Mark.NEWS)){
			// 把微博id加入 uid:<uid>:news
			String key = Keys.userNewsKey(weibo.getOwnerId()+"");
			lotteryDao.zadd(weibo.getCreateTime(), key, weibo.getId()+"");
			asyncService.notifyFollowers(weibo, TimeLineType.NEWS);
			return true;
		}
		return false;
	}

	@Override
	public boolean postAnnouncement(WeiboMsg weibo, String mark) {
		if (mark.equals(Constant.Mark.ANNOUNCEMENT)){
			if(null != weibo && weibo.getId() > 0) {
				lotteryDao.zadd(weibo.getCreateTime(), Keys.ANNOUNCE, weibo.getId()+"");
			} else {
				logger.error("无效微博={},不能生成公告", weibo);
			}
			return true;
		}
		return false;
	}

	@Override
	public void postWinningNews(WeiboMsg weibo, String mark) {
		if(null != weibo && weibo.getId() > 0) {
			long schemeId = isWinningNew(mark);
			if(schemeId > 0) {
				//存储中奖喜报，保存中奖喜报对象并创建索引
				//1.获取方案信息 //2.增加喜报对象 //3.创建喜报列表索引
				BetSchemePO scheme = schemeService.getScheme(schemeId);
				if(null != scheme) {
					boolean result = createWinningNew(scheme, weibo);
					if(!result) {
						return;
					}
					Map<String, String> hash = new HashMap<String, String>();
					hash.put("type", WeiboType.WINNING.getType());
					hash.put("schameId", schemeId+"");
					messageDao.updateWeiboType(weibo.getId()+"", hash);
					
					String schemeType = null;
					if(scheme.getType() == EntityType.BETTING_ALONE ||
							scheme.getType() == EntityType.BETTING_FOLLOW) {
						schemeType = WeiboType.SHOW_SCHEME.getType();
					} else if(scheme.getType() == EntityType.BETTING_PARTNER) {
						schemeType = WeiboType.GROUP.getType();
					}
					if(null != schemeType) {
						WeiboTag lotteryTag = tagService.createTagByLottery(scheme.getLotteryId());
						WeiboTag weiboTypeTag = tagService.createTagByWeiboType(schemeType);
						BetPartner betPartner = new BetPartner();
						BetScheme bs = new BetScheme();
						BeanUtils.copyProperties(scheme, bs);
						betPartner.setBetScheme(bs);
						WeiboTag schemeStatusTag = tagService.createTagBySchemeStatus(WeiboType.WINNING.getType(),betPartner);
						messageDao.zadd(lotteryTag.getIndex(), Keys.WeiboTags(weibo.getId()), lotteryTag.getId()+"");
						messageDao.zadd(weiboTypeTag.getIndex(), Keys.WeiboTags(weibo.getId()), weiboTypeTag.getId()+"");
						messageDao.zadd(schemeStatusTag.getIndex(), Keys.WeiboTags(weibo.getId()), schemeStatusTag.getId()+"");
					} else {
						logger.info("方案={}, 类型={}, 不符合喜报规则", 
								scheme, scheme.getType());
					}
				}
			} 
		} else {
			logger.error("无效微博={},不能生成中奖喜报", weibo);
		}
	}
	
	@Override
	public long isWinningNew(String markString) {
		long schemeId = 0;
		if(StringUtils.isNotBlank(markString)) {
			String winningNewPattern = "((\\#中奖喜报)\\((\\d+)\\)#)";
			Pattern pattern = Pattern.compile(winningNewPattern);
			Matcher matcher = pattern.matcher(markString);
			if (matcher.find()) {
				schemeId = Long.parseLong(matcher.group(3));
				logger.info("解析出的中奖喜报方案ID={}", schemeId);
			}
		}
		return schemeId;
	}
	
	@Override
	public boolean createWinningNew(BetSchemePO scheme, WeiboMsg weibo) {
		boolean createSucc = false;
		if(null == scheme) {
			logger.info("无法通过方案，获取方案信息",
					new Object[]{scheme});
			return createSucc;
		}
		if(checkWinningScheme(scheme)) {
			long showSchemeWeiboId = 0;
			String showWeiboIdStr = messageDao.getWeiboIdByShowSchemeId(scheme.getId()+"");
			if(StringUtils.isNotBlank(showWeiboIdStr)) {
				showSchemeWeiboId = Long.parseLong(showWeiboIdStr);
			} else {
				logger.info("未找到中奖喜报微博={}, 对应的晒单微博ID", weibo);
			}
			WinningNew win = initWinningByScheme(scheme, showSchemeWeiboId);
			if(win.getId() > 0) {
				createSucc = true;
			}
		} else {
			logger.info("需要生成的中奖喜报,对应方案ID={}，不符中奖合喜报规则",
					new Object[]{scheme.getId()});
		}
		return createSucc;
	}
	
	/**
	 * 根据方案，初始化一个中奖喜报对象
	 * @param scheme	方案PO
	 * @param showSchemeWeiboId	对于使用手动发喜报的方式,就是方案对应的微博ID；对于自动发喜报就不需要
	 * 	自动发喜报目前显示的就是中奖方案对应微博
	 * @return
	 */
	private WinningNew initWinningByScheme(BetSchemePO scheme, long showSchemeWeiboId) {
		long weiboId = 0;
		String weiboIdStr = lotteryDao.getString(Keys.showSchemeToWeibo(scheme.getId()+""));
		if(StringUtils.isNotBlank(weiboIdStr)) {
			weiboId = Long.valueOf(weiboIdStr);
		}
		
		BigDecimal afterTaxBonus = scheme.getAfterTaxBonus();
		String lotteryId = scheme.getLotteryId();
		WinningNew win = new WinningNew();
		if(weiboId <= 0) {
			logger.info("根据中奖方案ID={},找不到对应微博,方案详情={}", scheme.getId(), scheme);
			return win;
		}
		String playName=PlayType.getPlayNameByPlayId(scheme.getPlayId());
		long ltUserId = scheme.getSponsorId();
		long weiboUserId = 0;
		try {
			String weiboUserIdStr = lotteryDao.getString(Keys.getLotteryUserIdKey(ltUserId));
			if(StringUtils.isNotBlank(weiboUserIdStr)) {
				weiboUserId = Long.valueOf(weiboUserIdStr);
			}
		} catch (Exception e) {
			logger.error("中奖喜报，根据方案发起人ID={}，查找微博用户={}, error={}", 
					new Object[]{ltUserId, weiboUserId, e});
		}
		
		if(weiboUserId > 0) {
			Date createdTime = scheme.getAwardTime();
			
			win.setUserId(weiboUserId);
			win.setLotteryId(lotteryId);
			win.setBonus(afterTaxBonus);
			win.setWeiboId(weiboId);
			win.setShowWeiboId(weiboId);
			win.setSchemeId(scheme.getId());
			win.setCreateTime(createdTime);
			win.setPlayName(playName);//玩法中文名
			long winId = weiboId;
			win.setId(winId);
			logger.info("创建中奖喜报对象={}", win);
			
			lotteryDao.hashAdd(Keys.winKey(winId+""), win);
			//加入到全局中奖喜报时间线
			lotteryDao.zadd(createdTime.getTime(), Keys.WINNINGNEWS, winId+"");
			
			//加入到用户自己的中奖时间线
			lotteryDao.zadd(createdTime.getTime(), Keys.userWinning(weiboUserId+""), winId+"");
		} else {
			logger.error("准备生成中奖喜报，根据方案发起人ID={}，找不到对应微博用户！", 
					new Object[]{ltUserId});
		}
		return win;
	}
	
	@Override
	public boolean autoSendWinning(Long schemeId) {
		BetSchemePO scheme = schemeService.getScheme(schemeId);
		if(checkWinningScheme(scheme)) {
			WinningNew win = initWinningByScheme(scheme, 0L);
			if(win.getId() > 0) return true;
		}
		return false;
	}
	
	protected boolean checkWinningScheme(BetSchemePO scheme) {
		if(null != scheme && (scheme.getStatus() == EntityStatus.TICKET_NOT_AWARD
				|| scheme.getStatus() == EntityStatus.TICKET_AWARDED)
				&& (scheme.getShowScheme() == Constants.SHOW_SCHEME || 
				scheme.getType() == Constants.TYPE_GROUP) ||
				scheme.getType() == Constants.TYPE_FOLLOW) {
			return true;
		}
		return false;
	}
	
	@Override
	public boolean isSpecialUser(String weiboUserId, SpecialUser specialUserType) {
		switch(specialUserType){
		case LOTTERY_USER:
			return getBaseDao().sismember(Keys.SPECIAL_USERS_LOTTERY, ""+weiboUserId);
		default:
			logger.error("不支持的特殊用户类型：{}", specialUserType);
			break;
		}
		return false;
	}

	@Override
	public void addLotteryUser(String weiboUserId, LotteryId lotteryId) {
		lotteryDao.set(Keys.uidLotteryId(""+weiboUserId), lotteryId.name());
		lotteryDao.set(Keys.lotteryIdUid(lotteryId.name()), ""+weiboUserId);
		lotteryDao.sadd(Keys.SPECIAL_USERS_LOTTERY, ""+weiboUserId);
	}
	
	@Override
	public boolean deleteLotteryUser(String weiboUserId) {
		String lotteryId = lotteryDao.getString(Keys.uidLotteryId(""+weiboUserId));
		long delCount = lotteryDao.delete(Keys.uidLotteryId(""+weiboUserId));
		lotteryDao.delete(Keys.lotteryIdUid(lotteryId));
		lotteryDao.srem(Keys.SPECIAL_USERS_LOTTERY, ""+weiboUserId);
		return delCount > 0;
	}

	@Override
	public LotteryId lotteryIdOfSpecialUser(String weiboUserId) {
		String lottery = lotteryDao.getString(Keys.uidLotteryId(""+weiboUserId));
		if (StringUtils.isNotBlank(lottery)){
			return LotteryId.valueOf(lottery);
		}else{
			return LotteryId.UNKNOWN;
		}
	}

	@Override
	public long lotteryUserOf(LotteryId lotteryId) {
		String uid = lotteryDao.getString(Keys.lotteryIdUid(lotteryId.name()));
		if (uid!=null){
			return Long.parseLong(uid);
		}
		return 0;
	}

	@Override
	public void addWeiboToTimelines(WeiboMsg weibo, String flwrId,
			Set<TimeLineType> destTimelines) {
		long weiboId = weibo.getId();
		double score = weibo.getCreateTime();
		for (TimeLineType tt : destTimelines) {
			String key = timelineKey(flwrId, tt);
			String value = weiboId+"";
			lotteryDao.zadd(score, key, value);
		}
	}

	private String timelineKey(String userId, TimeLineType timeline) {
		switch(timeline) {
		case RECOMMENDS:
			return Keys.userRecommendsKey(userId);
		case MATCHES:
			return Keys.userMatchesKey(userId);
		case NEWS:
			return Keys.userNewsKey(userId);
		default:
			throw new ServiceException("不支持的时间线类型：" + timeline);
		}
	}

	@Override
	public PageResult<SpecialUserBean> listAllSpecialUsers(PageRequest pageRequest) {
		Set<String> users = lotteryDao.smembers(Keys.SPECIAL_USERS_LOTTERY);
		List<SpecialUserBean> spUserList = new LinkedList<>();
		for (String userId : users) {
			WeiboUser user = lotteryDao.get(WeiboUser.class, userId);
			LotteryId lotteryId = lotteryIdOfSpecialUser(userId);
			
			SpecialUserBean spUser = new SpecialUserBean();
			spUser.setUser(user);
			spUser.setLotteryId(lotteryId);
			spUserList.add(spUser);
		}
		PageResult<SpecialUserBean> result = new PageResult<SpecialUserBean>();
		result.setResults(spUserList);
		return result;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Set<String> lotteryUserRecommend(String weiboUserId,
			RecentDateType recentDateType) {
		String userRecommendsKey = Keys.userRecommendsKey(weiboUserId);
		Set<String> recommendSets = Collections.EMPTY_SET;
		if(StringUtils.isNotBlank(userRecommendsKey) && null != recentDateType) {
			long now = System.currentTimeMillis();
			long max = now;
			long min = now - recentDateType.getTime();
			Long count = lotteryDao.zcount(userRecommendsKey, min, max);
			recommendSets = lotteryDao.zrevrangeByScoreLimt(userRecommendsKey, max+"", 
					min+"", 0, count.intValue());
		}
		return recommendSets;
	}

	@Override
	public void followingLottery(String followerUid, String lotteryUserId) {
		// 注意：“彩种用户”A关注另一个“彩种用户”B时，A不能拷贝B的“推荐、赛事、新闻”时间线。
		if (isSpecialUser(followerUid, SpecialUser.LOTTERY_USER)){
			logger.debug("“彩种用户”A关注另一个“彩种用户”B时，A不能拷贝B的“推荐、赛事、新闻”时间线。");
			return;
		}
		// 拷贝"推荐"时间线
		copyTimeLine(lotteryUserId, followerUid, TimeLineType.RECOMMENDS);
		// 拷贝"赛事"时间线
		copyTimeLine(lotteryUserId, followerUid, TimeLineType.MATCHES);
		// 拷贝"新闻"时间线
		copyTimeLine(lotteryUserId, followerUid, TimeLineType.NEWS);
	}

	// 对于彩种用户相关时间线，只拷贝最近一个月的微博。
	private void copyTimeLine(String srcUid, String destUid,
			TimeLineType timelineType) {
		// 计算一个月前的时间 score 值
		long aMonthAgo = RecentDateType.MONTH.ago();
		// 拷贝指定时间线的微博id到目标时间线
		String srcKey = "";
		String destKey = "";
		switch(timelineType){
			case RECOMMENDS:
				srcKey = Keys.userRecommendsKey(srcUid);
				destKey = Keys.userRecommendsKey(destUid);
				break;
			case NEWS:
				srcKey = Keys.userNewsKey(srcUid);
				destKey = Keys.userNewsKey(destUid);
				break;
			case MATCHES:
				srcKey = Keys.userMatchesKey(srcUid);
				destKey = Keys.userMatchesKey(destUid);
				break;
			default:
				logger.error("copyTimeLine不支持时间线：{}", timelineType);
				break;
		}
		Set<Tuple> srcTuples = getBaseDao().zrangeByScoreWithScores(
				srcKey, ""+aMonthAgo, RedisConstant.INFINIT);
		logger.debug("准备拷贝{}的{}个元素。", srcKey, srcTuples.size());
		Map<Double, String> scoreMembers = new LinkedHashMap<>();
		Tuple firstTuple = null;
		for (Tuple tuple : srcTuples) {
			if (firstTuple == null){
				firstTuple = tuple;
			}
			scoreMembers.put(tuple.getScore(), tuple.getElement());
		}
		getBaseDao().zadd(destKey, scoreMembers);
		// 记录最旧一条微博的时间戳（即score）到 uid:<uid>:timelineLastPos
		if (firstTuple != null){
			recordLastPos(destUid, firstTuple, timelineType);
		}else{
			logger.debug("firstTuple 为空，不记录last pos!");
		}
	}

	private void recordLastPos(String destUid, Tuple firstTuple, TimeLineType timelineType) {
		String lastPosKey = Keys.timelineLastPos(destUid);
		Map<String, String> hash = new LinkedHashMap<>();
		String fieldName = "";
		switch(timelineType){
		case RECOMMENDS:
			fieldName = "recommends";
			break;
		case MATCHES:
			fieldName = "matches";
			break;
		case NEWS:
			fieldName = "news";
			break;
		default:
			logger.error("recordLastPos不支持时间线：{}", timelineType);
			break;
		}
		hash.put(fieldName, NumberUtils.doubleToLongString(firstTuple.getScore()));
		getBaseDao().hmset(lastPosKey, hash);
	}

	@Override
	public void unFollowingLottery(String followerUid, String lotteryUserId) {
		// 注意：“彩种用户”A关注另一个“彩种用户”B时，A不能拷贝B的“推荐、赛事、新闻”时间线。
		if (isSpecialUser(followerUid, SpecialUser.LOTTERY_USER)){
			logger.debug("“彩种用户”A取消关注另一个“彩种用户”B时，A不用删除B的“推荐、赛事、新闻”时间线。");
			return;
		}
		// 删除"推荐"时间线
		deleteCopiedTimeLine(lotteryUserId, followerUid, TimeLineType.RECOMMENDS);
		// 删除"赛事"时间线
		deleteCopiedTimeLine(lotteryUserId, followerUid, TimeLineType.MATCHES);
		// 删除"新闻"时间线
		deleteCopiedTimeLine(lotteryUserId, followerUid, TimeLineType.NEWS);
	}

	private void deleteCopiedTimeLine(String srcUid, String destUid,
			TimeLineType timelineType) {
		// 读取指定时间线的 last pos，如果没有就全部删除
		String lastPosScore = readLastPos(destUid, timelineType);
		if (lastPosScore == null){
			lastPosScore = RedisConstant.N_INFINIT;	// 读取全部数据
		}
		// 读取源时间线指定位置开始的所有微博id
		String srcKey = null;
		String destKey = null;
		switch(timelineType){
			case RECOMMENDS:
				srcKey = Keys.userRecommendsKey(srcUid);
				destKey = Keys.userRecommendsKey(destUid);
				break;
			case NEWS:
				srcKey = Keys.userNewsKey(srcUid);
				destKey = Keys.userNewsKey(destUid);
				break;
			case MATCHES:
				srcKey = Keys.userMatchesKey(srcUid);
				destKey = Keys.userMatchesKey(destUid);
				break;
			default:
				logger.error("copyTimeLine不支持时间线：{}", timelineType);
				break;
		}
		Set<String> weiboSetToDelete = 
				getBaseDao().zrangeByScore(srcKey, lastPosScore, RedisConstant.INFINIT);
		logger.debug("时间线'{}'要删除{}条微博。", timelineType, weiboSetToDelete.size());
		// 从目的时间线中删除微博
		if (weiboSetToDelete.size()>0){
			String[] members = new String[weiboSetToDelete.size()];
			getBaseDao().zrem(destKey, weiboSetToDelete.toArray(members));
		}else{
			logger.debug("没有需要删除的数据，srcUid={}, destUid={}, 时间线={}", 
					new Object[]{srcUid, destUid, timelineType});
		}
	}

	private String readLastPos(String destUid, TimeLineType timelineType) {
		String fieldName = null;
		switch(timelineType){
		case RECOMMENDS:
			fieldName = "recommends";
			break;
		case MATCHES:
			fieldName = "matches";
			break;
		case NEWS:
			fieldName = "news";
			break;
		default:
			logger.error("readLastPos不支持时间线：{}", timelineType);
			return null;
		}
		String key = Keys.timelineLastPos(destUid);
		return getBaseDao().hget(key, fieldName);
	}
	
	@Override
	public Set<String> findWeiboUserByLottery() {
		Set<String> weiboUserSet = new HashSet<String>();
		List<LotteryId> list = Arrays.asList(LotteryId.values());
		logger.info("系统彩种ID列表:{}", list);
		for(LotteryId lottery : list) {
			String lotteryIdUid = Keys.lotteryIdUid(lottery.name());
			String weiboUserId = lotteryDao.getString(lotteryIdUid);
			if(StringUtils.isNotBlank(weiboUserId)) {
				weiboUserSet.add(weiboUserId);
			}
		}
		logger.info("查询出彩种对应的微博用户ID列表:{}", weiboUserSet);
		return weiboUserSet;
	}

	@Transactional
	@Override
	public Object findMatch(String matchId, String lotteryId) {
		Object matchObject = null;
		if (StringUtils.isNotBlank(matchId)
				&& StringUtils.isNotBlank(lotteryId)) {
			if(lotteryId.equals(LotteryId.JCZQ.name())) {
				matchObject = fbMatchDao.get(Long.parseLong(matchId));
			} else if(lotteryId.equals(LotteryId.BJDC.name())) {
				matchObject = bjdcMatchDao.get(Long.parseLong(matchId));
			} else if(lotteryId.equals(LotteryId.JCLQ.name())) {
				matchObject = bbMatchDao.get(Long.parseLong(matchId));
			} else if(lotteryId.equals(LotteryId.CTZC.name())) {
				matchObject = ctfbMatchDao.get(matchId);
			}
		}
		if(null == matchObject) {
			logger.info("无法通过大V彩赛事ID={}, 彩种ID={},获取比赛信息...", 
					new Object[]{matchId, lotteryId});
		}
		return matchObject;
	}

	@Transactional
	@Override
	public String getMatchContent(String matchId, String lotteryId) {
		StringBuffer matchBuf = new StringBuffer();
		if (StringUtils.isNotBlank(matchId)
				&& StringUtils.isNotBlank(lotteryId)) {
			Object matchObject = this.findMatch(matchId, lotteryId);
			if (null != matchObject) {
				if (lotteryId.equals(LotteryId.JCZQ.name())) {
					matchBuf.append("JZ").append(matchId.substring(2));
				} else if (lotteryId.equals(LotteryId.JCLQ.name())) {
					matchBuf.append("JL").append(matchId.substring(2));
				} else if (lotteryId.equals(LotteryId.CTZC.name())) {
					CTFBMatchPO matchPO = (CTFBMatchPO) matchObject;
					matchBuf.append("CZ").append(matchPO.getIssueNumber())
							.append(matchPO.getMatchId());
				} else if (lotteryId.equals(LotteryId.BJDC.name())) {
					matchBuf.append("BD").append(matchId.substring(2));
				} 
			}
		}
		return matchBuf.toString();
	}
}
