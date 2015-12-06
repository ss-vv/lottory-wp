package com.unison.lottery.weibo.data.vo;

import com.unison.lottery.weibo.common.redis.Reference;
import com.unison.lottery.weibo.data.WeiboUser;
import com.unison.lottery.weibo.data.WinningNew;

/**
 * @desc
 * @author lei.li@unison.net.cn
 * @createTime 2014-1-7
 * @version 1.0
 */
public class WinningNewVO extends WinningNew {

	private static final long serialVersionUID = -2805057514164357307L;

	@Reference(by = "userId")
	private WeiboUser weiboUser;

	public WeiboUser getWeiboUser() {
		return weiboUser;
	}

	public void setWeiboUser(WeiboUser weiboUser) {
		this.weiboUser = weiboUser;
	}
}