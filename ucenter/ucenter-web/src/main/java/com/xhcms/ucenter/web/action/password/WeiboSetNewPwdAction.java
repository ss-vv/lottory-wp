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
 * @Description:大V彩微博：忘记密码-设置新密码 
 * @author haoxiang.jiang 
 * @date 2013-12-27 上午11:22:58 
 * @version V1.0
 */
public class WeiboSetNewPwdAction extends BaseAction {

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
    	try {
			findPasswordService.verifyNotUpdate(uid, code);
		} catch (XHRuntimeException e) {
			addActionMessage(getText("非法访问。"));
			return SUCCESS;
		}
    	
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
			addActionMessage(getText("非法访问。"));
			return SUCCESS;
		}
        
        userManager.updatePasswd(uid, password);

        addActionMessage(getText("修改密码成功！请用新密码重新登录大V彩。"));
        
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
