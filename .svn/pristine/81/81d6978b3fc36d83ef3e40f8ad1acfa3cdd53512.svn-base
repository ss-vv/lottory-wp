package com.unison.lottery.weibo.mq;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.unison.lottery.weibo.common.data.PublishCommand;
import com.unison.lottery.weibo.common.service.LotteryService;
import com.unison.lottery.weibo.data.Relationship;
import com.unison.lottery.weibo.data.WeiboMsg;
import com.unison.lottery.weibo.uc.service.RelationshipService;
import com.xhcms.commons.mq.MessageHandler;

public class AsyncPublishHandler implements MessageHandler<PublishCommand> {

	private static final long serialVersionUID = -5764055835469889778L;

	private Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private RelationshipService relationshipService;
	
	@Autowired
	private LotteryService lotteryService;
		
	@Override
	public void handle(PublishCommand publishCommand) {
		logger.debug("开始处理异步发布命令：{}", publishCommand);
		long start = System.currentTimeMillis();
		try{
			// 遍历微博作者的所有粉丝
			WeiboMsg weibo = publishCommand.getWeiboMsg();
			Relationship relationship = relationshipService.findFollowersByUid(weibo.getOwnerId());
			if(null != relationship && relationship.getFollowers() != null 
					&& relationship.getFollowers().length > 0){
				// 将微博id加入每个粉丝的指定时间线
				String[] followers = relationship.getFollowers();
				logger.debug("异步发布，即将遍历粉丝数：{}", followers.length);
				for (String flwrId : followers) {
					lotteryService.addWeiboToTimelines(weibo, flwrId, publishCommand.getDestTimelines());
					// 不用再加到用户的主时间线了，因为基本转发逻辑会做投递。
				}
			}
		}catch(Exception e){
			logger.error("处理异步发布微博到指定时间线命令失败！", e);
		}
		// 记录用时
		long end = System.currentTimeMillis();
		logger.debug("处理异步发布命令结束，耗时：{}秒", (end-start)/1000.0);
	}

}
