package com.unison.lottery.api.redEnvalope.servlet;

import java.util.Map;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.unison.lottery.api.framework.servlet.AbstractProcessServlet;
import com.unison.lottery.api.model.User;
import com.unison.lottery.api.protocol.common.MethodNames;
import com.unison.lottery.api.protocol.common.VONames;
import com.unison.lottery.api.protocol.response.model.HaveReturnStatusResponse;
import com.unison.lottery.api.redEnvalope.bo.SendRedEnvalopeBo;

@WebServlet("/json/security/sendRedEnvalope")
public class SendRedEnvalopeServlet extends AbstractProcessServlet{

	/**
	 * 
	 */
	private static final long serialVersionUID = -6171760410759037753L;
	
	
	private Logger logger=LoggerFactory.getLogger(getClass());
	@Override
	protected String getMethodName() {
		return MethodNames.SEND_RED_ENVALOPE;
	}

	@Override
	protected String getResponseVOName() {
		return VONames.SEND_RED_ENVALOPE_RESPONSE_VO_NAME;
	}
	
	@Override
	protected HaveReturnStatusResponse process(HttpServletRequest request) {
		User user = (User) request.getAttribute(VONames.USER_AFTER_LOGIN_VO_NAME);
		Map<String, Object> paramMap = (Map<String, Object>) request.getAttribute(VONames.SEND_RED_ENVALOPE_REQUEST_VO_NAME);
		Long redEnvalopeAmount = (Long) paramMap.get("redEnvalopeAmount");
		Integer envalopeNum = (Integer) paramMap.get("envalopeNum");
		String groupId = (String)paramMap.get("groupId");
		String hxUserName = (String)paramMap.get("hxUserName");
		//祝福语
		String greetings = (String) paramMap.get("greetings");
		
		SendRedEnvalopeBo sendRedEnvalopeBo = (SendRedEnvalopeBo) ctx.getBean("sendRedEnvalopeBo");
		return sendRedEnvalopeBo.sendRedEnvalope(redEnvalopeAmount,envalopeNum,user,groupId,hxUserName,greetings);

	}

}
