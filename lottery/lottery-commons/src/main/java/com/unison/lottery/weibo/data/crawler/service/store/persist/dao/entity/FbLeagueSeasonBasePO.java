package com.unison.lottery.weibo.data.crawler.service.store.persist.dao.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Type;

/**
 * @author 彭保星
 *
 * @since 2014年10月30日下午12:43:04
 */
@Entity
@Table(name="md_fb_league_season_base")
public class FbLeagueSeasonBasePO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8060736378385156267L;
	@Id
	private Integer seasonId;
	private String leagueId;
	private String seasonName;
	private Integer source;
	private Integer processStatus;
	private Integer crawlerCount;
	private Integer totalRound; //联赛赛季总轮数
	private String subLeagueId; //子联赛id
	private String subLeagueName; //子联赛简体名称
	private String subTranditionName; //子联赛繁体名称
	private Integer isSubLeague;  //是否是子联赛:0是，1否
	private Integer isHaveSubLeague; //是否有子联赛:0有，1没有
	private String isNow; //是否当前
	private Integer isCrawler; //是否已经抓取杯赛积分：1已抓取2未抓取3要抓取的积分不存在
	@Type(type = "timestamp")
	private Date createTime;
	@Type(type = "timestamp")
	private Date updateTime;
	
	public FbLeagueSeasonBasePO(String leagueId,String subLeagueId,String seasonName,Integer seasonId,Integer isSubLeague){
		this.seasonId = seasonId;
		this.leagueId= leagueId;
		this.subLeagueId = subLeagueId;
		this.seasonName = seasonName;
		this.isSubLeague = isSubLeague;
	}
	/**
	 * default constructor
	 */
	public FbLeagueSeasonBasePO(){
		
	}
	public String getIsNow() {
		return isNow;
	}
	public void setIsNow(String isNow) {
		this.isNow = isNow;
	}
	public Integer getSeasonId() {
		return seasonId;
	}
	public void setSeasonId(Integer seasonId) {
		this.seasonId = seasonId;
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
	public String getSubTranditionName() {
		return subTranditionName;
	}
	public void setSubTranditionName(String subTranditionName) {
		this.subTranditionName = subTranditionName;
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
	
	public Integer getCrawlerCount() {
		return crawlerCount;
	}
	public void setCrawlerCount(Integer crawlerCount) {
		this.crawlerCount = crawlerCount;
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
