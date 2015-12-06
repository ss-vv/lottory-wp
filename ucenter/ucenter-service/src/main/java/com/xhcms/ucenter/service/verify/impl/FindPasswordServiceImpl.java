package com.xhcms.ucenter.service.verify.impl;

import java.util.Date;
import java.util.UUID;

import org.apache.commons.lang.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.xhcms.commons.lang.Assert;
import com.xhcms.ucenter.lang.AppCode;
import com.xhcms.ucenter.lang.UCEntityStatus;
import com.xhcms.ucenter.lang.UCEntityType;
import com.xhcms.ucenter.persist.dao.IVerifyDao;
import com.xhcms.ucenter.persist.entity.VerifyPO;
import com.xhcms.ucenter.service.verify.IVerifyService;

/**
 * <p>Title: 找回密码，生成验证码</p>
 * <p>Description: </p>
 * <p>Copyright：Copyright (c) 2011</p>
 * <p>Company：XingHui Spirit (Beijing) Technical Co.,Ltd.</p>
 * 
 * @author jiajiancheng
 * @version 1.0.0
 */
public class FindPasswordServiceImpl implements IVerifyService {

    @Autowired
    private IVerifyDao verifyDao;

    // 验证码存活时间 单位：分钟
    private int survivalTime = 30;

    @Override
    @Transactional
    public String generateCode(long uid, String target) {
        VerifyPO verify = verifyDao.get(uid, UCEntityType.VERIFY_PASSWORD);
        String code = UUID.randomUUID().toString();
        if (verify == null) {
            verify = new VerifyPO();
            verify.setUid(uid);
            verify.setCreatedTime(new Date());
            verify.setExpiryTime(DateUtils.addMinutes(new Date(), survivalTime));
            verify.setCode(code);
            verify.setType(UCEntityType.VERIFY_PASSWORD);
            verifyDao.save(verify);
        } else {
            // 把 验证位 置0
            verify.setStatus(UCEntityStatus.VERIFY_NOT);
            verify.setCreatedTime(new Date());
            verify.setExpiryTime(DateUtils.addMinutes(new Date(), survivalTime));
            verify.setCode(code);
        }
        return code;
    }

    @Override
    @Transactional
    public void verify(long uid, String code) {
        VerifyPO verify = verifyDao.get(uid, UCEntityType.VERIFY_PASSWORD);

        Assert.notNull(verify, AppCode.VERIFY_CODE_NULL);

        Assert.eq(verify.getCode(), code, AppCode.VERIFY_INVALID_CODE);

        Assert.lt(new Date(), verify.getExpiryTime(), AppCode.VERIFY_AFTER_EXPIRETIME);

        Assert.ne(verify.getStatus(), UCEntityStatus.VERIFY_PASSED, AppCode.VERIFY_AFTER_EXPIRETIME);

        verify.setStatus(UCEntityStatus.VERIFY_PASSED);

    }

	@Override
	@Transactional
	public void verifyNotUpdate(long uid, String code) {
		VerifyPO verify = verifyDao.get(uid, UCEntityType.VERIFY_PASSWORD);

        Assert.notNull(verify, AppCode.VERIFY_CODE_NULL);

        Assert.eq(verify.getCode(), code, AppCode.VERIFY_INVALID_CODE);

        Assert.lt(new Date(), verify.getExpiryTime(), AppCode.VERIFY_AFTER_EXPIRETIME);

        Assert.ne(verify.getStatus(), UCEntityStatus.VERIFY_PASSED, AppCode.VERIFY_AFTER_EXPIRETIME);
	}
}
