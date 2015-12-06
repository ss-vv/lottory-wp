package com.unison.lottery.weibo.util;

import java.io.IOException;
import java.util.Properties;

public class BetNumConfig {
	public static Properties prop=new Properties();
	static{
		try {
			prop.load(Thread.currentThread().getContextClassLoader().getResourceAsStream("bet-num_config.properties"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	public static String getValue(String key){
		return prop.getProperty(key);
	}

    public static void updateProperties(String key,String value) {    
            prop.setProperty(key, value); 
    } 
}
