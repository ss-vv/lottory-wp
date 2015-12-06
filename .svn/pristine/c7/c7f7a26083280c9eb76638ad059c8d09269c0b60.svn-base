package com.unison.lottery.api.pay.servlet;


import java.math.BigDecimal;
import java.util.Map;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;

import com.unison.lottery.api.framework.servlet.AbstractProcessServlet;
import com.unison.lottery.api.model.User;
import com.unison.lottery.api.pay.bo.RechargeCardBO;
import com.unison.lottery.api.protocol.common.MethodNames;
import com.unison.lottery.api.protocol.common.VONames;
import com.unison.lottery.api.protocol.response.model.HaveReturnStatusResponse;
import com.unison.lottery.api.protocol.response.model.RechargeCardResponse;





/**
 * Servlet implementation class RegisteWithSecurityCodeServlet
 */
@WebServlet("/xml/security/rechargeCard")
public class RechargeCardServlet extends AbstractProcessServlet {
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1085310951676364404L;
	/**
     * @see HttpServlet#HttpServlet()
     */
    public RechargeCardServlet() {
        super();
        
    }
	@Override
	protected String getMethodName() {
		return MethodNames.RECHARGE_CARD;
	}
	@Override
	protected String getResponseVOName() {
		return VONames.RECHARGE_CARD_RESPONSE_VO_NAME;
	}
	@Override
	protected
	HaveReturnStatusResponse process(HttpServletRequest request) {
		RechargeCardBO rechargeCardBO=(RechargeCardBO) ctx.getBean("rechargeCardBO");
		User user=(User) request.getAttribute(VONames.USER_AFTER_LOGIN_VO_NAME);
		@SuppressWarnings("unchecked")
		Map<String,Object> params=(Map<String, Object>) request.getAttribute(VONames.RECHARGE_CARD_REQUEST_VO_NAME);
		BigDecimal rechargeAmount=(BigDecimal) params.get("rechargeAmount");
		String rechargeCardType=(String) params.get("rechargeCardType");
		String rechargeCardNumber=(String) params.get("rechargeCardNumber");
		String rechargeCardPass=(String) params.get("rechargeCardPass");
		String voucherUserId=(String) params.get(com.unison.lottery.api.protocol.common.Constants.VOUCHER_USER_ID);
		RechargeCardResponse rechargeCardResponse=rechargeCardBO.recharge(user,rechargeAmount,rechargeCardType,rechargeCardNumber,rechargeCardPass, voucherUserId);
		return (HaveReturnStatusResponse)rechargeCardResponse;
	}

}
