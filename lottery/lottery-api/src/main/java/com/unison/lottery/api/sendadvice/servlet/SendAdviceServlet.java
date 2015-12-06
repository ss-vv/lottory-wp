package com.unison.lottery.api.sendadvice.servlet;


import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;

import com.unison.lottery.api.checkupdate.bo.CheckUpdateBO;
import com.unison.lottery.api.framework.servlet.AbstractProcessServlet;
import com.unison.lottery.api.login.bo.LoginBO;
import com.unison.lottery.api.login.service.ILoginService;
import com.unison.lottery.api.model.User;
import com.unison.lottery.api.protocol.common.MethodNames;
import com.unison.lottery.api.protocol.common.VONames;
import com.unison.lottery.api.protocol.response.model.CheckUpdateResponse;
import com.unison.lottery.api.protocol.response.model.HaveReturnStatusResponse;
import com.unison.lottery.api.protocol.response.model.LoginResponse;
import com.unison.lottery.api.protocol.response.model.RegisteResponse;
import com.unison.lottery.api.protocol.response.model.SendAdviceResponse;
import com.unison.lottery.api.registe.bo.RegisteBO;
import com.unison.lottery.api.sendadvice.bo.SendAdviceBO;





/**
 * Servlet implementation class RegisteWithSecurityCodeServlet
 */
@WebServlet("/xml/sendAdvice")
public class SendAdviceServlet extends AbstractProcessServlet {
	
	
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 4009374851638567746L;
	/**
     * @see HttpServlet#HttpServlet()
     */
    public SendAdviceServlet() {
        super();
        
    }
	@Override
	protected String getMethodName() {
		return MethodNames.SEND_ADVICE;
	}
	@Override
	protected String getResponseVOName() {
		return VONames.SEND_ADVICE_RESPONSE_VO_NAME;
	}
	@Override
	protected
	HaveReturnStatusResponse process(HttpServletRequest request) {
		SendAdviceBO sendAdviceBO=(SendAdviceBO) ctx.getBean("sendAdviceBO");
		User user=(User) request.getAttribute(VONames.USER_VO_NAME);
		String advice=(String) request.getAttribute(VONames.SEND_ADVICE_REQUEST_VO_NAME);
		SendAdviceResponse sendAdviceResponse=sendAdviceBO.sendAdvice(user,advice);
		return (HaveReturnStatusResponse)sendAdviceResponse;
	}

}
