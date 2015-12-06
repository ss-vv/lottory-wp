package com.xhcms.lottery.commons.persist.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.xhcms.lottery.commons.persist.dao.GoldSchemeDao;
import com.xhcms.lottery.commons.persist.entity.GoldSchemePO;
import com.xhcms.lottery.commons.persist.service.GoldSchemeService;

public class GoldSchemeServiceImpl implements GoldSchemeService {
	@Autowired
	private GoldSchemeDao  goldSchemeDao;
	
	@Override
	public GoldSchemePO findGoldSchemeByGoldSchemeId(String goldSchemeId) {
		return goldSchemeDao.findGoldSchemeByGoldSchemeId(goldSchemeId);
	}

	@Override
	public GoldSchemePO findGoldSchemeById(String id) {
		return goldSchemeDao.findGoldSchemeById(id);
	}

	@Override
	public long addGoldScheme(GoldSchemePO goldScheme) {
		return goldSchemeDao.addGoldScheme(goldScheme);
	}

	@Override
	public void deteleGoldSchemeByGoldSchemeId(String goldSchemeId) {
		goldSchemeDao.deteleGoldSchemeByGoldSchemeId(goldSchemeId);
	}

	@Override
	public void deleteGoldSchemeById(String id) {
		goldSchemeDao.deleteGoldSchemeById(id);
	}

	@Override
	public void updateGoldScheme(GoldSchemePO goldScheme) {
		goldSchemeDao.updateGoldScheme(goldScheme);
	}

	@Override
	public List<GoldSchemePO> findAllGoldScheme() {
		return goldSchemeDao.findAllGoldScheme();
	}

}
