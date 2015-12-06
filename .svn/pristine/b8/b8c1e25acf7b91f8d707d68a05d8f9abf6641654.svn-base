package com.unison.lottery.api.login.service;

import com.unison.lottery.api.model.User;

import com.unison.lottery.api.protocol.response.model.LoginResponse;






public interface ILoginService {

	/**
	 * 尝试获取经验证的用户对象
	 * @param user
	 * @return 若通过验证，则返回user对象；否则，返回null
	 */
	User tryGetAsAuthenticate(User user);

	/**
	 * 更新超时时间
	 * @param user
	 */
	void updateExpiredTime(User user);

	

	

}
