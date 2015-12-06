package com.unison.lottery.weibo.dataservice.crawler.service.model;

import java.io.Serializable;
import java.util.Date;

/**
 * 联赛积分规则
 * @author 彭保星
 *
 * @since 2014年11月10日下午5:17:43
 */
public class FbLeagueScoreRuleModel implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -5556951739369054007L;
	/**
	 * 
	 */
	private Integer ruleId;
	private Integer seasonId;
	private Integer source;
	private Integer processStatus;
	private String ruleName;
	private Integer ruleNum;
	private String ruleColor;
	private Date createTime;
	private Date updateTime;
	public Integer getRuleId() {
		return ruleId;
	}
	public void setRuleId(Integer ruleId) {
		this.ruleId = ruleId;
	}
	public Integer getSeasonId() {
		return seasonId;
	}
	public void setSeasonId(Integer seasonId) {
		this.seasonId = seasonId;
	}
	public Integer getSource() {
		return source;
	}
	public void setSource(Integer source) {
		this.source = source;
	}
	public Integer getProcessStatus() {
		return processStatus;
	}
	public void setProcessStatus(Integer processStatus) {
		this.processStatus = processStatus;
	}
	public String getRuleName() {
		return ruleName;
	}
	public void setRuleName(String ruleName) {
		this.ruleName = ruleName;
	}
	public Integer getRuleNum() {
		return ruleNum;
	}
	public void setRuleNum(Integer ruleNum) {
		this.ruleNum = ruleNum;
	}
	public String getRuleColor() {
		return ruleColor;
	}
	public void setRuleColor(String ruleColor) {
		this.ruleColor = ruleColor;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public Date getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	
	
}
