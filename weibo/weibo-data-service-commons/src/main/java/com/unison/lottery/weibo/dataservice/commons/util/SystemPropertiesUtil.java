package com.unison.lottery.weibo.dataservice.commons.util;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.Properties;

/**
 * 读取系统资源配置信息
 * @author 彭保星
 *
 * @since 2014年12月1日下午5:41:17
 */
public class SystemPropertiesUtil {
	/**
	 * 配置文件
	 */
	private static String FILE_NAME="qt-init.properties";
	
	private static Properties props = new Properties(); ;
	
	static {
		try {
			InputStream is = SystemPropertiesUtil.class.getClassLoader().getResourceAsStream(FILE_NAME);
			props.load(is);
			is.close();
		} catch (FileNotFoundException e1) {
		    e1.printStackTrace();
		} catch (IOException e1) {
		    e1.printStackTrace();
		}
		
	}
	/**
	 * 根据key值从system.properties文件读取对应得value值
	 * @param key
	 * @return
	 */
	public static String getPropsValue(String key){
		
		try {
			//解决中文乱码
			return new String(props.getProperty(key).getBytes(),"UTF-8") ;
		}catch (NullPointerException e) {
			return null;
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			return null;
		}
	}
}
