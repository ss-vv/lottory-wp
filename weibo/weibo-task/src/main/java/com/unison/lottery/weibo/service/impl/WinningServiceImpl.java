package com.unison.lottery.weibo.service.impl;

import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.xhcms.lottery.commons.persist.dao.BetSchemeDao;
import com.xhcms.lottery.commons.persist.entity.BetSchemePO;

@Service
public class WinningServiceImpl {

	@Autowired
	private BetSchemeDao betSchemeDao;
	
	@Transactional
	public List<BetSchemePO> findByStatus(int status, Date from, Date to) {
		return betSchemeDao.findByStatusDesc(status, from, to);
	}
}