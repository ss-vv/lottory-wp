package com.unison.lottery.api.bet.servlet;


import java.util.Map;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;

import com.unison.lottery.api.bet.bo.BetSchemeBO;
import com.unison.lottery.api.framework.servlet.AbstractProcessServlet;
import com.unison.lottery.api.model.User;
import com.unison.lottery.api.protocol.common.MethodNames;
import com.unison.lottery.api.protocol.common.VONames;
import com.unison.lottery.api.protocol.response.model.HaveReturnStatusResponse;





/**
 * Servlet implementation class RegisteWithSecurityCodeServlet
 */
@WebServlet("/xml/security/betScheme")
public class BetSchemeServlet extends AbstractProcessServlet {
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 8981715718297792122L;
	
	
	
	/**
     * @see HttpServlet#HttpServlet()
     */
    public BetSchemeServlet() {
        super();
        
    }
	@Override
	protected String getMethodName() {
		return MethodNames.BET_SCHEME;
	}
	@Override
	protected String getResponseVOName() {
		return VONames.BET_SCHEME_VO_NAME;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	protected
	HaveReturnStatusResponse process(HttpServletRequest request) {
		User user=(User) request.getAttribute(VONames.USER_AFTER_LOGIN_VO_NAME);
		Map<String,String>  paramMap = (Map<String,String>)request.getAttribute(VONames.BET_SCHEME_VO_NAME);

		BetSchemeBO betSchemeBO=(BetSchemeBO) ctx.getBean("betSchemeBO");
		return betSchemeBO.bet(Long.parseLong(user.getId()),paramMap,user.getChannel());
//		return null;
	}

}
