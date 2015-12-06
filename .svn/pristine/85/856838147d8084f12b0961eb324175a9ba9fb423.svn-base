package com.xhcms.lottery.commons.persist.dao;

import java.util.List;

import com.xhcms.commons.persist.Dao; 
import com.xhcms.lottery.commons.persist.entity.ActivityPO;

public interface ActivityDao extends Dao<ActivityPO>{

	List<ActivityPO> findAll();
	
	Long createActivity(ActivityPO activityPO);
	
	ActivityPO getActivityById(Long id);

	boolean deleteActivityFromList(List<Long> list);

	String findMaxVersion();

	void updateAllVersion(Integer valueOf, List<Long> list);

	void updatePutOnListVersion(Integer valueOf, List<Long> list);
	
	List<ActivityPO> findAllStatus(Integer status);
	
	
}
