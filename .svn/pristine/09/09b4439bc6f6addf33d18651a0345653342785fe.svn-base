package com.unison.lottery.pm.register;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.unison.lottery.pm.service.RechargeGrantService;
import com.xhcms.commons.job.Job;

public class ColorringGrantPromoteTask extends Job {
	protected  Logger log = LoggerFactory.getLogger(getClass());
	
	@Autowired
	RechargeGrantService rechargeGrantService;
	
	private Long promotionId;
	
	@Override
	protected void execute() {
		if (promotionId == 32) {
			log.info("订购彩铃赠送彩金活动开始！promotionId={}", promotionId);
			try {
				rechargeGrantService.orderColorRingGrant(promotionId);
			} catch (Exception e) {
				log.error(e.getMessage());
				e.printStackTrace();
			}
			log.info("订购彩铃赠送彩金活动结束！promotionId={}", promotionId);
		} else {
			log.error("活动id出错！promotionId={}", promotionId);
		}
	}

	public Long getPromotionId() {
		return promotionId;
	}

	public void setPromotionId(Long promotionId) {
		this.promotionId = promotionId;
	}
}
