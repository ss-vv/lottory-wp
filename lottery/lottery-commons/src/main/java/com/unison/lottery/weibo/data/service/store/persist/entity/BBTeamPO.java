package com.unison.lottery.weibo.data.service.store.persist.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;


/**
 * @desc 篮球球队数据
 * @author lei.li@unison.net.cn
 * @createTime 2014-3-10
 * @version 1.0
 */
@Entity
@Table(name = "md_bb_team")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class BBTeamPO implements Serializable {

	private static final long serialVersionUID = 5960459745382705565L;
	
	@Id
	private long id;
	private long teamId;			//球队ID（和球探网对应）
	private long leagueId;			//所属联赛ID
	private String shortName;
	private String chineseName;
	private String traditionalName;	//繁体名
	private String englishName;
	private String logoUrl;
	private String teamUrl;
	private long locationId;		//联盟ID
	private long matchAddrID;		//分区ID
	private String city;			//所在城市
	private String gymnasium;		//球场
	private int capacity;			//可容纳人数
	private int joinYear;			//加入联盟年数
	private int winNumber;			//夺冠次数
	private String drillmaster;		//教练名
	private Date createTime;		//记录创建时间
	private Date updateTime;		//记录更新时间
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public long getTeamId() {
		return teamId;
	}
	public void setTeamId(long teamId) {
		this.teamId = teamId;
	}
	public long getLeagueId() {
		return leagueId;
	}
	public void setLeagueId(long leagueId) {
		this.leagueId = leagueId;
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
	public String getLogoUrl() {
		return logoUrl;
	}
	public void setLogoUrl(String logoUrl) {
		this.logoUrl = logoUrl;
	}
	public String getTeamUrl() {
		return teamUrl;
	}
	public void setTeamUrl(String teamUrl) {
		this.teamUrl = teamUrl;
	}
	public long getLocationId() {
		return locationId;
	}
	public void setLocationId(long locationId) {
		this.locationId = locationId;
	}
	public long getMatchAddrID() {
		return matchAddrID;
	}
	public void setMatchAddrID(long matchAddrID) {
		this.matchAddrID = matchAddrID;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getGymnasium() {
		return gymnasium;
	}
	public void setGymnasium(String gymnasium) {
		this.gymnasium = gymnasium;
	}
	public int getCapacity() {
		return capacity;
	}
	public void setCapacity(int capacity) {
		this.capacity = capacity;
	}
	public int getJoinYear() {
		return joinYear;
	}
	public void setJoinYear(int joinYear) {
		this.joinYear = joinYear;
	}
	public int getWinNumber() {
		return winNumber;
	}
	public void setWinNumber(int winNumber) {
		this.winNumber = winNumber;
	}
	public String getDrillmaster() {
		return drillmaster;
	}
	public void setDrillmaster(String drillmaster) {
		this.drillmaster = drillmaster;
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