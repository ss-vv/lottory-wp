package com.unison.lottery.weibo.data;

public class Relationship {
	
	private Long uid;
	private String[] followers;
	private String[] followings;
	
	public Long getUid() {
		return uid;
	}
	public void setUid(Long uid) {
		this.uid = uid;
	}
	public String[] getFollowers() {
		return followers;
	}
	public void setFollowers(String[] followers) {
		this.followers = followers;
	}
	public String[] getFollowings() {
		return followings;
	}
	public void setFollowings(String[] followings) {
		this.followings = followings;
	}
	
}
