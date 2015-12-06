package com.unison.lottery.weibo.web.action.pc;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.unison.lottery.weibo.common.service.NotificationService;
import com.unison.lottery.weibo.msg.service.CommentService;
import com.unison.lottery.weibo.web.action.BaseAction;
import com.unison.lottery.weibo.web.action.CommentMeType;

/**
 * 评论我的
 * 
 * @author Yang Bo
 */
public class CommentMeAction extends BaseAction {

	private static final long serialVersionUID = 3962117088728540350L;

	private Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private CommentService cmntService;
	
	@Autowired
	private NotificationService notificationService;
	
	// ========== in ==========
	private CommentMeType type = CommentMeType.all;
	private int page;	// 页号，从

	public CommentMeAction(){
		pageRequest.setPageSize(5);
	}
	
	@Override
	public String execute() throws Exception {
		long uid = getUserLaicaiWeiboId();
		if (page == 0){
			page = 1;
		}
		pageRequest.setPageIndex(page);
		
		switch (type) {
		case all:
			pageResult = cmntService.listAllCommentMe(uid, pageRequest);
			break;
		case direct:
			pageResult = cmntService.listDirectReplies(uid, pageRequest);
			break;
		case follow:
			pageResult = cmntService.listCommentsOfMyInterested(uid, pageRequest);
			break;
		default:
			logger.error("未知评论我的列表类型: {}", type);
		}
		
		// 用户查看评论我的列表时，清除用户未读‘评论我的’时间线上的数据
		notificationService.clearUnreadCommentionsTimeline(uid);
		return SUCCESS;
	}

	public CommentMeType getType() {
		return type;
	}

	public void setType(CommentMeType type) {
		this.type = type;
	}

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

}
