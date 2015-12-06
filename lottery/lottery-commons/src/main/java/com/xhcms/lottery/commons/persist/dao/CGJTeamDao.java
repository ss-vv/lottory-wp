package com.xhcms.lottery.commons.persist.dao;

import java.util.Collection;
import java.util.List;
import com.xhcms.commons.persist.Dao;
import com.xhcms.lottery.commons.persist.entity.CGJTeamPO;

public interface CGJTeamDao extends Dao<CGJTeamPO> {

	CGJTeamPO find(String playType, String season, String teamName,
			String matchName);

	CGJTeamPO findById(Long id);

	int updateCgjTeamCurrentSeason(String year, String playType);

	List<CGJTeamPO> findByTeamIdSet(Collection<Long> teamIdSet, String playId);
	
	CGJTeamPO findByTeamId(Long teamId, String playId);
	
	public List<CGJTeamPO> listTeamsCurrentSeason(String playType);
}