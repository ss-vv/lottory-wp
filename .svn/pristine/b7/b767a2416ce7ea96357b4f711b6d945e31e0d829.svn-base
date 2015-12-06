package com.unison.lottery.api.protocol.response.xml.parser.methodparse;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;

import com.unison.lottery.api.protocol.common.SystemStatusKeyNames;
import com.unison.lottery.api.protocol.common.VONames;
import com.unison.lottery.api.protocol.common.model.Item;
import com.unison.lottery.api.protocol.common.model.Result;
import com.unison.lottery.api.protocol.response.model.IHaveReturnStatus;
import com.unison.lottery.api.protocol.response.model.Response;
import com.unison.lottery.api.protocol.response.model.UseVoucherResponse;

public class UseVoucherResponseParser extends AbstractMethodResponseParser {

	@Override
	protected IHaveReturnStatus getResponseFromHttpRequest(
			HttpServletRequest httpRequest) {
		return (IHaveReturnStatus) httpRequest.getAttribute(VONames.USE_VOUCHER_RESPONSE_VO_NAME);
	}

	@Override
	protected void handle(Response resultResponse,
			Object responseFromHttpRequest) {
		UseVoucherResponse useVoucherResponse = (UseVoucherResponse)responseFromHttpRequest;
		if(null!=useVoucherResponse&&null!=useVoucherResponse.getReturnStatus()){
			resultResponse.result=new Result();
			resultResponse.result.item = new ArrayList<Item>();
			resultResponse.free = useVoucherResponse.getFree();
			resultResponse.fund = useVoucherResponse.getFund();
			resultResponse.grant = useVoucherResponse.getGrant();
		}
	}

	@Override
	protected String getDefaultReturnStatusKeyName() {
		return SystemStatusKeyNames.QueryVoucers.SUCC;
	}
}
