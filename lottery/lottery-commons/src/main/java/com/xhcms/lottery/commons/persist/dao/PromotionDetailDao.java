package com.xhcms.lottery.commons.persist.dao;

import java.util.List;
import com.xhcms.commons.persist.Dao;
import com.xhcms.lottery.commons.persist.entity.PromotionDetailPO;

/**
 * 活动详情DAO。
 * @author Wang Lei
 */
public interface PromotionDetailDao extends Dao<PromotionDetailPO>{
	
	List<PromotionDetailPO> findListByPromotionId(Long promotionId);
	
}
