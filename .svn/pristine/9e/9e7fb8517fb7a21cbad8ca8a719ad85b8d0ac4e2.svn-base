package com.xhcms.lottery.commons.persist.service.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.xhcms.commons.lang.Paging;
import com.xhcms.lottery.commons.persist.dao.GrantDao;
import com.xhcms.lottery.commons.persist.dao.PMPromotionDao;
import com.xhcms.lottery.commons.persist.dao.PMRechargeDao;
import com.xhcms.lottery.commons.persist.dao.PMRechargeRedeemedDao;
import com.xhcms.lottery.commons.persist.dao.UserDao;
import com.xhcms.lottery.commons.persist.entity.GrantPO;
import com.xhcms.lottery.commons.persist.entity.PMRechargePO;
import com.xhcms.lottery.commons.persist.entity.PMRechargeRedeemedPO;
import com.xhcms.lottery.commons.persist.entity.PromotionPO;
import com.xhcms.lottery.commons.persist.entity.UserPO;
import com.xhcms.lottery.commons.persist.service.PMRechargeRedeemedService;
import com.xhcms.lottery.lang.EntityStatus;
import com.xhcms.lottery.lang.EntityType;

public class PMRechargeRedeemedServiceImpl implements PMRechargeRedeemedService {
	
	private Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private PMRechargeRedeemedDao pMRechargeRedeemedDao;
	
	@Autowired
	private PMPromotionDao pMPromotionDao;
	
	@Autowired
	private GrantDao grantDao;
	
	@Autowired
	private UserDao userDao;
	
	@Autowired
	private PMRechargeDao pMRechargeDao;
	
	@Override
	@Transactional
	public void findPMRechargeRedeemed(Paging paging, Date from, Date to,
			int status, String username, String operatorName) {
		List<PMRechargeRedeemedPO> rechRePOs = pMRechargeRedeemedDao
				.find(paging, from, to, status, username,
						operatorName);
		paging.setResults(rechRePOs);
	}
	
	@Override
	@Transactional
	public void submitGrant(List<Long> ids, int operatorId, String operatorName) {
		List<PMRechargeRedeemedPO> rechRePOs = pMRechargeRedeemedDao.findByIds(ids);
		GrantPO grant;
		PromotionPO ppo;
		Date date = new Date();
		if(null != rechRePOs && rechRePOs.size() > 0) {
			for(PMRechargeRedeemedPO re: rechRePOs) {
				if(re.getStatus() == 0) {
					re.setStatus(EntityType.RECHARGE_REDEEMED_SUBMITED);
					re.setOperatorId(operatorId);
					re.setOperatorName(operatorName);
					re.setOperateTime(date);
					pMRechargeRedeemedDao.update(re);
					
					grant = new GrantPO();
					grant.setUserId(re.getUserId());
					grant.setAmount(re.getAmount());
					grant.setCreatedTime(date);
					grant.setOperatorId(operatorId);
					grant.setStatus(EntityStatus.GRANT_INIT);
					
					ppo = pMPromotionDao.get(re.getPromotionId());
					grant.setNote(ppo.getName());
					grant.setGrantTypeId(ppo.getGrantTypeId());
					grantDao.save(grant);
				}
			}
		}		
	}
	
	@Override
	@Transactional
	public boolean isPartakePromotion(Long userId, Long promotionId) {
		boolean isPartake = false;
		PromotionPO pmPO = pMPromotionDao.get(promotionId);
		UserPO userPO = userDao.get(userId);
		PMRechargeRedeemedPO reRePO = pMRechargeRedeemedDao.find(userId,
				promotionId, userPO.getIdNumber(), userPO.getMobile(),
				userPO.getEmail());
		Date date = new Date();
		if(null != pmPO) {
			if(null == reRePO && date.after(pmPO.getStartTime()) && date.before(pmPO.getEndTime())) {
				isPartake = true;
			}
		} else {
			logger.error("判断用户是否可以参加活动时，充值返还彩金活动id配置出错！");
		}
		
		
		return isPartake;
	}
	
	@Override
	@Transactional
	public boolean isRechargeToGrantAccount(Long promotionId, Long rechargeId, Long userId) {
		boolean isToGrant = false;
		Date date = new Date();
		PMRechargePO rechargePO = pMRechargeDao.find(userId, rechargeId);
		if(null != rechargePO) {
			PromotionPO pmPO = pMPromotionDao.get(promotionId);
			if(null != pmPO) {
				if(date.after(pmPO.getStartTime()) && date.before(pmPO.getEndTime())) {
					UserPO userPO = userDao.get(userId);
					PMRechargeRedeemedPO reRePO = pMRechargeRedeemedDao.find(userId,
							promotionId, userPO.getIdNumber(), userPO.getMobile(),
							userPO.getEmail());
					if(null == reRePO) {
						isToGrant = true;
					}
				}
			} else {
				logger.error("判断是否充值到用户赠款账户时，充值返还彩金活动id配置出错！");
			}
		}
		
		return isToGrant;
	}
	
	@Override
	@Transactional
	public void savePMRecharge(Long userId, Long promotionId, Long rechargeId) {
		PromotionPO pmPO = pMPromotionDao.get(promotionId);
		if(null != pmPO) {
			PMRechargePO pmrePO = new PMRechargePO();
			pmrePO.setUserId(userId);
			pmrePO.setPromotionId(promotionId);
			pmrePO.setGrantTypeId(pmPO.getGrantTypeId());
			pmrePO.setRechargeId(rechargeId);
			pmrePO.setCreatedTime(new Date());
			pMRechargeDao.save(pmrePO);
		} else {
			logger.error("保存用户参加活动记录时，充值返还彩金活动id配置出错！");
		}
	}
	
	@Override
	@Transactional
	public void savePMRechargeRedeemed(Long userId, Long promotionId,
			Long rechargeId, BigDecimal rechargeAmount) {
		Date date = new Date();
		PMRechargeRedeemedPO pmreredPO = new PMRechargeRedeemedPO();
		UserPO userPO = userDao.get(userId);
		PromotionPO pmPO = pMPromotionDao.get(promotionId);
		BigDecimal grantAmount = getGrantAmount(rechargeAmount);
		PMRechargePO rechargePO = pMRechargeDao.find(userId, rechargeId);
		pmreredPO.setUserId(userId);
		pmreredPO.setUsername(userPO.getUsername());
		pmreredPO.setPromotionId(promotionId);
		pmreredPO.setGrantTypeId(pmPO.getGrantTypeId());
		pmreredPO.setPmRechargeId(rechargePO.getId());
		pmreredPO.setAmount(grantAmount);
		pmreredPO.setCreatedTime(date);
		pmreredPO.setStatus(EntityType.RECHARGE_REDEEMED_UNSUBMIT);
		pmreredPO.setIdNumber(userPO.getIdNumber());
		pmreredPO.setMobile(userPO.getMobile());
		pmreredPO.setEmail(userPO.getEmail());
		
		pMRechargeRedeemedDao.save(pmreredPO);
	}
	
	private BigDecimal getGrantAmount(BigDecimal amount) {
		BigDecimal grantAmount = new BigDecimal(0);
		if(amount.compareTo(new BigDecimal(5000)) >= 0) {
			grantAmount = amount.multiply(new BigDecimal(0.15));
		} else if(amount.compareTo(new BigDecimal(1000)) >= 0) {
			grantAmount = amount.multiply(new BigDecimal(0.12));
		} else if(amount.compareTo(new BigDecimal(500)) >= 0) {
			grantAmount = amount.multiply(new BigDecimal(0.1));
		} else if(amount.compareTo(new BigDecimal(100)) >= 0) {
			grantAmount = amount.multiply(new BigDecimal(0.08));
		} else {
			logger.info("recharge promotion amount error!");
		}
		
		if(grantAmount.compareTo(new BigDecimal(0)) > 0) {
			return grantAmount;
		} else {
			return null;
		}
	}
}
