package com.xhcms.lottery.commons.persist.dao;

import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Set;

import com.xhcms.commons.lang.Paging;
import com.xhcms.commons.persist.Dao;
import com.xhcms.lottery.commons.persist.entity.BBMatchPO;

public interface BBMatchDao extends Dao<BBMatchPO>{
	List<BBMatchPO> find(Paging paging, int status, Date from, Date to);

    List<BBMatchPO> find(Set<Long> idSet);
    
    List<Object[]> queryBBMatchCountByStatus(int status);
    
    List<String> queryBBLeaguesByStatus(int status);
    
    List<Object[]>  queryLotteryInfoForBB();

	List<BBMatchPO> find(Paging paging, int state, Date from, Date to,
			int matchResult);

	List<BBMatchPO> findByLeagueName(String longLeagueName, long fromTime);
	
	long findBBMatchId(String playingDate, String code);
	
	int updatePresetScore(long lcMatchId, String finalScorePreset);
	
	List<Object[]> findMatchById(Long id);
	//查询在售赛事数量
	Integer findMatchCount();
	BBMatchPO findBBMatch(Long matchid);
	/**
	 * 
	 * @param beginTime
	 * @return
	 */
	List<BBMatchPO> findBBMatchByPlayTime(Date beginTime);

	List<Object[]> queryBBMatchCountByStatusAndAfterDeadLine(int status,
			Date currentDateTime);

	List<String> queryBBLeaguesByStatusAndAfterDeadLine(int status,
			Date currentDateTime);

	List<Object[]> getBBbifen(Date date, Date date2, String leagueShortName);
	
	List<Object[]> queryIdAndCodesByStatusAndLeagueListAndAfterDeadlineWithPage(
			int matchOnSale, List<String> leagueList, Date minMatchPlayingTime,
			Integer firstResult, Integer pageMaxResult);
	
	List<String> filterCancelMatch(Collection<Long> matchIds);
	
	
}
