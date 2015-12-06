package com.unison.lottery.api.query.bo;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.davcai.lottery.service.MatchNameService;
import com.unison.lottery.api.framework.utils.ProtocolUtils;
import com.unison.lottery.api.lang.DateUtil;
import com.unison.lottery.api.lang.IssueInfoUtil;
import com.unison.lottery.api.lang.LotteryUtil;
import com.unison.lottery.api.lang.PagingClient;
import com.unison.lottery.api.login.service.ILoginService;
import com.unison.lottery.api.lotteryInfo.util.BonusResultConvert;
import com.unison.lottery.api.model.ClientVersion;
import com.unison.lottery.api.model.User;
import com.unison.lottery.api.protocol.common.Constants;
import com.unison.lottery.api.protocol.common.SystemStatusKeyNames;
import com.unison.lottery.api.protocol.response.model.HaveReturnStatusResponse;
import com.unison.lottery.api.protocol.response.model.QueryCTZCMatchResponse;
import com.unison.lottery.api.protocol.response.model.QueryJCLQMatchResponse;
import com.unison.lottery.api.protocol.response.model.QueryJCZQMatchResponse;
import com.unison.lottery.api.protocol.response.model.QueryOnSaleLotteryResponse;
import com.unison.lottery.api.protocol.status.IStatusRepository;
import com.unison.lottery.api.protocol.status.ReturnStatus;
import com.unison.lottery.api.util.AllowReturnResultService;
import com.unison.lottery.api.util.ClientVersionUtil;
import com.xhcms.lottery.commons.data.BetScheme;
import com.xhcms.lottery.commons.data.CTFBMatch; 
import com.xhcms.lottery.commons.data.IssueInfo;
import com.xhcms.lottery.commons.data.ctfb.CTMatchInfo;
import com.xhcms.lottery.commons.lang.Channel;
import com.xhcms.lottery.commons.persist.dao.ActivityDao;
import com.xhcms.lottery.commons.persist.entity.ActivityPO;
import com.xhcms.lottery.commons.persist.entity.BBMatchPO;
import com.xhcms.lottery.commons.persist.entity.BBMatchPlayPO;
import com.xhcms.lottery.commons.persist.entity.FBMatchPO;
import com.xhcms.lottery.commons.persist.entity.FBMatchPlayPO;
import com.xhcms.lottery.commons.persist.entity.IssueInfoPO;
import com.xhcms.lottery.commons.persist.entity.MixMatchPlay;
import com.xhcms.lottery.commons.persist.entity.SingalMatchPlay;
import com.xhcms.lottery.commons.persist.service.BetException;
import com.xhcms.lottery.commons.persist.service.BetSchemeBaseService;
import com.xhcms.lottery.commons.persist.service.CTMatchService;
import com.xhcms.lottery.commons.persist.service.IssueService;
import com.xhcms.lottery.commons.persist.service.MatchService;
import com.xhcms.lottery.commons.persist.service.UserService;
import com.xhcms.lottery.conf.LeagueColorConf;
import com.xhcms.lottery.lang.EntityStatus;
import com.xhcms.lottery.lang.LotteryId;
import com.xhcms.lottery.lang.MixPlayType;
import com.xhcms.lottery.lang.PlayType;
import com.xhcms.lottery.lang.SchemeDisplayMode;

public class QueryBOImpl implements QueryBO {
	
	@Autowired
	private MatchService matchService;
	
 	@Autowired
 	private IssueService issueService; 
 	
 	@Autowired
 	private LeagueColorConf initLeagueColor;
 	
	@Autowired
	private IStatusRepository statusRepository;
	
	private Logger logger = LoggerFactory.getLogger(getClass());

	
	
	
	@Autowired
    private MatchNameService matchNameService;
	
	@Autowired
	private CTMatchService cTMatchService;
	
	@Autowired
	private ActivityDao activityDao;
	
	@Autowired
	private BetSchemeBaseService betSchemeBaseService;
	@Autowired
	private AllowReturnResultService allowReturnResultService;
	
	@Override
	public QueryOnSaleLotteryResponse queryOnSaleLottery(String banner,User user) {
		QueryOnSaleLotteryResponse response = new QueryOnSaleLotteryResponse();
		ReturnStatus succStatus = statusRepository.getSystemStatusBySystemKey(SystemStatusKeyNames.QueryOnSaleLottery.SUCC);
		if(allowReturnResultService.shouldDecideAllowReturnResult(user.getClientVersion())){//需要控制用户访问
			if(allowReturnResultService.allow(user)){//允许用户访问
				doQuery(banner,response);
			}
			else{//返回默认的轮播图内容
				returnDefaultActivitys(response);
				
			}
			
		}
		else{//不需要控制用户访问
			doQuery(banner,response);
		}
		
		response.setReturnStatus(succStatus);
		return response;
	}


	private void returnDefaultActivitys(QueryOnSaleLotteryResponse response) {
		List<ActivityPO> activitys = new ArrayList<ActivityPO>();
		ActivityPO activity=new ActivityPO();
		activity.setImageURL("http://images.davcai.com/ad/client/201504/banner_default.png");//默认图片地址
		activity.setType("0");//活动类型
		activity.setClientVersion("0");//内容版本号,给客户端返回一个初始版本号
		activity.setTitle("");
		activity.setStatus(0);
		activitys.add(activity);
		Map<String, Integer> map = new HashMap<String, Integer>();
		Map<String, Integer> mapBetType = new HashMap<String, Integer>();
		for(ActivityPO activityPO : activitys){//方案的时候创建displayMode
			if(StringUtils.equals("2", activityPO.getType()) && StringUtils.isNumeric(activityPO.getSchemeId())){
				BetScheme bet = betSchemeBaseService.getSchemeById(Long.valueOf(activityPO.getSchemeId()));	
				if(bet != null){
					map.put(activityPO.getSchemeId(), SchemeDisplayMode.getDisplayMode(bet.getType(), bet.getShowScheme(), bet.getIsPublishShow()));
					mapBetType.put(activityPO.getSchemeId(), bet.getType());
				}
			}
		}
		response.setMap(map);
		response.setMapBetType(mapBetType);
		response.setActivityPOs(activitys);
	}




	private void doQuery(String banner, QueryOnSaleLotteryResponse response) {
		List<Map<String,String>> results = new ArrayList<Map<String,String>>();
		if(betSchemeBaseService.canBet(Channel.MOBILE_CLIENT, LotteryId.JCZQ.toString())){
			Map<String, String> jczqMap = queryJCZQResult();
			results.add(jczqMap);
		}
		if(betSchemeBaseService.canBet(Channel.MOBILE_CLIENT, LotteryId.JCLQ.toString())){
			Map<String, String> jclqMap = queryJCLQResult();
			results.add(jclqMap);
		}
		if(betSchemeBaseService.canBet(Channel.MOBILE_CLIENT, LotteryId.CTZC.toString())){
			//查询传统足彩在售信息
			queryCTZCResult(results);
		}
		
		
		if(betSchemeBaseService.canBet(Channel.MOBILE_CLIENT, LotteryId.JX11.toString())){
			Map<String, String> jx11Map = querJX11Result(response);
			results.add(jx11Map);
		}
		if(betSchemeBaseService.canBet(Channel.MOBILE_CLIENT, LotteryId.CQSS.toString())){
			Map<String, String> cqssMap = queryCQSSResult(response);
			results.add(cqssMap);
		}
		
		if(betSchemeBaseService.canBet(Channel.MOBILE_CLIENT, LotteryId.SSQ.toString())){
			Map<String, String> ssqMap = querySSQResult(response);
			results.add(ssqMap);
		}
		
		if(StringUtils.isNotBlank(banner)){
			handleBanner(banner,response);
		}
		response.setResults(results);
	}

	private void handleBanner(String banner, QueryOnSaleLotteryResponse response) {
		String newBanner = activityDao.findMaxVersion();
		if(Integer.valueOf(banner) < Integer.valueOf(newBanner)){
			List<ActivityPO> activitys = activityDao.findAllStatus(0);
			Map<String, Integer> map = new HashMap<String, Integer>();
			Map<String, Integer> mapBetType = new HashMap<String, Integer>();
			for(ActivityPO activityPO : activitys){//方案的时候创建displayMode
				if(StringUtils.equals("2", activityPO.getType()) && StringUtils.isNumeric(activityPO.getSchemeId())){
					BetScheme bet = betSchemeBaseService.getSchemeById(Long.valueOf(activityPO.getSchemeId()));	
					if(bet != null){
						map.put(activityPO.getSchemeId(), SchemeDisplayMode.getDisplayMode(bet.getType(), bet.getShowScheme(), bet.getIsPublishShow()));
						mapBetType.put(activityPO.getSchemeId(), bet.getType());
					}
				}
			}
			response.setMap(map);
			response.setMapBetType(mapBetType);
			response.setActivityPOs(activitys);
		}
	}
	
	/**
	 * 双色球当前在售期查询
	 * @param response 
	 * @return
	 */
	private Map<String, String> querySSQResult(HaveReturnStatusResponse response) {
		Map<String,String> ssqMap = new HashMap<String, String>();
		String lotteryId = com.xhcms.lottery.lang.Constants.SSQ;
		String issueNumber = null;
		ssqMap.put(Constants.LOTTERY_ID, lotteryId);
		try {
			IssueInfo issueInfo = issueService.getCurrentOnSalingIssue(lotteryId, new Date());
			if(null != issueInfo) {
				
				issueNumber = issueInfo.getIssueNumber();
				ssqMap.put(Constants.ISSUE_NUMBER, issueNumber);
				ssqMap.put(Constants.COUNT_DOWN_TIME, Long.toString(IssueInfoUtil.getCurrentIssueStopTimeForUserCountDown(issueInfo)));
			}
			
			IssueInfo lastestBallotIssue = issueService.findLatestBallotIssue(lotteryId, 
					new Integer[]{
						com.xhcms.lottery.lang.Constants.ISSUE_STATUS_AWARD,
						com.xhcms.lottery.lang.Constants.ISSUE_STATUS_SOLD
					});

			if(null != lastestBallotIssue) {
				ssqMap.put(Constants.LATEST_BALLOT_ISSUE_NUMBER, lastestBallotIssue.getIssueNumber());
				ssqMap.put(Constants.LATEST_BALLOT_LOTTERY_NUMBER, lastestBallotIssue.getBonusCode());
			}
		} catch (Exception e) {
			logger.error("当前在售期查询, 彩种：{}, 期号：{}; 异常信息：{}", new Object[]{lotteryId, issueNumber, e.getMessage()});
			ReturnStatus failStatus=statusRepository.getSystemStatusBySystemKey(SystemStatusKeyNames.QueryOnSaleLottery.FAIL);
			response.setReturnStatus(failStatus);
		}
		return ssqMap;
	}

	/**
	 * 重庆时时彩当前在售期查询
	 * @param response 
	 * @return
	 */
	private Map<String, String> queryCQSSResult(HaveReturnStatusResponse response) {
		Map<String,String> cqssMap = new HashMap<String, String>();
		String lotteryId = com.xhcms.lottery.lang.Constants.CQSS;
		String issueNumber = null;
		cqssMap.put(Constants.LOTTERY_ID, lotteryId);
		try {
			parseLatestIssueBallot(lotteryId, cqssMap);
			
			parseCurrentCanBetIssue(lotteryId, cqssMap);
		} catch (Exception e) {
			logger.error("当前在售期查询, 彩种：{}, 期号：{}; 异常信息：{}", new Object[]{lotteryId, issueNumber, e.getMessage()});
			ReturnStatus failStatus=statusRepository.getSystemStatusBySystemKey(SystemStatusKeyNames.QueryOnSaleLottery.FAIL);;
			response.setReturnStatus(failStatus);
		}
		return cqssMap;
	}
	
	/**
	 * 可投注的期号队列(以当前期开始)，使用逗号隔开；包含期状态：“销售中”和“未开售”
	 * @param lotteryId
	 * @return
	 */
	private Map<String, String> getCanBetIssueQueue(String lotteryId) {
		int maxResults = 2;
		List<IssueInfo> salingIssueList = issueService.getSalingIssueFromCurrent(lotteryId, new Date(), new Integer[]{0,1}, maxResults);
		StringBuffer bufIssueNumber = new StringBuffer();
		StringBuffer bufIssueTimeInterval = new StringBuffer();
		Map<String, String> canBetIssueQueueMap = new HashMap<String, String>();
		int size = salingIssueList.size();
		for(int i = 0; i < size; i++) {
			IssueInfo issueInfo = salingIssueList.get(i);
			String issueNumber = issueInfo.getIssueNumber();
			bufIssueNumber.append(issueNumber);
			if(i != size - 1) {
				bufIssueNumber.append(",");//期号分隔符
			}
			if(0 == i) {
				bufIssueTimeInterval.append(IssueInfoUtil.getCurrentIssueCountDown(issueInfo)).append(",");
			} else {
				bufIssueTimeInterval.append(LotteryUtil.getTimeIntervalByLotteryId(issueInfo, lotteryId));
				if(i != size - 1) {
					bufIssueTimeInterval.append(",");
				}
			}
			canBetIssueQueueMap.put(Constants.ISSUE_NUMBER_QUEUE, bufIssueNumber.toString());
			canBetIssueQueueMap.put(Constants.TIME_INTERVAL_QUEUE, bufIssueTimeInterval.toString());
		}
		return canBetIssueQueueMap;
	}
	
	private Map<String, String> querJX11Result(HaveReturnStatusResponse response) {
		Map<String,String> jx11Map = new HashMap<String, String>();
		String lotteryId = com.xhcms.lottery.lang.Constants.JX11;
		jx11Map.put(Constants.LOTTERY_ID, lotteryId);
		try {
			IssueInfo issueInfo = issueService.getCurrentSalingIssueForShow(lotteryId, new Date());
			jx11Map.put(Constants.ISSUE_NUMBER, issueInfo.getIssueNumber());
			jx11Map.put(Constants.COUNT_DOWN_TIME, Long.toString(IssueInfoUtil.getCurrentIssueCountDown(issueInfo)));
			
			parseLatestIssueBallot(lotteryId, jx11Map);
			parseCurrentCanBetIssue(lotteryId, jx11Map);
		} catch (BetException e) {
			ReturnStatus failStatus=statusRepository.getSystemStatusBySystemKey(SystemStatusKeyNames.QueryOnSaleLottery.FAIL);
			response.setReturnStatus(failStatus);
		}
		return jx11Map;
	}
	
	/**
	 * 解析当前能够投注的期
	 * @param lotteryId
	 * @param map
	 */
	private void parseCurrentCanBetIssue(String lotteryId, Map<String,String> map) {
		Map<String, String> canBetIssueQueueMap = getCanBetIssueQueue(lotteryId);
		if(null != canBetIssueQueueMap && canBetIssueQueueMap.size() > 0) {
			String issue_number_queue = canBetIssueQueueMap.get(Constants.ISSUE_NUMBER_QUEUE);
			String time_interval_queue = canBetIssueQueueMap.get(Constants.TIME_INTERVAL_QUEUE);
			
			map.put(Constants.ISSUE_NUMBER_QUEUE, issue_number_queue);
			map.put(Constants.TIME_INTERVAL_QUEUE, time_interval_queue);
			
			int index = issue_number_queue.indexOf(",");
			String currentIssueNumber = issue_number_queue.substring(0, index);
			index = time_interval_queue.indexOf(",");
			String count_down_time = time_interval_queue.substring(0, index);
			
			map.put(Constants.ISSUE_NUMBER, currentIssueNumber);
			map.put(Constants.COUNT_DOWN_TIME, count_down_time);
		}
	}
	
	/**
	 * 解析出最近一期开奖的期信息
	 * @param lotteryId
	 * @param map
	 */
	private void parseLatestIssueBallot(String lotteryId, Map<String, String> map) {
		IssueInfo lastestBallotIssue = issueService.findLatestBallotIssue(lotteryId, 
				new Integer[]{
					com.xhcms.lottery.lang.Constants.ISSUE_STATUS_AWARD,
					com.xhcms.lottery.lang.Constants.ISSUE_STATUS_SOLD
				});
		IssueInfoPO issueInfo = issueService.getCurrentSalingIssueByLotteryId(lotteryId, new Date());
		if(null != issueInfo) {
			String issueNumber = issueInfo.getIssueNumber();
			map.put(Constants.ISSUE_NUMBER, issueNumber);
			map.put(Constants.COUNT_DOWN_TIME, Long.toString(IssueInfoUtil.getCurrentIssueCountDown(issueInfo)));
		}
		if(null != lastestBallotIssue) {
			map.put(Constants.LATEST_BALLOT_ISSUE_NUMBER, lastestBallotIssue.getIssueNumber());
			map.put(Constants.LATEST_BALLOT_LOTTERY_NUMBER, lastestBallotIssue.getBonusCode());
		}
	}
	
	private Map<String, String> queryJCLQResult() {
		Map<String,String> jclqMap = new HashMap<String, String>();
		jclqMap.put(Constants.LOTTERY_ID, com.xhcms.lottery.lang.Constants.JCLQ);
		Date minMatchPlayingTime=matchService.computeMinMatchPlayingTime();
		List<Object[]> results = matchService.queryJCBBMatchCountByStatusAndAfterDeadLine(EntityStatus.MATCH_ON_SALE,minMatchPlayingTime);
		long bbCount = 0;
		String issueNumber = null;
		if(results != null){
			for (Object[] objects : results) {
				//获取可投注篮球场次数量
				bbCount = (Long) objects[0];
				//获取最早截止可投注比赛的截止的日期
				Date date= (Date) objects[1];
				if(null==date){
					date=new Date();
				}
				SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy年MM月dd日");
				issueNumber = dateFormat.format( date);
				
			}
		}
		
		if(bbCount > 0){
			List<String> leagueNameList = matchService.queryJCBBLeaguesByStatusAndAfterDeadLine(EntityStatus.MATCH_ON_SALE,minMatchPlayingTime);
			String leagueName = ProtocolUtils.listToString(leagueNameList, Constants.COMMA_SEPARATOR);
			jclqMap.put(Constants.LEAGUES, leagueName);
			jclqMap.put(Constants.MATCH_COUNT, Long.toString(bbCount));
			jclqMap.put(Constants.ISSUE_NUMBER, issueNumber);
		}else{
			jclqMap.put(Constants.MATCH_COUNT, "0");
			jclqMap.put(Constants.ISSUE_NUMBER, issueNumber);
		}
			
		
		
		return jclqMap;
	}

	private Map<String, String> queryJCZQResult() {
		Map<String,String> jczqMap = new HashMap<String, String>();
		jczqMap.put(Constants.LOTTERY_ID, com.xhcms.lottery.lang.Constants.JCZQ);
		Date minMatchPlayingTime=matchService.computeMinMatchPlayingTime();
		
		//获取在可投注期内并且可投注的足球场次
		List<Object[]> results = matchService.queryJCFBMatchCountByStatusAndAfterDeadLine(EntityStatus.MATCH_ON_SALE,minMatchPlayingTime);
		long fbCount = 0;
		String issueNumber = null;
		if(results != null){
			for (Object[] objects : results) {
				//获取可投注足球场次数量
				fbCount = (Long) objects[0];
				//获取最早截止可投注比赛的截止的日期
				Date date= (Date) objects[1];
				if(null==date){
					date=new Date();
				}
				SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy年MM月dd日");
				issueNumber = dateFormat.format(date);
			}
		}
		if(fbCount>0){
			List<String> leagueNameList = matchService.queryJCFBLeaguesByStatusAndAfterDeadLine(EntityStatus.MATCH_ON_SALE,minMatchPlayingTime);
			
			String leagueName = ProtocolUtils.listToString(leagueNameList, Constants.COMMA_SEPARATOR);
			jczqMap.put(Constants.LEAGUES, leagueName);
			jczqMap.put(Constants.MATCH_COUNT, Long.toString(fbCount));
			jczqMap.put(Constants.ISSUE_NUMBER, issueNumber);
		}
		else{
			jczqMap.put(Constants.MATCH_COUNT, "0");
			jczqMap.put(Constants.ISSUE_NUMBER, issueNumber);
		}
		
	return jczqMap;
		
	}


	//查询在售的传统足彩数据详情
	private void queryCTZCResult(List<Map<String, String>> results) {
		CTMatchInfo ctMatchInfo_14 = cTMatchService.getCTIssueInfo(PlayType.CTZC_14.getShortPlayStr(), null);
		CTMatchInfo ctMatchInfo_R9 = cTMatchService.getCTIssueInfo(PlayType.CTZC_R9.getShortPlayStr(), null);
		CTMatchInfo ctMatchInfo_BQ = cTMatchService.getCTIssueInfo(PlayType.CTZC_BQ.getShortPlayStr(), null);
		CTMatchInfo ctMatchInfo_JQ = cTMatchService.getCTIssueInfo(PlayType.CTZC_JQ.getShortPlayStr(), null);
		
		results.add(convertCTMatchData(ctMatchInfo_14, PlayType.CTZC_14));
		results.add(convertCTMatchData(ctMatchInfo_R9, PlayType.CTZC_R9));
		results.add(convertCTMatchData(ctMatchInfo_BQ, PlayType.CTZC_BQ));
		results.add(convertCTMatchData(ctMatchInfo_JQ, PlayType.CTZC_JQ));
	}
	
	private Map<String,String> convertCTMatchData(CTMatchInfo ctMatchInfo, PlayType playType) {
		Map<String,String> ctzcMap = new HashMap<String, String>();
		ctzcMap.put(Constants.LOTTERY_ID, com.xhcms.lottery.lang.Constants.CTZC);
		ctzcMap.put(Constants.PLAY_ID, playType.getShortPlayStr());
		
		if(null != ctMatchInfo) {
			List<IssueInfo> issueNumberQueue = ctMatchInfo.getIssueInfos();//可投注期号列表
			ctzcMap.put(Constants.ISSUE_NUMBER_QUEUE, convertIssueInfo(issueNumberQueue));
			
			//返回最近一次开奖期号和开奖号码
			List<IssueInfo> oldIssueInfoList  = ctMatchInfo.getOldIssueInfos();
			if(null != oldIssueInfoList && oldIssueInfoList.size() > 0) {
				int size=oldIssueInfoList.size();
				ctzcMap.put(Constants.LATEST_BALLOT_ISSUE_NUMBER, oldIssueInfoList.get(size-1).getIssueNumber());
				ctzcMap.put(Constants.LATEST_BALLOT_LOTTERY_NUMBER, 
						BonusResultConvert.lotteryBonusCodeConvertBySplit(oldIssueInfoList.get(size-1).getBonusCode(), ""));
			}
			
			
			if(null!= ctMatchInfo.getIssueInfo()){//只有能查到当前期及当前期对应的比赛时，才算可投注
				String issueNumber = ctMatchInfo.getIssueInfo().getIssueNumber();//当前销售期号
				List<CTFBMatch> matchList = cTMatchService.listCTFB(playType.getShortPlayStr(), issueNumber, null);
				if(null!=matchList&&!matchList.isEmpty()){
					Date stopTimeForUser = ctMatchInfo.getIssueInfo().getStopTimeForUser();//此时已是自定义的截止时间
					String offtime = String.valueOf(DateUtil.format(stopTimeForUser, null));//截止时间
					long countDownTime = stopTimeForUser.getTime() - System.currentTimeMillis();
					countDownTime = countDownTime/1000;
					String count_down_time = String.valueOf(countDownTime);//倒计时
					ctzcMap.put(Constants.MATCH_OFF_TIME, offtime);
					ctzcMap.put(Constants.COUNT_DOWN_TIME, count_down_time);
					ctzcMap.put(Constants.ISSUE_NUMBER, issueNumber);
				}
				
			}
			
			
			
			
			
			
		}
		return ctzcMap;
	}
	
	private String convertIssueInfo(List<IssueInfo> issueInfoList) {
		if(null != issueInfoList && issueInfoList.size() > 0) {
			StringBuffer issueInfoStr = new StringBuffer();
			int size = issueInfoList.size();
			for(int i = 0; i < size; i++) {
				issueInfoStr.append(issueInfoList.get(i).getIssueNumber());
				if(i != size - 1) {
					issueInfoStr.append(",");
				}
			}
			return issueInfoStr.toString();
		}
		return "";
	}
	
	@Override
	public QueryJCZQMatchResponse queryJCZQMatch(Map<String,String> paramMap,User user) {
		ReturnStatus succStatus=statusRepository.getSystemStatusBySystemKey(SystemStatusKeyNames.QueryJCZQMatch.SUCC);
		QueryJCZQMatchResponse matchResponse=null;
		if(allowReturnResultService.shouldDecideAllowReturnResult(user.getClientVersion())){//需要控制用户访问
			if(allowReturnResultService.allow(user)){
				matchResponse = doQueryJCZQMatch(paramMap,
						succStatus);
			}
			else{
				matchResponse=new QueryJCZQMatchResponse();
				matchResponse.setReturnStatus(succStatus);
			}
		}
		else{
			matchResponse = doQueryJCZQMatch(paramMap,
					succStatus);
		}
		return matchResponse;
	}


	private QueryJCZQMatchResponse doQueryJCZQMatch(
			Map<String, String> paramMap, ReturnStatus succStatus) {
		String playId = paramMap.get(com.unison.lottery.api.protocol.common.Constants.PLAY_ID);
		
		if(playId == null){
			playId = "01_ZC_2";
		}
		Date minMatchPlayingTime=matchService.computeMinMatchPlayingTime();
		String leagueShortName = paramMap.get(com.unison.lottery.api.protocol.common.Constants.LEAGUE_SHORT_NAME); 
		
		List<String> leagueList = new ArrayList<String>();
		if(leagueShortName == null || leagueShortName.isEmpty()){
			leagueList = matchService.queryJCFBLeaguesByStatusAndAfterDeadLine(EntityStatus.MATCH_ON_SALE,minMatchPlayingTime);
		}else{
			String[] leagueNames = leagueShortName.split(",");
			
			leagueList = Arrays.asList(leagueNames);
		}
		
		String firstResult = paramMap.get(com.unison.lottery.api.protocol.common.Constants.FIRST_RESULT);
		//-------足球混合过关处理------
		if (com.xhcms.lottery.lang.Constants.PLAY_555_ZCFH_2.equals(playId)){ //混合过关
			List<MixMatchPlay> mixMatchPlays  = matchService.listMixFBOnSaleForPageAndLeagueName(playId, leagueList, Integer.parseInt(firstResult), Constants.PAGING_MAX_RESULT,minMatchPlayingTime);
			List<Map<String, String>> matches = getFBMatchResultMapsForHH(mixMatchPlays);
			QueryJCZQMatchResponse matchResponse = new QueryJCZQMatchResponse();
			matchResponse.setResults(matches);
			matchResponse.setReturnStatus(succStatus);
			return matchResponse;
		}
		//------------end--------------
		if(StringUtils.equals(com.xhcms.lottery.lang.Constants.PLAY_555_ZCFH_1, playId)){ //足球单关固定
			List<SingalMatchPlay> singalMatchPlay  = matchService.listSingalFBOnSaleForPageAndLeagueName(playId, leagueList, Integer.parseInt(firstResult), Constants.PAGING_MAX_RESULT,minMatchPlayingTime);
			List<Map<String, String>> matches = getSingalFBMatchResultMapsForHH(singalMatchPlay,Integer.parseInt(firstResult),leagueList);
			QueryJCZQMatchResponse matchResponse = new QueryJCZQMatchResponse();
			matchResponse.setResults(matches);
			matchResponse.setReturnStatus(succStatus);
			return matchResponse;
		}
		List<Object[]> matchAndPlayObjs = matchService.queryJCFBMatchByPlayIdAndStatusAndLeagueNameAndAfterDeadLine(playId, EntityStatus.MATCH_ON_SALE, leagueList,Integer.parseInt(firstResult),Constants.PAGING_MAX_RESULT,minMatchPlayingTime);
		List<Map<String, String>> matches = matchService.getFBMatchResultMaps(matchAndPlayObjs);
		QueryJCZQMatchResponse matchResponse = new QueryJCZQMatchResponse();
		matchResponse.setResults(matches);
		matchResponse.setReturnStatus(succStatus);
		return matchResponse;
	}
	/**
	 * 单关固定--足球
	 * @param singalMatchPlays
	 * @param parseInt
	 * @param leagueList
	 * @return
	 */
	private List<Map<String, String>> getSingalFBMatchResultMapsForHH(
			List<SingalMatchPlay> singalMatchPlays, int parseInt,
			List<String> leagueList) {

		List<Map<String,String>> matches = new ArrayList<Map<String, String>>();
		StringBuilder playIdSB = new StringBuilder();
		playIdSB.append(MixPlayType.SPF.getName().toLowerCase() + ";");
		playIdSB.append(MixPlayType.BRQSPF.getName().toLowerCase() + ";");
		playIdSB.append(MixPlayType.JQS.getName().toLowerCase() + ";");
		playIdSB.append(MixPlayType.BF.getName().toLowerCase() + ";");
		playIdSB.append(MixPlayType.BQC.getName().toLowerCase());
		String playIds = playIdSB.toString();
		for (SingalMatchPlay singalMatchPlay : singalMatchPlays) {
			Map<String,String> resultMap = new HashMap<String, String>();
			resultMap.put(Constants.MATCH_ID, String.valueOf(singalMatchPlay.getId()));
			resultMap.put(Constants.MATCH_CODE, singalMatchPlay.getCode());
			resultMap.put(Constants.LEAGUE_SHORT_NAME, singalMatchPlay.getLeagueName());
			resultMap.put(Constants.LEAGUE_COLOR, singalMatchPlay.getColor());
			resultMap.put(Constants.MATCH_OFF_TIME, matchService.formatEntrustDeadline(singalMatchPlay.getEntrustDeadline()));//避免客户端改动，将比赛截止时间赋值为“大V彩销售截止时间”
			resultMap.put(Constants.ENTRUST_DEADLINE, matchService.formatEntrustDeadline(singalMatchPlay.getEntrustDeadline()));
			resultMap.put(Constants.HOST_NAME, singalMatchPlay.getHomeTeamName());
			resultMap.put(Constants.GUEST_NAME, singalMatchPlay.getGuestTeamName());
			resultMap.put(Constants.CONCEDE_POINTS, String.valueOf((int)singalMatchPlay.getDefaultScore()));
			resultMap.put(Constants.PLAY_ID, playIds);
			
			StringBuilder oddSB = new StringBuilder();
			oddSB.append(singalMatchPlay.getOdds_01ZC1() + ";"); //让球胜平负过关
			oddSB.append(singalMatchPlay.getOdds_80ZC1() + ";"); //胜平负过关
			oddSB.append(singalMatchPlay.getOdds_03ZC1() + ";"); //总进球过关
			oddSB.append(singalMatchPlay.getOdds() + ";"); // 比分过关
			oddSB.append(singalMatchPlay.getOdds_04ZC1()); //半全场过关
			resultMap.put(Constants.ODDS, oddSB.toString());
			
			StringBuilder optionsSB = new StringBuilder();
			optionsSB.append(singalMatchPlay.getOptions_01ZC1() + ";");
			optionsSB.append(singalMatchPlay.getOptions_80ZC1() + ";");
			optionsSB.append(singalMatchPlay.getOptions_03ZC1() + ";");
			optionsSB.append(singalMatchPlay.getOptions() + ";");
			optionsSB.append(singalMatchPlay.getOptions_04ZC1());
			resultMap.put(Constants.OPTIONS, optionsSB.toString());
			
			matches.add(resultMap);
		}
		return matches;
	
	}

	/**
	 * @author 江浩翔
	 * @date 2013-6-28 上午11:47:08 
	 * @Description: 竞彩足球-混合赛事
	 * @param matchAndPlayObjs
	 * @return List<Map<String,String>>
	 * @Throw
	 */
	private List<Map<String, String>> getFBMatchResultMapsForHH(
			List<MixMatchPlay>  mixMatchPlays) {
		List<Map<String,String>> matches = new ArrayList<Map<String, String>>();
		StringBuilder playIdSB = new StringBuilder();
		playIdSB.append(MixPlayType.SPF.getName().toLowerCase() + ";");
		playIdSB.append(MixPlayType.BRQSPF.getName().toLowerCase() + ";");
		playIdSB.append(MixPlayType.JQS.getName().toLowerCase() + ";");
		playIdSB.append(MixPlayType.BF.getName().toLowerCase() + ";");
		playIdSB.append(MixPlayType.BQC.getName().toLowerCase());
		String playIds = playIdSB.toString();
		for (MixMatchPlay mixMatchPlay : mixMatchPlays) {
			Map<String,String> resultMap = new HashMap<String, String>();
			resultMap.put(Constants.MATCH_ID, String.valueOf(mixMatchPlay.getId()));
			resultMap.put(Constants.MATCH_CODE, mixMatchPlay.getCode());
			resultMap.put(Constants.LEAGUE_SHORT_NAME, mixMatchPlay.getLeagueName());
			resultMap.put(Constants.LEAGUE_COLOR, mixMatchPlay.getColor());
			resultMap.put(Constants.MATCH_OFF_TIME, matchService.formatEntrustDeadline(mixMatchPlay.getEntrustDeadline()));//避免客户端改动，将比赛截止时间赋值为“大V彩销售截止时间”
			resultMap.put(Constants.ENTRUST_DEADLINE, matchService.formatEntrustDeadline(mixMatchPlay.getEntrustDeadline()));
			resultMap.put(Constants.HOST_NAME, mixMatchPlay.getHomeTeamName());
			resultMap.put(Constants.GUEST_NAME, mixMatchPlay.getGuestTeamName());
			resultMap.put(Constants.CONCEDE_POINTS, String.valueOf((int)mixMatchPlay.getDefaultScore()));
			resultMap.put(Constants.PLAY_ID, playIds);
			
			StringBuilder oddSB = new StringBuilder();
			oddSB.append(mixMatchPlay.getOdds_01ZC2() + ";"); //让球胜平负过关
			oddSB.append(mixMatchPlay.getOdds_80ZC2() + ";"); //胜平负过关
			oddSB.append(mixMatchPlay.getOdds_03ZC2() + ";"); //总进球过关
			oddSB.append(mixMatchPlay.getOdds() + ";"); // 比分过关
			oddSB.append(mixMatchPlay.getOdds_04ZC2()); //半全场过关
			resultMap.put(Constants.ODDS, oddSB.toString());
			
			StringBuilder optionsSB = new StringBuilder();
			optionsSB.append(mixMatchPlay.getOptions_01ZC2() + ";");
			optionsSB.append(mixMatchPlay.getOptions_80ZC2() + ";");
			optionsSB.append(mixMatchPlay.getOptions_03ZC2() + ";");
			optionsSB.append(mixMatchPlay.getOptions() + ";");
			optionsSB.append(mixMatchPlay.getOptions_04ZC2());
			resultMap.put(Constants.OPTIONS, optionsSB.toString());
			
			matches.add(resultMap);
		}
		return matches;
	}

	

	@Override
	public QueryJCLQMatchResponse queryJCLQMatch(Map<String, String> paramMap,User user) {
		QueryJCLQMatchResponse queryJCLQMatchResponse=null;
		ReturnStatus succStatus=statusRepository.getSystemStatusBySystemKey(SystemStatusKeyNames.QueryJCLQMatch.SUCC);
		if(allowReturnResultService.shouldDecideAllowReturnResult(user.getClientVersion())){//需要控制用户访问
			if(allowReturnResultService.allow(user)){
				queryJCLQMatchResponse=doQueryJCLQMatch(paramMap,succStatus);
			}
			else{
				queryJCLQMatchResponse=new QueryJCLQMatchResponse();
				queryJCLQMatchResponse.setReturnStatus(succStatus);
			}
		}
		else{
			queryJCLQMatchResponse=doQueryJCLQMatch(paramMap,succStatus);
		}
		return queryJCLQMatchResponse;
	}


	private QueryJCLQMatchResponse doQueryJCLQMatch(Map<String, String> paramMap, ReturnStatus succStatus) {
		String playId = paramMap.get(com.unison.lottery.api.protocol.common.Constants.PLAY_ID);
		
		if(playId == null){
			playId = "06_LC_2";
		}
		String leagueShortName = paramMap.get(com.unison.lottery.api.protocol.common.Constants.LEAGUE_SHORT_NAME); 
		Date minMatchPlayingTime=matchService.computeMinMatchPlayingTime();
		
		List<String> leagueList = new ArrayList<String>();
		if(leagueShortName == null || leagueShortName.isEmpty()){
			leagueList = matchService.queryJCBBLeaguesByStatusAndAfterDeadLine(EntityStatus.MATCH_ON_SALE,minMatchPlayingTime);
		}else{
			String[] leagueNames = leagueShortName.split(",");
			leagueList = Arrays.asList(leagueNames);
			
		}
		
		String firstResult = paramMap.get(com.unison.lottery.api.protocol.common.Constants.FIRST_RESULT);
		//-------篮球混合过关处理------
		if (com.xhcms.lottery.lang.Constants.PLAY_666_LCFH_2.equals(playId)){ //混合过关
			List<MixMatchPlay> mixMatchPlays  = matchService.listMixBBOnSaleForPageAndLeagueName(playId, leagueList, Integer.parseInt(firstResult), Constants.PAGING_MAX_RESULT,minMatchPlayingTime);
			List<Map<String, String>> matches = getBBMatchResultMapsForHH(mixMatchPlays,Integer.parseInt(firstResult),leagueList);
			QueryJCLQMatchResponse matchResponse = new QueryJCLQMatchResponse();
			matchResponse.setResults(matches);
			matchResponse.setReturnStatus(succStatus);
			return matchResponse;
		}
		//------------end--------------
		if(StringUtils.equals(com.xhcms.lottery.lang.Constants.PLAY_666_LCFH_1, playId)){ //篮球单关固定
			List<SingalMatchPlay> singalMatchPlay  = matchService.listSingalBBOnSaleForPageAndLeagueName(playId, leagueList, Integer.parseInt(firstResult), Constants.PAGING_MAX_RESULT,minMatchPlayingTime);
			List<Map<String, String>> matches = getSingalBBMatchResultMapsForHH(singalMatchPlay,Integer.parseInt(firstResult),leagueList);
			QueryJCLQMatchResponse matchResponse = new QueryJCLQMatchResponse();
			matchResponse.setResults(matches);
			matchResponse.setReturnStatus(succStatus);
			return matchResponse;
		}
		List<Object[]> matchAndPlayObjs = matchService.queryJCBBMatchByPlayIdAndStatusAndLeagueNameAndAfterDeadLine(playId, EntityStatus.MATCH_ON_SALE, leagueList,Integer.parseInt(firstResult),Constants.PAGING_MAX_RESULT,minMatchPlayingTime);
		List<Map<String, String>> matches = matchService.getBBMatchResultMaps(matchAndPlayObjs);
		QueryJCLQMatchResponse matchResponse = new QueryJCLQMatchResponse();
		matchResponse.setResults(matches);
		matchResponse.setReturnStatus(succStatus);
		return matchResponse;
	}
	/**
	 * 单关固定--篮球
	 * @param singalMatchPlays
	 * @param parseInt
	 * @param leagueList
	 * @return
	 */
	private List<Map<String, String>> getSingalBBMatchResultMapsForHH(
			List<SingalMatchPlay> singalMatchPlays, int parseInt,
			List<String> leagueList) {
		StringBuilder playIdsSB = new StringBuilder();
		playIdsSB.append(MixPlayType.RFSF.getName().toLowerCase() + ";");
		playIdsSB.append(MixPlayType.SF.getName().toLowerCase() + ";");
		playIdsSB.append(MixPlayType.FC.getName().toLowerCase() + ";");
		playIdsSB.append(MixPlayType.DXF.getName().toLowerCase());
		String playIds = playIdsSB.toString(); // 全部玩法
		List<Map<String,String>> matches = new ArrayList<Map<String, String>>();
		for (SingalMatchPlay singalMatchPlay : singalMatchPlays) {
			Map<String,String> resultMap = new HashMap<String, String>();
			resultMap.put(Constants.MATCH_ID, String.valueOf(singalMatchPlay.getId()));
			resultMap.put(Constants.MATCH_CODE, singalMatchPlay.getCode());
			resultMap.put(Constants.LEAGUE_SHORT_NAME, singalMatchPlay.getLeagueName());
			resultMap.put(Constants.LEAGUE_COLOR, initLeagueColor.getColorMap()
					.get(singalMatchPlay.getLeagueName()));
			resultMap.put(Constants.MATCH_OFF_TIME,  matchService.formatEntrustDeadline(singalMatchPlay.getEntrustDeadline()));//避免客户端改动，将比赛截止时间赋值为“大V彩销售截止时间”
			resultMap.put(Constants.ENTRUST_DEADLINE, matchService.formatEntrustDeadline(singalMatchPlay.getEntrustDeadline()));
			resultMap.put(Constants.HOST_NAME, matchNameService.getTeamShortNameByLeagueIdAndTeamName(singalMatchPlay.getLeagueName(),singalMatchPlay.getHomeTeamName()));
			resultMap.put(Constants.GUEST_NAME, matchNameService.getTeamShortNameByLeagueIdAndTeamName(singalMatchPlay.getLeagueName(),singalMatchPlay.getGuestTeamName()));
			
			resultMap.put(Constants.PLAY_ID, playIds);
			
			StringBuilder oddsSB = new StringBuilder();
			oddsSB.append(singalMatchPlay.getOdds_06LC1() + ";"); //让分胜负
			oddsSB.append(singalMatchPlay.getOdds() + ";"); //胜负
			oddsSB.append(singalMatchPlay.getOdds_08LC1() + ";"); //胜分差
			oddsSB.append(singalMatchPlay.getOdds_09LC1()); //大小分
			String odds = oddsSB.toString();
			resultMap.put(Constants.ODDS, odds);
			
			StringBuilder optionsSB = new StringBuilder();
			optionsSB.append(singalMatchPlay.getOptions_06LC1() + ";");
			optionsSB.append(singalMatchPlay.getOptions() + ";");
			optionsSB.append(singalMatchPlay.getOptions_08LC1() + ";");
			optionsSB.append(singalMatchPlay.getOptions_09LC1());
			String optoins = optionsSB.toString();
			resultMap.put(Constants.OPTIONS, optoins);
			
			resultMap.put(Constants.CONCEDE_POINTS_DXF, String.valueOf(singalMatchPlay.getDefaultScore_09LC1()));
			resultMap.put(Constants.CONCEDE_POINTS, String.valueOf(singalMatchPlay.getDefaultScore()));
			
			matches.add(resultMap);
		}
		return matches;
	}

	/**
	 * @author 江浩翔
	 * @date 2013-7-2 下午1:28:12 
	 * @Description: 竞彩篮球-混合赛事
	 * @param mixMatchPlays
	 * @param firstResult
	 * @return List<Map<String,String>>
	 * @Throw
	 */
	private List<Map<String, String>> getBBMatchResultMapsForHH(
			List<MixMatchPlay> mixMatchPlays, int firstResult,List<String> leagueList) {
		StringBuilder playIdsSB = new StringBuilder();
		playIdsSB.append(MixPlayType.RFSF.getName().toLowerCase() + ";");
		playIdsSB.append(MixPlayType.SF.getName().toLowerCase() + ";");
		playIdsSB.append(MixPlayType.FC.getName().toLowerCase() + ";");
		playIdsSB.append(MixPlayType.DXF.getName().toLowerCase());
		String playIds = playIdsSB.toString(); // 全部玩法
		List<Map<String,String>> matches = new ArrayList<Map<String, String>>();
		for (MixMatchPlay mixMatchPlay : mixMatchPlays) {
			Map<String,String> resultMap = new HashMap<String, String>();
			resultMap.put(Constants.MATCH_ID, String.valueOf(mixMatchPlay.getId()));
			resultMap.put(Constants.MATCH_CODE, mixMatchPlay.getCode());
			resultMap.put(Constants.LEAGUE_SHORT_NAME, mixMatchPlay.getLeagueName());
			resultMap.put(Constants.LEAGUE_COLOR, initLeagueColor.getColorMap()
					.get(mixMatchPlay.getLeagueName()));
			resultMap.put(Constants.MATCH_OFF_TIME,  matchService.formatEntrustDeadline(mixMatchPlay.getEntrustDeadline()));//避免客户端改动，将比赛截止时间赋值为“大V彩销售截止时间”
			resultMap.put(Constants.ENTRUST_DEADLINE, matchService.formatEntrustDeadline(mixMatchPlay.getEntrustDeadline()));
			resultMap.put(Constants.HOST_NAME, matchNameService.getTeamShortNameByLeagueIdAndTeamName(mixMatchPlay.getLeagueName(),mixMatchPlay.getHomeTeamName()));
			resultMap.put(Constants.GUEST_NAME, matchNameService.getTeamShortNameByLeagueIdAndTeamName(mixMatchPlay.getLeagueName(),mixMatchPlay.getGuestTeamName()));
			
			resultMap.put(Constants.PLAY_ID, playIds);
			
			StringBuilder oddsSB = new StringBuilder();
			oddsSB.append(mixMatchPlay.getOdds_06LC2() + ";"); //让分胜负
			oddsSB.append(mixMatchPlay.getOdds() + ";"); //胜负
			oddsSB.append(mixMatchPlay.getOdds_08LC2() + ";"); //胜分差
			oddsSB.append(mixMatchPlay.getOdds_09LC2()); //大小分
			String odds = oddsSB.toString();
			resultMap.put(Constants.ODDS, odds);
			
			StringBuilder optionsSB = new StringBuilder();
			optionsSB.append(mixMatchPlay.getOptions_06LC2() + ";");
			optionsSB.append(mixMatchPlay.getOptions() + ";");
			optionsSB.append(mixMatchPlay.getOptions_08LC2() + ";");
			optionsSB.append(mixMatchPlay.getOptions_09LC2());
			String optoins = optionsSB.toString();
			resultMap.put(Constants.OPTIONS, optoins);
			
			resultMap.put(Constants.CONCEDE_POINTS_DXF, String.valueOf(mixMatchPlay.getDefaultScore_09LC2()));
			resultMap.put(Constants.CONCEDE_POINTS, String.valueOf(mixMatchPlay.getDefaultScore()));
			
			matches.add(resultMap);
		}
		return matches;
	}


	
	@Override
	public QueryCTZCMatchResponse queryCTZCMatch(Map<String, String> paramMap,User user) {
		
		ReturnStatus succStatus = statusRepository.getSystemStatusBySystemKey(SystemStatusKeyNames.QueryCTZCMatch.SUCC);
		ReturnStatus failStatus = statusRepository.getSystemStatusBySystemKey(SystemStatusKeyNames.QueryCTZCMatch.FAIL);
		QueryCTZCMatchResponse ctzcMatchResponse = null;
		if(allowReturnResultService.shouldDecideAllowReturnResult(user.getClientVersion())){//需要控制用户访问
			if(allowReturnResultService.allow(user)){
				ctzcMatchResponse=doQueryCTZCMatch(paramMap,
						succStatus, failStatus);
			}
			else{
				ctzcMatchResponse=new QueryCTZCMatchResponse();
				ctzcMatchResponse.setReturnStatus(succStatus);
			}
		}
		else{
			ctzcMatchResponse=doQueryCTZCMatch(paramMap,
					succStatus, failStatus);
		}
		return ctzcMatchResponse;
	}


	private QueryCTZCMatchResponse doQueryCTZCMatch(
			Map<String, String> paramMap, ReturnStatus succStatus,
			ReturnStatus failStatus) {
		String playId = paramMap.get(Constants.PLAY_ID);
		if(playId == null){
			playId = PlayType.CTZC_R9.getShortPlayStr();
			logger.info("查询传统足彩可投注赛事：玩法为空，使用默认玩法={}", playId);
		}
		String targetIssueNumber = paramMap.get(Constants.ISSUE_NUMBER);
		QueryCTZCMatchResponse ctzcMatchResponse = new QueryCTZCMatchResponse();
		try {
			if(StringUtils.isBlank(targetIssueNumber)) {
				CTMatchInfo ctMatchInfo = cTMatchService.getCTIssueInfo(playId, null);
				if(null != ctMatchInfo) {
					targetIssueNumber = ctMatchInfo.getIssueInfo().getIssueNumber();
				}
				logger.info("查询传统足彩可投注赛事：期号为空，取得默认期号={}, 玩法ID={}", 
						new Object[]{targetIssueNumber, playId});
			}
			
			PagingClient paging = new PagingClient();
			paging.setMaxResults(Constants.PAGING_MAX_RESULT);
			List<CTFBMatch> list = cTMatchService.listCTFB(playId, targetIssueNumber, paging);
			ctzcMatchResponse.setCtfbMatchs(list);
			
			if(StringUtils.isNotBlank(targetIssueNumber)) {
				//根据玩法ID和期号查询对应期的倒计时时间
				IssueInfo issueInfo = issueService.findByIssueAndPlayId(playId, targetIssueNumber);
				long countdownTime = issueService.computeCountDownTimeForUser(issueInfo);
				ctzcMatchResponse.setCountdownTime(String.valueOf(countdownTime));
			}
			
			ctzcMatchResponse.setReturnStatus(succStatus);
		} catch (Exception e) {
			ctzcMatchResponse.setReturnStatus(failStatus);
			e.printStackTrace();
		}
		return ctzcMatchResponse;
	}

}
