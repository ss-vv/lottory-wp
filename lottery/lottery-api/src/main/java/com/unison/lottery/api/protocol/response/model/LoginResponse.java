package com.unison.lottery.api.protocol.response.model;
import com.unison.lottery.api.model.User;
import com.xhcms.lottery.commons.data.Account;
import com.xhcms.lottery.commons.data.HX_user;


public class LoginResponse extends HaveReturnStatusResponse{
	
	
	private User user;
	
	private Account account;

	private HX_user hx_user;

	private String nickname;
	
	private String imageUrl;
	
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

	public Account getAccount() {
		return account;
	}

	public void setAccount(Account account) {
		this.account = account;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public HX_user getHx_user() {
		return hx_user;
	}

	public void setHx_user(HX_user hx_user) {
		this.hx_user = hx_user;
	}

	
	
	

}
