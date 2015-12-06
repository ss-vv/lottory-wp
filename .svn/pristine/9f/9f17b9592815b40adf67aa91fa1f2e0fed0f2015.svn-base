package com.xhcms.lottery.service.impl;

import java.math.BigDecimal;
import java.util.Date;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;


import com.xhcms.lottery.commons.data.Withdraw;
import com.xhcms.lottery.commons.persist.dao.WithdrawDao;
import com.xhcms.lottery.commons.persist.entity.WithdrawPO;
import com.xhcms.lottery.commons.persist.service.AccountService;
import com.xhcms.lottery.lang.EntityStatus;
import com.xhcms.lottery.service.WithdrawService;

/**
 * 提现服务
 * 
 * @author chenyan
 * @version 1.0.0
 */
public class WithdrawServiceImpl implements WithdrawService {
	@Autowired
	private WithdrawDao withdrawDao;
	@Autowired
	private AccountService accountService;
	
	@Override
	@Transactional
	public long applyWithdraw(Withdraw withdraw) {
		BigDecimal amount = withdraw.getAmount();
		//TODO: "收取比例"从渠道里面取
		// 站内手续费
		BigDecimal fee = BigDecimal.ZERO;
		// 银行手续费
		BigDecimal bankFee = BigDecimal.ZERO; // 去掉手续费. new BigDecimal(2);
		
		// 总手续费
		BigDecimal totalFee = fee.add(bankFee);
		BigDecimal payAmount = amount.subtract(totalFee);
		
		WithdrawPO wh = new WithdrawPO();
        BeanUtils.copyProperties(withdraw, wh);
        
        wh.setTotalFee(totalFee);  
        wh.setBankFee(bankFee);
        wh.setPayAmount(payAmount);
        wh.setCreatedTime(new Date());
        wh.setStatus(EntityStatus.WITHDRAW_INIT);
        
        withdrawDao.save(wh);
		accountService.withdrawFrozen(withdraw.getUserId(), payAmount, totalFee, wh.getId());// 提现
		
		return wh.getId();
	}

}
