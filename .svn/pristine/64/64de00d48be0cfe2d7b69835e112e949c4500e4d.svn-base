package com.unison.lottery.weibo.web.action.pc.ajax;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.unison.lottery.weibo.data.PageResult;
import com.unison.lottery.weibo.data.SearchResult;
import com.unison.lottery.weibo.data.WeiboMsgVO;
import com.unison.lottery.weibo.data.WeiboUser;
import com.unison.lottery.weibo.lang.Constant;
import com.unison.lottery.weibo.msg.service.MessageService;
import com.unison.lottery.weibo.uc.service.RelationshipService;
import com.unison.lottery.weibo.web.action.BaseAction;
import com.unison.lottery.weibo.web.service.SearchService;

public class SearchAction extends BaseAction {
	private static final long serialVersionUID = 1L;
	private SearchResult data = new SearchResult();
	private String keys;
	private String weiboType;
	
	private int page;//记录当前页
	private int size;//页大小
	@Autowired
	private SearchService searchService;
	@Autowired
	private MessageService messageService;
	@Autowired
	private RelationshipService relationshipService;
	
	public SearchAction(){
		pageRequest.setPageSize(10);
	}
	
	public String searchUser(){
		if(StringUtils.isBlank(keys)){
			return "toSearchUserPage";
		}
		if (page == 0){
			page = 1;
		}
		if(size != 0){
			pageRequest.setPageSize(size);
		}
		pageRequest.setPageIndex(page);
		PageResult<WeiboUser> users = new PageResult<WeiboUser>();
		List<WeiboUser> u = searchService.querryUser(keys,pageRequest);
		if(null != u){
			for (WeiboUser w : u) {
				int wFollowersCount = relationshipService.findFollowersByUid(w.getWeiboUserId()).getFollowers().length;
				w.setFollowerCount(wFollowersCount);
				int wFollowingCount = relationshipService.findFollowingByUid(w.getWeiboUserId()).getFollowings().length;
				w.setFollowingCount(wFollowingCount);
				w.setWeiboCount(messageService.weiboCount(w.getWeiboUserId() + "").intValue());
				relationshipService.isFollowing(getUserLaicaiWeiboId()+"", w);
			}
		}
		users.setResults(u);
		users.setPageRequest(pageRequest);
		users.setTotalResults(pageRequest.getRecordCount());
		users.setSuccess(true);
		data.setUsers(users);
		return SUCCESS;
	}
	public String searchWeibo(){
		if(StringUtils.isBlank(keys)){
			return "toSearchWeiboPage";
		}
		if (page == 0){
			page = 1;
		}
		weiboType = (StringUtils.isBlank(weiboType) ? "0" : weiboType);
		pageRequest.setPageIndex(page);
		PageResult<WeiboMsgVO> weiboMsgVOs = new PageResult<WeiboMsgVO>();
		weiboMsgVOs.setResults(searchService.querryWeiboMsg(keys,pageRequest,
				weiboType));
		weiboMsgVOs.setPageRequest(pageRequest);
		weiboMsgVOs.setTotalResults(pageRequest.getRecordCount());
		weiboMsgVOs.setSuccess(true);
		weiboMsgVOs.setUserId(getUserLaicaiWeiboId());
		if(loadUserInfo()){
			long uid = user.getWeiboUserId();
			messageService.checkFavoriateAndLike(uid, weiboMsgVOs);
		}
		data.setWeiboMsgVOs(weiboMsgVOs);
		return SUCCESS;
	}
	
	public String execute(){
		return SUCCESS;
	}

	public boolean loadUserInfo(){
		Object o = context.getSession().get(Constant.User.USER);
		if (null == o) {
			return false;
		}
		user = (WeiboUser) o;
		return true;
	}
	
	public SearchResult getData() {
		return data;
	}
	public void setData(SearchResult data) {
		this.data = data;
	}
	public String getKeys() {
		return keys;
	}
	public void setKeys(String keys) {
		this.keys = keys;
	}
	public int getPage() {
		return page;
	}
	public void setPage(int page) {
		this.page = page;
	}
	public int getSize() {
		return size;
	}
	public void setSize(int size) {
		this.size = size;
	}
	public String getWeiboType() {
		return weiboType;
	}
	public void setWeiboType(String weiboType) {
		this.weiboType = weiboType;
	}
}
