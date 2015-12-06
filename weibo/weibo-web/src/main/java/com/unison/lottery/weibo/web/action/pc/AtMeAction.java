package com.unison.lottery.weibo.web.action.pc;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.unison.lottery.weibo.common.service.NotificationService;
import com.unison.lottery.weibo.data.PageResult;
import com.unison.lottery.weibo.data.WeiboMsgVO;
import com.unison.lottery.weibo.msg.service.AtUserService;
import com.unison.lottery.weibo.web.action.BaseAction;

/**
 * 网页微博-提到我的
 * @author Wang Lei
 *
 */
public class AtMeAction extends BaseAction{

	private static final long serialVersionUID = -1079535644437265452L;
	private Logger logger = LoggerFactory.getLogger(getClass());
	@Autowired
	private AtUserService atUserService;
	private Long postId;
	private PageResult<WeiboMsgVO> data = new PageResult<WeiboMsgVO>();
	
	@Autowired
	private NotificationService notificationService;

	public String execute() {
		try {
		} catch (Exception e) {
			logger.error("用户={}, 查询提到我的出错！", getUserLaicaiWeiboId(),e);
		}
		return SUCCESS;
	}
	
	/**
	 * 提到我的列表
	 */
	public String load() {
		try {
			setData(atUserService.findAtMeList(getUserLaicaiWeiboId(), pageRequest));
			data.setUserId(getUserLaicaiWeiboId());
			
			//用户查看提到我的列表时，清除用户未读‘提到我的’时间线上的数据
			notificationService.clearUnreadMetionsTimeline(getUserLaicaiWeiboId());
		} catch (Exception e) {
			logger.error("用户={}, 查询提到我的出错！", getUserLaicaiWeiboId(),e);
		}
		return SUCCESS;
	}
	
	public Long getPostId() {
		return postId;
	}

	public void setPostId(Long postId) {
		this.postId = postId;
	}

	public PageResult<WeiboMsgVO> getData() {
		return data;
	}

	public void setData(PageResult<WeiboMsgVO> data) {
		this.data = data;
	}
}
