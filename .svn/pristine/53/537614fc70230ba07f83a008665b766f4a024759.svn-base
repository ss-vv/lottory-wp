package com.unison.lottery.weibo.lang;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ReservedKey {
	private static Logger logger = LoggerFactory.getLogger(ReservedKey.class);
	private static Properties props = new Properties(); 
	private static Map<String, String> keyMap = new HashMap<String,String>();
	static{
		try {
			props.load(Thread.currentThread().getContextClassLoader().getResourceAsStream("reserved_key.properties"));
			for (Object o : props.keySet()) {
				String key = null == o ? null:(String)o;
				if (StringUtils.isNotBlank(key)) {
					keyMap.put((String)props.get(key),(String)props.get(key));
				}
			}
		} catch (FileNotFoundException e) {
			logger.error("加载保留字错误",e);
		} catch (IOException e) {
			logger.error("加载保留字错误",e);
		}
	}
	public static String getValue(String key){
		return keyMap.get(key);
	}
}
