package com.xhcms.lottery.account.service;

import com.xhcms.lottery.commons.data.User;

public interface UserService {
    
    User getUser(Long userId);
    
	boolean checkPasswd(long userId, String passwd);
}
