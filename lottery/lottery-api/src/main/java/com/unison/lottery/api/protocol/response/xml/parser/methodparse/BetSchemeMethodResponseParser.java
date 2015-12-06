package com.unison.lottery.api.protocol.response.xml.parser.methodparse;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;

import com.unison.lottery.api.protocol.common.Constants;
import com.unison.lottery.api.protocol.common.MethodNames;
import com.unison.lottery.api.protocol.common.SystemStatusKeyNames;
import com.unison.lottery.api.protocol.common.VONames;
import com.unison.lottery.api.protocol.common.model.Result;
import com.unison.lottery.api.protocol.response.model.BetSchemeResponse;
import com.unison.lottery.api.protocol.response.model.IHaveReturnStatus;
import com.unison.lottery.api.protocol.response.model.Response;
import com.xhcms.lottery.commons.data.Account;
import com.xhcms.lottery.commons.data.BetScheme;
import com.xhcms.lottery.commons.data.User;

public class BetSchemeMethodResponseParser extends
		AbstractMethodResponseParser {

	@Override
	protected IHaveReturnStatus getResponseFromHttpRequest(
			HttpServletRequest httpRequest) {
		return (IHaveReturnStatus) httpRequest.getAttribute(VONames.BET_SCHEME_VO_NAME);
	}

	@Override
	protected void handle(Response resultResponse,
			Object responseFromHttpRequest) {
		BetSchemeResponse betSchemeResponse=(BetSchemeResponse) responseFromHttpRequest;
		
		if(null!=betSchemeResponse&&betSchemeResponse.getResultMap()!=null){
			Map<String,Object> resultMap = betSchemeResponse.getResultMap();
			BetScheme scheme = (BetScheme)resultMap.get(Constants.SCHEME);
			Account account = (Account)resultMap.get(Constants.ACCOUNT);
			User user = (User)resultMap.get("user");
			
			if(null != scheme && com.xhcms.lottery.lang.Constants.CQSS.equals(scheme.getLotteryId())) {
				resultResponse.currentIssueNumber = String.valueOf(resultMap.get(Constants.ISSUE_NUMBER));
				resultResponse.currentSalingIssueCountdownTime = String.valueOf(resultMap.get(Constants.COUNT_DOWN_TIME));
			}
			resultResponse.result=new Result();
			if(null != scheme) {
				if(user != null){
					resultResponse.result.nickName = user.getNickName();
					resultResponse.result.imageUrl = user.getHeadImageURL();
				}
				resultResponse.result.displayMode = (Integer) resultMap.get("displayMode");
				resultResponse.result.schemeId =scheme.getId();
				resultResponse.result.schemeStatus = scheme.getStatus();
				resultResponse.result.sponsor = scheme.getSponsor();
				resultResponse.result.betNote = scheme.getBetNote();
				resultResponse.result.multiple = scheme.getMultiple();
				String lotteryId = scheme.getLotteryId();
				if(StringUtils.isNotBlank(scheme.getLotteryId()) 
						&& !com.xhcms.lottery.lang.Constants.CQSS.equals(lotteryId)) {
					if(StringUtils.isNotBlank(scheme.getPassTypeIds())) {
						resultResponse.result.passType = scheme.getPassTypeIds().substring(1, scheme.getPassTypeIds().length()-1);
					}
				}
				resultResponse.result.maxBonus = scheme.getMaxBonus();
			}
			if(null != account) {
				resultResponse.result.blance = account.getFree();
				resultResponse.result.fund = account.getFund();
				resultResponse.result.frozenFund = account.getFrozenFund();
				resultResponse.result.frozenGrant = account.getFrozenGrant();
				resultResponse.result.grant = account.getGrant();
				resultResponse.result.totalBet = account.getTotalBet();
			}
			
//			for (Map<String,String> itemMap : bbMatchResponse.getResults()) {
//				Item resultItem = new Item();
//				String matchCode = itemMap.get(Constants.MATCH_CODE);
//				if(matchCode!=null){
//					resultItem.matchCode = matchCode;
//				}
//				String leagueShortName = itemMap.get(Constants.LEAGUE_SHORT_NAME);
//				if(leagueShortName!=null){
//					resultItem.leagueShortName = leagueShortName;
//				}
//				String leagueColor = itemMap.get(Constants.LEAGUE_COLOR);
//				if(leagueColor!=null){
//					resultItem.leagueColor = leagueColor;
//				}
//				String matchOffTime = itemMap.get(Constants.MATCH_OFF_TIME);
//				if(matchOffTime!=null){
//					resultItem.matchOffTime = matchOffTime;
//				}
//				String host = itemMap.get(Constants.HOST_NAME);
//				if(host!=null){
//					resultItem.host = host;
//				}
//				String guest = itemMap.get(Constants.GUEST_NAME);
//				if(guest!=null){
//					resultItem.guest = guest;
//				}
//				String odds = itemMap.get(Constants.ODDS);
//				if(odds!=null){
//					resultItem.odds = odds;
//				}
//				String options = itemMap.get(Constants.OPTIONS);
//				if(odds!=null){
//					resultItem.options = options;
//				}
//				String concedePoints = itemMap.get(Constants.CONCEDE_POINTS);
//				if(odds!=null){
//					resultItem.concedePoints = concedePoints;
//				}
//			
//				resultResponse.result.item.add(resultItem);
//			}
		}
		if(null != betSchemeResponse && null != betSchemeResponse.getReturnStatus()) {
			resultResponse.name = MethodNames.BET_SCHEME;
			resultResponse.desc = betSchemeResponse.getReturnStatus().getDescForClient();
			resultResponse.status = betSchemeResponse.getReturnStatus().getStatusCodeForClient();
		}
	}
	
	@Override
	protected String getDefaultReturnStatusKeyName() {
		return SystemStatusKeyNames.BetScheme.FAIL;
	}

}
