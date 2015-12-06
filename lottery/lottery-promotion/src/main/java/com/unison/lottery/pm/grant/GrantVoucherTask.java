package com.unison.lottery.pm.grant;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.unison.lottery.pm.service.RechargeGrantService;
import com.xhcms.commons.job.Job;

/**
 * 
 * @author yonglizhu
 *
 */
public class GrantVoucherTask extends Job {
	protected  Logger log = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private RechargeGrantService rechargeGrantService;
	
	private Long promotionId;
	
	@Override
	protected void execute() throws Exception {
		try{
		log.info("八月客户端首充20送10奖金开始, promotionId={}",promotionId);
		rechargeGrantService.clientGrantVoucher(promotionId);
		log.info("八月客户端首充20送10奖金结束, promotionId={}",promotionId);
		} catch(Exception e) {
			e.printStackTrace();
			log.error("八月客户端首充20送10奖金出错！promotionId={}",promotionId);
		}
	}

	public Long getPromotionId() {
		return promotionId;
	}

	public void setPromotionId(Long promotionId) {
		this.promotionId = promotionId;
	}
}
