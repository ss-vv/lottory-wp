package com.unison.lottery.weibo.utils;

import java.math.BigDecimal;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 一些数字工具方法。
 * 
 * @author Yang Bo
 */
public class NumberUtils {

	private static Logger logger = LoggerFactory.getLogger(NumberUtils.class);
	
	/**
	 * 如果 input 是 null、空串，就返回 0.0
	 */
	public static BigDecimal safeParseBigDecimal(String value){
		if (StringUtils.isBlank(value)){
			return BigDecimal.ZERO;
		}
		return new BigDecimal(value);
	}
	
	/**
	 * 将 double 转换为 long 型数字串。
	 * @param num double 类型变量
	 * @return long 型字符串
	 */
	public static String doubleToLongString(double num){
		BigDecimal bd = new BigDecimal(num);
		return bd.longValue()+"";
	}
	
	/**
	 * 确保无异常抛出地解析Integer串为int。
	 */
	public static int safeParseInt(String intStr){
		try{
			return Integer.parseInt(intStr);
		}catch(Exception e){
			logger.debug("无效的 Integer 串：{}", intStr);
			return 0;
		}
	}

	private static Pattern intPattern = Pattern.compile("\\d+");
	
	/**
	 * 抽取 intStr 中包含的 Integer 数字.
	 * @param intStr 包含 Integer 数字的串。
	 * @return 第一个数字。
	 */
	public static Integer extractInt(String intStr) {
		Matcher matcher = intPattern.matcher(intStr);
		if (matcher.find()){
			return Integer.valueOf(matcher.group());
		}else{
			return null;
		}
	}

	public static Long doubleObjectToLong(Object doubleObj) {
		return ((Double)doubleObj).longValue();
	}

	/**
	 * @param doubleObj
	 * @return 1 for true, 0 for false
	 */
	public static boolean doubleObjectToBoolean(Object doubleObj) {
		
		return ((Double) doubleObj).intValue()==1 ? true : false;
	}

	public static int doubleObjectToInt(Object doubleObj) {
		return ((Double)doubleObj).intValue();
	}

	public static BigDecimal stringToDecimal(Object strObj) {
		String percent = (String) strObj;
		
		return new BigDecimal(percent);
	}

	public static BigDecimal doubleObjectToDecimal(Object object) {
		return new BigDecimal((Double) object);
	}
}
