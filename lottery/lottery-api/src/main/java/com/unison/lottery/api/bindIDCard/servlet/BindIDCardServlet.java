package com.unison.lottery.api.bindIDCard.servlet;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;

import com.unison.lottery.api.bindIDCard.bo.BindIDCardBO;
import com.unison.lottery.api.framework.servlet.AbstractProcessServlet;
import com.unison.lottery.api.model.User;
import com.unison.lottery.api.protocol.common.MethodNames;
import com.unison.lottery.api.protocol.common.VONames;
import com.unison.lottery.api.protocol.response.model.BindIDCardResponse;
import com.unison.lottery.api.protocol.response.model.HaveReturnStatusResponse;

@WebServlet("/xml/security/bindIDCard")
public class BindIDCardServlet extends AbstractProcessServlet {

	private static final long serialVersionUID = 1085310951676364404L;

	public BindIDCardServlet() {
		super();
	}

	@Override
	protected String getMethodName() {
		return MethodNames.BIND_IDCARD;
	}

	@Override
	protected String getResponseVOName() {
		return VONames.BIND_IDCARD_RESPONSE_VO_NAME;
	}

	@Override
	protected HaveReturnStatusResponse process(HttpServletRequest request) {
		BindIDCardBO bindIDCardBO = (BindIDCardBO) ctx.getBean("bindIDCardBO");
		User user = (User) request
				.getAttribute(VONames.USER_AFTER_LOGIN_VO_NAME);
		String IDCard = (String) request
				.getAttribute(VONames.BIND_IDCARD_REQUEST_VO_NAME);
		BindIDCardResponse bindIDCardResponse = bindIDCardBO.bindIDCardForUser(
				IDCard, user);
		return (HaveReturnStatusResponse) bindIDCardResponse;
	}
}
