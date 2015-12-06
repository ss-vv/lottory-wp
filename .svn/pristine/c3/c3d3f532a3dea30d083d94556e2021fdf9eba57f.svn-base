package com.davcai.lottery.weibo.analyse.dao.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * 足球联赛积分排名
 * 
 * @author baoxing.peng
 *
 */
@Entity
public class LeagueScoreRandPO implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1327181371031329177L;
	@Id
	private String teamId;
	private String teamName;
	private Long totalMatches; // 比赛次数
	private Long winMatches; // 胜场次数
	private Long drawMatches; // 平次数
	private Long loseMatches;
	private Double goal; // 足球总得分/篮球场均得分
	private Double lose; // 足球总失分/篮球场均失分
	private Long leageScore; // 积分
	private String rank; // 排名
	private String winRate; //胜率
	private String scoreType; // 积分类型 1：总积分 2：主场积分 3：客场积分 4:近6场积分

	public LeagueScoreRandPO(String teamId, Long totalMatches, Double goal,
			Double lose, Long winMatches, Long drawMatches, Long loseMatches,
			Long leagueScore) {
		this.teamId = teamId;
		this.totalMatches = totalMatches;
		this.winMatches = winMatches;
		this.drawMatches = drawMatches;
		this.loseMatches = loseMatches;
		this.lose = lose;
		this.goal = goal;
		this.leageScore = leagueScore;
	}
	public LeagueScoreRandPO(String teamId, Long totalMatches, Long goal,
			Long lose, Long winMatches, Long drawMatches, Long loseMatches,
			Long leagueScore) {
		this.teamId = teamId;
		this.totalMatches = totalMatches;
		this.winMatches = winMatches;
		this.drawMatches = drawMatches;
		this.loseMatches = loseMatches;
		this.lose = lose*1.0;
		this.goal = goal*1.0;
		this.leageScore = leagueScore;
	}
	public LeagueScoreRandPO(String teamId, Long totalMatches, Double goal,
			Double lose, Long winMatches, Long loseMatches) {
		this.teamId = teamId;
		this.totalMatches = totalMatches;
		this.winMatches = winMatches;
		this.loseMatches = loseMatches;
		this.lose = lose;
		this.goal = goal;
	}

	/**
	 * default constructor
	 */
	public LeagueScoreRandPO() {

	}

	public String getTeamName() {
		return teamName;
	}
	
	public void setTeamName(String teamName) {
		this.teamName = teamName;
	}

	public String getWinRate() {
		return winRate;
	}
	public void setWinRate(String winRate) {
		this.winRate = winRate;
	}
	public String getTeamId() {
		return teamId;
	}

	public void setTeamId(String teamId) {
		this.teamId = teamId;
	}

	public Long getTotalMatches() {
		return totalMatches;
	}

	public void setTotalMatches(Long totalMatches) {
		this.totalMatches = totalMatches;
	}

	public Long getWinMatches() {
		return winMatches;
	}

	public void setWinMatches(Long winMatches) {
		this.winMatches = winMatches;
	}

	public Long getDrawMatches() {
		return drawMatches;
	}

	public void setDrawMatches(Long drawMatches) {
		this.drawMatches = drawMatches;
	}

	public Long getLoseMatches() {
		return loseMatches;
	}

	public void setLoseMatches(Long loseMatches) {
		this.loseMatches = loseMatches;
	}

	public Double getGoal() {
		return goal;
	}

	public void setGoal(Double goal) {
		this.goal = goal;
	}

	public Double getLose() {
		return lose;
	}

	public void setLose(Double lose) {
		this.lose = lose;
	}

	public Long getLeageScore() {
		return leageScore;
	}

	public void setLeageScore(Long leageScore) {
		this.leageScore = leageScore;
	}

	public String getRank() {
		return rank;
	}

	public void setRank(String rank) {
		this.rank = rank;
	}

	public String getScoreType() {
		return scoreType;
	}

	public void setScoreType(String scoreType) {
		this.scoreType = scoreType;
	}

}
