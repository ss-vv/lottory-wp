package com.davcai.lottery.push.client;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javassist.compiler.ast.NewExpr;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.davcai.lottery.push.common.model.BasketballMatchMessage;
import com.davcai.lottery.push.common.model.PushMessage;
import com.davcai.lottery.push.common.model.PushTaskType;
import com.davcai.lottery.push.common.redis.RedisClient;

public class NewPushMessageHandlerRedisImpl implements NewPushMessageHandler {
	@Autowired
	private RedisClient redisClient;
	
	

	@Override
	public boolean checkIfNew(PushMessage pushMessage) {
		boolean result=false;
		if(null==pushMessage){
			return false;
		}
		if(StringUtils.isBlank(pushMessage.getId())){
			return false;
		}
		PushMessage oldPushMessage=redisClient.getPushMessageByTemplate(pushMessage);
		if(pushMessage.isSame(oldPushMessage)){
			result=false;
		}
		else{
			result=true;
		}
		return result;
	}

	

	@Override
	public void setRedisClient(RedisClient redisClient) {
		this.redisClient=redisClient;
		
	}

	
	public RedisClient getRedisClient() {
		return redisClient;
	}

	@Override
	public void saveNew(PushMessage pushMessage) {
		redisClient.savePushMessage(pushMessage);
		
	}


	@Override
	public void saveNews(List<PushMessage> pushMessages) {
		for(PushMessage pushMessage : pushMessages){
			redisClient.savePushMessage(pushMessage);
		}
	}

	@Override
	public Map<PushTaskType,List<PushMessage>> checkPushMessagesIfNew(List<PushMessage> pushMessages) {
		List<PushMessage> newPushMessagesToCometd = new ArrayList<PushMessage>();
		List<PushMessage> newPushMessagesToGTUI = new ArrayList<PushMessage>();
		if(null==pushMessages || pushMessages.isEmpty()){
			return null;
		}
		for(PushMessage pushMessage : pushMessages){
			if(StringUtils.isNotBlank(pushMessage.getId())){
				PushMessage oldPushMessage=redisClient.getPushMessageByTemplate(pushMessage);
				if(pushMessage!=null && (pushMessage instanceof BasketballMatchMessage)){ //如果是篮球推送
					
					if(!pushMessage.isSame(oldPushMessage)){
						newPushMessagesToCometd.add(pushMessage);
					}
					if(!pushMessage.isSameForHuanxin(oldPushMessage)){
						BasketballMatchMessage basketPushMessage = (BasketballMatchMessage)pushMessage;
						basketPushMessage.makeStateDesc(oldPushMessage);
						newPushMessagesToGTUI.add(pushMessage);
					}
				}else{
					if(!pushMessage.isSame(oldPushMessage)){
						newPushMessagesToCometd.add(pushMessage);
						newPushMessagesToGTUI.add(pushMessage);
					}
				}
			}
		}
		
		if((!newPushMessagesToCometd.isEmpty())||(!newPushMessagesToGTUI.isEmpty())){
			Map<PushTaskType,List<PushMessage>> pushMap = new HashMap<>();
			pushMap.put(PushTaskType.CometD, newPushMessagesToCometd);
			pushMap.put(PushTaskType.GeTui, newPushMessagesToGTUI);
			
			return pushMap;
		}
		return null;
	}

}
