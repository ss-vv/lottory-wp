package com.xhcms.lottery.dc.persist.service.impl;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.xhcms.lottery.commons.data.MatchSupportPlay;
import com.xhcms.lottery.dc.data.LCOdds;
import com.xhcms.lottery.dc.data.Match;
import com.xhcms.lottery.dc.data.ZCOdds;
import com.xhcms.lottery.dc.persist.dao.MatchSupportPlayDao;
import com.xhcms.lottery.dc.persist.service.MatchSupportPlayService;
import com.xhcms.lottery.lang.LotteryId;

public class MatchSupportPlayServiceImpl implements MatchSupportPlayService {
	
	@Autowired
	private MatchSupportPlayDao supportPlayDao;

	@Transactional
	@Override
	public void saveSupportPlay(List<Match> data, LotteryId lotteryId) {
		if(null == lotteryId) return;
		List<BigInteger> existsPlay = supportPlayDao.findCurrSupportMatchId(lotteryId);
		if(null == existsPlay) {
			existsPlay = new ArrayList<BigInteger>();
		}
		
		List<MatchSupportPlay> supportPlays = new ArrayList<MatchSupportPlay>();
		for(Match match : data) {
			BigInteger val = new BigInteger(match.getMatchId()+"");
			if(existsPlay.contains(val)) {
				continue;
			}
			MatchSupportPlay supportPlay = new MatchSupportPlay();
			supportPlay.setLotteryId(lotteryId.name());
			supportPlay.setMatchId(match.getMatchId());
			if(null != match && null != match.getPlayIds() && 
					match.getPlayIds().size() > 0) {
				for(String playId : match.getPlayIds()) {
					MatchSupportPlay sup = new MatchSupportPlay();
					BeanUtils.copyProperties(supportPlay, sup);
					sup.setPlayId(playId);
					supportPlays.add(sup);
				}
			}
		}
		if(supportPlays.size() > 0) {
			supportPlayDao.save(supportPlays);
		}
	}

	@Transactional
	@Override
	public List<ZCOdds> filterZCOdds(List<ZCOdds> data) {
		List<ZCOdds> result = new ArrayList<ZCOdds>();
		if(null != data && data.size() > 0) {
			List<String> supportDGPlayList = supportPlayDao.findCurrSupportMatchPlay(LotteryId.JCZQ);
			if(null != supportDGPlayList && supportDGPlayList.size() > 0) {
				for(ZCOdds odds : data) {
					String key = odds.getMatchId() + odds.getPlayId();
					if(odds.getPlayId().endsWith("ZC_1")){
						if(supportDGPlayList.contains(key)) {
							result.add(odds);
						}
					} else {
						result.add(odds);
					}
				}
			}
		}
		return result;
	}
	
	@Transactional
	@Override
	public List<ZCOdds> filterZCZeroOdds(List<ZCOdds> data) {
		List<ZCOdds> result = new ArrayList<ZCOdds>();
		if(null != data && data.size() > 0) {
			for(ZCOdds odds : data) {
				if(checkodds(odds.getOdds())){//赔率都不为零
					result.add(odds);
				} else {
					if(odds.getPlayId().endsWith("ZC_1")){//如果是单关，需要处理单关支持表
						supportPlayDao.removeNotSupportPlay(odds.getMatchId(),odds.getPlayId());
					}
				}
			}
		}
		return result;
	}
	
	private boolean checkodds(String oddString){
		String[] odds = oddString.split(",");
		for (String string : odds) {
			if(BigDecimal.ZERO.equals(new BigDecimal(string))){//有一个赔率为0，就返回false
				return false;
			}
		}
		return true;
	}
	
	@Transactional
	@Override
	public List<LCOdds> filterLCOdds(List<LCOdds> data) {
		List<LCOdds> result = new ArrayList<LCOdds>();
		if(null != data && data.size() > 0) {
			List<String> supportDGPlayList = supportPlayDao.findCurrSupportMatchPlay(LotteryId.JCLQ);
			if(null != supportDGPlayList && supportDGPlayList.size() > 0) {
				for(LCOdds odds : data) {
					String key = odds.getMatchId() + odds.getPlayId();
					if(odds.getPlayId().endsWith("LC_1")){
						if(supportDGPlayList.contains(key)) {
							result.add(odds);
						}
					} else {
						result.add(odds);
					}
				}
			}
		}
		return result;
	}
}
