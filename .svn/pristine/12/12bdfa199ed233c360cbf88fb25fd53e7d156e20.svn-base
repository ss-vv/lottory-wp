package com.unison.lottery.weibo.data.service.store.persist.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.unison.lottery.weibo.data.service.store.persist.dao.FBAsiaOddsDao;
import com.unison.lottery.weibo.data.service.store.persist.dao.QTMatchDao;
import com.unison.lottery.weibo.data.service.store.persist.entity.FBAsiaOddsPO;
import com.unison.lottery.weibo.data.service.store.persist.entity.QTMatchPO;
import com.unison.lottery.weibo.data.service.store.persist.service.MatchInfoService;
import com.unison.lottery.weibo.data.vo.FightHistoryVO;
import com.unison.lottery.weibo.data.vo.FutureMatchVO;
import com.unison.lottery.weibo.data.vo.RecentMatchVO;

@Service
public class MatchInfoServiceImpl implements MatchInfoService{
	private Logger logger = LoggerFactory.getLogger(getClass());
	@Autowired
	private QTMatchDao qtMatchDao;
	@Autowired
	private FBAsiaOddsDao fbAsiaOddsDao;
	@Override
	@Transactional
	public List<FightHistoryVO> getFightHistoryMatchs(long homeTeamId,long guestTeamId, int size) {
		if(homeTeamId <= 0 && guestTeamId <= 0) {
			return new ArrayList<FightHistoryVO>();
		}
		List<QTMatchPO> qtMatchPOs = qtMatchDao.findQTMatchPO(homeTeamId, guestTeamId, null, new Date(), size);
		List<FightHistoryVO> fightHistoryVOs = new ArrayList<FightHistoryVO>();
		for (QTMatchPO qtMatchPO : qtMatchPOs) {
			FightHistoryVO f = new FightHistoryVO();
			f.setId(qtMatchPO.getId());
			f.setMatchName(qtMatchPO.getLeagueShortName());
			f.setMatchNameColor(qtMatchPO.getColor());
			f.setMatchDate(qtMatchPO.getMatchTime());
			f.setHomeTeam(qtMatchPO.getHomeTeamName());
			f.setGuestTeam(qtMatchPO.getGuestTeamName());
			f.setScore(qtMatchPO.getScore());
			
			List<FBAsiaOddsPO> fbAsiaOddsPOs = fbAsiaOddsDao.findByMatchId(qtMatchPO.getId());
			if(fbAsiaOddsPOs.size() < 1){
				logger.warn("找不到matchId={} 的亚赔记录！",qtMatchPO.getId());
				f.setPankou(0.25F);
				//continue; //跳过该场赛事处理
			} else {
				f.setPankou(fbAsiaOddsPOs.get(0).getCurHandicap().floatValue());
			}
			
			String[] ss = {};
			if(null != qtMatchPO.getScore()) {
				ss = qtMatchPO.getScore().split(":");
			}
			if(ss.length < 2){
				logger.warn("比分：'{}' ,格式不正确！",qtMatchPO.getScore());
			} else {
				int a = Integer.parseInt(ss[0]);
				int b = Integer.parseInt(ss[1]);
				f.setMatchResult(
						a > b ? "胜":
						a == b ? "平":"负");
				f.setPanlu(a + f.getPankou() > b ? true:false);
				f.setBigSmall(a + b > 2.5 ? "大":"小");
			}
			fightHistoryVOs.add(f);
		}		
		return fightHistoryVOs;
	}

	@Override
	@Transactional
	public List<RecentMatchVO> getRecentMatchs(long teamId, int size) {
		if(teamId <= 0) {
			return new ArrayList<RecentMatchVO>();
		}
		List<QTMatchPO> qtMatchPOs = qtMatchDao.findQTMatchPO(teamId, null, new Date(), size);
		List<RecentMatchVO> recentMatchVOs = new ArrayList<RecentMatchVO>();
		for (QTMatchPO qtMatchPO : qtMatchPOs) {
			RecentMatchVO r = new RecentMatchVO();
			r.setId(qtMatchPO.getId());
			r.setMatchName(qtMatchPO.getLeagueShortName());
			r.setMatchNameColor(qtMatchPO.getColor());
			r.setMatchDate(qtMatchPO.getMatchTime());
			r.setHomeTeam(qtMatchPO.getHomeTeamName());
			r.setGuestTeam(qtMatchPO.getGuestTeamName());
			r.setScore(qtMatchPO.getScore());
			
			List<FBAsiaOddsPO> fbAsiaOddsPOs = fbAsiaOddsDao.findByMatchId(qtMatchPO.getId());
			if(fbAsiaOddsPOs.size() < 1){
				logger.warn("找不到matchId={} 的亚赔记录！",qtMatchPO.getId());
				r.setPankou(0.25F);
				//continue; //跳过该场赛事处理
			} else {
				r.setPankou(fbAsiaOddsPOs.get(0).getCurHandicap().floatValue());
			}
			
			String[] ss = qtMatchPO.getScore().split(":");
			if(ss.length < 2){
				logger.warn("比分：'{}' ,格式不正确！",qtMatchPO.getScore());
			} else {
				int a = Integer.parseInt(ss[0]);
				int b = Integer.parseInt(ss[1]);
				r.setMatchResult(
						a > b ? "胜":
						a == b ? "平":"负");
				r.setPanlu(a + r.getPankou() > b ? true:false);
				r.setBigSmall(a + b > 2.5 ? "大":"小");
			}
			recentMatchVOs.add(r);
		}		
		return recentMatchVOs;
	}

	@Override
	@Transactional
	public List<FutureMatchVO> getFutureMatchs(long teamId, int size) {
		if(teamId <= 0) {
			return new ArrayList<FutureMatchVO>();
		}
		List<FutureMatchVO> futureMatchVOs = new ArrayList<FutureMatchVO>();
		List<QTMatchPO> qtMatchPOs = qtMatchDao.findQTMatchPO(teamId, new Date(), null, size);
		for (QTMatchPO qtMatchPO : qtMatchPOs) {
			FutureMatchVO f = new FutureMatchVO();
			f.setMatchName(qtMatchPO.getLeagueShortName());
			f.setHomeTeam(qtMatchPO.getHomeTeamName());
			f.setGuestTeam(qtMatchPO.getGuestTeamName());
			f.setMatchDate(qtMatchPO.getMatchTime());
			futureMatchVOs.add(f);
		}
		return futureMatchVOs;
	}

}
