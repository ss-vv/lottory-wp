package com.unison.lottery.api.pay.bo;

import java.util.Map;

import com.unison.lottery.api.model.User;
import com.unison.lottery.api.protocol.response.model.QueryAlipayRSAKeyResponse;

public interface QueryAlipayRSAKeyBO {

	QueryAlipayRSAKeyResponse getRSAResultString(User user,
			Map<String,Object>  paramMap);

}
