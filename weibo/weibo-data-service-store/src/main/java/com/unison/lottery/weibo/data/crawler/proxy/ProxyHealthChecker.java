package com.unison.lottery.weibo.data.crawler.proxy;

import java.util.Map;

import com.unison.lottery.weibo.data.crawler.proxy.query.dao.entity.ProxyPO;
import com.unison.lottery.weibo.dataservice.commons.crawler.constants.CrawlerInterfaceName;


public interface ProxyHealthChecker {

	boolean check(ProxyPO proxy,String checkUrl, Map<String, String> header);

}
