package com.xhcms.lottery.pb.service.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.xhcms.exception.XHRuntimeException;
import com.xhcms.lottery.commons.data.Account;
import com.xhcms.lottery.commons.data.User;
import com.xhcms.lottery.commons.data.Withdraw;
import com.xhcms.lottery.commons.persist.dao.UserDao;
import com.xhcms.lottery.commons.persist.entity.UserPO;
import com.xhcms.lottery.commons.persist.entity.WithdrawPO;
import com.xhcms.lottery.commons.persist.service.AccountService;
import com.xhcms.lottery.commons.persist.service.UserService;
import com.xhcms.lottery.lang.AppCode;
import com.xhcms.lottery.pb.dao.PartnerWithdrawDao;
import com.xhcms.lottery.pb.model.Constants;
import com.xhcms.lottery.pb.po.PartnerWithdrawPO;
import com.xhcms.lottery.pb.service.PartnerWithdrawService;
import com.xhcms.lottery.pb.xml.model.WithdrawReq;
import com.xhcms.lottery.pb.xml.model.WithdrawResp;
import com.xhcms.lottery.service.WithdrawService;

@Service
public class PartnerWithdrawServiceImpl implements PartnerWithdrawService {
	private Logger logger = LoggerFactory.getLogger(getClass());
	@Autowired
	private PartnerWithdrawDao partnerWithdrawDao;
	@Autowired
	AccountService accountService;
	@Autowired
	UserDao userDao;
	@Autowired
	private WithdrawService withdrawService;

	@Override
	@Transactional
	public WithdrawResp withdraw(WithdrawReq withdrawReq) {
		WithdrawResp withdrawResp = new WithdrawResp();
		withdrawResp.setTransactionId(withdrawReq.getTransactionId());
		if(null != withdrawReq 
			&& null == partnerWithdrawDao.findByUuid(withdrawReq.getUuid())
			&& null ==partnerWithdrawDao.findByTransactionId(withdrawReq.getTransactionId())){
			 UserPO userPO = userDao.getUserByUsername(withdrawReq.getPartnerId());
			 User user = new User();
			 BeanUtils.copyProperties(userPO, user);
			 Account account = accountService.getAccount(userPO.getId());
			 if(null != account){
				 Withdraw w = makeWithdraw(withdrawReq, user);
				 try {
					 long withdrawId = withdrawService.applyWithdraw(w);
					 PartnerWithdrawPO partnerWithdrawPO = new PartnerWithdrawPO();
					 partnerWithdrawPO.setWithdrawId(withdrawId);
					 partnerWithdrawPO.setStatus(Constants.WAIT_WITHDRAW_RESULT_INFORM);
					 partnerWithdrawPO.setCreateTime(new Date());
					 partnerWithdrawPO.setUserId(user.getId());
					 BeanUtils.copyProperties(withdrawReq, partnerWithdrawPO);
					 partnerWithdrawDao.save(partnerWithdrawPO);//保存商户提现记录
					 withdrawResp.setStatus(Constants.WITHDRAW_REQ_RCV_SUCCESS);
					 return withdrawResp;
				 } catch (XHRuntimeException exception){
					 logger.error("提现出错：", exception);
					 return errorProc(withdrawReq); 
				 }
			 } else {
				 return errorProc(withdrawReq);
			 }
		} else {
			return errorProc(withdrawReq);
		}
	}

	@Override
	public WithdrawResp errorProc(WithdrawReq withdrawReq) {
		WithdrawResp withdrawResp = new WithdrawResp();
		withdrawResp.setStatus(Constants.WITHDRAW_REQ_RCV__FAIL);
		withdrawResp.setTransactionId(withdrawReq.getTransactionId());
		return withdrawResp; 
	}
	
	private Withdraw makeWithdraw(WithdrawReq withdrawReq,User user){
		Withdraw w = new Withdraw();
		w.setUserId(user.getId());
		w.setUsername(user.getUsername());
		w.setRealName(withdrawReq.getRealname());
		w.setIp(withdrawReq.getIpAddress());
		w.setRegistIp(user.getIp());
		
		// 银行信息
		w.setBank(withdrawReq.getBankInfo());
		w.setBankAccount(withdrawReq.getCardNum());
		w.setAmount(new BigDecimal(withdrawReq.getMoney()));
		w.setProvince(withdrawReq.getProvince());
		w.setCity(withdrawReq.getCity());
		return w;
	} 
}
