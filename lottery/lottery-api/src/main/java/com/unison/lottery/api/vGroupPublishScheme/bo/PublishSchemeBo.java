package com.unison.lottery.api.vGroupPublishScheme.bo;

import java.util.Map;

import com.unison.lottery.api.model.User;
import com.unison.lottery.api.protocol.response.model.PublishSchemeResponse;

public interface PublishSchemeBo {

	PublishSchemeResponse publishScheme2HX(User user, Map<String, Object>  paramMap);
	
}
