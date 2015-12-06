package com.xhcms.lottery.dc.persist.persister;

import java.util.List;

import com.xhcms.lottery.dc.core.Persister;
import com.xhcms.lottery.dc.data.Match;
import com.xhcms.lottery.dc.persist.service.FBMatchService;
import com.xhcms.lottery.dc.persist.service.MatchSupportPlayService;
import com.xhcms.lottery.lang.LotteryId;

public class ZCMatchPersisterImpl implements Persister<Match> {
	
	private FBMatchService fbMatchService;
	private MatchSupportPlayService supportPlayService;
	
	@Override
	public void persist(List<Match> data) {
		fbMatchService.batchUpdateMatch(data);
		supportPlayService.saveSupportPlay(data, LotteryId.JCZQ);
	}

	public void setFbMatchService(FBMatchService fbMatchService) {
		this.fbMatchService = fbMatchService;
	}

	public void setSupportPlayService(MatchSupportPlayService supportPlayService) {
		this.supportPlayService = supportPlayService;
	}
}
