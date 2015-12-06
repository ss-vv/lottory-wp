package com.xhcms.lottery.account.service.impl;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.xhcms.commons.util.Text;
import com.xhcms.lottery.account.service.UserService;
import com.xhcms.lottery.commons.data.User;
import com.xhcms.lottery.commons.persist.dao.UserDao;
import com.xhcms.lottery.commons.persist.entity.UserPO;

public class UserServiceImpl implements UserService {
	@Autowired
	private UserDao userDao;

	@Override
	@Transactional(readOnly = true)
	public boolean checkPasswd(long userId, String passwd) {
		UserPO po = userDao.get(userId);
		if (po != null) {
			String pwd = Text.MD5Encode(passwd);
			if (pwd.equals(po.getPassword())) {
				return true;
			}
		}
		return false;
	}

    @Override
    @Transactional(readOnly = true)
    public User getUser(Long userId) {
        UserPO po = userDao.get(userId);
        User user = new User();
        if (po != null) {
            BeanUtils.copyProperties(po, user);
        }
        return user;
    }
	
	
}
