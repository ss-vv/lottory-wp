package com.unison.lottery.api.schemeDetail.servlet;


import java.util.Map;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;

import com.unison.lottery.api.framework.servlet.AbstractProcessServlet;
import com.unison.lottery.api.model.User;
import com.unison.lottery.api.protocol.common.MethodNames;
import com.unison.lottery.api.protocol.common.VONames;
import com.unison.lottery.api.protocol.response.model.HaveReturnStatusResponse;
import com.unison.lottery.api.schemeDetail.bo.SchemeDetailBO;





/**
 * Servlet implementation class RegisteWithSecurityCodeServlet
 */
@WebServlet("/xml/security/schemeTicket")
public class SchemeTicketServlet extends AbstractProcessServlet {
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -666524087840466789L;

	/**
     * @see HttpServlet#HttpServlet()
     */
    public SchemeTicketServlet() {
        super();
        
    }
	@Override
	protected String getMethodName() {
		return MethodNames.SCHEME_TICKET;
	}
	@Override
	protected String getResponseVOName() {
		return VONames.SCHEME_TICKET_VO_NAME;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	protected
	HaveReturnStatusResponse process(HttpServletRequest request) {
		User user=(User) request.getAttribute(VONames.USER_AFTER_LOGIN_VO_NAME);
		Map<String,String>  paramMap = (Map<String,String>)request.getAttribute(VONames.SCHEME_TICKET_VO_NAME);
		SchemeDetailBO schemeDetailBO=(SchemeDetailBO) ctx.getBean("schemeDetailBO");
		return schemeDetailBO.schemeTicket(Long.parseLong(user.getId()), paramMap);
	}

}
