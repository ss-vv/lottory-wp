package com.unison.lottery.pm.service;

/**
 * @author yongli zhu
 */
public interface RechargeGrantService {
	/**
	 * 注册时间在2012-06-08号到2012-07-02号之间，
	 * 已经绑定手机和验证邮箱，且充值金额大于等于50元的用户，
	 * 返还10元彩金。每个用户本次活动只能赠送一次，每个身份证号
	 * 只能赠送一次，每个电子邮箱只能赠送一次，每个手机号只能
	 * 赠送一次，每个ip只能赠送一次。赠送的购彩金只能用于投注，
	 * 不能提现。
	 */
	public void rechargeGrant(Long promotionId);
	
	/**
	 * 活动期间，注册送3元
	 * @param promotionId
	 */
	public void registGrant(Long promotionId);
	
	/**
	 * 绑定手机的新注册用户赠送充值优惠券
	 * @param promotionId
	 */
	public void newUserGrantVoucher(Long promotionId);
	
	/**
	 * 新老用户赠送客户端充值优惠券
	 * @param promotionId
	 */
	public void clientGrantVoucher(Long promotionId);
	
	/**
	 * 订购彩铃赠送购彩金
	 * @param promotionId
	 */
	public void orderColorRingGrant(Long promotionId);
	
	/**
	 * 投注赠送现金券
	 */
	public void betReturnCashCoupon(Long promotionId);
}
