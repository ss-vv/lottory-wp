/**
 * 
 */
package com.xhcms.ucenter.service.user;

import java.util.Date;

import com.xhcms.lottery.commons.data.User;

/**
 * @author bean
 *
 */
public interface IUserManager {
	/**
	 * 登录
	 * @param loginType
	 * @param username
	 * @param password
	 * @return
	 */
	boolean login(int loginType, String target, String password);
	
	/**
	 * 注册用户，同时添加账户信息
	 * @param userVO
	 */
	void regist(User userVO);
	
	/**
	 * 开启用户
	 * @param userId
	 */
	void openUser(long userId);
	/**
	 * 关闭用户
	 *     关闭的用户 不允许登录
	 * @param userId
	 */
	void closeUser(long userId);
	
	void updateUserLoginStatus(long uid, String lastLoginIp, Date lastLoginTime);
	
	/**
	 * 修改密码，用于找回密码
	 * @param userId
	 * @param newPasswd
	 */
	void updatePasswd(long userId, String newPasswd);

	void updatePasswd(long userId, String oldPasswd, String newPasswd);

    /**
     * 修改基本资料
     * @param user
     */
    void updateInfo(User user);

    /**修改手机号码
     * @param uid
     * @param newMobile
     */
    void updateMobile(Long uid, String newMobile);

	
}
