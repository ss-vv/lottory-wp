package com.xhcms.lottery.commons.data.repeat;

import java.io.Serializable;
import java.util.Date;
import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

/**
 * 追号计划<br/>
 * 参考表"LT_REPEAT_PLAN 追号计划"
 * 
 * @author Yang Bo
 */
public class RepeatPlan implements Serializable {
	private static final long serialVersionUID = 4830005679026643548L;
	private long id;
	private String lotteryId;
	private long sponsorId;
	private Integer status;
	private int privacy;
	private int stopType;
	private int stoppedReason;
	private int bonusForStop;
	private int suiteType;
	private boolean isStopped;
	private boolean isDone;
	private Date createdTime;
	private Date finishTime;
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public long getSponsorId() {
		return sponsorId;
	}
	public void setSponsorId(long sponsorId) {
		this.sponsorId = sponsorId;
	}
	public boolean isStopped() {
		return isStopped;
	}
	public void setStopped(boolean isStopped) {
		this.isStopped = isStopped;
	}
	public boolean isDone() {
		return isDone;
	}
	public void setDone(boolean isDone) {
		this.isDone = isDone;
	}
	public int getStopType() {
		return stopType;
	}
	public void setStopType(int stopType) {
		this.stopType = stopType;
	}
	public int getStoppedReason() {
		return stoppedReason;
	}
	public void setStoppedReason(int stoppedReason) {
		this.stoppedReason = stoppedReason;
	}
	public int getBonusForStop() {
		return bonusForStop;
	}
	public void setBonusForStop(int bonusForStop) {
		this.bonusForStop = bonusForStop;
	}
	public Date getCreatedTime() {
		return createdTime;
	}
	public void setCreatedTime(Date createdTime) {
		this.createdTime = createdTime;
	}
	public Date getFinishTime() {
		return finishTime;
	}
	public void setFinishTime(Date finishTime) {
		this.finishTime = finishTime;
	}
	public String getLotteryId() {
		return lotteryId;
	}
	public void setLotteryId(String lotteryId) {
		this.lotteryId = lotteryId;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public int getSuiteType() {
		return suiteType;
	}
	public void setSuiteType(int suiteType) {
		this.suiteType = suiteType;
	}
	public int getPrivacy() {
		return privacy;
	}
	public void setPrivacy(int privacy) {
		this.privacy = privacy;
	}
	
	@Override
	public String toString() {
		return ReflectionToStringBuilder.toString(this, 
				ToStringStyle.MULTI_LINE_STYLE);
	}
}
