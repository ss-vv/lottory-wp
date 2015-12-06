package com.unison.lottery.weibo.mq;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.unison.lottery.newsextract.lang.NewsType;
import com.unison.lottery.weibo.data.Relationship;
import com.unison.lottery.weibo.msg.service.MessageService;
import com.unison.lottery.weibo.uc.service.RelationshipService;
import com.xhcms.commons.mq.MessageHandler;
import com.xhcms.lottery.commons.data.weibo.PublishWeiboNewsHandle;

/**
 * 异步处理微博新闻
 * @author haoxiang.jiang@unison.com.cn
 * @date 2014-3-17 下午2:28:59
 */
public class AsyncPublishWeiboNewsHandler implements MessageHandler<PublishWeiboNewsHandle>{

	private static final long serialVersionUID = -8732886926528769656L;
	private Logger logger = LoggerFactory.getLogger(getClass());
	@Autowired
	private RelationshipService relationshipService;
	@Autowired
	private MessageService messageService;
	@Override
	public void handle(PublishWeiboNewsHandle publishWeiboNewsHandle) {
		logger.debug("开始处理'发布新闻到粉丝'异步命令...");
		if(publishWeiboNewsHandle == null){
			return;
		}
		if(StringUtils.isNotBlank(publishWeiboNewsHandle.getNewsType())){
			handleLotteryNews(publishWeiboNewsHandle);
		}
		if(StringUtils.isNotBlank(publishWeiboNewsHandle.getTeamId())){
			handleTeamNews(publishWeiboNewsHandle);
		}
		logger.debug("处理'发布新闻到粉丝'异步命令结束...");
	}

	private void handleTeamNews(PublishWeiboNewsHandle publishWeiboNewsHandle) {
		
	}
	private void handleLotteryNews(PublishWeiboNewsHandle publishWeiboNewsHandle) {
		double score = publishWeiboNewsHandle.getScore();
		long postid = publishWeiboNewsHandle.getPostid();
		long ownerId = publishWeiboNewsHandle.getOwnerId();
		logger.info("开始异步发布微博新闻到关注我的好友。");
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
			messageService.postNewsToAllposts(users , score, postid);
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
