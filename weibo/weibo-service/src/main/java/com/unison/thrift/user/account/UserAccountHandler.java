package com.unison.thrift.user.account;

import org.apache.thrift.TException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.unison.lottery.weibo.data.WeiboUser;
import com.unison.lottery.weibo.uc.service.UserAccountService;
import com.unison.thrift.user.account.gen.UserAccountHandlerGen.Iface;

@Service
public class UserAccountHandler implements Iface {

	@Autowired
	private UserAccountService accountService;
	
	@Override
	public long findWeiboUserIdByLotteryUid(String uid) throws TException {
		WeiboUser weiboUser = accountService.findWeiboUserByLotteryUid(uid);
		if(weiboUser != null) {
			return weiboUser.getWeiboUserId();
		}
		return 0;
	}
	
}