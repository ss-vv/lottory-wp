package com.unison.lottery.weibo.common.persist;

import java.util.Collection;
import java.util.List;
import java.util.Set;
import com.unison.lottery.weibo.common.entity.QTLCMatchidPO;
import com.xhcms.commons.persist.Dao;

public interface QTMatchidDao extends Dao<QTLCMatchidPO> {

	void merge(Collection<QTLCMatchidPO> data);

	List<Integer> findNewQTMatchIds();

	List<Object[]> findMatchTime(Set<Long> ids);

	/**
	 * 根据大V彩赛事ID获取球探赛事ID
	 * @param lcMatchId
	 * @return
	 */
	long findQTMatchId(String lcMatchId);
	
	/**
	 * 根据球探赛事ID获取大V彩赛事ID,当结果列表有多个时，取比赛时间最近的
	 * @param lotteryName 彩种名称
	 * @param lcMatchId
	 * @return
	 */
	long findLCMatchId(Long qtMatchId, String lotteryName);
	
	List<Object[]> findTeamPosition(Long matchId);
}
