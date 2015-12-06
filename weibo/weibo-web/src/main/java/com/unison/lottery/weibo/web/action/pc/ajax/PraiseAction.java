package com.unison.lottery.weibo.web.action.pc.ajax;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.unison.lottery.weibo.data.Favourite;
import com.unison.lottery.weibo.data.PageResult;
import com.unison.lottery.weibo.data.PraiseUserResult;
import com.unison.lottery.weibo.lang.Constant;
import com.unison.lottery.weibo.msg.service.MessageService;
import com.unison.lottery.weibo.web.action.BaseAction;

/**
 * 网页微博-采纳
 * @author Wang Lei
 *
 */
public class PraiseAction extends BaseAction{

	private static final long serialVersionUID = 4245738166634751373L;
	private Logger logger = LoggerFactory.getLogger(getClass());
	@Autowired
	private MessageService messageService;
	private Long postId;
	private PraiseUserResult data = new PraiseUserResult();
	
	/**
	 * 采纳
	 */
	public String like() {
		try {
			data.setDesc(messageService.like(getUserLaicaiWeiboId(), postId)+"");
			data.setUserId(getUserLaicaiWeiboId());
			data.setWeiboUser(getUser());
		} catch (Exception e) {
			data.fail(Constant.ResultMessage.ERROR);
			data.setUserId(getUserLaicaiWeiboId());
			data.setWeiboUser(getUser());
			logger.error("用户={}, 采纳微博={}出错！", getUserLaicaiWeiboId() , postId,e);
		}
		return SUCCESS;
	}
	
	/**
	 * 取消采纳
	 * @return
	 */
	public String unLike() {
		try {
			data.setDesc(messageService.unLike(getUserLaicaiWeiboId(), postId)+"");
			data.setUserId(getUserLaicaiWeiboId());
			data.setWeiboUser(getUser());
		} catch (Exception e) {
			data.fail(Constant.ResultMessage.ERROR);
			data.setUserId(getUserLaicaiWeiboId());
			data.setWeiboUser(getUser());
			logger.error("用户={}, 采纳微博={}出错！", getUserLaicaiWeiboId() , postId,e);
		}
		return SUCCESS;
	}
	
	public Long getPostId() {
		return postId;
	}

	public void setPostId(Long postId) {
		this.postId = postId;
	}

	public PraiseUserResult getData() {
		return data;
	}

	public void setData(PraiseUserResult data) {
		this.data = data;
	}
}
