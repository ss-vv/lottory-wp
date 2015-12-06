package com.xhcms.lottery.commons.persist.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import com.xhcms.lottery.commons.lang.CGJConstant;
import com.xhcms.lottery.commons.persist.dao.BetMatchDao;
import com.xhcms.lottery.commons.persist.dao.CGJIssueInfoDao;
import com.xhcms.lottery.commons.persist.dao.CGJTeamDao;
import com.xhcms.lottery.commons.persist.entity.BetMatchPO;
import com.xhcms.lottery.commons.persist.entity.CGJIssueInfoPO;
import com.xhcms.lottery.commons.persist.entity.CGJTeamPO;
import com.xhcms.lottery.commons.persist.service.CGJTeamService;
import com.xhcms.lottery.dc.data.CGJTeam;
import com.xhcms.lottery.dc.data.Match;
import com.xhcms.lottery.lang.EntityStatus;
import com.xhcms.lottery.lang.PlayType;

public class CGJTeamServiceImpl implements CGJTeamService {

	private Logger log = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private CGJTeamDao cgjTeamDao;
	
	@Autowired
	private BetMatchDao betMatchDao;
	
	@Autowired
	private CGJIssueInfoDao cgjIssueInfoDao;
	
	@Transactional
	@Override
	public void updateCGJTeam(List<Match> data, String year, String playType) {
		resetCgjTeamCurrentSeason(year, playType);
		if(null != data && data.size() > 0) {
			Date updateTime = new Date();
			for(Match match : data) {
				CGJTeam cgjTeam = (CGJTeam) match;
				CGJTeamPO po = cgjTeamDao.find(playType, year, 
						cgjTeam.getTeamName(), cgjTeam.getName());
				if(null != po && po.getId() > 0) {
					po.setTeamId(cgjTeam.getMatchId());
					
					po.setStatus(returnSaleStatus(cgjTeam.getOfftime(), 
							cgjTeam.getStatus()));
					
					po.setOdds(cgjTeam.getOdds());
					po.setOfftime(cgjTeam.getOfftime());
					po.setPlayingTime(cgjTeam.getPlayingTime());
					po.setUpdateTime(updateTime);
					po.setCurrentSeason(CGJConstant.YES_CURRENT_SEASON);
				}
			}
		} else {
			log.info("获取到的猜冠军队伍信息为空,无法进行更新操作.");
		}
	}

	/**
	 * 根据截止投注时间返回销售状态
	 * @param offtime
	 * @return
	 */
	protected int returnSaleStatus(Date offtime, int status) {
		int saleStatus = 1;
		if(status == 0) {
        	saleStatus = EntityStatus.MATCH_ON_SALE;
        } else if(offtime.compareTo(new Date()) < 1){
			saleStatus = EntityStatus.MATCH_STOP_SELLING;
        } else if(status == 3) {
        	saleStatus = EntityStatus.MATCH_CANCEL;
        } else if(status == 4) {
        	saleStatus = EntityStatus.MATCH_OVER;	
        }
		
		return saleStatus;
	}
	
	@Transactional
	@Override
	public void resetCgjTeamCurrentSeason(String year, String playType) {
		cgjTeamDao.updateCgjTeamCurrentSeason(year, playType);
	}

	@Transactional(readOnly=true)
	@Override
	public List<CGJTeam> listTeamsByCode(List<String> code, String playId) {
		List<Long> teamIdList = new ArrayList<Long>();
		if(code != null && code.size() > 0) {
			for(String option : code) {
				teamIdList.add(Long.valueOf(option));
			}
		}
		List<CGJTeamPO> cgjTeamList = cgjTeamDao.findByTeamIdSet(teamIdList, playId);
		
		List<CGJTeam> matchs = new ArrayList<CGJTeam>();
		for(CGJTeamPO teamPO : cgjTeamList) {
			CGJTeam cgjTeam = new CGJTeam();
			BeanUtils.copyProperties(teamPO, cgjTeam);
			cgjTeam.setMatchId(teamPO.getId());
			matchs.add(cgjTeam);
		}
		return matchs;
	}

	@Transactional(readOnly=true)
	@Override
	public Map<String, List<CGJTeam>> listTeamsCurrentSeason(String playId) {
		List<CGJTeamPO> cgjTeamList = cgjTeamDao.listTeamsCurrentSeason(playId);
		Map<String, List<CGJTeam>> map = new HashMap<String, List<CGJTeam>>();
		if(cgjTeamList != null && cgjTeamList.size() > 0) {
			for(CGJTeamPO po : cgjTeamList) {
				CGJTeam cgjTeam = new CGJTeam();
				BeanUtils.copyProperties(po, cgjTeam);
				cgjTeam.setMatchId(po.getId());
				String groupName = po.getGroupName();
				if(null == map.get(groupName)) {
					List<CGJTeam> list = new ArrayList<CGJTeam>();
					list.add(cgjTeam);
					map.put(groupName, list);
				} else {
					map.get(groupName).add(cgjTeam);
				}
			}
		}
		return map;
	}

	@Transactional(readOnly=true)
	@Override
	public List<CGJTeam> listTeamsBySchemeId(long schemeId) {
		List<BetMatchPO> betMatchList = betMatchDao.findBySchemeId(schemeId);
		List<CGJTeam> matchs = new ArrayList<CGJTeam>();
		if(betMatchList.size() > 0){
			BetMatchPO betMatchPO = betMatchList.get(0);
			List<String> teamIdList = Arrays.asList(betMatchPO.getCode().split(","));
			List<String> oddList = Arrays.asList(betMatchPO.getOdds().split(","));
			if(null != teamIdList && teamIdList.size() > 0
					&& teamIdList.size() == oddList.size()) {
				List<Long> teamIdSet = new ArrayList<Long>();
				for(String teamId : teamIdList) {
					teamIdSet.add(Long.valueOf(teamId));
				}
				
				List<CGJTeamPO> cgjTeamList = cgjTeamDao.findByTeamIdSet(teamIdSet, betMatchPO.getPlayId());
				if(null != cgjTeamList && cgjTeamList.size() > 0) {
					String win = getWinResult(betMatchPO.getPlayId(), ""+betMatchPO.getMatchId());
					
					for(CGJTeamPO teamPO : cgjTeamList) {
						for(int i=0; i<teamIdSet.size(); i++) {
							if(teamIdSet.get(i) == teamPO.getTeamId()) {
								teamPO.setOdds(new BigDecimal(oddList.get(i)));
								break;
							}
						}
						CGJTeam cgjTeam = new CGJTeam();
						cgjTeam.setMatchId(teamPO.getId());
						BeanUtils.copyProperties(teamPO, cgjTeam);
						cgjTeam.setResult(win);
						matchs.add(cgjTeam);
					}
				}
			}
		}
		return matchs;
	}
	
	//查询对应赛季，玩法的赛果
	protected String getWinResult(String playId, String season) {
		String win = "";
		if(PlayType.JCSJBGJ.getShortPlayStr().equals(playId)) {
			CGJIssueInfoPO cgjIssue = cgjIssueInfoDao.find(playId, season);
			if(null != cgjIssue) {
				CGJTeamPO winResult = cgjTeamDao.findByTeamId(cgjIssue.getResult(), playId);
				if(null != winResult) {
					win = winResult.getTeamName();
				}
			}
		}
		return win;
	}
}