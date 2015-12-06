package com.unison.lottery.pm.service.impl;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.unison.lottery.pm.data.ShowFollowResult;
import com.xhcms.lottery.commons.persist.dao.PMPromotionDao;
import com.unison.lottery.pm.persist.ShowFollowDao;
import com.unison.lottery.pm.service.ShowFollowWinListService;
import com.unison.lottery.pm.utils.TextTool;
import com.xhcms.lottery.commons.persist.dao.FollowWinListDao;
import com.xhcms.lottery.commons.persist.dao.ShowSchemeDao;
import com.xhcms.lottery.commons.persist.dao.ShowWinListDao;
import com.xhcms.lottery.commons.persist.entity.FollowWinListPO;
import com.xhcms.lottery.commons.persist.entity.PromotionPO;
import com.xhcms.lottery.commons.persist.entity.ShowWinListPO;

public class ShowFollowWinListServiceImpl implements ShowFollowWinListService {
	Logger log = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private ShowWinListDao showWinListDao;

	@Autowired
	private FollowWinListDao followWinListDao;
	
	@Autowired
	private ShowSchemeDao showSchemeDao;
	
	@Autowired
	private ShowFollowDao showFollowDao;
	
	@Autowired
	private PMPromotionDao pMPromotionDao;

	/**
	 * 统计晒单中奖榜
	 */
	@Override
	@Transactional
	public void countShowWinListAndSave() {
		showWinListDao.deleteShowWinList();
		List results = showSchemeDao.findShowWinListFromScheme();
		
		if (results != null && results.size() > 0) {
			for (int i = 0; i < results.size(); i++) {
				Object[] obj = (Object[]) results.get(i);
				ShowWinListPO showWinListPO = getSaveShowWinListPO(obj);
				List followData = showSchemeDao.countFollowDataforShowWinList(
						showWinListPO.getSponsorId(),
						showWinListPO.getLotteryId());
				if (followData != null && followData.size() > 0) {
					for (int j = 0; j < followData.size(); j++) {
						Object[] data = (Object[]) followData.get(j);
						if (data[0] != null) {
							showWinListPO.setFollowTotalAmount(((Long) data[0])
									.intValue());
						}
						if (data[1] != null) {
							showWinListPO
									.setFollowTotalBonus((BigDecimal) data[1]);
						}
					}
				}
				 
				showWinListDao.saveShowWinList(showWinListPO);
			}
		}
	}
	
	private ShowWinListPO getSaveShowWinListPO(Object[] obj) {
		Date date = new Date();
		ShowWinListPO showWinListPO = new ShowWinListPO();
		showWinListPO.setSponsorId((Long)obj[5]);
		showWinListPO.setSponsor((String)obj[0]);
		showWinListPO.setLotteryId((String)obj[6]);
		showWinListPO.setShowSchemeCount(((Long)obj[1]).intValue());
		showWinListPO.setTotalAmount(((Long)obj[2]).intValue());
		showWinListPO.setAfterTaxBonus((BigDecimal)obj[3]);
		if ((BigDecimal) obj[3] != null) {
			BigDecimal returnRate = ((BigDecimal) obj[3]).divide(
					new BigDecimal((Long)obj[2]), 0, BigDecimal.ROUND_HALF_UP);
			showWinListPO.setReturnRate(returnRate.intValue());
		}		
		showWinListPO.setFollowCount(((Long)obj[4]).intValue());
		showWinListPO.setCreatedTime(date);
		showWinListPO.setLastCountTime(date);
		
		return showWinListPO;
	}
	
	/**
	 * 统计跟单中奖榜
	 */
	@Override
	@Transactional
	public void countFollowWinListAndSave() {	
		followWinListDao.deleteFollowWinList();
		List results = showSchemeDao.findFollowWinListFromScheme();
		if(results != null && results.size() > 0) {
			for(int i=0;i<results.size();i++) {
				Object[] obj= (Object[])results.get(i);
				FollowWinListPO followWinListPO = getSaveFollowWinListPO(obj);
				followWinListDao.saveFollowWinList(followWinListPO);
			}
		}
	}
	
	private FollowWinListPO getSaveFollowWinListPO(Object[] obj) {
		Date date = new Date();
		FollowWinListPO followWinListPO = new FollowWinListPO();
		followWinListPO.setFollowerId((Long)obj[4]);
		followWinListPO.setFollower((String)obj[0]);
		followWinListPO.setLotteryId((String)obj[5]);
		followWinListPO.setFollowSchemeCount(((Long)obj[1]).intValue());
		followWinListPO.setTotalAmount(((Long)obj[2]).intValue());
		followWinListPO.setAfterTaxBonus((BigDecimal)obj[3]);
		followWinListPO.setCreatedTime(date);
		followWinListPO.setLastCountTime(date);
		
		return followWinListPO;
	}
	
	/**
	 * 双节活动晒单跟单中奖排行榜
	 */
	@Override
	@Transactional
	public List<ShowFollowResult> findShowFollowTOP(int top, Long promotionId) {
		PromotionPO promotionPO = pMPromotionDao.get(promotionId);
		log.info("show follow top list promotionId={}" + promotionId);
		Date startTime = promotionPO.getStartTime();
		Date endTime = promotionPO.getEndTime();
		log.info("startTime={}" + startTime);
		log.info("endTime={}" + endTime);
		log.info("top={}" + top);
		List sfList = showFollowDao.findShowFollowTOP(startTime, endTime, top);
		log.info("sfList size={}" + sfList.size());
		List<ShowFollowResult> sfDataList = new ArrayList<ShowFollowResult>();
		if(sfList != null && sfList.size() > 0) {
			for(int i = 0; i < sfList.size(); i++){
				ShowFollowResult sfResult = new ShowFollowResult();
				Object[] po = (Object[])sfList.get(i);
				sfResult.setUserId((BigInteger)po[0]);
				String username = TextTool.hideString((String)po[1], 8);
				sfResult.setUsername(username);
				sfResult.setFullname((String)po[1]);
				log.info("username={}" + sfResult.getUsername());
				log.info("username size={}" + sfResult.getUsername().length());
				sfResult.setTotalAmount(((BigDecimal)po[2]).setScale(0, BigDecimal.ROUND_HALF_UP));
				log.info("userId={}" + sfResult.getUserId());
				//晒单中奖金额
				Object showAmount = showFollowDao.findShowWin(startTime, endTime, sfResult.getUserId());
				if(showAmount != null) {
					sfResult.setShowAmount(((BigDecimal)showAmount).setScale(0, BigDecimal.ROUND_HALF_UP));
					log.info("showAmount={}" + sfResult.getShowAmount());
				} else {
					sfResult.setShowAmount(new BigDecimal(0));
				}
				//跟单中奖金额
				Object followAmount = showFollowDao.findFollowWin(startTime, endTime, sfResult.getUserId());
				if(followAmount != null) {
					sfResult.setFollowAmount(((BigDecimal)followAmount).setScale(0, BigDecimal.ROUND_HALF_UP));
					log.info("followAmount={}" + sfResult.getFollowAmount());
				} else {
					sfResult.setFollowAmount(new BigDecimal(0));
				}
				log.info("sfDataList={}" + sfDataList);
				sfDataList.add(sfResult);
			}
		}
		log.info("sfDataList={}" + sfDataList);
		return sfDataList;
	}
	
	/**
	 * 元月活动晒单排行榜
	 */
	@Override
	@Transactional
	public List<ShowFollowResult> findShowTOP(int top, Long promotionId) {
		PromotionPO promotionPO = pMPromotionDao.get(promotionId);
		if(null == promotionPO) {
			log.error("cannot find promotion ID:{}" + promotionId);
			return null;
		}
		log.info("show top list promotionId={}" + promotionId);
		Date startTime = promotionPO.getStartTime();
		Date endTime = promotionPO.getEndTime();
		log.info("startTime={}" + startTime);
		log.info("endTime={}" + endTime);
		log.info("top={}" + top);
		
		List<ShowFollowResult> showDataList = new ArrayList<ShowFollowResult>();
		List showList = showFollowDao.findShowTOP(startTime, endTime, top);
		
		if(null != showList && showList.size() > 0) {
			for(int i = 0; i < showList.size(); i++) {
				ShowFollowResult showResult = new ShowFollowResult();
				Object[] po = (Object[])showList.get(i);
				showResult.setShowNum((BigInteger)po[0]);
				showResult.setUserId((BigInteger)po[1]);
				String username = TextTool.hideString((String)po[2], 8);
				showResult.setUsername(username);
				showResult.setFullname((String)po[2]);
				showResult.setWinAmount(((BigDecimal)po[3]).setScale(0, BigDecimal.ROUND_HALF_UP));
				showResult.setReturnRatio(((BigDecimal)po[4]).setScale(1, BigDecimal.ROUND_HALF_UP));
				
				showDataList.add(showResult);
			}
		}
		
		return showDataList;
	}
}
