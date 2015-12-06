package com.davcai.lottery.push.common.model;

import java.io.Serializable;
import java.util.List;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.map.annotate.JsonSerialize;

@JsonIgnoreProperties(ignoreUnknown=true) 
@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
public class PushLqLiveOddsModel implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3102694939284702361L;

	private List<PushLiveOdds> oddsList;

	public List<PushLiveOdds> getOddsList() {
		return oddsList;
	}

	public void setOddsList(List<PushLiveOdds> oddsList) {
		this.oddsList = oddsList;
	}
	
	
}
