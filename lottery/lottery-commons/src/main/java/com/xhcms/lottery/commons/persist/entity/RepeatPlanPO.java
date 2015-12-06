package com.xhcms.lottery.commons.persist.entity;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * 
 * @desc 追号计划的业务实体
 * @createTime 2013-8-6
 * @author lei.li@unison.net.cn
 * @version 1.0
 */

@Entity
@Table(name = "lt_repeat_plan")
public class RepeatPlanPO {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	@Column(name = "lottery_id", nullable = false)
	private String lotteryId;

	@Column(name = "sponsor_id", nullable = false)
	private long sponsorId;

	@Column(name = "status", nullable = false)
	private Integer status;
	
	@Column(name = "follow_privacy", nullable = false)
	private int privacy;

	@Column(name = "stop_type", nullable = false)
	private int stopType;

	@Column(name = "stop_reason", nullable = false)
	private int stoppedReason;

	@Column(name = "bonus_stop")
	private int bonusForStop;

	@Column(name = "suite_type", nullable = false)
	private int suiteType;

	@Column(name = "created_time", nullable = false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date createdTime;

	@Column(name = "finish_time")
	@Temporal(TemporalType.TIMESTAMP)
	private Date finishTime;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getLotteryId() {
		return lotteryId;
	}

	public void setLotteryId(String lotteryId) {
		this.lotteryId = lotteryId;
	}

	public long getSponsorId() {
		return sponsorId;
	}

	public void setSponsorId(long sponsorId) {
		this.sponsorId = sponsorId;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
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

	public int getSuiteType() {
		return suiteType;
	}

	public void setSuiteType(int suiteType) {
		this.suiteType = suiteType;
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

	public int getPrivacy() {
		return privacy;
	}

	public void setPrivacy(int privacy) {
		this.privacy = privacy;
	}
}