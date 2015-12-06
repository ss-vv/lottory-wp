package com.xhcms.ucenter.web.action.password;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.unison.lottery.weibo.data.Result;
import com.unison.lottery.weibo.data.WeiboUser;
import com.unison.lottery.weibo.uc.service.UserAccountService;
import com.unison.lottery.weibo.utils.DateUtil;
import com.xhcms.exception.XHRuntimeException;
import com.xhcms.lottery.commons.data.User;
import com.xhcms.ucenter.action.BaseAction;
import com.xhcms.ucenter.lang.AppCode;
import com.xhcms.ucenter.service.mail.IEmailService;
import com.xhcms.ucenter.service.verify.IVerifyService;

public class WeiboSendEmailAction extends BaseAction {
	private static final long serialVersionUID = 699256788420756728L;
	@Autowired
	private IEmailService emailService;

	@Autowired
	private IVerifyService findPasswordService;
	@Autowired
	private UserAccountService userAccountService;
	
	private Result data = new Result();
	
	private String email;
	private String callbackFunName;
 
	public String execute() {
		WeiboUser user = userAccountService.getWeiboUserByEmail(email);
		if(null == user){
			data.fail("无效的邮箱！");
			
		} else {
			sendEmail(user);
			data.setDesc("邮件发送成功，请根据邮件提示内容完成操作，如未收到邮件，请等待几分钟");
			data.setSuccess(true);
		}
		addActionMessage(getText("get.passwd.send.mail.success"));
		
		try {
			response.getWriter().write(
					callbackFunName + 
					"([ { success:'" + data.isSuccess() + "'," +
							"desc:'" + data.getDesc() + "'}])");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return NONE;
	}

	private void sendEmail(WeiboUser user) {
		// 生成验证码
		String code = findPasswordService.generateCode(user.getId(), user.getEmail());

		// 发送邮件
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("uid", user.getId());
		model.put("code", code);
		model.put("currentTime", DateUtil.getCurrentTime());
		model.put("username", user.getUsername());
		emailService.setMailTemplateFile("weibo_get_pwd.vm");
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

	public String getCallbackFunName() {
		return callbackFunName;
	}

	public void setCallbackFunName(String callbackFunName) {
		this.callbackFunName = callbackFunName;
	}
}
