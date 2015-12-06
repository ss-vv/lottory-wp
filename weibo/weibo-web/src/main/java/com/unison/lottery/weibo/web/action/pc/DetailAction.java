package com.unison.lottery.weibo.web.action.pc;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.unison.lottery.weibo.data.PageResult;
import com.unison.lottery.weibo.data.WeiboMsgVO;
import com.unison.lottery.weibo.msg.service.MessageService;
import com.unison.lottery.weibo.web.action.BaseAction;

/**
 * 网页微博详情页
 * 
 * @author Wang Lei
 * 
 */
public class DetailAction extends BaseAction {

	private static final long serialVersionUID = -8700565177834871349L;
	private Logger logger = LoggerFactory.getLogger(getClass());
	@Autowired
	private MessageService messageService;
	
	private PageResult<WeiboMsgVO> data = new PageResult<WeiboMsgVO>();
	private String postId;

	public String execute() {
		return SUCCESS;
	}
	
	public String load(){
		try {
			List<WeiboMsgVO> list = new ArrayList<WeiboMsgVO>();
			WeiboMsgVO weibo = messageService.getWeiboVoById(Long.parseLong(postId));
			
			weibo.setLikeUsers(messageService.findLikeWeiboUser(""+weibo.getId()));
			if(weibo != null){
				list.add(weibo);
			}
			setData(new PageResult<>(pageRequest, list));
			//messageService.addMyFollowingData(getUserLaicaiWeiboId(), getData());
			data.setUserId(getUserLaicaiWeiboId());
		} catch (Exception e) {
			logger.error("用户={}, 查询所有微博出错！", getUserLaicaiWeiboId(),e);
		}
		return SUCCESS;
	}
	
	public String getPostId() {
		return postId;
	}

	public void setPostId(String postId) {
		this.postId = postId;
	}

	public PageResult<WeiboMsgVO> getData() {
		return data;
	}

	public void setData(PageResult<WeiboMsgVO> data) {
		this.data = data;
	}
}
