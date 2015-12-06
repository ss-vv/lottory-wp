package com.unison.lottery.api.query.bo;

import java.util.Map;

import com.unison.lottery.api.model.User;
import com.unison.lottery.api.protocol.response.model.QueryScoreLiveInfoResponse;

public interface QueryScoreLiveBO {
	
	QueryScoreLiveInfoResponse makeQueryScoreLiveInfoResponse(User user, Map<String, Object>  paramMap);
	
}
