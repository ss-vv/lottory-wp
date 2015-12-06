package com.unison.lottery.weibo.lang;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SolrConfig {
	public SolrConfig(){}
	private static Logger logger = LoggerFactory.getLogger(SolrConfig.class);
	private static Properties props = new Properties(); 
	
	static{
		try {
			props.load(Thread.currentThread().getContextClassLoader().getResourceAsStream("init.properties"));
		} catch (FileNotFoundException e) {
			logger.error("加载配置文件错误",e);
		} catch (IOException e) {
			logger.error("加载配置文件错误",e);
		} catch (NullPointerException e) {
			logger.error("加载配置文件错误",e);
		}
	}
	
	public static String getSolrUrl(){
		return props.getProperty("solrUrl");
	}
}
