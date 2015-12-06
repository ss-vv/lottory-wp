package com.unison.lottery.weibo.web.action.pc;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.unison.lottery.weibo.common.service.NotificationService;
import com.unison.lottery.weibo.data.PageResult;
import com.unison.lottery.weibo.data.Relationship;
import com.unison.lottery.weibo.data.WeiboUser;
import com.unison.lottery.weibo.msg.service.MessageService;
import com.unison.lottery.weibo.uc.service.RelationshipService;
import com.unison.lottery.weibo.uc.service.UserAccountService;
import com.unison.lottery.weibo.web.action.BaseAction;

/**
 * @Description:关系相关：关注、取消关注、查询followers、查询followings
 * @author 江浩翔
 * @date 2013-11-21 上午10:44:13
 * @version V1.0
 */
public class RelationshipAction extends BaseAction {
	private Logger logger = LoggerFactory.getLogger(getClass());
	private static final long serialVersionUID = 6795751016966820432L;
	@Autowired
	private RelationshipService relationshipService;
	@Autowired
	private UserAccountService userAccountService;
	
	@Autowired
	private NotificationService notificationService;
	
	@Autowired
	private MessageService messageService;
	
	private PageResult<WeiboUser> data = new PageResult<WeiboUser>();
	private long weiboUserId = 0;
	private int page = 0;
	public RelationshipAction(){
		pageRequest.setPageSize(10);
	}
	
	private String[] followingsIds;
	
	public String getFollowingId(){
		followingsIds = relationshipService.findFollowingByUid(getUserLaicaiWeiboId()).getFollowings();
		return SUCCESS;
	}
	
	/** 查询粉丝 */
	public String findFollowers() {
		initPageConf();
		if(weiboUserId < 1){
			weiboUserId = getUserLaicaiWeiboId();
		}
		if(0 == weiboUserId){
			data.fail("查询粉丝数量失败");
			return SUCCESS;
		}
		try {
			Relationship relationship = relationshipService
					.findFollowersByUid(weiboUserId,pageRequest);
			List<WeiboUser> follower = userAccountService
					.findWeiboUserByWeiboUids(relationship.getFollowers());
			user = getUser();
			if(null != user){
				follower = relationshipService.isFollower("" + user.getWeiboUserId(), follower);
				follower = relationshipService.isFollowing("" + user.getWeiboUserId(), follower);
				
				//用户查看粉丝时，清除用户未查看‘新增粉丝’时间线上的数据
				notificationService.clearNewFollowersTimeline(user.getWeiboUserId());
			}
			if(null != follower){
				for (WeiboUser w : follower) {
					int wFollowersCount = relationshipService.findFollowersByUid(w.getWeiboUserId()).getFollowers().length;
					w.setFollowerCount(wFollowersCount);
					int wFollowingCount = relationshipService.findFollowingByUid(w.getWeiboUserId()).getFollowings().length;
					w.setFollowingCount(wFollowingCount);
					w.setWeiboCount(messageService.weiboCount(w.getWeiboUserId() + "").intValue());
				}
			}
			data.setResults(follower);
			data.setPageRequest(pageRequest);
			data.setDesc("查询粉丝成功");
			return SUCCESS;
		} catch (RuntimeException e) {
			logger.error("查询粉丝失败", e);
			data.fail("查询粉丝失败");
			data.setResults(null);
			return ERROR;
		}
	}

	/** 查询关注 */
	public String findFollowings() {
		initPageConf();
		if(weiboUserId < 1){
			weiboUserId = getUserLaicaiWeiboId();
		}
		if(0 == weiboUserId){
			data.fail("查询关注数量失败");
			return SUCCESS;
		}
		try {
			Relationship relationship = relationshipService
					.findFollowingByUid(weiboUserId,pageRequest);
			List<WeiboUser> following = userAccountService
					.findWeiboUserByWeiboUids(relationship.getFollowings());
			user = getUser();
			if(null != user){
				following = relationshipService.isFollowing(""+user.getWeiboUserId(), following);
				following = relationshipService.isFollower(""+user.getWeiboUserId(), following);
			}
			if(null != following){
				for (WeiboUser w : following) {
					int wFollowersCount = relationshipService.findFollowersByUid(w.getWeiboUserId()).getFollowers().length;
					w.setFollowerCount(wFollowersCount);
					int wFollowingCount = relationshipService.findFollowingByUid(w.getWeiboUserId()).getFollowings().length;
					w.setFollowingCount(wFollowingCount);
					w.setWeiboCount(messageService.weiboCount(w.getWeiboUserId() + "").intValue());
				}
			}
			data.setResults(following);
			data.setPageRequest(pageRequest);
			data.setTotalResults(pageRequest.getRecordCount());
			data.setDesc("查询关注成功");
			return SUCCESS;
		} catch (RuntimeException e) {
			logger.error("查询关注失败", e);
			data.fail("查询关注失败");
			data.setResults(null);
			return ERROR;
		}
	}
	
	private void initPageConf(){
		if(page < 1){
			page = 1; 
		}
		pageRequest.setPageIndex(page);
		data.setPageRequest(pageRequest);
	}
	public PageResult<WeiboUser> getData() {
		return data;
	}

	public void setWeiboUserId(long weiboUserId) {
		this.weiboUserId = weiboUserId;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public String[] getFollowingsIds() {
		return followingsIds;
	}

	public void setFollowingsIds(String[] followingsIds) {
		this.followingsIds = followingsIds;
	}
}
