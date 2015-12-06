package com.unison.lottery.weibo.data;

import java.math.BigDecimal;

public class RealFollower extends WeiboUser{
	private static final long serialVersionUID = 6973195999494584005L;
	private int  multiple;//倍数
	private int buyAmount;//购买总额
	private BigDecimal afterTaxBonus;//税后奖金
	private long followSchemeId;//跟单方案 的方案id
	private String sponsor;//发起人用户名
	private long sponsorId;//发起人id
	
	private boolean myFollow;//我是否关注了这个人
	
	public int getMultiple() {
		return multiple;
	}
	public void setMultiple(int multiple) {
		this.multiple = multiple;
	}
	public int getBuyAmount() {
		return buyAmount;
	}
	public void setBuyAmount(int buyAmount) {
		this.buyAmount = buyAmount;
	}
	public BigDecimal getAfterTaxBonus() {
		return afterTaxBonus;
	}
	public void setAfterTaxBonus(BigDecimal afterTaxBonus) {
		this.afterTaxBonus = afterTaxBonus;
	}
	public long getFollowSchemeId() {
		return followSchemeId;
	}
	public void setFollowSchemeId(long followSchemeId) {
		this.followSchemeId = followSchemeId;
	}
	public String getSponsor() {
		return sponsor;
	}
	public void setSponsor(String sponsor) {
		this.sponsor = sponsor;
	}
	public long getSponsorId() {
		return sponsorId;
	}
	public void setSponsorId(long sponsorId) {
		this.sponsorId = sponsorId;
	}
	public boolean isMyFollow() {
		return myFollow;
	}
	public void setMyFollow(boolean myFollow) {
		this.myFollow = myFollow;
	}
}
