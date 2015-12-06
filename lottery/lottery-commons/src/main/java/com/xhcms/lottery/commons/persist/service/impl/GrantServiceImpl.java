package com.xhcms.lottery.commons.persist.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.xhcms.commons.lang.Paging;
import com.xhcms.lottery.commons.data.Grant;
import com.xhcms.lottery.commons.persist.dao.GrantDao;
import com.xhcms.lottery.commons.persist.entity.GrantPO;
import com.xhcms.lottery.commons.persist.service.AccountService;
import com.xhcms.lottery.commons.persist.service.GrantService;
import com.xhcms.lottery.lang.EntityStatus;

public class GrantServiceImpl implements GrantService {
	@Autowired
	private GrantDao grantDao;
	@Autowired
    private AccountService accountService;
	
	@Override
	@Transactional(readOnly = true)
	public void listGrant(Paging paging, long userId, Date begin, Date end) {
		List<GrantPO> list = grantDao.find(paging, userId, begin, end, EntityStatus.GRANT_AUDIT);
		List<Grant> rets = new ArrayList<Grant>();
		
		for (GrantPO po : list) {
			Grant grant = new Grant();
			BeanUtils.copyProperties(po, grant);
			rets.add(grant);
		}
		paging.setResults(rets);
	}

	/** 自动赠款  */
	@Override
	@Transactional
	public void autoGrant(Long userId, BigDecimal amount, Long granttypeId, String note){
		Date now = new Date();
		GrantPO grant = new GrantPO();
		grant.setAmount(amount);
		grant.setUserId(userId);
		grant.setOperatorId(0);
		grant.setOrderId(0);
		grant.setAuditId(0);
		grant.setCreatedTime(now);
		grant.setAuditTime(now);
		grant.setGrantTypeId(granttypeId);
		grant.setStatus(EntityStatus.GRANT_AUDIT);
		grant.setNote(note);
		grantDao.save(grant);
		accountService.grant(0, userId, amount, note);
	}
}
