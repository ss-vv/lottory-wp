package com.unison.lottery.pm.service.impl;

import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.unison.lottery.pm.persist.BetSchemeDao;
import com.unison.lottery.pm.service.PMGrantVoucherRecordService;

/**
 * 优惠劵活动记录service
 * @author Wang Lei
 *
 */
public class PMGrantVoucherRecordServiceImpl implements
		PMGrantVoucherRecordService {
	
	Logger logger = LoggerFactory.getLogger(getClass());
	@Autowired
	BetSchemeDao bSchemeDao;
	
	@Override
	@Transactional(readOnly=true)
	public List<Object[]> findUserJCZQBetByDate(Date day){
		return bSchemeDao.findUserJCZQBetByDate(day);
	}
}
