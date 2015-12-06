package com.unison.lottery.pm.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.unison.lottery.pm.persist.WinGrantDao;
import com.unison.lottery.pm.service.WinGrantService;

public class WinGrantServiceImpl implements WinGrantService {
	
	protected Logger log = LoggerFactory.getLogger(getClass());
	@Autowired
	private WinGrantDao winGrantDao;
	
	@Override
	@Transactional(readOnly=true)
	public List<Long> findIdsByPMIDandStatus(Long PMid, Integer status){
		return winGrantDao.findIdsByPMIDandStatus(PMid, status);
	}
}
