package com.xhcms.lottery.commons.persist.dao;

import java.util.Date;
import java.util.List;

import com.xhcms.commons.lang.Paging;
import com.xhcms.commons.persist.Dao;
import com.xhcms.lottery.commons.persist.entity.PMRechargeRedeemedPO;

/**
 * @author zhuyongli
 * 
 */
public interface PMRechargeRedeemedDao extends Dao<PMRechargeRedeemedPO> {
	/**
	 * admin 后台查询充值返还彩金
	 * @param paging
	 * @param from
	 * @param to
	 * @param status
	 * @param username
	 * @param operatorName
	 * @return
	 */
	List<PMRechargeRedeemedPO> find(Paging paging, Date from, Date to,
			int status, String username, String operatorName);
	
	/**
	 * 根据id查询充值返还彩金
	 * @param ids
	 * @return
	 */
	List<PMRechargeRedeemedPO> findByIds(List<Long> ids);
	
	/**
	 * 根据条件查询充值返还彩金
	 * @param userId
	 * @param promotionId
	 * @param idNumber
	 * @param mobile
	 * @param email
	 * @return
	 */
	PMRechargeRedeemedPO find(Long userId, Long promotionId, String idNumber, String mobile, String email);
}
