package com.unison.lottery.api.vGroupPublishScheme.serlvet;

import java.util.Map;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;

import com.unison.lottery.api.framework.servlet.AbstractProcessServlet;
import com.unison.lottery.api.model.User;
import com.unison.lottery.api.protocol.common.MethodNames;
import com.unison.lottery.api.protocol.common.VONames;
import com.unison.lottery.api.protocol.response.model.HaveReturnStatusResponse;
import com.unison.lottery.api.vGroupMembers.bo.QueryGroupMembersBo;
import com.unison.lottery.api.vGroupPublishScheme.bo.PublishSchemeBo;

@WebServlet("/json/security/publishScheme")
public class PublishSchemeServlet extends AbstractProcessServlet {

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		@Override
		protected String getMethodName() {
			return MethodNames.PUBLISH_SCHEME;
		}

		@Override
		protected String getResponseVOName() {
			return VONames.PUBLISH_SCHEME_RESPONSE_VO_NAME;
		}

		@SuppressWarnings("unchecked")
		@Override
		protected HaveReturnStatusResponse process(HttpServletRequest request) {
			Map<String, Object>  paramMap = (Map<String,Object>)request.getAttribute(VONames.PUBLISH_SCHEME_REQUEST_VO_NAME);
			User user=(User) request.getAttribute(VONames.USER_VO_NAME);
			PublishSchemeBo publishSchemeBo=(PublishSchemeBo) ctx.getBean("publishSchemeBo");
			return publishSchemeBo.publishScheme2HX(user, paramMap);
		}
}
