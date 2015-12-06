package com.xhcms.lottery.commons.persist.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 赛事背景颜色表
 * @author Wang Lei
 *
 */
@Entity
@Table(name = "lt_match_color")
public class MatchColorPO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -8623777672879393371L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(nullable = false, name = "league_name")
	private String leagueName;
	
	@Column(nullable= false, name = "lottery_id")
    private String lotteryId;
	
	@Column(nullable= false, name = "color")
    private String color;

	@Column(nullable = false, name = "league_name_for_short")
	private String leagueShortName;
	
	public String getLeagueShortName() {
		return leagueShortName;
	}

	public void setLeagueShortName(String leagueShortName) {
		this.leagueShortName = leagueShortName;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getLeagueName() {
		return leagueName;
	}

	public void setLeagueName(String leagueName) {
		this.leagueName = leagueName;
	}

	public String getLotteryId() {
		return lotteryId;
	}

	public void setLotteryId(String lotteryId) {
		this.lotteryId = lotteryId;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}
	
	
}
