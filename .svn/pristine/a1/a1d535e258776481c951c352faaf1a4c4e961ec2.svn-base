package com.unison.lottery.weibo.data.service.store.persist.dao;

import java.util.Collection;
import java.util.List;
import java.util.Set;

import com.unison.lottery.weibo.data.service.store.persist.entity.FBLeaguePO;
import com.xhcms.commons.persist.Dao;

public interface FBLeagueDao extends Dao<FBLeaguePO> {

	void batchInsert(Collection<FBLeaguePO> data);

	void batchUpdate(Collection<FBLeaguePO> data);

	List<Long> ids();

	List<Object[]> findLeagueIdsByShortNames(Set<String> names);

	List<FBLeaguePO> listAllLeagues();

}
