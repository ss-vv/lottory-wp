package com.xhcms.lottery.admin.persist.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.xhcms.commons.lang.Assert;
import com.xhcms.commons.lang.Paging;
import com.xhcms.exception.XHRuntimeException;
import com.xhcms.lottery.admin.persist.service.RechargeService;
import com.xhcms.lottery.commons.data.Recharge;
import com.xhcms.lottery.commons.persist.dao.RechargeDao;
import com.xhcms.lottery.commons.persist.entity.RechargePO;
import com.xhcms.lottery.commons.persist.service.AccountService;
import com.xhcms.lottery.lang.AppCode;
import com.xhcms.lottery.lang.EntityStatus;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright：Copyright (c) 2011</p>
 * <p>Company：XingHui Spirit (Beijing) Technical Co.,Ltd.</p>
 * 
 * @author xulang
 * @version 1.0.0
 */
public class RechargeServiceImpl implements RechargeService {
	@Autowired
	private RechargeDao rechargeDao;
	@Autowired
	private AccountService accountService;
	
	@Override
	@Transactional
	public void listRecharge(Paging paging, long id, String username, int status, Date beginTime, Date endTime) {
		List<RechargePO> list = rechargeDao.find(id, username, beginTime, endTime, status, paging);
		List<Recharge> rets = new ArrayList<Recharge>();
		
		Recharge rh = null;
		for(RechargePO po : list) {
			rh = new Recharge();
			BeanUtils.copyProperties(po, rh);
			rets.add(rh);
		}
		paging.setResults(rets);
	}
	
	@Override
	@Transactional
	public Recharge getRecharge(long id) {
		RechargePO po = rechargeDao.get(id);
		Recharge rh = null;
		if (po != null) {
			rh = new Recharge();
			BeanUtils.copyProperties(po, rh);
		}
		return rh;
	}
	

	@Override
	@Transactional
	public void pass(long id, int operator, String note) {
		RechargePO po = rechargeDao.get(id);
		Assert.notNull(po, AppCode.RECHARGE_NOT_EXIST);
		
		 switch (po.getStatus()) {
            case EntityStatus.RECHARGE_NOT_PAY:// 初审通过
                po.setStatus(EntityStatus.RECHARGE_NOT_AUDIT);
                po.setAuditId(operator);
                po.setAuditTime(new Date());
                break;
            case EntityStatus.RECHARGE_NOT_AUDIT:// 确认
                accountService.comfirmRecharge(operator, po.getUserId(), po.getAmount(), id, po.getBankOrder());
                po.setStatus(EntityStatus.RECHARGE_FINISH);
                po.setConfirmId(operator);
                po.setConfirmTime(new Date());
                break;
            default:
                throw new XHRuntimeException(AppCode.INVALID_WITHDRAW_STATUS);
        }
	}
	
	@Override
    @Transactional
    public void fail(long id, int operator, String note) {
	    RechargePO po = rechargeDao.get(id);
        Assert.notNull(po, AppCode.RECHARGE_NOT_EXIST);

        po.setStatus(EntityStatus.WITHDRAW_FAIL);
        po.setConfirmId(operator);
        po.setConfirmTime(new Date());
        po.setNote(note);
    }
	
	@Override
	@Transactional
	public void reject(long id, int operator, String note) {
	    RechargePO po = rechargeDao.get(id);
        Assert.notNull(po, AppCode.RECHARGE_NOT_EXIST);
        
        if (EntityStatus.RECHARGE_NOT_CONFIRM == po.getStatus()) {
            accountService.comfirmRecharge(operator, po.getUserId(), po.getAmount(), id, po.getBankOrder());
            po.setStatus(EntityStatus.RECHARGE_FINISH);
            po.setConfirmId(operator);
            po.setConfirmTime(new Date());
            po.setNote(note);
        }
        throw new XHRuntimeException(AppCode.INVALID_WITHDRAW_STATUS);
	}
	

    @Override
    @Transactional
    public void batchConfirm(List<Long> id, int operator) {
        Date date = new Date();
        List<RechargePO> pos = rechargeDao.find(id);
        for(RechargePO po: pos){
            // 充值成功, 更新用户账户
            accountService.comfirmRecharge(operator, po.getUserId(), po.getAmount(), po.getId(), po.getBankOrder());
            po.setStatus(EntityStatus.RECHARGE_FINISH);
            po.setConfirmId(operator);
            po.setConfirmTime(date);
        }
    }

}
