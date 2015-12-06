package com.unison.lottery.weibo.data.service.store.persist.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.unison.lottery.weibo.data.service.store.data.MatchResultStats;
import com.unison.lottery.weibo.data.service.store.data.MatchStatus;
import com.unison.lottery.weibo.data.service.store.persist.dao.BBMatchInfoDao;
import com.unison.lottery.weibo.data.service.store.persist.dao.BBOddsBigSmallDao;
import com.unison.lottery.weibo.data.service.store.persist.dao.BBTeamDao;
import com.unison.lottery.weibo.data.service.store.persist.dao.QTMatchDao;
import com.unison.lottery.weibo.data.service.store.persist.entity.BBMatchInfoPO;
import com.unison.lottery.weibo.data.service.store.persist.entity.BBOddsBigSmallPO;
import com.unison.lottery.weibo.data.service.store.persist.entity.BBTeamPO;
import com.unison.lottery.weibo.data.service.store.persist.service.BBMatchInfoService;
import com.unison.lottery.weibo.data.vo.FightHistoryVO;
import com.unison.lottery.weibo.data.vo.FutureMatchVO;
import com.unison.lottery.weibo.data.vo.RecentMatchVO;
import com.unison.lottery.weibo.lang.MatchResultNameEnum;
import com.xhcms.lottery.commons.persist.service.MatchService;
import com.xhcms.lottery.lang.Constants;

@Service
public class BBMatchInfoServiceImpl implements BBMatchInfoService {

	private Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private BBMatchInfoDao bbMatchInfoDao;
	
	@Autowired
	private BBOddsBigSmallDao bbOddsBigSmallDao;
	
	@Autowired
	private QTMatchDao qtMatchDao;
	
	@Autowired
	private MatchService matchService;
	
	@Autowired
	private BBTeamDao bbTeamDao;
	
	@Transactional
	@Override
	public List<FightHistoryVO> getFightHistoryMatchs(long homeTeamId,
			long guestTeamId, Date from, Date to, int size) {
		List<FightHistoryVO> list = new ArrayList<FightHistoryVO>();
		
		if(homeTeamId <= 0 || guestTeamId <= 0) {
			return list;
		}
		List<BBMatchInfoPO> bbMatchList = bbMatchInfoDao.findBBMatchs(homeTeamId, 
				guestTeamId, from, to, size);
		for (BBMatchInfoPO bbMatchPO : bbMatchList) {
			FightHistoryVO f = new FightHistoryVO();
			f.setId(bbMatchPO.getId());
			f.setMatchName(bbMatchPO.getName());
			f.setMatchNameColor(bbMatchPO.getColor());
			f.setMatchDate(bbMatchPO.getMatchTime());
			f.setHomeTeam(bbMatchPO.getHomeTeam());
			f.setGuestTeam(bbMatchPO.getGuestTeam());
			f.setScore(bbMatchPO.getHomeScore() + ":" + bbMatchPO.getGuestScore());
			f.setMatchResult(matchResult(bbMatchPO.getHomeScore(), bbMatchPO.getGuestScore()).getName());
			
			f.setPankou(findJLPanKou(bbMatchPO.getId()));
			f.setPanlu(bbMatchPO.getHomeScore() + f.getPankou() > bbMatchPO.getGuestScore() ? true:false);
			
			//计算大小
			f.setBigSmall(calBigOrSmall(bbMatchPO));
			list.add(f);
		}
		return list;
	}
	
	private MatchResultNameEnum matchResult(int score1, int score2) {
		return score1 > score2 ? MatchResultNameEnum.SHENG : 
			score1 == score2 ? MatchResultNameEnum.PING : 
			MatchResultNameEnum.FU;
	}
	
	/**
	 * 计算篮球比赛大小分结果，用总分和总分盘（大V彩的预设分值）做比较.
	 * @return
	 */
	protected String calBigOrSmall(BBMatchInfoPO bbMatchPO) {
		int finalScore = bbMatchPO.getHomeScore() + bbMatchPO.getGuestScore();
		long qiuTanMatchId = bbMatchPO.getId();
		String lcMatchId = qtMatchDao.findLCMatchId(qiuTanMatchId);
		//查询预设总分
		Float defaultScore = matchService.findByMatchIdAndPlayId(lcMatchId, Constants.PLAY_09_LC_2);
		if(null == defaultScore) {
			defaultScore = 0F;
			logger.warn("竞彩篮球，球探赛事ID={}, 赛事ID={},未查到预设总分.",
					new Object[]{qiuTanMatchId, lcMatchId});
		}
		BigDecimal bDefaultScore = new BigDecimal(defaultScore);
		BigDecimal bFinalScore = new BigDecimal(finalScore);
		logger.debug("大V彩比赛ID={},总分={},预设总分={}", 
				new Object[]{lcMatchId, bFinalScore.doubleValue(), bDefaultScore.doubleValue()});
		String result = "";
		if(bFinalScore.doubleValue() > 0 && bDefaultScore.doubleValue() > 0) {
			result = bFinalScore.doubleValue() > bDefaultScore.doubleValue() ? "大" : "小";
		}
		return result;
	}

	/**
	 * 获取篮球盘口
	 * @param bbMatchId
	 * @return
	 */
	protected float findJLPanKou(long bbMatchId) {
		BigDecimal pankou = new BigDecimal(0);
		List<BBOddsBigSmallPO> bbOddsBigSmall = bbOddsBigSmallDao.findByMatchId(bbMatchId);
		if(bbOddsBigSmall.size() < 1){
			logger.warn("找不到matchId={} 的亚赔记录！",bbMatchId);
		} else {
			pankou = bbOddsBigSmall.get(0).getRealtimeHandicap();
		}
		return pankou.floatValue();
	}
	
	@Transactional
	@Override
	public List<RecentMatchVO> getRecentMatchs(long teamId, int size) {
		if(teamId <= 0) {
			return new ArrayList<RecentMatchVO>();
		}
		List<BBMatchInfoPO> bbMatchPOs = bbMatchInfoDao.findBBMatchPO(teamId, null, new Date(), size, MatchStatus.COMPLETE);
		List<RecentMatchVO> recentMatchVOs = new ArrayList<RecentMatchVO>();
		for(BBMatchInfoPO bbMatchPO : bbMatchPOs) {
			RecentMatchVO vo = new RecentMatchVO();
			vo.setId(bbMatchPO.getId());
			vo.setMatchName(bbMatchPO.getName());
			vo.setMatchNameColor(bbMatchPO.getColor());
			vo.setMatchDate(bbMatchPO.getMatchTime());
			vo.setHomeTeam(bbMatchPO.getHomeTeam());
			vo.setGuestTeam(bbMatchPO.getGuestTeam());
			vo.setScore(bbMatchPO.getHomeScore() + ":" + bbMatchPO.getGuestScore());
			vo.setMatchResult(matchResult(bbMatchPO.getHomeScore(), bbMatchPO.getGuestScore()).getName());
			
			vo.setPankou(findJLPanKou(bbMatchPO.getId()));
			
			vo.setPanlu(bbMatchPO.getHomeScore() + vo.getPankou() > bbMatchPO.getGuestScore() ? true:false);
			
			//计算大小
			vo.setBigSmall(calBigOrSmall(bbMatchPO));
			recentMatchVOs.add(vo);
		}
		return recentMatchVOs;
	}
	
	@Transactional
	@Override
	public List<FutureMatchVO> getFutureMatchs(long teamId, int size) {
		if(teamId <= 0) {
			return new ArrayList<FutureMatchVO>();
		}
		List<FutureMatchVO> futureMatchVOs = new ArrayList<FutureMatchVO>();
		List<BBMatchInfoPO> bbMatchPOs = bbMatchInfoDao.findBBMatchPO(teamId, new Date(), null, size, MatchStatus.NOT_START);
		for (BBMatchInfoPO po : bbMatchPOs) {
			FutureMatchVO vo = new FutureMatchVO();
			vo.setMatchName(po.getName());
			vo.setHomeTeam(po.getHomeTeam());
			vo.setGuestTeam(po.getGuestTeam());
			vo.setMatchDate(po.getMatchTime());
			futureMatchVOs.add(vo);
		}
		return futureMatchVOs;
	}

	@Transactional
	@Override
	public MatchResultStats getRecentMatchsStats(long teamId, int size) {
		if(teamId <= 0) {
			return null;
		}
		List<BBMatchInfoPO> bbMatchPOs = bbMatchInfoDao.findBBMatchPO(teamId, null, new Date(), size, MatchStatus.COMPLETE);
		MatchResultStats resultStats = parseMatch(bbMatchPOs);
		
		BBTeamPO bbTeam = bbTeamDao.findByTeamId(teamId);
		if(null != bbTeam) {
			resultStats.setTeamName(bbTeam.getChineseName());
		}
		
		return resultStats;
	}

	protected MatchResultStats parseMatch(List<BBMatchInfoPO> bbMatchPOs) {
		MatchResultStats resultStats = new MatchResultStats();
		resultStats.setSize(0);
		int shengNum = 0, fuNum = 0;
		if(null != bbMatchPOs && bbMatchPOs.size() > 0) {
			resultStats.setSize(bbMatchPOs.size());
			for (Iterator<BBMatchInfoPO> iterator = bbMatchPOs.iterator(); iterator.hasNext();) {
				BBMatchInfoPO bbMatchPO = iterator.next();
				MatchResultNameEnum name = matchResult(bbMatchPO.getHomeScore(), bbMatchPO.getGuestScore());
				switch (name) {
					case SHENG:
						shengNum = shengNum + 1;
						break;
					case FU:
						fuNum = fuNum + 1;
						break;
				}
			}
			resultStats.setShengNum(shengNum);
			resultStats.setFuNum(fuNum);
		}
		return resultStats;
	}
	
	@Transactional
	@Override
	public MatchResultStats getFightHistoryMatchsStats(long homeTeamId, long guestTeamId, Date from, Date to, int size) {
		if(homeTeamId <= 0 || guestTeamId <= 0) {
			return null;
		}
		List<BBMatchInfoPO> bbMatchList = bbMatchInfoDao.findBBMatchs(homeTeamId, 
				guestTeamId, from, to, size);
		MatchResultStats resultStats = parseMatch(bbMatchList);
		
		BBTeamPO bbTeam = bbTeamDao.findByTeamId(homeTeamId);
		if(null != bbTeam) {
			resultStats.setTeamName(bbTeam.getChineseName());
		}
		return resultStats;
	}
}