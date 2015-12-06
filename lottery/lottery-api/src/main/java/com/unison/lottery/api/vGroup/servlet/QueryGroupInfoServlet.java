package com.unison.lottery.api.vGroup.servlet;

import java.util.Map;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;

import com.unison.lottery.api.framework.servlet.AbstractProcessServlet;
import com.unison.lottery.api.model.User;
import com.unison.lottery.api.protocol.common.MethodNames;
import com.unison.lottery.api.protocol.common.VONames;
import com.unison.lottery.api.protocol.response.model.HaveReturnStatusResponse;
import com.unison.lottery.api.vGroup.bo.QueryGroupInfoBo;
import com.unison.lottery.api.vGroup.data.GroupSecretkey;
import com.xhcms.lottery.utils.DES;
//@WebServlet("/json/security/queryGroupInfo")
@WebServlet("/json/queryGroupInfo")
public class QueryGroupInfoServlet extends AbstractProcessServlet{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected String getMethodName() {
		return MethodNames.QUERY_GROUP_INFO;
	}

	@Override
	protected String getResponseVOName() {
		return VONames.QUERY_GROUP_INFO_RESPONSE_VO_NAME;
	}

	@SuppressWarnings("unchecked")
	@Override
	protected HaveReturnStatusResponse process(HttpServletRequest request) {
		Map<String,String>  paramMap = (Map<String,String>)request.getAttribute(VONames.QUERY_GROUP_INFO_REQUEST_VO_NAME);
		User user = (User) request.getAttribute(VONames.USER_VO_NAME);
		QueryGroupInfoBo queryGroupInfoBo=(QueryGroupInfoBo) ctx.getBean("queryGroupInfoBo");
		return queryGroupInfoBo.makeQueryGroupInfoResponse(paramMap.get("channel"), parserGroupId(paramMap.get("groupid")), user);
	}
    /**
     * 解密groupId
     * @param groupid
     * @return
     */
	private String parserGroupId(String groupid){
		try {
			if(StringUtils.isNotBlank(groupid)){
				return DES.decryptDES(groupid, GroupSecretkey.secretKey, "utf-8");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
}
