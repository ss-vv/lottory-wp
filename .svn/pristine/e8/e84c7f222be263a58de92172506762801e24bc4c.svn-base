package com.unison.lottery.weibo.data.service.store.persist.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.unison.lottery.weibo.data.service.store.persist.dao.FBTeamDao;
import com.unison.lottery.weibo.data.service.store.persist.entity.FBTeamPO;
import com.unison.lottery.weibo.data.service.store.persist.service.FBTeamService;

@Service
public class FBTeamServiceImpl implements FBTeamService {

	@Autowired
	private FBTeamDao fbTeamDao;
	
	@Transactional
	@Override
	public FBTeamPO findFbTeam(long teamId) {
		return fbTeamDao.findByTeamId(teamId);
	}

}
