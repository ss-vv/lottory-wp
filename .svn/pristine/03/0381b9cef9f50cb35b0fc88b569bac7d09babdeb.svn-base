/**
 * 
 */
package com.xhcms.ucenter.sso.service.impl;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.xhcms.lottery.commons.data.User;
import com.xhcms.ucenter.exception.UCException;
import com.xhcms.ucenter.lang.EnumLoginType;
import com.xhcms.ucenter.service.user.IUserManager;
import com.xhcms.ucenter.service.user.IUserService;
import com.xhcms.ucenter.sso.principal.Credentials;
import com.xhcms.ucenter.sso.principal.UsernamePasswordCredentials;
import com.xhcms.ucenter.sso.service.IAuthenticateHandler;
import com.xhcms.ucenter.sso.service.UserProfile;

/**
 * @author bean
 *
 */
public class UsernamePasswordAuthenticationHandler implements
		IAuthenticateHandler {
	
	@Autowired
	private IUserManager userManager;
	@Autowired
	private IUserService userService;
	
	@Override
	public UserProfile authenticate(Credentials credentials) {
		if(!(credentials instanceof UsernamePasswordCredentials)) {
			throw new UCException("error credentials");
		}
		
		UsernamePasswordCredentials usernamePasswordCredentials 
			= (UsernamePasswordCredentials)credentials;
		
		boolean success = userManager.login(usernamePasswordCredentials.getLoginType(), usernamePasswordCredentials.getUsername(), 
				usernamePasswordCredentials.getPassword());
		
		if(success == false) {
			return null;
		}
		User user = new User();
		if (EnumLoginType.USERNAME.getValue() == usernamePasswordCredentials.getLoginType()) {
            user = userService.getUserByUsername(usernamePasswordCredentials.getUsername());
        } else if (EnumLoginType.MOBILE.getValue() == usernamePasswordCredentials.getLoginType()) {
        	user = userService.getUserByVerifyedMobile(usernamePasswordCredentials.getUsername());
        }
		
		UserProfile userProfile = new UserProfile();
		
		userProfile.setId(user.getId());
		userProfile.setUsername(user.getUsername());
		userProfile.setTruename(user.getRealname());
		userProfile.setLastLoginIp(user.getLastLoginIp());
		userProfile.setLastLoginTime(user.getLastLoginTime());
		userProfile.setRefresh(false);
		
		return userProfile;
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
