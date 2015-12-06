package com.unison.lottery.api.withdraw.servlet;


import java.math.BigDecimal;
import java.util.Map;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;

import com.unison.lottery.api.framework.servlet.AbstractProcessServlet;
import com.unison.lottery.api.model.User;
import com.unison.lottery.api.protocol.common.MethodNames;
import com.unison.lottery.api.protocol.common.VONames;
import com.unison.lottery.api.protocol.response.model.HaveReturnStatusResponse;
import com.unison.lottery.api.protocol.response.model.WithdrawResponse;
import com.unison.lottery.api.withdraw.bo.WithdrawBO;





/**
 * Servlet implementation class RegisteWithSecurityCodeServlet
 */
@WebServlet("/xml/security/withdraw")
public class WithdrawServlet extends AbstractProcessServlet {
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1085310951676364404L;
	/**
     * @see HttpServlet#HttpServlet()
     */
    public WithdrawServlet() {
        super();
        
    }
	@Override
	protected String getMethodName() {
		return MethodNames.WITHDRAW;
	}
	@Override
	protected String getResponseVOName() {
		return VONames.WITHDRAW_RESPONSE_VO_NAME;
	}
	@Override
	protected
	HaveReturnStatusResponse process(HttpServletRequest request) {
		WithdrawBO withdrawBO=(WithdrawBO) ctx.getBean("withdrawBO");
		User user=(User) request.getAttribute(VONames.USER_AFTER_LOGIN_VO_NAME);
		@SuppressWarnings("unchecked")
		Map<String,Object> params=(Map<String,Object>) request.getAttribute(VONames.WITHDRAW_REQUEST_VO_NAME);
		BigDecimal amount=(BigDecimal) params.get("amount");
		String withdrawPassword=(String) params.get("withdrawPassword");
		String realIP=(String) params.get("realIP");
		WithdrawResponse WithdrawResponse=withdrawBO.withdrawForUser(withdrawPassword, amount,realIP,user);
		return (HaveReturnStatusResponse)WithdrawResponse;
	}

}
