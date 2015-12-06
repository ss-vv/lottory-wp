package com.xhcms.ucenter.web.action.password;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.xhcms.exception.XHRuntimeException;
import com.xhcms.ucenter.action.BaseAction;
import com.xhcms.ucenter.lang.AppCode;
import com.xhcms.ucenter.service.mail.IEmailService;
import com.xhcms.ucenter.service.verify.IVerifyService;

/**
 * <p>Title: 向绑定邮箱发送设置新密码请求</p>
 * <p>Description: </p>
 * <p>Copyright：Copyright (c) 2011</p>
 * <p>Company：XingHui Spirit (Beijing) Technical Co.,Ltd.</p>
 * 
 * @author jiajiancheng
 * @version 1.0.0
 */
public class SendEmailAction extends BaseAction {

    private static final long serialVersionUID = -1027217987710599090L;

    @Autowired
    private IEmailService emailService;

    @Autowired
    private IVerifyService findPasswordService;

    private String email;

    private Long uid;

    public String execute() {

        sendEmail(uid, email);

        addActionMessage(getText("get.passwd.send.mail.success"));
        
        return SUCCESS;
    }

    private void sendEmail(long uid, String email) {
        // 生成验证码
        String code = findPasswordService.generateCode(uid, email);

        // 发送邮件
        Map<String, Object> model = new HashMap<String, Object>();
        model.put("uid", uid);
        model.put("code", code);
        emailService.setMailTemplateFile("getpwd.vm");
        try {
            emailService.sendEmail(model, email, "找回密码");
        } catch (Exception e) {
            e.printStackTrace();
            throw new XHRuntimeException(AppCode.VERIFY_EMAIL_SEND_FAILURE);
        }
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setUid(Long uid) {
        this.uid = uid;
    }

}
