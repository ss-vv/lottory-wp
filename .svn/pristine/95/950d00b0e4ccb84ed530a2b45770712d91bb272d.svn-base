package com.unison.lottery.weibo.web.action.pc.ajax;

import org.springframework.beans.factory.annotation.Autowired;
import com.unison.lottery.weibo.data.PageResult;
import com.unison.lottery.weibo.data.WeiboUser;
import com.unison.lottery.weibo.web.action.BaseAction;
import com.unison.lottery.weibo.web.service.ActiveUserService;
/**
 * 活跃用户
 * @author haohao
 *
 */
public class ActiveUserAction extends BaseAction{

	private static final long serialVersionUID = 7135054925969911314L;
	@Autowired
	private ActiveUserService activeUserService;
	private PageResult<WeiboUser> data=new PageResult<WeiboUser>();
	
	public String execute(){
		data.setResults(activeUserService.findActiveUser());
		return SUCCESS;
	}
	public PageResult<WeiboUser> getData() {
		return data;
	}
	public void setData(PageResult<WeiboUser> data) {
		this.data = data;
	}
	
}
