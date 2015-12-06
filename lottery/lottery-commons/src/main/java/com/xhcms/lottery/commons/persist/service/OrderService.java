package com.xhcms.lottery.commons.persist.service;

import java.util.Date;

import com.xhcms.commons.lang.Paging;
import com.xhcms.lottery.commons.data.Order;
import com.xhcms.lottery.commons.persist.entity.OrderPO;

public interface OrderService {

	/**
	 * 查询交易记录
	 * @param paging
	 * @param username 用户ID
	 * @param type 操作类型
	 * @param from 起始日期
	 * @param to 终止日期
	 */
	void listOrder(Paging paging, String username, int type, Date from, Date to);

	Order getOrder(Long id);
	
	OrderPO getByRelated(int type, Long relatedId,Long userId);
}
