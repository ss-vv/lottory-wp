package com.xhcms.lottery.commons.persist.service;

import java.util.Date;

import com.xhcms.commons.lang.Paging;

public interface WinService {
    
	void listWin(Paging paging, long userId, Date begin, Date end);
	void listWinByLotteryId(String lotteryId, Paging paging, long userId,Date begin, Date end);
}
