package com.xhcms.lottery.commons.persist.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.xhcms.lottery.commons.persist.dao.VoucherPMDao;
import com.xhcms.lottery.commons.persist.entity.VoucherPMPO;
import com.xhcms.lottery.commons.persist.service.VoucherPMService;

/**
 * 优惠劵活动service
 * @author Wang Lei
 *
 */
public class VoucherPMServiceImpl implements VoucherPMService {
	@Autowired
	private VoucherPMDao voucherPMDao;

	@Override
	@Transactional(readOnly=true)
	public VoucherPMPO get(Long id) {
		return voucherPMDao.get(id);
	}

}
