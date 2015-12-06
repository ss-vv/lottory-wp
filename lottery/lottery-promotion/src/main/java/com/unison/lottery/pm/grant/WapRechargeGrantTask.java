package com.unison.lottery.pm.grant;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.unison.lottery.pm.service.WapRechargeGrantService;
import com.xhcms.commons.job.Job;

/**
 * 
 * @author yonglizhu
 *
 */
public class WapRechargeGrantTask extends Job {
	protected  Logger log = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private WapRechargeGrantService wapRechargeGrantService;
	
	private Long promotionId;
	
	@Override
	protected void execute() throws Exception {
		log.info("wap recharge grant activity start promotionId={}",promotionId);
		wapRechargeGrantService.wapRechargeGrant(promotionId);
		log.info("wap recharge grant activity end promotionId={}",promotionId);
	}

	public Long getPromotionId() {
		return promotionId;
	}

	public void setPromotionId(Long promotionId) {
		this.promotionId = promotionId;
	}
}
