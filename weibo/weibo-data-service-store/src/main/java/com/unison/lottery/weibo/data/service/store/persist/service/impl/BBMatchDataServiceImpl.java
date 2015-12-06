package com.unison.lottery.weibo.data.service.store.persist.service.impl;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.unison.lottery.weibo.data.service.store.data.BBAsianOddResult;
import com.unison.lottery.weibo.data.service.store.data.BBAsianOddVO;
import com.unison.lottery.weibo.data.service.store.data.BBEuropeOddVO;
import com.unison.lottery.weibo.data.service.store.data.BBMatchScore;
import com.unison.lottery.weibo.data.service.store.data.BBOddsBigSmallResult;
import com.unison.lottery.weibo.data.service.store.data.BBOddsBigSmallVO;
import com.unison.lottery.weibo.data.service.store.data.KellyFormula;
import com.unison.lottery.weibo.data.service.store.persist.dao.BBConcedeScoreOddsDao;
import com.unison.lottery.weibo.data.service.store.persist.dao.BBLotteryCorpDao;
import com.unison.lottery.weibo.data.service.store.persist.dao.BBMatchInfoDao;
import com.unison.lottery.weibo.data.service.store.persist.dao.BBOddsBigSmallDao;
import com.unison.lottery.weibo.data.service.store.persist.dao.BBOddsEuroDao;
import com.unison.lottery.weibo.data.service.store.persist.dao.BBTeamDao;
import com.unison.lottery.weibo.data.service.store.persist.dao.QTMatchDao;
import com.unison.lottery.weibo.data.service.store.persist.entity.BBConcedeScoreOddsPO;
import com.unison.lottery.weibo.data.service.store.persist.entity.BBLotteryCorpPO;
import com.unison.lottery.weibo.data.service.store.persist.entity.BBMatchInfoPO;
import com.unison.lottery.weibo.data.service.store.persist.entity.BBOddsBigSmallPO;
import com.unison.lottery.weibo.data.service.store.persist.entity.BBOddsEuroPO;
import com.unison.lottery.weibo.data.service.store.persist.entity.BBTeamPO;
import com.unison.lottery.weibo.data.service.store.persist.service.BBMatchDataService;
import com.unison.lottery.weibo.data.service.store.persist.service.KellyFormulaService;

@Service
public class BBMatchDataServiceImpl implements BBMatchDataService {

	private Logger log = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private QTMatchDao qtMatchDao;
	
	@Autowired
	private BBMatchInfoDao bbMatchInfoDao;
	
	@Autowired
	private BBTeamDao bbTeamDao;
	
	@Autowired
	private KellyFormulaService formulaService;
	
	@Autowired
	private BBOddsEuroDao bbOddsEuroDao;
	
	@Autowired
	private BBLotteryCorpDao bbLotteryCorpDao;
	
	private List<BBLotteryCorpPO> bbLotteryCorpList;
	
	@Autowired
	private BBConcedeScoreOddsDao bbConcedeScoreOddsDao;
	
	@Autowired
	private BBOddsBigSmallDao bbOddsBigSmallDao;
	
	@Transactional
	@Override
	public BBMatchScore getBBMatch(String lcMatchId) {
		BBMatchScore bbMatchScore = new BBMatchScore();
		int qtMatchId = qtMatchDao.queryQTMatchId(lcMatchId);
		BBMatchInfoPO bbMatchInfoPO = bbMatchInfoDao.queryBBMatchInfo(qtMatchId);
		if(null == bbMatchInfoPO || bbMatchInfoPO.getId() <= 0) {
			return bbMatchScore; 
		}
		BeanUtils.copyProperties(bbMatchInfoPO, bbMatchScore);
		if(null != bbMatchInfoPO) {
//			long homeTeamId = bbMatchInfoPO.getHomeTeamId();
//			long guestTeamId = bbMatchInfoPO.getGuestTeamId();
//			BBTeamPO homeTeamPO = bbTeamDao.get(homeTeamId);
//			BBTeamPO guestTeamPO = bbTeamDao.get(guestTeamId);
//			if(null != homeTeamPO) {
//				bbMatchScore.setHomeTeamLogoUrl(homeTeamPO.getLogoUrl());
//			}
//			if(null != guestTeamPO) {
//				bbMatchScore.setGuestTeamLogoUrl(guestTeamPO.getLogoUrl());
//			}
		}
		return bbMatchScore;
	}
	
	public BBLotteryCorpPO getBBCompanyNameById(long id) {
		BBLotteryCorpPO corpPO = null;
		if(null != bbLotteryCorpList) {
			for(BBLotteryCorpPO cp : bbLotteryCorpList) {
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
	public List<BBEuropeOddVO> bbEuropeOddList(String lcMatchId) {
		List<BBEuropeOddVO> list = new ArrayList<BBEuropeOddVO>();
		int qtMatchId = qtMatchDao.queryQTMatchId(lcMatchId);
		List<BBOddsEuroPO> bbOddsEuroPOList = bbOddsEuroDao.findEuropeOddsList(qtMatchId);
		if(null != bbOddsEuroPOList && bbOddsEuroPOList.size() > 0) {
			BigDecimal percent = new BigDecimal(100);
			bbLotteryCorpList = bbLotteryCorpDao.listCorp();
			KellyFormula kelly = formulaService.calBBArgRatio(qtMatchId);
			DecimalFormat df = new DecimalFormat("0.000");
			for(int i = 0; i < bbOddsEuroPOList.size(); i++) {
				BBOddsEuroPO euroPO = bbOddsEuroPOList.get(i);
				BBEuropeOddVO europeOddVO = new BBEuropeOddVO();
				europeOddVO.setIndex(i+1);
				//取得博彩公司名称
				BBLotteryCorpPO bbLotteryCorpPO = getBBCompanyNameById(euroPO.getCorpId());
				europeOddVO.setName(bbLotteryCorpPO.getName());
				
				europeOddVO.setHomeWinOdds(euroPO.getRealtimeHomeWinOdds());
				europeOddVO.setGuestWinOdds(euroPO.getRealtimeGuestWinOdds());
				
				BigDecimal realHomeWin = euroPO.getRealtimeHomeWinOdds();
				BigDecimal realGuestWin = euroPO.getRealtimeGuestWinOdds();
				BigDecimal loseRatio = formulaService.lossRatio(realHomeWin, realGuestWin);
				BigDecimal homeWinPer = loseRatio.divide(realHomeWin, 4, BigDecimal.ROUND_DOWN);
				BigDecimal guestWinPer = loseRatio.divide(realGuestWin, 4, BigDecimal.ROUND_DOWN);
				
				europeOddVO.setHomeWinPer(homeWinPer.multiply(percent));
				europeOddVO.setGuestWinPer(guestWinPer.multiply(percent));
				//计算凯利指数
				if(null != kelly) {
					double argWin = kelly.getArgWin().multiply(euroPO.getRealtimeHomeWinOdds()).doubleValue();
					double argNegative = kelly.getArgNegative().multiply(euroPO.getRealtimeGuestWinOdds()).doubleValue();
					europeOddVO.setKellyHomeWin(df.format(argWin));
					europeOddVO.setKellyGuestWin(df.format(argNegative));
				}
				list.add(europeOddVO);
			}
		} else {
			log.info("查询篮球欧赔数据;通过lcMatchId={}, 查询球探赛事ID={}, 对应欧赔为空.", 
					new Object[]{lcMatchId, qtMatchId});
		}
		return list;
	}

	@Transactional
	@Override
	public List<BBAsianOddVO> bbAsianOddList(String lcMatchId) {
		int qtMatchId = qtMatchDao.queryQTMatchId(lcMatchId);
		List<BBConcedeScoreOddsPO> concedeScoreOddsList = bbConcedeScoreOddsDao.findByMatchId(qtMatchId);
		List<BBAsianOddVO> list = new ArrayList<BBAsianOddVO>();
		if(null != concedeScoreOddsList && concedeScoreOddsList.size() > 0) {
			bbLotteryCorpList = bbLotteryCorpDao.listCorp();
			for(BBConcedeScoreOddsPO po : concedeScoreOddsList) {
				BBAsianOddVO vo = new BBAsianOddVO();
				BeanUtils.copyProperties(po, vo);
				vo.setName(getBBCompanyNameById(po.getCorpId()).getName());
				list.add(vo);
			}
		}
		return list;
	}

	@Transactional
	@Override
	public List<BBOddsBigSmallVO> bbBigSmallOddList(String lcMatchId) {
		int qtMatchId = qtMatchDao.queryQTMatchId(lcMatchId);
		List<BBOddsBigSmallPO> oddsBigSmallList = bbOddsBigSmallDao.findByMatchId(qtMatchId);
		List<BBOddsBigSmallVO> list = new ArrayList<BBOddsBigSmallVO>();
		if(null != list) {
			bbLotteryCorpList = bbLotteryCorpDao.listCorp();
			for(BBOddsBigSmallPO po : oddsBigSmallList) {
				BBOddsBigSmallVO vo = new BBOddsBigSmallVO();
				BeanUtils.copyProperties(po, vo);
				vo.setName(getBBCompanyNameById(po.getCorpId()).getName());
				list.add(vo);
			}
		}
		return list;
	}

	@Transactional
	@Override
	public BBAsianOddResult bbAsianOddResult(String lcMatchId) {
		BBMatchScore bbMatch = getBBMatch(lcMatchId);
		BBAsianOddResult bbAsianOddResult = new BBAsianOddResult();
		if(null != bbMatch) {
			bbAsianOddResult.setHomeTeamName(bbMatch.getHomeTeam());
			bbAsianOddResult.setGuestTeamName(bbMatch.getGuestTeam());
		}
		List<BBAsianOddVO> bbAsianOddsList = bbAsianOddList(lcMatchId);
		bbAsianOddResult.setBbAsianOddList(bbAsianOddsList);
		return bbAsianOddResult;
	}

	@Transactional
	@Override
	public BBOddsBigSmallResult bbBigSmallOddResult(String lcMatchId) {
		List<BBOddsBigSmallVO> bbOddsBigSmallList = bbBigSmallOddList(lcMatchId);
		BBMatchScore bbMatch = getBBMatch(lcMatchId);
		BBOddsBigSmallResult bigSmallResult = new BBOddsBigSmallResult();
		if(null != bbMatch) {
			bigSmallResult.setHomeTeamName(bbMatch.getHomeTeam());
			bigSmallResult.setGuestTeamName(bbMatch.getGuestTeam());
		}
		bigSmallResult.setBbOddsBigSmallList(bbOddsBigSmallList);
		return bigSmallResult;
	}
}