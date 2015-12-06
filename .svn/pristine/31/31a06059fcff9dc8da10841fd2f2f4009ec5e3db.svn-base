package com.unison.lottery.weibo.data.crawler.service.store.persist.dao.entity;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.hibernate.annotations.Type;

/**
 * @author 彭保星
 *
 * @since 2014年10月28日下午3:26:28
 */
@Entity
@Table(name="md_bb_league_base")
public class BasketBallLeagueInfoPO implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -5251203820102656207L;
	@Id
	private Integer id;
	private String leagueId;
	private Integer source;
	private Integer processStatus;
	private String color;
	private String chineseName;
	private String shortName;
	private String traditionalName;
	private String englishName;
	private Integer sclassKind;  //赛事类型：1联赛，2杯赛
	private Integer type;//比赛的节数
	private String currMatchSeason;
	private String countryId;
	private String country;
	private String currYear;
	private String currMonth;
	private String sclassTime;
	private String logo;
	@Type(type = "timestamp")
	private Date createTime;
	@Type(type = "timestamp")
	private Date updateTime;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	
	public String getLeagueId() {
		return leagueId;
	}
	public void setLeagueId(String leagueId) {
		this.leagueId = leagueId;
	}
	public String toString(){
		return ReflectionToStringBuilder.toString(this,
				ToStringStyle.MULTI_LINE_STYLE);
	}

	public Integer getSource() {
		return source;
	}
	public void setSource(Integer source) {
		this.source = source;
	}
	public Integer getProcessStatus() {
		return processStatus;
	}
	public void setProcessStatus(Integer processStatus) {
		this.processStatus = processStatus;
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
	
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	
	public String getCurrMatchSeason() {
		return currMatchSeason;
	}
	public void setCurrMatchSeason(String currMatchSeason) {
		this.currMatchSeason = currMatchSeason;
	}
	public String getCountryId() {
		return countryId;
	}
	public void setCountryId(String countryId) {
		this.countryId = countryId;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	
	public String getColor() {
		return color;
	}
	public void setColor(String color) {
		this.color = color;
	}
	public String getShortName() {
		return shortName;
	}
	public void setShortName(String shortName) {
		this.shortName = shortName;
	}
	public Integer getSclassKind() {
		return sclassKind;
	}
	public void setSclassKind(Integer sclassKind) {
		this.sclassKind = sclassKind;
	}
	public String getCurrYear() {
		return currYear;
	}
	public void setCurrYear(String currYear) {
		this.currYear = currYear;
	}
	public String getCurrMonth() {
		return currMonth;
	}
	public void setCurrMonth(String currMonth) {
		this.currMonth = currMonth;
	}
	public String getSclassTime() {
		return sclassTime;
	}
	public void setSclassTime(String sclassTime) {
		this.sclassTime = sclassTime;
	}
	public String getLogo() {
		return logo;
	}
	public void setLogo(String logo) {
		this.logo = logo;
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
