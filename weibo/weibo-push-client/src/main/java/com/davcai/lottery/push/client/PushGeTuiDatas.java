package com.davcai.lottery.push.client;

import java.util.List;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.map.annotate.JsonSerialize;

@JsonIgnoreProperties(ignoreUnknown=true) 
@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
public class PushGeTuiDatas {

	private List<PushLiveScore> data;

	public List<PushLiveScore> getData() {
		return data;
	}

	public void setData(List<PushLiveScore> data) {
		this.data = data;
	}
	
	
	
}
