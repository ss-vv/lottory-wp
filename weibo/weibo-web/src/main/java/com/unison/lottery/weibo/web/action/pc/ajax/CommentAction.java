package com.unison.lottery.weibo.web.action.pc.ajax;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.unison.lottery.weibo.data.PageResult;
import com.unison.lottery.weibo.data.vo.CommentVO;
import com.unison.lottery.weibo.msg.service.CommentService;
import com.unison.lottery.weibo.web.action.BaseAction;

public class CommentAction extends BaseAction {
	private static final long serialVersionUID = 1L;
	private Logger logger = LoggerFactory.getLogger(getClass());
	private String weiboUserId;
	private int page;
	@Autowired
	CommentService commentService;
	
	private PageResult<CommentVO> data;
	
	public CommentAction(){
		pageRequest.setPageSize(10);
	}
	
	/**	我的全部评论 */
	public String myComments(){
		if(StringUtils.isBlank(weiboUserId)){
			return SUCCESS;
		}
		if(page < 1){
			page = 1;
		}
		pageRequest.setPageIndex(page);
		try{
			data = commentService.listMyComments(Long.parseLong(weiboUserId), pageRequest);
			long curLoginUid = getUserLaicaiWeiboId();
			if(curLoginUid != 0 && curLoginUid == Long.parseLong(weiboUserId)){
				data.setUserId(curLoginUid);
			} else {
				data.setUserId(0L);
			}
		} catch (NumberFormatException e) {
			logger.warn("非法访问! weiboUserId={}",weiboUserId,e);
		}
		return SUCCESS;
	}
	
	public PageResult<CommentVO> getData() {
		return data;
	}

	public void setWeiboUserId(String weiboUserId) {
		this.weiboUserId = weiboUserId;
	}

	public void setPage(int page) {
		this.page = page;
	}
}
