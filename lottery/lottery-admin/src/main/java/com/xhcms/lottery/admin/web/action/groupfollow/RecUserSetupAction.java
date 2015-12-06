package com.xhcms.lottery.admin.web.action.groupfollow;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.xhcms.lottery.admin.web.action.BaseAction;
import com.xhcms.lottery.commons.data.User;
import com.xhcms.lottery.commons.persist.service.UserService;
import com.xhcms.lottery.lang.Constants;

public class RecUserSetupAction extends BaseAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5892941206694125686L;
	
    @Autowired
    private UserService userService;
    private Map<String,String> lotteries;
    
    private long id;
    private User user;
	
	@Override
	public String execute() throws Exception {
		setupLotteryTypes();
		user = userService.getUser(id);
		return SUCCESS;
	}

	private void setupLotteryTypes() {
		lotteries = new HashMap<String,String>();
		lotteries.put(Constants.JCZQ, "竞彩足球");
		lotteries.put(Constants.JCLQ, "竞彩篮球");
		lotteries.put(Constants.CTZC, "传统足彩");
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Map<String, String> getLotteries() {
		return lotteries;
	}
}
