package com.unison.lottery.pm.service.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.unison.lottery.pm.data.RechargeResult;
import com.xhcms.lottery.commons.persist.entity.PromotionPO;
import com.unison.lottery.pm.lang.RechargeGrant;
import com.unison.lottery.pm.persist.PMGrantAccountDao;
import com.xhcms.lottery.commons.persist.dao.PMPromotionDao;
import com.unison.lottery.pm.persist.PMRechargeGrantDao;
import com.unison.lottery.pm.service.WapRechargeGrantService;
import com.xhcms.lottery.commons.persist.entity.GrantPO;
import com.xhcms.lottery.commons.persist.dao.GrantDao;
import com.xhcms.lottery.commons.persist.entity.UserPO;
import com.xhcms.lottery.commons.persist.service.AccountService;
import com.xhcms.lottery.lang.EntityStatus;

public class WapRechargeGrantServiceImpl implements WapRechargeGrantService {
	protected Logger log = LoggerFactory.getLogger(getClass());
	@Autowired
	private PMRechargeGrantDao pMRechargeGrantDao;
	@Autowired
	private PMGrantAccountDao pMGrantAccountDao;
	@Autowired
	private GrantDao grantDao;
	@Autowired
	private PMPromotionDao pMPromotionDao;
	@Autowired
	private AccountService accountService;

	/**
	 * 大V彩wap网首次充值赠款活动
	 * 2-19元赠2元，20-99元赠10元，100-999元赠20元，1000元-1999赠80元，2000元以上赠200元
	 */
	@Override
	@Transactional
	public void wapRechargeGrant(Long promotionId) {
		try {
			PromotionPO promotionPO = pMPromotionDao.get(promotionId);
			Date from = promotionPO.getStartTime();
			Date to = promotionPO.getEndTime();
			log.info("promotion information：promotionName={},startTime={},endTime={}",
					new Object[] { promotionPO.getName(),
							promotionPO.getStartTime(),
							promotionPO.getEndTime() });
			
			//取得wap支付宝首次充值的用户充值记录
			List<RechargeResult> reResultList = pMGrantAccountDao.getFirstRechargeRecord(from, to);
			log.info("wap first recharge record num={}" + reResultList.size());		
			if(reResultList != null && reResultList.size() > 0) {
				for(RechargeResult reResult : reResultList) {
					if(reResult.getAmount().compareTo(new BigDecimal(2)) >= 0) {
						//取得手机验证的用户信息	
						UserPO userPO = pMRechargeGrantDao.getVerifyMobileById(reResult.getUserId(), from, to);
						if(userPO != null) {
							log.info("userId={}" + userPO.getId());
							BigDecimal grantAmount = getGrantAmount(reResult.getAmount());
							log.info("grantAmount={}" + grantAmount);
							if(grantAmount != null) {
								// 插入赠款记录表，并返回赠款记录id
								Long grantId = pMRechargeGrantDao.insertWapGrant(
										userPO, promotionPO, grantAmount);
								// 插入wap赠款活动记录表
								pMRechargeGrantDao.insertWapRechargeGrant(userPO,
										promotionPO, grantAmount);
								// 审批、赠款
								this.batchPass(grantId, RechargeGrant.auditId);
							}
						}
					}
				}
			}	
		} catch (Exception e) {
			log.error("wap recharge grant error", e);
		}

	}
	
	private BigDecimal getGrantAmount(BigDecimal amount) {
		BigDecimal grantAmount = new BigDecimal(0);
		if(amount.compareTo(new BigDecimal(2000)) >= 0) {
			grantAmount = new BigDecimal(200);
		} else if(amount.compareTo(new BigDecimal(1000)) >= 0) {
			grantAmount = new BigDecimal(80);
		} else if(amount.compareTo(new BigDecimal(100)) >= 0) {
			grantAmount = new BigDecimal(20);
		} else if(amount.compareTo(new BigDecimal(20)) >= 0) {
			grantAmount = new BigDecimal(10);
		} else if(amount.compareTo(new BigDecimal(2)) >= 0){
			grantAmount = new BigDecimal(2);
		} else {
			log.info("recharge amount error!");
		}
		
		if(grantAmount.compareTo(new BigDecimal(0)) > 0) {
			return grantAmount;
		} else {
			return null;
		}
		
	}

	// 审批、赠款
	private void batchPass(Long id, int auditId) {
		if (id == null) {
			return;
		}
		GrantPO grantPO = grantDao.get(id);

		// 判断是否未审核
		if (grantPO != null && grantPO.getStatus() == EntityStatus.GRANT_INIT) {
			grantPO.setStatus(EntityStatus.GRANT_AUDIT);
			accountService.grant(auditId, grantPO.getUserId(), grantPO.getAmount(),
					grantPO.getNote());
		}
		
	}

}
