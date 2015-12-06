package com.xhcms.lottery.dc.persist.dao;

import java.util.Collection;
import java.util.Date;
import java.util.List;

import com.xhcms.commons.persist.Dao;
import com.xhcms.lottery.commons.persist.entity.FBMatchPO;
import com.xhcms.lottery.dc.data.Bonus;
import com.xhcms.lottery.dc.data.Match;
import com.xhcms.lottery.dc.data.ZCOdds;
import com.xhcms.lottery.dc.data.ZCResult;

/**
 * @author xulang
 */
public interface FBMatchDao extends Dao<FBMatchPO> {
    /**
     * 检查赛事是否存在
     * @param id
     * @return 取得已经存在的赛事ID
     */
    List<Long> filterExist(Collection<Long> ids);

    /**
     * 检查赛事玩法是否存在
     * @param ids
     * @return
     */
    List<String> filterExistMatchPlay(Collection<String> ids);

    /**
     * 批量修改赛果
     * @param data
     */
    void batchUpdateMatchResult(List<ZCResult> data);

    /**
     * 批量添加赛事数据
     * @param data
     */
    void batchInsertMatch(Collection<Match> data);

    /**
     * 批量更新赛事数据
     * @param data
     */
    void batchUpdateMatch(Collection<Match> data);
    
    /**
     * 批量更新赛事数据, 这个方法同时会更新  让球值
     * @param data
     */
    //void batchUpdateMatchConcede(Collection<ZCOdds> data);
    
    /**
     * 批量更新赛事玩法数据
     * @param data
     */
    void batchUpdateMatchPlay(Collection<ZCOdds> data);

    /**
     * 批量添加赛事玩法数据
     * @param data
     */
    void batchInsertMatchPlay(Collection<ZCOdds> data);

    /**
     * 查询在售赛事
     * @return
     */
    List<FBMatchPO> findOnSale(Date from);
    
    void batchUpdateResultBonus(List<Bonus> bs);
}
