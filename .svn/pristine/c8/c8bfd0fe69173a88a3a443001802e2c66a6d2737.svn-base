package com.unison.lottery.weibo.mq;

import org.springframework.beans.factory.annotation.Autowired;

import com.unison.lottery.weibo.common.service.PrivateMessageService;
import com.xhcms.commons.mq.MessageHandler;
import com.xhcms.lottery.commons.data.weibo.PushPrivateMsgToAllHandle;

public class AsyncPushPrivateMsgHandler implements MessageHandler<PushPrivateMsgToAllHandle>{

	private static final long serialVersionUID = -8732886926528769656L;
	
	@Autowired
	private PrivateMessageService privateMessageService;
	
	@Override
	public void handle(PushPrivateMsgToAllHandle pushPrivateMsgToAllHandle) {
		privateMessageService.pushPrivateMsgToAllUsers(pushPrivateMsgToAllHandle.getPrivateMsgId());
	}
}
