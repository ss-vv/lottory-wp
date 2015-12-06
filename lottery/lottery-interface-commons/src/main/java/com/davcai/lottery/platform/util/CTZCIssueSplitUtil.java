package com.davcai.lottery.platform.util;

import org.apache.commons.lang3.StringUtils;
/**
 * 彩客 CTZC 中的期信息去掉前两位
 * @author haohao
 *
 */
public class CTZCIssueSplitUtil {

	public static String splitCTZCIssueNumber(String issueNumber){
		if(StringUtils.isNotBlank(issueNumber) &&issueNumber.length()>2){
			return issueNumber.substring(2);
		}
		return "-1";
	}
}
