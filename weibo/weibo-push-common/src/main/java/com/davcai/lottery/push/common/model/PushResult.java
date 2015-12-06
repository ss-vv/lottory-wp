package com.davcai.lottery.push.common.model;

import java.util.concurrent.atomic.AtomicInteger;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class PushResult {
	private PushTaskType type;
	private boolean publishSucc;
	private AtomicInteger countOfSucc=new AtomicInteger(0);
	private AtomicInteger countOfFail=new AtomicInteger(0);
	private AtomicInteger countOfOld=new AtomicInteger(0);
	

	public boolean isPublishSucc() {
		return publishSucc;
	}

	public void setPublishSucc(boolean publishSucc) {
		this.publishSucc = publishSucc;
	}
	
	public String toString(){
		return ReflectionToStringBuilder.toString(this, ToStringStyle.MULTI_LINE_STYLE);
	}


	public void addCountOfSucc() {
		countOfSucc.incrementAndGet();
		
	}

	

	public void addCountOfFail() {
		countOfFail.incrementAndGet();
		
	}

	public int getCountOfSucc() {
		return countOfSucc.get();
	}

	public int getCountOfFail() {
		return countOfFail.get();
	}

	public int getCountOfOld() {
		return countOfOld.get();
	}

	public void addCountOfOld() {
		countOfOld.incrementAndGet();
		
	}

	public PushTaskType getType() {
		return type;
	}

	public void setType(PushTaskType type) {
		this.type = type;
	}


}
