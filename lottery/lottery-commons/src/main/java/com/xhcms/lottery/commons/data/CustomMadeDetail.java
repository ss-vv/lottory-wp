/**
 * 
 */
package com.xhcms.lottery.commons.data;

import java.util.Date;

/**
 * @author Bean.Long
 * 
 */
public class CustomMadeDetail {
	private Long id;
	private String followedUsername;
	private String followUsername;
	private Date createTime;
	private Long schemeId;
	private int betMoney;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getFollowUsername() {
		return followUsername;
	}

	public void setFollowUsername(String followUsername) {
		this.followUsername = followUsername;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Long getSchemeId() {
		return schemeId;
	}

	public void setSchemeId(Long schemeId) {
		this.schemeId = schemeId;
	}

	public int getBetMoney() {
		return betMoney;
	}

	public void setBetMoney(int betMoney) {
		this.betMoney = betMoney;
	}

	public String getFollowedUsername() {
		return followedUsername;
	}

	public void setFollowedUsername(String followedUsername) {
		this.followedUsername = followedUsername;
	}
}
