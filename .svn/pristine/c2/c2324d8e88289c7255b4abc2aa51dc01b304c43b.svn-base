package com.xhcms.lottery.commons.persist.service.impl;

import java.math.BigDecimal;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.LockMode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.unison.lottery.api.redEnvalope.model.RedEnvalope;
import com.unison.lottery.weibo.data.service.store.persist.entity.RedEnvalopeGrabRecordPO;
import com.unison.lottery.weibo.data.service.store.persist.entity.RedEnvalopePO;
import com.xhcms.commons.lang.Assert;
import com.xhcms.commons.lang.Paging;
import com.xhcms.commons.util.Text;
import com.xhcms.exception.XHRuntimeException;
import com.xhcms.lottery.commons.data.Account;
import com.xhcms.lottery.commons.data.Recharge;
import com.xhcms.lottery.commons.persist.dao.AccountDao;
import com.xhcms.lottery.commons.persist.dao.OrderDao;
import com.xhcms.lottery.commons.persist.dao.RechargeDao;
import com.xhcms.lottery.commons.persist.dao.UserDao;
import com.xhcms.lottery.commons.persist.dao.WithdrawDao;
import com.xhcms.lottery.commons.persist.entity.AccountPO;
import com.xhcms.lottery.commons.persist.entity.OrderPO;
import com.xhcms.lottery.commons.persist.entity.RechargePO;
import com.xhcms.lottery.commons.persist.entity.UserPO;
import com.xhcms.lottery.commons.persist.entity.WithdrawPO;
import com.xhcms.lottery.commons.persist.service.AccountService;
import com.xhcms.lottery.lang.AppCode;
import com.xhcms.lottery.lang.Constants;
import com.xhcms.lottery.lang.EntityStatus;
import com.xhcms.lottery.lang.EntityType;

public class AccountServiceImpl implements AccountService {

	private Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private OrderDao orderDao;

	@Autowired
	private AccountDao accountDao;

	@Autowired
	private RechargeDao rechargeDao;

	@Autowired
	private UserDao userDao;

	@Autowired
	private WithdrawDao withdrawDao;

	/**
	 * 得到全部用户id列表
	 * 
	 * @return
	 */
	@Override
	@Transactional(readOnly = true)
	public List<Long> listAllId() {
		return accountDao.listIds();
	}

	@Override
	@Transactional(readOnly = true)
	public void listAccount(Paging paging, String username) {
		List<AccountPO> data = accountDao.find(paging, username);
		List<Account> results = new ArrayList<Account>(data.size());
		for (AccountPO po : data) {
			Account a = new Account();
			BeanUtils.copyProperties(po, a);
			results.add(a);
		}
		paging.setResults(results);
	}

	@Override
	@Transactional
	public void addAccount(Long userId, String username) {
		AccountPO account = new AccountPO();
		account.setId(userId);
		account.setUsername(username);
		account.setFund(BigDecimal.ZERO);
		account.setGrant(BigDecimal.ZERO);
		account.setFrozenFund(BigDecimal.ZERO);
		account.setFrozenGrant(BigDecimal.ZERO);
		account.setFree(BigDecimal.ZERO);
		account.setTotalRecharge(BigDecimal.ZERO);
		account.setTotalWithdraw(BigDecimal.ZERO);
		account.setTotalBet(BigDecimal.ZERO);
		account.setTotalAward(BigDecimal.ZERO);
		account.setProvince(null);
		account.setCity(null);
		account.setBank(null);
		account.setAccountNumber(null);
		account.setPassword(null);
		account.setVerifyCode("");
		account.setCheckedTime(new Date());
		account.setVersion(0);
		accountDao.save(account);
	}

	@Override
	@Transactional
	public void addBankInfo(Long userId, String province, String city,
			String bank, String accountNumber, String password) {
		AccountPO po = accountDao.get(userId);
		Assert.notNull(po, AppCode.INVALID_ACCOUNT);

		po.setProvince(province);
		po.setCity(city);
		po.setBank(bank);
		po.setAccountNumber(accountNumber);
		po.setPassword(Text.MD5Encode(password));
	}

	@Override
	@Transactional
	public void modifyBankInfo(Long userId, String province, String city,
			String bank, String accountNumber) {
		AccountPO po = accountDao.get(userId);
		Assert.notNull(po, AppCode.INVALID_ACCOUNT);

		po.setProvince(province);
		po.setCity(city);
		po.setBank(bank);
		po.setAccountNumber(accountNumber);
	}

	@Override
	@Transactional
	public Long applyForRecharge(Long userId, BigDecimal amount,
			BigDecimal fee, BigDecimal channelFee, String channelCode, String ip) {
		// XXX 随业务发展需要将下面数据改为动态获取：易宝通道ID，总手续费，通道手续费
		Assert.ge(amount, BigDecimal.ZERO, AppCode.INVALID_AMOUNT);
		Assert.ge(fee, BigDecimal.ZERO, AppCode.INVALID_AMOUNT);
		Assert.ge(channelFee, BigDecimal.ZERO, AppCode.INVALID_AMOUNT);

		BigDecimal payAmount = amount.subtract(fee);
		Assert.gt(payAmount, BigDecimal.ZERO, AppCode.INVALID_RECHARGE_AMOUNT);

		// 生成现金单
		UserPO u = userDao.get(userId);
		RechargePO rpo = new RechargePO();
		rpo.setChannelCode(channelCode);
		rpo.setChannelId(1); // 易宝通道ID
		rpo.setCreatedTime(new Date());
		rpo.setIp(ip);
		rpo.setAmount(amount);
		rpo.setChannelFee(channelFee);
		rpo.setTotalFee(fee);
		rpo.setPayAmount(payAmount);
		rpo.setRealName(u.getRealname());
		rpo.setUserId(userId);
		rpo.setUsername(u.getUsername());

		rechargeDao.save(rpo);
		return rpo.getId();
	}

	@Override
	@Transactional
	public Long alipayApplyForRecharge(Long userId, BigDecimal amount,
			BigDecimal fee, BigDecimal channelFee, String channelCode, String ip) {
		// XXX 随业务发展需要将下面数据改为动态获取：支付宝通道ID，总手续费，通道手续费
		Assert.ge(amount, BigDecimal.ZERO, AppCode.INVALID_AMOUNT);
		Assert.ge(fee, BigDecimal.ZERO, AppCode.INVALID_AMOUNT);
		Assert.ge(channelFee, BigDecimal.ZERO, AppCode.INVALID_AMOUNT);

		BigDecimal payAmount = amount.subtract(fee);
		Assert.gt(payAmount, BigDecimal.ZERO, AppCode.INVALID_RECHARGE_AMOUNT);

		// 生成现金单
		UserPO u = userDao.get(userId);
		RechargePO rpo = new RechargePO();
		rpo.setChannelCode(channelCode);
		rpo.setChannelId(2); // 支付宝通道ID
		rpo.setCreatedTime(new Date());
		rpo.setIp(ip);
		rpo.setAmount(amount);
		rpo.setChannelFee(channelFee);
		rpo.setTotalFee(fee);
		rpo.setPayAmount(payAmount);
		rpo.setRealName(u.getRealname());
		rpo.setUserId(userId);
		rpo.setUsername(u.getUsername());

		rechargeDao.save(rpo);
		return rpo.getId();
	}

	@Override
	@Transactional
	public Long wapAlipayForRecharge(Long userId, BigDecimal amount,
			BigDecimal fee, BigDecimal channelFee, String channelCode, String ip) {
		// XXX 随业务发展需要将下面数据改为动态获取：支付宝通道ID，总手续费，通道手续费
		Assert.ge(amount, BigDecimal.ZERO, AppCode.INVALID_AMOUNT);
		Assert.ge(fee, BigDecimal.ZERO, AppCode.INVALID_AMOUNT);
		Assert.ge(channelFee, BigDecimal.ZERO, AppCode.INVALID_AMOUNT);

		BigDecimal payAmount = amount.subtract(fee);
		Assert.gt(payAmount, BigDecimal.ZERO, AppCode.INVALID_RECHARGE_AMOUNT);

		// 生成现金单
		UserPO u = userDao.get(userId);
		RechargePO rpo = new RechargePO();
		rpo.setChannelCode(channelCode);
		rpo.setChannelId(3); // 支付宝WAP通道ID
		rpo.setCreatedTime(new Date());
		rpo.setIp(ip);
		rpo.setAmount(amount);
		rpo.setChannelFee(channelFee);
		rpo.setTotalFee(fee);
		rpo.setPayAmount(payAmount);
		rpo.setRealName(u.getRealname());
		rpo.setUserId(userId);
		rpo.setUsername(u.getUsername());

		rechargeDao.save(rpo);
		return rpo.getId();
	}

	@Override
	@Transactional
	public Long yeepayCardForRecharge(Long userId, BigDecimal amount,
			BigDecimal fee, BigDecimal channelFee, String channelCode, String ip) {
		// XXX 随业务发展需要将下面数据改为动态获取：支付宝通道ID，总手续费，通道手续费
		Assert.ge(amount, BigDecimal.ZERO, AppCode.INVALID_AMOUNT);
		Assert.ge(fee, BigDecimal.ZERO, AppCode.INVALID_AMOUNT);
		Assert.ge(channelFee, BigDecimal.ZERO, AppCode.INVALID_AMOUNT);

		BigDecimal payAmount = amount.subtract(fee);
		Assert.gt(payAmount, BigDecimal.ZERO, AppCode.INVALID_RECHARGE_AMOUNT);

		// 生成现金单
		UserPO u = userDao.get(userId);
		RechargePO rpo = new RechargePO();
		rpo.setChannelCode(channelCode);
		rpo.setChannelId(4); // 易宝手机卡通道ID
		rpo.setCreatedTime(new Date());
		rpo.setIp(ip);
		rpo.setAmount(amount);
		rpo.setChannelFee(channelFee);
		rpo.setTotalFee(fee);
		rpo.setPayAmount(payAmount);
		rpo.setRealName(u.getRealname());
		rpo.setUserId(userId);
		rpo.setUsername(u.getUsername());

		rechargeDao.save(rpo);
		return rpo.getId();
	}

	@Override
	@Transactional
	public Long clientAlipayForRecharge(Long userId, BigDecimal amount,
			BigDecimal fee, BigDecimal channelFee, String channelCode, String ip) {
		// XXX 随业务发展需要将下面数据改为动态获取：支付宝通道ID，总手续费，通道手续费
		Assert.ge(amount, BigDecimal.ZERO, AppCode.INVALID_AMOUNT);
		Assert.ge(fee, BigDecimal.ZERO, AppCode.INVALID_AMOUNT);
		Assert.ge(channelFee, BigDecimal.ZERO, AppCode.INVALID_AMOUNT);

		BigDecimal payAmount = amount.subtract(fee);
		Assert.gt(payAmount, BigDecimal.ZERO, AppCode.INVALID_RECHARGE_AMOUNT);

		// 生成现金单
		UserPO u = userDao.get(userId);
		RechargePO rpo = new RechargePO();
		rpo.setChannelCode(channelCode);
		rpo.setChannelId(5); // 易宝手机卡通道ID
		rpo.setCreatedTime(new Date());
		rpo.setIp(ip);
		rpo.setAmount(amount);
		rpo.setChannelFee(channelFee);
		rpo.setTotalFee(fee);
		rpo.setPayAmount(payAmount);
		rpo.setRealName(u.getRealname());
		rpo.setUserId(userId);
		rpo.setUsername(u.getUsername());

		rechargeDao.save(rpo);
		return rpo.getId();
	}

	@Override
	@Transactional
	public Long clientYeepayCardForRecharge(Long userId, BigDecimal amount,
			BigDecimal fee, BigDecimal channelFee, String channelCode, String ip) {
		// XXX 随业务发展需要将下面数据改为动态获取：支付宝通道ID，总手续费，通道手续费
		Assert.ge(amount, BigDecimal.ZERO, AppCode.INVALID_AMOUNT);
		Assert.ge(fee, BigDecimal.ZERO, AppCode.INVALID_AMOUNT);
		Assert.ge(channelFee, BigDecimal.ZERO, AppCode.INVALID_AMOUNT);

		BigDecimal payAmount = amount.subtract(fee);
		Assert.gt(payAmount, BigDecimal.ZERO, AppCode.INVALID_RECHARGE_AMOUNT);

		// 生成现金单
		UserPO u = userDao.get(userId);
		RechargePO rpo = new RechargePO();
		rpo.setChannelCode(channelCode);
		rpo.setChannelId(6); // 易宝手机卡通道ID
		rpo.setCreatedTime(new Date());
		rpo.setIp(ip);
		rpo.setAmount(amount);
		rpo.setChannelFee(channelFee);
		rpo.setTotalFee(fee);
		rpo.setPayAmount(payAmount);
		rpo.setRealName(u.getRealname());
		rpo.setUserId(userId);
		rpo.setUsername(u.getUsername());

		rechargeDao.save(rpo);
		return rpo.getId();
	}

	@Override
	@Transactional
	public void comfirmRecharge(Integer operator, Long userId,
			BigDecimal amount, Long rechargeId, String trxId) {
		Assert.gt(amount, BigDecimal.ZERO, AppCode.INVALID_AMOUNT);

		RechargePO rpo = rechargeDao.get(rechargeId);
		Assert.eq(rpo.getStatus(), EntityStatus.RECHARGE_NOT_PAY,
				AppCode.INVALID_RECHARGE_STATUS);
		Assert.ge(amount, rpo.getPayAmount(), AppCode.INVALID_RECHARGE_AMOUNT);

		BigDecimal fee = amount.subtract(rpo.getPayAmount());

		Date date = new Date();
		rpo.setStatus(EntityStatus.RECHARGE_FINISH);
		rpo.setPayTime(date);
		rpo.setAuditTime(date);
		rpo.setConfirmTime(date);
		rpo.setBankOrder(rpo.getBankOrder());
		rechargeDao.update(rpo);

		AccountPO po = accountDao.get(userId);
		po.add(Constants.FREE, rpo.getPayAmount());
		po.add(Constants.FUND, rpo.getPayAmount());
		po.add(Constants.TOTAL_RECHARGE, rpo.getPayAmount());
		updateAccount(po);

		String note = MessageFormat.format("充值：{0}元，实际到账：{1}元，手续费：{2}元。",
				rpo.getAmount(), rpo.getPayAmount(), rpo.getTotalFee());
		log(po, EntityType.ORDER_RECHARGE, rpo.getPayAmount(), fee,
				rpo.getPayAmount(), BigDecimal.ZERO, null, rechargeId, 0, note);
	}

	@Override
	@Transactional
	public boolean isDealWithTransactionResult(Long rechargeId) {
		boolean dealWith = true;
		// 充值记录表的充值状态为0
		try {
			RechargePO rpo = rechargeDao.get(rechargeId);
			OrderPO opo = orderDao.getByRelated(EntityType.ORDER_RECHARGE,
					rechargeId);
			// 用户交易流水单表里无数据
			if (rpo != null) {
				if (rpo.getStatus() == 0 && opo == null) {
					dealWith = false;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("判断充值订单是否处理出错！");
		}

		return dealWith;
	}

	@Override
	@Transactional
	public void devide(Integer operator, Long userId, BigDecimal amount,
			Long betPartnerId, String note) {
		Assert.gt(amount, BigDecimal.ZERO, AppCode.INVALID_AMOUNT);

		AccountPO po = accountDao.get(userId);
		Assert.ne(po.getStatus(), EntityStatus.LOCKED, AppCode.ACCOUNT_FROZED);

		po.add(Constants.FUND, amount);
		po.add(Constants.FREE, amount);
		po.add(Constants.TOTAL_AWARD, amount);
		updateAccount(po);

		log(po, EntityType.ORDER_DEVIDE, amount, BigDecimal.ZERO, amount,
				BigDecimal.ZERO, null, betPartnerId, 0, note);
	}

	@Override
	@Transactional
	public void grant(Integer operator, Long userId, BigDecimal amount,
			String note) {
		Assert.gt(amount, BigDecimal.ZERO, AppCode.INVALID_AMOUNT);

		AccountPO po = accountDao.get(userId);

		po.add(Constants.FREE, amount);
		po.add(Constants.GRANT, amount);
		updateAccount(po);

		log(po, EntityType.ORDER_GRANT, amount, BigDecimal.ZERO,
				BigDecimal.ZERO, amount, null, null, operator, note);
	}

	@Override
	@Transactional
	public void betFrozen(Long userId, BigDecimal amount, Long betPartnerId) {
		frozen(EntityType.ORDER_BET_FROZEN, userId, amount, betPartnerId);
	}

	@Override
	@Transactional
	public void betFloorFrozen(Long userId, BigDecimal amount, Long schemeId) {
		frozen(EntityType.ORDER_BET_FLOOR_FROZEN, userId, amount, schemeId);
	}

	@Override
	@Transactional
	public void betDeductAndReturn(Long userId, BigDecimal deductAmount,
			BigDecimal returnAmount, Long betPartnerId) {
		deductReturn(EntityType.ORDER_BET_FROZEN, userId, deductAmount,
				returnAmount, betPartnerId);
	}

	@Override
	@Transactional
	public void betFloorDeductAndReturn(Long userId, BigDecimal deductAmount,
			BigDecimal returnAmount, Long schemeId) {
		deductReturn(EntityType.ORDER_BET_FLOOR_FROZEN, userId, deductAmount,
				returnAmount, schemeId);
	}

	@Override
	@Transactional
	public void withdrawReturn(Integer operator, Long userId,
			BigDecimal amount, Long withdrawId, String note) {
		Assert.gt(amount, BigDecimal.ZERO, AppCode.INVALID_AMOUNT);

		AccountPO po = accountDao.get(userId);
		OrderPO opo = orderDao.getByRelated(EntityType.ORDER_WITHDRAW_FROZEN,
				withdrawId);

		Assert.ge(opo.getAmount().add(opo.getFee()), amount,
				AppCode.UNUSUAL_OPERATE_AMOUNT);
		Assert.ge(po.getFrozenFund(), amount,
				AppCode.FROZEN_INSUFFICIENT_BALANCE);

		WithdrawPO wpo = withdrawDao.get(withdrawId);
		Assert.ne(wpo.getStatus(), EntityStatus.WITHDRAW_FAIL,
				AppCode.INVALID_WITHDRAW_STATUS);
		Assert.ne(wpo.getStatus(), EntityStatus.WITHDRAW_FINISH,
				AppCode.INVALID_WITHDRAW_STATUS);
		Assert.le(amount, wpo.getAmount(), AppCode.FROZEN_INSUFFICIENT_BALANCE);

		wpo.setNote(note);
		wpo.setStatus(EntityStatus.WITHDRAW_FAIL);
		wpo.setConfirmId(operator);
		wpo.setConfirmTime(new Date());
		withdrawDao.update(wpo);

		// 返款
		po.add(Constants.FREE, amount);
		po.add(Constants.FUND, amount);
		po.subtract(Constants.FROZEN_FUND, wpo.getAmount());
		updateAccount(po);

		BigDecimal fee = wpo.getAmount().subtract(amount);
		log(po,
				EntityType.ORDER_WITHDRAW_RETURN,
				amount,
				fee,
				amount,
				BigDecimal.ZERO,
				opo.getId(),
				withdrawId,
				operator,
				MessageFormat.format("提现失败，提现金额：元，返还金额：元，手续费：元。",
						wpo.getAmount(), amount, fee));
	}

	@Override
	@Transactional
	public Long withdrawFrozen(Long userId, BigDecimal amount, BigDecimal fee,
			Long withdrawId) {
		Assert.gt(amount, BigDecimal.ZERO, AppCode.INVALID_AMOUNT);
		Assert.ge(fee, BigDecimal.ZERO, AppCode.INVALID_AMOUNT);

		AccountPO po = accountDao.get(userId);
		Assert.ne(po.getStatus(), EntityStatus.LOCKED, AppCode.ACCOUNT_FROZED);

		BigDecimal fund = amount.add(fee);
		Assert.le(fund, po.getFund(), AppCode.INSUFFICIENT_BALANCE);

		po.add(Constants.FROZEN_FUND, fund);
		po.subtract(Constants.FUND, fund);
		po.subtract(Constants.FREE, fund);
		updateAccount(po);

		return log(po, EntityType.ORDER_WITHDRAW_FROZEN, amount, fee, fund,
				BigDecimal.ZERO, null, withdrawId, 0, null);
	}

	@Override
	@Transactional
	public void withdrawDeduct(Integer operator, Long userId,
			BigDecimal amount, Long withdrawId, String note) {
		Assert.gt(amount, BigDecimal.ZERO, AppCode.INVALID_AMOUNT);

		AccountPO po = accountDao.get(userId);
		Assert.ne(po.getStatus(), EntityStatus.LOCKED, AppCode.ACCOUNT_FROZED);

		OrderPO opo = orderDao.getByRelated(EntityType.ORDER_WITHDRAW_FROZEN,
				withdrawId);
		Assert.eq(opo.getFundAmount(), amount, AppCode.UNUSUAL_OPERATE_AMOUNT);

		WithdrawPO wpo = withdrawDao.get(withdrawId);
		Assert.eq(wpo.getStatus(), EntityStatus.WITHDRAW_PAYED,
				AppCode.INVALID_WITHDRAW_STATUS);
		Assert.eq(amount, wpo.getAmount(), AppCode.INVALID_AMOUNT);

		wpo.setNote(note);
		wpo.setStatus(EntityStatus.WITHDRAW_FINISH);
		wpo.setConfirmId(operator);
		wpo.setConfirmTime(new Date());
		withdrawDao.update(wpo);

		// 检查冻结资金
		Assert.ge(po.getFrozenFund(), opo.getFundAmount(),
				AppCode.FROZEN_INSUFFICIENT_BALANCE);

		// 扣除冻结资金，保存累计提现金额
		po.add(Constants.TOTAL_WITHDRAW, amount);
		po.subtract(Constants.FROZEN_FUND, amount);

		updateAccount(po);
		log(po, EntityType.ORDER_WITHDRAW_DEDUCT, amount, BigDecimal.ZERO,
				amount, BigDecimal.ZERO, opo.getId(), withdrawId, operator,
				note);
	}

	@Override
	@Transactional(readOnly = true)
	public Account getAccount(Long userId) {
		Account ac = new Account();
		AccountPO po = accountDao.get(userId);
		if (po != null) {
			BeanUtils.copyProperties(po, ac);
		}
		return ac;
	}

	@Override
	@Transactional
	public void updatePasswd(long userId, String passwd) {
		AccountPO po = accountDao.get(userId);
		if (po != null) {
			po.setPassword(Text.MD5Encode(passwd));
		}
	}

	@Override
	@Transactional
	public boolean checkPasswd(long userId, String passwd) {
		AccountPO po = accountDao.get(userId);
		if (po != null) {
			String pwd = Text.MD5Encode(passwd);
			Assert.notNull(po.getPassword(), AppCode.PASSWD_WH_NOTEXIST);
			if (pwd.equals(po.getPassword())) {
				return true;
			}
		}
		return false;
	}

	private Long log(AccountPO apo, int type, BigDecimal amount,
			BigDecimal fee, BigDecimal fundAmount, BigDecimal grantAmount,
			Long originId, Long relatedId, Integer operatorId, String note) {
		OrderPO po = new OrderPO();

		po.setAmount(amount);
		po.setFee(fee);
		po.setType(type);
		po.setCreatedTime(new Date());
		po.setGrantAmount(grantAmount);
		po.setOperatorId(operatorId);
		po.setFundAmount(fundAmount);
		po.setOriginId(originId);
		po.setRelatedId(relatedId);
		po.setUserId(apo.getId());
		po.setUsername(apo.getUsername());
		po.setNote(note);

		orderDao.save(po);
		return po.getId();
	}

	private void frozen(int type, Long userId, BigDecimal amount, Long relatedId) {
		Assert.gt(amount, BigDecimal.ZERO, AppCode.INVALID_AMOUNT);

		AccountPO po = accountDao.get(userId);
		Assert.ne(po.getStatus(), EntityStatus.LOCKED, AppCode.ACCOUNT_FROZED);
		Assert.ge(po.getFree(), amount, AppCode.INSUFFICIENT_BALANCE);

		BigDecimal fund = BigDecimal.ZERO;
		BigDecimal grant = BigDecimal.ZERO;
		if (po.getGrant().compareTo(amount) == -1) {
			// 赠款账户金额 < 要冻结金额
			grant = po.getGrant();
			fund = amount.subtract(grant);
		} else {
			// 赠款账户金额 >= 要冻结金额
			grant = amount;
		}

		po.add(Constants.FROZEN_FUND, fund);
		po.add(Constants.FROZEN_GRANT, grant);
		po.subtract(Constants.FUND, fund);
		po.subtract(Constants.GRANT, grant);
		po.subtract(Constants.FREE, amount);

		updateAccount(po);
		log(po, type, amount, BigDecimal.ZERO, fund, grant, null, relatedId, 0,
				null);
	}

	private void deductReturn(int type, Long userId, BigDecimal deductAmount,
			BigDecimal returnAmount, Long relatedId) {
		Assert.ge(deductAmount, BigDecimal.ZERO, AppCode.INVALID_AMOUNT);
		Assert.ge(returnAmount, BigDecimal.ZERO, AppCode.INVALID_AMOUNT);

		OrderPO opo = orderDao.getByRelated(type, relatedId);
		Assert.notNull(opo, AppCode.RELATED_ORDER_NOT_EXIST);
		Assert.eq(opo.getAmount().add(opo.getFee()),
				deductAmount.add(returnAmount), AppCode.UNUSUAL_OPERATE_AMOUNT);

		AccountPO po = accountDao.get(userId);
		Assert.ge(po.getFrozenFund(), opo.getFundAmount(),
				AppCode.FROZEN_INSUFFICIENT_BALANCE);
		Assert.ge(po.getFrozenGrant(), opo.getGrantAmount(),
				AppCode.FROZEN_INSUFFICIENT_BALANCE);

		// 扣除
		BigDecimal grant = BigDecimal.ZERO;
		BigDecimal fund = BigDecimal.ZERO;
		if (deductAmount.compareTo(opo.getGrantAmount()) != 1) {
			// 扣除金额 <= 冻结赠款金额，则直接从冻结赠款金额中扣除
			grant = deductAmount;
		} else {
			// 扣除金额 > 冻结赠款金额，则从冻结现金金额和冻结赠款金额中扣除
			grant = opo.getGrantAmount();
			fund = deductAmount.subtract(grant);
		}

		if (deductAmount.compareTo(BigDecimal.ZERO) == 1) {
			if (type == EntityType.ORDER_BET_FROZEN) {
				log(po, EntityType.ORDER_BET_DEDUCT, deductAmount,
						BigDecimal.ZERO, fund, grant, opo.getId(), relatedId,
						0, "扣除投注成功所涉及款项");
			} else {
				log(po, EntityType.ORDER_BET_FLOOR_DEDUCT, deductAmount,
						BigDecimal.ZERO, fund, grant, opo.getId(), relatedId,
						0, "扣除投注成功所涉及保底款项");
			}
		}
		// 返还
		grant = opo.getGrantAmount().subtract(grant);
		fund = opo.getFundAmount().subtract(fund);

		if (returnAmount.compareTo(BigDecimal.ZERO) == 1) {
			if (type == EntityType.ORDER_BET_FROZEN) {
				log(po, EntityType.ORDER_BET_RETURN, returnAmount,
						BigDecimal.ZERO, fund, grant, opo.getId(), relatedId,
						0, "返还投注失败所涉及款项");
			} else {
				log(po, EntityType.ORDER_BET_FLOOR_RETURN, returnAmount,
						BigDecimal.ZERO, fund, grant, opo.getId(), relatedId,
						0, "返还投注失败所涉及保底款项");
			}
		}

		po.add(Constants.FUND, fund);
		po.add(Constants.GRANT, grant);
		po.add(Constants.FREE, returnAmount);
		po.add(Constants.TOTAL_BET, deductAmount);
		po.subtract(Constants.FROZEN_FUND, opo.getFundAmount());
		po.subtract(Constants.FROZEN_GRANT, opo.getGrantAmount());

		updateAccount(po);
	}

	private void updateAccount(AccountPO po) {
		StringBuilder buf = new StringBuilder(128);

		buf.append(po.getFund().toString());
		buf.append(po.getGrant().toString());
		buf.append(po.getFrozenFund().toString());
		buf.append(po.getFrozenGrant().toString());
		buf.append(po.getFree().toString());

		po.setVerifyCode(Text.MD5Encode(buf.toString()));
		accountDao.update(po);
	}

	@Transactional
	@Override
	public void resetWithdrawPassword(long userId) {
		AccountPO po = accountDao.get(userId);
		UserPO userPo = userDao.get(userId);
		if (userPo == null) {
			logger.error("不能重置用户的提现密码，uid：{}", userId);
			return;
		}
		if (po != null) {
			po.setPassword(userPo.getPassword());
		} else {
			logger.error("不能重置用户的提现密码，账户信息对象为空，uid：{}", userId);
		}
	}

	@Override
	@Transactional
	public void giveCommission(Integer operator, Long givedUserId,
			BigDecimal commission, Long givingBetPartnerId) {
		Assert.gt(commission, BigDecimal.ZERO, AppCode.INVALID_AMOUNT);

		AccountPO accountPo = accountDao.get(givedUserId);
		Assert.ne(accountPo.getStatus(), EntityStatus.LOCKED,
				AppCode.ACCOUNT_FROZED);

		accountPo.add(Constants.FUND, commission);
		accountPo.add(Constants.FREE, commission);
		accountPo.add(Constants.TOTAL_COMMISSION, commission);
		updateAccount(accountPo);

		Long originId = null;
		log(accountPo, EntityType.ORDER_COMMISSION_ADD, commission,
				BigDecimal.ZERO, commission, BigDecimal.ZERO, originId,
				givingBetPartnerId, operator, "增加佣金");
	}

	@Override
	@Transactional
	public Recharge getRecharge(long id) {
		Recharge rc = new Recharge();
		RechargePO po = rechargeDao.findById(id);
		if (po != null) {
			BeanUtils.copyProperties(po, rc);
		}
		return rc;
	}

	@Override
	@Transactional
	public void updateRecharge(long rechargeId, String note) {
		RechargePO rpo = rechargeDao.get(rechargeId);

		rpo.setStatus(EntityStatus.RECHARGE_FAIL);
		rpo.setNote(note);
		rechargeDao.update(rpo);
	}

	@Override
	@Transactional(noRollbackFor = XHRuntimeException.class)
	public void checkAndUpdatePasswd(long userId, String oldPasswd,
			String newPasswd) {
		Assert.isTrue(checkPasswd(userId, oldPasswd), AppCode.PASSWD_WH_WRONG);
		updatePasswd(userId, newPasswd);

	}

	@Override
	@Transactional
	public void rechargeSuccess(long rechargeId) {
		RechargePO rpo = rechargeDao.get(rechargeId);

		Date date = new Date();
		rpo.setStatus(EntityStatus.RECHARGE_FINISH);
		rpo.setPayTime(date);
		rpo.setAuditTime(date);
		rpo.setConfirmTime(date);

		rechargeDao.update(rpo);
	}

	@Override
	@Transactional
	public Map<Integer,BigDecimal> withRedFrozen(Long userId,
			BigDecimal redEnvalopeAmountBigDecimal, BigDecimal fee,
			long envalopeId) {
		Assert.gt(redEnvalopeAmountBigDecimal, BigDecimal.ZERO,
				AppCode.INVALID_AMOUNT);
		Assert.ge(fee, BigDecimal.ZERO, AppCode.INVALID_AMOUNT);

		AccountPO po = accountDao.get(userId);
		Assert.ne(po.getStatus(), EntityStatus.LOCKED, AppCode.ACCOUNT_FROZED);

		BigDecimal fund = redEnvalopeAmountBigDecimal.add(fee);
		Assert.le(fund, po.getFund(), AppCode.INSUFFICIENT_BALANCE);

		po.add(Constants.FROZEN_FUND, fund);
		po.subtract(Constants.FUND, fund);
		po.subtract(Constants.FREE, fund);
		Map<Integer,BigDecimal> data = new HashMap<>();
		data.put(Constants.FUND, po.getFund());
		data.put(Constants.FREE, po.getFree());
		data.put(Constants.FROZEN_FUND, po.getFrozenFund());
		updateAccount(po);

		log(po, EntityType.SEND_REDENVALOPE_FROZEN,
				redEnvalopeAmountBigDecimal, fee, fund, BigDecimal.ZERO, null,
				envalopeId, 0, null);
		return data;
	}

	@Override
	@Transactional
	public Map<Integer, BigDecimal> withdGrabRedDeduct(Long userId, Long smallRedEnvalopeAmount,
			RedEnvalope redEnvalope) {
		Map<Integer, BigDecimal> data = new HashMap<>();
		BigDecimal amount = new BigDecimal(smallRedEnvalopeAmount).divide(new BigDecimal(100.0),2,BigDecimal.ROUND_HALF_UP);
		Assert.gt(amount, BigDecimal.ZERO, AppCode.INVALID_AMOUNT);

		AccountPO po = accountDao.get(redEnvalope.getLtUserId());
		Assert.ne(po.getStatus(), EntityStatus.LOCKED, AppCode.ACCOUNT_FROZED);
		// 扣除发红包用户的冻结资金
		po.subtract(Constants.FROZEN_FUND, amount);
		updateAccount(po);
		// 增加抢红包用户的赠款
		po = accountDao.get(userId);
		po.add(Constants.GRANT, amount);
		po.add(Constants.FREE, amount);
		data.put(Constants.FREE, po.getFree());
		data.put(Constants.GRANT, po.getGrant());
		updateAccount(po);
		log(po,EntityType.GRAB_REDENVALOPE_TRANSFER,amount,BigDecimal.ZERO,BigDecimal.ZERO,BigDecimal.ZERO,redEnvalope.getEnvalopeId(),redEnvalope.getEnvalopeId(),
				0,String.format("抢红包成功，id为%s的红包转账给用户%s的红包金额：%s元，手续费：%s元。",
						redEnvalope.getEnvalopeId(),userId,amount, BigDecimal.ZERO));
		return data;
	}

	@Override
	@Transactional
	public void withSendRedReturn(BigDecimal leaveAmount, RedEnvalopePO redEnvalopePO) {
		Assert.gt(leaveAmount, BigDecimal.ZERO, AppCode.INVALID_AMOUNT);

		AccountPO po = accountDao.get(redEnvalopePO.getLtUserId(),LockMode.UPGRADE);
		// 返款
		po.add(Constants.FUND, leaveAmount);
		po.add(Constants.FREE, leaveAmount);
		po.subtract(Constants.FROZEN_FUND,leaveAmount);
		//po.add(Constants.FREE, leaveAmount);//返款
 		updateAccount(po);
		BigDecimal redEnvalopeDecimal = new BigDecimal(redEnvalopePO.getRedEnvalopeAmount()/100.0);
		log(po,EntityType.SEND_REDENVALOPE_FROZEN_RETURN,leaveAmount,BigDecimal.ZERO,redEnvalopeDecimal,BigDecimal.ZERO,null,redEnvalopePO.getEnvalopeId(),
				0,String.format("红包失效，红包金额：%s元，返还金额：%s元，手续费：%s元。",
						redEnvalopeDecimal, leaveAmount, BigDecimal.ZERO));
	}
}
