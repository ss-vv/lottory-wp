package com.unison.lottery.weibo.mq;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.unison.lottery.weibo.common.data.FollowCommand;
import com.unison.lottery.weibo.uc.service.RelationshipService;
import com.xhcms.commons.mq.MessageHandler;

/**
 * 异步处理关注、取消关注操作。
 * 
 * @author Yang Bo
 */
public class AsyncFollowHandler implements MessageHandler<FollowCommand> {

	private static final long serialVersionUID = -943553700738300527L;

	private Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private RelationshipService relationshipService;
	
	@Override
	public void handle(FollowCommand followCommand) {
		long startTime = System.currentTimeMillis();
		logger.debug("开始处理'关注'异步命令...");
		
		try{
			relationshipService.processAsyncFollowCommand(followCommand);
		}catch(Exception e){
			logger.error("异步处理'关注/取消关注'命令失败！", e);
		}
		
		long endTime = System.currentTimeMillis();
		logger.debug("'关注'异步命令处理完成，耗时:{}秒.", (endTime-startTime)/1000.0);
	}

}
