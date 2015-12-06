package com.unison.lottery.weibo.dataservice.parse.model;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class BigSmallOdd extends BaseOdd{
	
	private String initHandicap;
	private String initBigOdd;
	private String initSmallOdd;
	private String immediateHandicap;
	private String immediateBigOdd;
	private String immediateSmallOdd;
	public String getInitHandicap() {
		return initHandicap;
	}
	public void setInitHandicap(String initHandicap) {
		this.initHandicap = initHandicap;
	}
	public String getInitBigOdd() {
		return initBigOdd;
	}
	public void setInitBigOdd(String initBigOdd) {
		this.initBigOdd = initBigOdd;
	}
	public String getInitSmallOdd() {
		return initSmallOdd;
	}
	public void setInitSmallOdd(String initSmallOdd) {
		this.initSmallOdd = initSmallOdd;
	}
	public String getImmediateHandicap() {
		return immediateHandicap;
	}
	public void setImmediateHandicap(String immediateHandicap) {
		this.immediateHandicap = immediateHandicap;
	}
	public String getImmediateBigOdd() {
		return immediateBigOdd;
	}
	public void setImmediateBigOdd(String immediateBigOdd) {
		this.immediateBigOdd = immediateBigOdd;
	}
	public String getImmediateSmallOdd() {
		return immediateSmallOdd;
	}
	public void setImmediateSmallOdd(String immediateSmallOdd) {
		this.immediateSmallOdd = immediateSmallOdd;
	}
	public String toString(){
		return ReflectionToStringBuilder.toString(this,
				ToStringStyle.MULTI_LINE_STYLE);
	}
}
