package com.unison.lottery.api.query.servlet;

import java.util.Map;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;

import com.unison.lottery.api.framework.servlet.AbstractProcessServlet;
import com.unison.lottery.api.model.User;
import com.unison.lottery.api.protocol.common.MethodNames;
import com.unison.lottery.api.protocol.common.VONames;
import com.unison.lottery.api.protocol.response.model.HaveReturnStatusResponse;
import com.unison.lottery.api.query.bo.QueryBO;

/**
 * @desc 接收请求，用于查询可投注的传统足彩赛事
 * @createTime 2013-7-2
 * @author lei.li@unison.net.cn
 * @version 1.0
 */
@WebServlet("/xml/queryCTZCMatch")
public class QueryCTZCMatchServlet extends AbstractProcessServlet {

	private static final long serialVersionUID = 8981715718297792122L;

	@Override
	protected String getMethodName() {
		return MethodNames.QUERY_CTZC_MATCH;
	}

	@Override
	protected String getResponseVOName() {
		return VONames.QUERY_CTZC_MATCH_RESPONSE_VO_NAME;
	}

	@SuppressWarnings("unchecked")
	@Override
	protected HaveReturnStatusResponse process(HttpServletRequest request) {
		Map<String, String> paramMap = (Map<String, String>) request.getAttribute(VONames.QUERY_CTZC_MATCH_VO_NAME);
		User user=(User) request.getAttribute(VONames.USER_VO_NAME);
		QueryBO queryBO = (QueryBO) ctx.getBean("queryBO");
		return queryBO.queryCTZCMatch(paramMap,user);
	}
}
