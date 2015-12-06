package com.unison.lottery.weibo.dataservice.parse;

import org.apache.commons.lang3.StringUtils;

public class BBOddsBigTransformer {

	public String corpId(String input){
		return BBCorpIdUtils.unifyCorpId(input);
	}
	
	public String groundHandicap(String input){
		if (StringUtils.isBlank(input)){
			return "0";
		}else{
			return input;
		}
	}
}
