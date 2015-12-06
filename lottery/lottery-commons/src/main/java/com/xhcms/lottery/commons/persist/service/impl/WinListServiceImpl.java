package com.xhcms.lottery.commons.persist.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.math.BigDecimal;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.xhcms.commons.lang.Paging;
import com.xhcms.lottery.commons.data.FollowWinList;
import com.xhcms.lottery.commons.data.ShowWinList;
import com.xhcms.lottery.commons.data.ShowWinListVo;
import com.xhcms.lottery.commons.persist.dao.BetSchemeDao;
import com.xhcms.lottery.commons.persist.dao.FollowWinListDao;
import com.xhcms.lottery.commons.persist.dao.ShowWinListDao;
import com.xhcms.lottery.commons.persist.entity.BetSchemePO;
import com.xhcms.lottery.commons.persist.entity.FollowWinListPO;
import com.xhcms.lottery.commons.persist.entity.ShowWinListPO;
import com.xhcms.lottery.commons.persist.service.WinListService;

/**
 * @author yonglizhu
 */
public class WinListServiceImpl implements WinListService {

	@Autowired
	private ShowWinListDao showWinListDao;

	@Autowired
	private FollowWinListDao followWinListDao;

	@Autowired
	private BetSchemeDao betSchemeDao;

	/**
	 * 查询晒单中奖榜
	 * 
	 * @param lotteryId
	 */
	@Override
	@Transactional(readOnly = true)
	public void findShowWinList(Paging paging, String lotteryId) {
		List<ShowWinListPO> showWinListPOs = showWinListDao.findShowWinList(
				paging, lotteryId);
		
		List<ShowWinList> result = new ArrayList<ShowWinList>();
		for(Object po : showWinListPOs) {
			ShowWinList showWinList = new ShowWinList();
			BeanUtils.copyProperties(po, showWinList);
			result.add(showWinList);
		}

		paging.setResults(result);
	}

	/**
	 * 查询跟单中奖榜
	 * 
	 * @param lotteryId
	 */
	@Override
	@Transactional(readOnly = true)
	public void findFollowWinList(Paging paging, String lotteryId) {
		List<FollowWinListPO> followWinListPOs = followWinListDao
				.findFollowWinList(paging, lotteryId);
		
		List<FollowWinList> result = new ArrayList<FollowWinList>();
		for(Object po : followWinListPOs) {
			FollowWinList followWinList = new FollowWinList();
			BeanUtils.copyProperties(po, followWinList);
			result.add(followWinList);
		}
	
		paging.setResults(result);
	}

	@Override
	@Transactional
	public void countWinList(BetSchemePO betSchemePO) {
		if (betSchemePO.getShowScheme() == 1) {
			countShowWinListAndSave(betSchemePO, null);
		}
		if (betSchemePO.getType() == 2) {
			countFollowWinListAndSave(betSchemePO);
			// 更新晒单中奖榜
			List<Long> ids = new ArrayList<Long>();
			ids.add(betSchemePO.getFollowedSchemeId());
			List<BetSchemePO> betSchemes = betSchemeDao.find(ids);
			if (betSchemes != null && betSchemes.size() > 0) {
				countShowWinListAndSave(betSchemes.get(0), betSchemePO);
			}
		}
	}

	/**
	 * 统计晒单中奖榜
	 */
	private void countShowWinListAndSave(BetSchemePO betSchemePO, BetSchemePO followSchemePO) {
		ShowWinListPO showWinListPO = showWinListDao
				.findShowWinListBySponsorIdLotteryId(
						betSchemePO.getSponsorId(), betSchemePO.getLotteryId());
		if (showWinListPO == null) {
			ShowWinListPO showPO = getSaveShowWinListPO(betSchemePO, followSchemePO);
			showWinListDao.saveShowWinList(showPO);
		} else {
			if (followSchemePO == null) {
				showWinListPO.setShowSchemeCount(showWinListPO
						.getShowSchemeCount() + 1);
				showWinListPO.setTotalAmount(showWinListPO.getTotalAmount()
						+ betSchemePO.getTotalAmount());
				showWinListPO.setAfterTaxBonus(showWinListPO.getAfterTaxBonus()
						.add(betSchemePO.getAfterTaxBonus()));
				showWinListPO.setFollowCount(showWinListPO.getFollowCount()
						+ betSchemePO.getFollowingCount());
				if (showWinListPO.getAfterTaxBonus() != null) {
					int returnRate = showWinListPO.getAfterTaxBonus()
							.divide(new BigDecimal(showWinListPO.getTotalAmount()), 0,
									BigDecimal.ROUND_HALF_UP).intValue();
					showWinListPO.setReturnRate(returnRate);
				}
			} else {
				showWinListPO.setFollowTotalAmount(showWinListPO
						.getFollowTotalAmount() + followSchemePO.getTotalAmount());
				
				if (showWinListPO.getFollowTotalBonus() != null
						&& followSchemePO.getAfterTaxBonus() != null) {
					BigDecimal totalBonus = showWinListPO.getFollowTotalBonus()
							.add(followSchemePO.getAfterTaxBonus());
					showWinListPO.setFollowTotalBonus(totalBonus);
				}
			}
			
			showWinListPO.setLastCountTime(new Date());
			showWinListDao.updateShowWinList(showWinListPO);
		}
	}

	private ShowWinListPO getSaveShowWinListPO(BetSchemePO betSchemePO,
			BetSchemePO followSchemePO) {
		Date date = new Date();
		ShowWinListPO showWinListPO = new ShowWinListPO();
		showWinListPO.setSponsorId(betSchemePO.getSponsorId());
		showWinListPO.setSponsor(betSchemePO.getSponsor());
		showWinListPO.setLotteryId(betSchemePO.getLotteryId());
		if (followSchemePO == null) {
			showWinListPO.setShowSchemeCount(1);
			showWinListPO.setTotalAmount(betSchemePO.getTotalAmount());
			showWinListPO.setAfterTaxBonus(betSchemePO.getAfterTaxBonus());
			showWinListPO.setFollowCount(betSchemePO.getFollowingCount());
			if (betSchemePO.getAfterTaxBonus() != null) {				
				int returnRate = betSchemePO.getAfterTaxBonus()
				.divide(new BigDecimal(betSchemePO.getTotalAmount()), 0,
						BigDecimal.ROUND_HALF_UP).intValue();
				showWinListPO.setReturnRate(returnRate);
			} else {
				showWinListPO.setReturnRate(0);
			}
		} else {
			showWinListPO.setFollowTotalAmount(followSchemePO.getTotalAmount());
			if(followSchemePO.getAfterTaxBonus() != null) {
				showWinListPO.setFollowTotalBonus(followSchemePO.getAfterTaxBonus());
			} else {
				showWinListPO.setFollowTotalBonus(new BigDecimal(0));
			}
			
		}
		showWinListPO.setCreatedTime(date);
		showWinListPO.setLastCountTime(date);

		return showWinListPO;
	}

	/**
	 * 统计跟单中奖榜
	 */
	private void countFollowWinListAndSave(BetSchemePO betSchemePO) {
		FollowWinListPO followWinListPO = followWinListDao
				.findFollowWinListBySponsorIdLotteryId(
						betSchemePO.getSponsorId(), betSchemePO.getLotteryId());
		if (followWinListPO == null) {
			FollowWinListPO followPO = getSaveFollowWinListPO(betSchemePO);
			followWinListDao.saveFollowWinList(followPO);
		} else {
			followWinListPO.setFollowSchemeCount(followWinListPO
					.getFollowSchemeCount() + 1);
			followWinListPO.setTotalAmount(followWinListPO.getTotalAmount()
					+ betSchemePO.getTotalAmount());
			followWinListPO.setAfterTaxBonus(followWinListPO.getAfterTaxBonus()
					.add(betSchemePO.getAfterTaxBonus()));
			followWinListPO.setLastCountTime(new Date());
			followWinListDao.updateFollowWinList(followWinListPO);
		}
	}

	private FollowWinListPO getSaveFollowWinListPO(BetSchemePO betSchemePO) {
		Date date = new Date();
		FollowWinListPO followWinListPO = new FollowWinListPO();
		followWinListPO.setFollowerId(betSchemePO.getSponsorId());
		followWinListPO.setFollower(betSchemePO.getSponsor());
		followWinListPO.setLotteryId(betSchemePO.getLotteryId());
		followWinListPO.setFollowSchemeCount(1);
		followWinListPO.setTotalAmount(betSchemePO.getTotalAmount());
		followWinListPO.setAfterTaxBonus(betSchemePO.getAfterTaxBonus());
		followWinListPO.setCreatedTime(date);
		followWinListPO.setLastCountTime(date);

		return followWinListPO;
	}

	
}
