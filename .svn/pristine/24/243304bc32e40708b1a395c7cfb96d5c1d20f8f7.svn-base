package com.unison.lottery.weibo.data.service.store.persist.service.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.script.Bindings;
import javax.script.ScriptContext;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import sun.org.mozilla.javascript.internal.NativeArray;

import com.unison.lottery.weibo.data.exception.DataException;
import com.unison.lottery.weibo.data.exception.ParseFormatException;
import com.unison.lottery.weibo.data.service.store.persist.entity.FBCupScorePO;
import com.unison.lottery.weibo.data.service.store.persist.entity.FBLeaguePO;
import com.unison.lottery.weibo.data.service.store.persist.entity.FBLeagueScorePO;
import com.unison.lottery.weibo.data.service.store.persist.entity.FBSubCupPO;
import com.unison.lottery.weibo.data.service.store.persist.entity.FBSubLeaguePO;
import com.unison.lottery.weibo.data.service.store.persist.entity.FBTeamPO;
import com.unison.lottery.weibo.data.service.store.persist.entity.QTMatchPO;
import com.unison.lottery.weibo.data.service.store.persist.service.DataCrawlStoreService;
import com.unison.lottery.weibo.data.service.store.persist.service.DataStoreService;
import com.unison.lottery.weibo.data.service.store.persist.service.FBTeamService;
import com.unison.lottery.weibo.dataservice.commons.constants.Constants;
import com.unison.lottery.weibo.dataservice.commons.model.MatchHistory;
import com.unison.lottery.weibo.dataservice.parse.JsUtils;
import com.unison.lottery.weibo.dataservice.scraper.MatchInfoScraper;
import com.unison.lottery.weibo.lang.Constant;
import com.unison.lottery.weibo.utils.NumberUtils;

/**
 * 球探网赛事数据抓取存储服务
 * 
 * @author Wang Lei
 */
@SuppressWarnings("restriction")
@Service
public class DataCrawlStoreServiceImpl implements DataCrawlStoreService {
	
	private Logger logger = LoggerFactory.getLogger(getClass());
	@Autowired
	private DataStoreService dataStoreService;
	@Autowired
	private MatchInfoScraper matchInfoScraper;
	
	
	/**
	 * 抓取并存储310win对赛战绩分析页面数据
	 * @param qtMatchIds
	 */
	@Override
	public void crawlAndStoreQTMatchRecord(Set<Integer> qtMatchIds){
		logger.debug("execute QTMatchRecord data crawl store task....");
		logger.debug("need crawl data count = {}", qtMatchIds==null? 0 : qtMatchIds.size());
		long startTime = System.currentTimeMillis();
		Set<Long> allQTMatchSet = new HashSet<Long>();
		for(Integer qtMatchId : qtMatchIds){
			try {
				Map<Long, QTMatchPO> onceQTMatchMap = new HashMap<Long, QTMatchPO>();
				logger.debug("start crawl qtMatchId={}.", qtMatchId);
				MatchHistory matchHistory = matchInfoScraper.scrapeMatchInfo(qtMatchId+"");
				if(matchHistory==null){
					logger.debug("crawl QTMatchRecord error! crawl faild! QTMatchId={}.", qtMatchId);
					continue;
				}
				convertAgainstHistoryToQTMatchPO(onceQTMatchMap, allQTMatchSet, matchHistory.getAgainstHistory());
				convertHomeGuestNearScoreToQTMatchPO(onceQTMatchMap, allQTMatchSet, matchHistory.getHostNearScore());
				convertHomeGuestNearScoreToQTMatchPO(onceQTMatchMap, allQTMatchSet, matchHistory.getGuestNearScore());
				logger.debug("QTMatchRecord data crawl count={}, need store count={}.", 
						(matchHistory.getAgainstHistory() == null ? 0 : matchHistory.getAgainstHistory().length)+
						(matchHistory.getHostNearScore() == null ? 0 : matchHistory.getHostNearScore().length)+
						(matchHistory.getGuestNearScore() == null ? 0 : matchHistory.getGuestNearScore().length),
						onceQTMatchMap.values().size());
				dataStoreService.storeQTMatchs(onceQTMatchMap);
			} catch (Exception e) {
				logger.error("{}", qtMatchId, e);
			}
		}
		logger.debug("QTMatchRecord data crawl store task end! Time = {} millisecond!" , System.currentTimeMillis() - startTime);
	}
	
	/**
	 * 使用对赛往绩数据生成PO
	 * @param oneQTMatchMap
	 * @param matchHistory
	 */
	private void convertAgainstHistoryToQTMatchPO(Map<Long, QTMatchPO> oneQTMatchMap,Set<Long> allQTMatchSet, Object[][]  againstHistory){
		SimpleDateFormat shortSdf=new SimpleDateFormat("yyyy-MM-dd");
		if(againstHistory ==null){
			return;
		}
		for(Object[] match : againstHistory){
			QTMatchPO qtmPO = new QTMatchPO();
			try {
				long qtMatchId = Double.valueOf(match[15].toString()).longValue();
				if(allQTMatchSet.contains(qtMatchId)){
					continue;
				}
				qtmPO.setHalfScore(match[10].toString());
				setValue(qtmPO, match);
				qtmPO.setId(qtMatchId);
				qtmPO.setMatchTime(shortSdf.parse(match[0].toString()));
			} catch (Exception e) {
				logger.error("convert convertAgainstHistoryToQTMatchPO error! ", e);
				return;
			}
			allQTMatchSet.add(qtmPO.getId());
			oneQTMatchMap.put(qtmPO.getId(), qtmPO);
		}
	}
	
	/**
	 * 使用主客队近期战绩生成赛事PO
	 * @param oneQTMatchMap
	 * @param nearScore
	 */
	private void convertHomeGuestNearScoreToQTMatchPO(Map<Long, QTMatchPO> oneQTMatchMap,Set<Long> allQTMatchSet, Object[][]  nearScore){
		SimpleDateFormat shortSdf=new SimpleDateFormat("yyyy-MM-dd");
		if(nearScore ==null){
			return;
		}
		for(Object[] match : nearScore){
			QTMatchPO qtmPO = new QTMatchPO();
			try {
				long qtMatchId = Double.valueOf(match[15].toString()).longValue();
				if(allQTMatchSet.contains(qtMatchId)){
					continue;
				}
				qtmPO.setHalfScore(match[10].toString().replace("-", ":"));
				setValue(qtmPO, match);
				qtmPO.setId(qtMatchId);
				qtmPO.setMatchTime(shortSdf.parse("20"+match[0].toString()));
			} catch (Exception e) {
				logger.error("convert convertHomeGuestNearScoreToQTMatchPO error! ", e);
				return;
			}
			allQTMatchSet.add(qtmPO.getId());
			oneQTMatchMap.put(qtmPO.getId(), qtmPO);
		}
	}
	
	private void setValue(QTMatchPO qtmPO,Object[] match) throws Exception{
		qtmPO.setLeagueId(Double.valueOf(match[1].toString()).longValue());
		qtmPO.setLeagueShortName(match[2].toString());
		qtmPO.setColor(match[3].toString());
//		qtmPO.setHomeTeamId(Double.valueOf(match[4].toString()).longValue());
		qtmPO.setHomeTeamName(replaceTeamName(match[5].toString()));
//		qtmPO.setGuestTeamId(Double.valueOf(match[6].toString()).longValue());
		qtmPO.setGuestTeamName(replaceTeamName(match[7].toString()));
		qtmPO.setScore(Double.valueOf(match[8].toString()).intValue() + ":" + Double.valueOf(match[9].toString()).intValue());
		//qtmPO.setAlreadyCrawl(1);
		String[] score = qtmPO.getScore().split(":");
		qtmPO.setHomeTeamScore(Integer.parseInt(score[0]));
		qtmPO.setGuestTeamScore(Integer.parseInt(score[1]));
		String[] halfScore = qtmPO.getHalfScore().split(":");
		qtmPO.setHomeHalfScore(Integer.parseInt(halfScore[0]));
		qtmPO.setGuestHalfScore(Integer.parseInt(halfScore[1]));
		qtmPO.setMatchStatus(Constant.QTMatchStatus.END);
	}
	
	private String replaceTeamName(String teamName){
		teamName = teamName.replaceAll("<span class=hp>.*?</span>", "");
		teamName = teamName.replace("(中)", "");
		String guestName = teamName.replaceAll("<.*?>.*?</.*?>", "").trim();
		return StringUtils.isBlank(guestName) ? teamName.replaceAll("<.*?>", "").trim() : guestName;
	}

	
	@Override
	public void crawlAndStoreQTFBDatabase() {
		// 1. 遍历 联赛表 md_fb_league
		List<FBLeaguePO> allLeagues = dataStoreService.listAllLeagues();
		logger.info("开始遍历联赛杯赛(共{}条)，抓取赛事数据库...", allLeagues.size());
		
		// 2. 获取“赛季信息”，对没有“当前赛季”的联赛杯赛，就不处理。
		for (FBLeaguePO league : allLeagues) {
			String curSeason = league.getCurrMatchSeason();
			if (StringUtils.isBlank(curSeason)){
				logger.info("League {}({}) 的当前赛季串为空，跳过。", league.getChineseName(), league.getLeagueId());
				continue;
			}
			// 3. 获取比赛信息；联赛、杯赛积分信息
			String matchJsUrl = composeMatchJsUrl(league);
			scrapeMatchData(matchJsUrl, league, curSeason);
		}
		logger.info("抓取球探网赛程数据库完毕.");
	}

	private String composeMatchJsUrl(FBLeaguePO leaguePO) {
		// 杯赛：c23.js, 联赛：s23.js
		String jsName = (isCup(leaguePO) ? "c" : "s") + leaguePO.getLeagueId();
		Date now = new Date();
		return String.format("http://info.310win.com/jsData/matchResult/%s/%s.js?version=%tY%tm%td%tH", 
				leaguePO.getCurrMatchSeason(), jsName, now, now, now, now);
	}
	
	private void scrapeMatchData(String matchJsUrl, FBLeaguePO league, String season) {
		// 读取 js
		String js = readMatchDataJs(matchJsUrl);
		// 运算 js
		ScriptEngineManager mgr = new ScriptEngineManager();
		ScriptEngine engine = mgr.getEngineByName("JavaScript");
		try {
			// 遍历 js，抽取需要的数据
			String hackJs = "var jh = [];";
			engine.eval(hackJs);
			engine.eval(js);
			Bindings bindings = engine.getBindings(ScriptContext.ENGINE_SCOPE);
			
			// 抽取 league 图片
			extractLeagueLogo(league, bindings);
			
			// 抽取 team 图片
			Object[][] teams = extractTeamLogo(league, bindings);
			
			// 抽取 杯赛、联赛积分
			if (isCup(league)){
				extractCupScores(league, bindings, teams, season);
			}else{
				extractLeagueScores(league, bindings, teams, season);
			}
			// 抽取比赛时间、TeamId 等比赛信息
			extractMatches(league, bindings, teams);
			
		} catch (ScriptException e) {
		     logger.error("不能执行JS！\n{}", js, e);
		}
	}

	private void extractCupScores(FBLeaguePO league, Bindings bindings,
			Object[][] teams, String season) {
		// 解析"子杯赛"类型
		Object arrCupKind = bindings.get("arrCupKind");
		Object[][] cupKinds = JsUtils.nativeArrayTo2DimArray(arrCupKind);
		List<FBSubCupPO> subCupPOs = composeSubCups(cupKinds, league, season);
		dataStoreService.storeFBSubCups(subCupPOs);
		
		// 解析"子杯赛"排名和组名
		NativeArray matchArray = (NativeArray) bindings.get("jh");
		Object[] ids = matchArray.getIds();
		for (Object id : ids) {
			// "Sxxx" is score arrays
			if ( id.toString().startsWith("S")){
				Object[][] ranks = JsUtils.nativeArrayTo2DimArray(matchArray.get(id));
				for (Object[] rank : ranks) {
					// 分析出组名
					String scoreName = id.toString();	// e.g. "S3814A"
					String groupName = extractGroupName(scoreName);
					if (StringUtils.isBlank(groupName)){
						continue;
					}
					FBCupScorePO cupScorePO = composeCupScore(scoreName, groupName, rank, teams);
					dataStoreService.storeFBCupScore(cupScorePO);
				}
			}
		}
	}

	// 格式：[总排名，队伍id，总，胜，平，负，得，失，净，积分]
	private FBCupScorePO composeCupScore(String scoreName, String groupName, 
			Object[] rank, Object[][] teams) {

		FBCupScorePO cupScorePO = new FBCupScorePO();
		// cupScorePO.setId(id); id 是自增长的
		cupScorePO.setRank(NumberUtils.doubleObjectToInt(rank[0]));
		cupScorePO.setTeamId(NumberUtils.doubleObjectToLong(rank[1]));
		cupScorePO.setTotalMatches(NumberUtils.doubleObjectToInt(rank[2]));
		cupScorePO.setWinMatches(NumberUtils.doubleObjectToInt(rank[3]));
		cupScorePO.setDrawMatches(NumberUtils.doubleObjectToInt(rank[4]));
		cupScorePO.setLoseMatches(NumberUtils.doubleObjectToInt(rank[5]));
		cupScorePO.setGoal(NumberUtils.doubleObjectToInt(rank[6]));
		cupScorePO.setMiss(NumberUtils.doubleObjectToInt(rank[7]));
		cupScorePO.setNet(NumberUtils.doubleObjectToInt(rank[8]));
		cupScorePO.setScore(NumberUtils.doubleObjectToInt(rank[9]));
		
		// 额外字段
		long subCupId = extractSubCupId(scoreName);
		String teamName = searchTeamName(cupScorePO.getTeamId(), teams);
		cupScorePO.setSubCupId(subCupId);
		cupScorePO.setGroupName(groupName);
		cupScorePO.setTeamName(teamName);
		cupScorePO.setCreateTime(new Date());

		return cupScorePO;
	}

	// e.g. "S3814A"
	private long extractSubCupId(String scoreName) {
		Matcher matcher = scoreNamePattern.matcher(scoreName);
		if (matcher.find()){
			return Long.parseLong(matcher.group(2));
		}else{
			String msg = String.format("不能解析出杯赛子类型id: %s", scoreName);
			throw new ParseFormatException(msg);
		}
	}

	@Autowired
	private FBTeamService fbTeamService;
	
	// 格式：[[558,'都灵','拖連奴','Torino','拖連奴','images/201313093257.jpg']...]
	private String searchTeamName(long teamId, Object[][] teams) {
		for (Object[] team : teams) {
			Long id = ((Double)team[0]).longValue();
			if (id.compareTo(teamId)==0){
				return (String)team[1];
			}
		}
		// fall back to database search
		FBTeamPO team = fbTeamService.findFbTeam(teamId);
		if (team != null) {
			return team.getChineseName();
		}
		throw new DataException(String.format("TeamId(%d)在Teams数组中没有出现，数据库中也没有！", teamId));
	}

	private Pattern scoreNamePattern = Pattern.compile("([a-zA-Z]+)(\\d+)([a-zA-Z]+)");
	
	private String extractGroupName(String scoreName) {
		Matcher matcher = scoreNamePattern.matcher(scoreName);
		if (matcher.find()){
			return matcher.group(3);
		}
		logger.error("不能抽取杯赛组名，杯赛成绩js数组名格式错误：{}", scoreName);
		return "";
	}

	// 格式：[[ 0子杯赛ID，1是否分组，2子杯赛简体名，3子杯赛繁体名，4子杯赛英文名，5分组数，6是否当前子杯赛，7出线球队数(可选) ]...]
	private List<FBSubCupPO> composeSubCups(Object[][] cupKinds, FBLeaguePO leaguePO, String season) {
		List<FBSubCupPO> subCupList = new LinkedList<>();
		for (Object[] subCup : cupKinds) {
			FBSubCupPO subCupPO = new FBSubCupPO();
			// add properties
			subCupPO.setId(NumberUtils.doubleObjectToLong(subCup[0]));
			subCupPO.setGroupingMatch(NumberUtils.doubleObjectToBoolean(subCup[1]));
			subCupPO.setChineseName((String) subCup[2]);
			subCupPO.setTraditionalName((String) subCup[3]);
			subCupPO.setEnglishName((String) subCup[4]);
			subCupPO.setGroupCount(NumberUtils.doubleObjectToInt(subCup[5]));
			subCupPO.setCurrent(NumberUtils.doubleObjectToBoolean(subCup[6]));
			if (subCup.length >=8){
				subCupPO.setWinnerCount(NumberUtils.doubleObjectToInt(subCup[7]));
			}
			subCupPO.setCreateTime(new Date());
			subCupPO.setLeagueId(leaguePO.getLeagueId());
			subCupPO.setSeason(season);
			
			subCupList.add(subCupPO);
		}
		return subCupList;
	}

	
	// 抽取联赛积分
	private void extractLeagueScores(FBLeaguePO league, Bindings bindings,
			Object[][] teams, String season) {

		// 抽取子联赛类型
		FBSubLeaguePO subLeague = composeSubLeague(league, bindings, season);
		dataStoreService.storeSubLeague(subLeague);
		
		// 抽取联赛总积分榜
		List<FBLeagueScorePO> leagueScores = composeTotalLeagueScores(subLeague, bindings, teams);
		for (FBLeagueScorePO leagueScorePO : leagueScores){
			dataStoreService.storeLeagueScore(leagueScorePO);
		}
	}

	// 联赛总积分榜
	// JS：var totalScore = [
	//  [0,1,166,2,24,20,3,1,59,19,40,'83.3','12.5','4.2',2.46,0.79,63,0,'',0,0,1,0,1,0,''],
	//  [0,2,174,5,23,16,6,1,48,11,37,'69.6','26.1','4.3',2.09,0.48,54,0,'',2,0,0,0,1,0,'']
	// 格式：[未知，排名，队伍id，红牌数，赛，胜，平，负，得，失，净，胜%，平%，负%，均得，均失，积分，未知，空串，最近六轮（6项），空串]
	private List<FBLeagueScorePO> composeTotalLeagueScores(FBSubLeaguePO subLeague,
			Bindings bindings, Object[][] teams) {
		
		List<FBLeagueScorePO> leagueScoreList = new LinkedList<>();
		
		Object[][] totalScores = JsUtils.nativeArrayTo2DimArray(bindings.get("totalScore"));
		
		for (Object[] scoreRecord : totalScores) {
			FBLeagueScorePO leagueScorePO = new FBLeagueScorePO();
			leagueScorePO.setRank(NumberUtils.doubleObjectToInt(scoreRecord[1]));
			leagueScorePO.setTeamId(NumberUtils.doubleObjectToLong(scoreRecord[2]));
			leagueScorePO.setTotalMatches(NumberUtils.doubleObjectToInt(scoreRecord[4]));
			leagueScorePO.setWinMatches(NumberUtils.doubleObjectToInt(scoreRecord[5]));
			leagueScorePO.setDrawMatches(NumberUtils.doubleObjectToInt(scoreRecord[6]));
			leagueScorePO.setLoseMatches(NumberUtils.doubleObjectToInt(scoreRecord[7]));
			leagueScorePO.setGoal(NumberUtils.doubleObjectToInt(scoreRecord[8]));
			leagueScorePO.setMiss(NumberUtils.doubleObjectToInt(scoreRecord[9]));
			leagueScorePO.setNet(NumberUtils.doubleObjectToInt(scoreRecord[10]));
			leagueScorePO.setWinPercent(NumberUtils.stringToDecimal(scoreRecord[11]));
			leagueScorePO.setDrawPercent(NumberUtils.stringToDecimal(scoreRecord[12]));
			leagueScorePO.setLosePercent(NumberUtils.stringToDecimal(scoreRecord[13]));
			leagueScorePO.setAverageGoal(NumberUtils.doubleObjectToDecimal(scoreRecord[14]));
			leagueScorePO.setAverageLose(NumberUtils.doubleObjectToDecimal(scoreRecord[15]));
			leagueScorePO.setScore(NumberUtils.doubleObjectToInt(scoreRecord[16]));

			// 其他字段
			leagueScorePO.setCreateTime(new Date());
			leagueScorePO.setSubLeagueId(subLeague.getId());
			leagueScorePO.setScoreType(0);	// 总积分榜
			leagueScorePO.setTeamName(searchTeamName(leagueScorePO.getTeamId(), teams));
			
			leagueScoreList.add(leagueScorePO);
		}
		return leagueScoreList;
	}

	// 格式：var arrLeague = [34,'意大利甲组联赛','意大利甲組聯賽','Italian Serie A','2013-2014','#0088FF','league_match/images/20140107211253.jpg',38,25,'意甲','意甲','ITA D1','意大利足球甲级联赛...'];
	private FBSubLeaguePO composeSubLeague(FBLeaguePO league, Bindings bindings, String season) {
		
		Object[] arrLeague = JsUtils.nativeArrayTo1DimArray(bindings.get("arrLeague"));
		
		FBSubLeaguePO subLeaguePO = new FBSubLeaguePO();
		subLeaguePO.setLeagueId(league.getLeagueId());
		subLeaguePO.setTotalRound(NumberUtils.doubleObjectToInt(arrLeague[7]));
		subLeaguePO.setCurrentRound(NumberUtils.doubleObjectToInt(arrLeague[8]));
		
		// 其他字段
		subLeaguePO.setSeason(season);
		subLeaguePO.setCreateTime(new Date());
		
		return subLeaguePO;
	}

	/**
	 * 联赛数组格式：var arrLeague = [联赛id，联赛中文名，联赛繁体名，联赛英文名，赛季，背景色，logo，(总轮数),(当前轮数), 联赛简称，联赛繁体简称，联赛英文简称，联赛介绍];
	 * <br/>
	 * @param leaguePO
	 * @param bindings
	 */
	private void extractLeagueLogo(FBLeaguePO leaguePO, Bindings bindings) {
		String variableName = isCup(leaguePO) ? "arrCup" : "arrLeague";
		Object[] leagueCupArray = JsUtils.nativeArrayTo1DimArray(bindings.get(variableName));
		Long id = ((Double) leagueCupArray[0]).longValue();
		if (id.compareTo(leaguePO.getLeagueId()) !=0 ){
			logger.error("联赛id不一致！js中是'{}', 外部是'{}'", id, leaguePO.getLeagueId());
			return;
		}
		// 杯赛: 8, 联赛: 6
		String logo = (String) leagueCupArray[isCup(leaguePO)?8:6];
		String logoUrl = Constants.QT_LEAGUE_LOGO_URL_PREFIX + logo;
		leaguePO.setLogoUrl(logoUrl);
		dataStoreService.storeLeague(leaguePO);
	}

	private boolean isCup(FBLeaguePO leaguePO) {
		return leaguePO.getType()==2;
	}

	private Object[][] extractTeamLogo(FBLeaguePO leaguePO, Bindings bindings) {
		Object[][] teamArrays = JsUtils.nativeArrayTo2DimArray(bindings.get("arrTeam"));
		for (Object[] team : teamArrays) {
			Long teamId = ((Double) team[0]).longValue();
			if (team.length>=6){
				String logo = (String) team[5];
				if (StringUtils.isNotBlank(logo)){
					String teamLogo = Constants.QT_TEAM_LOGO_URL_PREFIX + logo;
					dataStoreService.updateTeamLogo(teamId, teamLogo);
				}
			}
		}
		return teamArrays;
	}

	private void extractMatches(FBLeaguePO league, Bindings bindings, Object[][] teams) {
		NativeArray matchArray = (NativeArray) bindings.get("jh");
		Object[] ids = matchArray.getIds();
		for (Object id : ids) {
			// not "Sxxx" then is match
			if (! id.toString().startsWith("S")){
				Object subLeagueNative = matchArray.get(id);
				Object[][] subLeagues = JsUtils.nativeArrayTo2DimArray(subLeagueNative);
				for (Object[] subLeague : subLeagues) {
					saveSubLeague(subLeague, league, teams);
				}
			}
		}
	}

	
	// format: [比赛id，联赛id，比赛状态，比赛开始时间，主队id，客队id，全场比分(需要转成:)，半场比分, 让球全场，让球半场，大小全场，大小半场，......]
	private void saveSubLeague(Object[] subLeague, FBLeaguePO league, Object[][] teams) {
		try {
			QTMatchPO matchPO = new QTMatchPO();
			matchPO.setId(((Double) subLeague[0]).longValue());
			matchPO.setLeagueId(((Double) subLeague[1]).longValue());
			matchPO.setLeagueShortName(league.getChineseName());
			matchPO.setColor(league.getColour());
			//matchPO.setCountryID((long)league.getCountryID());
			matchPO.setMatchStatus(((Double) subLeague[2]).intValue());
			SimpleDateFormat matchTimeFmtr = new SimpleDateFormat("yyyy-MM-dd HH:mm");
			matchPO.setMatchTime(matchTimeFmtr.parse((String)subLeague[3]));
//			matchPO.setHomeTeamId(((Double) subLeague[4]).longValue());
//			matchPO.setGuestTeamId(((Double) subLeague[5]).longValue());
			fillTeamNames(matchPO, teams);
			// 全场得分，且设置独立的 homeScore, guestScore
			String score = (String) subLeague[6];
			int[] scoreNums = null;
			if(isValidScoreFormat(score)){
				score = score.replace('-', ':');
				scoreNums = parseScoreNums(score);
				matchPO.setScore(score);
				matchPO.setHomeTeamScore(scoreNums[0]);
				matchPO.setGuestTeamScore(scoreNums[1]);
			}
			// 半场得分，且设置独立的 halfHomeScore, halfGuestScore
			String halfScore = (String) subLeague[7];
			if(isValidScoreFormat(halfScore)){
				halfScore = halfScore.replace('-', ':');
				scoreNums = parseScoreNums(halfScore);
				matchPO.setHalfScore(halfScore);
				matchPO.setHomeHalfScore(scoreNums[0]);
				matchPO.setGuestHalfScore(scoreNums[1]);
			}
			dataStoreService.mergeMatchForCrawler(matchPO);
		} catch (Exception e) {
			logger.error("解析比赛数据失败：{}", ToStringBuilder.reflectionToString(subLeague), e);
		}
	}

	private Pattern validScorePattern = Pattern.compile(".*\\d+.*[:-].*\\d+.*");
	private boolean isValidScoreFormat(String score) {
		Matcher matcher = validScorePattern.matcher(score);
		return matcher.matches();
	}

	private void fillTeamNames(QTMatchPO matchPO, Object[][] teams) {
		Object[] homeTeam = null;
		Object[] guestTeam = null;
		for (Object[] team : teams) {
			Long id = ((Double)team[0]).longValue();
//			if (id.compareTo(matchPO.getHomeTeamId())==0){
//				homeTeam = team;
//			}
//			if (id.compareTo(matchPO.getGuestTeamId())==0){
//				guestTeam = team;
//			}
			if (homeTeam != null && guestTeam != null){
				break;
			}
		}
		if (homeTeam == null || guestTeam == null){
			logger.error("无法");
//			String msg = String.format("页面中没有相应的队：homeTeam=%d, guestTeam=%d", 
//					matchPO.getHomeTeamId(), matchPO.getGuestTeamId());
//			throw new RuntimeException(msg);
		}
		matchPO.setHomeTeamName((String)homeTeam[1]);
		matchPO.setGuestTeamName((String)guestTeam[1]);
	}

	private int[] parseScoreNums(String score) {
		int[] nums = new int[2];
		if (StringUtils.isBlank(score)){
			return nums;
		}
		String[] parts = score.split(":");
		if (parts.length != 2){
			logger.error("score 格式错误：{}", score);
			return nums;
		}
		nums[0] = Integer.parseInt(parts[0]);
		nums[1] = Integer.parseInt(parts[1]);
		return nums;
	}

	private String readMatchDataJs(String url) {
		logger.debug("开始读取 Data JS URL: {}", url);
		HttpClient client = new DefaultHttpClient();
		try {
			HttpGet httpGet = new HttpGet(url);
			HttpResponse response = client.execute(httpGet);
			HttpEntity respEntity = response.getEntity();
			String charset = "utf-8";
			String respText = EntityUtils.toString(respEntity, charset);
			return respText;
		} catch (Exception e) {
			logger.error("不能抓取页面地址：{}", url, e);
		} finally{
			client.getConnectionManager().shutdown();
		}
		return null;
	}

}
