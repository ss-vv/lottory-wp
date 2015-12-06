package com.xhcms.lottery.dc.persist.dao;

import java.util.Collection;
import java.util.List;
import com.xhcms.commons.persist.Dao;
import com.xhcms.lottery.commons.persist.entity.BJDCMatchPlayPO;
import com.xhcms.lottery.dc.data.BDMatch;
import com.xhcms.lottery.dc.data.BDOdds;
import com.xhcms.lottery.lang.PlayType;

/**
 * 北京单场赔率dao
 */
public interface BJDCOddsDao extends Dao<BJDCMatchPlayPO> {
	
	/**
	 * 检查是否存在
	 * @param id
	 * @return
	 */
	List<String> filterExist(Collection<String> ids);

	/**
	 * 批量添加赔率数据
	 * @param data
	 */
	void batchInsertOdds(Collection<BDOdds> data);
	
	/**
	 * 批量更新赔率数据
	 * @param data
	 */
	void batchUpdateOdds(Collection<BDOdds> data);
	
	/**
	 * 更新让球数
	 * @param playType 玩法类型 
	 */
	void batchUpdateConcedePonitsByLottery(Collection<BDMatch> data, PlayType playType);
}