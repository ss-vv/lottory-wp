package com.xhcms.lottery.commons.persist.dao;

import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Set;

import com.xhcms.commons.lang.Paging;
import com.xhcms.commons.persist.Dao;
import com.xhcms.lottery.commons.persist.entity.FBMatchPO;

public interface FBMatchDao extends Dao<FBMatchPO>{
	List<FBMatchPO> find(Paging paging, int status, Date from, Date to);

    List<FBMatchPO> find(Set<Long> idSet);
    
    List<Object[]>  queryFBMatchCountByStatus(int status);
    
    List<String> queryFBLeaguesByStatus(int status);
    
    List<Object[]>  queryLotteryInfoForFB();

	List<FBMatchPO> find(Paging paging, int status, Date from, Date to,
			int matchResult);

	List<FBMatchPO> findByLeagueName(String longLeagueName,long fromTime);
	
	long findFBMatchId(String playingDate, String code);
	
	/**
	 * 更新大V彩赛事预设分值
	 * @param lcMatchId
	 * @param scorePreset
	 * @param halfScorePreset
	 */
	int updatePresetScore(long lcMatchId, String scorePreset, String halfScorePreset);
	
	List<Object[]> findFBMatchById(Long id);
	//查询在售赛事数量
	Integer findMatchCount();
	
	Integer getConcedePointsById(Long id);
	
	FBMatchPO findFBMatch(Long matchId);
	/**
	 * 查询今天所有的比赛
	 * @param beginTime
	 * @return
	 */
	List<FBMatchPO> findFBMatchByPlayTime(Date beginTime);

	List<Object[]> queryFBMatchCountByStatusAndAfterDeadLine(int status,
			Date currentDateTime);

	List<String> queryFBLeaguesByStatusAndAfterDeadLine(int status,
			Date currentDateTime);

	List<Object[]> getZQbifen(Date from, Date to, String leagueShortName);
	
	List<Object[]> queryIdAndCodesByStatusAndLeagueListAndAfterDeadlineWithPage(
			int matchOnSale, List<String> leagueList, Date minMatchPlayingTime,
			Integer firstResult, Integer pageMaxResult);
	
	/**
	 * 根据赛事ID集合过滤出“已取消”状态的比赛
	 * @param matchIds
	 * @return
	 */
	List<String> filterCancelMatch(Collection<Long> matchIds);
	
	List<FBMatchPO> getFBMatchByPlayTime_(Date beginTime,String weekDay);
	
}
