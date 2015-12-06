package com.unison.lottery.weibo.bbs.persist.dao;

import java.util.List;

import com.unison.lottery.weibo.bbs.persist.entity.UserPO;
import com.xhcms.commons.persist.Dao;

public interface UserDao extends Dao<UserPO>{

	List<UserPO> findEffectiveUsers(int status);
	
}
