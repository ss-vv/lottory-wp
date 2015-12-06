package com.unison.lottery.api.vGroupSysScheme.bo;

import java.util.Map;

import com.unison.lottery.api.protocol.response.model.QuerySysSchemeResponse;

public interface QuerySysSchemeBo {
	
	QuerySysSchemeResponse makeSysScheme(Map<String, String> map);
	
}
