package com.xhcms.ucenter.service.verify.impl;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.apache.commons.lang.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Propagation;
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

public class BindEmailServiceImpl implements IVerifyService {

    @Autowired
    private IVerifyDao verifyDao;

    @Autowired
    private UserDao userDao;

    // 验证码存活时间 单位：分钟
    private int survivalTime = 30;

    @Override
    @Transactional
    public String generateCode(long uid, String email) {

        VerifyPO verify = verifyDao.get(uid, UCEntityType.VERIFY_EMAIL);
        String code = UUID.randomUUID().toString();
        if (verify == null) {
            verify = new VerifyPO();
            verify.setUid(uid);
            verify.setTarget(email);
            verify.setCreatedTime(new Date());
            verify.setExpiryTime(DateUtils.addMinutes(new Date(), survivalTime));
            verify.setCode(code);
            verify.setType(UCEntityType.VERIFY_EMAIL);
            verifyDao.save(verify);
        } else {
            verify.setStatus(UCEntityStatus.VERIFY_NOT);
            verify.setCreatedTime(new Date());
            verify.setExpiryTime(DateUtils.addMinutes(new Date(), survivalTime));
            verify.setTarget(email);
            verify.setCode(code);
        }

        return code;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void verify(long uid, String code) {
        VerifyPO verify = verifyDao.get(uid, UCEntityType.VERIFY_EMAIL);

        Assert.notNull(verify, AppCode.VERIFY_CODE_NULL);

        Assert.eq(verify.getCode(), code, AppCode.VERIFY_INVALID_CODE);

        Assert.lt(new Date(), verify.getExpiryTime(), AppCode.VERIFY_AFTER_EXPIRETIME);

        Assert.ne(verify.getStatus(), UCEntityStatus.VERIFY_PASSED, AppCode.VERIFY_AFTER_EXPIRETIME);

        verify.setStatus(UCEntityStatus.VERIFY_PASSED);

        List<UserPO> poList = userDao.getValidUsersByBindedEmail(verify.getTarget());
    
        Assert.isTrue(null==poList || poList.isEmpty(),  AppCode.VERIFY_EMAIL_IS_BIND);
        
        UserPO userPO = userDao.get(uid);
        Assert.notNull(userPO, AppCode.VERIFY_NOT_EXIST_USER);

        userPO.setEmail(verify.getTarget());
        userPO.setVerifyStatus(userPO.getVerifyStatus() | EntityStatus.VERIFY_EMAIL);

    }

	@Override
	@Transactional
	public void verifyNotUpdate(long uid, String code) {
		// TODO Auto-generated method stub
		
	}
}
