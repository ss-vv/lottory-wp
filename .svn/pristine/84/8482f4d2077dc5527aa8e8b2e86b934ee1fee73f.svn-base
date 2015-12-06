package com.unison.lottery.weibo.web.action.mobile;

import java.util.HashMap;
import java.util.List;
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
 * 手机微博首页
 * 
 * @author Wang Lei
 * 
 */
public class HomeAction extends BaseAction {

	private static final long serialVersionUID = -8700565177834874849L;
	private Logger logger = LoggerFactory.getLogger(getClass());
	@Autowired
	private UserAccountService userAccountService;
	@Autowired
	private MessageService messageService;
	private Map<Long, WeiboUser> usersMap = new HashMap<Long, WeiboUser>();

	@SuppressWarnings("unchecked")
	public String execute() {
		try {
			setPageResult(messageService.findAllPost(getUserLaicaiWeiboId(), pageRequest));
			setUsersMap(userAccountService.findWeiboUserByWeiboUids(Tool
					.findUserIds((List<WeiboMsgVO>) pageResult.getResults())));
		} catch (Exception e) {
			logger.error("用户={}, 查询所有微博出错！", getUserLaicaiWeiboId(),e);
		}
		return SUCCESS;
	}

	public Map<Long, WeiboUser> getUsersMap() {
		return usersMap;
	}

	public void setUsersMap(Map<Long, WeiboUser> usersMap) {
		this.usersMap = usersMap;
	}

}
