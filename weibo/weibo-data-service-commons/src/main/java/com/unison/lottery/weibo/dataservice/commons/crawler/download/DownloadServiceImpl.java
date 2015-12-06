package com.unison.lottery.weibo.dataservice.commons.crawler.download;


import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Date;
import java.util.EnumMap;
import java.util.Map;
import java.util.Map.Entry;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.unison.lottery.weibo.dataservice.commons.crawler.constants.CrawlerInterfaceName;
import com.unison.lottery.weibo.dataservice.commons.crawler.model.HttpResult;
import com.unison.lottery.weibo.dataservice.commons.crawler.model.Proxy;
import com.unison.lottery.weibo.dataservice.commons.crawler.util.HttpUtil;
import com.unison.lottery.weibo.dataservice.commons.crawler.util.HttpUtilImpl;
import com.unison.lottery.weibo.dataservice.commons.util.SystemPropertiesUtil;

/**
 * @author 彭保星
 *
 * @since 2014年10月29日下午1:40:47
 */
@Service
public class DownloadServiceImpl implements DownloadService {

	
	private Map<CrawlerInterfaceName,String> urlMap;
	
	@Autowired
	private HttpUtil httpUtil;

	private Logger logger=LoggerFactory.getLogger(getClass());

	private String decideUrlFromName(CrawlerInterfaceName crawlerInterfaceName) {
		String url=null;
		if(null!=urlMap&&!urlMap.isEmpty()){
			url=urlMap.get(crawlerInterfaceName);
		}
		return url;
	}

	public Map<CrawlerInterfaceName, String> getUrlMap() {
		return urlMap;
	}

	public void setUrlMap(Class<CrawlerInterfaceName> urlMap) {
		this.urlMap = new EnumMap<CrawlerInterfaceName, String>( urlMap);
	}

	private String makeFinalUrl(Map<String, String> extendParams, String baseUrl) {
		String finalUrl=baseUrl;
		StringBuffer sb=new StringBuffer(finalUrl);
		if(baseUrl.indexOf("?") == -1){
			sb.append("?ran="+new Date().getTime());
		} else {
			sb.append("&ran="+new Date().getTime());
		}
		if(null!=extendParams&&!extendParams.isEmpty()){
			int size=extendParams.size();
			sb.append("&");
			try {
				int i=1;
				for(Entry<String, String> entry :extendParams.entrySet()){
					sb.append(entry.getKey());
					sb.append("=");
					sb.append(URLEncoder.encode(entry.getValue(), "utf-8"));
					if(i<size){
						sb.append("&");
					}
					i++;
				}
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
				return null;
			}
		}
		return sb.toString();
	}
	
	@Override
	public HttpResult downloadToStringWithExtendParams(
			CrawlerInterfaceName crawlerInterfaceName,
			Map<String, String> extendParams,Proxy proxy,Map<String, String> header) {
		String baseUrl=decideUrlFromName(crawlerInterfaceName);
		String finalUrl = makeFinalUrl(extendParams, baseUrl);
		logger.info("finalUrl={}",finalUrl);
		
		HttpResult result= httpUtil.httpGet(finalUrl, proxy, header);
		
		logger.info("result:{}",result);
		return result;
	}
	
	@Override
	public HttpResult downloadJishiBifenToStringWithExtenParams(Map<String, String> extendParams,Map<String, String> header,String url){
		String finalUrl = makeFinalUrl(extendParams, url);
		logger.debug("finalUrl={}",finalUrl);
		
		HttpResult result= httpUtil.httpGet(finalUrl, null, header);
		
		logger.debug("result:{}",result);
		return result;
		
	}
	public HttpUtil getHttpUtil() { 
		return httpUtil;
	}

	public void setHttpUtil(HttpUtil httpUtil) {
		this.httpUtil = httpUtil;
	}
	
	
	public void setUrlMap(Map<CrawlerInterfaceName, String> urlMap) {
		this.urlMap = urlMap;
	}

	/**
	 * 获取这种特殊格式url的数据
	 * http://txt.win007.com/jsData/tech/1/97/197132.js?flesh=1414045316764&ran=1414045316764 
	 */
	@Override
	public HttpResult downloadToStringWithExtendParamsBySpecielUrl(
			CrawlerInterfaceName crawlerInterfaceName,String qtBsid,
			Map<String, String> extendParams, Proxy proxy,
			Map<String, String> header) {
		
		String baseUrl=decideUrlFromName(crawlerInterfaceName);
		String finalUrl = makeFinalUrl(extendParams, baseUrl+qtBsid.charAt(0)+"/"+qtBsid.substring(1, 3)+"/"+qtBsid+".js");
		logger.info("finalUrl={}",finalUrl);
		
		HttpResult result= httpUtil.httpGet(finalUrl, proxy, header);
		
		logger.debug("result:{}",result);
		return result;
	}

	@Override
	public void makeHttpUtil() {
		if(httpUtil==null){
			httpUtil = new HttpUtilImpl();
		}
	}

	@Override
	public HttpResult downloadJishiToStringWithExtendParamsBySpecielUrl(
			Map<String, String> makeFleshExtendParams,
			Map<String, String> header, String baseUrl, String bsId) {
		String finalUrl = makeFinalUrl(makeFleshExtendParams, baseUrl+bsId.charAt(0)+"/"+bsId.substring(1, 3)+"/"+bsId+".js");
		logger.debug("finalUrl={}",finalUrl);
		
		HttpResult result= httpUtil.httpGet(finalUrl, null, header);
		
		logger.debug("result:{}",result);
		return result;
	}


	
	

}
