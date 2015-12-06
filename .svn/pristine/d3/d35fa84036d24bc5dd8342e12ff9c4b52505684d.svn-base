package com.unison.lottery.api.protocol.response.json.model;

import java.io.IOException;
import java.util.List;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

import com.unison.lottery.api.protocol.response.model.Response;
import com.unison.lottery.api.vGroupMembers.data.GroupUser;

public class QueryGroupMembersJsonResponse extends Response {

	/**
	 * 转化给客户端需要的参数，减少不必要的参数传递
	 */
	public String toJsonString(Response QueryGroupMembersJsonResponse) {
		ObjectMapper mapper = new ObjectMapper();
		QueryGroupMembserResponseToJson toJson = makeResponseJsonData(QueryGroupMembersJsonResponse);
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

	private QueryGroupMembserResponseToJson makeResponseJsonData(Response groupMembers) {
		QueryGroupMembserResponseToJson toJson = new QueryGroupMembserResponseToJson();
		toJson.setName(groupMembers.name);
		toJson.setDesc(groupMembers.desc);
		toJson.setStatus(groupMembers.status);
		toJson.setPage(groupMembers.queryGroupMembersResponse.getPage());
		toJson.setUserList(groupMembers.queryGroupMembersResponse.getUserList());
		toJson.setGroupLeader(groupMembers.queryGroupMembersResponse.getGroupLeader());
		return toJson;
	}

	//内部类

    class QueryGroupMembserResponseToJson {
        private String name;
        private String status;
        private String desc;
        private Integer page;
        private List<GroupUser> userList;
        private GroupUser groupLeader;
        
		public Integer getPage() {
			return page;
		}
		public void setPage(Integer page) {
			this.page = page;
		}
		public List<GroupUser> getUserList() {
			return userList;
		}
		public void setUserList(List<GroupUser> userList) {
			this.userList = userList;
		}
		public GroupUser getGroupLeader() {
			return groupLeader;
		}
		public void setGroupLeader(GroupUser groupLeader) {
			this.groupLeader = groupLeader;
		}
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
