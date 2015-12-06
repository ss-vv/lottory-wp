package com.unison.lottery.weibo.data.crawler.service.store.service.impl;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.unison.lottery.weibo.data.crawler.proxy.ProxyChooser;
import com.unison.lottery.weibo.data.crawler.proxy.UserAgentChooser;
import com.unison.lottery.weibo.data.crawler.proxy.query.dao.entity.ProxyPO;
import com.unison.lottery.weibo.data.crawler.proxy.query.dao.entity.UserAgentPO;
import com.unison.lottery.weibo.dataservice.commons.crawler.constants.CrawlerInterfaceName;
import com.unison.lottery.weibo.dataservice.commons.crawler.model.Proxy;
import com.unison.lottery.weibo.dataservice.commons.util.SystemPropertiesUtil;
import com.unison.lottery.weibo.dataservice.crawler.service.CrawlerDataParseService;

/**
 * @author 彭保星
 *
 * @since 2014年11月19日下午4:13:53
 */
public abstract class AbstractCrawlerDataStoreService {
	@Autowired
	private ProxyChooser proxyChooser;
	@Autowired
	protected UserAgentChooser userAgentChooser;
	public Map<String, String> header;
	public Proxy proxy;
	
	public void init() {
		header = new HashMap<>();

	}
	protected void makeHeaderAndProxy(CrawlerInterfaceName crawlerInterfaceName) {
		UserAgentPO userAgentPO = userAgentChooser.randomChooseOne();
		makeHeader(userAgentPO);
//		proxy = makeRandomHealthProxy(crawlerInterfaceName, header);
	}
	protected void makeHeader(UserAgentPO userAgentPO) {
		header.put("Accept-Encoding", "gzip");
		if (userAgentPO != null) {
			// 生成ua
			String user_agent = String.format(
					"Score (%s; %s)",
					new Object[] { userAgentPO.getSystemVersion(),
							userAgentPO.getPhoneType() });
			header.put("User-Agent", user_agent);
		}
	}
	

	protected Proxy makeRandomHealthProxy(
			CrawlerInterfaceName crawlerInterfaceName,
			Map<String, String> header) {
		ProxyPO proxyPO = proxyChooser.randomChooseOne();
		Proxy proxy = new Proxy();
		if (proxyPO != null) {
			try {
				BeanUtils.copyProperties(proxy, proxyPO);
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return proxy;
	}
	
	protected void makeHeaderFromFile() {
		if (header == null) {
			header = new HashMap<String, String>();
		}
		String headerList = SystemPropertiesUtil.getPropsValue("headerList");
		String[] header1 = headerList.split("\\;", -1);
		int size = header1.length;
		Random random = new Random();
		int index = random.nextInt(size);
		if (header1[index] != null) {
			String[] agent = header1[index].split("\\:", -1);
			// 生成ua
			String user_agent = String.format("Score (%s; %s)", new Object[] {
					agent[1], agent[0] });
			header.put("User-Agent", user_agent);
		}
	}
	
	public ProxyChooser getProxyChooser() {
		return proxyChooser;
	}
	public void setProxyChooser(ProxyChooser proxyChooser) {
		this.proxyChooser = proxyChooser;
	}
	public UserAgentChooser getUserAgentChooser() {
		return userAgentChooser;
	}
	public void setUserAgentChooser(UserAgentChooser userAgentChooser) {
		this.userAgentChooser = userAgentChooser;
	}
	public Map<String, String> getHeader() {
		return header;
	}
	public void setHeader(Map<String, String> header) {
		this.header = header;
	}
	public Proxy getProxy() {
		return proxy;
	}
	public void setProxy(Proxy proxy) {
		this.proxy = proxy;
	}
	
	
}
