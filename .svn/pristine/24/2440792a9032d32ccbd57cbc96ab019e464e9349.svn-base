package com.xhcms.lottery.commons.persist.service;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Set;

import com.xhcms.lottery.commons.persist.entity.PMWeekWinnersRecordPO;

public interface PMWeekBonusService {

	public void autoAddBonus();

	void prize(Long userId, Long bonusRecordId);

	/**
	 * 查询中奖方案
	 * @param beginTime
	 * @param endTime
	 * @param lotteryIds	彩种ID集合
	 * @return
	 */
	Set<Long> findWinSchemeIds(Date beginTime, Date endTime,
			String[] lotteryIds);

	/**
	 * 保存中奖方案纪录
	 * @param schemeId
	 */
	void insertWinRecord(Long schemeId);

	PMWeekWinnersRecordPO getWinnerRecordBySid(Long schemeId);

	public Double getBonusPool();

	PMWeekWinnersRecordPO getWinnerRecordByid(Long rid);

	boolean subtractBonus(Long recordId);

	void addBonus(BigDecimal amount);
	
	List<PMWeekWinnersRecordPO> findCanAwardRecordsByUsers(Collection<Long> ids);
}
