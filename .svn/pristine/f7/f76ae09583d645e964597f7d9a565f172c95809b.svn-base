package com.unison.lottery.weibo.common.service.impl;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.unison.lottery.weibo.common.nosql.MessageDao;
import com.unison.lottery.weibo.common.nosql.NotificationDao;
import com.unison.lottery.weibo.common.nosql.impl.Keys;
import com.unison.lottery.weibo.common.service.NotificationService;
import com.unison.lottery.weibo.data.WeiboMsg;
import com.unison.lottery.weibo.data.WeiboNotice;

@Service
public class NotificationServiceImpl implements NotificationService {

	private Logger log = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private NotificationDao notificationDao;
	
	@Autowired
	private MessageDao messageDao;
	
	@Override
	public void addNewFollowers(Long uid, String... beFollowedUids) {
		if (null != uid && uid > 0 && null != beFollowedUids
				&& beFollowedUids.length > 0) {
			log.info("用户id:{},有新的粉丝:{}.", new Object[] {beFollowedUids, uid});
			notificationDao.haveNewFollowers(uid, beFollowedUids);
		} else {
			log.info("无法向用户集合={},的‘新粉丝’时间线上增加对应微博用户索引uid={};存在非法参数.", 
					new Object[]{beFollowedUids, uid});
		}
	}
	
	@Override
	public void addUnreadMetions(String[] uids, Long postId) {
		if(null != uids && uids.length > 0 && postId > 0) {
			for(String uidStr : uids) {
				if(StringUtils.isNotBlank(uidStr)) {
					log.info("用户uids:{},有未读'提到我的'微博:{}.", new Object[] { uids, postId});
					Long uid = Long.parseLong(uidStr);
					notificationDao.haveUnreadMetions(uid, postId);
				}
			}
		} else {
			log.info("无法向用户id={},的未读‘提到我的’时间线上增加对应微博索引postId={};存在非法参数.", 
					new Object[]{uids, postId});
		}
	}
	
	@Override
	public void addUnreadCommentions(Long weiboOwnerId, Long commentId) {
		if(weiboOwnerId > 0 && commentId > 0) {
			log.info("用户id={}，有未读'评论我的'微博, 评论id={}.", new Object[]{weiboOwnerId, commentId});
			notificationDao.haveUnreadCommentions(weiboOwnerId, commentId);
		} else {
			log.info("无法向用户id={},的未读‘评论我的’时间线上增加评论id={};存在非法参数.", 
					new Object[]{weiboOwnerId, commentId});
		}
	}
	
	@Override
	public void addUnreadPrivateMsgs(Long weiboOwnerId, Long privateMsgId) {
		if(weiboOwnerId > 0 && privateMsgId > 0) {
			log.info("用户id={}，有未读私信, 私信id={}.", new Object[]{weiboOwnerId, privateMsgId});
			notificationDao.haveUnreadPrivateMsgs(weiboOwnerId, privateMsgId);
		} else {
			log.info("无法向用户id={},的未读私信时间线上增加私信id={};存在非法参数.", 
					new Object[]{weiboOwnerId, privateMsgId});
		}
	}
	
	@Override
	public void deleteUnreadComment(Long postId, Long commentId) {
		WeiboMsg weiboMsg = messageDao.get(postId);
		if(null != weiboMsg) {
			Long ownerId = weiboMsg.getOwnerId();
			log.info("从微博={}的，用户id={}, 未读‘评论我的’时间线上删除评论={}", 
					new Object[]{postId, ownerId, commentId});
			notificationDao.zrem(Keys.haveUnreadComments(ownerId), String.valueOf(commentId));
		} else {
			log.info("无法删除微博id={}在未读‘评论我的’时间线上的评论id={};存在非法参数.", 
					new Object[]{postId, commentId});
		}
	}
	
	@Override
	public void deleteUnreadMetion(String[] users, Long postId) {
		if(null != users && users.length > 0 && postId > 0) {
			log.info("微博id={},中提到的用户集合={}, 删除对应每个用户未读的'提到我的'时间线上的数据", 
					new Object[]{postId, users});
			for(String user : users) {
				if(StringUtils.isNotBlank(user)) {
					notificationDao.zrem(Keys.haveUnreadMentions(Long.parseLong(user)), 
							String.valueOf(postId));
				}
			}
		} else {
			log.info("无效微博id={}或 用户集合={}, 在删除对应用户未读的'提到我的'时间线上数据时", new Object[]{postId, users});
		}
	}
	
	@Override
	public void clearNewFollowersTimeline(Long uid) {
		if(null != uid && uid > 0) {
			Long clearCount = notificationDao.clearNewFollowersTimeline(uid);		
			log.info("清除用户id:{},未查看'新粉丝'时间线上用户个数:{}", new Object[]{uid, clearCount});
		} else {
			log.info("微博用户id:{}, 不是有效用户, 无法清除未读'新粉丝'时间线上的数据");
		}
	}

	@Override
	public void clearUnreadMetionsTimeline(Long uid) {
		if(null != uid && uid > 0) {
			Long clearCount = notificationDao.clearUnreadMetionsTimeline(uid);
			log.info("清除用户id:{},的未读'提到我的'时间线上@消息条数:{}", new Object[]{uid, clearCount});
		} else {
			log.info("微博用户id:{}, 不是有效用户, 无法清除未读'提到我的'时间线上的数据");
		}
	}

	@Override
	public void clearUnreadCommentionsTimeline(Long uid) {
		if(null != uid && uid > 0) {
			Long clearCount = notificationDao.clearUnreadCommentionsTimeline(uid);
			log.info("清除用户id:{},的未读'评论我的'时间线上评论数：{}", new Object[]{uid, clearCount});
		} else {
			log.info("微博用户id:{}, 不是有效用户, 无法清除未读'评论我的'时间线上的数据");
		}
	}
	
	@Override
	public void clearUnreadPrivateMsgsTimeline(Long uid) {
		if(null != uid && uid > 0) {
			Long clearCount = notificationDao.clearUnreadPrivateMsgsTimeline(uid);
			log.info("清除用户id:{},的未读'私信'时间线上评论数：{}", new Object[]{uid, clearCount});
		} else {
			log.info("微博用户id:{}, 不是有效用户, 无法清除未读'私信'时间线上的数据");
		}
	}

	@Override
	public void cancelFollow(Long uid, String ... followedIds) {
		log.info("用户id:{},取消关注用户id={}", new Object[]{uid, followedIds});
		if(null != uid && uid > 0 && null != followedIds && followedIds.length > 0) {
			for(String followed : followedIds) {
				if(StringUtils.isNotBlank(followed)) {
					notificationDao.removeFollowerOfNewFollowers(uid, Long.valueOf(followed));
				}
			}
		} else {
			log.info("粉丝取消关注时，从未查看'新增粉丝'时间线删除，无法操作无效的用户id={}或被关注者id={}",
					new Object[]{uid, followedIds});
		}
	}

	@Override
	public void isMetionSelf(WeiboMsg weiboMsg) {
		
	}

	@Override
	public Long countNewFollowers(Long uid) {
		return notificationDao.countNewFollowers(uid);
	}

	@Override
	public Long countUnreadMetions(Long uid) {
		return notificationDao.countUnreadMetions(uid);
	}

	@Override
	public Long countUnreadComments(Long uid) {
		return notificationDao.countUnreadComments(uid);
	}

	@Override
	public WeiboNotice lookStatus(Long uid) {
		Long followers = notificationDao.countNewFollowers(uid);
		Long mentions = notificationDao.countUnreadMetions(uid);
		Long comments = notificationDao.countUnreadComments(uid);
		Long privateMsgs = notificationDao.countUnreadPrivateMsgs(uid);
		
		log.info("查看用户:{},的微博通知信息：新粉丝={}个, 提到我的={}条, 评论我的={}条, 新私信={}条",
				new Object[]{uid, followers, mentions, comments, privateMsgs});
		WeiboNotice weiboNotice = new WeiboNotice();
		weiboNotice.setCreatedTime(System.currentTimeMillis());
		if(null != followers) {
			weiboNotice.setFollowers(followers.intValue());
		}
		if(null != mentions) {
			weiboNotice.setMentions(mentions.intValue());
		}
		if(null != comments) {
			weiboNotice.setComments(comments.intValue());
		}
		if(null != privateMsgs) {
			weiboNotice.setPrivateMsgs(privateMsgs.intValue());
		}
		return weiboNotice;
	}
}