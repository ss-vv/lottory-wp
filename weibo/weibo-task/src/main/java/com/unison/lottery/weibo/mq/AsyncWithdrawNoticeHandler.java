package com.unison.lottery.weibo.mq;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import com.unison.lottery.weibo.common.service.PrivateMessageService;
import com.unison.lottery.weibo.data.PrivateMessage;
import com.unison.lottery.weibo.data.WeiboUser;
import com.unison.lottery.weibo.uc.service.UserAccountService;
import com.xhcms.commons.mq.MessageHandler;
import com.xhcms.lottery.commons.data.weibo.WithdrawMessageHandler;
import com.xhcms.lottery.lang.MQMessageType;

/**
 * @desc 提现通知
 * @author lei.li@unison.net.cn
 * @createTime 2014-6-19
 * @version 1.0
 */
public class AsyncWithdrawNoticeHandler implements
		MessageHandler<WithdrawMessageHandler> {

	private static final long serialVersionUID = -4368938836178040767L;

	private Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private PrivateMessageService messageService;
	
	@Autowired
	private UserAccountService accountService;

	@Override
	public void handle(WithdrawMessageHandler withdrawMessage) {
		logger.info("接收到提现消息：{}", withdrawMessage);
		if(null != withdrawMessage) {
			int messageType = withdrawMessage.getMessageType();
			int expectType = MQMessageType.WITHDRAW.getType();
			if(messageType == expectType) {
				long peer = withdrawMessage.getPeer();
				WeiboUser weiboUser = accountService.findWeiboUserByLotteryUid(peer+"");
				if(null != weiboUser && weiboUser.getWeiboUserId() > 0) {
					PrivateMessage msg = new PrivateMessage();
					msg.setOwnerId(withdrawMessage.getOwnerId());
					msg.setPeer(weiboUser.getWeiboUserId());
					msg.setContent(withdrawMessage.getContent());
					msg.setType(messageType);
					
					logger.info("微博用户={}, 准备给微博用户ID={}, 昵称={}，大V彩用户ID={}, 发送提现私信={}",
							new Object[]{withdrawMessage.getOwnerId(),
							weiboUser.getWeiboUserId(), 
							peer, weiboUser.getNickName(), msg});
					messageService.sendPrivateMessage(msg);
				} else {
					logger.info("准备给大V彩用户ID={},发送提现私信通知，找不到对应微博用户.", peer);
				}
			} else {
				logger.info("提现消息类型错误:expect type={}, actual type={}", expectType, messageType);
			}
		}
	}
}