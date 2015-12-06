package com.unison.lottery.api.withdraw.bo;

import java.math.BigDecimal;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.unison.lottery.api.model.User;
import com.unison.lottery.api.protocol.common.SystemStatusKeyNames;
import com.unison.lottery.api.protocol.response.model.WithdrawResponse;
import com.unison.lottery.api.protocol.status.IStatusRepository;
import com.unison.lottery.api.protocol.status.ReturnStatus;
import com.xhcms.lottery.commons.data.Account;
import com.xhcms.lottery.commons.data.Withdraw;
import com.xhcms.lottery.commons.persist.service.AccountService;
import com.xhcms.lottery.service.WithdrawService;
import com.xhcms.ucenter.persistent.service.IUserService;

public class WithdrawBOImpl implements WithdrawBO {

	private static final BigDecimal minAmount = new BigDecimal(10);

	private static final BigDecimal bankFee = new BigDecimal(0);//没有银行手续费

	@Autowired
	private IStatusRepository statusRepository;
	
	private Logger logger=LoggerFactory.getLogger(getClass());
	
	@Autowired
	private IUserService userService;
	
	@Autowired
    private AccountService accountService;

	@Autowired
	private WithdrawService withdrawService;
	
	
	@Override
	public WithdrawResponse withdrawForUser(String withdrawPassword,
			BigDecimal amount, String realIP, User user) {
		WithdrawResponse withdrawResponse=new WithdrawResponse();
		ReturnStatus succStatus=statusRepository.getSystemStatusBySystemKey(SystemStatusKeyNames.Withdraw.SUCC);
		ReturnStatus failStatus=statusRepository.getSystemStatusBySystemKey(SystemStatusKeyNames.Withdraw.FAIL);
		withdrawResponse.setReturnStatus(failStatus);
		try{
			com.xhcms.lottery.commons.data.User userVO = userService.getUser(Long.parseLong(user.getId()));
		    Account account = accountService.getAccount(userVO.getId());
		    if(!accountIsBind(account)){
		    	ReturnStatus bankNotBindStatus=statusRepository.getSystemStatusBySystemKey(SystemStatusKeyNames.Withdraw.FAIL_BANK_NOT_BIND);
				withdrawResponse.setReturnStatus(bankNotBindStatus);
		    }
		    else if (!accountService.checkPasswd(userVO.getId(), withdrawPassword)) {
		    	ReturnStatus passwordIsWrongStatus=statusRepository.getSystemStatusBySystemKey(SystemStatusKeyNames.Withdraw.FAIL_PASSWORD_IS_WRONG);
				withdrawResponse.setReturnStatus(passwordIsWrongStatus);
		    }
		    else{
		    	if(amount.compareTo(minAmount)<0){
		    		ReturnStatus amountTooSmallStatus=statusRepository.getSystemStatusBySystemKey(SystemStatusKeyNames.Withdraw.FAIL_AMOUNT_TOO_SMALL);
					withdrawResponse.setReturnStatus(amountTooSmallStatus);
		    	}
		    	
		    	else if(amountValid(amount, account)){
		    		Withdraw w = new Withdraw();
		    		w.setUserId(userVO.getId());
		    		w.setUsername(userVO.getUsername());
		    		w.setRealName(userVO.getRealname());
		    		w.setIp(StringUtils.isBlank(realIP)?"":realIP);
		    		w.setRegistIp(userVO.getIp());
		    		
		    		// 银行信息
		    		w.setBank(account.getBank());
		    		w.setBankAccount(account.getAccountNumber());
		    		w.setAmount(amount);
		    		w.setProvince(account.getProvince());
		    		w.setCity(account.getCity());
		    		
		    		withdrawService.applyWithdraw(w);
		    		account=accountService.getAccount(userVO.getId());//取最新的账户信息
		    		withdrawResponse.setAccount(account);
		    		withdrawResponse.setReturnStatus(succStatus);
		    	}
		    	else{
		    		
	    			ReturnStatus foundNotEnoughStatus=statusRepository.getSystemStatusBySystemKey(SystemStatusKeyNames.Withdraw.FAIL_FOUND_NOT_ENOUGH);
					withdrawResponse.setReturnStatus(foundNotEnoughStatus);
		    		
		    	}
		    }
		}
		catch(Exception e){
			e.printStackTrace();
			logger.error("申请提现时出现异常:{}",e.getMessage());
			withdrawResponse.setReturnStatus(failStatus);
		}
		return withdrawResponse;
	}


	private boolean amountValid(BigDecimal amount, Account account) {
		return amount.compareTo(BigDecimal.ZERO)>0&&amount.compareTo(account.getFund().subtract(bankFee))<=0;
	}


	private boolean accountIsBind(Account account) {
		return null != account && StringUtils.isNotBlank(account.getPassword())
				&& StringUtils.isNotBlank(account.getAccountNumber());
	}

}
