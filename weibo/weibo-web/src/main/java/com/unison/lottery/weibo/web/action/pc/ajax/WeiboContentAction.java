package com.unison.lottery.weibo.web.action.pc.ajax;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.unison.lottery.weibo.data.PageResult;
import com.unison.lottery.weibo.data.WeiboMsgVO;
import com.unison.lottery.weibo.msg.service.MessageService;
import com.unison.lottery.weibo.web.action.BaseAction;

public class WeiboContentAction extends BaseAction {
	private static final long serialVersionUID = 1L;
	private Logger logger = LoggerFactory.getLogger(getClass());
	private String weiboUserId;
	private int page;
	@Autowired
	private MessageService messageService;
	PageResult<WeiboMsgVO> data;
	
	public WeiboContentAction(){
		pageRequest.setPageSize(10);
	}
	
	public String execute(){
		if(StringUtils.isBlank(weiboUserId)){
			return SUCCESS;
		}
		if(page < 1){
			page = 1;
		}
		pageRequest.setPageIndex(page);
		try{
			data = messageService.findMyPost(Long.parseLong(weiboUserId), pageRequest);
			int totalRecords = messageService.weiboCount(weiboUserId).intValue();
			pageRequest.setRecordCount(totalRecords);
			data.setPageRequest(pageRequest);
			data.setUserId(getUserLaicaiWeiboId());
		} catch (NumberFormatException e) {
			logger.warn("非法访问! weiboUserId={}",weiboUserId,e);
		}
		return SUCCESS;
	}
	
	public String getWeiboUserId() {
		return weiboUserId;
	}
	public void setWeiboUserId(String weiboUserId) {
		this.weiboUserId = weiboUserId;
	}
	public int getPage() {
		return page;
	}
	public void setPage(int page) {
		this.page = page;
	}
	public PageResult<WeiboMsgVO> getData() {
		return data;
	}
	public void setData(PageResult<WeiboMsgVO> data) {
		this.data = data;
	}
}
