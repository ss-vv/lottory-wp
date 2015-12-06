package com.unison.lottery.api.protocol.request.xml.methodparser;

import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import com.unison.lottery.api.protocol.request.xml.model.MethodRequest;

/**
 * @desc
 * @createTime 2012-11-30
 * @author lei.li@unison.net.cn
 * @version 1.0
 */
public class LotteryResultMethodRequestParser extends
		AbstractRequestParserWithUserParser {

	@Override
	protected boolean parseParamList(MethodRequest methodRequest,
			HttpServletRequest httpRequest) {
		return false;
	}

	@Override
	protected boolean parseExtendRequestParams(MethodRequest methodRequest,
			HttpServletRequest httpRequest) {
		String lotteryId = methodRequest.lotteryId;
		String matchDate = methodRequest.matchDate;
		Integer page = methodRequest.page;
		String playId = methodRequest.playId;

		Map<String, String> m = new HashMap<String, String>();
		m.put("matchDate", matchDate);
		m.put("page", String.valueOf(page));
		m.put("lotteryId", lotteryId);
		m.put("playId", playId);
		httpRequest.setAttribute("paramMap", m);
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