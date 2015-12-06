package com.xhcms.lottery.dc.persist.dao;

import java.util.Collection;
import java.util.Date;
import java.util.List;
import com.xhcms.commons.persist.Dao;
import com.xhcms.lottery.commons.persist.entity.BJDCMatchPO;
import com.xhcms.lottery.dc.data.BDMatch;
import com.xhcms.lottery.dc.data.BDResult;
import com.xhcms.lottery.dc.data.Bonus;

/**
 * 北京单场赛事dao
 */
public interface BJDCMatchDao extends Dao<BJDCMatchPO> {
	/**
	 * 检查赛事是否存在
	 * 
	 * @param id
	 * @return 取得已经存在的赛事ID
	 */
	List<Long> filterExist(Collection<Long> ids);

	/**
	 * 批量添加赛事数据
	 * 
	 * @param data
	 */
	void batchInsertMatch(Collection<BDMatch> data);

	/**
	 * 批量更新赛事数据
	 * 
	 * @param data
	 */
	void batchUpdateMatch(Collection<BDMatch> data);
	
	/**
     * 查询赛事委托时间大于给定时间的赛程
     * @return
     */
    List<BJDCMatchPO> findMatchsGreatThanEntrust(Date from);
	
    /**
     * 批量修改赛果
     * @param data
     */
    void batchUpdateMatchResult(List<BDResult> data);
    
    void batchUpdateResultBonus(List<Bonus> bs);
    /**
     * 更新有赛果无赔率的赛事
     * @param bs
     */
    void batchUpdateNoSpResultBonus(List<Bonus> bs);
    List<String> getIssueNumber(String playId);
}