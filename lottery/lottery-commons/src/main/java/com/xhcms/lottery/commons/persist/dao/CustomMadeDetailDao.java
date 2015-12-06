/**
 * 
 */
package com.xhcms.lottery.commons.persist.dao;

import java.util.Date;
import java.util.List;

import com.xhcms.commons.lang.Paging;
import com.xhcms.commons.persist.Dao;
import com.xhcms.lottery.commons.persist.entity.CustomMadeDetailPO;

/**
 * @author Bean.Long
 *
 */
public interface CustomMadeDetailDao extends Dao<CustomMadeDetailPO> {
	
	/**
	 * 获取定制userId的followedUserId的明细
	 * @param userId
	 * @param followedUserId
	 * @param paging
	 * @return
	 */
	List<CustomMadeDetailPO> findCustomMadeDetails(long userId, long followedUserId, Paging paging);

	int countFollowCount(long userId, long followedUserId, Date startTime, Date endTime);

	int sumMoneny(long userId, long followedUserId, Date startTime, Date endTime);

}
