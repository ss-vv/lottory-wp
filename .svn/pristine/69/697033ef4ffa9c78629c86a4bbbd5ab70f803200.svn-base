package com.unison.lottery.api.odds.servlet;

import java.util.Map;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;

import com.unison.lottery.api.framework.servlet.AbstractProcessServlet;
import com.unison.lottery.api.odds.bo.QueryImmediateIndexDetailsBO;
import com.unison.lottery.api.protocol.common.MethodNames;
import com.unison.lottery.api.protocol.common.VONames;
import com.unison.lottery.api.protocol.response.model.HaveReturnStatusResponse;

/**
 *
 * @author baoxing.peng
 * @since 2015年3月26日上午9:33:48
 */
@WebServlet("/json/queryImmediateIndexDetails")
public class QueryImmediateIndexDetailsServlet extends AbstractProcessServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4303594520704378187L;

	@Override
	protected String getMethodName() {
		return MethodNames.IMMEDIATE_INDEX_DETAILS;
	}

	@Override
	protected String getResponseVOName() {
		return VONames.QUERY_IMMEDIATE_INDEX_DETAILS_RESPONSE_VO_NAME;
	}

	@Override
	protected HaveReturnStatusResponse process(HttpServletRequest request) {
		QueryImmediateIndexDetailsBO queryImmediateIndexDetailsBO = (QueryImmediateIndexDetailsBO) ctx.getBean("queryImmediateIndexDetailsBO");
		Map<String, Object> params = (Map<String, Object>) request.getAttribute(VONames.QUERY_IMMEDIATE_INDEX_DETAILS_REQUEST_VO_NAME);
		Long matchId = (Long) params.get("matchId");
		String corpId = (String)params.get("corpId");
		String oddsType = (String)params.get("oddsType");
		String matchType = (String)params.get("matchType");
		return queryImmediateIndexDetailsBO.queryImmediateIndexDetails(matchId,corpId,oddsType,matchType);
	}

}
