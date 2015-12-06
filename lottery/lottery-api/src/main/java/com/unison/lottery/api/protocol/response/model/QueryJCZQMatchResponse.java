package com.unison.lottery.api.protocol.response.model;

import java.util.List;
import java.util.Map;

public class QueryJCZQMatchResponse extends HaveReturnStatusResponse{
	
	List<Map<String,String>> results;

	public List<Map<String, String>> getResults() {
		return results;
	}

	public void setResults(List<Map<String, String>> results) {
		this.results = results;
	}

}
