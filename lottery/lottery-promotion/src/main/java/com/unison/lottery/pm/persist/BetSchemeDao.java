
package com.unison.lottery.pm.persist;

import java.math.BigInteger;
import java.util.Date;
import java.util.List;

import com.xhcms.commons.persist.Dao;
import com.xhcms.lottery.commons.persist.entity.BetSchemePO;

/**
 * @author yongli zhu
 *
 */
public interface BetSchemeDao extends Dao<BetSchemePO> {
	/**
	 * 查询出所有的晒单方案
	 * @return
	 */
	List<BigInteger> findByIsShow(String month);
	
	/**
	 * 更新跟单总金额
	 * @param showId
	 */
	void updateAmount(Long showId);

	/**
	 * 返回竞彩足球一段时间内出票成功的不含合买的用户和金额
	 * @param day
	 * @return
	 */
	List<Object[]> findUserJCZQBetByDate(Date day);
}
