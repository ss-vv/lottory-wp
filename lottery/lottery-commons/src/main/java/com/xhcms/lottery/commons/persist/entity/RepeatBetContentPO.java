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
 * @desc	追号的投注内容实体
 * @createTime 2013-8-6
 * @author lei.li@unison.net.cn
 * @version 1.0
 */

@Entity
@Table(name = "lt_repeat_bet_content")
public class RepeatBetContentPO {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	@Column(name = "plan_id", nullable = false)
	private long planId;
	
	@Column(name = "lottery_id", nullable = false)
	private String lotteryId;

	@Column(name = "play_id", nullable = false)
	private String playId;

	@Column(name = "code", nullable = false)
	private String code;
	
	@Column(name = "choose_type", nullable = false)
	private int chooseType;
	
	@Column(name = "created_time", nullable = false)
	private Date createdTime;

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

	public String getPlayId() {
		return playId;
	}

	public void setPlayId(String playId) {
		this.playId = playId;
	}
	
	public int getChooseType() {
		return chooseType;
	}

	public void setChooseType(int chooseType) {
		this.chooseType = chooseType;
	}

	public long getPlanId() {
		return planId;
	}

	public void setPlanId(long planId) {
		this.planId = planId;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public Date getCreatedTime() {
		return createdTime;
	}

	public void setCreatedTime(Date createdTime) {
		this.createdTime = createdTime;
	}
}