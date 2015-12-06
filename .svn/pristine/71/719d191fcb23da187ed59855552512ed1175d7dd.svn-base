package com.unison.lottery.api.query.data;

import java.util.List;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.unison.lottery.api.callBack.model.BasketballMatchMessage;
import com.unison.lottery.api.callBack.model.FootballMatchMessage;

@JsonIgnoreProperties(ignoreUnknown=true) 
@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
public class Finish {
	private List<FootballMatchMessage> footList;
	private List<BasketballMatchMessage> basketList;
	public List<FootballMatchMessage> getFootList() {
		return footList;
	}
	public void setFootList(List<FootballMatchMessage> footList) {
		this.footList = footList;
	}
	public List<BasketballMatchMessage> getBasketList() {
		return basketList;
	}
	public void setBasketList(List<BasketballMatchMessage> basketList) {
		this.basketList = basketList;
	}
	
	
}
