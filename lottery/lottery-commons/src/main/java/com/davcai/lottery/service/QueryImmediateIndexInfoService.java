package com.davcai.lottery.service;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.unison.lottery.weibo.data.crawler.service.store.persist.dao.entity.BasketMatchAsiaOuOddsInfoPO;
import com.unison.lottery.weibo.data.crawler.service.store.persist.dao.entity.BasketMatchOpOddsInfoPO;
import com.unison.lottery.weibo.data.crawler.service.store.persist.dao.entity.FbMatchAsiaOuOddsInfoPO;
import com.unison.lottery.weibo.data.crawler.service.store.persist.dao.entity.FbMatchBaseInfoPO;
import com.unison.lottery.weibo.data.crawler.service.store.persist.dao.entity.FbMatchOpOddsInfoPO;
import com.xhcms.lottery.lang.OddsType;

public interface QueryImmediateIndexInfoService {

	List<Object[]> queryFbMatchInDays(String time, Long matchId, String leagueShortName);

	List<FbMatchOpOddsInfoPO> findOpMatchOddsData(String matchIds);

	Map<String, List<FbMatchAsiaOuOddsInfoPO>> findAsianOuOddsData(OddsType asiaOdds,
			String matchIds);

	Map<String, Map<String, Map>> createFbOdds(
			List<FbMatchOpOddsInfoPO> fbMatchOpOddsInfoPOs,
			Map<String, List<FbMatchAsiaOuOddsInfoPO>> fbMatchAsiaOddsInfoPOs,
			Map<String, List<FbMatchAsiaOuOddsInfoPO>> fbMatchOuOddsInfoPOs);

	List<Object[]> queryBbMatchInDays(String time,Long matchId, String leagueShortName);

	Map<String, List<BasketMatchOpOddsInfoPO>> findBbOpOddsData(String matchIds);

	/**
	 * 
	 * @param matchIds
	 * @param oddsType
	 * @return
	 */
	Map<String, List<BasketMatchAsiaOuOddsInfoPO>> findBbAsianOuOddsData(
			String matchIds, OddsType oddsType);

	Map<String, Map<String, Map>> createBbOdds(
			Map<String, List<BasketMatchOpOddsInfoPO>> opOddsInfoPOs,
			Map<String, List<BasketMatchAsiaOuOddsInfoPO>> asianOddsInfoPOs,
			Map<String, List<BasketMatchAsiaOuOddsInfoPO>> ouOddsInfoPOs);

	/**
	 * 查询单个比赛的指定公司的欧赔
	 * @param matchId
	 * @param corpId
	 * @return
	 */
	Map<String, Object> findFbMatchOpOdds(Long matchId, String corpId);

	/**
	 * 
	 * @param matchId
	 * @param corpId
	 * @param oddsType
	 * @return
	 */
	Map<String, String> findFbMatchAsianOrOuOdds(Long matchId,
			String corpId, String oddsType);

	Map<String, String> findBbEuroOddsByMatchIdAndCorpId(Long matchId,
			String corpId);

	/**
	 * 
	 * @param matchId
	 * @param corpId
	 * @param oddsType
	 * @return
	 */
	Map<String, String> findBbAsianOrOuOddsByMatchIdAndCorpId(Long matchId,
			String corpId, String oddsType);

	String calculateTimeToMatchTime(Date timestamp, Date matchTime, int count,
			int length);

	/**
	 * 获取当天比赛的联赛
	 * @return
	 */
	Set<String> findAllFbLeagueShortNameInDays();

	Set<String> findAllBbLeagueShortNameInDays();

}
