package com.unison.lottery.weibo.mq;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.unison.lottery.weibo.data.Relationship;
import com.unison.lottery.weibo.msg.service.MessageService;
import com.unison.lottery.weibo.uc.service.RelationshipService;
import com.xhcms.commons.mq.MessageHandler;
import com.xhcms.lottery.commons.data.weibo.DeleteWeiboHandle;
import com.xhcms.lottery.commons.data.weibo.PublishFollowersHandle;

/**
 * 异步删除微博后相关处理：删除微博所在的各个时间线
 * @author haoxiang.jiang@unison.net.cn
 * @time 2014-5-13 下午1:57:35
 */
public class AsyncDeleteWeiboHandler implements MessageHandler<DeleteWeiboHandle>{

	private static final long serialVersionUID = -4368938836178040767L;
	private Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private RelationshipService relationshipService;
	@Autowired
	private MessageService messageService;
	
	@Override
	public void handle(DeleteWeiboHandle deleteWeiboHandle) {
		if(deleteWeiboHandle == null){
			return;
		}
		long postid = deleteWeiboHandle.getPostid();
		long ownerId = deleteWeiboHandle.getOwnerId();
		logger.info("开始删除时间线中包含微博Id={}的值",postid);
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
			messageService.deleteWeiboFromTimeline(users, postid,deleteWeiboHandle.getContent());
		} catch (Exception e) {
			logger.error("处理异步发布微博到关注我的好友失败！微博用户id={}, 微博id={}", ownerId, postid,e);
		}
		end(postid,start);
	}
	private void end(long postid,long start){
		// 记录用时
		long end = System.currentTimeMillis();
		logger.debug("删除时间线中包含微博id={}的值，耗时：{}秒",postid, (end-start)/1000.0);
	}
}
