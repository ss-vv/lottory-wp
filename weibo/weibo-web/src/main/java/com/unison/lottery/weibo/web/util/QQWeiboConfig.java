package com.unison.lottery.weibo.web.util;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class QQWeiboConfig {
	public QQWeiboConfig(){}
	private static Properties props = new Properties(); 
	static{
		try {
			props.load(Thread.currentThread().getContextClassLoader().getResourceAsStream("QQWeiboConfig.properties"));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public static String getValue(String key){
		return props.getProperty(key);
	}

    public static void updateProperties(String key,String value) {    
            props.setProperty(key, value); 
    } 
}
