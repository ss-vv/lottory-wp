package com.unison.lottery.weibo.data.crawler.service.store.persist.dao.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Type;


/**
 * 篮球比赛详情 球员统计
 * 
 * @author guixiangcui
 *
 */
@Entity
@Table(name="md_qt_bb_match_playerstatistic_base")
public class BasketMatchPlayerStatisticPO implements Serializable {
	
	private static final long serialVersionUID = -8470192096886453332L;
	
	@Id
	private long id;
	private Integer bsId;
	private Integer source; //来源：1球探
	private Integer processStatus; //是否已处理

	private String playerId; //2701
	private String playerName;//韦恩-艾灵顿（球员）
	private String playerNickName;//艾寧頓（别名）
	private String playerEnglishName;//Wayne Ellington（英文名）
	private String starter;// G后卫 F前锋 C中锋
	private Integer lineUp;//32（上场） 
	private Integer shootHitNumber;//4（投篮命中次数）
	private Integer shootTotalNumber;//12（投篮总次数）
	private Integer threeHitNumber;//1（3分命中次数）
	private Integer threeTotalNumber;//3（3分总次数）
	private Integer penaltyHitNumber;//2（罚球命中次数）
	private Integer penaltyTotalNunber;//2（罚球总次数）
	private Integer backboardAttackNumber;//0（篮板进攻）
	private Integer backboardDefenceNumber;//2（篮板防守）
	private Integer backboardTotalNumber;//2（篮板总计）
	private Integer assist;//2（助攻）
	private Integer foul;//1（犯规）
	private Integer steal;//2（抢断）
	private Integer fault;//1（失误）
	private Integer blockedShot;//0（盖帽）
	private Integer score;//11（得分）
	private Integer homeGuestType;//主队客队类型，1主队2客队 
	@Type(type = "timestamp")
	private Date createTime;
	@Type(type = "timestamp")
	private Date updateTime;
	
	
	
	public String getPlayerId() {
		return playerId;
	}
	public void setPlayerId(String playerId) {
		this.playerId = playerId;
	}
	public String getPlayerName() {
		return playerName;
	}
	public void setPlayerName(String playerName) {
		this.playerName = playerName;
	}
	public String getPlayerNickName() {
		return playerNickName;
	}
	public void setPlayerNickName(String playerNickName) {
		this.playerNickName = playerNickName;
	}
	public String getPlayerEnglishName() {
		return playerEnglishName;
	}
	public void setPlayerEnglishName(String playerEnglishName) {
		this.playerEnglishName = playerEnglishName;
	}
	public String getStarter() {
		return starter;
	}
	public void setStarter(String starter) {
		this.starter = starter;
	}
	public Integer getLineUp() {
		return lineUp;
	}
	public void setLineUp(Integer lineUp) {
		this.lineUp = lineUp;
	}
	public Integer getShootHitNumber() {
		return shootHitNumber;
	}
	public void setShootHitNumber(Integer shootHitNumber) {
		this.shootHitNumber = shootHitNumber;
	}
	public Integer getShootTotalNumber() {
		return shootTotalNumber;
	}
	public void setShootTotalNumber(Integer shootTotalNumber) {
		this.shootTotalNumber = shootTotalNumber;
	}
	public Integer getThreeHitNumber() {
		return threeHitNumber;
	}
	public void setThreeHitNumber(Integer threeHitNumber) {
		this.threeHitNumber = threeHitNumber;
	}
	public Integer getThreeTotalNumber() {
		return threeTotalNumber;
	}
	public void setThreeTotalNumber(Integer threeTotalNumber) {
		this.threeTotalNumber = threeTotalNumber;
	}
	public Integer getPenaltyHitNumber() {
		return penaltyHitNumber;
	}
	public void setPenaltyHitNumber(Integer penaltyHitNumber) {
		this.penaltyHitNumber = penaltyHitNumber;
	}
	public Integer getPenaltyTotalNunber() {
		return penaltyTotalNunber;
	}
	public void setPenaltyTotalNunber(Integer penaltyTotalNunber) {
		this.penaltyTotalNunber = penaltyTotalNunber;
	}
	public Integer getBackboardAttackNumber() {
		return backboardAttackNumber;
	}
	public void setBackboardAttackNumber(Integer backboardAttackNumber) {
		this.backboardAttackNumber = backboardAttackNumber;
	}
	public Integer getBackboardDefenceNumber() {
		return backboardDefenceNumber;
	}
	
	public Integer getHomeGuestType() {
		return homeGuestType;
	}
	public void setHomeGuestType(Integer homeGuestType) {
		this.homeGuestType = homeGuestType;
	}
	public void setBackboardDefenceNumber(Integer backboardDefenceNumber) {
		this.backboardDefenceNumber = backboardDefenceNumber;
	}
	public Integer getBackboardTotalNumber() {
		return backboardTotalNumber;
	}
	public void setBackboardTotalNumber(Integer backboardTotalNumber) {
		this.backboardTotalNumber = backboardTotalNumber;
	}
	public Integer getAssist() {
		return assist;
	}
	public void setAssist(Integer assist) {
		this.assist = assist;
	}
	public Integer getFoul() {
		return foul;
	}
	public void setFoul(Integer foul) {
		this.foul = foul;
	}
	public Integer getSteal() {
		return steal;
	}
	public void setSteal(Integer steal) {
		this.steal = steal;
	}
	public Integer getFault() {
		return fault;
	}
	public void setFault(Integer fault) {
		this.fault = fault;
	}
	public Integer getBlockedShot() {
		return blockedShot;
	}
	public void setBlockedShot(Integer blockedShot) {
		this.blockedShot = blockedShot;
	}
	public Integer getScore() {
		return score;
	}
	public void setScore(Integer score) {
		this.score = score;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public Integer getBsId() {
		return bsId;
	}
	public void setBsId(Integer bsId) {
		this.bsId = bsId;
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
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	
}
