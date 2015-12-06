package com.xhcms.lottery.commons.event;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import com.xhcms.commons.mq.XHMessage;

public class QueryIssueinfoMessage implements XHMessage{
	
	 /**
	 * 
	 */
	private static final long serialVersionUID = -450512498286793226L;

	private int priority;
	    
	 private String type;
	 
	 private String issueNumber;
	 private String lotteryId;

	@Override
	public int getPriority() {
		
		return this.priority;
	}

	@Override
	public String getType() {
		return type;
	}

	@Override
	public void setPriority(int priority) {
		this.priority=priority;
		
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getLotteryId() {
		return lotteryId;
	}

	public void setLotteryId(String lotteryId) {
		this.lotteryId = lotteryId;
	}

	public String getIssueNumber() {
		return issueNumber;
	}

	public void setIssueNumber(String issueNumber) {
		this.issueNumber = issueNumber;
	}
	
	public String toString(){
		return ReflectionToStringBuilder.toString(this, ToStringStyle.MULTI_LINE_STYLE);
	}

}
