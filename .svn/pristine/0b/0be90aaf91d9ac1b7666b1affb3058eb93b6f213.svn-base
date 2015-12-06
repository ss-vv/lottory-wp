package com.davcai.lottery.service.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.collections.ComparatorUtils;
import org.apache.commons.collections.ListUtils;
import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.commons.lang.time.DateUtils;
import org.apache.commons.lang3.StringUtils;
import org.jasypt.commons.CommonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.davcai.lottery.dao.JCLQAnalyseDao;
import com.davcai.lottery.dao.JCZQAnalyseDao;
import com.davcai.lottery.service.QueryImmediateIndexInfoService;
import com.unison.lottery.weibo.data.crawler.service.store.persist.dao.entity.BasketBallMatchPO;
import com.unison.lottery.weibo.data.crawler.service.store.persist.dao.entity.BasketMatchAsiaOuOddsInfoPO;
import com.unison.lottery.weibo.data.crawler.service.store.persist.dao.entity.BasketMatchOpOddsInfoPO;
import com.unison.lottery.weibo.data.crawler.service.store.persist.dao.entity.FbMatchAsiaOuOddsInfoPO;
import com.unison.lottery.weibo.data.crawler.service.store.persist.dao.entity.FbMatchBaseInfoPO;
import com.unison.lottery.weibo.data.crawler.service.store.persist.dao.entity.FbMatchOpOddsInfoPO;
import com.xhcms.lottery.lang.OddsType;
import com.xhcms.lottery.utils.ServiceUtils;

/**
 * @author baoxing.peng@davcai.com
 *
 */
public class QueryImmediateIndexInfoServiceImpl implements QueryImmediateIndexInfoService{

	private static final String EUROPE_KEY = "europe";
	private static final String ASIAN_KEY = "asian";
	private static final String BIGSMALL_KEY = "bigsmall";
	private static final Integer MAX_SIZE = 6;
	@Autowired
	private JCZQAnalyseDao jCZQAnalyseDao;
	@Autowired
	private JCLQAnalyseDao jCLQAnalyseDao;
	private static Map<String, String> fbOpCorpId = new HashMap<>(13);
	private static String opcorpIds = "";
	private static final String asianCorpIds = "'1','3','4','8','12','14','17','22','23','24','31','35'";
	private static final String bbOpCorpNames = "'1','2','3','8','9'";
	private Logger logger = LoggerFactory.getLogger(getClass());
	private static final String bbAsianOuCorpNames ="'1','2','3','8','9'";
	static {
		opcorpIds = "'80','545','82','281','90','81','517','16','499','18','474','659','110','115','71','104','222'";
		// 数据库足球欧赔公司id与即时欧赔公司id对照
		fbOpCorpId.put("1", "80");
		fbOpCorpId.put("3", "545");
		fbOpCorpId.put("4", "82");
		fbOpCorpId.put("8", "281");
		fbOpCorpId.put("12", "90");
		fbOpCorpId.put("14", "81");
		fbOpCorpId.put("17", "517");
		fbOpCorpId.put("22", "16");
		fbOpCorpId.put("23", "499");
		fbOpCorpId.put("24", "18");
		fbOpCorpId.put("31", "474");
		fbOpCorpId.put("35", "659");
		fbOpCorpId.put("7", "110");
		fbOpCorpId.put("9", "115");
		fbOpCorpId.put("350", "71");
		fbOpCorpId.put("19", "104");
		fbOpCorpId.put("33","222");
		fbOpCorpId.put("110", "7");
		fbOpCorpId.put("115", "9");
		fbOpCorpId.put("71", "350"); 
		fbOpCorpId.put("104", "19");
		fbOpCorpId.put("222","33");
		fbOpCorpId.put("80", "1");
		fbOpCorpId.put("545", "3");
		fbOpCorpId.put("82", "4");
		fbOpCorpId.put("281", "8");
		fbOpCorpId.put("90", "12");
		fbOpCorpId.put("81", "14");
		fbOpCorpId.put("517", "17");
		fbOpCorpId.put("16", "22");
		fbOpCorpId.put("499", "23");
		fbOpCorpId.put("18", "24");
		fbOpCorpId.put("474", "31");
		fbOpCorpId.put("659", "35");

	}
	@Override
	public List<Object[]> queryFbMatchInDays(String time,Long matchId, String leagueShortName) {
		List<Date> fromTo = ServiceUtils.getDateOfToDay();
		String leagueShortNames = makeQueryLeagueShortNameString(leagueShortName);
		List<Object[]> fbMatchBaseInfoPOs = jCZQAnalyseDao.queryFbMatchAllInfoByPlayingTime(fromTo.get(0), fromTo.get(1),leagueShortNames);
		List<Object[]> matchesToReturn =  new ArrayList<Object[]>();
		int from = 0;
		int to = 0;
		if(!fbMatchBaseInfoPOs.isEmpty()){
			//赛事结果排序
			Collections.sort(fbMatchBaseInfoPOs, new MatchComparator());
		}
		if(matchId!=null&&matchId!=0){
			for(int i=0;i<fbMatchBaseInfoPOs.size();i++){
				Object[] objects = fbMatchBaseInfoPOs.get(i);
				FbMatchBaseInfoPO fbMatch = (FbMatchBaseInfoPO) objects[0];
				if(fbMatch.getId()==matchId){
					from = i+1;
					break;
				}
			}
			
			if(from+MAX_SIZE-1<fbMatchBaseInfoPOs.size()){
				to = from+MAX_SIZE;
			}else{
				to = fbMatchBaseInfoPOs.size();
			}
		}else{
			if(MAX_SIZE<fbMatchBaseInfoPOs.size()){
				to = MAX_SIZE;
			}else{
				to = fbMatchBaseInfoPOs.size();
			}
		}
		for(int i=from;i<to;i++){
			matchesToReturn.add(fbMatchBaseInfoPOs.get(i));
		}
		return matchesToReturn;
	}
	private String makeQueryLeagueShortNameString(String leagueShortName) {
		if(StringUtils.isNotBlank(leagueShortName)){
			String[] names = StringUtils.split(leagueShortName,",");
			StringBuilder sb = new StringBuilder("");
			for(String name:names){
				sb.append("'"+name+"',");
			}
			return StringUtils.removeEnd(sb.toString(), ",");
		}
		
		return null;
	}
	@Override
	public List<FbMatchOpOddsInfoPO> findOpMatchOddsData(String matchIds) {
		return jCZQAnalyseDao.queryFbOpOddsByBsIdsAndCorpIds(opcorpIds, matchIds);
	}
	@Override
	public Map<String,List<FbMatchAsiaOuOddsInfoPO>> findAsianOuOddsData(OddsType oddsType,
			String matchIds) {
		int asianOuType = oddsType==OddsType.ASIA_ODDS?1:2;
		List<FbMatchAsiaOuOddsInfoPO> asiaOuInitOddsInfoPOs = jCZQAnalyseDao.
				queryFbMatchInitAsianOrOuOddsByBsIdsAndCorpIds(matchIds, asianCorpIds,asianOuType);
		List<FbMatchAsiaOuOddsInfoPO> asiaOuOddsInfoPOs = jCZQAnalyseDao.queryFbMatchAsianOrOuOddsByBsIdsAndCorpIds(asianCorpIds, matchIds, asianOuType, 2);
		Map<String, List<FbMatchAsiaOuOddsInfoPO>> asianOuOdds = new HashMap<>();
		asianOuOdds.put("init_odds", asiaOuInitOddsInfoPOs);
		asianOuOdds.put("max_odds", asiaOuOddsInfoPOs);
		return asianOuOdds;
	}
	
	@SuppressWarnings("rawtypes")
	private void makeOddsData(List<Object> oddsObjects, Map<String, Map<String, Map>> oddsMap) {
		String corpId = "";
		String playCode = "";
		String key = "";
		Map<String, Map> corpIdMap;
		Map<String, String> odds;
		try {
			for (Object oddsObject : oddsObjects) {
				String initOddsKey = "";
				String initOddsData = "";
				String oddsData = "";
				if (oddsObject instanceof FbMatchOpOddsInfoPO) {
					FbMatchOpOddsInfoPO fbMatchOpOddsInfoPO = (FbMatchOpOddsInfoPO) oddsObject;
					String[] euroOdds = fbMatchOpOddsInfoPO
							.getEuroOdds().split("!");
					corpId = fbOpCorpId.get(fbMatchOpOddsInfoPO.getCorpId());
					playCode = String.valueOf(fbMatchOpOddsInfoPO.getBsId());
					initOddsData = euroOdds[euroOdds.length - 1];
					initOddsData = StringUtils.substring(initOddsData,0,initOddsData.lastIndexOf(","));
					for (int i = 0; i < euroOdds.length; i++) {
						oddsData += StringUtils.substring(euroOdds[i],0,euroOdds[i].lastIndexOf(",")) + ",";
						if (i >= 1) {
							break;
						}
					}
					oddsData=StringUtils.removeEnd(oddsData, ",");
					key = EUROPE_KEY;
					initOddsKey = EUROPE_KEY+"_init";
				} else {
					FbMatchAsiaOuOddsInfoPO fbMatchAsiaOuOddsInfoPO = (FbMatchAsiaOuOddsInfoPO) oddsObject;
					Integer oddsType = fbMatchAsiaOuOddsInfoPO
							.getOddsType();
					Integer bsId = fbMatchAsiaOuOddsInfoPO.getBsId();
					corpId = fbMatchAsiaOuOddsInfoPO.getCorpId();
					playCode = String.valueOf(bsId);
					oddsData = fbMatchAsiaOuOddsInfoPO.getHomeWinOdds()
							+ ","
							+ fbMatchAsiaOuOddsInfoPO.getHandicap()
							+ ","
							+ fbMatchAsiaOuOddsInfoPO.getGuestWinOdds();
					if (oddsType == 1) {
						key = ASIAN_KEY;
						initOddsKey = ASIAN_KEY+"_init";
					} else if (oddsType == 2) {
						key = BIGSMALL_KEY;
					}
				}
				if (oddsMap.get(playCode) != null) {
					corpIdMap = oddsMap.get(playCode);
					if (corpIdMap.get(corpId) != null) {
						odds = corpIdMap.get(corpId);
						if(odds.get(key)!=null){
							odds.put(key,odds.get(key)+","+oddsData);
						}else{
							odds.put(key,oddsData);
							if(StringUtils.isNotBlank(initOddsData)){
								odds.put(initOddsKey, initOddsData);
							}
						}
					} else {
						odds = new HashMap<String, String>();
						odds.put(key,oddsData);
						if(StringUtils.isNotBlank(initOddsData)){
							odds.put(initOddsKey, initOddsData);
						}
					}
				} else {
					corpIdMap = new HashMap<String, Map>();
					odds = new HashMap<String, String>();
					odds.put(key,oddsData);
					if(StringUtils.isNotBlank(initOddsData)){
						odds.put(initOddsKey, initOddsData);
					}
				}
				corpIdMap.put(corpId, odds);
				oddsMap.put(playCode, corpIdMap);
			}
		} catch (Exception e) {
			logger.error("解析赔率数据时出错:{}",e);
		}
	}
	@Override
	public Map<String, Map<String, Map>> createFbOdds(
			List<FbMatchOpOddsInfoPO> fbMatchOpOddsInfoPOs,
			Map<String, List<FbMatchAsiaOuOddsInfoPO>> fbMatchAsiaOddsInfoPOs,
			Map<String, List<FbMatchAsiaOuOddsInfoPO>> fbMatchOuOddsInfoPOs) {
		List<Object> oddsObjects = new ArrayList<Object>();
		oddsObjects.addAll(fbMatchOpOddsInfoPOs);
		oddsObjects.addAll(fbMatchAsiaOddsInfoPOs.get("max_odds"));
		oddsObjects.addAll(fbMatchOuOddsInfoPOs.get("max_odds"));
		Map<String, Map<String, Map>> oddsMap = new HashMap<String, Map<String, Map>>();
		Map<String, Map> corpIdMap = null;
		Map<String, String> odds = null;
		
		makeOddsData(oddsObjects, oddsMap);
		fbMatchAsiaOddsInfoPOs.get("init_odds").addAll(fbMatchOuOddsInfoPOs.get("init_odds"));
		for(FbMatchAsiaOuOddsInfoPO fbMatchAsiaOuOddsInfoPO : fbMatchAsiaOddsInfoPOs.get("init_odds")){
			String playingCode =String.valueOf(fbMatchAsiaOuOddsInfoPO.getBsId());
			if(oddsMap.get(playingCode)!=null){
				if(oddsMap.get(playingCode).get(fbMatchAsiaOuOddsInfoPO.getCorpId())!=null){
					String init_key = (fbMatchAsiaOuOddsInfoPO.getOddsType()==1?ASIAN_KEY:BIGSMALL_KEY)+"_init";
					((Map<String, String>) oddsMap.get(playingCode).get(fbMatchAsiaOuOddsInfoPO.getCorpId())).put(init_key, 
							fbMatchAsiaOuOddsInfoPO.getHomeWinOdds()+","+
							fbMatchAsiaOuOddsInfoPO.getHandicap()+","+
							fbMatchAsiaOuOddsInfoPO.getGuestWinOdds());
				}
			}
		}
		return oddsMap;
	}
	@Override
	public List<Object[]> queryBbMatchInDays(String time,Long matchId,String leagueShortName) {
		List<Date> fromTo = ServiceUtils.getDateOfToDay();
		String leagueShortNames = makeQueryLeagueShortNameString(leagueShortName);
		List<Object[]> bbMatchInfos = jCLQAnalyseDao.queryBbMatchAllInfoByPlayingTime(fromTo.get(0), fromTo.get(1),leagueShortNames);
		List<Object[]> matchesToReturn = new ArrayList<Object[]>();
		int from = 0;
		int to = 0;
		if(!bbMatchInfos.isEmpty()){
			Collections.sort(bbMatchInfos,new MatchComparator());
		}
		if(matchId!=null&&matchId!=0){
			for(int i=0;i<bbMatchInfos.size();i++){
				Object[] objects = bbMatchInfos.get(i);
				BasketBallMatchPO fbMatch = (BasketBallMatchPO) objects[0];
				if(fbMatch.getId()==matchId){
					from = i+1;
					break;
				}
			}
			if(from+MAX_SIZE-1<bbMatchInfos.size()){
				to = from+MAX_SIZE;
			}else{
				to = bbMatchInfos.size();
			}
			
		}else{
			if(MAX_SIZE<bbMatchInfos.size()){
				to = MAX_SIZE;
			}else{
				to = bbMatchInfos.size();
			}
		}
		for(int i=from;i<to;i++){
			matchesToReturn.add(bbMatchInfos.get(i));
		}
		
		return matchesToReturn;
		
	}
	@Override
	public Map<String, List<BasketMatchOpOddsInfoPO>> findBbOpOddsData(String matchIds) {
		//获取最新的欧赔数据
 		List<BasketMatchOpOddsInfoPO> basketMatchNowOpOddsInfoPOs = jCLQAnalyseDao.queryBbOpOddsByBsIdsAndCorpIds(bbOpCorpNames, matchIds, 2);
 		List<BasketMatchOpOddsInfoPO> basketMatchInitOpOddsInfoPOs = jCLQAnalyseDao.queryBbOpInitOddsByBsIdsAndCorpIds(bbOpCorpNames,matchIds);
 		Map<String, List<BasketMatchOpOddsInfoPO>> bbMap = new HashMap<String, List<BasketMatchOpOddsInfoPO>>();
 		bbMap.put("init_odds", basketMatchInitOpOddsInfoPOs);
 		bbMap.put("max_odds", basketMatchNowOpOddsInfoPOs);
 		return bbMap;
	}
	@Override
	public Map<String, List<BasketMatchAsiaOuOddsInfoPO>> findBbAsianOuOddsData(
			String matchIds, OddsType oddsType) {
		int type = oddsType==OddsType.ASIA_ODDS?1:2;
		//获取最新的亚赔或大小数据
 		List<BasketMatchAsiaOuOddsInfoPO> basketMatchAsiaOuNowOddsInfoPOs = jCLQAnalyseDao.queryBbMatchAsianOrOuOddsByBsIdsAndCorpIds(bbAsianOuCorpNames, matchIds, type, 2);
 		List<BasketMatchAsiaOuOddsInfoPO> asiaOuOddsInfoPOs = jCLQAnalyseDao.queryBbMatchAsianOrOuInitOddsByBsIdsAndCorpIds(bbAsianOuCorpNames, matchIds, type);
		Map<String, List<BasketMatchAsiaOuOddsInfoPO>> asianOuOdds = new HashMap<>();
		asianOuOdds.put("init_odds", asiaOuOddsInfoPOs);
		asianOuOdds.put("max_odds", basketMatchAsiaOuNowOddsInfoPOs);
		return asianOuOdds;
	}
	@Override
	public Map<String, Map<String, Map>> createBbOdds(
			Map<String, List<BasketMatchOpOddsInfoPO>> opOddsInfoPOs,
			Map<String, List<BasketMatchAsiaOuOddsInfoPO>> asianOddsInfoPOs,
			Map<String, List<BasketMatchAsiaOuOddsInfoPO>> ouOddsInfoPOs) {
		Map<String, Map<String,Map>> oddsMap = new HashMap<String, Map<String,Map>>();
		List<Object> oddsObjects = new ArrayList<Object>();
		//获取最新的赔率
		oddsObjects.addAll(opOddsInfoPOs.get("max_odds"));
		oddsObjects.addAll(asianOddsInfoPOs.get("max_odds"));
		oddsObjects.addAll(ouOddsInfoPOs.get("max_odds"));
		Map<String, Map> corpIdMap = null;
		Map<String, String> odds = null;
		String corpId = "";
		String playCode = "";
		String oddsData = "";
		String key = "";
		for (int i = 0; i < oddsObjects.size(); i++) {
			if (oddsObjects.get(i) instanceof BasketMatchOpOddsInfoPO) {
				BasketMatchOpOddsInfoPO basketMatchOpOddsInfoPO = (BasketMatchOpOddsInfoPO) oddsObjects.get(i);
				playCode = String.valueOf(basketMatchOpOddsInfoPO.getBsId());
				corpId = basketMatchOpOddsInfoPO.getCorpId();
				oddsData = basketMatchOpOddsInfoPO.getHomeWinOdds()
						+ ",0.0,"
						+ basketMatchOpOddsInfoPO
								.getGuestWinOdds();
				key = EUROPE_KEY;
				
			}else{
				BasketMatchAsiaOuOddsInfoPO basketMatchAsianOuOddsInfoPO = (BasketMatchAsiaOuOddsInfoPO) oddsObjects.get(i);
				Integer oddsType = basketMatchAsianOuOddsInfoPO.getOddsType();
				corpId = basketMatchAsianOuOddsInfoPO.getCorpId();
				playCode = String.valueOf(basketMatchAsianOuOddsInfoPO.getBsId());
				oddsData = basketMatchAsianOuOddsInfoPO.getHomeWinOdds()
				+ "," + basketMatchAsianOuOddsInfoPO.getHandicapOrScore() + ","
				+ basketMatchAsianOuOddsInfoPO
						.getGuestWinOdds();
				if(oddsType == 1){
					key = ASIAN_KEY;
				}else if(oddsType == 2){
					key = BIGSMALL_KEY;
				}
			}
			if (oddsMap.get(playCode) != null) {
				corpIdMap = oddsMap.get(playCode);
				if (corpIdMap.get(corpId) != null) {
					odds = corpIdMap.get(corpId);
					if(odds.get(key)!=null){
						odds.put(key,odds.get(key)+","+oddsData);
					}else{
						odds.put(key,oddsData);
					}
				} else {
					odds = new HashMap<String, String>();
					odds.put(key,oddsData);
				}
			} else {
				corpIdMap = new HashMap<String, Map>();
				odds = new HashMap<String, String>();
				odds.put(key,oddsData);
			}
			corpIdMap.put(corpId, odds);
			oddsMap.put(playCode, corpIdMap);
		}
		//对初赔赋值
		oddsObjects.clear();
		oddsObjects.addAll(opOddsInfoPOs.get("init_odds"));
		oddsObjects.addAll(asianOddsInfoPOs.get("init_odds"));
		oddsObjects.addAll(ouOddsInfoPOs.get("init_odds"));
		for(Object object:oddsObjects){
			if(object instanceof BasketMatchOpOddsInfoPO){
				
				BasketMatchOpOddsInfoPO opOddsInfoPO = (BasketMatchOpOddsInfoPO) object;
				String data = opOddsInfoPO.getHomeWinOdds()+",0.0,"+opOddsInfoPO.getGuestWinOdds();
				oddsMap.get(String.valueOf(opOddsInfoPO.getBsId())).get(opOddsInfoPO.getCorpId()).put(EUROPE_KEY+"_init", data);
			}else {
				BasketMatchAsiaOuOddsInfoPO asiaOuOddsInfoPO = (BasketMatchAsiaOuOddsInfoPO) object;
				String playingCode =String.valueOf(asiaOuOddsInfoPO.getBsId());
				String asianCorpId = asiaOuOddsInfoPO.getCorpId();
				if(oddsMap.get(playingCode)!=null){
					if(oddsMap.get(playingCode).get(asianCorpId)!=null){
						String init_key = (asiaOuOddsInfoPO.getOddsType()==1?ASIAN_KEY:BIGSMALL_KEY)+"_init";
						((Map<String, String>) oddsMap.get(playingCode).get(asianCorpId)).put(init_key, 
								asiaOuOddsInfoPO.getHomeWinOdds()+","+
										asiaOuOddsInfoPO.getHandicapOrScore()+","+
										asiaOuOddsInfoPO.getGuestWinOdds());
					}
				}
			}
		}
		return oddsMap;
	}
	@Override
	public Map<String, Object> findFbMatchOpOdds(Long matchId, String corpId) {
		// TODO Auto-generated method stub
		List<Object[]> opOdds = jCZQAnalyseDao.queryFbMatchEuroOddsByMatchId(matchId,fbOpCorpId.get(corpId));
		Date matchTime = jCZQAnalyseDao.queryfbMatchTimeById(matchId);
		if(opOdds!=null&&!opOdds.isEmpty()&&matchTime!=null){
			FbMatchOpOddsInfoPO opOddsInfoPO = (FbMatchOpOddsInfoPO) opOdds.get(0)[0];
			Map<String, Object> oddsMap = new HashMap<String, Object>();
			oddsMap.put("matchTime", matchTime);
			oddsMap.put("opOdds", opOddsInfoPO);
			return oddsMap;
		}
		return null;
	}
	@Override
	public Map<String, String> findFbMatchAsianOrOuOdds(Long matchId,
			String corpId, String oddsType) {
		//获取比赛的开赛时间和比赛状态
		Date objects = jCZQAnalyseDao.queryfbMatchTimeById(matchId);
		if(objects!=null){
			List<FbMatchAsiaOuOddsInfoPO> asiaOuOddsInfoPOs = jCZQAnalyseDao.queryFbMatchAsianOrOuOddsByMatchIdAndCorpId(matchId, corpId,oddsType);
			Map<String, String> dataMap = createOdds(asiaOuOddsInfoPOs,objects);
			return dataMap;
		}
		return null;
	}
	private <T> Map<String, String> createOdds(
			List<T> asiaOuOddsInfoPOs,Date matchTime) {
		String oddsData = "";
		String timstamp = "";
		Map<String, String> dataMap = new HashMap<String, String>();
		int i=0;
		int length = asiaOuOddsInfoPOs.size();
		for(T t:asiaOuOddsInfoPOs){
			i++;
			if(t instanceof FbMatchAsiaOuOddsInfoPO){
				FbMatchAsiaOuOddsInfoPO asiaOuOddsInfoPO = (FbMatchAsiaOuOddsInfoPO)t;
				oddsData += asiaOuOddsInfoPO.getHomeWinOdds()+","+asiaOuOddsInfoPO.getHandicap()+","+asiaOuOddsInfoPO.getGuestWinOdds()+"!";
				
				timstamp += calculateTimeToMatchTime(asiaOuOddsInfoPO.getTimestamp(),matchTime,i,length)+",";
				
			}else if(t instanceof BasketMatchOpOddsInfoPO){
				BasketMatchOpOddsInfoPO opOddsInfoPO = (BasketMatchOpOddsInfoPO)t;
				oddsData += opOddsInfoPO.getHomeWinOdds()+","+opOddsInfoPO.getGuestWinOdds()+"!";
				timstamp += calculateTimeToMatchTime(opOddsInfoPO.getTimestamp(), matchTime,i,length)+",";
			}else if(t instanceof BasketMatchAsiaOuOddsInfoPO){
				BasketMatchAsiaOuOddsInfoPO asiaOuOddsInfoPO = (BasketMatchAsiaOuOddsInfoPO)t;
				oddsData += asiaOuOddsInfoPO.getHomeWinOdds()+","+asiaOuOddsInfoPO.getHandicapOrScore()+","+asiaOuOddsInfoPO.getGuestWinOdds()+"!";
				timstamp += calculateTimeToMatchTime(asiaOuOddsInfoPO.getTimestamp(), matchTime,i,length)+",";
			}
		}
		dataMap.put("oddsData", StringUtils.removeEnd(oddsData,"!"));
		dataMap.put("timestamp", StringUtils.removeEnd(timstamp,","));
		return dataMap;
	}
	@Override
	public String calculateTimeToMatchTime(Date timestamp, Date matchTime, int count, int length) {
		long timeChange = timestamp.getTime();
		long matchTimeLong = matchTime.getTime();
		String time = "";
		if(count==length){
			time = "初盘";
		}else{
			if(timeChange>=matchTimeLong){
				time = "赛事已结束";
			}else{
				long diffs = matchTimeLong - timeChange;
				long day = diffs/(1000*60*60*24);
				long hour = diffs/(1000*60*60)-day*24;
				long minute = diffs/(60*1000)-day*24*60-hour*60;
				long second = diffs/1000-day*24*60*60-hour*60*60-minute*60;
				if(day!=0){
					time = "赛前"+day+"天"+(hour!=0?(hour+"小时"):"");
				}else if(hour!=0){
					time =  "赛前"+hour+"小时"+(minute!=0?(minute+"分"):"");
				}else{
					time = "赛前"+(minute!=0?(minute+"分"):"")+second+"秒";
				}
			}
		}	
		
		return time;
	}
	@Override
	public Map<String, String> findBbEuroOddsByMatchIdAndCorpId(Long matchId,
			String corpId) {
		Date matchTime = jCLQAnalyseDao.queryBbMatchTimeById(matchId);
		if(matchTime!=null){
			List<BasketMatchOpOddsInfoPO> opOddsInfoPOs = jCLQAnalyseDao.queryBbOpAllOddsByBsIdAndCorpId(corpId, matchId);
			return createOdds(opOddsInfoPOs,matchTime);
		}
		return null;
	}
	@Override
	public Map<String, String> findBbAsianOrOuOddsByMatchIdAndCorpId(
			Long matchId, String corpId, String oddsType) {
		Date matchTime = jCLQAnalyseDao.queryBbMatchTimeById(matchId);
		if(matchTime!=null){
			List<BasketMatchAsiaOuOddsInfoPO> asiaOuOddsInfoPOs = jCLQAnalyseDao.queryBbAsianOrOuAllOdds(matchId,corpId,oddsType);
			
			return createOdds(asiaOuOddsInfoPOs,matchTime);
		}
		return null;
	}
	@Override
	public Set<String> findAllFbLeagueShortNameInDays() {
		List<Date> fromTo = ServiceUtils.getDateOfToDay();
		List<String> leagueShortNameList = jCZQAnalyseDao.queryAllLeagueShortNameInDays(fromTo.get(0),fromTo.get(1));
		Set<String> leagueShort = new HashSet<String>();
		for(String leagueName:leagueShortNameList){
			leagueShort.add(leagueName);
		}
		return leagueShort;
	}
	@Override
	public Set<String> findAllBbLeagueShortNameInDays() {
		List<Date> fromTo = ServiceUtils.getDateOfToDay();
		List<String> leagueShortNameList = jCLQAnalyseDao.queryAllLeagueShortNameInDays(fromTo.get(0),fromTo.get(1));
		Set<String> leagueShort = new HashSet<String>();
		for(String leagueName:leagueShortNameList){
			leagueShort.add(leagueName);
		}
		return leagueShort;
	}
}
