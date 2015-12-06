package com.unison.lottery.weibo.dataservice.parse.model;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class BBBigSmallOdd extends BaseOdd{
	
	private String initHandicap;
	private String initBigOdd;
	private String initSmallOdd;
	private String immediateHandicap;
	private String immediateBigOdd;
	private String immediateSmallOdd;
	private boolean zouDi;
	private String bigZouDiOdd;
	private String smallZouDiOdd;
	
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
	public boolean isZouDi() {
		return zouDi;
	}
	public void setZouDi(boolean zouDi) {
		this.zouDi = zouDi;
	}
	public String getBigZouDiOdd() {
		return bigZouDiOdd;
	}
	public void setBigZouDiOdd(String bigZouDiOdd) {
		this.bigZouDiOdd = bigZouDiOdd;
	}
	public String getSmallZouDiOdd() {
		return smallZouDiOdd;
	}
	public void setSmallZouDiOdd(String smallZouDiOdd) {
		this.smallZouDiOdd = smallZouDiOdd;
	}
	
}
