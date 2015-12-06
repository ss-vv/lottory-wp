package com.xhcms.lottery.dc.persist.persister;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.xhcms.lottery.dc.core.Persister;
import com.xhcms.lottery.dc.data.BDResult;
import com.xhcms.lottery.dc.persist.service.BJDCMatchService;

public class BDSFMatchResultPersisterImpl implements Persister<BDResult> {
    
	private Logger logger=LoggerFactory.getLogger(getClass());
    private BJDCMatchService bjdcMatchService;
	
	public void setBjdcMatchService(BJDCMatchService bjdcMatchService) {
		this.bjdcMatchService = bjdcMatchService;
	}

	@Override
	public void persist(List<BDResult> data) {
		logger.info("北京单场SF开始更新赛果");
		bjdcMatchService.betchUpadteBDSFMatchResult(data);
	}

}
