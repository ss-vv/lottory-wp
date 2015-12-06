package com.xhcms.lottery.commons.persist.dao;

import java.util.Collection;
import java.util.Date;
import java.util.List; 
import java.util.Set;

import com.xhcms.commons.lang.Paging;
import com.xhcms.commons.persist.Dao;
import com.xhcms.lottery.commons.data.PlayMatch;
import com.xhcms.lottery.commons.persist.entity.BBMatchPlayPO;

public interface BBMatchPlayDao extends Dao<BBMatchPlayPO> {

    List<BBMatchPlayPO> findByMatchId(Collection<Long> matchIds);

    /**
     * 
     * @param playId
     * @param matchs
     * @return
     */
    List<BBMatchPlayPO> find(Paging paging, String playId, Collection<Long> matchs);
    
    /**
     * 
     * @param playId
     * @param matchs
     * @return
     */
    List<BBMatchPlayPO> find(String playId, Collection<Long> matchs);
    
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

    List<Object[]> findMatchByPlayIdAndStatusAndLeagueName(String playId, int status,List<String> leagues,Integer firstResult,Integer pageMaxResult);

	List<BBMatchPlayPO> findByMatches(Collection<PlayMatch> matchs);
	
	List<BBMatchPlayPO> findByMatchIdAndPlayIds(Long matchId, Collection<String> playIds);
	
	Float findByMatchIdAndPlayId(String matchId, String playId);
	
	List<Object[]> findMatchBySelector(String playId, boolean isShowStopSell, 
			List<String> leagueList, Date playingTime);
	
	List<Object[]> findOptionsOddsById(String id);
	String getPointsById(String id);
	/**
	 * 查询投注赛事结果 和比赛结果
	 * @param schemeId
	 * @return
	 */
	List<Object[]> findMatchInfoBySchemeId(Long schemeId,String play_id);

	List<Object[]> findSignalsByStatus(String playId, int matchOnSale,
			List<String> leagueList, Integer firstResult, Integer pageMaxResult);

	List<Object[]> findByStatusAndAfterDeadline(String playId, int matchOnSale,
			List<String> leagueList, Integer firstResult,
			Integer pageMaxResult, Date currentDateTime);

	List<Object[]> findSignalsByStatusAndAfterDeadline(String playId,
			int matchOnSale, List<String> leagueList, Integer firstResult,
			Integer pageMaxResult, Date currentDateTime);

	List<Object[]> findMatchByPlayIdAndStatusAndLeagueNameAndAfterDeadLine(
			String playId, int status, List<String> leagues, int firstResult,
			int pageMaxResult, Date currentDateTime);

	List<Object[]> findByPlayingtime(String playId, Date from, Date to);

	List<Object[]> findByPlayingtimeForSinglePass(String playId, Date from,
			Date to);

	List<Object[]> findByStatusForSinglePass(String playId, int status); 
	
	List<Object[]> findMatchAndMatchPlayByMatchId(Set<Long> matchIdSet);

	List<Object[]> findSingleMatchAndMatchPlayByMatchId(Set<Long> matchIdSet); 
	
	
	
}
