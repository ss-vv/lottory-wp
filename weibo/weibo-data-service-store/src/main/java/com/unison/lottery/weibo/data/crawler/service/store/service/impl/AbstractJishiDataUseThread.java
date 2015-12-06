package com.unison.lottery.weibo.data.crawler.service.store.service.impl;


import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import com.unison.lottery.weibo.data.crawler.proxy.query.dao.entity.UserAgentPO;
import com.unison.lottery.weibo.data.crawler.service.store.persist.dao.JishiBifenDataStoreDao;
import com.unison.lottery.weibo.data.crawler.service.store.persist.dao.impl.JishiBifenDataStoreDaoImpl;
import com.unison.lottery.weibo.dataservice.crawler.service.JishiDataParseService;
import com.unison.lottery.weibo.dataservice.crawler.service.JishiDataParseServiceImpl;

public class AbstractJishiDataUseThread {
	protected JishiDataParseService jishiBifenParseService;
	protected JishiBifenDataStoreDao jishiBifenDataStoreDao;
	
	protected static Map<String, String> header;
	
	//最大的抓取次数
	protected final Integer MAX_CRAWLER_COUNT = 10;
	
	public AbstractJishiDataUseThread(){
		this.jishiBifenParseService = new JishiDataParseServiceImpl();
		this.jishiBifenDataStoreDao = new JishiBifenDataStoreDaoImpl();
	}
	
	protected void makeHeader() {
		if (header == null) {
			header = new HashMap<String, String>();
		}
		UserAgentPO userAgentPO = null;
		try {
			userAgentPO = jishiBifenDataStoreDao.getRandomHeader();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		if (userAgentPO != null) {
			// 生成ua
			String user_agent = String.format(
					"Score (%s; %s)",
					new Object[] { userAgentPO.getSystemVersion(),
							userAgentPO.getPhoneType() });
			header.put("User-Agent", user_agent);
		}
	}
}
