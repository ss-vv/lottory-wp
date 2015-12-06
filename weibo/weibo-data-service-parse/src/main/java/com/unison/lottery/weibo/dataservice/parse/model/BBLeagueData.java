package com.unison.lottery.weibo.dataservice.parse.model;

import java.util.List;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class BBLeagueData {

	List<BBLeagueContentData> bbLeagueContentDataList;

	
	public List<BBLeagueContentData> getBbLeagueContentDataList() {
		return bbLeagueContentDataList;
	}


	public void setBbLeagueContentDataList(
			List<BBLeagueContentData> bbLeagueContentDataList) {
		this.bbLeagueContentDataList = bbLeagueContentDataList;
	}


	public String toString(){
		return ReflectionToStringBuilder.toString(this,
				ToStringStyle.MULTI_LINE_STYLE);
	}
	
	
}
