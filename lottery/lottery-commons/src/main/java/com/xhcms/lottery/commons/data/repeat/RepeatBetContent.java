package com.xhcms.lottery.commons.data.repeat;

import java.io.Serializable;
import java.util.Date;


/**
 * 追号投注内容。
 * 
 * @author Yang Bo
 */
public class RepeatBetContent implements Serializable {
	private static final long serialVersionUID = 7346067022636481008L;
	private long id;
	private long planId;
	private String lotteryId;
	private String playId;
	private String code;
	private int chooseType;
	private Date createdTime;
	
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
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public int getChooseType() {
		return chooseType;
	}
	public void setChooseType(int chooseType) {
		this.chooseType = chooseType;
	}
	public Date getCreatedTime() {
		return createdTime;
	}
	public void setCreatedTime(Date createdTime) {
		this.createdTime = createdTime;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
}
