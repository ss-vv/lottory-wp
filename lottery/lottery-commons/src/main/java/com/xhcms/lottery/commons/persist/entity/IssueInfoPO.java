package com.xhcms.lottery.commons.persist.entity;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

@Entity
@Table(name = "lt_issueinfo")
public class IssueInfoPO {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
    @Column(name = "lottery_id", nullable = false)
	private String lotteryId;
    
    @Column(name = "issue_number", nullable = false)
	private String issueNumber;
    
    @Column(name = "play_id", nullable = true)
    private String playId;
	
    @Column(name = "start_time", nullable = true)
	private Date startTime;
	
    // 中民停止接票时间;如果高频彩则为期截止时间
    @Column(name = "stop_time", nullable = true)
	private Date stopTime;
	
    @Column(name = "stop_time_for_user", nullable = true)
	private Date stopTimeForUser;//由大V彩定义的用户结束时间
    
    @Column(name = "zm_close_time", nullable = true)
	private Date ZMCloseTime;//中民截止时间
	
    // 官方销售截止时间
    @Column(name = "close_time", nullable = true)
	private Date closeTime;
    
    /** 兑奖（开奖）时间 */
    @Column(name = "prize_time", nullable = true)
    private Date prizeTime;
	
    @Column(name = "status", nullable = true)
	private Integer status;
    
    @Column(name = "lc_status", nullable = true)
	private Integer LCStatus=0;
	
    @Column(name = "bonus_code", nullable = true)
	private String bonusCode;
	
    @Column(name = "bonus_info", nullable = true)
	private String bonusInfo;
	
    @Column(name = "lock_of_stop_time_for_user", nullable = false)
	private Boolean stopTimeForUserLocked=Boolean.FALSE;//是否锁定由大V彩定义的用户结束时间
    
    @Column(name = "is_valid", nullable = false)
	private Boolean valid=Boolean.TRUE;//是否
    
    @Column(name = "created_time")
    private Date createdTime;
    
    @Column(name = "update_time")
    private Date updateTime;

    @Column(name = "preset_bonus_info")
    private String presetBonusInfo;

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

	
	/**
	 * @return 一期结束时间（游戏的玩法时间）。
	 */
	public Date getStopTime() {
		return stopTime;
	}

	public void setStopTime(Date stopTime) {
		this.stopTime = stopTime;
	}

	/**
	 * @return 彩票中心官方停售时间
	 */
	public Date getCloseTime() {
		return closeTime;
	}

	public void setCloseTime(Date closeTime) {
		this.closeTime = closeTime;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
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

	/**
	 * 前台用户投注停止时间，大V彩停止投注时间。
	 */
	public Date getStopTimeForUser() {
		return stopTimeForUser;
	}

	public void setStopTimeForUser(Date stopTimeForUser) {
		this.stopTimeForUser = stopTimeForUser;
	}

	public boolean isStopTimeForUserLocked() {
		return stopTimeForUserLocked;
	}

	public void setStopTimeForUserLocked(boolean stopTimeForUserLocked) {
		this.stopTimeForUserLocked = stopTimeForUserLocked;
	}
	
	public String toString(){
		return ReflectionToStringBuilder.toString(this, ToStringStyle.MULTI_LINE_STYLE);
	}

	public Date getZMCloseTime() {
		return ZMCloseTime;
	}

	public void setZMCloseTime(Date zMCloseTime) {
		ZMCloseTime = zMCloseTime;
	}

	public Integer getLCStatus() {
		return LCStatus;
	}

	public void setLCStatus(Integer lCStatus) {
		LCStatus = lCStatus;
	}

	public Boolean getStopTimeForUserLocked() {
		return stopTimeForUserLocked;
	}

	public void setStopTimeForUserLocked(Boolean stopTimeForUserLocked) {
		this.stopTimeForUserLocked = stopTimeForUserLocked;
	}

	public Boolean getValid() {
		return valid;
	}

	public void setValid(Boolean valid) {
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

	public Date getCreatedTime() {
		return createdTime;
	}

	public void setCreatedTime(Date createdTime) {
		this.createdTime = createdTime;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public String getPresetBonusInfo() {
		return presetBonusInfo;
	}

	public void setPresetBonusInfo(String presetBonusInfo) {
		this.presetBonusInfo = presetBonusInfo;
	}
}
