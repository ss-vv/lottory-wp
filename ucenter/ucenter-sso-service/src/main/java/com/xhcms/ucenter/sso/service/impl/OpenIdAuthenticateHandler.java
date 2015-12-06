package com.xhcms.ucenter.sso.service.impl;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.xhcms.lottery.commons.data.User;
import com.xhcms.ucenter.exception.UCException;
import com.xhcms.ucenter.service.user.IUserService;
import com.xhcms.ucenter.sso.principal.Credentials;
import com.xhcms.ucenter.sso.principal.OpenIdCredentials;
import com.xhcms.ucenter.sso.service.IAuthenticateHandler;
import com.xhcms.ucenter.sso.service.IOpenIdAuthService;
import com.xhcms.ucenter.sso.service.IOpenIdServiceFactory;
import com.xhcms.ucenter.sso.service.UserProfile;

public class OpenIdAuthenticateHandler implements IAuthenticateHandler {

	@Autowired
	private IOpenIdServiceFactory openIdServiceFactory;
	@Autowired
	private IUserService userService;
	
	@Override
	public UserProfile authenticate(Credentials credentials) {
		if (!(credentials instanceof OpenIdCredentials)) {
			throw new UCException("need OpenIdCredentials not "
					+ credentials.toString());
		}
		OpenIdCredentials openIdCredentials = (OpenIdCredentials) credentials;
		IOpenIdAuthService openIdAuthService = openIdServiceFactory
				.getOpenIdAuthService(openIdCredentials.getAuthSrc());
		if(null == openIdAuthService){
			return null;
		} else {
			return openIdAuthService.authenticate(openIdCredentials);
		}
	}

	@Override
	public UserProfile loadUserProfile(UserProfile userProfile) {
	User user = null;
		
		if(userProfile.getId() > 0) {
			user = userService.getUser(userProfile.getId());
		} else if(StringUtils.isNotBlank(userProfile.getUsername())) {
			user = userService.getUserByUsername(userProfile.getUsername());
		}
		
		if(user == null) {
			return null;
		}
		
		userProfile.setId(user.getId());
		userProfile.setUsername(user.getUsername());
		userProfile.setTruename(user.getRealname());
		userProfile.setLastLoginIp(user.getLastLoginIp());
		userProfile.setRefresh(false);
		
		return userProfile;
	}

}
