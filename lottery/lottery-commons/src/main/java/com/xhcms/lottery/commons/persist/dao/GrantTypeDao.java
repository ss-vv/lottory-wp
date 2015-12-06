package com.xhcms.lottery.commons.persist.dao;

import java.util.List;

import com.xhcms.commons.persist.Dao;
import com.xhcms.lottery.commons.persist.entity.GrantTypePO;
/**
 * 赠款类型
 * @author Wang Lei
 *
 */
public interface GrantTypeDao extends Dao<GrantTypePO> {
	
	List<GrantTypePO> findGrantTypeListByLotteryId(String lotteryId);
	
}
