/**
 * 
 */
package com.xhcms.ucenter.persist.dao;

import com.xhcms.lottery.commons.data.User;

/**
 * @author Bean.Long
 *
 */
public interface ISyncUserDao {
	public void insertMember(long uid, String username, String password, String email, String regip);
	public void insertCommonMember(long uid, String username, String password, String email, String clientip, String groupid);
	public void insertMemberCount(long uid);
	public User findMemberByUsername(String username);
	public void updateMemberPassword(long uid, String password);
}