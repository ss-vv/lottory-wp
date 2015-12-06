package com.davcai.lottery.dao;

import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Set;

import com.unison.lottery.weibo.data.crawler.service.store.persist.dao.entity.FbLeagueScorePO;
import com.unison.lottery.weibo.data.crawler.service.store.persist.dao.entity.FbLeagueScoreRulePO;
import com.unison.lottery.weibo.data.crawler.service.store.persist.dao.entity.FbMatchAsiaOuOddsInfoPO;
import com.unison.lottery.weibo.data.crawler.service.store.persist.dao.entity.FbMatchBaseInfoPO;
import com.unison.lottery.weibo.data.crawler.service.store.persist.dao.entity.FbMatchOpOddsInfoPO;

/**
 *
 * @author baoxing.peng@davcai.com
 *
 * @since 2015年1月27日 下午2:33:38
 */
public interface JCZQAnalyseDao {

	Object[] findFbMatchInfoByDavcaiMatchId(String daVCaiMatchId);
	/**
	 * 获取近20场记录
	 * @param homeTeamId
	 * @param guestTeamId
	 * @param latestNum 
	 * @return
	 */
	List<FbMatchBaseInfoPO> queryFbMatchAgainstLatest_20(String homeTeamId,
			String guestTeamId,Integer latestNum);
	/**
	 * 球队近期战绩
	 * @param teamId 球队id
	 * @param rencentNum 近几场
	 * @return
	 */
	List<FbMatchBaseInfoPO> queryFbTeamMatchRecord_latest(
			String teamId, int rencentNum);
	/**
	 * 联赛总积分榜
	 * @param leagueId
	 * @return
	 */
	List<FbLeagueScorePO> queryLeagueOverallStandings(String leagueId);
	/**
	 * 联赛积分球队升降级规则
	 * @param leagueId
	 * @return
	 */
	List<FbLeagueScoreRulePO> queryLeagueOverallStandingsRule(String leagueId);
	/**
	 * 查询联赛当前赛季
	 * @param leagueId
	 * @return
	 */
	String queryNowSeasonByLeagueId(String leagueId);
	/**
	 * 
	 * @param leagueId
	 * @param qtSource
	 * @param seasonName
	 * @return
	 */
	Set<String> queryQtFbTeamByLeagueId(
			String leagueId, int i, String seasonName);
	/**
	 * 查询足球比赛id
	 * @param daVCaiMatchId
	 * @return
	 */
	Long queryFbMatchIdByDavcaiId(String daVCaiMatchId);
	/**
	 * 查询比赛赔率
	 * @param matchId
	 * @param corpId 
	 * @return
	 */
	List<Object[]> queryFbMatchEuroOddsByMatchId(Long matchId, String corpId);
	/**
	 * 查询比赛亚赔初赔
	 * @param matchId 比赛id
	 * @param order 最大最小值(max or min)
	 * @return
	 */
	List<Object[]> queryFbMatchAsianOddsById(Long matchId,String order);
	/**
	 * 
	 * @param matchId
	 * @param corpId
	 * @return
	 */
	List<FbMatchAsiaOuOddsInfoPO> queryFbMatchAsianOddsByIdAndCorpId(Long matchId,
			Integer corpId);
	/**
	 * 
	 * @param date
	 * @param date2
	 * @return
	 */
	List<Object[]> queryFbMatchByPlayingTime(Date date, Date date2);
	
	/**
	 * 查询足球欧赔
	 * @param euroCorpId
	 * @param bsIds
	 * @return
	 */
	List<FbMatchOpOddsInfoPO> queryFbOpOddsByBsIdsAndCorpIds(String euroCorpId,
			String bsIds);
	/**
	 * 
	 * @param asianOuCorpId
	 * @param bsIds
	 * @param oddsType
	 * @param maxNum
	 * @return
	 */
	List<FbMatchAsiaOuOddsInfoPO> queryFbMatchAsianOrOuOddsByBsIdsAndCorpIds(
			String asianOuCorpId, String bsIds, int oddsType, int maxNum);
	/**
	 * 查询亚赔或大小的初赔
	 * @param bsIds
	 * @param asianOuCorpId
	 * @param oddsType
	 * @return
	 */ 
	List<FbMatchAsiaOuOddsInfoPO> queryFbMatchInitAsianOrOuOddsByBsIdsAndCorpIds(
			String bsIds, String asianOuCorpId, int oddsType);
	
	List<Object[]> queryFbMatchAllInfoByPlayingTime(Date date,
			Date date2,String leagueShortName);
	List<Object[]> getZQbifen(Date from, Date to,
			String leagueShortName);
	/**
	 * 
	 * @param matchId
	 * @param corpId
	 * @param oddsType
	 * @return
	 */
	List<FbMatchAsiaOuOddsInfoPO> queryFbMatchAsianOrOuOddsByMatchIdAndCorpId(
			Long matchId, String corpId, String oddsType);
	/**
	 * 
	 * @param matchId
	 * @return
	 */
	Date queryfbMatchTimeById(Long matchId);
	List<String> queryAllLeagueShortNameInDays(Date from,Date to);
	
	List<Object[]> findFbMatchInfoByDavcaiMatchId(Collection<Long> ids, int matchTimeInterval);
	
	List<Object[]> getZQResoureData(Date now, Date from, Date to);
	Object[] getZQResoureDataById(String detailId);
	
	FbMatchBaseInfoPO findFbById(long id);
	
	List<Object[]> getZQResoureData(Date from, Date to);
}
