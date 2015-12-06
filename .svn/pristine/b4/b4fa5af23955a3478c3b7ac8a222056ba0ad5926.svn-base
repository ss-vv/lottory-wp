package com.xhcms.lottery.commons.data.repeat;

import java.io.Serializable;
import java.util.Date;
import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

/**
 * 追号计划期信息。
 * 
 * @author Yang Bo
 */
public class RepeatPlanIssues implements Serializable {
	private static final long serialVersionUID = 5707923762977298249L;

	private long id;
	private long planId;
	private String issueNumber;
	private int multiple;
	private Boolean valid;
	private Boolean executed;
	private Integer result;
	private Long schemeId;
	private Date executedTime;
	private Date createdTime;
	
	@Override
	public String toString() {
		return ReflectionToStringBuilder.toString(this, 
				ToStringStyle.MULTI_LINE_STYLE);
	}
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public long getPlanId() {
		return planId;
	}
	public void setPlanId(long planId) {
		this.planId = planId;
	}
	public String getIssueNumber() {
		return issueNumber;
	}
	public void setIssueNumber(String issueNumber) {
		this.issueNumber = issueNumber;
	}
	public int getMultiple() {
		return multiple;
	}
	public void setMultiple(int multiple) {
		this.multiple = multiple;
	}
	public Boolean getValid() {
		return valid;
	}
	public void setValid(Boolean valid) {
		this.valid = valid;
	}
	public Boolean getExecuted() {
		return executed;
	}
	public void setExecuted(Boolean executed) {
		this.executed = executed;
	}
	public Integer getResult() {
		return result;
	}
	public void setResult(Integer result) {
		this.result = result;
	}
	public Long getSchemeId() {
		return schemeId;
	}
	public void setSchemeId(Long schemeId) {
		this.schemeId = schemeId;
	}
	public Date getExecutedTime() {
		return executedTime;
	}
	public void setExecutedTime(Date executedTime) {
		this.executedTime = executedTime;
	}
	public Date getCreatedTime() {
		return createdTime;
	}
	public void setCreatedTime(Date createdTime) {
		this.createdTime = createdTime;
	}
}
