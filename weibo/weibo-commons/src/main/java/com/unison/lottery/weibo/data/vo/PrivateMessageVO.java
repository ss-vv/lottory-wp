package com.unison.lottery.weibo.data.vo;

import com.unison.lottery.weibo.data.PrivateMessage;
import com.unison.lottery.weibo.data.WeiboUser;

public class PrivateMessageVO extends PrivateMessage{
	private WeiboUser ownerUser;

	public WeiboUser getOwnerUser() {
		return ownerUser;
	}

	public void setOwnerUser(WeiboUser ownerUser) {
		this.ownerUser = ownerUser;
	}
}
