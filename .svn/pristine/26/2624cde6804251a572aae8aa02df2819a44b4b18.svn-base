package com.xhcms.ucenter.persistent.service.impl;

import java.util.Date;

import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.xhcms.commons.lang.Assert;
import com.xhcms.lottery.commons.persist.dao.UserDao;
import com.xhcms.lottery.commons.persist.entity.UserPO;
import com.xhcms.lottery.commons.persist.service.VerifyException;
import com.xhcms.lottery.lang.EntityStatus;
import com.xhcms.ucenter.lang.AppCode;
import com.xhcms.ucenter.lang.UCEntityStatus;
import com.xhcms.ucenter.lang.UCEntityType;
import com.xhcms.ucenter.persistent.dao.IVerifyDao;
import com.xhcms.ucenter.persistent.entity.VerifyPO;
import com.xhcms.ucenter.persistent.service.IVerifyService;

/**
 * <p>Title: 验证手机号码，生成校验码</p>
 * <p>Description: </p>
 * <p>Copyright：Copyright (c) 2011</p>
 * <p>Company：XingHui Spirit (Beijing) Technical Co.,Ltd.</p>
 * 
 * @author jiajiancheng
 * @version 1.0.0
 */ 
public class MobileServiceImpl implements IVerifyService {

    @Autowired
    private IVerifyDao verifyDao;

    @Autowired
    private UserDao userDao;

    private int survivalTime = 60;

    @Override
    @Transactional
    public String generateCode(long uid, String target) {
        VerifyPO verify = verifyDao.get(uid, UCEntityType.VERIFY_MOBILE);
        // String code = RandomStringUtils.randomAlphabetic(6);
        String code = RandomStringUtils.randomNumeric(6);
        if (verify == null) {
            verify = new VerifyPO();
            verify.setUid(uid);
            verify.setCreatedTime(new Date());
            verify.setExpiryTime(DateUtils.addMinutes(new Date(), survivalTime ));
            verify.setCode(code);
            verify.setTarget(target);
            verify.setType(UCEntityType.VERIFY_MOBILE);
            verifyDao.save(verify);
        } else {
            verify.setStatus(UCEntityStatus.VERIFY_NOT);
            verify.setCreatedTime(new Date());
            verify.setExpiryTime(DateUtils.addMinutes(new Date(), survivalTime));
            verify.setCode(code);
            verify.setTarget(target);
        }
        return code;
    }

    @Override
    @Transactional(propagation=Propagation.REQUIRES_NEW)
    public void verify(long uid, String code) throws  VerifyException{
        VerifyPO verify = verifyDao.get(uid, UCEntityType.VERIFY_MOBILE);
        if(null==verify){
        	throw new VerifyException("Can not find verifyCode by uid=" + uid ,
        			AppCode.VERIFY_CODE_NULL);
        }
        //Assert.notNull(verify, AppCode.VERIFY_CODE_NULL);
        if(!StringUtils.lowerCase(verify.getCode()).equals(StringUtils.lowerCase(code))){
        	throw new VerifyException("verifyCode wrong by uid=" + uid +",code="+code,
        			AppCode.VERIFY_INVALID_CODE);
        }
        //Assert.eq(StringUtils.lowerCase(verify.getCode()), StringUtils.lowerCase(code), AppCode.VERIFY_INVALID_CODE);
        if(verify.getStatus()==UCEntityStatus.VERIFY_PASSED){
        	throw new VerifyException("verifyCode expired by uid=" + uid +",code="+code,
        			AppCode.VERIFY_AFTER_EXPIRETIME);
        }
        //Assert.ne(verify.getStatus(), UCEntityStatus.VERIFY_PASSED, AppCode.VERIFY_AFTER_EXPIRETIME);

        verify.setStatus(UCEntityStatus.VERIFY_PASSED);
        
        UserPO userpo = new UserPO();
        try {
        	userpo = userDao.getUserByVerifyedMobile(verify.getTarget());
		} catch (Exception e) {
			e.printStackTrace();
			throw new VerifyException("mobile have been binded for uid=" + uid ,
        			AppCode.VERIFY_MOBILE_IS_BIND);
			//Assert.notNull(null, AppCode.VERIFY_MOBILE_IS_BIND);
		}
        
        if(userpo != null){
        	if(userpo.getId()!=Long.valueOf(uid)){
        		throw new VerifyException("mobile have been binded for uid=" + uid ,
            			AppCode.VERIFY_MOBILE_IS_BIND);
        	}
        	//Assert.eq(userpo.getId(), Long.valueOf(uid), AppCode.VERIFY_MOBILE_IS_BIND);
        }

        UserPO userPO = userDao.get(uid);
        if(null==userPO){
        	throw new VerifyException("user not exist for uid=" + uid ,
        			AppCode.VERIFY_NOT_EXIST_USER);
        }
        //Assert.notNull(userPO, AppCode.VERIFY_NOT_EXIST_USER);
        
        userPO.setMobile(verify.getTarget());

        userPO.setVerifyStatus(userPO.getVerifyStatus() | EntityStatus.VERIFY_MOBILE);
    }

	@Override
	@Transactional
	public void unbindMobile(long uid,String code) {
		UserPO userPO = userDao.get(uid);
		long verifyStatus = userPO.getVerifyStatus();
		
		Assert.isTrue(verifyStatus==EntityStatus.VERIFY_EMAIL_MOBILE||verifyStatus==EntityStatus.VERIFY_MOBILE, AppCode.VERIFY_MOBILE_IS_UNBIND);
		
		VerifyPO verify = verifyDao.get(uid, UCEntityType.VERIFY_MOBILE);

        Assert.notNull(verify, AppCode.VERIFY_CODE_NULL);

        Assert.eq(StringUtils.lowerCase(verify.getCode()), StringUtils.lowerCase(code), AppCode.VERIFY_INVALID_CODE);

        Assert.ne(verify.getStatus(), UCEntityStatus.VERIFY_PASSED, AppCode.VERIFY_AFTER_EXPIRETIME);

        verify.setStatus(UCEntityStatus.VERIFY_PASSED);
        
        
        
        Assert.notNull(userPO, AppCode.VERIFY_NOT_EXIST_USER);
        
        if(userPO.getVerifyStatus() == EntityStatus.VERIFY_EMAIL_MOBILE){
        	userPO.setVerifyStatus(EntityStatus.VERIFY_EMAIL);
        }else if (userPO.getVerifyStatus() == EntityStatus.VERIFY_MOBILE){
        	userPO.setVerifyStatus(0);
        }
        
	}

	@Override
	@Transactional
	public String getPhoneNumberOfVerifyCode(String verifyCode) {
		String result=null;
		if(StringUtils.isNotBlank(verifyCode)){
			VerifyPO verifyPO=verifyDao.findPhoneNumberByVerifyCode(verifyCode);
			if(null!=verifyPO){
				result=verifyPO.getTarget();
			}
		}
		return result;
	}

	@Override
	@Transactional
	public boolean haveVerifyCode(String verifyCode) {
		boolean result=false;
		VerifyPO verifyPO=verifyDao.findByVerifyCode(verifyCode);
		if(null!=verifyPO){
			result=true;
		}
		return result;
	}

	@Override
	public String haveVerifyCodeByPhoneNumber(String phoneNumber) {
		VerifyPO verifyPO=verifyDao.get(phoneNumber, UCEntityType.VERIFY_REGISTE);
		if(null!=verifyPO){
			return verifyPO.getCode();
		}
		return null;
	}

    
   
}
