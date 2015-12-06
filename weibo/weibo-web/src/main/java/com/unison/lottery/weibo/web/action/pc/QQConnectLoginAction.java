package com.unison.lottery.weibo.web.action.pc;

import java.io.IOException;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.unison.lottery.weibo.web.action.BaseAction;

public class QQConnectLoginAction extends BaseAction {
	private static final long serialVersionUID = 1L;
	private Logger logger = LoggerFactory.getLogger(getClass());

	private String access_token;
	
	public String execute() {
		String p = request.getRequestURL().toString();
		System.out.println(p);
		if(StringUtils.isBlank(access_token)) {
			return INPUT;
		}
		logger.info("QQ互联授权登录access_token:{}", access_token);
		try {
			response.sendRedirect("http://login.davcai.com/open_login.do?authSrc=QQ_Connect_open&code=" + 
					"code" + "&openid=" + "openid" + "&openkey=" + "openkey" + "&referer=http://www.davcai.com/index"
					+ "&failReturnURL=http://www.davcai.com/welcome&accessToken=" + access_token);
		} catch (IOException e) {
			logger.error("",e);
		}
		return NONE;
	}
	public void setAccess_token(String access_token) {
		this.access_token = access_token;
	}
}
