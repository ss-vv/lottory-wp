package com.unison.lottery.api.vGroupMembers.data;

import java.math.BigDecimal;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.map.annotate.JsonSerialize;

@JsonIgnoreProperties(ignoreUnknown=true) 
@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
public class GroupUser {

	private String imageUrl;//用户头衔
	
	private String nickName;
	
	private Long userId;//用户的id
	
	private String totalAward;//总中奖金额
	

	public String getTotalAward() {
		return totalAward;
	}

	public void setTotalAward(BigDecimal totalAward) {
		this.totalAward = String.valueOf(totalAward);
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	
	
}
