package com.unison.lottery.api.redEnvalope.model;

import java.io.Serializable;

public class EnvalopeGrabHistory implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 6640577663009966669L;
	private Long grabEnvalopeId;
	private String imageUrl;
	private String nickname;
	private String grabTime;
	private Long grabAmount;
	private int luckUser; //运气王：1是抢最多的用户，0其他用户
	
	public EnvalopeGrabHistory(Long grabEnvalopeId,String imageUrl,String nickName,
			String grabTime,Long grabAmount){
		this.grabAmount = grabAmount;
		this.grabEnvalopeId = grabEnvalopeId;
		this.grabTime = grabTime;
		this.imageUrl = imageUrl;
		this.nickname = nickName;
	}
	public Long getGrabEnvalopeId() {
		return grabEnvalopeId;
	}
	public void setGrabEnvalopeId(Long grabEnvalopeId) {
		this.grabEnvalopeId = grabEnvalopeId;
	}
	
	public int getLuckUser() {
		return luckUser;
	}
	public void setLuckUser(int luckUser) {
		this.luckUser = luckUser;
	}
	public String getImageUrl() {
		return imageUrl;
	}
	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}
	public String getNickname() {
		return nickname;
	}
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	public String getGrabTime() {
		return grabTime;
	}
	public void setGrabTime(String grabTime) {
		this.grabTime = grabTime;
	}
	public Long getGrabAmount() {
		return grabAmount;
	}
	public void setGrabAmount(Long grabAmount) {
		this.grabAmount = grabAmount;
	}
	
	
}
