package com.unison.lottery.api.query.servlet;

import java.util.Map;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;

import com.unison.lottery.api.framework.servlet.AbstractProcessServlet;
import com.unison.lottery.api.model.User;
import com.unison.lottery.api.protocol.common.MethodNames;
import com.unison.lottery.api.protocol.common.VONames;
import com.unison.lottery.api.protocol.response.model.HaveReturnStatusResponse;
import com.unison.lottery.api.protocol.response.model.QueryOnSaleLotteryResponse;
import com.unison.lottery.api.protocol.response.model.QueryScoreLiveInfoResponse;
import com.unison.lottery.api.query.bo.QueryBO;
import com.unison.lottery.api.query.bo.QueryScoreLiveBO;

@WebServlet("/json/queryScoreLiveInfo")
public class QueryScoreLiveInfoServlet extends AbstractProcessServlet {
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 8981715718297792122L;
	
	
	
	/**
     * @see HttpServlet#HttpServlet()
     */
    public QueryScoreLiveInfoServlet() {
        super();
    }
	@Override
	protected String getMethodName() {
		return MethodNames.QUERY_SCORE_LIVE_INFO;
	}
	@Override
	protected String getResponseVOName() {
		return VONames.QUERY_SCORE_LIVE_RESPONSE_VO_NAME;
	}
	@Override
	protected
	@SuppressWarnings("unchecked")
	HaveReturnStatusResponse process(HttpServletRequest request) {
		Map<String, Object>  paramMap = (Map<String,Object>)request.getAttribute(VONames.QUERY_SCORE_LIVE_REQUEST_VO_NAME);
		User user=(User) request.getAttribute(VONames.USER_VO_NAME);
		QueryScoreLiveBO queryBO=(QueryScoreLiveBO) ctx.getBean("queryScoreLiveBO");
		QueryScoreLiveInfoResponse queryScoreLiveInfoResponse = queryBO.makeQueryScoreLiveInfoResponse(user, paramMap);
		return	queryScoreLiveInfoResponse; 
		
	}
}
