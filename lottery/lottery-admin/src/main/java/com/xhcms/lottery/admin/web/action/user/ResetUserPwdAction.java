package com.xhcms.lottery.admin.web.action.user;

import org.springframework.beans.factory.annotation.Autowired;

import com.xhcms.lottery.admin.web.action.BaseAction;
import com.xhcms.lottery.commons.persist.service.UserService;

public class ResetUserPwdAction extends BaseAction {

    private static final long serialVersionUID = -595818439204237794L;

    @Autowired
    private UserService userService;
    private long id;
    private String userPwd;
    
    @Override
    public String execute(){
    	userPwd = userService.resetPassword(id);
        return SUCCESS;
    }

    public void setId(long id) {
        this.id = id;
    }

	public String getUserPwd() {
		return userPwd;
	}

	public void setUserPwd(String userPwd) {
		this.userPwd = userPwd;
	}

}
