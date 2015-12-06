package com.unison.weibo.admin.action.msg;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.opensymphony.xwork2.ActionContext;
import com.unison.lottery.weibo.common.nosql.PrivateMessageDao;
import com.unison.lottery.weibo.common.nosql.impl.Keys;
import com.unison.lottery.weibo.common.service.PrivateMessageService;
import com.unison.lottery.weibo.data.PageRequest;
import com.unison.lottery.weibo.data.PageResult;
import com.unison.lottery.weibo.data.PrivateMessage;
import com.unison.lottery.weibo.data.WeiboUser;
import com.unison.lottery.weibo.uc.service.UserAccountService;
import com.unison.weibo.admin.action.BaseAction;

/**
 * 群发私信
 * @author haoxiang.jiang@unison.net.cn
 * @time 2014-6-19 下午1:34:36
 */
public class PushPrivateMsgAction  extends BaseAction{
	private static final long serialVersionUID = 1L;
	@Autowired
	private PrivateMessageDao privateMessageDao;
	@Autowired
	private UserAccountService userAccountService;
	@Autowired
	private PrivateMessageService privateMessageService;
	
	private List<WeiboUser> users = new ArrayList<WeiboUser>();
	
	private String ownerId;
	
	private List<String>  receivers;
	
	private String privateMsgContent;
	
	private boolean data;
	
	private PageRequest pageRequest;
	
	private PageResult<PrivateMessage> pageResult;
	
	private int page;
	
	public String execute(){
		if(page <= 0){
			page = 1;
		}
		pageRequest = new PageRequest();
		pageRequest.setPageSize(10);
		pageRequest.setPageIndex(page);
		Object usersO = ActionContext.getContext().getSession().get("ALL_USERS");
		if(null == usersO){
			LinkedHashSet<String> weiboUserIds = privateMessageDao.zrange(Keys.WEIBO_USERS, 0, -1);
			for (String userId : weiboUserIds) {
				users.add(userAccountService.findWeiboUserByWeiboUid(userId));
			}
			ActionContext.getContext().getSession().put("ALL_USERS", users);
		} else {
			users = (List<WeiboUser>) usersO;
			LinkedHashSet<String> weiboUserIds = privateMessageDao.zrange(Keys.WEIBO_USERS, 0, -1);
			if(weiboUserIds.size() != users.size()){
				users = new ArrayList<WeiboUser>();
				for (String userId : weiboUserIds) {
					users.add(userAccountService.findWeiboUserByWeiboUid(userId));
				}
				ActionContext.getContext().getSession().put("ALL_USERS", users);
			}
		}
		pageResult = privateMessageService.showMyPostedPrivateMsg("619145310522368", pageRequest);
		return SUCCESS;
	}
	
	public String postPrivateMsg(){
		PrivateMessage p = new PrivateMessage();
		p.setContent(privateMsgContent);
		p.setOwnerId(Long.parseLong(ownerId));
		if(privateMessageService.sendSystemPrivateMsg(p, receivers) > 0){
			data = true;
		} else {
			data = false;
		}
		return SUCCESS;
	}
	
	public List<WeiboUser> getUsers() {
		return users;
	}

	public String getOwnerId() {
		return ownerId;
	}

	public void setOwnerId(String ownerId) {
		this.ownerId = ownerId;
	}

	public List<String> getReceivers() {
		return receivers;
	}

	public void setReceivers(List<String> receivers) {
		this.receivers = receivers;
	}

	public boolean isData() {
		return data;
	}

	public void setData(boolean data) {
		this.data = data;
	}

	public String getPrivateMsgContent() {
		return privateMsgContent;
	}

	public void setPrivateMsgContent(String privateMsgContent) {
		this.privateMsgContent = privateMsgContent;
	}

	public PageRequest getPageRequest() {
		return pageRequest;
	}

	public void setPageRequest(PageRequest pageRequest) {
		this.pageRequest = pageRequest;
	}

	public PageResult<PrivateMessage> getPageResult() {
		return pageResult;
	}

	public void setPageResult(PageResult<PrivateMessage> pageResult) {
		this.pageResult = pageResult;
	}

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}
}
