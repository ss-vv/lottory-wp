package com.xhcms.lottery.commons.persist.dao;

import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Set;

import com.xhcms.commons.persist.Dao;
import com.xhcms.lottery.commons.data.PlayMatch;
import com.xhcms.lottery.commons.persist.entity.FBMatchPlayPO;

public interface FBMatchPlayDao extends Dao<FBMatchPlayPO> {

    List<FBMatchPlayPO> findByMatchId(Collection<Long> matchIds);

    /**
     * 
     * @param playId
     * @param matchs
     * @return
     */
    List<FBMatchPlayPO> find(String playId, Collection<Long> matchs);
    
    List<Object[]> findByStatus(String playId, int status);
    /**
     * @Description: 分页+联赛名称查询赛事
     * @param playId
     * @param status
     * @param leagueList
     * @param firstResult
     * @param pageMaxResult
     * @return List<Object[]>
     * @Throw
     */
    List<Object[]> findByStatus(String playId, int status, List<String> leagueList, Integer firstResult,Integer pageMaxResult);

	List<Object[]> findMatchByPlayIdAndStatusAndLeagueName(String playId,
			int status, List<String> leagues,Integer firstResult, Integer pageMaxResult);

	/**
	 * 查找所有比赛对应的 FBMatchPlayPO.
	 * @param matchs
	 * @return
	 */
	List<FBMatchPlayPO> findByMatches(Collection<PlayMatch> matchs);
	
	List<Object[]> findMatchBySelector(String playId, boolean isShowStopSell, 
			List<String> leagueList, Date playingTime);
	
	List<Object[]> findLeagues(String playId, Date playingTime);
	
	List<Object[]> findOptionsOddsById(String id);
	/**
	 * 按schemeid 查方案投注结果和比赛结果
	 * @param schemeId
	 * @return
	 */
	List<Object[]> findMatchInfoBySchemeId(Long schemeId,String play_id);

	List<Object[]> findByStatusAndAfterDeadLine(String playId,
			int matchOnSale, List<String> leagueList, Integer firstResult,
			Integer pageMaxResult, Date currentDateTime);

	List<Object[]> findMatchByPlayIdAndStatusAndLeagueNameAndAfterDeadLine(
			String playId, int status, List<String> leagueList,
			int firstResult, int pagingMaxResult, Date currentDateTime);

	/**
	 * 查找含有单关固定玩法的足球比赛
	 * @param playId
	 * @param matchOnSale
	 * @param leagueList
	 * @param firstResult
	 * @param pageMaxResult
	 * @param minMatchPlayingTime
	 * @return
	 */
	List<Object[]> findSingleByStatusAndAfterDeadLine(String playId,
			int matchOnSale, List<String> leagueList, int firstResult,
			int pageMaxResult, Date minMatchPlayingTime);

	List<Object[]> findMatchAndMatchPlayByMatchId(Set<Long> matchIdSet);

	List<Object[]> findSingleMatchAndMatchPlayByMatchId(Set<Long> matchIdSet);
	
	List<Object[]> findByEntrustDeadline(String playId, Date from, Date to);

	List<Object[]> findByPlayingtime(String playId, Date from, Date to);

	List<Object[]> findByPlayingtimeForSinglePass(String playId, Date from,
			Date to);

	List<Object[]> findByStatusForSinglePass(String playId, int status);
}
