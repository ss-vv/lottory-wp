package com.xhcms.lottery.commons.persist.dao;

import java.util.List;
import com.xhcms.commons.persist.Dao;
import com.xhcms.lottery.commons.persist.entity.PromotionPO;

/**
 * 
 * @author Wang Lei
 *
 */
public interface PMPromotionDao extends Dao<PromotionPO> {
	List<Long> getPromotionIdsByLotteryId(String LotteryId);
}
