package com.unison.lottery.weibo.web.action.pc;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.unison.lottery.weibo.data.PageResult;
import com.unison.lottery.weibo.data.WeiboUser;
import com.unison.lottery.weibo.data.WeiboUserStatis;
import com.unison.lottery.weibo.msg.service.RecommendService;
import com.unison.lottery.weibo.web.action.BaseAction;
import com.unison.lottery.weibo.web.service.RecomendUserService;
import com.unison.lottery.weibo.web.service.UserStatisService;

public class AddRecUserAction extends BaseAction {

	private static final long serialVersionUID = -6440899883421781743L;

	private int page;
	private PageResult<WeiboUserStatis> pageResult;
	
	private String currentUserId;
	
	List<WeiboUser> data = new ArrayList<WeiboUser>();
	List<WeiboUser> ltUsers = new ArrayList<WeiboUser>();
	List<WeiboUser> recUsers = new ArrayList<WeiboUser>();
	List<WeiboUserStatis> guoDanLvUsers = new ArrayList<WeiboUserStatis>();
	List<WeiboUserStatis> bonusUsers = new ArrayList<WeiboUserStatis>();
	List<WeiboUserStatis> activeUsers = new ArrayList<WeiboUserStatis>();
	List<WeiboUserStatis> users = new ArrayList<WeiboUserStatis>();
	@Autowired
	private RecomendUserService recomendUserService;
	@Autowired
	private RecommendService recommendService;
	@Autowired
	private UserStatisService userStatisService;
	
	public String execute() {
		currentUserId = getUserLaicaiWeiboId()+"";
		guoDanLvUsers = recomendUserService.getGuoDanLvTop10WeiboUser(currentUserId);
		bonusUsers = recomendUserService.getBonusTop10WeiboUser(currentUserId);
		activeUsers = recomendUserService.getActiveWeiboUser(currentUserId);
		return SUCCESS;
	}
	
	public String addLotteryUser(){
		ltUsers = recomendUserService.getLotteryWeiboUser();
		return SUCCESS;
	}
	
	public String addRecUser(){
		users = recomendUserService.getActiveWeiboUser(getUserLaicaiWeiboId()+"");
		return SUCCESS; 
	}
	
	public String getActiveUsersByPage(){
		currentUserId = getUserLaicaiWeiboId()+"";
		if(page < 1){
			page =1;
		}
		pageRequest.setPageSize(9);
		pageRequest.setPageIndex(page);
		pageResult = recomendUserService.getActiveWeiboUser(currentUserId,pageRequest);
		pageResult.setUserId(getUserLaicaiWeiboId());
		return SUCCESS;
	}
	
	public String userList(){
		data = recomendUserService.getRecWeiboUser();
		return SUCCESS;
	}
	
	public List<WeiboUser> getData() {
		return data;
	}

	public List<WeiboUser> getLtUsers() {
		return ltUsers;
	}

	public List<WeiboUserStatis> getGuoDanLvUsers() {
		return guoDanLvUsers;
	}

	public List<WeiboUserStatis> getBonusUsers() {
		return bonusUsers;
	}

	public List<WeiboUserStatis> getActiveUsers() {
		return activeUsers;
	}

	public PageResult<WeiboUserStatis> getPageResult() {
		return pageResult;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public String getCurrentUserId() {
		return currentUserId;
	}

	public void setCurrentUserId(String currentUserId) {
		this.currentUserId = currentUserId;
	}

	public List<WeiboUser> getRecUsers() {
		return recUsers;
	}

	public List<WeiboUserStatis> getUsers() {
		return users;
	}
}
