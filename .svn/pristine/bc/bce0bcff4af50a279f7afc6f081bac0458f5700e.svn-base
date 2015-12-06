package com.xhcms.lottery.admin.persist.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import com.xhcms.commons.lang.Assert;
import com.xhcms.commons.lang.Paging;
import com.xhcms.commons.mq.MessageSender;
import com.xhcms.exception.XHRuntimeException;
import com.xhcms.lottery.admin.persist.service.WithdrawService;
import com.xhcms.lottery.commons.data.Withdraw;
import com.xhcms.lottery.commons.data.weibo.WithdrawMessageHandler;
import com.xhcms.lottery.commons.persist.dao.WithdrawDao;
import com.xhcms.lottery.commons.persist.entity.WithdrawPO;
import com.xhcms.lottery.commons.persist.service.AccountService;
import com.xhcms.lottery.lang.AppCode;
import com.xhcms.lottery.lang.EntityStatus;
import com.xhcms.lottery.lang.MQMessageType;
import com.xhcms.lottery.utils.DateUtils;

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
    
    @Autowired
    private MessageSender messageSender;
    
    private long privateMessageSenderId;
    private Logger logger  = LoggerFactory.getLogger(getClass());

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
        
        if(po.getStatus() == EntityStatus.WITHDRAW_FINISH) {
        	sendWithdrawNotic(po.getUserId(), note, po.getStatus(), po);
        }
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
        
        sendWithdrawNotic(po.getUserId(), note, po.getStatus(), po);
    }
    
	public long getPrivateMessageSenderId() {
		return privateMessageSenderId;
	}
    
	public void setPrivateMessageSenderId(long privateMessageSenderId) {
		this.privateMessageSenderId = privateMessageSenderId;
	}
    
    /**
     * 提现私信通知
     * @param ownerId
     * @param peerId
     * @param note
     */
    private void sendWithdrawNotic(long peerId, String note, int status, WithdrawPO po) {
    	if(StringUtils.isBlank(note)) {
    		note = "请联系客服QQ。";
    	}
    	
    	String auditTime = DateUtils.formatShort(po.getCreatedTime());
    	String amount = po.getAmount().toString();
    	if(status == EntityStatus.WITHDRAW_FINISH) {
    		note = "【提款到账】您于" + auditTime + "申请的" + amount + "元提现已到账，请您查收！";
    	} else if(status == EntityStatus.WITHDRAW_FAIL) {
    		note = "【提款失败】您于" + auditTime + "申请的" + amount + "元提现失败，原因是："+ note;
    	}
    	WithdrawMessageHandler msgHandler = new WithdrawMessageHandler();
    	msgHandler.setOwnerId(privateMessageSenderId);
    	msgHandler.setPeer(peerId);
    	msgHandler.setContent(note);
    	msgHandler.setMessageType(MQMessageType.WITHDRAW.getType());
    	msgHandler.setCreateTime(new Date());
    	
    	logger.info("向消息队列发送“提现私信消息”，内容：{}", msgHandler);
    	messageSender.send(msgHandler);
    }
}
