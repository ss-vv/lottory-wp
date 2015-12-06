package com.unison.lottery.api.bindbank.servlet;


import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;

import com.unison.lottery.api.bindbank.bo.BindBankBO;
import com.unison.lottery.api.framework.servlet.AbstractProcessServlet;
import com.unison.lottery.api.model.User;
import com.unison.lottery.api.protocol.common.MethodNames;
import com.unison.lottery.api.protocol.common.VONames;
import com.unison.lottery.api.protocol.response.model.BindBankResponse;
import com.unison.lottery.api.protocol.response.model.HaveReturnStatusResponse;
import com.xhcms.lottery.commons.data.Account;

/**
 * Servlet implementation class RegisteWithSecurityCodeServlet
 */
@WebServlet("/xml/security/bindBank")
public class BindBankServlet extends AbstractProcessServlet {
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1085310951676364404L;
	/**
     * @see HttpServlet#HttpServlet()
     */
    public BindBankServlet() {
        super();
        
    }
	@Override
	protected String getMethodName() {
		return MethodNames.BIND_BANK;
	}
	@Override
	protected String getResponseVOName() {
		return VONames.BIND_BANK_RESPONSE_VO_NAME;
	}
	@Override
	protected
	HaveReturnStatusResponse process(HttpServletRequest request) {
		BindBankBO bindBankBO=(BindBankBO) ctx.getBean("bindBankBO");
		User user=(User) request.getAttribute(VONames.USER_AFTER_LOGIN_VO_NAME);
		Account account=(Account) request.getAttribute(VONames.BIND_BANK_REQUEST_VO_NAME);
		BindBankResponse bindBankResponse=bindBankBO.bindBankForUser(account, user);
		return (HaveReturnStatusResponse)bindBankResponse;
	}

}
