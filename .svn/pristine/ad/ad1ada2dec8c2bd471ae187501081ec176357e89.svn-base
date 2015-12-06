package com.unison.lottery.weibo.data.service.store.persist.service.impl;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.unison.lottery.weibo.common.entity.QTLCMatchidPO;
import com.unison.lottery.weibo.common.persist.QTMatchidDao;
import com.unison.lottery.weibo.data.service.store.data.Constants;
import com.unison.lottery.weibo.data.service.store.persist.dao.BBConcedeScoreOddsDao;
import com.unison.lottery.weibo.data.service.store.persist.dao.BBLeagueDao;
import com.unison.lottery.weibo.data.service.store.persist.dao.BBLotteryCorpDao;
import com.unison.lottery.weibo.data.service.store.persist.dao.BBMatchInfoDao;
import com.unison.lottery.weibo.data.service.store.persist.dao.BBOddsBigSmallDao;
import com.unison.lottery.weibo.data.service.store.persist.dao.BBOddsEuroDao;
import com.unison.lottery.weibo.data.service.store.persist.dao.BBTeamDao;
import com.unison.lottery.weibo.data.service.store.persist.dao.FBAsiaOddsDao;
import com.unison.lottery.weibo.data.service.store.persist.dao.FBCupScoreDao;
import com.unison.lottery.weibo.data.service.store.persist.dao.FBEuroOddsDao;
import com.unison.lottery.weibo.data.service.store.persist.dao.FBLeagueDao;
import com.unison.lottery.weibo.data.service.store.persist.dao.FBLeagueScoreDao;
import com.unison.lottery.weibo.data.service.store.persist.dao.FBSubCupDao;
import com.unison.lottery.weibo.data.service.store.persist.dao.FBSubLeagueDao;
import com.unison.lottery.weibo.data.service.store.persist.dao.FBTeamDao;
import com.unison.lottery.weibo.data.service.store.persist.dao.LotteryCorpDao;
import com.unison.lottery.weibo.data.service.store.persist.dao.QTMatchDao;
import com.unison.lottery.weibo.data.service.store.persist.entity.BBConcedeScoreOddsPO;
import com.unison.lottery.weibo.data.service.store.persist.entity.BBLeaguePO;
import com.unison.lottery.weibo.data.service.store.persist.entity.BBLotteryCorpPO;
import com.unison.lottery.weibo.data.service.store.persist.entity.BBMatchInfoPO;
import com.unison.lottery.weibo.data.service.store.persist.entity.BBOddsBigSmallPO;
import com.unison.lottery.weibo.data.service.store.persist.entity.BBOddsEuroPO;
import com.unison.lottery.weibo.data.service.store.persist.entity.BBTeamPO;
import com.unison.lottery.weibo.data.service.store.persist.entity.FBAsiaOddsPO;
import com.unison.lottery.weibo.data.service.store.persist.entity.FBCupScorePO;
import com.unison.lottery.weibo.data.service.store.persist.entity.FBEuroOddsPO;
import com.unison.lottery.weibo.data.service.store.persist.entity.FBLeaguePO;
import com.unison.lottery.weibo.data.service.store.persist.entity.FBLeagueScorePO;
import com.unison.lottery.weibo.data.service.store.persist.entity.FBSubCupPO;
import com.unison.lottery.weibo.data.service.store.persist.entity.FBSubLeaguePO;
import com.unison.lottery.weibo.data.service.store.persist.entity.FBTeamPO;
import com.unison.lottery.weibo.data.service.store.persist.entity.LotteryCorpPO;
import com.unison.lottery.weibo.data.service.store.persist.entity.QTMatchPO;
import com.unison.lottery.weibo.data.service.store.persist.service.DataStoreService;
import com.unison.lottery.weibo.dataservice.parse.model.AsiaOdd;
import com.unison.lottery.weibo.dataservice.parse.model.BBBjEuropeOddsContentData;
import com.unison.lottery.weibo.dataservice.parse.model.BBBjEuropeOddsData;
import com.unison.lottery.weibo.dataservice.parse.model.BBLeagueContentData;
import com.unison.lottery.weibo.dataservice.parse.model.BBMatchEuropeOddDetail;
import com.unison.lottery.weibo.dataservice.parse.model.BBMatchInfoData;
import com.unison.lottery.weibo.dataservice.parse.model.BBOdds;
import com.unison.lottery.weibo.dataservice.parse.model.BBOddsBigData;
import com.unison.lottery.weibo.dataservice.parse.model.BBOddsConcedeData;
import com.unison.lottery.weibo.dataservice.parse.model.BBOddsEuroData;
import com.unison.lottery.weibo.dataservice.parse.model.BBRangFen;
import com.unison.lottery.weibo.dataservice.parse.model.BBTeamContentData;
import com.unison.lottery.weibo.dataservice.parse.model.EuropeOdd;
import com.unison.lottery.weibo.dataservice.parse.model.FBBFData;
import com.unison.lottery.weibo.dataservice.parse.model.FBBFResultContentData;
import com.unison.lottery.weibo.dataservice.parse.model.FBLeagueContentData;
import com.unison.lottery.weibo.dataservice.parse.model.FBMatchidContentData;
import com.unison.lottery.weibo.dataservice.parse.model.FBTeamContentData;
import com.unison.lottery.weibo.dataservice.parse.model.League;
import com.unison.lottery.weibo.dataservice.parse.model.MatchAgenda;
import com.unison.lottery.weibo.dataservice.parse.model.MatchProcess;
import com.unison.lottery.weibo.dataservice.parse.model.Team;
import com.unison.lottery.weibo.dataservice.parse.model.TeamStatus;
import com.unison.lottery.weibo.utils.DateUtil;
import com.unison.lottery.weibo.utils.NumberUtils;
import com.xhcms.lottery.commons.persist.dao.BBMatchDao;
import com.xhcms.lottery.commons.persist.dao.FBMatchDao;
import com.xhcms.lottery.utils.BeanUtilsTools;

/**
 * 存储赛事数据
 * 
 * @author Wang Lei
 */
@Service
public class DataStoreServiceImpl implements DataStoreService {
	
	private Logger log = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private QTMatchDao qTMatchDao;
	
	@Autowired
	private FBTeamDao fBTeamDao;
	
	@Autowired
	private QTMatchidDao qTMatchidDao;

	@Autowired
	private FBLeagueDao fBLeagueDao;
	
	@Autowired
	private FBEuroOddsDao fbEuroOddsDao;
	
	@Autowired
	private FBAsiaOddsDao fbAsiaOddsDao;

	@Autowired
	private LotteryCorpDao lotteryCorpDao; 
	
	@Autowired
	private FBSubCupDao subCupDao;
	
	@Autowired
	private FBSubLeagueDao subLeagueDao;
	
	@Autowired
	private FBCupScoreDao cupScoreDao;

	@Autowired
	private FBLeagueScoreDao leagueScoreDao;

	@Autowired
	private BBMatchInfoDao bbMatchInfoDao;
	
	@Autowired
	private BBTeamDao bbTeamDao;
	
	@Autowired
	private BBLeagueDao bbLeagueDao;
	
	@Autowired
	private BBConcedeScoreOddsDao concedeScoreOddsDao;

	@Autowired
	private BBOddsBigSmallDao bbOddsBigDao;

	@Autowired
	private BBOddsEuroDao bbOddsEuroDao;
	
	@Autowired
	private BBLotteryCorpDao bbLotteryCorpDao;
	
	@Autowired
	private FBMatchDao fbMatchDao;
	
	@Autowired
	private BBMatchDao bbMatchDao;
	
	
	private Set<String> lotteryNameSet = new HashSet<String>();
	{
		lotteryNameSet.add("14场胜负彩");
		lotteryNameSet.add("六场半全场");
		lotteryNameSet.add("四场进球彩");
		lotteryNameSet.add("竞彩足球");
		lotteryNameSet.add("竞彩篮球");
		lotteryNameSet.add("单场让球胜平负");
		lotteryNameSet.add("北京单场胜负过关");
	}
	
	/**
	 * 存储球探网赛事比分数据
	 * @param result
	 */
	@Override
	@Transactional
	public void storeFBBFData(FBBFData result){
		log.debug("begin insert FBBFData....");
		
		int insertCount=0;
		int updateCount=0;
		Map<Long,QTMatchPO> bfMap = makeQtMatchMap(result);
		List<Long>  ids = qTMatchDao.findExistMatchIds(bfMap.keySet());
		Map<Long, QTMatchPO> insertQTMatchMaps = new HashMap<Long, QTMatchPO>();
		Set<String> insertQTMatchNameSets = new HashSet<String>();
		
		for(Long id : bfMap.keySet()){
			if(ids.contains(id)){
				try {
					qTMatchDao.updateQTBFDataToQTMatch(bfMap.get(id));
					updateCount++;
				} catch (Exception e) {
					log.error("update storeFBBFData error! qtMatchId={}", id , e);
				}
			}else{
				insertQTMatchMaps.put(id, bfMap.get(id));
				insertQTMatchNameSets.add(bfMap.get(id).getLeagueShortName());
			}
		}
		insertCount = saveFBBFData(insertQTMatchMaps, insertQTMatchNameSets);
		log.debug("insert qtMatch BFData complete. total count={} ,successInsertCount={} , successUpdateCount={}.",
				bfMap.keySet().size(), insertCount, updateCount);
	}
	
	private int saveFBBFData(Map<Long, QTMatchPO> insertQTMatchMaps, Set<String> insertQTMatchNameSets){
		if(insertQTMatchMaps.size() ==0){
			return 0;
		}
		// 通过赛事id查询赛事的开赛时间
		List<Object[]> matchs= qTMatchidDao.findMatchTime(insertQTMatchMaps.keySet());
		// 通过联赛短名称查询联赛id
		List<Object[]> leagueNames = fBLeagueDao.findLeagueIdsByShortNames(insertQTMatchNameSets);
		
		int insertCount = 0;
		for(QTMatchPO qtm : insertQTMatchMaps.values()){
			try {
				qtm.setMatchTime(getMatchTime(matchs, qtm.getId()));										// 匹配开赛时间
				Long lid = getLeagueName(leagueNames, qtm.getLeagueShortName());
				if(lid == null){
					log.error("matching LeagueShortName Fail! qtMatchId={}, LeagueShortName = {} .", qtm.getId() , qtm.getLeagueShortName());
					continue;
				}
				qtm.setLeagueId(lid);	// 匹配联赛id
				qtm.setCreateTime(new Date());
				qTMatchDao.save(qtm);
				insertCount++;
			} catch (Exception e) {
				log.error("insert storeFBBFData error! qtMatchId={}, LeagueShortName = {} .", qtm.getId() , qtm.getLeagueShortName() , e);
			}
		}
		return insertCount;
	}
	
	private Date getMatchTime(List<Object[]> matchs, Long id){
		try {
			for(Object[] m : matchs){
				if(Long.parseLong(m[0].toString()) == id){
					return m[1] == null ? null : 	(Date) m[1];
				}
			}
		} catch (Exception e) {
			log.error("匹配、转换开赛时间错误！赛事id = {} .", id , e);
			return null;
		}
		return null;
	}
	
	private Long getLeagueName(List<Object[]> leagueNames, String leagueShortName){
		try {
			for(Object[] l : leagueNames){
				if(l[0].toString().equals(leagueShortName)){
					return Long.parseLong(l[1].toString());
				}
			}
		} catch (Exception e) {
			log.error("匹配、转换联赛id错误！联赛短名称 = {} .", leagueShortName , e);
			return null;
		}
		return null;
	}
	
	private Map<Long,QTMatchPO> makeQtMatchMap(FBBFData result){
		Map<Long,QTMatchPO> ids = new HashMap<Long,QTMatchPO>();
		for(MatchAgenda ma : result.getMatchAgendaList()){
			try {
				QTMatchPO qtm = new QTMatchPO();
				qtm.setId(Long.parseLong(ma.getMatchId()));
				qtm.setColor(ma.getColour());
				qtm.setLeagueShortName(ma.getMatchTypeName().getChineseName());
				qtm.setHomeTeamName(ma.getHomeName().getChineseName().replaceAll("<.*>", ""));
				qtm.setGuestTeamName(ma.getAwayName().getChineseName().replaceAll("<.*>", ""));
				qtm.setHalfStartTime(ma.getMatchBeginTime());
				
				qtm.setMatchStatus(Integer.parseInt(ma.getMatchStatus()));
				qtm.setHomeTeamScore(toInteger(ma.getHomeScores()));
				qtm.setGuestTeamScore(toInteger(ma.getAwayScores()));
				qtm.setScore(qtm.getHomeTeamScore() + ":" + qtm.getGuestTeamScore());
				qtm.setHomeHalfScore(toInteger(ma.getHomeHalfScores()));
				qtm.setGuestHalfScore(toInteger(ma.getAwayHalfScores()));
				qtm.setHalfScore(qtm.getHomeHalfScore() + ":" + qtm.getGuestHalfScore());
				qtm.setHomeTeamRed(toInteger(ma.getHomeRedCards()));
				qtm.setGuestTeamRed(toInteger(ma.getAwayRedCards()));
				qtm.setHomeTeamYellow(toInteger(ma.getHomeYellowCards()));
				qtm.setGuestTeamYellow(toInteger(ma.getAwayYellowCards()));
				qtm.setHomeTeamPosition( StringUtils.isBlank(ma.getHomeRanking()) ? null : Integer.parseInt(ma.getHomeRanking().replaceAll("[^-+.\\d]", "")));
				qtm.setGuestTeamPosition(StringUtils.isBlank(ma.getAwayRanking()) ? null :  Integer.parseInt(ma.getAwayRanking().replaceAll("[^-+.\\d]", "")));
//				qtm.setImportance(StringUtils.isBlank(ma.getIsAllOrSimple())?new BigDecimal("2"):new BigDecimal(ma.getIsAllOrSimple()));
				qtm.setFbLotteryScoreType(StringUtils.isBlank(ma.getIsFootballLotteryScore())?-1:Integer.parseInt(ma.getIsFootballLotteryScore()));
				qtm.setTelevison(ma.getTVLive());
				qtm.setHaveBattleArray(ma.getIsHaveBattleArray().equals("1")?1:0);
				qtm.setMatchMessage(ma.getMatchMessage());
//				qtm.setHomeTeamId(Long.parseLong(ma.getHomeTeamId()));
//				qtm.setGuestTeamId(Long.parseLong(ma.getAwayTeamId()));
				//qtm.setCountryID(toLong(ma.getCountryId()));
//				qtm.setCreateTime(new Date());
				
				ids.put(qtm.getId(), qtm);
			} catch (Exception e) {
				log.error("{}",e);
			}
		}
		return ids;
	}
	
	/**
	 * 
	 * @param str
	 * @return 如果str为空，返回0
	 */
	private Long toLong(String str) {
		Long result=0L;
		if(StringUtils.isNotBlank(str)){
			try{
				result=Long.parseLong(str);
			}catch(Exception e){
				e.printStackTrace();
				result=0L;
			}
			
		}
		return result;
	}

	/**
	 * 
	 * @param str
	 * @return 如果str为空，返回0
	 */
	private Integer toInteger(String str) {
		Integer value=0;
		if(StringUtils.isNotBlank(str)){
			try{
				value=Integer.parseInt(str);
			}catch(Exception e){
				e.printStackTrace();
				value=0;
			}
			
		}
		return value;
	}
	
	/**
	 * 存储球探赛事记录
	 * @param qtMatchPOs
	 */
	@Override
	@Transactional
	public void storeQTMatchs(Map<Long, QTMatchPO> onceQTMatchMap){
		log.debug("begin insert qtMatch....");
		int successInsertCount = 0;
		int successUpdateCount = 0;
		Set<Long> ids = onceQTMatchMap.keySet();
		Set<Long> ignoreId = new HashSet<Long>();
		Set<Long> updateId = new HashSet<Long>();
		List<Object[]> existIds = qTMatchDao.findExistMatchs(ids);
		
		for(Object[] existId : existIds){
			Long id = Long.parseLong(existId[0].toString());
			if(existId[1].equals(0)){
				updateId.add(id);
			}else{
				ignoreId.add(id);
			}
		}
		
		for(QTMatchPO qt : onceQTMatchMap.values()){
			try {
				if(ignoreId.contains(qt.getId())){
					continue;
				}else if(updateId.contains(qt.getId())){
					qTMatchDao.updateQTSiteDataToQTMatch(qt);
					successUpdateCount++;
				}else{
					qt.setCreateTime(new Date());
					//qt.setNeutralType(neutralType);
					qTMatchDao.save(qt);
					successInsertCount++;
				}
			} catch (Exception e) {
				log.error("insert qtMatch error! qtMatchId={}", qt.getId(), e);
			}
		}
		
		log.debug("insert qtMatch complete. total count={} ,successInsertCount={} , successUpdateCount={}.",
				onceQTMatchMap.values().size(), successInsertCount, successUpdateCount);
	}
	
	@Override
	@Transactional(readOnly=true)
	public Set<Integer> findNewQTMatchIds(){
		Set<Integer> qtMatchIdSet = new HashSet<Integer>();
		try {
			List<Integer> qtMatchIds = qTMatchidDao.findNewQTMatchIds();
			qtMatchIdSet.addAll(qtMatchIds);
		} catch (Exception e) {
			log.error("findNewQTMatchIds error !", e);
		}
		return qtMatchIdSet;
	}
	
	/**
	 * 保存足球联赛杯赛数据
	 */
	@Override
	@Transactional
	public void storeFBLeague(List<FBLeagueContentData> leagues){
		
		List<Long> ids = fBLeagueDao.ids();
		// 计算应该插入与更新的对象
		List<List<FBLeaguePO>> fbls = computeFBLeagueObject(ids, leagues);
		
		// 插入
		long startTime = System.currentTimeMillis();
		fBLeagueDao.batchInsert(fbls.get(0));
		log.debug("FBLeague insert success ! count = {} ! Time = {} second!", fbls.get(0).size(), (System.currentTimeMillis() - startTime)/1000);
		
		// 更新
		startTime = System.currentTimeMillis();
		fBLeagueDao.batchUpdate(fbls.get(1));
		log.debug("FBLeague update success ! count = {} ! Time = {} second!", fbls.get(1).size(), (System.currentTimeMillis() - startTime)/1000);
	}
	
	private List<List<FBLeaguePO>> computeFBLeagueObject(List<Long> oldIds, List<FBLeagueContentData> leagues){
		List<FBLeaguePO> inserts = new ArrayList<FBLeaguePO>();
		List<FBLeaguePO> upadtes = new ArrayList<FBLeaguePO>();
		List<List<FBLeaguePO>> result = new ArrayList<List<FBLeaguePO>>();
		
		for(FBLeagueContentData lc : leagues){
			if(oldIds.contains(Long.parseLong(lc.getLeagueId()))){
				upadtes.add(copyToFBLeaguePO(lc));
			}else{
				inserts.add(copyToFBLeaguePO(lc));
			}
		}
		
		result.add(inserts);
		result.add(upadtes);
		return result;
	}
	
	private FBLeaguePO copyToFBLeaguePO(FBLeagueContentData league){
		FBLeaguePO lg = new FBLeaguePO();
		try {
			if(StringUtils.isBlank(league.getAreaID())){
				System.out.println(league.toString());
			}else{
				lg.setAreaID(Integer.parseInt(league.getAreaID()));
			}
			lg.setColour(league.getColour());
			lg.setCountry(league.getCountry());
			lg.setCountryID(Integer.parseInt(league.getCountryID()));
			lg.setCurrMatchSeason(league.getCurrMatchSeason());
			if(StringUtils.isNotBlank(league.getCurrRound())){
				lg.setCurrRound(Integer.parseInt(league.getCurrRound()));
			}
			lg.setLeagueId(Long.parseLong(league.getLeagueId()));
			if(StringUtils.isNotBlank(league.getSumRound())){
				lg.setSumRound(Integer.parseInt(league.getSumRound()));
			}
			lg.setType(Integer.parseInt(league.getType()));
			lg.setChineseName(league.getLeagueSimpleName().getChineseName());
			lg.setChineseNameAll(league.getLeagueAllName().getChineseName());
			lg.setTraditionalName(league.getLeagueSimpleName().getTraditional());
			lg.setTraditionalNameAll(league.getLeagueAllName().getTraditional());
			lg.setEnglishName(league.getLeagueSimpleName().getEnglishName());
			lg.setEnglishNameAll(league.getLeagueAllName().getEnglishName());
		} catch (Exception e) {
			log.debug("copyToFBLeaguePO 错误! ", e);
		}
		return lg;
	}
	
	/**
	 * 保存足球球队数据
	 */
	@Override
	@Transactional
	public void storeFBTeam(List<FBTeamContentData> leagues){
		List<Long> ids = fBTeamDao.ids();
		// 计算应该插入与更新的对象
		List<List<FBTeamPO>> tm = computeFBTeamObject(ids, leagues);

		// 插入
		long startTime = System.currentTimeMillis();
		fBTeamDao.batchInsert(tm.get(0));
		log.debug("FBTeam insert success ! count = {} ! Time = {} millisecond!", tm.get(0).size(), System.currentTimeMillis() - startTime);
		
		// 更新
		startTime = System.currentTimeMillis();
		fBTeamDao.batchUpdate(tm.get(1));
		log.debug("FBTeam update success ! count = {} ! Time = {} millisecond!", tm.get(1).size(), System.currentTimeMillis() - startTime);
	}
	
	private List<List<FBTeamPO>> computeFBTeamObject(List<Long> oldIds, List<FBTeamContentData> team){
		List<FBTeamPO> inserts = new ArrayList<FBTeamPO>();
		List<FBTeamPO> upadtes = new ArrayList<FBTeamPO>();
		List<List<FBTeamPO>> result = new ArrayList<List<FBTeamPO>>();
		
		for(FBTeamContentData t : team){
			if(oldIds.contains(Long.parseLong(t.getTeamId()))){
				upadtes.add(copyToFBTeamPO(t));
			}else{
				inserts.add(copyToFBTeamPO(t));
			}
		}
		
		result.add(inserts);
		result.add(upadtes);
		return result;
	}
	
	private FBTeamPO copyToFBTeamPO(FBTeamContentData team){
		FBTeamPO result = new FBTeamPO();
		result.setTeamId(Integer.parseInt(team.getTeamId()));
		result.setLeagueId(Integer.parseInt(team.getLeaId()));
		result.setChineseName(team.getTeamName().getChineseName());
		result.setTraditionalName(team.getTeamName().getTraditional());
		result.setEnglishName(team.getTeamName().getEnglishName());
		result.setFoundDate(team.getFoundDate());
		result.setAddress(team.getAddress());
		result.setArea(team.getArea());
		result.setCapacity(team.getCapacity());
		result.setFlag(team.getFlag());
		result.setGym(team.getGym());
		result.setMaster(team.getMaster());
		result.setUrl(team.getUrl());
		return result;
	}
	
	/**
	 * 保存彩票赛事与球探网的关联数据
	 */
	@Override
	@Transactional
	public void storeQTMatchid(List<FBMatchidContentData> matchidList){
		List<QTLCMatchidPO> saveorupdate = new ArrayList<QTLCMatchidPO>();
		for(FBMatchidContentData t : matchidList){
			if(!lotteryNameSet.contains(t.getLotteryName().trim())){
				continue;
			}
			QTLCMatchidPO qt = new QTLCMatchidPO();
			BeanUtils.copyProperties(t, qt,new String[]{"qiuTanWangMatchId"});
			qt.setQiuTanWangMatchId(Integer.parseInt(t.getQiuTanWangMatchId()));
			List<QTLCMatchidPO> matchIds = makeLcMatchId(qt);
			for(QTLCMatchidPO matchId : matchIds){
				saveorupdate.add(matchId);
			}
		}
		qTMatchidDao.merge(saveorupdate);
	}
	
	private List<QTLCMatchidPO> makeLcMatchId(QTLCMatchidPO qt){
		ArrayList<QTLCMatchidPO> qtlcMatchIdPoList = new ArrayList<>(2);
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		String lotteryName = qt.getLotteryName().trim();
		String lcMatchId = "";
		if("14场胜负彩".equals(lotteryName)){
			lcMatchId = qt.getIssueNum().substring(2) + "24_ZC_14" + qt.getId();
		}else if("六场半全场".equals(lotteryName)){
			lcMatchId = qt.getIssueNum().substring(2) + "26_ZC_BQ" + qt.getId();
		}else if("四场进球彩".equals(lotteryName)){
			lcMatchId = qt.getIssueNum().substring(2) + "27_ZC_JQ" + qt.getId();
		} else if("竞彩足球".equals(lotteryName)){
			String playingDate = sdf.format(qt.getMatchTime());
			String code = getCode(qt.getId());
			long mId = fbMatchDao.findFBMatchId(playingDate, code);	//根据接口给出的开赛时间和赛事code去对应赛事表查询出对应大V彩赛事ID
			if(mId > 0) {
				lcMatchId = mId + "";
			} else {
				log.error("保存竞彩足球大V彩<->球探赛事数据对应关系，"
						+"无法通过比赛时间={}, code={}，找到大V彩赛事ID", 
						new Object[]{playingDate, code});
			}
		} else if("竞彩篮球".equals(lotteryName)) {
			String playingDate = sdf.format(qt.getMatchTime());
			String code = getCode(qt.getId());
			long mId = bbMatchDao.findBBMatchId(playingDate, code);
			if(mId > 0) {
				lcMatchId = mId + "";
			} else {
				log.error("保存竞彩篮球大V彩<->球探赛事数据对应关系，"
						+"无法通过比赛时间={}, code={}，找到大V彩赛事ID", 
						new Object[]{playingDate, code});
			}
		}else if("单场让球胜平负".equals(lotteryName)){
			String issueNum=qt.getIssueNum();
			String id=changeNumber(qt.getId(),issueNum);
			lcMatchId=issueNum+id;
		}else if("北京单场胜负过关".equals(lotteryName)){
			String issueNum=qt.getIssueNum();
			String id=changeNumber(qt.getId(),issueNum);
			lcMatchId=issueNum+id;
			
		}else{
			throw new RuntimeException("lotteryName=" + lotteryName +" is mismatching! ");
		}
		qt.setLcMatchId(lcMatchId);
		
		qtlcMatchIdPoList.add(qt);
		
		// 拷贝一份任九
		if("14场胜负彩".equals(lotteryName)){
			QTLCMatchidPO r9Po = new QTLCMatchidPO();
			BeanUtils.copyProperties(qt, r9Po);
			lcMatchId = qt.getIssueNum().substring(2) + "25_ZC_R9" + qt.getId();
			r9Po.setLcMatchId(lcMatchId);
			r9Po.setLotteryName("任选9场");
			
			qtlcMatchIdPoList.add(r9Po);
		}
		return qtlcMatchIdPoList;
	}
	//北单将id长度有一位的统一转换为3位
	private String changeNumber(String id,String issueNum){
		 
		String nid="";
		if(StringUtils.isBlank(id)){
			log.error("北京单场期号为{}的场次Id为空",issueNum);
			return "000";
		}
		if(id.length()==1){
			nid="00"+id;
			
		}else if(id.length()==2){
			
			nid="0"+id;
		}else if(id.length()==3){
			
			nid=id;
		}
		
		return nid;
	}
	
	private String getCode(String code){
		String week = code.substring(0, 2);
		String lastCode = code.substring(2);
		if("周一".equals(week)){
			return "1" + lastCode;
		}else if("周二".equals(week)){
			return "2" + lastCode;
		}else if("周三".equals(week)){
			return "3" + lastCode;
		}else if("周四".equals(week)){
			return "4" + lastCode;
		}else if("周五".equals(week)){
			return "5" + lastCode;
		}else if("周六".equals(week)){
			return "6" + lastCode;
		}else if("周日".equals(week)){
			return "7" + lastCode;
		}else{
			throw new RuntimeException("week=" + week + " is mismatching! ");
		}
	}

	@Transactional
	@Override
	public void storeFBMatch(List<MatchProcess> matches) {
		for (MatchProcess match : matches) {
			long matchId = Long.parseLong(match.getMatchId());
			QTMatchPO matchPO = qTMatchDao.get(matchId);
			if (matchPO != null){
				// update
				updateMatch(matchPO, match);
			}else{
				// insert
				matchPO = composeMatchPO(matchId, match);
				qTMatchDao.save(matchPO);
			}
			qTMatchDao.flushAndEvict(matchPO);
		}
	}

	private QTMatchPO composeMatchPO(long matchId, MatchProcess match) {
		QTMatchPO matchPO = new QTMatchPO();
		matchPO.setId(matchId);
		
		updateMatch(matchPO, match);
		
		matchPO.setCreateTime(new Date());
		return matchPO;
	}

	private void updateMatch(QTMatchPO matchPO, MatchProcess match) {
		matchPO.setUpdateTime(new Date());
		matchPO.setLeagueId(Long.parseLong(match.getLeagueId()));
		matchPO.setMatchTime(match.getMatchTime());
		String actualBeginTime = match.getActualBeginTime();
		if (StringUtils.isNotBlank(actualBeginTime)){
			matchPO.setHalfStartTime(DateUtil.epochToDate(actualBeginTime));
		}
		
		TeamStatus homeTeamStatus = match.getHomeTeamStatus();
		Team homeTeam = homeTeamStatus.getTeam();
		
//		matchPO.setHomeTeamId(Long.parseLong(homeTeam.getId()));
		matchPO.setHomeTeamName(homeTeam.getNameInGB());
		matchPO.setHomeTeamPosition(homeTeamStatus.getPosition());
		matchPO.setHomeTeamScore(homeTeamStatus.getScore());
		matchPO.setHomeTeamRed(homeTeamStatus.getRed());
		matchPO.setHomeTeamYellow(homeTeamStatus.getYellow());
		
		TeamStatus guestTeamStatus = match.getGuestTeamStatus();
		Team guestTeam = guestTeamStatus.getTeam();
		
//		matchPO.setGuestTeamId(Long.parseLong(guestTeam.getId()));
		matchPO.setGuestTeamName(guestTeam.getNameInGB());
		matchPO.setGuestTeamPosition(guestTeamStatus.getPosition());
		matchPO.setGuestTeamScore(guestTeamStatus.getScore());
		matchPO.setGuestTeamRed(guestTeamStatus.getRed());
		matchPO.setGuestTeamYellow(guestTeamStatus.getYellow());
		
		matchPO.setMatchStatus(Integer.parseInt(match.getMatchStatus()));
		matchPO.setTelevison(match.getTelevison());
		//matchPO.setNeutral(match.isZlc());
		matchPO.setNeutralType(match.isZlc()?1:0);
		//matchPO.setImportance(new BigDecimal(match.getLevel()));
		
		// 联赛短名和颜色
		FBLeaguePO league = fBLeagueDao.get(matchPO.getLeagueId());
		if (league != null){
			matchPO.setLeagueShortName(league.getChineseName());
			matchPO.setColor(league.getColour());
		}
	}

	@Transactional
	@Override
	public void storeFBAsiaOdds(List<AsiaOdd> asiaOdds) {
		for (AsiaOdd odds : asiaOdds) {
			long matchId = Long.parseLong(odds.getMatchId());
			Long hundredCorpId = eliteToHundredCorpId(odds.getCorpId());
			if (hundredCorpId == null){
				log.error("跳过一个亚赔入库，因为无法获取百家欧赔公司id，精选公司id是：{}", odds.getCorpId());
				continue;
			}
			FBAsiaOddsPO asiaPo = fbAsiaOddsDao.findBy(matchId, hundredCorpId);
			if (asiaPo != null) {
				// update
				updateAsiaOddsPO(asiaPo, odds);
			} else {
				// insert
				asiaPo = composeAsiaOddsPO(odds);
				fbAsiaOddsDao.save(asiaPo);
			}
			fbAsiaOddsDao.flushAndEvict(asiaPo);
		}
	}

	private FBAsiaOddsPO composeAsiaOddsPO(AsiaOdd odds) {
		FBAsiaOddsPO fbAsiaOdds = new FBAsiaOddsPO();
		
		fbAsiaOdds.setMatchId(Long.parseLong(odds.getMatchId()));
		
		Long hundredCorpId = eliteToHundredCorpId(odds.getCorpId());
		
		fbAsiaOdds.setCorpId(hundredCorpId);
		
		updateAsiaOddsPO(fbAsiaOdds, odds);
		
		fbAsiaOdds.setCreateTime(new Date());
		
		return fbAsiaOdds;
	}

	private void updateAsiaOddsPO(FBAsiaOddsPO fbAsiaOdds, AsiaOdd odds) {

		fbAsiaOdds.setInitHandicap((new BigDecimal(odds.getInitHandicap())));
		fbAsiaOdds.setInitHomeOdds(new BigDecimal(odds.getHomeInitOdd()));
		fbAsiaOdds.setInitGuestOdds(new BigDecimal(odds.getGuestInitOdd()));
		
		fbAsiaOdds.setCurHandicap(new BigDecimal(odds.getImmediateHandicap()));
		fbAsiaOdds.setCurHomeOdds(new BigDecimal(odds.getHomeImmediateOdd()));
		fbAsiaOdds.setCurGuestOdds(new BigDecimal(odds.getGuestImmediateOdd()));
		
		fbAsiaOdds.setFinalized(odds.isFengPan());
		fbAsiaOdds.setGround(odds.isZouDi());
		fbAsiaOdds.setUpdateTime(new Date());
	}

	@Transactional
	@Override
	public void storeFBEuroOdds(List<EuropeOdd> euroOdds) {
		for (EuropeOdd odds : euroOdds) {
			long matchId = Long.parseLong(odds.getMatchId());
			long hundredCorpId = eliteToHundredCorpId(odds.getCorpId());
			FBEuroOddsPO eoPo = fbEuroOddsDao.findBy(matchId, hundredCorpId);
			if (eoPo != null){
				// update
				updateEuroOddsPO(eoPo, odds);
				// 不能执行 fbEuroOddsDao.update(eoPo)，否则会出现约束异常
			}else{
				// insert
				eoPo = composeEuroOddsPO(odds);
				fbEuroOddsDao.save(eoPo);
			}
			fbEuroOddsDao.flushAndEvict(eoPo);
		}
	}

	private FBEuroOddsPO composeEuroOddsPO(EuropeOdd odds) {
		FBEuroOddsPO fbEuroOdds = new FBEuroOddsPO();
		
		fbEuroOdds.setMatchId(Long.parseLong(odds.getMatchId()));
		
		long hundredCorpId = eliteToHundredCorpId(odds.getCorpId());
		fbEuroOdds.setCorpId(hundredCorpId);
		
		updateEuroOddsPO(fbEuroOdds, odds);
		
		fbEuroOdds.setCreateTime(new Date());

		return fbEuroOdds;
	}

	private Map<Long, Long> eliteCorpIdMap = null;
	
	/**
	 * 转换 精选公司id 为 百家欧赔接口中的 博彩公司id
	 * @param eliteCorpId 精选公司id
	 * @return null 如果没有精选公司id对应的博彩公司id；成功返回"百家欧赔"的公司id
	 */
	private Long eliteToHundredCorpId(String eliteCorpId) {
		// 装载map
		if (eliteCorpIdMap == null) {
			eliteCorpIdMap = new Hashtable<>();
			List<LotteryCorpPO> elites = lotteryCorpDao.listElites();
			for (LotteryCorpPO lotteryCorp : elites) {
				eliteCorpIdMap.put(lotteryCorp.getEliteId(), lotteryCorp.getId());
			}
		}
		long eliteId = Long.parseLong(eliteCorpId);
		Long corpId = eliteCorpIdMap.get(eliteId);
		if (corpId == null){
			log.error("没有精选公司id（{}）对应的博彩公司id！使用精选公司id作为博彩公司id！", eliteCorpId);
		}
		return corpId;
	}

	private void updateEuroOddsPO(FBEuroOddsPO fbEuroOdds, EuropeOdd odds) {
		fbEuroOdds.setInitHomeWinOdds(new BigDecimal(odds.getInitHomeWinOdd()));
		fbEuroOdds.setInitDrawOdds(new BigDecimal(odds.getInitDrawOdd()));
		fbEuroOdds.setInitGuestWinOdds(new BigDecimal(odds.getInitGuestWinOdd()));
		
		fbEuroOdds.setCurHomeWinOdds(new BigDecimal(odds.getImmediateHomeWinOdd()));
		fbEuroOdds.setCurDrawOdds(new BigDecimal(odds.getImmediateDrawOdd()));
		fbEuroOdds.setCurGuestWinOdds(new BigDecimal(odds.getImmediateGuestWinOdd()));
		fbEuroOdds.setUpdateTime(new Date());
	}

	@Override
	@Transactional
	public List<FBLeaguePO> listAllLeagues() {
		return fBLeagueDao.listAllLeagues();
	}

	@Override
	@Transactional
	public void storeLeague(FBLeaguePO leaguePO) {
		if(null!=leaguePO){
			leaguePO.setUpdateTime(new Date());
		}
		fBLeagueDao.update(leaguePO);
	}

	@Transactional
	@Override
	public void updateTeamLogo(Long teamId, String teamLogo) {
		FBTeamPO team = fBTeamDao.findByTeamId(teamId);
		if (team != null) {
			team.setLogoUrl(teamLogo);
			team.setUpdateTime(new Date());
		}else{
			log.error("没有id={}的Team！", teamId);
		}
	}

	@Override
	@Transactional
	public void mergeMatchForCrawler(QTMatchPO matchPO) {
		QTMatchPO oldMatch = qTMatchDao.get(matchPO.getId());
		if (oldMatch != null){
			// update
			oldMatch.setMatchStatus(matchPO.getMatchStatus());
			oldMatch.setMatchTime(matchPO.getMatchTime());
//			oldMatch.setHomeTeamId(matchPO.getHomeTeamId());
//			oldMatch.setGuestTeamId(matchPO.getGuestTeamId());
			// 全场得分，且设置独立的 homeScore, guestScore
			oldMatch.setHomeTeamScore(matchPO.getHomeTeamScore());
			oldMatch.setGuestTeamScore(matchPO.getGuestTeamScore());
			// 半场得分，且设置独立的 halfHomeScore, halfGuestScore
			oldMatch.setHalfScore(matchPO.getHalfScore());
			oldMatch.setHomeHalfScore(matchPO.getHomeHalfScore());
			oldMatch.setGuestHalfScore(matchPO.getGuestHalfScore());
			oldMatch.setUpdateTime(new Date());
		}else{
			// insert
			matchPO.setCreateTime(new Date());
			qTMatchDao.save(matchPO);
		}
	}

	@Override
	@Transactional
	public void storeFBMatchResult(FBBFResultContentData matchData) {
		long matchId = Long.parseLong(matchData.getMatchId());
		QTMatchPO oldMatch = qTMatchDao.get(matchId);
		if (oldMatch != null){
			// update
			mergeMatchData(oldMatch, matchData);
		}else{
			// insert
			QTMatchPO matchPO = new QTMatchPO();
			matchPO.setId(matchId);
			mergeMatchData(matchPO, matchData);
			matchPO.setCreateTime(new Date());
			qTMatchDao.save(matchPO);
		}
	}

	private void mergeMatchData(QTMatchPO oldMatch, FBBFResultContentData matchData) {
		// 联赛相关
		League league = matchData.getLeague();
		oldMatch.setUpdateTime(new Date());
		oldMatch.setLeagueId(Long.parseLong(league.getId()));
		oldMatch.setLeagueShortName(league.getNameInGB());
		oldMatch.setColor(matchData.getColour());
		
		oldMatch.setMatchStatus(Integer.parseInt(matchData.getMatchStatus()));
		
		oldMatch.setMatchTime(matchData.getMatchTime());
		
//		oldMatch.setHomeTeamId(Long.parseLong(matchData.getHomeTeamId()));
		oldMatch.setHomeTeamName(matchData.getHomeName().getChineseName());
		oldMatch.setHomeTeamRed(NumberUtils.safeParseInt(matchData.getHomeRedCard()));
		oldMatch.setHomeTeamPosition(NumberUtils.extractInt(matchData.getHomeRankings()));
		
//		oldMatch.setGuestTeamId(Long.parseLong(matchData.getAwayTeamId()));
		oldMatch.setGuestTeamName(matchData.getAwayName().getChineseName());
		oldMatch.setGuestTeamRed(NumberUtils.safeParseInt(matchData.getAwayRedCard()));
		oldMatch.setGuestTeamPosition(NumberUtils.extractInt(matchData.getAwayRankings()));
		
		// 全场得分，且设置独立的 homeScore, guestScore
		String homeScore = matchData.getHomeScores();
		if(StringUtils.isNotBlank(homeScore)){
			oldMatch.setHomeTeamScore(Integer.parseInt(homeScore));
		}
		String awayScore = matchData.getAwayScores();
		if (StringUtils.isNotBlank(awayScore)){
			oldMatch.setGuestTeamScore(Integer.parseInt(awayScore));
		}
		if (StringUtils.isNotBlank(homeScore) && StringUtils.isNotBlank(awayScore)){
			oldMatch.setScore(homeScore + ":" + awayScore);
		}
		// 半场得分，且设置独立的 halfHomeScore, halfGuestScore
		String homeHalfScore = matchData.getHomeHalfScores();
		String awayHalfScore = matchData.getAwayHalfScores();
		if (StringUtils.isNotBlank(homeHalfScore)) {
			oldMatch.setHomeHalfScore(Integer.parseInt(homeHalfScore));
		}
		if (StringUtils.isNotBlank(awayHalfScore)) {
			oldMatch.setGuestHalfScore(Integer.parseInt(awayHalfScore));
		}
		if (StringUtils.isNotBlank(homeHalfScore) && StringUtils.isNotBlank(awayHalfScore)){
			oldMatch.setHalfScore(homeHalfScore + ":" + awayHalfScore);
		}
	}

	@Transactional
	@Override
	public void storeFBSubCups(List<FBSubCupPO> subCupPOList) {
		for (FBSubCupPO subCupPO : subCupPOList) {
			FBSubCupPO oldSubCup = subCupDao.get(subCupPO.getId());
			if (oldSubCup == null) {
				// insert
				subCupDao.save(subCupPO);
			}else{
				// update
				oldSubCup.setCurrent(subCupPO.isCurrent());
				oldSubCup.setUpdateTime(new Date());
			}
		}
	}

	@Transactional
	@Override
	public void storeSubLeague(FBSubLeaguePO subLeaguePO) {
		FBSubLeaguePO oldSubLeague = subLeagueDao.findFBSubLeagueBy(
				subLeaguePO.getLeagueId(), subLeaguePO.getSeason());
		if (oldSubLeague != null) {
			// update
			oldSubLeague.setCurrentRound(subLeaguePO.getCurrentRound());
			subLeaguePO.setId(oldSubLeague.getId());
		}else{
			// insert
			subLeagueDao.save(subLeaguePO);
		}
	}

	@Transactional
	@Override
	public void storeLeagueScore(FBLeagueScorePO leagueScore) {
		FBLeagueScorePO oldLeagueScore = 
			leagueScoreDao.findLeagueScoreBy(
					leagueScore.getSubLeagueId(), 
					leagueScore.getTeamId(),
					leagueScore.getScoreType());
		if (oldLeagueScore == null){
			// insert
			leagueScoreDao.save(leagueScore);
		}else{
			// update
			BeanUtils.copyProperties(leagueScore, oldLeagueScore, 
					new String[]{"id", "createTime"});
			oldLeagueScore.setUpdateTime(new Date());
		}
	}
	
	@Transactional
	@Override
	public void storeFBCupScore(FBCupScorePO cupScorePO) {
		log.debug("准备保存 FBCupScorePO: {}", cupScorePO);
		FBCupScorePO oldCupScorePO = cupScoreDao.findCupScoreBy(cupScorePO.getSubCupId(),
				cupScorePO.getGroupName(), cupScorePO.getTeamId());
		if (oldCupScorePO == null){
			// insert
			cupScoreDao.save(cupScorePO);
		}else{
			// update
			BeanUtils.copyProperties(cupScorePO, oldCupScorePO, new String[]{
					"id", "createTime"
			});
			oldCupScorePO.setUpdateTime(new Date());
		}
	}
	
	private BBTeamPO copyToBBTeamPO(BBTeamContentData team){
		BBTeamPO result = new BBTeamPO();
		result.setTeamId(Integer.parseInt(team.getTeamId()));
		result.setLeagueId(Integer.parseInt(team.getLeaId()));
		result.setChineseName(team.getChineseName());
		result.setTraditionalName(team.getTeamId());
		result.setEnglishName(team.getEnglishName());
		result.setShortName(team.getShortName());
		String logoUrl = team.getLogo();
		if(StringUtils.isNotBlank(logoUrl)) {
			logoUrl = Constants.logoHost + logoUrl;
		}
		result.setLogoUrl(logoUrl);
		result.setTeamUrl(team.getUrl());
		result.setLocationId(NumberUtils.safeParseInt(team.getLocationID()));
		result.setMatchAddrID(NumberUtils.safeParseInt(team.getMatchAddrID()));
		result.setCity(team.getCity());
		result.setGymnasium(team.getGymnasium());
		result.setCapacity(NumberUtils.safeParseInt(team.getCapacity()));
		int joinYear = NumberUtils.safeParseInt(team.getJoinYear());
		if(joinYear < 0) {//对于“加入联盟年数”为负数的处理
			joinYear = 0;
		}
		result.setJoinYear(joinYear);
		result.setWinNumber(NumberUtils.safeParseInt(team.getFirstTime()));
		result.setDrillmaster(team.getDrillmaster());
		
		return result;
	}

	@Override
	@Transactional
	public void storeBBMatchInfo(List<BBMatchInfoData> matches) {
		if (matches == null) {
			log.info("准备保存 BBMatchInfoData 失败，matches 为null！");
			return ;
		}
		log.debug("准备保存 BBMatchInfoData，共{}条。", matches.size());
		for (BBMatchInfoData bbMatch : matches) {
			BBMatchInfoPO bbMatchInfoPO = bbMatchInfoDao.get(bbMatch.getId());
			if (bbMatchInfoPO != null) {
				// update
				BeanUtilsTools.copyNotNullProperties(bbMatch, bbMatchInfoPO, null);
				bbMatchInfoPO.setUpdateTime(new Date());
			} else {
				// insert
				bbMatchInfoPO = new BBMatchInfoPO();
				bbMatchInfoPO.setCreateTime(new Date());
				BeanUtils.copyProperties(bbMatch, bbMatchInfoPO);
				bbMatchInfoDao.save(bbMatchInfoPO);
			}
			bbMatchInfoDao.flushAndEvict(bbMatchInfoPO);
		}
		log.debug("保存 BBMatchInfoData 完毕，共{}条。", matches.size());
	}
	

	@Override
	@Transactional
	public void updateBBMatchInfo(List<BBMatchInfoData> matches) {
		if (matches == null) {
			log.info("准备update BBMatchInfoData 失败，matches 为null！");
			return ;
		}
		log.debug("准备update BBMatchInfoData，共{}条。", matches.size());
		for (BBMatchInfoData bbMatch : matches) {
			BBMatchInfoPO bbMatchInfoPO = bbMatchInfoDao.get(bbMatch.getId());
			log.debug("更新前数据：{}", bbMatchInfoPO);
			if (bbMatchInfoPO != null) {
				// update
				BeanUtilsTools.copyNotNullProperties(bbMatch, bbMatchInfoPO, 
						new String[]{
						"createTime",
						"updateTime",
						"cupLeague",
						"name","nameF","sclassType","color",
						"matchTime","homeTeamId","homeTeam",
						"homeTeamF", "homeTeamE",
						"guestTeamId",
						"guestTeam", "guestTeamF","guestTeamE",
						"tv","season","matchType","matchClass",
						"matchSubClass","isNeutral"
						});
				bbMatchInfoPO.setUpdateTime(new Date());
				log.debug("更新后数据：{}", bbMatchInfoPO);
			} else {
				log.info("需要更新的篮球比赛不存在！\n{}", bbMatch);
			}
			bbMatchInfoDao.flushAndEvict(bbMatchInfoPO);
		}
		log.debug("update BBMatchInfoData 完毕，共{}条。", matches.size());
	}
	
	@Transactional
	@Override
	public void storeBBTeam(BBTeamContentData bbTeamData) {
		List<BBTeamPO> list = new ArrayList<BBTeamPO>();
		Long teamId = Long.parseLong(bbTeamData.getTeamId());
		BBTeamPO bbTeamPO = bbTeamDao.findByTeamId(teamId);
		
		BBTeamPO result = copyToBBTeamPO(bbTeamData);
		if(null == bbTeamPO) {
			list.add(result);
			bbTeamDao.batchInsert(list);
			
			log.debug("成功插入篮球球队信息,队名={}", result.getChineseName());
		} else {
			bbTeamPO.setUpdateTime(new Date());
			bbTeamPO.setLeagueId(NumberUtils.safeParseInt(bbTeamData.getLeaId()));
			bbTeamPO.setTraditionalName(bbTeamData.getTraditionalName());
			bbTeamPO.setEnglishName(bbTeamData.getEnglishName());
			bbTeamPO.setChineseName(bbTeamData.getChineseName());
			bbTeamPO.setWinNumber(NumberUtils.safeParseInt(bbTeamData.getFirstTime()));
			bbTeamPO.setDrillmaster(bbTeamData.getDrillmaster());
			String logoUrl = bbTeamData.getLogo();
			if(StringUtils.isNotBlank(logoUrl)) {
				logoUrl = Constants.logoHost + logoUrl;
			}
			bbTeamPO.setLogoUrl(logoUrl);
			
			log.debug("成功更新篮球球队资料:teamId={}, 最新球队信息={}", new Object[]{bbTeamPO.getTeamId(), bbTeamData});
		}
	}
	
	private BBLeaguePO copyToBBLeaguePO(BBLeagueContentData bbLeagueContentData){
		BBLeaguePO result = new BBLeaguePO();
		result.setLeagueId(Integer.parseInt(bbLeagueContentData.getLeagueId()));
		result.setColor(bbLeagueContentData.getColour());
		result.setShortName(bbLeagueContentData.getShortName());
		result.setChineseName(bbLeagueContentData.getChineseName());
		result.setTraditionalName(bbLeagueContentData.getTraditionalName());
		result.setEnglishName(bbLeagueContentData.getEnglishName());
		result.setType(NumberUtils.safeParseInt(bbLeagueContentData.getType()));
		result.setCurrMatchSeason(bbLeagueContentData.getCurrMatchSeason());
		result.setCountryId(Integer.parseInt(bbLeagueContentData.getCountryID()));
		result.setCountry(bbLeagueContentData.getCountry());
		result.setCurrYear(NumberUtils.safeParseInt(bbLeagueContentData.getCurrYear()));
		result.setCurrMonth(NumberUtils.safeParseInt(bbLeagueContentData.getCurrMonth()));
		result.setSclassKind(NumberUtils.safeParseInt(bbLeagueContentData.getSclassKind()));
		result.setSclassTime(bbLeagueContentData.getSclassTime());
		
		return result;
	}
	
	@Transactional
	@Override
	public void storeBBLeague(BBLeagueContentData leagueData) {
		Long leagueId = Long.parseLong(leagueData.getLeagueId());
		BBLeaguePO bbLeaguePO = bbLeagueDao.findByLeagueId(leagueId);
		
		BBLeaguePO result = copyToBBLeaguePO(leagueData);
		if(null == bbLeaguePO) {
			result.setCreateTime(new Date());
			bbLeagueDao.save(result);
			log.debug("成功插入篮球联赛信息,联赛名称名={}", result.getChineseName());
		} else {
			bbLeaguePO.setUpdateTime(new Date());
			bbLeaguePO.setLeagueId(NumberUtils.safeParseInt(leagueData.getLeagueId()));
			bbLeaguePO.setType(NumberUtils.safeParseInt(leagueData.getType()));
			bbLeaguePO.setColor(leagueData.getColour());
			bbLeaguePO.setShortName(leagueData.getShortName());
			bbLeaguePO.setChineseName(leagueData.getChineseName());
			bbLeaguePO.setTraditionalName(leagueData.getTraditionalName());
			bbLeaguePO.setEnglishName(leagueData.getEnglishName());
			bbLeaguePO.setType(NumberUtils.safeParseInt(leagueData.getType()));
			bbLeaguePO.setCurrMatchSeason(leagueData.getCurrMatchSeason());
			bbLeaguePO.setCountryId(Integer.parseInt(leagueData.getCountryID()));
			bbLeaguePO.setCountry(leagueData.getCountry());
			bbLeaguePO.setCurrYear(NumberUtils.safeParseInt(leagueData.getCurrYear()));
			bbLeaguePO.setCurrMonth(NumberUtils.safeParseInt(leagueData.getCurrMonth()));
			bbLeaguePO.setSclassKind(NumberUtils.safeParseInt(leagueData.getSclassKind()));
			bbLeaguePO.setSclassTime(leagueData.getSclassTime());
			
			log.debug("成功更新篮球联赛信息:leagueId={}, 最新联赛信息={}", 
					new Object[]{bbLeaguePO.getLeagueId(), leagueData});
		}
	}
	
	private BBConcedeScoreOddsPO copyToBBConcedeScorePO(BBRangFen bbRangFen, 
			BBConcedeScoreOddsPO concedeScoreOddsPO) {
		if(concedeScoreOddsPO == null) {
			concedeScoreOddsPO = new BBConcedeScoreOddsPO();
			concedeScoreOddsPO.setCreateTime(new Date());
		} else {
			concedeScoreOddsPO.setUpdateTime(new Date());
		}
		concedeScoreOddsPO.setMatchId(Long.parseLong(bbRangFen.getMatchId()));
		concedeScoreOddsPO.setCorpId(Long.parseLong(bbRangFen.getCorpId()));
		concedeScoreOddsPO.setInitHandicap(new BigDecimal(bbRangFen.getInitHandicap()));
		concedeScoreOddsPO.setInitHomeOdds(new BigDecimal(bbRangFen.getHomeInitOdd()));
		concedeScoreOddsPO.setInitGuestOdds(new BigDecimal(bbRangFen.getGuestInitOdd()));
		concedeScoreOddsPO.setCurHandicap(new BigDecimal(bbRangFen.getImmediateHandicap()));
		concedeScoreOddsPO.setCurHomeOdds(new BigDecimal(bbRangFen.getHomeImmediateOdd()));
		concedeScoreOddsPO.setCurGuestOdds(new BigDecimal(bbRangFen.getGuestImmediateOdd()));
		//走地盘口
		if(bbRangFen.isZouDi()) {
			concedeScoreOddsPO.setHomeGroundOdds(new BigDecimal(bbRangFen.getHomeZouDiOdd()));
			concedeScoreOddsPO.setGuestGroundOdds(new BigDecimal(bbRangFen.getGuestZouDiOdd()));
		}
		return concedeScoreOddsPO;
	}
	
	@Transactional
	@Override
	public void storeBBConcedeScoreOdds(List<BBRangFen> bbRangFens) {
		if(null == bbRangFens) return;
		int saveCnt = 0;
		int updateCnt = 0;
		for(BBRangFen bbRangFen : bbRangFens) {
			long matchId = Long.parseLong(bbRangFen.getMatchId());
			long corpId = Long.parseLong(bbRangFen.getCorpId());
			BBConcedeScoreOddsPO bbConcedeScoreOddsPO = concedeScoreOddsDao.findBy(matchId, corpId);
			if(null == bbConcedeScoreOddsPO) {
				bbConcedeScoreOddsPO = copyToBBConcedeScorePO(bbRangFen, null);
				concedeScoreOddsDao.save(bbConcedeScoreOddsPO);
				saveCnt = saveCnt + 1;
			} else {
				copyToBBConcedeScorePO(bbRangFen, bbConcedeScoreOddsPO);
				updateCnt = updateCnt + 1;
			}
			concedeScoreOddsDao.flushAndEvict(bbConcedeScoreOddsPO);
		}
		log.debug("篮球让分赔率入库:{}条记录/更新:{}条记录", new Object[]{saveCnt, updateCnt});
	}

	@Override
	@Transactional
	public void storeBBOdds(BBOdds bbOdds) {
		if (bbOdds == null){
			return;
		}
		List<BBOddsConcedeData> concedeOdds = bbOdds.getBbOddsConcede();
		storeBBOddsConcede(concedeOdds);
		
		List<BBOddsBigData> bigOdds = bbOdds.getBbOddsBig();
		storeBBOddsBig(bigOdds);
		
		List<BBOddsEuroData> euroOdds = bbOdds.getBbOddsEuro();
		storeBBOddsEuro(euroOdds);
	}

	private void storeBBOddsConcede(List<BBOddsConcedeData> concedeOdds) {
		log.debug("开始保存篮球让分赔率。。。");
		for (BBOddsConcedeData odds : concedeOdds){
			long matchId = odds.getMatchId();
			long corpId = odds.getCorpId();
			
			BBConcedeScoreOddsPO po = 
					concedeScoreOddsDao.findBy(matchId, corpId);
			
			if(null == po) {
				po = new BBConcedeScoreOddsPO();
				po.setCreateTime(new Date());
				BeanUtilsTools.copyNotNullProperties(odds, po, null);
				// insert
				concedeScoreOddsDao.save(po);
			}else{
				// update
				BeanUtilsTools.copyNotNullProperties(odds, po, null);
				po.setUpdateTime(new Date());
			}
			concedeScoreOddsDao.flushAndEvict(po);
		}
		log.debug("成功保存篮球让分赔率{}条。", concedeOdds.size());
	}

	private void storeBBOddsBig(List<BBOddsBigData> bigOdds) {
		log.debug("开始保存篮球大小分赔率。。。");
		for (BBOddsBigData odds : bigOdds){
			long matchId = odds.getMatchId();
			long corpId = odds.getCorpId();
			
			BBOddsBigSmallPO po = bbOddsBigDao.findBy(matchId, corpId);
			
			if(null == po) {
				po = new BBOddsBigSmallPO();
				po.setCreateTime(new Date());
				BeanUtilsTools.copyNotNullProperties(odds, po, null);
				// insert
				bbOddsBigDao.save(po);
			}else{
				// update
				BeanUtilsTools.copyNotNullProperties(odds, po, null);
				po.setUpdateTime(new Date());
			}
			bbOddsBigDao.flushAndEvict(po);
		}
		log.debug("成功保存篮球大小分赔率{}条。", bigOdds.size());
	}

	private void storeBBOddsEuro(List<BBOddsEuroData> euroOdds) {
		log.debug("开始保存篮球欧赔。。。");
		for (BBOddsEuroData odds : euroOdds){
			long matchId = odds.getMatchId();
			long corpId = odds.getCorpId();
			
			BBOddsEuroPO po = bbOddsEuroDao.findBy(matchId, corpId);
			
			if(null == po) {
				po = new BBOddsEuroPO();
				po.setCreateTime(new Date());
				BeanUtilsTools.copyNotNullProperties(odds, po, null);
				// insert
				bbOddsEuroDao.save(po);
			}else{
				// update
				BeanUtilsTools.copyNotNullProperties(odds, po, null);
				po.setUpdateTime(new Date());
			}
			bbOddsEuroDao.flushAndEvict(po);
		}
		log.debug("成功保存篮球欧赔{}条。", euroOdds.size());
	}

	@Override
	@Transactional
	public void storeBBOddsBjEuro(BBBjEuropeOddsData bbBjOddsEuro) {
		if (bbBjOddsEuro == null||bbBjOddsEuro.fbBjEuropeOddsContentDataList==null){
			log.error("篮球百家欧赔数据为null了，无法保存！");
			return;
		}
		for (BBBjEuropeOddsContentData bj : bbBjOddsEuro.fbBjEuropeOddsContentDataList){
			int matchId = NumberUtils.safeParseInt(bj.getMatchId());
			if (matchId==0){
				log.error("赛事id错误：{}", bj.getMatchId());
				continue;
			}
			List<BBMatchEuropeOddDetail> euroOddsList = bj.getMatchEuropeOddDetailList();
			if (euroOddsList == null){
				continue;
			}
			for (BBMatchEuropeOddDetail odds : euroOddsList){
				// 保存 公司
				saveBBCorp(odds);
			}
			// 保存赔率
			List<BBOddsEuroData> euroOddsData = euroDetailToOddsData(matchId, euroOddsList);
			storeBBOddsEuro(euroOddsData);
		}
	}

	private List<BBOddsEuroData> euroDetailToOddsData(int matchId, List<BBMatchEuropeOddDetail> oddsList) {
		LinkedList<BBOddsEuroData> euroDataList = new LinkedList<>();
		for (BBMatchEuropeOddDetail oddsDetail : oddsList){
			BBOddsEuroData data = new BBOddsEuroData();
			int corpId = NumberUtils.safeParseInt(oddsDetail.getCorpId());
			if (corpId == 0){
				continue;
			}
			data.setCorpId(corpId);
			data.setMatchId(matchId);
			data.setInitGuestWinOdds(NumberUtils.safeParseBigDecimal(oddsDetail.getInitGuestWin()));
			data.setInitHomeWinOdds(NumberUtils.safeParseBigDecimal(oddsDetail.getInitHomeWin()));
			data.setRealtimeGuestWinOdds(NumberUtils.safeParseBigDecimal(oddsDetail.getGuestWin()));
			data.setRealtimeHomeWinOdds(NumberUtils.safeParseBigDecimal(oddsDetail.getHomeWin()));
			
			euroDataList.add(data);
		}
		return euroDataList;
	}

	private void saveBBCorp(BBMatchEuropeOddDetail odds) {
		long id = Long.parseLong(odds.getCorpId());
		BBLotteryCorpPO corp = bbLotteryCorpDao.get(id);
		if (corp == null){
			corp = new BBLotteryCorpPO();
			corp.setId(id);
			corp.setName(odds.getCorpName());
			corp.setCreateTime(new Date());
			bbLotteryCorpDao.save(corp);
			bbLotteryCorpDao.flushAndEvict(corp);
			log.debug("保存了公司：{}, {}", id, corp.getName());
		}
	}
}
