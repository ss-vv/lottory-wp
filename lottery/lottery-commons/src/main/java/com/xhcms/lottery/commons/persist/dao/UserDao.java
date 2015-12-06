package com.xhcms.lottery.commons.persist.dao;

import java.util.Collection;
import java.util.Date;
import java.util.List;

import com.xhcms.commons.lang.Paging;
import com.xhcms.commons.persist.Dao;
import com.xhcms.lottery.commons.persist.entity.UserPO;

/**
 * @author jiajiancheng
 * 
 */
public interface UserDao extends Dao<UserPO> {
	
	UserPO getUserByUsername(String username);

	UserPO getUserByEmail(String email);
	
	
	
	/**
	 * 根据手机号，返回唯一的状态是已绑定手机的用户
	 * @param mobile
	 * @return
	 */
	UserPO getUserByVerifyedMobile(String mobile);

	/**
	 * 根据身份证返回有效用户列表
	 * @param idnumber
	 * @return
	 */
    List<UserPO> getValidUsersByIdNumber(String idnumber);
    
    UserPO findById(Long id);
    
    List<UserPO> find(Collection<Long> ids);
    
    List<UserPO> find(Paging paging, Date from, Date to, int status, String username, String ip, String idNumber);
    
    List<UserPO> find(Paging paging, Date from, Date to, int status, String username, String ip, String idNumber, String mobile, String nickName);
    
    void updateHeadImageUrl(long id, String url);
    void updateNickname(long id, String nickname);
    void updateStatus(Collection<Long> ids, int status);

    /**
     * 根据用户已绑定的手机号返回有效状态的用户列表
     * @param mobile
     * @param validStatus
     * @return
     */
	List<UserPO> getValidUsersByBindedMobile(String mobile);

	/**
	 * 根据用户已绑定的邮箱返回有效状态的用户列表
	 * @param email
	 * @return
	 */
	List<UserPO> getValidUsersByBindedEmail(String email);

	/**
	 * 更新用户上次登录状态，包括登录次数和上次登录时间
	 * @param user
	 */
	void updateLastLoginStatus(UserPO user);

	/**
	 * 判断昵称是否被占用
	 * @param nickName
	 * @return
	 */
	boolean isExistByNickName(String nickName);

	/**
	 * 第三方登陆客户端
	 * @param uid
	 * @param type
	 * @return
	 */
	UserPO getUserByUidAndType(String uid, String type);

	/**
	 * 验证用户是否登陆
	 * @param phoneNumber
	 * @return
	 */
	UserPO getUserByPhoneNumberAndStatus0(String phoneNumber);

	UserPO findByOpenUid(String openUid, String field);

	void updateQQInfo(long id, String uid, String token);

	void updateSinaInfo(long id, String uid, String token);

	void updateAlipayInfo(long id, String uid, String token);

	void updateWeixinInfo(long id, String uid, String token);

	List<UserPO> findUsersByIds(List<Long> userIds);

	List<UserPO> findByFieldIsNotNull(String weixinUid);

	void updateWeixinInfo(long id, String weixinUid, String token,
			String weixinUnionId, String weixinPCUid);
	
	/**根据channel分组统计每天的新增用户数
	 * @param from
	 * @param to
	 * */
	List<Object[]> findNewRegUserGroupByPid(Date from, Date to);
	//设置是否让用户看重要的东西
	void updateUserAllow(long id,int allow);
}
