package com.xhcms.lottery.account.web.action.my;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.opensymphony.xwork2.validator.annotations.RequiredStringValidator;
import com.opensymphony.xwork2.validator.annotations.StringLengthFieldValidator;
import com.xhcms.lottery.account.service.UserService;
import com.xhcms.lottery.account.web.action.BaseAction;
import com.xhcms.lottery.commons.persist.service.AccountService;
import com.xhcms.lottery.lang.AppCode;
import com.xhcms.ucenter.sso.client.UserProfile;

/**
 * 
 * <p>Title: 修改提现密码 </p>
 * <p>Description: </p>
 * <p>Copyright：Copyright (c) 2011</p>
 * <p>Company：XingHui Spirit (Beijing) Technical Co.,Ltd.</p>
 * 
 * @author xulang
 * @version 1.0.0
 */
public class PasswdAction extends BaseAction {
	private static final long serialVersionUID = 885728603968609801L;
	@Autowired
	private UserService userService;
	@Autowired
	private AccountService accountService;
	
	private String loginPwd;
	private String oldPwd;
	private String newPwd;
	private String confirmPwd;
	
	@Override
	public String execute() {
		UserProfile profile = getUser();
		// 检查两次输入是否一致
		if (StringUtils.isNotEmpty(newPwd) && !newPwd.equals(confirmPwd)) {
			this.addFieldError("newPwd", getErrorText(AppCode.PASSWD_MUSTBEEQUAL));
		}
		// 检查登录密码
		if (!userService.checkPasswd(profile.getId(), loginPwd)) {
			addFieldError("loginPwd", getErrorText(AppCode.PASSWD_LOGIN_WRONG));
			return INPUT;
		}
		// 检查原提现密码
		if (!accountService.checkPasswd(profile.getId(), oldPwd)) {
			addFieldError("oldPwd", getErrorText(AppCode.PASSWD_WH_WRONG));
			return INPUT;
		}
		accountService.updatePasswd(profile.getId(), newPwd);
		addActionMessage(getText("message.success"));
		return SUCCESS;
	}
	
	public String getOldPwd() {
		return oldPwd;
	}
	
	@RequiredStringValidator(key = "user.pass.MustInputOldpass")
	public void setOldPwd(String oldPwd) {
		this.oldPwd = oldPwd;
	}

	public String getNewPwd() {
		return newPwd;
	}

	@RequiredStringValidator(key = "user.pass.MustInputPassword")
	@StringLengthFieldValidator(key = "user.pass.PasswordLength", maxLength = "20", minLength = "6")
	public void setNewPwd(String newPwd) {
		this.newPwd = newPwd;
	}

	public String getConfirmPwd() {
		return confirmPwd;
	}
	
	@RequiredStringValidator(key = "user.pass.MustInputCfPassword")
	public void setConfirmPwd(String confirmPwd) {
		this.confirmPwd = confirmPwd;
	}

	@RequiredStringValidator(key = "user.pass.MustInputLoginpass")
	public void setLoginPwd(String loginPwd) {
		this.loginPwd = loginPwd;
	}

    public String getLoginPwd() {
        return loginPwd;
    }

}
