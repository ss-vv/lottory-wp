package com.unison.lottery.api.protocol.response.model;



public class QueryImmediateIndexDetailsResponse extends
		HaveReturnStatusResponse {
	private String oddsData;
	private String timestamp;
	public String getOddsData() {
		return oddsData;
	}
	public void setOddsData(String oddsData) {
		this.oddsData = oddsData;
	}
	public String getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}
	
}
