/**
 * 
 */
package com.xhcms.lottery.dc.feed.persist.service;

import java.util.Date;

import com.xhcms.commons.lang.Paging;

/**
 * @author Bean.Long
 *
 */
public interface GroupBuySchemeService {
	/**
	 * 查询合买方法
	 * @param username
	 * @param commission 佣金
	 * @param play
	 * @param orderBy
	 * @param asc 是否升序
	 * @param paging
	 */
	void pagingRecommendGroupBuyShcemes(String username, Integer commission,
			String play, String orderBy, boolean asc, Date startTime, 
			Date endTime, Paging paging);

	void pagingGroupBuyShcemesByLottery(String lottery, String username,
			Integer commission, String play, String orderBy, Boolean asc, Date startTime,
			Date endTime, Paging paging);
}
