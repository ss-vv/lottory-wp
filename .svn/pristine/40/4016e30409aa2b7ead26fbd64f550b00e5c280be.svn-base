package com.unison.lottery.weibo.dataservice.commons.download;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.nio.charset.UnsupportedCharsetException;
import java.util.EnumMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.UUID;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.entity.ContentType;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.CharArrayBuffer;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.unison.lottery.weibo.dataservice.commons.constants.DataInterfaceName;
import com.unison.lottery.weibo.dataservice.commons.model.DataInterfaceResponse;
import com.unison.lottery.weibo.dataservice.commons.util.CharSetDecider;

@Service
public class DownloadServiceImpl implements DowloadService {

	
	private Map<DataInterfaceName,String> urlMap;
	
	@Autowired
	private CharSetDecider charSetDecider;
	

	private String storePath="/dataInterface";

	private Logger logger=LoggerFactory.getLogger(getClass());
	
	@Override
	public DataInterfaceResponse downloadToString(DataInterfaceName dataInterfaceName) {
		
		
		return downloadToStringWithExtendParams(dataInterfaceName,null);
	}



	private HttpEntity connect(String url) {
		HttpEntity entity=null;
		if(StringUtils.isNotBlank(url)){
			HttpGet httpGet=new HttpGet(url);
//			httpGet.addHeader("user-agent","Mozilla/5.0 (Windows; U; MSIE 7.0; Windows NT 6.0)" );
			httpGet.addHeader("user-agent","Mozilla/5.0 (Windows NT 6.1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/33.0.1750.146 Safari/537.36"+UUID.randomUUID() );
			
			
			HttpClient httpclient = new DefaultHttpClient();
			httpclient.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, 50000);
			httpclient.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT, 50000);
			try {
				HttpResponse response = httpclient.execute(httpGet);
				entity= response.getEntity();
				
			} catch (ClientProtocolException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}catch(Throwable e){
				e.printStackTrace();
			}
		}
		return entity;
	}

	private String decideUrlFromName(DataInterfaceName dataInterfaceName) {
		String url=null;
		if(null!=urlMap&&!urlMap.isEmpty()){
			url=urlMap.get(dataInterfaceName);
		}
		return url;
	}

	public Map<DataInterfaceName,String> getUrlMap() {
		return urlMap;
	}

	public void setUrlMap(Map<DataInterfaceName,String> urlMap) {
		this.urlMap = new EnumMap<DataInterfaceName, String>( urlMap);
	}

	

	@Override
	public DataInterfaceResponse downloadToFile(
			DataInterfaceName dataInterfaceName) {
		
		
		return downloadToFileWithExtendParams(dataInterfaceName,null);
	}

	private String makeFileName(DataInterfaceName dataInterfaceName) {
		return storePath+File.separator+dataInterfaceName.toString()+File.separator+System.currentTimeMillis()+".txt";
	}

	@Override
	public DataInterfaceResponse downloadToStringWithExtendParams(
			DataInterfaceName dataInterfaceName,
			Map<String, String> extendParams) {
		DataInterfaceResponse result=new DataInterfaceResponse();		
		String baseUrl=decideUrlFromName(dataInterfaceName);
		String finalUrl = makeFinalUrl(extendParams, baseUrl);
		logger.debug("finalUrl={}",finalUrl);
		HttpEntity entity=connect(finalUrl);
		if(null!=entity){
			try {
				String forcedCharset = charSetDecider.decideCharsetFromName(dataInterfaceName);
				String responseStr = entityToString(entity, forcedCharset);
				if(StringUtils.isNotBlank(responseStr)){
					result.setSucc(true);
					result.setResponseStr(responseStr);
				}
			} catch (ParseException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		logger.debug("result:{}",result);
		return result;
	}

	/**
	 * 强制用指定的字符编码读取 http entity 内容。<br/>
	 * copy from {@link EntityUtils.toString()}
	 * 
	 * @param entity 实体
	 * @param forcedCharset 强制使用的字符集，如果为 null 会自动猜测字符集。
	 * @return 解码后的string对象。
	 * @throws IOException
	 * @throws ParseException
	 */
	private String entityToString(HttpEntity entity, String forcedCharset) 
			throws IOException, ParseException {
        if (entity == null) {
            throw new IllegalArgumentException("HTTP entity may not be null");
        }
        InputStream instream = entity.getContent();
        if (instream == null) {
            return null;
        }
        try {
            if (entity.getContentLength() > Integer.MAX_VALUE) {
                throw new IllegalArgumentException("HTTP entity too large to be buffered in memory");
            }
            int i = (int)entity.getContentLength();
            if (i < 0) {
                i = 4096;
            }
            Charset charset = Charset.forName(forcedCharset);
            if (charset == null){
	            try {
	                ContentType contentType = ContentType.get(entity);
	                if (contentType != null) {
	                    charset = contentType.getCharset();
	                }
	            } catch (final UnsupportedCharsetException ex) {
	                throw new UnsupportedEncodingException(ex.getMessage());
	            }
	            if (charset == null) {
	                charset = HTTP.DEF_CONTENT_CHARSET;
	            }
            }
            Reader reader = new InputStreamReader(instream, charset);
            CharArrayBuffer buffer = new CharArrayBuffer(i);
            char[] tmp = new char[1024];
            int l;
            while((l = reader.read(tmp)) != -1) {
                buffer.append(tmp, 0, l);
            }
            return buffer.toString();
        } finally {
            instream.close();
        }
	}
	
	@Override
	public DataInterfaceResponse downloadToFileWithExtendParams(
			DataInterfaceName dataInterfaceName,
			Map<String, String> extendParams) {
		DataInterfaceResponse result=new DataInterfaceResponse();
		String baseUrl=decideUrlFromName(dataInterfaceName);
		String finalUrl = makeFinalUrl(extendParams, baseUrl);
		logger.debug("finalUrl={}",finalUrl);
		HttpEntity entity=connect(finalUrl);
		
		if(null!=entity){
			try {
				byte[] bytes=EntityUtils.toByteArray(entity);
				String fileName=makeFileName(dataInterfaceName);
				File file=new File(fileName);
				FileUtils.writeByteArrayToFile(file, bytes);
//				OutputStream os=new FileOutputStream(new File(fileName));
//				entity.writeTo(os);
//				String responseStr=EntityUtils.toString(entity,decideCharsetFromName(dataInterfaceName));
				if(file.exists()){
					result.setSucc(true);
					result.setResponseFile(file);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		logger.debug("result:{}",result);
		return result;
	}

	private String makeFinalUrl(Map<String, String> extendParams, String baseUrl) {
		String finalUrl=baseUrl;
		if(null!=extendParams&&!extendParams.isEmpty()){
			int size=extendParams.size();
			StringBuffer sb=new StringBuffer(finalUrl);
			sb.append("?");
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
				finalUrl=sb.toString();
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
				finalUrl=null;
			}
		}
		return finalUrl;
	}

	public CharSetDecider getCharSetDecider() {
		return charSetDecider;
	}

	public void setCharSetDecider(CharSetDecider charSetDecider) {
		this.charSetDecider = charSetDecider;
	}

}
