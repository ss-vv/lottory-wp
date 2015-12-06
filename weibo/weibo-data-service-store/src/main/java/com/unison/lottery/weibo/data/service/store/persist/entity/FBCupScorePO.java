package com.unison.lottery.weibo.data.service.store.persist.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 杯赛积分，只有分组赛才有
 * 
 * @author Yang Bo
 */
@Entity
@Table(name = "md_fb_cup_score")
public class FBCupScorePO implements Serializable {

	private static final long serialVersionUID = 6856273399223204057L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;			// 自增主键
	private long subCupId;		// 子杯赛id
	private String groupName;	// 组名
	private long teamId;		// 队伍id
	private String teamName;	// 队伍名
	private int rank;			// 排名
	private int totalMatches;	// 总，已比赛总数
	private int winMatches;		// 胜
	private int drawMatches;	// 平
	private int loseMatches;	// 负
	private int goal;			// 得
	private int miss;			// 失
	private int net;			// 净
	private int score;			// 积分
	private Date createTime;	// 创建时间
	private Date updateTime;	// 记录更新时间
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public long getSubCupId() {
		return subCupId;
	}
	public void setSubCupId(long subCupId) {
		this.subCupId = subCupId;
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
	public String getGroupName() {
		return groupName;
	}
	public void setGroupName(String groupName) {
		this.groupName = groupName;
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
	public String toString(){
		return ReflectionToStringBuilder.toString(this, ToStringStyle.MULTI_LINE_STYLE);
	}
}
