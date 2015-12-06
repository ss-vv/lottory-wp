package com.unison.lottery.weibo.common.service.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.unison.lottery.weibo.common.cache.SchemeCache;
import com.unison.lottery.weibo.common.nosql.impl.Keys;
import com.unison.lottery.weibo.common.service.SchemeService;
import com.xhcms.lottery.commons.data.BetScheme;
import com.xhcms.lottery.commons.persist.dao.BetSchemeDao;
import com.xhcms.lottery.commons.persist.entity.BetSchemePO;
import com.xhcms.lottery.commons.persist.service.Scheme2ViewService;

@Service
public class SchemeServiceImpl implements SchemeService {

	@Autowired(required = false)
	private BetSchemeDao betSchemeDao;
	@Autowired
	private SchemeCache schemeCache;
	@Autowired
	private com.xhcms.lottery.commons.persist.service.BetSchemeService bsService;

	
	@Autowired
	private Scheme2ViewService scheme2ViewService;

	@Transactional
	@Override
	public BetSchemePO getScheme(long schemeId) {
		Collection<Long> ids = new ArrayList<Long>();
		ids.add(schemeId);
		List<BetSchemePO> betSchemeList = betSchemeDao.find(ids);
		BetSchemePO scheme = null;
		if (null != betSchemeList && betSchemeList.size() > 0) {
			scheme = betSchemeList.get(0);
		}
		return scheme;
	}

	@Transactional
	@Override
	public BetScheme viewSchemeCache(Long schemeId, Long userId, int displayMode) {
		BetScheme betScheme = new BetScheme();
		if (null == schemeId || schemeId <= 0) {
			return betScheme;
		}
		userId = (null == userId) ? 0L : userId;

		String schemeCacheKey = Keys.realSchemeCacheKey(schemeId, displayMode);
		betScheme = schemeCache.getSchemeCache(schemeCacheKey);
		if (!schemeCache.isOpenQueryCache() || null == betScheme
				|| betScheme.getId() <= 0) {
			betScheme = bsService.viewScheme(schemeId, displayMode);
			schemeCache.setSchemeCache(schemeCacheKey, betScheme);
		}
		
		// 保密设置
		if (null != betScheme && betScheme.getId() > 0) {
			bsService.setBetCodeWithPrivacy(betScheme, userId, displayMode);
		}
		//标志为实单
		betScheme.setRealScheme(true);
		
		//转换展现层需要的方案数据
		scheme2ViewService.scheme2View(betScheme, true);
		
		return betScheme;
	}

	@Transactional(readOnly=true)
	@Override
	public boolean updateRealSchemeCache(Long schemeId, int displayMode) {
		BetScheme viewScheme = bsService.viewScheme(schemeId, displayMode);
		String schemeCacheKey = Keys.realSchemeCacheKey(schemeId, displayMode);
		if(null != viewScheme && viewScheme.getId() > 0) {
			schemeCache.updateSchemeCache(schemeCacheKey, viewScheme);
		}
		return true;
	}
}
