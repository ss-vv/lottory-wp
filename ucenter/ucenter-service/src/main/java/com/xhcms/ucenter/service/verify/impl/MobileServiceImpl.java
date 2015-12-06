package com.xhcms.ucenter.service.verify.impl;

import java.util.Date;
import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import com.xhcms.commons.lang.Assert;
import com.xhcms.lottery.commons.persist.dao.UserDao;
import com.xhcms.lottery.commons.persist.entity.UserPO;
import com.xhcms.lottery.lang.EntityStatus;
import com.xhcms.ucenter.lang.AppCode;
import com.xhcms.ucenter.lang.UCEntityStatus;
import com.xhcms.ucenter.lang.UCEntityType;
import com.xhcms.ucenter.persist.dao.IVerifyDao;
import com.xhcms.ucenter.persist.entity.VerifyPO;
import com.xhcms.ucenter.service.verify.IVerifyService;

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
        String code = RandomStringUtils.randomAlphabetic(6);
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
    @Transactional
    public void verify(long uid, String code) {
        VerifyPO verify = verifyDao.get(uid, UCEntityType.VERIFY_MOBILE);

        Assert.notNull(verify, AppCode.VERIFY_CODE_NULL);

        Assert.eq(StringUtils.lowerCase(verify.getCode()), StringUtils.lowerCase(code), AppCode.VERIFY_INVALID_CODE);

        Assert.ne(verify.getStatus(), UCEntityStatus.VERIFY_PASSED, AppCode.VERIFY_AFTER_EXPIRETIME);

        verify.setStatus(UCEntityStatus.VERIFY_PASSED);
        
        UserPO userpo = new UserPO();
        try {
        	userpo = userDao.getUserByVerifyedMobile(verify.getTarget());
		} catch (Exception e) {
			e.printStackTrace();
			Assert.notNull(null, AppCode.VERIFY_MOBILE_IS_BIND);
		}
        
        if(userpo != null){
        	Assert.eq(userpo.getId(), Long.valueOf(uid), AppCode.VERIFY_MOBILE_IS_BIND);
        }

        UserPO userPO = userDao.get(uid);
        Assert.notNull(userPO, AppCode.VERIFY_NOT_EXIST_USER);
        
        userPO.setMobile(verify.getTarget());
        userPO.setVerifyStatus(userPO.getVerifyStatus() | EntityStatus.VERIFY_MOBILE);

    }

	@Override
	@Transactional
	public void verifyNotUpdate(long uid, String code) {
		// TODO Auto-generated method stub
		
	}
}
