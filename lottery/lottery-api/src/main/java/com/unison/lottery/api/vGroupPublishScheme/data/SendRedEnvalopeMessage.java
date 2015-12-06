package com.unison.lottery.api.vGroupPublishScheme.data;

import java.io.IOException;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

public class SendRedEnvalopeMessage {
	public long envalopeId;

	public String hxUserName;
	
	public String target_type;
	
	public String[] target;
	
	public String from;
	
	public ExtData ext;
	
	public Message msg;
	
	public static String toJsonString(Object publishScheme) {
		ObjectMapper mapper = new ObjectMapper();
		String jsonString = null;
		try {
//			jsonString = mapper.defaultPrettyPrintingWriter().writeValueAsString(publishScheme);
			jsonString = mapper.writeValueAsString(publishScheme);
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
