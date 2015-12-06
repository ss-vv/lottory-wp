package com.unison.lottery.weibo.dataservice.crawler.service.model;

import java.util.Date;

/**
 * 赛季信息
 * @author 彭保星
 *
 * @since 2014年10月28日下午3:39:40
 */
public class SeasonModel {
	private Integer seasonId;
	private String leagueId;
	private String seasonName;
	private Integer crawlerCount; //已抓取的轮数
	private Integer source; //来源：1球探
	private Integer processStatus; //是否已处理
	private Integer totalRound; //联赛赛季总轮数
	private String subLeagueId; //子联赛id
	private String subLeagueName; //子联赛简体名称
	private String subTranditionName; //子联赛繁体名称
	private Integer isSubLeague;  //是否是子联赛:0是，1否
	private Integer isHaveSubLeague; //是否有子联赛:0有，1没有
	private String isNow; //是否当前，True or False
	private Date createTime;
	private Date updateTime;
	
	public SeasonModel(){
		
	}
	public String getIsNow() {
		return isNow;
	}
	public void setIsNow(String isNow) {
		this.isNow = isNow;
	}
	public Integer getCrawlerCount() {
		return crawlerCount;
	}
	public void setCrawlerCount(Integer crawlerCount) {
		this.crawlerCount = crawlerCount;
	}
	public Integer getIsSubLeague() {
		return isSubLeague;
	}
	public void setIsSubLeague(Integer isSubLeague) {
		this.isSubLeague = isSubLeague;
	}
	
	public Integer getIsHaveSubLeague() {
		return isHaveSubLeague;
	}
	public void setIsHaveSubLeague(Integer isHaveSubLeague) {
		this.isHaveSubLeague = isHaveSubLeague;
	}
	public String getSubTranditionName() {
		return subTranditionName;
	}
	public void setSubTranditionName(String subTranditionName) {
		this.subTranditionName = subTranditionName;
	}
	
	public Integer getTotalRound() {
		return totalRound;
	}
	public void setTotalRound(Integer totalRound) {
		this.totalRound = totalRound;
	}
	public String getSubLeagueId() {
		return subLeagueId;
	}
	public void setSubLeagueId(String subLeagueId) {
		this.subLeagueId = subLeagueId;
	}
	public String getSubLeagueName() {
		return subLeagueName;
	}
	public void setSubLeagueName(String subLeagueName) {
		this.subLeagueName = subLeagueName;
	}
	public Integer getSeasonId() {
		return seasonId;
	}
	public void setSeasonId(Integer seasonId) {
		this.seasonId = seasonId;
	}
	
	public String getLeagueId() {
		return leagueId;
	}
	public void setLeagueId(String leagueId) {
		this.leagueId = leagueId;
	}
	public String getSeasonName() {
		return seasonName;
	}
	public void setSeasonName(String seasonName) {
		this.seasonName = seasonName;
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
