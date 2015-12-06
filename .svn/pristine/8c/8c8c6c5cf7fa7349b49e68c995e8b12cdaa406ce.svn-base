package com.xhcms.lottery.commons.persist.dao;

import java.util.Date;

import com.xhcms.commons.persist.Dao;
import com.xhcms.lottery.commons.persist.entity.ValidIdPO;


public interface ValidIdDao extends Dao<ValidIdPO>{

	String findUserIdByValidIdAndCurrentTime(String validId);

	String findUserIdByValidIdAndTargetTime(String validId, Date targetTime);

	void updateExpiredTime(String userId, String validId, Date createExpiredTime);

}
