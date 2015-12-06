package com.xhcms.lottery.commons.persist.service;

import java.util.List;

import com.xhcms.lottery.commons.data.BetMatch;

public interface BetOptionService {

	void combinBetOptions(String playId, List<BetMatch> matchList,boolean needOdds);

}
