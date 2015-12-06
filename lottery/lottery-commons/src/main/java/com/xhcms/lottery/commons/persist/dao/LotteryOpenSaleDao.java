package com.xhcms.lottery.commons.persist.dao;

import java.util.List;

import com.xhcms.lottery.commons.persist.entity.LotteryOpenSalePO;
import com.xhcms.lottery.lang.LotteryId;

public interface LotteryOpenSaleDao {

	Long countByLotteryIdAndDayOfWeek(LotteryId lotteryId, int dayOfWeek);
	LotteryOpenSalePO findByLotteryIdAndDayOfWeek(LotteryId lotteryId, int dayOfWeek);
	List<LotteryOpenSalePO> findOpenAndEndTime();
	LotteryOpenSalePO findOpenAndEndTimeById(Long id);

}
