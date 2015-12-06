package com.xhcms.lottery.commons.persist.service.impl;

import java.util.Date;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import com.xhcms.commons.mq.MessageSender;
import com.xhcms.lottery.commons.data.weibo.SchemeCacheUpdateMessageHandler;
import com.xhcms.lottery.commons.persist.entity.BetSchemePO;
import com.xhcms.lottery.commons.persist.service.SchemeCacheUpdateNoticeService;
import com.xhcms.lottery.lang.SchemeType;

public class SchemeCacheUpdateNoticeServiceImpl implements SchemeCacheUpdateNoticeService {

	private Logger logger  = LoggerFactory.getLogger(getClass());
	
	@Autowired
    private MessageSender messageSender;

	@Override
	public void sendMsg(SchemeCacheUpdateMessageHandler updateCacheMsg) {
		messageSender.send(updateCacheMsg);
	}

	@Override
	public void buildMsgAndSend(BetSchemePO betSchemePo) {
		SchemeCacheUpdateMessageHandler schemeCacheUpdateMsg = new SchemeCacheUpdateMessageHandler();
		schemeCacheUpdateMsg.setSchemeId(betSchemePo.getId());
		schemeCacheUpdateMsg.setSchemeType(SchemeType.REAL);
		schemeCacheUpdateMsg.setShowType(betSchemePo.getType());
		schemeCacheUpdateMsg.setCreateTime(new Date());
		
		logger.info("发送更新方案缓存的消息:{}", schemeCacheUpdateMsg);
		sendMsg(schemeCacheUpdateMsg);
	}
}