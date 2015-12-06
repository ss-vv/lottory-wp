package com.xhcms.lottery.utils;

import java.util.Collection;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang3.StringUtils;

public final class TextUtils {
    private TextUtils(){}
    
	public static String arrayToString(Collection<String> array) {
	    return ArrayUtils.toString(array).replace("[", "").replace("]", "").replace(" ", "");
	}
	
	public static double parseDouble(String obj){
	    double ret = 0d;
		if (obj != null) {
		    try {
	            ret = Double.parseDouble(obj);
	        } catch (NumberFormatException e) {
	        }
		}
		
		return ret;
	}

	/**
	 * 去掉第一行是空行的字符串。
	 * @param content
	 * @return 去掉第一行空行的剩下内容。
	 */
	public static String removeFirstBlankLine(String content) {
		if (StringUtils.isBlank(content)){
			return content;
		}
		return content.replaceFirst("^\n|^\r\n", "");
	}
	
}
