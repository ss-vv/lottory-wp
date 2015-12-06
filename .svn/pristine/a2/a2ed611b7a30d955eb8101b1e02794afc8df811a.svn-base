package com.xhcms.lottery.dc.persist.persister;

import java.util.List;

import com.xhcms.lottery.dc.core.Persister;
import com.xhcms.lottery.dc.data.Match;
import com.xhcms.lottery.dc.persist.service.BBMatchService;
import com.xhcms.lottery.dc.persist.service.MatchSupportPlayService;
import com.xhcms.lottery.lang.LotteryId;

public class LCMatchPersisterImpl implements Persister<Match>  {
	
	private BBMatchService bbMatchService;
	private MatchSupportPlayService supportPlayService;
	
	@Override
	public void persist(List<Match> data) {
		bbMatchService.batchUpdateMatch(data);
		supportPlayService.saveSupportPlay(data, LotteryId.JCLQ);
	}

	public void setBbMatchService(BBMatchService bbMatchService) {
		this.bbMatchService = bbMatchService;
	}
	
	public void setSupportPlayService(MatchSupportPlayService supportPlayService) {
		this.supportPlayService = supportPlayService;
	}
}