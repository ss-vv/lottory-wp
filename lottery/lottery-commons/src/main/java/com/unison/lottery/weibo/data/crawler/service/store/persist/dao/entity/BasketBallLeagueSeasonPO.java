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
@Table(name="md_bb_sjlsgx_base")
public class BasketBallLeagueSeasonPO implements Serializable {

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
	private String subLeagueId; //子联赛id
	private String subLeagueName; //子联赛简体名称
	private Integer isSubLeague;  //是否是子联赛:0是，1否
	private String kind;
	private String isCrawler;
	private String isNow; //是否当前
	@Type(type = "timestamp")
	private Date createTime;
	@Type(type = "timestamp")
	private Date updateTime;
	
	public String getIsNow() {
		return isNow;
	}
	
	public String getKind() {
		return kind;
	}

	public void setKind(String kind) {
		this.kind = kind;
	}

	public String getIsCrawler() {
		return isCrawler;
	}

	public void setIsCrawler(String isCrawler) {
		this.isCrawler = isCrawler;
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
	public Integer getIsSubLeague() {
		return isSubLeague;
	}
	public void setIsSubLeague(Integer isSubLeague) {
		this.isSubLeague = isSubLeague;
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
