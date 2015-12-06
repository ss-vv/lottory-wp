package com.xhcms.ucenter.web.action.verify;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import com.xhcms.commons.lang.Data;
import com.xhcms.commons.mq.MessageSender;
import com.xhcms.lottery.commons.event.SMSSendMessage;
import com.xhcms.lottery.lang.EntityStatus;
import com.xhcms.ucenter.action.BaseAction;
import com.xhcms.ucenter.service.user.IUserService;
import com.xhcms.ucenter.service.verify.IVerifyService;

/**
 * 发送手机验证码
 * @author Wang Lei
 *
 */
public class AjaxSendCodeAction extends BaseAction {

    private static final long serialVersionUID = -5313063909809107186L;

    @Autowired
    private MessageSender messageSender;

    @Autowired
    private IUserService userService;
    
    @Autowired
    private IVerifyService verifyMobileService;

    private String mobile;

    private Data data;

    public String execute() {
    	if(StringUtils.isBlank(mobile)){
    		data = Data.failure(getText("user.regist.MustInputMobile"));
    	}else if ((getSelf().getVerifyStatus() & EntityStatus.VERIFY_MOBILE) == EntityStatus.VERIFY_MOBILE) {
			data = Data.failure(getText("user.verify.mobile.is.bind"));
		} else {
			this.sendSMS();
		}
        return SUCCESS;
    }

    public String changeMobile(){
    	if(StringUtils.isBlank(mobile)){
    		data = Data.failure(getText("user.regist.MustInputMobile"));
    		return SUCCESS;
    	}
    	if(userService.getUserByVerifyedMobile(mobile) != null){
    		data = Data.failure(getText("user.verify.mobile.is.bind"));
		} else {
			this.sendSMS();
		}
    	return SUCCESS;
    }
    
    private void sendSMS(){
    	String code = verifyMobileService.generateCode(getSelf().getId(), mobile);
		SMSSendMessage sm = new SMSSendMessage();
		sm.setMobile(mobile);
		//sm.setContent(getText("user.verify.mobile.content", new String[] { getSelf().getUsername(), code }));
		sm.setContent(code);
		//sm.setExt("00000");
		//sm.setMsgId("12345");
		messageSender.send(sm);
		data = Data.success("验证码已发出，如没有收到，请重新发送验证码");
    }
    
    public Data getData() {
        return data;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

}
