package com.unison.lottery.weibo.web.action.mobile;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.unison.lottery.weibo.data.Relationship;
import com.unison.lottery.weibo.data.Result;
import com.unison.lottery.weibo.data.WeiboUser;
import com.unison.lottery.weibo.lang.Constant;
import com.unison.lottery.weibo.uc.service.RelationshipService;
import com.unison.lottery.weibo.uc.service.UserAccountService;

public class FollowingAction extends ActionSupport {
	private static final long serialVersionUID = 1L;
	private Logger logger = LoggerFactory.getLogger(getClass());
	@Autowired
	private RelationshipService relationshipService;
	@Autowired
	private UserAccountService userAccountService;
	
	private ArrayList<String> followingIds;
	
	private String from;
	
	/**ajax data*/
	private List<WeiboUser> followings = new ArrayList<WeiboUser>();
	private List<WeiboUser> allUsers = new ArrayList<WeiboUser>();
	private List<WeiboUser> followers = new ArrayList<WeiboUser>();
	/**ajax data*/
	private Result result;
	
	public String following(){
		init();
		WeiboUser weiboUser = (WeiboUser)ActionContext.getContext().getSession().get(Constant.User.USER);
		try {
			relationshipService.saveFollower(weiboUser.getWeiboUserId(), parseObject2String(followingIds.toArray()));
			Relationship relationship = relationshipService.findFollowingByUid(weiboUser.getWeiboUserId());
			followings = userAccountService.findWeiboUserByWeiboUids(relationship.getFollowings());
			result.setDesc("关注成功");
			result.setResultCode(Constant.AccountCode.FollowResultCode.FOLLOW_SUCCESS);
			return SUCCESS;
		} catch (RuntimeException e) {
			logger.error("添加关注异常！",e);
			result.setDesc("关注失败");
			result.setResultCode(Constant.AccountCode.FollowResultCode.FOLLOW_ERROR);
			return ERROR;
		}
	}
	
	private void init(){
		this.result = new Result();
		result.setResultCode(Constant.AccountCode.FollowResultCode.FOLLOW_ERROR);
		result.setDesc("关注失败");
	}
	
	public String unFollowing(){
		WeiboUser weiboUser = (WeiboUser)ActionContext.getContext().getSession().get(Constant.User.USER);
		try {
			relationshipService.deleteFollower(weiboUser.getWeiboUserId(), parseObject2String(followingIds.toArray()));
			return SUCCESS;
		} catch (RuntimeException e) {
			logger.error("取消关注异常！",e);
			return ERROR;
		}
	}
	
	public String followList(){
		WeiboUser weiboUser = (WeiboUser)ActionContext.getContext().getSession().get(Constant.User.USER);
		try {
			Relationship relationship;
			relationship = relationshipService.findFollowingByUid(weiboUser.getWeiboUserId());
			followings = userAccountService.findWeiboUserByWeiboUids(relationship.getFollowings());
			logger.info("我关注的----------------------------");
			for (int i = 0; i < followings.size(); i++) {
				logger.info(followings.get(i).getWeiboUserId().toString());
			}
			
			relationship = relationshipService.findFollowersByUid(weiboUser.getWeiboUserId());
			followers = userAccountService.findWeiboUserByWeiboUids(relationship.getFollowers());
			logger.info("关注我的----------------------------");
			for (int i = 0; i < followers.size(); i++) {
				logger.info(followers.get(i).getWeiboUserId().toString());
			}
			
			logger.info("全部的----------------------------");
			//===========test==============
			String[] a = new String[10];
			for (int i = 0; i < 10; i++) {
				a[i] = "" + (i + 10);
			}
			allUsers = userAccountService.findWeiboUserByWeiboUids(a);
			for (int i = 0; i < allUsers.size(); i++) {
				logger.info(allUsers.get(i).getWeiboUserId().toString());
			}
			return SUCCESS;
		} catch (RuntimeException e) {
			logger.error("关注列表异常！",e);
			return ERROR;
		}
	}
	
	private String[] parseObject2String(Object[] orig){
		String[] string = new String[orig.length];
		for (int i = 0; i < orig.length; i++) {
			string[i] = orig[i].toString();
		}
		return string;
	}
	public void setFollowingIds(ArrayList<String> followingIds) {
		this.followingIds = followingIds;
	}

	public Result getResult() {
		return result;
	}

	public List<WeiboUser> getFollowings() {
		return followings;
	}

	public List<WeiboUser> getFollowers() {
		return followers;
	}

	public List<WeiboUser> getAllUsers() {
		return allUsers;
	}
	
	public String getFrom() {
		return from;
	}

	public void setFrom(String from) {
		this.from = from;
	}

}
