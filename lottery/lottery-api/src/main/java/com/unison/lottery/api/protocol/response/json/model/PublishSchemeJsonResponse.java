package com.unison.lottery.api.protocol.response.json.model;

import java.io.IOException;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

import com.unison.lottery.api.protocol.response.model.Response;

public class PublishSchemeJsonResponse extends Response{

	/**
	 * 转化给客户端需要的参数，减少不必要的参数传递
	 */
	public String toJsonString(Response PublishSchemeJsonResponse) {
		ObjectMapper mapper = new ObjectMapper();
		PublishSchemeResponseToJson toJson = makeResponseJsonData(PublishSchemeJsonResponse);
		String jsonString = null;
		try {
			jsonString = mapper.writeValueAsString(toJson);
		} catch (JsonGenerationException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return jsonString;
	}

	private PublishSchemeResponseToJson makeResponseJsonData(Response groupMembers) {
		PublishSchemeResponseToJson toJson = new PublishSchemeResponseToJson();
		toJson.setName(groupMembers.name);
		toJson.setDesc(groupMembers.desc);
		toJson.setStatus(groupMembers.status);
		return toJson;
	}

	//内部类

    class PublishSchemeResponseToJson {
        private String name;
        private String status;
        private String desc;
		
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		public String getStatus() {
			return status;
		}
		public void setStatus(String status) {
			this.status = status;
		}
		public String getDesc() {
			return desc;
		}
		public void setDesc(String desc) {
			this.desc = desc;
		}

    }
}
