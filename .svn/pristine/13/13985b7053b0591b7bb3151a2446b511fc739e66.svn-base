package com.unison.lottery.weibo.data.crawler.proxy;

import java.util.List;
import java.util.Map;
import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.unison.lottery.weibo.data.crawler.proxy.query.dao.ProxyDao;
import com.unison.lottery.weibo.data.crawler.proxy.query.dao.entity.ProxyPO;
import com.unison.lottery.weibo.dataservice.commons.crawler.constants.CrawlerInterfaceName;


public class ProxyChooserImpl implements ProxyChooser {
	
	@Autowired
	private ProxyDao proxyDao;
	
	@Autowired
	private ProxyHealthChecker proxyHealthChecker;
	
	Logger log = LoggerFactory.getLogger(getClass());
	
	private Map<CrawlerInterfaceName, String> urlMap;
	@Autowired
	@Value(value = "10")
	private int maxCheckCount;

	@Override
	public ProxyPO randomChooseOne() {
		ProxyPO proxy=null;
		List<ProxyPO> proxies=proxyDao.findAll();
		if(null!=proxies&&!proxies.isEmpty()){
			proxy = chooseOneFromList(proxies);
		}
		return proxy;
	}

	private ProxyPO chooseOneFromList(List<ProxyPO> proxies) {
		ProxyPO proxy;
		int size=proxies.size();
		Random random=new Random();
		int index=random.nextInt(size);
		proxy=proxies.get(index);
		return proxy;
	}

	@Override
	public ProxyPO randomChooseHealthOne(CrawlerInterfaceName crawlerInterfaceName,Map<String, String> header) {
		ProxyPO proxy=null;
		List<ProxyPO> proxies=proxyDao.findAll();
		if(null!=proxies&&!proxies.isEmpty()){
			proxy = chooseOneFromList(proxies);
			int count=1;
			while(!proxyHealthChecker.check(proxy,makeCheckUrl(crawlerInterfaceName),header)&&count<=maxCheckCount){
				proxy = chooseOneFromList(proxies);
				count++;
			}
		}
		log.debug("获取的健康的proxy:{}",proxy);
		return proxy;
	}

	private String makeCheckUrl(CrawlerInterfaceName crawlerInterfaceName) {
		// TODO Auto-generated method stub
		
		return urlMap.get(crawlerInterfaceName);
	}

	public Map<CrawlerInterfaceName, String> getUrlMap() {
		return urlMap;
	}

	public void setUrlMap(Map<CrawlerInterfaceName, String> urlMap) {
		this.urlMap = urlMap;
	}
	

}
