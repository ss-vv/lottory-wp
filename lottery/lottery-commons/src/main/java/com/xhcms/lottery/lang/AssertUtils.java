package com.xhcms.lottery.lang;

import org.apache.commons.lang.StringUtils;

/**
 * 
 * @author Yang Bo
 *
 */
public class AssertUtils {
	/**
	 * 确保参数不能为空。
	 * @param checkedValue 被检查的值
	 * @param name 参数名
	 */
	public static void assertNotBlank(String checkedValue, String name){
		if (StringUtils.isBlank(checkedValue)){
			throw new IllegalArgumentException(
					String.format("argument '%s' can not be blank!", name));
		}
	}
}
