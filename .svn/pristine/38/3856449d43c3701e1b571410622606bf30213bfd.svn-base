package com.unison.lottery.api.login.service;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.unison.lottery.api.model.User;
import com.xhcms.ucenter.persistent.service.IUserValidIdManager;

public class LoginServiceImpl implements ILoginService {

	@Autowired
	private IUserValidIdManager userValidManager;
	
	@Override
	@Transactional
	public User tryGetAsAuthenticate(User user) {
		User userAfterLogin=null;
		if(null!=user&&StringUtils.isNotBlank(user.getValidId())){
			String userId=userValidManager.findUserIdByValidIdAndCurrentTime(user.getValidId());
			if(StringUtils.isNotBlank(userId)){
				userAfterLogin=new User();
				userAfterLogin.setId(userId);
				userAfterLogin.setIp(user.getIp());
				userAfterLogin.setValidId(user.getValidId());
				userAfterLogin.setChannel(user.getChannel());
			}
		}
		
		return userAfterLogin;
	}

	@Override
	@Transactional
	public void updateExpiredTime(User user) {
		if(null!=user
				&&StringUtils.isNotBlank(user.getValidId())){
			userValidManager.updateExpiredTime(user.getValidId(),user.getId());
		}
		
	}

	

}
