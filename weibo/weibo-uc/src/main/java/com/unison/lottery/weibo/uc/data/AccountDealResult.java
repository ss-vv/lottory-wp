package com.unison.lottery.weibo.uc.data;

import com.unison.lottery.weibo.data.Result;
import com.unison.lottery.weibo.data.WeiboUser;

/**
 * @Description: 账户相关服务的处理结果 
 * @author 江浩翔   
 * @date 2013-10-16 下午2:45:17 
 * @version V1.0
 */
public class AccountDealResult extends Result {
	/** 用户信息*/
	private WeiboUser weiboUser;
	
	public WeiboUser getWeiboUser() {
		return weiboUser;
	}

	public void setWeiboUser(WeiboUser weiboUser) {
		this.weiboUser = weiboUser;
	}
	
}
