package com.unison.lottery.weibo.data.service.store.persist.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.unison.lottery.weibo.data.service.store.persist.dao.FBCupScoreDao;
import com.unison.lottery.weibo.data.service.store.persist.dao.FBSubCupDao;
import com.unison.lottery.weibo.data.service.store.persist.entity.FBSubCupPO;
import com.unison.lottery.weibo.data.service.store.persist.service.FBCupScoreService;

@Service
public class FBSubCupServiceImpl implements FBCupScoreService {

	@Autowired
	private FBSubCupDao fbSubCupDao;
	@Autowired
	private FBCupScoreDao cupScoreDao;

	@Override
	public List<Object[]> findCupScoreBy(long leagueId, String season) {
		FBSubCupPO fbSubCupPO = fbSubCupDao.findBy(leagueId, season);
		if (null != fbSubCupPO && fbSubCupPO.isGroupingMatch() == true) {
			return cupScoreDao.findCupScoreBy(fbSubCupPO.getId());
		}
		return null;
	}
}