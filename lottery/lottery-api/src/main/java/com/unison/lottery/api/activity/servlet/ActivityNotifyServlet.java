package com.unison.lottery.api.activity.servlet;


import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;

import com.unison.lottery.api.activity.bo.ActivityNotifyBO;
import com.unison.lottery.api.checkupdate.bo.CheckUpdateBO;
import com.unison.lottery.api.framework.servlet.AbstractProcessServlet;
import com.unison.lottery.api.login.bo.LoginBO;
import com.unison.lottery.api.login.service.ILoginService;
import com.unison.lottery.api.model.User;
import com.unison.lottery.api.protocol.common.MethodNames;
import com.unison.lottery.api.protocol.common.VONames;
import com.unison.lottery.api.protocol.response.model.ActivityNotifyResponse;
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
@WebServlet("/xml/activityNotify")
public class ActivityNotifyServlet extends AbstractProcessServlet {
	
	
	
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -788622847015462553L;
	/**
     * @see HttpServlet#HttpServlet()
     */
    public ActivityNotifyServlet() {
        super();
        
    }
	@Override
	protected String getMethodName() {
		return MethodNames.ACTIVITY_NOTIFY;
	}
	@Override
	protected String getResponseVOName() {
		return VONames.ACTIVITY_NOTIFY_RESPONSE_VO_NAME;
	}
	@Override
	protected
	HaveReturnStatusResponse process(HttpServletRequest request) {
		ActivityNotifyBO activityNotifyBO=(ActivityNotifyBO) ctx.getBean("activityNotifyBO");
		
		String firstResult=(String) request.getAttribute(VONames.ACTIVITY_NOTIFY_REQUEST_VO_NAME);
		ActivityNotifyResponse activityNotifyResponse=activityNotifyBO.listActivityNotify(firstResult);
		return (HaveReturnStatusResponse)activityNotifyResponse;
	}

}
