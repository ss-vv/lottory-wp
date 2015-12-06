package com.unison.lottery.api.vGroupSysScheme.servlet;

import java.util.Map;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;

import com.unison.lottery.api.framework.servlet.AbstractProcessServlet;
import com.unison.lottery.api.model.User;
import com.unison.lottery.api.protocol.common.MethodNames;
import com.unison.lottery.api.protocol.common.VONames;
import com.unison.lottery.api.protocol.response.model.HaveReturnStatusResponse;
import com.unison.lottery.api.vGroupPublishScheme.bo.PublishSchemeBo;
import com.unison.lottery.api.vGroupSysScheme.bo.QuerySysSchemeBo;

@WebServlet("/json/security/querySysScheme")
public class SysSchemeServlet extends AbstractProcessServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected String getMethodName() {
		return MethodNames.SYS_SCHEME;
	}

	@Override
	protected String getResponseVOName() {
		return VONames.SYS_SCHEME_RESPONSE_VO_NAME;
	}

	@SuppressWarnings("unchecked")
	@Override
	protected HaveReturnStatusResponse process(HttpServletRequest request) {
		Map<String, String>  paramMap = (Map<String,String>)request.getAttribute(VONames.SYS_SCHEME_REQUEST_VO_NAME);
		QuerySysSchemeBo querySysSchemeBo=(QuerySysSchemeBo) ctx.getBean("querySysSchemeBo");
		return querySysSchemeBo.makeSysScheme(paramMap);
	}
}
