package com.xhcms.ucenter.persistent.service.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.xhcms.lottery.commons.data.User;
import com.xhcms.lottery.commons.persist.dao.UserDao;
import com.xhcms.lottery.commons.persist.entity.UserPO;
import com.xhcms.lottery.lang.Constants;
import com.xhcms.ucenter.persistent.service.IUserService;

/**
 * 
 * @author bean 用户查询
 */
public class UserServiceImpl implements IUserService {
	@Autowired
	private UserDao userDao;

	@Override
	@Transactional
	public User getUser(long userId) {
		return toUser(userDao.get(userId));
	}

	@Override
	@Transactional
	public User getUserByUsername(String username) {
		return toUser(userDao.getUserByUsername(username));
	}

	

   
    
    private User toUser(UserPO po){
        User user = null;
        if (po != null) {
            user = new User();
            BeanUtils.copyProperties(po, user);
        }
        return user;
    }

	@Override
	@Transactional
	public int recordUserLoginFailureCount(String username) {
		UserPO po = userDao.getUserByUsername(username);
		int loginFailureNumber = po.getLoginFailureNumber() + 1;
		po.setLoginFailureNumber(loginFailureNumber);
		userDao.update(po);
		return loginFailureNumber;
	}

	@Override
	@Transactional
	public int lockUserAccount(User user) {
		UserPO po = new UserPO();
		BeanUtils.copyProperties(user, po);
		po.setIsLocked(Constants.ISLOCKED);
		po.setLocked_time(new Date());
		userDao.update(po);
		return 1;
	}

	@Override
	@Transactional
	public int unlockUserAccount(long id) {
		UserPO po = userDao.get(id);
		po.setLoginFailureNumber(0);
		po.setIsLocked(0);
		Calendar cal = Calendar.getInstance();
		cal.set(1970, 0, 1, 0, 0, 0);
		Date d = cal.getTime();
		po.setLocked_time(d);
		userDao.update(po);
		return 1;
	}

	@Override
	@Transactional
	public long autoUnlockUserAccount(User user, long auto_unlock_interval) {
		Date locked_time = user.getLocked_time();
		if(null == locked_time) {
			return 0;
		}
		Date now = new Date();
		long time = (now.getTime() - locked_time.getTime())/1000;
		long interval = time/60;
		if(time >= auto_unlock_interval * 60 && auto_unlock_interval > 2) {
			this.unlockUserAccount(user.getId());
			return -1;
		}
		return auto_unlock_interval - interval;
	}

	

	@Override
	@Transactional(readOnly=true)
	public boolean emailValidAndBinded(String email) {
		boolean result=false;
		List<UserPO> poList = userDao.getValidUsersByBindedEmail(email);
	    if(null!=poList&&!poList.isEmpty()){
	        result=true;
	    }
		return result;
	}

	@Override
	@Transactional(readOnly=true)
	public boolean mobileValidAndBinded(String mobile) {
		boolean result=false;
		List<UserPO> poList = userDao.getValidUsersByBindedMobile(mobile);
	    if(null!=poList&&!poList.isEmpty()){
	        result=true;
	    }
		return result;
	}

	@Override
	@Transactional(readOnly=true)
	public boolean idNumberBindToUser(String idnumber) {
		boolean result=false;
		List<UserPO> poList = userDao.getValidUsersByIdNumber(idnumber);
	    if(null!=poList&&!poList.isEmpty()){
	        result=true;
	    }
		return result;
	}

	@Override
	@Transactional(readOnly=true)
	public User getUserByVerifyedMobile(String mobile) {
		if(StringUtils.isBlank(mobile)){
			return null;
		}
		User user = null;
		try {
			UserPO po = userDao.getUserByVerifyedMobile(mobile);
			if (po != null) {
	            user = new User();
	            BeanUtils.copyProperties(po, user);
	        }
		} catch (Exception e) {
			e.printStackTrace();
		}
		return user;
	}

	@Override
	public List<User> findUsersByIds(List<Long> userIds) {
		List<UserPO> userPOs = userDao.findUsersByIds(userIds);
		List<User> users = new ArrayList<User>();
		if(userPOs != null && !userPOs.isEmpty()){
			User user = null;
			for(UserPO userPO : userPOs){
				user = new User();
				BeanUtils.copyProperties(userPO, user);
				users.add(user);
			}
		}
		return users;
	}

	
	
}