package com.unison.lottery.weibo.common.nosql.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.Tuple;

import com.unison.lottery.weibo.common.nosql.MessageDao;
import com.unison.lottery.weibo.common.nosql.TagDao;
import com.unison.lottery.weibo.common.redis.RedisCallback;
import com.unison.lottery.weibo.common.redis.RedisTemplate;
import com.unison.lottery.weibo.common.service.IdGenerator;
import com.unison.lottery.weibo.data.RecentDateType;
import com.unison.lottery.weibo.data.WeiboMsg;
import com.unison.lottery.weibo.data.WeiboMsgVO;
import com.unison.lottery.weibo.lang.LotteryUser;

@Repository
public class MessageDaoImpl extends BaseDaoImpl<WeiboMsg> implements MessageDao{
	private Logger log = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private RedisTemplate redisTemplate;
	
	@Autowired
	private IdGenerator idGenertor;
	
	@Autowired
	private TagDao tagDao;
	
	@Override
	public long post(WeiboMsg weiboMsg) {
		long postId = idGenertor.nextId(); //incr(Keys.getGlobalNextPostId());
		weiboMsg.setId(postId);
		hashAdd(Keys.postKey(""+postId), weiboMsg);
		return postId;
	}

	@Override
	public Long delete(String id){
		return super.delete(Keys.postKey(id));
	}

	@Override
	public WeiboMsg get(String id) {
		return hashGet(Keys.postKey(id));
	}

	@Override
	public List<WeiboMsgVO> list(String[] keys) {
		List<WeiboMsgVO> result = hashList(keys, WeiboMsgVO.class);
		if(result == null){
			return new ArrayList<WeiboMsgVO>();
		}
		for(WeiboMsgVO weibovo : result){
			if(weibovo != null && weibovo.getPostId() > 0){
				WeiboMsg sourceWeibo = hashGet(Keys.postKey(""+weibovo.getPostId()));
				if(sourceWeibo == null || sourceWeibo.getId() < 1){
					log.error("sourceWeiboId is wrong ! , id = {} not found !", sourceWeibo.getId());
					continue;
				}
				weibovo.setSourceWeiboMsg(sourceWeibo);
			}
			
			weibovo.setTags(tagDao.getTagListByWeiboId(weibovo.getId()));
		}
		return result;
	}
	
	@Override
	public WeiboMsgVO getVO(String id){
		WeiboMsgVO result = hashGet(Keys.postKey(id), WeiboMsgVO.class);
		if(result == null){
			log.error("Weibo is not found ! , postKey = {}  !", Keys.postKey(id));
			return new WeiboMsgVO();
		}
		if(result.getPostId() > 0){
			WeiboMsg sourceWeibo = hashGet(Keys.postKey(""+result.getPostId()));
			if(sourceWeibo == null || sourceWeibo.getId() < 1){
				log.error("sourceWeiboId is wrong ! , id = {} not found !", sourceWeibo.getId());
				sourceWeibo = new WeiboMsg();
			}
			result.setSourceWeiboMsg(sourceWeibo);
		}
		result.setTags(tagDao.getTagListByWeiboId(result.getId()));
		
		if(StringUtils.isBlank(result.getContent())){
			result.setContent("");
		}
		return result;
	}
	
	@Override
	public long postToMyPosts(final String uId, Double score, final String postId) {
		if(StringUtils.isBlank(uId) || StringUtils.isBlank(postId)){
			log.error("uId or postId is wrong!");
			return 0;
		}
		return zadd(score, Keys.myPostKey(uId), postId);
	}

	@Override
	public long postToAllposts(final String[] uids , final Double score, final String postId) {
		if(uids == null || uids.length == 0 ||  StringUtils.isBlank(postId)){
			log.error("parameters error! uids = {} , postId = {}",java.util.Arrays.toString(uids), postId);
			return 0;
		}
		log.info("push to users , ids ={}", java.util.Arrays.toString(uids).toString());
		return (long) redisTemplate.doExecute(new RedisCallback() {
			public Object doInRedis(Jedis jedis) {
				long count = 0;
				for(String uid : uids){
					String key = Keys.userMainTimeline(uid);
					String value = postId;
					count += jedis.zadd( key, score , value);
					log.info("zadd key={} , value={}", key, value);
				}
				return count;
			}
		});
	}
	@Override
	public long postToDisscussTimeline(final String[] uids , final Double score, final String postId) {
		if(uids == null || uids.length == 0 ||  StringUtils.isBlank(postId)){
			log.error("parameters error! uids = {} , postId = {}",java.util.Arrays.toString(uids), postId);
			return 0;
		}
		log.info("push to users , ids ={}", java.util.Arrays.toString(uids).toString());
		return (long) redisTemplate.doExecute(new RedisCallback() {
			public Object doInRedis(Jedis jedis) {
				long count = 0;
				for(String uid : uids){
					String key = Keys.userDiscussTimeline(uid);
					String value = postId;
					count += jedis.zadd( key, score , value);
					log.info("zadd key={} , value={}", key, value);
				}
				return count;
			}
		});
	}
	@Override
	public long postNewsToAllposts(final String[] uids , final Double score, final String postId) {
		if(uids == null || uids.length == 0 ||  StringUtils.isBlank(postId)){
			log.error("parameters error! uids = {} , postId = {}",java.util.Arrays.toString(uids), postId);
			return 0;
		}
		log.info("push to users , ids ={}", java.util.Arrays.toString(uids).toString());
		return (long) redisTemplate.doExecute(new RedisCallback() {
			public Object doInRedis(Jedis jedis) {
				long count = 0;
				for(String uid : uids){
					String key = Keys.userNewsKey(uid);
					String value = postId;
					count += jedis.zadd( key, score , value);
					log.info("zadd key={} , value={}", key, value);
				}
				return count;
			}
		});
	}
	
	@Override
	public long postRecomRealToFollowers(final List<Long> followerList, final Double score,
			final String postId) {
		if(followerList == null || followerList.size() == 0 ||  StringUtils.isBlank(postId)){
			log.error("parameters error! uids = {} , postId = {}",followerList, postId);
			return 0;
		}
		log.info("push recommends and real to followers, ids ={}", followerList);
		long cnt = 0;
		for(Long uid : followerList) {
			String userSelfKey = Keys.userRecomRealsTimelineKey(uid+"");
			cnt += zadd(score, userSelfKey, postId);
		}
		return cnt;
	}

	@Override
	public Long deleteMyPosts(String id, String postId) {
		if(zrem(Keys.myPostKey(id), postId) == 1){
			log.info("用户uid = {} ,删除MyPosts微博 postId = {} , 成功！",id,postId);
			return 1l;
		}else{
			log.info("用户uid = {} ,删除MyPosts微博 postId = {} , 失败！",id,postId);
			return 0l;
		}
	}

	@Override
	public Long deleteAllposts(String[] ids, String postId) {
		if(ids == null || ids.length==0){
			return 0L;
		}
		long count = 0;
		for(String id : ids){
			String key = Keys.userMainTimeline(id);
			if(zrem(key, String.valueOf(postId)) ==1){
				log.info("用户uid = {} ,删除Allposts微博 postId = {} , 成功！",id,postId);
				count++;
			}else{
				log.info("用户uid = {} ,删除Allposts微博 postId = {} , 失败！",id,postId);
			}
		}
		return count;
	}
	
	@Override
	public Long deleteAllposts(List<Long> ids, String postId) {
		if(ids == null || ids.size() == 0){
			return 0L;
		}
		long count = 0;
		for(long id : ids){
			String key = Keys.userMainTimeline(id+"");
			if(zrem(key, String.valueOf(postId)) ==1){
				log.info("用户uid = {} ,删除Allposts微博 postId = {} , 成功！",id,postId);
				count++;
			}else{
				log.info("用户uid = {} ,删除Allposts微博 postId = {} , 失败！",id,postId);
			}
		}
		return count;
	}

	@Override
	public long increaseCommentCount(String postId, long value) {
		return hincrBy(Keys.postKey(postId), Keys.COMMENT_COUNT, value);
	}
	
	@Override
	public long increaseForwardCount(String postId) {
		return hincrBy(Keys.postKey(postId), Keys.FORWARD_COUNT, 1);
	}
	
	@Override
	public long increaseShareCount(String postId) {
		return hincrBy(Keys.postKey(postId), Keys.SHARE_COUNT, 1);
	}
	
	@Override
	public long increaseFavoriateCount(String postId, long value) {
		return hincrBy(Keys.postKey(postId), Keys.FAVORIATE_COUNT, value);
	}
	
	@Override
	public long increaseLikeCount(String postId, long value) {
		return hincrBy(Keys.postKey(postId), Keys.LIKE_COUNT_FIELD, value);
	}

	@Override
	public String edit(String postId, String title, String content) {
		Map<String, String> hash = new HashMap<String, String>();
		hash.put("title", title);
		hash.put("content", content);
		return hmset(Keys.postKey(postId), hash);
	}
	@Override
	public String updateWeiboMsg(String postId, String type, int status) {
		Map<String, String> hash = new HashMap<String, String>();
		hash.put("type", type);
		hash.put("status", status + "");
		return hmset(Keys.postKey(postId), hash);
	}

	@Override
	public Long getNewPostCountByScore(final String key, final Double score, final double  max) {
		Long result = (Long) redisTemplate.doExecute(new RedisCallback() {
			public Long doInRedis(Jedis jedis) {
				return jedis.zcount(key, score, max);
			}
		});
		return  result;
	}

	@Override
	public long addMyAllPostFromUser(String mykey, String userKey, String uid) {
		LinkedHashSet<Tuple> userPosts = zrangeWithScores(userKey, 0, -1);
		Map<Double,String> par = new HashMap<Double,String>();
		for(Tuple tuple : userPosts){
			par.put(tuple.getScore(), tuple.getElement());
		}
		return zadd(mykey, par);
	}
	@Override
	public long addMyAllPostFromUserWithDiscuss(String mykey, String userKey, String uid) {
		LinkedHashSet<Tuple> userPosts = zrangeWithScores(userKey, 0, -1);
		Map<Double,String> par = new HashMap<Double,String>();
		for(Tuple tuple : userPosts){
			WeiboMsg w = this.get(tuple.getElement());
			if(null != w && StringUtils.isBlank(w.getType()) &&
					!(w.getOwnerId()+"").equals(LotteryUser.JCZQ.getWeiboUserId()) &&
					!(w.getOwnerId()+"").equals(LotteryUser.JCLQ.getWeiboUserId()) &&
					!(w.getOwnerId()+"").equals(LotteryUser.CTZC.getWeiboUserId()) &&
					!(w.getOwnerId()+"").equals(LotteryUser.SSQ.getWeiboUserId()) ){ //微博类型为用户自己发的 且不是彩种用户发的){ //微博类型为用户自己发的
				par.put(tuple.getScore(), tuple.getElement());
			}
		}
		return zadd(mykey, par);
	}

	@Override
	public long delMyallPostFromUser(String mykey, String userKey,  String useruid) {
		LinkedHashSet<String> userPosts = zrange(userKey, 0, -1);
		return zrem(mykey, userPosts.toArray(new String[0]));
	}

	@Override
	public long addRecomRealsFromUser(String mykey, String userKey) {
		LinkedHashSet<Tuple> userPosts = zrangeWithScores(userKey, 0, -1);
		Map<Double,String> par = new HashMap<Double,String>();
		for(Tuple tuple : userPosts){
			par.put(tuple.getScore(), tuple.getElement());
		}
		return zadd(mykey, par);
	}
	
	@Override
	public long delRecomRealsFromUser(String mykey, String userKey) {
		LinkedHashSet<String> userPosts = zrange(userKey, 0, -1);
		return zrem(mykey, userPosts.toArray(new String[0]));
	}
	
	@Override
	public long addToGlobalTimeline(String postId, double score) {
		return zadd(score, Keys.GLOBAL_TIMELINE, postId);
	}
	
	@Override
	public Long getNewMatchNewsPostCountByScore(final String matchId,final double score,final double max) {
		Long result = (Long) redisTemplate.doExecute(new RedisCallback() {
			public Long doInRedis(Jedis jedis) {
				return jedis.zcount(Keys.getTeamNewsTimelineKey(matchId), score, max);
			}
		});
		return  result;
	}

	@Override
	public Long deleteFromHotRecommend(String postId) {
		if(zrem(Keys.HOTRECOMMEND, postId) == 1) {
			log.info("删除“热门推荐”时间线中微博 postId = {}", postId);
			return 1l;
		}
		return 0L;
	}
	
	@Override
	public Long deleteFromHotDiscuss(RecentDateType recentDateType,
			String postId) {
		if(zrem(Keys.discussKey(recentDateType), postId) == 1) {
			log.info("删除“热门讨论”日期类型={},时间线中微博 postId = {}", 
					recentDateType, postId);
			return 1l;
		}
		return 0L;
	}

	@Override
	public Long deleteFromUserRecomReal(String postId, String userId) {
		long result = 0;
		if(1 == zrem(Keys.userSelfRecomRealsKey(userId), postId)) {
			result++;
			log.info("从用户={},自己发的推荐实单时间线中移除微博ID={}", userId, postId);
		}
		if(1 == zrem(Keys.userRecomRealsTimelineKey(userId), postId)) {
			result++;
			log.info("从用户（包括关注的人）ID={}, 推荐/实单时间线中移除微博ID={}", userId, postId);
		}
		return result;
	}

	@Override
	public Long deleteFromMatchReal(String matchId, String postId) {
		long result = 0;
		if(result == zrem(Keys.matchRealDataKey(matchId), postId)) {
			log.info("从实单和每场比赛ID={}, 推荐的时间线移除微博ID={}", matchId, postId);
		}
		return result;
	}

	@Override
	public Long deleteFromUserMatch(String userId, String postId) {
		long result = 0;
		if(result == zrem(Keys.userMatchesKey(userId), postId)) {
			log.info("从用户ID={},的所有彩种的比赛时间线移除微博ID={}", userId, postId);
		}
		return result;
	}

	@Override
	public Long deleteFromMatch(String matchId, String postId) {
		long result = 0;
		if(1 == zrem(Keys.matchDataKey(matchId), postId)) {
			result++;
			log.info("从每场比赛ID={}, 赛事数据时间线移除微博ID={}", matchId, postId);
		}
		if(1 == zrem(Keys.matchAllContentKey(matchId), postId)) {
			result++;
			log.info("从每场比赛ID={}, 全部内容时间线移除微博ID={}", matchId, postId);
		}
		return result;
	}

	@Override
	public Long deleteFromFollowerMatch(String userId, String postId) {
		long result = 0;
		if(result == zrem(Keys.userMatchesKey(userId), postId)) {
			log.info("从用户ID={},的所有彩种的比赛时间线移除微博ID={}", userId, postId);
		}
		return result;
	}

	@Override
	public void addShowSchemeToWeiboTimeline(String schemeId, String postId) {
		set(Keys.showSchemeToWeibo(schemeId), postId);
	}
	
	@Override
	public void addGroupBuySchemeToWeiboTimeline(String betRecordId,
			String postId) {
		set(Keys.groupBuySchemeToWeibo(betRecordId), postId);
	}
	
	@Override
	public void addRecommendToWeiboTimeline(String schemeId, String postId) {
		set(Keys.recommendSchemeToWeibo(schemeId), postId);
	}
	
	@Override
	public String getWeiboIdByShowSchemeId(String schemeId) {
		return getString(Keys.showSchemeToWeibo(schemeId));
	}
	
	@Override
	public String getWeiboIdByGroupBuyRecordId(String betRecordId) {
		return getString(Keys.groupBuySchemeToWeibo(betRecordId));
	}

	@Override
	public void updateWeiboType(String postId, Map<String, String> hash) {
		hmset(Keys.postKey(postId), hash);
	}
}
