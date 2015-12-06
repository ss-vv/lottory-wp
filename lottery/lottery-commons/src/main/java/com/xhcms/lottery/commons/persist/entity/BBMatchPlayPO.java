package com.xhcms.lottery.commons.persist.entity;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

@Entity
@Table(name = "bb_match_play")
@Cache(usage = CacheConcurrencyStrategy.READ_ONLY)
public class BBMatchPlayPO implements Serializable {
	private static final long serialVersionUID = 1858418499307611047L;

	@Id
	private String id;

	@Column(nullable = false, name = "match_id")
	private Long matchId;

	@Column(nullable = false, name = "play_id")
	private String playId;

	@Column(nullable = false)
	private String options;

	@Column(nullable = false)
	private String odds;

	@Column(name = "prior_odds")
	private String priorOdds;

	private int status;

	@Column(nullable = false, name = "win_bonus")
	private BigDecimal winBonus;

	@Column(name = "win_option")
	private String winOption;

	@Column(name = "default_score")
	private float defaultScore;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Long getMatchId() {
		return matchId;
	}

	public void setMatchId(Long matchId) {
		this.matchId = matchId;
	}

	public String getPlayId() {
		return playId;
	}

	public void setPlayId(String playId) {
		this.playId = playId;
	}

	public String getOptions() {
		return options;
	}

	public void setOptions(String options) {
		this.options = options;
	}

	public String getOdds() {
		return odds;
	}

	public void setOdds(String odds) {
		this.odds = odds;
	}

	public String getPriorOdds() {
		return priorOdds;
	}

	public void setPriorOdds(String priorOdds) {
		this.priorOdds = priorOdds;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public BigDecimal getWinBonus() {
		return winBonus;
	}

	public void setWinBonus(BigDecimal winBonus) {
		this.winBonus = winBonus;
	}

	public String getWinOption() {
		return winOption;
	}

	public void setWinOption(String winOption) {
		this.winOption = winOption;
	}

	public float getDefaultScore() {
		return defaultScore;
	}

	public void setDefaultScore(float defaultScore) {
		this.defaultScore = defaultScore;
	}
	
}
