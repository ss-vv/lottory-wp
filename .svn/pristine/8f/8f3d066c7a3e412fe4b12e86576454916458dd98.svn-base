package com.xhcms.ucenter.sso.service.impl;

import org.springframework.beans.factory.annotation.Autowired;

import com.xhcms.ucenter.lang.Constant;
import com.xhcms.ucenter.sso.service.IOpenIdAuthService;
import com.xhcms.ucenter.sso.service.IOpenIdServiceFactory;

public class OpenIdServiceFactory implements IOpenIdServiceFactory {
	
	@Autowired
	private IOpenIdAuthService sinaOpenIdAuthService;
	
	@Autowired
	private IOpenIdAuthService qqConnectAuthService;
	
	@Autowired
	private IOpenIdAuthService alipayOpenIdAuthService;
	
	@Autowired
	private IOpenIdAuthService weixinOpenIdAuthService;
	
	@Override
	public IOpenIdAuthService getOpenIdAuthService(String openIdName) {
		if(Constant.SINA_OPEN_SERVICE_NAME.equals(openIdName)){
			return sinaOpenIdAuthService;
		} else if(Constant.QQ_CONNECT_SERVICE_NAME.equals(openIdName)){
			return qqConnectAuthService;
		} else if(Constant.ALIPAY_OPEN_SERVICE_NAME.equals(openIdName)){
			return alipayOpenIdAuthService;
		} else if(Constant.WEIXIN_OPEN_SERVICE_NAME.equals(openIdName)){
			return weixinOpenIdAuthService;
		}
		return null;
	}
}
	
