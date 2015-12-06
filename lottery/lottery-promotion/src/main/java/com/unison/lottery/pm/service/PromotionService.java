package com.unison.lottery.pm.service;

import java.util.Collection;
import java.util.Date;
import java.util.List;
import com.unison.lottery.pm.data.PromotionWinResult;
import com.unison.lottery.pm.entity.WinGrantPO;
import com.xhcms.commons.lang.Paging;
import com.xhcms.lottery.commons.data.Promotion;
import com.xhcms.lottery.commons.persist.entity.PromotionPO;

/**
 * 
 * @author Wang Lei
 *
 */
public interface PromotionService {
	
	/**
	 * 查询篮彩奖上奖活动条件方案。
	 */
	List<Long> findBasketballPromotionSchemeIds(Long promotionId);
	
	/**
	 * 查询足彩奖上奖活动条件方案。
	 */
	List<Long> findFootballPromotionSchemeIds(Long promotionId);
	
	/**
	 * 查询2串1未中奖方案。
	 */
	List<Long> findNotWin2C1PromotionSchemeIds(Long promotionId);
	
	/**
	 * 查询2串1中奖方案。
	 */
	List<Long> findWin2C1PromotionSchemeIds(Long promotionId);
	
	/**
	 *  返回符合条件的活动id列表
	 * @param promotion
	 * @return
	 */
	List<Long> findSchemeIdsByPromotion(Promotion promotion);
	
	/** 
	 * 根据活动id返回活动VO
	 * @param promotionId
	 * @return
	 */
	Promotion getPromotionVOById(Long promotionId);
	
	/**
	 * 活动加奖插入活动记录表
	 */
	void insertWingrantFromPromotion(Long schemeId,PromotionPO  promotionPO);
	
	/**
	 * 活动加奖插入活动记录表
	 * @param schemeId
	 * @param promotion
	 */
	void insertWingrantFromPromotion(Long schemeId,Promotion  promotion);

	/**
	 * 查询奖上奖活动排行榜TOP
	 * @param top
	 * @param pomotionId
	 * @return
	 */
	List<PromotionWinResult> findPromotionWinTOP(int top,Long pomotionId);
	
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
	 * 根据活动id获得一条活动信息
	 * @param promotionId
	 * @return
	 */
	PromotionPO getPromotionById(Long promotionId);
	/**
	 * 查询活动子表list
	 * @param paging
	 * @param from
	 * @param to
	 * @param status
	 * @param winGrant
	 * @param promotionidSet
	 * @return
	 */
	List<WinGrantPO> listWinGrantPO(Paging paging, Date from, Date to, int status,WinGrantPO winGrant,String lottertId);
	
	/**
	 * 将活动子表数据 批量插入赠款表,更新活动自表状态为已提交
	 * @param ids
	 * @param operatorid
	 * @param operatorName
	 */
	void sponsorGrants(Collection<Long> ids,int operatorid,String operatorName);

	/**
	 * 自动审核并赠送优惠劵
	 * @param winGrantId
	 * @param ppo
	 */
	void autoSponsorAndCreateVouchers(Long winGrantId, PromotionPO ppo);
}
