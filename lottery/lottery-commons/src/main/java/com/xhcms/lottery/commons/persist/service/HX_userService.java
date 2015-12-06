package com.xhcms.lottery.commons.persist.service;

import java.util.List;

import com.xhcms.lottery.commons.data.HX_user;

public interface HX_userService { 

	HX_user getHX_user(Long id);

	Long saveOrUpdateHX_user(HX_user hx_user, String userIdString, String nickName);

	HX_user findHX_userByUsername(String username);

	List<HX_user> findHX_userByUsernames(List<String> userMembers);

	HX_user getHXuserByNickName(String name);

	void updateHX_user(HX_user hx_user);

	Object[] findUserInfoByUsername(String useranme);

//	HX_user makeHX_user(Long id, String channel, String nickName);
 
	
	
	
}
  