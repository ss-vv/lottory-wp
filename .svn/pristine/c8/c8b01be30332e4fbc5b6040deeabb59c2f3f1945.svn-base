package com.xhcms.lottery.pb.util;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.xhcms.lottery.pb.xml.model.Msg;

/**
 * 获取Msg类的JAXBContext对象，单例模式
 * @author Hassan
 *
 */
public class JAXBContextUtil {
	private static Logger logger = LoggerFactory.getLogger(JAXBContextUtil.class);
	private static JAXBContext jaxbContext;
	public static JAXBContext getJaxbContext(){
		if(null == jaxbContext){
			try {
				jaxbContext = JAXBContext.newInstance(Msg.class);
			} catch (JAXBException e) {
				e.printStackTrace();
				logger.error("JAXBContext 实例化错误，GetJaxbContextUtil:getJaxbContext",e);
			}
			return jaxbContext;
		} else {
			return jaxbContext;
		}
	}
}
