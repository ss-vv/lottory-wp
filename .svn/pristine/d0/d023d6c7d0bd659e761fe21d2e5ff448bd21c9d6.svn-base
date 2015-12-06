package com.unison.lottery.api.protocol.request.json.methodparser;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.unison.lottery.api.protocol.common.VONames;
import com.unison.lottery.api.protocol.request.xml.methodparser.AbstractRequestParserWithUserParser;
import com.unison.lottery.api.protocol.request.xml.model.MethodRequest;
import com.unison.lottery.weibo.data.MatchIdInfo;

/**
 * 即时指数初始化
 * @author baoxing.peng
 * @since 2015-03-24 13:41:02
 */
public class QueryImmediateIndexInfoRequestParser extends AbstractRequestParserWithUserParser {

	@Override
	protected boolean parseParamList(MethodRequest methodRequest,
			HttpServletRequest httpRequest) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	protected boolean parseExtendRequestParams(MethodRequest methodRequest,
			HttpServletRequest httpRequest) {
		String matchType = methodRequest.matchType;
		String time = methodRequest.time;
		Long matchId = methodRequest.matchId;
		String leagueShortName = methodRequest.leagueShortName;
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("matchType", Integer.valueOf(matchType));
		param.put("time", time);
		param.put("matchId", matchId);
		param.put("leagueShortName", leagueShortName);
		httpRequest.setAttribute(VONames.QUERY_IMMEDIATE_INDEX_INFO_REQUEST_VO_NAME, param);
		return true;
	}

	@Override
	protected boolean decideShouldParseParamList() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	protected boolean decideShouldParseExtendRequestParams() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	protected boolean decideShouldParseUser() {
		// TODO Auto-generated method stub
		return true;
	}

}
