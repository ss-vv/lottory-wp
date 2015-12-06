package com.unison.lottery.api.protocol.status.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.xhcms.lottery.commons.persist.dao.StatusDao;
import com.xhcms.lottery.commons.persist.entity.ReturnStatusPO;

@Transactional
public class StatusService implements IStatusService {
	
	@Autowired
	private StatusDao statusDao;

	@Override
	public List<ReturnStatusPO> loadAllStatus() {
		
		return statusDao.list();
	}

}
