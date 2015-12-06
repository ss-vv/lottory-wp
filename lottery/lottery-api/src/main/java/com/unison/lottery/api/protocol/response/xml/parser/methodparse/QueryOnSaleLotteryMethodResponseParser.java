package com.unison.lottery.api.protocol.response.xml.parser.methodparse;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils; 
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.unison.lottery.api.framework.utils.ProtocolUtils;
import com.unison.lottery.api.lotteryInfo.util.LotteryNumberConvertor;
import com.unison.lottery.api.protocol.common.Constants;
import com.unison.lottery.api.protocol.common.SystemStatusKeyNames;
import com.unison.lottery.api.protocol.common.VONames;
import com.unison.lottery.api.protocol.common.model.Item;
import com.unison.lottery.api.protocol.common.model.Result;
import com.unison.lottery.api.protocol.common.model.ResultList;
import com.unison.lottery.api.protocol.response.model.IHaveReturnStatus;
import com.unison.lottery.api.protocol.response.model.QueryOnSaleLotteryResponse;
import com.unison.lottery.api.protocol.response.model.Response;
import com.xhcms.lottery.commons.persist.entity.ActivityPO;
import com.xhcms.lottery.commons.persist.service.MatchColorService;
import com.xhcms.lottery.conf.LeagueColorConf;

public class QueryOnSaleLotteryMethodResponseParser extends
		AbstractMethodResponseParser {

	@Autowired
 	private LeagueColorConf initLeagueName;
	
	@Autowired
	private MatchColorService matchColorServiceImpl;
	
	private Logger log = LoggerFactory.getLogger(getClass());
	
	@Override
	protected IHaveReturnStatus getResponseFromHttpRequest(
			HttpServletRequest httpRequest) {
		return (IHaveReturnStatus) httpRequest.getAttribute(VONames.QUERY_ONSALE_LOTTERY_RESPONSE_VO_NAME);
	}
	
	@Override
	protected void handle(Response resultResponse,
			Object responseFromHttpRequest) {
		QueryOnSaleLotteryResponse onSaleLotteryResponse=(QueryOnSaleLotteryResponse) responseFromHttpRequest;
		if(null!=onSaleLotteryResponse&&null!=onSaleLotteryResponse.getReturnStatus()){
			resultResponse.result=new Result();
			resultResponse.result.item = new ArrayList<Item>();
			for (Map<String,String> itemMap : onSaleLotteryResponse.getResults()) {
				Item resultItem = new Item();
				String lotteryId = itemMap.get(Constants.LOTTERY_ID);
				if(lotteryId!=null){
					resultItem.lotteryId = lotteryId;
				}
				String matchCount = itemMap.get(Constants.MATCH_COUNT);
				if(matchCount!=null){
					resultItem.matchCount = matchCount;
				}
				String issueNumber = itemMap.get(Constants.ISSUE_NUMBER);
				if(issueNumber!=null){
					resultItem.issueNumber = issueNumber;
				}
				if(com.xhcms.lottery.lang.Constants.CQSS.equals(lotteryId) ||
						com.xhcms.lottery.lang.Constants.JX11.equals(lotteryId) ||
						com.xhcms.lottery.lang.Constants.SSQ.equals(lotteryId) ||
						com.xhcms.lottery.lang.Constants.CTZC.equals(lotteryId)
						) {
					String countdownTime = itemMap.get(Constants.COUNT_DOWN_TIME);
					if(countdownTime!=null){
						resultItem.countdownTime = countdownTime;
					}
					resultItem.issueNumberQueue = itemMap.get(Constants.ISSUE_NUMBER_QUEUE);
					resultItem.timeIntervalQueue = itemMap.get(Constants.TIME_INTERVAL_QUEUE);
										
					String lastestBallotIssueNumber = itemMap.get(Constants.LATEST_BALLOT_ISSUE_NUMBER);
					String lastestBallotLotteryNumber = itemMap.get(Constants.LATEST_BALLOT_LOTTERY_NUMBER);
					if(null != lastestBallotIssueNumber) {
						resultItem.latestIssueNumber = lastestBallotIssueNumber;
						resultItem.latestLotteryNumber = LotteryNumberConvertor.convert(lotteryId, lastestBallotLotteryNumber);
					}
				}
				
				if(com.xhcms.lottery.lang.Constants.CTZC.equals(lotteryId)) {
					resultItem.playId = itemMap.get(Constants.PLAY_ID);
					resultItem.matchOffTime = itemMap.get(Constants.MATCH_OFF_TIME);
				}
				
				String leagueShortName = itemMap.get(Constants.LEAGUES);
				if(leagueShortName!=null){
					String[] leagues = leagueShortName.split(",");
					Map<String,String> leagueNameShortNameMap = initLeagueName.getLeagueNameShortNameMap();
					List<String> shortNameList = new ArrayList<String>();
					for (String leagueName : leagues) {
						String shortName = (String) leagueNameShortNameMap.get(leagueName);
						if (shortName == null) {
							initLeagueName.initLeagueName();//重新同步赛事颜色数据库
							leagueNameShortNameMap = initLeagueName.getLeagueNameShortNameMap();
							shortName = (String) leagueNameShortNameMap.get(leagueName);
							if (shortName == null) {
								if(!leagueNameShortNameMap.containsValue(leagueName)) {
									
									if (leagueName.length() > Constants.MAX_LEAGUE_SIZE) {
										shortName = leagueName.substring(0, Constants.MAX_LEAGUE_SIZE);
										//插入新赛事简称记录
										log.info("插入联赛色值记录：长名:{}, 短名:{}", leagueName, shortName);
										try{
											matchColorServiceImpl.saveMatchColor(leagueName, shortName, lotteryId, null);
										}
										catch(Exception e){
											e.printStackTrace();
										}
										
									} else {
										shortName = leagueName;
										log.info("插入联赛色值记录：长名:{}, 短名:{}", leagueName, shortName);
										try{
											matchColorServiceImpl.saveMatchColor(leagueName, shortName, lotteryId, null);
										}catch(Exception e){
											e.printStackTrace();
										}
									} 
									initLeagueName.initLeagueName();//重新同步赛事颜色数据库
								}
							}
						}
						shortNameList.add(shortName);
					}
					resultItem.leagueShortName = ProtocolUtils.listToString(shortNameList, Constants.COMMA_SEPARATOR);
				}
				resultResponse.result.item.add(resultItem);
				
			}
			//加上轮播返回值
			resultResponse.resultList = new ResultList();
			resultResponse.resultList.item = new ArrayList<Item>();
			resultResponse.resultList.name = "activityList";
			if(onSaleLotteryResponse.getActivityPOs() != null && onSaleLotteryResponse.getActivityPOs().size() > 0){
				resultResponse.resultList.banner = onSaleLotteryResponse.getActivityPOs().get(0).getClientVersion();
				Map<String, Integer> map = onSaleLotteryResponse.getMap();
				Map<String, Integer> mapBetType = onSaleLotteryResponse.getMapBetType();
				for(ActivityPO activityPO : onSaleLotteryResponse.getActivityPOs()){
					Item item = new Item();
					item.type = String.valueOf(activityPO.getType());
					item.title = activityPO.getTitle();
					item.imageUrl = activityPO.getImageURL();
					item.itemId = String.valueOf(activityPO.getId());
					if(StringUtils.equals("0", item.type)){//活动
						item.activityUrl = activityPO.getActivityURL();
					}
					if(StringUtils.equals("1", item.type)){//投注
						item.lotteryType = activityPO.getLotteryType();
					}
					if(StringUtils.equals("2", item.type) && StringUtils.isNumeric(activityPO.getSchemeId())){//方案
						item.schemeId = Long.valueOf(activityPO.getSchemeId());
						item.displayMode = map.get(activityPO.getSchemeId());
						item.betType = mapBetType.get(activityPO.getSchemeId());
					}
					resultResponse.resultList.item.add(item);
				}
			}
		}
	}

	@Override
	protected String getDefaultReturnStatusKeyName() {
		return SystemStatusKeyNames.QueryOnSaleLottery.SUCC;
	}
}