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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.xhcms.lottery.commons.persist.dao.MatchColorDao;
import com.xhcms.lottery.commons.persist.entity.BBMatchPO;
import com.xhcms.lottery.commons.util.OptionUtils;
import com.xhcms.lottery.dc.data.Bonus;
import com.xhcms.lottery.dc.data.LCOdds;
import com.xhcms.lottery.dc.data.LCResult;
import com.xhcms.lottery.dc.data.Match;
import com.xhcms.lottery.dc.data.Score;
import com.xhcms.lottery.dc.persist.dao.BBMatchDao;
import com.xhcms.lottery.dc.persist.service.BBMatchService;
import com.xhcms.lottery.lang.Constants;
import com.xhcms.lottery.lang.EntityStatus;

public class BBMatchServiceImpl implements BBMatchService {
	private BBMatchDao bbMatchDao;
	
	@Autowired
	private MatchColorDao matchColorDao;
	
	public void setBbMatchDao(BBMatchDao bbMatchDao) {
		this.bbMatchDao = bbMatchDao;
	}
	
	/**
	 * 更新、插入赛程信息。同时会插入篮球的让分、预设总分值，但不会更新该值，因为在获取赔率时会更新。
	 */
	@Override
	@Transactional
	public void batchUpdateMatch(List<Match> data) {
		if (data == null || data.size() == 0) {
			return;
		}
		Set<Long> cos = new HashSet<Long>(); // code 和 offtime 作为唯一标识
		List<Match> ms = new ArrayList<Match>();  // 需要修改的 Match
		HashMap<Long, Match> mMap = new HashMap<Long, Match>();  // 需要添加的 Match
		
		Map<String, Score> scores = new HashMap<String, Score>();
		for (Match bb : data) {
			mMap.put(bb.getMatchId(), bb);
			cos.add(bb.getMatchId());
			
			Score score = new Score(Constants.PLAY_06_LC_1, bb.getMatchId(), bb.getConcedeScore());
			scores.put(score.getId(), score);
			score = new Score(Constants.PLAY_06_LC_2, bb.getMatchId(), bb.getConcedeScorePass());
			scores.put(score.getId(), score);
			score = new Score(Constants.PLAY_09_LC_1, bb.getMatchId(), bb.getGuestScore());
			scores.put(score.getId(), score);
			score = new Score(Constants.PLAY_09_LC_2, bb.getMatchId(), bb.getGuestScorePass());
			scores.put(score.getId(), score);
		}
		
		// 过滤已经存在的 MatchID
		for (long id : bbMatchDao.filterExist(cos)) {
			Match fb = mMap.remove(id);
		    ms.add(fb);
		}
		
		//根据字典设置短名称
		Map<String, String> shortMap = matchColorDao.loadLeagueNameShortName();
		for (Match match : mMap.values()) {
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
		
		updateMatchs(ms, mMap.values());


		List<String> existsScores = bbMatchDao.filterExistMatchPlay(scores.keySet());
	
		List<Score> insertScores = new ArrayList<Score>();
		List<Score> updateScores = new ArrayList<Score>();
		
		//过滤出需要更新和需要插入的Score
		for(String id : existsScores) {
			Score score = scores.remove(id);
			updateScores.add(score);
		}
		insertScores.addAll(scores.values());
		
		// 插入新让分、预设分值
//		if(insertScores.size() > 0) {
//			bbMatchDao.batchInsertScore(insertScores);
//		}
		
		// 不更新从赛事接口获取的让分、预设分值。
//		if(updateScores.size() > 0) {
//			bbMatchDao.batchUpdateScore(updateScores);
//		}
	}
	
	@Override
	@Transactional
	public void batchUpdateOdds(List<LCOdds> data) {
		if (data == null || data.size() == 0) {
			return;
		}
		Set<String> matchPlayIds = new HashSet<String>();
		List<LCOdds> updatedOdds = new ArrayList<LCOdds>();  // 需要修改的 MatchPlay
		HashMap<String, LCOdds> insertedOddsMap = new HashMap<String, LCOdds>(); // 需要添加的 MatchPlay
		
		for (LCOdds fb : data) {
		    insertedOddsMap.put(fb.getId(), fb);
		    matchPlayIds.add(fb.getId());
		}
		// 过滤已经存在的 MatchPlayID
		for(String id : bbMatchDao.filterExistMatchPlay(matchPlayIds)){
			LCOdds fb = insertedOddsMap.remove(id);
			updatedOdds.add(fb);
		}
		
		updateMatchPlays(updatedOdds, insertedOddsMap.values());
	}

	/**
	 * 更新赛事
	 * @param update 需要修改的赛事
	 * @param insert   需要新增的赛事
	 */
	private void updateMatchs(Collection<Match> update, Collection<Match> insert) {
		if (insert.size() > 0) {
			bbMatchDao.batchInsertMatch(insert);
		}
		if (update.size() > 0) {
			bbMatchDao.batchUpdateMatch(update);
		}
	}
	
	/**
	 * 更新赛事玩法对照
	 * @param update 需要修改的赛事玩法对照
	 * @param insert   需要新增的赛事玩法对照
	 */
	private void updateMatchPlays(Collection<LCOdds> update, Collection<LCOdds> insert) {
		if (insert.size() > 0) {
			bbMatchDao.batchInsertMatchPlay(insert);
		}
		if (update.size() > 0) {
			bbMatchDao.batchUpdateMatchPlay(update);
		}
	}
	
	@Override
	@Transactional
	public void batchUpdateMatchResult(List<LCResult> rts) {
		if (rts == null || rts.size() == 0) {
			return;
		}
		bbMatchDao.batchUpdateMatchResult(rts);
		//TODO: update bonus 更新比赛状态
		
		List<Bonus> bs = new ArrayList<Bonus>();
		for (LCResult r : rts) {
			bs.add(new Bonus(Constants.PLAY_06_LC_1, r.getMatchId(), r.getYfsfSp(), 
					OptionUtils.lcWinOption(Constants.PLAY_06, r.getConcedeScore(), r.getGuessScore(), r.getScore())));
			bs.add(new Bonus(Constants.PLAY_06_LC_2, r.getMatchId(), 0.00d, 
					OptionUtils.lcWinOption(Constants.PLAY_06, r.getConcedeScore(), r.getGuessScore(), r.getScore())));
			bs.add(new Bonus(Constants.PLAY_07_LC_1, r.getMatchId(), r.getSfSp(), 
					OptionUtils.lcWinOption(Constants.PLAY_07, r.getConcedeScore(), r.getGuessScore(), r.getScore())));
			bs.add(new Bonus(Constants.PLAY_07_LC_2, r.getMatchId(), 0.00d, 
					OptionUtils.lcWinOption(Constants.PLAY_07, r.getConcedeScore(), r.getGuessScore(), r.getScore())));
			bs.add(new Bonus(Constants.PLAY_08_LC_1, r.getMatchId(), r.getSfcSp(), 
					OptionUtils.lcWinOption(Constants.PLAY_08, r.getConcedeScore(), r.getGuessScore(), r.getScore())));
			bs.add(new Bonus(Constants.PLAY_08_LC_2, r.getMatchId(), 0.00d, 
					OptionUtils.lcWinOption(Constants.PLAY_08, r.getConcedeScore(), r.getGuessScore(), r.getScore())));
			bs.add(new Bonus(Constants.PLAY_09_LC_1, r.getMatchId(), r.getDxfSp(), 
					OptionUtils.lcWinOption(Constants.PLAY_09, r.getConcedeScore(), r.getGuessScore(), r.getScore())));
			bs.add(new Bonus(Constants.PLAY_09_LC_2, r.getMatchId(), 0.00d, 
					OptionUtils.lcWinOption(Constants.PLAY_09, r.getConcedeScore(), r.getGuessScore(), r.getScore())));
		}
		bbMatchDao.batchUpdateResultBonus(bs);
	}

    @Override
    @Transactional
    public void updateMatchStatus() {
        Date date = new Date();
        
        List<BBMatchPO> matchs = bbMatchDao.findOnSale(DateUtils.addDays(date, -5));
        for(BBMatchPO m: matchs){
            if(m.getEntrustDeadline().compareTo(date) != 1){
                m.setStatus(EntityStatus.MATCH_STOP_SELLING);
            }
        }
    }

}
