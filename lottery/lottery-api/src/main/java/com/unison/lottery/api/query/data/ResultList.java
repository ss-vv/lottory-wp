package com.unison.lottery.api.query.data;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.map.annotate.JsonSerialize;


@JsonIgnoreProperties(ignoreUnknown=true) 
@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
public class ResultList {

	private Finish finish;
	
	private NoFinish noFinish;

	public Finish getFinish() {
		return finish;
	}

	public void setFinish(Finish finish) {
		this.finish = finish;
	}

	public NoFinish getNoFinish() {
		return noFinish;
	}

	public void setNoFinish(NoFinish noFinish) {
		this.noFinish = noFinish;
	}
	
	
}
