package com.unison.lottery.weibo.web.action.pc;

import java.io.IOException;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.opensymphony.xwork2.Action;
import com.unison.lottery.weibo.web.action.BaseAction;

public class WeixinLoginAction extends BaseAction {
	private static final long serialVersionUID = 1L;
	private Logger logger = LoggerFactory.getLogger(getClass());
	private String code;

	public String execute() {
		if(StringUtils.isBlank(code)) {
			return Action.LOGIN;
		}
		logger.info("微信授权登录code:{}", code);
		try {
			response.sendRedirect("http://login.davcai.com/open_login.do?authSrc=weixin_open&code=" + 
					code + "&referer=http://www.davcai.com/index"
					+ "&failReturnURL=http://www.davcai.com/welcome");
		} catch (IOException e) {
			logger.error("微信登录跳转失败",e);
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
