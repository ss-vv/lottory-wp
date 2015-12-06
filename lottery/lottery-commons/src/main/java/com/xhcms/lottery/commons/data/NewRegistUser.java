package com.xhcms.lottery.commons.data;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

public class NewRegistUser {
	
	private String date;
	private String pid;
	private int count;
	
	public NewRegistUser() {
		
	}
	
	public NewRegistUser(String date, String pid, int count) {
		super();
		this.date = date;
		this.pid = pid;
		this.count = count;
	}

	@Override
	public String toString() {
		return ReflectionToStringBuilder.toString(this, ToStringStyle.MULTI_LINE_STYLE);
	}
	
	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getPid() {
		return pid;
	}

	public void setPid(String pid) {
		this.pid = pid;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}
}