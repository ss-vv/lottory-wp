package com.unison.lottery.weibo.dataservice.parse.model;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class BBTeamContentData {

	private String teamId;
	private String leaId;
	private String shortName;
	private String chineseName;
	private String traditionalName;
	private String englishName;
	private String logo;
	private String url;
	private String locationID;
	private String matchAddrID;
	private String city;
	private String gymnasium;
	private String capacity;
	private String joinYear;
	private String firstTime;
	private String drillmaster;
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
	public String getLogo() {
		return logo;
	}
	public void setLogo(String logo) {
		this.logo = logo;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getLocationID() {
		return locationID;
	}
	public void setLocationID(String locationID) {
		this.locationID = locationID;
	}
	public String getMatchAddrID() {
		return matchAddrID;
	}
	public void setMatchAddrID(String matchAddrID) {
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
	public String getCapacity() {
		return capacity;
	}
	public void setCapacity(String capacity) {
		this.capacity = capacity;
	}
	public String getJoinYear() {
		return joinYear;
	}
	public void setJoinYear(String joinYear) {
		this.joinYear = joinYear;
	}
	public String getFirstTime() {
		return firstTime;
	}
	public void setFirstTime(String firstTime) {
		this.firstTime = firstTime;
	}
	public String getDrillmaster() {
		return drillmaster;
	}
	public void setDrillmaster(String drillmaster) {
		this.drillmaster = drillmaster;
	}
	public String getTeamId() {
		return teamId;
	}
	public void setTeamId(String teamId) {
		this.teamId = teamId;
	}
	public String getLeaId() {
		return leaId;
	}
	public void setLeaId(String leaId) {
		this.leaId = leaId;
	}

	public String toString(){
		return ReflectionToStringBuilder.toString(this,
				ToStringStyle.MULTI_LINE_STYLE);
	}
}
