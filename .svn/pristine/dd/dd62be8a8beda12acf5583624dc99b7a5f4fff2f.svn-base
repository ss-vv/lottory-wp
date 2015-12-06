package com.unison.lottery.weibo.data.service.store.persist.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * 足球联赛、杯赛数据
 * @author Wang Lei
 *
 */
@Entity
@Table(name = "md_fb_league")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class FBLeaguePO implements Serializable {

	private static final long serialVersionUID = 3880808488939922981L;
	@Id
	private long leagueId;
	private String colour;
	private String chineseName;
	private String chineseNameAll;
	private String traditionalName;
	private String traditionalNameAll;
	private String englishName;
	private String englishNameAll;
	private int type;
	private int sumRound;
	private int currRound;
	private String currMatchSeason;
	private int countryID;
	private String country;
	
	//0洲际赛事,1欧洲联赛,2美洲联赛,3亚洲联赛,4大洋洲联赛,5非洲联赛
	private int areaID;
	private String logoUrl;
	private Date createTime;
	private Date updateTime;
	
	public long getLeagueId() {
		return leagueId;
	}
	public void setLeagueId(long leagueId) {
		this.leagueId = leagueId;
	}
	public String getColour() {
		return colour;
	}
	public void setColour(String colour) {
		this.colour = colour;
	}
	public String getChineseName() {
		return chineseName;
	}
	public void setChineseName(String chineseName) {
		this.chineseName = chineseName;
	}
	public String getChineseNameAll() {
		return chineseNameAll;
	}
	public void setChineseNameAll(String chineseNameAll) {
		this.chineseNameAll = chineseNameAll;
	}
	public String getTraditionalName() {
		return traditionalName;
	}
	public void setTraditionalName(String traditionalName) {
		this.traditionalName = traditionalName;
	}
	public String getTraditionalNameAll() {
		return traditionalNameAll;
	}
	public void setTraditionalNameAll(String traditionalNameAll) {
		this.traditionalNameAll = traditionalNameAll;
	}
	public String getEnglishName() {
		return englishName;
	}
	public void setEnglishName(String englishName) {
		this.englishName = englishName;
	}
	public String getEnglishNameAll() {
		return englishNameAll;
	}
	public void setEnglishNameAll(String englishNameAll) {
		this.englishNameAll = englishNameAll;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public int getSumRound() {
		return sumRound;
	}
	public void setSumRound(int sumRound) {
		this.sumRound = sumRound;
	}
	public int getCurrRound() {
		return currRound;
	}
	public void setCurrRound(int currRound) {
		this.currRound = currRound;
	}
	public String getCurrMatchSeason() {
		return currMatchSeason;
	}
	public void setCurrMatchSeason(String currMatchSeason) {
		this.currMatchSeason = currMatchSeason;
	}
	public int getCountryID() {
		return countryID;
	}
	public void setCountryID(int countryID) {
		this.countryID = countryID;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public int getAreaID() {
		return areaID;
	}
	public void setAreaID(int areaID) {
		this.areaID = areaID;
	}
	public String getLogoUrl() {
		return logoUrl;
	}
	public void setLogoUrl(String logoUrl) {
		this.logoUrl = logoUrl;
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
	
	@Override
	public String toString() {
		return ReflectionToStringBuilder.toString(this, 
				ToStringStyle.MULTI_LINE_STYLE);
	}
}
