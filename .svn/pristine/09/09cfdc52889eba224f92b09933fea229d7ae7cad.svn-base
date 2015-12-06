package com.xhcms.lottery.commons.persist.service;

import java.util.List;

import com.xhcms.lottery.commons.data.BetMatch;

public interface CheckMatchService {
	
	 /**
     * 检查投注赛事有效性，并设置赛事的即时赔率信息
     * @param playId
     * @param matchs
     * @return 0：赛事有效，其他值赛事无效，不能投注
     */
	int checkMatch(String playId, List<BetMatch> matchList);

}
