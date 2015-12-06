package com.unison.lottery.api.forgotpassword.servlet;


import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.unison.lottery.api.forgotpassword.bo.ForgotPasswordBO;
import com.unison.lottery.api.framework.servlet.AbstractProcessServlet;
import com.unison.lottery.api.model.User;

import com.unison.lottery.api.protocol.common.MethodNames;
import com.unison.lottery.api.protocol.common.SystemStatusKeyNames;
import com.unison.lottery.api.protocol.common.VONames;
import com.unison.lottery.api.protocol.response.model.ForgotPasswordResponse;
import com.unison.lottery.api.protocol.response.model.HaveReturnStatusResponse;
import com.unison.lottery.api.protocol.status.IStatusRepository;
import com.unison.lottery.api.protocol.status.ReturnStatus;
import com.xhcms.lottery.commons.persist.service.VerifyException;
import com.xhcms.ucenter.lang.AppCode;


/**
 * Servlet implementation class RegisteWithSecurityCodeServlet
 */
@WebServlet("/xml/forgotPassword")
public class ForgotPasswordServlet extends AbstractProcessServlet {
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1085310951676364404L;
	/**
     * @see HttpServlet#HttpServlet()
     */
    public ForgotPasswordServlet() {
        super();
        
    }
	@Override
	protected String getMethodName() {
		return MethodNames.FORGOT_PASSWORD;
	}
	@Override
	protected String getResponseVOName() {
		return VONames.FORGOT_PASSWORD_RESPONSE_VO_NAME;
	}
	
	private Logger logger = LoggerFactory.getLogger(getClass());
	
	@Override
	protected
	HaveReturnStatusResponse process(HttpServletRequest request) {
		ForgotPasswordBO forgotPasswordBO=(ForgotPasswordBO) ctx.getBean("forgotPasswordBO");
		User user=(User) request.getAttribute(VONames.USER_VO_NAME);
		String verifyCode= (String) request.getAttribute(VONames.FORGOT_PASSWORD_REQUEST_VO_NAME);
		
		ForgotPasswordResponse forgotPasswordResponse = new ForgotPasswordResponse();
		IStatusRepository statusRepository = ctx.getBean("statusRepository",IStatusRepository.class);
		ReturnStatus failStatus = statusRepository
				.getSystemStatusBySystemKey(SystemStatusKeyNames.ForgotPassword.FAIL);
		forgotPasswordResponse.setReturnStatus(failStatus);
	
		try{
			forgotPasswordResponse = forgotPasswordBO.resetPassword(verifyCode,user);
		}
		catch(VerifyException ve){
			if(AppCode.VERIFY_AFTER_EXPIRETIME==ve.getErrorCode()
					||AppCode.VERIFY_INVALID_CODE==ve.getErrorCode()
					||AppCode.VERIFY_CODE_NULL==ve.getErrorCode()){
				ReturnStatus verifyCodeIsWrongStatus=statusRepository.getSystemStatusBySystemKey(SystemStatusKeyNames.ForgotPassword.VERIFY_CODE_IS_WRONG);
				forgotPasswordResponse.setReturnStatus(verifyCodeIsWrongStatus);
			}
			else if(AppCode.VERIFY_MOBILE_IS_BIND==ve.getErrorCode()){
				ReturnStatus phoneNumberIsBindToOtherStatus=statusRepository.getSystemStatusBySystemKey(SystemStatusKeyNames.ForgotPassword.PHONE_NUMBER_IS_BIND_TO_OTHER);
				forgotPasswordResponse.setReturnStatus(phoneNumberIsBindToOtherStatus);
			}
			else{
				logger.error("重置密码时出现异常:{}",ve.getMessage());
				forgotPasswordResponse.setReturnStatus(failStatus);
			}
		}
		catch(Exception e){
			e.printStackTrace();
			logger.error("重置密码时出现异常:{}",e.getMessage());
			forgotPasswordResponse.setReturnStatus(failStatus);
		}
		return (HaveReturnStatusResponse)forgotPasswordResponse;
	}

}
