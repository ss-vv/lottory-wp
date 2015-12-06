package com.xhcms.lottery.dc.persist.dao;

import java.util.List;
import java.util.Map;
import com.xhcms.commons.persist.Dao;
import com.xhcms.lottery.commons.persist.entity.BJDCMatchPlayPO;
import com.xhcms.lottery.dc.data.BDMatch;
import com.xhcms.lottery.dc.data.BDResult;

public interface BJDCMatchPlayDao extends Dao<BJDCMatchPlayPO> {
    //获取让球数
	Map<String,BDMatch> getBDMatchMap(List<BDResult> rs,String typeId);
}
