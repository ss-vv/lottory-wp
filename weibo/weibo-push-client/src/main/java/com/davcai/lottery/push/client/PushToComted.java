package com.davcai.lottery.push.client;


import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class PushToComted {

	private boolean publishSucc;
	private int countOfSucc;
	private int countOfFail;
	private int countOfOld;
	
	public String toString(){
		return ReflectionToStringBuilder.toString(this, ToStringStyle.MULTI_LINE_STYLE);
	}
	
	public int getCountOfSucc() {
		return countOfSucc;
	}


	public int getCountOfFail() {
		return countOfFail;
	}


	public int getCountOfOld() {
		return countOfOld;
	}


	public boolean isPublishSucc() {
		return publishSucc;
	}


	public void setCountOfSucc(int countOfSucc) {
		this.countOfSucc = countOfSucc;
	}

	public void setCountOfFail(int countOfFail) {
		this.countOfFail = countOfFail;
	}

	public void setCountOfOld(int countOfOld) {
		this.countOfOld = countOfOld;
	}

	public void setPublishSucc(boolean publishSucc) {
		this.publishSucc = publishSucc;
	}

	
}
