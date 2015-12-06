package com.unison.lottery.api.odds.bo;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.commons.lang.time.DateUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.davcai.lottery.service.MatchNameService;
import com.davcai.lottery.service.QueryImmediateIndexInfoService;
import com.unison.lottery.api.odds.model.JCOdds;
import com.unison.lottery.api.protocol.common.SystemStatusKeyNames;
import com.unison.lottery.api.protocol.response.model.QueryImmediateIndexInfoResponse;
import com.unison.lottery.api.protocol.status.IStatusRepository;
import com.unison.lottery.api.protocol.status.ReturnStatus;
import com.unison.lottery.weibo.data.crawler.service.store.persist.dao.entity.BasketBallMatchPO;
import com.unison.lottery.weibo.data.crawler.service.store.persist.dao.entity.BasketMatchAsiaOuOddsInfoPO;
import com.unison.lottery.weibo.data.crawler.service.store.persist.dao.entity.BasketMatchOpOddsInfoPO;
import com.unison.lottery.weibo.data.crawler.service.store.persist.dao.entity.FbMatchAsiaOuOddsInfoPO;
import com.unison.lottery.weibo.data.crawler.service.store.persist.dao.entity.FbMatchBaseInfoPO;
import com.unison.lottery.weibo.data.crawler.service.store.persist.dao.entity.FbMatchOpOddsInfoPO;
import com.xhcms.lottery.lang.OddsType;

/**
 * 即时指数查询
 * 
 * @author baoxing.peng
 * @since 2015年3月24日下午4:00:03
 */
public class QueryImmediateIndexInfoBOImpl implements QueryImmediateIndexInfoBO {

	private Logger logger = LoggerFactory.getLogger(getClass());
	@Autowired
	private QueryImmediateIndexInfoService queryImmediateIndexInfoService;
	@Autowired
	private IStatusRepository status;
	@Autowired
	private MatchNameService matchNameService;
	@Override
	public QueryImmediateIndexInfoResponse queryOddsDataByMatchType(
			Integer matchType,String time,Long matchId, String leagueShortName) {
		QueryImmediateIndexInfoResponse queryImmediateIndexInfoResponse = new QueryImmediateIndexInfoResponse();
		ReturnStatus succStatus = status
				.getSystemStatusBySystemKey(SystemStatusKeyNames.QueryImmediateIndexInfo.QUERY_INDEX_INFO_SUCC);
		ReturnStatus failStatus = status
				.getSystemStatusBySystemKey(SystemStatusKeyNames.QueryImmediateIndexInfo.QUERY_INDEX_INFO_FAIL);

		queryImmediateIndexInfoResponse.setReturnStatus(failStatus);
		
		if (matchType != null) {
			queryImmediateIndexInfoResponse.setMatchType(matchType);
			Set<String> leagueShortNameSet = null;
			if (matchType == 0) { // 足球
				List<Object[]> fbMatchBaseInfoPOs = queryImmediateIndexInfoService
						.queryFbMatchInDays(time,matchId,leagueShortName);
				makeFbOddsData(queryImmediateIndexInfoResponse,
						fbMatchBaseInfoPOs);
				leagueShortNameSet = queryImmediateIndexInfoService.findAllFbLeagueShortNameInDays();
				queryImmediateIndexInfoResponse.setLeagueShortName(leagueShortNameSet);

			} else if (matchType == 1) { // 篮球
				List<Object[]> bbMatchInfoPOs = queryImmediateIndexInfoService.queryBbMatchInDays(time,matchId,leagueShortName);
				makeBbOddsData(queryImmediateIndexInfoResponse,bbMatchInfoPOs);
				leagueShortNameSet = queryImmediateIndexInfoService.findAllBbLeagueShortNameInDays();
				
			}
			queryImmediateIndexInfoResponse.setLeagueShortName(leagueShortNameSet);
			queryImmediateIndexInfoResponse.setReturnStatus(succStatus);
		}else{
			failStatus.setDescForClient("查询失败，赛事类型为null");
		}
		return queryImmediateIndexInfoResponse;
	}

	private void makeBbOddsData(
			QueryImmediateIndexInfoResponse queryImmediateIndexInfoResponse,
			List<Object[]> bbMatchInfoPOs) {
		if(bbMatchInfoPOs!=null&&!bbMatchInfoPOs.isEmpty()){
			String matchIds = "";
			for (Object[] object : bbMatchInfoPOs) {
				BasketBallMatchPO bbMatchBaseInfoPO = (BasketBallMatchPO) object[0];
				matchIds += bbMatchBaseInfoPO.getId() + ",";
			}
			matchIds = StringUtils.removeEnd(matchIds, ",");
			Map<String,List<BasketMatchOpOddsInfoPO>> opOddsInfoPOs = queryImmediateIndexInfoService.findBbOpOddsData(matchIds);
			Map<String, List<BasketMatchAsiaOuOddsInfoPO>> asianOddsInfoPOs = queryImmediateIndexInfoService.findBbAsianOuOddsData(matchIds,OddsType.ASIA_ODDS);
			Map<String, List<BasketMatchAsiaOuOddsInfoPO>> ouOddsInfoPOs = queryImmediateIndexInfoService.findBbAsianOuOddsData(matchIds, OddsType.BIGORSMALL_ODDS);
			Map<String, Map<String, Map>> oddsDataMap = queryImmediateIndexInfoService.createBbOdds(opOddsInfoPOs,asianOddsInfoPOs,ouOddsInfoPOs);

			List<JCOdds> matchList = new ArrayList<JCOdds>();

			for (Object[] object : bbMatchInfoPOs) {
				JCOdds jcOdds = new JCOdds();
				BasketBallMatchPO bbMatchBaseInfoPO = (BasketBallMatchPO) object[0];
				jcOdds.setCode(bbMatchBaseInfoPO.getJingcaiId());
				jcOdds.setGuestTeamName(matchNameService.getTeamShortNameByLeagueIdAndTeamName(
						(String)object[1],bbMatchBaseInfoPO.getGuestTeam()));
				jcOdds.setHomeTeamName(matchNameService.getTeamShortNameByLeagueIdAndTeamName(
						(String)object[1],bbMatchBaseInfoPO.getHomeTeam()));
				assemblyOddsForEveryMatch(oddsDataMap,object, jcOdds, bbMatchBaseInfoPO.getId(),bbMatchBaseInfoPO.getMatchTime());
				matchList.add(jcOdds);
			}
			queryImmediateIndexInfoResponse.setResultList(matchList);
		}
	}

	private void makeFbOddsData(
			QueryImmediateIndexInfoResponse queryImmediateIndexInfoResponse,
			List<Object[]> fbMatchBaseInfoPOs) {
		if (fbMatchBaseInfoPOs != null && !fbMatchBaseInfoPOs.isEmpty()) {
			String matchIds = "";
			for (Object[] object : fbMatchBaseInfoPOs) {
				FbMatchBaseInfoPO fbMatchBaseInfoPO = (FbMatchBaseInfoPO) object[0];
				matchIds += fbMatchBaseInfoPO.getId() + ",";
			}
			matchIds = StringUtils.removeEnd(matchIds, ",");
			List<FbMatchOpOddsInfoPO> fbMatchOpOddsInfoPOs = queryImmediateIndexInfoService
					.findOpMatchOddsData(matchIds);
			Map<String, List<FbMatchAsiaOuOddsInfoPO>> fbMatchAsiaOddsInfoPOs = queryImmediateIndexInfoService
					.findAsianOuOddsData(OddsType.ASIA_ODDS, matchIds);
			Map<String, List<FbMatchAsiaOuOddsInfoPO>> fbMatchOuOddsInfoPOs = queryImmediateIndexInfoService
					.findAsianOuOddsData(OddsType.BIGORSMALL_ODDS,
							matchIds);
			Map<String, Map<String,Map>> oddsDataMap = queryImmediateIndexInfoService
					.createFbOdds(fbMatchOpOddsInfoPOs,
							fbMatchAsiaOddsInfoPOs,
							fbMatchOuOddsInfoPOs);
			List<JCOdds> matchList = new ArrayList<JCOdds>();

			for (Object[] object : fbMatchBaseInfoPOs) {
				JCOdds jcOdds = new JCOdds();
				FbMatchBaseInfoPO fbMatchBaseInfoPO = (FbMatchBaseInfoPO) object[0];
				jcOdds.setCode(fbMatchBaseInfoPO.getJingcaiId());
				jcOdds.setGuestTeamName(fbMatchBaseInfoPO
						.getGuestTeamName());
				jcOdds.setHomeTeamName(fbMatchBaseInfoPO
						.getHomeTeamName());
				assemblyOddsForEveryMatch(oddsDataMap, object,
						jcOdds, fbMatchBaseInfoPO.getId(),fbMatchBaseInfoPO.getMatchTime());
				matchList.add(jcOdds);
			}
			queryImmediateIndexInfoResponse.setResultList(matchList);

		}
	}

	private void assemblyOddsForEveryMatch(Map<String, Map<String,Map>> oddsDataMap, Object[] object, JCOdds jcOdds,
			Long id,Date matchTime) {
		jcOdds.setLeagueShortName((String) object[1]);
		jcOdds.setMatchTime(matchTime);
		jcOdds.setMatchId(id);
		Map<String, Map> corpMap = null;
		if (oddsDataMap != null) {
			corpMap = oddsDataMap
					.get(String.valueOf(id));
		}
		if (corpMap != null) {
			List<Map> oddsData = new ArrayList<Map>();
			jcOdds.setItemList(oddsData);
			for (Entry<String, Map> entry : corpMap.entrySet()) {
				Map<String, String> odds = new HashMap<String, String>();
				Map<String, String> data = entry.getValue();
				String asian_init = data.get("asian_init");
				String europe_init = data.get("europe_init");
				String bigsmall_init = data
						.get("bigsmall_init");
				if (asian_init != null) {
					makeOddsDataMap(odds, data, asian_init,
							"asian");
				}
				if (europe_init != null) {
					makeOddsDataMap(odds, data, europe_init,
							"europe");
				}
				if (bigsmall_init != null) {
					makeOddsDataMap(odds, data, bigsmall_init,
							"bigsmall");
				}
				odds.put("corpId", entry.getKey());
				oddsData.add(odds);
			}
		}
	}

	private void makeOddsDataMap(Map<String, String> odds,
			Map<String, String> data, String oddInit, String key) {
		String odds_max = data.get(key);
		String[] odds_data = odds_max.split(",");
		String oddChange = "";
		// 生成变化趋势
		if (odds_data.length >= 6) {
			oddChange = maxEqualOrSmall(Double.valueOf(odds_data[0]),
					Double.valueOf(odds_data[3]));
			oddChange += ","
					+ maxEqualOrSmall(Double.valueOf(odds_data[1]),
							Double.valueOf(odds_data[4]));
			oddChange += ","
					+ maxEqualOrSmall(Double.valueOf(odds_data[2]),
							Double.valueOf(odds_data[5]));
		} else {
			oddChange = "0,0,0";
		}
		odds.put(key + "OddsData", odds_data[0] + "," + odds_data[1] + ","
				+ odds_data[2] + "," + oddInit);
		odds.put(key + "Change", oddChange);
	}

	private String maxEqualOrSmall(double oddsNewer, double oddsOlder) {
		// TODO Auto-generated method stub
		String asianChange;
		if (oddsNewer > oddsOlder) {
			asianChange = "1";
		} else if (oddsNewer == oddsOlder) {
			asianChange = "0";
		} else {
			asianChange = "-1";
		}
		return asianChange;
	}

}
