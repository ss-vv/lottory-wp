package com.xhcms.ucenter.web.action.my;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.opensymphony.xwork2.validator.annotations.RequiredStringValidator;
import com.opensymphony.xwork2.validator.annotations.StringLengthFieldValidator;
import com.xhcms.exception.XHRuntimeException;
import com.xhcms.ucenter.action.BaseAction;
import com.xhcms.ucenter.service.user.IUserManager;

/**
 * 
 * <p>Title: 修改密码 </p>
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
    private IUserManager userManager;

    private String oldPwd;
    private String newPwd;
    private String confirmPwd;

    @Override
    public String execute() {
        if (StringUtils.isEmpty(oldPwd)) {
            return INPUT;
        }
        try {
            userManager.updatePasswd(getSelf().getId(), oldPwd, newPwd);
        } catch (XHRuntimeException e) {
            addFieldError("oldPwd", getErrorText(e.getCode()));
            return INPUT;
        }
        addActionMessage(getText("user.pass.success"));
        return SUCCESS;
    }

    @Override
    public void validate() {
        // 验证确认密码
        if (!newPwd.equals(confirmPwd)) {
            this.addFieldError("password", getText("user.pass.MustBeEqual"));
        }
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

    @RequiredStringValidator(key = "user.pass.MustInputPassword")
    public void setConfirmPwd(String confirmPwd) {
        this.confirmPwd = confirmPwd;
    }

}
