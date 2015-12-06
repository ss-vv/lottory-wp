/**
 * 
 */
package com.xhcms.lottery.commons.persist.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Version;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * @author Bean.Long 跟单合买定制表
 */
@Entity
@Table(name = "lt_custom_made")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class CustomMadePO implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -4488839772955867186L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	/**
	 * 定制人的用户信息
	 */
	@Column(name = "user_id", nullable = false)
	private Long userId;

	@Column(name = "username", nullable = false)
	private String username;

	/**
	 * 被跟单用户
	 */
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="followed_user_id")
	private UserPO followedUser;

	/**
	 * 每日跟单合买最大的金额
	 */
	@Column(name = "maxmoney")
	private int maxMoney;

	/**
	 * 每日跟单合买最大次数
	 */
	@Column(name = "maxfollowcount")
	private int maxFollowCount;
	
	/**
	 * 定制有效开始时间
	 */
	@Column(name = "starttime")
	private Date startTime;

	/**
	 * 定制有效的结束时间
	 */
	@Column(name = "endtime")
	private Date endTime;
	
	/**
	 * 定制的玩法ID列表，用户","分隔
	 */
	@Column(name = "playids")
	private String playIds;
	
	/**
	 * 是否已定制跟单
	 */
	@Column(name = "follow_buy")
	private boolean followBuy;
	
	/**
	 * 每次跟单的倍数
	 */
	@Column(name = "follow_multiple")
	private int followMultiple;

	/**
	 * 是否已经定制合买
	 */
	@Column(name = "group_buy")
	private boolean groupBuy;

	/**
	 * 每次合买的分数（每份为1元）
	 */
	@Column(name = "group_money")
	private int groupMoney;

	/**
	 * 连续跟单未中奖的次数
	 */
	@Column(name = "continualfailed_times")
	private int continualFailedTimes;
	
	/**
	 * 设置的最大跟单未中奖的次数
	 */
	@Column(name = "maxcontinualfailed_times")
	private int maxContinualFailedTimes;
	
	/**
	 * 连续跟单未中奖之后是否停止定制
	 */
	@Column(name = "stop_continualfailed")
	private boolean stopWhenContinualFailed;
	
	@Version
	private Integer version;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public UserPO getFollowedUser() {
		return followedUser;
	}

	public void setFollowedUser(UserPO followedUser) {
		this.followedUser = followedUser;
	}

	public int getMaxMoney() {
		return maxMoney;
	}

	public void setMaxMoney(int maxMoney) {
		this.maxMoney = maxMoney;
	}

	public int getMaxFollowCount() {
		return maxFollowCount;
	}

	public void setMaxFollowCount(int maxFollowCount) {
		this.maxFollowCount = maxFollowCount;
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public String getPlayIds() {
		return playIds;
	}

	public void setPlayIds(String playIds) {
		this.playIds = playIds;
	}

	public boolean isFollowBuy() {
		return followBuy;
	}

	public void setFollowBuy(boolean followBuy) {
		this.followBuy = followBuy;
	}

	public int getFollowMultiple() {
		return followMultiple;
	}

	public void setFollowMultiple(int followMultiple) {
		this.followMultiple = followMultiple;
	}

	public boolean isGroupBuy() {
		return groupBuy;
	}

	public void setGroupBuy(boolean groupBuy) {
		this.groupBuy = groupBuy;
	}

	public int getGroupMoney() {
		return groupMoney;
	}

	public void setGroupMoney(int groupMoney) {
		this.groupMoney = groupMoney;
	}

	public Integer getVersion() {
		return version;
	}

	public int getContinualFailedTimes() {
		return continualFailedTimes;
	}

	public void setContinualFailedTimes(int continualFailedTimes) {
		this.continualFailedTimes = continualFailedTimes;
	}

	public boolean isStopWhenContinualFailed() {
		return stopWhenContinualFailed;
	}

	public void setStopWhenContinualFailed(boolean stopWhenContinualFailed) {
		this.stopWhenContinualFailed = stopWhenContinualFailed;
	}

	public int getMaxContinualFailedTimes() {
		return maxContinualFailedTimes;
	}

	public void setMaxContinualFailedTimes(int maxContinualFailedTimes) {
		this.maxContinualFailedTimes = maxContinualFailedTimes;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}
}
