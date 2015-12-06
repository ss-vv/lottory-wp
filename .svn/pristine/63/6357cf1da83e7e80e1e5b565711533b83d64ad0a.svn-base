package com.unison.lottery.weibo.uc.persist.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.StringUtils; 
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import redis.clients.jedis.Jedis;

import com.unison.lottery.weibo.common.nosql.impl.BaseDaoImpl;
import com.unison.lottery.weibo.common.nosql.impl.Keys;
import com.unison.lottery.weibo.common.redis.RedisCallback;
import com.unison.lottery.weibo.common.redis.RedisTemplate;
import com.unison.lottery.weibo.common.service.IdGenerator;
import com.unison.lottery.weibo.data.WeiboUser;
import com.unison.lottery.weibo.lang.Constant;
import com.unison.lottery.weibo.uc.data.UserQueryCondition;
import com.unison.lottery.weibo.uc.persist.UserAccountDao;
import com.xhcms.lottery.commons.persist.dao.UserDao;
import com.xhcms.lottery.commons.persist.entity.UserPO;
import com.xhcms.lottery.utils.BeanUtilsTools;

@Repository
public class UserAccountDaoImpl extends BaseDaoImpl<WeiboUser> implements UserAccountDao {

	private Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private RedisTemplate redisTemplate;
	
	@Autowired
	private IdGenerator idGenerator;
	
	@Autowired
	private UserDao userDao;
	
	/** 保存用户信息*/
	private String saveUserInfo(Jedis jedis, WeiboUser weiboUser) {
		String userKey = Keys.getUserKey(weiboUser.getWeiboUserId());
		Map<String, String> value = weiboUser.toRedisHashValue();
		userDao.updateHeadImageUrl(weiboUser.getId(), weiboUser.getHeadImageURL());
		String result = jedis.hmset(userKey, value);
		if ("OK".equals(result)) {
			logger.info("插入:'udi-->用户信息' 成功: key={},value={}", new Object[] { userKey,
					value });
		} else {
			logger.error("插入:'udi-->用户信息' 失败: key={},value={}", new Object[] { userKey,
					value });
		}
		return result;
	}
	/** 保存nickname-->weiboUserId*/
	private String saveNickname(Jedis jedis, WeiboUser weiboUser) {
		String nicknameKey = Keys.getNicknameKey(weiboUser.getNickName());
		String weiboUserId = "" + weiboUser.getWeiboUserId();
		String result = jedis.set(nicknameKey, weiboUserId);
		userDao.updateNickname(weiboUser.getId(), weiboUser.getNickName());
		if ("OK".equals(result)) {
			logger.info("插入: ‘昵称-->微博UserID’ 信息 成功:key={},value={}",
					new Object[] { nicknameKey, weiboUserId });
		} else {
			logger.error("插入: ‘昵称-->微博UserID’ 信息 失败:key={},value={}",
					new Object[] { nicknameKey, weiboUserId });
		}
		return result;
	}
	/** 保存LotteryUserId-->weiboUserId*/
	private String saveLotteryUserId(Jedis jedis, WeiboUser weiboUser) {
		String lotteryUserIdKey = Keys.getLotteryUserIdKey(weiboUser.getId());
		String weiboUserId = "" + weiboUser.getWeiboUserId();
		String result = jedis.set(lotteryUserIdKey, weiboUserId);
		if ("OK".equals(result)) {
			logger.info("插入: ‘彩票用户ID-->微博UserID’ 信息 成功:key={},value={}",
					new Object[] { lotteryUserIdKey, weiboUserId });
		} else {
			logger.error("插入: ‘彩票用户ID-->微博UserID’ 信息 失败:key={},value={}",
					new Object[] { lotteryUserIdKey, weiboUserId });
		}
		return result;
	}
	/** 保存SinaWeiboUserId-->weiboUserId*/
	private String saveSinaWeiboUserId(Jedis jedis, WeiboUser weiboUser){
		String sinaWeiboUidKey = Keys.getSinaWeiboUidKey(weiboUser.getSinaWeiboUid());
		if(StringUtils.isBlank(sinaWeiboUidKey)){
			return "0";
		}
		String weiboUserId = "" + weiboUser.getWeiboUserId();
		String result = jedis.set(sinaWeiboUidKey, weiboUserId);
		if ("OK".equals(result)) {
			logger.info("插入: ‘新浪微博Uid-->微博UserID’ 信息 成功:key={},value={}",
					new Object[] { sinaWeiboUidKey, weiboUserId });
		} else {
			logger.error("插入: ‘新浪微博Uid-->微博UserID’ 信息 失败:key={},value={}",
					new Object[] { sinaWeiboUidKey, weiboUserId });
		}
		return result;
	}
	/** 保存QQConnectUserId-->weiboUserId*/
	private String saveQQConnectUserId(Jedis jedis, WeiboUser weiboUser){
		String qqConnectUidKey = Keys.getQQConnectUidKey(weiboUser.getQqConnectUid());
		if(StringUtils.isBlank(qqConnectUidKey)){
			return "0";
		}
		String weiboUserId = "" + weiboUser.getWeiboUserId();
		String result = jedis.set(qqConnectUidKey, weiboUserId);
		if ("OK".equals(result)) {
			logger.info("插入: ‘QQ互联Uid-->-微博UserID’ 信息 成功:key={},value={}",
					new Object[] { qqConnectUidKey, weiboUserId });
		} else {
			logger.error("插入: ‘QQ互联Uid-->-微博UserID’ 信息 失败:key={},value={}",
					new Object[] { qqConnectUidKey, weiboUserId });
		}
		return result;
	}
	/** 保存微信WeixinUserId-->weiboUserId*/
	private String saveWeixinUserId(Jedis jedis, WeiboUser weiboUser){
		String weixinPCUidKey = Keys.getWeixinUidKey(weiboUser.getWeixinPCUid());
		if(StringUtils.isBlank(weixinPCUidKey)){
			return "0";
		}
		String weiboUserId = "" + weiboUser.getWeiboUserId();
		String result = jedis.set(weixinPCUidKey, weiboUserId);
		if ("OK".equals(result)) {
			logger.info("插入: ‘微信Uid-->-微博UserID’ 信息 成功:key={},value={}",
					new Object[] { weixinPCUidKey, weiboUserId });
		} else {
			logger.error("插入: ‘微信Uid-->-微博UserID’ 信息 失败:key={},value={}",
					new Object[] { weixinPCUidKey, weiboUserId });
		}
		return result;
	}
	/** 保存支付宝WeixinUserId-->weiboUserId*/
	private String saveAlipayUserId(Jedis jedis, WeiboUser weiboUser){
		String alipayUidKey = Keys.getAlipayUidKay(weiboUser.getAlipayUid());
		if(StringUtils.isBlank(alipayUidKey)){
			return "0";
		}
		String weiboUserId = "" + weiboUser.getWeiboUserId();
		String result = jedis.set(alipayUidKey, weiboUserId);
		if ("OK".equals(result)) {
			logger.info("插入: ‘支付宝Uid-->-微博UserID’ 信息 成功:key={},value={}",
					new Object[] { alipayUidKey, weiboUserId });
		} else {
			logger.error("插入: ‘支付宝Uid-->-微博UserID’ 信息 失败:key={},value={}",
					new Object[] { alipayUidKey, weiboUserId });
		}
		return result;
	}
	@Override
	public void saveWeiboUser(final WeiboUser weiboUser) {
		redisTemplate.doExecute(new RedisCallback() {
			public Object doInRedis(Jedis jedis) {
				Long uid = idGenerator.nextId(); // incr("global:nextUserId");
				weiboUser.setWeiboUserId(uid);
				saveUserInfo(jedis, weiboUser);
				saveNickname(jedis, weiboUser);
				saveLotteryUserId(jedis, weiboUser);
				saveSinaWeiboUserId(jedis, weiboUser);
				saveQQConnectUserId(jedis, weiboUser);
				saveWeixinUserId(jedis, weiboUser);
				saveAlipayUserId(jedis, weiboUser);
				saveInWeiboUsers(uid);
				return "OK";
			}
		});
	}

	protected void saveInWeiboUsers(Long uid) {
		zadd(uid, Keys.getWeiboUsers(), uid + "");
	}
	@Override
	public void updateAll(final WeiboUser weiboUser) {
		redisTemplate.doExecute(new RedisCallback() {
			public Object doInRedis(Jedis jedis) {
				saveUserInfo(jedis, weiboUser);
				saveNickname(jedis, weiboUser);
				saveLotteryUserId(jedis, weiboUser);
				saveSinaWeiboUserId(jedis, weiboUser);
				saveQQConnectUserId(jedis, weiboUser);
				saveWeixinUserId(jedis, weiboUser);
				saveAlipayUserId(jedis, weiboUser);
				return "OK";
			}
		});
	}

	private void checkDataIntegrality(WeiboUser weiboUser){
		long lid = weiboUser.getId();
		UserPO userPO = userDao.findById(lid);
		boolean isUpdated=false;
		if(StringUtils.isBlank(weiboUser.getNickName()) 
				&& StringUtils.isNotBlank(userPO.getNickName())){
			BeanUtilsTools.copyNotNullProperties(userPO, weiboUser, null);
			weiboUser.setNickName(userPO.getNickName());
			this.updateAll(weiboUser);
			isUpdated=true;
		}
		if(!isUpdated){
			if(StringUtils.isNotBlank(userPO.getSinaWeiboUid())
					|| StringUtils.isNotBlank(userPO.getQqConnectUid())
					|| StringUtils.isNotBlank(userPO.getWeixinPCUid())){
				if(StringUtils.isBlank(weiboUser.getSinaWeiboUid())
						&& StringUtils.isNotBlank(userPO.getSinaWeiboUid())){
					BeanUtilsTools.copyNotNullProperties(userPO, weiboUser, null);
					this.updateAll(weiboUser);
					return;
				}
				if(StringUtils.isBlank(weiboUser.getQqConnectUid())
					&& StringUtils.isNotBlank(userPO.getQqConnectUid())){
					BeanUtilsTools.copyNotNullProperties(userPO, weiboUser, null);
					this.updateAll(weiboUser);
					return;
				}
				if(StringUtils.isBlank(weiboUser.getWeixinPCUid())
						&& StringUtils.isNotBlank(userPO.getWeixinPCUid())){
					BeanUtilsTools.copyNotNullProperties(userPO, weiboUser, null);
					this.updateAll(weiboUser);
					return;
				}
			}
		}
	}
	
	private WeiboUser querryWeiboUserById(final Long weiboUserId) {
		if (null != weiboUserId) {
			Object o = redisTemplate.doExecute(new RedisCallback() {
				public WeiboUser doInRedis(Jedis jedis) {
					Map<String, String> map = jedis.hgetAll(Keys
							.getUserKey(weiboUserId));
					if(map.size() < 1){
						return null;
					}
					Set<String> field = new HashSet<String>();
					field.add(Constant.NeedConvertTimeField.WeiboUser_weiboUserCreateTime);
					return reflectToClass(map, WeiboUser.class, field);
				}
			});
			if (null != o) {
				WeiboUser w = (WeiboUser) o;
				checkDataIntegrality(w);
				return w;
			}
		}
		return null;
	}

	private WeiboUser querryWeiboUserByNickname(final String nickname) {
		if (null != nickname) {
			Object o = redisTemplate.doExecute(new RedisCallback() {
				public WeiboUser doInRedis(Jedis jedis) {
					String weiboUserId = jedis.get(Keys
							.getNicknameKey(nickname));
					if(StringUtils.isBlank(weiboUserId)){
						return null;
					}
					Map<String, String> map = jedis.hgetAll(Keys
							.getUserKey(Long.valueOf(weiboUserId)));
					if(map.size() < 1){
						return null;
					}
					Set<String> field = new HashSet<String>();
					field.add(Constant.NeedConvertTimeField.WeiboUser_weiboUserCreateTime);
					return reflectToClass(map, WeiboUser.class, field);
				}
			});
			if (null != o) {
				WeiboUser w = (WeiboUser) o;
				checkDataIntegrality(w);
				return w;
			}
		}
		return null;
	}

	private WeiboUser querryWeiboUserByLotteryUserId(final Long lotteryUserId) {
		if (null != lotteryUserId) {
			Object o = redisTemplate.doExecute(new RedisCallback() {
				public WeiboUser doInRedis(Jedis jedis) {
					String weiboUserId = jedis.get(Keys
							.getLotteryUserIdKey(lotteryUserId));
					if(StringUtils.isBlank(weiboUserId)){
						return null;
					}
					Map<String, String> map = jedis.hgetAll(Keys
							.getUserKey(Long.valueOf(weiboUserId)));
					if(map.size() < 1){
						return null;
					}
					Set<String> field = new HashSet<String>();
					field.add(Constant.NeedConvertTimeField.WeiboUser_weiboUserCreateTime);
					return reflectToClass(map, WeiboUser.class, field);
				}
			});
			if (null != o) {
				WeiboUser w = (WeiboUser) o;
				checkDataIntegrality(w);
				return w;
			}
		}
		return null;
	}

	private WeiboUser querryWeiboUserBySinaWeiboUid(final String sinaWeiboUid) {
		if (null != sinaWeiboUid) {
			Object o = redisTemplate.doExecute(new RedisCallback() {
				public WeiboUser doInRedis(Jedis jedis) {
					String weiboUserId = jedis.get(Keys
							.getSinaWeiboUidKey(sinaWeiboUid));
					if (null != weiboUserId) {
						Map<String, String> map = jedis.hgetAll(Keys
								.getUserKey(Long.valueOf(weiboUserId)));
						if(map.size() < 1){
							return null;
						}
						Set<String> field = new HashSet<String>();
						field.add(Constant.NeedConvertTimeField.WeiboUser_weiboUserCreateTime);
						return reflectToClass(map, WeiboUser.class, field);
					} else {
						return null;
					}
				}
			});
			if (null != o) {
				WeiboUser w = (WeiboUser) o;
				checkDataIntegrality(w);
				return w;
			}
		}
		return null;
	}
	
	@Override
	@Transactional
	public List<WeiboUser> querryWeiboUser(
			final UserQueryCondition userQueryCondition) {
		// 根据微博用户ID查用户信息
		List<WeiboUser> weiboUserList = new ArrayList<WeiboUser>();
		Long weiboUserId = userQueryCondition.getWeiboUid();
		if (null != weiboUserId) {
			WeiboUser weiboUser = querryWeiboUserById(weiboUserId);
			if (null != weiboUser && weiboUser.getWeiboUserId() != null) {
				weiboUserList.add(weiboUser);
				return weiboUserList;
			}
		}

		// 根据微博昵称查用户信息
		String nickname = userQueryCondition.getNickName();
		if (null != nickname) {
			WeiboUser weiboUser = querryWeiboUserByNickname(nickname);
			if (null != weiboUser && weiboUser.getWeiboUserId() != null) {
				weiboUserList.add(weiboUser);
				return weiboUserList;
			}
		}

		// 根据彩票用户id查用户信息
		Long lotteryUserId = userQueryCondition.getLotteryUserUid();
		if (null != lotteryUserId) {
			WeiboUser weiboUser = querryWeiboUserByLotteryUserId(lotteryUserId);
			if (null != weiboUser && weiboUser.getWeiboUserId() != null) {
				weiboUserList.add(weiboUser);
				return weiboUserList;
			} else {
				UserPO userPO = userDao.findById(lotteryUserId);
				if(null != userPO && StringUtils.isNotBlank(userPO.getNickName())){
					//如果有用户信息，没有redis信息，且用户信息昵称不为空，则同步mysql 用户数据到redis
					WeiboUser w = new WeiboUser();
					BeanUtilsTools.copyNotNullProperties(userPO, w, null);
					this.saveWeiboUser(w);
					weiboUser = querryWeiboUserByLotteryUserId(lotteryUserId);
					weiboUserList.add(weiboUser);
					return weiboUserList;
				}
			}
		}
		// 根据新浪微博用户的uid 查 用户信息
		String sinaWeiboUid = userQueryCondition.getSinaWeiboUid();
		if (null != sinaWeiboUid) {
			WeiboUser weiboUser = querryWeiboUserBySinaWeiboUid(sinaWeiboUid);
			if (null != weiboUser && weiboUser.getWeiboUserId() != null) {
				weiboUserList.add(weiboUser);
				return weiboUserList;
			}
		}
		return null;
	}

	@Override
	public void updateNickname(WeiboUser weiboUser) {
		final Long weiboUserId = weiboUser.getWeiboUserId();
		final String nickname = weiboUser.getNickName();
		if (null != weiboUserId && null != nickname) {
			redisTemplate.doExecute(new RedisCallback() {
				public Long doInRedis(Jedis jedis) {
					String key = Keys.getUserKey(weiboUserId);
					jedis.set(Keys.getNicknameKey(nickname), "" + weiboUserId);
					return jedis.hset(key, "nickname", nickname);
				}
			});
		}
	}

	@Override
	public void saveSinaWeiboUid(WeiboUser weiboUser) {
		final Long weiboUserId = weiboUser.getWeiboUserId();
		final String sinaWeiboUid = weiboUser.getSinaWeiboUid();
		if (null != weiboUserId && null != sinaWeiboUid) {
			redisTemplate.doExecute(new RedisCallback() {
				public Long doInRedis(Jedis jedis) {
					String key = Keys.getUserKey(weiboUserId);
					jedis.set(Keys.getSinaWeiboUidKey(sinaWeiboUid), ""
							+ weiboUserId);
					return jedis.hset(key, "sinaWeiboUid", sinaWeiboUid);
				}
			});
		}
	}

	@Override
	public void updateWeixinToken(final WeiboUser weiboUser,
			final String weixinToken) {
		if (null != weiboUser && null != weixinToken) {
			redisTemplate.doExecute(new RedisCallback() {
				public Long doInRedis(Jedis jedis) {
					String key = Keys.getUserKey(weiboUser.getWeiboUserId());
					return jedis.hset(key, "weixinToken", weixinToken);
				}
			});
		}
	}

	@Override
	public void updateSinaToken(final WeiboUser weiboUser,
			final String sinaWeiboToken) {
		if (null != weiboUser && null != sinaWeiboToken) {
			redisTemplate.doExecute(new RedisCallback() {
				public Long doInRedis(Jedis jedis) {
					String key = Keys.getUserKey(weiboUser.getWeiboUserId());
					Long result = jedis.hset(key, "sinaWeiboToken", sinaWeiboToken);
					logger.info("weiboUserId={}, 更新token",weiboUser.getWeiboUserId());
					logger.info("oldToken={},newToken={}",weiboUser.getSinaWeiboToken(),sinaWeiboToken);
					return result;
				}
			});
		}
	}

	@Override
	@Transactional
	public void updateHeadImage(WeiboUser weiboUser) {
		final Long weiboUserId = weiboUser.getWeiboUserId();
		final String headImageURL = weiboUser.getHeadImageURL();
		userDao.updateHeadImageUrl(weiboUser.getId(), headImageURL);
		if (null != weiboUserId && null != headImageURL) {
			redisTemplate.doExecute(new RedisCallback() {
				public Long doInRedis(Jedis jedis) {
					String key = Keys.getUserKey(weiboUserId);
					return jedis.hset(key, "headImageURL", headImageURL);
				}
			});
		}
	}
	
	/**
	 * 根据微博用户ID查用户信息.
	 */
	@Override
	public WeiboUser get(long weiboUserId) {
		return querryWeiboUserById(weiboUserId);
	}
	@Override
	public void updateQQConnectToken(final WeiboUser weiboUser,final String token) {
		if (null != weiboUser && null != token) {
			redisTemplate.doExecute(new RedisCallback() {
				public Long doInRedis(Jedis jedis) {
					String key = Keys.getUserKey(weiboUser.getWeiboUserId());
					return jedis.hset(key, "qqConnectToken", token);
				}
			});
		}
	}
	public IdGenerator getIdGenerator() {
		return idGenerator;
	}
	public void setIdGenerator(IdGenerator idGenerator) {
		this.idGenerator = idGenerator;
	}
	
	
}
