package com.unison.lottery.weibo.data.service.store.persist.service.impl;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.unison.lottery.weibo.data.service.store.data.FBAsianOddResult;
import com.unison.lottery.weibo.data.service.store.data.FBAsianOddVO;
import com.unison.lottery.weibo.data.service.store.data.FBEuropeOddVO;
import com.unison.lottery.weibo.data.service.store.data.FBMatchData;
import com.unison.lottery.weibo.data.service.store.data.FBMatchScore;
import com.unison.lottery.weibo.data.service.store.data.KellyFormula;
import com.unison.lottery.weibo.data.service.store.data.MatchStatus;
import com.unison.lottery.weibo.data.service.store.persist.dao.FBAsiaOddsDao;
import com.unison.lottery.weibo.data.service.store.persist.dao.FBEuroOddsDao;
import com.unison.lottery.weibo.data.service.store.persist.dao.FBTeamDao;
import com.unison.lottery.weibo.data.service.store.persist.dao.LotteryCorpDao;
import com.unison.lottery.weibo.data.service.store.persist.dao.QTMatchDao;
import com.unison.lottery.weibo.data.service.store.persist.entity.FBAsiaOddsPO;
import com.unison.lottery.weibo.data.service.store.persist.entity.FBEuroOddsPO;
import com.unison.lottery.weibo.data.service.store.persist.entity.FBTeamPO;
import com.unison.lottery.weibo.data.service.store.persist.entity.LotteryCorpPO;
import com.unison.lottery.weibo.data.service.store.persist.entity.QTMatchPO;
import com.unison.lottery.weibo.data.service.store.persist.service.KellyFormulaService;
import com.unison.lottery.weibo.data.service.store.persist.service.MatchDataService;

@Service
public class MatchDataServiceImpl implements MatchDataService {
	
	private Logger log = LoggerFactory.getLogger(getClass());

	@Autowired
	private QTMatchDao qtMatchDao;
	
	@Autowired
	private FBEuroOddsDao fbEuroOddsDao;
	
	@Autowired
	private FBAsiaOddsDao asiaOddsDao;
	
	@Autowired
	private KellyFormulaService formulaService;
	
	@Autowired
	private LotteryCorpDao corpDao;
	
	private List<LotteryCorpPO> corpList;
	
	@Autowired
	private FBTeamDao fbTeamDao;
	
	@Transactional
	@Override
	public FBMatchScore getFBMatch(String matchId) {
		FBMatchScore result = new FBMatchScore();
		
		int qtMatchId = qtMatchDao.queryQTMatchId(matchId);
		QTMatchPO matchPO = qtMatchDao.queryQTMatchInfo(qtMatchId);
		String halfScore = "-";
		String score = "-";
		if(null != matchPO) {
			BeanUtils.copyProperties(matchPO, result);
			int matchStatus = matchPO.getMatchStatus();
			
			if(StringUtils.isBlank(matchPO.getHalfScore())) {
				result.setHalfScore(halfScore);
			}
			if(StringUtils.isBlank(matchPO.getScore())) {
				result.setScore(score);
			}
			if(MatchStatus.COMPLETE == matchStatus) {
				result.setFinish(true);
			}
//			FBTeamPO homeTeamPO = fbTeamDao.findByTeamId(matchPO.getHomeTeamId());
//			FBTeamPO guestTeamPO = fbTeamDao.findByTeamId(matchPO.getGuestTeamId());
//			if(null != homeTeamPO) {
//				result.setHomeTeamLogoUrl(homeTeamPO.getLogoUrl());
//			}
//			if(null != guestTeamPO) {
//				result.setGuestTeamLogoUrl(guestTeamPO.getLogoUrl());
//			}
		}
		return result;
	}

	@Transactional
	@Override
	public FBMatchData getFBLatestMatchResult(String lcMatchId, int count) {
		FBMatchScore fbMatchScore = getFBMatch(lcMatchId);
		FBMatchData fbMatchData = new FBMatchData();
		if(null == fbMatchScore || fbMatchScore.getId() <= 0) {
			return fbMatchData;
		}
//		long homeTeamId = fbMatchScore.getHomeTeamId();
//		long guestGuestId = fbMatchScore.getGuestTeamId();
		//主客队名称
//		fbMatchData.setHomeTeamId(fbMatchScore.getHomeTeamId());
//		fbMatchData.setGuestTeamId(fbMatchScore.getGuestTeamId());
		fbMatchData.setHomeTeamName(fbMatchScore.getHomeTeamName());
		fbMatchData.setGuestTeamName(fbMatchScore.getGuestTeamName());
		
//		List<QTMatchPO> qtHomeMatchList = qtMatchDao.findLastestCompleteQTMatchPO(homeTeamId, count);
//		List<QTMatchPO> qtGuestMatchList = qtMatchDao.findLastestCompleteQTMatchPO(guestGuestId, count);
		
		//最近主客队胜平负数
		List<Integer> homeMatchResult = new ArrayList<Integer>();
		List<Integer> guestMatchResult = new ArrayList<Integer>(); 
		
//		homeMatchResult = getWinFlatNegativeCount(qtHomeMatchList, homeTeamId);
//		guestMatchResult = getWinFlatNegativeCount(qtGuestMatchList, guestGuestId);
		fbMatchData.setHomeMatchResult(homeMatchResult);
		fbMatchData.setGuestMatchResult(guestMatchResult);
		
		//主客队不输概率的百分值
		fbMatchData.setHomeNotLosePercent(calNotLosePercent(homeMatchResult));
		fbMatchData.setGuestNotLosePercent(calNotLosePercent(guestMatchResult));
		
		//获取比分走势
//		fbMatchData.setHomeChartList(qtHomeMatchList);
//		fbMatchData.setGuestChartList(qtGuestMatchList);
		
		return fbMatchData;
	}

	/**
	 * 解析近期完场比赛数据，获取赛事胜平负数量
	 * @param qtMatchList
	 * @param teamId 球队ID
	 * @return
	 */
	public List<Integer> getWinFlatNegativeCount(List<QTMatchPO> qtMatchList, 
			long teamId) {
		List<Integer> matchResult = new ArrayList<Integer>();
		int win = 0;
		int flat = 0;
		int negative = 0;
		for(QTMatchPO matchPO : qtMatchList) {
			String score = matchPO.getScore();
//			long homeTeamId = matchPO.getHomeTeamId();
//			long guestTeamId = matchPO.getGuestTeamId();
			if(StringUtils.isNotBlank(score)) {
				String[] hgScore = score.split(":");
				int homeScore = Integer.parseInt(hgScore[0]);
				int guestScore = Integer.parseInt(hgScore[1]);
				if(homeScore == guestScore) {
					flat = flat + 1;
				}
//				if((teamId == homeTeamId && homeScore > guestScore) || 
//						(teamId == guestTeamId && guestScore > homeScore)) {
//					win = win + 1;
//				}
//				if((teamId == homeTeamId && homeScore < guestScore) ||
//						(teamId == guestTeamId && guestScore < homeScore)) {
//					negative = negative + 1;
//				}
			}
		}
		matchResult.add(win);
		matchResult.add(flat);
		matchResult.add(negative);
		return matchResult;
	}
	/**
	 * 主客队不输概率的百分值
	 * @param list	赛事胜平负数集合([2,2,1]:表示最近5场比赛2胜2平1负)
	 * @return
	 */
	public String calNotLosePercent(List<Integer> list) {
		int score = list.get(0) * 3 + list.get(1);
		return (score * 100/15) + "";
	}
	
	@Transactional
	@Override
	public List<FBEuropeOddVO> fbEuropeOddList(String matchId) {
		List<FBEuropeOddVO> list = new ArrayList<FBEuropeOddVO>();
		int qtMatchId = qtMatchDao.queryQTMatchId(matchId);
		List<FBEuroOddsPO> euroOddsList = fbEuroOddsDao.findEuropeOddsList(qtMatchId);
		if(null != euroOddsList && euroOddsList.size() > 0) {
			DecimalFormat df = new DecimalFormat("0.000");
			corpList = corpDao.listCorp();
			KellyFormula kelly = formulaService.calFBArgRatio(qtMatchId);
			for(int i=0; i<euroOddsList.size(); i++) {
				FBEuroOddsPO eurOddsPO = euroOddsList.get(i);
				FBEuropeOddVO eurOdd = new FBEuropeOddVO();
				eurOdd.setIndex(i+1);
				long corpId = eurOddsPO.getCorpId();
				LotteryCorpPO corpPO = getFBCompanyNameById(corpId);
				if(null != corpPO) {
					BeanUtils.copyProperties(corpPO, eurOdd);
				}
				
				eurOdd.setEuropeOddWin(eurOddsPO.getCurHomeWinOdds().toString());
				eurOdd.setEuropeOddFlat(eurOddsPO.getCurDrawOdds().toString());
				eurOdd.setEuropeOddNegative(eurOddsPO.getCurGuestWinOdds().toString());
				
				if(null != kelly) {
					double argWin = kelly.getArgWin().multiply(eurOddsPO.getCurHomeWinOdds()).doubleValue();
					double argFlat = kelly.getArgFlat().multiply(eurOddsPO.getCurDrawOdds()).doubleValue();
					double argNegative = kelly.getArgNegative().multiply(eurOddsPO.getCurGuestWinOdds()).doubleValue();
					eurOdd.setKellyOddWin(df.format(argWin));
					eurOdd.setKellyOddFlat(df.format(argFlat));
					eurOdd.setKellyOddNegative(df.format(argNegative));
				}
				list.add(eurOdd);
			}
		} else {
			log.info("查询足球欧赔数据;通过lcMatchId={}, 查询球探赛事ID={}, 对应欧赔为空.", 
					new Object[]{matchId, qtMatchId});
		}
		return list;
	}

	@Transactional
	@Override
	public List<FBAsianOddVO> fbAsianOddList(String matchId) {
		List<FBAsianOddVO> list = new ArrayList<FBAsianOddVO>();
		int qtMatchId = qtMatchDao.queryQTMatchId(matchId);
		List<FBAsiaOddsPO> asiaOddsList = asiaOddsDao.findAsianOddsList(qtMatchId);
		if(null != asiaOddsList && asiaOddsList.size() > 0) {
			corpList = corpDao.listCorp();
			for(int i=0; i<asiaOddsList.size(); i++) {
				FBAsiaOddsPO po = asiaOddsList.get(i);
				FBAsianOddVO asianOdd = new FBAsianOddVO();
				asianOdd.setIndex(i+1);
				long corpId = po.getCorpId();
				LotteryCorpPO corpPO = getFBCompanyNameById(corpId);
				if(null != corpPO) {
					BeanUtils.copyProperties(corpPO, asianOdd);
				}
				
				asianOdd.setInitHomeOdds(po.getInitHomeOdds());
				asianOdd.setInitHandicap(po.getInitHandicap());
				asianOdd.setInitGuestOdds(po.getInitGuestOdds());
				
				asianOdd.setCurHomeOdds(po.getCurHomeOdds());
				asianOdd.setCurHandicap(po.getCurHandicap());
				asianOdd.setCurGuestOdds(po.getCurGuestOdds());
				
				list.add(asianOdd);
			}
		}
		return list;
	}
	
	public LotteryCorpPO getFBCompanyNameById(long id) {
		LotteryCorpPO corpPO = null;
		if(null != corpList) {
			for(LotteryCorpPO cp : corpList) {
				if(id == cp.getId()) {
					corpPO = cp;
					break;
				}
			}
		}
		return corpPO;
	}

	@Transactional
	@Override
	public FBAsianOddResult fbAsianOddResult(String lcMatchId) {
		FBMatchScore fbMatchScore = getFBMatch(lcMatchId);
		FBAsianOddResult fbAsianOddResult = new FBAsianOddResult();
		if(null != fbMatchScore && fbMatchScore.getId() > 0) {
			fbAsianOddResult.setHomeTeamName(fbMatchScore.getHomeTeamName());
			fbAsianOddResult.setGuestTeamName(fbMatchScore.getGuestTeamName());
		}
		List<FBAsianOddVO> asianOddList = fbAsianOddList(lcMatchId);
		fbAsianOddResult.setFbAsianOddList(asianOddList);
		return fbAsianOddResult;
	}
}