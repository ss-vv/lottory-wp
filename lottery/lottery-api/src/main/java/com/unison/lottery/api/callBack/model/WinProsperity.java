package com.unison.lottery.api.callBack.model;

import java.util.List;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.map.annotate.JsonSerialize;

@JsonIgnoreProperties(ignoreUnknown=true) 
@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
public class WinProsperity {
 
	public String type = "3";//中奖喜报
	
	public List<Buyer> buyers;
}
