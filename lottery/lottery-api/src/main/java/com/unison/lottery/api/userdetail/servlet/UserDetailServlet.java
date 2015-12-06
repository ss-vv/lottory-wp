package com.unison.lottery.api.userdetail.servlet;


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

import com.unison.lottery.api.protocol.common.MethodNames;
import com.unison.lottery.api.protocol.common.VONames;
import com.unison.lottery.api.protocol.response.model.BindIDCardResponse;
import com.unison.lottery.api.protocol.response.model.BindMobileResponse;
import com.unison.lottery.api.protocol.response.model.HaveReturnStatusResponse;
import com.unison.lottery.api.protocol.response.model.LoginResponse;
import com.unison.lottery.api.protocol.response.model.UserDetailResponse;
import com.unison.lottery.api.userdetail.bo.UserDetailBO;





/**
 * Servlet implementation class RegisteWithSecurityCodeServlet
 */
@WebServlet("/xml/security/userDetail")
public class UserDetailServlet extends AbstractProcessServlet {
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1085310951676364404L;
	/**
     * @see HttpServlet#HttpServlet()
     */
    public UserDetailServlet() {
        super();
        
    }
	@Override
	protected String getMethodName() {
		return MethodNames.USER_DETAIL;
	}
	@Override
	protected String getResponseVOName() {
		return VONames.USER_DETAIL_RESPONSE_VO_NAME;
	}
	@Override
	protected
	HaveReturnStatusResponse process(HttpServletRequest request) {
		UserDetailBO userDetailBO=(UserDetailBO) ctx.getBean("userDetailBO");
		User user=(User) request.getAttribute(VONames.USER_AFTER_LOGIN_VO_NAME);
		@SuppressWarnings("unchecked")
		Map<String,String> params=(Map<String,String>) request.getAttribute(VONames.USER_DETAIL_REQUEST_VO_NAME);
		String type=params.get("type");
		String firstResult=params.get("firstResult");
		UserDetailResponse userDetailResponse=userDetailBO.showUserDetail(user, type,Integer.parseInt(firstResult));
		return (HaveReturnStatusResponse)userDetailResponse;
	}

}
