package com.unison.lottery.weibo.dataservice.commons.crawler;

import static org.junit.Assert.*;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.unison.lottery.weibo.dataservice.commons.crawler.model.HttpResult;
import com.unison.lottery.weibo.dataservice.commons.crawler.model.Proxy;
import com.unison.lottery.weibo.dataservice.commons.crawler.util.HttpUtil;

/**
 * @author 彭保星
 *
 * @since 2014年10月27日下午1:50:34
 */
public class HttpClientUtilTest {
	HttpUtil httpUtil;
	ApplicationContext ctx;
	@Before
	public void init(){
		ctx = new ClassPathXmlApplicationContext("classpath:dataServiceTest.xml");
		httpUtil = (HttpUtil) ctx.getBean("httpUtil");
	}
	@Test
	public void testHttpClient(){
		
		Proxy proxy = new Proxy();
		proxy.setIp("218.92.227.168");
		proxy.setPort("18186");
		Map<String, String> header = new HashMap<>();
		header.put("systemVersion","Android 4.4");
		header.put("Accept-Encoding", "gzip");
		header.put("phoneType", "OPPO-N1t");
		Long ran = new Date().getTime();
		String finalUrl = "http://apk.win007.com/phone/InfoIndex.aspx?ran="+ran.toString();
		HttpResult httpResult = httpUtil.httpGet(finalUrl, proxy, header);
		assertNotNull(httpResult);
	}
}
