package com.xhcms.lottery.commons.persist.service;

import java.math.BigDecimal;
import java.util.Date;

import com.xhcms.commons.lang.Paging;

/**
 * 赠款
 * 
 * @author langhsu
 */
public interface GrantService {
	void listGrant(Paging paging, long userId, Date begin, Date end);

	/**
	 * 自动赠款
	 * @param userId
	 * @param amount
	 * @param granttypeId
	 * @param note
	 */
	void autoGrant(Long userId, BigDecimal amount, Long granttypeId, String note);
}
