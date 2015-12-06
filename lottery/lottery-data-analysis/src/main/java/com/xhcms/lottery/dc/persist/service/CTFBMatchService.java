package com.xhcms.lottery.dc.persist.service;

import java.util.List;
import com.xhcms.lottery.commons.data.CTFBMatch;
import com.xhcms.lottery.dc.data.ZCOdds;
import com.xhcms.lottery.dc.data.ZCResult;

/**
 * @author Wang Lei
 */
public interface CTFBMatchService {
	void batchSaveOrUpdateMatch(List<CTFBMatch> data);
	
//	void batchUpdateMatchResult(List<ZCResult> data);
//	
//	void updateMatchStatus();
}
