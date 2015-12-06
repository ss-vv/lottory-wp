package com.xhcms.lottery.commons.persist.dao;

import java.util.List;

import com.xhcms.commons.persist.Dao;
import com.xhcms.lottery.commons.data.DaVGroup;
import com.xhcms.lottery.commons.persist.entity.DaVGroupPO;

public interface DaVGroupDao extends Dao<DaVGroupPO>{

	List<DaVGroupPO> listGroupPO(Integer firstResult, Integer pageMaxResult);
	
	List<DaVGroupPO> findDaVGroupPOByGroupId(String groupid);

	List<String> listAllVidsByDistinct();
}
