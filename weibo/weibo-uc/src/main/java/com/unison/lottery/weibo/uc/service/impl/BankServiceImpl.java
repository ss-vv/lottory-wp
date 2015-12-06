package com.unison.lottery.weibo.uc.service.impl;

import java.text.ParseException;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.unison.lottery.weibo.data.Result;
import com.unison.lottery.weibo.uc.service.BankService;
import com.xhcms.commons.lang.Data;
import com.xhcms.lottery.commons.data.Account;
import com.xhcms.lottery.commons.data.User;
import com.xhcms.lottery.commons.persist.dao.UserDao;
import com.xhcms.lottery.commons.persist.entity.UserPO;
import com.xhcms.lottery.commons.persist.service.AccountService;
import com.xhcms.ucenter.persistent.service.IUserManager;
import com.xhcms.ucenter.util.IDCardUtil;

@Service
public class BankServiceImpl implements BankService {
	
	@Autowired
	private AccountService accountService;
	private Logger logger = LoggerFactory.getLogger(getClass());
	@Autowired
	private IUserManager userManager;
	
	@Autowired
	private UserDao userDao;
	
	private boolean checkBankInfo(User curUser) {
		Account acc = accountService.getAccount(curUser.getId());
		// 如果没有填写过, 跳至添加页
		if(StringUtils.isEmpty(acc.getAccountNumber()) || StringUtils.isEmpty(acc.getBank())) {
			return true;
		}
		return false;
	}
	
	@Override
	@Transactional
	public Result saveBankInfo(User curUser,String newIdNumber, Account newAccount,String newRealname) {
		Result data = new Result();
		if(StringUtils.isBlank(curUser.getIdNumber())){//当前身份证号码为空
			if(StringUtils.isBlank(newIdNumber)){ //新身份证号码为空
				data.fail("身份证号码为空");
				return data;
			}
			// 保存身份证号码
			Data idNumberData;
			try {
				idNumberData = IDCardUtil.IDCardValidate(newIdNumber);
				if (!idNumberData.isSuccess()) {
					data.fail("身份证号码不正确");
					return data;
	            } else {
	            	User user4Update = curUser;
	            	user4Update.setIdNumber(newIdNumber);
	            	userManager.updateIDNumber(user4Update);
	            }
			} catch (ParseException e) {
				data.fail("身份证号码不正确");
				logger.error("身份证号码不正确,newIdNumber={}",newIdNumber,e);
				return data;
			}
		}
		if(StringUtils.isBlank(curUser.getRealname())){//当前真实姓名为空
			if(StringUtils.isBlank(newRealname)){//新真实姓名为空
				data.fail("真实姓名为空");
				return data;
			}
			//保存真实姓名
			UserPO po = userDao.get(curUser.getId());
	        if (null != po) {
	            po.setRealname(newRealname);
	        }
		}
		if(checkBankInfo(curUser)) {
			//保存银行卡信息
			accountService.addBankInfo(curUser.getId(), 
					newAccount.getProvince(), 
					newAccount.getCity(), 
					newAccount.getBank(),
					newAccount.getAccountNumber(),
					newAccount.getPassword());
		} else {
			if (!accountService.checkPasswd(curUser.getId(),  newAccount.getPassword())) {
				data.fail("提现密码不正确，修改失败");
				return data;
			}
			accountService.modifyBankInfo(curUser.getId(), 
					newAccount.getProvince(), 
					newAccount.getCity(), 
					newAccount.getBank(),
					newAccount.getAccountNumber());
		}
		data.setSuccess(true);
		return data;
	}

}
