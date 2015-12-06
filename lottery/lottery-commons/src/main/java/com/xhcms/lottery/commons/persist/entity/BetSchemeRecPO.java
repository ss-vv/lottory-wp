package com.xhcms.lottery.commons.persist.entity;

import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;

@Entity
@Table(name = "lt_bet_scheme_rec")
public class BetSchemeRecPO {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false, name = "lottery_id")
	private String lotteryId;

	/**
	 * 玩法
	 */
	@Column(name = "play_id", updatable = false)
	private String playId;

	@Column(name = "pass_type_ids", updatable = false)
	private String passTypeIds;

	/**
	 * 方案发起人
	 */
	@Column(nullable = false, name = "sponsor_id", updatable = false)
	private Long sponsorId;

	@Column(nullable = false, updatable = false)
	private String sponsor;

	@Column(name = "total_amount", updatable = false)
	private int totalAmount;

	@Column(name = "match_number", updatable = false)
	private int matchNumber;

	@Column(updatable = false)
	private int multiple;

	@Column(name = "max_bonus", updatable = false)
	private BigDecimal maxBonus;

	@Version
	private int version;

	@Column(nullable = false, name = "created_time", updatable = false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date createdTime;

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

	public String getPlayId() {
		return playId;
	}

	public void setPlayId(String playId) {
		this.playId = playId;
	}

	public String getPassTypeIds() {
		return passTypeIds;
	}

	public void setPassTypeIds(String passTypeIds) {
		this.passTypeIds = passTypeIds;
	}

	public Long getSponsorId() {
		return sponsorId;
	}

	public void setSponsorId(Long sponsorId) {
		this.sponsorId = sponsorId;
	}

	public String getSponsor() {
		return sponsor;
	}

	public void setSponsor(String sponsor) {
		this.sponsor = sponsor;
	}

	public int getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(int totalAmount) {
		this.totalAmount = totalAmount;
	}

	public int getMatchNumber() {
		return matchNumber;
	}

	public void setMatchNumber(int matchNumber) {
		this.matchNumber = matchNumber;
	}

	public int getMultiple() {
		return multiple;
	}

	public void setMultiple(int multiple) {
		this.multiple = multiple;
	}

	public BigDecimal getMaxBonus() {
		return maxBonus;
	}

	public void setMaxBonus(BigDecimal maxBonus) {
		this.maxBonus = maxBonus;
	}
	
	public int getVersion() {
		return version;
	}

	public void setVersion(int version) {
		this.version = version;
	}

	public Date getCreatedTime() {
		return createdTime;
	}

	public void setCreatedTime(Date createdTime) {
		this.createdTime = createdTime;
	}
}
