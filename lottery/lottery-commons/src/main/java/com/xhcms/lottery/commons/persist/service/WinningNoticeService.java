package com.xhcms.lottery.commons.persist.service;

import java.util.List;
import com.xhcms.lottery.commons.persist.entity.BetSchemePO;

public interface WinningNoticeService {

	void sendWinningNew(List<Long> schemeList);
	
	boolean checkWinningScheme(BetSchemePO scheme);
}