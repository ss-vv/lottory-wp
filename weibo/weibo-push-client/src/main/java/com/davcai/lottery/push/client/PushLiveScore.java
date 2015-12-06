package com.davcai.lottery.push.client;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.davcai.lottery.push.common.model.BasketballMatchMessage;
import com.davcai.lottery.push.common.model.FootballMatchMessage;

@JsonIgnoreProperties(ignoreUnknown=true) 
@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
public class PushLiveScore {

	public String type;
	
	public String subType;
	
	public String stateDesc;
	
	public Integer playingTime;
		
	public FootballMatchMessage footballMatchMessage;
	
	public BasketballMatchMessage basketballMatchMessage;
	
	public String matchType;//直播类型：1.竞彩
}
