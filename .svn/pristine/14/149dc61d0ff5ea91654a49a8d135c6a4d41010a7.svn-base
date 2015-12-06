package com.xhcms.lottery.commons.persist.dao;

import java.util.List;

import com.xhcms.commons.persist.Dao;
import com.xhcms.lottery.commons.persist.entity.PlayPO;

public interface PlayDao extends Dao<PlayPO> {
	
	List<PlayPO> findPlay();

    List<PlayPO> find(String lotteryId);

    /**
     * 根据玩法查询过关方式
     * 
     * @param id
     * @return
     */
    PlayPO getWithPassType(String id);
    
    /**
     * 查询定制合买跟单玩法
     * @return
     */
    List<PlayPO> findCustomMadePlay(String lotteryId);

}
