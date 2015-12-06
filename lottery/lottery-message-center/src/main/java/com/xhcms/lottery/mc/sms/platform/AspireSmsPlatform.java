package com.xhcms.lottery.mc.sms.platform;

import java.io.IOException;
import java.net.URI;
import java.security.MessageDigest;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;




public class AspireSmsPlatform {

	private static final int CONNECTION_TIME_OUT = 30000;
	private String password;
	private String signature;
	private String templateId;
	private String userId;
	private String requestUrl;
	private Logger logger=LoggerFactory.getLogger(getClass());

	public AspireSmsPlatformResult send(String phone, String port, String data) {
		AspireSmsPlatformResult result=null;
		AspireSmsPlatformRequest request=new AspireSmsPlatformRequest();
		request.setData(data);
		try {
			
			request.setPassword(hexMD5(password));
			request.setPhone(phone);
			request.setPort(StringUtils.isBlank(port)?"":port);
			request.setSignature(signature);
			request.setTemplateId(templateId);
			request.setUserId(userId);
			String resultString=post(request);
			result=makeResult(resultString);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}

	private AspireSmsPlatformResult makeResult(String resultString) {
		
		
		return AspireSmsPlatformResult.parseFromXml(resultString);
	}

	private String post(AspireSmsPlatformRequest request) {
		String result=null;
		HttpPost post = new HttpPost(requestUrl);
		try {
			String requestString = request.toXml();
			logger.debug("requestXml={}",requestString);
			String ts=String.valueOf(System.currentTimeMillis()/1000);
			logger.debug("ts={}",ts);
			StringEntity entity = new StringEntity(requestString, "utf-8");
			post.setEntity(entity);

			post.setHeader(HTTP.CONTENT_TYPE, "text/xml");
			post.setHeader("Cmd", "mt");
			logger.debug("Cmd=mt");
			post.setHeader("TS",ts);
			String md5Hex=hexMD5(requestString+ts+request.getPassword());
			logger.debug("Authorization={}",md5Hex);
			post.setHeader(
					"Authorization",md5Hex);
			HttpParams httpParameters = new BasicHttpParams();
			HttpConnectionParams.setConnectionTimeout(httpParameters, CONNECTION_TIME_OUT);
			HttpConnectionParams.setSoTimeout(httpParameters, CONNECTION_TIME_OUT);
			HttpClient httpclient = new DefaultHttpClient(httpParameters);

			HttpResponse response = httpclient.execute(post);
			logger.debug("Response status code:{}",response.getStatusLine().getStatusCode());
		    logger.debug("Response body: ");
			result=EntityUtils.toString(response.getEntity());
			logger.debug(result);
		} catch (ClientProtocolException e) {

			e.printStackTrace();
		} catch (IOException e) {

			e.printStackTrace();
		}catch(Exception e){
			e.printStackTrace();
		} 
		
		finally {
			post.abort();
		}
		return result;
	}
	
	public static String hexMD5(String src) throws Exception {
	    MessageDigest md = MessageDigest.getInstance("MD5");
	    byte[] bs = md.digest(src.getBytes("UTF-8"));
	    String r = new String(org.apache.commons.codec.binary.Hex.encodeHex(bs));
	    return r.toUpperCase();
	  }

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getSignature() {
		return signature;
	}

	public void setSignature(String signature) {
		this.signature = signature;
	}

	public String getTemplateId() {
		return templateId;
	}

	public void setTemplateId(String templateId) {
		this.templateId = templateId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getRequestUrl() {
		return requestUrl;
	}

	public void setRequestUrl(String requestUrl) {
		this.requestUrl = requestUrl;
	}

}
