package com.unison.lottery.api.buyHistory.servlet;


import java.util.Map;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;

import com.unison.lottery.api.buyHistory.bo.BuyHistoryBO;
import com.unison.lottery.api.framework.servlet.AbstractProcessServlet;
import com.unison.lottery.api.model.User;
import com.unison.lottery.api.protocol.common.MethodNames;
import com.unison.lottery.api.protocol.common.VONames;
import com.unison.lottery.api.protocol.response.model.HaveReturnStatusResponse;

/**
 * Servlet implementation class RegisteWithSecurityCodeServlet
 * @author Wang Lei
 */
@WebServlet("/xml/security/buyHistory")
public class BuyHistoryServlet extends AbstractProcessServlet {
	
	private static final long serialVersionUID = 8981715718297792122L;
	
	/**
     * @see HttpServlet#HttpServlet()
     */
    public BuyHistoryServlet() {
        super();
    }
	@Override
	protected String getMethodName() {
		return MethodNames.BUY_HISTORY;
	}
	@Override
	protected String getResponseVOName() {
		return VONames.BUY_HISTORY_RESPONSE_VO_NAME;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	protected HaveReturnStatusResponse process(HttpServletRequest request) {
		User user=(User) request.getAttribute(VONames.USER_AFTER_LOGIN_VO_NAME);
		Map<String,String>  paramMap = (Map<String,String>)request.getAttribute(VONames.BUY_HISTORY_VO_NAME);
		BuyHistoryBO buyHistoryBO=(BuyHistoryBO) ctx.getBean("buyHistoryBO");
		return buyHistoryBO.buyHistory(paramMap,user.getId());
	}
}
