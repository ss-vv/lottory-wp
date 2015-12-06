package com.xhcms.lottery.commons.persist.dao;

import java.util.Date;
import java.util.List;

import com.xhcms.commons.persist.Dao;
import com.xhcms.lottery.commons.persist.entity.BetMatchPO;

public interface BetMatchDao extends Dao<BetMatchPO>{

    List<BetMatchPO> findBySchemeId(Long schemeId);

    BetMatchPO find(Long schemeId, String subcode);
    //晒单最多的5场比赛
    List<Object[]> findTop5Match(Date beginTime);

	List<BetMatchPO> findByMatchId(String matchId);
}
