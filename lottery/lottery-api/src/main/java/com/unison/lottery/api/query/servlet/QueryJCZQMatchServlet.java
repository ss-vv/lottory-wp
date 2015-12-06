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
import com.unison.lottery.api.query.bo.QueryBO;





/**
 * Servlet implementation class RegisteWithSecurityCodeServlet
 */
@WebServlet("/xml/queryJCZQMatch")
public class QueryJCZQMatchServlet extends AbstractProcessServlet {
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 8981715718297792122L;
	
	
	
	/**
     * @see HttpServlet#HttpServlet()
     */
    public QueryJCZQMatchServlet() {
        super();
        
    }
	@Override
	protected String getMethodName() {
		return MethodNames.QUERY_JCZQ_MATCH;
	}
	@Override
	protected String getResponseVOName() {
		return VONames.QUERY_JCZQ_MATCH_RESPONSE_VO_NAME;
	}
	@Override
	protected
	HaveReturnStatusResponse process(HttpServletRequest request) {
		
		Map<String,String>  paramMap = (Map<String,String>)request.getAttribute(VONames.QUERY_JCZQ_MATCH_VO_NAME);
		User user=(User) request.getAttribute(VONames.USER_VO_NAME);
		QueryBO queryBO=(QueryBO) ctx.getBean("queryBO");
		return queryBO.queryJCZQMatch(paramMap,user);
		
	}

}
