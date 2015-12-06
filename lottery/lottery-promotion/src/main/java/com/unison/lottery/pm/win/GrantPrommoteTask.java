package com.unison.lottery.pm.win;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.unison.lottery.pm.lang.WinGrant;
import com.unison.lottery.pm.service.PromotionService;
import com.unison.lottery.pm.service.WinGrantService;
import com.xhcms.commons.job.Job;
import com.xhcms.lottery.commons.data.Promotion;
import com.xhcms.lottery.commons.persist.entity.PromotionPO;

/**
 *  自动选择符合条件的数据，并插入赠款表
 * @author Wang Lei
 */
public class GrantPrommoteTask extends Job{
	
	protected  Logger log = LoggerFactory.getLogger(getClass());
	@Autowired
	PromotionService promotionService;
	@Autowired
	WinGrantService winGrantService;
	
	private Long promotionId;
	
	@Override
	protected void execute() throws Exception {
		Promotion promotion = promotionService.getPromotionVOById(promotionId);
		if(promotion==null){
			log.error("处理活动失败！未找到id=:{}的活动！",promotionId);
			return;
		}
		log.info("开始处理:{}活动。",promotion.getName());
		insertWingrantFromPromotion(promotion);
		
		log.info("开始自动审核"+promotion.getName()+"活动记录，并赠送优惠劵！");
		autoSponsorAndCreateVouchers();
		
		log.info("结束处理:{}活动。",promotion.getName());
	}
	
	private void insertWingrantFromPromotion(Promotion promotion){
		int count=0;
		List<Long> schemeIds = promotionService.findSchemeIdsByPromotion(promotion);
		for(Long schemeId:schemeIds){
			try {
				promotionService.insertWingrantFromPromotion(schemeId,promotion);
				count++;
			} catch (Exception e) {
				log.error(e.getMessage());
			}
		}
		log.info("{}活动插入活动子表完毕，共成功插入{}条。",promotion.getName(),count);
	}
	
	private void autoSponsorAndCreateVouchers(){
		PromotionPO ppo = promotionService.getPromotionById(promotionId);
		List<Long> ids = winGrantService.findIdsByPMIDandStatus(promotionId, WinGrant.submitStatus.unSubmit);
		int count=0;
		for(Long id:ids){
			try {
				promotionService.autoSponsorAndCreateVouchers(id, ppo);
				count++;
			} catch (Exception e) {
				log.error(e.getMessage());
			}
		}
		log.info("结束自动审核"+ppo.getName()+"活动记录！共成功审核"+count+"条");
	}

	public Long getPromotionId() {
		return promotionId;
	}

	public void setPromotionId(Long promotionId) {
		this.promotionId = promotionId;
	}
}
