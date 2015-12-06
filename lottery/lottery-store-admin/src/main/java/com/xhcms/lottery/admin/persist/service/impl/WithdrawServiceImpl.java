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
import com.xhcms.lottery.admin.persist.service.WithdrawService;
import com.xhcms.lottery.commons.data.Withdraw;
import com.xhcms.lottery.commons.persist.dao.WithdrawDao;
import com.xhcms.lottery.commons.persist.entity.WithdrawPO;
import com.xhcms.lottery.commons.persist.service.AccountService;
import com.xhcms.lottery.lang.AppCode;
import com.xhcms.lottery.lang.EntityStatus;

/**
 * 
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright：Copyright (c) 2011</p>
 * <p>Company：XingHui Spirit (Beijing) Technical Co.,Ltd.</p>
 * 
 * @author xulang
 * @version 1.0.0
 */
public class WithdrawServiceImpl implements WithdrawService {
    @Autowired
    private WithdrawDao withdrawDao;
    @Autowired
    private AccountService accountService;

    @Override
    @Transactional
    public void listWithdraw(Paging paging, long userId, String username, int status, Date from, Date to) {
        List<WithdrawPO> list = withdrawDao.find(userId, username, from, to, status, paging);
        List<Withdraw> rets = new ArrayList<Withdraw>();
        Withdraw wh = null;
        for (WithdrawPO po : list) {
            wh = new Withdraw();
            BeanUtils.copyProperties(po, wh);
            rets.add(wh);
        }
        paging.setResults(rets);
    }

    @Override
    @Transactional
    public Withdraw getWithdraw(Long withdrawId) {
        WithdrawPO po = withdrawDao.get(withdrawId);
        Assert.notNull(po, AppCode.WITHDRAW_NOT_EXIST);

        Withdraw wh = new Withdraw();
        BeanUtils.copyProperties(po, wh);
        return wh;
    }

    @Override
    @Transactional
    public void batchConfirm(List<Long> id, int operator) {
        Date date = new Date();
        List<WithdrawPO> pos = withdrawDao.find(id);
        for(WithdrawPO po: pos){
            if(po.getStatus() == EntityStatus.WITHDRAW_PAYED){
                accountService.withdrawDeduct(operator, po.getUserId(), po.getAmount(), po.getId(), "批量确认");
                po.setStatus(EntityStatus.WITHDRAW_FINISH);
                po.setConfirmId(operator);
                po.setConfirmTime(date);
            }
        }
    }
    
    @Override
    @Transactional
    public void pass(Long id, int operator, String bankOrder, String note) {
        WithdrawPO po = withdrawDao.get(id);
        Assert.notNull(po, AppCode.WITHDRAW_NOT_EXIST);

        switch (po.getStatus()) {
            case EntityStatus.WITHDRAW_INIT:// 初审通过
                po.setStatus(EntityStatus.WITHDRAW_AUDIT);
                po.setAuditId(operator);
                po.setAuditTime(new Date());
                break;
            case EntityStatus.WITHDRAW_AUDIT:// 复审打款
                po.setStatus(EntityStatus.WITHDRAW_PAYED);
                po.setBankOrder(bankOrder);
                po.setAuditId(operator);
                po.setAuditTime(new Date());
                break;
            case EntityStatus.WITHDRAW_PAYED:// 确认完成
                accountService.withdrawDeduct(operator, po.getUserId(), po.getAmount(), id, note);
                po.setStatus(EntityStatus.WITHDRAW_FINISH);
                po.setConfirmId(operator);
                po.setConfirmTime(new Date());
                break;
            default:
                throw new XHRuntimeException(AppCode.INVALID_WITHDRAW_STATUS);
        }
        po.setNote(note);
    }

    @Override
    @Transactional
    public void reject(Long id, int operator, String note) {
        WithdrawPO po = withdrawDao.get(id);
        Assert.notNull(po, AppCode.WITHDRAW_NOT_EXIST);
        Assert.eq(EntityStatus.WITHDRAW_PAYED, po.getStatus(), AppCode.INVALID_WITHDRAW_STATUS);

        po.setStatus(EntityStatus.WITHDRAW_AUDIT);
        po.setConfirmId(operator);
        po.setConfirmTime(new Date());
        po.setNote(note);
    }

    @Override
    @Transactional
    public void fail(Long id, int operator, String note) {
        WithdrawPO po = withdrawDao.get(id);
        Assert.notNull(po, AppCode.WITHDRAW_NOT_EXIST);

        switch (po.getStatus()) {
            case EntityStatus.WITHDRAW_INIT:
            case EntityStatus.WITHDRAW_AUDIT:
                accountService.withdrawReturn(operator, po.getUserId(), po.getAmount(), id, note);
                break;
            case EntityStatus.WITHDRAW_PAYED:// 确认失败
                accountService.withdrawReturn(operator, po.getUserId(), po.getAmount().subtract(po.getTotalFee()), id, note);
                break;
            default:
                throw new XHRuntimeException(AppCode.INVALID_WITHDRAW_STATUS);
        }

        po.setStatus(EntityStatus.WITHDRAW_FAIL);
        po.setConfirmId(operator);
        po.setConfirmTime(new Date());
        po.setNote(note);
    }

}
