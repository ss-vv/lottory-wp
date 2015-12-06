package com.unison.lottery.api.query.bo;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.davcai.lottery.service.MatchNameService;
import com.unison.lottery.api.callBack.model.BasketballMatchMessage;
import com.unison.lottery.api.callBack.model.FootballMatchMessage;
import com.unison.lottery.api.model.User;
import com.unison.lottery.api.protocol.common.SystemStatusKeyNames;
import com.unison.lottery.api.protocol.response.model.QueryScoreLiveInfoResponse;
import com.unison.lottery.api.protocol.status.IStatusRepository;
import com.unison.lottery.api.protocol.status.ReturnStatus;
import com.unison.lottery.api.util.DateFormateUtil;
import com.unison.lottery.weibo.data.crawler.service.store.persist.dao.entity.BasketBallMatchPO;
import com.unison.lottery.weibo.data.crawler.service.store.persist.dao.entity.FbMatchBaseInfoPO;
import com.xhcms.lottery.commons.data.BasketBallMatchData;
import com.xhcms.lottery.commons.data.FootBallMatchData;
import com.xhcms.lottery.commons.persist.service.MatchService;

public class QueryScoreLiveBOImpl implements QueryScoreLiveBO{
	
	private Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private MatchNameService matchNameService;
	@Autowired
	private MatchService matchService;
	
	@Autowired
	private IStatusRepository statusRepository;
	
	@Override
	public QueryScoreLiveInfoResponse makeQueryScoreLiveInfoResponse(User user,
			Map<String, Object> paramMap) {
		QueryScoreLiveInfoResponse queryScoreLiveInfoResponse = new QueryScoreLiveInfoResponse();
		ReturnStatus succStatus=statusRepository.getSystemStatusBySystemKey(SystemStatusKeyNames.QUERY_GROUP_INFO_SUCC);
		ReturnStatus failStatus=statusRepository.getSystemStatusBySystemKey(SystemStatusKeyNames.QUERY_GROUP_INFO_FAIL);
		queryScoreLiveInfoResponse.setReturnStatus(failStatus);
		
		String matchType = (String) paramMap.get("matchType");
		String subType = (String) paramMap.get("subType");
		String leagueShortName = (String) paramMap.get("leagueShortName");
		
		List<BasketBallMatchData> basketBiFen = null;
		List<FootBallMatchData> footDatas = null;
		if(StringUtils.equals("0", matchType)){//篮球
			basketBiFen = matchService.getLQbifenData(matchType,subType,leagueShortName);
		} else if(StringUtils.equals("1", matchType)){
			footDatas = matchService.getZQbifenData(matchType,subType,leagueShortName);
		}
		queryScoreLiveInfoResponse.matchType = matchType;
		if(basketBiFen != null && !basketBiFen.isEmpty()){
			ArrayList<BasketballMatchMessage> basFinishs = new ArrayList<BasketballMatchMessage>();
			ArrayList<BasketballMatchMessage> basNoFinishs = new ArrayList<BasketballMatchMessage>();
			BasketballMatchMessage basMessage = null;
			for(BasketBallMatchData basketBallMatchData : basketBiFen){
				basMessage= makeBasketballMatchMessage(basketBallMatchData);
				if(basketBallMatchData.getBasketBallMatchPO().getMatchState() == -1){
					basFinishs.add(basMessage);
				} else {
					basNoFinishs.add(basMessage);
				}
			}
			Map<String, ArrayList<BasketballMatchMessage>> basMaps = new HashMap<String, ArrayList<BasketballMatchMessage>>();
			basMaps.put(SystemStatusKeyNames.FINISH, basFinishs);
			basMaps.put(SystemStatusKeyNames.NO_FINISH, basNoFinishs);
			queryScoreLiveInfoResponse.setBasketInfos(basMaps);
		} else if(footDatas != null && !footDatas.isEmpty()){
			ArrayList<FootballMatchMessage> footFinishs = new ArrayList<FootballMatchMessage>();
			ArrayList<FootballMatchMessage> footNoFinishs = new ArrayList<FootballMatchMessage>();
			FootballMatchMessage footMessage = null;
			for(FootBallMatchData footBallMatchData : footDatas){
				footMessage= makeFootballMatchMessage(footBallMatchData);
				if(footBallMatchData.getFoBaseInfoPO().getMatchStatus()== -1){
					footFinishs.add(footMessage);
				} else {
					footNoFinishs.add(footMessage);
				}
			}
			Map<String, ArrayList<FootballMatchMessage>> footMaps = new HashMap<String, ArrayList<FootballMatchMessage>>();
			footMaps.put(SystemStatusKeyNames.FINISH, footFinishs);
			footMaps.put(SystemStatusKeyNames.NO_FINISH, footNoFinishs);
			queryScoreLiveInfoResponse.setFootInfos(footMaps);
			
		} else {
			logger.info("查询竞猜(篮球/足球为null), matchType:{}",matchType);
		}
		queryScoreLiveInfoResponse.setReturnStatus(succStatus);
		return queryScoreLiveInfoResponse;
	}

	private BasketballMatchMessage makeBasketballMatchMessage(
			BasketBallMatchData basketBallMatchData) {
		BasketballMatchMessage basketballMatchMessage = new BasketballMatchMessage();
		basketballMatchMessage.setMatchId(makeMatchId(basketBallMatchData.getBasketBallMatchPO()));
		basketballMatchMessage.setHomeTeamName(matchNameService.getTeamShortNameByLeagueIdAndTeamName(basketBallMatchData.getLeagueShortName(), 
				basketBallMatchData.getBasketBallMatchPO().getHomeTeam()));
		basketballMatchMessage.setGuestTeamName(matchNameService.getTeamShortNameByLeagueIdAndTeamName(basketBallMatchData.getLeagueShortName(), 
				basketBallMatchData.getBasketBallMatchPO().getGuestTeam()));
		basketballMatchMessage.setHomeScore(basketBallMatchData.getBasketBallMatchPO().getHomeScore());
		basketballMatchMessage.setGuestScore(basketBallMatchData.getBasketBallMatchPO().getGuestScore());
		basketballMatchMessage.setHomeOne(basketBallMatchData.getBasketBallMatchPO().getHomeOne());
		basketballMatchMessage.setGuestOne(basketBallMatchData.getBasketBallMatchPO().getGuestOne());
		basketballMatchMessage.setHomeTwo(basketBallMatchData.getBasketBallMatchPO().getHomeTwo());
		basketballMatchMessage.setGuestTwo(basketBallMatchData.getBasketBallMatchPO().getGuestTwo());
		basketballMatchMessage.setHomeFour(basketBallMatchData.getBasketBallMatchPO().getHomeFour());
		basketballMatchMessage.setGuestFour(basketBallMatchData.getBasketBallMatchPO().getGuestFour());
		basketballMatchMessage.setHomeThree(basketBallMatchData.getBasketBallMatchPO().getHomeThree());
		basketballMatchMessage.setGuestThree(basketBallMatchData.getBasketBallMatchPO().getGuestThree());
		basketballMatchMessage.setRemainTime(basketBallMatchData.getBasketBallMatchPO().getRemainTime());
		basketballMatchMessage.setHomeAddTimeScore(basketBallMatchData.getBasketBallMatchPO().getHomeAddTime1());
		basketballMatchMessage.setGuestAddTimeScore(basketBallMatchData.getBasketBallMatchPO().getGuestAddTime1());
		SimpleDateFormat simpleDateFormat =  new SimpleDateFormat("HH:mm");
		String time = simpleDateFormat.format(basketBallMatchData.getBasketBallMatchPO().getMatchTime());
		basketballMatchMessage.setMatchTime(time);
		if(StringUtils.contains(basketBallMatchData.getBasketBallMatchPO().getExplainContent(), "^^")){
			String explainContent = basketBallMatchData.getBasketBallMatchPO().getExplainContent();
			String[] content = StringUtils.split(explainContent,"^^");
			//取最新的直播内容，兼容旧客户端
			basketballMatchMessage.setExplainContent(content[content.length-1]);
		}else{
			basketballMatchMessage.setExplainContent(basketBallMatchData.getBasketBallMatchPO().getExplainContent());
		}
		basketballMatchMessage.setMatchCode(basketBallMatchData.getBasketBallMatchPO().getJingcaiId());
		basketballMatchMessage.setLeagueShortName(basketBallMatchData.getLeagueShortName());
		basketballMatchMessage.setRemainTime(basketBallMatchData.getBasketBallMatchPO().getRemainTime());
		if (basketBallMatchData.getBasketBallMatchPO().getMatchState() != null) {
			basketballMatchMessage.setState(String.valueOf(basketBallMatchData.getBasketBallMatchPO().getMatchState()));
		} else {
			basketballMatchMessage.setState("");
		}
		basketballMatchMessage.setLeagueColor(basketBallMatchData.getColor());
		return basketballMatchMessage;
	}
	private FootballMatchMessage makeFootballMatchMessage(
			FootBallMatchData footBallMatchData) {
		FootballMatchMessage footballMatchMessage = new FootballMatchMessage();
		footballMatchMessage.setMatchId(makeMatchId(footBallMatchData.getFoBaseInfoPO()));
		footballMatchMessage.setHomeTeamName(footBallMatchData.getFoBaseInfoPO().getHomeTeamId());
		footballMatchMessage.setGuestTeamName(footBallMatchData.getFoBaseInfoPO().getGuestTeamId());
		footballMatchMessage.setHomeScore(makeDefaultValue(footBallMatchData.getFoBaseInfoPO().getHomeTeamScore()));
		footballMatchMessage.setGuestScore(makeDefaultValue(footBallMatchData.getFoBaseInfoPO().getGuestTeamScore()));
		footballMatchMessage.setState(String.valueOf(footBallMatchData.getFoBaseInfoPO().getMatchStatus()));
		footballMatchMessage.setGuestTeamHalfScore(makeDefaultValue(footBallMatchData.getFoBaseInfoPO().getGuestTeamHalfScore()));
		footballMatchMessage.setHomeTeamHalfScore(makeDefaultValue(footBallMatchData.getFoBaseInfoPO().getHomeTeamHalfScore()));
		footballMatchMessage.setGuestTeamPosition(String.valueOf(footBallMatchData.getFoBaseInfoPO().getGuestTeamPosition()));
		Integer guestTeamRed = footBallMatchData.getFoBaseInfoPO().getGuestTeamRed();
		guestTeamRed = makeDefaultValue(guestTeamRed);
		footballMatchMessage.setGuestTeamRed(guestTeamRed);
		Integer teamYellow = footBallMatchData.getFoBaseInfoPO().getGuestTeamYellow();
		teamYellow = makeDefaultValue(teamYellow);
		footballMatchMessage.setGuestTeamYellow(teamYellow);
		footballMatchMessage.setHalfStartTime(footBallMatchData.getFoBaseInfoPO().getHalfStartTime());
		footballMatchMessage.setHomeTeamPosition(String.valueOf(footBallMatchData.getFoBaseInfoPO().getHomeTeamPosition()));
		Integer homeTeamRed = footBallMatchData.getFoBaseInfoPO().getHomeTeamRed();
		homeTeamRed = makeDefaultValue(homeTeamRed);
		footballMatchMessage.setHomeTeamRed(homeTeamRed);
		teamYellow = footBallMatchData.getFoBaseInfoPO().getHomeTeamYellow();
		teamYellow = makeDefaultValue(teamYellow);
		footballMatchMessage.setHomeTeamYellow(teamYellow);
		Integer state = footBallMatchData.getFoBaseInfoPO().getMatchStatus();
		String time = null;
		if(state == 1){//上半场
			time = makeMatchMinutes(footBallMatchData, 0);
		} else if(state == 3){//下半场
			time = makeMatchMinutes(footBallMatchData,45);
		} else if(state == 4){ //加时赛
			time = makeMatchMinutes(footBallMatchData, 90);
		}else {
			SimpleDateFormat simpleDateFormat =  new SimpleDateFormat("HH:mm");
			time = simpleDateFormat.format(footBallMatchData.getFoBaseInfoPO().getMatchTime());
		}
		footballMatchMessage.setMatchTime(state == 2 ? "-1" : time);//中场时为空
		footballMatchMessage.setMatchCode(footBallMatchData.getFoBaseInfoPO().getJingcaiId());
		footballMatchMessage.setLeagueShortName(footBallMatchData.getLeagueShortName());
		footballMatchMessage.setLeagueColor(footBallMatchData.getColor());
		footballMatchMessage.setLiveContent(footBallMatchData.getFoBaseInfoPO().getLiveContent());
		return footballMatchMessage;
	}

	private String makeMatchMinutes(FootBallMatchData footBallMatchData,int minutes) {
		String time;
		if(footBallMatchData.getFoBaseInfoPO().getHalfStartTime() != null){
			time = String.valueOf(((new Date().getTime() - footBallMatchData.getFoBaseInfoPO().getHalfStartTime().getTime())/(60*1000)) + minutes); 
		}else{
			time = String.valueOf(((new Date().getTime() - footBallMatchData.getFoBaseInfoPO().getMatchTime().getTime())/(60*1000)) + minutes);;
		}
		return time;
	}

	private Integer makeDefaultValue(Integer value) {
		if(value == null){
			value = 0;
		}
		return value;
	}
	private String makeMatchId(Object qtMatchBaseModel) {
		StringBuilder matchId = new StringBuilder("");
		String jingcaiId = "";
		if (qtMatchBaseModel instanceof FbMatchBaseInfoPO) {
			FbMatchBaseInfoPO qtMatchBaseModel2 = (FbMatchBaseInfoPO) qtMatchBaseModel;
			matchId.append(DateFormateUtil.getNowDate(qtMatchBaseModel2
					.getMatchTime()));
			jingcaiId = qtMatchBaseModel2.getJingcaiId();
		} else if (qtMatchBaseModel instanceof BasketBallMatchPO) {
			BasketBallMatchPO qtMatchBaseModel2 = (BasketBallMatchPO) qtMatchBaseModel;
			matchId.append(DateFormateUtil.getNowDate(qtMatchBaseModel2
					.getMatchTime()));
			jingcaiId = qtMatchBaseModel2.getJingcaiId();
		}
		if (jingcaiId != null) {
			String weekend = jingcaiId.substring(0, jingcaiId.length() - 3);
			String index = jingcaiId.substring(jingcaiId.length() - 3); // 序号，例如周三004指的就是004
			switch (weekend) {
			case "周一":
				weekend = "1";
				break;
			case "周二":
				weekend = "2";
				break;
			case "周三":
				weekend = "3";
				break;
			case "周四":
				weekend = "4";
				break;
			case "周五":
				weekend = "5";
				break;
			case "周六":
				weekend = "6";
				break;
			case "周日":
				weekend = "7";
				break;
			default:
				weekend = "0";
				break;
			}
			matchId.append(weekend);
			matchId.append(index);
		}
		return matchId.toString();
	}
}
