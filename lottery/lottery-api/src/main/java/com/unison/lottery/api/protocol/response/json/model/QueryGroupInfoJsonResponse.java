package com.unison.lottery.api.protocol.response.json.model;

import java.io.IOException;
import java.util.List;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

import com.unison.lottery.api.protocol.response.model.Response;
import com.unison.lottery.api.vGroup.data.Group;

public class QueryGroupInfoJsonResponse extends Response {
	
	/**
	 * 转化给客户端需要的参数，减少不必要的参数传递
	 */
	public String toJsonString(Response queryGroupInfoJsonResponse) {
		ObjectMapper mapper = new ObjectMapper();
		QueryGroupInfoResponseToJson toJson = makeResponseJsonData(queryGroupInfoJsonResponse);
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

	private QueryGroupInfoResponseToJson makeResponseJsonData(Response queryGroupInfoJsonResponse) {
		QueryGroupInfoResponseToJson toJson = new QueryGroupInfoResponseToJson();
		toJson.setName(queryGroupInfoJsonResponse.name);
		toJson.setDesc(queryGroupInfoJsonResponse.desc);
		toJson.setStatus(queryGroupInfoJsonResponse.status);
		toJson.setGroupList(queryGroupInfoJsonResponse.queryGroupInfoResponse.getGroupList());
		return toJson;
	}

	//内部类

    class QueryGroupInfoResponseToJson {
        private String name;
        private String status;
        private String desc;
        private List<Group> groupList;
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
		public List<Group> getGroupList() {
			return groupList;
		}
		public void setGroupList(List<Group> groupList) {
			this.groupList = groupList;
		}

    }
}
