package com.unison.lottery.weibo.mq;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.unison.lottery.weibo.common.nosql.impl.Keys;
import com.unison.lottery.weibo.common.service.BetRecordCache;
import com.xhcms.commons.mq.MessageHandler;
import com.xhcms.lottery.commons.data.BetScheme;
import com.xhcms.lottery.commons.data.weibo.BetMessage;
import com.xhcms.lottery.commons.persist.service.BetPartnerService;
import com.xhcms.lottery.commons.persist.service.BetSchemeService;

/**
 *
 * @author lei.li@davcai.com
 */
public class AsyncBetMessageHandler implements MessageHandler<BetMessage>{

	private static final long serialVersionUID = -3305196252848895594L;
	private Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	BetRecordCache betRecordService;
	@Autowired
	BetSchemeService betSchemeService;
	@Autowired
	BetPartnerService betPartnerService;
	
	@Override
	public void handle(BetMessage betMessage) {
		logger.info("接收到投注消息:{}", betMessage);
		
		if(null != betMessage) {
			long userId = betMessage.getUserId();
			long schemeId = betMessage.getSchemeId();
			String lotteryId = betMessage.getLotteryId();
			int displayMode = betMessage.getDisplayMode();
			long createdTime = betMessage.getSchemeCreateTime();
			long betRecordId = betMessage.getBetRecordId();
			
			BetScheme betScheme = betSchemeService.getSchemeById(schemeId);
			if(null != betScheme && betScheme.getId() > 0) {
				logger.debug("准备缓存方案ID={}", betScheme.getId());
				
				if(userId > 0 && StringUtils.isNotBlank(lotteryId) && 
						schemeId > 0 && displayMode >= 0 && createdTime > 0) {
					String value = Keys.schemeIdCacheValue(schemeId, displayMode, betRecordId);
					betRecordService.cacheBetRecord(userId, lotteryId, value, createdTime);
				}
			} else {
				logger.error("根据投注方案ID={},找不到有效的方案对象！", schemeId);
			}
		}
	}
}