package com.xhcms.lottery.dc.persist.dao;

import java.util.Collection;
import java.util.List;

import com.xhcms.commons.persist.Dao;
import com.xhcms.lottery.commons.data.CTFBMatch;
import com.xhcms.lottery.commons.persist.entity.CTFBMatchPO;

/**
 * @author Wang Lei
 */
public interface CTFBMatchDao extends Dao<CTFBMatchPO> {
    /**
     * 检查赛事是否存在
     * @param id
     * @return 取得已经存在的赛事ID
     */
    List<String> filterExist(Collection<String> ids);
    
    /**
     * 批量添加赛事数据
     * @param data
     */
    void batchInsertMatch(Collection<CTFBMatch> data);

    /**
     * 批量更新赛事数据
     * @param data
     */
    void batchUpdateMatch(Collection<CTFBMatch> data);
}
