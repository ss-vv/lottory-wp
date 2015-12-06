package com.unison.lottery.weibo.uc.service;

import com.unison.lottery.weibo.data.Result;
import com.xhcms.lottery.commons.data.Account;
import com.xhcms.lottery.commons.data.User;

public interface BankService {
	Result saveBankInfo(User curUser,String newIdNumber,Account newAccount,String newRealname);
}
