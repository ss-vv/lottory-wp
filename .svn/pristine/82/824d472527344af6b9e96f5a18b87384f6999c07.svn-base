package com.unison.lottery.api.login.servlet;


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





/**
 * Servlet implementation class RegisteWithSecurityCodeServlet
 */
@WebServlet("/xml/login")
public class LoginServlet extends AbstractProcessServlet {
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1085310951676364404L;
	/**
     * @see HttpServlet#HttpServlet()
     */
    public LoginServlet() {
        super();
        
    }
	@Override
	protected String getMethodName() {
		return MethodNames.LOGIN;
	}
	@Override
	protected String getResponseVOName() {
		return VONames.LOGIN_RESPONSE_VO_NAME;
	}
	@Override
	protected
	HaveReturnStatusResponse process(HttpServletRequest request) {
		LoginBO loginBO=(LoginBO) ctx.getBean("loginBO");
		User user=(User) request.getAttribute(VONames.USER_VO_NAME);
		LoginResponse loginResponse=loginBO.loginWithNameAndPassword(user);
		return (HaveReturnStatusResponse)loginResponse;
	}

}
