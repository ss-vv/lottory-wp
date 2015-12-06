package com.xhcms.ucenter.persistent.service;

import java.util.List;

import com.xhcms.lottery.commons.data.User;

/**
 * @author bean
 * 
 */
public interface IUserService {
	User getUser(long userId);

	/**
	 * 按用户名查询用户
	 * @param username
	 * @return
	 */
	User getUserByUsername(String username);
	
	/**
	 * 按手机号查询已绑定此手机号的有效或者无效用户
	 * @param mobile
	 * @return
	 */
	User getUserByVerifyedMobile(String mobile);

	/**
	 * 查询有效用户是否绑定该邮箱
	 * @param email
	 * @return
	 */
	boolean emailValidAndBinded(String email);

    /**
     * 查询有效用户手机号码是否已绑定
     * @param mobile
     * @return
     */
    boolean mobileValidAndBinded(String mobile);
    
    
    
    /**
     * 该身份证号是否有效
     * @param idnumber
     * @return
     */
    boolean idNumberBindToUser(String idnumber);
    
    /**
     * 记录用户登录失败的次数
     * @param idnumber
     * @return
     */
    int recordUserLoginFailureCount(String username);
    
    /**
     * 用户登录失败次数超出最大失败次数限制，锁定用户账号
     * @param userId
     * @return
     */
    int lockUserAccount(User user);
    
    /**
     * 用户解锁
     * @param userId
     * @return
     */
    int unlockUserAccount(long id);
    
    /**
     * 按照解锁策略，自动解锁已锁定账号
     * @param id
     */
    long autoUnlockUserAccount(User user, long auto_unlock_interval);

    /**
     * 根据用户id得到所有的用户
     * @param userIds
     * @return
     */
	List<User> findUsersByIds(List<Long> userIds);

	

   
    
}
