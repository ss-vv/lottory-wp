package com.xhcms.ucenter.web.action.password;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.xhcms.exception.XHRuntimeException;
import com.xhcms.lottery.commons.data.User;
import com.xhcms.ucenter.action.BaseAction;
import com.xhcms.ucenter.service.user.IUserManager;
import com.xhcms.ucenter.service.user.IUserService;
import com.xhcms.ucenter.service.verify.IVerifyService;

/**
 * <p>Title: 修改密码</p>
 * <p>Description: </p>
 * <p>Copyright：Copyright (c) 2011</p>
 * <p>Company：XingHui Spirit (Beijing) Technical Co.,Ltd.</p>
 * 
 * @author jiajiancheng
 * @version 1.0.0
 */
public class EditPasswordAction extends BaseAction {

    private static final long serialVersionUID = -7763702286366174263L;

    @Autowired
    private IVerifyService findPasswordService;

    @Autowired
    private IUserManager userManager;

    @Autowired
    private IUserService userService;

    private String code;

    private String password;

    private Long uid;
    
    private String username;

    public String execute() {

        User user = userService.getUser(uid);
        if(user != null){
            username = user.getUsername();
        }
        
        if(StringUtils.isBlank(password)){
            return INPUT;
        }
        
        try {
            findPasswordService.verify(uid, code);
        } catch (XHRuntimeException e) {
            addActionMessage(getErrorText(e.getCode()));
            return SUCCESS;
        }

        userManager.updatePasswd(uid, password);

        addActionMessage(getText("get.passwd.success"));
        
        return SUCCESS;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setUid(Long uid) {
        this.uid = uid;
    }

    public String getCode() {
        return code;
    }

    public Long getUid() {
        return uid;
    }

    public String getUsername() {
        return username;
    }

}
