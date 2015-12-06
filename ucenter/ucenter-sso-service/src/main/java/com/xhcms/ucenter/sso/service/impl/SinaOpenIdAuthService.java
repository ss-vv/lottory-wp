package com.xhcms.ucenter.sso.service.impl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import weibo4j.Oauth;
import weibo4j.http.AccessToken;
import weibo4j.model.WeiboException;

import com.xhcms.lottery.commons.data.User;
import com.xhcms.ucenter.persist.redis.dao.IOpenUserDao;
import com.xhcms.ucenter.service.user.IUserService;
import com.xhcms.ucenter.sso.principal.OpenIdCredentials;
import com.xhcms.ucenter.sso.service.IOpenIdAuthService;
import com.xhcms.ucenter.sso.service.OpenUserProfile;
import com.xhcms.ucenter.sso.service.UserProfile;

public class SinaOpenIdAuthService implements IOpenIdAuthService {
	Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	IOpenUserDao openUserDao;

	@Autowired
	private IUserService userService;
	
	@Override
	public UserProfile authenticate(OpenIdCredentials openIdCredentials) {
		String code = openIdCredentials.getCode();
		logger.info("sina weibo code:{}", code);
		try {
			Oauth oauth = new Oauth();
			AccessToken accessToken = oauth.getAccessTokenByCode(code);
			logger.info("新浪微博登录成功，accessToken:{}，openUid:{}", accessToken.toString(),accessToken.getUid());
			String username = openUserDao.getLotteryUsername(accessToken.getUid(), openIdCredentials.getAuthSrc());
			return makeUserProfile(username, accessToken , openIdCredentials);
		} catch ( WeiboException e) {
			logger.error("新浪微博授权出错，非法code参数",e);
			return null;
		}
	}
	
	private OpenUserProfile makeUserProfile(String username, AccessToken accessToken,OpenIdCredentials openIdCredentials){
		OpenUserProfile openUserProfile = new OpenUserProfile();
		if(null == username){//未注册/绑定的第三方帐号
			openUserProfile.setOpenUid(accessToken.getUid());
			openUserProfile.setToken(accessToken.getAccessToken());
			openUserProfile.setAuthSrc(openIdCredentials.getAuthSrc());
			openUserProfile.setRegist(false);
			return openUserProfile;
		}
		
		//已绑定大V彩帐号的第三方帐号
		User user = userService.getUserByUsername(username);
		if(null == user){
			logger.error("新浪用户：uid={} 登录异常，" +
				"在redis数据库中找到绑定帐号的用户名：username={};" +
				"但未在大V彩lt_user帐号中查到该用户信息");
			return null;
		}
		userService.updateToken(username,"sinaWeiboToken",accessToken.getAccessToken());
		openUserProfile.setId(user.getId());
		openUserProfile.setUsername(user.getUsername());
		openUserProfile.setTruename(user.getRealname());
		openUserProfile.setLastLoginIp(user.getLastLoginIp());
		openUserProfile.setLastLoginTime(user.getLastLoginTime());
		openUserProfile.setRefresh(false);
		
		openUserProfile.setOpenUid(accessToken.getUid());
		openUserProfile.setToken(accessToken.getAccessToken());
		openUserProfile.setAuthSrc(openIdCredentials.getAuthSrc());
		openUserProfile.setRegist(true);
		return openUserProfile;
	}
}
