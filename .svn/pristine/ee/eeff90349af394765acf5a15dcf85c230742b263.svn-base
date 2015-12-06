package com.xhcms.lottery.dc.persist.dao;

import java.util.Collection;
import java.util.Date;
import java.util.List;

import com.xhcms.commons.persist.Dao;
import com.xhcms.lottery.commons.persist.entity.BBMatchPO;
import com.xhcms.lottery.dc.data.Bonus;
import com.xhcms.lottery.dc.data.LCOdds;
import com.xhcms.lottery.dc.data.LCResult;
import com.xhcms.lottery.dc.data.Match;
import com.xhcms.lottery.dc.data.Score;

/**
 * @author langhsu
 *
 */
public interface BBMatchDao extends Dao<BBMatchPO> {
	 /**
     * 检查赛事是否存在
     * @param id
     * @return 取得已经存在的赛事ID
     */
    List<Long> filterExist(Collection<Long> indexs);

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
    void batchUpdateMatchResult(List<LCResult> data);

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
     * 批量更新赛事玩法数据
     * @param data
     */
    void batchUpdateMatchPlay(Collection<LCOdds> data);

    /**
     * 批量添加赛事玩法数据
     * @param data
     */
    void batchInsertMatchPlay(Collection<LCOdds> data);
    
    /**
     * 批量更新赛事开奖结果及奖金
     * @param data
     */
    void batchUpdateResultBonus(List<Bonus> bs);
    
    /**
     * 批量修改让分
     * @param scores
     */
    void batchUpdateScore(List<Score> scores);
    
    /**
     * 批量插入让分
     * @param scores
     */
    void batchInsertScore(List<Score> scores);
    
    List<BBMatchPO> findOnSale(Date from);
}
