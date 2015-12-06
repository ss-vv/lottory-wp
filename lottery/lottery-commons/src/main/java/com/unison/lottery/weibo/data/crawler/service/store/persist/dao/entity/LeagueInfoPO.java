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
@Table(name="md_fb_league_base")
public class LeagueInfoPO implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -5251203820102656207L;
	@Id
	private Integer id;
	private String leagueId;
	private Integer source;
	private Integer processStatus;
	private String colour;
	private String chineseName;
	private String chineseNameAll;
	private String traditionalName;
	private String traditionalNameAll;
	private String englishName;
	private String englishNameAll;
	private Integer type;  //赛事类型：1联赛，2杯赛
	private Integer sumRound;
	private Integer currRound;
	private String currMatchSeason;
	private String countryId;
	private String country;
	private Integer areaId; //0国际联赛,1欧洲联赛,2美洲联赛,3亚洲联赛,4大洋洲联赛,5非洲联赛
	private String documentPath; //
	private Integer importance; //是否重要赛事：0次要，1重要
	private String logoUrl;
	private String ruleInfo;
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
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	public Integer getSumRound() {
		return sumRound;
	}
	public void setSumRound(Integer sumRound) {
		this.sumRound = sumRound;
	}
	public Integer getCurrRound() {
		return currRound;
	}
	public void setCurrRound(Integer currRound) {
		this.currRound = currRound;
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
	public Integer getAreaId() {
		return areaId;
	}
	public void setAreaId(Integer areaId) {
		this.areaId = areaId;
	}
	public String getDocumentPath() {
		return documentPath;
	}
	public void setDocumentPath(String documentPath) {
		this.documentPath = documentPath;
	}
	public Integer getImportance() {
		return importance;
	}
	public void setImportance(Integer importance) {
		this.importance = importance;
	}
	public String getLogoUrl() {
		return logoUrl;
	}
	public void setLogoUrl(String logoUrl) {
		this.logoUrl = logoUrl;
	}
	public String getRuleInfo() {
		return ruleInfo;
	}
	public void setRuleInfo(String ruleInfo) {
		this.ruleInfo = ruleInfo;
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
