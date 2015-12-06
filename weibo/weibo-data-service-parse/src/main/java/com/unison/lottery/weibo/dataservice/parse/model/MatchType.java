package com.unison.lottery.weibo.dataservice.parse.model;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class MatchType {
	
	private BaseName name;
	
	private String type;
	
	private String matchId;
	
	private String isAllOrSimple;
	
	private String customOne;
	
	private String customTwo;
	
	public String toString(){
		return ReflectionToStringBuilder.toString(this,
				ToStringStyle.MULTI_LINE_STYLE);
	}

	public BaseName getName() {
		return name;
	}

	public void setName(BaseName name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getMatchId() {
		return matchId;
	}

	public void setMatchId(String matchId) {
		this.matchId = matchId;
	}

	public String getIsAllOrSimple() {
		return isAllOrSimple;
	}

	public void setIsAllOrSimple(String isAllOrSimple) {
		this.isAllOrSimple = isAllOrSimple;
	}

	public String getCustomOne() {
		return customOne;
	}

	public void setCustomOne(String customOne) {
		this.customOne = customOne;
	}

	public String getCustomTwo() {
		return customTwo;
	}

	public void setCustomTwo(String customTwo) {
		this.customTwo = customTwo;
	}
	
	
	

}
