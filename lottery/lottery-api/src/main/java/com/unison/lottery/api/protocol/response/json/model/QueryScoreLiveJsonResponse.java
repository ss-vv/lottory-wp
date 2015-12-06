package com.unison.lottery.api.protocol.response.json.model;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

import com.unison.lottery.api.callBack.model.BasketballMatchMessage;
import com.unison.lottery.api.callBack.model.FootballMatchMessage;
import com.unison.lottery.api.protocol.common.SystemStatusKeyNames;
import com.unison.lottery.api.protocol.response.model.Response;
import com.unison.lottery.api.query.data.Finish;
import com.unison.lottery.api.query.data.NoFinish;
import com.unison.lottery.api.query.data.ResultList;

public class QueryScoreLiveJsonResponse extends Response{

	/**
	 * 转化给客户端需要的参数，减少不必要的参数传递
	 */
	public String toJsonString(Response queryScoreLiveJsonResponse) {
		ObjectMapper mapper = new ObjectMapper();
		QueryScoreLiveResponseToJson toJson = makeResponseJsonData(queryScoreLiveJsonResponse);
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

	private QueryScoreLiveResponseToJson makeResponseJsonData(Response response) {
		QueryScoreLiveResponseToJson toJson = new QueryScoreLiveResponseToJson();
		toJson.setName(response.name);
		toJson.setDesc(response.desc);
		toJson.setStatus(response.status);
		toJson.setMatchType(response.queryScoreLiveInfoResponse.matchType);
		ResultList resultList = new ResultList();
		Map<String, ArrayList<BasketballMatchMessage>> basMap = response.queryScoreLiveInfoResponse.getBasketInfos();
		Map<String, ArrayList<FootballMatchMessage>> footMap = response.queryScoreLiveInfoResponse.getFootInfos();
		
		Finish finish = new Finish();
		NoFinish noFinish = new NoFinish();
		if(basMap != null && !basMap.isEmpty()){
			finish.setBasketList(basMap.get(SystemStatusKeyNames.FINISH));
			noFinish.setBasketList(basMap.get(SystemStatusKeyNames.NO_FINISH));
			resultList.setFinish(finish);
			resultList.setNoFinish(noFinish);
		}
		if(footMap != null && !footMap.isEmpty()){
			finish.setFootList(footMap.get(SystemStatusKeyNames.FINISH));
			noFinish.setFootList(footMap.get(SystemStatusKeyNames.NO_FINISH));
			resultList.setFinish(finish);
			resultList.setNoFinish(noFinish);
		}
		toJson.setResultList(resultList);
		return toJson;
	}

	//内部类

    class QueryScoreLiveResponseToJson {
        private String name;
        private String status;
        private String desc;
        private String matchType;
        private ResultList resultList;
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
		public ResultList getResultList() {
			return resultList;
		}
		public void setResultList(ResultList resultList) {
			this.resultList = resultList;
		}
		public String getMatchType() {
			return matchType;
		}
		public void setMatchType(String matchType) {
			this.matchType = matchType;
		}
        
        
    }
}
