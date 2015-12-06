package com.xhcms.ucenter.web.action.verify;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.xhcms.exception.XHRuntimeException;
import com.xhcms.lottery.commons.data.User;
import com.xhcms.lottery.lang.EntityStatus;
import com.xhcms.ucenter.action.BaseAction;
import com.xhcms.ucenter.lang.AppCode;
import com.xhcms.ucenter.service.mail.IEmailService;
import com.xhcms.ucenter.service.user.IUserService;
import com.xhcms.ucenter.service.verify.IVerifyService;

/**
 * 
 * <p>Title: 验证邮箱</p>
 * <p>Description: </p>
 * <p>Copyright：Copyright (c) 2011</p>
 * <p>Company：XingHui Spirit (Beijing) Technical Co.,Ltd.</p>
 * 
 * @author jiajiancheng
 * @version 1.0.0
 */
public class EmailAction extends BaseAction {

    private static final long serialVersionUID = 309246331354809190L;

    @Autowired
    private IVerifyService bindEmailService;

    @Autowired
    private IEmailService mailService;

    @Autowired
    private IUserService userService;

    private String email;

    private User user;

    private boolean verify;

    public String execute() {
        user = getSelf();
        verify = (user.getVerifyStatus() & EntityStatus.VERIFY_EMAIL) == EntityStatus.VERIFY_EMAIL;

        if (StringUtils.isEmpty(email)) {
            return INPUT;
        }

		if (userService.emailValidAndBinded(email)) {
			addActionMessage(getErrorText(AppCode.VERIFY_EMAIL_IS_BIND));
			return INPUT;
		}

        sendEmail(user.getId(), user.getUsername(), email);
        addActionMessage(getText("user.verify.email.success"));

        return SUCCESS;
    }

    private void sendEmail(long uid, String username, String email) {
        // 生成验证码
        String code = bindEmailService.generateCode(uid, email);

        // 发送邮件
        Map<String, Object> model = new HashMap<String, Object>();
        model.put("uid", uid);
        model.put("code", code);
        model.put("username", username);
        model.put("currentTime", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
        mailService.setMailTemplateFile("bindemail.vm");
        try {
            mailService.sendEmail(model, email, "验证邮箱");
        } catch (Exception e) {
            e.printStackTrace();
            throw new XHRuntimeException(AppCode.VERIFY_EMAIL_SEND_FAILURE);
        }
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public User getUser() {
        return user;
    }

    public boolean isVerify() {
        return verify;
    }

}
