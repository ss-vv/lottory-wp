package com.xhcms.lottery.commons.persist.service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.xhcms.commons.lang.Paging;

public interface PMRechargeRedeemedService {
	/**
	 * 查询充值返彩金
	 * @param paging
	 * @param from
	 * @param to
	 * @param state
	 * @param username
	 * @param operatorName
	 */
	void findPMRechargeRedeemed(Paging paging, Date from, Date to,
			int status, String username, String operatorName);
	
	/**
	 * 充值返彩金提交到赠款表
	 * @param ids
	 * @param operatorid
	 * @param operatorName
	 */
	void submitGrant(List<Long> ids, int operatorid, String operatorName);
	
	/**
	 * 判断用户是否可以参加充值返还彩金活动
	 * @param userId
	 * @param promotionId
	 * @return true 可以参加
	 */
	boolean isPartakePromotion(Long userId, Long promotionId);
	
	/**
	 * 判断用户充值是否充到赠款账户
	 * @param promotionId
	 * @param orderNo
	 * @param userId
	 * @return true到赠款账户
	 */
	boolean isRechargeToGrantAccount(Long promotionId, Long rechargeId, Long userId);
	
	/**
	 * 保存用户参加活动记录
	 * @param userId
	 * @param promotionId
	 * @param rechargeId
	 */
	void savePMRecharge(Long userId, Long promotionId, Long rechargeId);
	
	/**
	 * 保存充值返还彩金活动数据
	 * @param userId
	 * @param promotionId
	 * @param rechargeId
	 * @param rechargeAmount
	 */
	void savePMRechargeRedeemed(Long userId, Long promotionId, Long rechargeId, BigDecimal rechargeAmount);
}
