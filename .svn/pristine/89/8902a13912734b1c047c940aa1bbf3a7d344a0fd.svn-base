package com.xhcms.lottery.dc.persist.persister;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.xhcms.lottery.dc.core.Persister;
import com.xhcms.lottery.dc.data.LCOdds;
import com.xhcms.lottery.dc.persist.service.BBMatchService;
import com.xhcms.lottery.dc.persist.service.MatchSupportPlayService;

public class AnRuiLCOddsPersisterImpl implements Persister<LCOdds> {
	@Autowired
	private BBMatchService bbMatchService;
	
	@Override
	public void persist(List<LCOdds> data) {
		if(null != data && data.size() > 0) {
			//不需要过滤玩法是单关的赔率数据
//			if(data.get(0).getPlayId().endsWith("LC_1")) {
//				List<LCOdds> result = supportPlayService.filterLCOdds(data);
//				data = result;
//			}
			bbMatchService.batchUpdateOdds(data);
		}
	}

	public BBMatchService getBbMatchService() {
		return bbMatchService;
	}

	public void setBbMatchService(BBMatchService bbMatchService) {
		this.bbMatchService = bbMatchService;
	}
	
}
