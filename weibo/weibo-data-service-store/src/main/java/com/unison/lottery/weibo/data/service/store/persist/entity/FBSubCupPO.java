package com.unison.lottery.weibo.data.service.store.persist.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 联赛杯赛子类型
 * 
 * @author Yang Bo
 */
@Entity
@Table(name = "md_fb_sub_cup")
public class FBSubCupPO implements Serializable {
	
	private static final long serialVersionUID = -103096678033859605L;
	
	@Id
	private Long id;				// 球探网联赛子类型id
	private Long leagueId;			// 子类型所属的联赛id
	private String season;			// 赛季
	private boolean groupingMatch;	// 是否分组赛
	private String chineseName;		// 子杯赛简体名
	private String traditionalName;	// 子杯赛繁体名
	private String englishName;		// 子杯赛英文名
	private int groupCount;			// 分组总数
	private boolean current;		// 是否当前子杯赛，进行中的子类型
	private int winnerCount;		// 出线球队数
	private Date createTime;		// 创建时间
	private Date updateTime;		// 更新时间
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getLeagueId() {
		return leagueId;
	}
	public void setLeagueId(Long leagueId) {
		this.leagueId = leagueId;
	}
	public String getSeason() {
		return season;
	}
	public void setSeason(String season) {
		this.season = season;
	}
	public boolean isGroupingMatch() {
		return groupingMatch;
	}
	public void setGroupingMatch(boolean groupingMatch) {
		this.groupingMatch = groupingMatch;
	}
	public String getChineseName() {
		return chineseName;
	}
	public void setChineseName(String chineseName) {
		this.chineseName = chineseName;
	}
	public String getTraditionalName() {
		return traditionalName;
	}
	public void setTraditionalName(String traditionalName) {
		this.traditionalName = traditionalName;
	}
	public String getEnglishName() {
		return englishName;
	}
	public void setEnglishName(String englishName) {
		this.englishName = englishName;
	}
	public int getGroupCount() {
		return groupCount;
	}
	public void setGroupCount(int groupCount) {
		this.groupCount = groupCount;
	}
	public boolean isCurrent() {
		return current;
	}
	public void setCurrent(boolean current) {
		this.current = current;
	}
	public int getWinnerCount() {
		return winnerCount;
	}
	public void setWinnerCount(int winnerCount) {
		this.winnerCount = winnerCount;
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
