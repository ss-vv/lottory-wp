package com.xhcms.lottery.dc.feed.data;

import java.io.Serializable;

import com.xhcms.lottery.commons.data.IssueInfo;

/**
 * 用于json接口返回数据的bean。
 * 
 * @author Yang Bo
 */
public class JsonIssueInfo implements Serializable{
	private static final long serialVersionUID = -1843805383226832696L;
	
	private boolean success;
	private IssueInfo issue;		// 期信息
	private long countDownSeconds;	// 本期结束倒计时
	
	public JsonIssueInfo() {
	}

	public JsonIssueInfo(IssueInfo issue, long countDownSeconds) {
		super();
		this.issue = issue;
		this.countDownSeconds = countDownSeconds;
	}
	
	public IssueInfo getIssue() {
		return issue;
	}
	public void setIssue(IssueInfo issue) {
		this.issue = issue;
	}
	public long getCountDownSeconds() {
		return countDownSeconds;
	}
	public void setCountDownSeconds(long countDownSeconds) {
		this.countDownSeconds = countDownSeconds;
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}
}
