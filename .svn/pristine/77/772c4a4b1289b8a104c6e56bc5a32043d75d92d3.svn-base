package com.unison.lottery.api.bindbank.bo;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.unison.lottery.api.model.User;
import com.unison.lottery.api.protocol.common.SystemStatusKeyNames;
import com.unison.lottery.api.protocol.response.model.BindBankResponse;
import com.unison.lottery.api.protocol.status.IStatusRepository;
import com.unison.lottery.api.protocol.status.ReturnStatus;
import com.xhcms.commons.util.Text;
import com.xhcms.lottery.commons.data.Account;
import com.xhcms.lottery.commons.persist.service.AccountService;
import com.xhcms.ucenter.persistent.service.IUserManager;
import com.xhcms.ucenter.persistent.service.IUserService;

@Transactional
public class BindBankBOImpl implements BindBankBO {
	
	@Autowired
	private IUserService userService;
	@Autowired
	private IStatusRepository statusRepository;
	@Autowired
    private AccountService accountService;
	@Autowired
	private IUserManager userManager;

	private Logger logger=LoggerFactory.getLogger(getClass());

	@Override
	public BindBankResponse bindBankForUser(Account account, User user) {
		BindBankResponse bindBankResponse=new BindBankResponse();
		ReturnStatus succStatus=statusRepository.getSystemStatusBySystemKey(SystemStatusKeyNames.BindBank.SUCC);
		ReturnStatus failStatus=statusRepository.getSystemStatusBySystemKey(SystemStatusKeyNames.BindBank.FAIL);
		bindBankResponse.setReturnStatus(failStatus);
		try{
			if(null != user && null != account) {
				if(StringUtils.isBlank(account.getPassword())){
					ReturnStatus withdrawPasswordIsBlankStatus=statusRepository.getSystemStatusBySystemKey(SystemStatusKeyNames.BindBank.FAIL_WITHDRAW_PASSWORD_IS_BLANK);
					bindBankResponse.setReturnStatus(withdrawPasswordIsBlankStatus);
				} else {
					long userId = Long.parseLong(user.getId());
					boolean checkResult = checkBindRealNameAndIDCard(userId, account);
					if(!checkResult) {
						bindBankResponse.setReturnStatus(failStatus);
					} else {
						Account oldAccount=accountService.getAccount(userId);
						if (!haveBinded(oldAccount)) {
							updateAccountInfo(account, bindBankResponse, succStatus, userId);
						} else{
							if(withdrawPasswordIsRight(account, oldAccount)) {
								updateAccountInfo(account, bindBankResponse, succStatus, userId);
							} else {
								ReturnStatus withdrawPasswordIsWrongStatus=statusRepository.getSystemStatusBySystemKey(SystemStatusKeyNames.BindBank.FAIL_WITHDRAW_PASSWORD_IS_WRONG);
								bindBankResponse.setReturnStatus(withdrawPasswordIsWrongStatus);
							}
						}
					}
				}
			}
		} catch(Exception e){
			logger.error("绑定银行卡时出现异常:",e);
			bindBankResponse.setReturnStatus(failStatus);
		}
		return bindBankResponse;
	}

	private void updateAccountInfo(Account account,
			BindBankResponse bindBankResponse, ReturnStatus succStatus,
			Long userId) {
		accountService.addBankInfo(userId,
				account.getProvince(), account.getProvince(),
				account.getBank(), account.getAccountNumber(),
				account.getPassword());
		bindBankResponse.setReturnStatus(succStatus);
	}

	private boolean withdrawPasswordIsRight(Account account, Account oldAccount) {
		return Text.MD5Encode(account.getPassword()).equals(oldAccount.getPassword());
	}

	private boolean haveBinded(Account oldAccount) {
		boolean result=false;
		if (null != oldAccount && StringUtils.isNotBlank(oldAccount.getPassword())) {
			result = true;
		}
		return result;
	}
	
	/***********加入绑定真实姓名和身份证号的验证 by lei.li@davcai.com*************/
	private boolean checkBindRealNameAndIDCard(long userId, Account account) {
		boolean result = false;
		
		com.xhcms.lottery.commons.data.User user = userService.getUser(userId);
		String realName = user.getRealname();
		String idNumber = user.getIdNumber();
		if(StringUtils.isNotBlank(realName)) {
			result = true;
		} else if(StringUtils.isBlank(realName) && StringUtils.isNotBlank(account.getRealName())) {
			result = true;
			com.xhcms.lottery.commons.data.User u = new com.xhcms.lottery.commons.data.User();
			u.setId(userId);
	        u.setRealname(account.getRealName());
			userManager.updateRealName(u);
		}
		if(StringUtils.isNotBlank(idNumber)) {
			result = true;
		} else if(StringUtils.isBlank(idNumber) && StringUtils.isNotBlank(account.getIdCard())) {
			result = true;
			com.xhcms.lottery.commons.data.User u = new com.xhcms.lottery.commons.data.User();
			u.setId(userId);
	        u.setIdNumber(account.getIdCard());
			userManager.updateIDNumber(u);
		}
		return result;
	}
}
