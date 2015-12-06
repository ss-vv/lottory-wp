package com.unison.lottery.weibo.data.service.store.persist.dao;

import com.unison.lottery.weibo.data.service.store.persist.entity.FBSubCupPO;
import com.xhcms.commons.persist.Dao;

public interface FBSubCupDao extends Dao<FBSubCupPO>  {

	FBSubCupPO findBy(long leagueId, String season);
}
