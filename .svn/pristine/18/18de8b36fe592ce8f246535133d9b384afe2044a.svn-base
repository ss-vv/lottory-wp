package com.unison.lottery.weibo.web.action.pc.ajax;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;

import com.unison.lottery.weibo.data.PageResult;
import com.unison.lottery.weibo.data.WeiboUser;
import com.unison.lottery.weibo.web.action.BaseAction;
import com.unison.lottery.weibo.web.service.InterestUserService;

public class InterestUserAction extends BaseAction {
	private static final long serialVersionUID = 1L;
	
	@Autowired
	private InterestUserService interestUserService;
	private int size;
	private Set<String> excludeUids = new HashSet<>();
	private PageResult<WeiboUser> data = new PageResult<WeiboUser>();
	
	public InterestUserAction(){
		size = 1;
	}
	public String execute(){
		if(getUserLaicaiWeiboId() < 1){
			data.setResults(new ArrayList<WeiboUser>());
			return SUCCESS;
		}
		excludeUids.add(getUserLaicaiWeiboId() + "");
		data.setResults(interestUserService.findUserByTogetherFollow(getUserLaicaiWeiboId(), excludeUids, size));
		return SUCCESS;
	}
	
	public void setSize(int size) {
		this.size = size;
	}
	public void setExcludeUids(Set<String> excludeUids) {
		this.excludeUids = excludeUids;
	}
	public PageResult<WeiboUser> getData() {
		return data;
	}
}
