package com.unison.lottery.api.protocol.request.xml.methodparser;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;


import com.unison.lottery.api.protocol.common.VONames;
import com.unison.lottery.api.protocol.request.xml.model.MethodRequest;



public class RechargeCardMethodRequestParser extends
		AbstractRequestParserWithUserParser {

	@Override
	protected boolean parseParamList(MethodRequest methodRequest,
			HttpServletRequest httpRequest) {
		return false;
	}

	@Override
	protected boolean parseExtendRequestParams(MethodRequest methodRequest,
			HttpServletRequest httpRequest) {
		boolean result=false;
		BigDecimal rechargeAmount=methodRequest.rechargeAmount;
		String rechargeCardType=methodRequest.rechargeCardType;
		String rechargeCardNumber=methodRequest.rechargeCardNumber;
		String rechargeCardPass=methodRequest.rechargeCardPass;
		String voucherUserId=methodRequest.voucherUserId;
		Map<String,Object> params=new HashMap<String,Object>();
		params.put("rechargeAmount", rechargeAmount);
		params.put("rechargeCardType", rechargeCardType);
		params.put("rechargeCardNumber", rechargeCardNumber);
		params.put("rechargeCardPass", rechargeCardPass);
		params.put("voucherUserId", voucherUserId);
		httpRequest.setAttribute(VONames.RECHARGE_CARD_REQUEST_VO_NAME, params);		
		result=true;
		return result;
	}

	@Override
	protected boolean decideShouldParseParamList() {
		
		return false;
	}

	@Override
	protected boolean decideShouldParseExtendRequestParams() {
		
		return true;
	}

	@Override
	protected boolean decideShouldParseUser() {
		
		return true;
	}

}
