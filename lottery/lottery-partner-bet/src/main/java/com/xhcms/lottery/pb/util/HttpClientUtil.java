package com.xhcms.lottery.pb.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HttpClientUtil {
	private Logger logger = LoggerFactory.getLogger(getClass());
	
	private HttpPost httpPost;
	private String responseXml = new String();
	private Map<String,String> headerMap = new HashMap<String, String>();
	
	public HttpClientUtil(String uri,Header[] headers, String contentXml) {
		this.httpPost = new HttpPost(uri);
		this.httpPost.setHeaders(headers);
		StringEntity xmlEntity = new StringEntity(contentXml,
				ContentType.create("text/xml", "UTF-8"));
		this.httpPost.setEntity(xmlEntity);
	}
	
	/**
	 * 执行请求，并将响应字符串和响应头信息放入入参中
	 * @param responseXml
	 * @param responseHeaders
	 */
	public void exec() {
		HttpClient httpclient = new DefaultHttpClient();
		try {
			HttpResponse response = httpclient.execute(this.httpPost);
			//获取头信息
			Header[] hs = response.getAllHeaders();
			logger.info("---------响应头信息-----------");
			for (Header header : hs) {
				this.headerMap.put((String)header.getName(),(String)header.getValue());
				logger.info((String)header.getName() + ":" + (String)header.getValue());
			}
			//获取消息体信息
			HttpEntity entity = response.getEntity();
			if (entity != null) {
				InputStream instream = entity.getContent();
				this.responseXml = FileUtil.inputStreamToString(instream);
				instream.close();
				logger.info("---------响应体信息-----------");
				logger.info("响应体:{}",this.responseXml);
			}
		} catch (ClientProtocolException e) {
			e.printStackTrace();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public String getResponseXml() {
		return responseXml;
	}
	public Map<String, String> getHeaderMap() {
		return headerMap;
	}
}
