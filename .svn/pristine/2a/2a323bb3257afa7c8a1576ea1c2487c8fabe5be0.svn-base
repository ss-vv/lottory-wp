package com.unison.lottery.api.bindmobile.servlet;


import java.util.Map;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.unison.lottery.api.bindmobile.bo.BindMobileBO;
import com.unison.lottery.api.framework.servlet.AbstractProcessServlet;
import com.unison.lottery.api.model.User;
import com.unison.lottery.api.protocol.common.MethodNames;
import com.unison.lottery.api.protocol.common.VONames;
import com.unison.lottery.api.protocol.response.model.BindMobileResponse;
import com.unison.lottery.api.protocol.response.model.HaveReturnStatusResponse;
import com.unison.lottery.api.protocol.status.ReturnStatus;





/**
 * Servlet implementation class RegisteWithSecurityCodeServlet
 */
@WebServlet("/xml/security/bindMobile")
public class BindMobileServlet extends AbstractProcessServlet {
	private Logger logger = LoggerFactory.getLogger(getClass());
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1085310951676364404L;
	/**
     * @see HttpServlet#HttpServlet()
     */
    public BindMobileServlet() {
        super();
    }
	@Override
	protected String getMethodName() {
		return MethodNames.BIND_MOBILE;
	}
	@Override
	protected String getResponseVOName() {
		return VONames.BIND_MOBILE_RESPONSE_VO_NAME;
	}
	@Override
	protected
	HaveReturnStatusResponse process(HttpServletRequest request) {
		BindMobileBO bindMobileBO=(BindMobileBO) ctx.getBean("bindMobileBO");
		User user=(User) request.getAttribute(VONames.USER_AFTER_LOGIN_VO_NAME);
		@SuppressWarnings("unchecked")
		Map<String,String> params=(Map<String,String>) request.getAttribute(VONames.BIND_MOBILE_REQUEST_VO_NAME);
		String phoneNumber=params.get("phoneNumber");
		String verifyCode=params.get("verifyCode");
		BindMobileResponse bindMobileResponse = null;
		try{
			bindMobileResponse = bindMobileBO.bindMobileForUser(phoneNumber, verifyCode,user);
		}catch(Exception e){
			logger.error("绑定手机失败!", e);
			bindMobileResponse = new BindMobileResponse();
			ReturnStatus returnStatus = new ReturnStatus();
			returnStatus.setStatusCodeForClient("1041");
			returnStatus.setDescForClient("绑定手机号失败");
			returnStatus.setSystemKey("bind_mobile_fail");
			bindMobileResponse.setReturnStatus(returnStatus);
		}
		return (HaveReturnStatusResponse)bindMobileResponse;
	}

}
