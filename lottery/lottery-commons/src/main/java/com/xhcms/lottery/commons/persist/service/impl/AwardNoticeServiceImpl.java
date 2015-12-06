package com.xhcms.lottery.commons.persist.service.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import com.xhcms.commons.mq.MessageSender;
import com.xhcms.lottery.commons.data.weibo.AwardMessageHandler;
import com.xhcms.lottery.commons.persist.service.AwardNoticeService;
import com.xhcms.lottery.lang.LotteryId;
import com.xhcms.lottery.lang.MQMessageType;

public class AwardNoticeServiceImpl implements AwardNoticeService {

	private Logger logger  = LoggerFactory.getLogger(getClass());
	
	//默认私信发送人ID
	private long privateMessageSenderId = 619145310522368L;
	
	@Autowired
    private MessageSender messageSender;
	
	/**
     * 派奖私信通知
     * @param ownerId
     * @param peerId
     * @param note
     */
	@Override
	public void sendAwardNotic(Map<Long, BigDecimal> awardMap, String lotteryId) {
		logger.info("lottery={}, 接收到派奖请求：{}", awardMap);
		String lotteryName = LotteryId.getLotteryName(lotteryId);
    	
    	if(null != awardMap && awardMap.size() > 0) {
    		String awardTpl = "【中奖通知】恭喜您购买的%s中奖啦，奖金<span class='red'>%s</span>元。";
    		
    		Set<Entry<Long, BigDecimal>> entrySet = awardMap.entrySet();
    		Iterator<Entry<Long, BigDecimal>> iter = entrySet.iterator();
    		while(iter.hasNext()) {
    			AwardMessageHandler msgHandler = new AwardMessageHandler();
    			msgHandler.setOwnerId(privateMessageSenderId);
    			msgHandler.setMessageType(MQMessageType.PRIZE.getType());
    			
    			Entry<Long, BigDecimal> awardEntry = iter.next();
    			Long userId = awardEntry.getKey();
    			BigDecimal bonus = awardEntry.getValue();
    			
    			String result = String.format(awardTpl, lotteryName, bonus);
    			msgHandler.setPeer(userId);
    			msgHandler.setContent(result);
    			msgHandler.setCreateTime(new Date());
    			
    			logger.info("向消息队列发送“派奖私信消息”，内容：{}", msgHandler);
    			messageSender.send(msgHandler);
    		}
    	}
	}

	public long getPrivateMessageSenderId() {
		return privateMessageSenderId;
	}

	public void setPrivateMessageSenderId(long privateMessageSenderId) {
		this.privateMessageSenderId = privateMessageSenderId;
	}
}