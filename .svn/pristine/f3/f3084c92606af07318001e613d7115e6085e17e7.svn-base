package com.unison.lottery.api.framework.log.model;

import java.io.IOException;
import java.io.StringWriter;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.unison.lottery.api.lang.JAXBContextHolder;

@XmlRootElement
@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
public class ActivityLog {
	@XmlElement
	public String time;
	@XmlElement
	public String userId;

	@XmlElement
	public String clientVersion;
	@XmlElement
	public String methodName;
	@XmlElement
	public String methodType;

	@XmlElement
	public String modelName;

	@XmlElement
	public RequestContent requestContent;

	@XmlElement
	public ResponseContent responseContent;

	@XmlElement
	public String validId;

	@XmlElement
	public String deviceId;

	@XmlElement
	public String channel;

	public String toXmlString() {
		String result = "";
		JAXBContext context;
		try {
			context = JAXBContextHolder.getInstance(ActivityLog.class);
			Marshaller m = context.createMarshaller();
			m.setProperty(Marshaller.JAXB_ENCODING, "utf-8");
			m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
			m.setProperty(Marshaller.JAXB_FRAGMENT, true);
			StringWriter sw = new StringWriter();
			m.marshal(this, sw);
			result = sw.toString();
		} catch (JAXBException e) {
			e.printStackTrace();
		}
		return result;
	}
	
	public static String toJsonString(ActivityLog activityLog) {
		ObjectMapper mapper = new ObjectMapper();
		String jsonString = null;
		try {
			jsonString = mapper.defaultPrettyPrintingWriter().writeValueAsString(activityLog);
		} catch (JsonGenerationException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return jsonString;
	}
}
