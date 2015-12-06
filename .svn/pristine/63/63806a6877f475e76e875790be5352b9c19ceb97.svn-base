package com.xhcms.ucenter.sso.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xhcms.lottery.commons.data.User;
import com.xhcms.ucenter.persist.redis.dao.IOpenUserDao;
import com.xhcms.ucenter.service.user.IUserService;
import com.xhcms.ucenter.sso.principal.AlipayBackBean;
import com.xhcms.ucenter.sso.principal.OpenIdCredentials;
import com.xhcms.ucenter.sso.service.AlipayOpenUserProfile;
import com.xhcms.ucenter.sso.service.IOpenIdAuthService;
import com.xhcms.ucenter.sso.service.OpenUserProfile;
import com.xhcms.ucenter.sso.service.UserProfile;
@Service
public class AlipayOpenIdAuthService implements IOpenIdAuthService{

	Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	IOpenUserDao openUserDao;
	@Autowired
	private IUserService userService;
	
	@Override
	public UserProfile authenticate(OpenIdCredentials openIdCredentials) {
		AlipayBackBean alipay=(AlipayBackBean)openIdCredentials;
		String username = openUserDao.getLotteryUsername(alipay.getUser_id(), openIdCredentials.getAuthSrc());
		return makeUserProfile(username, alipay);
	}
	
	private OpenUserProfile makeUserProfile(String username, AlipayBackBean alipay){
		AlipayOpenUserProfile openUser=new AlipayOpenUserProfile();
		if(alipay==null){
           return null;
		}
		
		if(null == username){//未注册/绑定的第三方帐号
			openUser.setOpenUid(alipay.getUser_id());
			openUser.setTruename(alipay.getReal_name());	
			openUser.setNickname(alipay.getReal_name());	
			openUser.setAuthSrc(alipay.getAuthSrc());
			openUser.setToken(alipay.getAccessToken());
			openUser.setRegist(false);
			return openUser;
		}
		
		//已绑定大V彩帐号的第三方帐号 
		User user = userService.getUserByUsername(username);
		if(null == user){
			logger.error("支付宝用户登录异常，" +
				"在redis数据库中找到绑定帐号的用户名：username={};" +
				"但未在大V彩lt_user帐号中查到该用户信息",username);
			return null;
		}
		openUser.setId(user.getId());
		openUser.setUsername(user.getUsername());
		openUser.setTruename(user.getRealname());
		openUser.setLastLoginIp(user.getLastLoginIp());
		openUser.setLastLoginTime(user.getLastLoginTime());
		openUser.setRefresh(false);
		openUser.setToken(alipay.getAccessToken());
		openUser.setAuthSrc(alipay.getAuthSrc());
		openUser.setRegist(true);
		return openUser;
	}

}
