package com.unison.lottery.api.callBack.model;

import java.util.List;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.map.annotate.JsonSerialize;

@JsonIgnoreProperties(ignoreUnknown=true) 
@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
public class LiveScores {

	public String type;
	
	public String subType;
	
	public List<Buyer> buyers;
	
	public String stateDesc;
	
	public int playingTime;
	
	public FootballMatchMessage footballMatchMessage;
	
	public BasketballMatchMessage basketballMatchMessage;
}
