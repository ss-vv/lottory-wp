package com.xhcms.lottery.commons.persist.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.xhcms.lottery.commons.persist.dao.LotteryOpenSaleDao;
import com.xhcms.lottery.commons.persist.entity.LotteryOpenSalePO;
import com.xhcms.lottery.commons.persist.service.BetTimeService;

public class BetTimeServiceImpl implements BetTimeService {
	@Autowired
    private LotteryOpenSaleDao lotteryOpenSaleDao;
	
	@Override
	@Transactional
	public List<LotteryOpenSalePO> getLotteryOpenSalePOs() {
		return lotteryOpenSaleDao.findOpenAndEndTime();
	}

	@Override
	@Transactional
	public void save(LotteryOpenSalePO po) {
		LotteryOpenSalePO lpo = lotteryOpenSaleDao.findOpenAndEndTimeById(po.getId());
		lpo.setOpenTime(po.getOpenTime());
		lpo.setEndTime(po.getEndTime());
		lpo.setMachineOfftime(po.getMachineOfftime());
		lpo.setIsEndTimeCrossDay(po.getIsEndTimeCrossDay());
		lpo.setIsMachineOfftimeCrossDay(po.getIsMachineOfftimeCrossDay());
	}
}
