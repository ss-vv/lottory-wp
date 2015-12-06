package com.unison.lottery.pm.persist;

import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Set;
import com.unison.lottery.pm.data.PromotionWinResult;
import com.unison.lottery.pm.entity.WinGrantPO;
import com.xhcms.commons.lang.Paging;
import com.xhcms.commons.persist.Dao;

/**
 * 
 * @author Wang Lei
 *
 */
public interface WinGrantDao extends Dao<WinGrantPO> {
	
	/**
	 * 奖上奖排行榜
	 * @param number  返回前几名
	 * @param promotionId  活动id
	 * @return
	 */
	List<PromotionWinResult> findPromotionWinTOP(int number,Long promotionId);
	
	/**
	 * 查询符合条件的活动记录
	 * @param paging
	 * @param status
	 * @param from
	 * @param to
	 * @param winGrant
	 * @param promotionidSet
	 * @return
	 */
	List<WinGrantPO> findWinGrantList(Paging paging, int status, Date from, Date to,WinGrantPO winGrant,Set<Long> promotionidSet);
	
	/**
	 * 根据id集合,查询未提交的活动记录列表
	 * @param ids
	 * @return
	 */
	List<WinGrantPO> find(Collection<Long> ids);

	/**
	 * 使用活动id和状态，查找活动记录id集合
	 * @param pMid
	 * @param status
	 * @return
	 */
	List<Long> findIdsByPMIDandStatus(Long pMid, Integer status);
}
