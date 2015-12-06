package com.xhcms.lottery.commons.client;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HttpClient {

    protected Logger log = LoggerFactory.getLogger(getClass());

    private String charset = HTTP.UTF_8;
    
    private int timeout = 60000; // 60 seconds. reading/socket timeout
    
    public HttpClient(){}
    
    /**
     * @param timeoutMS "http.socket.timeout" 读socket超时。单位毫秒
     * @author Yang Bo
     */
    public HttpClient(int timeoutMS){
    	this.timeout = timeoutMS;
    }
    
    public String post(String url, String msg) {
        try {
            return post(url, new StringEntity(msg, charset));
        } catch (UnsupportedEncodingException e) {
            log.warn(e.getMessage());
        }
        return null;
    }

    public String post(String url, Map<String, Object> params) {
        try {
            return post(url, new UrlEncodedFormEntity(wrapParams(params), charset));
        } catch (UnsupportedEncodingException e) {
            log.warn(e.getMessage());
        }
        return null;
    }

    /**
     * 发送 POST 的 form-encoded 消息。
     * @author Yang Bo
     */
    public String postNoEncodingWithContentType(String url, Map<String,Object> params, String contentType) {
    	try {
    		StringEntity entity = new StringEntity(wrapParamsToString(params), charset);
    		entity.setContentType(contentType);
            return post(url, entity);
        } catch (UnsupportedEncodingException e) {
            log.warn(e.getMessage());
        }
        return null;
    }
    
    private String post(String url, HttpEntity entity) {
        HttpPost post = new HttpPost(url);
        try {
            post.setEntity(entity);
            DefaultHttpClient httpClient = new DefaultHttpClient();
            httpClient.getParams().setParameter("http.socket.timeout", new Integer(this.timeout));
            return httpClient.execute(post, new DefaultResponseHandler());
        } catch (ClientProtocolException e) {
            log.warn(e.getMessage());
        } catch (IOException e) {
            log.warn(e.getMessage(), e);
        }

        return null;
    }

    public String get(String url, Map<String, Object> params) {
        String queryString = URLEncodedUtils.format(wrapParams(params), HTTP.UTF_8);
        HttpGet request = new HttpGet(wrapUrl(url, queryString));
        DefaultHttpClient client = new DefaultHttpClient();
        String responseBody = null;
        try {
            responseBody = client.execute(request, new DefaultResponseHandler());
        } catch (IOException e) {
            log.warn(e.getMessage());
        } finally {
            if (request != null) {
                request.abort();
            }
        }
        return responseBody;
    }

    private String wrapParamsToString(Map<String, Object> params) {
    	if (params == null || params.size() == 0) {
    		return StringUtils.EMPTY;
    	}
    	StringBuilder builder = new StringBuilder();
    	for (String key : params.keySet()) {
            Object value = params.get(key);
            if (builder.length()>0){
            	builder.append("&");
            }
            builder.append(key).append("=").append(value);
        }
    	return builder.toString();
    }
    
    @SuppressWarnings("unchecked")
    private List<NameValuePair> wrapParams(Map<String, Object> params) {
        if (params == null || params.size() == 0) {
            return Collections.EMPTY_LIST;
        }

        List<NameValuePair> pairs = new ArrayList<NameValuePair>(params.size());

        for (String key : params.keySet()) {
            Object value = params.get(key);
            // TODO:以后可以增加对POJO的处理
            // 处理集合类
            if (value instanceof Collection) {
                for (Object v : (Collection<?>) value) {
                    pairs.add(new BasicNameValuePair(key, v.toString()));
                }
            } else {
                pairs.add(new BasicNameValuePair(key, String.valueOf(value)));
            }
        }

        return pairs;
    }

    private String wrapUrl(String url, String queryString) {
        return new StringBuilder(512).append(url).append(url.lastIndexOf('?') == -1 ? "?" : "&").append(queryString)
                .toString();
    }

    public String getCharset() {
        return charset;
    }

    public void setCharset(String charset) {
        this.charset = charset;
    }

    private class DefaultResponseHandler implements ResponseHandler<String> {
        @Override
        public String handleResponse(org.apache.http.HttpResponse response) throws ClientProtocolException, IOException {
            String chaset = EntityUtils.getContentCharSet(response.getEntity());
            if (chaset == null) {
                chaset = HTTP.UTF_8;
            }
            return EntityUtils.toString(response.getEntity(), chaset);
        }
    }

	public String post(String finalUrl) {
		 try {
			log.debug("finalUrl={}",finalUrl);
			URL url = new URL(finalUrl);
//			URI uri = new URI(url.getProtocol(), url.getHost(), url.getPath(), url.getQuery(), null);
			URI uri = new URI(url.getProtocol(),null,url.getHost(),url.getPort(),url.getPath(),url.getQuery(),null);
			log.debug("uri={}",uri);
			HttpPost post = new HttpPost(uri);
       
            DefaultHttpClient httpClient = new DefaultHttpClient();
            httpClient.getParams().setParameter("http.socket.timeout", new Integer(this.timeout));
            HttpResponse httpResonse = httpClient.execute(post);
            String responseString=EntityUtils.toString(httpResonse.getEntity(), "gb2312");
            log.debug("responseString={}",responseString);
            return responseString;
        } catch (ClientProtocolException e) {
            log.warn(e.getMessage());
        } catch (IOException e) {
            log.warn(e.getMessage(), e);
        } catch (URISyntaxException e) {
			e.printStackTrace();
			log.warn(e.getMessage(), e);
		}

        return null;
		
	}

	public String postWithEncoding(String interfaceUrl,
			Map<String, Object> params, String postEncoding) {
		String result=null;
		try {
			result= post(interfaceUrl, new UrlEncodedFormEntity(wrapParams(params), postEncoding));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			log.warn(e.getMessage());
		}
		return result;
	}

	public String postStringEntityWithEncodingAndContentType(String interfaceUrl,
			String finalParams, String postEncoding, String contentType,String responseEncoding) {
		if(StringUtils.isNotBlank(interfaceUrl)&&StringUtils.isNotBlank(finalParams)){
			HttpPost post = new HttpPost(interfaceUrl);
	        try {
	        	StringEntity entity=new StringEntity(finalParams, postEncoding);
	        	entity.setContentType(contentType);
	            post.setEntity(entity);
	            DefaultHttpClient httpClient = new DefaultHttpClient();
	            httpClient.getParams().setParameter("http.socket.timeout", new Integer(this.timeout));
	            HttpResponse httpResonse = httpClient.execute(post);
	            String response=EntityUtils.toString(httpResonse.getEntity(), responseEncoding);
	            log.debug("interfaceUrl:{},finalParams:{},response{}",new Object[]{interfaceUrl,finalParams,response});
	            return response;
	        } catch (ClientProtocolException e) {
	            log.warn(e.getMessage());
	        } catch (IOException e) {
	            log.warn(e.getMessage(), e);
	        }
		}
	    return null;
	}
}
