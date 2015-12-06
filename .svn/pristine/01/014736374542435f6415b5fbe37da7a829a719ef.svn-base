package com.unison.lottery.api.odds.servlet;

import java.util.Map;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;

import com.unison.lottery.api.framework.servlet.AbstractProcessServlet;
import com.unison.lottery.api.odds.bo.QueryImmediateIndexInfoBO;
import com.unison.lottery.api.protocol.common.MethodNames;
import com.unison.lottery.api.protocol.common.VONames;
import com.unison.lottery.api.protocol.response.model.HaveReturnStatusResponse;
import com.unison.lottery.api.protocol.response.model.QueryImmediateIndexInfoResponse;

/**
 *
 * @author baoxing.peng
 * @since 2015年3月24日下午2:15:34
 */
@WebServlet("/json/queryImmediateIndexInfo")
public class QueryImmediateIndexInfoServlet extends AbstractProcessServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6683636060055970134L;

	@Override
	protected String getMethodName() {
		
		return MethodNames.IMMEDIATE_INDEX_INFO;
	}

	protected String getResponseVOName() {
		// TODO Auto-generated method stub
		return VONames.QUERY_IMMEDIATE_INDEX_INFO_RESPONSE_VO_NAME;
	}

	@Override
	protected HaveReturnStatusResponse process(HttpServletRequest request) {
		QueryImmediateIndexInfoBO immediateIndexInfoBO = (QueryImmediateIndexInfoBO) ctx.getBean("queryImmediateIndexInfoBO");
		Map<String, Object> parMap = (Map<String, Object>) request.getAttribute(VONames.QUERY_IMMEDIATE_INDEX_INFO_REQUEST_VO_NAME);
		Integer matchType = (Integer) parMap.get("matchType");
		String time = (String) parMap.get("time");
		Long matchId = (Long)parMap.get("matchId");
		String leagueShortName = (String)parMap.get("leagueShortName");
		QueryImmediateIndexInfoResponse indexInfoReponse = immediateIndexInfoBO.queryOddsDataByMatchType(matchType,time,matchId,leagueShortName);
		return indexInfoReponse;
	}

}
