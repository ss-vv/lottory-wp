package com.unison.lottery.api.protocol.response.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.unison.lottery.api.callBack.model.BasketballMatchMessage;
import com.unison.lottery.api.callBack.model.FootballMatchMessage;

public class QueryScoreLiveInfoResponse extends HaveReturnStatusResponse{

	private Map<String, ArrayList<FootballMatchMessage>> footInfos;
	private Map<String,ArrayList<BasketballMatchMessage>> basketInfos;
	public String matchType;
	public Map<String, ArrayList<FootballMatchMessage>> getFootInfos() {
		return footInfos;
	}
	public void setFootInfos(Map<String, ArrayList<FootballMatchMessage>> footInfos) {
		this.footInfos = footInfos;
	}
	public Map<String, ArrayList<BasketballMatchMessage>> getBasketInfos() {
		return basketInfos;
	}
	public void setBasketInfos(Map<String, ArrayList<BasketballMatchMessage>> basketInfos) {
		this.basketInfos = basketInfos;
	}
	
	
	
	
}
