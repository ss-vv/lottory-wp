package com.davcai.lottery.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.davcai.lottery.service.MatchNameService;
import com.unison.lottery.weibo.data.service.store.persist.dao.BBLeagueDao;
import com.unison.lottery.weibo.data.service.store.persist.dao.BBTeamDao;
import com.unison.lottery.weibo.data.service.store.persist.entity.BBLeaguePO;
import com.unison.lottery.weibo.data.service.store.persist.entity.BBTeamPO;

@Service
public class MatchNameServiceImpl implements MatchNameService {
	
	@Autowired
	private BBLeagueDao bbLeagueDao;
	@Autowired
	private BBTeamDao bbTeamDao;
	
	private static Map<String, String> teamMap = new HashMap<String, String>();
	
	@Override
	@Transactional
	public String getTeamShortNameByLeagueIdAndTeamName(String leagueName, String teamName) {
		if(null != teamMap.get(leagueName+teamName)){
			return teamMap.get(leagueName+teamName);
		}
		BBLeaguePO b = bbLeagueDao.findByShortName(leagueName);
		BBTeamPO bbTeamPO = null;
		if(null != b){
			long leagueId = b.getLeagueId();
			bbTeamPO = bbTeamDao.findByLeagueIdAndChineseName(leagueId, teamName);
		} else {//联赛名称没有匹配成功，则直接使用队名查询
			bbTeamPO = bbTeamDao.findByChineseName(teamName);
		}
		if(null == bbTeamPO){
			teamMap.put(leagueName+teamName,teamName);
			return teamName;
		} else {
			teamMap.put(leagueName+teamName,bbTeamPO.getShortName());
			return bbTeamPO.getShortName();
		}
	}
}
