package com.xhcms.ucenter.web.action.verify;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.xhcms.lottery.lang.EntityStatus;
import com.xhcms.ucenter.action.BaseAction;
import com.xhcms.ucenter.service.user.IUserService;
import com.xhcms.ucenter.service.verify.IVerifyService;

/**
 * 手机号码验证
 * @author Wang Lei
 *
 */
public class MobileAction extends BaseAction {

    private static final long serialVersionUID = -8133722634429412469L;

    @Autowired
    private IUserService userService;
    
    @Autowired
    private IVerifyService verifyMobileService;

    private String code;

    private String mobile;
    
    private String newMobile;

    private boolean verify;

    public String execute() {

        mobile = getSelf().getMobile();
        verify = (getSelf().getVerifyStatus() & EntityStatus.VERIFY_MOBILE) == EntityStatus.VERIFY_MOBILE;
        
        if (StringUtils.isEmpty(code)) {
            return INPUT;
        }

        verifyMobileService.verify(getSelf().getId(), code);

        mobile = userService.getUser(getSelf().getId()).getMobile();
        verify = true;
        addActionMessage(getText("user.verify.mobile.success"));
        
        return SUCCESS;
    }

    public String getMobile() {
        return mobile;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setNewMobile(String newMobile) {
        this.newMobile = newMobile;
    }

    public String getNewMobile() {
        return newMobile;
    }

    public boolean isVerify() {
        return verify;
    }
    
}
