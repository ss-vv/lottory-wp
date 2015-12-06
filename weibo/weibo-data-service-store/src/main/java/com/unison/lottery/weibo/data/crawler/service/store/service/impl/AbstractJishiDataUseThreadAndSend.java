package com.unison.lottery.weibo.data.crawler.service.store.service.impl;


import java.util.Map;

import com.unison.lottery.weibo.dataservice.crawler.service.JishiDataParseService;
import com.unison.lottery.weibo.dataservice.crawler.service.JishiDataParseServiceImpl;

public class AbstractJishiDataUseThreadAndSend {
	protected JishiDataParseService jishiBifenParseService;
	
	protected static Map<String, String> header;
	
	//最大的抓取次数
	protected final Integer MAX_CRAWLER_COUNT = 10;
	
	public AbstractJishiDataUseThreadAndSend(){
		this.jishiBifenParseService = new JishiDataParseServiceImpl();
	}
}
