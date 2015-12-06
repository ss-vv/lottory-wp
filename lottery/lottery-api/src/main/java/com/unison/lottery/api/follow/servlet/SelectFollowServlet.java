package com.unison.lottery.api.follow.servlet;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;

import com.unison.lottery.api.follow.bo.SelectFollowBO;
import com.unison.lottery.api.framework.servlet.AbstractProcessServlet;
import com.unison.lottery.api.model.User;
import com.unison.lottery.api.protocol.common.MethodNames;
import com.unison.lottery.api.protocol.common.VONames;
import com.unison.lottery.api.protocol.response.model.HaveReturnStatusResponse;
import com.unison.lottery.api.protocol.response.model.SelectFollowResponse;

/**
 * @desc
 * @createTime 2012-11-30
 * @author lei.li@unison.net.cn
 * @version 1.0
 */
@WebServlet("/xml/security/selectFollow")
public class SelectFollowServlet extends AbstractProcessServlet {

	private static final long serialVersionUID = 1L;

	@Override
	protected String getMethodName() {
		return MethodNames.SELECTFOLLOW;
	}

	@Override
	protected String getResponseVOName() {
		return VONames.SELECTFOLLOW_RESPONSE_VO_NAME;
	}
	
	@Override
	protected HaveReturnStatusResponse process(HttpServletRequest request) {
		SelectFollowBO selectFollowBO = (SelectFollowBO) ctx.getBean("selectFollowBO");
		User user = (User) request.getAttribute(VONames.USER_AFTER_LOGIN_VO_NAME);
		
		String betSchemeIDStr = String.valueOf(request.getAttribute("betSchemeID"));
		String firstResultStr = String.valueOf(request.getAttribute("firstResult"));
		int startPosition = Integer.parseInt(firstResultStr);
		Long betSchemeID = Long.valueOf(betSchemeIDStr);
		String type = (String) request.getAttribute("type");
		
		SelectFollowResponse response = selectFollowBO.selectFollow(user, betSchemeID, startPosition, type);
		return response;
	}
}