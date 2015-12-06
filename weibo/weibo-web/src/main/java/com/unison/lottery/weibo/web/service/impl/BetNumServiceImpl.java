package com.unison.lottery.weibo.web.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.unison.lottery.weibo.common.nosql.LotteryBetDao;
import com.unison.lottery.weibo.common.nosql.impl.Keys;
import com.unison.lottery.weibo.web.service.BetNumService;

@Service
public class BetNumServiceImpl implements BetNumService{
	
	@Autowired
	private LotteryBetDao lotteryBetDao;
    @Transactional
	@Override
	public String getJCZQBetNum() {
		
		return lotteryBetDao.getString(Keys.getJCZQKey());
	}
    @Transactional
	@Override
	public String getJCLQBetNum() {
		
		return lotteryBetDao.getString(Keys.getJCLQKey());
	}
    @Transactional
	@Override
	public String getBJDCBetNum() {
		
		return lotteryBetDao.getString(Keys.getBJDCKey());
	}
    @Transactional
	@Override
	public String getCTZQBetNum() {
	
		return lotteryBetDao.getString(Keys.getCTZQKey());
	}
    @Transactional
	@Override
	public String getSSQBetNum() {
		
		return lotteryBetDao.getString(Keys.getSSQKey());
	}

}
