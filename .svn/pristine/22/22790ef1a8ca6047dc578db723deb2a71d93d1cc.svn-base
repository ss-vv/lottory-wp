package com.xhcms.ucenter.sso.service.impl;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.qq.connect.QQConnectException;
import com.qq.connect.api.OpenID;
import com.qq.connect.api.qzone.UserInfo;
import com.xhcms.lottery.commons.data.User;
import com.xhcms.ucenter.persist.redis.dao.IOpenUserDao;
import com.xhcms.ucenter.service.user.IUserService;
import com.xhcms.ucenter.sso.principal.OpenIdCredentials;
import com.xhcms.ucenter.sso.service.IOpenIdAuthService;
import com.xhcms.ucenter.sso.service.OpenUserProfile;
import com.xhcms.ucenter.sso.service.UserProfile;

public class QQConnectAuthService implements IOpenIdAuthService {
	Logger logger = LoggerFactory.getLogger(getClass());
	@Autowired
	IOpenUserDao openUserDao;
	@Autowired
	private IUserService userService;
	
	@Override
	public UserProfile authenticate(OpenIdCredentials openIdCredentials) {
		String accessToken = openIdCredentials.getAccessToken();
		if(StringUtils.isBlank(accessToken)){
			return null;
		}
		OpenID openIDObj =  new OpenID(accessToken);
        String openID;
		try {
			openID = openIDObj.getUserOpenID();
			openIdCredentials.setOpenid(openID);
			UserInfo qzoneUserInfo = new UserInfo(accessToken, openID);
			qzoneUserInfo.getUserInfo();
			logger.info("QQ互联登录成功，accessToken:{}，openUid:{}", accessToken, openID);
			String username = openUserDao.getLotteryUsername(openID, openIdCredentials.getAuthSrc());
			return makeUserProfile(username, openIdCredentials);
		} catch (QQConnectException e) {
			logger.error("QQ互联登录失败 : ",e);
			return null;
		}
	}
	
	private OpenUserProfile makeUserProfile(String username, OpenIdCredentials openIdCredentials){
		OpenUserProfile openUserProfile = new OpenUserProfile();
		if(null == username){//未注册/绑定的第三方帐号
			openUserProfile.setOpenUid(openIdCredentials.getOpenid());
			openUserProfile.setToken(openIdCredentials.getAccessToken());
			openUserProfile.setAuthSrc(openIdCredentials.getAuthSrc());
			openUserProfile.setRegist(false);
			return openUserProfile;
		}
		
		//已绑定大V彩帐号的第三方帐号 
		User user = userService.getUserByUsername(username);
		if(null == user){
			logger.error("QQ用户登录异常，" +
				"在redis数据库中找到绑定帐号的用户名：username={};" +
				"但未在大V彩lt_user帐号中查到该用户信息",username);
			return null;
		}
		userService.updateToken(username,"qqConnectToken",openIdCredentials.getAccessToken());
		openUserProfile.setId(user.getId());
		openUserProfile.setUsername(user.getUsername());
		openUserProfile.setTruename(user.getRealname());
		openUserProfile.setLastLoginIp(user.getLastLoginIp());
		openUserProfile.setLastLoginTime(user.getLastLoginTime());
		openUserProfile.setRefresh(false);
		
		openUserProfile.setOpenUid(openIdCredentials.getOpenid());
		openUserProfile.setToken(openIdCredentials.getAccessToken());
		openUserProfile.setAuthSrc(openIdCredentials.getAuthSrc());
		openUserProfile.setRegist(true);
		return openUserProfile;
	}
}
