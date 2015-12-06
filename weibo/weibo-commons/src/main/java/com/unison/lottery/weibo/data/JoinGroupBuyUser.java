package com.unison.lottery.weibo.data;

import com.xhcms.lottery.commons.data.BetRecord;

public class JoinGroupBuyUser extends BetRecord {

	private static final long serialVersionUID = 1L;
	
	private WeiboUser weiboUser;

	public WeiboUser getWeiboUser() {
		return weiboUser;
	}

	public void setWeiboUser(WeiboUser weiboUser) {
		this.weiboUser = weiboUser;
	}
}