package com.xhcms.lottery.dc.persist.service.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.time.DateUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.xhcms.lottery.commons.persist.dao.MatchColorDao;
import com.xhcms.lottery.commons.persist.entity.FBMatchPO;
import com.xhcms.lottery.commons.util.OptionUtils;
import com.xhcms.lottery.dc.data.Bonus;
import com.xhcms.lottery.dc.data.Match;
import com.xhcms.lottery.dc.data.ZCOdds;
import com.xhcms.lottery.dc.data.ZCResult;
import com.xhcms.lottery.dc.persist.dao.FBMatchDao;
import com.xhcms.lottery.dc.persist.dao.impl.FBMatchDaoImpl;
import com.xhcms.lottery.dc.persist.service.FBMatchService;
import com.xhcms.lottery.lang.Constants;
import com.xhcms.lottery.lang.EntityStatus;

/**
 * @author xulang
 */
public class FBMatchServiceImpl implements FBMatchService {
	private Logger log = LoggerFactory.getLogger(getClass());
	
	private FBMatchDao fbMatchDao;
	
	@Autowired
	private MatchColorDao matchColorDao;
	
	public void setFbMatchDao(FBMatchDaoImpl fbMatchDao) {
		this.fbMatchDao = fbMatchDao;
	}
	
	@Override
	@Transactional
	public void batchUpdateMatch(List<Match> data) {
		if (data == null || data.size() == 0) {
			return;
		}
		Set<Long> cos = new HashSet<Long>(); 							// code 和 offtime 作为唯一标识
		List<Match> modifyMatches = new ArrayList<Match>();				// 需要修改的 Match
		HashMap<Long, Match> insertMatches = new HashMap<Long, Match>();// 需要添加的 Match
		
		for (Match fb : data) {
			insertMatches.put(fb.getMatchId(), fb);
			cos.add(fb.getMatchId());
		}
		
		// 过滤已经存在的 MatchID
		for (long id : fbMatchDao.filterExist(cos)) {
			Match fb = insertMatches.remove(id);
		    modifyMatches.add(fb);
		}
		
		//根据字典设置短名称
		Map<String, String> shortMap = matchColorDao.loadLeagueNameShortName();
		for (Match match : insertMatches.values()) {
			String longLeagueName = match.getLeague();
			String shortLeagueName = shortMap.get(longLeagueName);
			if(StringUtils.isNotBlank(shortLeagueName)){
				match.setLongLeagueName(longLeagueName);
				match.setLeague(shortLeagueName);
			} else {
				match.setLongLeagueName(longLeagueName);
				match.setLeague(longLeagueName);
			}
		}
		updateMatchs(modifyMatches, insertMatches.values());
		
	}
	
	@Override
	@Transactional
	public void batchUpdateOdds(List<ZCOdds> data) {
		if (data == null || data.size() == 0) {
			return;
		}
		
		Set<String> mpIds = new HashSet<String>();
		List<ZCOdds> mps = new ArrayList<ZCOdds>();  // 需要修改的 MatchPlay
		HashMap<String, ZCOdds> mpMap = new HashMap<String, ZCOdds>(); // 需要添加的 MatchPlay
		
		for (ZCOdds fb : data) {
		    mpMap.put(fb.getId(), fb);
		    mpIds.add(fb.getId());
		}
		
		// 过滤已经存在的 MatchPlayID
		for(String id : fbMatchDao.filterExistMatchPlay(mpIds)){
			ZCOdds fb = mpMap.remove(id);
			mps.add(fb);
		}
		
		updateMatchPlays(data.get(0).getPlayId(), mps, mpMap.values());
	}
	
	/**
	 * 更新赛事
	 * @param update 需要修改的赛事
	 * @param insert   需要新增的赛事
	 */
	private void updateMatchs(Collection<Match> update, Collection<Match> insert) {
		if (insert.size() > 0) {
			fbMatchDao.batchInsertMatch(insert);
		}
		if (update.size() > 0) {
			fbMatchDao.batchUpdateMatch(update);
		}
	}
	
	/**
	 * 更新赛事玩法对照
	 * @param update 需要修改的赛事玩法对照
	 * @param insert   需要新增的赛事玩法对照
	 */
	private void updateMatchPlays(String playId, Collection<ZCOdds> update, Collection<ZCOdds> insert) {
		if (insert.size() > 0) {
			fbMatchDao.batchInsertMatchPlay(insert);
		}
		if (update.size() > 0) {
			fbMatchDao.batchUpdateMatchPlay(update);
		}
	}
	
	@Override
	@Transactional
	public void batchUpdateMatchResult(List<ZCResult> results) {
		if (results == null || results.size() == 0) {
			return;
		}
		
		List<Bonus> bs = new ArrayList<Bonus>();
		for (ZCResult r : results) {
			bs.add(new Bonus(Constants.PLAY_01_ZC_1, r.getMatchId(), r.getSfpSp(), 
					OptionUtils.zcWinOption(Constants.PLAY_01, r.getConcedePoints(), r.getHalfScore(), r.getScore())));
			bs.add(new Bonus(Constants.PLAY_01_ZC_2, r.getMatchId(), 0.00d, 
					OptionUtils.zcWinOption(Constants.PLAY_01, r.getConcedePoints(), r.getHalfScore(), r.getScore())));
			bs.add(new Bonus(Constants.PLAY_02_ZC_1, r.getMatchId(), r.getBfSp(), 
					OptionUtils.zcWinOption(Constants.PLAY_02, r.getConcedePoints(), r.getHalfScore(), r.getScore())));
			bs.add(new Bonus(Constants.PLAY_02_ZC_2, r.getMatchId(), 0.00d, 
					OptionUtils.zcWinOption(Constants.PLAY_02, r.getConcedePoints(), r.getHalfScore(), r.getScore())));
			bs.add(new Bonus(Constants.PLAY_03_ZC_1, r.getMatchId(), r.getZjqSp(), 
					OptionUtils.zcWinOption(Constants.PLAY_03, r.getConcedePoints(), r.getHalfScore(), r.getScore())));
			bs.add(new Bonus(Constants.PLAY_03_ZC_2, r.getMatchId(), 0.00d, 
					OptionUtils.zcWinOption(Constants.PLAY_03, r.getConcedePoints(), r.getHalfScore(), r.getScore())));
			bs.add(new Bonus(Constants.PLAY_04_ZC_1, r.getMatchId(), r.getBqcSp(), 
					OptionUtils.zcWinOption(Constants.PLAY_04, r.getConcedePoints(), r.getHalfScore(), r.getScore())));
			bs.add(new Bonus(Constants.PLAY_04_ZC_2, r.getMatchId(), 0.00d, 
					OptionUtils.zcWinOption(Constants.PLAY_04, r.getConcedePoints(), r.getHalfScore(), r.getScore())));
			bs.add(new Bonus(Constants.PLAY_80_ZC_1, r.getMatchId(), r.getBrqspfSp(), 
					OptionUtils.zcWinOption(Constants.PLAY_80, r.getConcedePoints(), r.getHalfScore(), r.getScore())));
			bs.add(new Bonus(Constants.PLAY_80_ZC_2, r.getMatchId(), 0.00d, 
					OptionUtils.zcWinOption(Constants.PLAY_80, r.getConcedePoints(), r.getHalfScore(), r.getScore())));

			if("取消".equals(r.getHalfScore()) || "取消".equals(r.getScore())){
				r.setStatus(EntityStatus.MATCH_CANCEL);
			} else {
				r.setStatus(EntityStatus.MATCH_OVER);
			}
		}
		
		log.info("竞彩足球：1.开始更新比赛各玩法中奖选项；2.不使用平台提供的比分数据!");
		fbMatchDao.batchUpdateMatchResult(results);
		fbMatchDao.batchUpdateResultBonus(bs);
	}

    @Override
    @Transactional
    public void updateMatchStatus() {
        Date date = new Date();
        
        List<FBMatchPO> matchs = fbMatchDao.findOnSale(DateUtils.addDays(date, -5));
        for(FBMatchPO m: matchs){
            if(m.getEntrustDeadline().compareTo(date) != 1){
                m.setStatus(EntityStatus.MATCH_STOP_SELLING);
            }
        }
    }

}
