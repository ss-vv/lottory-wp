package com.unison.lottery.api.protocol.response.xml.parser.methodparse;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;

import com.unison.lottery.api.protocol.common.SystemStatusKeyNames;
import com.unison.lottery.api.protocol.common.VONames;
import com.unison.lottery.api.protocol.response.model.IHaveReturnStatus;
import com.unison.lottery.api.protocol.response.model.QueryUserInfoResponse;
import com.unison.lottery.api.protocol.response.model.Response;
import com.unison.lottery.api.protocol.response.util.ResultResponseUtil;
import com.xhcms.lottery.commons.data.Account;
import com.xhcms.lottery.commons.data.User;

public class QueryUserInfoMethodResponseParser extends
		com.unison.lottery.api.protocol.response.json.parser.methodparse.AbstractMethodResponseParser {

	@Override
	protected IHaveReturnStatus getResponseFromHttpRequest(
			HttpServletRequest httpRequest) {
		return (IHaveReturnStatus) httpRequest
				.getAttribute(VONames.QUERY_USER_INFO_RESPONSE_VO_NAME);
	}

	@Override
	protected void handle(Response resultResponse,
			Object responseFromHttpRequest) {
		QueryUserInfoResponse resp = (QueryUserInfoResponse) responseFromHttpRequest;
		User user = resp.getUser();
		Account account = resp.getAccount();
		bindRespData(resultResponse, user, account);
		resultResponse.hxUsername = resp.getHxUsername();
		resultResponse.hxPassword = resp.getHxPassword();
	}

	private void bindRespData(Response resultResponse, User user, Account account) {
		if(null != user && user.getId() != null && user.getId() > 0) {
			resultResponse.nickName = user.getNickName();
			resultResponse.imageUrl = user.getHeadImageURL();
			resultResponse.phoneNumber = user.getMobile();
			resultResponse.IDCard = user.getIdNumber();
			resultResponse.realName = user.getRealname();
			resultResponse.provinceOfBank = user.getProvince();
			resultResponse.cityOfBank = user.getCity();
			resultResponse.accountUser = user.getRealname();
			boolean isBindMobile = false;
			if(user.getVerifyStatus() == 1 || user.getVerifyStatus() == 3 
					|| StringUtils.isNotBlank(user.getEmail())) {
				isBindMobile = true;
			}
			resultResponse.isBindMobile = isBindMobile;
		}
		ResultResponseUtil.bindAccountInfoToResultResponse(resultResponse, account);
	}


	@Override
	protected String getDefaultReturnStatusKeyName() {
		return SystemStatusKeyNames.QUERY_GROUP_INFO_FAIL;
	}
}