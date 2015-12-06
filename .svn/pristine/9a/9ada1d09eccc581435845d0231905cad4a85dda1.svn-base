package com.xhcms.lottery.commons.persist.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.xhcms.lottery.commons.data.GrantType;
import com.xhcms.lottery.commons.persist.dao.GrantTypeDao;
import com.xhcms.lottery.commons.persist.service.GrantTypeService;
import com.xhcms.lottery.utils.POUtils;

/**
 * @author Wang Lei
 *
 */
public class GrantTypeServiceImpl implements GrantTypeService{

	@Autowired
	GrantTypeDao grantTypeDao;
	
	@Override
	@Transactional(readOnly=true)
	public List<GrantType> JCZCGrantTypeList(String lotteryId) {
		return POUtils.copyBeans(grantTypeDao.findGrantTypeListByLotteryId(lotteryId), GrantType.class);
	}

	@Override
	@Transactional(readOnly=true)
	public List<GrantType> list() {
		return POUtils.copyBeans(grantTypeDao.list(),GrantType.class);
	}
}
