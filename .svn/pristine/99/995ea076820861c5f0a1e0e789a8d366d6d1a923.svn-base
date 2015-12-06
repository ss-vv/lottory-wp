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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import com.xhcms.lottery.commons.persist.entity.BJDCMatchPO;
import com.xhcms.lottery.commons.util.OptionUtils;
import com.xhcms.lottery.dc.data.BDMatch;
import com.xhcms.lottery.dc.data.BDOdds;
import com.xhcms.lottery.dc.data.BDResult;
import com.xhcms.lottery.dc.data.Bonus;
import com.xhcms.lottery.dc.persist.dao.BJDCMatchDao;
import com.xhcms.lottery.dc.persist.dao.BJDCMatchPlayDao;
import com.xhcms.lottery.dc.persist.dao.BJDCOddsDao;
import com.xhcms.lottery.dc.persist.service.BJDCMatchService;
import com.xhcms.lottery.lang.Constants;
import com.xhcms.lottery.lang.EntityStatus;
import com.xhcms.lottery.lang.PlayType;

/**
 * 北京单场service
 */
public class BJDCMatchServiceImpl implements BJDCMatchService {
	
	@Autowired
	private BJDCMatchDao bjdcMatchDao;
	
	@Autowired
	private BJDCOddsDao bjdcOddsDao;
	@Autowired
	private BJDCMatchPlayDao bjdcMatchPlayDao;

	@Override
	@Transactional
	public void batchUpdateMatch(List<BDMatch> data, PlayType playType) {
		if (data == null || data.size() == 0) {
			return;
		}
		Set<Long> idSets = new HashSet<Long>();
		List<BDMatch> updateMatchs = new ArrayList<BDMatch>();	//需要插入的赛事集合
		HashMap<Long, BDMatch> insertMaps = new HashMap<Long, BDMatch>();	//需要更新的赛事集合

		for (BDMatch bjdcMatch : data) {
			insertMaps.put(bjdcMatch.getMatchId(), bjdcMatch);
			idSets.add(bjdcMatch.getMatchId());
		}

		// 过滤已经存在的 MatchID
		for (long id : bjdcMatchDao.filterExist(idSets)) {
			BDMatch bjdcMatch = insertMaps.remove(id);
			updateMatchs.add(bjdcMatch);
		}
		
		Collection<BDMatch> update = updateMatchs;
		Collection<BDMatch> insert = insertMaps.values();
		
		if (insert.size() > 0) {
			bjdcMatchDao.batchInsertMatch(insert);
			
			bjdcOddsDao.batchUpdateConcedePonitsByLottery(insert, playType);
		}
		if (update.size() > 0) {
			bjdcMatchDao.batchUpdateMatch(update);
			
			bjdcOddsDao.batchUpdateConcedePonitsByLottery(update, playType);
		}
	}
	
	@Transactional
	@Override
	public void batchUpdateOdds(List<BDOdds> data) {
		if (data == null || data.size() == 0) {
			return;
		}
		Set<String> idSets = new HashSet<String>();
		List<BDOdds> updateOdds = new ArrayList<BDOdds>();
		HashMap<String, BDOdds> insertOddsMaps = new HashMap<String, BDOdds>();

		for (BDOdds odds : data) {
			insertOddsMaps.put(odds.getId(), odds);
			idSets.add(odds.getId());
		}

		// 过滤已经存在的赛事Odds
		List<String> existIds = bjdcOddsDao.filterExist(idSets);
		for (String id : existIds) {
			BDOdds odds = insertOddsMaps.remove(id);
			updateOdds.add(odds);
		}
		
		if (insertOddsMaps.values().size() > 0) {
			bjdcOddsDao.batchInsertOdds(insertOddsMaps.values());
		}
		if (updateOdds.size() > 0) {
			bjdcOddsDao.batchUpdateOdds(updateOdds);
		}
	}

	@Transactional
	@Override
	public void updateMatchStatus() {
		Date date = new Date();
        List<BJDCMatchPO> matchs = bjdcMatchDao.findMatchsGreatThanEntrust(DateUtils.addDays(date, -5));
        for(BJDCMatchPO m: matchs){
            if(m.getEntrustDeadline().compareTo(date) != 1){
                m.setStatus(EntityStatus.MATCH_STOP_SELLING);
            }
        }
	}
	
	@Override
	@Transactional
	public void betchUpdateMatchResult(List<BDResult> rs) {
	
		if (rs == null || rs.size() == 0) {
			return;
		}

		//有可能是多期的让球数 
		Map<String,BDMatch> bdmatch=bjdcMatchPlayDao.getBDMatchMap(rs,Constants.PLAY_01_BD_SPF);
		List<Bonus> bs = new ArrayList<Bonus>();
		for(BDResult bd:rs){
			
			BDMatch bdmatch_=bdmatch.get(bd.getMatchId()+Constants.PLAY_01_BD_SPF);
			int num=0;
			if(bdmatch_!=null){
				
				num=bdmatch_.getConcedePoints();
			}
			//sfp
			
			bs.add(new Bonus(Constants.PLAY_01_BD_SPF,bd.getMatchId(),bd.getSfpSp(),
						OptionUtils.bdWinOption(Constants.PLAY_100,num, bd.getHalfScore(), bd.getScore())));
				
			bs.add(new Bonus(Constants.PLAY_01_BD_SPF,bd.getMatchId(),bd.getSfpSp(),
					OptionUtils.bdWinOption(Constants.PLAY_100,num, bd.getHalfScore(), bd.getScore())));

			//jqs
			bs.add(new Bonus(Constants.PLAY_02_BD_JQS,bd.getMatchId(),bd.getJqsSp(),
					OptionUtils.bdWinOption(Constants.PLAY_101, 0, bd.getHalfScore(), bd.getScore())));

			//sxds
			bs.add(new Bonus(Constants.PLAY_03_BD_SXDS,bd.getMatchId(),bd.getSxdsSp(),
					OptionUtils.bdWinOption(Constants.PLAY_102, 0, bd.getHalfScore(), bd.getScore())));

			//bf
			bs.add(new Bonus(Constants.PLAY_04_BD_BF,bd.getMatchId(),bd.getBfSp(),
					OptionUtils.bdWinOption(Constants.PLAY_103, 0, bd.getHalfScore(), bd.getScore())));

			//bqc
			bs.add(new Bonus(Constants.PLAY_05_BD_BQC,bd.getMatchId(),bd.getBqcSp(),
					OptionUtils.bdWinOption(Constants.PLAY_104, 0, bd.getHalfScore(), bd.getScore())));
			
			if("取消".equals(bd.getHalfScore()) || "取消".equals(bd.getScore())){
				bd.setStatus(EntityStatus.MATCH_CANCEL);
			} else {
				bd.setStatus(EntityStatus.MATCH_OVER);
			}
			
		}

		bjdcMatchDao.batchUpdateMatchResult(rs);
		bjdcMatchDao.batchUpdateResultBonus(bs);
		
	}
    /**
     * 北单 SF
     */
	@Override
	@Transactional
	public void betchUpadteBDSFMatchResult(List<BDResult> rs) {
		
		if (rs == null || rs.size() == 0) {
			return;
		}

		List<Bonus> bs = new ArrayList<Bonus>();
		List<Bonus> nspbs=new ArrayList<Bonus>();
		for(BDResult bd:rs){

			//sf
			if(bd.getSfSp()==0.0){
	
				nspbs.add(new Bonus(Constants.PLAY_06_BD_SF,bd.getMatchId(),bd.getSfSp(),
						OptionUtils.bdSFWinOption(Constants.PLAY_105,bd.getResult())));
			}else{
				
				bs.add(new Bonus(Constants.PLAY_06_BD_SF,bd.getMatchId(),bd.getSfSp(),
						OptionUtils.bdSFWinOption(Constants.PLAY_105,bd.getResult())));
			}
		}
		
	           bjdcMatchDao.batchUpdateResultBonus(bs);
			if(nspbs.size()>0){
				
				bjdcMatchDao.batchUpdateNoSpResultBonus(nspbs);	
			}
		
	}

	@Override
	@Transactional
	public List<String> getIssueNumber(String playId) {
		
	   return bjdcMatchDao.getIssueNumber(playId);
	}

	

}
