package com.xhcms.lottery.commons.persist.entity;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 
 * @desc	追号期计划实体
 * @createTime 2013-8-6
 * @author lei.li@unison.net.cn
 * @version 1.0
 */

@Entity
@Table(name = "lt_repeat_issue_plan")
public class RepeatIssuePlanPO {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	@Column(name = "plan_id")
	private long planId;
//	@ManyToOne(fetch = FetchType.EAGER)
//	@JoinColumn(name = "plan_id", nullable = false)
//	private RepeatPlanPO plan;
	
	@Column(name = "issue_number")
	private String issueNumber;
	
	@Column(name = "multiple")
	private int multiple;
	
	@Column(name = "is_valid", nullable = false)
	private Boolean valid;
	
	@Column(name = "is_executed", nullable = false)
	private Boolean executed;
	
	@Column(name = "result")
	private Integer result;
	
	@Column(name = "scheme_id")
	private Long schemeId;
	
	@Column(name = "executed_time")
	private Date executedTime;
	
	@Column(name = "created_time")
	private Date createdTime;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}
	
	public String getIssueNumber() {
		return issueNumber;
	}

	public void setIssueNumber(String issueNumber) {
		this.issueNumber = issueNumber;
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

	public long getPlanId() {
		return planId;
	}

	public void setPlanId(long planId) {
		this.planId = planId;
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
}