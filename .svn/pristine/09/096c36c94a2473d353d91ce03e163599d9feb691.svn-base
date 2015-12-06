package com.unison.lottery.weibo.mq;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import com.unison.lottery.weibo.common.service.PrivateMessageService;
import com.unison.lottery.weibo.data.PrivateMessage;
import com.unison.lottery.weibo.data.WeiboUser;
import com.unison.lottery.weibo.uc.service.UserAccountService;
import com.xhcms.commons.mq.MessageHandler;
import com.xhcms.lottery.commons.data.weibo.CertificationUserNoticeHandler;
import com.xhcms.lottery.lang.MQMessageType;

/**
 * @desc “分析达人”推送通知
 * @author lei.li@unison.net.cn
 * @createTime 2014-7-11
 * @version 1.0
 */
public class AsyncAnalysisExpertPushHandler implements MessageHandler<CertificationUserNoticeHandler>{

	private static final long serialVersionUID = -4368938836178040767L;
	
	private Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private PrivateMessageService messageService;
	
	@Autowired
	private UserAccountService accountService;
	
	@Override
	public void handle(CertificationUserNoticeHandler messageHandler) {
		if(null != messageHandler) {
			logger.info("消息内容={}", messageHandler);
			int messageType = messageHandler.getMessageType().getType();
			int expectType = MQMessageType.ANALYSIS_EXPERT.getType();
			if(messageType == expectType) {
				long peer = messageHandler.getPeer();
				WeiboUser weiboUser = accountService.findWeiboUserByWeiboUid(peer+"");
				if(null != weiboUser && weiboUser.getWeiboUserId() > 0) {
					PrivateMessage msg = new PrivateMessage();
					msg.setOwnerId(messageHandler.getOwnerId());
					msg.setPeer(weiboUser.getWeiboUserId());
					msg.setContent(messageHandler.getContent());
					msg.setType(messageType);
					
					logger.info("发送私信消息={}",
							new Object[]{messageHandler.getOwnerId(),
							weiboUser.getWeiboUserId(), 
							weiboUser.getNickName(), peer, msg});
					
					messageService.sendPrivateMessage(msg);
				} else {
					logger.info("准备给微博用户ID={},发送私信通知，找不到对应微博用户.", peer);
				}
			} else {
				logger.info("消息类型错误:expect type={}, actual type={}", expectType, messageType);
			}
		} else {
			logger.info("消息为空.");
		}
	}
}