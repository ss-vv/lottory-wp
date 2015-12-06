package com.unison.lottery.api.callBack.model;

import java.util.List;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@JsonIgnoreProperties(ignoreUnknown=true) 
@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
public class WinAndLiveScores {

	static Logger logger = LoggerFactory.getLogger(WinAndLiveScores.class);
	
	public String type; // 0比分直播 ,3中奖喜报
	
	public String subType;//0竞篮 ,1竞足
	
	public Host host;//主队
	
	public Guest guest;//客队
	
	public String matchStatus;//时间状态
	
	public List<Buyer> buyers;//购买人
}
