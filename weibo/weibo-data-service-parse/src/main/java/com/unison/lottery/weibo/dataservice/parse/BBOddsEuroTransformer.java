package com.unison.lottery.weibo.dataservice.parse;


/**
 * 篮球让分赔率解析时的属性转换器。
 * 
 * @author Yang Bo
 */
public class BBOddsEuroTransformer {

	public String corpId(String input){
		return BBCorpIdUtils.unifyCorpId(input);
	}
}
