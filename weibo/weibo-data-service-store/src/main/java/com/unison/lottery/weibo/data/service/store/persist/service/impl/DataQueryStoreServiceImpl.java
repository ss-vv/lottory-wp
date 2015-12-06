package com.unison.lottery.weibo.data.service.store.persist.service.impl;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.unison.lottery.weibo.data.service.store.persist.service.DataCrawlStoreService;
import com.unison.lottery.weibo.data.service.store.persist.service.DataQueryStoreService;
import com.unison.lottery.weibo.data.service.store.persist.service.DataStoreService;
import com.unison.lottery.weibo.dataservice.parse.DataParseService;
import com.unison.lottery.weibo.dataservice.parse.model.BBBjEuropeOddsData;
import com.unison.lottery.weibo.dataservice.parse.model.BBLeagueContentData;
import com.unison.lottery.weibo.dataservice.parse.model.BBLeagueData;
import com.unison.lottery.weibo.dataservice.parse.model.BBMatchInfoData;
import com.unison.lottery.weibo.dataservice.parse.model.BBOdds;
import com.unison.lottery.weibo.dataservice.parse.model.BBTeamContentData;
import com.unison.lottery.weibo.dataservice.parse.model.BBTeamData;
import com.unison.lottery.weibo.dataservice.parse.model.CurrentDayOddsData;
import com.unison.lottery.weibo.dataservice.parse.model.FBBFData;
import com.unison.lottery.weibo.dataservice.parse.model.FBBFResultContentData;
import com.unison.lottery.weibo.dataservice.parse.model.FBBFResultData;
import com.unison.lottery.weibo.dataservice.parse.model.FBLeagueData;
import com.unison.lottery.weibo.dataservice.parse.model.FBMatchidContentData;
import com.unison.lottery.weibo.dataservice.parse.model.FBMatchidData;
import com.unison.lottery.weibo.dataservice.parse.model.FBTeamData;
import com.unison.lottery.weibo.dataservice.parse.model.MatchAgenda;
import com.xhcms.lottery.commons.persist.service.QtLcMatchService;

/**
 * 查询并存储赛事数据service
 * 
 * @author Wang Lei, Yang Bo
 */
@Service("dataQueryStoreService")
public class DataQueryStoreServiceImpl implements DataQueryStoreService {
	
	private Logger log = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private DataStoreService dataStoreService;
	@Autowired
	private DataParseService dataParseService;
	@Autowired
	private DataCrawlStoreService dataCrawlStoreService;
	
	@Autowired
	private QtLcMatchService lcMatchService;
	
	/**
	 * 查询并存储联赛杯赛数据
	 */
	@Override
	public void queryFBLeagueStore(){
		log.info("execute FBLeague data store task....");
		try {
			log.info("start queryFBLeague !");
			long startTime = System.currentTimeMillis();
			FBLeagueData result= dataParseService.getFBLeague();
			if(result == null || result.getFbLeagueContentDataList() == null || result.getFbLeagueContentDataList().isEmpty()){
				log.info("queryFBLeague end . result = 0 ! Time = {} millisecond! " , System.currentTimeMillis() - startTime);
				return;
			}
			log.info("queryFBLeague end . result = {} ! Time = {} millisecond!", result.getFbLeagueContentDataList().size(), System.currentTimeMillis() - startTime);
			
			dataStoreService.storeFBLeague(result.getFbLeagueContentDataList());
		} catch (Exception e) {
			log.error("queryFBLeague error!",e);
		}
		log.info("execute FBLeague data store task end....");
	}
	
	/**
	 * 查询并存储足球球队数据
	 */
	@Override
	public void queryFBTeamStore(){
		log.info("execute FBTeam data store task....");
		try {
			log.info("start queryFBTeam !");
			long startTime = System.currentTimeMillis();
			FBTeamData result= dataParseService.getFBTeam();
			if(result == null || result.getFbTeamContentDataList() == null || result.getFbTeamContentDataList().isEmpty()){
				log.info("queryFBTeam end . result = 0 ! Time = {} millisecond! " , System.currentTimeMillis() - startTime);
				return;
			}
			log.info("queryFBTeam end . result = {} ! Time = {} millisecond!", result.getFbTeamContentDataList().size(), System.currentTimeMillis() - startTime);
			
			dataStoreService.storeFBTeam(result.getFbTeamContentDataList());
		} catch (Exception e) {
			log.error("queryFBTeam error!",e);
		}
		log.info("execute FBTeam data store task end....");
	}
	
	/**
	 * 1、查询并存储彩票赛事与球探网的关联
	 * 2、查询5分钟内最新插入的球探网抓取赛事id
	 * 3、抓取球探网足球赛事id对应的赛事数据，并入库
	 */
	@Override
	public void qtMatchStore(){
		// 1
		queryQTMatchIdAndStore();
		
		// 2
		Set<Integer> qtMatchIdSet = dataStoreService.findNewQTMatchIds();
		
		// 3
		dataCrawlStoreService.crawlAndStoreQTMatchRecord(qtMatchIdSet);
	}
	
	/**
	 *  查询并存储彩票赛事与球探网的关联
	 */
	@Override
	public void queryQTMatchIdAndStore(){
		log.info("execute QTMatchId data store task....");
		try {
			log.info("start queryQTMatchId !");
			long startTime = System.currentTimeMillis();
			FBMatchidData result = dataParseService.getFBMatchid();
			if(result == null || result.getFbMatchidContentDataList() == null || 
					result.getFbMatchidContentDataList().isEmpty()){
				log.info("queryQTMatchId end . result = 0 ! Time = {} millisecond! " , 
						System.currentTimeMillis() - startTime);
				return;
			}
			log.info("queryQTMatchId end . result = {} ! Time = {} millisecond!", 
					result.getFbMatchidContentDataList().size(), 
					System.currentTimeMillis() - startTime);
			
			List<FBMatchidContentData> matches = result.getFbMatchidContentDataList();
			//保存到md_qt_lc_match
			dataStoreService.storeQTMatchid(matches);
			//保存到md_qt_match
			queryAndStoreMatches(matches);
		} catch (Exception e) {
			log.error("QTMatchId error!",e);
		}
		log.info("execute QTMatchId data store task end....");
	}
	
	private void queryAndStoreMatches(List<FBMatchidContentData> matches) {
		log.info("开始用 matchId 查询赛程赛果。。。");
		for (FBMatchidContentData match : matches) {
			String matchId = match.getQiuTanWangMatchId();
			log.info("查询比赛({})的赛程赛果。。。", matchId);
			FBBFResultData matchList = dataParseService.getFBBFResultByID(matchId);
			if(matchList==null){
				continue;
			}
			for (FBBFResultContentData matchData : matchList.getBfResultContentDataList()){
				
				dataStoreService.storeFBMatchResult(matchData);
			}
		}
	}

	/**
	 * 查询并存储球探网足球比分数据（实时更新：建议30秒）
	 */
	@Override
	public void queryFBBFDataAndStore(){
		log.debug("execute FBBFData data store task....");
		FBBFData result= dataParseService.getFBBFData();
		try {
			long startTime = System.currentTimeMillis();
			if(result == null || result.getMatchAgendaList() == null || result.getMatchAgendaList().isEmpty()){
				log.info("queryFBBFData end . result = 0 ! Time = {} millisecond! " , System.currentTimeMillis() - startTime);
				return;
			}
			log.info("queryFBBFData end . result = {} ! Time = {} millisecond!", result.getMatchAgendaList().size(), System.currentTimeMillis() - startTime);
			
			dataStoreService.storeFBBFData(result);
		} catch (Exception e) {
			log.error("queryFBBFData error!",e);
		}
		
		log.debug("execute FBBFData data store task end....");
		
		List<MatchAgenda> matchList = result.getMatchAgendaList();
		if(null != matchList && matchList.size() > 0) {
			for(MatchAgenda match : matchList) {
				String homeHalfScore = match.getHomeHalfScores();
				String guestHalfScore = match.getAwayHalfScores();
				String homeScore = match.getHomeScores();
				String guestScore = match.getAwayScores();
				String qtMatchId = match.getMatchId();
				String matchStatus = match.getMatchStatus();
				if(("-1".equals(matchStatus) || "-10".equals(matchStatus)) 
						&& StringUtils.isNotBlank(qtMatchId)) {
					lcMatchService.updateFBPresetScore(qtMatchId, homeHalfScore, guestHalfScore, homeScore, guestScore);
				}
			}
		}
		
		log.info("execute FBBFData data store task end....");
	}

	@Override
	public void queryAndStoreFBEuroOdds() {
		log.info("开始获取足球欧赔数据...");
		CurrentDayOddsData fbOdds = dataParseService.getCurrentDayOdds();
		
		log.info("开始保存足球赛事数据, ...");
		dataStoreService.storeFBMatch(fbOdds.getMatchProcessDatas());
		
		log.info("开始保存足球欧赔数据, ...");
		dataStoreService.storeFBEuroOdds(fbOdds.getEuropeOddDatas());
		
		log.info("开始保存足球亚赔数据, ...");
		dataStoreService.storeFBAsiaOdds(fbOdds.getAsiaOddDatas());
		
		log.info("足球欧赔接口数据保存完毕！");
	}
	
	/**
	 * 历史盘口
	 */
	@Override
	public void queryAndStoreFBOddsHistory(){
		SimpleDateFormat shortSdf=new SimpleDateFormat("yyyy-MM-dd");
		Calendar currentDay = Calendar.getInstance();	// 查询开始日期
		// 手动设置开始时间
		
		Calendar endDay = Calendar.getInstance();		// 查询结束日期
		endDay.add(Calendar.YEAR, -4);
		long totalTime = System.currentTimeMillis();
		log.info("开始执行查询、存储足球历史盘口数据任务。 查询范围从{}日至{}日。", shortSdf.format(currentDay.getTime()), shortSdf.format(endDay.getTime()));
		
		while(currentDay.getTime().after(endDay.getTime())){
			try {
				long startTime = System.currentTimeMillis();
				log.info("开始查询{}日足球历史盘口数据。", shortSdf.format(currentDay.getTime()));
				CurrentDayOddsData result= dataParseService.getFBChangeOddsHistory(currentDay.getTime());
				if(result!=null && result.getMatchProcessDatas() != null && result.getMatchProcessDatas().size()>0){
					log.info("查询{}日足球历史盘口数据完成，返回数据 = {}条 ! 耗时 = {} 毫秒!",new Object[]{shortSdf.format(currentDay.getTime()),
							result.getMatchProcessDatas().size(), System.currentTimeMillis() - startTime});
					
					log.info("开始保存足球赛事数据, ...");
					dataStoreService.storeFBMatch(result.getMatchProcessDatas());
					
					log.info("开始保存足球欧赔数据, ...");
					dataStoreService.storeFBEuroOdds(result.getEuropeOddDatas());
					
					log.info("开始保存足球亚赔数据, ...");
					dataStoreService.storeFBAsiaOdds(result.getAsiaOddDatas());
				}else{
					log.info("查询{}日足球历史盘口数据完成，返回数据 = 0条 !", shortSdf.format(currentDay.getTime()));
				}
			} catch (Exception e) {
				log.error("执行查询、存储足球历史盘口数据任务出错!",e);
			}
			currentDay.add(Calendar.DATE, -1);
		}
		
		log.info("查询、存储足球历史盘口数据任务结束！耗时 = {} 秒!", (System.currentTimeMillis() - totalTime)/1000);
	}
	
	/**
	 * 百家欧赔按日期范围入库
	 */
	@Override
	public void queryAndStoreFBBjEuropeOdds(){
		SimpleDateFormat shortSdf=new SimpleDateFormat("yyyy-MM-dd");
		Calendar currentDay = Calendar.getInstance();	// 查询开始日期
		Calendar endDay = Calendar.getInstance();		// 查询结束日期
		endDay.add(Calendar.YEAR, -2);
		long totalTime = System.currentTimeMillis();
		log.info("开始执行查询、存储足球百家欧赔数据任务。 查询范围从{}日至{}日。", shortSdf.format(currentDay.getTime()), shortSdf.format(endDay.getTime()));
		
		while(currentDay.getTime().after(endDay.getTime())){
			try {
				long startTime = System.currentTimeMillis();
				CurrentDayOddsData result= dataParseService.getFBChangeOddsHistory(currentDay.getTime());
				if(result!=null && result.getMatchProcessDatas() != null && result.getMatchProcessDatas().size()>0){
					log.info("查询{}日百家欧赔数据完成，返回数据 = {}条 ! 耗时 = {} 毫秒!",new Object[]{shortSdf.format(currentDay.getTime()),
							result.getMatchProcessDatas().size(), System.currentTimeMillis() - startTime});
					
					log.info("开始保存足球赛事数据, ...");
					dataStoreService.storeFBMatch(result.getMatchProcessDatas());
					
					log.info("开始保存足球欧赔数据, ...");
					dataStoreService.storeFBEuroOdds(result.getEuropeOddDatas());
					
					log.info("开始保存足球亚赔数据, ...");
					dataStoreService.storeFBAsiaOdds(result.getAsiaOddDatas());
				}else{
					log.info("查询{}日百家欧赔数据完成，返回数据 = 0条 !", shortSdf.format(currentDay.getTime()));
				}
			} catch (Exception e) {
				log.error("执行查询、存储百家欧赔数据任务出错!",e);
			}
			currentDay.add(Calendar.DATE, -1);
		}
		
		log.info("查询、存储百家欧赔数据任务结束！耗时 = {} 秒!", (System.currentTimeMillis() - totalTime)/1000);
	}
	
	@Override
	public void importBBMatchToday() {
		log.info("开始获取篮球当天比赛数据...");
		List<BBMatchInfoData> matches = dataParseService.getBBMatchInfoToday();
		dataStoreService.storeBBMatchInfo(matches);
		log.info("获取篮球当天比赛数据结束");
		
		// 抓取 未来10天的赛程
		int tenDays = 10;
		runFutureTimeTask(tenDays, new TimeTask(){
			@Override
			public void runOnDay(Date date) {
				log.info("开始获取未来篮球赛程赛果，时间是 {}", date);
				List<BBMatchInfoData> matches = dataParseService.getBBMatchInfo(date);
				dataStoreService.storeBBMatchInfo(matches);
				log.info("完成未来篮球赛程赛果获取。");
			}
		});
		//更新预兑奖的篮球预设总分
		if(null != matches && matches.size() > 0) {
			for(BBMatchInfoData bbMatch : matches) {
				long matchId = bbMatch.getId();
				int homeScore = bbMatch.getHomeScore();
				int guestScore = bbMatch.getGuestScore();
				int matchStatus = bbMatch.getMatchState();
				if((matchStatus == -1 || matchStatus == -4) && matchId > 0) {
					lcMatchService.updateBBPresetScore(matchId, homeScore, guestScore);
				}
			}
		}
	}

	@Override
	public void importBBMatchHistory() {
		// 抓取 3 年的
		int threeYears = 365*3;
		runTimeTask(threeYears, new TimeTask(){
			@Override
			public void runOnDay(Date date) {
				log.info("开始获取篮球历史赛程赛果，时间是 {}", date);
				List<BBMatchInfoData> matches = dataParseService.getBBMatchInfo(date);
				dataStoreService.storeBBMatchInfo(matches);
				log.info("完成篮球历史赛程赛果获取。");
			}
		});
		log.info("获取篮球历史赛程赛果结束，一共获取了{}天的数据。", threeYears);
	}
	
	@Override
	public void importBBMatchRealtime() {
		log.info("开始获取篮球即时比赛数据...");
		List<BBMatchInfoData> matches = dataParseService.getBBMatchInfoRealtime();
		dataStoreService.updateBBMatchInfo(matches);
		log.info("获取篮球即时比赛数据结束");
	}
	
	@Override
	public void queryBBTeamStore() {
		log.info("execute queryBBTeamStore task....");
		try {
			long startTime = System.currentTimeMillis();
			BBTeamData result = dataParseService.getBBTeam();
			if(result == null || result.getBbTeamContentDataList() == null || result.getBbTeamContentDataList().isEmpty()){
				log.info("getBBTeam end . result = 0 ! Time = {} millisecond! " , System.currentTimeMillis() - startTime);
				return;
			}
			log.info("getBBTeam end . result = {} ! Time = {} millisecond!", result.getBbTeamContentDataList().size(), System.currentTimeMillis() - startTime);
			
			long begin = System.currentTimeMillis();
			for(BBTeamContentData bbTeamData : result.getBbTeamContentDataList()) {
				dataStoreService.storeBBTeam(bbTeamData);
			}
			long end = System.currentTimeMillis();
			log.info("（更新/插入）篮球球队信息:{}条, 耗时(秒):{}", 
					new Object[]{result.getBbTeamContentDataList().size(), (end-begin)/1000});
		} catch (Exception e) {
			log.error("queryBBTeamStore error!",e);
			e.printStackTrace();
		}
		log.info("execute queryBBTeamStore task end....");
	}
	
	@Override
	public void queryBBLeagueStore() {
		log.info("execute queryBBLeagueStore task....");
		try {
			long startTime = System.currentTimeMillis();
			BBLeagueData result = dataParseService.getBBLeague();
			if(result == null || result.getBbLeagueContentDataList() == null || result.getBbLeagueContentDataList().isEmpty()){
				log.info("getBBLeague end . result = 0 ! Time = {} millisecond! " , System.currentTimeMillis() - startTime);
				return;
			}
			log.info("getBBLeague end . result = {} ! Time = {} millisecond!", result.getBbLeagueContentDataList().size(), System.currentTimeMillis() - startTime);
			
			long begin = System.currentTimeMillis();
			for(BBLeagueContentData bbLeagueData : result.getBbLeagueContentDataList()) {
				dataStoreService.storeBBLeague(bbLeagueData);
			}
			long end = System.currentTimeMillis();
			log.info("（更新/插入）篮球联赛信息:{}条, 耗时(秒):{}", 
					new Object[]{result.getBbLeagueContentDataList().size(), (end-begin)/1000});
		} catch (Exception e) {
			log.error("queryBBLeagueStore error!",e);
			e.printStackTrace();
		}
		log.info("execute queryBBLeagueStore task end....");
	}
	
	@Override
	public void importBBOddsHistory(){
		// 抓取 3 年的
		int threeYears = 365*3;
		runTimeTask(threeYears, new TimeTask(){

			@Override
			public void runOnDay(Date date) {
				log.info("开始请求篮球历史赔率数据，日期：{}", date);
				
				BBOdds bbOdds = dataParseService.getBBOddsHistory(date);
				dataStoreService.storeBBOdds(bbOdds);
				
				log.info("完成篮球历史赔率数据导入。");
			}
			
		});
		log.info("完成了3年篮球赔率历史的导入。");
	}
	
	/**
	 * 导入篮球赔率，《篮球赔率接口》中的第一个接口。
	 */
	@Override
	public void importBBOdds(){
		log.info("开始请求篮球赔率数据");
		
		BBOdds bbOdds = dataParseService.getBBOdds();
		dataStoreService.storeBBOdds(bbOdds);
		
		log.info("完成篮球赔率数据导入。");
	}
	
	@Override
	public void importBBOddsRealtime(){
		log.info("开始请求篮球即时赔率数据");
		
		BBOdds bbOdds = dataParseService.getBBOddsRealtime();
		dataStoreService.storeBBOdds(bbOdds);
		
		log.info("完成篮球即时赔率数据导入。");
	}
	
	/**
	 * 导入5分钟内的篮球百家欧赔
	 */
	@Override
	public void importBBOddsBjEuro(){
		log.info("开始请求篮球百家欧赔数据...");
		
		String dateNum = null;
		Date date = null;
		String minNum = "5";
		
		BBBjEuropeOddsData bbBjOddsEuro = 
				dataParseService.getBBBjEuropeOdds(dateNum, date, minNum);
				
		dataStoreService.storeBBOddsBjEuro(bbBjOddsEuro);
		
		log.info("完成篮球百家欧赔数据导入。");
	}
	
	
	/**
	 * 导入3年的篮球百家欧赔历史
	 */
	@Override
	public void importBBOddsBjEuroHistory(){
		int threeYears = 365*3;
		runTimeTask(threeYears, new TimeTask(){
			@Override
			public void runOnDay(Date date) {
				log.info("开始请求篮球百家欧赔数据，日期是：{}", date);
				
				String dateNum = null;
				String minNum = null;
				
				BBBjEuropeOddsData bbBjOddsEuro = 
						dataParseService.getBBBjEuropeOdds(dateNum, date, minNum);
						
				dataStoreService.storeBBOddsBjEuro(bbBjOddsEuro);
				
				log.info("完成一天的篮球百家欧赔导入。");
			}
		});
		log.info("完成了导入3年的篮球百家欧赔历史。");
	}
	
	private void runTimeTask(int days, TimeTask task){
		Date date = new Date();
		int i=0;
		for (; i<days; i++) {
			task.runOnDay(date);
			date.setTime(date.getTime()-(60*60*24*1000L));
		}
	}
	
	private void runFutureTimeTask(int days, TimeTask task){
		Date date = new Date();
		int i=0;
		for (; i<days; i++) {
			task.runOnDay(date);
			date.setTime(date.getTime()+(60*60*24*1000L));
		}
	}
	
}
