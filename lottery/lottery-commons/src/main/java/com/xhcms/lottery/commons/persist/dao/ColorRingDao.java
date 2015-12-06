package com.xhcms.lottery.commons.persist.dao;

import com.xhcms.commons.persist.Dao;
import com.xhcms.lottery.commons.persist.entity.PmColorRingGrantPO;

/**
 * 订购彩铃dao
 * @author zhuyongli
 */
public interface ColorRingDao extends Dao<PmColorRingGrantPO> {
	
	/**
	 * 根据交易流水号取得PO
	 * @param tradeNo
	 * @return
	 */
	PmColorRingGrantPO findByTradeNo(String tradeNo);
	
}
