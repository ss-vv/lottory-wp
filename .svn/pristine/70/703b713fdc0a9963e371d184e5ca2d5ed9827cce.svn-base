package com.xhcms.lottery.commons.persist.dao;

import java.util.List;

import com.xhcms.lottery.commons.persist.entity.GoldSchemePO;

public interface GoldSchemeDao {
	/**
	 * 通过goldSchemeId查询
	 * @return
	 */
	GoldSchemePO findGoldSchemeByGoldSchemeId(String goldSchemeId);
	/**
	 * 通过Id查询
	 * @return
	 */
	GoldSchemePO findGoldSchemeById(String id);
	
	
	long addGoldScheme(GoldSchemePO goldScheme);
	
	/**
	 * 通过goldSchemeId删除
	 * @param dav_id
	 */
	void deteleGoldSchemeByGoldSchemeId(String goldSchemeId);
	/**
	 * 通过id删除
	 * @param id
	 */
	void deleteGoldSchemeById(String id);
	
	
	void updateGoldScheme(GoldSchemePO goldScheme);
	
	List<GoldSchemePO> findAllGoldScheme();
	
	
}
