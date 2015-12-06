package com.xhcms.lottery.admin.web.action.user;

import org.springframework.beans.factory.annotation.Autowired;

import com.xhcms.lottery.admin.web.action.BaseAction;
import com.xhcms.lottery.commons.data.User;
import com.xhcms.lottery.commons.data.UserScore;
import com.xhcms.lottery.commons.persist.service.UserScoreService;
import com.xhcms.lottery.commons.persist.service.UserService;
import com.xhcms.lottery.lang.Constants;

public class ViewAction extends BaseAction {

    private static final long serialVersionUID = -595818439204217794L;

    @Autowired
    private UserService userService;
	@Autowired
	private UserScoreService userScoreService;
    private long id;
    private User u;
    private UserScore us;
    
    @Override
    public String execute(){
        u = userService.getUser(id);
        us = userScoreService.getUserScoreByUserIdLotteryId(id, Constants.ZCZ);
        return SUCCESS;
    }

    public void setId(long id) {
        this.id = id;
    }

    public User getU() {
        return u;
    }

	public UserScore getUs() {
		return us;
	}
    
}
