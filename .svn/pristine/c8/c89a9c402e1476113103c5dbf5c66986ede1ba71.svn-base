package com.xhcms.lottery.admin.web.action;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.xhcms.commons.lang.Data;
import com.xhcms.lottery.admin.data.Admin;
import com.xhcms.lottery.admin.lang.AdminConstant;
import com.xhcms.lottery.admin.persist.service.AdminManager;
import com.xhcms.lottery.admin.web.action.captcha.CaptchaServiceSingleton;

public class LoginAction extends BaseAction {
	private static final long serialVersionUID = -145060649751919608L;
	@Autowired
	private AdminManager adminManager;
	private String username;
	private String password;
	private Data data;
	private String captcha;
	
	@Override
	public String execute() {
	    Admin user = (Admin)request.getSession().getAttribute(AdminConstant.USER);
	    if(user != null){
	        return SUCCESS;
	    }
	    
	    if(StringUtils.isNotBlank(username) && StringUtils.isNotBlank(password)){
	        user = adminManager.login(username, password);
	        if(null != captcha) {
	        	String captchaId = request.getSession().getId();
	        	boolean respCorrect = CaptchaServiceSingleton.getInstance().validateResponseForID(captchaId, captcha);
	        	if(!respCorrect) {
	        		addActionError("验证码输入有误");
	        		return INPUT;
	        	}
	        }
	        if(user != null){
	            request.getSession().setAttribute(AdminConstant.USER, user);
	        }
	    }
	    
	    if(user == null){
	        data = Data.failure("用户名或密码错误");
            return INPUT;
	    }
		return SUCCESS;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Data getData() {
		return data;
	}
	
	public void setCaptcha(String captcha) {
		this.captcha = captcha;
	}
	
}
