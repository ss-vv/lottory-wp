package com.xhcms.lottery.commons.persist.service.impl;

import java.util.Date;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import com.xhcms.commons.mq.MessageSender;
import com.xhcms.lottery.commons.data.weibo.WinningMessageHandler;
import com.xhcms.lottery.commons.persist.entity.BetSchemePO;
import com.xhcms.lottery.commons.persist.service.WinningNoticeService;
import com.xhcms.lottery.lang.Constants;
import com.xhcms.lottery.lang.EntityStatus;
import com.xhcms.lottery.lang.MQMessageType;

public class WinningNoticeServiceImpl implements WinningNoticeService {

	private Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private MessageSender messageSender;

	/**
	 * 发送中奖喜报消息通知
	 * 
	 * @param schemeList
	 */
	@Override
	public void sendWinningNew(List<Long> schemeList) {
		logger.info("接收到发中喜报的请求：给定的方案号={}", schemeList);
		if (null != schemeList && schemeList.size() > 0) {
			for (long schemeId : schemeList) {
				WinningMessageHandler winningMsg = new WinningMessageHandler();
				winningMsg.setMessageType(MQMessageType.WINNING.getType());
				winningMsg.setCreateTime(new Date());
				winningMsg.setSchemeId(schemeId);

				logger.info("向消息队列发送“中奖喜报消息”，内容：{}", winningMsg);
				messageSender.send(winningMsg);
			}
		}
	}

	@Override
	public boolean checkWinningScheme(BetSchemePO scheme) {
		if (null != scheme
				&& (scheme.getStatus() == EntityStatus.TICKET_NOT_AWARD)
				&& (scheme.getShowScheme() == Constants.SHOW_SCHEME || scheme
						.getType() == Constants.TYPE_GROUP)
				|| scheme.getType() == Constants.TYPE_FOLLOW) {
			return true;
		}
		return false;
	}
}