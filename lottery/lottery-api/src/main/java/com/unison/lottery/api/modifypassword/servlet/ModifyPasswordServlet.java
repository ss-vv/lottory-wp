package com.unison.lottery.api.modifypassword.servlet;


import java.util.Map;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;

import com.unison.lottery.api.bindIDCard.bo.BindIDCardBO;
import com.unison.lottery.api.bindmobile.bo.BindMobileBO;
import com.unison.lottery.api.framework.servlet.AbstractProcessServlet;
import com.unison.lottery.api.login.bo.LoginBO;
import com.unison.lottery.api.login.service.ILoginService;
import com.unison.lottery.api.model.User;
import com.unison.lottery.api.modifypassword.bo.ModifyPasswordBO;

import com.unison.lottery.api.protocol.common.MethodNames;
import com.unison.lottery.api.protocol.common.VONames;
import com.unison.lottery.api.protocol.response.model.BindIDCardResponse;
import com.unison.lottery.api.protocol.response.model.BindMobileResponse;
import com.unison.lottery.api.protocol.response.model.HaveReturnStatusResponse;
import com.unison.lottery.api.protocol.response.model.LoginResponse;
import com.unison.lottery.api.protocol.response.model.ModifyPasswordResponse;





/**
 * Servlet implementation class RegisteWithSecurityCodeServlet
 */
@WebServlet("/xml/security/modifyPassword")
public class ModifyPasswordServlet extends AbstractProcessServlet {
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1085310951676364404L;
	/**
     * @see HttpServlet#HttpServlet()
     */
    public ModifyPasswordServlet() {
        super();
        
    }
	@Override
	protected String getMethodName() {
		return MethodNames.MODIFY_PASSWORD;
	}
	@Override
	protected String getResponseVOName() {
		return VONames.MODIFY_PASSWORD_RESPONSE_VO_NAME;
	}
	@Override
	protected
	HaveReturnStatusResponse process(HttpServletRequest request) {
		ModifyPasswordBO modifyPasswordBO=(ModifyPasswordBO) ctx.getBean("modifyPasswordBO");
		User user=(User) request.getAttribute(VONames.USER_AFTER_LOGIN_VO_NAME);
		@SuppressWarnings("unchecked")
		Map<String,String> params=(Map<String,String>) request.getAttribute(VONames.MODIFY_PASSWORD_REQUEST_VO_NAME);
		String oldPassword=params.get("oldPassword");
		String newPassword=params.get("newPassword");
		String type=params.get("type");
		ModifyPasswordResponse modifyPasswordResponse=modifyPasswordBO.modifyPasswordForUser(oldPassword, newPassword,user,type);
		return (HaveReturnStatusResponse)modifyPasswordResponse;
	}

}
