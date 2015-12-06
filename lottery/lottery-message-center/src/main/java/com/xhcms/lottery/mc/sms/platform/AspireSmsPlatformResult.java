package com.xhcms.lottery.mc.sms.platform;

import java.io.StringReader;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlRootElement;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

@XmlRootElement(name="response")
public class AspireSmsPlatformResult {
	
	private Integer retCode;

	

	public static AspireSmsPlatformResult parseFromXml(String resultString) {
		AspireSmsPlatformResult result = null;
		JAXBContext context;
		try {
			if(StringUtils.isNotBlank(resultString)){
				context = JAXBContext.newInstance(AspireSmsPlatformResult.class);
				Unmarshaller m = context.createUnmarshaller();
				StringReader sw = new StringReader(resultString);
				result = (AspireSmsPlatformResult) m.unmarshal(sw);
			}
			
			
		} catch (JAXBException e) {
			e.printStackTrace();
		}

		return result;
	}



	public Integer getRetCode() {
		return retCode;
	}



	public void setRetCode(Integer retCode) {
		this.retCode = retCode;
	}
	
	public String toString(){
		return ReflectionToStringBuilder.toString(this, ToStringStyle.MULTI_LINE_STYLE);
	}

}
