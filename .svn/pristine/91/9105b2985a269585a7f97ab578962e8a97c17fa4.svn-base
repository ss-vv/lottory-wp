/**
 * 
 */
package com.xhcms.lottery.commons.data;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import org.apache.commons.lang.time.DateUtils;

/**
 * @author Bean.Long
 * 
 */
public class CustomMade implements Serializable {
	private static final long serialVersionUID = 6196265137886976135L;

	private Long id;
	private Long userId;
	private String username;
	private User followedUser;
	private int maxMoney;
	private int maxFollowCount;
	private Date startTime;
	private Date endTime;
	private Set<String> plays = new HashSet<String>();
	private String playIds;
	private boolean followBuy;
	private int followMultiple;
	private boolean groupBuy;
	private int groupMoney;
	private int continualFailedTimes;
	private int maxContinualFailedTimes;
	private boolean stopWhenContinualFailed;
	
	public CustomMade() {
		maxMoney = 50;
		maxFollowCount = 5;
		startTime = Calendar.getInstance().getTime();
		endTime = DateUtils.addMonths(startTime, 3);
		followBuy = false;
		followMultiple = 1;
		groupBuy = false;
		groupMoney = 10;
	}
	
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

	public User getFollowedUser() {
		return followedUser;
	}

	public void setFollowedUser(User followedUser) {
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

	public Set<String> getPlays() {
		return plays;
	}

	public void setPlays(Set<String> plays) {
		this.plays = plays;
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

	public int getContinualFailedTimes() {
		return continualFailedTimes;
	}

	public void setContinualFailedTimes(int continualFailedTimes) {
		this.continualFailedTimes = continualFailedTimes;
	}

	public int getMaxContinualFailedTimes() {
		return maxContinualFailedTimes;
	}

	public void setMaxContinualFailedTimes(int maxContinualFailedTimes) {
		this.maxContinualFailedTimes = maxContinualFailedTimes;
	}

	public boolean isStopWhenContinualFailed() {
		return stopWhenContinualFailed;
	}

	public void setStopWhenContinualFailed(boolean stopWhenContinualFailed) {
		this.stopWhenContinualFailed = stopWhenContinualFailed;
	}
}
