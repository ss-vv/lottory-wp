package com.xhcms.lottery.commons.persist.dao;

import java.util.Date;
import java.util.List;
import java.util.Set;

import com.xhcms.commons.lang.Paging;
import com.xhcms.commons.persist.Dao;
import com.xhcms.lottery.commons.persist.entity.VoucherTypePO;
import com.xhcms.lottery.commons.persist.entity.VoucherUserExtendPO;
import com.xhcms.lottery.commons.persist.entity.VoucherUserPO;

/**
 * 用户优惠卷dao
 * @author Wang Lei
 *
 */
public interface VoucherUserDao extends Dao<VoucherUserExtendPO>{

	/**
	 * 查询用户所有有效的优惠卷
	 * @param paging
	 * @param userId
	 * @return
	 */
	List<VoucherUserPO> listValidByUserid(Paging paging, Long userId);
	List<VoucherUserPO> listValidRechargeByUserid(Long userId);


	/**
	 * 查询用户所有已使用的优惠卷
	 * @param paging
	 * @param userId
	 * @return
	 */
	List<VoucherUserPO> listUsedByUserid(Paging paging, Long userId);

	/**
	 * 查询用户所有已过期的优惠卷
	 * @param paging
	 * @param userId
	 * @return
	 */
	List<VoucherUserPO> listExpireByUserid(Paging paging, Long userId);

	VoucherUserPO getCanUseByRechargeId(Long rechargeId);

	List<String> listUserVoucherByGrant(Long granttypeId, Long userId);

	List<VoucherUserExtendPO> list(Paging paging, String voucherUserViewStatus,
			String username, Long id, String voucherId, Long granttypeId);
	int getVoucherCountByGrantTypeIdsAndTime(Set<Long> grantTypeIds,
			Long userId, Date startTime, Date endTime);
	List<VoucherUserExtendPO> list(Paging paging, String voucherUserViewStatus,
			VoucherUserExtendPO voucher);
	List<VoucherTypePO> loadAllVoucherType();
	
}
