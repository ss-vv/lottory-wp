package com.xhcms.lottery.account.service;

import java.util.Date;
import java.util.List;

import com.unison.lottery.weibo.data.service.store.persist.entity.BBMatchInfoPO;
import com.xhcms.commons.persist.Dao;

public interface QTBBMatchDao extends Dao<BBMatchInfoPO>{
	BBMatchInfoPO findById(long qtMatchId);
	
	//List<Object[]> fillBBMatchResult();
	List<Object[]> fillBBMatchResult();
	
	String getTeamColor(Long id);

	BBMatchInfoPO findByJingCaiIdAndMatchTime(String cnCode, Date playingTime);
	
}
