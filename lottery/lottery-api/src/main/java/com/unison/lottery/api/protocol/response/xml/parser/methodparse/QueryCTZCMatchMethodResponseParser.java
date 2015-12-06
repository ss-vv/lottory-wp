package com.unison.lottery.api.protocol.response.xml.parser.methodparse;

import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import com.unison.lottery.api.lang.DateUtil;
import com.unison.lottery.api.protocol.common.SystemStatusKeyNames;
import com.unison.lottery.api.protocol.common.VONames;
import com.unison.lottery.api.protocol.common.model.Item;
import com.unison.lottery.api.protocol.common.model.Result;
import com.unison.lottery.api.protocol.response.model.IHaveReturnStatus;
import com.unison.lottery.api.protocol.response.model.QueryCTZCMatchResponse;
import com.unison.lottery.api.protocol.response.model.Response;
import com.xhcms.lottery.commons.data.CTFBMatch;

public class QueryCTZCMatchMethodResponseParser extends
		AbstractMethodResponseParser {
	
	@Override
	protected IHaveReturnStatus getResponseFromHttpRequest(
			HttpServletRequest httpRequest) {
		return (IHaveReturnStatus) httpRequest
				.getAttribute(VONames.QUERY_CTZC_MATCH_RESPONSE_VO_NAME);
	}

	@Override
	protected void handle(Response resultResponse,
			Object responseFromHttpRequest) {
		QueryCTZCMatchResponse ctzcMatchResponse = (QueryCTZCMatchResponse) responseFromHttpRequest;
		if (null != ctzcMatchResponse
				&& null != ctzcMatchResponse.getReturnStatus()) {
			
			String countdownTime = ctzcMatchResponse.getCountdownTime();
			resultResponse.countdownTime = countdownTime;
			
			List<CTFBMatch> ctfbMatchs = ctzcMatchResponse.getCtfbMatchs();
			if(null != ctfbMatchs && ctfbMatchs.size() > 0) {
				resultResponse.result = new Result();
				resultResponse.result.item = new ArrayList<Item>();
				
				for(CTFBMatch ctfbMatch : ctfbMatchs) {
					Item resultItem = new Item();
					String matchId = String.valueOf(ctfbMatch.getMatchId());
					if (matchId != null) {
						resultItem.matchId = matchId;
					}
					String matchCode = ctfbMatch.getCode();
					if (matchCode != null) {
						resultItem.matchCode = matchCode;
					}
					String leagueShortName = ctfbMatch.getLeagueName();
					if (leagueShortName != null) {
						resultItem.leagueShortName = leagueShortName;
					}
					String leagueColor = ctfbMatch.getColor();
					if (leagueColor != null) {
						resultItem.leagueColor = leagueColor;
					}
					String matchOffTime = DateUtil.format(ctfbMatch.getOfftime(), null);
					if (matchOffTime != null) {
						resultItem.matchOffTime = matchOffTime;
					}
					String host = ctfbMatch.getHomeTeamName();
					if (host != null) {
						resultItem.host = host;
					}
					String guest = ctfbMatch.getGuestTeamName();
					if (guest != null) {
						resultItem.guest = guest;
					}
					String options = ctfbMatch.getOptions();
					if (options != null) {
						resultItem.options = options;
					}
					resultResponse.result.item.add(resultItem);
				}
			}
		}
	}

	@Override
	protected String getDefaultReturnStatusKeyName() {
		return SystemStatusKeyNames.QueryCTZCMatch.SUCC;
	}
}