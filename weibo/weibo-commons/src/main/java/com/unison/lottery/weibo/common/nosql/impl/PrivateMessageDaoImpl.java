package com.unison.lottery.weibo.common.nosql.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import redis.clients.jedis.Jedis;
import com.unison.lottery.weibo.common.nosql.PrivateMessageDao;
import com.unison.lottery.weibo.common.redis.RedisCallback;
import com.unison.lottery.weibo.common.redis.RedisTemplate;
import com.unison.lottery.weibo.common.service.IdGenerator;
import com.unison.lottery.weibo.data.PrivateField;
import com.unison.lottery.weibo.data.PrivateMessage;

@Repository
public class PrivateMessageDaoImpl extends BaseDaoImpl<PrivateMessage> implements PrivateMessageDao {

	@Autowired
	private IdGenerator idGenerator;
	@Autowired
	private RedisTemplate redisTemplate;
	
	@Override
	public long savePrivateMessage(final PrivateMessage privateMsg) {
		Map<String, String> hash = new HashMap<String, String>();
		long ownerId = privateMsg.getOwnerId();
		long peer = privateMsg.getPeer();

		hash.put(PrivateField.ownerId, String.valueOf(ownerId));
		hash.put(PrivateField.peer, String.valueOf(peer));
		hash.put(PrivateField.content, privateMsg.getContent());
		hash.put(PrivateField.type, privateMsg.getType()+"");
		hash.put(PrivateField.createTime, privateMsg.getCreateTime().getTime()+"");
		
		long sendPrivateMsgId = idGenerator.nextId();
		privateMsg.setId(sendPrivateMsgId);
		hmset(Keys.privateMsg(sendPrivateMsgId), hash);
		return sendPrivateMsgId;
	}

	@Override
	public long savePrivateMessageWithUser(final PrivateMessage privateMsg) {
		long ownerId = privateMsg.getOwnerId();
		long peer = privateMsg.getPeer();
		double score = Double.valueOf(privateMsg.getCreateTime().getTime());
		long ret = zadd(score, Keys.privatePeerMsgs(ownerId, peer),
				String.valueOf(privateMsg.getId()));
		return ret;
	}

	@Override
	public long saveSessionUserSet(final PrivateMessage privateMsg) {
		long ownerId = privateMsg.getOwnerId();
		long peer = privateMsg.getPeer();
		double score = Double.valueOf(System.currentTimeMillis());
		long ret = zadd(score, Keys.privateSessionUserId(ownerId),
				String.valueOf(peer));
		return ret;
	}
	
	@Override
	public long deliverToReceiver(long peerId, long privateMsgId, Date createTime) {
		double score = Double.valueOf(createTime.getTime());
		long ret = zadd(score, Keys.privateMsgReceived(peerId),
				String.valueOf(privateMsgId));
		return ret;
	}

	@Override
	public Set<String> findSelfPrivateMessageUserId(final long userId,
			int startIndex, int endIndex) {
		Set<String> userSet = zrange(Keys.privateMsgReceived(userId), startIndex,
				endIndex);
		return userSet;
	}

	@Override
	public List<PrivateMessage> findSessionInfoWithUser(final Set<String> msgIdSet,
			final int start, final int end) {
		List<PrivateMessage> list = new ArrayList<PrivateMessage>();
		Iterator<String> iter = msgIdSet.iterator();
		while (iter.hasNext()) {
			String privateId = iter.next();
			Object o = hashGet(Keys.privateMsg(Long.parseLong(privateId)));
			if (null != o) {
				PrivateMessage privateMsg = (PrivateMessage) o;
				privateMsg.setId(Long.parseLong(privateId));
				list.add(privateMsg);
			}
		}
		return list;
	}

	@Override
	public PrivateMessage findLastestSessionInfoWithUser(final String msgId) {
		Set<String> msgIdSet = new HashSet<String>();
		msgIdSet.add(msgId);
		List<PrivateMessage> list = findSessionInfoWithUser(msgIdSet, 0, 1);
		if (null != list && list.size() > 0) {
			return list.get(0);
		}
		return null;
	}

	@Override
	public long sessionCountWithUserId(final long userId, final long peerId) {
		Long sessionCnt = zcard(Keys.privatePeerMsgs(userId, peerId));
		return sessionCnt == null ? 0L : sessionCnt.longValue();
	}

	@Override
	public Long deleteById(final long userId, final long peerId,
			final long privateMsgId) {
		zrem(Keys.privatePeerMsgs(userId, peerId), String.valueOf(privateMsgId));
		Long ret = delete(Keys.privateMsg(privateMsgId));
		return ret;
	}

	@Override
	public int deleteSession(final long userId, final long peerId) {
		zrem(Keys.privateMsgReceived(userId), String.valueOf(peerId));
		Set<String> msgIdList = zrange(Keys.privatePeerMsgs(userId, peerId), 0, -1);
		Long delCount = 0L;
		if (null != msgIdList && msgIdList.size() > 0) {
			Iterator<String> iter = msgIdList.iterator();
			while (iter.hasNext()) {
				String msgId = iter.next();
				delCount = delCount
						+ deleteById(userId, peerId, Long.parseLong(msgId));
			}
		}
		return delCount.intValue();
	}

	@Override
	public long countPrivateMessageUser(final long userId) {
		return zcard(Keys.privateMsgReceived(userId));
	}

	@Override
	public Set<String> findPrivateMessageMsgId(long userId, long peer,
			int startIndex, int endIndex) {
		Set<String> privateIdSet = zrange(Keys.privatePeerMsgs(userId, peer),
				startIndex, endIndex);
		return privateIdSet;
	}

	@Override
	public PrivateMessage findPrivateMessageById(final long msgId) {
		if(msgId <= 0){
			return new PrivateMessage();
		}
		Object o = redisTemplate.doExecute(new RedisCallback() {
			public PrivateMessage doInRedis(Jedis jedis) {
				Map<String, String> map = jedis.hgetAll(Keys.privateMsg(msgId));
				if(map.size() < 1){
					return new PrivateMessage();
				}
				Set<String> field = new HashSet<String>();
				field.add("createTime");
				return reflectToClass(map, PrivateMessage.class, field);
			}
		});
		return (PrivateMessage)o;
	}
}