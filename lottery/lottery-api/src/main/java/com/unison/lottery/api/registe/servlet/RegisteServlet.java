package com.unison.lottery.api.registe.servlet;


import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;

import com.unison.lottery.api.framework.servlet.AbstractProcessServlet;
import com.unison.lottery.api.login.bo.LoginBO;
import com.unison.lottery.api.login.service.ILoginService;
import com.unison.lottery.api.model.User;
import com.unison.lottery.api.protocol.common.MethodNames;
import com.unison.lottery.api.protocol.common.VONames;
import com.unison.lottery.api.protocol.response.model.HaveReturnStatusResponse;
import com.unison.lottery.api.protocol.response.model.LoginResponse;
import com.unison.lottery.api.protocol.response.model.RegisteResponse;
import com.unison.lottery.api.registe.bo.RegisteBO;





/**
 * Servlet implementation class RegisteWithSecurityCodeServlet
 */
@WebServlet("/xml/registe")
public class RegisteServlet extends AbstractProcessServlet {
	
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -4606534234746250187L;
	/**
     * @see HttpServlet#HttpServlet()
     */
    public RegisteServlet() {
        super();
        
    }
	@Override
	protected String getMethodName() {
		return MethodNames.REGISTE;
	}
	@Override
	protected String getResponseVOName() {
		return VONames.REGISTE_RESPONSE_VO_NAME;
	}
	@Override
	protected
	HaveReturnStatusResponse process(HttpServletRequest request) {
		RegisteBO registeBO=(RegisteBO) ctx.getBean("registeBO");
		User user=(User) request.getAttribute(VONames.USER_VO_NAME);
		RegisteResponse registeResponse=registeBO.regist(user);
		return (HaveReturnStatusResponse)registeResponse;
	}

}
