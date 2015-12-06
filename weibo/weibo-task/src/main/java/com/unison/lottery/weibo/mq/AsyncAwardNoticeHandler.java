package com.unison.lottery.weibo.mq;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import com.unison.lottery.weibo.common.service.PrivateMessageService;
import com.unison.lottery.weibo.data.PrivateMessage;
import com.unison.lottery.weibo.data.WeiboUser;
import com.unison.lottery.weibo.uc.service.UserAccountService;
import com.xhcms.commons.mq.MessageHandler;
import com.xhcms.lottery.commons.data.weibo.AwardMessageHandler;
import com.xhcms.lottery.lang.MQMessageType;

/**
 * @desc 派奖通知
 * @author lei.li@unison.net.cn
 * @createTime 2014-6-19
 * @version 1.0
 */
public class AsyncAwardNoticeHandler implements MessageHandler<AwardMessageHandler>{

	private static final long serialVersionUID = -4368938836178040767L;
	
	private Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private PrivateMessageService messageService;
	
	@Autowired
	private UserAccountService accountService;
	
	@Override
	public void handle(AwardMessageHandler awardMessage) {
		if(null != awardMessage) {
			logger.info("接收到派奖消息；派奖消息接收人id={},消息内容={}", awardMessage.getPeer(), awardMessage);
			int messageType = awardMessage.getMessageType();
			int expectType = MQMessageType.PRIZE.getType();
			if(messageType == expectType) {
				long peer = awardMessage.getPeer();
				WeiboUser weiboUser = accountService.findWeiboUserByLotteryUid(peer+"");
				if(null != weiboUser && weiboUser.getWeiboUserId() > 0) {
					PrivateMessage msg = new PrivateMessage();
					msg.setOwnerId(awardMessage.getOwnerId());
					msg.setPeer(weiboUser.getWeiboUserId());
					msg.setContent(awardMessage.getContent());
					msg.setType(messageType);
					
					logger.info("微博用户={}, 准备给微博用户ID={}, 微博用户昵称={}，大V彩用户ID={}, 发送派奖私信={}",
							new Object[]{awardMessage.getOwnerId(),
							weiboUser.getWeiboUserId(), 
							weiboUser.getNickName(), peer, msg});
					
					messageService.sendPrivateMessage(msg);
				} else {
					logger.info("准备给大V彩用户ID={},发送派奖私信通知，找不到对应微博用户.", peer);
				}
			} else {
				logger.info("派奖消息类型错误:expect type={}, actual type={}", expectType, messageType);
			}
		} else {
			logger.info("无效派奖消息请求，消息为空.");
		}
	}
}