package com.xhcms.lottery.commons.persist.dao;

import java.util.Collection;
import java.util.Date;
import java.util.List;

import com.xhcms.commons.persist.Dao;
import com.xhcms.lottery.commons.data.PlayMatch;
import com.xhcms.lottery.commons.persist.entity.BJDCMatchPlayPO;

public interface BJDCMatchPlayDao extends Dao<BJDCMatchPlayPO>{

	List<BJDCMatchPlayPO> find(String playId, Collection<Long> matchs);
	
	List<Object[]> findByStatus(String issueNumber, String playId, int status);
	
	List<Object[]> findBy(String issueNumber, String playId);
	
	List<BJDCMatchPlayPO> findByMatches(Collection<PlayMatch> matchs);
	
	List<BJDCMatchPlayPO> findById(Collection<String> ids);

	List<Object[]> findMatchBySelector(String playId, boolean showStopSell,
			List<String> leagueList, Date playingTime);

	List<Object[]> findByStatus(String playId, int matchOnSale);
	
	List<Object[]> findOptionsOddsById(String id);
	
	String getPoints(String id);
	/**
	 * 查询 赛事投注结果和 比赛结果信息等
	 * @param schemeId
	 * @return
	 */
	List<Object[]> findMatchInfoBySchemeId(Long schemeId,String play_id);
}

