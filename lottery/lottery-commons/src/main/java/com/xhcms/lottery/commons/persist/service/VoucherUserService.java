package com.xhcms.lottery.commons.persist.service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.xhcms.commons.lang.Paging;
import com.xhcms.lottery.commons.data.Account;
import com.xhcms.lottery.commons.data.MayPMVoucher;
import com.xhcms.lottery.commons.data.VoucherUser;
import com.xhcms.lottery.commons.persist.entity.VoucherPMPO;
import com.xhcms.lottery.commons.persist.entity.VoucherPO;
import com.xhcms.lottery.commons.persist.entity.VoucherTypePO;
import com.xhcms.lottery.commons.persist.entity.VoucherUserExtendPO;

/**
 * 优惠卷service
 * @author Wang Lei
 *
 */
public interface VoucherUserService {

	/**
	 * 分页获取有效优惠卷
	 * @param paging
	 * @param userId
	 * @return
	 */
	Paging listValidByUserid(Paging paging, Long userId);

	/**
	 * 分页获取已使用优惠卷
	 * @param paging
	 * @param userId
	 * @return
	 */
	Paging listUsedByUserid(Paging paging, Long userId);

	/**
	 * 分页获取已过期优惠卷
	 * @param paging
	 * @param userId
	 * @return
	 */
	Paging listExpireByUserid(Paging paging, Long userId);

	/**
	 * 获取所有有效的充值优惠卷
	 * @param userId
	 * @return
	 */
	List<VoucherUser> listValidRechargeByUserid(Long userId);

	/**
	 * 锁定充值优惠劵
	 * @param rechargeId
	 * @param userId
	 * @param voucherId
	 * @param price
	 */
	void lockingRechargeVoucher(Long rechargeId, Long userId, Long voucherId,
			BigDecimal price);

	/**
	 * 使用Web赠款优惠劵
	 * @param voucherId
	 * @param userId
	 * @return
	 */
	void useWebGrantVoucher(Long voucherId, Long userId);
	
	/**
	 * 使用Web充值优惠劵
	 * @param rechargeId
	 * @param userId
	 * @return
	 */
	VoucherUser useWebRechargeVoucher(Long rechargeId, Long userId);

	/**
	 * 使用Client赠款优惠劵
	 * @param voucherId
	 * @param userId
	 * @return
	 */
	void useClientGrantVoucher(Long voucherId, Long userId);

	/**
	 * 使用Client充值优惠劵
	 * @param rechargeId
	 * @param userId
	 * @return
	 */
	VoucherUser useClientRechargeVoucher(Long rechargeId, Long userId);
	
	/***
	 * 产生充值优惠劵，并返回产生的数量
	 * @param voupm
	 * @param ac
	 * @param voucherMaps
	 * @return
	 */
	int createRechargeVoucher(VoucherPMPO voupm, Account ac,
			Map<String, VoucherPO> voucherMaps);

	List<VoucherUserExtendPO> list(Paging paging, String voucherUserViewStatus,
			String username, Long id, String voucherId, Long granttypeId);

	/**
	 * 生成优惠劵 
	 * @param username
	 * @param validDay
	 * @param voucherId
	 * @param granttypeId
	 */
	void createVoucher(String username, Long validDay,
			String voucherId, Long granttypeId);

	/**
	 * 生成优惠劵
	 * @param userId 用户id
	 * @param validDay 优惠劵有效时间
	 * @param voucherId 优惠劵id
	 * @param granttypeId 赠款类型id
	 */
	void createVoucher(Long userId, Long validDay, String voucherId,
			Long granttypeId);
	
	/**
	 * 取得五月份优惠券赠送数据
	 * @return
	 */
	MayPMVoucher getMayPMVoucherGrant(Long userId);

	/**
	 * 生成优惠劵并插入按日赠送记录表
	 * @param userId
	 * @param voucherId
	 * @param granttypeId
	 * @param day 赠送日期
	 */
	void createVoucerAndRecord(Long userId, String voucherId,
			Long granttypeId, Date day);

	List<VoucherUserExtendPO> list(Paging paging, String voucherUserViewStatus,
			VoucherUserExtendPO voucher);

	List<VoucherTypePO> listAllVoucherType();

	/**
	 * 检查网站充值优惠劵是否有效
	 * @param voucherId
	 * @param userId
	 * @param reahangePrice
	 */
	void validWebVoucherRV(Long voucherId, Long userId, BigDecimal reahangePrice);
	/**
	 * 检查网站彩金优惠劵是否有效
	 * @param voucherId
	 * @param userId
	 * @param reahangePrice
	 */
	void validWebVoucher(Long voucherId, Long userId, BigDecimal reahangePrice);

	/**
	 * 检查客户端充值优惠劵是否有效
	 * @param voucherId
	 * @param userId
	 * @param reahangePrice
	 */
	void validClientVoucherRV(Long voucherId, Long userId,
			BigDecimal reahangePrice);
	
	/**
	 * 检查客户端彩金优惠劵是否有效
	 * @param voucherId
	 * @param userId
	 * @param reahangePrice
	 */
	void validClientVoucher(Long voucherId, Long userId,
			BigDecimal reahangePrice);
}
