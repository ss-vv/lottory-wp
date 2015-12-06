package com.unison.lottery.api.protocol.response.json.model;

import java.io.IOException;
import java.util.List;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

import com.unison.lottery.api.protocol.response.model.Response;
import com.unison.lottery.api.vGroupSysScheme.data.SysScheme;

public class QuerySysSchemesJsonResponse extends Response{

	/**
	 * 转化给客户端需要的参数，减少不必要的参数传递
	 */
	public String toJsonString(Response querySysSchemesJsonResponse) {
		ObjectMapper mapper = new ObjectMapper();
		QuerySysSchemeResponseToJson toJson = makeResponseJsonData(querySysSchemesJsonResponse);
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

	private QuerySysSchemeResponseToJson makeResponseJsonData(Response response) {
		QuerySysSchemeResponseToJson toJson = new QuerySysSchemeResponseToJson();
		toJson.setName(response.name);
		toJson.setDesc(response.desc);
		toJson.setStatus(response.status);
		toJson.setPage(response.querySysSchemeResponse.getPage());
		toJson.setSchemeList(response.querySysSchemeResponse.getSysSchemes());
		
		return toJson;
	}

	//内部类

    class QuerySysSchemeResponseToJson {
        private String name;
        private String status;
        private String desc;
        private Integer page;
        private List<SysScheme> schemeList;
        
		public Integer getPage() {
			return page;
		}
		public void setPage(Integer page) {
			this.page = page;
		}

		public List<SysScheme> getSchemeList() {
			return schemeList;
		}
		public void setSchemeList(List<SysScheme> schemeList) {
			this.schemeList = schemeList;
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
