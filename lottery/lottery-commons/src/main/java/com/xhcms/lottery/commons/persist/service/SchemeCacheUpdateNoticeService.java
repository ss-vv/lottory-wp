package com.xhcms.lottery.commons.persist.service;

import com.xhcms.lottery.commons.data.weibo.SchemeCacheUpdateMessageHandler;
import com.xhcms.lottery.commons.persist.entity.BetSchemePO;

public interface SchemeCacheUpdateNoticeService {

	void sendMsg(SchemeCacheUpdateMessageHandler msg);
	
	void buildMsgAndSend(BetSchemePO betSchemePo);
}