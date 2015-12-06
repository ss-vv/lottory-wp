package com.xhcms.lottery.utils;

import java.math.BigDecimal;

import org.apache.commons.lang.StringUtils;

/**
 * 数字工具。
 * @author Yang Bo
 *
 */
public class NumberUtils {
	
	public static final Double DOUBLE_ZERO = Double.valueOf(0.0);

	/**
	 * @return true if a > b
	 */
	public static boolean gt(BigDecimal a, BigDecimal b){
		return a.compareTo(b) == 1;
	}
	
	/**
	 * 百分比
	 * @param a
	 * @return
	 */
	public static BigDecimal percent(int a){
		return new BigDecimal(a).divide(new BigDecimal(100)) ;
	}
	
	/**
	 * 安全解析double字符串。
	 * @param num
	 * @return 0.0 如果 num 为blank(即null或空串)。
	 */
	public static Double safeParseDouble(String num){
		if (StringUtils.isBlank(num)){
			return NumberUtils.DOUBLE_ZERO;
		}
		return Double.parseDouble(num);
	}
}
