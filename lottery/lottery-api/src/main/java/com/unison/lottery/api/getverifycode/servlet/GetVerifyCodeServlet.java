package com.unison.lottery.api.getverifycode.servlet;


import java.util.Map;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;

import com.unison.lottery.api.bindIDCard.bo.BindIDCardBO;
import com.unison.lottery.api.framework.servlet.AbstractProcessServlet;
import com.unison.lottery.api.getverifycode.bo.GetVerifyCodeBO;
import com.unison.lottery.api.login.bo.LoginBO;
import com.unison.lottery.api.login.service.ILoginService;
import com.unison.lottery.api.model.User;

import com.unison.lottery.api.protocol.common.MethodNames;
import com.unison.lottery.api.protocol.common.VONames;
import com.unison.lottery.api.protocol.response.model.BindIDCardResponse;
import com.unison.lottery.api.protocol.response.model.GetVerifyCodeResponse;
import com.unison.lottery.api.protocol.response.model.HaveReturnStatusResponse;
import com.unison.lottery.api.protocol.response.model.LoginResponse;





/**
 * Servlet implementation class RegisteWithSecurityCodeServlet
 */
@WebServlet("/xml/getVerifyCode")
public class GetVerifyCodeServlet extends AbstractProcessServlet {
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1085310951676364404L;
	/**
     * @see HttpServlet#HttpServlet()
     */
    public GetVerifyCodeServlet() {
        super();
        
    }
	@Override
	protected String getMethodName() {
		return MethodNames.GET_VERIFY_CODE;
	}
	@Override
	protected String getResponseVOName() {
		return VONames.GET_VERIFY_CODE_RESPONSE_VO_NAME;
	}
	@Override
	protected
	HaveReturnStatusResponse process(HttpServletRequest request) {
		GetVerifyCodeBO getVerifyCodeBO=(GetVerifyCodeBO) ctx.getBean("getVerifyCodeBO");
		User user=(User) request.getAttribute(VONames.USER_VO_NAME);
		@SuppressWarnings("unchecked")
		Map<String,Object> params= (Map<String, Object>) request.getAttribute(VONames.GET_VERIFY_CODE_REQUEST_VO_NAME);
		String phoneNumber=(String) params.get("phoneNumber");
		String type=(String) params.get("type");
		GetVerifyCodeResponse getVerifyCodeResponse=getVerifyCodeBO.getVerifyCode(phoneNumber,user,type);
		return (HaveReturnStatusResponse)getVerifyCodeResponse;
	}

}
