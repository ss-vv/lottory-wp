package com.xhcms.lottery.commons.persist.dao;

import java.util.List;

import com.xhcms.commons.persist.Dao;
import com.xhcms.lottery.commons.data.HX_user;
import com.xhcms.lottery.commons.persist.entity.HX_userPO;

public interface HX_userDao extends Dao<HX_userPO> { 
 
	HX_userPO findHx_userByUserPOId(String userId);
   
	Long createHX_user(HX_userPO hx_userPO);

	HX_userPO findHx_userByUserPOName(String username);

	List<HX_userPO> findHx_userByUserPONames(List<String> usernames);

	HX_userPO findHx_userByNickName(String name);

	void updateHX_user(HX_userPO hx_userPO);

	Object[] findHxUserByUsername(String useranme);
}
