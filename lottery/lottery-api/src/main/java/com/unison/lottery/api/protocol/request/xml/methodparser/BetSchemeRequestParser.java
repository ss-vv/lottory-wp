package com.unison.lottery.api.protocol.request.xml.methodparser;

import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import com.unison.lottery.api.protocol.common.VONames;
import com.unison.lottery.api.protocol.request.xml.model.MethodRequest;



public class BetSchemeRequestParser extends
		AbstractRequestParserWithUserParser {

	@Override
	protected boolean parseParamList(MethodRequest methodRequest,
			HttpServletRequest httpRequest) {
		return false;
	}

	@Override
	protected boolean parseExtendRequestParams(MethodRequest methodRequest,
			HttpServletRequest httpRequest) {
		Map<String,String> paramMap = new HashMap<String, String>();
		paramMap.put(com.unison.lottery.api.protocol.common.Constants.LOTTERY_ID ,methodRequest.lotteryId );
		paramMap.put(com.unison.lottery.api.protocol.common.Constants.PLAY_ID ,methodRequest.playId );
		paramMap.put(com.unison.lottery.api.protocol.common.Constants.SCHEME_ID ,methodRequest.schemeId );
		paramMap.put(com.unison.lottery.api.protocol.common.Constants.MUTIPLE ,methodRequest.multiple);
		paramMap.put(com.unison.lottery.api.protocol.common.Constants.BET_NOTE ,methodRequest.betNote);
		paramMap.put(com.unison.lottery.api.protocol.common.Constants.PASS_TYPE ,methodRequest.passType);
		paramMap.put(com.unison.lottery.api.protocol.common.Constants.IS_SHOW ,methodRequest.isShow );
		paramMap.put(com.unison.lottery.api.protocol.common.Constants.SHOW_TYPE ,methodRequest.showType);
		paramMap.put(com.unison.lottery.api.protocol.common.Constants.FOLLOWED_RATIO ,methodRequest.followedRatio);
		paramMap.put(com.unison.lottery.api.protocol.common.Constants.BET_CONTENT ,methodRequest.betContent);
		paramMap.put(com.unison.lottery.api.protocol.common.Constants.BET_TYPE,methodRequest.betType);
		paramMap.put(com.unison.lottery.api.protocol.common.Constants.ISSUE_NUMBER,methodRequest.issueNumber);
		paramMap.put(com.unison.lottery.api.protocol.common.Constants.CHOOSE_TYPE, String.valueOf(methodRequest.chooseType));
		paramMap.put("buyAmount", methodRequest.buyAmount);
		paramMap.put("floorAmount", methodRequest.floorAmount);
		httpRequest.setAttribute(VONames.BET_SCHEME_VO_NAME, paramMap);
		return true;
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
