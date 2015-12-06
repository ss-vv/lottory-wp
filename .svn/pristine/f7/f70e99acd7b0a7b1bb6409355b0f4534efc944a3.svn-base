package com.xhcms.lottery.commons.persist.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "lt_match_platform")
public class MatchPlatformPO implements Serializable {
	private static final long serialVersionUID = -8830057769185889080L;

	@Id
	private Long id;

	@Column(name = "platform_id", nullable = false)
	private String platformId;

	@Column(name = "lottery_id", nullable = false)
	private String lotteryId;

	@Column(name = "match_id", nullable = false)
	private Long matchId;

	@Column(name = "p_match_id", nullable = false)
	private Long platformMatchId;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "created_time")
	private Date createdTime;

	public Date getCreatedTime() {
		return createdTime;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getPlatformId() {
		return platformId;
	}

	public void setPlatformId(String platformId) {
		this.platformId = platformId;
	}

	public String getLotteryId() {
		return lotteryId;
	}

	public void setLotteryId(String lotteryId) {
		this.lotteryId = lotteryId;
	}

	public Long getMatchId() {
		return matchId;
	}

	public void setMatchId(Long matchId) {
		this.matchId = matchId;
	}

	public Long getPlatformMatchId() {
		return platformMatchId;
	}

	public void setPlatformMatchId(Long platformMatchId) {
		this.platformMatchId = platformMatchId;
	}

	public void setCreatedTime(Date createdTime) {
		this.createdTime = createdTime;
	}
}