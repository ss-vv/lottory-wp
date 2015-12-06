package com.xhcms.lottery.commons.persist.service.impl;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.xhcms.lottery.commons.data.ColorRingGrant;
import com.xhcms.lottery.commons.persist.dao.ColorRingDao;
import com.xhcms.lottery.commons.persist.entity.PmColorRingGrantPO;
import com.xhcms.lottery.commons.persist.service.ColorRingService;

@Transactional
public class ColorRingServiceImpl implements ColorRingService {
	
	@Autowired
	private ColorRingDao colorRingDao;

	@Override
	@Transactional
	public boolean whetherTreatment(String tradeNo) {
		PmColorRingGrantPO crGrantPO = colorRingDao.findByTradeNo(tradeNo);
		if (null != crGrantPO && StringUtils.isNotBlank(crGrantPO.getTradeNo())) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	@Transactional
	public void addColorRingGrant(ColorRingGrant crGrant) {
		PmColorRingGrantPO crGrantPO = new PmColorRingGrantPO();
		BeanUtils.copyProperties(crGrant, crGrantPO);
		colorRingDao.save(crGrantPO);
	}
	
}
