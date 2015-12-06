package com.unison.lottery.api.userInfo.servlet;

import java.util.Map;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;

import com.unison.lottery.api.framework.servlet.AbstractProcessServlet;
import com.unison.lottery.api.protocol.common.MethodNames;
import com.unison.lottery.api.protocol.common.VONames;
import com.unison.lottery.api.protocol.response.model.HaveReturnStatusResponse;
import com.unison.lottery.api.protocol.response.model.QueryUserInfoResponse;
import com.unison.lottery.api.userInfo.bo.UserQueryInfoBO;

/**
 * 查询用户信息，用于同步用户账户信息
 * 
 * @author lei.li@davcai.com
 */
@WebServlet("/json/security/queryUserInfo")
public class QueryUserInfoServlet extends AbstractProcessServlet {

	private static final long serialVersionUID = 1085310951676364404L;

	public QueryUserInfoServlet() {
		super();
	}

	@Override
	protected String getMethodName() {
		return MethodNames.QUERY_USER_INFO;
	}

	@Override
	protected String getResponseVOName() {
		return VONames.QUERY_USER_INFO_RESPONSE_VO_NAME;
	}

	@SuppressWarnings("unchecked")
	@Override
	protected HaveReturnStatusResponse process(HttpServletRequest request) {
		UserQueryInfoBO userQueryInfo = (UserQueryInfoBO) ctx
				.getBean("userQueryInfoBO");
		Map<String, String> params = (Map<String, String>) request
				.getAttribute(VONames.QUERY_USER_INFO_REQUEST_VO_NAME);
		String validId = params.get("validId");
		QueryUserInfoResponse resp = userQueryInfo.query(validId);
		return (HaveReturnStatusResponse) resp;
	}
}