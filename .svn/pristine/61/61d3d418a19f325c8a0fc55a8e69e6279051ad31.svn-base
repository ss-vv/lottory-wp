package com.xhcms.ucenter.web.action.verify;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import com.xhcms.ucenter.action.BaseAction;
import com.xhcms.ucenter.service.user.IUserService;
import com.xhcms.ucenter.service.verify.IVerifyService;

/**
 * 手机号码修改
 * @author Wang Lei
 *
 */
public class MobileChangeAction extends BaseAction {

    private static final long serialVersionUID = -8133722634429412469L;

    @Autowired
    private IUserService userService;
    
    @Autowired
    private IVerifyService verifyMobileService;

    private String code;

    private String mobile;

    private String newMobile;

    public String execute() {

        mobile = getSelf().getMobile();
        
        if (StringUtils.isEmpty(code) || StringUtils.isBlank(newMobile)) {
            return INPUT;
        }

        verifyMobileService.verify(getSelf().getId(), code);

        mobile = userService.getUser(getSelf().getId()).getMobile();

        addActionMessage(getText("user.verify.mobile.edit.success"));
        
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
}
