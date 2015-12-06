package com.unison.lottery.weibo.data.service.store.persist.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 足球联赛积分表
 * 
 * @author Yang Bo
 */
@Entity
@Table(name = "md_fb_league_score")
public class FBLeagueScorePO implements Serializable {

	private static final long serialVersionUID = 5471582242620296351L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;			// 自增主键
	private long subLeagueId;	// 联赛子类型记录id
	private long teamId;		// 队伍id
	private String teamName;	// 球队中文名
	private int scoreType;		// 积分榜类型: 0 总积分榜，1主场积分，2客场积分，3半场积分榜，4半场主场积分，5半场客场积分'
	private int rank;			// 排名
	private int totalMatches;	// 赛，已赛总数
	private int winMatches;		// 胜
	private int drawMatches;	// 平
	private int loseMatches;	// 负
	private int goal;			// 得，进球数
	private int miss;			// 失，失球数
	private int net;			// 净，净胜球
	private BigDecimal winPercent;	// 胜%
	private BigDecimal drawPercent;	// 平%
	private BigDecimal losePercent;	// 负%
	private BigDecimal averageGoal;	// 均得
	private BigDecimal averageLose;	// 均失
	private int score;				// 积分
	private Date createTime;		// 记录创建时间
	private Date updateTime;		// 更新时间
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public long getSubLeagueId() {
		return subLeagueId;
	}
	public void setSubLeagueId(long subLeagueId) {
		this.subLeagueId = subLeagueId;
	}
	public long getTeamId() {
		return teamId;
	}
	public void setTeamId(long teamId) {
		this.teamId = teamId;
	}
	public String getTeamName() {
		return teamName;
	}
	public void setTeamName(String teamName) {
		this.teamName = teamName;
	}
	public int getScoreType() {
		return scoreType;
	}
	public void setScoreType(int scoreType) {
		this.scoreType = scoreType;
	}
	public int getRank() {
		return rank;
	}
	public void setRank(int rank) {
		this.rank = rank;
	}
	public int getTotalMatches() {
		return totalMatches;
	}
	public void setTotalMatches(int totalMatches) {
		this.totalMatches = totalMatches;
	}
	public int getWinMatches() {
		return winMatches;
	}
	public void setWinMatches(int winMatches) {
		this.winMatches = winMatches;
	}
	public int getDrawMatches() {
		return drawMatches;
	}
	public void setDrawMatches(int drawMatches) {
		this.drawMatches = drawMatches;
	}
	public int getLoseMatches() {
		return loseMatches;
	}
	public void setLoseMatches(int loseMatches) {
		this.loseMatches = loseMatches;
	}
	public int getGoal() {
		return goal;
	}
	public void setGoal(int goal) {
		this.goal = goal;
	}
	public int getMiss() {
		return miss;
	}
	public void setMiss(int miss) {
		this.miss = miss;
	}
	public int getNet() {
		return net;
	}
	public void setNet(int net) {
		this.net = net;
	}
	public BigDecimal getWinPercent() {
		return winPercent;
	}
	public void setWinPercent(BigDecimal winPercent) {
		this.winPercent = winPercent;
	}
	public BigDecimal getDrawPercent() {
		return drawPercent;
	}
	public void setDrawPercent(BigDecimal drawPercent) {
		this.drawPercent = drawPercent;
	}
	public BigDecimal getLosePercent() {
		return losePercent;
	}
	public void setLosePercent(BigDecimal losePercent) {
		this.losePercent = losePercent;
	}
	public BigDecimal getAverageGoal() {
		return averageGoal;
	}
	public void setAverageGoal(BigDecimal averageGoal) {
		this.averageGoal = averageGoal;
	}
	public BigDecimal getAverageLose() {
		return averageLose;
	}
	public void setAverageLose(BigDecimal averageLose) {
		this.averageLose = averageLose;
	}
	public int getScore() {
		return score;
	}
	public void setScore(int score) {
		this.score = score;
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
	
}
