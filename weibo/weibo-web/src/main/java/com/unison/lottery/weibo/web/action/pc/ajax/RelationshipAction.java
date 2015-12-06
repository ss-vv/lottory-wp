package com.unison.lottery.weibo.web.action.pc.ajax;

import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.unison.lottery.weibo.data.PageResult;
import com.unison.lottery.weibo.data.Relationship;
import com.unison.lottery.weibo.data.WeiboUser;
import com.unison.lottery.weibo.msg.service.MessageService;
import com.unison.lottery.weibo.uc.data.UserQueryCondition;
import com.unison.lottery.weibo.uc.service.RelationshipService;
import com.unison.lottery.weibo.uc.service.UserAccountService;
import com.unison.lottery.weibo.web.action.BaseAction;

public class RelationshipAction extends BaseAction{
	private Logger logger = LoggerFactory.getLogger(getClass());
	private static final long serialVersionUID = 1L;
	@Autowired
	private RelationshipService relationshipService;
	@Autowired
	private UserAccountService userAccountService;
	
	PageResult<WeiboUser> data = new PageResult<WeiboUser>();
	private int page;
	
	/** 关注的id集合 */
	private Set<String> followIds;
	/** 未关注的id集合 */
	private Set<String> unfollowIds;
	
	private long weiboUserId = 0;
	
	/** 用户查询条件 */
	private UserQueryCondition userQueryCondition;
	
	@Autowired
	private MessageService messageService;
	
	public RelationshipAction(){
		pageRequest.setPageSize(10);
	}
	
	public String getFollowings(){
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
			data.setDesc("查询关注成功");
			return SUCCESS;
		} catch (RuntimeException e) {
			logger.error("查询关注失败", e);
			data.fail("查询关注失败");
			data.setResults(null);
			return SUCCESS;
		}
	}
	
	public String getFollowers(){
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
			return SUCCESS;
		}
	}
	private void initPageConf(){
		if(page < 1){
			page = 1; 
		}
		pageRequest.setPageIndex(page);
	}
	
	/** 根据条件查询用户 */
	public String findWeiboUser() {
		try {
			if (null != userQueryCondition) {

			} else {
				// ===========test===========
				logger.info("全部的----------------------------");
				String[] a = new String[20];
				for (int i = 0; i < 20; i++) {
					a[i] = "" + i;
				}
				List<WeiboUser> weiboUsers = 
						userAccountService.findWeiboUserByWeiboUids(a);
				// weiboUsers = relationshipService.isFollowing(user
				// .getWeiboUserId().toString(), weiboUsers);
				// weiboUsers = relationshipService.isFollower(user
				// .getWeiboUserId().toString(), weiboUsers);
				data.setResults(weiboUsers);
				return SUCCESS;
			}
			return SUCCESS;
		} catch (RuntimeException e) {
			logger.error("查询失败", e);
			data.setDesc("查询失败");
			data.setResults(null);
			return ERROR;
		}
	}
	
	public String countFollowers() {
		if(weiboUserId < 1){
			weiboUserId = getUserLaicaiWeiboId();
		}
		if(0 == weiboUserId){
			data.fail("查询粉丝数量失败");
			return SUCCESS;
		}
		try {
			Relationship relationship = relationshipService
					.findFollowersByUid(weiboUserId);
			if(null == relationship.getFollowers() || relationship.getFollowers().length < 1){
				pageRequest.setRecordCount(0);
			} else {
				pageRequest.setRecordCount(relationship.getFollowers().length);
			}
			data.setPageRequest(pageRequest);
			data.setDesc("查询粉丝数量成功");
			return SUCCESS;
		} catch (RuntimeException e) {
			logger.error("查询粉丝数量失败", e);
			data.fail("查询粉丝数量失败");
			data.setResults(null);
			return SUCCESS;
		}
	}

	public String countFollowings() {
		if(weiboUserId < 1){
			weiboUserId = getUserLaicaiWeiboId();
		}
		if(0 == weiboUserId){
			data.fail("查询关注数量失败");
			return SUCCESS;
		}
		try {
			Relationship relationship = relationshipService
					.findFollowingByUid(weiboUserId);
			if(null == relationship.getFollowings() || relationship.getFollowings().length < 1){
				pageRequest.setRecordCount(0);
			} else {
				pageRequest.setRecordCount(relationship.getFollowings().length);
			}
			data.setPageRequest(pageRequest);
			data.setDesc("查询关注数量成功");
			return SUCCESS;
		} catch (RuntimeException e) {
			logger.error("查询关注数量失败", e);
			data.fail("查询关注数量失败");
			data.setResults(null);
			return SUCCESS;
		}
	}
	
	/** 关注 */
	public String follow() {
		user = getUser();
		if (null == user || null == followIds) {
			data.fail("请登录！");
			data.setResultCode("notlogin");
			return SUCCESS;
		}
		if(null != followIds){
			if(followIds.remove(user.getWeiboUserId()+"")){
				data.fail("关注失败,不能关注自己");
				return SUCCESS;
			}
		}
		try {
			relationshipService.saveFollower(user.getWeiboUserId(),
					parseObject2String(followIds.toArray()));
			logger.info("用户:" + user.getNickName() + "&id={},关注了用户：{}",
					user.getWeiboUserId(), followIds.toString());
			data.setDesc("关注成功");
			return SUCCESS;
		} catch (RuntimeException e) {
			logger.error("关注失败", e);
			data.fail("关注失败");
			return SUCCESS;
		}
	}

	/** 取消关注 */
	public String unfollow() {
		user = getUser();
		if (null == user || null == unfollowIds) {
			data.fail("请登录！");
			data.setResultCode("notlogin");
			return SUCCESS;
		}
		if(null != unfollowIds){
			if(unfollowIds.remove(user.getWeiboUserId()+"")){
				data.fail("取消关注失败，关注和取消关注功能不能对自己操作！");
				return SUCCESS;	
			}
		}
		
		try {
			relationshipService.deleteFollower(user.getWeiboUserId(),
					parseObject2String(unfollowIds.toArray()));
			logger.info("用户:" + user.getNickName() + "&id={},取消关注了用户：{}",
					user.getWeiboUserId(), unfollowIds.toString());
			data.setDesc("取消关注成功");
			return SUCCESS;
		} catch (RuntimeException e) {
			logger.error("取消关注失败", e);
			data.fail("取消关注失败");
			return SUCCESS;
		}
	}
	private String[] parseObject2String(Object[] orig) {
		String[] string = new String[orig.length];
		for (int i = 0; i < orig.length; i++) {
			string[i] = orig[i].toString();
		}
		return string;
	}
	//---------------getters and setters-------------------
	public PageResult<WeiboUser> getData() {
		return data;
	}

	public void setData(PageResult<WeiboUser> data) {
		this.data = data;
	}

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public void setFollowIds(Set<String> followIds) {
		this.followIds = followIds;
	}

	public void setUnfollowIds(Set<String> unfollowIds) {
		this.unfollowIds = unfollowIds;
	}

	public void setWeiboUserId(long weiboUserId) {
		this.weiboUserId = weiboUserId;
	}

	public void setUserQueryCondition(UserQueryCondition userQueryCondition) {
		this.userQueryCondition = userQueryCondition;
	}
}
