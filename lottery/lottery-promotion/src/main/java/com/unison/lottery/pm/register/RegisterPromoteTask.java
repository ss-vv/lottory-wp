package com.unison.lottery.pm.register;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.unison.lottery.pm.service.RechargeGrantService;
import com.xhcms.commons.job.Job;

public class RegisterPromoteTask extends Job {
	protected  Logger log = LoggerFactory.getLogger(getClass());
	
	@Autowired
	RechargeGrantService rechargeGrantService;
	
	private Long promotionId;
	
	@Override
	protected void execute() throws Exception {
		try{
			log.info("注册送3元彩金活动开始 promotionId={}", promotionId);
			rechargeGrantService.registGrant(promotionId);
			log.info("注册送3元彩金活动结束 promotionId={}", promotionId);
		}catch(Exception e) {
			e.printStackTrace();
			log.error("注册送3元活动出错！promotionId={}", promotionId);
		}
	}

	public Long getPromotionId() {
		return promotionId;
	}

	public void setPromotionId(Long promotionId) {
		this.promotionId = promotionId;
	}
}
