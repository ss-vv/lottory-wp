package com.unison.lottery.weibo.dataservice.crawler.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author 彭保星
 *
 * @since 2014年11月7日下午2:44:52
 */
public class ValueConvertUtil {
	private static Logger logger = LoggerFactory.getLogger(ValueConvertUtil.class);

	public static Integer safeInteger(String integer) {
		// TODO Auto-generated method stub
		Integer value = null;
		try{
			value = Integer.valueOf(integer);
		}catch(NumberFormatException exception){
			logger.debug("",exception);
		}
		return value;
	}
	
	public static Double safeDouble(String doubleValue){
		Double value = null;
		try{
			value = Double.valueOf(doubleValue);
		}catch(NumberFormatException e){
			logger.debug("",e);
		}
		return value;
	}
}
