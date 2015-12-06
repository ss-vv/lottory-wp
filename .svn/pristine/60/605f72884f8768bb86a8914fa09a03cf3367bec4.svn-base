package com.unison.lottery.weibo.dataservice.commons.crawler.util;

import java.io.IOException;
import java.util.Map;

import org.apache.http.client.ClientProtocolException;

import com.unison.lottery.weibo.dataservice.commons.crawler.model.HttpResult;
import com.unison.lottery.weibo.dataservice.commons.crawler.model.Proxy;

public interface HttpUtil {

	/**
	 * 
	 * @param finalUrl 请求url
	 * @param proxy 动态代理ip地址
	 * @param header 请求头参数，<参数名，参数值>:<systemVersion,Android 4.0.3>
	 * @return
	 */
	HttpResult httpGet(String finalUrl, Proxy proxy,Map<String, String> header);

	HttpResult httpPost(String finalUrl, Map<String, String> params, Proxy proxy) throws IOException;
}
