package com.unison.lottery.weibo.web.action.mobile;

import java.io.IOException;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.opensymphony.xwork2.Action;
import com.unison.lottery.weibo.web.action.BaseAction;

public class QQWeiboLoginAction extends BaseAction {
	private static final long serialVersionUID = 1L;
	private Logger logger = LoggerFactory.getLogger(getClass());

	private String code;
	private String openid;
	private String openkey;
	
	public String execute() {
		if(StringUtils.isBlank(code) || 
				StringUtils.isBlank(openid) ||
				StringUtils.isBlank(openkey)) {
			return Action.LOGIN;
		}
		logger.info("QQ微博授权登录code:{}", code);
		logger.info("QQ微博授权登录openid:{}", openid);
		logger.info("QQ微博授权登录openkey:{}", openkey);
		try {
			response.sendRedirect("http://login.davcai.com/open_login.do?authSrc=QQ_open&code=" + 
					code + "&openid=" + openid + "&openkey=" + openkey + "&referer=http://www.davcai.com/index"
					+ "&failReturnURL=http://www.davcai.com/welcome");
		} catch (IOException e) {
			logger.error("QQ微博登录跳转失败",e);
		}
		return NONE;
	}
	
	public void setCode(String code) {
		this.code = code;
	}

	public void setOpenid(String openid) {
		this.openid = openid;
	}

	public void setOpenkey(String openkey) {
		this.openkey = openkey;
	}
}
