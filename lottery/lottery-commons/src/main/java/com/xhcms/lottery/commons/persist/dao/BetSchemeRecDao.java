package com.xhcms.lottery.commons.persist.dao;

import java.util.Collection;
import java.util.List;

import com.xhcms.commons.persist.Dao;
import com.xhcms.lottery.commons.persist.entity.BetSchemeRecPO;

/**
 * @desc 推荐方案
 * @author lei.li@unison.net.cn
 * @createTime 2014-3-28
 * @version 1.0
 */
public interface BetSchemeRecDao extends Dao<BetSchemeRecPO> {

	List<BetSchemeRecPO> findBySchemaIds(Collection<Long> ids);

	String getPlayType(Long id);
	List<BetSchemeRecPO> findBySchemaId(Long id);
	String getSponsorById(Long id);
	
}