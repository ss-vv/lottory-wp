
package com.unison.lottery.pm.persist;

import java.util.Collection;
import java.util.Date;
import java.util.List;

import com.unison.lottery.pm.data.CountResult;
import com.unison.lottery.pm.data.RechargeResult;
import com.xhcms.lottery.commons.persist.dao.AccountDao;

/**
 * @author yongli zhu
 *
 */
public interface PMGrantAccountDao extends AccountDao {
	
	public List<Long> getUserListFromRecharge(Collection<Long> ids,Date from,Date to);
	
	/**
	 * 取得指定时间段内首次充值记录
	 * @param from
	 * @param to
	 * @return
	 */
	public List<RechargeResult> getFirstRechargeRecord(Date from, Date to);
	
	public CountResult getRecharge(String ids);
	
	public CountResult getBet(String ids);
}
