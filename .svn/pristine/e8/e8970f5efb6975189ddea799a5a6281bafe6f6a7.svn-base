package com.unison.lottery.api.lang;

import java.util.HashMap;
import java.util.Map;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JAXBContextHolder {
	
	private static Map<Class<? extends Object>, JAXBContext> map = 
			new HashMap<Class<? extends Object>, JAXBContext>();
	
	private static final Logger logger = LoggerFactory.getLogger(JAXBContextHolder.class);
	
	private synchronized static JAXBContext createContext(Class<? extends Object> clz) {
		JAXBContext result = null;
		try {
			result = JAXBContext.newInstance(clz);
		} catch (JAXBException e) {
			e.printStackTrace();
			result = null;
		}
		return result;
	}
	
	public static JAXBContext getInstance(Class<? extends Object> clz) {
		JAXBContext jaxbContext = null;
		if(null != clz) {
			jaxbContext = map.get(clz);
			if(null == jaxbContext) {
				long startTime = System.currentTimeMillis();
				jaxbContext = createContext(clz);
				long endTime = System.currentTimeMillis();
				logger.debug("build JAXBContext={}, time millis={}", new Object[]{clz, (endTime - startTime)});
				map.put(clz, jaxbContext);
				logger.debug("创建JAXBContext, ClassesToBeBound={}, map->size={}", 
						new Object[]{clz, map.size()});
			}
		}
		return jaxbContext;
	}
}
