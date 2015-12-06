package com.xhcms.lottery.commons.persist.dao;

import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Set;

import com.xhcms.commons.lang.Paging;
import com.xhcms.commons.persist.Dao;
import com.xhcms.lottery.commons.persist.entity.GrantPO;

public interface GrantDao extends Dao<GrantPO> {
	List<GrantPO> find(Paging paging, long userId, Date begin, Date end, Integer status);
	List<GrantPO> find(Collection<Long> ids);

	/**
	 * 根据赠款类型集合和赠款时间范围得到赠款次数
	 * @param grantTypeIds
	 * @param userId
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	int getGrantCountByGrantTypeIdsAndTime(Set<Long> grantTypeIds,Long userId,Date startTime,Date endTime);
	
	GrantPO find(Long orderId, Long grantTypeId);
}
