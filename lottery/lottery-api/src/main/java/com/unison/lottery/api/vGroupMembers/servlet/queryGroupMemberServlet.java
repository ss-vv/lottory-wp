package com.unison.lottery.api.vGroupMembers.servlet;

import java.util.Map;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;

import com.unison.lottery.api.framework.servlet.AbstractProcessServlet;
import com.unison.lottery.api.protocol.common.MethodNames;
import com.unison.lottery.api.protocol.common.VONames;
import com.unison.lottery.api.protocol.response.model.HaveReturnStatusResponse;
import com.unison.lottery.api.vGroupMembers.bo.QueryGroupMembersBo;

@WebServlet("/json/security/queryGroupMembers")
//@WebServlet("/json/queryGroupMembers")
public class queryGroupMemberServlet extends AbstractProcessServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected String getMethodName() {
		return MethodNames.QUERY_GROUP_MEMBERS;
	}

	@Override
	protected String getResponseVOName() {
		return VONames.QUERY_GROUP_MEMBERS_RESPONSE_VO_NAME;
	}

	@SuppressWarnings("unchecked")
	@Override
	protected HaveReturnStatusResponse process(HttpServletRequest request) {
		Map<String, Object>  paramMap = (Map<String,Object>)request.getAttribute(VONames.QUERY_GROUP_MEMBERS_REQUEST_VO_NAME);
		QueryGroupMembersBo queryGroupMemberBo=(QueryGroupMembersBo) ctx.getBean("queryGroupMembersBo");
		return queryGroupMemberBo.makeQueryGroupMembersResponse(String.valueOf(paramMap.get("groupid")), String.valueOf(paramMap.get("pages")));
	}
}
