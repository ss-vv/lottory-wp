package com.unison.lottery.weibo.data.service.store.persist.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 足球子联赛类型
 * 
 * @author Yang Bo
 */
@Entity
@Table(name = "md_fb_sub_league")
public class FBSubLeaguePO implements Serializable {

	private static final long serialVersionUID = -7798776388286892891L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;		// 自增主键
	private long leagueId;	// 联赛id
	private String season;	// 赛季
	private int totalRound;	// 总轮数
	private int currentRound;	// 当前轮
	private Date createTime;	// 记录创建时间
	private Date updateTime;	// 记录更新时间
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public long getLeagueId() {
		return leagueId;
	}
	public void setLeagueId(long leagueId) {
		this.leagueId = leagueId;
	}
	public String getSeason() {
		return season;
	}
	public void setSeason(String season) {
		this.season = season;
	}
	public int getTotalRound() {
		return totalRound;
	}
	public void setTotalRound(int totalRound) {
		this.totalRound = totalRound;
	}
	public int getCurrentRound() {
		return currentRound;
	}
	public void setCurrentRound(int currentRound) {
		this.currentRound = currentRound;
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
