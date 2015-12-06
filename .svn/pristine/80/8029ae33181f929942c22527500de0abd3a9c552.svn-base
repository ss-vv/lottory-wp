package com.xhcms.lottery.commons.persist.dao;

import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Set;

import com.xhcms.commons.persist.Dao;
import com.xhcms.lottery.commons.persist.entity.PMWeekWinnersRecordPO;

public interface PMWeekWinnersRecordDao extends Dao<PMWeekWinnersRecordPO> {

	/**
	 * 查询中奖方案ID
	 * 
	 * @param beginTime
	 * @param endTime
	 * @param lotteryIds
	 * @return
	 */
	Set<Long> findWinSchemeIds(Date beginTime, Date endTime,
			String[] lotteryIds);

	PMWeekWinnersRecordPO getBySid(Long sid);

	List<PMWeekWinnersRecordPO> getRecordsByUsers(Collection<Long> ids);
}
