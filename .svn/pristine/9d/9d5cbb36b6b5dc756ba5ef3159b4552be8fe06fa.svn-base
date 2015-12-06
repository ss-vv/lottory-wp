package com.xhcms.lottery.account.service;

import java.util.Date;

import com.unison.lottery.weibo.data.service.store.persist.entity.QTMatchPO;
import com.xhcms.commons.persist.Dao;

public interface QTFBMatchDao extends Dao<QTMatchPO>{
	QTMatchPO findById(long qtMatchId);

	QTMatchPO findByJingCaiIdAndMatchTime(String cnCode, Date playingTime);
}
