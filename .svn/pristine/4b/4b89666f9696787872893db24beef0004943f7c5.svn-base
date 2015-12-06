package com.unison.lottery.weibo.mq;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import com.unison.lottery.weibo.common.service.LotteryService;
import com.xhcms.commons.mq.MessageHandler;
import com.xhcms.lottery.commons.data.weibo.WinningMessageHandler;
import com.xhcms.lottery.lang.MQMessageType;

/**
 * @desc	异步发送中奖喜报
 * @author lei.li@unison.net.cn
 * @createTime 2014-7-3
 * @version 1.0
 */
public class AsyncWinningHandler implements MessageHandler<WinningMessageHandler>{

	private static final long serialVersionUID = -4368938836178040767L;
	
	private Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	LotteryService lotteryService;
	
	@Override
	public void handle(WinningMessageHandler winnMessage) {
		logger.info("接收到喜报消息对象:{}", winnMessage);
		if(null != winnMessage) {
			Long schemeId = winnMessage.getSchemeId();
			int messageType = winnMessage.getMessageType();
			if(null != schemeId && schemeId > 0 
					&& MQMessageType.WINNING.getType() == messageType) {
				lotteryService.autoSendWinning(schemeId);
			}
		}
	}
}