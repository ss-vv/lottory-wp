package com.unison.lottery.weibo.web.action.mobile;

import java.io.IOException;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.unison.lottery.weibo.web.action.BaseAction;

public class SinaWeiboLoginAction extends BaseAction {
	private static final long serialVersionUID = 1L;
	private Logger logger = LoggerFactory.getLogger(getClass());
	private String code;
	
	public String execute() {
		if(StringUtils.isBlank(code)) {
			return "login_mobile";
		}
		logger.info("新浪微博授权登录code:{}", code);
		try {
			response.sendRedirect("http://login.davcai.com/open_login.do?authSrc=sina_open&code=" + 
					code + "&referer=http://www.davcai.com/index"
					+ "&failReturnURL=http://www.davcai.com/welcome");
		} catch (IOException e) {
			logger.error("新浪微博登录跳转失败",e);
		}
		return NONE;
	}
	
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

}
