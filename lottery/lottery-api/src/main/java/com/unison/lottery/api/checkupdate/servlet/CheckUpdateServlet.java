package com.unison.lottery.api.checkupdate.servlet;


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
import com.unison.lottery.api.registe.bo.RegisteBO;





/**
 * Servlet implementation class RegisteWithSecurityCodeServlet
 */
@WebServlet("/xml/checkUpdate")
public class CheckUpdateServlet extends AbstractProcessServlet {
	
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -4606534234746250187L;
	/**
     * @see HttpServlet#HttpServlet()
     */
    public CheckUpdateServlet() {
        super();
        
    }
	@Override
	protected String getMethodName() {
		return MethodNames.CHECK_UPDATE;
	}
	@Override
	protected String getResponseVOName() {
		return VONames.CHECK_UPDATE_RESPONSE_VO_NAME;
	}
	@Override
	protected
	HaveReturnStatusResponse process(HttpServletRequest request) {
		CheckUpdateBO checkUpdateBO=(CheckUpdateBO) ctx.getBean("checkUpdateBO");
		User user=(User) request.getAttribute(VONames.USER_VO_NAME);
		CheckUpdateResponse checkUpdateResponse=checkUpdateBO.checkUpdate(user);
		return (HaveReturnStatusResponse)checkUpdateResponse;
	}

}
