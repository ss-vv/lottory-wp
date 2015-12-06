package com.unison.lottery.weibo.mq;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import com.unison.lottery.weibo.common.cache.SchemeCache;
import com.unison.lottery.weibo.common.nosql.impl.Keys;
import com.xhcms.commons.mq.MessageHandler;
import com.xhcms.lottery.commons.data.BetScheme;
import com.xhcms.lottery.commons.data.weibo.SchemeCacheUpdateMessageHandler;
import com.xhcms.lottery.commons.persist.service.BetSchemeService;
import com.xhcms.lottery.lang.SchemeType;

/**
 * @desc 方案缓存更新
 * @createTime 2014-7-17
 * @author lei.li@unison.net.cn
 * @version 1.0
 */
public class AsyncSchemeCacheUpdateHandler implements
		MessageHandler<SchemeCacheUpdateMessageHandler> {

	private static final long serialVersionUID = -4368938836178040767L;

	private Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	BetSchemeService betSchemeService;

	@Autowired
	SchemeCache schemeCache;

	@Override
	public void handle(SchemeCacheUpdateMessageHandler schemeCacheUpdateMsg) {
		logger.info("\n接收到更新方案缓存的消息：{}", schemeCacheUpdateMsg);
		
		long schemeId = schemeCacheUpdateMsg.getSchemeId();
		SchemeType schemeType = schemeCacheUpdateMsg.getSchemeType();
		
		if (schemeId > 0 && schemeType != null) {
			if(schemeType.getType() == SchemeType.REAL.getType()) {
				updateRealScheme(schemeCacheUpdateMsg);
			} else if(schemeType.getType() == SchemeType.RECOMMEND.getType()) {
				
			}
		}
	}
	
	private void updateRealScheme(SchemeCacheUpdateMessageHandler updateMsg) {
		long schemeId = updateMsg.getSchemeId();
		int buyType = updateMsg.getShowType();
		String cacheKey = Keys.realSchemeCacheKey(schemeId, updateMsg.getShowType());
		
		BetScheme betScheme = betSchemeService.viewScheme(schemeId, buyType);
		if (null != betScheme && betScheme.getId() > 0) {
			schemeCache.updateSchemeCache(cacheKey, betScheme);
			logger.info("\n成功更新方案缓存数据：key={}", cacheKey);
		} else {
			logger.info("\n收到更新方案缓存的消息，更新缓存失败！无法查询到方案对象；方案ID={}, 方案类型={}", schemeId, buyType);
		}
	}
}