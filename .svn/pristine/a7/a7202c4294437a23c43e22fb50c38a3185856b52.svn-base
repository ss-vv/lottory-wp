package com.unison.lottery.weibo.dataservice.commons.crawler.download;

import java.util.Map;

import com.unison.lottery.weibo.dataservice.commons.constants.DataInterfaceName;
import com.unison.lottery.weibo.dataservice.commons.crawler.constants.CrawlerInterfaceName;
import com.unison.lottery.weibo.dataservice.commons.crawler.model.HttpResult;
import com.unison.lottery.weibo.dataservice.commons.crawler.model.Proxy;
import com.unison.lottery.weibo.dataservice.commons.model.DataInterfaceResponse;

public interface DownloadService {

	/**
	 * 球探客户端数据下载
	 * @param crawlerInterfaceName 数据接口名称，枚举类型
	 * @param extendParams 额外参数
	 * @param proxy 代理ip地址
	 * @param header http请求头的header属性
	 * @return 响应结果
	 */
	HttpResult downloadToStringWithExtendParams(
			CrawlerInterfaceName crawlerInterfaceName,
			Map<String, String> extendParams, Proxy proxy,Map<String, String> header);
	
	HttpResult downloadToStringWithExtendParamsBySpecielUrl(
			CrawlerInterfaceName crawlerInterfaceName,String qtBsid,
			Map<String, String> extendParams, Proxy proxy,Map<String, String> header);

	void makeHttpUtil();

	HttpResult downloadJishiBifenToStringWithExtenParams(
			Map<String, String> extendParams, Map<String, String> header,
			String url);

	HttpResult downloadJishiToStringWithExtendParamsBySpecielUrl(
			Map<String, String> makeFleshExtendParams,
			Map<String, String> header, String propsValue, String bsId);
}
