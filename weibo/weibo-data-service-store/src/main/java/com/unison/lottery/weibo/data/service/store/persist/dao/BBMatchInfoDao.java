package com.unison.lottery.weibo.data.service.store.persist.dao;

import java.util.Date;
import java.util.List;
import com.unison.lottery.weibo.data.service.store.persist.entity.BBMatchInfoPO;

/**
 * 篮球比赛信息DAO
 * 
 * @author Yang Bo
 */
public interface BBMatchInfoDao extends BasicDao<BBMatchInfoPO> {
	
	/**
	 * 查询篮球赛事数据
	 * @param qtMatchId	球探赛事
	 * @return
	 */
	BBMatchInfoPO queryBBMatchInfo(long qtMatchId);
	
	/**
	 * 查询篮球赛事信息
	 * @param homeTeamId
	 * @param guestTeamId
	 * @param from
	 * @param to
	 * @param size
	 * @return
	 */
	List<BBMatchInfoPO> findBBMatchs(long homeTeamId, long guestTeamId,
			Date from, Date to, int size);
	
	List<BBMatchInfoPO> findBBMatchPO(long teamId, Date from,Date to, int size);
	
	/**
	 * 查询篮球比赛信息
	 * @param teamId
	 * @param from
	 * @param to
	 * @param size
	 * @param matchStatus	赛事状态
	 * @return
	 */
	List<BBMatchInfoPO> findBBMatchPO(long teamId, Date from,Date to, int size, int matchStatus);
	
	/**
	 * 查询篮球联赛排名，说明：由于篮球赛事数据中的当前赛季和联赛信息表中的赛季内容不是完全匹配，
	 * 查询时可以将一组当前赛季传入作为查询条件查找.
	 * @param leagueName
	 * @param currMatchSeason
	 * @return
	 */
	List<BBMatchInfoPO> findBBMatchPO(String leagueName, String[] currMatchSeason);
}