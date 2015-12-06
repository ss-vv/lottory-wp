package com.unison.lottery.weibo.data.service.store.persist.dao;

import java.util.Collection;
import java.util.List;

import com.unison.lottery.weibo.data.service.store.persist.entity.FBTeamPO;
import com.xhcms.commons.persist.Dao;

public interface FBTeamDao extends Dao<FBTeamPO> {

	void batchInsert(Collection<FBTeamPO> data);

	void batchUpdate(Collection<FBTeamPO> data);

	List<Long> ids();
	
	long findIdByChineseName(String teamName);
	long findIdByTraditionalName(String teamName);

	FBTeamPO findByTeamId(Long teamId);
}
