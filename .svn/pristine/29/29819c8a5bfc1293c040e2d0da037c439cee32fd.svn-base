package com.unison.lottery.weibo.dataservice.commons.crawler.util;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.Header;
import org.apache.http.HeaderElement;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.HttpVersion;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.GzipDecompressingEntity;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.params.ConnRoutePNames;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.PoolingClientConnectionManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.unison.lottery.weibo.dataservice.commons.crawler.model.HttpResult;
import com.unison.lottery.weibo.dataservice.commons.crawler.model.Proxy;

@Component
public class HttpUtilImpl implements HttpUtil {
	private static final String UTF_8 = "utf-8";
	private static final String USER_AGENT = "User-Agent";
	private static final String ACCEPT_ENCODING = "Accept-Encoding";
	
	private HttpClient httpclient;
	private HttpContext httpContext;
	private Logger logger = LoggerFactory.getLogger(HttpUtilImpl.class);

	private static HttpParams httpParams;

	private static PoolingClientConnectionManager cm;
	/**
	 * 最大连接数
	 */
	public final static int MAX_TOTAL_CONNECTIONS = 80;
	/**
	 * 获取连接的最大等待时间
	 */
	public final static int WAIT_TIMEOUT = 60000;
	/**
	 * 每个路由最大连接数
	 */
	public final static int MAX_ROUTE_CONNECTIONS = 40;
	/**
	 * 连接超时时间
	 */
	public final static int CONNECT_TIMEOUT = 10000;
	/**
	 * 读取超时时间
	 */
	public final static int READ_TIMEOUT = 60000;

	static {
		httpParams = new BasicHttpParams();
		
		// 设置访问协议
		SchemeRegistry schreg = new SchemeRegistry();
		schreg.register(new Scheme("http", 80, PlainSocketFactory
				.getSocketFactory()));
		schreg.register(new Scheme("https", 443, SSLSocketFactory
				.getSocketFactory()));
		cm = new PoolingClientConnectionManager(schreg);
		// 设置最大连接数
		cm.setMaxTotal(MAX_TOTAL_CONNECTIONS);
		// 设置每个主机的最大并行连接数
		cm.setDefaultMaxPerRoute(MAX_ROUTE_CONNECTIONS);
		
		HttpProtocolParams.setVersion(httpParams,
				HttpVersion.HTTP_1_1);
		HttpProtocolParams.setContentCharset(httpParams, "UTF-8");
		HttpProtocolParams.setUseExpectContinue(httpParams, true);
	}

	@Override
	public HttpResult httpGet(String finalUrl, Proxy proxy,
			Map<String, String> header) {
		HttpResult result = new HttpResult();
		httpContext = new BasicHttpContext();
		String responseStr = null;
		if (StringUtils.isNotBlank(finalUrl)) {
			logger.debug("finalUrl:{}", finalUrl);
			logger.debug("proxy:{}", proxy);
			HttpGet httpGet = new HttpGet(finalUrl);
			//获取一个httpClient
			defaultHttpClient();
			if (null != proxy) {
				HttpHost httpHost = new HttpHost(proxy.getIp(),
						Integer.parseInt(proxy.getPort().trim()));
				httpclient.getParams().setParameter(
						ConnRoutePNames.DEFAULT_PROXY, httpHost);
			}
			makeRequestHeader(header, httpGet);
			HttpResponse response = null;
			try {
				response = httpclient.execute(httpGet,this.httpContext);
				logger.debug("http response:{}", response);
				int statusCode = response.getStatusLine().getStatusCode();
				logger.debug("statusCode:{}", statusCode);
				if (statusCode == HttpStatus.SC_OK) {
					responseStr = entityToString(response);
					result.setResponseStr(responseStr);
					result.setSucc(true);
				} else {
					result.setStatusCode(statusCode);
					result.setSucc(false);
				}
				
				logger.debug("http responseStr:{}", responseStr);

			} catch (ClientProtocolException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				httpGet.abort();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				httpGet.abort();
			}finally{
				if(response!=null){
					try {
						response.getEntity().getContent().close();
					} catch (Exception e) {
						logger.error("",e);
					}
				}
			}

		}
		return result;
	}

	private void makeRequestHeader(Map<String, String> header, HttpGet request) {
		if (header != null) {
			if (null != header.get(USER_AGENT)) {
				request.addHeader(USER_AGENT, header.get(USER_AGENT));
			}
			if (header.get(ACCEPT_ENCODING) != null) {
				request.addHeader(ACCEPT_ENCODING, header.get(ACCEPT_ENCODING));
			}
		}
	}

	private void defaultHttpClient() {
		httpclient = new DefaultHttpClient(cm,httpParams);
		httpclient.getParams().setParameter(
				CoreConnectionPNames.CONNECTION_TIMEOUT, CONNECT_TIMEOUT);// 连接时间20s
		httpclient.getParams().setParameter(
				CoreConnectionPNames.SO_TIMEOUT, READ_TIMEOUT);// 数据传输时间60s
	}

	private String entityToString(HttpResponse response) throws IOException {
		String responseStr;
		HttpEntity entity = response.getEntity();

		boolean isGzip = decideIsGZip(entity);
		if (isGzip) {
			responseStr = EntityUtils.toString(new GzipDecompressingEntity(
					response.getEntity()), UTF_8);
		} else {
			responseStr = EntityUtils.toString(response.getEntity(), UTF_8);
		}
		//释放http链接
		EntityUtils.consume(entity);
		return responseStr;
	}

	private boolean decideIsGZip(HttpEntity entity) {
		boolean isGzip = false;
		if (entity != null) {
			Header ceheader = entity.getContentEncoding();
			if (null != ceheader && null != ceheader.getElements()
					&& ceheader.getElements().length > 0)
				for (HeaderElement element : ceheader.getElements()) {
					if (element.getName().equalsIgnoreCase("gzip")) {
						isGzip = true;
						break;
					}
				}
		}
		return isGzip;
	}

	@Override
	public HttpResult httpPost(String finalUrl,Map<String, String> params, Proxy proxy) throws IOException {
		HttpResult result = null;
		String responseStr = null;
		if (StringUtils.isNotBlank(finalUrl)) {
			logger.debug("finalUrl:{}", finalUrl);
			logger.debug("proxy:{}", proxy);
			HttpPost httpPost = new HttpPost(finalUrl);
			defaultHttpClient();
			if (null != proxy) {
				HttpHost httpHost = new HttpHost(proxy.getIp(),
						Integer.parseInt(proxy.getPort().trim()));
				httpclient.getParams().setParameter(
						ConnRoutePNames.DEFAULT_PROXY, httpHost);
			}
			UrlEncodedFormEntity encodedFormEntity = makeUrlEncodeFormEntity(params);
			if(encodedFormEntity!=null){
				httpPost.setEntity(encodedFormEntity);
			}
			HttpResponse response = null;
			try {
				httpContext = new BasicHttpContext();
				response = httpclient.execute(httpPost,httpContext);
				int statusCode = response.getStatusLine().getStatusCode();
				logger.debug("statusCode:{}", statusCode);
				if (statusCode == HttpStatus.SC_OK) {
					responseStr = entityToString(response);
					result = new HttpResult();
					result.setResponseStr(responseStr);
					result.setSucc(true);

				}
				logger.debug("http response:{}", responseStr);
			} catch (ClientProtocolException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				httpPost.abort();
				throw e;
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				httpPost.abort();
				throw e;
			}finally{
				if(response!=null){
					try {
						response.getEntity().getContent().close();
					} catch (Exception e) {
						logger.error("",e);
					}
				}
			}

		}
		return result;
	}

	private UrlEncodedFormEntity makeUrlEncodeFormEntity(
			Map<String, String> params) {
		List<NameValuePair> formparams = new ArrayList<>();
		Iterator<String> keys = params.keySet().iterator();
		while (keys.hasNext()) {
			String key = keys.next();
			formparams.add(new BasicNameValuePair(key, params.get(key)));
		}
		try {
			return new UrlEncodedFormEntity(formparams,"UTF-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

}
