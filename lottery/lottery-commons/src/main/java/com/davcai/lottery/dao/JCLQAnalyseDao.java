package com.davcai.lottery.dao;

import java.util.Collection;
import java.util.Date;
import java.util.List;

import com.unison.lottery.weibo.data.crawler.service.store.persist.dao.entity.BasketBallLeagueScorePO;
import com.unison.lottery.weibo.data.crawler.service.store.persist.dao.entity.BasketBallMatchPO;
import com.unison.lottery.weibo.data.crawler.service.store.persist.dao.entity.BasketMatchAsiaOuOddsInfoPO;
import com.unison.lottery.weibo.data.crawler.service.store.persist.dao.entity.BasketMatchOpOddsInfoPO;
import com.unison.lottery.weibo.data.crawler.service.store.persist.dao.entity.FbMatchBaseInfoPO;
import com.xhcms.lottery.commons.data.BasketBallMatchData;

/**
 *
 * @author baoxing.peng@davcai.com
 * 
 * @since 2015年3月2日 下午5:21:00
 */
public interface JCLQAnalyseDao {

	List<BasketBallMatchPO> queryBbMatchAgainstLatest_20(String homeTeamId,
			String guestTeamId, Integer latestNum);

	Object[] findBbMatchInfoByDavcaiMatchId(String daVCaiMatchId);

	/**
	 * 
	 * @param leagueId
	 * @param subLeagueId
	 * @return
	 */
	List<BasketBallLeagueScorePO> queryLeagueOverallStandings(String leagueId,
			String subLeagueId);

	List<Object[]> queryBbMatchByPlayingTime(Date from, Date to);

	/**
	 * 
	 * @param corpNames 
	 * @param bsIds
	 * @param maxNum 每个比赛每个公司的最新maxNum条记录
	 * @return
	 */
	List<BasketMatchOpOddsInfoPO> queryBbOpOddsByBsIdsAndCorpIds(
			String corpNames, String bsIds, Integer maxNum);

	/**
	 * 
	 * @param corpNames
	 * @param bsIds
	 * @param type 赔率类型 1亚盘 2大小 
	 * @param maxNum 每个比赛每个公司的最新maxNum条记录
	 * @return
	 */
	List<BasketMatchAsiaOuOddsInfoPO> queryBbMatchAsianOrOuOddsByBsIdsAndCorpIds(
			String corpNames, String bsIds, int type, Integer maxNum);

	List<Object[]> queryBbMatchAllInfoByPlayingTime(Date date, Date date2, String leagueShortNames);

	List<BasketMatchOpOddsInfoPO> queryBbOpInitOddsByBsIdsAndCorpIds(
			String bbOpCoprNames, String matchIds);

	List<BasketMatchAsiaOuOddsInfoPO> queryBbMatchAsianOrOuInitOddsByBsIdsAndCorpIds(
			String bbasianoucorpnames, String matchIds, int type);
	/**
	 * 查询篮球比分直播（客户端用）
	 * @param date
	 * @param date2
	 * @param leagueShortName
	 * @return
	 */
	List<Object[]> getBBbifen(Date date, Date date2, String leagueShortName);

	List<BasketMatchOpOddsInfoPO> queryBbOpAllOddsByBsIdAndCorpId(
			String corpName, Long matchId);

	List<BasketMatchAsiaOuOddsInfoPO> queryBbAsianOrOuAllOdds(Long matchId,
			String corpName, String oddsType);

	Date queryBbMatchTimeById(Long matchId);

	List<String> queryAllLeagueShortNameInDays(Date from, Date to);

	List<Object[]> findBbMatchInfoByDavcaiMatchId(Collection<Long> ids, int matchTimeInterval);

	boolean saveFbMatchBaseInfoPO(FbMatchBaseInfoPO newFoBaseInfoPO);

	List<Object[]> getBBResoureData(Date date, Date from, Date to);

	Object[] getBBResoureData(String dtailId);

	boolean saveBbMatchBaseInfoPO(BasketBallMatchData newBbBaseInfoPO);

	List<Object[]> getLQResoureData(Date from, Date to);

}
