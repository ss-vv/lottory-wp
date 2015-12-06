package com.unison.lottery.api.pay.servlet;


import java.util.Map;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;

import com.unison.lottery.api.framework.servlet.AbstractProcessServlet;
import com.unison.lottery.api.model.User;
import com.unison.lottery.api.pay.bo.QueryAlipayRSAKeyBO;
import com.unison.lottery.api.protocol.common.MethodNames;
import com.unison.lottery.api.protocol.common.VONames;
import com.unison.lottery.api.protocol.response.model.HaveReturnStatusResponse;
import com.unison.lottery.api.protocol.response.model.QueryAlipayRSAKeyResponse;





/**
 * Servlet implementation class RegisteWithSecurityCodeServlet
 */
@WebServlet("/xml/security/queryAlipayRSAKey")
public class QueryAlipayRSAKeyServlet extends AbstractProcessServlet {
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1085310951676364404L;
	/**
     * @see HttpServlet#HttpServlet()
     */
    public QueryAlipayRSAKeyServlet() {
        super();
        
    }
	@Override
	protected String getMethodName() {
		return MethodNames.QUERY_ALIPAY_RSA_KEY;
	}
	@Override
	protected String getResponseVOName() {
		return VONames.QUERY_ALIPAY_RSA_KEY_RESPONSE_VO_NAME;
	}
	@Override
	protected
	HaveReturnStatusResponse process(HttpServletRequest request) {
		QueryAlipayRSAKeyBO queryAlipayRSAKeyBO=(QueryAlipayRSAKeyBO) ctx.getBean("queryAlipayRSAKeyBO");
		User user=(User) request.getAttribute(VONames.USER_AFTER_LOGIN_VO_NAME);
		@SuppressWarnings("unchecked")
		Map<String,Object>  paramMap =(Map<String,Object>) request.getAttribute(VONames.QUERY_ALIPAY_RSA_KEY_REQUEST_VO_NAME);
		
		QueryAlipayRSAKeyResponse queryAlipayRSAKeyResponse=queryAlipayRSAKeyBO.getRSAResultString(user,paramMap);
		return (HaveReturnStatusResponse)queryAlipayRSAKeyResponse;
	}

}
