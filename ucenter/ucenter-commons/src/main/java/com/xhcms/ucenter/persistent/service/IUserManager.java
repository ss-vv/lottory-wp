/**
 * 
 */
package com.xhcms.ucenter.persistent.service;

import java.util.Date;

import com.xhcms.exception.XHRuntimeException;
import com.xhcms.lottery.commons.data.User;
import com.xhcms.lottery.commons.persist.entity.UserPO;
import com.xhcms.ucenter.persistent.service.exception.LoginNameNotFoundException;
import com.xhcms.ucenter.persistent.service.exception.LoginNameOrPasswordBlankException;
import com.xhcms.ucenter.persistent.service.exception.PasswordWrongException;
import com.xhcms.ucenter.persistent.service.exception.RegisteFailNickNameOrPhoneNumberIsBlankException;
import com.xhcms.ucenter.persistent.service.exception.RegisteFailPasswordIsBlankException;
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

    /**
     * 使用登录名和经过md5的密码登录
     * @param loginName
     * @param passwordMD5
     * @return 登录成功,返回UserPO；登录失败，返回null
     */
	UserPO loginByNameAndPasswordMD5(String loginName, String passwordMD5) throws LoginNameNotFoundException,PasswordWrongException,LoginNameOrPasswordBlankException;
	/**
	 * 给客户端使用的注册用户方法
	 * 要求用户必须输入真实姓名、手机号和密码
	 * @param userVo
	 */
	String regist4Client(com.xhcms.lottery.commons.data.User userVo) throws RegisteFailPasswordIsBlankException,RegisteFailNickNameOrPhoneNumberIsBlankException;

	/**
	 * 更新身份证
	 * @param user4Update
	 */
	void updateIDNumber(com.xhcms.lottery.commons.data.User user4Update);
	
	/**
	 * 更新真实姓名
	 * @param user
	 */
	void updateRealName(com.xhcms.lottery.commons.data.User user);

	/**
	 * 为客户端准备的更新密码方法，会
	 * 抛出异常
	 * @param userId
	 * @param oldPasswd
	 * @param newPasswd
	 * @param type 表示是登录密码还是提现密码
	 */
	void updatePasswdForClient(long userId, String oldPasswd, String newPasswd, String type) throws XHRuntimeException;

	/**
	 * 判断昵称是否被占用
	 * @param nickName
	 * @return
	 */

	boolean isExistNickName(String nickName);
	/**
	 * 根据uid和type登陆客户端(第三方账号)
	 * @param uid
	 * @param type
	 * @return
	 */
	UserPO loginByUidAndType(String uid, String type);

	boolean fingUserByPhoneNumber(String phoneNumber);
	
}
