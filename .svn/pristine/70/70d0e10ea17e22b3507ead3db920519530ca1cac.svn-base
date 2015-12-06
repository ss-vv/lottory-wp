/**
 * 
 */
package com.xhcms.ucenter.sso.ticket.impl;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.xhcms.ucenter.sso.ticket.IService;

/**
 * @author bean
 *
 */
public class Service implements IService {
	
	private Logger logger = LoggerFactory.getLogger(getClass());
	
	/**
	 * 服务的ID
	 */
	private String id;
	
	/**
	 * 每个服务器中的存储扩展属性
	 */
	private Map<String, Object> attributes = new HashMap<String, Object>();
	
	public Service(String id) {
		this.id = id;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@Override
	public boolean logOutOfService() {
		
		String url = getId();
		
		HttpPost post = new HttpPost(url);
		
		List<NameValuePair> nvps=new ArrayList<NameValuePair>();
		nvps.add(new BasicNameValuePair("ticket", (String)getAtrribute(IService.TICKET)));
		nvps.add(new BasicNameValuePair("logoutRequest", "logout"));
		
		try {
			post.setEntity(new UrlEncodedFormEntity(nvps,"utf-8"));
		} catch (UnsupportedEncodingException exp) {
			exp.printStackTrace();
		}
		
		HttpClient client = new DefaultHttpClient();
		logger.info("执行单点登录退出，url={}",url);
		
		try {
			boolean result = client.execute(post, new ResponseHandler<Boolean>() {
				@Override
				public Boolean handleResponse(HttpResponse response)
						throws ClientProtocolException, IOException {
					return true;
				}
			});
			
			return result;
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return false;
	}

	@Override
	public Object getAtrribute(String name) {
		return attributes.get(name);
	}

	@Override
	public void setAttribute(String name, Object value) {
		attributes.put(name, value);
	}

	@Override
	public Map<String, Object> getAttributes() {
		return Collections.unmodifiableMap(attributes);
	}
}
