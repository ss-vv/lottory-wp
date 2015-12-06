package com.unison.lottery.api.protocol.response.xml.parser.methodparse;

import java.util.ArrayList;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import com.unison.lottery.api.protocol.common.Constants;
import com.unison.lottery.api.protocol.common.SystemStatusKeyNames;
import com.unison.lottery.api.protocol.common.VONames;
import com.unison.lottery.api.protocol.common.model.Item;
import com.unison.lottery.api.protocol.common.model.Result;
import com.unison.lottery.api.protocol.response.model.IHaveReturnStatus;
import com.unison.lottery.api.protocol.response.model.QueryJCZQMatchResponse;
import com.unison.lottery.api.protocol.response.model.Response;

public class QueryJCZQMatchMethodResponseParser extends
		AbstractMethodResponseParser {
	
	@Override
	protected IHaveReturnStatus getResponseFromHttpRequest(
			HttpServletRequest httpRequest) {
		return (IHaveReturnStatus) httpRequest.getAttribute(VONames.QUERY_JCZQ_MATCH_RESPONSE_VO_NAME);
	}

	@Override
	protected void handle(Response resultResponse,
			Object responseFromHttpRequest) {
		QueryJCZQMatchResponse fbMatchResponse=(QueryJCZQMatchResponse) responseFromHttpRequest;
		if(null!=fbMatchResponse&&null!=fbMatchResponse.getReturnStatus()){
			resultResponse.result=new Result();
			resultResponse.result.item = new ArrayList<Item>();
			for (Map<String,String> itemMap : fbMatchResponse.getResults()) {
				Item resultItem = new Item();
				
				String matchId = itemMap.get(Constants.MATCH_ID);
				if(matchId!=null){
					resultItem.matchId = matchId;
				}
				String matchCode = itemMap.get(Constants.MATCH_CODE);
				if(matchCode!=null){
					resultItem.matchCode = matchCode;
				}
				String leagueShortName = itemMap.get(Constants.LEAGUE_SHORT_NAME);
				if(leagueShortName!=null){
					resultItem.leagueShortName = leagueShortName;
				}
				String leagueColor = itemMap.get(Constants.LEAGUE_COLOR);
				if(leagueColor!=null){
					resultItem.leagueColor = leagueColor;
				}
				String matchOffTime = itemMap.get(Constants.MATCH_OFF_TIME);
				if(matchOffTime!=null){
					resultItem.matchOffTime = matchOffTime;
				}
				String host = itemMap.get(Constants.HOST_NAME);
				if(host!=null){
					resultItem.host = host;
				}
				String guest = itemMap.get(Constants.GUEST_NAME);
				if(guest!=null){
					resultItem.guest = guest;
				}
				String odds = itemMap.get(Constants.ODDS);
				if(odds!=null){
					resultItem.odds = odds;
				}
				String options = itemMap.get(Constants.OPTIONS);
				if(options!=null){
					resultItem.options = options;
				}
				String concedePoints = itemMap.get(Constants.CONCEDE_POINTS);
				if(concedePoints!=null){
					resultItem.concedePoints = concedePoints;
				}
				//江浩翔 2013-06-28 ，混合过关协议使用
				String playId = itemMap.get(Constants.PLAY_ID);
				if(playId != null){
					resultItem.playId = playId;
				}
				
				String entrustTime = itemMap.get(Constants.ENTRUST_DEADLINE);
				if(entrustTime != null){
					resultItem.entrust_time = entrustTime;
				}
				resultResponse.result.item.add(resultItem);
			}
			
				
			
		}
		

	}

	@Override
	protected String getDefaultReturnStatusKeyName() {
		return SystemStatusKeyNames.QueryJCLQMatch.SUCC;
	}

}
