package com.xhcms.lottery.dc.persist.service;

import java.util.List;

import com.xhcms.lottery.dc.data.LCOdds;
import com.xhcms.lottery.dc.data.Match;
import com.xhcms.lottery.dc.data.ZCOdds;
import com.xhcms.lottery.lang.LotteryId;

public interface MatchSupportPlayService {
	
	void saveSupportPlay(List<Match> data, LotteryId lotteryId);
	
	List<ZCOdds> filterZCOdds(List<ZCOdds> data);
	
	List<LCOdds> filterLCOdds(List<LCOdds> data);

	List<ZCOdds> filterZCZeroOdds(List<ZCOdds> data);
}