package com.unison.lottery.weibo.dataservice.util;

import org.apache.commons.lang3.StringUtils;

/**
 * 字符串分割去空格工具类
 * @author 彭保星
 *
 * @since 2014年10月20日上午10:38:25
 */
public class StringSplitUtilsAfterTrim {
	public static String[] splitPreserveAllTokens(String str,String separatorChars){
		String[] strArr = StringUtils.splitPreserveAllTokens(str, separatorChars);
		if(strArr!=null){
			int length = strArr.length;
			for (int i = 0; i < length; i++) {
				String element = strArr[i];
				if(StringUtils.isNotBlank(element)){
					strArr[i] = element.trim();
				}
				
			}
		}
		return strArr;
	}
}
