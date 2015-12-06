package com.unison.lottery.pm.win;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import com.unison.lottery.pm.service.PromotionService;
import com.xhcms.commons.job.Job;
import com.xhcms.lottery.commons.persist.entity.PromotionPO;

/**
 *  竞彩代购，代购晒单，跟单，2串1玩法，中奖
 * @author Wang Lei
 *
 */
public class Win2C1Task extends Job{
	
	protected  Logger log = LoggerFactory.getLogger(getClass());
	@Autowired
	PromotionService promotionService;
	
	private Long promotionId;
	
	@Override
	protected void execute() throws Exception {
		PromotionPO promotionPO = promotionService.getPromotionById(promotionId);
		if(promotionPO==null){
			log.error("处理活动失败！未找到id=:{}的活动！",promotionId);
			return;
		}
		log.info("开始处理:{}活动。",promotionPO.getName());
		int count=0;
		for(Long schemeId:promotionService.findWin2C1PromotionSchemeIds(promotionId)){
			try {
				promotionService.insertWingrantFromPromotion(schemeId,promotionPO);
				count++;
			} catch (Exception e) {
				log.error(e.getMessage());
			}
		}
		log.info("{}活动插入活动子表完毕，共成功插入{}条。",promotionPO.getName(),count);
		log.info("结束处理:{}活动。",promotionPO.getName());
	}

	public Long getPromotionId() {
		return promotionId;
	}

	public void setPromotionId(Long promotionId) {
		this.promotionId = promotionId;
	}
}
