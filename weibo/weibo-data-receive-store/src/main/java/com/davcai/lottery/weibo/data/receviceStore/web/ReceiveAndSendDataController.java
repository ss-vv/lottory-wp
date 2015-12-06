package com.davcai.lottery.weibo.data.receviceStore.web;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.davcai.lottery.weibo.data.receviceStore.service.ReceiveAndSendDataService;
import com.unison.lottery.weibo.dataservice.commons.crawler.constants.Qt_fb_match_oddsType;
import com.unison.lottery.weibo.dataservice.commons.crawler.constants.ReceiveAndSendDataType;

/**
 * 发送和接受发送抓取服务器的接口
 * 
 * @author baoxing.peng
 * @since 2014年12月31日上午9:26:21
 */
@Controller
public class ReceiveAndSendDataController {
	private static final String DATA_TYPE = "dataType";
	Logger log = LoggerFactory.getLogger(ReceiveAndSendDataController.class);
	@Autowired
	private ReceiveAndSendDataService receiveAndSendDataService;

	@RequestMapping(value = "/receive")
	public String receiveData(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String jsonObject = request.getParameter("jsonObject");
		ReceiveAndSendDataType dataType = ReceiveAndSendDataType
				.valueOf(request.getParameter(DATA_TYPE));
		if (dataType == ReceiveAndSendDataType.FbJingcaiJishiBifen) {
			receiveAndSendDataService.saveFbJingcaiJishiBifen(jsonObject);
		} else if (dataType == ReceiveAndSendDataType.BbJingcaiJishiBifen) {
			receiveAndSendDataService.saveBbJingcaiJishiBifen(jsonObject);
		} else if (dataType == ReceiveAndSendDataType.ZqOddsChange) {
			Qt_fb_match_oddsType oddsType = Qt_fb_match_oddsType
					.valueOf(request.getParameter("oddsType"));
			receiveAndSendDataService.saveFbOddChange(jsonObject, oddsType);
		} else if (dataType == ReceiveAndSendDataType.ZqJingcaiJishiEvent) {
			receiveAndSendDataService.saveFbJishiEvent(jsonObject);
		} else if (dataType == ReceiveAndSendDataType.ZqLeagueInfo) {
			receiveAndSendDataService.saveFbLeagueInfo(jsonObject);
		}else if(dataType == ReceiveAndSendDataType.fbSubLeague){
			String leagueType = request.getParameter("leagueType");
			receiveAndSendDataService.saveFbSubLeague(jsonObject,leagueType);
		}else if(dataType == ReceiveAndSendDataType.saveFbMatchList){
			int round = Integer.valueOf(request.getParameter("round"));
			int seasonId = Integer.valueOf(request.getParameter("seasonId"));
			receiveAndSendDataService.saveFbMatchList(jsonObject,round,seasonId);
		}else if(dataType == ReceiveAndSendDataType.saveFbCupMatchList){
			String seasonId = request.getParameter("seasonId");
			receiveAndSendDataService.saveFbCupMatchInfo(jsonObject,seasonId);
		}else if(dataType == ReceiveAndSendDataType.saveFbMatchLineup){
			receiveAndSendDataService.saveFbMatchLineup(jsonObject);
		}else if(dataType == ReceiveAndSendDataType.saveFbLeagueScore){
			String encodeSeasonJson = request.getParameter("season");
			receiveAndSendDataService.saveFbLeagueScore(encodeSeasonJson,jsonObject);
		}else if(dataType == ReceiveAndSendDataType.saveCupScoreData){
			String encodeSeasonJson = request.getParameter("season");
			receiveAndSendDataService.saveFbCupSocre(encodeSeasonJson,jsonObject);
		}else if(dataType == ReceiveAndSendDataType.updateFbAllJishiMatchMess){
			receiveAndSendDataService.updateFbAllJishiMatchMess(jsonObject);
		}else if(dataType == ReceiveAndSendDataType.updateLqAllJishiMatchMess){
			receiveAndSendDataService.updateLqAllJishiMatchMess(jsonObject);
		}else if(dataType == ReceiveAndSendDataType.saveBbLeagueSeason){
			receiveAndSendDataService.saveBbLeagueSeason(jsonObject);
		}else if (dataType == ReceiveAndSendDataType.saveLqLeagueMatchMessage) {
			String seasonDecodeJson = request.getParameter("season");
			receiveAndSendDataService.saveLqLeagueMatchMessage(seasonDecodeJson,jsonObject);
		}else if(dataType == ReceiveAndSendDataType.saveLqCupGroup){
			String leagueType = request.getParameter("leagueType");
			receiveAndSendDataService.saveLqCupGroup(jsonObject,leagueType);
		}else if(dataType == ReceiveAndSendDataType.saveLqSubLeague){
			receiveAndSendDataService.saveLqSubLeague(jsonObject);
		}else if(dataType == ReceiveAndSendDataType.saveLqCupMatchAndCupScore){
			String seasonEncodeJson = request.getParameter("season");
			receiveAndSendDataService.saveLqCupMatchAndCupScore(jsonObject,seasonEncodeJson); 
		}else if(dataType == ReceiveAndSendDataType.saveLqMatchPlayerStatisticData){
			receiveAndSendDataService.saveBasketMatchPlayerStatisticData(jsonObject);
		}else if(dataType == ReceiveAndSendDataType.saveLqMatchTeamStatistic){
			receiveAndSendDataService.saveLqMatchTeamStatistic(jsonObject);
		}else if(dataType == ReceiveAndSendDataType.saveLqJishiOdds){
			String lqMatch = request.getParameter("lqMatchBase");
			Qt_fb_match_oddsType oddsType = Qt_fb_match_oddsType.valueOf(request.getParameter("oddsType"));
			receiveAndSendDataService.saveLqJishiOdds(jsonObject,lqMatch,oddsType);
		}else if(dataType == ReceiveAndSendDataType.saveZqTelevisonLiveUrl){
			String id = request.getParameter("id");
			receiveAndSendDataService.saveZqMatchTelevisonUrl(id,jsonObject);
		}else if(dataType == ReceiveAndSendDataType.saveFbMatchOpOneCompany){
			String companyId = request.getParameter("companyId");
			Long bsId = Long.valueOf(request.getParameter("bsId"));
			receiveAndSendDataService.saveFbMatchOpOneCompany(jsonObject,companyId,bsId);
		}
		try {
			response.getWriter().write("ok");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			log.error("发送response响应数据时出错{}", e);
		}
		return null;
	}

	@RequestMapping(value = "/send")
	public String sendData(HttpServletRequest request,
			HttpServletResponse response) {
		String encodeString = "";
		ReceiveAndSendDataType dataType = ReceiveAndSendDataType.valueOf(request.getParameter(DATA_TYPE));
		if (dataType==ReceiveAndSendDataType.ZqJingcaiMatchInLive) {
			encodeString = receiveAndSendDataService.gotFbJingcaiMatchInLive();
		} else if (dataType == ReceiveAndSendDataType.fbSeasonMessSubLeague) {
			String source = request.getParameter("source");
			String leagueType = request.getParameter("leagueType");
			encodeString = receiveAndSendDataService.gotFbSeasonMessSubLeagueMess(
					source, leagueType);
		}else if(dataType == ReceiveAndSendDataType.queryAllSeasonMessNotCrawler){
			String source = request.getParameter("source");
			encodeString = receiveAndSendDataService.gotAllSeasonMessNotCrawler(source);
		}else if(dataType == ReceiveAndSendDataType.queryFbCupGroupMess){
			String source = request.getParameter("source");
			String cupType = request.getParameter("cupType");
			encodeString = receiveAndSendDataService.receiveFbCupGroupMess(source,cupType);
		}else if(dataType == ReceiveAndSendDataType.queryLeagueNowSeason){
			String leagueId = request.getParameter("leagueId");
			encodeString = receiveAndSendDataService.receiveFbLeagueNowSeason(leagueId);
		}else if(dataType == ReceiveAndSendDataType.queryZqMatchNotHaveLineup){
			encodeString = receiveAndSendDataService.receiveZqMatchNotHaveLineup();
		}else if(dataType == ReceiveAndSendDataType.queryFbLeagueSeasonNotHaveRule){
			String leagueType = request.getParameter("leagueType");
			encodeString = receiveAndSendDataService.queryFbLeagueSeasonNotHaveRule(leagueType);
		}else if(dataType == ReceiveAndSendDataType.queryAllCupSeasonNotCrawler){
			String source = request.getParameter("source");
			String leagueType = request.getParameter("leagueType");
			String isSubLeague = request.getParameter("isSubLeague");
			encodeString = receiveAndSendDataService.queryAllCupSeasonNotCrawler(leagueType,source,isSubLeague);
		}else if(dataType == ReceiveAndSendDataType.queryLqSubLeagueNotCrawler){
			String source = request.getParameter("source");
			String leagueType = request.getParameter("leagueType");
			String isSubLeague = request.getParameter("isSubLeague");
			encodeString = receiveAndSendDataService.queryLqSubLeague(leagueType,source,isSubLeague);
		}else if(dataType == ReceiveAndSendDataType.queryLqLeagueNotSubByType){
			String source = request.getParameter("source");
			String leagueType = request.getParameter("leagueType");
			encodeString = receiveAndSendDataService.queryLqLeagueNotSubByType(leagueType,source);
		}else if(dataType == ReceiveAndSendDataType.queryAllJingcaiBasketMathcInlive){
			encodeString = receiveAndSendDataService.queryAllJingcaiBasketMathcInlive();
		}else if(dataType == ReceiveAndSendDataType.queryAllJingcaiLqMatchNotStart){
			encodeString = receiveAndSendDataService.queryAllJingcaiLqMatchNotStart();
		}else if(dataType == ReceiveAndSendDataType.queryAllZqMatchInMatchNotHaveLiveUrl){
			encodeString = receiveAndSendDataService.queryAllZqMatchInMatchNotHaveLiveUrl();
		}else if(dataType == ReceiveAndSendDataType.queryJingcaiZqMatchNotStart){
			encodeString = receiveAndSendDataService.queryJingcaiZqMatchNotStart();
		}
		try {
			response.getWriter().write(encodeString);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			log.error("发送response响应数据时出错{}", e);
		}
		return null;
	}

	public ReceiveAndSendDataService getReceiveAndSendDataService() {
		return receiveAndSendDataService;
	}

	public void setReceiveAndSendDataService(
			ReceiveAndSendDataService receiveAndSendDataService) {
		this.receiveAndSendDataService = receiveAndSendDataService;
	}

}
