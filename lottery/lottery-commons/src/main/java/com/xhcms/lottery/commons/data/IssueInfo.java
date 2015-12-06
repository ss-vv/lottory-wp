package com.xhcms.lottery.commons.data;

import java.io.Serializable;
import java.util.Date;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

/**
 * 期信息VO
 */
public class IssueInfo implements Serializable{
	private static final long serialVersionUID = 2133202581027917178L;

	private Long id;
	
	private String lotteryId;
	
	private String playId;
    
	private String issueNumber;
	
	private Date startTime;
	
	private Date stopTime;					// 中民停止接票时间;如果高频彩则为期截止时间
	
	private Date stopTimeForUser;			// 由大V彩定义的用户结束时间
    
	private Date ZMCloseTime;				// 中民截止时间
	
	private Date closeTime;					// 官方截止时间
	
	/** 兑奖（开奖）时间 */
	private Date prizeTime;
	
	private int status;
    
	private int LCStatus;
	
	private String bonusCode;
	
	private String bonusInfo;
	
	private boolean stopTimeForUserLocked;	//是否锁定由大V彩定义的用户结束时间
    
	private boolean valid;					//是否有效
	
	private String presetBonusInfo;
	
	public String toString(){
		return ReflectionToStringBuilder.toString(this, ToStringStyle.MULTI_LINE_STYLE);
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getStopTime() {
		return stopTime;
	}

	public void setStopTime(Date stopTime) {
		this.stopTime = stopTime;
	}

	public Date getCloseTime() {
		return closeTime;
	}

	public void setCloseTime(Date closeTime) {
		this.closeTime = closeTime;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getBonusCode() {
		return bonusCode;
	}

	public void setBonusCode(String bonusCode) {
		this.bonusCode = bonusCode;
	}

	public String getBonusInfo() {
		return bonusInfo;
	}

	public void setBonusInfo(String bonusInfo) {
		this.bonusInfo = bonusInfo;
	}

	public Date getStopTimeForUser() {
		return stopTimeForUser;
	}

	public void setStopTimeForUser(Date stopTimeForUser) {
		this.stopTimeForUser = stopTimeForUser;
	}

	public Date getZMCloseTime() {
		return ZMCloseTime;
	}

	public void setZMCloseTime(Date zMCloseTime) {
		ZMCloseTime = zMCloseTime;
	}

	public int getLCStatus() {
		return LCStatus;
	}

	public void setLCStatus(int lCStatus) {
		LCStatus = lCStatus;
	}

	public boolean getStopTimeForUserLocked() {
		return stopTimeForUserLocked;
	}

	public void setStopTimeForUserLocked(boolean stopTimeForUserLocked) {
		this.stopTimeForUserLocked = stopTimeForUserLocked;
	}

	public boolean getValid() {
		return valid;
	}

	public void setValid(boolean valid) {
		this.valid = valid;
	}

	public String getPlayId() {
		return playId;
	}

	public void setPlayId(String playId) {
		this.playId = playId;
	}

	public Date getPrizeTime() {
		return prizeTime;
	}

	public void setPrizeTime(Date prizeTime) {
		this.prizeTime = prizeTime;
	}

	public String getPresetBonusInfo() {
		return presetBonusInfo;
	}

	public void setPresetBonusInfo(String presetBonusInfo) {
		this.presetBonusInfo = presetBonusInfo;
	}
}
