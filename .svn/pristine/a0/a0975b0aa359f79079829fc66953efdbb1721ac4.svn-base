package com.unison.lottery.pm.persist;

import java.util.Date;
import java.util.List;
import com.unison.lottery.pm.data.PromotionWinResult;
import com.xhcms.lottery.commons.data.Promotion;
import com.xhcms.lottery.commons.persist.dao.BetSchemeDao;
import com.xhcms.lottery.commons.persist.entity.PromotionPO;

/**
 * 活动投注方案
 * @author Wang Lei
 */
public interface PMBetSchemeDao extends BetSchemeDao {
	/**
	 * 查询竞彩活动条件的方案id列表
	 * @param promotion
	 * @param status
	 * @param passTypeIds
	 * @param buyAmount
	 * @param afterTaxBonus
	 * @return
	 */
	List<Long> findPromotionSchemeIds(PromotionPO promotion,int status,String passTypeIds,Integer buyAmount,Integer afterTaxBonus);
	
	 /**
     * 查询符合2012.6竞彩足球中奖加奖活动条件的方案列表
     * @param paging
     * @param from
     * @param to
     * @param status
     * @return
     */
    List<Long> findFootballPromotionSchemes(PromotionPO promotion, int status);
    
    /**
     * 查询符合2012.6竞彩篮球中奖加奖活动条件的方案列表
     * @param paging
     * @param from
     * @param to
     * @param status
     * @return
     */
    List<Long> findBasketballPromotionSchemes(PromotionPO promotion, int status); 
    
    /**
     * 中奖排行榜
     * @param top
     * @param startTime
     * @param endTime
     * @param lotteryId
     * @param status
     * @return
     */
    List<PromotionWinResult> findWinTOP(int top,Date startTime,Date endTime,String lotteryId,int status);
    
    /**
     * 返回符合条件的方案id列表
     * @param Promotion
     * @return
     */
    List<Long> findSchemeIdsByPromtion(Promotion promotion);
}
