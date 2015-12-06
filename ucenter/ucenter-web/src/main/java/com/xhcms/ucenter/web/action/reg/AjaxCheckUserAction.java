/**
 * 
 */
package com.xhcms.ucenter.web.action.reg;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.unison.lottery.weibo.uc.service.UserAccountService;
import com.xhcms.ucenter.action.BaseAction;
import com.xhcms.ucenter.service.user.IUserService;

/**
 * @author bean
 * 
 */
public class AjaxCheckUserAction extends BaseAction {

    private static final long serialVersionUID = -6891213737565007232L;
    private Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    private IUserService userService;

    private String email;

    private String idnumber;

    private String mobile;

    private String username;
    
    private boolean data = false;
    
    private String nickname;
    @Autowired
    private UserAccountService userAccountService;
    
    public String nickname() throws Exception {
    	if(null == nickname){
    		logger.debug("nickname == null");
    		return SUCCESS;
    	}
    	logger.debug("nickname = {}",nickname);
    	data = userAccountService.isNicknameUnique(nickname);
    	return SUCCESS;
    }
    
    @Override
    public String execute() throws Exception {

        if (null == userService.getUserByUsername(username)) {
            data = true;
        }
        return SUCCESS;
    }

    public String idnumber() {
        if (!userService.idNumberValid(idnumber)) {
            data = true;
        }
        return SUCCESS;
    }

    public String mobile() {
        if (!userService.mobileValidAndBinded(mobile)) {
            data = true;
        }
        return SUCCESS;
    }

    public String email() {
		if (!userService.emailValidAndBinded(email)) {
			data = true;
		}
        return SUCCESS;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public void setIdnumber(String idnumber) {
        this.idnumber = idnumber;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public boolean isData() {
        return data;
    }

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
}
