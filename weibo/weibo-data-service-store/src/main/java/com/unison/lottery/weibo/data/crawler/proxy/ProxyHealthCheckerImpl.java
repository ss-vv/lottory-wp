package com.unison.lottery.weibo.data.crawler.proxy;

import java.io.IOException;
import java.net.URI;
import java.util.Map;

import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.HttpVersion;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.conn.params.ConnRoutePNames;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;

import com.unison.lottery.weibo.data.crawler.proxy.query.dao.entity.ProxyPO;



public class ProxyHealthCheckerImpl implements ProxyHealthChecker {

	private String defaultCheckUrl;
	

	@Override
	public boolean check(ProxyPO proxy,String checkUrl,Map<String, String> header) {
		boolean result=false;
		if(null!=proxy){
			HttpClient client = defaultHttpClient(); 
			HttpHost httpHost = new HttpHost(proxy.getIp(), Integer.parseInt(proxy.getPort().trim()));  
			client.getParams().setParameter(ConnRoutePNames.DEFAULT_PROXY, httpHost);  
			HttpGet httpGet = new HttpGet(checkUrl); 
//			// determines the timeout in milliseconds until a connection is established.  
//			httpGet.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, 1000);  
//			// defines the socket timeout (SO_TIMEOUT) in milliseconds, which is the timeout for waiting for data or, put differently, a maximum period inactivity between two consecutive data packets).  
//			httpGet.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT, 1000);
			makeHttpHeader(httpGet,header);
			try {
				HttpResponse response = client.execute(httpGet);
				int statusCode = response.getStatusLine().getStatusCode();
				if(statusCode==HttpStatus.SC_OK){
					result=true;
				}
			} catch (ClientProtocolException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}  
		}
		return result;
	}
	
	private HttpClient defaultHttpClient(){
	    HttpParams localBasicHttpParams = new BasicHttpParams();
	    HttpProtocolParams.setVersion(localBasicHttpParams, HttpVersion.HTTP_1_1);
	    HttpProtocolParams.setContentCharset(localBasicHttpParams, "UTF-8");
	    HttpProtocolParams.setUseExpectContinue(localBasicHttpParams, true);
	    HttpConnectionParams.setConnectionTimeout(localBasicHttpParams, 1000);
	    HttpConnectionParams.setSoTimeout(localBasicHttpParams, 1000);
	    
	    return new DefaultHttpClient(localBasicHttpParams);
	}

	private void makeHttpHeader(HttpGet httpGet, Map<String, String> header) {
		// TODO Auto-generated method stub
		if(header!=null&&!header.isEmpty()){
			if(header.get("User-Agent")!=null){
				httpGet.setHeader("User-Agent", header.get("User-Agent"));
			}
			if(header.get("Accept-Encoding")!=null){
				httpGet.setHeader("Accept-Encoding", header.get("Accept-Encoding"));
			}
		}
		
	}

	public String getDefaultCheckUrl() {
		return defaultCheckUrl;
	}

	public void setDefaultCheckUrl(String defaultCheckUrl) {
		this.defaultCheckUrl = defaultCheckUrl;
	}

}
