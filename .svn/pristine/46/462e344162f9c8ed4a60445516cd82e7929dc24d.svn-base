package com.unison.lottery.weibo.dataservice.commons.crawler.util;

import java.io.IOException;

import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;
import org.codehaus.jackson.type.JavaType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * json convert to object or object to json
 * 
 * @author baoxing.peng
 * @since 2014-12-30 10:48:32
 */
public class JsonConvertUtil {
	private static ObjectMapper objectMapper = new ObjectMapper();
	private static Logger logger = LoggerFactory.getLogger(JsonConvertUtil.class);

	public static String convertObjectNotNullPropertiesToJsonString(Object objects) {
		objectMapper.getSerializationConfig().setSerializationInclusion(Inclusion.NON_NULL);
		if (objects != null) {
			try {
				return objectMapper.writeValueAsString(objects);
			} catch (IOException e) {
				logger.error("convert to json error:{}",e);
			}
		}
		return null;
	}

	public static Object convertJsonToObject(
			String originJsonData,Class collectionType,Class... elementType) {
		try {
			JavaType javaType = getColectionType(collectionType,elementType);
			return objectMapper.readValue(originJsonData,javaType);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			logger.error("json convert to object error:{}",e);
		}
		return null;
	}

	private static JavaType getColectionType(Class collectionType, Class... elementType) {
		// TODO Auto-generated method stub
		return objectMapper.getTypeFactory().constructParametricType(collectionType, elementType);
	}

	@SuppressWarnings("unchecked")
	public static Object convertJsonToObject(String jsonObject,
			Class elementType) {
		// TODO Auto-generated method stub
		try {
			return objectMapper.readValue(jsonObject, elementType);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			logger.error("json convert to object error:{}",e);
		}
		return null;
	}

}
