package com.davcai.lottery.push.client;

import java.util.List;

import com.davcai.lottery.push.common.model.*;

public interface PushClient {
	
	/**
	 * 先判断message是否有变化，如果有变化，才推送消息
	 * @param match
	 * @return 推送成功，返回true；失败返回false
	 */
	PushResult pushMessage(PushMessage message) throws PushClientException;


	/**先判断message是否有变化，如果有变化，才推送消息
	 * 批量推送消息,要求messages非空且每一個message都有type值
	 * @param messages
	 * @return 推送成功，返回true；失败返回false
	 */
	PushResult pushMessages(List<PushMessage> messages) throws PushClientException;


	/**
	 * 设置pushServerUrl
	 * @param pushServerUrl
	 */
	void initPushServerUrl(String pushServerUrl);


	/**
	 * 推送赔率消息
	 * @param pushMessages
	 * @param oldCount
	 * @return
	 * @throws PushClientException
	 */
	PushResult pushOddsMessages(List<PushMessage> pushMessages, Integer oldCount)
			throws PushClientException;


}
