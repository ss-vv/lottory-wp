package com.unison.lottery.weibo.mq;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.unison.lottery.weibo.data.Relationship;
import com.unison.lottery.weibo.msg.service.MessageService;
import com.unison.lottery.weibo.uc.service.RelationshipService;
import com.xhcms.commons.mq.MessageHandler;
import com.xhcms.lottery.commons.data.weibo.PublishFollowersHandle;

/**
 * 大V彩用户异步发送微博到关注我的人
 * @author Wang Lei
 *
 */
public class AsyncPublishFollowersHandler implements MessageHandler<PublishFollowersHandle>{

	private static final long serialVersionUID = -4368938836178040767L;
	private Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private RelationshipService relationshipService;
	@Autowired
	private MessageService messageService;
	
	@Override
	public void handle(PublishFollowersHandle followersHandle) {
		if(followersHandle == null){
			return;
		}
		double score = followersHandle.getScore();
		long postid = followersHandle.getPostid();
		long ownerId = followersHandle.getOwnerId();
		logger.info("开始异步发布微博到关注我的好友。");
		long start = System.currentTimeMillis();
		// 处理
		try {
			String[] users;
			Relationship relationship = relationshipService.findFollowersByUid(ownerId);
			if(null != relationship && relationship.getFollowers() != null 
					&& relationship.getFollowers().length > 0){
				users = new String[relationship.getFollowers().length + 1];
				System.arraycopy(relationship.getFollowers(), 0, users, 0, users.length - 1);
				users[users.length - 1] = ownerId+"";
			}else{
				users = new String[]{ownerId+""};
			}
			messageService.postToAllposts(users , score, postid);
		} catch (Exception e) {
			logger.error("处理异步发布微博到关注我的好友失败！微博用户id={}, 微博id={}", ownerId, postid,e);
		}
		end(start);
	}
	private void end(long start){
		// 记录用时
		long end = System.currentTimeMillis();
		logger.debug("处理异步发布微博到关注我的好友结束，耗时：{}秒", (end-start)/1000.0);
	}
}
