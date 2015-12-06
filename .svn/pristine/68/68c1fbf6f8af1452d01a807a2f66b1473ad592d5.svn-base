package com.davcai.lottery.platform.client.yuanchengchupiao.util;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

public class YuanChengChuPiaoUrlUtil {

	public static String makeFinalParamStringWithSign(String apiId,String messageId, 
			String timestamp, Map<String, Object> params, String secret, String contentKey) {
		String result=null;
		if(StringUtils.isNotBlank(apiId)&&StringUtils.isNotBlank(messageId)&&StringUtils.isNotBlank(timestamp)&&StringUtils.isNotBlank(secret)){
			Map<String,Object> finalMap=new HashMap<String,Object>();
			finalMap.put("ApiId", apiId);
			finalMap.put("MessageID", messageId);
			finalMap.put("TimeStamp", timestamp);
			finalMap.put("Validate", DigestUtils.md5Hex(apiId+secret+timestamp).toUpperCase());
			finalMap.putAll(params);
			ObjectMapper mapper = new ObjectMapper();
			try {
				result= "_data=" + YjBase64Util.encode(mapper.writeValueAsString(finalMap), contentKey);
			} catch (JsonGenerationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (JsonMappingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return result;
	}
}
