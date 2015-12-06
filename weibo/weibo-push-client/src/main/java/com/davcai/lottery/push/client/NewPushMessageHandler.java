package com.davcai.lottery.push.client;

import java.util.List;
import java.util.Map;

import com.davcai.lottery.push.common.model.PushMessage;
import com.davcai.lottery.push.common.model.PushTaskType;
import com.davcai.lottery.push.common.redis.RedisClient;

public interface NewPushMessageHandler {
	
	/**
	 * 检查pushMessage是否数据有变化
	 * @param pushMessage
	 * @return
	 */
	boolean checkIfNew(PushMessage pushMessage);

	void setRedisClient(RedisClient redisClient);

	void saveNew(PushMessage pushMessage);

	Map<PushTaskType,List<PushMessage>> checkPushMessagesIfNew(List<PushMessage> pushMessages);
	
	void saveNews(List<PushMessage> pushMessages);

}
