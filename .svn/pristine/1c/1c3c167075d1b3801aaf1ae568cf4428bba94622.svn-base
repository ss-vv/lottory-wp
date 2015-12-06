package com.xhcms.lottery.commons.persist.service.impl;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.unison.lottery.weibo.common.persist.QTMatchidDao;
import com.xhcms.lottery.commons.persist.service.MatchService;
import com.xhcms.lottery.commons.persist.service.QtLcMatchService;
import com.xhcms.lottery.lang.LotteryId;

public class QtLcMatchServiceImpl implements QtLcMatchService {

	private Logger log = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private QTMatchidDao qtLcMatchIdDao;
	
	@Autowired
	private MatchService matchService;
	
	@Transactional
	@Override
	public long findQTMatchId(String lcMatchId) {
		return qtLcMatchIdDao.findQTMatchId(lcMatchId);
	}

	@Transactional
	@Override
	public long findLCMatchId(Long qtMatchId, String lotteryName) {
		return qtLcMatchIdDao.findLCMatchId(qtMatchId, lotteryName);
	}
	

	@Transactional
	@Override
	public void updateFBPresetScore(String qtMatchId, String homeHalfScore,
			String guestHalfScore, String homeScore, String guestScore) {
		String halfScorePreset = null;
		String scorePreset = null;
		if(StringUtils.isNotBlank(homeHalfScore) && StringUtils.isNotBlank(guestHalfScore)) {
			halfScorePreset = homeHalfScore + ":" + guestHalfScore;
		}
		if(StringUtils.isNotBlank(homeScore) && StringUtils.isNotBlank(guestScore)) {
			scorePreset = homeScore + ":" + guestScore;
		}
		long qtMid = 0;
		try {
			qtMid = Long.parseLong(qtMatchId);
		} catch (NumberFormatException e) {
			log.error("无法转换球探赛事ID类型:qtMatchId={}, error={}", qtMatchId, e);
		}
		long lcMatchId = findLCMatchId(qtMid, LotteryId.JCZQ.cnName());
		if(StringUtils.isNotBlank(halfScorePreset) && StringUtils.isNotBlank(scorePreset) 
				&& lcMatchId > 0) {
			log.info("update fbMatch preset score lcMatchId={}, scorePreset={}, halfScorePreset={}", 
					new Object[]{lcMatchId, scorePreset, halfScorePreset});
			matchService.updateScoreToFBMatchPreset(lcMatchId, scorePreset, halfScorePreset);
		} else if(lcMatchId <= 0) {
			log.error("无法通过球探赛事Id={}, lotteryName={}, 找到大V彩赛事ID!", 
					new Object[]{qtMid, LotteryId.JCZQ.cnName()});
		}
	}

	@Transactional
	@Override
	public void updateBBPresetScore(Long qtMatchId, int homeScore,
			int guestScore) {
		long lcMatchId = findLCMatchId(qtMatchId, LotteryId.JCLQ.cnName());
		if(lcMatchId > 0 && homeScore > 0 && guestScore > 0) {
			//注意这块比分是用在预兑奖时使用的，比分客队在前主队在后
			String finalScorePreset = guestScore + ":" + homeScore;
			matchService.updateScoreToBBMatchPreset(lcMatchId, finalScorePreset);
			log.info("update bb preset score: lcMatchId={}, finalScorePreset={}",
					new Object[]{lcMatchId, finalScorePreset});
		} else if(lcMatchId <= 0) {
			log.error("无法通过球探赛事Id={}, lotteryName={}, 找到大V彩赛事ID!", 
					new Object[]{qtMatchId, LotteryId.JCLQ.cnName()});
		}
	}
}