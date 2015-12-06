package com.unison.lottery.weibo.data.service.store.persist.entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * @desc 篮球联赛信息
 * @author lei.li@unison.net.cn
 * @createTime 2014-3-10
 * @version 1.0
 */
@Entity
@Table(name = "md_bb_league_base")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class BBLeaguePO implements Serializable {

	private static final long serialVersionUID = 3880808488939922981L;
	
	@Id
	//private long id;
	private long leagueId;  //联赛id
	private int source;   //数据来源 1球探客户端
	private int processStatus;  //处理状态 0 未处理 1 已转换
	private String color;
	private String shortName;
	private String chineseName;
	private String traditionalName;	//繁体名称
	private String englishName;
	private Integer type;				//比赛分几节
	private String currMatchSeason;
	private int countryId;
	private String country;
	private Integer currYear;
	private Integer currMonth;
	private int sclassKind;			//类型，1联赛2杯赛
	private String sclassTime;		//1节打几分钟
	private String logo;         //联赛logo
	private Date createTime;
	private Date updateTime;

	public long getLeagueId() {
		return leagueId;
	}
	public void setLeagueId(long leagueId) {
		this.leagueId = leagueId;
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
	public int getCountryId() {
		return countryId;
	}
	public void setCountryId(int countryId) {
		this.countryId = countryId;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public Integer getCurrYear() {
		return currYear;
	}
	public void setCurrYear(Integer currYear) {
		this.currYear = currYear;
	}
	public Integer getCurrMonth() {
		return currMonth;
	}
	public void setCurrMonth(Integer currMonth) {
		this.currMonth = currMonth;
	}
	public int getSclassKind() {
		return sclassKind;
	}
	public void setSclassKind(int sclassKind) {
		this.sclassKind = sclassKind;
	}
	public String getSclassTime() {
		return sclassTime;
	}
	public void setSclassTime(String sclassTime) {
		this.sclassTime = sclassTime;
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
	public int getSource() {
		return source;
	}
	public void setSource(int source) {
		this.source = source;
	}
	public int getProcessStatus() {
		return processStatus;
	}
	public void setProcessStatus(int processStatus) {
		this.processStatus = processStatus;
	}
	public String getLogo() {
		return logo;
	}
	public void setLogo(String logo) {
		this.logo = logo;
	}
	
}