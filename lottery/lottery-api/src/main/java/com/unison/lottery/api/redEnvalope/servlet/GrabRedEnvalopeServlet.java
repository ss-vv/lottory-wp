package com.unison.lottery.api.redEnvalope.servlet;

import java.util.Map;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;

import com.unison.lottery.api.framework.servlet.AbstractProcessServlet;
import com.unison.lottery.api.model.User;
import com.unison.lottery.api.protocol.common.MethodNames;
import com.unison.lottery.api.protocol.common.VONames;
import com.unison.lottery.api.protocol.response.model.HaveReturnStatusResponse;
import com.unison.lottery.api.redEnvalope.bo.GrabRedEnvalopeBo;

@WebServlet("/json/security/grabRedEnvalope")
public class GrabRedEnvalopeServlet extends AbstractProcessServlet{

	/**
	 * 
	 */
	private static final long serialVersionUID = -6171760410759037753L;

	@Override
	protected String getMethodName() {
		return MethodNames.GRAB_RED_ENVALOPE;
	}

	@Override
	protected String getResponseVOName() {
		return VONames.GRAB_RED_ENVALOPE_RESPONSE_VO_NAME;
	}

	@Override
	protected HaveReturnStatusResponse process(HttpServletRequest request) {
		User user = (User) request.getAttribute(VONames.USER_AFTER_LOGIN_VO_NAME);
		Map<String, Object> paramMap = (Map<String, Object>) request.getAttribute(VONames.GRAB_RED_ENVALOPE_REQUEST_VO_NAME);
		Long envalopeId = (Long) paramMap.get("envalopeId");
		GrabRedEnvalopeBo grabRedEnvalopeBo = (GrabRedEnvalopeBo) ctx.getBean("grabRedEnvalopeBo");
		return grabRedEnvalopeBo.grabRedEnvalope(envalopeId,user);
	}

}
