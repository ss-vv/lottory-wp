package com.unison.lottery.weibo.dataservice.parse.model;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class BBEuropeOdd extends BaseOdd{
	
	private String initHomeWinOdd;
	private String initGuestWinOdd;
	private String immediateHomeWinOdd;
	private String immediateGuestWinOdd;
	public String getInitHomeWinOdd() {
		return initHomeWinOdd;
	}
	public void setInitHomeWinOdd(String initHomeWinOdd) {
		this.initHomeWinOdd = initHomeWinOdd;
	}
	public String getInitGuestWinOdd() {
		return initGuestWinOdd;
	}
	public void setInitGuestWinOdd(String initGuestWinOdd) {
		this.initGuestWinOdd = initGuestWinOdd;
	}
	public String getImmediateHomeWinOdd() {
		return immediateHomeWinOdd;
	}
	public void setImmediateHomeWinOdd(String immediateHomeWinOdd) {
		this.immediateHomeWinOdd = immediateHomeWinOdd;
	}
	public String getImmediateGuestWinOdd() {
		return immediateGuestWinOdd;
	}
	public void setImmediateGuestWinOdd(String immediateGuestWinOdd) {
		this.immediateGuestWinOdd = immediateGuestWinOdd;
	}
	public String toString(){
		return ReflectionToStringBuilder.toString(this,
				ToStringStyle.MULTI_LINE_STYLE);
	}
}
