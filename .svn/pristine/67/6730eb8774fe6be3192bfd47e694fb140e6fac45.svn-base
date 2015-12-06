package com.unison.lottery.weibo.common.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.unison.lottery.weibo.common.data.FollowCommand;
import com.unison.lottery.weibo.common.data.PublishCommand;
import com.unison.lottery.weibo.common.data.TimeLineType;
import com.unison.lottery.weibo.common.service.AsyncService;
import com.unison.lottery.weibo.data.WeiboMsg;
import com.xhcms.commons.mq.MessageSender;

/**
 * 异步服务实现。
 * 
 * @author Yang Bo
 */
@Service
public class AsyncServiceImpl implements AsyncService {

	private Logger logger = LoggerFactory.getLogger(getClass());
	
    @Autowired(required=false)
    private MessageSender messageSender;
    
	@Override
	public void notifyFollowers(WeiboMsg weibo, TimeLineType timeline) {
		// 构建发布命令对象
		PublishCommand pubCmd = new PublishCommand(weibo, timeline);
		// 发布到消息队列
		if (messageSender == null){
			logger.info("messageSender 为 null了！");
			return;
		}
		messageSender.send(pubCmd);
	}

	@Override
	public void followLotteryUser(long followerUid, long followingUid) {
		boolean isFollowing = true;
		// 构建发布命令对象
		FollowCommand cmd = new FollowCommand(followerUid, followingUid, isFollowing);
		// 发布到消息队列
		messageSender.send(cmd);
	}

	@Override
	public void unFollowLotteryUser(long followerUid, long followingUid) {
		boolean isFollowing = false;
		// 构建发布命令对象
		FollowCommand cmd = new FollowCommand(followerUid, followingUid, isFollowing);
		// 发布到消息队列
		messageSender.send(cmd);
	}
}
