package com.xhcms.lottery.dc.persist.service;

import java.util.List;
import com.xhcms.lottery.dc.data.BDMatch;
import com.xhcms.lottery.dc.data.BDOdds;
import com.xhcms.lottery.dc.data.BDResult;
import com.xhcms.lottery.lang.PlayType;

public interface BJDCMatchService {

	void batchUpdateMatch(List<BDMatch> data, PlayType playType);
	
	void batchUpdateOdds(List<BDOdds> data);
	
	void betchUpdateMatchResult(List<BDResult> rs);
	
	void betchUpadteBDSFMatchResult(List<BDResult> rs);
	
	List<String> getIssueNumber(String playId);

	void updateMatchStatus();

}