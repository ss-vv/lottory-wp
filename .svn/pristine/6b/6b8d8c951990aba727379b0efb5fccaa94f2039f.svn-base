package com.xhcms.lottery.commons.persist.service;

import java.util.Collection;
import java.util.Date;
import java.util.List;

import com.xhcms.commons.lang.Paging;
import com.xhcms.lottery.commons.data.NewRegistUser;
import com.xhcms.lottery.commons.data.User;

public interface UserService {
	User getUser(String username, String realname);
	void update(User user);
	User getUser(long userId);
    void open(Collection<Long> ids);
    void close(Collection<Long> ids);
	List<User> listUser(Paging paging, Date from, Date to, int status, String username, String ip, String idNumber);
	List<User> listUser(Paging paging, Date from, Date to, int status, String username, String ip, String idNumber, String mobile, String nickName);
	void updateRealname(Long userId, String realname);
	void updateIdnumber(Long userId, String idnumber);
	/**
	 * 重置用户登陆密码，并返回该密码明文。
	 * @param userId
	 * @return 新密码的明文
	 */
	String resetPassword(long userId);
	
	 /**
     * 解除用户登录限制
     * @param id
     * @return
     */
    int unlockUserAccount(long id);
    
    List<NewRegistUser> findNewRegUserGroupByPid(Date from, Date to, String channel);
    
    void allow(long id);
    void unAllow(long id);
}
