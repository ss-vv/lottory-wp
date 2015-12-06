package com.davcai.lottery.platform.client.qiutan;

import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.davcai.lottery.platform.client.LotteryPlatformBaseResponse;
import com.xhcms.lottery.commons.client.HttpClient;

public abstract class QiutanBaseClient {
	
	private String url="";
	
	public LotteryPlatformBaseResponse doGetDirect(Map<String, Object> params) {
		if(StringUtils.isBlank(url)){
			return new LotteryPlatformBaseResponse();
		}
		HttpClient httpClient=new HttpClient();
		String responseStr="";
		try {
			responseStr=httpClient.get(url, params);
		} catch (Exception e) {
			System.out.println(e);
		}
		return parseResponse(responseStr);
	}
	public LotteryPlatformBaseResponse doPost(Map<String, Object> params) {
		//未实现
		return doGetDirect(params);
	}

	protected abstract LotteryPlatformBaseResponse parseResponse(String responseStr);
	
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
}
