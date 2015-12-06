package com.xhcms.ucenter.web.action.password;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.xhcms.lottery.commons.data.User;
import com.xhcms.lottery.lang.EntityStatus;
import com.xhcms.ucenter.action.BaseAction;
import com.xhcms.ucenter.service.user.IUserService;

/**
 * <p>Title: 找回密码，填写用户名，查看绑定邮箱</p>
 * <p>Description: </p>
 * <p>Copyright：Copyright (c) 2011</p>
 * <p>Company：XingHui Spirit (Beijing) Technical Co.,Ltd.</p>
 * 
 * @author jiajiancheng
 * @version 1.0.0
 */
public class CheckUserAction extends BaseAction {

    private static final long serialVersionUID = -2209318824389406460L;

    @Autowired
    private IUserService userService;

    private String email;

    private String username;

    private Long uid;

    private String validCode;

    public String execute() {

        if (StringUtils.isBlank(username)) {
            return INPUT;
        }

        if (!validateWaterCode()) {
            addFieldError("validCode", getText("get.passwd.invalid.code"));
            return INPUT;
        }

        User user = userService.getUserByUsername(username);

        if (user == null) {
            addFieldError("username", getText("get.passwd.user.not.exist"));
            return INPUT;
        }

        if ((user.getVerifyStatus() & EntityStatus.VERIFY_EMAIL) != EntityStatus.VERIFY_EMAIL) {
            addActionError(getText("get.passwd.not.bind.email"));
            return ERROR;
        } else {
            email = user.getEmail();
            uid = user.getId();
        }

        return SUCCESS;
    }

    /**
     * 验证水印码
     * 
     * @return
     */
    private boolean validateWaterCode() {
        String waterCodeInSession = (String) request.getSession(true).getAttribute("random");

        if (!StringUtils.isEmpty(validCode) && validCode.equals(waterCodeInSession)) {
            return true;
        }

        return false;
    }

    public String getEmail() {
        return email;
    }

    public Long getUid() {
        return uid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setValidCode(String validCode) {
        this.validCode = validCode;
    }

}
