package com.unison.lottery.weibo.web.action.mobile;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.unison.lottery.weibo.data.WeiboMsgVO;
import com.unison.lottery.weibo.data.WeiboUser;
import com.unison.lottery.weibo.msg.service.MessageService;
import com.unison.lottery.weibo.uc.service.UserAccountService;
import com.unison.lottery.weibo.utils.Tool;
import com.unison.lottery.weibo.web.action.BaseAction;

/**
 * 手机微博详情页
 * 
 * @author Wang Lei
 * 
 */
public class DetailAction extends BaseAction {

	private static final long serialVersionUID = 3014414614453033605L;
	private Logger logger = LoggerFactory.getLogger(getClass());
	@Autowired
	private UserAccountService userAccountService;
	@Autowired
	private MessageService messageService;
	private Map<Long, WeiboUser> usersMap = new HashMap<Long, WeiboUser>();
	private Long postId;
	private WeiboMsgVO post;

	public String execute() {
		try {
			setPost(messageService.getWeiboVoById(postId));
			setUsersMap(userAccountService.findWeiboUserByWeiboUids(Tool
					.findUserIds(post)));
		} catch (Exception e) {
			logger.error("用户={}, 查询微博详情出错！postId={}", getUserLaicaiWeiboId(),postId,e);
		}
		return SUCCESS;
	}

	public Map<Long, WeiboUser> getUsersMap() {
		return usersMap;
	}

	public void setUsersMap(Map<Long, WeiboUser> usersMap) {
		this.usersMap = usersMap;
	}

	public Long getPostId() {
		return postId;
	}

	public void setPostId(Long postId) {
		this.postId = postId;
	}

	public WeiboMsgVO getPost() {
		return post;
	}

	public void setPost(WeiboMsgVO post) {
		this.post = post;
	}

}
