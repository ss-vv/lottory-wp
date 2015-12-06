package com.xhcms.ucenter.persistent.service.impl;

import java.util.Date;
import java.util.UUID;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.xhcms.lottery.commons.persist.dao.UserDao;
import com.xhcms.lottery.commons.persist.dao.ValidIdDao;
import com.xhcms.lottery.commons.persist.entity.UserPO;
import com.xhcms.lottery.commons.persist.entity.ValidIdPO;
import com.xhcms.ucenter.persistent.service.IUserValidIdManager;

@Transactional
public class UserValidIdManagerImpl implements IUserValidIdManager {
	
	@Autowired
    private UserDao userDao;
	
	@Autowired
	private ValidIdDao validIdDao;

	private int defaultExpiredMinute=525600;//默认超时时间一年，单位为分钟 
	
	

	@Override
	public String createValidIdByLoginName(String loginName) {
		String validId=null;
		if(StringUtils.isNotBlank(loginName)){
			
			UserPO user=userDao.getUserByUsername(loginName);
			if(null!=user){
				validId=UUID.randomUUID().toString();
				Date now=new Date();
				ValidIdPO validIdPO=new ValidIdPO();
				validIdPO.setUserId(user.getId());
				validIdPO.setValidId(validId);
				validIdPO.setCreateTime(now);
				validIdPO.setExpiredTime(createExpiredTime(now));
				validIdDao.saveOrUpdate(validIdPO);
			}
			
		}
		return validId;
	}

	@Override
	public String createValidIdByPhoneNumber(String phoneNubmer) {
		String validId=null;
		if(StringUtils.isNotBlank(phoneNubmer)){
			validId=UUID.randomUUID().toString();
			UserPO user=userDao.getUserByVerifyedMobile(phoneNubmer);
			if(null!=user){
				Date now=new Date();
				ValidIdPO validIdPO=new ValidIdPO();
				validIdPO.setUserId(user.getId());
				validIdPO.setValidId(validId);
				validIdPO.setCreateTime(now);
				validIdPO.setExpiredTime(createExpiredTime(now));
				validIdDao.saveOrUpdate(validIdPO);
			}
			
		}
		return validId;
	}


	private Date createExpiredTime(Date now) {
		
		return DateUtils.addMinutes(now, defaultExpiredMinute);
	}



	@Override
	public String findUserIdByValidIdAndCurrentTime(String validId) {
		
		return validIdDao.findUserIdByValidIdAndCurrentTime(validId);
	}



	@Override
	public void updateExpiredTime(String validId,String userId) {
		Date now=new Date();
		validIdDao.updateExpiredTime(userId,validId,createExpiredTime(now) );
		
	}

	@Override
	public String createValidIdByUidAndType(String uid, String platType) {
		String validId=null;
		if(StringUtils.isNotBlank(uid) && StringUtils.isNotBlank(platType)){
			validId=UUID.randomUUID().toString();
			UserPO user=userDao.getUserByUidAndType(uid, platType);
			if(null!=user){
				Date now=new Date();
				ValidIdPO validIdPO=new ValidIdPO();
				validIdPO.setUserId(user.getId());
				validIdPO.setValidId(validId);
				validIdPO.setCreateTime(now);
				validIdPO.setExpiredTime(createExpiredTime(now));
				validIdDao.saveOrUpdate(validIdPO);
			}
			
		}
		return validId;
	}

}
